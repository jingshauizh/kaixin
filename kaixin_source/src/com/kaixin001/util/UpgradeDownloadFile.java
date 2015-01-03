package com.kaixin001.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.kaixin001.network.HttpProgressListener;
import com.kaixin001.network.HttpProxy;
import java.io.File;
import java.io.IOException;

public class UpgradeDownloadFile
{
  private static final String DEFAULT_FILE_NAME = "Kaixin-For-Android.apk";
  private static final String TAG = "UpgradeDownloadFile";
  private static UpgradeDownloadFile instance;
  private HttpProxy httpProxy;
  private boolean isForcedShow = false;
  private String lbtnTitle;
  private String mCatchPath;
  private Context mContext;
  private String mDefaultCatchPath;
  public String mDescription;
  public String mFileFullName;
  public String mFileShortName;
  public boolean mForceDelete = false;
  private Handler mHandler;
  private boolean mInstall;
  private String mNewVersionContent;
  private String mNewVersionDialogTitle;
  public String mUrl;
  private String mVersion;
  private String rbtnTitle;

  private String ensureFullFilePath()
  {
    boolean bool = true;
    if (TextUtils.isEmpty(this.mCatchPath))
      if (TextUtils.isEmpty(this.mDefaultCatchPath))
      {
        if (!Environment.getExternalStorageState().equals("mounted"))
          break label309;
        this.mDefaultCatchPath = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/kaixin001/Update/");
      }
    while (true)
    {
      bool = setCatchPath(this.mDefaultCatchPath);
      if (!bool)
        break;
      if (TextUtils.isEmpty(this.mFileShortName))
        this.mFileShortName = "Kaixin-For-Android.apk";
      File localFile = new File(this.mCatchPath + this.mFileShortName);
      if ((this.mForceDelete) || (localFile.exists()))
      {
        int i = 1;
        if (this.mContext != null)
        {
          PackageInfo localPackageInfo = this.mContext.getPackageManager().getPackageArchiveInfo(this.mCatchPath + this.mFileShortName, 1);
          if ((localPackageInfo != null) && (localPackageInfo.versionName != null) && (localPackageInfo.versionName.equals(getInstance().getVersion())) && (localPackageInfo.packageName.equals(this.mContext.getPackageName())))
            i = 0;
        }
        if (i != 0)
        {
          KXLog.d("UpgradeDownloadFile", "Delete tempFile:" + this.mCatchPath + this.mFileShortName);
          FileUtil.deleteFileWithoutCheckReturnValue(localFile);
        }
      }
      this.mFileFullName = (this.mCatchPath + this.mFileShortName);
      this.mForceDelete = false;
      return this.mFileFullName;
      label309: if (this.mContext != null)
      {
        this.mDefaultCatchPath = (this.mContext.getCacheDir().getAbsolutePath() + "/Update/");
        continue;
      }
      return "";
    }
    return "";
  }

  public static UpgradeDownloadFile getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new UpgradeDownloadFile();
      UpgradeDownloadFile localUpgradeDownloadFile = instance;
      return localUpgradeDownloadFile;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void sendDownloadMessage(int paramInt, String paramString)
  {
    Message localMessage = Message.obtain();
    int i;
    if (paramInt == 100)
      i = 9003;
    while (true)
    {
      localMessage.what = i;
      localMessage.arg1 = paramInt;
      localMessage.obj = paramString;
      if (this.mHandler == null)
        break;
      this.mHandler.sendMessage(localMessage);
      return;
      if (paramInt >= 0)
      {
        i = 9002;
        continue;
      }
      i = 9004;
    }
    KXLog.d("HTTPUTIL", "downloadAPK mHandler is null");
  }

  public void cancel()
  {
    if (this.httpProxy != null)
      this.httpProxy.cancelDownload();
    try
    {
      Thread.sleep(500L);
      label20: if (this.mInstall)
      {
        this.mInstall = false;
        return;
      }
      sendDownloadMessage(-1, this.mContext.getString(2131428038));
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label20;
    }
  }

  public void clear()
  {
    this.mCatchPath = "";
    this.mFileShortName = "";
    this.mDefaultCatchPath = "";
    this.mForceDelete = false;
  }

  public String getLbtnTitle()
  {
    return this.lbtnTitle;
  }

  public String getNewVersionContent()
  {
    return this.mNewVersionContent;
  }

  public String getNewVersionDialogTitle()
  {
    return this.mNewVersionDialogTitle;
  }

  public String getRbtnTitle()
  {
    return this.rbtnTitle;
  }

  public String getVersion()
  {
    return this.mVersion;
  }

  public boolean isForcedShow()
  {
    return this.isForcedShow;
  }

  public void pause(boolean paramBoolean)
  {
    if (this.httpProxy != null)
      this.httpProxy.cancelDownload();
  }

  public boolean setCatchPath(String paramString)
  {
    boolean bool;
    if (TextUtils.isEmpty(paramString))
      bool = false;
    File localFile;
    do
    {
      return bool;
      bool = true;
      this.mCatchPath = paramString;
      localFile = new File(paramString);
      if (localFile.exists())
        continue;
      bool = localFile.mkdir();
    }
    while (Environment.getExternalStorageState().equals("mounted"));
    try
    {
      Runtime.getRuntime().exec("chmod 777 " + localFile);
      return bool;
    }
    catch (IOException localIOException)
    {
    }
    return false;
  }

  public void setContext(Context paramContext)
  {
    if ((this.mContext == null) && (paramContext != null))
      this.mContext = paramContext;
  }

  public void setForcedShow(boolean paramBoolean)
  {
    this.isForcedShow = paramBoolean;
  }

  public void setHandler(Handler paramHandler)
  {
    this.mHandler = paramHandler;
  }

  public void setInstall(boolean paramBoolean)
  {
    this.mInstall = paramBoolean;
  }

  public void setLbtnTitle(String paramString)
  {
    this.lbtnTitle = paramString;
  }

  public void setNewVersionContent(String paramString)
  {
    this.mNewVersionContent = paramString;
  }

  public void setNewVersionDialogTitle(String paramString)
  {
    this.mNewVersionDialogTitle = paramString;
  }

  public void setRbtnTitle(String paramString)
  {
    this.rbtnTitle = paramString;
  }

  public void setVersion(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      this.mVersion = paramString.toLowerCase().replaceFirst("android-", "").trim();
  }

  // ERROR //
  public void start()
  {
    // Byte code:
    //   0: new 292	com/kaixin001/network/HttpConnection
    //   3: dup
    //   4: aload_0
    //   5: getfield 46	com/kaixin001/util/UpgradeDownloadFile:mContext	Landroid/content/Context;
    //   8: invokespecial 294	com/kaixin001/network/HttpConnection:<init>	(Landroid/content/Context;)V
    //   11: astore_1
    //   12: aload_0
    //   13: invokespecial 296	com/kaixin001/util/UpgradeDownloadFile:ensureFullFilePath	()Ljava/lang/String;
    //   16: astore_2
    //   17: lconst_0
    //   18: lstore_3
    //   19: aload_1
    //   20: aload_0
    //   21: getfield 298	com/kaixin001/util/UpgradeDownloadFile:mUrl	Ljava/lang/String;
    //   24: invokevirtual 302	com/kaixin001/network/HttpConnection:getRemoteFileSize	(Ljava/lang/String;)J
    //   27: lstore_3
    //   28: new 85	java/io/File
    //   31: dup
    //   32: aload_2
    //   33: invokespecial 111	java/io/File:<init>	(Ljava/lang/String;)V
    //   36: astore 11
    //   38: aload 11
    //   40: invokevirtual 115	java/io/File:exists	()Z
    //   43: ifeq +9 -> 52
    //   46: aload 11
    //   48: invokevirtual 305	java/io/File:delete	()Z
    //   51: pop
    //   52: aload 11
    //   54: invokevirtual 115	java/io/File:exists	()Z
    //   57: istore 12
    //   59: iconst_0
    //   60: istore 6
    //   62: iload 12
    //   64: ifeq +35 -> 99
    //   67: aload 11
    //   69: invokevirtual 309	java/io/File:length	()J
    //   72: lstore 13
    //   74: lload 13
    //   76: lload_3
    //   77: lcmp
    //   78: ifle +127 -> 205
    //   81: aload_0
    //   82: iconst_0
    //   83: aload_0
    //   84: getfield 46	com/kaixin001/util/UpgradeDownloadFile:mContext	Landroid/content/Context;
    //   87: ldc_w 310
    //   90: invokevirtual 225	android/content/Context:getString	(I)Ljava/lang/String;
    //   93: invokespecial 52	com/kaixin001/util/UpgradeDownloadFile:sendDownloadMessage	(ILjava/lang/String;)V
    //   96: iconst_0
    //   97: istore 6
    //   99: aload_0
    //   100: new 207	com/kaixin001/network/HttpProxy
    //   103: dup
    //   104: aload_0
    //   105: getfield 46	com/kaixin001/util/UpgradeDownloadFile:mContext	Landroid/content/Context;
    //   108: invokespecial 311	com/kaixin001/network/HttpProxy:<init>	(Landroid/content/Context;)V
    //   111: putfield 205	com/kaixin001/util/UpgradeDownloadFile:httpProxy	Lcom/kaixin001/network/HttpProxy;
    //   114: aload_0
    //   115: iconst_0
    //   116: aload_0
    //   117: getfield 46	com/kaixin001/util/UpgradeDownloadFile:mContext	Landroid/content/Context;
    //   120: ldc_w 312
    //   123: invokevirtual 225	android/content/Context:getString	(I)Ljava/lang/String;
    //   126: invokespecial 52	com/kaixin001/util/UpgradeDownloadFile:sendDownloadMessage	(ILjava/lang/String;)V
    //   129: aload_0
    //   130: iconst_0
    //   131: aload_0
    //   132: getfield 46	com/kaixin001/util/UpgradeDownloadFile:mContext	Landroid/content/Context;
    //   135: ldc_w 313
    //   138: invokevirtual 225	android/content/Context:getString	(I)Ljava/lang/String;
    //   141: invokespecial 52	com/kaixin001/util/UpgradeDownloadFile:sendDownloadMessage	(ILjava/lang/String;)V
    //   144: new 315	com/kaixin001/util/UpgradeDownloadFile$1
    //   147: dup
    //   148: aload_0
    //   149: invokespecial 318	com/kaixin001/util/UpgradeDownloadFile$1:<init>	(Lcom/kaixin001/util/UpgradeDownloadFile;)V
    //   152: astore 8
    //   154: aload_0
    //   155: getfield 205	com/kaixin001/util/UpgradeDownloadFile:httpProxy	Lcom/kaixin001/network/HttpProxy;
    //   158: aload_0
    //   159: getfield 298	com/kaixin001/util/UpgradeDownloadFile:mUrl	Ljava/lang/String;
    //   162: aload_2
    //   163: iload 6
    //   165: aconst_null
    //   166: aload 8
    //   168: invokevirtual 322	com/kaixin001/network/HttpProxy:httpDownload	(Ljava/lang/String;Ljava/lang/String;ZLcom/kaixin001/network/HttpRequestState;Lcom/kaixin001/network/HttpProgressListener;)Z
    //   171: pop
    //   172: new 85	java/io/File
    //   175: dup
    //   176: aload_2
    //   177: invokespecial 111	java/io/File:<init>	(Ljava/lang/String;)V
    //   180: invokevirtual 309	java/io/File:length	()J
    //   183: lload_3
    //   184: lcmp
    //   185: ifne +19 -> 204
    //   188: aload_0
    //   189: bipush 100
    //   191: aload_0
    //   192: getfield 46	com/kaixin001/util/UpgradeDownloadFile:mContext	Landroid/content/Context;
    //   195: ldc_w 323
    //   198: invokevirtual 225	android/content/Context:getString	(I)Ljava/lang/String;
    //   201: invokespecial 52	com/kaixin001/util/UpgradeDownloadFile:sendDownloadMessage	(ILjava/lang/String;)V
    //   204: return
    //   205: lload 13
    //   207: lload_3
    //   208: lcmp
    //   209: ifge +24 -> 233
    //   212: aload_0
    //   213: iconst_0
    //   214: aload_0
    //   215: getfield 46	com/kaixin001/util/UpgradeDownloadFile:mContext	Landroid/content/Context;
    //   218: ldc_w 324
    //   221: invokevirtual 225	android/content/Context:getString	(I)Ljava/lang/String;
    //   224: invokespecial 52	com/kaixin001/util/UpgradeDownloadFile:sendDownloadMessage	(ILjava/lang/String;)V
    //   227: iconst_1
    //   228: istore 6
    //   230: goto -131 -> 99
    //   233: aload_0
    //   234: bipush 100
    //   236: aload_0
    //   237: getfield 46	com/kaixin001/util/UpgradeDownloadFile:mContext	Landroid/content/Context;
    //   240: ldc_w 323
    //   243: invokevirtual 225	android/content/Context:getString	(I)Ljava/lang/String;
    //   246: invokespecial 52	com/kaixin001/util/UpgradeDownloadFile:sendDownloadMessage	(ILjava/lang/String;)V
    //   249: iconst_1
    //   250: istore 6
    //   252: goto -153 -> 99
    //   255: astore 10
    //   257: aload 10
    //   259: invokevirtual 327	com/kaixin001/network/HttpException:printStackTrace	()V
    //   262: iconst_0
    //   263: istore 6
    //   265: goto -166 -> 99
    //   268: astore 5
    //   270: aload 5
    //   272: invokevirtual 328	java/lang/Exception:printStackTrace	()V
    //   275: iconst_0
    //   276: istore 6
    //   278: goto -179 -> 99
    //   281: astore 7
    //   283: ldc 11
    //   285: ldc_w 330
    //   288: aload 7
    //   290: invokestatic 334	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   293: return
    //
    // Exception table:
    //   from	to	target	type
    //   19	52	255	com/kaixin001/network/HttpException
    //   52	59	255	com/kaixin001/network/HttpException
    //   67	74	255	com/kaixin001/network/HttpException
    //   81	96	255	com/kaixin001/network/HttpException
    //   212	227	255	com/kaixin001/network/HttpException
    //   233	249	255	com/kaixin001/network/HttpException
    //   19	52	268	java/lang/Exception
    //   52	59	268	java/lang/Exception
    //   67	74	268	java/lang/Exception
    //   81	96	268	java/lang/Exception
    //   212	227	268	java/lang/Exception
    //   233	249	268	java/lang/Exception
    //   114	204	281	java/lang/Exception
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.UpgradeDownloadFile
 * JD-Core Version:    0.6.0
 */