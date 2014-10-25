package com.kaixin001.util;

import android.os.Environment;
import android.util.Log;
import com.kaixin001.model.Setting;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KXLog
{
  private static Date date;
  private static SimpleDateFormat sd = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS] ");
  static PrintWriter writer;

  public static String GET_THREAD_INFO(Thread paramThread)
  {
    StringBuffer localStringBuffer = new StringBuffer("");
    if (paramThread != null)
      localStringBuffer.append("Thread<id:").append(paramThread.getId()).append(">");
    return localStringBuffer.toString();
  }

  public static void d(String paramString1, String paramString2)
  {
    d(paramString1, paramString2, null);
  }

  public static void d(String paramString1, String paramString2, Throwable paramThrowable)
  {
		boolean bool = Setting.getInstance().isDebug();
		if (!bool){
			 return;
		}
		 
		Log.d(paramString1, paramString2, paramThrowable);
		if (!Setting.getInstance().isDebug()){
			log2File(paramString1, paramString2, paramThrowable);
		}
  }

  public static void e(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString2 != null))
    {
      Log.e(paramString1, paramString2);
      log2File(paramString1, paramString2, null);
    }
  }

  public static void e(String paramString1, String paramString2, Throwable paramThrowable)
  {
    Log.e(paramString1, paramString2, paramThrowable);
    log2File(paramString1, paramString2, paramThrowable);
  }

  public static void e(String paramString1, String paramString2, Object[] paramArrayOfObject)
  {
    if (paramArrayOfObject == null)
      return;
    e(paramString1, String.format(paramString2, paramArrayOfObject));
  }

  private static void log2File(String paramString1, String paramString2, Throwable paramThrowable)
  {
    try
    {
      File localFile = new File(Environment.getExternalStorageDirectory(), "kxlog.txt");
      if (localFile.exists())
      {
        if (localFile.length() > 20971520L)
          localFile.renameTo(new File(Environment.getExternalStorageDirectory(), "kxlog.bak"));
        writer = new PrintWriter(new FileWriter(localFile, true));
        writer.write(paramString2 + "\n" + new Date() + "\n");
        if (paramThrowable != null)
          paramThrowable.printStackTrace(writer);
        writer.flush();
      }
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
     
     
    }
    finally
    {
      CloseUtil.close(writer);
    }
    
  }

  public static void w(String paramString1, String paramString2)
  {
    if (!Setting.getInstance().isDebug())
      return;
    e(paramString1, sd.format(new Date()) + paramString2);
  }

  public static void w(String paramString1, String paramString2, Object[] paramArrayOfObject)
  {
    if (!Setting.getInstance().isDebug()){
    	return;
    }
   if (paramArrayOfObject == null){
	   return;
   }
   Date date = new Date();
   String str = String.format(paramString2, paramArrayOfObject);
   Log.w(paramString1, sd.format(date) + str);
    
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.KXLog
 * JD-Core Version:    0.6.0
 */