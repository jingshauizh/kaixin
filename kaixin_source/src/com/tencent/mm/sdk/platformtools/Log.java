package com.tencent.mm.sdk.platformtools;

import android.os.Build;
import android.os.Build.VERSION;
import com.tencent.mm.algorithm.MD5;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

public class Log
{
  public static final int LEVEL_DEBUG = 1;
  public static final int LEVEL_ERROR = 4;
  public static final int LEVEL_FATAL = 5;
  public static final int LEVEL_INFO = 2;
  public static final int LEVEL_NONE = 6;
  public static final int LEVEL_VERBOSE = 0;
  public static final int LEVEL_WARNING = 3;
  private static PrintStream W;
  private static byte[] X;
  private static final String Y;
  private static int level = 6;

  static
  {
    X = null;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("VERSION.RELEASE:[" + Build.VERSION.RELEASE);
    localStringBuilder.append("] VERSION.CODENAME:[" + Build.VERSION.CODENAME);
    localStringBuilder.append("] VERSION.INCREMENTAL:[" + Build.VERSION.INCREMENTAL);
    localStringBuilder.append("] BOARD:[" + Build.BOARD);
    localStringBuilder.append("] DEVICE:[" + Build.DEVICE);
    localStringBuilder.append("] DISPLAY:[" + Build.DISPLAY);
    localStringBuilder.append("] FINGERPRINT:[" + Build.FINGERPRINT);
    localStringBuilder.append("] HOST:[" + Build.HOST);
    localStringBuilder.append("] MANUFACTURER:[" + Build.MANUFACTURER);
    localStringBuilder.append("] MODEL:[" + Build.MODEL);
    localStringBuilder.append("] PRODUCT:[" + Build.PRODUCT);
    localStringBuilder.append("] TAGS:[" + Build.TAGS);
    localStringBuilder.append("] TYPE:[" + Build.TYPE);
    localStringBuilder.append("] USER:[" + Build.USER + "]");
    Y = localStringBuilder.toString();
  }

  public static void d(String paramString1, String paramString2)
  {
    d(paramString1, paramString2, null);
  }

  public static void d(String paramString1, String paramString2, Object[] paramArrayOfObject)
  {
    if (level <= 1)
      if (paramArrayOfObject != null)
        break label44;
    while (true)
    {
      android.util.Log.d(paramString1, paramString2);
      LogHelper.writeToStream(W, X, "D/" + paramString1, paramString2);
      return;
      label44: paramString2 = String.format(paramString2, paramArrayOfObject);
    }
  }

  public static void e(String paramString1, String paramString2)
  {
    e(paramString1, paramString2, null);
  }

  public static void e(String paramString1, String paramString2, Object[] paramArrayOfObject)
  {
    if (level <= 4)
      if (paramArrayOfObject != null)
        break label44;
    while (true)
    {
      android.util.Log.e(paramString1, paramString2);
      LogHelper.writeToStream(W, X, "E/" + paramString1, paramString2);
      return;
      label44: paramString2 = String.format(paramString2, paramArrayOfObject);
    }
  }

  public static void f(String paramString1, String paramString2)
  {
    f(paramString1, paramString2, null);
  }

  public static void f(String paramString1, String paramString2, Object[] paramArrayOfObject)
  {
    if (level <= 5)
      if (paramArrayOfObject != null)
        break label55;
    while (true)
    {
      android.util.Log.e(paramString1, paramString2);
      LogHelper.writeToStream(W, X, "F/" + paramString1, paramString2);
      MMHandlerThread.postToMainThread(new Log.1(paramString2));
      return;
      label55: paramString2 = String.format(paramString2, paramArrayOfObject);
    }
  }

  public static int getLevel()
  {
    return level;
  }

  public static String getSysInfo()
  {
    return Y;
  }

  public static void i(String paramString1, String paramString2)
  {
    i(paramString1, paramString2, null);
  }

  public static void i(String paramString1, String paramString2, Object[] paramArrayOfObject)
  {
    if (level <= 2)
      if (paramArrayOfObject != null)
        break label44;
    while (true)
    {
      android.util.Log.i(paramString1, paramString2);
      LogHelper.writeToStream(W, X, "I/" + paramString1, paramString2);
      return;
      label44: paramString2 = String.format(paramString2, paramArrayOfObject);
    }
  }

  public static void reset()
  {
    W = null;
    X = null;
  }

  public static void setLevel(int paramInt, boolean paramBoolean)
  {
    level = paramInt;
    android.util.Log.w("MicroMsg.SDK.Log", "new log level: " + paramInt);
    if (paramBoolean)
      android.util.Log.e("MicroMsg.SDK.Log", "no jni log level support");
  }

  public static void setOutputPath(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    if ((paramString1 == null) || (paramString1.length() == 0) || (paramString3 == null) || (paramString3.length() == 0))
      return;
    while (true)
    {
      try
      {
        File localFile = new File(paramString1);
        if (!localFile.exists())
          break;
        if (localFile.length() > 0L)
        {
          localFileInputStream = new FileInputStream(paramString1);
          setOutputStream(localFileInputStream, new FileOutputStream(paramString1, true), paramString2, paramString3, paramInt);
          if (localFileInputStream == null)
            break;
          localFileInputStream.close();
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      FileInputStream localFileInputStream = null;
    }
  }

  public static void setOutputStream(InputStream paramInputStream, OutputStream paramOutputStream, String paramString1, String paramString2, int paramInt)
  {
    try
    {
      W = new PrintStream(new BufferedOutputStream(paramOutputStream));
      long l;
      if (paramInputStream != null)
      {
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
        String str = Util.nullAsNil(localBufferedReader.readLine()).substring(2).trim();
        paramString2 = Util.nullAsNil(localBufferedReader.readLine()).substring(2).trim();
        l = Util.getLong(Util.nullAsNil(localBufferedReader.readLine()).trim().substring(2), 0L);
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = str;
        arrayOfObject[1] = paramString2;
        arrayOfObject[2] = Long.valueOf(l);
        d("MicroMsg.SDK.Log", "using provided info, type=%s, user=%s, createtime=%d", arrayOfObject);
      }
      while (true)
      {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(paramString2);
        localStringBuffer.append(l);
        localStringBuffer.append("dfdhgc");
        X = MD5.getMessageDigest(localStringBuffer.toString().getBytes()).substring(7, 21).getBytes();
        android.util.Log.d("MicroMsg.SDK.Log", "set up out put stream");
        return;
        l = System.currentTimeMillis();
        LogHelper.initLogHeader(W, paramString1, paramString2, l, paramInt);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static void v(String paramString1, String paramString2)
  {
    v(paramString1, paramString2, null);
  }

  public static void v(String paramString1, String paramString2, Object[] paramArrayOfObject)
  {
    if (level <= 0)
      if (paramArrayOfObject != null)
        break label44;
    while (true)
    {
      android.util.Log.v(paramString1, paramString2);
      LogHelper.writeToStream(W, X, "V/" + paramString1, paramString2);
      return;
      label44: paramString2 = String.format(paramString2, paramArrayOfObject);
    }
  }

  public static void w(String paramString1, String paramString2)
  {
    w(paramString1, paramString2, null);
  }

  public static void w(String paramString1, String paramString2, Object[] paramArrayOfObject)
  {
    if (level <= 3)
      if (paramArrayOfObject != null)
        break label45;
    while (true)
    {
      android.util.Log.w(paramString1, paramString2);
      LogHelper.writeToStream(W, X, "W/" + paramString1, paramString2);
      return;
      label45: paramString2 = String.format(paramString2, paramArrayOfObject);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.Log
 * JD-Core Version:    0.6.0
 */