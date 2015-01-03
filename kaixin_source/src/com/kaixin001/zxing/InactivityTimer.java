package com.kaixin001.zxing;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public final class InactivityTimer
{
  private static final int INACTIVITY_DELAY_SECONDS = 300;
  private final Activity activity;
  private ScheduledFuture<?> inactivityFuture = null;
  private final ScheduledExecutorService inactivityTimer = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory(null));
  private final PowerStatusReceiver powerStatusReceiver = new PowerStatusReceiver(null);

  public InactivityTimer(Activity paramActivity)
  {
    this.activity = paramActivity;
    onActivity();
  }

  private void cancel()
  {
    if (this.inactivityFuture != null)
    {
      this.inactivityFuture.cancel(true);
      this.inactivityFuture = null;
    }
  }

  public void onActivity()
  {
    cancel();
    if (!this.inactivityTimer.isShutdown());
    try
    {
      this.inactivityFuture = this.inactivityTimer.schedule(new FinishListener(this.activity), 300L, TimeUnit.SECONDS);
      return;
    }
    catch (RejectedExecutionException localRejectedExecutionException)
    {
    }
  }

  public void onPause()
  {
    this.activity.unregisterReceiver(this.powerStatusReceiver);
  }

  public void onResume()
  {
    this.activity.registerReceiver(this.powerStatusReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
  }

  public void shutdown()
  {
    cancel();
    this.inactivityTimer.shutdown();
  }

  private static final class DaemonThreadFactory
    implements ThreadFactory
  {
    public Thread newThread(Runnable paramRunnable)
    {
      Thread localThread = new Thread(paramRunnable);
      localThread.setDaemon(true);
      return localThread;
    }
  }

  private final class PowerStatusReceiver extends BroadcastReceiver
  {
    private PowerStatusReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if ("android.intent.action.BATTERY_CHANGED".equals(paramIntent.getAction()))
      {
        if (paramIntent.getIntExtra("plugged", -1) == 0)
          InactivityTimer.this.onActivity();
      }
      else
        return;
      InactivityTimer.this.cancel();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.zxing.InactivityTimer
 * JD-Core Version:    0.6.0
 */