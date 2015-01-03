package com.tencent.mm.sdk.platformtools;

import android.os.Handler;
import android.os.Looper;

public abstract class SyncTask<R>
{
  private final long bg;
  private long bh;
  private long bi;
  private Runnable bj = new SyncTask.1(this);
  private Object lock = new Object();
  private R result;

  public SyncTask()
  {
    this(0L, null);
  }

  public SyncTask(long paramLong, R paramR)
  {
    this.bg = paramLong;
    this.result = paramR;
  }

  public R exec(Handler paramHandler)
  {
    if (paramHandler == null)
    {
      Log.d("MicroMsg.SDK.SyncTask", "null handler, task in exec thread, return now");
      return run();
    }
    if (Thread.currentThread().getId() == paramHandler.getLooper().getThread().getId())
    {
      Log.d("MicroMsg.SDK.SyncTask", "same tid, task in exec thread, return now");
      return run();
    }
    this.bh = Util.currentTicks();
    try
    {
      synchronized (this.lock)
      {
        paramHandler.post(this.bj);
        this.lock.wait(this.bg);
        long l = Util.ticksToNow(this.bh);
        Object[] arrayOfObject = new Object[4];
        arrayOfObject[0] = this.result;
        arrayOfObject[1] = Long.valueOf(l);
        arrayOfObject[2] = Long.valueOf(this.bi);
        arrayOfObject[3] = Long.valueOf(l - this.bi);
        Log.v("MicroMsg.SDK.SyncTask", "sync task done, return=%s, cost=%d(wait=%d, run=%d)", arrayOfObject);
        return this.result;
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      while (true)
        localInterruptedException.printStackTrace();
    }
  }

  protected abstract R run();

  public void setResult(R paramR)
  {
    this.result = paramR;
    synchronized (this.lock)
    {
      this.lock.notify();
      return;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.SyncTask
 * JD-Core Version:    0.6.0
 */