package com.kaixin001.activity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.util.DisplayMetrics;
import com.kaixin001.activity.wxapi.WXManager;
import com.kaixin001.businesslogic.CrashHandler;
import com.kaixin001.businesslogic.ThirdPartyAppUtil;
import com.kaixin001.db.UserDBAdapter;
import com.kaixin001.lbs.LocationService;
import com.kaixin001.lbs.RefreshLandmarkProxy;
import com.kaixin001.model.Setting;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownLoaderManager;
import com.kaixin001.util.ImageDownloader;
import com.kaixin001.util.InnerPushManager;
import com.umeng.analytics.MobclickAgent;
import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class KXApplication extends Application
{
  private static final int CONNECTION_TIMEOUT = 30000;
  private static final int MAX_TOTAL_CONNECTION = 5;
  private static final int SO_TIMEOUT = 60000;
  public static float density;
  private static KXApplication instance = null;
  public RefreshLandmarkProxy buildingUtil;
  private HttpClient httpClient;
  public ThirdPartyAppUtil thirdPartyAppUtil;
  private WXManager wm = WXManager.getInstance();

  private HttpClient createHttpClient()
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    ConnManagerParams.setMaxTotalConnections(localBasicHttpParams, 5);
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 30000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 60000);
    HttpProtocolParams.setVersion(localBasicHttpParams, HttpVersion.HTTP_1_1);
    HttpProtocolParams.setContentCharset(localBasicHttpParams, "ISO-8859-1");
    HttpProtocolParams.setUseExpectContinue(localBasicHttpParams, true);
    SchemeRegistry localSchemeRegistry = new SchemeRegistry();
    localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    localSchemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
    return new DefaultHttpClient(new ThreadSafeClientConnManager(localBasicHttpParams, localSchemeRegistry), localBasicHttpParams);
  }

  public static KXApplication getInstance()
  {
    return instance;
  }

  public void clearCountLogin()
  {
    getSharedPreferences("loginNum", 0).edit().putInt("loginCount", 0).commit();
  }

  public HttpClient getHttpClient()
  {
    if (this.httpClient == null)
      this.httpClient = createHttpClient();
    NetworkInfo localNetworkInfo = ((ConnectivityManager)getSystemService("connectivity")).getActiveNetworkInfo();
    if ((localNetworkInfo != null) && (localNetworkInfo.getType() == 0))
    {
      String str = Proxy.getDefaultHost();
      int i = Proxy.getDefaultPort();
      if ((str == null) || (i == -1))
        break label91;
      HttpHost localHttpHost = new HttpHost(str, i);
      this.httpClient.getParams().setParameter("http.route.default-proxy", localHttpHost);
    }
    while (true)
    {
      return this.httpClient;
      label91: this.httpClient.getParams().setParameter("http.route.default-proxy", null);
    }
  }

  public void onCreate()
  {
    super.onCreate();
    density = getResources().getDisplayMetrics().density;
    this.httpClient = createHttpClient();
    Context localContext = getApplicationContext();
    ImageCache.getInstance().setContext(localContext);
    ImageDownLoaderManager.getInstance().setContext(localContext);
    ImageDownloader.getInstance().setContext(localContext);
    instance = this;
    LocationService.getLocationService().setContext(this);
    this.thirdPartyAppUtil = new ThirdPartyAppUtil(this);
    if (!Setting.getInstance().isTestVersion())
      MobclickAgent.onError(localContext);
    CrashHandler.getInstance().init(getApplicationContext());
    KXEnvironment.init(localContext);
    this.wm.regToWx(this);
  }

  public void onLowMemory()
  {
    ImageCache.getInstance().clear();
    System.gc();
    super.onLowMemory();
    shutdownHttpClient();
  }

  public void onTerminate()
  {
    super.onTerminate();
    shutdownHttpClient();
    ImageCache.getInstance().clear();
    LocationService.getLocationService().destroy();
    clearCountLogin();
    InnerPushManager.getInstance(this).setLoginTime();
    new UserDBAdapter(this).clearAllUsersInfo();
  }

  public void shutdownHttpClient()
  {
    if ((this.httpClient != null) && (this.httpClient.getConnectionManager() != null));
    try
    {
      this.httpClient.getConnectionManager().shutdown();
      this.httpClient = null;
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.KXApplication
 * JD-Core Version:    0.6.0
 */