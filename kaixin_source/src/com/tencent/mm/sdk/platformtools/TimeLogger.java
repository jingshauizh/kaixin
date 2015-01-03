package com.tencent.mm.sdk.platformtools;

import android.os.SystemClock;
import java.util.ArrayList;

public class TimeLogger
{
  private String bm;
  private String bn;
  private boolean bo;
  ArrayList<Long> bp;
  ArrayList<String> bq;

  public TimeLogger(String paramString1, String paramString2)
  {
    reset(paramString1, paramString2);
  }

  public void addSplit(String paramString)
  {
    if (this.bo)
      return;
    long l = SystemClock.elapsedRealtime();
    this.bp.add(Long.valueOf(l));
    this.bq.add(paramString);
  }

  public void dumpToLog()
  {
    if (this.bo)
      return;
    Log.d(this.bm, this.bn + ": begin");
    long l1 = ((Long)this.bp.get(0)).longValue();
    int i = 1;
    long l2 = l1;
    while (i < this.bp.size())
    {
      long l3 = ((Long)this.bp.get(i)).longValue();
      String str = (String)this.bq.get(i);
      long l4 = ((Long)this.bp.get(i - 1)).longValue();
      Log.d(this.bm, this.bn + ":      " + (l3 - l4) + " ms, " + str);
      i++;
      l2 = l3;
    }
    Log.d(this.bm, this.bn + ": end, " + (l2 - l1) + " ms");
  }

  public void reset()
  {
    this.bo = false;
    if (this.bo)
      return;
    if (this.bp == null)
    {
      this.bp = new ArrayList();
      this.bq = new ArrayList();
    }
    while (true)
    {
      addSplit(null);
      return;
      this.bp.clear();
      this.bq.clear();
    }
  }

  public void reset(String paramString1, String paramString2)
  {
    this.bm = paramString1;
    this.bn = paramString2;
    reset();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.TimeLogger
 * JD-Core Version:    0.6.0
 */