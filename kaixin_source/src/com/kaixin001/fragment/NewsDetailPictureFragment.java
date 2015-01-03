package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;
import com.kaixin001.engine.CloudAlbumEngine;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CloseUtil;
import com.kaixin001.util.CloudAlbumManager;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownLoaderManager;
import com.kaixin001.util.ImageDownloadCallback;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.KXImageView;
import java.io.File;

public class NewsDetailPictureFragment extends BaseFragment
  implements View.OnClickListener
{
  private static final String TAG = "NewsDetailPictureFragment";
  private String mCpid;
  private ImageView mDeleteBtn;
  private DeletePicTask mDeletePicTask;
  private ImageView mDownloadBtn;
  private String mFrom;
  private KXImageView mImageView;
  private LoadPhotoTask mLoadPhotoTask;
  private Bitmap mLocalBitmap;
  private String mMd5;
  private Bitmap mPhotoBitmap;
  private String mPhotoUrl;
  private View mProgressBar;
  private ProgressDialog mProgressDialog;
  private int mStatus = -1;

  private void deletePicture()
  {
    this.mProgressDialog = ProgressDialog.show(getActivity(), null, "请稍候...");
    this.mDeletePicTask = new DeletePicTask(null);
    this.mDeletePicTask.execute(new Void[0]);
  }

  private void initDataRemote(Bundle paramBundle)
  {
    if (paramBundle == null);
    String str1;
    String str2;
    while (true)
    {
      return;
      str1 = paramBundle.getString("small");
      str2 = paramBundle.getString("large");
      if ((TextUtils.isEmpty(str2)) || (str2.startsWith("http")))
        break;
      this.mLocalBitmap = FileUtil.getBmpFromFile(getActivity(), str2);
      showProgressDialog(false);
      if (this.mLocalBitmap == null)
        continue;
      this.mImageView.setImageBitmap(this.mLocalBitmap);
      return;
    }
    if (HttpConnection.checkNetworkAvailable(getActivity()) < 0)
    {
      DialogUtil.showMsgDlgStdConfirm(getActivity(), 2131427329, 2131427387, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          NewsDetailPictureFragment.this.finish();
        }
      });
      return;
    }
    if (HttpConnection.checkNetworkAvailable(getActivity()) == 0)
    {
      DialogUtil.showMsgDlgStdConfirm(getActivity(), 2131427329, 2131427388, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          NewsDetailPictureFragment.this.finish();
        }
      });
      return;
    }
    if (str2 == null)
      str2 = str1;
    this.mPhotoUrl = str2;
    ImageCache localImageCache = ImageCache.getInstance();
    Bitmap localBitmap1 = localImageCache.createSafeImage(this.mPhotoUrl);
    if (localBitmap1 != null)
    {
      showProgressDialog(false);
      this.mImageView.setImageBitmap(localBitmap1);
      return;
    }
    showProgressDialog(true);
    ImageDownLoaderManager.getInstance().displayPicture(this, this.mImageView, str2, 2130838766, new ImageDownloadCallback()
    {
      public void onImageDownloadFailed()
      {
      }

      public void onImageDownloadSuccess()
      {
        NewsDetailPictureFragment.this.showProgressDialog(false);
      }

      public void onImageDownloading()
      {
      }
    });
    if (localImageCache.isCacheFileExists(str1))
    {
      Bitmap localBitmap2 = localImageCache.createSafeImage(str1);
      this.mImageView.setImageBitmap(localBitmap2);
      this.mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
      return;
    }
    this.mImageView.setImageResource(2130838766);
  }

  private void initPhoto(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    showProgressDialog(true);
    this.mLoadPhotoTask = new LoadPhotoTask(null);
    String[] arrayOfString = { paramString };
    this.mLoadPhotoTask.execute(arrayOfString);
  }

  private void savePicture()
  {
    if (this.mPhotoUrl != null)
      try
      {
        String str = FileUtil.savePicture(this.mPhotoUrl);
        if (str != null)
        {
          getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(str))));
          showToast(2130838515, 2131427493);
          return;
        }
        showToast(-1, 2131427494);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        showToast(-1, 2131427494);
      }
  }

  private void setImage()
  {
    if (this.mPhotoBitmap != null)
    {
      this.mImageView.setImageBitmap(this.mPhotoBitmap);
      this.mDownloadBtn.setEnabled(true);
      return;
    }
    this.mImageView.setImageResource(2130838766);
  }

  private void showProgressDialog(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mProgressBar.setVisibility(0);
      return;
    }
    this.mProgressBar.setVisibility(8);
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public void onClick(View paramView)
  {
    if (paramView == this.mDownloadBtn)
    {
      savePicture();
      return;
    }
    if (paramView == this.mDeleteBtn)
    {
      DialogUtil.showMsgDlg(getActivity(), 2131428519, 2131428520, 2131427381, 2131427587, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          NewsDetailPictureFragment.this.deletePicture();
        }
      }
      , new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
        }
      });
      return;
    }
    finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903244, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if ((this.mLoadPhotoTask != null) && (!this.mLoadPhotoTask.isCancelled()) && (this.mLoadPhotoTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mLoadPhotoTask.cancel(true);
      this.mLoadPhotoTask = null;
    }
    if ((this.mDeletePicTask != null) && (!this.mDeletePicTask.isCancelled()) && (this.mDeletePicTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mDeletePicTask.cancel(true);
      this.mDeletePicTask = null;
    }
    if ((this.mLocalBitmap != null) && (!this.mLocalBitmap.isRecycled()))
      this.mLocalBitmap.recycle();
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mImageView = ((KXImageView)paramView.findViewById(2131363159));
    this.mImageView.setOnClickListener(this);
    this.mDownloadBtn = ((ImageView)paramView.findViewById(2131363160));
    this.mDownloadBtn.setOnClickListener(this);
    this.mDeleteBtn = ((ImageView)paramView.findViewById(2131363161));
    this.mDeleteBtn.setOnClickListener(this);
    this.mProgressBar = paramView.findViewById(2131363162);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      Object localObject = localBundle.getString("large");
      String str = localBundle.getString("small");
      this.mFrom = localBundle.getString("from");
      this.mCpid = localBundle.getString("cpid");
      this.mMd5 = localBundle.getString("md5");
      this.mStatus = localBundle.getInt("status");
      if ((!TextUtils.isEmpty(this.mFrom)) && (this.mFrom.equals("cloudalbum")) && (!TextUtils.isEmpty(this.mMd5)))
      {
        this.mDownloadBtn.setVisibility(8);
        if (this.mStatus == 1)
          this.mDeleteBtn.setVisibility(0);
      }
      if (localObject == null)
        localObject = str;
      if (ImageCache.getInstance().isCacheFileExists((String)localObject))
      {
        this.mPhotoUrl = ((String)localObject);
        displayPicture(this.mImageView, this.mPhotoUrl, 2130838816);
      }
    }
    else
    {
      return;
    }
    initDataRemote(localBundle);
  }

  private class DeletePicTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    private DeletePicTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      return Integer.valueOf(CloudAlbumEngine.getInstance().deletePic(NewsDetailPictureFragment.this.getActivity(), NewsDetailPictureFragment.this.mCpid, NewsDetailPictureFragment.this.mMd5));
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      DialogUtil.dismissDialog(NewsDetailPictureFragment.this.mProgressDialog);
      if (paramInteger.intValue() == 1)
      {
        Toast.makeText(NewsDetailPictureFragment.this.getActivity(), "删除成功！", 1).show();
        CloudAlbumManager.getInstance().addIgnoreFile(NewsDetailPictureFragment.this.getActivity(), NewsDetailPictureFragment.this.mMd5);
        NewsDetailPictureFragment.this.mHandler.postDelayed(new Runnable()
        {
          public void run()
          {
            if (NewsDetailPictureFragment.this.isNeedReturn())
              return;
            NewsDetailPictureFragment.this.finish();
          }
        }
        , 500L);
        return;
      }
      Toast.makeText(NewsDetailPictureFragment.this.getActivity(), "删除失败！", 1).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class LoadPhotoTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private LoadPhotoTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
        return Boolean.valueOf(false);
      try
      {
        String str = paramArrayOfString[0];
        if (TextUtils.isEmpty(str))
        {
          Boolean localBoolean3 = Boolean.valueOf(false);
          return localBoolean3;
        }
        NewsDetailPictureFragment.this.mPhotoBitmap = ImageCache.getInstance().createSafeImage(str);
        KXLog.d("NewsDetailPictureFragment", "doInBackground" + str);
        Boolean localBoolean2 = Boolean.valueOf(true);
        return localBoolean2;
      }
      catch (Exception localException)
      {
        KXLog.e("NewsDetailPictureFragment", "doInBackground", localException);
        Boolean localBoolean1 = Boolean.valueOf(false);
        return localBoolean1;
      }
      finally
      {
        CloseUtil.close(null);
      }
      throw localObject;
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      try
      {
        if (paramBoolean.booleanValue())
          NewsDetailPictureFragment.this.setImage();
        NewsDetailPictureFragment.this.showProgressDialog(false);
        return;
      }
      catch (Exception localException)
      {
        while (true)
          KXLog.e("NewsDetailPictureFragment", "onPostExecute", localException);
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
 * Qualified Name:     com.kaixin001.fragment.NewsDetailPictureFragment
 * JD-Core Version:    0.6.0
 */