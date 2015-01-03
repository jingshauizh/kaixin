package com.kaixin001.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TreadPoolUtils
{
  private static final int POOL_SIZE = 1;
  private static final Executor thearpool = Executors.newScheduledThreadPool(1);

  public static void execute(Runnable paramRunnable)
  {
    if (paramRunnable == null)
      return;
    thearpool.execute(paramRunnable);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.TreadPoolUtils
 * JD-Core Version:    0.6.0
 */