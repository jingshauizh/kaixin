package com.tencent.mm.sdk.platformtools;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import java.util.LinkedList;
import java.util.List;

public class LBSManager extends BroadcastReceiver
{
  private static LocationCache F;
  public static final String FILTER_GPS = "filter_gps";
  public static final int INVALID_ACC = -1000;
  public static final float INVALID_LAT = -1000.0F;
  public static final float INVALID_LNG = -1000.0F;
  public static final int MM_SOURCE_HARDWARE = 0;
  public static final int MM_SOURCE_NET = 1;
  public static final int MM_SOURCE_REPORT_HARWARE = 3;
  public static final int MM_SOURCE_REPORT_NETWORK = 4;
  private OnLocationGotListener G;
  private LocationManager H;
  private PendingIntent I;
  private boolean J = false;
  boolean K;
  boolean L = false;
  boolean M = false;
  int N;
  private MTimerHandler O = new MTimerHandler(new LBSManager.1(this), false);
  private Context q;

  public LBSManager(Context paramContext, OnLocationGotListener paramOnLocationGotListener)
  {
    this.G = paramOnLocationGotListener;
    this.K = false;
    this.N = 0;
    this.q = paramContext;
    PhoneUtil.getSignalStrength(paramContext);
    this.H = ((LocationManager)paramContext.getSystemService("location"));
    a();
    this.I = PendingIntent.getBroadcast(paramContext, 0, new Intent("filter_gps"), 134217728);
  }

  private boolean a()
  {
    LocationManager localLocationManager = this.H;
    int i = 0;
    if (localLocationManager != null);
    try
    {
      this.H.sendExtraCommand("gps", "force_xtra_injection", null);
      this.H.sendExtraCommand("gps", "force_time_injection", null);
      i = 1;
      return i;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private void b()
  {
    this.O.stopTimer();
    this.K = true;
  }

  public static void setLocationCache(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2)
  {
    if (paramInt1 == 0)
      return;
    Log.v("MicroMsg.LBSManager", "setLocationCache [" + paramFloat1 + "," + paramFloat2 + "] acc:" + paramInt1 + " source:" + paramInt2);
    if (F == null)
      F = new LocationCache();
    F.Q = paramFloat1;
    F.R = paramFloat2;
    F.S = paramInt1;
    F.time = System.currentTimeMillis();
    F.T = paramInt2;
  }

  public String getTelLocation()
  {
    return PhoneUtil.getCellXml(PhoneUtil.getCellInfoList(this.q));
  }

  public String getWIFILocation()
  {
    WifiManager localWifiManager = (WifiManager)this.q.getSystemService("wifi");
    if (localWifiManager == null)
    {
      Log.e("MicroMsg.LBSManager", "no wifi service");
      return "";
    }
    if (localWifiManager.getConnectionInfo() == null)
    {
      Log.e("MicroMsg.LBSManager", "WIFILocation wifi info null");
      return "";
    }
    LinkedList localLinkedList = new LinkedList();
    List localList = localWifiManager.getScanResults();
    if (localList != null)
      for (int i = 0; i < localList.size(); i++)
        localLinkedList.add(new PhoneUtil.MacInfo(((ScanResult)localList.get(i)).BSSID, ((ScanResult)localList.get(i)).level));
    return PhoneUtil.getMacXml(localLinkedList);
  }

  public boolean isGpsEnable()
  {
    try
    {
      boolean bool = this.H.isProviderEnabled("gps");
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public boolean isNetworkPrividerEnable()
  {
    try
    {
      boolean bool = this.H.isProviderEnabled("network");
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Location localLocation = (Location)paramIntent.getExtras().get("location");
    this.N = (1 + this.N);
    boolean bool;
    int i;
    String str1;
    String str2;
    if (localLocation != null)
    {
      bool = "gps".equals(localLocation.getProvider());
      if (((bool) && (localLocation.getAccuracy() <= 200.0F)) || ((!bool) && (localLocation.getAccuracy() <= 1000.0F) && (localLocation.getAccuracy() > 0.0F)))
      {
        if (!bool)
          break label276;
        i = 0;
        setLocationCache((float)localLocation.getLatitude(), (float)localLocation.getLongitude(), (int)localLocation.getAccuracy(), i);
        if ((this.G != null) && ((!this.K) || (!this.L) || (!this.M)))
        {
          str1 = Util.nullAsNil(getWIFILocation());
          str2 = Util.nullAsNil(getTelLocation());
          if (this.K)
            break label282;
          b();
          this.K = true;
          Log.v("MicroMsg.LBSManager", "location by provider ok:[" + localLocation.getLatitude() + " , " + localLocation.getLongitude() + "]  accuracy:" + localLocation.getAccuracy() + "  retry count:" + this.N + " isGpsProvider:" + bool);
          this.G.onLocationGot((float)localLocation.getLatitude(), (float)localLocation.getLongitude(), (int)localLocation.getAccuracy(), i, str1, str2, true);
        }
      }
    }
    label276: label282: 
    do
    {
      return;
      i = 1;
      break;
      if ((this.L) || (i != 0))
        continue;
      this.L = true;
      Log.v("MicroMsg.LBSManager", "report location by GPS ok:[" + localLocation.getLatitude() + " , " + localLocation.getLongitude() + "]  accuracy:" + localLocation.getAccuracy() + "  retry count:" + this.N + " isGpsProvider:" + bool);
      this.G.onLocationGot((float)localLocation.getLatitude(), (float)localLocation.getLongitude(), (int)localLocation.getAccuracy(), 3, str1, str2, true);
      return;
    }
    while ((this.M) || (i != 1));
    this.M = true;
    Log.v("MicroMsg.LBSManager", "report location by Network ok:[" + localLocation.getLatitude() + " , " + localLocation.getLongitude() + "]  accuracy:" + localLocation.getAccuracy() + "  retry count:" + this.N + " isGpsProvider:" + bool);
    this.G.onLocationGot((float)localLocation.getLatitude(), (float)localLocation.getLongitude(), (int)localLocation.getAccuracy(), 4, str1, str2, true);
  }

  public void removeGpsUpdate()
  {
    Log.v("MicroMsg.LBSManager", "removed gps update");
    if (this.H != null)
      this.H.removeUpdates(this.I);
    try
    {
      this.q.unregisterReceiver(this);
      return;
    }
    catch (Exception localException)
    {
      Log.v("MicroMsg.LBSManager", "location receiver has already unregistered");
    }
  }

  public void removeListener()
  {
    Log.v("MicroMsg.LBSManager", "removed gps update on destroy");
    removeGpsUpdate();
    if (this.O != null)
      b();
    this.G = null;
    this.q = null;
    this.O = null;
    this.H = null;
  }

  public void requestGpsUpdate()
  {
    requestGpsUpdate(500);
  }

  public void requestGpsUpdate(int paramInt)
  {
    if ((!isGpsEnable()) && (!isNetworkPrividerEnable()))
      return;
    if (paramInt > 0);
    while (true)
    {
      Log.v("MicroMsg.LBSManager", "requested gps update");
      IntentFilter localIntentFilter = new IntentFilter();
      localIntentFilter.addAction("filter_gps");
      this.q.registerReceiver(this, localIntentFilter);
      if (isGpsEnable())
        this.H.requestLocationUpdates("gps", paramInt, 0.0F, this.I);
      if (!isNetworkPrividerEnable())
        break;
      this.H.requestLocationUpdates("network", paramInt, 0.0F, this.I);
      return;
      paramInt = 500;
    }
  }

  public void resetForContinueGetLocation()
  {
    this.L = false;
    this.M = false;
  }

  public void start()
  {
    String str1 = Util.nullAsNil(getWIFILocation());
    String str2 = Util.nullAsNil(getTelLocation());
    int i;
    if ((isGpsEnable()) || (isNetworkPrividerEnable()))
    {
      i = 1;
      if ((i == 0) || (this.J))
        break label73;
      this.J = true;
      this.N = 0;
      requestGpsUpdate();
      this.O.startTimer(3000L);
    }
    label73: label245: 
    do
    {
      do
      {
        return;
        i = 0;
        break;
        int j;
        if (F == null)
          j = 0;
        while (true)
        {
          if (j == 0)
            break label245;
          if (this.G == null)
            break;
          this.K = true;
          Log.v("MicroMsg.LBSManager", "location by GPS cache ok:[" + F.Q + " , " + F.R + "]  accuracy:" + F.S + " source:" + F.T);
          this.G.onLocationGot(F.Q, F.R, F.S, F.T, str1, str2, true);
          return;
          if ((System.currentTimeMillis() - F.time > 180000L) || (F.S <= 0))
          {
            j = 0;
            continue;
          }
          j = 1;
        }
        this.K = true;
        if ((!str1.equals("")) || (!str2.equals("")))
          break label306;
        Log.v("MicroMsg.LBSManager", "get location by network failed");
      }
      while (this.G == null);
      this.G.onLocationGot(-1000.0F, -1000.0F, -1000, 0, "", "", false);
      return;
      Log.v("MicroMsg.LBSManager", "get location by network ok, macs : " + str1 + " cell ids :" + str2);
    }
    while (this.G == null);
    label306: this.G.onLocationGot(-1000.0F, -1000.0F, -1000, 0, str1, str2, true);
  }

  static class LocationCache
  {
    float Q = -1000.0F;
    float R = -1000.0F;
    int S = -1000;
    int T = 1;
    long time;
  }

  public static abstract interface OnLocationGotListener
  {
    public abstract void onLocationGot(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.LBSManager
 * JD-Core Version:    0.6.0
 */