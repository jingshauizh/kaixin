package com.tencent.mm.sdk.platformtools;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import junit.framework.Assert;

public class MAlarmHandler
{
  public static final long NEXT_FIRE_INTERVAL = 9223372036854775807L;
  private static int ac;
  private static Map<Integer, MAlarmHandler> ah = new HashMap();
  private static IBumper aj;
  private static boolean ak = false;
  private final int ad;
  private final boolean ae;
  private long af = 0L;
  private long ag = 0L;
  private final CallBack ai;

  public MAlarmHandler(CallBack paramCallBack, boolean paramBoolean)
  {
    Assert.assertTrue("bumper not initialized", ak);
    this.ai = paramCallBack;
    this.ae = paramBoolean;
    if (ac >= 8192)
      ac = 0;
    int i = 1 + ac;
    ac = i;
    this.ad = i;
  }

  public static long fire()
  {
    LinkedList localLinkedList = new LinkedList();
    HashSet localHashSet = new HashSet();
    localHashSet.addAll(ah.keySet());
    Iterator localIterator = localHashSet.iterator();
    long l1 = 9223372036854775807L;
    long l2;
    if (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      MAlarmHandler localMAlarmHandler = (MAlarmHandler)ah.get(localInteger);
      if (localMAlarmHandler == null)
        break label266;
      long l3 = Util.ticksToNow(localMAlarmHandler.af);
      if (l3 < 0L)
        l3 = 0L;
      if (l3 > localMAlarmHandler.ag)
      {
        if ((!localMAlarmHandler.ai.onTimerExpired()) || (!localMAlarmHandler.ae))
          localLinkedList.add(localInteger);
        while (true)
        {
          localMAlarmHandler.af = Util.currentTicks();
          break;
          l1 = localMAlarmHandler.ag;
        }
      }
      if (localMAlarmHandler.ag - l3 >= l1)
        break label266;
      l2 = localMAlarmHandler.ag - l3;
    }
    while (true)
    {
      l1 = l2;
      break;
      for (int i = 0; i < localLinkedList.size(); i++)
        ah.remove(localLinkedList.get(i));
      if ((l1 == 9223372036854775807L) && (aj != null))
      {
        aj.cancel();
        Log.v("MicroMsg.MAlarmHandler", "cancel bumper for no more handler");
      }
      return l1;
      label266: l2 = l1;
    }
  }

  public static void initAlarmBumper(IBumper paramIBumper)
  {
    ak = true;
    aj = paramIBumper;
  }

  protected void finalize()
  {
    stopTimer();
    super.finalize();
  }

  public void startTimer(long paramLong)
  {
    this.ag = paramLong;
    this.af = Util.currentTicks();
    long l1 = this.ag;
    Log.d("MicroMsg.MAlarmHandler", "check need prepare: check=" + l1);
    Iterator localIterator = ah.entrySet().iterator();
    long l2 = 9223372036854775807L;
    long l3;
    while (localIterator.hasNext())
    {
      MAlarmHandler localMAlarmHandler = (MAlarmHandler)((Map.Entry)localIterator.next()).getValue();
      if (localMAlarmHandler == null)
        break label229;
      long l4 = Util.ticksToNow(localMAlarmHandler.af);
      if (l4 < 0L)
        l4 = 0L;
      if (l4 > localMAlarmHandler.ag)
      {
        l2 = localMAlarmHandler.ag;
        continue;
      }
      if (localMAlarmHandler.ag - l4 >= l2)
        break label229;
      l3 = localMAlarmHandler.ag - l4;
    }
    while (true)
    {
      l2 = l3;
      break;
      if (l2 > l1);
      for (int i = 1; ; i = 0)
      {
        stopTimer();
        ah.put(Integer.valueOf(this.ad), this);
        if ((aj != null) && (i != 0))
        {
          Log.v("MicroMsg.MAlarmHandler", "prepare bumper");
          aj.prepare();
        }
        return;
      }
      label229: l3 = l2;
    }
  }

  public void stopTimer()
  {
    ah.remove(Integer.valueOf(this.ad));
  }

  public boolean stopped()
  {
    return !ah.containsKey(Integer.valueOf(this.ad));
  }

  public static abstract interface CallBack
  {
    public abstract boolean onTimerExpired();
  }

  public static abstract interface IBumper
  {
    public abstract void cancel();

    public abstract void prepare();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.MAlarmHandler
 * JD-Core Version:    0.6.0
 */