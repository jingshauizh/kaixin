package com.kaixin001.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Location;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.adapter.CheckBoxListAdapter;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.AlbumInfo;
import com.kaixin001.item.VoiceRecordUploadTask;
import com.kaixin001.model.FaceModel;
import com.kaixin001.model.Setting;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FancyDialogAdapter;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.FriendsUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.FaceKeyboardView;
import com.kaixin001.view.FaceKeyboardView.OnFaceSelectedListener;
import com.kaixin001.view.LocationMarkerView;
import com.kaixin001.view.media.KXMediaManager;
import com.kaixin001.view.media.KXMediaView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoiceRecordFragment extends BaseFragment
  implements View.OnTouchListener, View.OnClickListener, TextWatcher, DialogInterface.OnClickListener
{
  private static final String FROM_WEBPAGE = "from_webpage";
  private static final long MAX_RECORD_TIMESPAN = 60000L;
  private static final long MIN_VALID_RECORD_TIMESPAN = 3000L;
  static final String RECORD_FILE = "RECORD_FILE_PATH";
  private static final String TAG = "VoiceRecordActivity";
  private RelativeLayout animLayout;
  private ImageView atImgView;
  private volatile File audioFile;
  private int audioLength = 0;
  private ImageView cameraImgView;
  private ImageView cancelBtn;
  private ImageView circle1;
  private ImageView circle2;
  private ImageView circle3;
  private volatile State currentState = State.STOPPED;
  private TextView elapsedTime;
  private ImageView faceImgView;
  private FancyDialogAdapter fancyDialogAdapter = new FancyDialogAdapter();
  private Handler handler;
  private volatile State lastState = State.STOPPED;
  private ImageView locationImgView;
  private LocationMarkerView locationMarker;
  private FaceKeyboardView mFaceKeyboardView;
  private KXMediaView mediaPlayer;
  private MediaRecorder mediaRecorder = null;
  private HashMap<String, Bitmap> nameBmpMap = new HashMap();
  private int orientation = 1;
  private ImageView photoImgView;
  private ImageView photoImgViewDelete;
  private String postScript = null;
  private String privacy = "0";
  private ProgressBar progressBar;
  private RelativeLayout progressLayout;
  private ImageView recordImgView;
  private ImageView recordProgress;
  private EditText recordText;
  private String selectedImgFile;
  private ImageView sendBtn;
  private volatile long startTime = 0L;
  private Timer timer;
  private Toast toast;
  private ImageView volumeImgView;
  private int vuSize = 0;

  private void backOrExit()
  {
    if (this.audioFile != null)
    {
      DialogUtil.showKXAlertDialog(getActivity(), 2131427451, 2131427382, 2131427383, this, this);
      return;
    }
    finish();
  }

  private void changeContentForOrientation()
  {
    int i = this.recordText.getSelectionStart();
    int j = this.recordText.getSelectionEnd();
    String str1 = this.recordText.getText().toString();
    SpannableString localSpannableString;
    Matcher localMatcher;
    if (((1 == this.orientation) || (Setting.getInstance().isShowFaceIconInLandScape())) && (!TextUtils.isEmpty(str1)))
    {
      localSpannableString = StringUtil.convertTextToStateFace(str1);
      if (localSpannableString != null)
      {
        localMatcher = Pattern.compile("@([0-9]+)\\(\\#(\\S+?)\\#\\)").matcher(localSpannableString);
        if (localMatcher.find());
      }
      else
      {
        this.recordText.setText(localSpannableString);
      }
    }
    while (true)
    {
      this.recordText.setSelection(i, j);
      return;
      String str2 = localMatcher.group();
      String str3 = localMatcher.group(2);
      int k = localMatcher.start();
      int m = localMatcher.end();
      Bitmap localBitmap = (Bitmap)this.nameBmpMap.get(str2);
      if (localBitmap == null)
      {
        localBitmap = ImageCache.createStringBitmap("@" + str3, this.recordText.getPaint());
        this.nameBmpMap.put(str2, localBitmap);
      }
      localSpannableString.setSpan(new ImageSpan(localBitmap), k, m, 33);
      break;
      this.recordText.setText(str1);
    }
  }

  private int handlerStrSpace(int paramInt1, String paramString, int paramInt2)
  {
    int i;
    if (paramInt2 >= 1)
    {
      paramInt1 = 1;
      i = 1;
      if (i < paramInt2);
    }
    else
    {
      return paramInt1;
    }
    String str1 = String.valueOf(paramString.charAt(i));
    String str2 = String.valueOf(paramString.charAt(i - 1));
    if (str1.equals(" "))
      if (!str2.equals(" "))
        paramInt1++;
    while (true)
    {
      i++;
      break;
      paramInt1++;
    }
  }

  private void initFaceKeyBoardLayout()
  {
    this.mFaceKeyboardView = ((FaceKeyboardView)findViewById(2131361976));
    this.mFaceKeyboardView.setOnFaceSelectedListener(new FaceKeyboardView.OnFaceSelectedListener()
    {
      public void FaceSelected(int paramInt, String paramString, Bitmap paramBitmap)
      {
        switch (paramInt)
        {
        default:
          VoiceRecordFragment.this.insertFaceIcon(paramString, paramBitmap);
        case -2:
        case -1:
        }
        int i;
        do
        {
          return;
          i = VoiceRecordFragment.this.recordText.getSelectionStart();
        }
        while (i <= 0);
        String str1 = VoiceRecordFragment.this.recordText.getText().toString();
        ArrayList localArrayList = FaceModel.getInstance().getAllFaceStringSortByLength();
        int j = 1;
        Iterator localIterator = localArrayList.iterator();
        if (!localIterator.hasNext());
        while (true)
        {
          VoiceRecordFragment.this.recordText.getText().delete(i - j, i);
          return;
          String str2 = (String)localIterator.next();
          if (!str1.endsWith(str2))
            break;
          j = str2.length();
        }
      }
    });
    this.mFaceKeyboardView.init(getActivity());
    showFaceKeyboardView(false);
  }

  private void initPrivacyBtn()
  {
    String[] arrayOfString = getResources().getStringArray(2131492890);
    new CheckBoxListAdapter(getActivity(), arrayOfString)
    {
      public int getCount()
      {
        return this.val$locks.length;
      }

      public Object getItem(int paramInt)
      {
        return this.val$locks[paramInt];
      }
    };
    Button localButton = (Button)findViewById(2131363998);
    localButton.setOnClickListener(new View.OnClickListener(arrayOfString, localButton)
    {
      public void onClick(View paramView)
      {
        DialogUtil.showValueListDlg(VoiceRecordFragment.this.getActivity(), 2131427901, this.val$locks, VoiceRecordFragment.this.fancyDialogAdapter, new DialogInterface.OnClickListener(this.val$btn, this.val$locks)
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
            VoiceRecordFragment.this.fancyDialogAdapter.setSelectedIndex(paramInt);
            this.val$btn.setText(this.val$locks[paramInt]);
            switch (paramInt)
            {
            default:
              return;
            case 0:
              VoiceRecordFragment.this.privacy = "0";
              return;
            case 1:
              VoiceRecordFragment.this.privacy = "3";
              return;
            case 2:
              VoiceRecordFragment.this.privacy = "1";
              return;
            case 3:
            }
            VoiceRecordFragment.this.privacy = "2";
          }
        }
        , true, false);
      }
    });
  }

  private void initViews()
  {
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(2131428477);
    findViewById(2131362919).setVisibility(8);
    this.circle1 = ((ImageView)findViewById(2131364004));
    this.circle2 = ((ImageView)findViewById(2131364005));
    this.circle3 = ((ImageView)findViewById(2131364006));
    this.cameraImgView = ((ImageView)findViewById(2131363993));
    this.atImgView = ((ImageView)findViewById(2131363994));
    this.faceImgView = ((ImageView)findViewById(2131363995));
    this.locationImgView = ((ImageView)findViewById(2131363996));
    this.photoImgView = ((ImageView)findViewById(2131363988));
    this.photoImgViewDelete = ((ImageView)findViewById(2131363989));
    this.recordText = ((EditText)findViewById(2131363991));
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.postScript = localBundle.getString("postScript");
      if ((this.postScript != null) && (!"".equals(this.postScript)))
        this.recordText.setHint(2131428473);
      String str = localBundle.getString("content");
      this.recordText.setText(str);
    }
    this.recordText.addTextChangedListener(this);
    this.recordText.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        VoiceRecordFragment.this.mFaceKeyboardView.setVisibility(8);
      }
    });
    this.cancelBtn = ((ImageView)findViewById(2131362914));
    this.cancelBtn.setOnClickListener(this);
    this.sendBtn = ((ImageView)findViewById(2131362928));
    this.sendBtn.setImageResource(2130839272);
    this.sendBtn.setOnClickListener(this);
    this.sendBtn.setClickable(false);
    this.recordProgress = ((ImageView)findViewById(2131364009));
    this.elapsedTime = ((TextView)findViewById(2131364008));
    this.animLayout = ((RelativeLayout)findViewById(2131364000));
    this.animLayout.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
      {
        return true;
      }
    });
    this.progressLayout = ((RelativeLayout)findViewById(2131364007));
    this.recordImgView = ((ImageView)findViewById(2131363999));
    this.recordImgView.setOnTouchListener(this);
    this.volumeImgView = ((ImageView)findViewById(2131364002));
    this.progressBar = ((ProgressBar)findViewById(2131364010));
    this.mediaPlayer = ((KXMediaView)findViewById(2131363990));
    this.locationMarker = ((LocationMarkerView)findViewById(2131363986));
    initPrivacyBtn();
    this.toast = Toast.makeText(getActivity(), 2131428476, 1);
  }

  private void showFaceKeyboardView(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mFaceKeyboardView.setVisibility(0);
      ActivityUtil.hideInputMethod(getActivity());
      return;
    }
    this.mFaceKeyboardView.setVisibility(8);
  }

  private void startAnimation()
  {
    this.handler.postDelayed(new AnimationStarter(this.circle1), 0L);
    this.handler.postDelayed(new AnimationStarter(this.circle2), 500L);
    this.handler.postDelayed(new AnimationStarter(this.circle3), 1000L);
  }

  private void stopAnimation()
  {
    this.handler.post(new Runnable()
    {
      public void run()
      {
        VoiceRecordFragment.this.circle1.setVisibility(8);
        VoiceRecordFragment.this.circle2.setVisibility(8);
        VoiceRecordFragment.this.circle3.setVisibility(8);
        VoiceRecordFragment.this.circle1.clearAnimation();
        VoiceRecordFragment.this.circle2.clearAnimation();
        VoiceRecordFragment.this.circle3.clearAnimation();
      }
    });
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
    this.selectedImgFile = paramString1;
    Bitmap localBitmap = FileUtil.getBmpFromFile(getActivity(), paramString1);
    this.photoImgView.setImageBitmap(localBitmap);
    this.photoImgView.setClickable(true);
    this.photoImgView.setOnClickListener(new View.OnClickListener(paramString1)
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(VoiceRecordFragment.this.getActivity(), PreviewUploadPhotoFragment.class);
        Bundle localBundle = new Bundle();
        localBundle.putString("imgFilePath", this.val$filePath);
        localIntent.putExtras(localBundle);
        VoiceRecordFragment.this.startActivityForResult(localIntent, 0);
      }
    });
    this.photoImgView.setVisibility(0);
    this.photoImgViewDelete.setVisibility(0);
    this.photoImgViewDelete.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        VoiceRecordFragment.this.selectedImgFile = null;
        VoiceRecordFragment.this.photoImgView.setImageBitmap(null);
        VoiceRecordFragment.this.photoImgView.setVisibility(8);
        VoiceRecordFragment.this.photoImgViewDelete.setVisibility(8);
      }
    });
  }

  protected void insertFaceIcon(int paramInt)
  {
    FaceModel localFaceModel = FaceModel.getInstance();
    String str;
    Editable localEditable;
    int i;
    int j;
    if (localFaceModel.getStateFaceStrings() != null)
    {
      str = (String)localFaceModel.getStateFaceStrings().get(paramInt);
      localEditable = this.recordText.getText();
      i = Selection.getSelectionStart(localEditable);
      j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      if (localEditable.length() <= 140 - str.length())
        break label109;
      Toast.makeText(getActivity(), 2131427899, 0).show();
    }
    label109: 
    do
    {
      return;
      localEditable.replace(i, j, str);
    }
    while ((1 != this.orientation) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    localEditable.setSpan(new ImageSpan((Bitmap)localFaceModel.getStateFaceIcons().get(paramInt)), i, i + str.length(), 33);
  }

  protected void insertFaceIcon(String paramString, Bitmap paramBitmap)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramBitmap == null));
    Editable localEditable;
    int i;
    do
    {
      return;
      localEditable = this.recordText.getText();
      i = Selection.getSelectionStart(localEditable);
      int j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      if (localEditable.length() > 140 - paramString.length())
      {
        Toast.makeText(getActivity(), 2131427899, 0).show();
        return;
      }
      localEditable.replace(i, j, paramString.subSequence(0, paramString.length()));
    }
    while ((1 != this.orientation) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    localEditable.setSpan(new ImageSpan(paramBitmap), i, i + paramString.length(), 33);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1)
    {
      if (paramInt1 != 4)
        break label88;
      String str1 = paramIntent.getStringExtra("fuid");
      String str2 = paramIntent.getStringExtra("fname");
      int i = this.recordText.getSelectionStart();
      if (FriendsUtil.insertFriendIntoContent(i, i, str1, str2, this.recordText, this.nameBmpMap, 140, this.orientation) < 0)
        Toast.makeText(getActivity(), 2131427900, 0).show();
    }
    label88: 
    do
      return;
    while ((paramInt1 != 0) || (paramIntent.getIntExtra("userAction", 0) != 101));
    this.selectedImgFile = null;
    this.photoImgView.setImageBitmap(null);
    this.photoImgView.setVisibility(8);
    this.photoImgViewDelete.setVisibility(8);
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    if (paramInt == -1)
      finish();
  }

  public void onClick(View paramView)
  {
    if (paramView == this.cancelBtn)
      backOrExit();
    label185: 
    do
    {
      return;
      if (paramView == this.sendBtn)
      {
        VoiceRecordUploadTask localVoiceRecordUploadTask = new VoiceRecordUploadTask(getActivity().getApplicationContext());
        localVoiceRecordUploadTask.setTaskType(1);
        localVoiceRecordUploadTask.setAudioFormat("AMR");
        localVoiceRecordUploadTask.setAudioLength(String.valueOf(this.audioLength));
        localVoiceRecordUploadTask.setUploadAudio(this.audioFile.getAbsolutePath());
        localVoiceRecordUploadTask.setTaskType(1);
        localVoiceRecordUploadTask.setmImageFileName(this.selectedImgFile);
        localVoiceRecordUploadTask.setPrivacy(this.privacy);
        localVoiceRecordUploadTask.setmStatus("2");
        String str1 = this.locationMarker.getLandmark();
        if (str1 == null)
          str1 = "";
        localVoiceRecordUploadTask.setLocationName(str1);
        Location localLocation = this.locationMarker.getLocation();
        String str3;
        if (localLocation != null)
        {
          localVoiceRecordUploadTask.setLatitude(String.valueOf(localLocation.getLatitude()));
          localVoiceRecordUploadTask.setLongitude(String.valueOf(localLocation.getLongitude()));
          String str2 = this.recordText.getText().toString();
          if (TextUtils.isEmpty(str2))
            break label280;
          localVoiceRecordUploadTask.setTitle(str2);
          if (this.postScript != null)
          {
            str3 = getResources().getString(2131428473);
            if (TextUtils.isEmpty(str2))
              break label297;
            localVoiceRecordUploadTask.setTitle(str3 + str2);
          }
        }
        while (true)
        {
          UploadTaskListEngine.getInstance().addUploadTask(localVoiceRecordUploadTask);
          setResult(10002, getActivity().getIntent());
          finish();
          return;
          localVoiceRecordUploadTask.setLatitude("");
          localVoiceRecordUploadTask.setLongitude("");
          break;
          localVoiceRecordUploadTask.setTitle(getResources().getString(2131428472));
          break label185;
          localVoiceRecordUploadTask.setTitle(str3);
        }
      }
      if (paramView == this.cameraImgView)
      {
        getActivity().getApplicationContext().getSharedPreferences("from_webpage", 0).edit().putBoolean("fromwebpage", false).commit();
        Intent localIntent2 = new Intent();
        localIntent2.setClass(getActivity(), PhotoSelectFragment.class);
        localIntent2.putExtra("single", true);
        AnimationUtil.startFragmentForResult(this, localIntent2, 103, 3);
        return;
      }
      if (paramView == this.atImgView)
      {
        Intent localIntent1 = new Intent(getActivity(), FriendsFragment.class);
        Bundle localBundle = new Bundle();
        localBundle.putInt("MODE", 2);
        localIntent1.putExtras(localBundle);
        AnimationUtil.startFragmentForResult(this, localIntent1, 4, 3);
        return;
      }
      if (paramView != this.faceImgView)
        continue;
      showFaceListDialog(true);
      return;
    }
    while (paramView != this.locationImgView);
    label280: label297: this.locationMarker.toggleState();
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    if (this.orientation != paramConfiguration.orientation)
    {
      this.orientation = paramConfiguration.orientation;
      changeContentForOrientation();
    }
    super.onConfigurationChanged(paramConfiguration);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903412, paramViewGroup, false);
  }

  public void onDestroy()
  {
    super.onDestroy();
    this.timer.cancel();
    if (this.mediaRecorder != null)
    {
      this.mediaRecorder.reset();
      this.mediaRecorder.release();
      this.mediaRecorder = null;
    }
    if (this.audioFile != null)
      this.audioFile.deleteOnExit();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      backOrExit();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void onPause()
  {
    super.onPause();
    KXMediaManager.getInstance().requestStopCurrentMedia();
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    if (isNeedReturn())
      return;
    String str = paramCharSequence.toString().trim();
    if (140 < handlerStrSpace(0, str, str.length()))
    {
      this.sendBtn.setEnabled(false);
      return;
    }
    this.sendBtn.setEnabled(true);
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0)
    {
      Log.e("VoiceRecordActivity", "start recording");
      this.currentState = State.RUNNING;
      return true;
    }
    if (paramMotionEvent.getAction() == 1)
    {
      Log.e("VoiceRecordActivity", "end recording");
      this.currentState = State.STOPPED;
      return true;
    }
    paramMotionEvent.getAction();
    return true;
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.handler = new Handler();
    initViews();
    initFaceKeyBoardLayout();
    this.timer = new Timer(true);
    this.timer.scheduleAtFixedRate(new RecordTask(null), 0L, 100L);
    this.timer.scheduleAtFixedRate(new VolumeTask(null), 0L, 100L);
  }

  public void requestFinish()
  {
  }

  public void showFaceListDialog(boolean paramBoolean)
  {
    ImageView localImageView = (ImageView)findViewById(2131363995);
    if (this.mFaceKeyboardView.getVisibility() == 0)
    {
      localImageView.setImageResource(2130838130);
      showFaceKeyboardView(false);
      ActivityUtil.showInputMethod(getActivity());
      return;
    }
    localImageView.setImageResource(2130838135);
    showFaceKeyboardView(true);
  }

  private class AnimationStarter
    implements Runnable, Animation.AnimationListener
  {
    final View view;

    AnimationStarter(View arg2)
    {
      Object localObject;
      this.view = localObject;
    }

    private Animation createAnimation()
    {
      AnimationSet localAnimationSet = new AnimationSet(false);
      ScaleAnimation localScaleAnimation = new ScaleAnimation(0.9F, 1.6F, 0.9F, 1.6F, 1, 0.5F, 1, 0.5F);
      localScaleAnimation.setInterpolator(new AccelerateInterpolator());
      localAnimationSet.addAnimation(localScaleAnimation);
      localAnimationSet.addAnimation(new AlphaAnimation(1.0F, 0.0F));
      localAnimationSet.setDuration(1500L);
      localAnimationSet.setAnimationListener(this);
      return localAnimationSet;
    }

    public void onAnimationEnd(Animation paramAnimation)
    {
      this.view.setVisibility(8);
      if ((this.view == VoiceRecordFragment.this.circle3) && (VoiceRecordFragment.this.currentState == VoiceRecordFragment.State.RUNNING))
        VoiceRecordFragment.this.handler.postDelayed(new Runnable()
        {
          public void run()
          {
            if (VoiceRecordFragment.this.isNeedReturn())
              return;
            VoiceRecordFragment.this.startAnimation();
          }
        }
        , 100L);
    }

    public void onAnimationRepeat(Animation paramAnimation)
    {
    }

    public void onAnimationStart(Animation paramAnimation)
    {
    }

    public void run()
    {
      if (VoiceRecordFragment.this.isNeedReturn());
      do
        return;
      while (VoiceRecordFragment.this.currentState != VoiceRecordFragment.State.RUNNING);
      Animation localAnimation = createAnimation();
      this.view.setVisibility(0);
      this.view.startAnimation(localAnimation);
    }
  }

  private class RecordTask extends TimerTask
  {
    private RecordTask()
    {
    }

    private void createAudioFile()
    {
      try
      {
        File localFile = VoiceRecordFragment.this.getActivity().getCacheDir();
        VoiceRecordFragment.this.audioFile = File.createTempFile("record", ".amr", localFile);
        return;
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
    }

    private void fireRecordCompleted(long paramLong)
    {
      Log.e("VoiceRecordActivity", "录音文件长度:" + VoiceRecordFragment.this.audioFile.length());
      VoiceRecordFragment.this.handler.post(new Runnable(paramLong)
      {
        public void run()
        {
          VoiceRecordFragment.this.audioLength = (int)(this.val$elapsed / 1000L);
          VoiceRecordFragment.this.mediaPlayer.setMediaData(VoiceRecordFragment.this.audioFile.getAbsolutePath(), String.valueOf(VoiceRecordFragment.this.audioLength));
          VoiceRecordFragment.this.sendBtn.setClickable(true);
          VoiceRecordFragment.this.recordImgView.setVisibility(8);
          VoiceRecordFragment.this.animLayout.setVisibility(8);
          VoiceRecordFragment.this.animLayout.setOnTouchListener(null);
          VoiceRecordFragment.this.mediaPlayer.setVisibility(0);
          if (VoiceRecordFragment.this.postScript != null)
            VoiceRecordFragment.this.recordText.setText(VoiceRecordFragment.this.postScript);
          VoiceRecordFragment.this.recordText.requestFocus();
          VoiceRecordFragment.this.cameraImgView.setOnClickListener(VoiceRecordFragment.this);
          VoiceRecordFragment.this.atImgView.setOnClickListener(VoiceRecordFragment.this);
          VoiceRecordFragment.this.faceImgView.setOnClickListener(VoiceRecordFragment.this);
          VoiceRecordFragment.this.locationImgView.setOnClickListener(VoiceRecordFragment.this);
          VoiceRecordFragment.this.locationMarker.toggleState();
          VoiceRecordFragment.this.locationMarker.setVisibility(0);
        }
      });
    }

    private long getElapsedTime()
    {
      long l = 0L;
      if (VoiceRecordFragment.this.startTime != l)
        l = System.currentTimeMillis() - VoiceRecordFragment.this.startTime;
      return l;
    }

    private void startRecord()
    {
      VoiceRecordFragment.this.handler.post(new Runnable()
      {
        public void run()
        {
          VoiceRecordFragment.this.progressBar.setVisibility(0);
        }
      });
      try
      {
        if (VoiceRecordFragment.this.mediaRecorder != null)
        {
          VoiceRecordFragment.this.mediaRecorder.reset();
          VoiceRecordFragment.this.mediaRecorder.release();
          VoiceRecordFragment.this.mediaRecorder = null;
        }
        VoiceRecordFragment.this.mediaRecorder = new MediaRecorder();
        VoiceRecordFragment.this.mediaRecorder.reset();
        createAudioFile();
        VoiceRecordFragment.this.mediaRecorder.setAudioSource(1);
        VoiceRecordFragment.this.mediaRecorder.setOutputFormat(3);
        VoiceRecordFragment.this.mediaRecorder.setAudioEncoder(1);
        VoiceRecordFragment.this.mediaRecorder.setOutputFile(VoiceRecordFragment.this.audioFile.getAbsolutePath());
        VoiceRecordFragment.this.mediaRecorder.prepare();
        VoiceRecordFragment.this.mediaRecorder.start();
        VoiceRecordFragment.this.handler.post(new Runnable()
        {
          public void run()
          {
            VoiceRecordFragment.this.progressBar.setVisibility(8);
          }
        });
        VoiceRecordFragment.this.startAnimation();
        VoiceRecordFragment.this.startTime = System.currentTimeMillis();
        return;
      }
      catch (Exception localException)
      {
        VoiceRecordFragment.this.mediaRecorder.reset();
        VoiceRecordFragment.this.mediaRecorder.release();
        Log.e("VoiceRecordActivity", String.valueOf(localException));
      }
    }

    private void stopRecord()
    {
      VoiceRecordFragment.this.stopAnimation();
      VoiceRecordFragment.this.mediaRecorder.stop();
      long l = getElapsedTime();
      if (l > 3000L)
        fireRecordCompleted(l);
      while (true)
      {
        VoiceRecordFragment.this.startTime = 0L;
        VoiceRecordFragment.this.mediaRecorder.reset();
        VoiceRecordFragment.this.mediaRecorder.release();
        VoiceRecordFragment.this.mediaRecorder = null;
        return;
        VoiceRecordFragment.this.handler.post(new Runnable()
        {
          public void run()
          {
            VoiceRecordFragment.this.toast.show();
            VoiceRecordFragment.this.elapsedTime.setText("0''");
            RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)VoiceRecordFragment.this.recordProgress.getLayoutParams();
            localLayoutParams.width = 0;
            VoiceRecordFragment.this.recordProgress.setLayoutParams(localLayoutParams);
          }
        });
        if (VoiceRecordFragment.this.audioFile == null)
          continue;
        VoiceRecordFragment.this.audioFile.deleteOnExit();
      }
    }

    public void run()
    {
      if (VoiceRecordFragment.this.isNeedReturn());
      while (true)
      {
        return;
        if (VoiceRecordFragment.this.lastState != VoiceRecordFragment.this.currentState)
        {
          VoiceRecordFragment.this.lastState = VoiceRecordFragment.this.currentState;
          switch ($SWITCH_TABLE$com$kaixin001$fragment$VoiceRecordFragment$State()[VoiceRecordFragment.this.currentState.ordinal()])
          {
          default:
          case 1:
          case 2:
          }
        }
        while (VoiceRecordFragment.this.currentState == VoiceRecordFragment.State.RUNNING)
        {
          if (getElapsedTime() < 60000L)
            break label150;
          VoiceRecordFragment localVoiceRecordFragment1 = VoiceRecordFragment.this;
          VoiceRecordFragment localVoiceRecordFragment2 = VoiceRecordFragment.this;
          VoiceRecordFragment.State localState = VoiceRecordFragment.State.STOPPED;
          localVoiceRecordFragment2.currentState = localState;
          localVoiceRecordFragment1.lastState = localState;
          stopRecord();
          return;
          stopRecord();
          continue;
          startRecord();
        }
      }
      label150: VoiceRecordFragment.this.handler.post(new Runnable()
      {
        public void run()
        {
          long l = VoiceRecordFragment.RecordTask.this.getElapsedTime();
          VoiceRecordFragment.this.elapsedTime.setText(l / 1000L + "''");
          RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)VoiceRecordFragment.this.recordProgress.getLayoutParams();
          localLayoutParams.width = (int)(l * VoiceRecordFragment.this.progressLayout.getWidth() / 60000L);
          VoiceRecordFragment.this.recordProgress.setLayoutParams(localLayoutParams);
        }
      });
    }
  }

  private static enum State
  {
    static
    {
      RUNNING = new State("RUNNING", 1);
      State[] arrayOfState = new State[2];
      arrayOfState[0] = STOPPED;
      arrayOfState[1] = RUNNING;
      ENUM$VALUES = arrayOfState;
    }
  }

  private class VolumeTask extends TimerTask
  {
    private VolumeTask()
    {
    }

    public void run()
    {
      if (VoiceRecordFragment.this.isNeedReturn())
        return;
      if ((VoiceRecordFragment.this.currentState == VoiceRecordFragment.State.RUNNING) && (VoiceRecordFragment.this.mediaRecorder != null))
      {
        VoiceRecordFragment.this.vuSize = (VoiceRecordFragment.this.dipToPx(VoiceRecordFragment.this.getResources().getDimension(2131296262)) * VoiceRecordFragment.this.mediaRecorder.getMaxAmplitude() / 32768);
        VoiceRecordFragment.this.handler.post(new Runnable()
        {
          public void run()
          {
            RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)VoiceRecordFragment.this.volumeImgView.getLayoutParams();
            localLayoutParams.height = Math.min(VoiceRecordFragment.this.vuSize, 100);
            VoiceRecordFragment.this.volumeImgView.setLayoutParams(localLayoutParams);
          }
        });
        return;
      }
      VoiceRecordFragment.this.handler.post(new Runnable()
      {
        public void run()
        {
          RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)VoiceRecordFragment.this.volumeImgView.getLayoutParams();
          localLayoutParams.height = 0;
          VoiceRecordFragment.this.volumeImgView.setLayoutParams(localLayoutParams);
        }
      });
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.VoiceRecordFragment
 * JD-Core Version:    0.6.0
 */