package com.tencent.mm.sdk.platformtools;

import android.content.Context;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.List<Lcom.tencent.mm.sdk.platformtools.PhoneUtil.CellInfo;>;

class PhoneUtil16Impl
{
  private static int aJ = 10000;
  private static int aK = 10000;
  private TelephonyManager aL;
  private PhoneStateListener aM = new PhoneUtil16Impl.1(this);

  public List<PhoneUtil.CellInfo> getCellInfoList(Context paramContext)
  {
    LinkedList localLinkedList = new LinkedList();
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    String str1 = localTelephonyManager.getNetworkOperator();
    if ((str1 == null) || (str1.equals("")))
      return localLinkedList;
    String str2 = "460";
    Object localObject1 = "";
    try
    {
      str2 = str1.substring(0, 3);
      String str4 = str1.substring(3);
      localObject1 = str4;
    }
    catch (Exception localException1)
    {
      try
      {
        GsmCellLocation localGsmCellLocation = (GsmCellLocation)localTelephonyManager.getCellLocation();
        int i;
        int j;
        if (localGsmCellLocation != null)
        {
          i = localGsmCellLocation.getCid();
          j = localGsmCellLocation.getLac();
          if ((j < 65535) && (j != -1) && (i != -1))
            if (aK != aJ)
              break label290;
        }
        label290: String str3;
        for (Object localObject2 = ""; ; localObject2 = str3)
        {
          localLinkedList.add(new PhoneUtil.CellInfo(str2, (String)localObject1, String.valueOf(j), String.valueOf(i), (String)localObject2, "gsm", "", "", ""));
          List localList = localTelephonyManager.getNeighboringCellInfo();
          if ((localList == null) || (localList.size() <= 0))
            break label325;
          Iterator localIterator = localList.iterator();
          while (localIterator.hasNext())
          {
            NeighboringCellInfo localNeighboringCellInfo = (NeighboringCellInfo)localIterator.next();
            if (localNeighboringCellInfo.getCid() == -1)
              continue;
            localLinkedList.add(new PhoneUtil.CellInfo(str2, (String)localObject1, "", localNeighboringCellInfo.getCid(), "", "gsm", "", "", ""));
          }
          localException1 = localException1;
          localException1.printStackTrace();
          break;
          str3 = aK;
        }
      }
      catch (Exception localException2)
      {
        while (true)
          localException2.printStackTrace();
      }
    }
    label325: return (List<PhoneUtil.CellInfo>)(List<PhoneUtil.CellInfo>)localLinkedList;
  }

  public void getSignalStrength(Context paramContext)
  {
    this.aL = ((TelephonyManager)paramContext.getSystemService("phone"));
    this.aL.listen(this.aM, 256);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.PhoneUtil16Impl
 * JD-Core Version:    0.6.0
 */