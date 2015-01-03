package com.tencent.mm.sdk.platformtools;

import android.os.Process;
import java.io.File;
import java.util.Scanner;

public final class TrafficStats
{
  public static final String DEV_FILE = "/proc/self/net/dev";
  public static final String GPRSLINE = "rmnet0";
  public static final String WIFILINE = "tiwlan0";
  private static long br;
  private static long bs;
  private static long bt;
  private static long bu;
  private static long bv;
  private static long bw;
  private static long bx;
  private static long by;

  public static long getMobileRx(long paramLong)
  {
    if (bw > paramLong)
      paramLong = bw;
    return paramLong;
  }

  public static long getMobileTx(long paramLong)
  {
    if (bv > paramLong)
      paramLong = bv;
    return paramLong;
  }

  public static long getWifiRx(long paramLong)
  {
    if (by > paramLong)
      paramLong = by;
    return paramLong;
  }

  public static long getWifiTx(long paramLong)
  {
    if (bx > paramLong)
      paramLong = bx;
    return paramLong;
  }

  public static void reset()
  {
    br = -1L;
    bs = -1L;
    bt = -1L;
    bu = -1L;
    update();
  }

  public static void update()
  {
    long l1 = 0L;
    long l2 = 0L;
    long l3 = 0L;
    long l4 = 0L;
    try
    {
      Scanner localScanner = new Scanner(new File("/proc/" + Process.myPid() + "/net/dev"));
      localScanner.nextLine();
      localScanner.nextLine();
      while (localScanner.hasNext())
      {
        String[] arrayOfString = localScanner.nextLine().split("[ :\t]+");
        if (arrayOfString[0].length() != 0)
          break label633;
        i = 1;
        if ((!arrayOfString[0].equals("lo")) && (arrayOfString[(i + 0)].startsWith("rmnet")))
        {
          l1 += Long.parseLong(arrayOfString[(i + 9)]);
          l2 += Long.parseLong(arrayOfString[(i + 1)]);
        }
        if ((arrayOfString[(i + 0)].equals("lo")) || (arrayOfString[(i + 0)].startsWith("rmnet")))
          continue;
        l3 += Long.parseLong(arrayOfString[(i + 9)]);
        l4 += Long.parseLong(arrayOfString[(i + 1)]);
      }
      localScanner.close();
      if (br < 0L)
      {
        br = l1;
        Object[] arrayOfObject7 = new Object[1];
        arrayOfObject7[0] = Long.valueOf(l1);
        Log.v("MicroMsg.SDK.TrafficStats", "fix loss newMobileTx %d", arrayOfObject7);
      }
      if (bs < 0L)
      {
        bs = l2;
        Object[] arrayOfObject6 = new Object[1];
        arrayOfObject6[0] = Long.valueOf(l2);
        Log.v("MicroMsg.SDK.TrafficStats", "fix loss newMobileRx %d", arrayOfObject6);
      }
      if (bt < 0L)
      {
        bt = l3;
        Object[] arrayOfObject5 = new Object[1];
        arrayOfObject5[0] = Long.valueOf(l3);
        Log.v("MicroMsg.SDK.TrafficStats", "fix loss newWifiTx %d", arrayOfObject5);
      }
      if (bu < 0L)
      {
        bu = l4;
        Object[] arrayOfObject4 = new Object[1];
        arrayOfObject4[0] = Long.valueOf(l4);
        Log.v("MicroMsg.SDK.TrafficStats", "fix loss newWifiRx %d", arrayOfObject4);
      }
      if (l4 - bu < 0L)
      {
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Long.valueOf(l4 - bu);
        Log.v("MicroMsg.SDK.TrafficStats", "minu %d", arrayOfObject3);
      }
      if (l3 - bt < 0L)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Long.valueOf(l3 - bt);
        Log.v("MicroMsg.SDK.TrafficStats", "minu %d", arrayOfObject2);
      }
      long l5;
      long l6;
      label474: long l7;
      label496: long l8;
      if (l1 >= br)
      {
        l5 = l1 - br;
        bv = l5;
        if (l2 < bs)
          break label603;
        l6 = l2 - bs;
        bw = l6;
        if (l3 < bt)
          break label609;
        l7 = l3 - bt;
        bx = l7;
        if (l4 < bu)
          break label616;
        l8 = l4 - bu;
      }
      while (true)
      {
        by = l8;
        br = l1;
        bs = l2;
        bt = l3;
        bu = l4;
        Object[] arrayOfObject1 = new Object[4];
        arrayOfObject1[0] = Long.valueOf(by);
        arrayOfObject1[1] = Long.valueOf(bx);
        arrayOfObject1[2] = Long.valueOf(bw);
        arrayOfObject1[3] = Long.valueOf(bv);
        Log.d("MicroMsg.SDK.TrafficStats", "current system traffic: wifi rx/tx=%d/%d, mobile rx/tx=%d/%d", arrayOfObject1);
        return;
        l5 = l1;
        break;
        label603: l6 = l2;
        break label474;
        label609: l7 = l3;
        break label496;
        label616: l8 = l4;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        continue;
        label633: int i = 0;
      }
    }
  }

  public static long updateMobileRx(long paramLong)
  {
    update();
    return getMobileRx(paramLong);
  }

  public static long updateMobileTx(long paramLong)
  {
    update();
    return getMobileTx(paramLong);
  }

  public static long updateWifiRx(long paramLong)
  {
    update();
    return getWifiRx(paramLong);
  }

  public static long updateWifiTx(long paramLong)
  {
    update();
    return getWifiTx(paramLong);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.TrafficStats
 * JD-Core Version:    0.6.0
 */