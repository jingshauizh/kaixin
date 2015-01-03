package com.tencent.mm.sdk.platformtools;

import android.os.Handler;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueWorkerThread
{
  private LinkedBlockingQueue<ThreadObject> aQ = new LinkedBlockingQueue();
  private boolean aR = false;
  private int aS = 1;
  private Vector<WorkerThread> aT = new Vector();
  private Handler aU = new QueueWorkerThread.1(this);
  private Object lock = new byte[0];
  private String name = "";
  private int priority = 1;

  public QueueWorkerThread(int paramInt, String paramString)
  {
    this(paramInt, paramString, 1);
  }

  public QueueWorkerThread(int paramInt1, String paramString, int paramInt2)
  {
    this.aS = paramInt2;
    this.name = paramString;
    this.priority = paramInt1;
  }

  public int add(ThreadObject paramThreadObject)
  {
    int j;
    if (paramThreadObject == null)
    {
      Log.e("QueueWorkerThread.QueueWorkerThread", "add empty thread object");
      j = -1;
    }
    int k;
    int m;
    do
    {
      int i;
      do
      {
        return j;
        try
        {
          if (!this.aQ.offer(paramThreadObject, 1L, TimeUnit.MILLISECONDS))
          {
            Log.e("QueueWorkerThread.QueueWorkerThread", "add To Queue failed");
            return -2;
          }
        }
        catch (Exception localException)
        {
          Log.e("QueueWorkerThread.QueueWorkerThread", "add To Queue failed :" + localException.getMessage());
          localException.printStackTrace();
          return -3;
        }
        if (this.aT.size() == 0)
          break;
        i = this.aQ.size();
        j = 0;
      }
      while (i <= 0);
      k = this.aS;
      m = this.aT.size();
      j = 0;
    }
    while (k <= m);
    new WorkerThread(0).start();
    return 0;
  }

  public int getQueueSize()
  {
    return this.aQ.size();
  }

  public boolean isDead()
  {
    return (this.aT == null) || (this.aT.size() == 0);
  }

  public void pause(boolean paramBoolean)
  {
    synchronized (this.lock)
    {
      this.aR = paramBoolean;
      if (!paramBoolean);
      synchronized (this.lock)
      {
        this.lock.notifyAll();
        return;
      }
    }
  }

  public static abstract interface ThreadObject
  {
    public abstract boolean doInBackground();

    public abstract boolean onPostExecute();
  }

  final class WorkerThread extends Thread
  {
    private int aW = 60;

    private WorkerThread()
    {
      super();
      setPriority(QueueWorkerThread.b(QueueWorkerThread.this));
      QueueWorkerThread.c(QueueWorkerThread.this).add(this);
    }

    // ERROR //
    public final void run()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 23	com/tencent/mm/sdk/platformtools/QueueWorkerThread$WorkerThread:aW	I
      //   4: ifle +145 -> 149
      //   7: aload_0
      //   8: getfield 12	com/tencent/mm/sdk/platformtools/QueueWorkerThread$WorkerThread:aV	Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;
      //   11: invokestatic 52	com/tencent/mm/sdk/platformtools/QueueWorkerThread:d	(Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;)Ljava/lang/Object;
      //   14: astore_2
      //   15: aload_2
      //   16: monitorenter
      //   17: aload_0
      //   18: getfield 12	com/tencent/mm/sdk/platformtools/QueueWorkerThread$WorkerThread:aV	Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;
      //   21: invokestatic 56	com/tencent/mm/sdk/platformtools/QueueWorkerThread:e	(Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;)Z
      //   24: ifeq +13 -> 37
      //   27: aload_0
      //   28: getfield 12	com/tencent/mm/sdk/platformtools/QueueWorkerThread$WorkerThread:aV	Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;
      //   31: invokestatic 52	com/tencent/mm/sdk/platformtools/QueueWorkerThread:d	(Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;)Ljava/lang/Object;
      //   34: invokevirtual 61	java/lang/Object:wait	()V
      //   37: aload_2
      //   38: monitorexit
      //   39: aload_0
      //   40: getfield 12	com/tencent/mm/sdk/platformtools/QueueWorkerThread$WorkerThread:aV	Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;
      //   43: invokestatic 65	com/tencent/mm/sdk/platformtools/QueueWorkerThread:f	(Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;)Ljava/util/concurrent/LinkedBlockingQueue;
      //   46: ldc2_w 66
      //   49: getstatic 73	java/util/concurrent/TimeUnit:MILLISECONDS	Ljava/util/concurrent/TimeUnit;
      //   52: invokevirtual 79	java/util/concurrent/LinkedBlockingQueue:poll	(JLjava/util/concurrent/TimeUnit;)Ljava/lang/Object;
      //   55: checkcast 81	com/tencent/mm/sdk/platformtools/QueueWorkerThread$ThreadObject
      //   58: astore 6
      //   60: aload 6
      //   62: ifnonnull +44 -> 106
      //   65: aload_0
      //   66: iconst_m1
      //   67: aload_0
      //   68: getfield 23	com/tencent/mm/sdk/platformtools/QueueWorkerThread$WorkerThread:aW	I
      //   71: iadd
      //   72: putfield 23	com/tencent/mm/sdk/platformtools/QueueWorkerThread$WorkerThread:aW	I
      //   75: goto -75 -> 0
      //   78: astore 4
      //   80: aload 4
      //   82: invokevirtual 84	java/lang/Exception:printStackTrace	()V
      //   85: goto -48 -> 37
      //   88: astore_3
      //   89: aload_2
      //   90: monitorexit
      //   91: aload_3
      //   92: athrow
      //   93: astore 5
      //   95: aload 5
      //   97: invokevirtual 84	java/lang/Exception:printStackTrace	()V
      //   100: aconst_null
      //   101: astore 6
      //   103: goto -43 -> 60
      //   106: aload_0
      //   107: bipush 60
      //   109: putfield 23	com/tencent/mm/sdk/platformtools/QueueWorkerThread$WorkerThread:aW	I
      //   112: aload 6
      //   114: invokeinterface 88 1 0
      //   119: ifeq -119 -> 0
      //   122: aload_0
      //   123: getfield 12	com/tencent/mm/sdk/platformtools/QueueWorkerThread$WorkerThread:aV	Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;
      //   126: invokestatic 92	com/tencent/mm/sdk/platformtools/QueueWorkerThread:g	(Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;)Landroid/os/Handler;
      //   129: aload_0
      //   130: getfield 12	com/tencent/mm/sdk/platformtools/QueueWorkerThread$WorkerThread:aV	Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;
      //   133: invokestatic 92	com/tencent/mm/sdk/platformtools/QueueWorkerThread:g	(Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;)Landroid/os/Handler;
      //   136: iconst_0
      //   137: aload 6
      //   139: invokevirtual 98	android/os/Handler:obtainMessage	(ILjava/lang/Object;)Landroid/os/Message;
      //   142: invokevirtual 102	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
      //   145: pop
      //   146: goto -146 -> 0
      //   149: aload_0
      //   150: getfield 12	com/tencent/mm/sdk/platformtools/QueueWorkerThread$WorkerThread:aV	Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;
      //   153: invokestatic 35	com/tencent/mm/sdk/platformtools/QueueWorkerThread:c	(Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;)Ljava/util/Vector;
      //   156: aload_0
      //   157: invokevirtual 105	java/util/Vector:remove	(Ljava/lang/Object;)Z
      //   160: pop
      //   161: ldc 107
      //   163: new 109	java/lang/StringBuilder
      //   166: dup
      //   167: ldc 111
      //   169: invokespecial 112	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   172: aload_0
      //   173: getfield 12	com/tencent/mm/sdk/platformtools/QueueWorkerThread$WorkerThread:aV	Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;
      //   176: invokestatic 65	com/tencent/mm/sdk/platformtools/QueueWorkerThread:f	(Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;)Ljava/util/concurrent/LinkedBlockingQueue;
      //   179: invokevirtual 116	java/util/concurrent/LinkedBlockingQueue:size	()I
      //   182: invokevirtual 120	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   185: ldc 122
      //   187: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   190: aload_0
      //   191: getfield 12	com/tencent/mm/sdk/platformtools/QueueWorkerThread$WorkerThread:aV	Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;
      //   194: invokestatic 35	com/tencent/mm/sdk/platformtools/QueueWorkerThread:c	(Lcom/tencent/mm/sdk/platformtools/QueueWorkerThread;)Ljava/util/Vector;
      //   197: invokevirtual 126	java/util/Vector:size	()I
      //   200: invokevirtual 120	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   203: invokevirtual 130	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   206: invokestatic 135	com/tencent/mm/sdk/platformtools/Log:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   209: return
      //
      // Exception table:
      //   from	to	target	type
      //   17	37	78	java/lang/Exception
      //   17	37	88	finally
      //   37	39	88	finally
      //   80	85	88	finally
      //   39	60	93	java/lang/Exception
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.QueueWorkerThread
 * JD-Core Version:    0.6.0
 */