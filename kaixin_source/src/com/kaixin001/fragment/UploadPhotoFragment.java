package com.kaixin001.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.MessageHandlerHolder;
import com.kaixin001.adapter.CheckBoxListAdapter;
import com.kaixin001.engine.AlbumListEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.AlbumInfo;
import com.kaixin001.item.LocalPhotoItem;
import com.kaixin001.item.PhotoUploadTask;
import com.kaixin001.lbs.RefreshLandmarkProxy;
import com.kaixin001.lbs.RefreshLandmarkProxy.IRefreshLandmarkListCallback;
import com.kaixin001.lbs.RefreshLocationProxy;
import com.kaixin001.model.AlbumListModel;
import com.kaixin001.model.EditPictureModel;
import com.kaixin001.model.EventModel.EventData;
import com.kaixin001.model.PhotoSelectModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FancyDialogAdapter;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXExifInterface;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.KXFrameImageView;
import com.kaixin001.view.KXScrollView;
import com.kaixin001.view.KXScrollView.SizeChangeListener;
import com.kaixin001.view.KXUpgradeGallery;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UploadPhotoFragment extends BaseFragment
{
  protected static final int EDIT_PHOTO_CONTINUE = 203;
  protected static final int EDIT_PHOTO_EFFECT = 206;
  public static final String EVENT_ACTIVITY = "event_activity";
  private static final String FROM_WEBPAGE = "from_webpage";
  protected static final int NEED_RETRUN_NEWS = 204;
  public static final int REQUEST_CODE_ADD_PIC = 1011;
  private static final int STATE_FINDING_LOCATION = 0;
  private static final int STATE_NO_LOCATION = 1;
  private static final int STATE_SET_LOCATION = 2;
  private static final String TAG = "UploadPhotoActivity";
  protected static final int UPLOAD_PHOTO_CAMERA_CONTINUE = 201;
  protected static final int UPLOAD_PHOTO_GALLERY_CONTINUE = 202;
  protected static final int WEB_ACTIVITY = 200;
  public static final String mc_DEFAULTFILENAME = "kx_upload_tmp.jpg";
  private GalleryImageAdapter adapter;
  ImageView cancelButton = null;
  private View curView;
  private int effectPosition;
  private FancyDialogAdapter fancyDialogAdapter = new FancyDialogAdapter();
  private Map<String, String> fileTitle = new HashMap();
  private KXUpgradeGallery gallery;
  private View lastView;
  private ArrayList<LocalPhotoItem> list = new ArrayList();
  String mActivityName = null;
  private TextView mAlbumChosen = null;
  private String mAlbumId = "";
  private ArrayList<AlbumInfo> mAlbumList = null;
  private AlbumInfoCheckBoxListAdapter mAlbumListAdapter = new AlbumInfoCheckBoxListAdapter(getActivity());
  String mAlbumName;
  private Context mContext = null;
  private int mCurIndex = 0;
  private AlertDialog mDlgChooseAlbum = null;
  EventModel.EventData mEventData = null;
  private String mFileFrom = "";
  private String mFilePath = "";
  private String mFilePathName = "";
  private String mFileTitle = "";
  private boolean mFromOtherApp = false;
  private GetAlbumlistTask mGetAlbumlistTask = null;
  private View mGuideView;
  private KXScrollView mKXScrollView;
  private View mLocationBtn;
  private TextView mLocationDes = null;
  boolean mNeedLocation = Setting.getInstance().gpsEnabled();
  private View mPicBtn;
  private TextView mPicNum;
  private boolean mShowGuide = true;
  private boolean mShowLbs = true;
  ImageView mUploadButton = null;
  private String mUploadFilePathName = "";
  private String mUploadPhotoFrom = "camera";
  private ProgressDialog progressDialog = null;
  private RefreshLandmarkProxy refreshLandmarkProxy;
  private String seletedLandmark;
  private EditText titleInputView;

  private void addItem(ArrayList<LocalPhotoItem> paramArrayList, int paramInt, String paramString1, String paramString2)
  {
    if (paramArrayList != null)
    {
      LocalPhotoItem localLocalPhotoItem = new LocalPhotoItem();
      localLocalPhotoItem.mId = paramString1;
      localLocalPhotoItem.mPath = paramString2;
      paramArrayList.add(paramInt, localLocalPhotoItem);
    }
  }

  private void addItem(ArrayList<LocalPhotoItem> paramArrayList, String paramString1, String paramString2)
  {
    if (paramArrayList != null)
    {
      LocalPhotoItem localLocalPhotoItem = new LocalPhotoItem();
      localLocalPhotoItem.mId = paramString1;
      localLocalPhotoItem.mPath = paramString2;
      paramArrayList.add(localLocalPhotoItem);
    }
  }

  private void addItem(ArrayList<LocalPhotoItem> paramArrayList, String paramString1, String paramString2, String paramString3)
  {
    if (paramArrayList != null)
    {
      LocalPhotoItem localLocalPhotoItem = new LocalPhotoItem();
      localLocalPhotoItem.mId = paramString1;
      localLocalPhotoItem.mPath = paramString2;
      localLocalPhotoItem.mFrom = paramString3;
      paramArrayList.add(localLocalPhotoItem);
    }
  }

  private void deleteHeadAlbum()
  {
    if (this.mAlbumList == null);
    while (true)
    {
      return;
      for (int i = 0; i < this.mAlbumList.size(); i++)
      {
        if (!((AlbumInfo)this.mAlbumList.get(i)).albumsFeedAlbumid.equals("0"))
          continue;
        this.mAlbumList.remove(i);
        return;
      }
    }
  }

  private void dismissAlbumDlg()
  {
    if (this.mDlgChooseAlbum != null)
    {
      if (this.mDlgChooseAlbum.isShowing())
        this.mDlgChooseAlbum.dismiss();
      this.mDlgChooseAlbum = null;
    }
  }

  private void doCancelUploadPhoto()
  {
    DialogUtil.showMsgDlg(getActivity(), 0, 2131427451, 2131427382, 2131427383, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        KXLog.d("FRAGMENT", "AAAAAAAAAAAAAAAAAAA");
        UploadPhotoFragment.this.finish();
      }
    }
    , null);
  }

  private void doUploadPhoto()
  {
    String str1 = "";
    String str2 = "";
    String str3 = "";
    if ((this.mNeedLocation) && (!TextUtils.isEmpty(this.seletedLandmark)))
    {
      str1 = this.seletedLandmark;
      Location localLocation = this.refreshLandmarkProxy.getLocation();
      str2 = String.valueOf(localLocation.getLatitude());
      str3 = String.valueOf(localLocation.getLongitude());
    }
    if ((this.list.size() <= 0) || (!super.checkNetworkAndHint(true)))
      return;
    if ((str1.equals(getString(2131427721))) || (str1.equals("位置加载中")))
      str1 = "";
    String str4 = this.mAlbumChosen.getText().toString();
    if (!TextUtils.isEmpty(str4))
      Setting.getInstance().setPhotoAlbumName(str4);
    hideKeyboard();
    Iterator localIterator = this.list.iterator();
    if (!localIterator.hasNext())
    {
      this.list.clear();
      PhotoSelectModel.getInstance().clearAllPhotos();
      Toast.makeText(getActivity().getApplication(), 2131428308, 0).show();
      this.mUploadFilePathName = null;
      showUploadPhotoOptions();
      finish();
      return;
    }
    LocalPhotoItem localLocalPhotoItem = (LocalPhotoItem)localIterator.next();
    PhotoUploadTask localPhotoUploadTask = new PhotoUploadTask(getActivity().getApplicationContext());
    if ((this.fileTitle.get(localLocalPhotoItem.mPath) != null) && (((String)this.fileTitle.get(localLocalPhotoItem.mPath)).length() > 0));
    for (this.mFileTitle = ((String)this.fileTitle.get(localLocalPhotoItem.mPath)); ; this.mFileTitle = getString(2131427648))
    {
      localPhotoUploadTask.initPhotoUploadTask(this.mFileTitle, localLocalPhotoItem.mPath, this.mAlbumId, str2, str3, str1, str4);
      UploadTaskListEngine.getInstance().addUploadTask(localPhotoUploadTask);
      break;
    }
  }

  private void initAlbumListener()
  {
    10 local10 = new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if ((UploadPhotoFragment.this.mAlbumList != null) && (UploadPhotoFragment.this.mAlbumList.size() > 0))
          UploadPhotoFragment.this.showAlbumBuildingList();
        do
          return;
        while (UploadPhotoFragment.this.mGetAlbumlistTask != null);
        UploadPhotoFragment.this.progressDialog = ProgressDialog.show(UploadPhotoFragment.this.mContext, "", "正在获取专辑信息", true, true, new DialogInterface.OnCancelListener()
        {
          public void onCancel(DialogInterface paramDialogInterface)
          {
            if ((UploadPhotoFragment.this.mGetAlbumlistTask != null) && (UploadPhotoFragment.this.mGetAlbumlistTask.getStatus() != AsyncTask.Status.FINISHED))
            {
              UploadPhotoFragment.this.mGetAlbumlistTask.cancel(true);
              UploadPhotoFragment.this.mGetAlbumlistTask = null;
            }
          }
        });
        UploadPhotoFragment.this.mGetAlbumlistTask = new UploadPhotoFragment.GetAlbumlistTask(UploadPhotoFragment.this, null);
        UploadPhotoFragment.this.mGetAlbumlistTask.execute(new Void[0]);
      }
    };
    this.mAlbumChosen.setOnClickListener(local10);
  }

  private void initLocationRelated()
  {
    if (this.mNeedLocation)
    {
      this.refreshLandmarkProxy = new RefreshLandmarkProxy(getActivity(), new RefreshLandmarkProxy.IRefreshLandmarkListCallback()
      {
        public void onCancleRefreshLandmarks()
        {
          UploadPhotoFragment.this.setAddLocationButtonState(1);
        }

        public void onGetLandmarksFailed()
        {
          UploadPhotoFragment.this.setAddLocationButtonState(1);
          UploadPhotoFragment.this.setLandmarkDesc(1, null);
        }

        public void onRefreshLandmarks()
        {
          UploadPhotoFragment.this.setAddLocationButtonState(0);
          UploadPhotoFragment.this.setLandmarkDesc(0, null);
        }

        public void onSelectLandmark(String paramString)
        {
          UploadPhotoFragment.this.setAddLocationButtonState(2);
          UploadPhotoFragment.this.setLandmarkDesc(2, paramString);
        }
      });
      if (this.refreshLandmarkProxy.getIncludeLocationPreference())
        this.refreshLandmarkProxy.refreshLandmarks();
    }
  }

  private void initViewHandles()
  {
    this.mKXScrollView = ((KXScrollView)findViewById(2131363386));
    this.mKXScrollView.setSizeChangeListener(new KXScrollView.SizeChangeListener()
    {
      public void onSizeChanged(int paramInt)
      {
        if (paramInt == 2)
        {
          UploadPhotoFragment.this.mKXScrollView.scrollTo(0, 1000);
          return;
        }
        UploadPhotoFragment.this.mKXScrollView.scrollTo(0, 0);
      }
    });
    this.mPicNum = ((TextView)findViewById(2131363387));
    this.mLocationDes = ((TextView)findViewById(2131363393));
    this.mAlbumChosen = ((TextView)findViewById(2131363398));
    View localView1 = findViewById(2131363392);
    int i;
    View localView2;
    int j;
    if (this.mShowLbs)
    {
      i = 0;
      localView1.setVisibility(i);
      if (this.mAlbumName != null)
        this.mAlbumChosen.setText(this.mAlbumName);
      this.mPicBtn = findViewById(2131363396);
      this.mPicBtn.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          Intent localIntent = new Intent();
          localIntent.setClass(UploadPhotoFragment.this.getActivity(), PhotoSelectFragment.class);
          localIntent.putExtra("continue", true);
          AnimationUtil.startFragmentForResult(UploadPhotoFragment.this, localIntent, 1011, 3);
        }
      });
      this.mLocationBtn = findViewById(2131363397);
      this.mLocationBtn.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (UploadPhotoFragment.this.mLocationDes == null)
            return;
          View localView = UploadPhotoFragment.this.findViewById(2131363392);
          if (localView.getVisibility() == 8)
          {
            UploadPhotoFragment.this.mShowLbs = true;
            localView.setVisibility(0);
            if ((UploadPhotoFragment.this.refreshLandmarkProxy.isLocationValid()) && (UploadPhotoFragment.this.refreshLandmarkProxy.isLandmarkListExist()))
            {
              UploadPhotoFragment.this.refreshLandmarkProxy.showLandmarks(UploadPhotoFragment.this.getActivity());
              return;
            }
            UploadPhotoFragment.this.refreshLandmarkProxy.refreshLandmarks();
            return;
          }
          UploadPhotoFragment.this.mShowLbs = false;
          localView.setVisibility(8);
          UploadPhotoFragment.this.setAddLocationButtonState(1);
          UploadPhotoFragment.this.mLocationDes.setText("");
          UploadPhotoFragment.this.seletedLandmark = "";
        }
      });
      this.mGuideView = findViewById(2131363400);
      localView2 = this.mGuideView;
      boolean bool = this.mShowGuide;
      j = 0;
      if (!bool)
        break label221;
    }
    while (true)
    {
      localView2.setVisibility(j);
      this.mGuideView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          UploadPhotoFragment.this.mGuideView.setVisibility(8);
        }
      });
      return;
      i = 8;
      break;
      label221: j = 8;
    }
  }

  private void refreshView()
  {
    ArrayList localArrayList = PhotoSelectModel.getInstance().getSelectPhotoList();
    if (localArrayList.size() == 0)
    {
      finish();
      return;
    }
    this.list.clear();
    this.list.addAll(localArrayList);
    this.adapter.notifyDataSetChanged();
    updateNumText();
  }

  private void saveLocationPreference()
  {
    if (this.refreshLandmarkProxy != null)
      this.refreshLandmarkProxy.saveIncludeLocationPreference(true);
  }

  private void setAddLocationButtonState(int paramInt)
  {
    if ((getActivity() == null) || (getView() == null))
      return;
    String str = getString(2131427668);
    switch (paramInt)
    {
    default:
      return;
    case 0:
      this.mLocationDes.setText(str);
      this.mLocationDes.setTextColor(getResources().getColor(2130839405));
      this.mLocationDes.setEnabled(false);
      return;
    case 1:
      this.mLocationDes.setText(2131427721);
      this.mLocationDes.setTextColor(getResources().getColor(2130839405));
      this.mLocationDes.setEnabled(true);
      return;
    case 2:
    }
    this.mLocationDes.setText(str);
    this.mLocationDes.setTextColor(getResources().getColor(2130839405));
    this.mLocationDes.setEnabled(true);
  }

  private void setButtonHandlers()
  {
    this.mLocationDes.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (UploadPhotoFragment.this.getActivity() == null)
          return;
        if ((UploadPhotoFragment.this.refreshLandmarkProxy.isLocationValid()) && (UploadPhotoFragment.this.refreshLandmarkProxy.isLandmarkListExist()))
        {
          UploadPhotoFragment.this.refreshLandmarkProxy.showLandmarks(UploadPhotoFragment.this.getActivity());
          return;
        }
        UploadPhotoFragment.this.refreshLandmarkProxy.refreshLandmarks();
      }
    });
    ((Button)findViewById(2131363394)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        UploadPhotoFragment.this.setAddLocationButtonState(1);
        UploadPhotoFragment.this.mLocationDes.setText("");
        UploadPhotoFragment.this.seletedLandmark = "";
        UploadPhotoFragment.this.findViewById(2131363392).setVisibility(8);
      }
    });
  }

  private void setLandmarkDesc(int paramInt, String paramString)
  {
    switch (paramInt)
    {
    default:
      return;
    case 0:
      this.mLocationDes.setText("位置加载中");
      this.mLocationDes.setTextColor(getResources().getColor(2130839405));
      this.seletedLandmark = null;
      return;
    case 1:
      this.mLocationDes.setText(2131427721);
      this.seletedLandmark = null;
      this.mLocationDes.setTextColor(getResources().getColor(2130839405));
      return;
    case 2:
    }
    this.mLocationDes.setText(paramString);
    this.mLocationDes.setTextColor(getResources().getColor(2130839393));
    this.seletedLandmark = paramString;
  }

  private void showAlbumBuildingList()
  {
    String[] arrayOfString;
    int i;
    Iterator localIterator;
    if ((this.mAlbumList != null) && (this.mAlbumList.size() > 0))
    {
      dismissAlbumDlg();
      arrayOfString = new String[this.mAlbumList.size()];
      i = 0;
      localIterator = this.mAlbumList.iterator();
      if (!localIterator.hasNext())
        DialogUtil.showValueListDlg(getActivity(), 2131427811, arrayOfString, this.fancyDialogAdapter, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramDialogInterface, int paramInt)
          {
            UploadPhotoFragment.this.fancyDialogAdapter.setSelectedIndex(paramInt);
            String str = UploadPhotoFragment.this.mContext.getResources().getString(2131427809);
            UploadPhotoFragment.this.mAlbumId = "";
            try
            {
              str = ((AlbumInfo)UploadPhotoFragment.this.mAlbumList.get(paramInt)).albumsFeedAlbumtitle;
              UploadPhotoFragment.this.mAlbumId = ((AlbumInfo)UploadPhotoFragment.this.mAlbumList.get(paramInt)).albumsFeedAlbumid;
              UploadPhotoFragment.this.mAlbumChosen.setText(str);
              return;
            }
            catch (Exception localException)
            {
              while (true)
                localException.printStackTrace();
            }
          }
        }
        , true, true);
    }
    else
    {
      return;
    }
    AlbumInfo localAlbumInfo = (AlbumInfo)localIterator.next();
    int j = i + 1;
    if (localAlbumInfo.albumsFeedAlbumtitle.length() <= 10);
    for (String str = localAlbumInfo.albumsFeedAlbumtitle; ; str = localAlbumInfo.albumsFeedAlbumtitle.substring(0, 10) + "...")
    {
      arrayOfString[i] = str;
      i = j;
      break;
    }
  }

  private void showUploadPhotoOptions()
  {
    if (UploadTaskListEngine.getInstance().getWaitingTaskCount() < 20)
    {
      setResult(-1);
      finish();
      return;
    }
    DialogUtil.showSelectListDlg(getActivity(), 2131427637, getResources().getStringArray(2131492881), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if (UploadPhotoFragment.this.getActivity() != null)
        {
          Intent localIntent = new Intent(UploadPhotoFragment.this.getActivity(), UploadTaskListFragment.class);
          UploadPhotoFragment.this.startFragment(localIntent, true, 1);
        }
        UploadPhotoFragment.this.finish();
      }
    }
    , new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        UploadPhotoFragment.this.setResult(4);
        UploadPhotoFragment.this.finish();
      }
    });
  }

  private void updateNumText()
  {
    if ((this.list != null) && (this.list.size() > 0))
    {
      String str = "*/#".replace("*", 1 + this.mCurIndex).replace("#", this.list.size());
      this.mPicNum.setText(str);
      return;
    }
    this.mPicNum.setVisibility(4);
  }

  public void finish()
  {
    if (this.mFromOtherApp)
    {
      getActivity().finish();
      return;
    }
    super.finish();
  }

  public boolean getShowGuide(Context paramContext)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    try
    {
      boolean bool = localSharedPreferences.getBoolean("photo_upload_guide_" + User.getInstance().getUID(), true);
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  protected void handleActivityUploadPhotoOptions(int paramInt)
  {
    if (paramInt == 0)
      if (this.mUploadPhotoFrom.equals("camera"))
        takePictureWithCamera();
    do
    {
      return;
      finish();
      return;
      if (paramInt != 1)
        continue;
      finish();
      IntentUtil.showWebPage(getActivity(), this, this.mEventData.getUrl(), getString(2131427974), this.mEventData, 200);
      return;
    }
    while (paramInt != 2);
    finish();
  }

  public void handleUploadPhotoOptions(int paramInt)
  {
    if ((paramInt == 0) || (paramInt == -1))
    {
      if (this.mUploadPhotoFrom.equals("camera"))
        takePictureWithCamera();
    }
    else
      return;
    finish();
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    int i;
    if (paramInt2 == -1)
      if (paramInt1 == 201)
      {
        i = 0;
        if (paramIntent == null);
      }
    do
    {
      do
      {
        File localFile;
        do
        {
          try
          {
            Bundle localBundle3 = paramIntent.getExtras();
            i = 0;
            if (localBundle3 != null)
            {
              Object localObject1 = localBundle3.get("data");
              i = 0;
              if (localObject1 != null)
                i = 1;
            }
            if (i != 0)
              try
              {
                Cursor localCursor1 = getActivity().managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
                if (localCursor1.moveToLast())
                {
                  String str2 = localCursor1.getString(localCursor1.getColumnIndexOrThrow("_data"));
                  addItem(this.list, "", str2);
                  if (localCursor1 != null)
                    localCursor1.close();
                  ((EditText)findViewById(2131363390)).setText("");
                  Bundle localBundle2 = new Bundle();
                  localBundle2.putString("filePathName", ((LocalPhotoItem)this.list.get(0)).mPath);
                  localBundle2.putString("fileFrom", this.mUploadPhotoFrom);
                  IntentUtil.launchEditPhotoForResult(getActivity(), this, 204, localBundle2);
                }
                return;
              }
              catch (IllegalArgumentException localIllegalArgumentException)
              {
                KXLog.e("UploadPhotoActivity", "onActivityResult", localIllegalArgumentException);
                return;
              }
          }
          catch (OutOfMemoryError localOutOfMemoryError)
          {
            Toast.makeText(getActivity(), getResources().getString(2131427724), 0).show();
            finish();
            return;
          }
          localFile = new File(Environment.getExternalStorageDirectory() + "/kaixin001", "kx_upload_tmp.jpg");
        }
        while (!localFile.exists());
        String str1 = localFile.getAbsolutePath();
        addItem(this.list, "", str1);
        ((EditText)findViewById(2131363390)).setText("");
        Bundle localBundle1 = new Bundle();
        localBundle1.putString("filePathName", ((LocalPhotoItem)this.list.get(0)).mPath);
        localBundle1.putString("fileFrom", this.mUploadPhotoFrom);
        IntentUtil.launchEditPhotoForResult(getActivity(), this, 203, localBundle1);
        return;
        if (paramInt1 == 202)
        {
          Object localObject2 = "";
          Uri localUri = paramIntent.getData();
          String[] arrayOfString;
          Cursor localCursor2;
          if (localUri.getScheme().equals("content"))
          {
            arrayOfString = new String[] { "_data" };
            localCursor2 = null;
          }
          while (true)
          {
            try
            {
              localCursor2 = getActivity().getContentResolver().query(localUri, arrayOfString, null, null, null);
              localCursor2.moveToFirst();
              String str7 = localCursor2.getString(localCursor2.getColumnIndex(arrayOfString[0]));
              localObject2 = str7;
              if (localCursor2 == null)
                continue;
              localCursor2.close();
              addItem(this.list, "", (String)localObject2);
              Bundle localBundle5 = new Bundle();
              localBundle5.putString("filePathName", ((LocalPhotoItem)this.list.get(0)).mPath);
              localBundle5.putString("fileFrom", this.mUploadPhotoFrom);
              IntentUtil.launchEditPhotoForResult(getActivity(), this, 203, localBundle5);
              ((EditText)findViewById(2131363390)).setText("");
              return;
            }
            finally
            {
              if (localCursor2 == null)
                continue;
              localCursor2.close();
            }
            if (localUri.getScheme().equals("file"))
            {
              localObject2 = localUri.getPath();
              continue;
            }
            KXLog.e("UploadPhotoActivity", "UNKNOW UPLOAD SCHEME:" + localUri.getScheme());
          }
        }
        if (paramInt1 == 203)
        {
          if (!this.mNeedLocation)
            this.mLocationDes.setVisibility(8);
          Bitmap localBitmap1 = EditPictureModel.getBimap();
          KXExifInterface localKXExifInterface1 = ImageCache.getExifInfo(((LocalPhotoItem)this.list.get(this.effectPosition)).mPath);
          String str3 = new File(((LocalPhotoItem)this.list.get(this.effectPosition)).mPath).getName();
          this.mUploadFilePathName = ImageCache.saveBitmapToFile(getActivity(), localBitmap1, localKXExifInterface1, str3);
          this.list.remove(this.effectPosition);
          addItem(this.list, this.effectPosition, "", this.mUploadFilePathName);
          this.adapter.notifyDataSetChanged();
          initLocationRelated();
          return;
        }
        if (paramInt1 == 200)
        {
          finish();
          return;
        }
        if (paramInt1 != 1011)
          continue;
        if (paramIntent != null)
        {
          Bundle localBundle4 = paramIntent.getExtras();
          if ((localBundle4 != null) && (!TextUtils.isEmpty(localBundle4.getString("from"))))
          {
            this.mFilePathName = EditPictureModel.getBitmapPath();
            Bitmap localBitmap3 = EditPictureModel.getBimap();
            KXExifInterface localKXExifInterface3 = ImageCache.getExifInfo(this.mFilePathName);
            String str6 = new File(this.mFilePathName).getName();
            this.mUploadFilePathName = ImageCache.saveBitmapToFile(getActivity(), localBitmap3, localKXExifInterface3, str6);
            addItem(this.list, "", this.mFilePathName);
            PhotoSelectModel.getInstance().addPhoto(getActivity(), this.mFilePathName);
          }
        }
        refreshView();
        return;
      }
      while (paramInt1 != 206);
      this.mFilePathName = EditPictureModel.getBitmapPath();
      Bitmap localBitmap2 = EditPictureModel.getBimap();
      KXExifInterface localKXExifInterface2 = ImageCache.getExifInfo(this.mFilePathName);
      String str4 = new File(this.mFilePathName).getName();
      this.mUploadFilePathName = ImageCache.saveBitmapToFile(getActivity(), localBitmap2, localKXExifInterface2, str4);
      ImageCache.getInstance().RemoveBitmapToHardCache(this.mUploadFilePathName);
      repacePhotoPath(this.list, this.mFilePathName, this.mUploadFilePathName);
      String str5 = (String)this.fileTitle.get(this.mFilePathName);
      if (!TextUtils.isEmpty(str5))
        this.fileTitle.put(this.mUploadFilePathName, str5);
      PhotoSelectModel.getInstance().repacePhotoPath(this.mFilePathName, this.mUploadFilePathName);
      this.adapter.notifyDataSetChanged();
      return;
    }
    while (paramInt1 != 204);
    finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
    do
    {
      return;
      Bundle localBundle = getArguments();
      if (localBundle == null)
        continue;
      this.mFilePath = localBundle.getString("filePathName");
      this.mFileFrom = localBundle.getString("fileFrom");
    }
    while (TextUtils.isEmpty(this.mFilePath));
    addItem(this.list, "", this.mFilePath);
    PhotoSelectModel.getInstance().addPhoto("", this.mFilePath);
    this.mFromOtherApp = true;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903281, paramViewGroup, false);
  }

  public void onDestroy()
  {
    dismissAlbumDlg();
    if (this.mAlbumList != null)
      this.mAlbumList.clear();
    cancelTask(this.mGetAlbumlistTask);
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      doCancelUploadPhoto();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public void onPause()
  {
    saveLocationPreference();
    super.onPause();
  }

  public void onResume()
  {
    if (this.refreshLandmarkProxy != null)
      this.refreshLandmarkProxy.refreshLocationProxy.reworkOnResume();
    refreshView();
    super.onResume();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mShowGuide = getShowGuide(getActivity());
    setShowGuide(getActivity(), false);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mEventData = ((EventModel.EventData)localBundle.getSerializable("event_activity"));
      if (this.mEventData != null)
        this.mActivityName = this.mEventData.getKeyWord();
    }
    boolean bool = getActivity().getApplicationContext().getSharedPreferences("from_webpage", 0).getBoolean("fromwebpage", false);
    label150: Iterator localIterator;
    label370: String[] arrayOfString;
    int i;
    if (this.mActivityName == null)
    {
      this.mAlbumName = Setting.getInstance().getPhotoAlbumName();
      if ((TextUtils.isEmpty(this.mAlbumName)) && (bool))
        this.mAlbumName = getString(2131427810);
    }
    else
    {
      if (!bool)
        break label704;
      this.mAlbumName = getString(2131427810);
      this.mContext = getActivity();
      this.mFilePathName = EditPictureModel.getBitmapPath();
      this.mUploadPhotoFrom = EditPictureModel.getPicFrom();
      this.cancelButton = ((ImageView)findViewById(2131362914));
      ImageView localImageView1 = this.cancelButton;
      1 local1 = new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          UploadPhotoFragment.this.doCancelUploadPhoto();
        }
      };
      localImageView1.setOnClickListener(local1);
      ((ImageView)findViewById(2131362919)).setVisibility(8);
      TextView localTextView = (TextView)findViewById(2131362920);
      localTextView.setText(2131428563);
      localTextView.setVisibility(0);
      this.mUploadButton = ((ImageView)findViewById(2131362928));
      this.mUploadButton.setImageResource(2130839272);
      (int)(130.0F * getResources().getDisplayMetrics().density);
      ImageView localImageView2 = this.mUploadButton;
      2 local2 = new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          UploadPhotoFragment.this.doUploadPhoto();
        }
      };
      localImageView2.setOnClickListener(local2);
      initViewHandles();
      initAlbumListener();
      if (!this.mNeedLocation)
        this.mLocationDes.setVisibility(8);
      setButtonHandlers();
      initLocationRelated();
      if (localBundle != null)
      {
        ArrayList localArrayList = localBundle.getStringArrayList("selection");
        if (localArrayList != null)
        {
          localIterator = localArrayList.iterator();
          if (localIterator.hasNext())
            break label715;
        }
        String str2 = localBundle.getString("selection");
        if (!TextUtils.isEmpty(str2))
        {
          arrayOfString = str2.split(";");
          if ((arrayOfString != null) && (arrayOfString.length > 0))
          {
            i = arrayOfString.length;
            this.list.clear();
          }
        }
      }
    }
    for (int j = 0; ; j += 3)
    {
      if (j >= i)
      {
        if (this.list.size() == 0)
        {
          Bitmap localBitmap = EditPictureModel.getBimap();
          KXExifInterface localKXExifInterface = ImageCache.getExifInfo(this.mFilePathName);
          String str1 = new File(this.mFilePathName).getName();
          this.mUploadFilePathName = ImageCache.saveBitmapToFile(getActivity(), localBitmap, localKXExifInterface, str1);
          addItem(this.list, "", this.mUploadFilePathName);
          PhotoSelectModel.getInstance().addPhoto(getActivity(), this.mFilePathName);
          PhotoSelectModel.getInstance().repacePhotoPath(this.mFilePathName, this.mUploadFilePathName);
        }
        updateNumText();
        this.titleInputView = ((EditText)findViewById(2131363390));
        EditText localEditText = this.titleInputView;
        3 local3 = new TextWatcher()
        {
          public void afterTextChanged(Editable paramEditable)
          {
            if (UploadPhotoFragment.this.curView != null)
            {
              UploadPhotoFragment.ViewHolder localViewHolder = (UploadPhotoFragment.ViewHolder)UploadPhotoFragment.this.curView.getTag();
              UploadPhotoFragment.this.fileTitle.put(localViewHolder.item.mPath, UploadPhotoFragment.this.titleInputView.getText().toString());
            }
          }

          public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
          {
          }

          public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
          {
          }
        };
        localEditText.addTextChangedListener(local3);
        this.gallery = ((KXUpgradeGallery)findViewById(2131363388));
        GalleryImageAdapter localGalleryImageAdapter = new GalleryImageAdapter(null);
        this.adapter = localGalleryImageAdapter;
        this.gallery.setAdapter(this.adapter);
        KXUpgradeGallery localKXUpgradeGallery1 = this.gallery;
        4 local4 = new AdapterView.OnItemClickListener()
        {
          public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
          {
            UploadPhotoFragment.this.effectPosition = paramInt;
            UploadPhotoFragment.ViewHolder localViewHolder = (UploadPhotoFragment.ViewHolder)paramView.getTag();
            if ((localViewHolder != null) && (localViewHolder.notifyView.getVisibility() != 0))
              return;
            Bundle localBundle = new Bundle();
            localBundle.putString("filePathName", localViewHolder.item.mPath);
            localBundle.putString("fileFrom", UploadPhotoFragment.this.mUploadPhotoFrom);
            UploadPhotoFragment.this.hideKeyboard();
            Intent localIntent = new Intent(UploadPhotoFragment.this.getActivity(), IFEditPhotoFragment.class);
            localIntent.putExtras(localBundle);
            AnimationUtil.startFragmentForResult(UploadPhotoFragment.this, localIntent, 206, 3);
          }
        };
        localKXUpgradeGallery1.setOnItemClickListener(local4);
        KXUpgradeGallery localKXUpgradeGallery2 = this.gallery;
        5 local5 = new AdapterView.OnItemSelectedListener()
        {
          public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
          {
            if (UploadPhotoFragment.this.lastView != null)
              ((UploadPhotoFragment.ViewHolder)UploadPhotoFragment.this.lastView.getTag()).delete.setVisibility(8);
            if (paramView == null)
              return;
            UploadPhotoFragment.this.lastView = paramView;
            UploadPhotoFragment.ViewHolder localViewHolder = (UploadPhotoFragment.ViewHolder)paramView.getTag();
            if (UploadPhotoFragment.this.list.size() > 1)
              localViewHolder.delete.setVisibility(0);
            UploadPhotoFragment.this.curView = paramView;
            if ((localViewHolder != null) && (!TextUtils.isEmpty((CharSequence)UploadPhotoFragment.this.fileTitle.get(localViewHolder.item.mPath))))
              UploadPhotoFragment.this.titleInputView.setText((CharSequence)UploadPhotoFragment.this.fileTitle.get(localViewHolder.item.mPath));
            while (true)
            {
              UploadPhotoFragment.this.adapter.setRemoveItem();
              UploadPhotoFragment.this.mCurIndex = (int)paramLong;
              UploadPhotoFragment.this.updateNumText();
              if (paramLong <= 0L)
                break;
              UploadPhotoFragment.this.enableSlideBack(false);
              return;
              UploadPhotoFragment.this.titleInputView.setText("");
            }
            UploadPhotoFragment.this.enableSlideBack(true);
          }

          public void onNothingSelected(AdapterView<?> paramAdapterView)
          {
          }
        };
        localKXUpgradeGallery2.setOnItemSelectedListener(local5);
        MessageHandlerHolder.getInstance().addHandler(this.mHandler);
        return;
        if (!TextUtils.isEmpty(this.mAlbumName))
          break;
        this.mAlbumName = getString(2131427809);
        break;
        label704: this.mAlbumName = this.mActivityName;
        break label150;
        label715: String str3 = (String)localIterator.next();
        addItem(this.list, "", str3);
        break label370;
      }
      addItem(this.list, arrayOfString[j], arrayOfString[(j + 1)], arrayOfString[(j + 2)]);
    }
  }

  public void repacePhotoPath(ArrayList<LocalPhotoItem> paramArrayList, String paramString1, String paramString2)
  {
    Iterator localIterator = paramArrayList.iterator();
    LocalPhotoItem localLocalPhotoItem;
    do
    {
      if (!localIterator.hasNext())
        return;
      localLocalPhotoItem = (LocalPhotoItem)localIterator.next();
    }
    while (!localLocalPhotoItem.mPath.equals(paramString1));
    localLocalPhotoItem.mPath = paramString2;
  }

  public void requestFinish()
  {
  }

  public void selectPictureFromGallery()
  {
    Intent localIntent = new Intent();
    localIntent.setType("image/*");
    localIntent.setAction("android.intent.action.GET_CONTENT");
    startActivityForResult(Intent.createChooser(localIntent, getResources().getText(2131427328)), 202);
  }

  public void setShowGuide(Context paramContext, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
    localEditor.putBoolean("photo_upload_guide_" + User.getInstance().getUID(), paramBoolean);
    localEditor.commit();
  }

  public boolean takePictureWithCamera()
  {
    File localFile = new File(Environment.getExternalStorageDirectory() + "/kaixin001", "kx_upload_tmp.jpg");
    if (!FileUtil.makeEmptyFile(localFile))
    {
      Toast.makeText(getActivity(), 2131427883, 0).show();
      return false;
    }
    Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
    localIntent.putExtra("output", Uri.fromFile(localFile));
    startActivityForResult(localIntent, 201);
    return true;
  }

  private class AlbumInfoCheckBoxListAdapter extends CheckBoxListAdapter<AlbumInfo>
  {
    AlbumInfoCheckBoxListAdapter(Context arg2)
    {
      super();
    }

    public int getCount()
    {
      if (UploadPhotoFragment.this.mAlbumList != null)
        return UploadPhotoFragment.this.mAlbumList.size();
      return 0;
    }

    public Object getItem(int paramInt)
    {
      return ((AlbumInfo)UploadPhotoFragment.this.mAlbumList.get(paramInt)).albumsFeedAlbumtitle;
    }
  }

  private class AsyncImageLoader
  {
    private String imgSrc;
    private ImageView iv;

    public AsyncImageLoader(ImageView paramString, String arg3)
    {
      this.iv = paramString;
      Object localObject;
      this.imgSrc = localObject;
    }

    public void load()
    {
      new DownloadTask(null).execute(new String[0]);
    }

    private class DownloadTask extends AsyncTask<String, Void, Bitmap>
    {
      private DownloadTask()
      {
      }

      protected Bitmap doInBackground(String[] paramArrayOfString)
      {
        Bitmap localBitmap = ImageCache.getInstance().loadMemoryCacheImage(UploadPhotoFragment.AsyncImageLoader.this.imgSrc);
        if (localBitmap == null)
        {
          localBitmap = FileUtil.loadBitmapFromFile(UploadPhotoFragment.AsyncImageLoader.this.imgSrc, 400, 400);
          if (localBitmap != null)
            ImageCache.getInstance().addBitmapToHardCache(UploadPhotoFragment.AsyncImageLoader.this.imgSrc, localBitmap);
        }
        return localBitmap;
      }

      protected void onPostExecute(Bitmap paramBitmap)
      {
        UploadPhotoFragment.AsyncImageLoader.this.iv.setImageBitmap(paramBitmap);
      }
    }
  }

  private class GalleryImageAdapter extends BaseAdapter
  {
    private int removeItem = -1;

    private GalleryImageAdapter()
    {
    }

    public int getCount()
    {
      return UploadPhotoFragment.this.list.size();
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
      UploadPhotoFragment.ViewHolder localViewHolder;
      if (paramView == null)
      {
        paramView = ((LayoutInflater)UploadPhotoFragment.this.getActivity().getSystemService("layout_inflater")).inflate(2130903385, null);
        localViewHolder = new UploadPhotoFragment.ViewHolder(UploadPhotoFragment.this, null);
        localViewHolder.iv = ((KXFrameImageView)paramView.findViewById(2131363842));
        localViewHolder.iv.setFrameNinePatchResId(0);
        localViewHolder.iv.setFrameResId(2130838245);
        localViewHolder.delete = ((ImageView)paramView.findViewById(2131363843));
        localViewHolder.notifyView = paramView.findViewById(2131363844);
        paramView.setTag(localViewHolder);
      }
      while (true)
      {
        if ((UploadPhotoFragment.this.curView == null) && (paramInt == 0))
          UploadPhotoFragment.this.curView = paramView;
        new LocalPhotoItem();
        if (UploadPhotoFragment.this.list.size() > paramInt)
          break;
        return paramView;
        localViewHolder = (UploadPhotoFragment.ViewHolder)paramView.getTag();
      }
      LocalPhotoItem localLocalPhotoItem = (LocalPhotoItem)UploadPhotoFragment.this.list.get(paramInt);
      String str = localLocalPhotoItem.mPath;
      new UploadPhotoFragment.AsyncImageLoader(UploadPhotoFragment.this, localViewHolder.iv, str).load();
      localViewHolder.item = localLocalPhotoItem;
      if (UploadPhotoFragment.this.list.size() > 1)
        localViewHolder.delete.setVisibility(0);
      localViewHolder.delete.setOnClickListener(new View.OnClickListener(paramInt)
      {
        public void onClick(View paramView)
        {
          UploadPhotoFragment.GalleryImageAdapter.this.removeItem = this.val$_position;
          LocalPhotoItem localLocalPhotoItem = (LocalPhotoItem)UploadPhotoFragment.this.list.get(this.val$_position);
          UploadPhotoFragment.this.list.remove(this.val$_position);
          PhotoSelectModel.getInstance().removePhotoById(localLocalPhotoItem.mId);
          UploadPhotoFragment.this.adapter.notifyDataSetChanged();
          UploadPhotoFragment.this.refreshView();
        }
      });
      View localView = localViewHolder.notifyView;
      if (1 != 0);
      for (int i = 0; ; i = 8)
      {
        localView.setVisibility(i);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        UploadPhotoFragment.this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        paramView.setLayoutParams(new Gallery.LayoutParams(70 * localDisplayMetrics.widthPixels / 100, -2));
        return paramView;
      }
    }

    public void setRemoveItem()
    {
      this.removeItem = -1;
    }
  }

  private class GetAlbumlistTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    private GetAlbumlistTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        Boolean localBoolean = Boolean.valueOf(AlbumListEngine.getInstance().getAlbumPhotoList(UploadPhotoFragment.this.getActivity().getApplicationContext(), User.getInstance().getUID()));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if (paramBoolean == null)
      {
        UploadPhotoFragment.this.finish();
        return;
      }
      if (UploadPhotoFragment.this.progressDialog != null)
        UploadPhotoFragment.this.progressDialog.dismiss();
      if (paramBoolean.booleanValue())
      {
        UploadPhotoFragment.this.mAlbumList = null;
        UploadPhotoFragment.this.mAlbumList = ((ArrayList)AlbumListModel.getMyAlbumList().getAlbumslist().clone());
        UploadPhotoFragment.this.deleteHeadAlbum();
        int i;
        Iterator localIterator;
        if (UploadPhotoFragment.this.mAlbumName != null)
        {
          i = 0;
          localIterator = UploadPhotoFragment.this.mAlbumList.iterator();
        }
        while (true)
        {
          if (!localIterator.hasNext());
          while (true)
          {
            if (i >= UploadPhotoFragment.this.mAlbumList.size())
            {
              AlbumInfo localAlbumInfo = new AlbumInfo();
              localAlbumInfo.albumsFeedAlbumtitle = UploadPhotoFragment.this.mAlbumName;
              UploadPhotoFragment.this.mAlbumList.add(0, localAlbumInfo);
              UploadPhotoFragment.this.mAlbumListAdapter.setSelectIndex(0);
            }
            if ((UploadPhotoFragment.this.mAlbumList == null) || (UploadPhotoFragment.this.mAlbumList.size() <= 0))
              break label240;
            UploadPhotoFragment.this.showAlbumBuildingList();
            return;
            if (!((AlbumInfo)localIterator.next()).albumsFeedAlbumtitle.equals(UploadPhotoFragment.this.mAlbumName))
              break;
            UploadPhotoFragment.this.mAlbumListAdapter.setSelectIndex(i);
          }
          i++;
        }
        label240: Toast.makeText(UploadPhotoFragment.this.getActivity(), 2131427840, 0).show();
        return;
      }
      UploadPhotoFragment.this.mGetAlbumlistTask = null;
      Toast.makeText(UploadPhotoFragment.this.getActivity(), 2131427741, 0).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class ViewHolder
  {
    ImageView delete;
    LocalPhotoItem item;
    KXFrameImageView iv;
    View notifyView;

    private ViewHolder()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.UploadPhotoFragment
 * JD-Core Version:    0.6.0
 */