package com.tencent.mm.sdk.platformtools;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

class PhoneUtil20Impl
{
  private static int aJ = 10000;
  private static int aK = 10000;
  private TelephonyManager aL;
  private PhoneStateListener aM = new PhoneUtil20Impl.1(this);
  private int aO;

  // ERROR //
  public java.util.List<PhoneUtil.CellInfo> getCellInfoList(Context paramContext)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 46
    //   3: invokevirtual 52	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   6: checkcast 54	android/telephony/TelephonyManager
    //   9: astore_2
    //   10: new 56	java/util/LinkedList
    //   13: dup
    //   14: invokespecial 57	java/util/LinkedList:<init>	()V
    //   17: astore_3
    //   18: ldc 59
    //   20: astore 4
    //   22: ldc 61
    //   24: astore 5
    //   26: aload_2
    //   27: invokevirtual 65	android/telephony/TelephonyManager:getNetworkOperator	()Ljava/lang/String;
    //   30: astore 7
    //   32: aload 7
    //   34: ifnull +179 -> 213
    //   37: aload 7
    //   39: ldc 61
    //   41: invokevirtual 71	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   44: ifne +169 -> 213
    //   47: aload 7
    //   49: iconst_0
    //   50: iconst_3
    //   51: invokevirtual 75	java/lang/String:substring	(II)Ljava/lang/String;
    //   54: astore 4
    //   56: aload 7
    //   58: iconst_3
    //   59: iconst_5
    //   60: invokevirtual 75	java/lang/String:substring	(II)Ljava/lang/String;
    //   63: astore 38
    //   65: aload 38
    //   67: astore 9
    //   69: aload_2
    //   70: invokevirtual 79	android/telephony/TelephonyManager:getPhoneType	()I
    //   73: iconst_2
    //   74: if_icmpne +492 -> 566
    //   77: aload_2
    //   78: invokevirtual 83	android/telephony/TelephonyManager:getCellLocation	()Landroid/telephony/CellLocation;
    //   81: checkcast 85	android/telephony/cdma/CdmaCellLocation
    //   84: astore 33
    //   86: aload 33
    //   88: ifnull +123 -> 211
    //   91: getstatic 18	com/tencent/mm/sdk/platformtools/PhoneUtil20Impl:aK	I
    //   94: getstatic 16	com/tencent/mm/sdk/platformtools/PhoneUtil20Impl:aJ	I
    //   97: if_icmpne +175 -> 272
    //   100: ldc 61
    //   102: astore 34
    //   104: aload 33
    //   106: invokevirtual 88	android/telephony/cdma/CdmaCellLocation:getBaseStationId	()I
    //   109: iconst_m1
    //   110: if_icmpeq +101 -> 211
    //   113: aload 33
    //   115: invokevirtual 91	android/telephony/cdma/CdmaCellLocation:getNetworkId	()I
    //   118: iconst_m1
    //   119: if_icmpeq +92 -> 211
    //   122: aload 33
    //   124: invokevirtual 94	android/telephony/cdma/CdmaCellLocation:getSystemId	()I
    //   127: iconst_m1
    //   128: if_icmpeq +83 -> 211
    //   131: aload_3
    //   132: new 96	com/tencent/mm/sdk/platformtools/PhoneUtil$CellInfo
    //   135: dup
    //   136: aload 4
    //   138: aload 9
    //   140: ldc 61
    //   142: ldc 61
    //   144: aload 34
    //   146: ldc 98
    //   148: new 100	java/lang/StringBuilder
    //   151: dup
    //   152: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   155: aload 33
    //   157: invokevirtual 88	android/telephony/cdma/CdmaCellLocation:getBaseStationId	()I
    //   160: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   163: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   166: new 100	java/lang/StringBuilder
    //   169: dup
    //   170: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   173: aload 33
    //   175: invokevirtual 91	android/telephony/cdma/CdmaCellLocation:getNetworkId	()I
    //   178: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   181: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   184: new 100	java/lang/StringBuilder
    //   187: dup
    //   188: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   191: aload 33
    //   193: invokevirtual 94	android/telephony/cdma/CdmaCellLocation:getSystemId	()I
    //   196: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   199: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   202: invokespecial 111	com/tencent/mm/sdk/platformtools/PhoneUtil$CellInfo:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   205: invokeinterface 116 2 0
    //   210: pop
    //   211: aload_3
    //   212: areturn
    //   213: aload_2
    //   214: invokevirtual 119	android/telephony/TelephonyManager:getSimOperator	()Ljava/lang/String;
    //   217: astore 8
    //   219: aload 8
    //   221: ifnull +35 -> 256
    //   224: aload 8
    //   226: ldc 61
    //   228: invokevirtual 71	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   231: ifne +25 -> 256
    //   234: aload 8
    //   236: iconst_0
    //   237: iconst_3
    //   238: invokevirtual 75	java/lang/String:substring	(II)Ljava/lang/String;
    //   241: astore 4
    //   243: aload 8
    //   245: iconst_3
    //   246: iconst_5
    //   247: invokevirtual 75	java/lang/String:substring	(II)Ljava/lang/String;
    //   250: astore 37
    //   252: aload 37
    //   254: astore 5
    //   256: aload 5
    //   258: astore 9
    //   260: goto -191 -> 69
    //   263: astore 6
    //   265: aload 6
    //   267: invokevirtual 122	java/lang/Exception:printStackTrace	()V
    //   270: aload_3
    //   271: areturn
    //   272: new 100	java/lang/StringBuilder
    //   275: dup
    //   276: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   279: getstatic 18	com/tencent/mm/sdk/platformtools/PhoneUtil20Impl:aK	I
    //   282: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   285: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   288: astore 36
    //   290: aload 36
    //   292: astore 34
    //   294: goto -190 -> 104
    //   297: astore 22
    //   299: aload_2
    //   300: invokevirtual 83	android/telephony/TelephonyManager:getCellLocation	()Landroid/telephony/CellLocation;
    //   303: checkcast 124	android/telephony/gsm/GsmCellLocation
    //   306: astore 29
    //   308: aload 29
    //   310: ifnull +74 -> 384
    //   313: aload 29
    //   315: invokevirtual 127	android/telephony/gsm/GsmCellLocation:getCid	()I
    //   318: istore 30
    //   320: aload 29
    //   322: invokevirtual 130	android/telephony/gsm/GsmCellLocation:getLac	()I
    //   325: istore 31
    //   327: iload 31
    //   329: ldc 131
    //   331: if_icmpge +53 -> 384
    //   334: iload 31
    //   336: iconst_m1
    //   337: if_icmpeq +47 -> 384
    //   340: iload 30
    //   342: iconst_m1
    //   343: if_icmpeq +41 -> 384
    //   346: aload_3
    //   347: new 96	com/tencent/mm/sdk/platformtools/PhoneUtil$CellInfo
    //   350: dup
    //   351: aload 4
    //   353: aload 9
    //   355: iload 31
    //   357: invokestatic 135	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   360: iload 30
    //   362: invokestatic 135	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   365: ldc 61
    //   367: ldc 137
    //   369: ldc 61
    //   371: ldc 61
    //   373: ldc 61
    //   375: invokespecial 111	com/tencent/mm/sdk/platformtools/PhoneUtil$CellInfo:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   378: invokeinterface 116 2 0
    //   383: pop
    //   384: aload_2
    //   385: invokevirtual 141	android/telephony/TelephonyManager:getNeighboringCellInfo	()Ljava/util/List;
    //   388: astore 24
    //   390: aload 24
    //   392: ifnull -181 -> 211
    //   395: aload 24
    //   397: invokeinterface 144 1 0
    //   402: ifle -191 -> 211
    //   405: aload 24
    //   407: invokeinterface 148 1 0
    //   412: astore 25
    //   414: aload 25
    //   416: invokeinterface 154 1 0
    //   421: ifeq -210 -> 211
    //   424: aload 25
    //   426: invokeinterface 158 1 0
    //   431: checkcast 160	android/telephony/NeighboringCellInfo
    //   434: astore 26
    //   436: aload 26
    //   438: invokevirtual 161	android/telephony/NeighboringCellInfo:getCid	()I
    //   441: iconst_m1
    //   442: if_icmpeq -28 -> 414
    //   445: aload 26
    //   447: invokevirtual 162	android/telephony/NeighboringCellInfo:getLac	()I
    //   450: ldc 131
    //   452: if_icmpgt -38 -> 414
    //   455: aload 26
    //   457: invokevirtual 162	android/telephony/NeighboringCellInfo:getLac	()I
    //   460: iconst_m1
    //   461: if_icmpeq -47 -> 414
    //   464: new 100	java/lang/StringBuilder
    //   467: dup
    //   468: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   471: bipush 143
    //   473: iconst_2
    //   474: aload 26
    //   476: invokevirtual 165	android/telephony/NeighboringCellInfo:getRssi	()I
    //   479: imul
    //   480: iadd
    //   481: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   484: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   487: astore 27
    //   489: aload_3
    //   490: new 96	com/tencent/mm/sdk/platformtools/PhoneUtil$CellInfo
    //   493: dup
    //   494: aload 4
    //   496: aload 9
    //   498: new 100	java/lang/StringBuilder
    //   501: dup
    //   502: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   505: aload 26
    //   507: invokevirtual 162	android/telephony/NeighboringCellInfo:getLac	()I
    //   510: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   513: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   516: new 100	java/lang/StringBuilder
    //   519: dup
    //   520: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   523: aload 26
    //   525: invokevirtual 161	android/telephony/NeighboringCellInfo:getCid	()I
    //   528: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   531: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   534: aload 27
    //   536: ldc 137
    //   538: ldc 61
    //   540: ldc 61
    //   542: ldc 61
    //   544: invokespecial 111	com/tencent/mm/sdk/platformtools/PhoneUtil$CellInfo:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   547: invokeinterface 116 2 0
    //   552: pop
    //   553: goto -139 -> 414
    //   556: astore 23
    //   558: aload 23
    //   560: invokevirtual 122	java/lang/Exception:printStackTrace	()V
    //   563: goto -179 -> 384
    //   566: aload_2
    //   567: invokevirtual 83	android/telephony/TelephonyManager:getCellLocation	()Landroid/telephony/CellLocation;
    //   570: checkcast 124	android/telephony/gsm/GsmCellLocation
    //   573: astore 16
    //   575: aload 16
    //   577: ifnull +87 -> 664
    //   580: aload 16
    //   582: invokevirtual 127	android/telephony/gsm/GsmCellLocation:getCid	()I
    //   585: istore 17
    //   587: aload 16
    //   589: invokevirtual 130	android/telephony/gsm/GsmCellLocation:getLac	()I
    //   592: istore 18
    //   594: iload 18
    //   596: ldc 131
    //   598: if_icmpge +66 -> 664
    //   601: iload 18
    //   603: iconst_m1
    //   604: if_icmpeq +60 -> 664
    //   607: iload 17
    //   609: iconst_m1
    //   610: if_icmpeq +54 -> 664
    //   613: getstatic 18	com/tencent/mm/sdk/platformtools/PhoneUtil20Impl:aK	I
    //   616: getstatic 16	com/tencent/mm/sdk/platformtools/PhoneUtil20Impl:aJ	I
    //   619: if_icmpne +256 -> 875
    //   622: ldc 61
    //   624: astore 19
    //   626: aload_3
    //   627: new 96	com/tencent/mm/sdk/platformtools/PhoneUtil$CellInfo
    //   630: dup
    //   631: aload 4
    //   633: aload 9
    //   635: iload 18
    //   637: invokestatic 135	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   640: iload 17
    //   642: invokestatic 135	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   645: aload 19
    //   647: ldc 137
    //   649: ldc 61
    //   651: ldc 61
    //   653: ldc 61
    //   655: invokespecial 111	com/tencent/mm/sdk/platformtools/PhoneUtil$CellInfo:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   658: invokeinterface 116 2 0
    //   663: pop
    //   664: aload_2
    //   665: invokevirtual 141	android/telephony/TelephonyManager:getNeighboringCellInfo	()Ljava/util/List;
    //   668: astore 11
    //   670: aload 11
    //   672: ifnull -461 -> 211
    //   675: aload 11
    //   677: invokeinterface 144 1 0
    //   682: ifle -471 -> 211
    //   685: aload 11
    //   687: invokeinterface 148 1 0
    //   692: astore 12
    //   694: aload 12
    //   696: invokeinterface 154 1 0
    //   701: ifeq -490 -> 211
    //   704: aload 12
    //   706: invokeinterface 158 1 0
    //   711: checkcast 160	android/telephony/NeighboringCellInfo
    //   714: astore 13
    //   716: aload 13
    //   718: invokevirtual 161	android/telephony/NeighboringCellInfo:getCid	()I
    //   721: iconst_m1
    //   722: if_icmpeq -28 -> 694
    //   725: aload 13
    //   727: invokevirtual 162	android/telephony/NeighboringCellInfo:getLac	()I
    //   730: ldc 131
    //   732: if_icmpgt -38 -> 694
    //   735: new 100	java/lang/StringBuilder
    //   738: dup
    //   739: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   742: bipush 143
    //   744: iconst_2
    //   745: aload 13
    //   747: invokevirtual 165	android/telephony/NeighboringCellInfo:getRssi	()I
    //   750: imul
    //   751: iadd
    //   752: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   755: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   758: astore 14
    //   760: ldc 167
    //   762: new 100	java/lang/StringBuilder
    //   765: dup
    //   766: ldc 169
    //   768: invokespecial 172	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   771: aload 13
    //   773: invokevirtual 162	android/telephony/NeighboringCellInfo:getLac	()I
    //   776: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   779: ldc 174
    //   781: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   784: aload 13
    //   786: invokevirtual 161	android/telephony/NeighboringCellInfo:getCid	()I
    //   789: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   792: ldc 179
    //   794: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   797: aload 14
    //   799: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   802: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   805: invokestatic 185	com/tencent/mm/sdk/platformtools/Log:v	(Ljava/lang/String;Ljava/lang/String;)V
    //   808: aload_3
    //   809: new 96	com/tencent/mm/sdk/platformtools/PhoneUtil$CellInfo
    //   812: dup
    //   813: aload 4
    //   815: aload 9
    //   817: new 100	java/lang/StringBuilder
    //   820: dup
    //   821: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   824: aload 13
    //   826: invokevirtual 162	android/telephony/NeighboringCellInfo:getLac	()I
    //   829: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   832: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   835: new 100	java/lang/StringBuilder
    //   838: dup
    //   839: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   842: aload 13
    //   844: invokevirtual 161	android/telephony/NeighboringCellInfo:getCid	()I
    //   847: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   850: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   853: aload 14
    //   855: ldc 137
    //   857: ldc 61
    //   859: ldc 61
    //   861: ldc 61
    //   863: invokespecial 111	com/tencent/mm/sdk/platformtools/PhoneUtil$CellInfo:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   866: invokeinterface 116 2 0
    //   871: pop
    //   872: goto -178 -> 694
    //   875: new 100	java/lang/StringBuilder
    //   878: dup
    //   879: invokespecial 101	java/lang/StringBuilder:<init>	()V
    //   882: getstatic 18	com/tencent/mm/sdk/platformtools/PhoneUtil20Impl:aK	I
    //   885: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   888: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   891: astore 21
    //   893: aload 21
    //   895: astore 19
    //   897: goto -271 -> 626
    //   900: astore 10
    //   902: aload 10
    //   904: invokevirtual 122	java/lang/Exception:printStackTrace	()V
    //   907: goto -243 -> 664
    //
    // Exception table:
    //   from	to	target	type
    //   26	32	263	java/lang/Exception
    //   37	65	263	java/lang/Exception
    //   213	219	263	java/lang/Exception
    //   224	252	263	java/lang/Exception
    //   77	86	297	java/lang/Exception
    //   91	100	297	java/lang/Exception
    //   104	211	297	java/lang/Exception
    //   272	290	297	java/lang/Exception
    //   299	308	556	java/lang/Exception
    //   313	327	556	java/lang/Exception
    //   346	384	556	java/lang/Exception
    //   566	575	900	java/lang/Exception
    //   580	594	900	java/lang/Exception
    //   613	622	900	java/lang/Exception
    //   626	664	900	java/lang/Exception
    //   875	893	900	java/lang/Exception
  }

  public void getSignalStrength(Context paramContext)
  {
    this.aL = ((TelephonyManager)paramContext.getSystemService("phone"));
    this.aL.listen(this.aM, 256);
    this.aO = this.aL.getPhoneType();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.PhoneUtil20Impl
 * JD-Core Version:    0.6.0
 */