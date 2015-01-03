package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.common.Log;
import com.umeng.common.b;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.json.JSONArray;
import org.json.JSONObject;

class a
  implements Thread.UncaughtExceptionHandler
{
  private static final String d = "com_umeng__crash.cache";
  private Thread.UncaughtExceptionHandler a;
  private g b;
  private Context c;

  private void a(Throwable paramThrowable)
  {
    if (paramThrowable == null)
    {
      Log.e("MobclickAgent", "Exception is null in handleException");
      return;
    }
    this.b.f(this.c);
    a(this.c, paramThrowable);
  }

  public void a(Context paramContext)
  {
    if (Thread.getDefaultUncaughtExceptionHandler() == this)
    {
      Log.a("MobclickAgent", "can't call onError more than once!");
      return;
    }
    this.c = paramContext.getApplicationContext();
    this.a = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
  }

  void a(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)))
      return;
    try
    {
      String str1 = com.umeng.common.b.g.a();
      String str2 = str1.split(" ")[0];
      String str3 = str1.split(" ")[1];
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("date", str2);
      localJSONObject.put("time", str3);
      localJSONObject.put("context", paramString);
      localJSONObject.put("type", "error");
      localJSONObject.put("version", b.d(paramContext));
      JSONArray localJSONArray = b(paramContext);
      if (localJSONArray == null)
        localJSONArray = new JSONArray();
      localJSONArray.put(localJSONObject);
      FileOutputStream localFileOutputStream = paramContext.openFileOutput("com_umeng__crash.cache", 0);
      localFileOutputStream.write(localJSONArray.toString().getBytes());
      localFileOutputStream.flush();
      localFileOutputStream.close();
      return;
    }
    catch (Exception localException)
    {
      Log.b("MobclickAgent", "an error occured while writing report file...", localException);
      localException.printStackTrace();
    }
  }

  void a(Context paramContext, Throwable paramThrowable)
  {
    if ((paramContext == null) || (paramThrowable == null))
      return;
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    paramThrowable.printStackTrace(localPrintWriter);
    for (Throwable localThrowable = paramThrowable.getCause(); localThrowable != null; localThrowable = localThrowable.getCause())
      localThrowable.printStackTrace(localPrintWriter);
    String str = localStringWriter.toString();
    localPrintWriter.close();
    a(paramContext, str);
  }

  public void a(g paramg)
  {
    this.b = paramg;
  }

  JSONArray b(Context paramContext)
  {
    Object localObject = null;
    if (paramContext == null);
    File localFile;
    FileInputStream localFileInputStream;
    StringBuffer localStringBuffer;
    while (true)
    {
      return null;
      localFile = new File(paramContext.getFilesDir(), "com_umeng__crash.cache");
      try
      {
        if (!localFile.exists())
          continue;
        localFileInputStream = paramContext.openFileInput("com_umeng__crash.cache");
        localStringBuffer = new StringBuffer();
        byte[] arrayOfByte = new byte[1024];
        while (true)
        {
          int i = localFileInputStream.read(arrayOfByte);
          if (i == -1)
            break;
          localStringBuffer.append(new String(arrayOfByte, 0, i));
        }
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
    }
    try
    {
      localFile.delete();
      return localObject;
    }
    catch (Exception localException2)
    {
      localException2.printStackTrace();
      return localObject;
    }
    localFileInputStream.close();
    if (localStringBuffer.length() != 0);
    for (JSONArray localJSONArray = new JSONArray(localStringBuffer.toString()); ; localJSONArray = null)
    {
      localObject = localJSONArray;
      break;
    }
  }

  public void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    a(paramThrowable);
    if (this.a != null)
      this.a.uncaughtException(paramThread, paramThrowable);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.a
 * JD-Core Version:    0.6.0
 */