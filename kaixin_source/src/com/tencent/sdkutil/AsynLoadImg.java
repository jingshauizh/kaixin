package com.tencent.sdkutil;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsynLoadImg
{
  private static String ALBUM_PATH;
  private static final String TAG = "AsynLoadImg";
  private String imgUrl;
  private String localImageLocalPath = "";
  private Handler messageHandler = new AsynLoadImg.1(this);
  private AsynLoadImg.AsynLoadImgBack saveCallBack;
  private Runnable saveFileRunnable = new AsynLoadImg.2(this);
  private long startTime;

  public static Bitmap getbitmap(String paramString)
  {
    Log.v("AsynLoadImg", "getbitmap:" + paramString);
    try
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      localHttpURLConnection.setDoInput(true);
      localHttpURLConnection.connect();
      InputStream localInputStream = localHttpURLConnection.getInputStream();
      Bitmap localBitmap = BitmapFactory.decodeStream(localInputStream);
      localInputStream.close();
      Log.v("AsynLoadImg", "image download finished." + paramString);
      return localBitmap;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      Log.v("AsynLoadImg", "getbitmap bmp fail---");
    }
    return null;
  }

  public String getLocalImageLocalPath()
  {
    return this.localImageLocalPath;
  }

  public void save(String paramString, AsynLoadImg.AsynLoadImgBack paramAsynLoadImgBack)
  {
    Log.v("AsynLoadImg", "--save---");
    if ((paramString == null) || (paramString.equals("")))
    {
      paramAsynLoadImgBack.saved(1, null);
      return;
    }
    if (!Util.checkSd())
    {
      paramAsynLoadImgBack.saved(2, null);
      return;
    }
    ALBUM_PATH = Environment.getExternalStorageDirectory() + "/tmp/";
    this.startTime = System.currentTimeMillis();
    this.imgUrl = paramString;
    this.saveCallBack = paramAsynLoadImgBack;
    new Thread(this.saveFileRunnable).start();
  }

  public boolean saveFile(Bitmap paramBitmap, String paramString)
  {
    String str1 = ALBUM_PATH;
    try
    {
      File localFile = new File(str1);
      if (!localFile.exists())
        localFile.mkdir();
      String str2 = str1 + paramString;
      Log.v("AsynLoadImg", "saveFile:" + paramString);
      BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(str2)));
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, 80, localBufferedOutputStream);
      localBufferedOutputStream.flush();
      localBufferedOutputStream.close();
      return true;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      Log.v("AsynLoadImg", "saveFile bmp fail---");
    }
    return false;
  }

  public void setLocalImageLocalPath(String paramString)
  {
    this.localImageLocalPath = paramString;
    Log.e("AsynLoadImg", "localImageLocalPath:" + this.localImageLocalPath);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.AsynLoadImg
 * JD-Core Version:    0.6.0
 */