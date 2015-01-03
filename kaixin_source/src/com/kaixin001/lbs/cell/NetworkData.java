package com.kaixin001.lbs.cell;

import java.io.Serializable;
import java.util.ArrayList;

class NetworkData
{
  private static final long serialVersionUID = 1L;
  private ArrayList<CellInfo> cellInfoList = new ArrayList();
  private ArrayList<WifiInfo> wifiInfoList = new ArrayList();

  public void addCellInfo(CellInfo paramCellInfo)
  {
    this.cellInfoList.add(paramCellInfo);
  }

  public void addWifiInfo(WifiInfo paramWifiInfo)
  {
    this.wifiInfoList.add(paramWifiInfo);
  }

  public void clear()
  {
    this.cellInfoList.clear();
    this.wifiInfoList.clear();
  }

  public ArrayList<CellInfo> getCellInfoList()
  {
    return this.cellInfoList;
  }

  public int getNetworkDataSize()
  {
    return this.cellInfoList.size() + this.wifiInfoList.size();
  }

  public ArrayList<WifiInfo> getWifiInfoList()
  {
    return this.wifiInfoList;
  }

  public boolean isCellInfoEmpty()
  {
    return this.cellInfoList.isEmpty();
  }

  public boolean isWifiInfoEmpty()
  {
    return this.wifiInfoList.isEmpty();
  }

  public static class CellInfo
    implements Serializable
  {
    private static final long serialVersionUID = 1L;
    private int cellId;
    private int locationAreaCode;
    private String mobileCountryCode;
    private String mobileNetworkCode;

    public int getCellId()
    {
      return this.cellId;
    }

    public int getLocationAreaCode()
    {
      return this.locationAreaCode;
    }

    public String getMobileCountryCode()
    {
      return this.mobileCountryCode;
    }

    public String getMobileNetworkCode()
    {
      return this.mobileNetworkCode;
    }

    public void setCellId(int paramInt)
    {
      this.cellId = paramInt;
    }

    public void setLocationAreaCode(int paramInt)
    {
      this.locationAreaCode = paramInt;
    }

    public void setMobileCountryCode(String paramString)
    {
      this.mobileCountryCode = paramString;
    }

    public void setMobileNetworkCode(String paramString)
    {
      this.mobileNetworkCode = paramString;
    }
  }

  public static class WifiInfo
    implements Serializable
  {
    private static final long serialVersionUID = 1L;
    private String bssid;

    public String getBssid()
    {
      return this.bssid;
    }

    public void setBssid(String paramString)
    {
      this.bssid = paramString;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.lbs.cell.NetworkData
 * JD-Core Version:    0.6.0
 */