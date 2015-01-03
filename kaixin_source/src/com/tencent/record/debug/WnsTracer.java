package com.tencent.record.debug;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Environment;
import com.tencent.record.a.b;
import com.tencent.record.a.c;
import java.io.File;

public class WnsTracer
  implements SharedPreferences.OnSharedPreferenceChangeListener, TraceLevel
{
  protected static final e CLIENT_CONFIG;
  protected static final e SERVICE_CONFIG;
  private volatile boolean enabled = true;
  protected a fileTracer;
  private volatile boolean fileTracerEnabled = true;
  private volatile boolean logcatTracerEnabled = true;

  static
  {
    File localFile = getLogFilePath();
    CLIENT_CONFIG = new e(localFile, 24, 262144, 8192, "OpenSDK.Client.File.Tracer", 10000L, 10, ".app.log", 604800000L);
    SERVICE_CONFIG = new e(localFile, 24, 262144, 8192, "OpenSDK.File.Tracer", 10000L, 10, ".OpenSDK.log", 604800000L);
  }

  public static void deleteFile(File paramFile)
  {
    if ((paramFile == null) || (!paramFile.exists()));
    while (true)
    {
      return;
      if (paramFile.isFile())
      {
        paramFile.delete();
        return;
      }
      File[] arrayOfFile = paramFile.listFiles();
      int i = arrayOfFile.length;
      for (int j = 0; j < i; j++)
        deleteFile(arrayOfFile[j]);
    }
  }

  public static File getLogFilePath()
  {
    String str = b.a + File.separator + c.b();
    com.tencent.record.a.e locale = com.tencent.record.a.a.b();
    int i = 0;
    if (locale != null)
    {
      boolean bool = locale.c() < 8388608L;
      i = 0;
      if (bool)
        i = 1;
    }
    if (i != 0)
      return new File(Environment.getExternalStorageDirectory(), str);
    return new File(c.c(), str);
  }

  public static void setFileTraceLevel(int paramInt)
  {
  }

  public static void setMaxFolderSize(long paramLong)
  {
    (int)(paramLong / 262144L);
  }

  public static void setMaxKeepPeriod(long paramLong)
  {
    if (paramLong < 86400000L);
  }

  public void flush()
  {
    if (this.fileTracer != null)
      this.fileTracer.a();
  }

  public final boolean isEnabled()
  {
    return this.enabled;
  }

  public final boolean isFileTracerEnabled()
  {
    return this.fileTracerEnabled;
  }

  public final boolean isLogcatTracerEnabled()
  {
    return this.logcatTracerEnabled;
  }

  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    if ("debug.file.tracelevel".equals(paramString))
    {
      int i = paramSharedPreferences.getInt("debug.file.tracelevel", 63);
      trace(8, "WnsTracer", "File Trace Level Changed = " + i, null);
      this.fileTracer.a(i);
    }
  }

  public final void setEnabled(boolean paramBoolean)
  {
    this.enabled = paramBoolean;
  }

  public final void setFileTracerEnabled(boolean paramBoolean)
  {
    this.fileTracer.a();
    this.fileTracerEnabled = paramBoolean;
  }

  public final void setFileTracerLevel(int paramInt)
  {
    this.fileTracer.a(paramInt);
  }

  public final void setLogcatTracerEnabled(boolean paramBoolean)
  {
    this.logcatTracerEnabled = paramBoolean;
  }

  public void stop()
  {
    if (this.fileTracer != null)
    {
      this.fileTracer.a();
      this.fileTracer.b();
    }
  }

  public void trace(int paramInt, String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (isEnabled())
    {
      if (!isFileTracerEnabled())
        break label40;
      if (this.fileTracer != null)
        break label22;
    }
    label22: label40: 
    do
    {
      return;
      this.fileTracer.b(paramInt, Thread.currentThread(), System.currentTimeMillis(), paramString1, paramString2, paramThrowable);
    }
    while (!isLogcatTracerEnabled());
    d.a.b(paramInt, Thread.currentThread(), System.currentTimeMillis(), paramString1, paramString2, paramThrowable);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.record.debug.WnsTracer
 * JD-Core Version:    0.6.0
 */