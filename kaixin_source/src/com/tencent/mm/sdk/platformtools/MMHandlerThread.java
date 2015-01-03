package com.tencent.mm.sdk.platformtools;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import junit.framework.Assert;

public class MMHandlerThread
{
  public static long mainThreadID = -1L;
  private HandlerThread ao = null;
  private Handler ap = null;

  public MMHandlerThread()
  {
    c();
  }

  private void c()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Util.getStack();
    Log.d("MicroMsg.MMHandlerThread", "MMHandlerThread init [%s]", arrayOfObject);
    this.ap = null;
    this.ao = new HandlerThread("MMHandlerThread", 1);
    this.ao.start();
  }

  public static boolean isMainThread()
  {
    if (mainThreadID == -1L);
    for (boolean bool = true; ; bool = false)
    {
      Assert.assertFalse("mainThreadID not init ", bool);
      if (Thread.currentThread().getId() != mainThreadID)
        break;
      return true;
    }
    return false;
  }

  public static void postToMainThread(Runnable paramRunnable)
  {
    if (paramRunnable == null)
      return;
    new Handler(Looper.getMainLooper()).post(paramRunnable);
  }

  public static void postToMainThreadDelayed(Runnable paramRunnable, long paramLong)
  {
    if (paramRunnable == null)
      return;
    new Handler(Looper.getMainLooper()).postDelayed(paramRunnable, paramLong);
  }

  public static void setMainThreadID(long paramLong)
  {
    if ((mainThreadID < 0L) && (paramLong > 0L))
      mainThreadID = paramLong;
  }

  public Looper getLooper()
  {
    return this.ao.getLooper();
  }

  public Handler getWorkerHandler()
  {
    if (this.ap == null)
      this.ap = new Handler(this.ao.getLooper());
    return this.ap;
  }

  public int postAtFrontOfWorker(IWaitWorkThread paramIWaitWorkThread)
  {
    if (paramIWaitWorkThread == null)
      return -1;
    if (new Handler(getLooper()).postAtFrontOfQueue(new MMHandlerThread.3(this, paramIWaitWorkThread)))
      return 0;
    return -2;
  }

  public int postToWorker(Runnable paramRunnable)
  {
    if (paramRunnable == null)
      return -1;
    getWorkerHandler().post(paramRunnable);
    return 0;
  }

  public int reset(IWaitWorkThread paramIWaitWorkThread)
  {
    return postAtFrontOfWorker(new MMHandlerThread.1(this, paramIWaitWorkThread));
  }

  // ERROR //
  public int syncReset(ResetCallback paramResetCallback)
  {
    // Byte code:
    //   0: ldc 133
    //   2: invokestatic 135	com/tencent/mm/sdk/platformtools/MMHandlerThread:isMainThread	()Z
    //   5: invokestatic 138	junit/framework/Assert:assertTrue	(Ljava/lang/String;Z)V
    //   8: iconst_0
    //   9: newarray byte
    //   11: astore_2
    //   12: new 140	com/tencent/mm/sdk/platformtools/MMHandlerThread$2
    //   15: dup
    //   16: aload_0
    //   17: aload_1
    //   18: aload_2
    //   19: invokespecial 143	com/tencent/mm/sdk/platformtools/MMHandlerThread$2:<init>	(Lcom/tencent/mm/sdk/platformtools/MMHandlerThread;Lcom/tencent/mm/sdk/platformtools/MMHandlerThread$ResetCallback;Ljava/lang/Object;)V
    //   22: astore_3
    //   23: aload_2
    //   24: monitorenter
    //   25: aload_0
    //   26: aload_3
    //   27: invokevirtual 127	com/tencent/mm/sdk/platformtools/MMHandlerThread:postAtFrontOfWorker	(Lcom/tencent/mm/sdk/platformtools/MMHandlerThread$IWaitWorkThread;)I
    //   30: istore 5
    //   32: iload 5
    //   34: ifne +7 -> 41
    //   37: aload_2
    //   38: invokevirtual 146	java/lang/Object:wait	()V
    //   41: aload_2
    //   42: monitorexit
    //   43: iload 5
    //   45: ireturn
    //   46: astore 4
    //   48: aload_2
    //   49: monitorexit
    //   50: aload 4
    //   52: athrow
    //   53: astore 6
    //   55: goto -14 -> 41
    //
    // Exception table:
    //   from	to	target	type
    //   25	32	46	finally
    //   37	41	46	finally
    //   41	43	46	finally
    //   37	41	53	java/lang/Exception
  }

  public static abstract interface IWaitWorkThread
  {
    public abstract boolean doInBackground();

    public abstract boolean onPostExecute();
  }

  public static abstract interface ResetCallback
  {
    public abstract void callback();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.MMHandlerThread
 * JD-Core Version:    0.6.0
 */