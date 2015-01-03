package com.autonavi.aps.api;

import android.content.Context;
import android.content.IntentFilter;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class APSYUNPINGTAI
  implements IAPS
{
  private static long A;
  private static boolean B;
  private static boolean C;
  private static boolean D;
  private static String a = null;
  private static String b = null;
  private static String c = null;
  private static String d = null;
  private static APSYUNPINGTAI e = null;
  private static Context f = null;
  private static TelephoneBean g = null;
  private static int h = 0;
  private static ConnectivityManager i = null;
  private static WifiManager j = null;
  private static TelephonyManager k = null;
  private static LocationManager l = null;
  private static LocationListener m = null;
  private static LocationListener n = null;
  private static android.location.Location o = null;
  private static android.location.Location p = null;
  private static ArrayList q = new ArrayList();
  private static ArrayList r = new ArrayList();
  private static List s = new ArrayList();
  private static Des t = new Des("autonavi00spas$#@!666666");
  private static PhoneStateListener u = null;
  private static int v = 10;
  private static f w = null;
  private static WifiInfo x = null;
  private static String y = null;
  private static Location z = null;

  static
  {
    A = 0L;
    B = false;
    C = false;
    D = true;
  }

  private Location a(String paramString)
  {
    monitorenter;
    try
    {
      String str1 = NetManagerApache.getInstance(f).doPostXmlAsString("http://aps.amap.com/APS/r", paramString);
      Location localLocation;
      if ((str1 != null) && (str1.length() > 0))
      {
        String str2 = t.decrypt(new ParserResponse().ParserSapsXml(str1), "GBK");
        Utils.writeLogCat("response:" + str2);
        if ((str2 != null) && (str2.length() > 0))
        {
          localLocation = new ParserResponse().ParserApsXml(str2);
          if (localLocation != null)
          {
            double d1 = localLocation.getCenx();
            if (d1 <= 0.0D);
          }
        }
      }
      while (true)
      {
        return localLocation;
        localLocation = null;
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private static void b(CellLocation paramCellLocation, List paramList)
  {
    monitorenter;
    if (paramCellLocation != null);
    while (true)
    {
      String str;
      try
      {
        str = k.getNetworkOperator();
        Utils.writeLogCat("network operator is " + str);
        if ((paramCellLocation instanceof GsmCellLocation))
        {
          Utils.writeLogCat("celllocation is gsmcelllocation");
          h = 1;
          q.clear();
          if ((str == null) || (str.length() <= 4))
            continue;
          GsmCellBean localGsmCellBean1 = new GsmCellBean();
          localGsmCellBean1.setLac(((GsmCellLocation)paramCellLocation).getLac());
          localGsmCellBean1.setCellid(((GsmCellLocation)paramCellLocation).getCid());
          localGsmCellBean1.setSignal(v);
          localGsmCellBean1.setMcc(str.substring(0, 3));
          localGsmCellBean1.setMnc(str.substring(3, 5));
          q.add(localGsmCellBean1);
          if (paramList == null)
            continue;
          int i1 = paramList.size();
          int i2 = 0;
          if (i1 <= 0)
            continue;
          int i3 = paramList.size();
          if (i2 >= i3)
            return;
          NeighboringCellInfo localNeighboringCellInfo = (NeighboringCellInfo)paramList.get(i2);
          GsmCellBean localGsmCellBean2 = new GsmCellBean();
          localGsmCellBean2.setSignal(-133 + 2 * localNeighboringCellInfo.getRssi());
          localGsmCellBean2.setLac(localNeighboringCellInfo.getLac());
          localGsmCellBean2.setCellid(localNeighboringCellInfo.getCid());
          localGsmCellBean2.setMcc(str.substring(0, 3));
          localGsmCellBean2.setMnc(str.substring(3, 5));
          q.add(localGsmCellBean2);
          i2++;
          continue;
          Utils.writeLogCat("network operator: " + str);
          continue;
        }
      }
      finally
      {
        monitorexit;
      }
      try
      {
        Class.forName("android.telephony.cdma.CdmaCellLocation");
        Utils.writeLogCat("celllocation is cdmacelllocation");
        h = 2;
        r.clear();
        if ((str == null) || (str.length() <= 4))
          break label454;
        CdmaCellBean localCdmaCellBean = new CdmaCellBean();
        CdmaCellLocation localCdmaCellLocation = (CdmaCellLocation)paramCellLocation;
        localCdmaCellBean.setLat(localCdmaCellLocation.getBaseStationLatitude());
        localCdmaCellBean.setLon(localCdmaCellLocation.getBaseStationLongitude());
        localCdmaCellBean.setSid(localCdmaCellLocation.getSystemId());
        localCdmaCellBean.setNid(localCdmaCellLocation.getNetworkId());
        localCdmaCellBean.setBid(localCdmaCellLocation.getBaseStationId());
        localCdmaCellBean.setSignal(v);
        localCdmaCellBean.setMcc(str.substring(0, 3));
        localCdmaCellBean.setMnc(str.substring(3, 5));
        r.add(localCdmaCellBean);
      }
      catch (Exception localException)
      {
        Utils.printException(localException);
      }
      continue;
      label454: Utils.writeLogCat("network operator: " + str);
    }
  }

  public static String getCurrenttime()
  {
    return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
  }

  public static APSYUNPINGTAI getInstance(Context paramContext)
  {
    if (e == null)
    {
      e = new APSYUNPINGTAI();
      f = paramContext;
      j = (WifiManager)paramContext.getSystemService("wifi");
      APSYUNPINGTAI localAPSYUNPINGTAI = e;
      localAPSYUNPINGTAI.getClass();
      w = new f(localAPSYUNPINGTAI);
      if (j.getWifiState() == 3)
      {
        x = j.getConnectionInfo();
        new d().start();
      }
      f.registerReceiver(w, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
      i = (ConnectivityManager)f.getSystemService("connectivity");
      k = (TelephonyManager)f.getSystemService("phone");
      CellLocation.requestLocationUpdate();
      g = TelephoneBean.getInstance(k, f, null);
      u = new e();
      k.listen(u, 272);
      i();
    }
    Utils.writeLogCat("public in debug, only for test use");
    Utils.doToast(f, "public in debug, only for test use");
    return e;
  }

  // ERROR //
  private static void h()
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: new 165	java/lang/StringBuilder
    //   6: dup
    //   7: ldc_w 408
    //   10: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   13: astore_0
    //   14: getstatic 126	com/autonavi/aps/api/APSYUNPINGTAI:B	Z
    //   17: ifne +71 -> 88
    //   20: ldc_w 410
    //   23: astore_2
    //   24: aload_0
    //   25: aload_2
    //   26: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   29: ldc_w 412
    //   32: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   38: invokestatic 181	com/autonavi/aps/api/Utils:writeLogCat	(Ljava/lang/String;)V
    //   41: getstatic 126	com/autonavi/aps/api/APSYUNPINGTAI:B	Z
    //   44: ifne +51 -> 95
    //   47: new 414	com/autonavi/aps/api/a
    //   50: dup
    //   51: invokespecial 415	com/autonavi/aps/api/a:<init>	()V
    //   54: putstatic 86	com/autonavi/aps/api/APSYUNPINGTAI:n	Landroid/location/LocationListener;
    //   57: getstatic 82	com/autonavi/aps/api/APSYUNPINGTAI:l	Landroid/location/LocationManager;
    //   60: ldc_w 417
    //   63: ldc2_w 418
    //   66: fconst_0
    //   67: getstatic 86	com/autonavi/aps/api/APSYUNPINGTAI:n	Landroid/location/LocationListener;
    //   70: invokestatic 425	android/os/Looper:getMainLooper	()Landroid/os/Looper;
    //   73: invokevirtual 431	android/location/LocationManager:requestLocationUpdates	(Ljava/lang/String;JFLandroid/location/LocationListener;Landroid/os/Looper;)V
    //   76: iconst_0
    //   77: putstatic 128	com/autonavi/aps/api/APSYUNPINGTAI:C	Z
    //   80: iconst_1
    //   81: putstatic 126	com/autonavi/aps/api/APSYUNPINGTAI:B	Z
    //   84: ldc 2
    //   86: monitorexit
    //   87: return
    //   88: ldc_w 433
    //   91: astore_2
    //   92: goto -68 -> 24
    //   95: getstatic 86	com/autonavi/aps/api/APSYUNPINGTAI:n	Landroid/location/LocationListener;
    //   98: ifnull +12 -> 110
    //   101: getstatic 82	com/autonavi/aps/api/APSYUNPINGTAI:l	Landroid/location/LocationManager;
    //   104: getstatic 86	com/autonavi/aps/api/APSYUNPINGTAI:n	Landroid/location/LocationListener;
    //   107: invokevirtual 437	android/location/LocationManager:removeUpdates	(Landroid/location/LocationListener;)V
    //   110: aconst_null
    //   111: putstatic 86	com/autonavi/aps/api/APSYUNPINGTAI:n	Landroid/location/LocationListener;
    //   114: aconst_null
    //   115: putstatic 88	com/autonavi/aps/api/APSYUNPINGTAI:o	Landroid/location/Location;
    //   118: iconst_0
    //   119: putstatic 128	com/autonavi/aps/api/APSYUNPINGTAI:C	Z
    //   122: iconst_0
    //   123: putstatic 126	com/autonavi/aps/api/APSYUNPINGTAI:B	Z
    //   126: goto -42 -> 84
    //   129: astore_1
    //   130: ldc 2
    //   132: monitorexit
    //   133: aload_1
    //   134: athrow
    //   135: astore_3
    //   136: aload_3
    //   137: invokestatic 317	com/autonavi/aps/api/Utils:printException	(Ljava/lang/Exception;)V
    //   140: goto -30 -> 110
    //
    // Exception table:
    //   from	to	target	type
    //   3	20	129	finally
    //   24	84	129	finally
    //   95	110	129	finally
    //   110	126	129	finally
    //   136	140	129	finally
    //   95	110	135	java/lang/Exception
  }

  private static void i()
  {
    if (D)
    {
      Utils.writeLogCat("start system network listener");
      l = (LocationManager)f.getSystemService("location");
      m = new b();
    }
  }

  // ERROR //
  private String j()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: monitorenter
    //   4: getstatic 60	com/autonavi/aps/api/APSYUNPINGTAI:a	Ljava/lang/String;
    //   7: ifnull +10 -> 17
    //   10: aload_0
    //   11: invokevirtual 449	com/autonavi/aps/api/APSYUNPINGTAI:getProductName	()Ljava/lang/String;
    //   14: ifnonnull +9 -> 23
    //   17: ldc_w 451
    //   20: invokestatic 181	com/autonavi/aps/api/Utils:writeLogCat	(Ljava/lang/String;)V
    //   23: aload_0
    //   24: invokevirtual 454	com/autonavi/aps/api/APSYUNPINGTAI:getKey	()Ljava/lang/String;
    //   27: ifnull +10 -> 37
    //   30: aload_0
    //   31: invokevirtual 457	com/autonavi/aps/api/APSYUNPINGTAI:getPackageName	()Ljava/lang/String;
    //   34: ifnonnull +13 -> 47
    //   37: ldc_w 459
    //   40: invokestatic 181	com/autonavi/aps/api/Utils:writeLogCat	(Ljava/lang/String;)V
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_1
    //   46: areturn
    //   47: getstatic 78	com/autonavi/aps/api/APSYUNPINGTAI:j	Landroid/net/wifi/WifiManager;
    //   50: invokevirtual 356	android/net/wifi/WifiManager:getWifiState	()I
    //   53: iconst_3
    //   54: if_icmpne +14 -> 68
    //   57: new 461	com/autonavi/aps/api/c
    //   60: dup
    //   61: aload_0
    //   62: invokespecial 462	com/autonavi/aps/api/c:<init>	(Lcom/autonavi/aps/api/APSYUNPINGTAI;)V
    //   65: invokevirtual 463	com/autonavi/aps/api/c:start	()V
    //   68: getstatic 80	com/autonavi/aps/api/APSYUNPINGTAI:k	Landroid/telephony/TelephonyManager;
    //   71: astore_3
    //   72: aconst_null
    //   73: astore 4
    //   75: aload_3
    //   76: ifnull +11 -> 87
    //   79: getstatic 80	com/autonavi/aps/api/APSYUNPINGTAI:k	Landroid/telephony/TelephonyManager;
    //   82: invokevirtual 467	android/telephony/TelephonyManager:getCellLocation	()Landroid/telephony/CellLocation;
    //   85: astore 4
    //   87: getstatic 112	com/autonavi/aps/api/APSYUNPINGTAI:u	Landroid/telephony/PhoneStateListener;
    //   90: ifnull +11 -> 101
    //   93: getstatic 112	com/autonavi/aps/api/APSYUNPINGTAI:u	Landroid/telephony/PhoneStateListener;
    //   96: aload 4
    //   98: invokevirtual 473	android/telephony/PhoneStateListener:onCellLocationChanged	(Landroid/telephony/CellLocation;)V
    //   101: new 165	java/lang/StringBuilder
    //   104: dup
    //   105: invokespecial 474	java/lang/StringBuilder:<init>	()V
    //   108: astore 5
    //   110: aload 5
    //   112: ldc_w 476
    //   115: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: pop
    //   119: aload 5
    //   121: ldc_w 478
    //   124: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   127: pop
    //   128: aload 5
    //   130: ldc_w 480
    //   133: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   136: getstatic 60	com/autonavi/aps/api/APSYUNPINGTAI:a	Ljava/lang/String;
    //   139: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   142: ldc_w 482
    //   145: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   148: pop
    //   149: aload 5
    //   151: ldc_w 484
    //   154: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   157: aload_0
    //   158: invokevirtual 449	com/autonavi/aps/api/APSYUNPINGTAI:getProductName	()Ljava/lang/String;
    //   161: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   164: ldc_w 486
    //   167: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   170: pop
    //   171: aload 5
    //   173: ldc_w 488
    //   176: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   179: aload_0
    //   180: invokevirtual 454	com/autonavi/aps/api/APSYUNPINGTAI:getKey	()Ljava/lang/String;
    //   183: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   186: ldc_w 490
    //   189: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: pop
    //   193: aload 5
    //   195: ldc_w 492
    //   198: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   201: aload_0
    //   202: invokevirtual 457	com/autonavi/aps/api/APSYUNPINGTAI:getPackageName	()Ljava/lang/String;
    //   205: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   208: ldc_w 494
    //   211: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   214: pop
    //   215: aload 5
    //   217: ldc_w 496
    //   220: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   223: getstatic 72	com/autonavi/aps/api/APSYUNPINGTAI:g	Lcom/autonavi/aps/api/TelephoneBean;
    //   226: invokevirtual 499	com/autonavi/aps/api/TelephoneBean:getDeviceId	()Ljava/lang/String;
    //   229: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   232: ldc_w 501
    //   235: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   238: pop
    //   239: getstatic 76	com/autonavi/aps/api/APSYUNPINGTAI:i	Landroid/net/ConnectivityManager;
    //   242: invokevirtual 505	android/net/ConnectivityManager:getActiveNetworkInfo	()Landroid/net/NetworkInfo;
    //   245: astore 13
    //   247: aload 5
    //   249: ldc_w 507
    //   252: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   255: pop
    //   256: aload 13
    //   258: ifnull +213 -> 471
    //   261: aload 13
    //   263: invokevirtual 513	android/net/NetworkInfo:isAvailable	()Z
    //   266: ifeq +205 -> 471
    //   269: aload 13
    //   271: invokevirtual 516	android/net/NetworkInfo:isConnected	()Z
    //   274: ifeq +197 -> 471
    //   277: aload 5
    //   279: aload 13
    //   281: invokevirtual 517	android/net/NetworkInfo:toString	()Ljava/lang/String;
    //   284: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   287: pop
    //   288: getstatic 80	com/autonavi/aps/api/APSYUNPINGTAI:k	Landroid/telephony/TelephonyManager;
    //   291: ifnull +51 -> 342
    //   294: aload 5
    //   296: ldc_w 519
    //   299: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   302: getstatic 80	com/autonavi/aps/api/APSYUNPINGTAI:k	Landroid/telephony/TelephonyManager;
    //   305: invokevirtual 522	android/telephony/TelephonyManager:getNetworkCountryIso	()Ljava/lang/String;
    //   308: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   311: ldc_w 524
    //   314: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   317: getstatic 80	com/autonavi/aps/api/APSYUNPINGTAI:k	Landroid/telephony/TelephonyManager;
    //   320: invokevirtual 527	android/telephony/TelephonyManager:getNetworkOperatorName	()Ljava/lang/String;
    //   323: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   326: ldc_w 529
    //   329: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   332: getstatic 80	com/autonavi/aps/api/APSYUNPINGTAI:k	Landroid/telephony/TelephonyManager;
    //   335: invokevirtual 532	android/telephony/TelephonyManager:getLine1Number	()Ljava/lang/String;
    //   338: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   341: pop
    //   342: getstatic 78	com/autonavi/aps/api/APSYUNPINGTAI:j	Landroid/net/wifi/WifiManager;
    //   345: invokevirtual 356	android/net/wifi/WifiManager:getWifiState	()I
    //   348: iconst_3
    //   349: if_icmpne +122 -> 471
    //   352: getstatic 78	com/autonavi/aps/api/APSYUNPINGTAI:j	Landroid/net/wifi/WifiManager;
    //   355: invokevirtual 536	android/net/wifi/WifiManager:getDhcpInfo	()Landroid/net/DhcpInfo;
    //   358: astore 82
    //   360: aload 5
    //   362: new 165	java/lang/StringBuilder
    //   365: dup
    //   366: ldc_w 538
    //   369: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   372: aload 82
    //   374: getfield 543	android/net/DhcpInfo:dns1	I
    //   377: invokestatic 547	com/autonavi/aps/api/NetManagerApache:intToIpAddr	(I)Ljava/lang/String;
    //   380: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   383: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   386: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   389: new 165	java/lang/StringBuilder
    //   392: dup
    //   393: ldc_w 549
    //   396: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   399: aload 82
    //   401: getfield 552	android/net/DhcpInfo:dns2	I
    //   404: invokestatic 547	com/autonavi/aps/api/NetManagerApache:intToIpAddr	(I)Ljava/lang/String;
    //   407: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   410: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   413: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   416: new 165	java/lang/StringBuilder
    //   419: dup
    //   420: ldc_w 554
    //   423: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   426: aload 82
    //   428: getfield 557	android/net/DhcpInfo:gateway	I
    //   431: invokestatic 547	com/autonavi/aps/api/NetManagerApache:intToIpAddr	(I)Ljava/lang/String;
    //   434: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   437: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   440: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   443: new 165	java/lang/StringBuilder
    //   446: dup
    //   447: ldc_w 559
    //   450: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   453: aload 82
    //   455: getfield 562	android/net/DhcpInfo:ipAddress	I
    //   458: invokestatic 547	com/autonavi/aps/api/NetManagerApache:intToIpAddr	(I)Ljava/lang/String;
    //   461: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   464: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   467: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   470: pop
    //   471: aload 5
    //   473: ldc_w 564
    //   476: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   479: pop
    //   480: getstatic 74	com/autonavi/aps/api/APSYUNPINGTAI:h	I
    //   483: iconst_1
    //   484: if_icmpne +863 -> 1347
    //   487: aload 5
    //   489: ldc_w 566
    //   492: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   495: pop
    //   496: getstatic 97	com/autonavi/aps/api/APSYUNPINGTAI:q	Ljava/util/ArrayList;
    //   499: invokevirtual 567	java/util/ArrayList:size	()I
    //   502: ifle +186 -> 688
    //   505: getstatic 97	com/autonavi/aps/api/APSYUNPINGTAI:q	Ljava/util/ArrayList;
    //   508: iconst_0
    //   509: invokevirtual 568	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   512: checkcast 217	com/autonavi/aps/api/GsmCellBean
    //   515: astore 69
    //   517: aload 5
    //   519: ldc_w 570
    //   522: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   525: aload 69
    //   527: invokevirtual 573	com/autonavi/aps/api/GsmCellBean:getMcc	()Ljava/lang/String;
    //   530: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   533: ldc_w 575
    //   536: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   539: pop
    //   540: aload 5
    //   542: ldc_w 577
    //   545: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   548: aload 69
    //   550: invokevirtual 580	com/autonavi/aps/api/GsmCellBean:getMnc	()Ljava/lang/String;
    //   553: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   556: ldc_w 582
    //   559: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   562: pop
    //   563: aload 5
    //   565: ldc_w 584
    //   568: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   571: aload 69
    //   573: invokevirtual 585	com/autonavi/aps/api/GsmCellBean:getLac	()I
    //   576: invokevirtual 588	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   579: ldc_w 590
    //   582: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   585: pop
    //   586: aload 5
    //   588: ldc_w 592
    //   591: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   594: aload 69
    //   596: invokevirtual 595	com/autonavi/aps/api/GsmCellBean:getCellid	()I
    //   599: invokevirtual 588	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   602: ldc_w 597
    //   605: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   608: pop
    //   609: aload 5
    //   611: ldc_w 599
    //   614: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   617: aload 69
    //   619: invokevirtual 602	com/autonavi/aps/api/GsmCellBean:getSignal	()I
    //   622: invokevirtual 588	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   625: ldc_w 604
    //   628: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   631: pop
    //   632: getstatic 97	com/autonavi/aps/api/APSYUNPINGTAI:q	Ljava/util/ArrayList;
    //   635: invokevirtual 567	java/util/ArrayList:size	()I
    //   638: iconst_1
    //   639: if_icmple +49 -> 688
    //   642: new 606	java/lang/StringBuffer
    //   645: dup
    //   646: invokespecial 607	java/lang/StringBuffer:<init>	()V
    //   649: astore 75
    //   651: iconst_1
    //   652: istore 76
    //   654: iload 76
    //   656: getstatic 97	com/autonavi/aps/api/APSYUNPINGTAI:q	Ljava/util/ArrayList;
    //   659: invokevirtual 567	java/util/ArrayList:size	()I
    //   662: if_icmplt +598 -> 1260
    //   665: aload 5
    //   667: ldc_w 609
    //   670: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   673: aload 75
    //   675: invokevirtual 610	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   678: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   681: ldc_w 612
    //   684: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   687: pop
    //   688: getstatic 82	com/autonavi/aps/api/APSYUNPINGTAI:l	Landroid/location/LocationManager;
    //   691: ifnull +1015 -> 1706
    //   694: getstatic 82	com/autonavi/aps/api/APSYUNPINGTAI:l	Landroid/location/LocationManager;
    //   697: ldc_w 417
    //   700: invokevirtual 616	android/location/LocationManager:isProviderEnabled	(Ljava/lang/String;)Z
    //   703: ifeq +1003 -> 1706
    //   706: getstatic 88	com/autonavi/aps/api/APSYUNPINGTAI:o	Landroid/location/Location;
    //   709: ifnull +114 -> 823
    //   712: getstatic 88	com/autonavi/aps/api/APSYUNPINGTAI:o	Landroid/location/Location;
    //   715: invokevirtual 621	android/location/Location:getLatitude	()D
    //   718: dstore 60
    //   720: getstatic 88	com/autonavi/aps/api/APSYUNPINGTAI:o	Landroid/location/Location;
    //   723: invokevirtual 624	android/location/Location:getLongitude	()D
    //   726: dstore 62
    //   728: dload 60
    //   730: ldc2_w 625
    //   733: dcmpl
    //   734: ifle +85 -> 819
    //   737: dload 62
    //   739: ldc2_w 627
    //   742: dcmpl
    //   743: ifle +76 -> 819
    //   746: aload 5
    //   748: ldc_w 630
    //   751: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   754: pop
    //   755: aload 5
    //   757: ldc_w 632
    //   760: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   763: dload 62
    //   765: invokevirtual 635	java/lang/StringBuilder:append	(D)Ljava/lang/StringBuilder;
    //   768: ldc_w 637
    //   771: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   774: pop
    //   775: aload 5
    //   777: ldc_w 639
    //   780: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   783: dload 60
    //   785: invokevirtual 635	java/lang/StringBuilder:append	(D)Ljava/lang/StringBuilder;
    //   788: ldc_w 641
    //   791: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   794: pop
    //   795: aload 5
    //   797: ldc_w 643
    //   800: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   803: getstatic 88	com/autonavi/aps/api/APSYUNPINGTAI:o	Landroid/location/Location;
    //   806: invokevirtual 647	android/location/Location:getAccuracy	()F
    //   809: invokevirtual 650	java/lang/StringBuilder:append	(F)Ljava/lang/StringBuilder;
    //   812: ldc_w 652
    //   815: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   818: pop
    //   819: aconst_null
    //   820: putstatic 88	com/autonavi/aps/api/APSYUNPINGTAI:o	Landroid/location/Location;
    //   823: getstatic 78	com/autonavi/aps/api/APSYUNPINGTAI:j	Landroid/net/wifi/WifiManager;
    //   826: invokevirtual 356	android/net/wifi/WifiManager:getWifiState	()I
    //   829: iconst_3
    //   830: if_icmpne +190 -> 1020
    //   833: getstatic 101	com/autonavi/aps/api/APSYUNPINGTAI:s	Ljava/util/List;
    //   836: invokeinterface 252 1 0
    //   841: ifle +51 -> 892
    //   844: new 165	java/lang/StringBuilder
    //   847: dup
    //   848: invokespecial 474	java/lang/StringBuilder:<init>	()V
    //   851: astore 45
    //   853: iconst_0
    //   854: istore 46
    //   856: iload 46
    //   858: getstatic 101	com/autonavi/aps/api/APSYUNPINGTAI:s	Ljava/util/List;
    //   861: invokeinterface 252 1 0
    //   866: if_icmplt +1022 -> 1888
    //   869: aload 5
    //   871: ldc_w 654
    //   874: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   877: aload 45
    //   879: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   882: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   885: ldc_w 656
    //   888: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   891: pop
    //   892: getstatic 78	com/autonavi/aps/api/APSYUNPINGTAI:j	Landroid/net/wifi/WifiManager;
    //   895: invokevirtual 360	android/net/wifi/WifiManager:getConnectionInfo	()Landroid/net/wifi/WifiInfo;
    //   898: astore 48
    //   900: aload 48
    //   902: putstatic 118	com/autonavi/aps/api/APSYUNPINGTAI:x	Landroid/net/wifi/WifiInfo;
    //   905: aload 48
    //   907: ifnull +1040 -> 1947
    //   910: getstatic 118	com/autonavi/aps/api/APSYUNPINGTAI:x	Landroid/net/wifi/WifiInfo;
    //   913: invokevirtual 661	android/net/wifi/WifiInfo:getBSSID	()Ljava/lang/String;
    //   916: ifnull +1031 -> 1947
    //   919: getstatic 101	com/autonavi/aps/api/APSYUNPINGTAI:s	Ljava/util/List;
    //   922: invokeinterface 252 1 0
    //   927: ifgt +48 -> 975
    //   930: aload 5
    //   932: ldc_w 654
    //   935: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   938: getstatic 118	com/autonavi/aps/api/APSYUNPINGTAI:x	Landroid/net/wifi/WifiInfo;
    //   941: invokevirtual 661	android/net/wifi/WifiInfo:getBSSID	()Ljava/lang/String;
    //   944: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   947: ldc_w 663
    //   950: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   953: getstatic 118	com/autonavi/aps/api/APSYUNPINGTAI:x	Landroid/net/wifi/WifiInfo;
    //   956: invokevirtual 664	android/net/wifi/WifiInfo:getRssi	()I
    //   959: invokevirtual 588	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   962: ldc_w 666
    //   965: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   968: ldc_w 656
    //   971: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   974: pop
    //   975: aload 5
    //   977: ldc_w 668
    //   980: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   983: getstatic 118	com/autonavi/aps/api/APSYUNPINGTAI:x	Landroid/net/wifi/WifiInfo;
    //   986: invokevirtual 661	android/net/wifi/WifiInfo:getBSSID	()Ljava/lang/String;
    //   989: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   992: ldc_w 663
    //   995: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   998: getstatic 118	com/autonavi/aps/api/APSYUNPINGTAI:x	Landroid/net/wifi/WifiInfo;
    //   1001: invokevirtual 664	android/net/wifi/WifiInfo:getRssi	()I
    //   1004: invokevirtual 588	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1007: ldc_w 666
    //   1010: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1013: ldc_w 670
    //   1016: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1019: pop
    //   1020: aload 5
    //   1022: ldc_w 672
    //   1025: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1028: invokestatic 674	com/autonavi/aps/api/APSYUNPINGTAI:getCurrenttime	()Ljava/lang/String;
    //   1031: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1034: ldc_w 676
    //   1037: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1040: pop
    //   1041: aload 5
    //   1043: ldc_w 678
    //   1046: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1049: pop
    //   1050: new 680	com/autonavi/aps/api/ApsRequest
    //   1053: dup
    //   1054: invokespecial 681	com/autonavi/aps/api/ApsRequest:<init>	()V
    //   1057: aload 5
    //   1059: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1062: invokevirtual 684	com/autonavi/aps/api/ApsRequest:convertApsRequestXml	(Ljava/lang/String;)Ljava/lang/String;
    //   1065: astore 34
    //   1067: aload 5
    //   1069: iconst_0
    //   1070: aload 5
    //   1072: invokevirtual 685	java/lang/StringBuilder:length	()I
    //   1075: invokevirtual 689	java/lang/StringBuilder:delete	(II)Ljava/lang/StringBuilder;
    //   1078: pop
    //   1079: aload 5
    //   1081: aload 34
    //   1083: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1086: pop
    //   1087: new 165	java/lang/StringBuilder
    //   1090: dup
    //   1091: ldc_w 691
    //   1094: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1097: aload 5
    //   1099: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1102: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1105: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1108: invokestatic 181	com/autonavi/aps/api/Utils:writeLogCat	(Ljava/lang/String;)V
    //   1111: ldc_w 693
    //   1114: invokestatic 181	com/autonavi/aps/api/Utils:writeLogCat	(Ljava/lang/String;)V
    //   1117: invokestatic 698	com/autonavi/aps/api/Storage:getInstance	()Lcom/autonavi/aps/api/Storage;
    //   1120: aload 5
    //   1122: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1125: invokevirtual 701	com/autonavi/aps/api/Storage:writeLog	(Ljava/lang/String;)V
    //   1128: new 165	java/lang/StringBuilder
    //   1131: dup
    //   1132: invokespecial 474	java/lang/StringBuilder:<init>	()V
    //   1135: astore 37
    //   1137: aload 37
    //   1139: ldc_w 476
    //   1142: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1145: pop
    //   1146: aload 37
    //   1148: ldc_w 703
    //   1151: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1154: pop
    //   1155: aload 37
    //   1157: ldc_w 484
    //   1160: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1163: aload_0
    //   1164: invokevirtual 449	com/autonavi/aps/api/APSYUNPINGTAI:getProductName	()Ljava/lang/String;
    //   1167: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1170: ldc_w 486
    //   1173: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1176: pop
    //   1177: aload 37
    //   1179: ldc_w 705
    //   1182: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1185: getstatic 110	com/autonavi/aps/api/APSYUNPINGTAI:t	Lcom/autonavi/aps/api/Des;
    //   1188: aload 5
    //   1190: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1193: invokevirtual 708	com/autonavi/aps/api/Des:encrypt	(Ljava/lang/String;)Ljava/lang/String;
    //   1196: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1199: ldc_w 710
    //   1202: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1205: pop
    //   1206: aload 37
    //   1208: ldc_w 712
    //   1211: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1214: pop
    //   1215: aload 5
    //   1217: iconst_0
    //   1218: aload 5
    //   1220: invokevirtual 685	java/lang/StringBuilder:length	()I
    //   1223: invokevirtual 689	java/lang/StringBuilder:delete	(II)Ljava/lang/StringBuilder;
    //   1226: pop
    //   1227: new 165	java/lang/StringBuilder
    //   1230: dup
    //   1231: ldc_w 714
    //   1234: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1237: aload 37
    //   1239: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1242: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1245: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1248: invokestatic 181	com/autonavi/aps/api/Utils:writeLogCat	(Ljava/lang/String;)V
    //   1251: aload 37
    //   1253: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1256: astore_1
    //   1257: goto -1214 -> 43
    //   1260: iload 76
    //   1262: iconst_1
    //   1263: if_icmple +12 -> 1275
    //   1266: aload 75
    //   1268: ldc_w 666
    //   1271: invokevirtual 717	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1274: pop
    //   1275: getstatic 97	com/autonavi/aps/api/APSYUNPINGTAI:q	Ljava/util/ArrayList;
    //   1278: iload 76
    //   1280: invokevirtual 568	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   1283: checkcast 217	com/autonavi/aps/api/GsmCellBean
    //   1286: astore 78
    //   1288: aload 75
    //   1290: aload 78
    //   1292: invokevirtual 580	com/autonavi/aps/api/GsmCellBean:getMnc	()Ljava/lang/String;
    //   1295: invokevirtual 717	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1298: ldc_w 663
    //   1301: invokevirtual 717	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1304: aload 78
    //   1306: invokevirtual 585	com/autonavi/aps/api/GsmCellBean:getLac	()I
    //   1309: invokevirtual 720	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
    //   1312: ldc_w 663
    //   1315: invokevirtual 717	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1318: aload 78
    //   1320: invokevirtual 595	com/autonavi/aps/api/GsmCellBean:getCellid	()I
    //   1323: invokevirtual 720	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
    //   1326: ldc_w 663
    //   1329: invokevirtual 717	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1332: aload 78
    //   1334: invokevirtual 602	com/autonavi/aps/api/GsmCellBean:getSignal	()I
    //   1337: invokevirtual 720	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
    //   1340: pop
    //   1341: iinc 76 1
    //   1344: goto -690 -> 654
    //   1347: getstatic 74	com/autonavi/aps/api/APSYUNPINGTAI:h	I
    //   1350: iconst_2
    //   1351: if_icmpne -663 -> 688
    //   1354: aload 5
    //   1356: ldc_w 722
    //   1359: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1362: pop
    //   1363: getstatic 99	com/autonavi/aps/api/APSYUNPINGTAI:r	Ljava/util/ArrayList;
    //   1366: invokevirtual 567	java/util/ArrayList:size	()I
    //   1369: ifle -681 -> 688
    //   1372: getstatic 99	com/autonavi/aps/api/APSYUNPINGTAI:r	Ljava/util/ArrayList;
    //   1375: iconst_0
    //   1376: invokevirtual 568	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   1379: checkcast 277	com/autonavi/aps/api/CdmaCellBean
    //   1382: astore 17
    //   1384: aload 5
    //   1386: ldc_w 570
    //   1389: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1392: aload 17
    //   1394: invokevirtual 723	com/autonavi/aps/api/CdmaCellBean:getMcc	()Ljava/lang/String;
    //   1397: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1400: ldc_w 575
    //   1403: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1406: pop
    //   1407: aload 5
    //   1409: ldc_w 725
    //   1412: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1415: aload 17
    //   1417: invokevirtual 728	com/autonavi/aps/api/CdmaCellBean:getSid	()I
    //   1420: invokevirtual 588	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1423: ldc_w 730
    //   1426: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1429: pop
    //   1430: aload 5
    //   1432: ldc_w 732
    //   1435: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1438: aload 17
    //   1440: invokevirtual 735	com/autonavi/aps/api/CdmaCellBean:getNid	()I
    //   1443: invokevirtual 588	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1446: ldc_w 737
    //   1449: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1452: pop
    //   1453: aload 5
    //   1455: ldc_w 739
    //   1458: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1461: aload 17
    //   1463: invokevirtual 742	com/autonavi/aps/api/CdmaCellBean:getBid	()I
    //   1466: invokevirtual 588	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1469: ldc_w 744
    //   1472: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1475: pop
    //   1476: aload 5
    //   1478: ldc_w 746
    //   1481: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1484: aload 17
    //   1486: invokevirtual 749	com/autonavi/aps/api/CdmaCellBean:getLon	()I
    //   1489: invokevirtual 588	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1492: ldc_w 751
    //   1495: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1498: pop
    //   1499: aload 5
    //   1501: ldc_w 753
    //   1504: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1507: aload 17
    //   1509: invokevirtual 756	com/autonavi/aps/api/CdmaCellBean:getLat	()I
    //   1512: invokevirtual 588	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1515: ldc_w 758
    //   1518: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1521: pop
    //   1522: aload 5
    //   1524: ldc_w 599
    //   1527: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1530: aload 17
    //   1532: invokevirtual 759	com/autonavi/aps/api/CdmaCellBean:getSignal	()I
    //   1535: invokevirtual 588	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1538: ldc_w 604
    //   1541: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1544: pop
    //   1545: getstatic 99	com/autonavi/aps/api/APSYUNPINGTAI:r	Ljava/util/ArrayList;
    //   1548: invokevirtual 567	java/util/ArrayList:size	()I
    //   1551: iconst_1
    //   1552: if_icmple -864 -> 688
    //   1555: new 606	java/lang/StringBuffer
    //   1558: dup
    //   1559: invokespecial 607	java/lang/StringBuffer:<init>	()V
    //   1562: astore 25
    //   1564: iconst_1
    //   1565: istore 26
    //   1567: iload 26
    //   1569: getstatic 99	com/autonavi/aps/api/APSYUNPINGTAI:r	Ljava/util/ArrayList;
    //   1572: invokevirtual 567	java/util/ArrayList:size	()I
    //   1575: if_icmplt +44 -> 1619
    //   1578: aload 5
    //   1580: new 165	java/lang/StringBuilder
    //   1583: dup
    //   1584: ldc_w 609
    //   1587: invokespecial 168	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1590: aload 25
    //   1592: invokevirtual 610	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   1595: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1598: ldc_w 612
    //   1601: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1604: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1607: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1610: pop
    //   1611: goto -923 -> 688
    //   1614: astore_2
    //   1615: aload_0
    //   1616: monitorexit
    //   1617: aload_2
    //   1618: athrow
    //   1619: iload 26
    //   1621: iconst_1
    //   1622: if_icmple +12 -> 1634
    //   1625: aload 25
    //   1627: ldc_w 666
    //   1630: invokevirtual 717	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1633: pop
    //   1634: getstatic 99	com/autonavi/aps/api/APSYUNPINGTAI:r	Ljava/util/ArrayList;
    //   1637: iload 26
    //   1639: invokevirtual 568	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   1642: checkcast 277	com/autonavi/aps/api/CdmaCellBean
    //   1645: astore 27
    //   1647: aload 25
    //   1649: aload 27
    //   1651: invokevirtual 728	com/autonavi/aps/api/CdmaCellBean:getSid	()I
    //   1654: invokevirtual 720	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
    //   1657: ldc_w 663
    //   1660: invokevirtual 717	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1663: aload 27
    //   1665: invokevirtual 735	com/autonavi/aps/api/CdmaCellBean:getNid	()I
    //   1668: invokevirtual 720	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
    //   1671: ldc_w 663
    //   1674: invokevirtual 717	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1677: aload 27
    //   1679: invokevirtual 742	com/autonavi/aps/api/CdmaCellBean:getBid	()I
    //   1682: invokevirtual 720	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
    //   1685: ldc_w 663
    //   1688: invokevirtual 717	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1691: aload 27
    //   1693: invokevirtual 759	com/autonavi/aps/api/CdmaCellBean:getSignal	()I
    //   1696: invokevirtual 720	java/lang/StringBuffer:append	(I)Ljava/lang/StringBuffer;
    //   1699: pop
    //   1700: iinc 26 1
    //   1703: goto -136 -> 1567
    //   1706: getstatic 82	com/autonavi/aps/api/APSYUNPINGTAI:l	Landroid/location/LocationManager;
    //   1709: ifnull -886 -> 823
    //   1712: getstatic 82	com/autonavi/aps/api/APSYUNPINGTAI:l	Landroid/location/LocationManager;
    //   1715: ldc_w 761
    //   1718: invokevirtual 616	android/location/LocationManager:isProviderEnabled	(Ljava/lang/String;)Z
    //   1721: ifeq -898 -> 823
    //   1724: getstatic 84	com/autonavi/aps/api/APSYUNPINGTAI:m	Landroid/location/LocationListener;
    //   1727: ifnull -904 -> 823
    //   1730: ldc_w 763
    //   1733: invokestatic 181	com/autonavi/aps/api/Utils:writeLogCat	(Ljava/lang/String;)V
    //   1736: getstatic 82	com/autonavi/aps/api/APSYUNPINGTAI:l	Landroid/location/LocationManager;
    //   1739: ldc_w 761
    //   1742: ldc2_w 764
    //   1745: fconst_0
    //   1746: getstatic 84	com/autonavi/aps/api/APSYUNPINGTAI:m	Landroid/location/LocationListener;
    //   1749: invokestatic 425	android/os/Looper:getMainLooper	()Landroid/os/Looper;
    //   1752: invokevirtual 431	android/location/LocationManager:requestLocationUpdates	(Ljava/lang/String;JFLandroid/location/LocationListener;Landroid/os/Looper;)V
    //   1755: ldc_w 767
    //   1758: invokestatic 181	com/autonavi/aps/api/Utils:writeLogCat	(Ljava/lang/String;)V
    //   1761: ldc2_w 768
    //   1764: invokestatic 774	java/lang/Thread:sleep	(J)V
    //   1767: getstatic 90	com/autonavi/aps/api/APSYUNPINGTAI:p	Landroid/location/Location;
    //   1770: ifnull +86 -> 1856
    //   1773: getstatic 90	com/autonavi/aps/api/APSYUNPINGTAI:p	Landroid/location/Location;
    //   1776: invokevirtual 621	android/location/Location:getLatitude	()D
    //   1779: dstore 53
    //   1781: getstatic 90	com/autonavi/aps/api/APSYUNPINGTAI:p	Landroid/location/Location;
    //   1784: invokevirtual 624	android/location/Location:getLongitude	()D
    //   1787: dstore 55
    //   1789: dload 53
    //   1791: ldc2_w 625
    //   1794: dcmpl
    //   1795: ifle +61 -> 1856
    //   1798: dload 55
    //   1800: ldc2_w 627
    //   1803: dcmpl
    //   1804: ifle +52 -> 1856
    //   1807: aload 5
    //   1809: ldc_w 776
    //   1812: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1815: pop
    //   1816: aload 5
    //   1818: ldc_w 632
    //   1821: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1824: dload 55
    //   1826: invokevirtual 635	java/lang/StringBuilder:append	(D)Ljava/lang/StringBuilder;
    //   1829: ldc_w 637
    //   1832: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1835: pop
    //   1836: aload 5
    //   1838: ldc_w 639
    //   1841: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1844: dload 53
    //   1846: invokevirtual 635	java/lang/StringBuilder:append	(D)Ljava/lang/StringBuilder;
    //   1849: ldc_w 641
    //   1852: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1855: pop
    //   1856: ldc_w 778
    //   1859: invokestatic 181	com/autonavi/aps/api/Utils:writeLogCat	(Ljava/lang/String;)V
    //   1862: aconst_null
    //   1863: putstatic 90	com/autonavi/aps/api/APSYUNPINGTAI:p	Landroid/location/Location;
    //   1866: getstatic 82	com/autonavi/aps/api/APSYUNPINGTAI:l	Landroid/location/LocationManager;
    //   1869: getstatic 84	com/autonavi/aps/api/APSYUNPINGTAI:m	Landroid/location/LocationListener;
    //   1872: invokevirtual 437	android/location/LocationManager:removeUpdates	(Landroid/location/LocationListener;)V
    //   1875: goto -1052 -> 823
    //   1878: astore 31
    //   1880: aload 31
    //   1882: invokestatic 317	com/autonavi/aps/api/Utils:printException	(Ljava/lang/Exception;)V
    //   1885: goto -118 -> 1767
    //   1888: iload 46
    //   1890: bipush 20
    //   1892: if_icmpgt -1023 -> 869
    //   1895: getstatic 101	com/autonavi/aps/api/APSYUNPINGTAI:s	Ljava/util/List;
    //   1898: iload 46
    //   1900: invokeinterface 256 2 0
    //   1905: checkcast 780	android/net/wifi/ScanResult
    //   1908: astore 51
    //   1910: aload 45
    //   1912: aload 51
    //   1914: getfield 783	android/net/wifi/ScanResult:BSSID	Ljava/lang/String;
    //   1917: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1920: ldc_w 663
    //   1923: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1926: aload 51
    //   1928: getfield 786	android/net/wifi/ScanResult:level	I
    //   1931: invokevirtual 588	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1934: ldc_w 666
    //   1937: invokevirtual 172	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1940: pop
    //   1941: iinc 46 1
    //   1944: goto -1088 -> 856
    //   1947: getstatic 118	com/autonavi/aps/api/APSYUNPINGTAI:x	Landroid/net/wifi/WifiInfo;
    //   1950: ifnull -930 -> 1020
    //   1953: getstatic 118	com/autonavi/aps/api/APSYUNPINGTAI:x	Landroid/net/wifi/WifiInfo;
    //   1956: invokevirtual 661	android/net/wifi/WifiInfo:getBSSID	()Ljava/lang/String;
    //   1959: ifnonnull -939 -> 1020
    //   1962: ldc_w 788
    //   1965: invokestatic 181	com/autonavi/aps/api/Utils:writeLogCat	(Ljava/lang/String;)V
    //   1968: goto -948 -> 1020
    //   1971: astore 41
    //   1973: aload 41
    //   1975: invokestatic 317	com/autonavi/aps/api/Utils:printException	(Ljava/lang/Exception;)V
    //   1978: goto -772 -> 1206
    //
    // Exception table:
    //   from	to	target	type
    //   4	17	1614	finally
    //   17	23	1614	finally
    //   23	37	1614	finally
    //   37	43	1614	finally
    //   47	68	1614	finally
    //   68	72	1614	finally
    //   79	87	1614	finally
    //   87	101	1614	finally
    //   101	256	1614	finally
    //   261	342	1614	finally
    //   342	471	1614	finally
    //   471	651	1614	finally
    //   654	688	1614	finally
    //   688	728	1614	finally
    //   746	819	1614	finally
    //   819	823	1614	finally
    //   823	853	1614	finally
    //   856	869	1614	finally
    //   869	892	1614	finally
    //   892	905	1614	finally
    //   910	975	1614	finally
    //   975	1020	1614	finally
    //   1020	1177	1614	finally
    //   1177	1206	1614	finally
    //   1206	1257	1614	finally
    //   1266	1275	1614	finally
    //   1275	1341	1614	finally
    //   1347	1564	1614	finally
    //   1567	1611	1614	finally
    //   1625	1634	1614	finally
    //   1634	1700	1614	finally
    //   1706	1755	1614	finally
    //   1755	1767	1614	finally
    //   1767	1789	1614	finally
    //   1807	1856	1614	finally
    //   1856	1875	1614	finally
    //   1880	1885	1614	finally
    //   1895	1941	1614	finally
    //   1947	1968	1614	finally
    //   1973	1978	1614	finally
    //   1755	1767	1878	java/lang/InterruptedException
    //   1177	1206	1971	java/lang/Exception
  }

  protected void finalize()
  {
    try
    {
      super.finalize();
      return;
    }
    catch (Exception localException)
    {
      Utils.printException(localException);
      return;
    }
    catch (Throwable localThrowable)
    {
      Utils.printThrowable(localThrowable);
    }
  }

  public Location getCurrentLocation(android.location.Location paramLocation)
  {
    long l1 = System.currentTimeMillis() - A;
    if (l1 < 2000L)
    {
      Utils.writeLogCat("block frequent request, return last location, duration is " + String.valueOf(l1) + " milliseconds");
      return z;
    }
    StringBuilder localStringBuilder = new StringBuilder("start new request, duration is ");
    if (A < Double.parseDouble("1.0"));
    String str2;
    for (String str1 = "0 (the very first request)"; ; str1 = String.valueOf(l1))
    {
      Utils.writeLogCat(str1 + " milliseconds");
      if (!B)
        o = paramLocation;
      str2 = j();
      if ((!str2.equalsIgnoreCase(y)) || (z == null))
        break;
      Utils.writeLogCat("same request, direct return");
      return z;
    }
    y = str2;
    Location localLocation = a(y);
    z = localLocation;
    double d1;
    double d2;
    if ((localLocation != null) && (o != null))
    {
      d1 = o.getLatitude();
      d2 = o.getLongitude();
      z.setRawx(d2);
      z.setRawy(d1);
    }
    try
    {
      float[] arrayOfFloat = new float[1];
      android.location.Location.distanceBetween(d1, d2, z.getCeny(), z.getCenx(), arrayOfFloat);
      z.setRawd(arrayOfFloat[0]);
      A = System.currentTimeMillis();
      if (z != null)
        Storage.getInstance().writeLog(z.toString().replace("\n", "#"));
      return z;
    }
    catch (Exception localException)
    {
      while (true)
        Utils.printException(localException);
    }
  }

  public String getKey()
  {
    return c;
  }

  public String getPackageName()
  {
    return d;
  }

  public String getProductName()
  {
    return b;
  }

  public String getVersion()
  {
    return "1.0.201301091612";
  }

  public void onDestroy()
  {
    Utils.writeLogCat("ondestroy");
    try
    {
      if ((f != null) && (w != null))
        f.unregisterReceiver(w);
    }
    catch (Exception localException3)
    {
      try
      {
        if (m != null)
          l.removeUpdates(m);
        m = null;
      }
      catch (Exception localException3)
      {
        try
        {
          if (n != null)
            l.removeUpdates(n);
          n = null;
        }
        catch (Exception localException3)
        {
          try
          {
            while (true)
            {
              if (k != null)
                k.listen(u, 0);
              u = null;
              q.clear();
              r.clear();
              s.clear();
              x = null;
              k = null;
              if (e != null)
                e.finalize();
              A = 0L;
              y = null;
              z = null;
              C = false;
              o = null;
              p = null;
              e = null;
              System.gc();
              return;
              localException1 = localException1;
              Utils.printException(localException1);
              continue;
              localException2 = localException2;
              Utils.printException(localException2);
            }
            localException3 = localException3;
            Utils.printException(localException3);
          }
          catch (Exception localException4)
          {
            while (true)
              Utils.printException(localException4);
          }
        }
      }
    }
  }

  public void setKey(String paramString)
  {
    c = paramString;
  }

  public void setLicence(String paramString)
  {
    a = paramString;
  }

  public void setOpenGps(boolean paramBoolean)
  {
    if (paramBoolean != B)
    {
      if (C)
      {
        Utils.writeLogCat("gps status change command is pending");
        return;
      }
      C = true;
      h();
      return;
    }
    if (C)
    {
      Utils.writeLogCat("gps status change command is pending");
      return;
    }
    Utils.writeLogCat("block same gps status change command");
  }

  public void setOpenSystemNetworkLocation(boolean paramBoolean)
  {
    if (D != paramBoolean)
    {
      D = paramBoolean;
      if (paramBoolean)
        i();
    }
    else
    {
      return;
    }
    Utils.writeLogCat("close system network listener");
    try
    {
      if (m != null)
        l.removeUpdates(m);
      Utils.writeLogCat("start system network listener");
      m = null;
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Utils.printException(localException);
    }
  }

  public void setPackageName(String paramString)
  {
    d = paramString;
  }

  public void setProductName(String paramString)
  {
    b = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.APSYUNPINGTAI
 * JD-Core Version:    0.6.0
 */