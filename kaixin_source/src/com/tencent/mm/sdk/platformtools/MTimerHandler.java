package com.tencent.mm.sdk.platformtools;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class MTimerHandler extends Handler
{
  private static int ac;
  private final int ad;
  private final boolean av;
  private long aw = 0L;
  private final CallBack ax;

  public MTimerHandler(Looper paramLooper, CallBack paramCallBack, boolean paramBoolean)
  {
    super(paramLooper);
    this.ax = paramCallBack;
    this.ad = d();
    this.av = paramBoolean;
  }

  public MTimerHandler(CallBack paramCallBack, boolean paramBoolean)
  {
    this.ax = paramCallBack;
    this.ad = d();
    this.av = paramBoolean;
  }

  private static int d()
  {
    if (ac >= 8192)
      ac = 0;
    int i = 1 + ac;
    ac = i;
    return i;
  }

  protected void finalize()
  {
    stopTimer();
    super.finalize();
  }

  public void handleMessage(Message paramMessage)
  {
    if ((paramMessage.what != this.ad) || (this.ax == null));
    do
      return;
    while ((!this.ax.onTimerExpired()) || (!this.av));
    sendEmptyMessageDelayed(this.ad, this.aw);
  }

  public void startTimer(long paramLong)
  {
    this.aw = paramLong;
    stopTimer();
    sendEmptyMessageDelayed(this.ad, paramLong);
  }

  public void stopTimer()
  {
    removeMessages(this.ad);
  }

  public boolean stopped()
  {
    return !hasMessages(this.ad);
  }

  public static abstract interface CallBack
  {
    public abstract boolean onTimerExpired();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.MTimerHandler
 * JD-Core Version:    0.6.0
 */