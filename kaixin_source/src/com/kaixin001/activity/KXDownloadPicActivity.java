package com.kaixin001.activity;

import android.os.Handler;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.kaixin001.util.ImageDownLoaderManager;
import com.kaixin001.util.ImageDownloader.RoundCornerType;

public abstract class KXDownloadPicActivity extends KXActivity
{
  public static final String TAG = "KXDownloadPicActivity";
  private ImageDownLoaderManager imageDownLoaderManager = ImageDownLoaderManager.getInstance();

  public void displayPicture(ImageView paramImageView, String paramString, int paramInt)
  {
    this.imageDownLoaderManager.displayPicture(this, paramImageView, paramString, paramInt, null);
  }

  public void displayPicture(ImageView paramImageView, String paramString1, String paramString2, int paramInt)
  {
    this.imageDownLoaderManager.displayPicture(this, paramImageView, paramString1, paramString2, paramInt, null);
  }

  public void displayPictureCancel(ImageView paramImageView)
  {
    this.imageDownLoaderManager.removeImageViewItem(paramImageView);
  }

  public void displayPictureExt(ImageView paramImageView, String paramString, int paramInt)
  {
    this.imageDownLoaderManager.displayPictureExt(this, paramImageView, paramString, paramInt, null);
  }

  public void displayRoundPicture(ImageView paramImageView, String paramString, ImageDownloader.RoundCornerType paramRoundCornerType, int paramInt)
  {
    this.imageDownLoaderManager.displayOtherShapPicture(this, paramImageView, paramString, "round", paramInt, null);
  }

  public Handler getHandler()
  {
    return this.mHandler;
  }

  public boolean getIsCanLoad()
  {
    return this.imageDownLoaderManager.getIsCanLoad();
  }

  protected void onPause()
  {
    this.imageDownLoaderManager.cancel(this);
    super.onPause();
  }

  public void setCanLoad()
  {
    this.imageDownLoaderManager.setCanLoad();
  }

  public void setNotCanLoad()
  {
    this.imageDownLoaderManager.setNotCanLoad();
  }

  public void showVideoThumbnail(ImageView paramImageView, String paramString)
  {
    if (paramImageView == null)
      return;
    paramImageView.setVisibility(0);
    paramImageView.setTag("BG");
    displayPicture(paramImageView, paramString, 2130838784);
    paramImageView.setImageResource(2130838791);
    paramImageView.setScaleType(ImageView.ScaleType.CENTER);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.KXDownloadPicActivity
 * JD-Core Version:    0.6.0
 */