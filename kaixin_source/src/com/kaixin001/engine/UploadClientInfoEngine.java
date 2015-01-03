package com.kaixin001.engine;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import java.util.HashMap;
import java.util.Locale;

public class UploadClientInfoEngine extends KXEngine
{
  private static UploadClientInfoEngine instance;
  private String community = "";
  private String device = "";
  private String imei = "";
  private String imsi = "";
  private String language = "";
  private String lat = "";
  private String location = "";
  private String lon = "";
  private String mac = "";
  private String mobile = "";
  private String ostype = "14";
  private String station = "";

  private void getClientInfo(Context paramContext)
  {
    WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    LocationManager localLocationManager = (LocationManager)paramContext.getSystemService("location");
    this.mac = localWifiManager.getConnectionInfo().getMacAddress();
    this.imei = localTelephonyManager.getDeviceId();
    this.imsi = localTelephonyManager.getSubscriberId();
    this.mobile = localTelephonyManager.getLine1Number();
    this.device = (Build.MANUFACTURER + "_" + Build.MODEL);
    this.language = Locale.getDefault().toString();
    String str = this.imsi;
    int i = 0;
    int j = 0;
    Location localLocation;
    if (str != null)
    {
      if ((this.imsi.startsWith("46000")) || (this.imsi.startsWith("46002")) || (this.imsi.startsWith("46001")))
      {
        GsmCellLocation localGsmCellLocation = (GsmCellLocation)localTelephonyManager.getCellLocation();
        j = localGsmCellLocation.getLac();
        i = localGsmCellLocation.getCid();
      }
    }
    else
    {
      if (i != 0)
        this.station = i;
      if (j != 0)
        this.community = j;
      if (!localLocationManager.isProviderEnabled("gps"))
        break label391;
      localLocation = localLocationManager.getLastKnownLocation("gps");
    }
    while (true)
    {
      if (localLocation != null)
      {
        this.lat = localLocation.getLatitude();
        this.lon = localLocation.getLongitude();
        this.location = ("latitude[" + this.lat + "]_longitude[" + this.lon + "]");
      }
      return;
      boolean bool2 = this.imsi.startsWith("46003");
      i = 0;
      j = 0;
      if (!bool2)
        break;
      CdmaCellLocation localCdmaCellLocation = (CdmaCellLocation)localTelephonyManager.getCellLocation();
      j = localCdmaCellLocation.getNetworkId();
      i = localCdmaCellLocation.getBaseStationId() / 16;
      break;
      label391: boolean bool1 = localLocationManager.isProviderEnabled("network");
      localLocation = null;
      if (!bool1)
        continue;
      localLocation = localLocationManager.getLastKnownLocation("network");
    }
  }

  public static UploadClientInfoEngine getInstance()
  {
    if (instance == null)
      instance = new UploadClientInfoEngine();
    return instance;
  }

  public static final boolean isOPen(Context paramContext)
  {
    LocationManager localLocationManager = (LocationManager)paramContext.getSystemService("location");
    boolean bool1 = localLocationManager.isProviderEnabled("gps");
    boolean bool2 = localLocationManager.isProviderEnabled("network");
    return (bool1) || (bool2);
  }

  private void uploadByGet(Context paramContext)
  {
    String str = Protocol.getInstance().uploadClientInfoUrl(this.mac, this.imei, this.imsi, this.station, this.community, this.mobile, this.device, this.language, this.location, this.ostype, this.lat, this.lon);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      localHttpProxy.httpGet(str, null, null);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void uploadByPost(Context paramContext)
  {
    String str = Protocol.getInstance().getUploadClientInfoUrl();
    HashMap localHashMap = new HashMap();
    localHashMap.put("mac", this.mac);
    localHashMap.put("imei", this.imei);
    localHashMap.put("imsi", this.imsi);
    localHashMap.put("station", this.station);
    localHashMap.put("community", this.community);
    localHashMap.put("mobile", this.mobile);
    localHashMap.put("device", this.device);
    localHashMap.put("language", this.language);
    localHashMap.put("location", this.location);
    localHashMap.put("ostype", this.ostype);
    localHashMap.put("lat", this.lat);
    localHashMap.put("lon", this.lon);
    localHashMap.put("uid", User.getInstance().getUID());
    localHashMap.put("api_version", "3.9.9");
    localHashMap.put("version", "android-3.9.9");
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      localHttpProxy.httpPost(str, localHashMap, null, null);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void UploadClientInfo(Context paramContext)
    throws Exception
  {
    getClientInfo(paramContext);
    uploadByGet(paramContext);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.UploadClientInfoEngine
 * JD-Core Version:    0.6.0
 */