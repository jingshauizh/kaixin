package com.tencent.open.cgireport;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import com.tencent.common.OpenConfig;
import java.util.ArrayList;
import java.util.Random;

public class ReportManager
{
  public static final String POST = "POST";
  private static ReportManager mInstance = null;
  private e dataModal;
  private long mLastReportTime = 0L;
  private Random mRand = new Random();
  private int mReportRetryCount = 3;
  private boolean mUploading = false;
  private ArrayList<a> newItems = new ArrayList();
  private ArrayList<a> oldItems = new ArrayList();

  private boolean availableForCount(Context paramContext)
  {
    int i = OpenConfig.a(paramContext, null).b("Common_CGIReportMaxcount");
    Log.d("OpenConfig_test", "config 6:Common_CGIReportMaxcount     config_value:" + i);
    if (i == 0)
      i = 20;
    Log.d("OpenConfig_test", "config 6:Common_CGIReportMaxcount     result_value:" + i);
    if (this.dataModal.e() >= i)
    {
      Log.i("cgi_report_debug", "ReportManager availableForCount = ture");
      return true;
    }
    Log.i("cgi_report_debug", "ReportManager availableForCount = false");
    return false;
  }

  private boolean availableForFrequency(Context paramContext, int paramInt)
  {
    int i = countFrequency(paramContext, paramInt);
    if (this.mRand.nextInt(100) < i)
    {
      Log.i("cgi_report_debug", "ReportManager availableForFrequency = ture");
      return true;
    }
    Log.i("cgi_report_debug", "ReportManager availableForFrequency = false");
    return false;
  }

  private boolean availableForTime(Context paramContext)
  {
    long l1 = OpenConfig.a(paramContext, null).c("Common_CGIReportTimeinterval");
    Log.d("OpenConfig_test", "config 5:Common_CGIReportTimeinterval     config_value:" + l1);
    if (l1 == 0L)
      l1 = 1200L;
    Log.d("OpenConfig_test", "config 5:Common_CGIReportTimeinterval     result_value:" + l1);
    long l2 = System.currentTimeMillis() / 1000L;
    if ((this.mLastReportTime == 0L) || (l1 + this.mLastReportTime <= l2))
    {
      this.mLastReportTime = l2;
      Log.i("cgi_report_debug", "ReportManager availableForTime = ture");
      return true;
    }
    Log.i("cgi_report_debug", "ReportManager availableForTime = false");
    return false;
  }

  private int countFrequency(Context paramContext, int paramInt)
  {
    if (paramInt == 0)
    {
      int j = OpenConfig.a(paramContext, null).b("Common_CGIReportFrequencySuccess");
      Log.d("OpenConfig_agent", "config 4:Common_CGIReportFrequencySuccess     config_value:" + j);
      if (j == 0)
        j = 10;
      Log.d("OpenConfig_agent", "config 4:Common_CGIReportFrequencySuccess     result_value:" + j);
      return j;
    }
    int i = OpenConfig.a(paramContext, null).b("Common_CGIReportFrequencyFailed");
    Log.d("OpenConfig_agent", "config 4:Common_CGIReportFrequencyFailed     config_value:" + i);
    if (i == 0)
      i = 100;
    Log.d("OpenConfig_agent", "config 4:Common_CGIReportFrequencyFailed     result_value:" + i);
    return i;
  }

  private void doUpload(Context paramContext, String paramString)
  {
    Log.i("cgi_report_debug", "ReportManager doUpload start");
    this.mUploading = true;
    this.newItems = this.dataModal.c();
    this.dataModal.b();
    this.oldItems = this.dataModal.d();
    this.dataModal.a();
    Bundle localBundle = new Bundle();
    localBundle.putString("appid", paramString);
    localBundle.putString("releaseversion", "QQConnect_SDK_Android_1_7");
    localBundle.putString("device", Build.DEVICE);
    localBundle.putString("qua", "V1_AND_OpenSDK_2.2_1077_RDM_B");
    localBundle.putString("key", "apn,frequency,commandid,resultcode,tmcost,reqsize,rspsize,detail,deviceinfo");
    for (int i = 0; i < this.newItems.size(); i++)
    {
      localBundle.putString(i + "_1", ((a)this.newItems.get(i)).a());
      localBundle.putString(i + "_2", ((a)this.newItems.get(i)).b());
      localBundle.putString(i + "_3", ((a)this.newItems.get(i)).c());
      localBundle.putString(i + "_4", ((a)this.newItems.get(i)).d());
      localBundle.putString(i + "_5", ((a)this.newItems.get(i)).e());
      localBundle.putString(i + "_6", ((a)this.newItems.get(i)).f());
      localBundle.putString(i + "_7", ((a)this.newItems.get(i)).g());
      localBundle.putString(i + "_8", ((a)this.newItems.get(i)).h());
      String str2 = c.b(paramContext) + ((a)this.newItems.get(i)).i();
      localBundle.putString(i + "_9", str2);
    }
    for (int j = this.newItems.size(); j < this.oldItems.size() + this.newItems.size(); j++)
    {
      int k = j - this.newItems.size();
      localBundle.putString(j + "_1", ((a)this.oldItems.get(k)).a());
      localBundle.putString(j + "_2", ((a)this.oldItems.get(k)).b());
      localBundle.putString(j + "_3", ((a)this.oldItems.get(k)).c());
      localBundle.putString(j + "_4", ((a)this.oldItems.get(k)).d());
      localBundle.putString(j + "_5", ((a)this.oldItems.get(k)).e());
      localBundle.putString(j + "_6", ((a)this.oldItems.get(k)).f());
      localBundle.putString(j + "_7", ((a)this.oldItems.get(k)).g());
      localBundle.putString(j + "_8", ((a)this.newItems.get(k)).h());
      String str1 = c.b(paramContext) + ((a)this.newItems.get(k)).i();
      localBundle.putString(j + "_9", str1);
    }
    doUploadItems(paramContext, "http://wspeed.qq.com/w.cgi", "POST", localBundle);
  }

  private void doUploadItems(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
  {
    new d(this, paramString1, paramContext, paramBundle).start();
  }

  private String getAPN(Context paramContext)
  {
    try
    {
      ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (localConnectivityManager == null)
      {
        Log.e("cgi_report_debug", "ReportManager getAPN failed:ConnectivityManager == null");
        return "no_net";
      }
      NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
      if ((localNetworkInfo == null) || (!localNetworkInfo.isAvailable()))
      {
        Log.e("cgi_report_debug", "ReportManager getAPN failed:NetworkInfo == null");
        return "no_net";
      }
      if (localNetworkInfo.getTypeName().toUpperCase().equals("WIFI"))
      {
        Log.i("cgi_report_debug", "ReportManager getAPN type = wifi");
        return "wifi";
      }
      String str1 = localNetworkInfo.getExtraInfo();
      if (str1 == null)
      {
        Log.e("cgi_report_debug", "ReportManager getAPN failed:extraInfo == null");
        return "mobile_unknow";
      }
      String str2 = str1.toLowerCase();
      Log.i("cgi_report_debug", "ReportManager getAPN type = " + str2);
      return str2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "unknow";
  }

  public static ReportManager getInstance()
  {
    if (mInstance == null)
      mInstance = new ReportManager();
    return mInstance;
  }

  private void updateDB(Context paramContext, String paramString1, long paramLong1, long paramLong2, long paramLong3, int paramInt, String paramString2, String paramString3)
  {
    long l = SystemClock.elapsedRealtime() - paramLong1;
    Log.i("cgi_report_debug", "ReportManager updateDB url=" + paramString1 + ",resultCode=" + paramInt + ",timeCost=" + l + ",reqSize=" + paramLong2 + ",rspSize=" + paramLong3);
    int i = 100 / countFrequency(paramContext, paramInt);
    int j;
    if (i <= 0)
      j = 1;
    while (true)
    {
      String str = getAPN(paramContext);
      this.dataModal.a(str, j + "", paramString1, paramInt, l, paramLong2, paramLong3, paramString3);
      return;
      if (i > 100)
      {
        j = 100;
        continue;
      }
      j = i;
    }
  }

  public void report(Context paramContext, String paramString1, long paramLong1, long paramLong2, long paramLong3, int paramInt, String paramString2)
  {
    report(paramContext, paramString1, paramLong1, paramLong2, paramLong3, paramInt, paramString2, "", null);
  }

  public void report(Context paramContext, String paramString1, long paramLong1, long paramLong2, long paramLong3, int paramInt, String paramString2, String paramString3, String paramString4)
  {
    if (paramString4 == null)
      paramString4 = "1000067";
    if (this.dataModal == null)
      this.dataModal = new e(paramContext);
    if (availableForFrequency(paramContext, paramInt) == true)
    {
      updateDB(paramContext, paramString1, paramLong1, paramLong2, paramLong3, paramInt, paramString2, paramString3);
      if (this.mUploading != true)
        break label66;
    }
    label66: 
    do
    {
      return;
      if (availableForTime(paramContext) != true)
        continue;
      doUpload(paramContext, paramString4);
      return;
    }
    while (availableForCount(paramContext) != true);
    doUpload(paramContext, paramString4);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.cgireport.ReportManager
 * JD-Core Version:    0.6.0
 */