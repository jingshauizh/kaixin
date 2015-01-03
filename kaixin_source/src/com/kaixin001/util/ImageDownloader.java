package com.kaixin001.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;
import com.kaixin001.network.HttpProxy;
import java.lang.ref.WeakReference;
import java.util.Vector;

public class ImageDownloader
  implements Runnable
{
  public static final int GET_IMAGE_DOWNLOADING = 8003;
  public static final int GET_IMAGE_FAILED = 8002;
  public static final int GET_IMAGE_SAVE_FLOW = 8004;
  public static final int GET_IMAGE_SUCCESS = 8001;
  private static final String LOGTAG = "ImageDownloader";
  private static volatile ImageDownloader instance;
  protected static Context mContext = null;
  private boolean bIsStop = true;
  private HttpProxy httpProxy;
  private ImageCache imageCache = ImageCache.getInstance();
  private ImageDownLoaderManager imageDownLoaderManager;
  private Thread myThread = null;
  private Object objLock = new Object();
  private Vector<ImageItem> urls = new Vector();

  public static ImageDownloader getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new ImageDownloader();
      ImageDownloader localImageDownloader = instance;
      return localImageDownloader;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void recoverImageUtil(Context paramContext)
  {
    ImageCache.getInstance().setContext(paramContext);
  }

  private void sendMsg(ImageView paramImageView, String paramString1, String paramString2, int paramInt, boolean paramBoolean, ImageDownloadCallback paramImageDownloadCallback)
  {
    if ((this.imageDownLoaderManager != null) && (paramImageView != null))
    {
      String str1 = this.imageCache.getCacheBmpPath(paramString1);
      LruFileCache.getInstance().updateFile(str1);
      if (!paramBoolean)
        break label170;
    }
    label170: for (String str2 = this.imageCache.getOtherShapUrl(paramString1, paramString2); ; str2 = paramString1)
    {
      Bitmap localBitmap1 = this.imageCache.createSafeImage(str2);
      if ((!paramBoolean) && (!paramString2.equals("")))
      {
        Bitmap localBitmap2 = this.imageCache.getOtherShapBitmap(localBitmap1, paramString1, paramString2);
        String str3 = this.imageCache.getOtherShapFileByUrl(paramString1, paramString2);
        String str4 = this.imageCache.getOtherShapUrl(paramString1, paramString2);
        LruFileCache.getInstance().updateFile(str3);
        this.imageCache.addBitmapToHardCache(str4, localBitmap2);
      }
      Message localMessage = Message.obtain();
      localMessage.what = paramInt;
      localMessage.obj = new ImageDownLoaderManager.ImageMessage(this.imageCache.getOtherShapUrl(paramString1, paramString2), paramImageView, paramImageDownloadCallback);
      this.imageDownLoaderManager.addMessage(localMessage);
      return;
    }
  }

  private void start()
  {
    monitorenter;
    try
    {
      if ((!this.bIsStop) && (this.myThread != null))
      {
        boolean bool = this.myThread.isAlive();
        if (!bool);
      }
      while (true)
      {
        return;
        this.bIsStop = false;
        if ((this.myThread != null) && (this.myThread.isAlive()))
        {
          this.myThread.stop();
          this.myThread = null;
        }
        this.myThread = new Thread(this);
        this.myThread.start();
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void stop()
  {
    monitorenter;
    try
    {
      this.bIsStop = true;
      try
      {
        synchronized (this.objLock)
        {
          this.objLock.notify();
          monitorexit;
          return;
        }
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject1;
  }

  private void waitforUrl()
  {
    try
    {
      if (this.urls.size() == 0)
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

  public void cancel(Object paramObject)
  {
    monitorenter;
    while (true)
    {
      int i;
      try
      {
        synchronized (this.urls)
        {
          i = -1 + this.urls.size();
          if (i > -1)
            continue;
          monitorexit;
          return;
          ImageItem localImageItem = (ImageItem)this.urls.get(i);
          if ((localImageItem == null) || (localImageItem.dependentObject == null) || (localImageItem.dependentObject.get() == null) || (localImageItem.dependentObject.get() == paramObject))
            this.urls.remove(localImageItem);
        }
      }
      finally
      {
        monitorexit;
      }
      i--;
    }
  }

  public void clear()
  {
    stop();
    this.urls.clear();
    if (this.httpProxy != null)
      this.httpProxy.cancel();
    instance = null;
  }

  public void downloadImageAsync(Object paramObject, ImageView paramImageView, String paramString1, String paramString2, ImageDownloadCallback paramImageDownloadCallback)
  {
    monitorenter;
    try
    {
      int j;
      if ((!TextUtils.isEmpty(paramString1)) && (paramImageView != null) && (paramObject != null))
      {
        if ((this.myThread == null) || ((this.myThread != null) && (!this.myThread.isAlive())) || (isStopped().booleanValue()))
          start();
        ImageItem localImageItem1 = new ImageItem(paramObject, paramImageView, paramString1, paramString2, paramImageDownloadCallback);
        this.urls.add(localImageItem1);
        int i = this.urls.size();
        j = i - 2;
        if (j > -1)
          break label124;
      }
      try
      {
        synchronized (this.objLock)
        {
          this.objLock.notify();
          monitorexit;
          return;
          label124: ImageItem localImageItem2 = (ImageItem)this.urls.get(j);
          if ((localImageItem2 == null) || (localImageItem2.dependentObject == null) || (localImageItem2.dependentObject.get() == null) || (localImageItem2.imageView == null) || (localImageItem2.imageView.get() == null))
            this.urls.remove(j);
          j--;
        }
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject1;
  }

  public boolean downloadImageSync(String paramString)
  {
    if ((ImageCache.getInstance().getContext() == null) && (mContext != null))
      recoverImageUtil(mContext);
    String str = this.imageCache.getCacheBmpPath(paramString);
    if (mContext != null)
    {
      this.httpProxy = new HttpProxy(mContext);
      try
      {
        boolean bool2 = this.httpProxy.httpDownload(paramString, str, false, null, null);
        bool1 = bool2;
        return bool1;
      }
      catch (Exception localException)
      {
        while (true)
        {
          KXLog.e("ImageDownloader", "downloadImageSync error", localException);
          boolean bool1 = false;
        }
      }
    }
    KXLog.e("ImageDownloader ", "mThreadContext = null");
    return false;
  }

  public Context getContext()
  {
    return mContext;
  }

  public Boolean isStopped()
  {
    monitorenter;
    try
    {
      Boolean localBoolean = Boolean.valueOf(this.bIsStop);
      monitorexit;
      return localBoolean;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void run()
  {
    try
    {
      waitforUrl();
      while (true)
      {
        int i = this.urls.size();
        if (i <= 0);
        do
        {
          this.bIsStop = true;
          return;
        }
        while (this.bIsStop);
        int j = this.urls.size();
        localImageItem = (ImageItem)this.urls.remove(j - 1);
        if ((localImageItem == null) || (localImageItem.dependentObject == null) || (localImageItem.imageView == null) || (localImageItem.imageView.get() == null) || (localImageItem.dependentObject.get() == null))
          continue;
        if (!this.imageCache.isCacheFileExists(this.imageCache.getOtherShapUrl(localImageItem.url, localImageItem.imageShapType)))
          break;
        sendMsg((ImageView)localImageItem.imageView.get(), localImageItem.url, localImageItem.imageShapType, localImageItem.type, true, localImageItem.callback);
        waitforUrl();
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        ImageItem localImageItem;
        KXLog.e("ImageEngine", "run", localException);
        continue;
        if (this.imageCache.isCacheFileExists(localImageItem.url))
        {
          sendMsg((ImageView)localImageItem.imageView.get(), localImageItem.url, localImageItem.imageShapType, localImageItem.type, false, localImageItem.callback);
          continue;
        }
        if (this.bIsStop)
          continue;
        if (localImageItem.callback != null)
        {
          Message localMessage2 = Message.obtain();
          localMessage2.what = 8003;
          localMessage2.obj = new ImageDownLoaderManager.ImageMessage(this.imageCache.getOtherShapUrl(localImageItem.url, localImageItem.imageShapType), (ImageView)localImageItem.imageView.get(), localImageItem.callback);
          this.imageDownLoaderManager.addMessage(localMessage2);
        }
        if (downloadImageSync(localImageItem.url))
        {
          sendMsg((ImageView)localImageItem.imageView.get(), localImageItem.url, localImageItem.imageShapType, localImageItem.type, false, localImageItem.callback);
          continue;
        }
        Message localMessage1 = Message.obtain();
        localMessage1.what = 8002;
        localMessage1.obj = new ImageDownLoaderManager.ImageMessage(this.imageCache.getOtherShapUrl(localImageItem.url, localImageItem.imageShapType), (ImageView)localImageItem.imageView.get(), localImageItem.callback);
        this.imageDownLoaderManager.addMessage(localMessage1);
      }
    }
  }

  public void setContext(Context paramContext)
  {
    if (mContext == null)
      mContext = paramContext;
    if ((ImageCache.getInstance().getContext() == null) && (mContext != null))
      recoverImageUtil(mContext);
    this.imageDownLoaderManager = ImageDownLoaderManager.getInstance();
    this.imageCache = ImageCache.getInstance();
  }

  public static class ImageItem
  {
    public ImageDownloadCallback callback;
    public WeakReference<Object> dependentObject;
    public String imageShapType = "";
    public WeakReference<ImageView> imageView;
    public int type = 8001;
    public String url;

    public ImageItem(Object paramObject, ImageView paramImageView, String paramString1, String paramString2, ImageDownloadCallback paramImageDownloadCallback)
    {
      this.dependentObject = new WeakReference(paramObject);
      this.imageView = new WeakReference(paramImageView);
      this.url = paramString1;
      this.type = 8001;
      this.imageShapType = paramString2;
      this.callback = paramImageDownloadCallback;
    }
  }

  public static enum RoundCornerType
  {
    static
    {
      RoundCornerType[] arrayOfRoundCornerType = new RoundCornerType[4];
      arrayOfRoundCornerType[0] = hdpi_big;
      arrayOfRoundCornerType[1] = hdpi_small;
      arrayOfRoundCornerType[2] = mdpi_big;
      arrayOfRoundCornerType[3] = mdpi_small;
      ENUM$VALUES = arrayOfRoundCornerType;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.ImageDownloader
 * JD-Core Version:    0.6.0
 */