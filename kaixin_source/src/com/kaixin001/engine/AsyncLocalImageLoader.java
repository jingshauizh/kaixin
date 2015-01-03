package com.kaixin001.engine;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Message;
import android.provider.MediaStore.Images.Thumbnails;
import android.text.TextUtils;
import android.widget.ImageView;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.KXLog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Vector;

public class AsyncLocalImageLoader
  implements Runnable
{
  public static final int TYPE_FILE_NAME;
  public static final int TYPE_MEDIA_ID;
  private Activity mContext;
  private final Vector<ImageItem> mImages = new Vector();
  private boolean mStop = false;
  private Thread mThread = new Thread(this);
  private final Object objLock = new Object();

  public AsyncLocalImageLoader(Activity paramActivity)
  {
    this.mContext = paramActivity;
    this.mThread.start();
  }

  private void notifyThread()
  {
    try
    {
      synchronized (this.objLock)
      {
        this.objLock.notify();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void waitForImage()
  {
    try
    {
      if (this.mImages.size() == 0)
        synchronized (this.objLock)
        {
          this.objLock.wait();
          return;
        }
    }
    catch (Exception localException)
    {
      KXLog.e("ImageDownloader", "waitforUrl", localException);
    }
  }

  protected void finalize()
    throws Throwable
  {
    stop();
    super.finalize();
  }

  public void run()
  {
    while (true)
    {
      if (this.mStop);
      do
      {
        return;
        waitForImage();
      }
      while (this.mStop);
      int i;
      synchronized (this.mImages)
      {
        i = this.mImages.size();
        if (i > 0);
      }
      ImageItem localImageItem = (ImageItem)this.mImages.remove(i - 1);
      monitorexit;
      if ((localImageItem == null) || (localImageItem.imageView.get() == null) || (TextUtils.isEmpty(localImageItem.imageSrc)))
        continue;
      long l1;
      if (localImageItem.imageType == 0)
        l1 = 0L;
      Bitmap localBitmap;
      while (true)
      {
        try
        {
          long l2 = Long.parseLong(localImageItem.imageSrc);
          l1 = l2;
          localBitmap = MediaStore.Images.Thumbnails.getThumbnail(this.mContext.getContentResolver(), l1, 3, null);
          if ((localImageItem.mMsgList == null) || (localImageItem.mMsgListDealLock == null))
            break label302;
          Message localMessage = Message.obtain();
          localMessage.what = 8001;
          String str = ImageCache.getInstance().getFileByUrl(localImageItem.imageSrc);
          ImageCache.saveBitmapToFileWithAbsolutePath(this.mContext, localBitmap, null, str);
          ImageCache.getInstance().addBitmapToHardCache(localImageItem.imageSrc, localBitmap);
          localMessage.obj = localImageItem.imageSrc;
          localImageItem.mMsgList.add(localMessage);
          try
          {
            synchronized (localImageItem.mMsgListDealLock)
            {
              localImageItem.mMsgListDealLock.notify();
            }
          }
          catch (Exception localException1)
          {
            localException1.printStackTrace();
          }
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
          continue;
        }
        if (localImageItem.imageType == 0)
        {
          localBitmap = ImageCache.loadBitmapFromFile(localImageItem.imageSrc, true);
          continue;
        }
        localBitmap = null;
      }
      label302: this.mContext.runOnUiThread(new Runnable(localImageItem, localBitmap)
      {
        public void run()
        {
          try
          {
            if ((this.val$item != null) && (this.val$item.imageView != null) && (this.val$item.imageView.get() != null))
              ((ImageView)this.val$item.imageView.get()).setImageBitmap(this.val$bmp);
            return;
          }
          catch (Exception localException)
          {
            KXLog.e("ImageDownloader", "runOnUiThread", localException);
          }
        }
      });
    }
  }

  public void showImage(ImageView paramImageView, String paramString, int paramInt)
  {
    monitorenter;
    try
    {
      ImageItem localImageItem1 = new ImageItem(paramImageView, paramString, paramInt, null, null);
      synchronized (this.mImages)
      {
        this.mImages.add(localImageItem1);
        try
        {
          int i = this.mImages.size();
          for (int j = i - 2; ; j--)
          {
            if (j <= -1)
            {
              notifyThread();
              monitorexit;
              return;
            }
            ImageItem localImageItem2 = (ImageItem)this.mImages.get(j);
            if ((localImageItem2 != null) && (localImageItem2.imageView.get() != null) && (paramImageView != localImageItem2.imageView.get()))
              continue;
            this.mImages.remove(j);
          }
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject1;
  }

  public void showImage(ImageView paramImageView, String paramString, int paramInt, ArrayList<Message> paramArrayList, Object paramObject)
  {
    monitorenter;
    try
    {
      ImageItem localImageItem1 = new ImageItem(paramImageView, paramString, paramInt, paramArrayList, paramObject);
      synchronized (this.mImages)
      {
        int i = -2 + this.mImages.size();
        while (true)
        {
          if (i <= -1)
            this.mImages.add(localImageItem1);
          try
          {
            int j = this.mImages.size();
            for (int k = j - 2; ; k--)
            {
              if (k <= -1)
              {
                notifyThread();
                monitorexit;
                return;
                ImageItem localImageItem3 = (ImageItem)this.mImages.get(i);
                if ((localImageItem3.imageView == null) || (localImageItem3.imageView.get() == paramImageView))
                  this.mImages.remove(i);
                i--;
                break;
              }
              ImageItem localImageItem2 = (ImageItem)this.mImages.get(k);
              if ((localImageItem2 != null) && (localImageItem2.imageView.get() != null) && (paramImageView != localImageItem2.imageView.get()))
                continue;
              this.mImages.remove(k);
            }
          }
          catch (Exception localException)
          {
            while (true)
              localException.printStackTrace();
          }
        }
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject1;
  }

  public void showImageCancel(ImageView paramImageView)
  {
    monitorenter;
    if (paramImageView == null)
    {
      monitorexit;
      return;
    }
    while (true)
    {
      int i;
      try
      {
        synchronized (this.mImages)
        {
          i = -2 + this.mImages.size();
          if (i > -1);
        }
      }
      finally
      {
        monitorexit;
      }
      ImageItem localImageItem = (ImageItem)this.mImages.get(i);
      if ((localImageItem.imageView == null) || (localImageItem.imageView.get() == paramImageView))
        this.mImages.remove(i);
      i--;
    }
  }

  public void stop()
  {
    monitorenter;
    try
    {
      this.mStop = true;
      notify();
      this.mImages.clear();
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  private class ImageItem
  {
    public String imageSrc;
    public int imageType;
    public WeakReference<ImageView> imageView;
    public ArrayList<Message> mMsgList;
    public Object mMsgListDealLock;

    public ImageItem(String paramInt, int paramArrayList, ArrayList<Message> paramObject, Object arg5)
    {
      this.imageView = new WeakReference(paramInt);
      this.imageSrc = paramArrayList;
      this.imageType = paramObject;
      Object localObject1;
      this.mMsgList = localObject1;
      Object localObject2;
      this.mMsgListDealLock = localObject2;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.AsyncLocalImageLoader
 * JD-Core Version:    0.6.0
 */