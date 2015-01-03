package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.Selection;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.InputFaceActivity;
import com.kaixin001.engine.FriendsEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.CircleRecordUploadTask;
import com.kaixin001.model.EditPictureModel;
import com.kaixin001.model.FaceModel;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.model.WriteWeiboModel;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXExifInterface;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.ScreenUtil;
import com.kaixin001.util.StringUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;

public class WriteCircleRecordFragment extends BaseFragment
  implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener
{
  public static final String ACTION_VIEW_WEIBOPHONE = "com.kaixin001.VIEW_WEIBOPHONE";
  protected static final int DIALOG_LIST_MODE = 14;
  protected static final int DIALOG_MODE = 12;
  protected static final int DIALOG_TO_FRIEND_MODE = 15;
  public static final int FRIEND_SELECTED = 4;
  protected static final int NORMAL_MODE = 10;
  protected static final int REPOST_LIST_MODE = 13;
  protected static final int REPOST_MODE = 11;
  protected static final int SELECT_LOCATION = 106;
  private static final String TAG_AT = "@";
  private static final String TAG_NAME_LEFT = "(#";
  private static final String TAG_NAME_RIGHT = "#)";
  public static final String UPDATE_TYPE_ALL = "0";
  private static final int WEIBO_MAXNUM = 140;
  private static final String ms_TempPicName = "kx_circle_record_draft_pic_1.jpg";
  private ImageView btnLeft;
  private ImageView btnRight;
  private EditText editWeibo;
  private ArrayList<FriendsModel.Friend> friendslist = new ArrayList();
  private String gid;
  private int index = -1;
  private String[] locks;
  private ListView lvReferList;
  LockListAdapter mAlbumListAdapter = new LockListAdapter(null);
  private String mDraftFileName = "circle_record.kx";
  private FaceModel mFaceModel;
  private String mFilePathName = "";
  private int mInsertedPictureIdBase = 1;
  private LoadPhotoTask mLoadPhotoTask = null;
  public JSONArray mLocationBuildingList = null;
  private HashMap<String, Bitmap> mNameBmpMap = new HashMap();
  private int mOrientation = 1;
  private ProgressDialog mProgressDialog = null;
  private String mUploadFilePathName = "";
  private String mUploadPhotoFrom = "camera";
  private WriteWeiboModel mWriteWeiboModel = WriteWeiboModel.getInstance();
  private int mode = 0;
  private String msTempFilePath = null;
  private Button pictureButton;
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

  private void SaveWeiboDraft(String paramString)
  {
    FileUtil.setCacheData(getActivity().getCacheDir().getAbsolutePath(), User.getInstance().getUID(), this.mDraftFileName, paramString);
  }

  private void backOrExit()
  {
    if (String.valueOf(this.editWeibo.getText()).trim().length() != 0)
    {
      showSaveDraftOptions();
      return;
    }
    deleteDiaryDraft();
    this.mWriteWeiboModel.getPicPath().clear();
    hideInputMethod();
    finish();
  }

  private void changeContentForOrientation()
  {
    int i = this.editWeibo.getSelectionStart();
    int j = this.editWeibo.getSelectionEnd();
    String str1 = this.editWeibo.getText().toString();
    SpannableString localSpannableString;
    Matcher localMatcher;
    if (((1 == this.mOrientation) || (Setting.getInstance().isShowFaceIconInLandScape())) && (!TextUtils.isEmpty(str1)))
    {
      localSpannableString = StringUtil.convertTextToStateFace(str1);
      if (localSpannableString != null)
      {
        localMatcher = Pattern.compile("@([0-9]+)\\(\\#(\\S+?)\\#\\)").matcher(localSpannableString);
        if (localMatcher.find());
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
      String str2 = localMatcher.group();
      String str3 = localMatcher.group(2);
      int k = localMatcher.start();
      int m = localMatcher.end();
      Bitmap localBitmap = (Bitmap)this.mNameBmpMap.get(str2);
      if (localBitmap == null)
      {
        localBitmap = ImageCache.createStringBitmap("@" + str3, this.editWeibo.getPaint());
        this.mNameBmpMap.put(str2, localBitmap);
      }
      localSpannableString.setSpan(new ImageSpan(localBitmap), k, m, 33);
      break;
      this.editWeibo.setText(str1);
    }
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
    FileUtil.deleteCacheFile(FileUtil.getCacheDir(getActivity().getCacheDir().getAbsolutePath(), User.getInstance().getUID()), "kx_circle_record_draft_pic_1.jpg");
  }

  private void getAllFriends()
  {
    try
    {
      this.friendslist.clear();
      ArrayList localArrayList = FriendsModel.getInstance().getFriends();
      if (localArrayList != null)
        this.friendslist.addAll(localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
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
    this.editWeibo = ((EditText)findViewById(2131363991));
    this.editWeibo.requestFocus();
    EditText localEditText = this.editWeibo;
    InputFilter[] arrayOfInputFilter = new InputFilter[1];
    arrayOfInputFilter[0] = new InputFilter.LengthFilter(140);
    localEditText.setFilters(arrayOfInputFilter);
    this.editWeibo.addTextChangedListener(this);
    this.lvReferList = ((ListView)findViewById(2131364095));
    this.waitLayout = ((LinearLayout)findViewById(2131362356));
    this.editWeibo.setText(loadDraft());
    this.editWeibo.requestFocus();
    this.editWeibo.addTextChangedListener(this);
    changeContentForOrientation();
  }

  private void initialAtData()
  {
    if (FriendsModel.getInstance().getFriends().size() != 0)
    {
      getAllFriends();
      return;
    }
    GetDataTask localGetDataTask = new GetDataTask(null);
    Integer[] arrayOfInteger = new Integer[1];
    arrayOfInteger[0] = Integer.valueOf(1);
    localGetDataTask.execute(arrayOfInteger);
  }

  private void insertFriendIntoContent(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)));
    while (true)
    {
      return;
      String str = "@" + paramString1 + "(#" + paramString2 + "#)";
      Bitmap localBitmap = (Bitmap)this.mNameBmpMap.get(str);
      if (localBitmap == null)
        localBitmap = ImageCache.createStringBitmap("@" + paramString2, this.editWeibo.getPaint());
      if (localBitmap != null)
        this.mNameBmpMap.put(str, localBitmap);
      Editable localEditable = this.editWeibo.getText().replace(paramInt1, paramInt2, str + " ");
      if (paramInt1 + str.length() > 140)
        break;
      if ((1 != this.mOrientation) && (!Setting.getInstance().isShowFaceIconInLandScape()))
        continue;
      localEditable.setSpan(new ImageSpan(localBitmap), paramInt1, paramInt1 + str.length(), 33);
      return;
    }
    Toast.makeText(getActivity(), 2131427900, 0).show();
  }

  private String loadDraft()
  {
    String str1 = getActivity().getCacheDir().getAbsolutePath();
    String str2 = FileUtil.getCacheDir(str1, User.getInstance().getUID());
    boolean bool = FileUtil.existCacheFile(str2, this.mDraftFileName);
    String str3 = null;
    if (bool)
    {
      ArrayList localArrayList = this.mWriteWeiboModel.getPicPath();
      if (FileUtil.existCacheFile(str2, "kx_circle_record_draft_pic_1.jpg"))
        localArrayList.add(str2 + "/" + "kx_circle_record_draft_pic_1.jpg");
      str3 = FileUtil.getCacheData(str1, User.getInstance().getUID(), this.mDraftFileName);
      deleteDiarystrContent();
    }
    return str3;
  }

  private void setButtonHandlers()
  {
    ((Button)findViewById(2131362354)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(WriteCircleRecordFragment.this.getActivity(), InputFaceActivity.class);
        WriteCircleRecordFragment.this.startActivityForResult(localIntent, 209);
      }
    });
    Button localButton1 = (Button)findViewById(2131364081);
    if ((this.mode == 11) || (this.mode == 13))
    {
      localButton1.setVisibility(8);
      localButton1.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (WriteCircleRecordFragment.this.mWriteWeiboModel.getPicPath().size() == 0)
          {
            WriteCircleRecordFragment.this.showInsertPictureOptions();
            return;
          }
          String str = StringUtil.replaceTokenWith(WriteCircleRecordFragment.this.getResources().getString(2131427672), "*", WriteCircleRecordFragment.this.getResources().getString(2131427370));
          Toast.makeText(WriteCircleRecordFragment.this.getActivity(), str, 0).show();
        }
      });
      Button localButton2 = (Button)findViewById(2131362355);
      localButton2.setVisibility(8);
      localButton2.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          Intent localIntent = new Intent(WriteCircleRecordFragment.this.getActivity(), FriendsFragment.class);
          Bundle localBundle = new Bundle();
          localBundle.putInt("MODE", 2);
          localIntent.putExtras(localBundle);
          AnimationUtil.startFragmentForResult(WriteCircleRecordFragment.this, localIntent, 4, 1);
        }
      });
      this.pictureButton = ((Button)findViewById(2131364105));
      if (this.mWriteWeiboModel.getPicPath().size() <= 0)
        break label183;
      this.pictureButton.setVisibility(0);
    }
    while (true)
    {
      this.pictureButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          Intent localIntent = new Intent("com.kaixin001.VIEW_WEIBOPHONE");
          localIntent.setClass(WriteCircleRecordFragment.this.getActivity(), PreviewUploadPhotoFragment.class);
          Bundle localBundle = new Bundle();
          localBundle.putString("imgFilePath", (String)WriteCircleRecordFragment.this.mWriteWeiboModel.getPicPath().get(0));
          localIntent.putExtras(localBundle);
          WriteCircleRecordFragment.this.startActivityForResult(localIntent, 0);
        }
      });
      ((Button)findViewById(2131363998)).setVisibility(8);
      findViewById(2131364082).setVisibility(8);
      return;
      localButton1.setVisibility(0);
      break;
      label183: this.pictureButton.setVisibility(8);
    }
  }

  private void showInsertPictureOptions()
  {
    selectPictureFromGallery();
  }

  private void showMainLayout(boolean paramBoolean)
  {
    View localView1 = findViewById(2131362348);
    View localView2 = findViewById(2131364104);
    localView2.setVisibility(8);
    View localView3 = findViewById(2131362353);
    localView3.setVisibility(8);
    if (paramBoolean)
    {
      localView1.setVisibility(0);
      return;
    }
    localView1.setVisibility(8);
    localView2.setVisibility(8);
    localView3.setVisibility(8);
  }

  private void showSaveDraftOptions()
  {
    DialogUtil.showSelectListDlg(getActivity(), 2131427898, getResources().getStringArray(2131492889), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if (paramInt == 0)
        {
          String str = String.valueOf(WriteCircleRecordFragment.this.editWeibo.getText()).trim();
          WriteCircleRecordFragment.this.SaveWeiboDraft(str);
        }
        while (true)
        {
          WriteCircleRecordFragment.this.mWriteWeiboModel.getPicPath().clear();
          WriteCircleRecordFragment.this.hideInputMethod();
          WriteCircleRecordFragment.this.finish();
          return;
          WriteCircleRecordFragment.this.deleteDiaryDraft();
        }
      }
    }
    , 1);
  }

  private void submitRecord()
  {
    if (TextUtils.isEmpty(String.valueOf(this.editWeibo.getText()).trim()))
      DialogUtil.showKXAlertDialog(getActivity(), 2131427454, null);
    do
      return;
    while (!super.checkNetworkAndHint(true));
    this.btnRight.setEnabled(false);
    this.btnLeft.setEnabled(false);
    this.waitLayout.setVisibility(0);
    showMainLayout(false);
    if (this.msTempFilePath != null)
    {
      String str = FileUtil.getDynamicFilePath(this.msTempFilePath);
      FileUtil.renameCachePath(this.msTempFilePath, str);
      this.msTempFilePath = str;
      this.mWriteWeiboModel.clear();
      this.mWriteWeiboModel.addPic(str);
    }
    CircleRecordUploadTask localCircleRecordUploadTask = new CircleRecordUploadTask(getActivity().getApplicationContext());
    localCircleRecordUploadTask.initCircleRecordUploadTask(String.valueOf(this.editWeibo.getText()), this.msTempFilePath, "2", this.gid, 10);
    UploadTaskListEngine.getInstance().addUploadTask(localCircleRecordUploadTask);
    Toast.makeText(getActivity().getApplication(), 2131428307, 0).show();
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
      LoadInsertedPicture(paramString1);
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("WriteDiaryActivity", "onActivityResult", localException);
    }
  }

  public void inputFinish()
  {
    this.mWriteWeiboModel.getPicPath().clear();
    setResult(-1);
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
    localEditable.setSpan(new ImageSpan((Bitmap)this.mFaceModel.getStateFaceIcons().get(paramInt)), i, i + str.length(), 33);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1)
    {
      if (paramInt1 != 4)
        break label57;
      String str1 = paramIntent.getStringExtra("fuid");
      String str2 = paramIntent.getStringExtra("fname");
      int i = this.editWeibo.getSelectionStart();
      insertFriendIntoContent(i, i, str1, str2);
    }
    label57: 
    do
      while (true)
      {
        return;
        if (paramInt1 != 0)
          break;
        if (paramIntent.getIntExtra("userAction", 0) != 101)
          continue;
        this.mWriteWeiboModel.getPicPath().clear();
        FileUtil.deleteCacheFile(FileUtil.getCacheDir(getActivity().getCacheDir().getAbsolutePath(), User.getInstance().getUID()), "kx_circle_record_draft_pic_1.jpg");
        this.msTempFilePath = null;
        return;
      }
    while (paramInt1 != 105);
    this.mLoadPhotoTask = new LoadPhotoTask(null);
    this.mLoadPhotoTask.execute(new Void[0]);
    this.mProgressDialog = ProgressDialog.show(getActivity(), "", getResources().getText(2131427649), true, true, new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramDialogInterface)
      {
        WriteCircleRecordFragment.this.mProgressDialog = null;
        WriteCircleRecordFragment.this.mLoadPhotoTask.cancel(true);
      }
    });
  }

  public void onClick(View paramView)
  {
    paramView.requestFocus();
    if (paramView.equals(this.btnLeft))
      backOrExit();
    do
      return;
    while (!paramView.equals(this.btnRight));
    submitRecord();
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
    Bundle localBundle = getArguments();
    if (localBundle == null);
    for (String str = null; str == null; str = localBundle.getString("gid"))
      throw new IllegalArgumentException("gid can not be null");
    this.gid = str;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903426, paramViewGroup, false);
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
        cancelTask(this.mLoadPhotoTask);
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
    insertFriendIntoContent(i, this.editWeibo.getSelectionStart(), localFriend.getFuid(), localFriend.getFname());
    this.lvReferList.setVisibility(8);
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
    this.index = -1;
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
    if (this.mWriteWeiboModel.getPicPath().size() > 0)
    {
      this.pictureButton.setText(String.valueOf(this.mWriteWeiboModel.getPicPath().size()));
      this.pictureButton.setVisibility(0);
      return;
    }
    this.pictureButton.setVisibility(8);
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mFaceModel = FaceModel.getInstance();
    this.mFilePathName = EditPictureModel.getBitmapPath();
    this.mUploadPhotoFrom = EditPictureModel.getPicFrom();
    setTitleBar();
    initMainView();
    setButtonHandlers();
    initialAtData();
    this.mOrientation = ScreenUtil.getOrientation(getActivity());
    this.locks = getResources().getStringArray(2131492890);
  }

  public void requestFinish()
  {
    backOrExit();
  }

  protected void setTitleBar()
  {
    this.btnLeft = ((ImageView)findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    this.btnLeft.setOnClickListener(this);
    this.tvTitle = ((TextView)findViewById(2131362920));
    this.tvTitle.setText(2131428085);
    this.tvTitle.setVisibility(0);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    this.btnRight = ((ImageView)findViewById(2131362928));
    this.btnRight.setOnClickListener(this);
    this.btnRight.setImageResource(2130839272);
    this.btnRight.setOnClickListener(this);
  }

  private class GetDataTask extends KXFragment.KXAsyncTask<Integer, Void, Integer>
  {
    private GetDataTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      if ((paramArrayOfInteger == null) || (paramArrayOfInteger.length == 0))
        return Integer.valueOf(0);
      try
      {
        if (FriendsEngine.getInstance().getFriendsList(WriteCircleRecordFragment.this.getActivity().getApplicationContext(), paramArrayOfInteger[0].intValue()))
          return Integer.valueOf(1);
        Integer localInteger = Integer.valueOf(0);
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((paramInteger == null) || (paramInteger.intValue() == 0));
      do
        return;
      while (paramInteger.intValue() != 1);
      WriteCircleRecordFragment.this.getAllFriends();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class LoadPhotoTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    private Bitmap mLoadedBitmap = null;

    private LoadPhotoTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      if (EditPictureModel.getBitmapPath().length() < 1)
      {
        KXLog.d("WriteDiaryActivity", "doInBackground");
        return Boolean.valueOf(false);
      }
      try
      {
        this.mLoadedBitmap = EditPictureModel.getBimap();
        KXExifInterface localKXExifInterface = ImageCache.getExifInfo(WriteCircleRecordFragment.this.mFilePathName);
        WriteCircleRecordFragment.this.mUploadFilePathName = ImageCache.saveBitmapToFile(WriteCircleRecordFragment.this.getActivity(), this.mLoadedBitmap, localKXExifInterface, "kx_tmp_upload.jpg");
        if (TextUtils.isEmpty(WriteCircleRecordFragment.this.mUploadFilePathName))
          return Boolean.valueOf(false);
      }
      catch (Exception localException)
      {
        KXLog.e("WriteDiaryActivity", "doInBackground", localException);
        return Boolean.valueOf(false);
      }
      return Boolean.valueOf(true);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      try
      {
        WriteCircleRecordFragment.this.msTempFilePath = WriteCircleRecordFragment.this.mUploadFilePathName;
        WriteCircleRecordFragment.this.mWriteWeiboModel.addPic(WriteCircleRecordFragment.this.mUploadFilePathName);
        if (WriteCircleRecordFragment.this.mWriteWeiboModel.getPicPath().size() >= 1)
        {
          WriteCircleRecordFragment.this.pictureButton = ((Button)WriteCircleRecordFragment.this.findViewById(2131364105));
          WriteCircleRecordFragment.this.pictureButton.setText(String.valueOf(WriteCircleRecordFragment.this.mWriteWeiboModel.getPicPath().size()));
          WriteCircleRecordFragment.this.pictureButton.setVisibility(0);
        }
        WriteCircleRecordFragment localWriteCircleRecordFragment = WriteCircleRecordFragment.this;
        localWriteCircleRecordFragment.mInsertedPictureIdBase = (1 + localWriteCircleRecordFragment.mInsertedPictureIdBase);
        this.mLoadedBitmap = null;
        if (WriteCircleRecordFragment.this.mProgressDialog != null)
        {
          WriteCircleRecordFragment.this.mProgressDialog.dismiss();
          WriteCircleRecordFragment.this.mProgressDialog = null;
        }
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("WriteDiaryActivity", "onPostExecute", localException);
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class LockListAdapter extends BaseAdapter
  {
    private int mSelectIndex = 0;

    private LockListAdapter()
    {
    }

    public int getCount()
    {
      return WriteCircleRecordFragment.this.locks.length;
    }

    public Object getItem(int paramInt)
    {
      return Integer.valueOf(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View localView = paramView;
      if (localView == null)
      {
        localView = WriteCircleRecordFragment.this.getActivity().getLayoutInflater().inflate(2130903053, paramViewGroup, false);
        ViewHolder localViewHolder2 = new ViewHolder(null);
        localViewHolder2.nameView = ((TextView)localView.findViewById(2131361847));
        localViewHolder2.albumChoseMark = localView.findViewById(2131361846);
        localView.setTag(localViewHolder2);
      }
      try
      {
        ViewHolder localViewHolder1 = (ViewHolder)localView.getTag();
        String str = WriteCircleRecordFragment.this.locks[paramInt];
        localViewHolder1.nameView.setText(str);
        if (this.mSelectIndex == paramInt)
        {
          localViewHolder1.albumChoseMark.setVisibility(0);
          return localView;
        }
        localViewHolder1.albumChoseMark.setVisibility(4);
        return localView;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return localView;
    }

    private class ViewHolder
    {
      public View albumChoseMark;
      public TextView nameView;

      private ViewHolder()
      {
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.WriteCircleRecordFragment
 * JD-Core Version:    0.6.0
 */