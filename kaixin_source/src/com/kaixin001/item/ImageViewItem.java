package com.kaixin001.item;

import android.widget.ImageView;
import java.lang.ref.WeakReference;

public class ImageViewItem
{
  public WeakReference<Object> dependentObject = null;
  public WeakReference<ImageView> mImgView = null;
  public String mUrl = null;

  public ImageViewItem(String paramString, ImageView paramImageView, Object paramObject)
  {
    this.mUrl = paramString;
    this.mImgView = new WeakReference(paramImageView);
    this.dependentObject = new WeakReference(paramObject);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.ImageViewItem
 * JD-Core Version:    0.6.0
 */