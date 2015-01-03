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
import com.kaixin001.engine.CircleGetNextPreviousPhotoEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.model.CirclePhotoModel.CirclePhoto;
import com.kaixin001.model.User;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownloader;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.KXImageView;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ViewCirclePhotoFragment extends BaseFragment
  implements View.OnClickListener
{
  private static final int MSG_HIDE_BUTTONS = 113;
  private String gid;
  private String gname;
  private ImageCache imageUtil = ImageCache.getInstance();
  private boolean isAllPhotoesInList = false;
  private int mActivePhotoIndex;
  private AlbumAdapter mAlbumAdapter = new AlbumAdapter(null);
  TranslateAnimation mBottomHideAnim = null;
  TranslateAnimation mBottomShowAnim = null;
  private long mButtonShowDuration = 3000L;
  private boolean mGallerySelectionChangedByButton = false;
  GetPhotoTask mGetPhotoListTask = null;
  private Timer mHideButtonTimer = new Timer();
  private HideButtonsTask mHideButtonsTask = new HideButtonsTask(null);
  private ImageDownloader mImgDownloader = ImageDownloader.getInstance();
  private String mLatestDownloadPhotoUrl = "";
  private long mLoadPhotoDelay = 500L;
  private Timer mLoadPhotoDelayTimer = new Timer();
  private ArrayList<CirclePhotoModel.CirclePhoto> mPhotoList = new ArrayList();
  TranslateAnimation mTitleHideAnim = null;
  TranslateAnimation mTitleShowAnim = null;

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
        ViewCirclePhotoFragment.this.findViewById(2131363480).setVisibility(8);
      }

      public void onAnimationRepeat(Animation paramAnimation)
      {
      }

      public void onAnimationStart(Animation paramAnimation)
      {
      }
    });
  }

  private void downloadPhotoList(String paramString1, String paramString2, String paramString3, Integer paramInteger)
  {
    if ((this.mGetPhotoListTask != null) && (!this.mGetPhotoListTask.isCancelled()) && (this.mGetPhotoListTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    showProgressBar(true);
    this.mGetPhotoListTask = new GetPhotoTask(null);
    Object[] arrayOfObject = { paramString2, paramString1, paramString3, paramInteger };
    this.mGetPhotoListTask.execute(arrayOfObject);
  }

  private void enableBottomButtons(boolean paramBoolean)
  {
    ImageView localImageView1 = (ImageView)findViewById(2131363952);
    ImageView localImageView2 = (ImageView)findViewById(2131363953);
    ImageView localImageView3 = (ImageView)findViewById(2131363954);
    ImageView localImageView4 = (ImageView)findViewById(2131363481);
    localImageView1.setEnabled(paramBoolean);
    localImageView2.setEnabled(paramBoolean);
    localImageView3.setEnabled(paramBoolean);
    localImageView4.setEnabled(paramBoolean);
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
        String str2 = FileUtil.savePicture(((CirclePhotoModel.CirclePhoto)this.mPhotoList.get(this.mActivePhotoIndex)).largePhoto);
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
      String str1 = ((CirclePhotoModel.CirclePhoto)this.mPhotoList.get(this.mActivePhotoIndex)).largePhoto;
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

  private void setBottomButtons()
  {
    ImageView localImageView1 = (ImageView)findViewById(2131363952);
    localImageView1.setOnClickListener(this);
    ImageView localImageView2 = (ImageView)findViewById(2131363953);
    localImageView2.setOnClickListener(this);
    ImageView localImageView3 = (ImageView)findViewById(2131363954);
    localImageView3.setOnClickListener(this);
    localImageView3.setVisibility(4);
    ((ImageView)findViewById(2131363481)).setOnClickListener(this);
    if (this.mPhotoList == null)
      enableBottomButtons(false);
    do
    {
      return;
      enableBottomButtons(true);
    }
    while (this.mPhotoList.size() != 1);
    localImageView1.setEnabled(false);
    localImageView2.setEnabled(false);
  }

  private void setGallery()
  {
    Gallery localGallery = (Gallery)findViewById(2131363948);
    localGallery.setAdapter(this.mAlbumAdapter);
    localGallery.setOnItemClickListener(new GalleryOnItemClickListener(null));
    localGallery.setOnItemSelectedListener(new GalleryOnItemSelectedListener(null));
    localGallery.setSelection(0);
  }

  private void setTitleBar()
  {
    RelativeLayout localRelativeLayout = (RelativeLayout)findViewById(2131361802);
    localRelativeLayout.setBackgroundResource(2130837565);
    localRelativeLayout.setVisibility(8);
    findViewById(2131362919).setVisibility(8);
    findViewById(2131362920).setVisibility(8);
    ((ImageView)findViewById(2131362914)).setOnClickListener(this);
    ImageView localImageView = (ImageView)findViewById(2131362928);
    localImageView.setImageResource(2130839051);
    localImageView.setOnClickListener(this);
  }

  private void showAlbumActivity()
  {
    IntentUtil.viewCircleAlbum(this, this.gid, null);
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
        ViewCirclePhotoFragment.this.handlePhotoOperationOptions(paramInt);
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
    if ((paramInt2 == -1) && (paramInt1 == 1))
    {
      this.mActivePhotoIndex = paramIntent.getIntExtra("pos", 0);
      ((Gallery)findViewById(2131363948)).setSelection(this.mActivePhotoIndex);
    }
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
    case 2131363952:
    case 2131363953:
      while (true)
      {
        return;
        if (this.mPhotoList != null)
        {
          Gallery localGallery2 = (Gallery)findViewById(2131363948);
          int k = localGallery2.getSelectedItemPosition();
          if ((this.isAllPhotoesInList) && (k == 0))
            k = -1 + this.mPhotoList.size();
          CirclePhotoModel.CirclePhoto localCirclePhoto4 = (CirclePhotoModel.CirclePhoto)this.mPhotoList.get(k);
          CirclePhotoModel.CirclePhoto localCirclePhoto5 = null;
          if (k > 0)
          {
            localCirclePhoto5 = (CirclePhotoModel.CirclePhoto)this.mPhotoList.get(k - 1);
            if (!TextUtils.isEmpty(localCirclePhoto5.pid))
            {
              this.mGallerySelectionChangedByButton = true;
              this.mActivePhotoIndex = (k - 1);
              localGallery2.setSelection(k - 1);
              return;
            }
          }
          if ((localCirclePhoto4.pid == null) && (localCirclePhoto5 == null))
            continue;
          if (localCirclePhoto5 == null)
          {
            CirclePhotoModel.CirclePhoto localCirclePhoto6 = new CirclePhotoModel.CirclePhoto();
            this.mPhotoList.add(k, localCirclePhoto6);
          }
          this.mActivePhotoIndex = k;
          this.mGallerySelectionChangedByButton = true;
          localGallery2.setSelection(this.mActivePhotoIndex);
          downloadPhotoList(localCirclePhoto4.pid, localCirclePhoto4.gid, "prev", Integer.valueOf(this.mActivePhotoIndex));
        }
        startHideButtonsTimer();
        return;
        if (this.mPhotoList == null)
          break;
        Gallery localGallery1 = (Gallery)findViewById(2131363948);
        int i = localGallery1.getSelectedItemPosition();
        if ((this.isAllPhotoesInList) && (i == -1 + this.mPhotoList.size()))
          i = 0;
        CirclePhotoModel.CirclePhoto localCirclePhoto1 = (CirclePhotoModel.CirclePhoto)this.mPhotoList.get(i);
        int j = -1 + localGallery1.getCount();
        CirclePhotoModel.CirclePhoto localCirclePhoto2 = null;
        if (i < j)
        {
          localCirclePhoto2 = (CirclePhotoModel.CirclePhoto)this.mPhotoList.get(i + 1);
          if (!TextUtils.isEmpty(localCirclePhoto2.pid))
          {
            this.mGallerySelectionChangedByButton = true;
            this.mActivePhotoIndex = (i + 1);
            localGallery1.setSelection(i + 1);
            return;
          }
        }
        if ((localCirclePhoto1.pid == null) && (localCirclePhoto2 == null))
          continue;
        if (localCirclePhoto2 == null)
        {
          CirclePhotoModel.CirclePhoto localCirclePhoto3 = new CirclePhotoModel.CirclePhoto();
          this.mPhotoList.add(i + 1, localCirclePhoto3);
        }
        this.mActivePhotoIndex = (i + 1);
        this.mGallerySelectionChangedByButton = true;
        localGallery1.setSelection(this.mActivePhotoIndex);
        downloadPhotoList(localCirclePhoto1.pid, localCirclePhoto1.gid, "prev", Integer.valueOf(this.mActivePhotoIndex));
      }
      startHideButtonsTimer();
      return;
    case 2131363481:
      showSavePhotoOptions();
      return;
    case 2131362914:
      finish();
      return;
    case 2131362928:
    }
    showAlbumActivity();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
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
    super.onDestroy();
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
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      String str1 = localBundle.getString("pid");
      this.gid = localBundle.getString("gid");
      String str2 = localBundle.getString("smallPhoto");
      String str3 = localBundle.getString("largePhoto");
      CirclePhotoModel.CirclePhoto localCirclePhoto = new CirclePhotoModel.CirclePhoto();
      localCirclePhoto.gid = this.gid;
      localCirclePhoto.pid = str1;
      localCirclePhoto.smallPhoto = str2;
      localCirclePhoto.largePhoto = str3;
      this.mPhotoList.add(localCirclePhoto);
    }
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
      if (ViewCirclePhotoFragment.this.mPhotoList == null)
        return 0;
      return ViewCirclePhotoFragment.this.mPhotoList.size();
    }

    public Object getItem(int paramInt)
    {
      if (ViewCirclePhotoFragment.this.mPhotoList == null);
      do
        return null;
      while ((paramInt < 0) || (paramInt >= ViewCirclePhotoFragment.this.mPhotoList.size()));
      return ViewCirclePhotoFragment.this.mPhotoList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
      {
        paramView = ViewCirclePhotoFragment.this.getActivity().getLayoutInflater().inflate(2130903405, paramViewGroup, false);
        ViewHolder localViewHolder = new ViewHolder(null);
        localViewHolder.imageView = ((ImageView)paramView.findViewById(2131363969));
        paramView.setTag(localViewHolder);
      }
      ImageView localImageView = ((ViewHolder)paramView.getTag()).imageView;
      CirclePhotoModel.CirclePhoto localCirclePhoto = (CirclePhotoModel.CirclePhoto)getItem(paramInt);
      if (localCirclePhoto == null)
        return paramView;
      Bitmap localBitmap1 = ViewCirclePhotoFragment.this.imageUtil.createSafeImage(localCirclePhoto.largePhoto);
      Bitmap localBitmap2 = null;
      if (localBitmap1 == null)
        localBitmap2 = ImageCache.getInstance().createSafeImage(localCirclePhoto.smallPhoto);
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
      if (ViewCirclePhotoFragment.this.findViewById(2131361802).getVisibility() != 0)
      {
        ViewCirclePhotoFragment.this.showTitleBar(true);
        ViewCirclePhotoFragment.this.showBottomButtons(true);
        ViewCirclePhotoFragment.this.startHideButtonsTimer();
        return;
      }
      ViewCirclePhotoFragment.this.showTitleBar(false);
      ViewCirclePhotoFragment.this.showBottomButtons(false);
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
      ViewCirclePhotoFragment.this.mActivePhotoIndex = paramInt;
      if ((ViewCirclePhotoFragment.this.findViewById(2131361802).getVisibility() == 0) && (!ViewCirclePhotoFragment.this.mGallerySelectionChangedByButton))
      {
        ViewCirclePhotoFragment.this.showTitleBar(false);
        ViewCirclePhotoFragment.this.showBottomButtons(false);
      }
      ViewCirclePhotoFragment.this.mGallerySelectionChangedByButton = false;
      String str1;
      if ((ViewCirclePhotoFragment.this.mPhotoList != null) && (paramInt < ViewCirclePhotoFragment.this.mPhotoList.size()))
        str1 = null;
      try
      {
        CirclePhotoModel.CirclePhoto localCirclePhoto = (CirclePhotoModel.CirclePhoto)ViewCirclePhotoFragment.this.mPhotoList.get(paramInt);
        String str2 = localCirclePhoto.title;
        str1 = localCirclePhoto.largePhoto;
        ((TextView)ViewCirclePhotoFragment.this.findViewById(2131363951)).setText(str2);
        ((ImageView)ViewCirclePhotoFragment.this.findViewById(2131363952)).setEnabled(true);
        ((ImageView)ViewCirclePhotoFragment.this.findViewById(2131363953)).setEnabled(true);
        if (ViewCirclePhotoFragment.this.mLoadPhotoDelayTimer != null)
        {
          ViewCirclePhotoFragment.this.mLoadPhotoDelayTimer.cancel();
          ViewCirclePhotoFragment.this.mLoadPhotoDelayTimer = null;
        }
        if (str1 != null)
        {
          localBitmap = ViewCirclePhotoFragment.this.imageUtil.createSafeImage(str1);
          if (localBitmap == null)
          {
            ViewCirclePhotoFragment.this.mLoadPhotoDelayTimer = new Timer();
            ViewCirclePhotoFragment.LoadPhotoDelayTimerTask localLoadPhotoDelayTimerTask = new ViewCirclePhotoFragment.LoadPhotoDelayTimerTask(ViewCirclePhotoFragment.this, str1);
            ViewCirclePhotoFragment.this.mLoadPhotoDelayTimer.schedule(localLoadPhotoDelayTimerTask, ViewCirclePhotoFragment.this.mLoadPhotoDelay);
            ViewCirclePhotoFragment.this.showProgressBar(true);
          }
        }
        else
        {
          return;
        }
      }
      catch (Exception localException)
      {
        Bitmap localBitmap;
        while (true)
          localException.printStackTrace();
        localKXImageView.setImageBitmap(localBitmap);
        ViewCirclePhotoFragment.this.showProgressBar(false);
      }
    }

    public void onNothingSelected(AdapterView<?> paramAdapterView)
    {
    }
  }

  private class GetPhotoTask extends KXFragment.KXAsyncTask<Object, Void, Integer>
  {
    private String action;
    private int indexPic;
    private int mActivePhotoIndex;
    private String pid;
    private ArrayList<CirclePhotoModel.CirclePhoto> resultContainer = new ArrayList();

    private GetPhotoTask()
    {
      super();
    }

    private int getPhotoIndexByPid(String paramString)
    {
      int i = ViewCirclePhotoFragment.this.mPhotoList.size();
      for (int j = 0; ; j++)
      {
        if (j >= i)
          j = -1;
        do
          return j;
        while (paramString.equals(((CirclePhotoModel.CirclePhoto)ViewCirclePhotoFragment.this.mPhotoList.get(j)).pid));
      }
    }

    protected Integer doInBackgroundA(Object[] paramArrayOfObject)
    {
      String str = (String)paramArrayOfObject[0];
      this.pid = ((String)paramArrayOfObject[1]);
      this.action = ((String)paramArrayOfObject[2]);
      this.indexPic = ((Integer)paramArrayOfObject[3]).intValue();
      try
      {
        Integer localInteger = Integer.valueOf(CircleGetNextPreviousPhotoEngine.getInstance().doGetCircleNextPreviousPhoto(ViewCirclePhotoFragment.this.getActivity().getApplicationContext(), str, this.action, this.pid, this.resultContainer));
        return localInteger;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      ViewCirclePhotoFragment.this.showProgressBar(false);
      if (paramInteger == null)
      {
        ViewCirclePhotoFragment.this.finish();
        return;
      }
      if (1 != paramInteger.intValue())
      {
        Toast.makeText(ViewCirclePhotoFragment.this.getActivity().getApplicationContext(), 2131427374, 0).show();
        return;
      }
      CirclePhotoModel.CirclePhoto localCirclePhoto = (CirclePhotoModel.CirclePhoto)this.resultContainer.get(0);
      while (true)
      {
        try
        {
          if (getPhotoIndexByPid(localCirclePhoto.pid) == -1)
            break label236;
          ViewCirclePhotoFragment.this.isAllPhotoesInList = true;
          if ("next".equals(this.action))
          {
            this.mActivePhotoIndex = 0;
            ViewCirclePhotoFragment.this.mPhotoList.remove(this.indexPic);
            if (ViewCirclePhotoFragment.this.mAlbumAdapter == null)
              continue;
            ViewCirclePhotoFragment.this.mAlbumAdapter.notifyDataSetChanged();
            ((Gallery)ViewCirclePhotoFragment.this.findViewById(2131363948)).setSelection(this.mActivePhotoIndex);
            ViewCirclePhotoFragment.this.enableBottomButtons(true);
            ((ImageView)ViewCirclePhotoFragment.this.findViewById(2131363952)).setEnabled(true);
            ((ImageView)ViewCirclePhotoFragment.this.findViewById(2131363953)).setEnabled(true);
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("ViewPhotoActivity", "onPostExecute", localException);
          return;
        }
        this.mActivePhotoIndex = (-1 + ViewCirclePhotoFragment.this.mPhotoList.size());
        ViewCirclePhotoFragment.this.mPhotoList.remove(this.indexPic);
        continue;
        label236: this.mActivePhotoIndex = this.indexPic;
        ViewCirclePhotoFragment.this.mPhotoList.remove(this.indexPic);
        ViewCirclePhotoFragment.this.mPhotoList.add(this.indexPic, localCirclePhoto);
      }
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
      if (ViewCirclePhotoFragment.this.isNeedReturn())
        return;
      Message localMessage = Message.obtain();
      localMessage.what = 113;
      ViewCirclePhotoFragment.this.mHandler.sendMessage(localMessage);
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
      if (ViewCirclePhotoFragment.this.isNeedReturn());
      do
        return;
      while ((TextUtils.isEmpty(this.mPhotoUrl)) || (0 != 0));
      ViewCirclePhotoFragment.this.mImgDownloader.downloadImageSync(this.mPhotoUrl);
      ViewCirclePhotoFragment.this.mLatestDownloadPhotoUrl = this.mPhotoUrl;
      Message localMessage = new Message();
      localMessage.what = 8001;
      localMessage.obj = this.mPhotoUrl;
      ViewCirclePhotoFragment.this.mHandler.sendMessage(localMessage);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.ViewCirclePhotoFragment
 * JD-Core Version:    0.6.0
 */