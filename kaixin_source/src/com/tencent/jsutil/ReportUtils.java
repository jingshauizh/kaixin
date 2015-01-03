package com.tencent.jsutil;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;
import com.tencent.mta.TencentStat;
import com.tencent.open.cgireport.ReportManager;
import com.tencent.stat.StatConfig;
import com.tencent.tauth.QQToken;
import java.util.Properties;

public class ReportUtils
{
  private static final String BERNOULLI_REPORT_URL = "http://cgi.qplus.com/report/report";
  private static final int REPORT_BERNOULLI = 2;
  private static final int REPORT_QQ = 0;
  private static final int REPORT_TRACKCUSTOMEVENT = 1;
  private static final String REPORT_URL = "http://cgi.connect.qq.com/qqconnectutil/sdk";
  Context mContext = null;
  String mEvent = null;
  Handler mHandler = new ReportUtils.1(this);
  Properties mProperties = null;
  QQToken mQqToken = null;

  public ReportUtils(Context paramContext, QQToken paramQQToken)
  {
    this.mContext = paramContext;
    this.mQqToken = paramQQToken;
  }

  public static void reportBernoulli(Context paramContext, String paramString1, long paramLong, String paramString2)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("appid_for_getting_config", paramString2);
    localBundle.putString("strValue", paramString2);
    localBundle.putString("nValue", paramString1);
    localBundle.putString("qver", "2.2");
    if (paramLong != 0L)
      localBundle.putLong("elt", paramLong);
    new ReportUtils.2(paramContext, localBundle).start();
  }

  @JavascriptInterface
  public void cgiReport(String paramString1, long paramLong1, long paramLong2, long paramLong3, int paramInt, String paramString2, String paramString3, String paramString4)
  {
    ReportManager.getInstance().report(this.mContext, paramString1, paramLong1, paramLong2, paramLong3, paramInt, paramString2, paramString3, paramString4);
  }

  public Properties getProperties(String paramString1, String paramString2)
  {
    Properties localProperties = new Properties();
    localProperties.setProperty(paramString1, paramString2);
    return localProperties;
  }

  @JavascriptInterface
  public void reportBernoulli(String paramString1, long paramLong, String paramString2)
  {
    Message localMessage = new Message();
    localMessage.what = 2;
    Bundle localBundle = new Bundle();
    localBundle.putString("reportId", paramString1);
    localBundle.putString("appId", paramString2);
    localBundle.putLong("costTime", paramLong);
    localMessage.setData(localBundle);
    this.mHandler.sendMessage(localMessage);
  }

  @JavascriptInterface
  public void reportError(String paramString)
  {
    TencentStat.a(this.mContext, paramString);
  }

  @JavascriptInterface
  public void reportQQ(String paramString)
  {
    this.mHandler.sendEmptyMessage(0);
  }

  @JavascriptInterface
  public void setSessionTimoutMillis(int paramInt)
  {
    StatConfig.setSessionTimoutMillis(paramInt);
  }

  @JavascriptInterface
  public void startMonitor(String paramString1, String paramString2)
  {
    TencentStat.a(this.mContext, paramString1, paramString2);
  }

  @JavascriptInterface
  public void startQQ4Connect(String paramString)
  {
    if (paramString.equals("true"));
    for (boolean bool = true; ; bool = false)
    {
      TencentStat.a(this.mContext, this.mQqToken, bool, "http://cgi.connect.qq.com/qqconnectutil/sdk");
      return;
    }
  }

  @JavascriptInterface
  public void trackCustomBeginKVEvent(String paramString1, String paramString2, String paramString3)
  {
    Properties localProperties = getProperties(paramString1, paramString2);
    this.mProperties = localProperties;
    this.mEvent = paramString3;
    TencentStat.b(this.mContext, localProperties, paramString3);
  }

  @JavascriptInterface
  public void trackCustomEndKVEvent()
  {
    if ((this.mProperties != null) && (this.mEvent != null))
    {
      TencentStat.c(this.mContext, this.mProperties, this.mEvent);
      this.mProperties = null;
      this.mEvent = null;
    }
  }

  @JavascriptInterface
  public void trackCustomEvent(String paramString1, String paramString2)
  {
    Message localMessage = new Message();
    localMessage.what = 1;
    Bundle localBundle = new Bundle();
    localBundle.putString("eventId", paramString1);
    localBundle.putString("array", paramString2);
    localMessage.setData(localBundle);
    this.mHandler.sendMessage(localMessage);
  }

  @JavascriptInterface
  public void trackCustomKVEvent(String paramString1, String paramString2, String paramString3)
  {
    TencentStat.a(this.mContext, getProperties(paramString1, paramString2), paramString3);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.ReportUtils
 * JD-Core Version:    0.6.0
 */