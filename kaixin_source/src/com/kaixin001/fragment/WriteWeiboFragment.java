package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.adapter.AtFriendsListAdapter;
import com.kaixin001.adapter.CheckBoxListAdapter;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.AlbumInfo;
import com.kaixin001.item.RecordUploadTask;
import com.kaixin001.lbs.RefreshLandmarkProxy;
import com.kaixin001.lbs.RefreshLandmarkProxy.IRefreshLandmarkListCallback;
import com.kaixin001.lbs.RefreshLocationProxy;
import com.kaixin001.model.EditPictureModel;
import com.kaixin001.model.FaceModel;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.model.WriteWeiboModel;
import com.kaixin001.service.SyncShareService;
import com.kaixin001.task.GetFriendsListTask;
import com.kaixin001.task.LoadPhotoTask;
import com.kaixin001.task.LoadPhotoTask.DoPostExecute;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FancyDialogAdapter;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.FriendsUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXTextUtil;
import com.kaixin001.util.KXUBLog;
import com.kaixin001.util.ScreenUtil;
import com.kaixin001.util.SocialShareManager;
import com.kaixin001.util.SocialShareManager.OnRecieveListener;
import com.kaixin001.util.SocialShareManager.ReceiveType;
import com.kaixin001.util.StringUtil;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.FaceKeyboardView;
import com.kaixin001.view.FaceKeyboardView.OnFaceSelectedListener;
import com.kaixin001.view.KXFrameImageView;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;

public class WriteWeiboFragment extends BaseFragment
  implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener, SocialShareManager.OnRecieveListener
{
  public static final String ACTION_VIEW_WEIBOPHONE = "com.kaixin001.VIEW_WEIBOPHONE";
  private static final int CAN_ADD_LOCATION = 0;
  private static final int CAN_REMOVE_LOCATION = 1;
  private static final String DATA_ADD_TIME = "data_add_time";
  public static final int FRIEND_SELECTED = 4;
  private static final String FROM_WEBPAGE = "from_webpage";
  protected static final int GPS_SETTING_SCREEN = 107;
  public static final String MODE_KEY = "mode";
  public static final int NORMAL_MODE = 10;
  private static final String PREFERENCE_LATEST_PIC = "preference_latest_pic";
  private static final String PREFERENCE_USER_WEIBO = "weibo_preference_";
  public static final String RESULT_STRING_KEY = "content";
  private static final int STATE_FINDING_LOCATION = 0;
  private static final int STATE_HIDE_LOCATION_DESC = -1;
  public static final int STATE_MODE = 16;
  private static final int STATE_NO_LOCATION = 1;
  private static final int STATE_SET_LOCATION = 2;
  private static final String TAG = "WriteRecordActivity";
  private static final String TAG_AT = "@";
  private static final String TEXT_WEIBO = "weibo_text";
  private static final String TIME_WEIBO = "weibo_time";
  public static final int WEIBO_MAXNUM = 140;
  private ImageView btnLeft;
  private ImageView btnRight;
  private KXFrameImageView contentImg = null;
  private View contentImgDelete = null;
  private View contentImgLayout;
  private EditText editWeibo;
  private FancyDialogAdapter fancyDialogAdapter = new FancyDialogAdapter();
  private ArrayList<FriendsModel.Friend> friendslist = new ArrayList();
  private boolean fromHoroscopeFragment = false;
  private int index = -1;
  private ImageView insertPictureButton = null;
  private boolean isShareToQzone;
  private boolean isShareToWeibo;
  private ImageView ivShareQzone;
  private ImageView ivShareWeibo;
  private View locationView = null;
  private String[] locks;
  private ListView lvReferList;
  private ImageView mAddLocationButton = null;
  PrivaligeCheckBoxListAdapter mAlbumListAdapter = new PrivaligeCheckBoxListAdapter(getActivity());
  private String mDraftFileName = "diray_weibo.kx";
  private FaceKeyboardView mFaceKeyboardView;
  private FaceModel mFaceModel;
  private int mInsertedPictureIdBase = 1;
  private boolean mIsOpenLocation = true;
  private View mLandmarkLayout = null;
  private String mLatestPicPath = null;
  private LoadPhotoTask mLoadPhotoTask = null;
  public JSONArray mLocationBuildingList = null;
  private View mLocationDelete = null;
  private Button mLocationDesc = null;
  private ImageView mLocationIcon = null;
  private ProgressBar mLocationProBar = null;
  private TextView mLocationWaiting = null;
  private HashMap<String, Bitmap> mNameBmpMap = new HashMap();
  boolean mNeedLocation = Setting.getInstance().gpsEnabled();
  private int mOrientation = 1;
  private ProgressDialog mProgressDialog = null;
  private View mSendArrowLayout = null;
  private View mUpgradeImgLayout = null;
  private String mUploadPhotoFrom = "camera";
  private Button mWriteWeiboLock = null;
  private WriteWeiboModel mWriteWeiboModel = WriteWeiboModel.getInstance();
  private int mode = 0;
  private String msTempFilePath = null;
  private String privacy = "0";
  private AtFriendsListAdapter referAdapter;
  private RefreshLandmarkProxy refreshLandmarkProxy;
  private String seletedLandmark;
  private SocialShareManager shareManager;
  private TextView tvTitle;
  private LinearLayout waitLayout;

  private void LoadInsertedPicture(String paramString)
  {
    if (!new File(paramString).exists())
    {
      Toast.makeText(getActivity(), 2131427841, 0).show();
      return;
    }
    Bundle localBundle = new Bundle();
    localBundle.putString("filePathName", paramString);
    localBundle.putString("fileFrom", this.mUploadPhotoFrom);
    IntentUtil.launchEditPhotoForResult(getActivity(), this, 105, localBundle);
  }

  private void addPic(String paramString)
  {
    this.msTempFilePath = paramString;
    this.mWriteWeiboModel.addPic(paramString);
    if (this.mWriteWeiboModel.getPicPath().size() >= 1)
    {
      Bitmap localBitmap = FileUtil.getBmpFromFile(getActivity(), paramString);
      this.contentImgLayout.setVisibility(0);
      if (localBitmap != null)
      {
        localBitmap.getWidth();
        localBitmap.getHeight();
        this.contentImg.setImageBitmap(localBitmap);
      }
    }
    this.mInsertedPictureIdBase = (1 + this.mInsertedPictureIdBase);
  }

  private void cancleLocationSearch()
  {
    setAddLocationButtonState(0);
    setLandmarkDescription(-1, null);
    this.seletedLandmark = "";
  }

  private void changeContentForOrientation()
  {
    int i = this.editWeibo.getSelectionStart();
    int j = this.editWeibo.getSelectionEnd();
    String str1 = this.editWeibo.getText().toString();
    SpannableString localSpannableString;
    ArrayList localArrayList;
    int k;
    Matcher localMatcher;
    if (((1 == this.mOrientation) || (Setting.getInstance().isShowFaceIconInLandScape())) && (!TextUtils.isEmpty(str1)))
    {
      localSpannableString = StringUtil.convertTextToStateFace(str1);
      if (localSpannableString != null)
      {
        localArrayList = this.mFaceModel.getFaceCodes(1);
        k = 0;
        if (k >= localSpannableString.length())
        {
          localMatcher = Pattern.compile("@([0-9]+)\\(\\#(\\S+?)\\#\\)").matcher(localSpannableString);
          label100: if (localMatcher.find())
            break label204;
        }
      }
      else
      {
        this.editWeibo.setText(localSpannableString);
      }
    }
    while (true)
    {
      this.editWeibo.setSelection(i, j);
      return;
      String str2 = localSpannableString.charAt(k);
      if (localArrayList.contains(str2))
      {
        Bitmap localBitmap1 = this.mFaceModel.getFaceIcon(str2);
        ImageSpan localImageSpan1 = new ImageSpan(null, localBitmap1, 1);
        localSpannableString.setSpan(localImageSpan1, k, k + 1, 33);
      }
      k++;
      break;
      label204: String str3 = localMatcher.group();
      String str4 = localMatcher.group(2);
      int m = localMatcher.start();
      int n = localMatcher.end();
      Bitmap localBitmap2 = (Bitmap)this.mNameBmpMap.get(str3);
      if (localBitmap2 == null)
      {
        localBitmap2 = ImageCache.createStringBitmap("@" + str4, this.editWeibo.getPaint());
        this.mNameBmpMap.put(str3, localBitmap2);
      }
      ImageSpan localImageSpan2 = new ImageSpan(null, localBitmap2, 1);
      localSpannableString.setSpan(localImageSpan2, m, n, 33);
      break label100;
      this.editWeibo.setText(str1);
    }
  }

  private void clearSavedWeibo()
  {
    getActivity().getApplicationContext().getSharedPreferences("weibo_preference_" + User.getInstance().getUID(), 0).edit().clear().commit();
    this.mLatestPicPath = null;
  }

  private void deleteDiaryDraft()
  {
    deleteDiarystrContent();
    deleteRecordImgContent();
  }

  private void deleteDiarystrContent()
  {
    FileUtil.deleteCacheFile(FileUtil.getCacheDir(getActivity().getCacheDir().getAbsolutePath(), User.getInstance().getUID()), this.mDraftFileName);
  }

  private void deleteRecordImgContent()
  {
    this.contentImg.setBackgroundDrawable(null);
    this.contentImgLayout.setVisibility(8);
    this.mWriteWeiboModel.getPicPath().clear();
    this.msTempFilePath = null;
  }

  private void exitOrBack()
  {
    if (String.valueOf(this.editWeibo.getText()).trim().length() != 0)
    {
      showSaveDraftOptions();
      return;
    }
    deleteDiaryDraft();
    hideInputMethod();
    finish();
  }

  private String getLatestNewPicPath()
  {
    long l1 = System.currentTimeMillis() / 1000L - 600L;
    SharedPreferences localSharedPreferences = getActivity().getSharedPreferences("preference_latest_pic", 0);
    long l2 = localSharedPreferences.getLong("data_add_time", 0L);
    if ((l2 == 0L) || (l2 < l1))
      l2 = l1;
    Uri localUri;
    if (Environment.getExternalStorageState().equals("mounted"))
      localUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    while (true)
    {
      String[] arrayOfString1 = { "_data", "title", "_display_name", "date_added" };
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = l2;
      Cursor localCursor = null;
      try
      {
        localCursor = getActivity().getContentResolver().query(localUri, arrayOfString1, "date_added > ?", arrayOfString2, "date_added DESC");
        boolean bool1 = localCursor.moveToFirst();
        String str1 = null;
        if (bool1)
        {
          str1 = localCursor.getString(localCursor.getColumnIndex("_data"));
          long l3 = localCursor.getLong(localCursor.getColumnIndex("date_added"));
          localSharedPreferences.edit().putLong("data_add_time", l3).commit();
        }
        if (localCursor != null)
          localCursor.close();
        String str2;
        if ((str1 == null) || (FileUtil.skipPicture(str1)))
        {
          boolean bool2 = FileUtil.isScreenShot(getActivity(), str1);
          str2 = null;
          if (!bool2);
        }
        else
        {
          str2 = str1;
        }
        return str2;
        localUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
        continue;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
    }
    throw localObject;
  }

  private String getSavedWeibo()
  {
    String str = "";
    SharedPreferences localSharedPreferences = getActivity().getApplicationContext().getSharedPreferences("weibo_preference_" + User.getInstance().getUID(), 0);
    if (localSharedPreferences.contains("weibo_text"))
    {
      long l = localSharedPreferences.getLong("weibo_time", 0L);
      if ((float)((System.currentTimeMillis() - l) / 86400000L) <= 1.0D)
        str = localSharedPreferences.getString("weibo_text", "");
    }
    else
    {
      return str;
    }
    localSharedPreferences.edit().clear().commit();
    return str;
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

  private void hideInputMethod()
  {
    View localView = getActivity().getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
  }

  private void initMainView()
  {
    this.locationView = findViewById(2131364089);
    this.editWeibo = ((EditText)findViewById(2131363991));
    this.lvReferList = ((ListView)findViewById(2131364095));
    this.waitLayout = ((LinearLayout)findViewById(2131362356));
    this.contentImg = ((KXFrameImageView)findViewById(2131364093));
    this.contentImg.setFrameNinePatchResId(2130838245);
    this.contentImgLayout = findViewById(2131364092);
    if (this.mWriteWeiboModel.getPicPath().size() >= 1)
    {
      Bitmap localBitmap = FileUtil.getBmpFromFile(getActivity(), this.mWriteWeiboModel.getLastPicPath());
      this.contentImgLayout.setVisibility(0);
      this.contentImg.setImageBitmap(localBitmap);
    }
    this.contentImg.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(WriteWeiboFragment.this.getActivity(), PreviewUploadPhotoFragment.class);
        Bundle localBundle = new Bundle();
        localBundle.putString("imgFilePath", WriteWeiboFragment.this.mWriteWeiboModel.getLastPicPath());
        localIntent.putExtras(localBundle);
        WriteWeiboFragment.this.startActivityForResult(localIntent, 0);
      }
    });
    this.contentImgDelete = findViewById(2131364094);
    this.contentImgDelete.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        WriteWeiboFragment.this.deleteRecordImgContent();
      }
    });
    getActivity().getIntent();
    Bundle localBundle = getArguments();
    if (localBundle == null)
      this.editWeibo.setText(getSavedWeibo());
    while (true)
    {
      this.editWeibo.requestFocus();
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          if (WriteWeiboFragment.this.isNeedReturn())
            return;
          WriteWeiboFragment.this.showKeyboard();
        }
      }
      , 500L);
      this.editWeibo.addTextChangedListener(this);
      this.editWeibo.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          WriteWeiboFragment.this.mFaceKeyboardView.setVisibility(8);
        }
      });
      changeContentForOrientation();
      this.mFaceKeyboardView = ((FaceKeyboardView)findViewById(2131361976));
      this.mFaceKeyboardView.setOnFaceSelectedListener(new FaceKeyboardView.OnFaceSelectedListener()
      {
        public void FaceSelected(int paramInt, String paramString, Bitmap paramBitmap)
        {
          switch (paramInt)
          {
          default:
            WriteWeiboFragment.this.insertFaceIcon(paramString, paramBitmap);
          case -2:
          case -1:
          }
          EditText localEditText;
          int i;
          do
          {
            return;
            localEditText = (EditText)WriteWeiboFragment.this.findViewById(2131363991);
            i = localEditText.getSelectionStart();
          }
          while (i <= 0);
          String str1 = localEditText.getText().toString();
          ArrayList localArrayList = FaceModel.getInstance().getAllFaceStringSortByLength();
          int j = 1;
          Iterator localIterator = localArrayList.iterator();
          if (!localIterator.hasNext());
          while (true)
          {
            localEditText.getText().delete(i - j, i);
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
      return;
      if (this.fromHoroscopeFragment)
      {
        String str4 = localBundle.getString("yunShare");
        if (TextUtils.isEmpty(str4))
          continue;
        this.editWeibo.setText(str4);
        continue;
      }
      String str1 = localBundle.getString("content");
      this.mode = localBundle.getInt("mode", 0);
      if (this.mode == 16)
      {
        this.tvTitle.setText(2131427430);
        this.editWeibo.setText(loadDraft());
        continue;
      }
      if (str1 != null)
      {
        this.editWeibo.setText(str1);
        continue;
      }
      String str2 = localBundle.getString("type");
      if (str2 == null)
        str2 = "";
      String str3;
      if (!str2.contains("text"))
      {
        boolean bool = str2.contains("plain");
        str3 = null;
        if (!bool);
      }
      else
      {
        str3 = localBundle.getString("android.intent.extra.TEXT");
      }
      if (TextUtils.isEmpty(str3))
      {
        Toast.makeText(getActivity(), getResources().getString(2131427423), 0).show();
        finish();
        continue;
      }
      if ((str3 != null) && (str3.length() > 140))
        str3 = str3.substring(0, 137) + "...";
      this.editWeibo.setText(str3);
      this.editWeibo.setSelection(str3.length());
    }
  }

  private void initPromptView()
  {
    this.mUpgradeImgLayout = findViewById(2131364096);
    this.mSendArrowLayout = findViewById(2131364099);
    ImageView localImageView = (ImageView)findViewById(2131364098);
    if ((this.mode == 16) || (this.fromHoroscopeFragment))
      this.mUpgradeImgLayout.setVisibility(8);
    this.mLatestPicPath = getLatestNewPicPath();
    if ((this.mLatestPicPath != null) && (this.mWriteWeiboModel.getPicPath().size() < 1))
    {
      this.mUpgradeImgLayout.setVisibility(0);
      this.mSendArrowLayout.setVisibility(0);
      this.insertPictureButton = ((ImageView)findViewById(2131364081));
      this.insertPictureButton.setImageResource(2130838140);
      localImageView.setImageBitmap(FileUtil.getBmpFromFile(getActivity(), this.mLatestPicPath));
    }
    this.mUpgradeImgLayout.setOnClickListener(this);
  }

  private void initViewHandles()
  {
    this.mLocationWaiting = ((TextView)findViewById(2131363947));
    this.mAddLocationButton = ((ImageView)findViewById(2131364082));
    this.mLandmarkLayout = findViewById(2131363986);
    this.mLocationProBar = ((ProgressBar)findViewById(2131363946));
    this.mLocationDesc = ((Button)findViewById(2131363944));
    this.mLocationDelete = findViewById(2131363945);
    this.mLocationIcon = ((ImageView)findViewById(2131363943));
    this.lvReferList = ((ListView)findViewById(2131364095));
    this.referAdapter = new AtFriendsListAdapter(getActivity().getApplicationContext(), this.friendslist);
    this.lvReferList.setAdapter(this.referAdapter);
    this.lvReferList.setOnItemClickListener(this);
  }

  private void initialAtData()
  {
    if (FriendsModel.getInstance().getFriends().size() == 0)
    {
      GetFriendsListTask localGetFriendsListTask = new GetFriendsListTask(getActivity());
      Integer[] arrayOfInteger = new Integer[1];
      arrayOfInteger[0] = Integer.valueOf(1);
      localGetFriendsListTask.execute(arrayOfInteger);
    }
  }

  private boolean isFromHoroscope()
  {
    Bundle localBundle = getArguments();
    return (localBundle != null) && (localBundle.getString("from") != null) && (localBundle.getString("from").equals("HoroscopeFragment"));
  }

  private String loadDraft()
  {
    String str1 = getActivity().getCacheDir().getAbsolutePath();
    boolean bool = FileUtil.existCacheFile(FileUtil.getCacheDir(str1, User.getInstance().getUID()), this.mDraftFileName);
    String str2 = null;
    if (bool)
    {
      str2 = FileUtil.getCacheData(str1, User.getInstance().getUID(), this.mDraftFileName);
      deleteDiarystrContent();
    }
    return str2;
  }

  private void locationSearchFailed()
  {
    setAddLocationButtonState(0);
    setLandmarkDescription(1, null);
  }

  private void saveEditWeibo()
  {
    String str = String.valueOf(this.editWeibo.getText()).trim();
    SharedPreferences localSharedPreferences = getActivity().getApplicationContext().getSharedPreferences("weibo_preference_" + User.getInstance().getUID(), 0);
    if ((str != null) && (!"".equals(str)))
    {
      localSharedPreferences.edit().putString("weibo_text", str).putLong("weibo_time", System.currentTimeMillis()).commit();
      return;
    }
    localSharedPreferences.edit().clear().commit();
  }

  private void saveLocationPreference()
  {
    if (this.refreshLandmarkProxy != null)
      this.refreshLandmarkProxy.saveIncludeLocationPreference(this.mIsOpenLocation);
  }

  private void saveWeiboDraft(String paramString)
  {
    FileUtil.setCacheData(getActivity().getCacheDir().getAbsolutePath(), User.getInstance().getUID(), this.mDraftFileName, paramString);
  }

  private void setAddLocationButtonState(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return;
    case 0:
      this.mIsOpenLocation = false;
      return;
    case 1:
    }
    this.mIsOpenLocation = true;
  }

  private void setButtonHandlers()
  {
    ((ImageView)findViewById(2131362354)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        WriteWeiboFragment.this.showFaceListDialog(true);
      }
    });
    if (!this.mNeedLocation)
      this.mAddLocationButton.setVisibility(8);
    while (true)
    {
      this.mLocationDesc.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          WriteWeiboFragment.this.refreshLandmarkProxy.showLandmarks(WriteWeiboFragment.this.getActivity());
        }
      });
      this.mLocationDelete.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          WriteWeiboFragment.this.cancleLocationSearch();
        }
      });
      this.insertPictureButton = ((ImageView)findViewById(2131364081));
      this.insertPictureButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (WriteWeiboFragment.this.mWriteWeiboModel.getPicPath().size() == 0)
          {
            WriteWeiboFragment.this.selectPictureFromGallery();
            WriteWeiboFragment.this.hideInputMethod();
            return;
          }
          String str = StringUtil.replaceTokenWith(WriteWeiboFragment.this.getResources().getString(2131427672), "*", WriteWeiboFragment.this.getResources().getString(2131427370));
          Toast.makeText(WriteWeiboFragment.this.getActivity(), str, 0).show();
        }
      });
      ImageView localImageView = (ImageView)findViewById(2131362355);
      localImageView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          WriteWeiboFragment.this.hideInputMethod();
          Intent localIntent = new Intent(WriteWeiboFragment.this.getActivity(), FriendsFragment.class);
          Bundle localBundle = new Bundle();
          localBundle.putInt("MODE", 2);
          localIntent.putExtras(localBundle);
          AnimationUtil.startFragmentForResult(WriteWeiboFragment.this, localIntent, 4, 1);
        }
      });
      this.mWriteWeiboLock = ((Button)findViewById(2131363998));
      this.mWriteWeiboLock.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          DialogUtil.showValueListDlg(WriteWeiboFragment.this.getActivity(), 2131427901, WriteWeiboFragment.this.locks, WriteWeiboFragment.this.fancyDialogAdapter, new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
              WriteWeiboFragment.this.fancyDialogAdapter.setSelectedIndex(paramInt);
              WriteWeiboFragment.this.mWriteWeiboLock.setText(WriteWeiboFragment.this.locks[paramInt]);
              switch (paramInt)
              {
              default:
                return;
              case 0:
                WriteWeiboFragment.this.privacy = "0";
                Drawable localDrawable3 = WriteWeiboFragment.this.getResources().getDrawable(2130838254);
                WriteWeiboFragment.this.mWriteWeiboLock.setCompoundDrawablesWithIntrinsicBounds(localDrawable3, null, null, null);
                return;
              case 1:
                WriteWeiboFragment.this.privacy = "1";
                Drawable localDrawable2 = WriteWeiboFragment.this.getResources().getDrawable(2130838252);
                WriteWeiboFragment.this.mWriteWeiboLock.setCompoundDrawablesWithIntrinsicBounds(localDrawable2, null, null, null);
                return;
              case 2:
              }
              WriteWeiboFragment.this.privacy = "2";
              Drawable localDrawable1 = WriteWeiboFragment.this.getResources().getDrawable(2130838253);
              WriteWeiboFragment.this.mWriteWeiboLock.setCompoundDrawablesWithIntrinsicBounds(localDrawable1, null, null, null);
            }
          }
          , true, false);
        }
      });
      if (this.mode == 16)
      {
        this.mAddLocationButton.setVisibility(8);
        this.mLocationDesc.setVisibility(8);
        this.mLocationDelete.setVisibility(8);
        this.insertPictureButton.setVisibility(8);
        localImageView.setVisibility(8);
        this.mWriteWeiboLock.setVisibility(8);
      }
      this.ivShareWeibo = ((ImageView)findViewById(2131364086));
      this.ivShareQzone = ((ImageView)findViewById(2131364085));
      this.ivShareWeibo.setOnClickListener(this);
      this.ivShareQzone.setOnClickListener(this);
      return;
      this.mAddLocationButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (!WriteWeiboFragment.this.mIsOpenLocation)
          {
            if ((WriteWeiboFragment.this.refreshLandmarkProxy.isLocationValid()) && (WriteWeiboFragment.this.refreshLandmarkProxy.isLandmarkListExist()))
            {
              WriteWeiboFragment.this.refreshLandmarkProxy.showLandmarks(WriteWeiboFragment.this.getActivity());
              return;
            }
            WriteWeiboFragment.this.refreshLandmarkProxy.refreshLandmarks();
            return;
          }
          WriteWeiboFragment.this.cancleLocationSearch();
        }
      });
    }
  }

  private void setLandmarkDescription(int paramInt, String paramString)
  {
    if (paramInt >= 0)
    {
      this.mLandmarkLayout.setVisibility(0);
      this.locationView.setVisibility(8);
      switch (paramInt)
      {
      default:
        return;
      case 0:
        this.mLocationIcon.setVisibility(8);
        this.mLocationProBar.setVisibility(0);
        this.mLocationWaiting.setVisibility(0);
        this.mLocationDesc.setVisibility(8);
        this.mLocationDelete.setVisibility(8);
        this.mLocationWaiting.setText(2131427720);
        this.seletedLandmark = null;
        return;
      case 1:
        this.mLocationIcon.setVisibility(0);
        this.mLocationProBar.setVisibility(8);
        this.mLocationWaiting.setVisibility(0);
        this.mLocationDesc.setVisibility(8);
        this.mLocationDelete.setVisibility(8);
        this.mLocationWaiting.setText(2131427721);
        this.seletedLandmark = null;
        return;
      case 2:
      }
      this.mLocationIcon.setVisibility(0);
      this.mLocationProBar.setVisibility(8);
      this.mLocationWaiting.setVisibility(8);
      this.mLocationDesc.setVisibility(0);
      this.mLocationDelete.setVisibility(0);
      this.mLocationDesc.setText(paramString);
      this.seletedLandmark = paramString;
      return;
    }
    this.mLandmarkLayout.setVisibility(8);
    this.locationView.setVisibility(0);
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

  private void showMainLayout(boolean paramBoolean)
  {
    View localView4;
    View localView5;
    View localView6;
    if (this.mNeedLocation)
    {
      localView4 = findViewById(2131362348);
      localView5 = findViewById(2131363986);
      localView6 = findViewById(2131362353);
      if (paramBoolean)
      {
        localView4.setVisibility(0);
        localView5.setVisibility(0);
        this.locationView.setVisibility(8);
        localView6.setVisibility(0);
      }
    }
    while (true)
    {
      if (this.fromHoroscopeFragment)
        findViewById(2131363986).setVisibility(8);
      return;
      localView4.setVisibility(8);
      localView5.setVisibility(8);
      this.locationView.setVisibility(0);
      localView6.setVisibility(8);
      continue;
      View localView1 = findViewById(2131362348);
      View localView2 = findViewById(2131363986);
      localView2.setVisibility(8);
      this.locationView.setVisibility(0);
      View localView3 = findViewById(2131362353);
      localView3.setVisibility(8);
      if (paramBoolean)
      {
        localView1.setVisibility(0);
        continue;
      }
      localView1.setVisibility(8);
      localView2.setVisibility(8);
      this.locationView.setVisibility(0);
      localView3.setVisibility(8);
    }
  }

  private void showSaveDraftOptions()
  {
    DialogUtil.showSelectListDlg(getActivity(), 2131427898, getResources().getStringArray(2131492889), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if (paramInt == 0)
        {
          String str = String.valueOf(WriteWeiboFragment.this.editWeibo.getText()).trim();
          WriteWeiboFragment.this.saveWeiboDraft(str);
        }
        while (true)
        {
          WriteWeiboFragment.this.mWriteWeiboModel.getPicPath().clear();
          WriteWeiboFragment.this.hideInputMethod();
          WriteWeiboFragment.this.finish();
          return;
          WriteWeiboFragment.this.deleteDiaryDraft();
        }
      }
    }
    , 1);
  }

  private void startLoadPhotoTask()
  {
    this.mLoadPhotoTask = new LoadPhotoTask(getActivity(), new LoadPhotoTask.DoPostExecute()
    {
      public void doPostExecute(Boolean paramBoolean, String paramString)
      {
        try
        {
          WriteWeiboFragment.this.msTempFilePath = paramString;
          WriteWeiboFragment.this.mWriteWeiboModel.addPic(paramString);
          if (WriteWeiboFragment.this.mWriteWeiboModel.getPicPath().size() >= 1)
          {
            Bitmap localBitmap = FileUtil.getBmpFromFile(WriteWeiboFragment.this.getActivity(), paramString);
            WriteWeiboFragment.this.contentImgLayout.setVisibility(0);
            WriteWeiboFragment.this.contentImg.setImageBitmap(localBitmap);
          }
          WriteWeiboFragment localWriteWeiboFragment = WriteWeiboFragment.this;
          localWriteWeiboFragment.mInsertedPictureIdBase = (1 + localWriteWeiboFragment.mInsertedPictureIdBase);
          if (WriteWeiboFragment.this.mProgressDialog != null)
          {
            WriteWeiboFragment.this.mProgressDialog.dismiss();
            WriteWeiboFragment.this.mProgressDialog = null;
          }
          return;
        }
        catch (Exception localException)
        {
          KXLog.e("WriteDiaryActivity", "onPostExecute", localException);
        }
      }
    });
    this.mLoadPhotoTask.execute(new Void[0]);
    this.mProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427649), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        WriteWeiboFragment.this.mProgressDialog = null;
        WriteWeiboFragment.this.mLoadPhotoTask.cancel(true);
      }
    });
  }

  private void startSyncShareService(String paramString)
  {
    Intent localIntent = new Intent(getActivity(), SyncShareService.class);
    localIntent.putExtra("summary", paramString);
    localIntent.putExtra("isShareToWeibo", this.isShareToWeibo);
    localIntent.putExtra("isShareToQzone", this.isShareToQzone);
    getActivity().startService(localIntent);
  }

  private void submitRecord()
  {
    hideKeyboard();
    String str1 = "";
    String str2 = "";
    String str3 = "";
    if ((this.mNeedLocation) && (!TextUtils.isEmpty(this.seletedLandmark)))
    {
      str3 = this.seletedLandmark;
      Location localLocation = this.refreshLandmarkProxy.getLocation();
      str1 = String.valueOf(localLocation.getLatitude());
      str2 = String.valueOf(localLocation.getLongitude());
    }
    String str4 = String.valueOf(this.editWeibo.getText()).trim();
    String str5 = String.valueOf(this.editWeibo.getText()).trim();
    int i = 2131427454;
    if (this.mode == 16)
      i = 2131427452;
    if (TextUtils.isEmpty(str4))
      DialogUtil.showKXAlertDialog(getActivity(), i, null);
    do
      return;
    while (!super.checkNetworkAndHint(true));
    this.btnRight.setEnabled(false);
    this.btnLeft.setEnabled(false);
    this.waitLayout.setVisibility(0);
    showMainLayout(false);
    if (this.msTempFilePath != null)
    {
      String str7 = FileUtil.getDynamicFilePath(this.msTempFilePath);
      FileUtil.renameCachePath(this.msTempFilePath, str7);
      this.msTempFilePath = str7;
      this.mWriteWeiboModel.getPicPath().clear();
      this.mWriteWeiboModel.addPic(str7);
    }
    RecordUploadTask localRecordUploadTask = new RecordUploadTask(getActivity().getApplicationContext());
    String str6 = "2";
    if (this.mode == 16)
      str6 = "1";
    localRecordUploadTask.initRecordUploadTask(this.privacy, str4, this.msTempFilePath, str6, str1, str2, str3, null, 1);
    UploadTaskListEngine.getInstance().addUploadTask(localRecordUploadTask);
    if (this.fromHoroscopeFragment)
      startSyncShareService(str5);
    inputFinish();
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
    try
    {
      if (!new File(paramString1).exists())
      {
        Toast.makeText(getActivity(), 2131427841, 0).show();
        return;
      }
      addPic(paramString1);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("WriteDiaryActivity", "onActivityResult", localException);
    }
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((this.mUpgradeImgLayout.getVisibility() == 0) && (paramMotionEvent.getAction() == 0))
    {
      int[] arrayOfInt = new int[2];
      this.mUpgradeImgLayout.getLocationOnScreen(arrayOfInt);
      int i = arrayOfInt[0];
      int j = arrayOfInt[1];
      if (!new RectF(i, j, i + this.mUpgradeImgLayout.getWidth(), j + this.mUpgradeImgLayout.getHeight()).contains(paramMotionEvent.getX(), paramMotionEvent.getY()))
      {
        this.mUpgradeImgLayout.setVisibility(8);
        this.mSendArrowLayout.setVisibility(8);
        this.insertPictureButton.setImageResource(2130838022);
        this.mLatestPicPath = null;
        return true;
      }
    }
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  public void inputFinish()
  {
    this.mWriteWeiboModel.getPicPath().clear();
    Intent localIntent = new Intent();
    localIntent.putExtra("content", String.valueOf(this.editWeibo.getText()));
    setResult(-1, localIntent);
    finish();
  }

  protected void insertFaceIcon(int paramInt)
  {
    String str;
    Editable localEditable;
    int i;
    int j;
    if (this.mFaceModel.getStateFaceStrings() != null)
    {
      str = (String)this.mFaceModel.getStateFaceStrings().get(paramInt);
      localEditable = ((EditText)findViewById(2131363991)).getText();
      i = Selection.getSelectionStart(localEditable);
      j = Selection.getSelectionEnd(localEditable);
      if ((i == -1) || (j == -1))
      {
        i = localEditable.length();
        j = localEditable.length();
      }
      if (localEditable.length() <= 140 - str.length())
        break label111;
      Toast.makeText(getActivity(), 2131427899, 0).show();
    }
    label111: 
    do
    {
      return;
      localEditable.replace(i, j, str.subSequence(0, str.length()));
    }
    while ((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    localEditable.setSpan(new ImageSpan(null, (Bitmap)this.mFaceModel.getStateFaceIcons().get(paramInt), 1), i, i + str.length(), 33);
  }

  protected void insertFaceIcon(String paramString, Bitmap paramBitmap)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramBitmap == null));
    Editable localEditable;
    int i;
    do
    {
      return;
      localEditable = ((EditText)findViewById(2131363991)).getText();
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
    while ((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape()));
    localEditable.setSpan(new ImageSpan(null, paramBitmap, 1), i, i + paramString.length(), 33);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1)
    {
      if (paramInt1 != 4)
        break label93;
      String str1 = paramIntent.getStringExtra("fuid");
      String str2 = paramIntent.getStringExtra("fname");
      int j = this.editWeibo.getSelectionStart();
      if (FriendsUtil.insertFriendIntoContent(j, j, str1, str2, this.editWeibo, this.mNameBmpMap, 140, this.mOrientation, dipToPx(2.0F)) < 0)
        Toast.makeText(getActivity(), 2131427900, 0).show();
    }
    label93: int i;
    do
    {
      do
        return;
      while (paramInt1 != 0);
      i = paramIntent.getIntExtra("userAction", 0);
      if (i != 101)
        continue;
      deleteRecordImgContent();
    }
    while (i != 105);
    paramIntent.getExtras();
    startLoadPhotoTask();
  }

  public void onClick(View paramView)
  {
    paramView.requestFocus();
    if (paramView.equals(this.mUpgradeImgLayout))
      if (this.mLatestPicPath != null)
      {
        if (new File(this.mLatestPicPath).exists())
          break label55;
        Toast.makeText(getActivity(), 2131427841, 0).show();
      }
    label55: 
    do
    {
      while (true)
      {
        return;
        this.msTempFilePath = this.mLatestPicPath;
        this.mWriteWeiboModel.addPic(this.mLatestPicPath);
        Bitmap localBitmap = FileUtil.getBmpFromFile(getActivity(), this.mLatestPicPath);
        this.contentImgLayout.setVisibility(0);
        this.contentImg.setImageBitmap(localBitmap);
        this.mInsertedPictureIdBase = (1 + this.mInsertedPictureIdBase);
        this.mUpgradeImgLayout.setVisibility(8);
        this.mSendArrowLayout.setVisibility(8);
        this.insertPictureButton.setImageResource(2130838022);
        this.mLatestPicPath = null;
        return;
        if (paramView.equals(this.btnLeft))
        {
          saveEditWeibo();
          hideInputMethod();
          if (finishAndRedirect())
            continue;
          finish();
          return;
        }
        if (!paramView.equals(this.btnRight))
          break;
        submitRecord();
        clearSavedWeibo();
        hideInputMethod();
        if (!this.fromHoroscopeFragment)
          continue;
        UserHabitUtils.getInstance(getActivity()).addUserHabit("share_horoscope");
        return;
      }
      if (!paramView.equals(this.ivShareWeibo))
        continue;
      if (TextUtils.isEmpty(this.shareManager.getWeiboToken()))
      {
        this.shareManager.authWeibo();
        return;
      }
      if (this.isShareToWeibo)
      {
        this.isShareToWeibo = false;
        this.ivShareWeibo.setBackgroundResource(2130838946);
        return;
      }
      this.isShareToWeibo = true;
      this.ivShareWeibo.setBackgroundResource(2130838947);
      return;
    }
    while (!paramView.equals(this.ivShareQzone));
    if (TextUtils.isEmpty(this.shareManager.getQQToken()))
    {
      this.shareManager.authQzone();
      return;
    }
    if (this.isShareToQzone)
    {
      this.isShareToQzone = false;
      this.ivShareQzone.setBackgroundResource(2130838944);
      return;
    }
    this.isShareToQzone = true;
    this.ivShareQzone.setBackgroundResource(2130838945);
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    if (this.mOrientation != paramConfiguration.orientation)
    {
      this.mOrientation = paramConfiguration.orientation;
      changeContentForOrientation();
    }
    super.onConfigurationChanged(paramConfiguration);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.fromHoroscopeFragment = isFromHoroscope();
    if (this.fromHoroscopeFragment)
      this.mNeedLocation = false;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903425, paramViewGroup, false);
  }

  public void onDestroy()
  {
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

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    FriendsModel.Friend localFriend = (FriendsModel.Friend)this.friendslist.get(paramInt);
    int i = this.index;
    this.index = -1;
    if (FriendsUtil.insertFriendIntoContent(i, this.editWeibo.getSelectionStart(), localFriend.getFuid(), localFriend.getFname(), this.editWeibo, this.mNameBmpMap, 140, this.mOrientation, dipToPx(2.0F)) < 0)
      Toast.makeText(getActivity(), 2131427900, 0).show();
    this.lvReferList.setVisibility(8);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    super.onKeyDown(paramInt, paramKeyEvent);
    if (paramInt == 4)
    {
      saveEditWeibo();
      hideInputMethod();
      finish();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void onPause()
  {
    this.index = -1;
    saveLocationPreference();
    super.onPause();
  }

  public void onReceived(SocialShareManager.ReceiveType paramReceiveType)
  {
    if (paramReceiveType == SocialShareManager.ReceiveType.RECEIVE_QZONE)
    {
      this.ivShareQzone.setBackgroundResource(2130838945);
      this.isShareToQzone = true;
    }
    do
      return;
    while (paramReceiveType != SocialShareManager.ReceiveType.RECEIVE_WEIBO);
    this.ivShareWeibo.setBackgroundResource(2130838947);
    this.isShareToWeibo = true;
  }

  public void onResume()
  {
    if (this.refreshLandmarkProxy != null)
      this.refreshLandmarkProxy.refreshLocationProxy.reworkOnResume();
    super.onResume();
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    if (isNeedReturn())
      return;
    String str1 = paramCharSequence.toString();
    String str2 = str1.trim();
    if (140 < handlerStrSpace(0, str2, str2.length()))
      this.btnRight.setEnabled(false);
    while (true)
    {
      if (KXTextUtil.isCurrentAtSymbol(str1, paramInt1, paramInt2, paramInt3) >= 0)
      {
        this.index = KXTextUtil.isCurrentAtSymbol(str1, paramInt1, paramInt2, paramInt3);
        this.friendslist.clear();
        FriendsUtil.getAllFriends(this.friendslist);
        this.referAdapter.notifyDataSetChanged();
        this.lvReferList.setVisibility(0);
      }
      if (KXTextUtil.isValidPositionAtSymbol(str1, this.index) < 0)
      {
        this.index = -1;
        this.lvReferList.setVisibility(8);
      }
      String str3 = KXTextUtil.isValidNameInputing(str1, paramInt1, paramInt2, paramInt3, this.index);
      if (str3 != null)
      {
        this.friendslist.clear();
        FriendsUtil.searchListData(FriendsModel.getInstance().getFriends(), str3, this.friendslist);
        this.referAdapter.notifyDataSetChanged();
        this.lvReferList.setVisibility(0);
      }
      if (this.friendslist.size() != 0)
        break;
      this.lvReferList.setVisibility(8);
      return;
      this.btnRight.setEnabled(true);
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mFaceModel = FaceModel.getInstance();
    this.mUploadPhotoFrom = EditPictureModel.getPicFrom();
    setTitleBar();
    initMainView();
    initViewHandles();
    setButtonHandlers();
    initPromptView();
    initialAtData();
    if (this.mode == 16)
    {
      this.mNeedLocation = false;
      findViewById(2131363986).setVisibility(8);
    }
    this.mOrientation = ScreenUtil.getOrientation(getActivity());
    String[] arrayOfString = getResources().getStringArray(2131492890);
    int i = arrayOfString.length;
    if (this.mode == 16)
      i--;
    this.locks = new String[i];
    int j = 0;
    if (j >= i)
      if (this.mNeedLocation)
      {
        this.refreshLandmarkProxy = new RefreshLandmarkProxy(getActivity(), new RefreshLandmarkProxy.IRefreshLandmarkListCallback()
        {
          public void onCancleRefreshLandmarks()
          {
            WriteWeiboFragment.this.cancleLocationSearch();
          }

          public void onGetLandmarksFailed()
          {
            WriteWeiboFragment.this.locationSearchFailed();
          }

          public void onRefreshLandmarks()
          {
            WriteWeiboFragment.this.setAddLocationButtonState(1);
            WriteWeiboFragment.this.setLandmarkDescription(0, null);
          }

          public void onSelectLandmark(String paramString)
          {
            WriteWeiboFragment.this.setAddLocationButtonState(1);
            WriteWeiboFragment.this.setLandmarkDescription(2, paramString);
            KXLog.d("WriteRecordActivity", paramString);
          }
        });
        if (!this.refreshLandmarkProxy.getIncludeLocationPreference())
          break label328;
        this.refreshLandmarkProxy.refreshLandmarks();
      }
    while (true)
    {
      if (this.fromHoroscopeFragment)
      {
        findViewById(2131363986).setVisibility(8);
        this.mAddLocationButton.setVisibility(8);
        findViewById(2131364083).setVisibility(8);
        findViewById(2131364084).setVisibility(0);
        this.shareManager = new SocialShareManager(getActivity());
        this.shareManager.setOnReceiverListener(this);
        if (!TextUtils.isEmpty(this.shareManager.getQQToken()))
        {
          this.ivShareQzone.setBackgroundResource(2130838945);
          this.isShareToQzone = true;
        }
        if (!TextUtils.isEmpty(this.shareManager.getWeiboToken()))
        {
          this.ivShareWeibo.setBackgroundResource(2130838947);
          this.isShareToWeibo = true;
        }
      }
      KXUBLog.log("homepage_records");
      return;
      this.locks[j] = arrayOfString[j];
      j++;
      break;
      label328: cancleLocationSearch();
    }
  }

  public void requestFinish()
  {
  }

  protected void setTitleBar()
  {
    this.btnLeft = ((ImageView)findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    this.btnLeft.setOnClickListener(this);
    this.tvTitle = ((TextView)findViewById(2131362920));
    getArguments();
    if (this.fromHoroscopeFragment)
      this.tvTitle.setText(2131427429);
    while (true)
    {
      this.tvTitle.setVisibility(0);
      ((ImageView)findViewById(2131362919)).setVisibility(8);
      this.btnRight = ((ImageView)findViewById(2131362928));
      this.btnRight.setOnClickListener(this);
      this.btnRight.setImageResource(2130839272);
      this.btnRight.setOnClickListener(this);
      return;
      this.tvTitle.setText(2131427428);
    }
  }

  public void showFaceListDialog(boolean paramBoolean)
  {
    ImageView localImageView = (ImageView)findViewById(2131362354);
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

  private class PrivaligeCheckBoxListAdapter extends CheckBoxListAdapter<AlbumInfo>
  {
    PrivaligeCheckBoxListAdapter(Context arg2)
    {
      super();
    }

    public int getCount()
    {
      if (WriteWeiboFragment.this.locks != null)
        return WriteWeiboFragment.this.locks.length;
      return 0;
    }

    public Object getItem(int paramInt)
    {
      return WriteWeiboFragment.this.locks[paramInt];
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.WriteWeiboFragment
 * JD-Core Version:    0.6.0
 */