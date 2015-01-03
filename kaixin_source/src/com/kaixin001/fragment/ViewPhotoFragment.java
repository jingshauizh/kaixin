package com.kaixin001.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.AlbumPhotoEngine;
import com.kaixin001.engine.CheckinListEngine;
import com.kaixin001.engine.CircleAlbumEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.item.CheckInPhotoItem;
import com.kaixin001.item.KaixinPhotoItem;
import com.kaixin001.item.PoiPhotoesItem;
import com.kaixin001.model.AlbumPhotoModel;
import com.kaixin001.model.CheckInInfoModel;
import com.kaixin001.model.CircleAlbumModel;
import com.kaixin001.model.KaixinModelTemplate;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownloader;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.KXImageView;
import com.kaixin001.view.KXSliderLayout2;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ViewPhotoFragment extends BaseFragment
  implements View.OnClickListener
{
  private static final String AVATAR_ALBUM_ID = "0";
  public static final String IS_FROM_VIEW_ALBUM = "isFromViewAlbum";
  private static final int MSG_HIDE_BUTTONS = 113;
  private int albumType;
  private ImageView btnComment;
  private ImageView btnNext;
  private ImageView btnPrevious;
  private ImageView btnSaveAs;
  private String gid;
  private String gname;
  private ImageCache imageUtil = ImageCache.getInstance();
  private boolean isFromViewAlbumActiviy;
  private int mActivePhotoIndex = 0;
  private AlbumAdapter mAlbumAdapter = new AlbumAdapter(null);
  private String mAlbumId = "";
  private String mAlbumTitle = "";
  TranslateAnimation mBottomHideAnim = null;
  TranslateAnimation mBottomShowAnim = null;
  private long mButtonShowDuration = 3000L;
  private String mCthreadCid = "";
  private String mFuid = "";
  private boolean mGallerySelectionChangedByButton = false;
  GetPhotoListTask mGetPhotoListTask = null;
  private Timer mHideButtonTimer = new Timer();
  private HideButtonsTask mHideButtonsTask = new HideButtonsTask(null);
  private ImageDownloader mImgDownloader = ImageDownloader.getInstance();
  private String mLatestDownloadPhotoUrl = "";
  private long mLoadPhotoDelay = 500L;
  private Timer mLoadPhotoDelayTimer = new Timer();
  private String mPassword = "";
  private ArrayList<KaixinPhotoItem> mPhotoList = new ArrayList();
  private String mReplyContent = "";
  TranslateAnimation mTitleHideAnim = null;
  TranslateAnimation mTitleShowAnim = null;
  private String poiName;

  private void constructAnimations()
  {
    this.mTitleShowAnim = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, -1.0F, 1, 0.0F);
    this.mTitleShowAnim.setDuration(600L);
    this.mTitleHideAnim = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 0.0F, 1, -1.0F);
    this.mTitleHideAnim.setDuration(600L);
    this.mBottomShowAnim = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 0.71F, 1, 0.0F);
    this.mBottomShowAnim.setDuration(600L);
    this.mBottomHideAnim = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 0.0F, 1, 0.71F);
    this.mBottomHideAnim.setDuration(600L);
    this.mBottomHideAnim.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnimation)
      {
        ViewPhotoFragment.this.findViewById(2131363480).setVisibility(8);
      }

      public void onAnimationRepeat(Animation paramAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnimation)
      {
      }
    });
  }

  private void downloadPhotoList(int paramInt1, int paramInt2)
  {
    int i = HttpConnection.checkNetworkAvailable(getActivity().getApplicationContext());
    int j = -1;
    if (i < 0)
      j = 2131427387;
    do
      while (i != 1)
      {
        if (paramInt1 == 0)
          DialogUtil.showKXAlertDialog(getActivity(), j, new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
              ViewPhotoFragment.this.finish();
            }
          });
        return;
        if (i != 0)
          continue;
        j = 2131427388;
      }
    while ((this.mGetPhotoListTask != null) && (!this.mGetPhotoListTask.isCancelled()) && (this.mGetPhotoListTask.getStatus() != AsyncTask.Status.FINISHED));
    showProgressBar(true);
    this.mGetPhotoListTask = new GetPhotoListTask(null);
    Integer[] arrayOfInteger = new Integer[2];
    arrayOfInteger[0] = Integer.valueOf(paramInt1);
    arrayOfInteger[1] = Integer.valueOf(paramInt2);
    this.mGetPhotoListTask.execute(arrayOfInteger);
  }

  private void enableBottomButtons(boolean paramBoolean)
  {
    boolean bool1 = true;
    this.btnPrevious.setEnabled(paramBoolean);
    this.btnNext.setEnabled(paramBoolean);
    this.btnComment.setEnabled(paramBoolean);
    this.btnSaveAs.setEnabled(paramBoolean);
    boolean bool2;
    ImageView localImageView2;
    if (paramBoolean)
    {
      ImageView localImageView1 = this.btnPrevious;
      if (this.mActivePhotoIndex <= 0)
        break label88;
      bool2 = bool1;
      localImageView1.setEnabled(bool2);
      localImageView2 = this.btnNext;
      if (this.mActivePhotoIndex >= -1 + this.mPhotoList.size())
        break label94;
    }
    while (true)
    {
      localImageView2.setEnabled(bool1);
      return;
      label88: bool2 = false;
      break;
      label94: bool1 = false;
    }
  }

  private void handlePhotoOperationOptions(int paramInt)
  {
    switch (paramInt)
    {
    default:
    case 0:
    case 1:
    }
    do
    {
      do
        return;
      while (this.mPhotoList == null);
      try
      {
        String str2 = FileUtil.savePicture(((KaixinPhotoItem)this.mPhotoList.get(this.mActivePhotoIndex)).large);
        if (str2 != null)
        {
          getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(str2))));
          Toast.makeText(getActivity(), getResources().getString(2131427492), 0).show();
          return;
        }
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        Toast.makeText(getActivity(), getResources().getString(2131427494), 0).show();
        return;
      }
      Toast.makeText(getActivity(), getResources().getString(2131427494), 0).show();
      return;
    }
    while (this.mPhotoList == null);
    try
    {
      String str1 = ((KaixinPhotoItem)this.mPhotoList.get(this.mActivePhotoIndex)).large;
      Bitmap localBitmap = this.imageUtil.createSafeImage(str1);
      if (localBitmap != null)
      {
        getActivity().setWallpaper(localBitmap);
        Toast.makeText(getActivity(), getResources().getString(2131427495), 0).show();
        return;
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Toast.makeText(getActivity(), getResources().getString(2131427496), 0).show();
      return;
    }
    Toast.makeText(getActivity(), getResources().getString(2131427496), 0).show();
  }

  private void initAlbum(Bundle paramBundle)
  {
    if (paramBundle != null)
    {
      this.albumType = paramBundle.getInt("albumType", -1);
      this.mActivePhotoIndex = paramBundle.getInt("imgIndex", 0);
      this.mAlbumId = paramBundle.getString("albumId");
      this.mAlbumTitle = paramBundle.getString("title");
      this.isFromViewAlbumActiviy = paramBundle.getBoolean("isFromViewAlbum", false);
      if (this.albumType == -1)
      {
        if (!"0".equals(this.mAlbumId))
          break label154;
        this.albumType = 1;
      }
      if (this.albumType != 1)
        break label162;
      this.mFuid = paramBundle.getString("fuid");
      this.mCthreadCid = paramBundle.getString("cthread_cid");
      this.mPassword = paramBundle.getString("password");
      if (this.mPassword == null)
        this.mPassword = "";
      this.mPhotoList = AlbumPhotoModel.getInstance().mListLogos.getItemList();
    }
    label154: label162: 
    do
    {
      return;
      this.albumType = 2;
      break;
      if (this.albumType == 2)
      {
        this.mFuid = paramBundle.getString("fuid");
        this.mCthreadCid = paramBundle.getString("cthread_cid");
        this.mPassword = paramBundle.getString("password");
        if (this.mPassword == null)
          this.mPassword = "";
        this.mPhotoList = AlbumPhotoModel.getInstance().mListPhotos.getItemList();
        return;
      }
      if (this.albumType != 3)
        continue;
      this.gid = paramBundle.getString("gid");
      this.gname = paramBundle.getString("gname");
      this.mPhotoList.clear();
      if (this.isFromViewAlbumActiviy)
      {
        CircleAlbumModel localCircleAlbumModel = CircleAlbumModel.getInstance();
        if (this.gid.equals(localCircleAlbumModel.gid))
        {
          this.mPhotoList.addAll(localCircleAlbumModel.mCirclePhotoList.getItemList());
          return;
        }
        downloadPhotoList(0, Math.max(24, 1 + this.mActivePhotoIndex));
        return;
      }
      downloadPhotoList(0, Math.max(24, 1 + this.mActivePhotoIndex));
      return;
    }
    while (this.albumType != 4);
    this.poiName = paramBundle.getString("poiName");
    this.mPhotoList.clear();
    String str = this.mAlbumId;
    PoiPhotoesItem localPoiPhotoesItem = CheckInInfoModel.getInstance().getPoiPhotoesItemByPoiid(str);
    if (localPoiPhotoesItem != null)
    {
      this.mPhotoList.addAll(localPoiPhotoesItem.photoList.getItemList());
      return;
    }
    downloadPhotoList(0, Math.max(24, 1 + this.mActivePhotoIndex));
  }

  private void setBottomButtons()
  {
    this.btnPrevious = ((ImageView)findViewById(2131363952));
    this.btnPrevious.setOnClickListener(this);
    this.btnNext = ((ImageView)findViewById(2131363953));
    this.btnNext.setOnClickListener(this);
    this.btnComment = ((ImageView)findViewById(2131363954));
    this.btnComment.setOnClickListener(this);
    if (!TextUtils.isEmpty(this.gid))
      this.btnComment.setVisibility(4);
    this.btnSaveAs = ((ImageView)findViewById(2131363481));
    this.btnSaveAs.setOnClickListener(this);
    if (this.mPhotoList == null)
    {
      enableBottomButtons(false);
      return;
    }
    enableBottomButtons(true);
  }

  private void setGallery()
  {
    Gallery localGallery = (Gallery)findViewById(2131363948);
    localGallery.setAdapter(this.mAlbumAdapter);
    localGallery.setOnItemClickListener(new GalleryOnItemClickListener(null));
    localGallery.setOnItemSelectedListener(new GalleryOnItemSelectedListener(null));
    localGallery.setSelection(this.mActivePhotoIndex);
  }

  private void setTitleBar()
  {
    RelativeLayout localRelativeLayout = (RelativeLayout)findViewById(2131361802);
    localRelativeLayout.setBackgroundResource(2130837565);
    localRelativeLayout.setVisibility(8);
    findViewById(2131362919).setVisibility(8);
    findViewById(2131362920).setVisibility(0);
    ((ImageView)findViewById(2131362914)).setOnClickListener(this);
    ImageView localImageView = (ImageView)findViewById(2131362928);
    localImageView.setImageResource(2130838119);
    localImageView.setOnClickListener(this);
  }

  private void showAlbumActivity()
  {
    int i;
    AlbumPhotoModel localAlbumPhotoModel;
    int j;
    if ((this.albumType == 1) || (this.albumType == 2))
    {
      i = this.mPhotoList.size();
      localAlbumPhotoModel = AlbumPhotoModel.getInstance();
      if ((TextUtils.isEmpty(this.mAlbumId)) || ("0".equals(this.mAlbumId)))
      {
        j = Math.max(i, localAlbumPhotoModel.mListLogos.total);
        IntentUtil.showViewAlbumFromViewPhoto(getActivity(), this, this.mAlbumId, this.mAlbumTitle, this.mFuid, null, String.valueOf(j), null);
      }
    }
    do
    {
      return;
      j = Math.max(i, localAlbumPhotoModel.mListPhotos.total);
      break;
      if (this.albumType != 3)
        continue;
      IntentUtil.viewCircleAlbum(this, this.gid, this.gname);
      return;
    }
    while (this.albumType != 4);
    Intent localIntent = new Intent(getActivity(), AlbumViewFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("albumId", this.mAlbumId);
    localBundle.putInt("albumType", this.albumType);
    localBundle.putString("title", this.poiName);
    localIntent.putExtras(localBundle);
    startActivityForResult(localIntent, 1);
  }

  private void showBottomAndTitle()
  {
    if (findViewById(2131361802).getVisibility() != 0)
    {
      showTitleBar(true);
      showBottomButtons(true);
      return;
    }
    showTitleBar(false);
    showBottomButtons(false);
  }

  private void showBottomButtons(boolean paramBoolean)
  {
    View localView1 = findViewById(2131363480);
    View localView2 = findViewById(2131363950);
    int i = localView1.getVisibility();
    if ((i == 0) && (paramBoolean));
    do
      return;
    while ((i != 0) && (!paramBoolean));
    if (paramBoolean)
    {
      localView2.startAnimation(this.mBottomShowAnim);
      localView1.setVisibility(0);
      return;
    }
    localView2.startAnimation(this.mBottomHideAnim);
  }

  private void showObjCommentsActivity(int paramInt)
  {
    if (this.mPhotoList == null);
    while (true)
    {
      return;
      if (this.albumType != 4)
        break;
      CheckInPhotoItem localCheckInPhotoItem = (CheckInPhotoItem)this.mPhotoList.get(paramInt);
      if ((localCheckInPhotoItem == null) || (TextUtils.isEmpty(localCheckInPhotoItem.wid)))
        continue;
      Intent localIntent2 = new Intent(getActivity(), ObjCommentFragment.class);
      Bundle localBundle = new Bundle();
      localBundle.putInt("needRefresh", 1);
      localBundle.putString("id", localCheckInPhotoItem.wid);
      localBundle.putString("type", "1192");
      localBundle.putString("fuid", "");
      localBundle.putString("commentflag", "");
      localBundle.putString("fname", "");
      localBundle.putString("title", "");
      localBundle.putString("ctime", "");
      localBundle.putString("thumbnail", "");
      localBundle.putString("large", "");
      localBundle.putString("location", "");
      localBundle.putString("client_name", "");
      localIntent2.putExtras(localBundle);
      startFragment(localIntent2, true, 1);
      return;
    }
    KaixinPhotoItem localKaixinPhotoItem = (KaixinPhotoItem)this.mPhotoList.get(paramInt);
    Intent localIntent1 = new Intent(getActivity(), ObjCommentFragment.class);
    localIntent1.putExtra("id", localKaixinPhotoItem.pid);
    if ("0".equals(this.mAlbumId));
    for (String str = "4"; ; str = "1")
    {
      localIntent1.putExtra("type", str);
      localIntent1.putExtra("fuid", this.mFuid);
      localIntent1.putExtra("cthread_cid", this.mCthreadCid);
      localIntent1.putExtra("mode", 3);
      startActivityForResult(localIntent1, 3);
      return;
    }
  }

  private void showProgressBar(boolean paramBoolean)
  {
    View localView = findViewById(2131363949);
    if (paramBoolean)
    {
      localView.setVisibility(0);
      return;
    }
    localView.setVisibility(8);
  }

  private void showSavePhotoOptions()
  {
    DialogUtil.showSelectListDlg(getActivity(), 2131427491, getResources().getStringArray(2131492870), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        ViewPhotoFragment.this.handlePhotoOperationOptions(paramInt);
      }
    }
    , 1);
  }

  private void showTitleBar(boolean paramBoolean)
  {
    View localView = findViewById(2131361802);
    int i = localView.getVisibility();
    if ((i == 0) && (paramBoolean));
    do
      return;
    while ((i != 0) && (!paramBoolean));
    if (paramBoolean)
    {
      localView.startAnimation(this.mTitleShowAnim);
      localView.setVisibility(0);
      return;
    }
    localView.startAnimation(this.mTitleHideAnim);
    localView.setVisibility(8);
  }

  private void startHideButtonsTimer()
  {
    if (this.mHideButtonTimer != null)
    {
      this.mHideButtonTimer.cancel();
      this.mHideButtonTimer = null;
      this.mHideButtonsTask = null;
    }
    this.mHideButtonTimer = new Timer();
    this.mHideButtonsTask = new HideButtonsTask(null);
    this.mHideButtonTimer.schedule(this.mHideButtonsTask, this.mButtonShowDuration);
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (paramMessage == null)
      return false;
    if (paramMessage.what == 8001)
    {
      if (this.mAlbumAdapter != null)
        this.mAlbumAdapter.notifyDataSetChanged();
      String str = (String)paramMessage.obj;
      if (this.mLatestDownloadPhotoUrl.equals(str))
        showProgressBar(false);
      return true;
    }
    if (paramMessage.what == 113)
    {
      this.mHideButtonTimer.cancel();
      showTitleBar(false);
      showBottomButtons(false);
      return true;
    }
    return super.handleMessage(paramMessage);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 == -1)
    {
      if (paramInt1 == 1)
      {
        this.mActivePhotoIndex = paramIntent.getIntExtra("pos", 0);
        ((Gallery)findViewById(2131363948)).setSelection(this.mActivePhotoIndex);
      }
      if (paramInt1 == 3)
        this.mReplyContent = paramIntent.getStringExtra("content");
    }
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131363952:
      Gallery localGallery2;
      int m;
      if (this.mPhotoList != null)
      {
        localGallery2 = (Gallery)findViewById(2131363948);
        int k = localGallery2.getSelectedItemPosition();
        if (k > 0)
        {
          this.mGallerySelectionChangedByButton = true;
          m = k - 1;
          localGallery2.setSelection(m);
          if (m != 0)
            break label164;
          paramView.setEnabled(false);
        }
      }
      while (true)
      {
        if (m == -2 + localGallery2.getCount())
          ((ImageView)findViewById(2131363953)).setEnabled(true);
        startHideButtonsTimer();
        return;
        paramView.setEnabled(true);
      }
    case 2131363953:
      int j;
      if (this.mPhotoList != null)
      {
        Gallery localGallery1 = (Gallery)findViewById(2131363948);
        int i = localGallery1.getSelectedItemPosition();
        if (i < -1 + localGallery1.getCount())
        {
          this.mGallerySelectionChangedByButton = true;
          j = i + 1;
          localGallery1.setSelection(j);
          if (j != -1 + localGallery1.getCount())
            break label270;
          paramView.setEnabled(false);
        }
      }
      while (true)
      {
        if (j == 1)
          ((ImageView)findViewById(2131363952)).setEnabled(true);
        startHideButtonsTimer();
        return;
        paramView.setEnabled(true);
      }
    case 2131363954:
      showObjCommentsActivity(this.mActivePhotoIndex);
      return;
    case 2131363481:
      showSavePhotoOptions();
      return;
    case 2131362914:
      label164: if (!TextUtils.isEmpty(this.mReplyContent))
      {
        Intent localIntent = new Intent();
        localIntent.putExtra("content", this.mReplyContent);
        setResult(-1, localIntent);
        finishFragment(3);
      }
      label270: finish();
      return;
    case 2131362928:
    case 2131362930:
    }
    showAlbumActivity();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    String str = null;
    if (localBundle != null)
      str = localBundle.getString("action");
    if ((str != null) && (!str.equals("com.kaixin001.VIEW_LARGE_PHOTO")) && (CrashRecoverUtil.isCrashBefore()))
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903403, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.mGetPhotoListTask != null) && (this.mGetPhotoListTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mGetPhotoListTask.cancel(true);
      AlbumPhotoEngine.getInstance().cancel();
    }
    if (this.mHideButtonTimer != null)
      this.mHideButtonTimer.cancel();
    if (this.mLoadPhotoDelayTimer != null)
      this.mLoadPhotoDelayTimer.cancel();
    if (!this.isFromViewAlbumActiviy)
    {
      if (this.albumType != 3)
        break label89;
      CircleAlbumModel.getInstance().clear();
    }
    while (true)
    {
      super.onDestroy();
      return;
      label89: AlbumPhotoModel.getInstance().clear();
    }
  }

  public void onPause()
  {
    this.mImgDownloader.cancel(this.mHandler);
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
    if (!TextUtils.isEmpty(this.mLatestDownloadPhotoUrl))
    {
      if (this.imageUtil.createSafeImage(this.mLatestDownloadPhotoUrl) == null)
      {
        this.mLoadPhotoDelayTimer = new Timer();
        LoadPhotoDelayTimerTask localLoadPhotoDelayTimerTask = new LoadPhotoDelayTimerTask(this.mLatestDownloadPhotoUrl);
        this.mLoadPhotoDelayTimer.schedule(localLoadPhotoDelayTimerTask, this.mLoadPhotoDelay);
        showProgressBar(true);
      }
    }
    else
      return;
    this.mAlbumAdapter.notifyDataSetChanged();
    showProgressBar(false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    KXSliderLayout2.setSlideEnable(false);
    enableSlideBack(false);
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
    initAlbum(getArguments());
    constructAnimations();
    setTitleBar();
    setGallery();
    setBottomButtons();
  }

  private class AlbumAdapter extends BaseAdapter
  {
    private AlbumAdapter()
    {
    }

    public int getCount()
    {
      if (ViewPhotoFragment.this.mPhotoList == null)
        return 0;
      return ViewPhotoFragment.this.mPhotoList.size();
    }

    public Object getItem(int paramInt)
    {
      if (ViewPhotoFragment.this.mPhotoList == null);
      do
        return null;
      while ((paramInt < 0) || (paramInt >= ViewPhotoFragment.this.mPhotoList.size()));
      return ViewPhotoFragment.this.mPhotoList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
      {
        paramView = ViewPhotoFragment.this.getActivity().getLayoutInflater().inflate(2130903405, paramViewGroup, false);
        ViewHolder localViewHolder = new ViewHolder(null);
        localViewHolder.imageView = ((ImageView)paramView.findViewById(2131363969));
        paramView.setTag(localViewHolder);
      }
      ImageView localImageView = ((ViewHolder)paramView.getTag()).imageView;
      if (ViewPhotoFragment.this.mPhotoList == null);
      KaixinPhotoItem localKaixinPhotoItem;
      do
      {
        return paramView;
        localKaixinPhotoItem = (KaixinPhotoItem)getItem(paramInt);
      }
      while (localKaixinPhotoItem == null);
      Bitmap localBitmap1 = ViewPhotoFragment.this.imageUtil.createSafeImage(localKaixinPhotoItem.large);
      Bitmap localBitmap2 = null;
      if (localBitmap1 == null)
        localBitmap2 = ImageCache.getInstance().createSafeImage(localKaixinPhotoItem.thumbnail);
      if (localBitmap1 != null)
      {
        localImageView.setImageBitmap(localBitmap1);
        localImageView.setScaleType(ImageView.ScaleType.MATRIX);
        return paramView;
      }
      if (localBitmap2 != null)
      {
        localImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        localImageView.setImageBitmap(localBitmap2);
        return paramView;
      }
      localImageView.setImageResource(2130838766);
      return paramView;
    }

    private class ViewHolder
    {
      public ImageView imageView;

      private ViewHolder()
      {
      }
    }
  }

  private class GalleryOnItemClickListener
    implements AdapterView.OnItemClickListener
  {
    private GalleryOnItemClickListener()
    {
    }

    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      try
      {
        if (ViewPhotoFragment.this.findViewById(2131361802).getVisibility() != 0)
        {
          ViewPhotoFragment.this.showTitleBar(true);
          ViewPhotoFragment.this.showBottomButtons(true);
          ViewPhotoFragment.this.startHideButtonsTimer();
          return;
        }
        ViewPhotoFragment.this.showTitleBar(false);
        ViewPhotoFragment.this.showBottomButtons(false);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  private class GalleryOnItemSelectedListener
    implements AdapterView.OnItemSelectedListener
  {
    private GalleryOnItemSelectedListener()
    {
    }

    public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      KXImageView localKXImageView = (KXImageView)paramView.findViewById(2131363969);
      localKXImageView.zoomTo(1.0F);
      ViewPhotoFragment.this.mActivePhotoIndex = paramInt;
      if ((ViewPhotoFragment.this.findViewById(2131361802).getVisibility() == 0) && (!ViewPhotoFragment.this.mGallerySelectionChangedByButton))
      {
        ViewPhotoFragment.this.showTitleBar(false);
        ViewPhotoFragment.this.showBottomButtons(false);
      }
      ViewPhotoFragment.this.mGallerySelectionChangedByButton = false;
      int i = ViewPhotoFragment.this.mPhotoList.size();
      AlbumPhotoModel localAlbumPhotoModel = AlbumPhotoModel.getInstance();
      if (ViewPhotoFragment.this.albumType == 1)
        i = Math.max(i, localAlbumPhotoModel.mListLogos.total);
      while (true)
      {
        if ((ViewPhotoFragment.this.mPhotoList.size() < i) && (ViewPhotoFragment.this.mPhotoList.size() - ViewPhotoFragment.this.mActivePhotoIndex < 10))
          ViewPhotoFragment.this.downloadPhotoList(ViewPhotoFragment.this.mPhotoList.size(), 24);
        String str1;
        if ((ViewPhotoFragment.this.mPhotoList != null) && (paramInt < ViewPhotoFragment.this.mPhotoList.size()))
          str1 = null;
        try
        {
          KaixinPhotoItem localKaixinPhotoItem = (KaixinPhotoItem)ViewPhotoFragment.this.mPhotoList.get(paramInt);
          String str2 = localKaixinPhotoItem.title;
          str1 = localKaixinPhotoItem.large;
          ((TextView)ViewPhotoFragment.this.findViewById(2131363951)).setText(str2);
          ((TextView)ViewPhotoFragment.this.findViewById(2131362920)).setText(1 + ViewPhotoFragment.this.mActivePhotoIndex + "/" + i);
          ViewPhotoFragment.this.enableBottomButtons(true);
          if (ViewPhotoFragment.this.mLoadPhotoDelayTimer != null)
          {
            ViewPhotoFragment.this.mLoadPhotoDelayTimer.cancel();
            ViewPhotoFragment.this.mLoadPhotoDelayTimer = null;
          }
          if (str1 == null)
            break;
          localBitmap = ViewPhotoFragment.this.imageUtil.createSafeImage(str1);
          if (localBitmap == null)
          {
            ViewPhotoFragment.this.mLoadPhotoDelayTimer = new Timer();
            ViewPhotoFragment.LoadPhotoDelayTimerTask localLoadPhotoDelayTimerTask = new ViewPhotoFragment.LoadPhotoDelayTimerTask(ViewPhotoFragment.this, str1);
            ViewPhotoFragment.this.mLoadPhotoDelayTimer.schedule(localLoadPhotoDelayTimerTask, ViewPhotoFragment.this.mLoadPhotoDelay);
            ViewPhotoFragment.this.showProgressBar(true);
            return;
            if (ViewPhotoFragment.this.albumType == 2)
            {
              i = Math.max(i, localAlbumPhotoModel.mListPhotos.total);
              continue;
            }
            if (ViewPhotoFragment.this.albumType == 3)
            {
              i = Math.max(i, CircleAlbumModel.getInstance().mCirclePhotoList.total);
              continue;
            }
            if (ViewPhotoFragment.this.albumType != 4)
              continue;
            i = Math.max(i, CheckInInfoModel.getInstance().getPoiPhotoesItemByPoiid(ViewPhotoFragment.this.mAlbumId).photoList.total);
          }
        }
        catch (Exception localException)
        {
          Bitmap localBitmap;
          while (true)
            localException.printStackTrace();
          localKXImageView.setImageBitmap(localBitmap);
          ViewPhotoFragment.this.showProgressBar(false);
          return;
        }
      }
      ViewPhotoFragment.this.showProgressBar(true);
    }

    public void onNothingSelected(AdapterView<?> paramAdapterView)
    {
    }
  }

  private class GetPhotoListTask extends KXFragment.KXAsyncTask<Integer, Void, Integer>
  {
    private GetPhotoListTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Integer[] paramArrayOfInteger)
    {
      try
      {
        int i = paramArrayOfInteger[0].intValue();
        int j = paramArrayOfInteger[1].intValue();
        if (ViewPhotoFragment.this.albumType == 1)
          return Integer.valueOf(AlbumPhotoEngine.getInstance().getLogoPhotoList(ViewPhotoFragment.this.getActivity().getApplicationContext(), ViewPhotoFragment.this.mFuid, i, j));
        if (ViewPhotoFragment.this.albumType == 2)
          return Integer.valueOf(AlbumPhotoEngine.getInstance().getAlbumPhotoList(ViewPhotoFragment.this.getActivity().getApplicationContext(), ViewPhotoFragment.this.mAlbumId, ViewPhotoFragment.this.mFuid, ViewPhotoFragment.this.mPassword, i, j));
        if (ViewPhotoFragment.this.albumType == 3)
          return Integer.valueOf(CircleAlbumEngine.getInstance().getCirclePhotoList(ViewPhotoFragment.this.getActivity().getApplicationContext(), i, ViewPhotoFragment.this.gid));
        if (ViewPhotoFragment.this.albumType == 4)
          return CheckinListEngine.getInstance().getPoiPhotoes(ViewPhotoFragment.this.getActivity().getApplicationContext(), ViewPhotoFragment.this.mAlbumId, i, j);
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
      if (paramInteger == null)
      {
        ViewPhotoFragment.this.finish();
        return;
      }
      while (true)
      {
        AlbumPhotoModel localAlbumPhotoModel;
        try
        {
          localAlbumPhotoModel = AlbumPhotoModel.getInstance();
          if (ViewPhotoFragment.this.albumType == 1)
          {
            ViewPhotoFragment.this.mPhotoList = localAlbumPhotoModel.mListLogos.getItemList();
            if (ViewPhotoFragment.this.mAlbumAdapter == null)
              continue;
            ViewPhotoFragment.this.mAlbumAdapter.notifyDataSetChanged();
            if (ViewPhotoFragment.this.mPhotoList != null)
              break;
            Toast.makeText(ViewPhotoFragment.this.getActivity(), 2131427485, 0).show();
            ViewPhotoFragment.this.showProgressBar(false);
            ViewPhotoFragment.this.showBottomAndTitle();
            ViewPhotoFragment.this.enableBottomButtons(false);
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("ViewPhotoActivity", "onPostExecute", localException);
          return;
        }
        if (ViewPhotoFragment.this.albumType == 2)
        {
          ViewPhotoFragment.this.mPhotoList = localAlbumPhotoModel.mListPhotos.getItemList();
          continue;
        }
        if (ViewPhotoFragment.this.albumType == 3)
        {
          ViewPhotoFragment.this.mPhotoList.clear();
          ViewPhotoFragment.this.mPhotoList.addAll(CircleAlbumModel.getInstance().mCirclePhotoList.getItemList());
          continue;
        }
        String str = ViewPhotoFragment.this.mAlbumId;
        PoiPhotoesItem localPoiPhotoesItem = CheckInInfoModel.getInstance().getPoiPhotoesItemByPoiid(str);
        ViewPhotoFragment.this.mPhotoList.clear();
        ViewPhotoFragment.this.mPhotoList.addAll(localPoiPhotoesItem.photoList.getItemList());
      }
      ((Gallery)ViewPhotoFragment.this.findViewById(2131363948)).setSelection(ViewPhotoFragment.this.mActivePhotoIndex);
      ViewPhotoFragment.this.enableBottomButtons(true);
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class HideButtonsTask extends TimerTask
  {
    private HideButtonsTask()
    {
    }

    public void run()
    {
      if (ViewPhotoFragment.this.isNeedReturn())
        return;
      Message localMessage = Message.obtain();
      localMessage.what = 113;
      ViewPhotoFragment.this.mHandler.sendMessage(localMessage);
    }
  }

  private class LoadPhotoDelayTimerTask extends TimerTask
  {
    private String mPhotoUrl;

    LoadPhotoDelayTimerTask(String arg2)
    {
      Object localObject;
      this.mPhotoUrl = localObject;
    }

    public void run()
    {
      if (ViewPhotoFragment.this.isNeedReturn());
      do
        return;
      while ((TextUtils.isEmpty(this.mPhotoUrl)) || (0 != 0));
      ViewPhotoFragment.this.mImgDownloader.downloadImageSync(this.mPhotoUrl);
      ViewPhotoFragment.this.mLatestDownloadPhotoUrl = this.mPhotoUrl;
      Message localMessage = new Message();
      localMessage.what = 8001;
      localMessage.obj = this.mPhotoUrl;
      ViewPhotoFragment.this.mHandler.sendMessage(localMessage);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.ViewPhotoFragment
 * JD-Core Version:    0.6.0
 */