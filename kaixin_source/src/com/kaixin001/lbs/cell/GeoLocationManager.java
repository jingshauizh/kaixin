package com.kaixin001.lbs.cell;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GeoLocationManager
{
  private static final String SERVER_URL = "http://www.google.com/loc/json";
  private static final String TAG = "GeoLocationManager";
  private String mBssid = null;
  private NetworkData.CellInfo mCellInfo = null;
  private Context mContext = null;
  private LocationListener mLocationListener = null;
  private NetworkData mNetworkData = null;
  private boolean mRegisterWifi = false;
  private String mResult = null;
  private TelephonyManager mTelephonyManager = null;
  private WifiManager mWifiManager = null;
  private WifiScanReceiver mWifiScanReceiver = null;

  public GeoLocationManager(Context paramContext, LocationListener paramLocationListener)
  {
    this.mContext = paramContext;
    this.mLocationListener = paramLocationListener;
    this.mNetworkData = new NetworkData();
    this.mTelephonyManager = ((TelephonyManager)this.mContext.getSystemService("phone"));
    this.mWifiManager = ((WifiManager)this.mContext.getSystemService("wifi"));
    this.mWifiScanReceiver = new WifiScanReceiver();
    this.mRegisterWifi = false;
  }

  private void cleanData()
  {
    this.mResult = null;
    this.mBssid = null;
    this.mCellInfo = null;
    this.mNetworkData.clear();
  }

  private void getCellInfo()
  {
    int i = 1;
    KXLog.d("GeoLocationManager", "WifiInfoManager: getCellInfo()...");
    while (true)
    {
      int j;
      try
      {
        if (this.mTelephonyManager == null)
          return;
        j = this.mTelephonyManager.getPhoneType();
        KXLog.d("GeoLocationManager", "type:" + j);
        if (j == i)
        {
          localObject = new CellLocationUtil.GsmCellLocationUtil();
          if (localObject == null)
            break;
          this.mCellInfo = ((CellLocationUtil)localObject).getCellInfo(this.mTelephonyManager);
          StringBuilder localStringBuilder = new StringBuilder("mCellInfo is null?");
          if (this.mCellInfo != null)
            break label324;
          KXLog.d("GeoLocationManager", i);
          if (this.mCellInfo == null)
            break;
          this.mNetworkData.addCellInfo(this.mCellInfo);
          List localList = this.mTelephonyManager.getNeighboringCellInfo();
          if (localList == null)
            break;
          Iterator localIterator = localList.iterator();
          if (!localIterator.hasNext())
            break;
          int k = ((NeighboringCellInfo)localIterator.next()).getCid();
          if ((k == -1) || (k == this.mCellInfo.getCellId()))
            continue;
          NetworkData.CellInfo localCellInfo = new NetworkData.CellInfo();
          localCellInfo.setCellId(k);
          localCellInfo.setLocationAreaCode(this.mCellInfo.getLocationAreaCode());
          localCellInfo.setMobileCountryCode(this.mCellInfo.getMobileCountryCode());
          localCellInfo.setMobileNetworkCode(this.mCellInfo.getMobileNetworkCode());
          this.mNetworkData.addCellInfo(localCellInfo);
          continue;
        }
      }
      catch (Exception localException)
      {
        KXLog.d("GeoLocationManager", "getCellInfo() error:" + localException.toString());
        localException.printStackTrace();
        return;
      }
      if ((j != 2) || (new Integer(Build.VERSION.SDK).intValue() <= 4))
        break;
      Object localObject = new CellLocationUtil.CdmaCellLocationUtil();
      continue;
      label324: i = 0;
    }
  }

  private void getWifiInfo()
  {
    KXLog.d("GeoLocationManager", "WifiInfoManager: getWifiInfo()...");
    this.mBssid = this.mWifiManager.getConnectionInfo().getBSSID();
    if (!TextUtils.isEmpty(this.mBssid))
    {
      NetworkData.WifiInfo localWifiInfo = new NetworkData.WifiInfo();
      localWifiInfo.setBssid(this.mBssid);
      KXLog.d("GeoLocationManager", "WifiScanReceiver: addWifiInfo >> " + this.mBssid);
      this.mNetworkData.addWifiInfo(localWifiInfo);
    }
    boolean bool1 = this.mWifiManager.isWifiEnabled();
    boolean bool2 = false;
    if (bool1)
      bool2 = this.mWifiManager.startScan();
    if (!bool2)
      updateLocation();
  }

  private String netwrokDatatoJsonString(NetworkData paramNetworkData, String paramString, int paramInt)
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    localJSONObject1.put("version", "1.1.0");
    localJSONObject1.put("request_address", false);
    localJSONObject1.put("radio_type", paramString);
    localJSONObject1.put("is_chinacdma", paramInt);
    JSONArray localJSONArray1 = new JSONArray();
    JSONArray localJSONArray2 = new JSONArray();
    Iterator localIterator1 = Arrays.asList((NetworkData.CellInfo[])paramNetworkData.getCellInfoList().toArray(new NetworkData.CellInfo[0])).iterator();
    Iterator localIterator2;
    if (!localIterator1.hasNext())
    {
      localJSONObject1.put("cell_towers", localJSONArray1);
      localIterator2 = Arrays.asList((NetworkData.WifiInfo[])paramNetworkData.getWifiInfoList().toArray(new NetworkData.WifiInfo[0])).iterator();
    }
    while (true)
    {
      if (!localIterator2.hasNext())
      {
        localJSONObject1.put("wifi_towers", localJSONArray2);
        return localJSONObject1.toString();
        NetworkData.CellInfo localCellInfo = (NetworkData.CellInfo)localIterator1.next();
        JSONObject localJSONObject2 = new JSONObject();
        localJSONObject2.put("cell_id", localCellInfo.getCellId());
        localJSONObject2.put("location_area_code", localCellInfo.getLocationAreaCode());
        localJSONObject2.put("mobile_country_code", localCellInfo.getMobileCountryCode());
        localJSONObject2.put("mobile_network_code", localCellInfo.getMobileNetworkCode());
        localJSONArray1.put(localJSONObject2);
        break;
      }
      NetworkData.WifiInfo localWifiInfo = (NetworkData.WifiInfo)localIterator2.next();
      JSONObject localJSONObject3 = new JSONObject();
      localJSONObject3.put("mac_address", localWifiInfo.getBssid());
      localJSONArray2.put(localJSONObject3);
    }
  }

  private void sendMsgToServer()
  {
    if (this.mTelephonyManager == null)
      return;
    String str1 = "";
    if (this.mTelephonyManager.getPhoneType() == 1)
      str1 = "gsm";
    while (true)
    {
      String str2 = this.mTelephonyManager.getNetworkOperator();
      boolean bool1 = TextUtils.isEmpty(str2);
      int i = 0;
      if (!bool1)
      {
        int j = str2.length();
        i = 0;
        if (j >= 3)
        {
          boolean bool2 = str2.substring(0, 3).equals("460");
          i = 0;
          if (bool2)
          {
            int k = this.mTelephonyManager.getNetworkType();
            i = 0;
            if (k != 1)
            {
              i = 0;
              if (k != 2)
              {
                i = 0;
                if (k != 3)
                  i = 1;
              }
            }
          }
        }
      }
      try
      {
        String str3 = netwrokDatatoJsonString(this.mNetworkData, str1, i);
        KXLog.d("GeoLocationManager", "request:" + str3);
        this.mResult = NetworkRequest.doPost("http://www.google.com/loc/json", str3);
        return;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        this.mResult = null;
        return;
      }
      if (this.mTelephonyManager.getPhoneType() != 2)
        continue;
      str1 = "cdma";
    }
  }

  private void updateLocation()
  {
    removeUpdates();
    new GetLocationTask().execute(new Void[0]);
  }

  public void removeUpdates()
  {
    if (this.mRegisterWifi)
    {
      this.mRegisterWifi = false;
      this.mContext.unregisterReceiver(this.mWifiScanReceiver);
    }
  }

  public void update()
  {
    KXLog.d("GeoLocationManager", "WifiInfoManager: update()...");
    cleanData();
    this.mContext.registerReceiver(this.mWifiScanReceiver, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
    this.mRegisterWifi = true;
    getCellInfo();
    getWifiInfo();
  }

  class GetLocationTask extends AsyncTask<Void, Void, Void>
  {
    GetLocationTask()
    {
    }

    protected Void doInBackground(Void[] paramArrayOfVoid)
    {
      if (GeoLocationManager.this.mNetworkData.getNetworkDataSize() > 0)
        GeoLocationManager.this.sendMsgToServer();
      return null;
    }

    protected void onPostExecute(Void paramVoid)
    {
      if (GeoLocationManager.this.mResult == null);
      while (true)
      {
        return;
        try
        {
          JSONObject localJSONObject = new JSONObject(GeoLocationManager.this.mResult).optJSONObject("location");
          if ((GeoLocationManager.this.mLocationListener == null) || (localJSONObject == null))
            continue;
          Location localLocation = new Location("wifi");
          localLocation.setLatitude(localJSONObject.optDouble("latitude"));
          localLocation.setLongitude(localJSONObject.optDouble("longitude"));
          localLocation.setAccuracy((float)localJSONObject.optDouble("accuracy"));
          localLocation.setTime(System.currentTimeMillis());
          Bundle localBundle = new Bundle();
          localBundle.putInt("wifi_num", GeoLocationManager.this.mNetworkData.getWifiInfoList().size());
          localLocation.setExtras(localBundle);
          GeoLocationManager.this.mLocationListener.onLocationChanged(localLocation);
          return;
        }
        catch (JSONException localJSONException)
        {
          KXLog.e("GeoLocationManager", "GetLocationTask", localJSONException);
        }
      }
    }
  }

  class WifiScanReceiver extends BroadcastReceiver
  {
    WifiScanReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      KXLog.d("GeoLocationManager", "WifiScanReceiver: onReceive()...");
      Iterator localIterator;
      if ((GeoLocationManager.this.mWifiManager != null) && (GeoLocationManager.this.mWifiManager.getScanResults() != null))
        localIterator = GeoLocationManager.this.mWifiManager.getScanResults().iterator();
      while (true)
      {
        if (!localIterator.hasNext())
        {
          GeoLocationManager.this.updateLocation();
          return;
        }
        ScanResult localScanResult = (ScanResult)localIterator.next();
        if (localScanResult.BSSID.equals(GeoLocationManager.this.mBssid))
          continue;
        NetworkData.WifiInfo localWifiInfo = new NetworkData.WifiInfo();
        localWifiInfo.setBssid(localScanResult.BSSID);
        KXLog.d("TESTAPP", "WifiScanReceiver: addWifiInfo >> " + localScanResult.BSSID);
        GeoLocationManager.this.mNetworkData.addWifiInfo(localWifiInfo);
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.lbs.cell.GeoLocationManager
 * JD-Core Version:    0.6.0
 */