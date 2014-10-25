package com.kaixin001.businesslogic;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;
import com.kaixin001.model.Setting;
import com.kaixin001.util.KXLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.TreeSet;

public class CrashHandler
  implements Thread.UncaughtExceptionHandler
{
  private static final String CRASH_REPORTER_EXTENSION = ".cr";
  private static CrashHandler INSTANCE;
  private static final String STACK_TRACE = "STACK_TRACE";
  public static final String TAG = "CrashHandler";
  private static final String VERSION_CODE = "versionCode";
  private static final String VERSION_NAME = "versionName";
  private Context mContext;
  private Thread.UncaughtExceptionHandler mDefaultHandler;
  private Properties mDeviceCrashInfo = new Properties();
  private SendReports sendReports = null;

  private String[] getCrashReportFiles(Context paramContext)
  {
    return paramContext.getFilesDir().list(new FilenameFilter()
    {
      public boolean accept(File paramFile, String paramString)
      {
        return paramString.endsWith(".cr");
      }
    });
  }

  public static CrashHandler getInstance()
  {
    if (INSTANCE == null)
      INSTANCE = new CrashHandler();
    return INSTANCE;
  }

  private boolean handleException(Throwable paramThrowable)
  {
    if (paramThrowable == null)
      return true;
    new Thread(paramThrowable.getLocalizedMessage())
    {
      public void run()
      {
        Looper.prepare();
        //Toast.makeText(CrashHandler.this.mContext, "绋嬪簭鍑洪敊鍟�鎴戜滑浼氬敖蹇慨鏀�" + this.val$msg, 0).show();
        Looper.loop();
      }
    }
    .start();
    collectCrashDeviceInfo(this.mContext);
    saveCrashInfoToFile(paramThrowable);
    return true;
  }

  private void postReport(Context paramContext, File paramFile)
  {
    try
    {
      FileInputStream localFileInputStream = new FileInputStream(paramFile);
      StringBuffer localStringBuffer = new StringBuffer();
      byte[] arrayOfByte = new byte[1024];
      while (true)
      {
        int i = localFileInputStream.read(arrayOfByte);
        if (i == -1)
        {
          localFileInputStream.close();
          //MobclickAgent.reportError(paramContext, localStringBuffer.toString());
          return;
        }
        localStringBuffer.append(new String(arrayOfByte, 0, i));
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private String saveCrashInfoToFile(Throwable paramThrowable)
  {
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    paramThrowable.printStackTrace(localPrintWriter);
    for (Throwable localThrowable = paramThrowable.getCause(); ; localThrowable = localThrowable.getCause())
    {
      if (localThrowable == null)
      {
        String str1 = localStringWriter.toString();
        localPrintWriter.close();
        this.mDeviceCrashInfo.put("STACK_TRACE", str1);
        KXLog.e("CrashHandler", str1);
        String str2 = this.mDeviceCrashInfo.toString();
        //MobclickAgent.reportError(this.mContext, str2);
        return null;
      }
      localThrowable.printStackTrace(localPrintWriter);
    }
  }

  public void collectCrashDeviceInfo(Context paramContext)
  {
    while (true)
    {
      int j;
      Field localField;
      try
      {
        PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 1);
        if (localPackageInfo == null)
          continue;
        Properties localProperties = this.mDeviceCrashInfo;
        if (localPackageInfo.versionName != null)
          continue;
        String str = "not set";
        localProperties.put("versionName", str);
        this.mDeviceCrashInfo.put("versionCode", String.valueOf(localPackageInfo.versionCode));
        this.mDeviceCrashInfo.put("ctype", Setting.getInstance().getCType());
        //arrayOfField = Build.class.getDeclaredFields();
        int i = Build.class.getDeclaredFields().length;
        j = 0;
        
        if (j >= i)
        {
         
          str = localPackageInfo.versionName;
          
          
        }
        
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        
        KXLog.e("CrashHandler", "Error while collect package info", localNameNotFoundException);
       
        
      }
   
     
    }
  }

  public void init(Context paramContext)
  {
    this.mContext = paramContext;
    this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
  }

  public void sendCrashReportsToServer(Context paramContext)
  {
    String[] arrayOfString = getCrashReportFiles(paramContext);
    Iterator localIterator = null;
    if ((arrayOfString != null) && (arrayOfString.length > 0))
    {
      TreeSet localTreeSet = new TreeSet();
      localTreeSet.addAll(Arrays.asList(arrayOfString));
      localIterator = localTreeSet.iterator();
    }
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      String str = (String)localIterator.next();
      File localFile = new File(paramContext.getFilesDir(), str);
      postReport(paramContext, localFile);
      localFile.delete();
    }
  }

  public void sendPreviousReportsToServer()
  {
    if ((this.sendReports != null) && (!this.sendReports.isAlive()))
      this.sendReports.start();
  }

  public void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    if ((!handleException(paramThrowable)) && (this.mDefaultHandler != null))
    {
      this.mDefaultHandler.uncaughtException(paramThread, paramThrowable);
      return;
    }
    try
    {
      Thread.sleep(4000L);
      Process.killProcess(Process.myPid());
      System.exit(10);
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      while (true)
        KXLog.e("CrashHandler", "Error : ", localInterruptedException);
    }
  }

  private final class SendReports extends Thread
  {
    private final Object lock = new Object();
    private Context mContext;

    public SendReports(Context arg2)
    {
     
      this.mContext =  arg2;
    }

    public void run()
    {
      try
      {
        synchronized (this.lock)
        {
          CrashHandler.this.sendCrashReportsToServer(this.mContext);
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("CrashHandler", "SendReports error.", localException);
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.businesslogic.CrashHandler
 * JD-Core Version:    0.6.0
 */