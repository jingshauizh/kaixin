package com.kaixin001.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.Selection;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.CircleReplyNewsEngine;
import com.kaixin001.engine.KXEngine;
import com.kaixin001.engine.MessageEngine;
import com.kaixin001.engine.PostCommentEngine;
import com.kaixin001.engine.RecordEngine;
import com.kaixin001.engine.ReplyEngine;
import com.kaixin001.engine.RepostEngine;
import com.kaixin001.engine.StatusEngine;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.CircleDetailMessageUploadTask;
import com.kaixin001.item.MessageUploadTask;
import com.kaixin001.model.FaceModel;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.model.Setting;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FriendsUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXTextUtil;
import com.kaixin001.util.ScreenUtil;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.FaceKeyboardView;
import com.kaixin001.view.FaceKeyboardView.OnFaceSelectedListener;
import com.kaixin001.view.KXEditTextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class InputFragment extends BaseFragment
  implements View.OnClickListener, DialogInterface.OnClickListener, TextWatcher, AdapterView.OnItemClickListener
{
  public static final int CANCEL_MODE = -1;
  public static final int CIRCLE_DETAIL_REPLY_MODE = 10;
  public static final int CIRCLE_REPLY_MODE = 9;
  public static final int COMMENT_ERROR_CLOSE = -5;
  public static final int COMMENT_MODE = 2;
  public static final String MODE_KEY = "mode";
  public static final int MSG_REPLY_MODE = 5;
  public static final int MSG_SEND_MODE = 7;
  public static final int PHOTO_TITLE_MODE = 4;
  public static final int RECORD_MODE = 1;
  public static final int REPLY_COMMENT_MODE = 8;
  public static final int REPLY_MODE = 3;
  public static final int REPOST_TAG_MODE = 6;
  public static final String RESULT_STRING_KEY = "content";
  public static final int STATE_MODE = 0;
  public static final String TAG_AT = "@";
  public static final String TAG_NAME_LEFT = "(#";
  public static final String TAG_NAME_RIGHT = "#)";
  private static int maxLength = 280;
  protected final int ERR_WITH_MSG = 1;
  private RelativeLayout actionLayout;
  private ImageView atOne;
  private boolean bStop = false;
  private ImageView btnFace;
  private ImageView btnLeft;
  private ImageView btnRight;
  private ImageView cbkWhisper;
  private String cid;
  private KXEngine curEngine;
  private KXEditTextView evContent;
  private ArrayList<FriendsModel.Friend> friendslist = new ArrayList();
  private String fuid;
  private String gid;
  private String id;
  private String index;
  private int index1 = -1;
  private ListView lvReferList;
  private String mContent;
  private FaceKeyboardView mFaceKeyboardView;
  private HashMap<String, Bitmap> mNameBmpMap = new HashMap();
  private int mOrientation = 1;
  private boolean mQuietChecked = false;
  private String mid;
  private int mode = 0;
  private String mtype;
  private String ouid;
  private ReferListAdapter referAdapter;
  private String stid;
  private String strWhisper = "0";
  private SubmitTask submitTask;
  private String suid;
  private String surpid;
  private String thread_cid;
  private TextView tvProgress;
  private TextView tvTitle;
  private String type;
  private LinearLayout waitLayout;

  private void cancelCurrTask()
  {
    if (this.curEngine != null)
    {
      this.curEngine.cancel();
      this.curEngine = null;
    }
  }

  private void checkCustomCheckBox(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.strWhisper = "1";
      this.mQuietChecked = true;
      this.cbkWhisper.setBackgroundResource(2130838019);
      return;
    }
    this.strWhisper = "0";
    this.mQuietChecked = false;
    this.cbkWhisper.setBackgroundResource(2130838018);
  }

  private void getAllFriends()
  {
    try
    {
      this.friendslist.clear();
      ArrayList localArrayList = FriendsModel.getInstance().getFriends();
      if (localArrayList != null)
      {
        Iterator localIterator = localArrayList.iterator();
        while (true)
        {
          if (!localIterator.hasNext())
            return;
          FriendsModel.Friend localFriend = (FriendsModel.Friend)localIterator.next();
          this.friendslist.add(localFriend);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void hideCustomCheckBox(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.cbkWhisper.setVisibility(8);
      findViewById(2131362861).setVisibility(8);
      return;
    }
    this.cbkWhisper.setVisibility(0);
    findViewById(2131362861).setVisibility(0);
  }

  private void hideInputMethod()
  {
    View localView = getActivity().getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
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
          InputFragment.this.insertFaceIcon(paramString, paramBitmap);
        case -2:
        case -1:
        }
        int i;
        do
        {
          return;
          i = InputFragment.this.evContent.getSelectionStart();
        }
        while (i <= 0);
        String str1 = InputFragment.this.evContent.getText().toString();
        ArrayList localArrayList = FaceModel.getInstance().getAllFaceStringSortByLength();
        int j = 1;
        Iterator localIterator = localArrayList.iterator();
        if (!localIterator.hasNext());
        while (true)
        {
          InputFragment.this.evContent.getText().delete(i - j, i);
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

  private void inputCancel()
  {
    hideInputMethod();
    finish();
  }

  private void insertFriendIntoContent(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)));
    String str;
    Bitmap localBitmap;
    Editable localEditable;
    do
    {
      return;
      str = "@" + paramString1 + "(#" + paramString2 + "#)";
      localBitmap = (Bitmap)this.mNameBmpMap.get(str);
      if (localBitmap == null)
        localBitmap = ImageCache.createStringBitmap("@" + paramString2, this.evContent.getPaint());
      if (localBitmap != null)
        this.mNameBmpMap.put(str, localBitmap);
      localEditable = this.evContent.getText().replace(paramInt1, paramInt2, str + " ");
    }
    while ((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    if (paramInt1 + str.length() <= maxLength)
    {
      localEditable.setSpan(new ImageSpan(localBitmap), paramInt1, paramInt1 + str.length(), 33);
      return;
    }
    Toast.makeText(getActivity(), 2131427900, 0).show();
  }

  private void setCustomCheckBoxText(int paramInt)
  {
    ((TextView)findViewById(2131362861)).setText(paramInt);
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

  private void submit()
  {
    String str1 = String.valueOf(this.evContent.getText()).trim();
    int i = 2131427452;
    if (this.mode == 1)
    {
      i = 2131427454;
      if (!TextUtils.isEmpty(str1))
        break label194;
      DialogUtil.showKXAlertDialog(getActivity(), i, null);
    }
    label194: 
    do
    {
      return;
      if (this.mode == 2)
      {
        i = 2131427456;
        if (this.mQuietChecked)
        {
          this.strWhisper = "1";
          break;
        }
        this.strWhisper = "0";
        break;
      }
      if ((this.mode == 3) || (8 == this.mode))
      {
        i = 2131427455;
        break;
      }
      if (this.mode == 5)
      {
        i = 2131427455;
        break;
      }
      if (this.mode == 4)
      {
        inputFinish();
        return;
      }
      if (this.mode == 6)
      {
        i = 2131427457;
        break;
      }
      if (this.mode == 7)
      {
        i = 2131427455;
        break;
      }
      if ((this.mode != 9) && (this.mode != 10))
        break;
      i = 2131427455;
      break;
      if (this.mode != 6)
        continue;
      hideInputMethod();
    }
    while (!super.checkNetworkAndHint(true));
    this.btnRight.setEnabled(false);
    this.btnFace.setEnabled(false);
    this.evContent.setVisibility(8);
    this.actionLayout.setVisibility(8);
    this.waitLayout.setVisibility(0);
    this.bStop = false;
    if ((this.mode == 3) || (this.mode == 5))
      while (true)
      {
        MessageUploadTask localMessageUploadTask;
        try
        {
          localMessageUploadTask = new MessageUploadTask(getActivity().getApplicationContext(), 5);
          localMessageUploadTask.setContent(String.valueOf(this.evContent.getText()));
          localMessageUploadTask.setMainThreadFuid(this.fuid);
          if (this.mode == 5)
          {
            localMessageUploadTask.setThreadId(this.mid);
            localMessageUploadTask.setnMode(5);
            localMessageUploadTask.setTaskType(5);
            UploadTaskListEngine.getInstance().addUploadTask(localMessageUploadTask);
            inputFinish();
            return;
          }
        }
        catch (Exception localException1)
        {
          KXLog.e("InputActivity", localException1.toString());
          return;
        }
        localMessageUploadTask.setThreadId(this.thread_cid);
        localMessageUploadTask.setnMode(3);
        localMessageUploadTask.setTaskType(4);
      }
    if (this.mode == 10)
      try
      {
        CircleDetailMessageUploadTask localCircleDetailMessageUploadTask = new CircleDetailMessageUploadTask(getActivity().getApplicationContext());
        localCircleDetailMessageUploadTask.initCircleMessageDetailTask(KXTextUtil.tranformAtFriend(this.evContent.getText().toString()), this.gid, this.stid);
        localCircleDetailMessageUploadTask.setMode(10);
        UploadTaskListEngine.getInstance().addUploadTask(localCircleDetailMessageUploadTask);
        inputFinish();
        return;
      }
      catch (Exception localException2)
      {
        KXLog.e("InputActivity", localException2.toString());
        return;
      }
    this.submitTask = new SubmitTask(null);
    String str2 = KXTextUtil.tranformAtFriend(this.evContent.getText().toString());
    this.submitTask.execute(new String[] { str2 });
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void inputFinish()
  {
    hideInputMethod();
    Intent localIntent = new Intent();
    localIntent.putExtra("content", String.valueOf(this.evContent.getText()));
    if ((this.mode == 3) || (this.mode == 8))
    {
      localIntent.putExtra("thread_cid", this.thread_cid);
      localIntent.putExtra("index", this.index);
    }
    while (true)
    {
      setResult(-1, localIntent);
      finishFragment(this.mode);
      finish();
      return;
      if (this.mode == 2)
      {
        this.cid = PostCommentEngine.getInstance().getCommentCid();
        localIntent.putExtra("cid", this.cid);
        if (this.mQuietChecked)
        {
          localIntent.putExtra("mtype", "1");
          continue;
        }
        localIntent.putExtra("mtype", "0");
        continue;
      }
      if (this.mode != 9)
        continue;
      localIntent.putExtra("stid", CircleReplyNewsEngine.getInstance().stid);
      localIntent.putExtra("rtid", CircleReplyNewsEngine.getInstance().rtid);
      localIntent.putExtra("content", CircleReplyNewsEngine.getInstance().content);
      localIntent.putExtra("ctime", CircleReplyNewsEngine.getInstance().ctime);
    }
  }

  protected void insertFaceIcon(int paramInt)
  {
    ArrayList localArrayList1 = FaceModel.getInstance().getStateFaceStrings();
    ArrayList localArrayList2 = FaceModel.getInstance().getStateFaceIcons();
    String str;
    Editable localEditable;
    int i;
    int j;
    if (localArrayList1 != null)
    {
      str = (String)localArrayList1.get(paramInt);
      localEditable = this.evContent.getText();
      i = Selection.getSelectionStart(localEditable);
      j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      int k = j - i;
      if (localEditable.length() <= maxLength - str.length() - k)
        break label125;
      Toast.makeText(getActivity(), 2131427899, 0).show();
    }
    label125: 
    do
    {
      return;
      localEditable.replace(i, j, str);
    }
    while ((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    localEditable.setSpan(new ImageSpan((Bitmap)localArrayList2.get(paramInt)), i, i + str.length(), 33);
  }

  protected void insertFaceIcon(String paramString, Bitmap paramBitmap)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramBitmap == null));
    Editable localEditable;
    int i;
    do
    {
      return;
      localEditable = this.evContent.getText();
      i = Selection.getSelectionStart(localEditable);
      int j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      if (localEditable.length() > maxLength - paramString.length())
      {
        Toast.makeText(getActivity(), 2131427899, 0).show();
        return;
      }
      localEditable.replace(i, j, paramString.subSequence(0, paramString.length()));
    }
    while ((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    localEditable.setSpan(new ImageSpan(paramBitmap), i, i + paramString.length(), 33);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt2 == -1) && (paramInt1 == 4))
    {
      String str1 = paramIntent.getStringExtra("fuid");
      String str2 = paramIntent.getStringExtra("fname");
      int i = this.evContent.getSelectionStart();
      insertFriendIntoContent(i, i, str1, str2);
    }
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    if (paramInt == -1)
      inputCancel();
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.btnRight))
    {
      hideInputMethod();
      submit();
    }
    do
    {
      return;
      if (paramView.equals(this.btnLeft))
      {
        String str = String.valueOf(this.evContent.getText()).trim();
        if (this.curEngine != null)
        {
          this.bStop = true;
          cancelCurrTask();
          return;
        }
        if (TextUtils.isEmpty(str))
        {
          inputCancel();
          return;
        }
        DialogUtil.showKXAlertDialog(getActivity(), 2131427451, 2131427382, 2131427383, this, this);
        return;
      }
      if (paramView.equals(this.btnFace))
      {
        showFaceListDialog(true);
        return;
      }
      if (!paramView.equals(this.atOne))
        continue;
      if (this.evContent.getText().length() < -8 + maxLength)
      {
        Intent localIntent = new Intent(getActivity(), FriendsFragment.class);
        Bundle localBundle = new Bundle();
        localBundle.putInt("MODE", 2);
        localIntent.putExtras(localBundle);
        AnimationUtil.startFragmentForResult(this, localIntent, 4, 1);
        return;
      }
      Toast.makeText(getActivity(), 2131427900, 0).show();
      return;
    }
    while (!paramView.equals(this.cbkWhisper));
    if (this.mQuietChecked)
    {
      checkCustomCheckBox(false);
      return;
    }
    checkCustomCheckBox(true);
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    int i;
    int j;
    String str1;
    SpannableString localSpannableString;
    Iterator localIterator;
    if (this.mOrientation != paramConfiguration.orientation)
    {
      this.mOrientation = ScreenUtil.getOrientation(getActivity());
      i = this.evContent.getSelectionStart();
      j = this.evContent.getSelectionEnd();
      str1 = this.evContent.getText().toString();
      if (((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape())) || (TextUtils.isEmpty(str1)))
        break label255;
      localSpannableString = StringUtil.convertTextToMessageFace(str1);
      if (localSpannableString != null)
      {
        localIterator = this.mNameBmpMap.keySet().iterator();
        if (localIterator.hasNext())
          break label137;
      }
      this.evContent.setText(localSpannableString);
    }
    while (true)
    {
      this.evContent.setSelection(i, j);
      super.onConfigurationChanged(paramConfiguration);
      return;
      label137: String str2 = (String)localIterator.next();
      Bitmap localBitmap1 = (Bitmap)this.mNameBmpMap.get(str2);
      int k = str1.indexOf(str2);
      if (k < 0)
      {
        Bitmap localBitmap2 = (Bitmap)this.mNameBmpMap.remove(str2);
        if (localBitmap2 == null)
          break;
        localBitmap2.recycle();
        break;
      }
      do
      {
        localSpannableString.setSpan(new ImageSpan(localBitmap1), k, k + str2.length(), 33);
        k = str1.indexOf(str2, k + str2.length());
      }
      while (k >= 0);
      break;
      label255: this.evContent.setText(str1);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return super.onCreateDialog(paramInt);
    case 1:
    }
    return DialogUtil.showMsgDlgStd(getActivity(), 2131427329, RepostEngine.getInstance().getLastError(), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        InputFragment.this.finish();
      }
    });
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903176, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.submitTask != null) && (this.submitTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.submitTask.cancel(true);
      cancelCurrTask();
    }
    Iterator localIterator;
    if (this.mNameBmpMap != null)
      localIterator = this.mNameBmpMap.values().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        this.mNameBmpMap.clear();
        this.mNameBmpMap = null;
        super.onDestroy();
        return;
      }
      ((Bitmap)localIterator.next()).recycle();
    }
  }

  public void onDestroyView()
  {
    hideKeyboard();
    super.onDestroyView();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    FriendsModel.Friend localFriend = (FriendsModel.Friend)this.friendslist.get(paramInt);
    int i = this.index1;
    this.index1 = -1;
    insertFriendIntoContent(i, this.evContent.getSelectionStart(), localFriend.getFuid(), localFriend.getFname());
    this.lvReferList.setVisibility(8);
  }

  public void onPause()
  {
    this.index1 = -1;
    super.onPause();
  }

  public void onResume()
  {
    setCustomCheckBoxText(2131427460);
    super.onResume();
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    if (isNeedReturn());
    do
    {
      return;
      String str1 = paramCharSequence.toString();
      this.btnRight.setEnabled(true);
      if (KXTextUtil.isCurrentAtSymbol(str1, paramInt1, paramInt2, paramInt3) >= 0)
      {
        this.index1 = KXTextUtil.isCurrentAtSymbol(str1, paramInt1, paramInt2, paramInt3);
        this.friendslist.clear();
        FriendsUtil.getAllFriends(this.friendslist);
        this.referAdapter.notifyDataSetChanged();
        this.lvReferList.setVisibility(0);
      }
      if (KXTextUtil.isValidPositionAtSymbol(str1, this.index1) < 0)
      {
        this.index1 = -1;
        this.lvReferList.setVisibility(8);
      }
      String str2 = KXTextUtil.isValidNameInputing(str1, paramInt1, paramInt2, paramInt3, this.index1);
      if (str2 == null)
        continue;
      this.friendslist.clear();
      FriendsUtil.searchListData(FriendsModel.getInstance().getFriends(), str2, this.friendslist);
      this.referAdapter.notifyDataSetChanged();
      this.lvReferList.setVisibility(0);
    }
    while (this.friendslist.size() != 0);
    this.lvReferList.setVisibility(8);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initFaceKeyBoardLayout();
    this.tvProgress = ((TextView)findViewById(2131362856));
    this.waitLayout = ((LinearLayout)findViewById(2131362854));
    this.tvTitle = ((TextView)findViewById(2131362920));
    this.tvTitle.setVisibility(0);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    this.btnLeft = ((ImageView)findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    this.btnRight = ((ImageView)findViewById(2131362928));
    this.btnRight.setOnClickListener(this);
    this.btnRight.setImageResource(2130839272);
    this.evContent = ((KXEditTextView)findViewById(2131362857));
    InputFilter[] arrayOfInputFilter1 = new InputFilter[1];
    arrayOfInputFilter1[0] = new InputFilter.LengthFilter(maxLength);
    this.evContent.setFilters(arrayOfInputFilter1);
    this.actionLayout = ((RelativeLayout)findViewById(2131362859));
    this.btnFace = ((ImageView)findViewById(2131362014));
    this.btnFace.setOnClickListener(this);
    this.atOne = ((ImageView)findViewById(2131362015));
    this.atOne.setOnClickListener(this);
    this.cbkWhisper = ((ImageView)findViewById(2131362860));
    this.cbkWhisper.setOnClickListener(this);
    FaceModel.getInstance();
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mode = localBundle.getInt("mode", 0);
      this.mContent = localBundle.getString("content");
    }
    if (this.mode == 0)
    {
      this.tvTitle.setText(2131427430);
      this.tvProgress.setText(2131427435);
      hideCustomCheckBox(true);
      maxLength = 140;
      arrayOfInputFilter1[0] = new InputFilter.LengthFilter(maxLength);
      this.evContent.setFilters(arrayOfInputFilter1);
    }
    while (true)
    {
      if (!TextUtils.isEmpty(this.mContent))
        this.evContent.setText(this.mContent);
      this.lvReferList = ((ListView)findViewById(2131362858));
      this.referAdapter = new ReferListAdapter(getActivity().getApplicationContext());
      this.lvReferList.setAdapter(this.referAdapter);
      this.lvReferList.setOnItemClickListener(this);
      this.evContent.requestFocus();
      if ((this.mode != 5) && (this.mode != 7) && (this.mode != 9) && (this.mode != 10))
        this.evContent.addTextChangedListener(this);
      this.mOrientation = ScreenUtil.getOrientation(getActivity());
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          if (InputFragment.this.isNeedReturn())
            return;
          InputFragment.this.showKeyboard();
        }
      }
      , 500L);
      return;
      if (this.mode == 1)
      {
        this.tvTitle.setText(2131427428);
        this.tvProgress.setText(2131427434);
        this.actionLayout.setVisibility(8);
        hideCustomCheckBox(true);
        maxLength = 140;
        arrayOfInputFilter1[0] = new InputFilter.LengthFilter(maxLength);
        this.evContent.setFilters(arrayOfInputFilter1);
        continue;
      }
      if (this.mode == 2)
      {
        this.type = localBundle.getString("type");
        this.id = localBundle.getString("id");
        this.ouid = localBundle.getString("ouid");
        this.tvTitle.setText(2131427427);
        this.tvProgress.setText(2131427436);
        maxLength = 500;
        arrayOfInputFilter1[0] = new InputFilter.LengthFilter(maxLength);
        this.evContent.setFilters(arrayOfInputFilter1);
        continue;
      }
      if ((this.mode == 3) || (8 == this.mode))
      {
        this.thread_cid = localBundle.getString("thread_cid");
        this.index = localBundle.getString("index");
        this.fuid = localBundle.getString("fuid");
        this.mtype = localBundle.getString("mtype");
        if (this.mtype != null)
          if (this.mtype.compareTo("1") == 0)
          {
            hideCustomCheckBox(false);
            checkCustomCheckBox(true);
            this.cbkWhisper.setEnabled(false);
          }
        while (true)
        {
          this.tvTitle.setText(2131427562);
          this.tvProgress.setText(2131427437);
          maxLength = 500;
          arrayOfInputFilter1[0] = new InputFilter.LengthFilter(maxLength);
          this.evContent.setFilters(arrayOfInputFilter1);
          break;
          hideCustomCheckBox(true);
          continue;
          hideCustomCheckBox(true);
        }
      }
      if ((this.mode == 9) || (this.mode == 10))
      {
        this.gid = localBundle.getString("gid");
        this.stid = localBundle.getString("stid");
        this.tvTitle.setText(2131427562);
        this.tvProgress.setText(2131427437);
        maxLength = 140;
        arrayOfInputFilter1[0] = new InputFilter.LengthFilter(maxLength);
        this.evContent.setFilters(arrayOfInputFilter1);
        this.atOne.setVisibility(8);
        hideCustomCheckBox(true);
        continue;
      }
      if (this.mode == 4)
      {
        this.tvTitle.setText(2131427431);
        this.actionLayout.setVisibility(8);
        hideCustomCheckBox(true);
        continue;
      }
      if (this.mode == 5)
      {
        this.mid = localBundle.getString("mid");
        this.tvTitle.setText(2131427562);
        this.tvProgress.setText(2131427437);
        this.atOne.setVisibility(8);
        hideCustomCheckBox(true);
        maxLength = 500;
        arrayOfInputFilter1[0] = new InputFilter.LengthFilter(maxLength);
        this.evContent.setFilters(arrayOfInputFilter1);
        continue;
      }
      if (this.mode == 6)
      {
        this.surpid = localBundle.getString("surpid");
        this.suid = localBundle.getString("suid");
        this.tvTitle.setText(2131427432);
        this.tvProgress.setText(2131427436);
        this.evContent.setSingleLine(true);
        this.actionLayout.setVisibility(8);
        hideCustomCheckBox(true);
        maxLength = 16;
        InputFilter[] arrayOfInputFilter2 = new InputFilter[1];
        arrayOfInputFilter2[0] = new InputFilter.LengthFilter(maxLength);
        this.evContent.setFilters(arrayOfInputFilter2);
        continue;
      }
      if (this.mode != 7)
        continue;
      this.fuid = localBundle.getString("fuid");
      this.tvTitle.setText(2131427562);
      this.tvProgress.setText(2131427437);
      hideCustomCheckBox(true);
      this.atOne.setVisibility(8);
      maxLength = 500;
      arrayOfInputFilter1[0] = new InputFilter.LengthFilter(maxLength);
      this.evContent.setFilters(arrayOfInputFilter1);
    }
  }

  public void requestFinish()
  {
    String str = String.valueOf(this.evContent.getText()).trim();
    if (this.curEngine != null)
    {
      this.bStop = true;
      cancelCurrTask();
      return;
    }
    if (TextUtils.isEmpty(str))
    {
      inputCancel();
      return;
    }
    DialogUtil.showKXAlertDialog(getActivity(), 2131427451, 2131427382, 2131427383, this, this);
  }

  public void showFaceListDialog(boolean paramBoolean)
  {
    if (this.mFaceKeyboardView.getVisibility() == 0)
    {
      this.btnFace.setImageResource(2130838130);
      showFaceKeyboardView(false);
      ActivityUtil.showInputMethod(getActivity());
      return;
    }
    this.btnFace.setImageResource(2130838135);
    showFaceKeyboardView(true);
  }

  private class ReferListAdapter extends BaseAdapter
  {
    private LayoutInflater inflater;

    ReferListAdapter(Context arg2)
    {
      Context localContext;
      this.inflater = LayoutInflater.from(localContext);
    }

    public int getCount()
    {
      return InputFragment.this.friendslist.size();
    }

    public Object getItem(int paramInt)
    {
      return InputFragment.this.friendslist.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
      {
        paramView = this.inflater.inflate(2130903202, null);
        ViewHolder localViewHolder = new ViewHolder(null);
        localViewHolder.realNameText = ((TextView)paramView.findViewById(2131362966));
        paramView.setTag(localViewHolder);
      }
      FriendsModel.Friend localFriend = (FriendsModel.Friend)InputFragment.this.friendslist.get(paramInt);
      ((ViewHolder)paramView.getTag()).realNameText.setText(localFriend.getFname());
      return paramView;
    }

    private class ViewHolder
    {
      public TextView realNameText;

      private ViewHolder()
      {
      }
    }
  }

  private class SubmitTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    static
    {
      if (!InputFragment.class.desiredAssertionStatus());
      for (boolean bool = true; ; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }

    private SubmitTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        if (InputFragment.this.mode == 0)
        {
          InputFragment.this.curEngine = StatusEngine.getInstance();
          return Integer.valueOf(RecordEngine.getInstance().postRecordRequest(InputFragment.this.getActivity().getApplicationContext(), "0", paramArrayOfString[0], null, null, null, null, "1", null));
        }
        if (InputFragment.this.mode == 8)
          return Integer.valueOf(ReplyEngine.getInstance().postReply(InputFragment.this.getActivity().getApplicationContext(), InputFragment.this.thread_cid, InputFragment.this.fuid, paramArrayOfString[0], null));
        if (InputFragment.this.mode == 1)
        {
          InputFragment.this.curEngine = RecordEngine.getInstance();
          return Integer.valueOf(RecordEngine.getInstance().postRecord(InputFragment.this.getActivity().getApplicationContext(), "1", paramArrayOfString[0]));
        }
        if (InputFragment.this.mode == 2)
        {
          InputFragment.this.curEngine = PostCommentEngine.getInstance();
          return Integer.valueOf(PostCommentEngine.getInstance().postComment(InputFragment.this.getActivity().getApplicationContext(), InputFragment.this.type, InputFragment.this.id, InputFragment.this.ouid, InputFragment.this.strWhisper, paramArrayOfString[0]));
        }
        if (InputFragment.this.mode == 6)
        {
          InputFragment.this.curEngine = RepostEngine.getInstance();
          if (RepostEngine.getInstance().doSubmitRepostTag(InputFragment.this.getActivity().getApplicationContext(), "", paramArrayOfString[0], InputFragment.this.surpid, InputFragment.this.suid))
            return Integer.valueOf(1);
        }
        else if (InputFragment.this.mode == 7)
        {
          InputFragment.this.curEngine = RepostEngine.getInstance();
          ArrayList localArrayList = new ArrayList();
          localArrayList.add(0, InputFragment.this.fuid);
          if (MessageEngine.getInstance().doPostMessage(InputFragment.this.getActivity().getApplicationContext(), localArrayList, paramArrayOfString[0]))
            return Integer.valueOf(1);
        }
        else
        {
          if (InputFragment.this.mode == 9)
          {
            InputFragment.this.curEngine = CircleReplyNewsEngine.getInstance();
            return Integer.valueOf(CircleReplyNewsEngine.getInstance().doPostCircleNewsReply(InputFragment.this.getActivity().getApplicationContext(), InputFragment.this.gid, InputFragment.this.stid, paramArrayOfString[0]));
          }
          if (!$assertionsDisabled)
            throw new AssertionError();
        }
      }
      catch (Exception localException)
      {
        KXLog.e("InputActivity", "doInBackground", localException);
      }
      return Integer.valueOf(0);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      InputFragment.this.curEngine = null;
      int k;
      while (true)
      {
        try
        {
          int i = paramInteger.intValue();
          j = 2131427448;
          k = 2131427441;
          if (InputFragment.this.mode != 1)
            continue;
          j = 2131427446;
          k = 2131427438;
          if (i != 1)
            continue;
          InputFragment.this.inputFinish();
          if (InputFragment.this.mode != 1)
            break label345;
          Toast.makeText(InputFragment.this.getActivity(), j, 0).show();
          return;
          if (InputFragment.this.mode != 2)
            continue;
          j = 2131427450;
          if (i != -5)
            break label349;
          k = 2131427471;
          break label346;
          if ((InputFragment.this.mode == 3) || (InputFragment.this.mode == 5))
            break label357;
          if (InputFragment.this.mode != 8)
            continue;
          j = 2131427449;
          if (i != -5)
            break label372;
          k = 2131427471;
          break;
          if (InputFragment.this.mode != 6)
            continue;
          k = 2131427444;
          continue;
          if ((InputFragment.this.mode != 9) || (i != -3002))
            continue;
          k = 2131427445;
          continue;
          InputFragment.this.btnRight.setEnabled(true);
          InputFragment.this.btnFace.setEnabled(true);
          InputFragment.this.evContent.setVisibility(0);
          if (InputFragment.this.mode == 6)
            continue;
          InputFragment.this.actionLayout.setVisibility(0);
          InputFragment.this.waitLayout.setVisibility(8);
          if (InputFragment.this.bStop)
            break label345;
          if ((InputFragment.this.mode == 6) && (!TextUtils.isEmpty(RepostEngine.getInstance().getLastError())))
          {
            InputFragment.this.showDialog(1);
            return;
          }
        }
        catch (Resources.NotFoundException localNotFoundException)
        {
          KXLog.e("InputActivity", "onPostExecute", localNotFoundException);
          return;
        }
        Toast.makeText(InputFragment.this.getActivity(), k, 0).show();
        label345: return;
        while (true)
        {
          label346: break;
          label349: k = 2131427443;
        }
        label357: int j = 2131427449;
        k = 2131427442;
      }
      while (true)
      {
        break;
        label372: k = 2131427442;
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.InputFragment
 * JD-Core Version:    0.6.0
 */