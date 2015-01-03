package com.kaixin001.lbs.cell;

import android.telephony.TelephonyManager;

public abstract interface CellLocationUtil
{
  public abstract NetworkData.CellInfo getCellInfo(TelephonyManager paramTelephonyManager);

  public static class CdmaCellLocationUtil
    implements CellLocationUtil
  {
    // ERROR //
    public NetworkData.CellInfo getCellInfo(TelephonyManager paramTelephonyManager)
    {
      // Byte code:
      //   0: aload_1
      //   1: invokevirtual 20	android/telephony/TelephonyManager:getCellLocation	()Landroid/telephony/CellLocation;
      //   4: checkcast 22	android/telephony/cdma/CdmaCellLocation
      //   7: astore_3
      //   8: aconst_null
      //   9: astore 4
      //   11: aload_3
      //   12: ifnull +110 -> 122
      //   15: aload_3
      //   16: invokevirtual 26	android/telephony/cdma/CdmaCellLocation:getBaseStationId	()I
      //   19: istore 5
      //   21: aconst_null
      //   22: astore 4
      //   24: iload 5
      //   26: ifle +96 -> 122
      //   29: new 28	com/kaixin001/lbs/cell/NetworkData$CellInfo
      //   32: dup
      //   33: invokespecial 29	com/kaixin001/lbs/cell/NetworkData$CellInfo:<init>	()V
      //   36: astore 6
      //   38: aload 6
      //   40: aload_3
      //   41: invokevirtual 26	android/telephony/cdma/CdmaCellLocation:getBaseStationId	()I
      //   44: invokevirtual 33	com/kaixin001/lbs/cell/NetworkData$CellInfo:setCellId	(I)V
      //   47: aload 6
      //   49: aload_3
      //   50: invokevirtual 36	android/telephony/cdma/CdmaCellLocation:getNetworkId	()I
      //   53: invokevirtual 39	com/kaixin001/lbs/cell/NetworkData$CellInfo:setLocationAreaCode	(I)V
      //   56: aload_1
      //   57: invokevirtual 43	android/telephony/TelephonyManager:getNetworkOperator	()Ljava/lang/String;
      //   60: astore 7
      //   62: aload 7
      //   64: invokestatic 49	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   67: ifne +39 -> 106
      //   70: aload 7
      //   72: invokevirtual 54	java/lang/String:length	()I
      //   75: iconst_3
      //   76: if_icmplt +30 -> 106
      //   79: aload 6
      //   81: aload_1
      //   82: invokevirtual 43	android/telephony/TelephonyManager:getNetworkOperator	()Ljava/lang/String;
      //   85: iconst_0
      //   86: iconst_3
      //   87: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
      //   90: invokevirtual 62	com/kaixin001/lbs/cell/NetworkData$CellInfo:setMobileCountryCode	(Ljava/lang/String;)V
      //   93: aload 6
      //   95: aload_1
      //   96: invokevirtual 43	android/telephony/TelephonyManager:getNetworkOperator	()Ljava/lang/String;
      //   99: iconst_3
      //   100: invokevirtual 65	java/lang/String:substring	(I)Ljava/lang/String;
      //   103: invokevirtual 68	com/kaixin001/lbs/cell/NetworkData$CellInfo:setMobileNetworkCode	(Ljava/lang/String;)V
      //   106: aload 6
      //   108: aload_3
      //   109: invokevirtual 71	android/telephony/cdma/CdmaCellLocation:getSystemId	()I
      //   112: invokestatic 74	java/lang/String:valueOf	(I)Ljava/lang/String;
      //   115: invokevirtual 68	com/kaixin001/lbs/cell/NetworkData$CellInfo:setMobileNetworkCode	(Ljava/lang/String;)V
      //   118: aload 6
      //   120: astore 4
      //   122: aload 4
      //   124: areturn
      //   125: astore_2
      //   126: aload_2
      //   127: invokevirtual 77	java/lang/Exception:printStackTrace	()V
      //   130: aconst_null
      //   131: areturn
      //   132: astore_2
      //   133: goto -7 -> 126
      //
      // Exception table:
      //   from	to	target	type
      //   0	8	125	java/lang/Exception
      //   15	21	125	java/lang/Exception
      //   29	38	125	java/lang/Exception
      //   38	106	132	java/lang/Exception
      //   106	118	132	java/lang/Exception
    }
  }

  public static class GsmCellLocationUtil
    implements CellLocationUtil
  {
    // ERROR //
    public NetworkData.CellInfo getCellInfo(TelephonyManager paramTelephonyManager)
    {
      // Byte code:
      //   0: aload_1
      //   1: invokevirtual 20	android/telephony/TelephonyManager:getCellLocation	()Landroid/telephony/CellLocation;
      //   4: checkcast 22	android/telephony/gsm/GsmCellLocation
      //   7: astore_3
      //   8: aconst_null
      //   9: astore 4
      //   11: aload_3
      //   12: ifnull +98 -> 110
      //   15: aload_3
      //   16: invokevirtual 26	android/telephony/gsm/GsmCellLocation:getCid	()I
      //   19: istore 5
      //   21: aconst_null
      //   22: astore 4
      //   24: iload 5
      //   26: ifle +84 -> 110
      //   29: new 28	com/kaixin001/lbs/cell/NetworkData$CellInfo
      //   32: dup
      //   33: invokespecial 29	com/kaixin001/lbs/cell/NetworkData$CellInfo:<init>	()V
      //   36: astore 6
      //   38: aload 6
      //   40: aload_3
      //   41: invokevirtual 26	android/telephony/gsm/GsmCellLocation:getCid	()I
      //   44: invokevirtual 33	com/kaixin001/lbs/cell/NetworkData$CellInfo:setCellId	(I)V
      //   47: aload 6
      //   49: aload_3
      //   50: invokevirtual 36	android/telephony/gsm/GsmCellLocation:getLac	()I
      //   53: invokevirtual 39	com/kaixin001/lbs/cell/NetworkData$CellInfo:setLocationAreaCode	(I)V
      //   56: aload_1
      //   57: invokevirtual 43	android/telephony/TelephonyManager:getNetworkOperator	()Ljava/lang/String;
      //   60: astore 7
      //   62: aload 7
      //   64: invokestatic 49	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   67: ifne +57 -> 124
      //   70: aload 7
      //   72: invokevirtual 54	java/lang/String:length	()I
      //   75: iconst_3
      //   76: if_icmplt +48 -> 124
      //   79: aload 6
      //   81: aload_1
      //   82: invokevirtual 43	android/telephony/TelephonyManager:getNetworkOperator	()Ljava/lang/String;
      //   85: iconst_0
      //   86: iconst_3
      //   87: invokevirtual 58	java/lang/String:substring	(II)Ljava/lang/String;
      //   90: invokevirtual 62	com/kaixin001/lbs/cell/NetworkData$CellInfo:setMobileCountryCode	(Ljava/lang/String;)V
      //   93: aload 6
      //   95: aload_1
      //   96: invokevirtual 43	android/telephony/TelephonyManager:getNetworkOperator	()Ljava/lang/String;
      //   99: iconst_3
      //   100: invokevirtual 65	java/lang/String:substring	(I)Ljava/lang/String;
      //   103: invokevirtual 68	com/kaixin001/lbs/cell/NetworkData$CellInfo:setMobileNetworkCode	(Ljava/lang/String;)V
      //   106: aload 6
      //   108: astore 4
      //   110: aload 4
      //   112: areturn
      //   113: astore_2
      //   114: aload_2
      //   115: invokevirtual 71	java/lang/Exception:printStackTrace	()V
      //   118: aconst_null
      //   119: areturn
      //   120: astore_2
      //   121: goto -7 -> 114
      //   124: aload 6
      //   126: areturn
      //
      // Exception table:
      //   from	to	target	type
      //   0	8	113	java/lang/Exception
      //   15	21	113	java/lang/Exception
      //   29	38	113	java/lang/Exception
      //   38	106	120	java/lang/Exception
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.lbs.cell.CellLocationUtil
 * JD-Core Version:    0.6.0
 */