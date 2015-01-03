package com.kaixin001.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.item.ImageViewItem;
import com.kaixin001.view.KXSaveFlowImageView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ImageDownLoaderManager
  implements Runnable
{
  public static final String TAG = "ImageDownLoaderManager";
  private static volatile ImageDownLoaderManager instance;
  private Object addLock = new Object();
  private boolean bIsRunning = false;
  private boolean bIsStop = true;
  public boolean isCanLoad = true;
  private Context mContext;
  public Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      if (paramMessage.what == 8001)
        ImageDownLoaderManager.this.updateImage((ImageDownLoaderManager.ImageMessage)paramMessage.obj);
      do
      {
        return;
        if (paramMessage.what != 8002)
          continue;
        ImageDownLoaderManager.this.onUpdateImageFailed((ImageDownLoaderManager.ImageMessage)paramMessage.obj);
        return;
      }
      while (paramMessage.what != 8003);
      ImageDownLoaderManager.this.onUpdateImageDownloading((ImageDownLoaderManager.ImageMessage)paramMessage.obj);
    }
  };
  private volatile ArrayList<ImageViewItem> mImageViewItems = new ArrayList();
  private ImageDownloader mImgDownloader = ImageDownloader.getInstance();
  private volatile ArrayList<Message> messageList = new ArrayList();
  public Object objLock = new Object();

  public static ImageDownLoaderManager getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new ImageDownLoaderManager();
      ImageDownLoaderManager localImageDownLoaderManager = instance;
      return localImageDownLoaderManager;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void start()
  {
    monitorenter;
    try
    {
      boolean bool = this.bIsStop;
      if (!bool);
      while (true)
      {
        return;
        this.bIsStop = false;
        if (this.bIsRunning)
          continue;
        this.bIsRunning = true;
        new Thread(this).start();
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void waitForMessageSignal()
  {
    try
    {
      if (this.messageList.size() == 0)
        synchronized (this.addLock)
        {
          this.addLock.wait();
          return;
        }
    }
    catch (Exception localException)
    {
      KXLog.e("KXDownloadPicActivity", "waitForMessageSignal", localException);
    }
  }

  private void waitForSignal()
  {
    try
    {
      if (!this.isCanLoad)
        synchronized (this.objLock)
        {
          this.objLock.wait();
          return;
        }
    }
    catch (Exception localException)
    {
      KXLog.e("KXDownloadPicActivity", "waitForSignal", localException);
    }
  }

  public void addMessage(Message paramMessage)
  {
    synchronized (this.messageList)
    {
      this.messageList.add(paramMessage);
    }
    try
    {
      synchronized (this.addLock)
      {
        this.addLock.notify();
        return;
        localObject1 = finally;
        monitorexit;
        throw localObject1;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void cancel(Object paramObject)
  {
    this.mImgDownloader.cancel(paramObject);
    cancelImageViewItem(paramObject);
  }

  protected void cancelImageViewItem(Object paramObject)
  {
    if (paramObject == null)
      return;
    int i = -1 + this.mImageViewItems.size();
    label15: ImageViewItem localImageViewItem;
    if (i > -1)
    {
      localImageViewItem = (ImageViewItem)this.mImageViewItems.get(i);
      if (localImageViewItem != null)
        break label51;
      this.mImageViewItems.remove(i);
    }
    while (true)
    {
      i--;
      break label15;
      break;
      label51: if (localImageViewItem.dependentObject != paramObject)
        continue;
      this.mImageViewItems.remove(i);
    }
  }

  public void displayOtherShapPicture(Object paramObject, ImageView paramImageView, String paramString1, String paramString2, int paramInt, ImageDownloadCallback paramImageDownloadCallback)
  {
    if (paramImageView == null)
      return;
    removeImageViewItem(paramObject, paramImageView);
    if ((TextUtils.isEmpty(paramString1)) && (paramInt > 0))
    {
      paramImageView.setImageBitmap(ImageCache.getInstance().getLoadingBitmap(paramInt, 0, 0));
      return;
    }
    String str = ImageCache.getInstance().getOtherShapUrl(paramString1, paramString2);
    Bitmap localBitmap;
    try
    {
      localBitmap = ImageCache.getInstance().loadMemoryCacheImage(str);
      if (localBitmap == null)
        break label125;
      if ((paramImageView.getTag() != null) && (paramImageView.getTag().equals("BG")) && (localBitmap != null))
      {
        paramImageView.setBackgroundDrawable(new BitmapDrawable(this.mContext.getResources(), localBitmap));
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    paramImageView.setImageBitmap(localBitmap);
    return;
    label125: if (paramInt != 0)
    {
      if ((paramImageView.getTag() == null) || (!paramImageView.getTag().equals("BG")))
        break label225;
      paramImageView.setBackgroundDrawable(new BitmapDrawable(ImageCache.getInstance().getLoadingBitmap(paramInt, 0, 0)));
    }
    while (true)
    {
      KXLog.d("ImageDownLoaderManager", "mImageViewItems.add");
      if (isStopped().booleanValue())
        start();
      this.mImageViewItems.add(new ImageViewItem(str, paramImageView, paramObject));
      this.mImgDownloader.downloadImageAsync(paramObject, paramImageView, paramString1, paramString2, paramImageDownloadCallback);
      return;
      label225: paramImageView.setImageBitmap(ImageCache.getInstance().getLoadingBitmap(paramInt, 0, 0));
    }
  }

  public void displayPicture(Object paramObject, ImageView paramImageView, String paramString, int paramInt, ImageDownloadCallback paramImageDownloadCallback)
  {
    if (paramImageView == null);
    while (true)
    {
      return;
      removeImageViewItem(paramObject, paramImageView);
      if (TextUtils.isEmpty(paramString))
      {
        if ((paramImageView instanceof KXSaveFlowImageView))
          ((KXSaveFlowImageView)paramImageView).setState(3);
        paramImageView.setImageBitmap(ImageCache.getInstance().getLoadingBitmap(paramInt, 0, 0));
        return;
      }
      Bitmap localBitmap;
      try
      {
        localBitmap = ImageCache.getInstance().loadMemoryCacheImage(paramString);
        if (localBitmap == null)
          break;
        if ((paramImageView.getTag() != null) && (paramImageView.getTag().equals("BG")) && (localBitmap != null))
        {
          paramImageView.setBackgroundDrawable(new BitmapDrawable(this.mContext.getResources(), localBitmap));
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      if (localBitmap == null)
        continue;
      paramImageView.setImageBitmap(localBitmap);
      if (!(paramImageView instanceof KXSaveFlowImageView))
        continue;
      ((KXSaveFlowImageView)paramImageView).setState(3);
      return;
    }
    if (paramInt != 0)
    {
      if ((paramImageView.getTag() != null) && (paramImageView.getTag().equals("BG")))
        paramImageView.setBackgroundDrawable(new BitmapDrawable(ImageCache.getInstance().getLoadingBitmap(paramInt, 0, 0)));
    }
    else
    {
      label188: KXLog.d("ImageDownLoaderManager", "mImageViewItems.add");
      if (isStopped().booleanValue())
        start();
      if ((paramImageView instanceof KXSaveFlowImageView))
      {
        if (!ImageCache.getInstance().hasCacheBmp(paramString))
          break label284;
        ((KXSaveFlowImageView)paramImageView).setState(2);
      }
    }
    while (true)
    {
      this.mImageViewItems.add(new ImageViewItem(paramString, paramImageView, paramObject));
      this.mImgDownloader.downloadImageAsync(paramObject, paramImageView, paramString, "", paramImageDownloadCallback);
      return;
      paramImageView.setImageBitmap(ImageCache.getInstance().getLoadingBitmap(paramInt, 0, 0));
      break label188;
      label284: if (!((KXSaveFlowImageView)paramImageView).saveFlowState())
        continue;
      if ((KXEnvironment.saveFlowOpen()) && (!KXEnvironment.wifiEnabled()))
        break;
      ((KXSaveFlowImageView)paramImageView).startDownloading();
    }
  }

  public void displayPicture(Object paramObject, ImageView paramImageView, String paramString1, String paramString2, int paramInt, ImageDownloadCallback paramImageDownloadCallback)
  {
    if ((paramObject == null) || (paramImageView == null))
      return;
    removeImageViewItem(paramObject, paramImageView);
    try
    {
      Bitmap localBitmap1 = ImageCache.getInstance().loadMemoryCacheImage(paramString2);
      if (localBitmap1 != null)
      {
        paramImageView.setImageBitmap(localBitmap1);
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    Bitmap localBitmap2 = ImageCache.getInstance().createSafeImage(paramString1);
    if (localBitmap2 != null)
      paramImageView.setImageBitmap(localBitmap2);
    while (true)
    {
      if (isStopped().booleanValue())
        start();
      this.mImageViewItems.add(new ImageViewItem(paramString2, paramImageView, paramObject));
      this.mImgDownloader.downloadImageAsync(paramObject, paramImageView, paramString2, "", paramImageDownloadCallback);
      return;
      paramImageView.setImageResource(paramInt);
    }
  }

  public void displayPictureExt(Object paramObject, ImageView paramImageView, String paramString, int paramInt, ImageDownloadCallback paramImageDownloadCallback)
  {
    if (paramImageView == null);
    while (true)
    {
      return;
      removeImageViewItem(paramObject, paramImageView);
      if (TextUtils.isEmpty(paramString))
      {
        paramImageView.setImageBitmap(ImageCache.getInstance().getLoadingBitmap(paramInt, 0, 0));
        return;
      }
      Bitmap localBitmap;
      try
      {
        localBitmap = ImageCache.getInstance().createSafeImage(paramString);
        if (localBitmap == null)
          break;
        if ((paramImageView.getTag() != null) && (paramImageView.getTag().equals("BG")) && (localBitmap != null))
        {
          paramImageView.setBackgroundDrawable(new BitmapDrawable(this.mContext.getResources(), localBitmap));
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      if (localBitmap == null)
        continue;
      paramImageView.setImageBitmap(localBitmap);
      return;
    }
    if (paramInt != 0)
    {
      if ((paramImageView.getTag() == null) || (!paramImageView.getTag().equals("BG")))
        break label212;
      paramImageView.setBackgroundDrawable(new BitmapDrawable(ImageCache.getInstance().getLoadingBitmap(paramInt, 0, 0)));
    }
    while (true)
    {
      KXLog.d("ImageDownLoaderManager", "mImageViewItems.add");
      if (isStopped().booleanValue())
        start();
      this.mImageViewItems.add(new ImageViewItem(paramString, paramImageView, paramObject));
      this.mImgDownloader.downloadImageAsync(paramObject, paramImageView, paramString, "", paramImageDownloadCallback);
      return;
      label212: paramImageView.setImageBitmap(ImageCache.getInstance().getLoadingBitmap(paramInt, 0, 0));
    }
  }

  public boolean getIsCanLoad()
  {
    return this.isCanLoad;
  }

  protected boolean isImageViewItemExist(ImageView paramImageView, String paramString)
  {
    if (paramImageView == null);
    while (true)
    {
      return false;
      for (int i = -1 + this.mImageViewItems.size(); i > -1; i--)
      {
        ImageViewItem localImageViewItem = (ImageViewItem)this.mImageViewItems.get(i);
        if ((localImageViewItem != null) && (localImageViewItem.mImgView != null) && (localImageViewItem.mImgView.get() == paramImageView) && (localImageViewItem.mUrl.equals(paramString)))
          return true;
      }
    }
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

  protected void onUpdateImageDownloading(ImageMessage paramImageMessage)
  {
    if (paramImageMessage.imageView == null);
    do
      return;
    while (paramImageMessage.callback == null);
    paramImageMessage.callback.onImageDownloading();
  }

  protected void onUpdateImageFailed(ImageMessage paramImageMessage)
  {
    if (paramImageMessage.imageView == null);
    do
      return;
    while (paramImageMessage.callback == null);
    paramImageMessage.callback.onImageDownloadFailed();
  }

  public ImageView removeImageViewItem(ImageView paramImageView)
  {
    if (paramImageView == null)
    {
      paramImageView = null;
      return paramImageView;
    }
    int i = -1 + this.mImageViewItems.size();
    label18: ImageViewItem localImageViewItem;
    if (i > -1)
    {
      localImageViewItem = (ImageViewItem)this.mImageViewItems.get(i);
      if ((localImageViewItem != null) && (localImageViewItem.dependentObject != null) && (localImageViewItem.dependentObject.get() != null) && (localImageViewItem.mImgView != null) && (localImageViewItem.mImgView.get() != null))
        break label88;
      this.mImageViewItems.remove(i);
    }
    label88: 
    do
    {
      i--;
      break label18;
      break;
    }
    while ((localImageViewItem.mImgView == null) || (localImageViewItem.mImgView.get() != paramImageView));
    this.mImageViewItems.remove(i);
    return paramImageView;
  }

  protected ImageView removeImageViewItem(Object paramObject, ImageView paramImageView)
  {
    if (paramImageView == null)
    {
      paramImageView = null;
      return paramImageView;
    }
    int i = -1 + this.mImageViewItems.size();
    label18: ImageViewItem localImageViewItem;
    if (i > -1)
    {
      localImageViewItem = (ImageViewItem)this.mImageViewItems.get(i);
      if ((localImageViewItem != null) && (localImageViewItem.dependentObject != null) && (localImageViewItem.dependentObject.get() != null) && (localImageViewItem.mImgView != null) && (localImageViewItem.mImgView.get() != null))
        break label94;
      this.mImageViewItems.remove(i);
    }
    label94: 
    do
    {
      i--;
      break label18;
      break;
    }
    while ((localImageViewItem.mImgView == null) || (localImageViewItem.mImgView.get() != paramImageView) || (localImageViewItem.dependentObject.get() != paramObject));
    this.mImageViewItems.remove(i);
    return paramImageView;
  }

  public void run()
  {
    try
    {
      waitForMessageSignal();
      int i = this.messageList.size();
      if (i <= 0);
      do
      {
        this.bIsRunning = false;
        return;
      }
      while (this.bIsStop);
      waitForSignal();
      j = this.messageList.size();
    }
    catch (Exception localException)
    {
      synchronized (this.messageList)
      {
        while (true)
        {
          int j;
          Message localMessage = (Message)this.messageList.remove(j - 1);
          if (localMessage == null)
            continue;
          ImageMessage localImageMessage = (ImageMessage)localMessage.obj;
          if (isImageViewItemExist(localImageMessage.imageView, localImageMessage.url))
            this.mHandler.sendMessage(localMessage);
          waitForMessageSignal();
        }
        localException = localException;
        KXLog.e("KXDownloadPicActivity", "run", localException);
      }
    }
  }

  public void setCanLoad()
  {
    try
    {
      this.isCanLoad = true;
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

  public void setContext(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public void setNotCanLoad()
  {
    this.isCanLoad = false;
  }

  // ERROR //
  public void stop()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iconst_1
    //   4: putfield 55	com/kaixin001/util/ImageDownLoaderManager:bIsStop	Z
    //   7: aload_0
    //   8: getfield 51	com/kaixin001/util/ImageDownLoaderManager:objLock	Ljava/lang/Object;
    //   11: astore_3
    //   12: aload_3
    //   13: monitorenter
    //   14: aload_0
    //   15: getfield 51	com/kaixin001/util/ImageDownLoaderManager:objLock	Ljava/lang/Object;
    //   18: invokevirtual 108	java/lang/Object:notify	()V
    //   21: aload_3
    //   22: monitorexit
    //   23: aload_0
    //   24: getfield 53	com/kaixin001/util/ImageDownLoaderManager:addLock	Ljava/lang/Object;
    //   27: astore 5
    //   29: aload 5
    //   31: monitorenter
    //   32: aload_0
    //   33: getfield 53	com/kaixin001/util/ImageDownLoaderManager:addLock	Ljava/lang/Object;
    //   36: invokevirtual 108	java/lang/Object:notify	()V
    //   39: aload 5
    //   41: monitorexit
    //   42: aload_0
    //   43: monitorexit
    //   44: return
    //   45: astore 4
    //   47: aload_3
    //   48: monitorexit
    //   49: aload 4
    //   51: athrow
    //   52: astore_2
    //   53: aload_2
    //   54: invokevirtual 111	java/lang/Exception:printStackTrace	()V
    //   57: goto -15 -> 42
    //   60: astore_1
    //   61: aload_0
    //   62: monitorexit
    //   63: aload_1
    //   64: athrow
    //   65: astore 6
    //   67: aload 5
    //   69: monitorexit
    //   70: aload 6
    //   72: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   14	23	45	finally
    //   47	49	45	finally
    //   7	14	52	java/lang/Exception
    //   23	32	52	java/lang/Exception
    //   49	52	52	java/lang/Exception
    //   70	73	52	java/lang/Exception
    //   2	7	60	finally
    //   7	14	60	finally
    //   23	32	60	finally
    //   49	52	60	finally
    //   53	57	60	finally
    //   70	73	60	finally
    //   32	42	65	finally
    //   67	70	65	finally
  }

  protected void updateImage(ImageMessage paramImageMessage)
  {
    ImageView localImageView = paramImageMessage.imageView;
    if (localImageView == null);
    while (true)
    {
      return;
      removeImageViewItem(localImageView);
      Bitmap localBitmap = ImageCache.getInstance().createSafeImage(paramImageMessage.url);
      if ((localImageView.getTag() != null) && (localImageView.getTag().equals("BG")) && (localBitmap != null))
        localImageView.setBackgroundDrawable(new BitmapDrawable(this.mContext.getResources(), localBitmap));
      while (paramImageMessage.callback != null)
      {
        paramImageMessage.callback.onImageDownloadSuccess();
        return;
        if (localBitmap == null)
          continue;
        localImageView.setImageBitmap(localBitmap);
      }
    }
  }

  public static class ImageMessage
  {
    ImageDownloadCallback callback;
    ImageView imageView;
    String url;

    public ImageMessage(String paramString, ImageView paramImageView, ImageDownloadCallback paramImageDownloadCallback)
    {
      this.url = paramString;
      this.imageView = paramImageView;
      this.callback = paramImageDownloadCallback;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.ImageDownLoaderManager
 * JD-Core Version:    0.6.0
 */