package com.tencent.mm.sdk.platformtools;

import android.content.Context;
import android.os.Build.VERSION;
import java.util.List;

public final class PhoneUtil
{
  public static final String CELL_CDMA = "cdma";
  public static final String CELL_GSM = "gsm";
  private static final int aI = 17;

  public static List<CellInfo> getCellInfoList(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 5)
      return new PhoneUtil20Impl().getCellInfoList(paramContext);
    return new PhoneUtil16Impl().getCellInfoList(paramContext);
  }

  public static String getCellXml(List<CellInfo> paramList)
  {
    Object localObject;
    if ((paramList == null) || (paramList.size() <= 0))
      localObject = "";
    while (true)
    {
      return localObject;
      localObject = "";
      int i = 0;
      while (i < paramList.size())
      {
        String str1 = (String)localObject + "<cell ";
        String str2 = str1 + "mcc=\"" + ((CellInfo)paramList.get(i)).mcc + "\" ";
        String str3 = str2 + "mnc=\"" + ((CellInfo)paramList.get(i)).mnc + "\" ";
        String str4 = str3 + "lac=\"" + ((CellInfo)paramList.get(i)).lac + "\" ";
        String str5 = str4 + "type=\"" + ((CellInfo)paramList.get(i)).type + "\" ";
        String str6 = str5 + "stationId=\"" + ((CellInfo)paramList.get(i)).stationId + "\" ";
        String str7 = str6 + "networkId=\"" + ((CellInfo)paramList.get(i)).networkId + "\" ";
        String str8 = str7 + "systemId=\"" + ((CellInfo)paramList.get(i)).systemId + "\" ";
        String str9 = str8 + "dbm=\"" + ((CellInfo)paramList.get(i)).dbm + "\" ";
        String str10 = str9 + " >";
        String str11 = str10 + ((CellInfo)paramList.get(i)).cellid;
        String str12 = str11 + "</cell>";
        i++;
        localObject = str12;
      }
    }
  }

  public static String getMacXml(List<MacInfo> paramList)
  {
    Object localObject1 = "";
    if ((paramList == null) || (paramList.size() <= 0))
    {
      localObject1 = "";
      return localObject1;
    }
    int i = 0;
    label23: String str4;
    if (i < paramList.size())
    {
      if ((paramList.get(i) == null) || (((MacInfo)paramList.get(i)).mac.length() != aI))
        break label213;
      String str1 = (String)localObject1 + "<mac ";
      String str2 = str1 + "macDbm=\"" + ((MacInfo)paramList.get(i)).dbm + "\"";
      String str3 = str2 + ">";
      str4 = str3 + ((MacInfo)paramList.get(i)).mac;
    }
    label213: for (Object localObject2 = str4 + "</mac>"; ; localObject2 = localObject1)
    {
      i++;
      localObject1 = localObject2;
      break label23;
      break;
    }
  }

  public static void getSignalStrength(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 5)
    {
      new PhoneUtil20Impl().getSignalStrength(paramContext);
      return;
    }
    new PhoneUtil16Impl().getSignalStrength(paramContext);
  }

  public static class CellInfo
  {
    public static final int MAX_CID = 65535;
    public static final int MAX_LAC = 65535;
    public String cellid;
    public String dbm;
    public String lac;
    public String mcc;
    public String mnc;
    public String networkId;
    public String stationId;
    public String systemId;
    public String type;

    public CellInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
    {
      this.mcc = paramString1;
      this.mnc = paramString2;
      this.lac = paramString3;
      this.type = paramString6;
      this.cellid = paramString4;
      this.stationId = paramString7;
      this.networkId = paramString8;
      this.systemId = paramString9;
      this.dbm = paramString5;
    }
  }

  public static class MacInfo
  {
    public String dbm;
    public String mac;

    public MacInfo(String paramString1, String paramString2)
    {
      this.mac = paramString1;
      this.dbm = paramString2;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.PhoneUtil
 * JD-Core Version:    0.6.0
 */