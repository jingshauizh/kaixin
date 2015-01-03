package com.tencent.tauth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.webkit.CookieSyncManager;
import android.widget.Toast;
import com.tencent.connect.a.a;
import com.tencent.connect.a.b;
import com.tencent.javascript.RawFile;
import com.tencent.jsutil.IntentMap;
import com.tencent.jsutil.JsBridge;
import com.tencent.jsutil.JsConfig;
import com.tencent.jsutil.JsDialogListener;
import com.tencent.jsutil.JsTokenListener;
import com.tencent.jsutil.JumpController;
import com.tencent.jsutil.PackIRequestListener;
import com.tencent.jsutil.PackIUiListener;
import com.tencent.jsutil.ReportUtils;
import com.tencent.mta.TencentStat;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import com.tencent.open.SocialApi;
import com.tencent.open.TaskGuide;
import com.tencent.plus.ImageActivity;
import com.tencent.record.a.c;
import com.tencent.record.debug.WnsClientLog;
import com.tencent.sdkutil.AppUtils;
import com.tencent.sdkutil.DataStorage;
import com.tencent.sdkutil.HttpUtils;
import com.tencent.sdkutil.HttpUtils.Statistic;
import com.tencent.sdkutil.JsonUtil;
import com.tencent.sdkutil.Security;
import com.tencent.sdkutil.TemporaryStorage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class Tencent
{
  public static final String DEFAULT_PF = "openmobile_android";
  protected static final String PREFERENCE_PF = "pfStore";
  public static final String SHARE_TO_QQ_APP_NAME = "appName";
  public static final String SHARE_TO_QQ_AUDIO_URL = "audio_url";
  public static final String SHARE_TO_QQ_EXT_INT = "cflag";
  public static final String SHARE_TO_QQ_EXT_STR = "share_qq_ext_str";
  public static final int SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN = 1;
  public static final int SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE = 2;
  public static final String SHARE_TO_QQ_IMAGE_LOCAL_URL = "imageLocalUrl";
  public static final String SHARE_TO_QQ_IMAGE_URL = "imageUrl";
  public static final String SHARE_TO_QQ_KEY_TYPE = "req_type";
  public static final int SHARE_TO_QQ_NO_SHARE_TYPE = 0;
  public static final String SHARE_TO_QQ_SITE = "site";
  public static final String SHARE_TO_QQ_SUMMARY = "summary";
  public static final String SHARE_TO_QQ_TARGET_URL = "targetUrl";
  public static final String SHARE_TO_QQ_TITLE = "title";
  public static final int SHARE_TO_QQ_TYPE_AUDIO = 2;
  public static final int SHARE_TO_QQ_TYPE_DEFAULT = 1;
  protected static final int SHARE_TO_QQ_TYPE_HYPERTEXT = 3;
  public static final int SHARE_TO_QQ_TYPE_IMAGE = 5;
  protected static final int SHARE_TO_QQ_TYPE_VIDEO = 4;
  static AppUtils appUtils;
  public static IntentMap intentmap;
  private static QQToken jsQQToken;
  public static JsBridge mBridge;
  static JumpController mController;
  static JsDialogListener mJsDialogListener;
  static boolean mbHasInitJS = false;
  private final int JSFILESUM = 5;
  public final int SETAVATAR_REQUESTCODE = 0;
  private int jsDebugFlag = 0;
  private Activity mActivity;
  private b mCheckUpdate;
  private Context mContext;
  private JsConfig mJsConfig;
  private QQToken mQQToken;
  private JsTokenListener tokenListener;

  private Tencent(String paramString, Context paramContext)
  {
    this.mContext = paramContext;
    this.mJsConfig = JsConfig.getInstance(paramContext);
    this.mCheckUpdate = new b(paramContext);
  }

  private void JsQQTokenUpdate()
  {
    if (jsQQToken == null)
      return;
    jsQQToken.copyData(this.mQQToken);
  }

  public static void UpdateQQToken()
  {
    Tencent localTencent = DataStorage.getTencentInstance(jsQQToken.getAppId());
    if (localTencent != null)
      localTencent.mQQToken.copyData(jsQQToken);
  }

  private void checkLoadFile(Context paramContext)
  {
    Log.d("Tencent", "tencent_file_path:" + this.mJsConfig.getTencentFilePath());
    File localFile1 = new File(this.mJsConfig.getDirZipTemp() + File.separator + "js.zip");
    JSONObject localJSONObject1;
    JSONObject localJSONObject2;
    if (localFile1.exists())
    {
      localJSONObject1 = this.mJsConfig.getConfig();
      localJSONObject2 = this.mJsConfig.readConfigFromZip(localFile1);
      if ((localJSONObject1 == null) || (localJSONObject2 == null));
    }
    try
    {
      long l1 = localJSONObject1.getLong("version");
      long l2 = localJSONObject2.getLong("version");
      Log.d("Tencent", "checkloadFile : dataVersion = " + l1 + "zipVersion = " + l2);
      if (l1 < l2)
        this.mCheckUpdate.c();
      if (new File(this.mJsConfig.getTencentFilePath()).exists())
      {
        mBridge = JsBridge.getInstance(paramContext, this.mJsConfig.getTencentFileProtocolPath());
        return;
      }
    }
    catch (JSONException localJSONException)
    {
      File localFile2;
      do
      {
        while (true)
          localJSONException.printStackTrace();
        moveRawFile(this.mContext, this.mJsConfig.getDirZipTemp() + "/js.zip");
        localFile2 = new File(this.mJsConfig.getTencentFilePath());
        Log.d("Tencent", "fileExist:" + localFile2.exists());
      }
      while (!localFile2.exists());
      mBridge = JsBridge.getInstance(paramContext, this.mJsConfig.getTencentFileProtocolPath());
    }
  }

  // ERROR //
  private static boolean checkManifestConfig(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: new 247	android/content/ComponentName
    //   3: dup
    //   4: aload_0
    //   5: invokevirtual 252	android/content/Context:getPackageName	()Ljava/lang/String;
    //   8: ldc 254
    //   10: invokespecial 257	android/content/ComponentName:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   13: astore_2
    //   14: aload_0
    //   15: invokevirtual 261	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   18: aload_2
    //   19: iconst_0
    //   20: invokevirtual 267	android/content/pm/PackageManager:getActivityInfo	(Landroid/content/ComponentName;I)Landroid/content/pm/ActivityInfo;
    //   23: pop
    //   24: new 247	android/content/ComponentName
    //   27: dup
    //   28: aload_0
    //   29: invokevirtual 252	android/content/Context:getPackageName	()Ljava/lang/String;
    //   32: ldc_w 269
    //   35: invokespecial 257	android/content/ComponentName:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   38: astore 7
    //   40: aload_0
    //   41: invokevirtual 261	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   44: aload 7
    //   46: iconst_0
    //   47: invokevirtual 267	android/content/pm/PackageManager:getActivityInfo	(Landroid/content/ComponentName;I)Landroid/content/pm/ActivityInfo;
    //   50: pop
    //   51: iconst_1
    //   52: ireturn
    //   53: astore_3
    //   54: new 155	java/lang/StringBuilder
    //   57: dup
    //   58: invokespecial 156	java/lang/StringBuilder:<init>	()V
    //   61: ldc_w 271
    //   64: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   67: aload_1
    //   68: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: ldc_w 273
    //   74: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: invokevirtual 168	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   80: astore 4
    //   82: new 155	java/lang/StringBuilder
    //   85: dup
    //   86: invokespecial 156	java/lang/StringBuilder:<init>	()V
    //   89: aload 4
    //   91: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: ldc_w 275
    //   97: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: aload_1
    //   101: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   104: ldc_w 277
    //   107: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: ldc_w 279
    //   113: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: ldc_w 281
    //   119: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: invokevirtual 168	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   125: astore 5
    //   127: invokestatic 286	com/tencent/record/debug/WnsClientLog:getInstance	()Lcom/tencent/record/debug/WnsClientLog;
    //   130: ldc_w 288
    //   133: aload 5
    //   135: invokevirtual 291	com/tencent/record/debug/WnsClientLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   138: iconst_0
    //   139: ireturn
    //   140: astore 8
    //   142: new 155	java/lang/StringBuilder
    //   145: dup
    //   146: invokespecial 156	java/lang/StringBuilder:<init>	()V
    //   149: ldc_w 293
    //   152: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: ldc_w 295
    //   158: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   161: invokevirtual 168	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   164: astore 9
    //   166: invokestatic 286	com/tencent/record/debug/WnsClientLog:getInstance	()Lcom/tencent/record/debug/WnsClientLog;
    //   169: ldc_w 297
    //   172: aload 9
    //   174: invokevirtual 291	com/tencent/record/debug/WnsClientLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   177: iconst_0
    //   178: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   0	24	53	android/content/pm/PackageManager$NameNotFoundException
    //   24	51	140	android/content/pm/PackageManager$NameNotFoundException
  }

  public static Tencent createInstance(String paramString, Context paramContext)
  {
    c.a(paramContext.getApplicationContext());
    WnsClientLog.getInstance().v("openSDK_LOG", "createInstance() --start");
    if (DataStorage.hasTencentInstance(paramString))
    {
      WnsClientLog.getInstance().v("openSDK_LOG", "createInstance() ,sessionMap.containsKey --end");
      return DataStorage.getTencentInstance(paramString);
    }
    if (!checkManifestConfig(paramContext, paramString))
      return null;
    Tencent localTencent = new Tencent(paramString, paramContext.getApplicationContext());
    localTencent.mQQToken = new QQToken(paramString, paramContext.getApplicationContext());
    localTencent.initJs();
    DataStorage.setTencentInstance(paramString, localTencent);
    WnsClientLog.getInstance().v("openSDK_LOG", "createInstance()  --end");
    return localTencent;
  }

  private Bundle fillParams(String paramString, Bundle paramBundle)
  {
    JsQQTokenUpdate();
    if (paramBundle == null)
      paramBundle = new Bundle();
    paramBundle.putString("format", "json");
    paramBundle.putString("status_os", Build.VERSION.RELEASE);
    paramBundle.putString("status_machine", Build.MODEL);
    paramBundle.putString("status_version", Build.VERSION.SDK);
    paramBundle.putString("sdkv", "2.2");
    paramBundle.putString("sdkp", "a");
    if (this.mQQToken == null)
      return paramBundle;
    if (isSessionValid())
      paramBundle.putString("access_token", getAccessToken());
    if (!"oauth2.0/m_me".equals(paramString))
    {
      paramBundle.putString("oauth_consumer_key", this.mQQToken.getAppId() + "");
      if (this.mQQToken.getOpenId() != null)
        paramBundle.putString("openid", this.mQQToken.getOpenId() + "");
    }
    paramBundle.putString("appid_for_getting_config", this.mQQToken.getAppId() + "");
    try
    {
      paramBundle.putString("pf", this.mQQToken.getAppContext().getSharedPreferences("pfStore", 0).getString("pf", "openmobile_android"));
      return paramBundle;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      paramBundle.putString("pf", "openmobile_android");
    }
    return paramBundle;
  }

  private void initJs()
  {
    if (mbHasInitJS)
      return;
    mbHasInitJS = true;
    Context localContext = this.mContext;
    for (String str : RawFile.fileList)
      moveJsFile(this.mContext, str);
    switch (this.jsDebugFlag)
    {
    default:
      Log.e("Tencent js", "javascript 包下  js文件不完整");
      return;
    case 0:
      checkLoadFile(localContext);
      startUpdate();
      if (Security.verify(this.mJsConfig.getDirJsRoot()))
      {
        registerObj(localContext);
        Log.d("Tencent", "verifysuccess");
        return;
      }
      Log.d("Tencent", "verifyfailed");
      this.mJsConfig.setJsVersion("0");
      moveRawFile(this.mContext, this.mJsConfig.getDirZipTemp() + "/js.zip");
      startUpdate();
      registerObj(localContext);
      return;
    case 5:
    }
    registerObj(localContext);
  }

  private void moveJsFile(Context paramContext, String paramString)
  {
    URL localURL = RawFile.class.getResource(paramString);
    if (localURL == null)
      return;
    try
    {
      a.a(localURL.openConnection().getInputStream(), new File(paramContext.getFilesDir().toString() + "/tencent/js/" + paramString));
      this.jsDebugFlag = (1 + this.jsDebugFlag);
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  // ERROR //
  private void moveRawFile(Context paramContext, String paramString)
  {
    // Byte code:
    //   0: aload_2
    //   1: invokestatic 501	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifeq +4 -> 8
    //   7: return
    //   8: ldc_w 422
    //   11: ldc 184
    //   13: invokevirtual 468	java/lang/Class:getResource	(Ljava/lang/String;)Ljava/net/URL;
    //   16: astore_3
    //   17: aload_3
    //   18: ifnull -11 -> 7
    //   21: aconst_null
    //   22: astore 4
    //   24: aload_3
    //   25: invokevirtual 474	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   28: astore 16
    //   30: aload 16
    //   32: ifnonnull +23 -> 55
    //   35: aconst_null
    //   36: invokevirtual 506	java/io/InputStream:close	()V
    //   39: return
    //   40: astore 23
    //   42: invokestatic 286	com/tencent/record/debug/WnsClientLog:getInstance	()Lcom/tencent/record/debug/WnsClientLog;
    //   45: ldc_w 310
    //   48: ldc_w 508
    //   51: invokevirtual 291	com/tencent/record/debug/WnsClientLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   54: return
    //   55: aload 16
    //   57: invokevirtual 480	java/net/URLConnection:getInputStream	()Ljava/io/InputStream;
    //   60: astore 17
    //   62: aload 17
    //   64: astore 4
    //   66: aload 4
    //   68: ifnonnull +24 -> 92
    //   71: aload 4
    //   73: invokevirtual 506	java/io/InputStream:close	()V
    //   76: return
    //   77: astore 22
    //   79: invokestatic 286	com/tencent/record/debug/WnsClientLog:getInstance	()Lcom/tencent/record/debug/WnsClientLog;
    //   82: ldc_w 310
    //   85: ldc_w 508
    //   88: invokevirtual 291	com/tencent/record/debug/WnsClientLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   91: return
    //   92: new 176	java/io/File
    //   95: dup
    //   96: aload_2
    //   97: invokespecial 187	java/io/File:<init>	(Ljava/lang/String;)V
    //   100: astore 18
    //   102: aload 4
    //   104: aload 18
    //   106: invokestatic 492	com/tencent/connect/a/a:a	(Ljava/io/InputStream;Ljava/io/File;)Z
    //   109: pop
    //   110: aload 18
    //   112: new 176	java/io/File
    //   115: dup
    //   116: aload_0
    //   117: getfield 111	com/tencent/tauth/Tencent:mJsConfig	Lcom/tencent/jsutil/JsConfig;
    //   120: invokevirtual 443	com/tencent/jsutil/JsConfig:getDirJsRoot	()Ljava/lang/String;
    //   123: invokespecial 187	java/io/File:<init>	(Ljava/lang/String;)V
    //   126: invokestatic 511	com/tencent/connect/a/a:a	(Ljava/io/File;Ljava/io/File;)Z
    //   129: pop
    //   130: aload 4
    //   132: invokevirtual 506	java/io/InputStream:close	()V
    //   135: return
    //   136: astore 21
    //   138: invokestatic 286	com/tencent/record/debug/WnsClientLog:getInstance	()Lcom/tencent/record/debug/WnsClientLog;
    //   141: ldc_w 310
    //   144: ldc_w 508
    //   147: invokevirtual 291	com/tencent/record/debug/WnsClientLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   150: return
    //   151: astore 14
    //   153: invokestatic 286	com/tencent/record/debug/WnsClientLog:getInstance	()Lcom/tencent/record/debug/WnsClientLog;
    //   156: ldc_w 310
    //   159: ldc_w 513
    //   162: invokevirtual 291	com/tencent/record/debug/WnsClientLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   165: aload 4
    //   167: invokevirtual 506	java/io/InputStream:close	()V
    //   170: return
    //   171: astore 15
    //   173: invokestatic 286	com/tencent/record/debug/WnsClientLog:getInstance	()Lcom/tencent/record/debug/WnsClientLog;
    //   176: ldc_w 310
    //   179: ldc_w 508
    //   182: invokevirtual 291	com/tencent/record/debug/WnsClientLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   185: return
    //   186: astore 12
    //   188: invokestatic 286	com/tencent/record/debug/WnsClientLog:getInstance	()Lcom/tencent/record/debug/WnsClientLog;
    //   191: ldc_w 310
    //   194: ldc_w 508
    //   197: invokevirtual 291	com/tencent/record/debug/WnsClientLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   200: aload 4
    //   202: invokevirtual 506	java/io/InputStream:close	()V
    //   205: return
    //   206: astore 13
    //   208: invokestatic 286	com/tencent/record/debug/WnsClientLog:getInstance	()Lcom/tencent/record/debug/WnsClientLog;
    //   211: ldc_w 310
    //   214: ldc_w 508
    //   217: invokevirtual 291	com/tencent/record/debug/WnsClientLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   220: return
    //   221: astore 9
    //   223: invokestatic 286	com/tencent/record/debug/WnsClientLog:getInstance	()Lcom/tencent/record/debug/WnsClientLog;
    //   226: ldc_w 310
    //   229: ldc_w 515
    //   232: invokevirtual 291	com/tencent/record/debug/WnsClientLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   235: aload 4
    //   237: invokevirtual 506	java/io/InputStream:close	()V
    //   240: return
    //   241: astore 11
    //   243: invokestatic 286	com/tencent/record/debug/WnsClientLog:getInstance	()Lcom/tencent/record/debug/WnsClientLog;
    //   246: ldc_w 310
    //   249: ldc_w 508
    //   252: invokevirtual 291	com/tencent/record/debug/WnsClientLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   255: return
    //   256: astore 5
    //   258: aconst_null
    //   259: astore 6
    //   261: aload 5
    //   263: astore 7
    //   265: aload 6
    //   267: invokevirtual 506	java/io/InputStream:close	()V
    //   270: aload 7
    //   272: athrow
    //   273: astore 8
    //   275: invokestatic 286	com/tencent/record/debug/WnsClientLog:getInstance	()Lcom/tencent/record/debug/WnsClientLog;
    //   278: ldc_w 310
    //   281: ldc_w 508
    //   284: invokevirtual 291	com/tencent/record/debug/WnsClientLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   287: goto -17 -> 270
    //   290: astore 10
    //   292: aload 4
    //   294: astore 6
    //   296: aload 10
    //   298: astore 7
    //   300: goto -35 -> 265
    //
    // Exception table:
    //   from	to	target	type
    //   35	39	40	java/io/IOException
    //   71	76	77	java/io/IOException
    //   130	135	136	java/io/IOException
    //   24	30	151	java/io/FileNotFoundException
    //   55	62	151	java/io/FileNotFoundException
    //   92	130	151	java/io/FileNotFoundException
    //   165	170	171	java/io/IOException
    //   24	30	186	java/io/IOException
    //   55	62	186	java/io/IOException
    //   92	130	186	java/io/IOException
    //   200	205	206	java/io/IOException
    //   24	30	221	java/lang/Exception
    //   55	62	221	java/lang/Exception
    //   92	130	221	java/lang/Exception
    //   235	240	241	java/io/IOException
    //   24	30	256	finally
    //   55	62	256	finally
    //   265	270	273	java/io/IOException
    //   92	130	290	finally
    //   153	165	290	finally
    //   188	200	290	finally
    //   223	235	290	finally
  }

  private void registerObj(Context paramContext)
  {
    mBridge = JsBridge.getInstance(paramContext, JsConfig.mTencentFileProtocolPath);
    if (jsQQToken != null)
      return;
    jsQQToken = new QQToken(null, null);
    mBridge.regiesterObj(jsQQToken, "sdk_QQToken");
    mBridge.regiesterObj(WnsClientLog.getInstance(), "sdk_log");
    mBridge.regiesterObj(new PackIRequestListener(), "sdk_reqeustListener");
    mBridge.regiesterObj(new PackIUiListener(), "sdk_uiListener");
    mBridge.regiesterObj(new HttpUtils(this.mContext, jsQQToken), "sdk_httpUtils");
    mBridge.regiesterObj(new JsonUtil(), "sdk_jsonUtil");
    mBridge.regiesterObj(new ReportUtils(this.mContext, jsQQToken), "sdk_reportUtils");
    mController = new JumpController(null, jsQQToken);
    mBridge.regiesterObj(mController, "sdk_jump");
    appUtils = new AppUtils(null, jsQQToken);
    mBridge.regiesterObj(appUtils, "sdk_appUtils");
    mJsDialogListener = new JsDialogListener(jsQQToken, null);
    mBridge.regiesterObj(mJsDialogListener, "sdk_dialogListener");
    intentmap = new IntentMap(new Intent());
    mBridge.regiesterObj(intentmap, "sdk_data");
    mBridge.loadUrl(JsConfig.mTencentFileProtocolPath);
  }

  private void setBundleParams(Bundle paramBundle)
  {
    if (this.mQQToken != null)
    {
      paramBundle.putString("appid", this.mQQToken.getAppId());
      if (this.mQQToken.isSessionValid())
      {
        paramBundle.putString("keystr", this.mQQToken.getAccessToken());
        paramBundle.putString("keytype", "0x80");
      }
      String str = this.mQQToken.getOpenId();
      if (str != null)
        paramBundle.putString("hopenid", str);
      paramBundle.putString("platform", "androidqz");
    }
    try
    {
      paramBundle.putString("pf", this.mContext.getSharedPreferences("pfStore", 0).getString("pf", "pf"));
      paramBundle.putString("sdkv", "2.2");
      paramBundle.putString("sdkp", "a");
      return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        paramBundle.putString("pf", "pf");
      }
    }
  }

  private void startUpdate()
  {
    new Thread(new Tencent.1(this)).start();
  }

  public static int startWPAConversation(Context paramContext, String paramString1, String paramString2)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW");
    if (TextUtils.isEmpty(paramString1))
      return -1;
    if (paramString1.length() < 5)
      return -3;
    for (int i = 0; i < paramString1.length(); i++)
      if (!Character.isDigit(paramString1.charAt(i)))
        return -4;
    String str = "";
    if (!TextUtils.isEmpty(paramString2))
      str = Base64.encodeToString(paramString2.getBytes(), 2);
    localIntent.setData(Uri.parse(String.format("mqqwpa://im/chat?chat_type=wpa&uin=%1$s&version=1&src_type=app&attach_content=%2$s", new Object[] { paramString1, str })));
    PackageManager localPackageManager = paramContext.getPackageManager();
    if (localPackageManager.queryIntentActivities(localIntent, 65536).size() > 0)
    {
      paramContext.startActivity(localIntent);
      return 0;
    }
    localIntent.setData(Uri.parse("http://www.myapp.com/forward/a/45592?g_f=990935"));
    if (localPackageManager.queryIntentActivities(localIntent, 65536).size() > 0)
    {
      paramContext.startActivity(localIntent);
      return 0;
    }
    return -2;
  }

  public int ask(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQToken).ask(paramActivity, paramBundle, paramIUiListener);
    return -1;
  }

  public int brag(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQToken).brag(paramActivity, paramBundle, paramIUiListener);
    return -1;
  }

  public int challenge(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQToken).challenge(paramActivity, paramBundle, paramIUiListener);
    return -1;
  }

  protected Bundle composeActivityParams()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("appid", this.mQQToken.getAppId());
    if (this.mQQToken.isSessionValid())
    {
      localBundle.putString("keystr", this.mQQToken.getAccessToken());
      localBundle.putString("keytype", "0x80");
    }
    String str = this.mQQToken.getOpenId();
    if (str != null)
      localBundle.putString("hopenid", str);
    localBundle.putString("platform", "androidqz");
    localBundle.putString("pf", this.mContext.getSharedPreferences("pfStore", 0).getString("pf", "openmobile_android"));
    localBundle.putString("pf", "openmobile_android");
    localBundle.putString("sdkv", "2.2");
    localBundle.putString("sdkp", "a");
    return localBundle;
  }

  protected Bundle composeCGIParams()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("format", "json");
    localBundle.putString("status_os", Build.VERSION.RELEASE);
    localBundle.putString("status_machine", Build.MODEL);
    localBundle.putString("status_version", Build.VERSION.SDK);
    localBundle.putString("sdkv", "2.2");
    localBundle.putString("sdkp", "a");
    if ((this.mQQToken != null) && (this.mQQToken.isSessionValid()))
    {
      localBundle.putString("access_token", this.mQQToken.getAccessToken());
      localBundle.putString("oauth_consumer_key", this.mQQToken.getAppId());
      localBundle.putString("openid", this.mQQToken.getOpenId());
    }
    localBundle.putString("appid_for_getting_config", this.mQQToken.getAppId());
    localBundle.putString("pf", this.mContext.getSharedPreferences("pfStore", 0).getString("pf", "openmobile_android"));
    return localBundle;
  }

  public String getAccessToken()
  {
    WnsClientLog.getInstance().v("openSDK_LOG", "getAccessToken()");
    return this.mQQToken.getAccessToken();
  }

  public void getAppFriends(IRequestListener paramIRequestListener)
  {
    requestAsync("user/get_app_friends", null, "GET", paramIRequestListener, null);
  }

  public String getAppId()
  {
    WnsClientLog.getInstance().v("openSDK_LOG", "getAppId()");
    return this.mQQToken.getAppId();
  }

  public long getExpiresIn()
  {
    WnsClientLog.getInstance().v("openSDK_LOG", "getExpiresIn()");
    return this.mQQToken.getExpiresIn();
  }

  public String getOpenId()
  {
    WnsClientLog.getInstance().v("openSDK_LOG", "getOpenId()");
    return this.mQQToken.getOpenId();
  }

  public void getPhotoList(String paramString, IRequestListener paramIRequestListener)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("albumid", paramString);
    requestAsync("photo/list_photo", localBundle, "GET", paramIRequestListener, null);
  }

  public void getWPAUserOnlineState(String paramString, IRequestListener paramIRequestListener)
  {
    if (paramString == null)
      try
      {
        throw new Exception("uin null");
      }
      catch (Exception localException)
      {
        if (paramIRequestListener != null)
          paramIRequestListener.onUnknowException(localException, null);
        return;
      }
    if (paramString.length() < 5)
      throw new Exception("uin length < 5");
    while (true)
    {
      if (i < paramString.length())
      {
        if (!Character.isDigit(paramString.charAt(i)))
          throw new Exception("uin not digit");
        i++;
        continue;
      }
      String str = "http://webpresence.qq.com/getonline?Type=1&" + paramString + ":";
      requestAsync(this.mQQToken.getAppContext(), str, paramIRequestListener, null);
      return;
      int i = 0;
    }
  }

  public int gift(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQToken).gift(paramActivity, paramBundle, paramIUiListener);
    return -1;
  }

  public void grade(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQToken).grade(paramActivity, paramBundle, paramIUiListener);
  }

  public int invite(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQToken).invite(paramActivity, paramBundle, paramIUiListener);
    return -1;
  }

  public boolean isSessionValid()
  {
    WnsClientLog localWnsClientLog = WnsClientLog.getInstance();
    StringBuilder localStringBuilder = new StringBuilder().append("isSessionValid(), result = ");
    if (this.mQQToken.isSessionValid());
    for (String str = "true"; ; str = "false")
    {
      localWnsClientLog.v("openSDK_LOG", str + "");
      return this.mQQToken.isSessionValid();
    }
  }

  public boolean isSupportSSOLogin(Activity paramActivity)
  {
    if ((paramActivity == null) || (this.mQQToken == null))
      return false;
    AppUtils localAppUtils = new AppUtils(paramActivity, this.mQQToken);
    if (localAppUtils.getQQVersion() == null)
    {
      Toast.makeText(paramActivity, "没有安装手Q", 0).show();
      return false;
    }
    if (localAppUtils.checkMobileQQ())
    {
      Toast.makeText(paramActivity, "已安装的手Q版本支持SSO登陆", 0).show();
      return true;
    }
    Toast.makeText(paramActivity, "已安装的手Q版本不支持SSO登陆", 0).show();
    return false;
  }

  public int login(Activity paramActivity, String paramString, IUiListener paramIUiListener)
  {
    JsQQTokenUpdate();
    this.mActivity = paramActivity;
    this.tokenListener = new JsTokenListener(paramIUiListener, this.mQQToken, this.mContext);
    TemporaryStorage.setListener(new Tencent.FeedConfirmListener(this, this.tokenListener));
    mController.setActivity(paramActivity);
    mJsDialogListener.setActivity(paramActivity);
    appUtils.setActivity(paramActivity);
    mBridge.executeMethod("login", new String[] { paramString });
    return -1;
  }

  public void logout(Context paramContext)
  {
    WnsClientLog.getInstance().v("openSDK_LOG", "logout() --start");
    CookieSyncManager.createInstance(paramContext);
    setAccessToken(null, null);
    setOpenId(null);
    WnsClientLog.getInstance().v("openSDK_LOG", "logout() --end");
  }

  public JSONObject parseBundleToJSON4QQShare(Bundle paramBundle, int paramInt)
  {
    JSONObject localJSONObject = new JSONObject();
    while (true)
    {
      int j;
      try
      {
        int i = paramBundle.getInt("req_type", 1);
        localJSONObject.put("shareType", paramInt);
        localJSONObject.put("type", i + "");
        localJSONObject.put("req_type", paramBundle.get("req_type"));
        localJSONObject.put("cflag", paramBundle.getInt("cflag"));
        localJSONObject.put("object_title", (String)paramBundle.get("title"));
        localJSONObject.put("object_description", paramBundle.get("summary"));
        if (paramBundle.get("imageUrl") != null)
        {
          Object localObject = paramBundle.get("imageUrl");
          if (!(localObject instanceof String))
            continue;
          Log.d("iamgeUrl", paramBundle.getString("imageUrl"));
          localJSONObject.put("IsImageUrlArrayList", false);
          localJSONObject.put("object_imageUrl", paramBundle.getString("imageUrl"));
          localJSONObject.put("object_targetUrl", paramBundle.get("targetUrl"));
          localJSONObject.put("audioUrl", paramBundle.get("audio_url"));
          localJSONObject.put("app_name", paramBundle.get("appName"));
          localJSONObject.put("platform", "android");
          return localJSONObject;
          if (!(localObject instanceof ArrayList))
            continue;
          ArrayList localArrayList = (ArrayList)localObject;
          String str = "";
          j = 0;
          if (j >= localArrayList.size())
            continue;
          if (j != -1 + localArrayList.size())
            continue;
          str = str + (String)localArrayList.get(j);
          break label393;
          str = str + (String)localArrayList.get(j) + ";";
          break label393;
          localJSONObject.put("IsImageUrlArrayList", true);
          localJSONObject.put("object_imageUrl", str);
          continue;
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return localJSONObject;
      }
      localJSONObject.put("object_imageUrl", paramBundle.get("imageLocalUrl"));
      continue;
      label393: j++;
    }
  }

  public int pay(Activity paramActivity, IUiListener paramIUiListener)
  {
    return -1;
  }

  public int reAuth(Activity paramActivity, String paramString, IUiListener paramIUiListener)
  {
    JsQQTokenUpdate();
    this.tokenListener = new JsTokenListener(paramIUiListener, this.mQQToken, this.mContext);
    TemporaryStorage.setListener(this.tokenListener);
    mBridge.executeMethod("reAuth", new String[] { paramString });
    return -1;
  }

  public boolean ready(Context paramContext)
  {
    if (this.mQQToken == null)
      return false;
    if ((this.mQQToken.isSessionValid()) && (this.mQQToken.getOpenId() != null));
    for (int i = 1; ; i = 0)
    {
      if (i == 0)
        Toast.makeText(paramContext, "login and get openId first, please!", 0).show();
      return i;
    }
  }

  public JSONObject request(String paramString1, Bundle paramBundle, String paramString2)
  {
    JsQQTokenUpdate();
    WnsClientLog.getInstance().v("openSDK_LOG", "request() ,graphPath = " + paramString1 + "");
    new JSONObject();
    JSONObject localJSONObject = JsonUtil.parseJson(HttpUtils.openUrl(this.mQQToken.getAppContext(), paramString1, paramString2, paramBundle).response);
    Log.i("request-result", localJSONObject.toString());
    return localJSONObject;
  }

  public void requestAsync(Context paramContext, String paramString, IRequestListener paramIRequestListener, Object paramObject)
  {
    String str1 = TemporaryStorage.getId();
    Bundle localBundle = new Bundle();
    localBundle.putString("oauth_consumer_key", this.mQQToken.getAppId());
    localBundle.putString("action", "get_wpastate");
    TemporaryStorage.set(str1, localBundle);
    String str2 = TemporaryStorage.getId();
    TemporaryStorage.set(str2, paramIRequestListener);
    mBridge.executeMethod("tencent.wpaApi.getWAPUserState", new String[] { paramString, str1, str2 });
  }

  public void requestAsync(String paramString1, Bundle paramBundle, String paramString2, IRequestListener paramIRequestListener, Object paramObject)
  {
    WnsClientLog.getInstance().v("openSDK_LOG", "requestAsync() ,graphPath = " + paramString1 + "");
    JsQQTokenUpdate();
    String str1 = TemporaryStorage.getId();
    if (paramBundle == null)
      paramBundle = new Bundle();
    paramBundle.putString("oauth_consumer_key", this.mQQToken.getAppId());
    paramBundle.putString("openid", this.mQQToken.getOpenId());
    paramBundle.putString("access_token", this.mQQToken.getAccessToken());
    paramBundle.putString("format", "json");
    TemporaryStorage.set(str1, paramBundle);
    String str2 = TemporaryStorage.getId();
    TemporaryStorage.set(str2, paramIRequestListener);
    mBridge.executeMethod("requestAsync", new String[] { paramString1, str1, str2 });
  }

  // ERROR //
  public JSONObject requestSync(String paramString1, Bundle paramBundle, String paramString2)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 341	com/tencent/tauth/Tencent:JsQQTokenUpdate	()V
    //   4: new 203	org/json/JSONObject
    //   7: dup
    //   8: invokespecial 837	org/json/JSONObject:<init>	()V
    //   11: astore 4
    //   13: ldc_w 944
    //   16: aload_2
    //   17: invokevirtual 945	android/os/Bundle:toString	()Ljava/lang/String;
    //   20: invokestatic 925	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   23: pop
    //   24: aload_0
    //   25: aload_1
    //   26: aload_2
    //   27: invokespecial 947	com/tencent/tauth/Tencent:fillParams	(Ljava/lang/String;Landroid/os/Bundle;)Landroid/os/Bundle;
    //   30: astore 12
    //   32: aload 12
    //   34: ldc_w 404
    //   37: new 155	java/lang/StringBuilder
    //   40: dup
    //   41: invokespecial 156	java/lang/StringBuilder:<init>	()V
    //   44: aload_0
    //   45: getfield 123	com/tencent/tauth/Tencent:mQQToken	Lcom/tencent/tauth/QQToken;
    //   48: invokevirtual 134	com/tencent/tauth/QQToken:getAppId	()Ljava/lang/String;
    //   51: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: ldc_w 397
    //   57: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   60: invokevirtual 168	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   63: invokevirtual 351	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   66: aload_0
    //   67: getfield 123	com/tencent/tauth/Tencent:mQQToken	Lcom/tencent/tauth/QQToken;
    //   70: invokevirtual 409	com/tencent/tauth/QQToken:getAppContext	()Landroid/content/Context;
    //   73: aload_1
    //   74: aload_3
    //   75: aload 12
    //   77: invokestatic 910	com/tencent/sdkutil/HttpUtils:openUrl	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Landroid/os/Bundle;)Lcom/tencent/sdkutil/HttpUtils$Statistic;
    //   80: getfield 915	com/tencent/sdkutil/HttpUtils$Statistic:response	Ljava/lang/String;
    //   83: astore 13
    //   85: aload 13
    //   87: invokestatic 919	com/tencent/sdkutil/JsonUtil:parseJson	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   90: astore 16
    //   92: aload 16
    //   94: astore 6
    //   96: ldc_w 949
    //   99: aload 6
    //   101: invokevirtual 922	org/json/JSONObject:toString	()Ljava/lang/String;
    //   104: invokestatic 925	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   107: pop
    //   108: aload 6
    //   110: areturn
    //   111: astore 14
    //   113: aload 4
    //   115: astore 6
    //   117: aload 14
    //   119: astore 15
    //   121: aload 15
    //   123: invokevirtual 230	org/json/JSONException:printStackTrace	()V
    //   126: aload 6
    //   128: areturn
    //   129: astore 7
    //   131: aload 7
    //   133: invokevirtual 420	java/lang/Exception:printStackTrace	()V
    //   136: invokestatic 286	com/tencent/record/debug/WnsClientLog:getInstance	()Lcom/tencent/record/debug/WnsClientLog;
    //   139: ldc_w 310
    //   142: ldc_w 951
    //   145: aload 7
    //   147: invokevirtual 954	com/tencent/record/debug/WnsClientLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   150: aload 6
    //   152: ldc_w 956
    //   155: iconst_m1
    //   156: invokevirtual 847	org/json/JSONObject:put	(Ljava/lang/String;I)Lorg/json/JSONObject;
    //   159: pop
    //   160: aload 6
    //   162: ldc_w 958
    //   165: aload 7
    //   167: invokevirtual 959	java/lang/Exception:toString	()Ljava/lang/String;
    //   170: invokevirtual 855	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   173: pop
    //   174: aload 6
    //   176: areturn
    //   177: astore 8
    //   179: aload 8
    //   181: invokevirtual 230	org/json/JSONException:printStackTrace	()V
    //   184: aload 6
    //   186: areturn
    //   187: astore 5
    //   189: aload 4
    //   191: astore 6
    //   193: aload 5
    //   195: astore 7
    //   197: goto -66 -> 131
    //   200: astore 15
    //   202: goto -81 -> 121
    //
    // Exception table:
    //   from	to	target	type
    //   85	92	111	org/json/JSONException
    //   96	108	129	java/lang/Exception
    //   121	126	129	java/lang/Exception
    //   150	174	177	org/json/JSONException
    //   13	85	187	java/lang/Exception
    //   85	92	187	java/lang/Exception
    //   96	108	200	org/json/JSONException
  }

  public void setAccessToken(String paramString1, String paramString2)
  {
    WnsClientLog.getInstance().v("openSDK_LOG", "setAccessToken(), expiresIn = " + paramString2 + "");
    this.mQQToken.setAccessToken(paramString1, paramString2);
  }

  public void setAvatar(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    paramBundle.putString("appid", this.mQQToken.getAppId());
    paramBundle.putString("access_token", this.mQQToken.getAccessToken());
    paramBundle.putLong("expires_in", this.mQQToken.getExpiresIn());
    paramBundle.putString("openid", this.mQQToken.getOpenId());
    setBundleParams(paramBundle);
    Intent localIntent = new Intent();
    localIntent.putExtra("key_action", "action_avatar");
    localIntent.putExtra("key_params", paramBundle);
    localIntent.setClass(paramActivity, ImageActivity.class);
    paramActivity.startActivityForResult(localIntent, 0);
  }

  public void setAvatar(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener, int paramInt1, int paramInt2)
  {
    Toast.makeText(paramActivity, "setAvatar...Anim", 0).show();
    paramBundle.putInt("exitAnim", paramInt2);
    paramActivity.overridePendingTransition(paramInt1, 0);
    setAvatar(paramActivity, paramBundle, paramIUiListener);
  }

  public void setOpenId(String paramString)
  {
    WnsClientLog.getInstance().v("openSDK_LOG", "setOpenId() --start");
    this.mQQToken.setOpenId(paramString);
    TencentStat.c(this.mContext, this.mQQToken);
    WnsClientLog.getInstance().v("openSDK_LOG", "setOpenId() --end");
  }

  public void shareToQQ(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    JsQQTokenUpdate();
    if (mBridge != null)
      paramActivity.runOnUiThread(new Tencent.2(this, paramBundle, paramIUiListener, paramActivity));
  }

  public void shareToQzone(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    JsQQTokenUpdate();
    if (mBridge != null)
      paramActivity.runOnUiThread(new Tencent.3(this, paramBundle, paramIUiListener, paramActivity));
  }

  public void showTaskGuideWindow(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new TaskGuide(paramActivity, this.mQQToken).showTaskGuideWindow(paramActivity, paramBundle, paramIUiListener);
  }

  public int story(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQToken).story(paramActivity, paramBundle, paramIUiListener);
    return -1;
  }

  public JSONObject upload(String paramString, Bundle paramBundle)
  {
    try
    {
      Bundle localBundle = fillParams(paramString, paramBundle);
      JSONObject localJSONObject = HttpUtils.upload(this.mQQToken.getAppContext(), paramString, localBundle);
      return localJSONObject;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
      return null;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return null;
    }
    catch (NetworkUnavailableException localNetworkUnavailableException)
    {
      localNetworkUnavailableException.printStackTrace();
      return null;
    }
    catch (HttpStatusException localHttpStatusException)
    {
      localHttpStatusException.printStackTrace();
      return null;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return null;
  }

  public void voice(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    new SocialApi(paramActivity, this.mQQToken).voice(paramActivity, paramBundle, paramIUiListener);
  }

  public void webViewDestory()
  {
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.Tencent
 * JD-Core Version:    0.6.0
 */