package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

@Beta
public final class Monitor
{
  private final ArrayList<Guard> activeGuards = Lists.newArrayListWithCapacity(1);
  private final ReentrantLock lock;

  public Monitor()
  {
    this(false);
  }

  public Monitor(boolean paramBoolean)
  {
    this.lock = new ReentrantLock(paramBoolean);
  }

  @GuardedBy("lock")
  private void decrementWaiters(Guard paramGuard)
  {
    int i = -1 + paramGuard.waiterCount;
    paramGuard.waiterCount = i;
    if (i == 0)
      this.activeGuards.remove(paramGuard);
  }

  @GuardedBy("lock")
  private void incrementWaiters(Guard paramGuard)
  {
    int i = paramGuard.waiterCount;
    paramGuard.waiterCount = (i + 1);
    if (i == 0)
      this.activeGuards.add(paramGuard);
  }

  @GuardedBy("lock")
  private void signalConditionsOfSatisfiedGuards(@Nullable Guard paramGuard)
  {
    ArrayList localArrayList = this.activeGuards;
    int i = localArrayList.size();
    for (int j = 0; ; j++)
    {
      if (j < i);
      try
      {
        Guard localGuard = (Guard)localArrayList.get(j);
        if (((localGuard == paramGuard) && (localGuard.waiterCount == 1)) || (!localGuard.isSatisfied()))
          continue;
        localGuard.condition.signal();
        return;
      }
      catch (Throwable localThrowable)
      {
        for (int k = 0; k < i; k++)
          ((Guard)localArrayList.get(k)).condition.signalAll();
        throw Throwables.propagate(localThrowable);
      }
    }
  }

  // ERROR //
  @GuardedBy("lock")
  private void waitInterruptibly(Guard paramGuard, boolean paramBoolean)
    throws InterruptedException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 68	com/google/common/util/concurrent/Monitor$Guard:isSatisfied	()Z
    //   4: ifne +46 -> 50
    //   7: iload_2
    //   8: ifeq +8 -> 16
    //   11: aload_0
    //   12: aconst_null
    //   13: invokespecial 92	com/google/common/util/concurrent/Monitor:signalConditionsOfSatisfiedGuards	(Lcom/google/common/util/concurrent/Monitor$Guard;)V
    //   16: aload_0
    //   17: aload_1
    //   18: invokespecial 94	com/google/common/util/concurrent/Monitor:incrementWaiters	(Lcom/google/common/util/concurrent/Monitor$Guard;)V
    //   21: aload_1
    //   22: getfield 72	com/google/common/util/concurrent/Monitor$Guard:condition	Ljava/util/concurrent/locks/Condition;
    //   25: astore 4
    //   27: aload 4
    //   29: invokeinterface 97 1 0
    //   34: aload_1
    //   35: invokevirtual 68	com/google/common/util/concurrent/Monitor$Guard:isSatisfied	()Z
    //   38: istore 7
    //   40: iload 7
    //   42: ifeq -15 -> 27
    //   45: aload_0
    //   46: aload_1
    //   47: invokespecial 99	com/google/common/util/concurrent/Monitor:decrementWaiters	(Lcom/google/common/util/concurrent/Monitor$Guard;)V
    //   50: return
    //   51: astore 5
    //   53: aload_0
    //   54: aload_1
    //   55: invokespecial 92	com/google/common/util/concurrent/Monitor:signalConditionsOfSatisfiedGuards	(Lcom/google/common/util/concurrent/Monitor$Guard;)V
    //   58: aload 5
    //   60: athrow
    //   61: astore_3
    //   62: aload_0
    //   63: aload_1
    //   64: invokespecial 99	com/google/common/util/concurrent/Monitor:decrementWaiters	(Lcom/google/common/util/concurrent/Monitor$Guard;)V
    //   67: aload_3
    //   68: athrow
    //   69: astore 6
    //   71: invokestatic 105	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   74: invokevirtual 108	java/lang/Thread:interrupt	()V
    //   77: aload 6
    //   79: invokestatic 86	com/google/common/base/Throwables:propagate	(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;
    //   82: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   27	34	51	java/lang/InterruptedException
    //   21	27	61	finally
    //   27	34	61	finally
    //   34	40	61	finally
    //   53	58	61	finally
    //   58	61	61	finally
    //   71	83	61	finally
    //   53	58	69	java/lang/Throwable
  }

  // ERROR //
  @GuardedBy("lock")
  private boolean waitInterruptibly(Guard paramGuard, long paramLong, boolean paramBoolean)
    throws InterruptedException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 68	com/google/common/util/concurrent/Monitor$Guard:isSatisfied	()Z
    //   4: ifne +66 -> 70
    //   7: iload 4
    //   9: ifeq +8 -> 17
    //   12: aload_0
    //   13: aconst_null
    //   14: invokespecial 92	com/google/common/util/concurrent/Monitor:signalConditionsOfSatisfiedGuards	(Lcom/google/common/util/concurrent/Monitor$Guard;)V
    //   17: aload_0
    //   18: aload_1
    //   19: invokespecial 94	com/google/common/util/concurrent/Monitor:incrementWaiters	(Lcom/google/common/util/concurrent/Monitor$Guard;)V
    //   22: aload_1
    //   23: getfield 72	com/google/common/util/concurrent/Monitor$Guard:condition	Ljava/util/concurrent/locks/Condition;
    //   26: astore 6
    //   28: lload_2
    //   29: lconst_0
    //   30: lcmp
    //   31: ifgt +10 -> 41
    //   34: aload_0
    //   35: aload_1
    //   36: invokespecial 99	com/google/common/util/concurrent/Monitor:decrementWaiters	(Lcom/google/common/util/concurrent/Monitor$Guard;)V
    //   39: iconst_0
    //   40: ireturn
    //   41: aload 6
    //   43: lload_2
    //   44: invokeinterface 113 3 0
    //   49: lstore 9
    //   51: lload 9
    //   53: lstore_2
    //   54: aload_1
    //   55: invokevirtual 68	com/google/common/util/concurrent/Monitor$Guard:isSatisfied	()Z
    //   58: istore 11
    //   60: iload 11
    //   62: ifeq -34 -> 28
    //   65: aload_0
    //   66: aload_1
    //   67: invokespecial 99	com/google/common/util/concurrent/Monitor:decrementWaiters	(Lcom/google/common/util/concurrent/Monitor$Guard;)V
    //   70: iconst_1
    //   71: ireturn
    //   72: astore 7
    //   74: aload_0
    //   75: aload_1
    //   76: invokespecial 92	com/google/common/util/concurrent/Monitor:signalConditionsOfSatisfiedGuards	(Lcom/google/common/util/concurrent/Monitor$Guard;)V
    //   79: aload 7
    //   81: athrow
    //   82: astore 5
    //   84: aload_0
    //   85: aload_1
    //   86: invokespecial 99	com/google/common/util/concurrent/Monitor:decrementWaiters	(Lcom/google/common/util/concurrent/Monitor$Guard;)V
    //   89: aload 5
    //   91: athrow
    //   92: astore 8
    //   94: invokestatic 105	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   97: invokevirtual 108	java/lang/Thread:interrupt	()V
    //   100: aload 8
    //   102: invokestatic 86	com/google/common/base/Throwables:propagate	(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;
    //   105: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   41	51	72	java/lang/InterruptedException
    //   22	28	82	finally
    //   41	51	82	finally
    //   54	60	82	finally
    //   74	79	82	finally
    //   79	82	82	finally
    //   94	106	82	finally
    //   74	79	92	java/lang/Throwable
  }

  @GuardedBy("lock")
  private void waitUninterruptibly(Guard paramGuard, boolean paramBoolean)
  {
    if (!paramGuard.isSatisfied())
    {
      if (paramBoolean)
        signalConditionsOfSatisfiedGuards(null);
      incrementWaiters(paramGuard);
    }
    try
    {
      Condition localCondition = paramGuard.condition;
      boolean bool;
      do
      {
        localCondition.awaitUninterruptibly();
        bool = paramGuard.isSatisfied();
      }
      while (!bool);
      return;
    }
    finally
    {
      decrementWaiters(paramGuard);
    }
    throw localObject;
  }

  @GuardedBy("lock")
  private boolean waitUninterruptibly(Guard paramGuard, long paramLong, boolean paramBoolean)
  {
    long l1;
    int i;
    if (!paramGuard.isSatisfied())
    {
      l1 = System.nanoTime();
      if (paramBoolean)
        signalConditionsOfSatisfiedGuards(null);
      i = 0;
    }
    try
    {
      incrementWaiters(paramGuard);
      try
      {
        Condition localCondition = paramGuard.condition;
        long l2 = paramLong;
        while (true)
        {
          if (l2 <= 0L)
          {
            decrementWaiters(paramGuard);
            if (i != 0)
              Thread.currentThread().interrupt();
            return false;
          }
          try
          {
            long l3 = localCondition.awaitNanos(l2);
            l2 = l3;
            boolean bool = paramGuard.isSatisfied();
            if (!bool)
              continue;
            decrementWaiters(paramGuard);
            if (i != 0)
              Thread.currentThread().interrupt();
            return true;
          }
          catch (InterruptedException localInterruptedException)
          {
            try
            {
              signalConditionsOfSatisfiedGuards(paramGuard);
              i = 1;
              l2 = paramLong - (System.nanoTime() - l1);
            }
            catch (Throwable localThrowable)
            {
              Thread.currentThread().interrupt();
              throw Throwables.propagate(localThrowable);
            }
          }
        }
      }
      finally
      {
        decrementWaiters(paramGuard);
      }
    }
    finally
    {
      if (i != 0)
        Thread.currentThread().interrupt();
    }
    throw localObject1;
  }

  public void enter()
  {
    this.lock.lock();
  }

  public boolean enter(long paramLong, TimeUnit paramTimeUnit)
  {
    ReentrantLock localReentrantLock = this.lock;
    long l1 = System.nanoTime();
    long l2 = paramTimeUnit.toNanos(paramLong);
    long l3 = l2;
    int i = 0;
    try
    {
      boolean bool = localReentrantLock.tryLock(l3, TimeUnit.NANOSECONDS);
      return bool;
    }
    catch (InterruptedException localInterruptedException)
    {
      while (true)
      {
        i = 1;
        long l4 = System.nanoTime();
        l3 = l2 - (l4 - l1);
      }
    }
    finally
    {
      if (i != 0)
        Thread.currentThread().interrupt();
    }
    throw localObject;
  }

  public boolean enterIf(Guard paramGuard)
  {
    if (paramGuard.monitor != this)
      throw new IllegalMonitorStateException();
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      boolean bool = paramGuard.isSatisfied();
      if (bool)
        return true;
    }
    catch (Throwable localThrowable)
    {
      localReentrantLock.unlock();
      throw Throwables.propagate(localThrowable);
    }
    localReentrantLock.unlock();
    return false;
  }

  public boolean enterIf(Guard paramGuard, long paramLong, TimeUnit paramTimeUnit)
  {
    if (paramGuard.monitor != this)
      throw new IllegalMonitorStateException();
    ReentrantLock localReentrantLock = this.lock;
    if (!enter(paramLong, paramTimeUnit))
      return false;
    try
    {
      boolean bool = paramGuard.isSatisfied();
      if (bool)
        return true;
    }
    catch (Throwable localThrowable)
    {
      localReentrantLock.unlock();
      throw Throwables.propagate(localThrowable);
    }
    localReentrantLock.unlock();
    return false;
  }

  public boolean enterIfInterruptibly(Guard paramGuard)
    throws InterruptedException
  {
    if (paramGuard.monitor != this)
      throw new IllegalMonitorStateException();
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lockInterruptibly();
    try
    {
      boolean bool = paramGuard.isSatisfied();
      if (bool)
        return true;
    }
    catch (Throwable localThrowable)
    {
      localReentrantLock.unlock();
      throw Throwables.propagate(localThrowable);
    }
    localReentrantLock.unlock();
    return false;
  }

  public boolean enterIfInterruptibly(Guard paramGuard, long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    if (paramGuard.monitor != this)
      throw new IllegalMonitorStateException();
    ReentrantLock localReentrantLock = this.lock;
    if (!localReentrantLock.tryLock(paramLong, paramTimeUnit))
      return false;
    try
    {
      boolean bool = paramGuard.isSatisfied();
      if (bool)
        return true;
    }
    catch (Throwable localThrowable)
    {
      localReentrantLock.unlock();
      throw Throwables.propagate(localThrowable);
    }
    localReentrantLock.unlock();
    return false;
  }

  public void enterInterruptibly()
    throws InterruptedException
  {
    this.lock.lockInterruptibly();
  }

  public boolean enterInterruptibly(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    return this.lock.tryLock(paramLong, paramTimeUnit);
  }

  public void enterWhen(Guard paramGuard)
    throws InterruptedException
  {
    if (paramGuard.monitor != this)
      throw new IllegalMonitorStateException();
    ReentrantLock localReentrantLock = this.lock;
    boolean bool = localReentrantLock.isHeldByCurrentThread();
    localReentrantLock.lockInterruptibly();
    try
    {
      waitInterruptibly(paramGuard, bool);
      return;
    }
    catch (Throwable localThrowable)
    {
      localReentrantLock.unlock();
    }
    throw Throwables.propagate(localThrowable);
  }

  public boolean enterWhen(Guard paramGuard, long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    if (paramGuard.monitor != this)
      throw new IllegalMonitorStateException();
    ReentrantLock localReentrantLock = this.lock;
    boolean bool1 = localReentrantLock.isHeldByCurrentThread();
    long l = System.nanoTime();
    if (!localReentrantLock.tryLock(paramLong, paramTimeUnit))
      return false;
    try
    {
      boolean bool2 = waitInterruptibly(paramGuard, paramTimeUnit.toNanos(paramLong) - (System.nanoTime() - l), bool1);
      if (bool2)
        return true;
    }
    catch (Throwable localThrowable)
    {
      localReentrantLock.unlock();
      throw Throwables.propagate(localThrowable);
    }
    localReentrantLock.unlock();
    return false;
  }

  public void enterWhenUninterruptibly(Guard paramGuard)
  {
    if (paramGuard.monitor != this)
      throw new IllegalMonitorStateException();
    ReentrantLock localReentrantLock = this.lock;
    boolean bool = localReentrantLock.isHeldByCurrentThread();
    localReentrantLock.lock();
    try
    {
      waitUninterruptibly(paramGuard, bool);
      return;
    }
    catch (Throwable localThrowable)
    {
      localReentrantLock.unlock();
    }
    throw Throwables.propagate(localThrowable);
  }

  // ERROR //
  public boolean enterWhenUninterruptibly(Guard paramGuard, long paramLong, TimeUnit paramTimeUnit)
  {
    // Byte code:
    //   0: aload_1
    //   1: getfield 145	com/google/common/util/concurrent/Monitor$Guard:monitor	Lcom/google/common/util/concurrent/Monitor;
    //   4: aload_0
    //   5: if_acmpeq +11 -> 16
    //   8: new 147	java/lang/IllegalMonitorStateException
    //   11: dup
    //   12: invokespecial 148	java/lang/IllegalMonitorStateException:<init>	()V
    //   15: athrow
    //   16: aload_0
    //   17: getfield 30	com/google/common/util/concurrent/Monitor:lock	Ljava/util/concurrent/locks/ReentrantLock;
    //   20: astore 5
    //   22: aload 5
    //   24: invokevirtual 163	java/util/concurrent/locks/ReentrantLock:isHeldByCurrentThread	()Z
    //   27: istore 6
    //   29: invokestatic 123	java/lang/System:nanoTime	()J
    //   32: lstore 7
    //   34: aload 4
    //   36: lload_2
    //   37: invokevirtual 132	java/util/concurrent/TimeUnit:toNanos	(J)J
    //   40: lstore 9
    //   42: lload 9
    //   44: lstore 11
    //   46: iconst_0
    //   47: istore 13
    //   49: aload 5
    //   51: lload 11
    //   53: getstatic 136	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
    //   56: invokevirtual 139	java/util/concurrent/locks/ReentrantLock:tryLock	(JLjava/util/concurrent/TimeUnit;)Z
    //   59: istore 19
    //   61: iload 19
    //   63: ifeq +51 -> 114
    //   66: invokestatic 123	java/lang/System:nanoTime	()J
    //   69: lstore 25
    //   71: lload 9
    //   73: lload 25
    //   75: lload 7
    //   77: lsub
    //   78: lsub
    //   79: lstore 27
    //   81: aload_0
    //   82: aload_1
    //   83: lload 27
    //   85: iload 6
    //   87: invokespecial 172	com/google/common/util/concurrent/Monitor:waitUninterruptibly	(Lcom/google/common/util/concurrent/Monitor$Guard;JZ)Z
    //   90: istore 30
    //   92: iload 30
    //   94: ifeq +113 -> 207
    //   97: iconst_1
    //   98: istore 24
    //   100: iload 13
    //   102: ifeq +9 -> 111
    //   105: invokestatic 105	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   108: invokevirtual 108	java/lang/Thread:interrupt	()V
    //   111: iload 24
    //   113: ireturn
    //   114: invokestatic 123	java/lang/System:nanoTime	()J
    //   117: lstore 20
    //   119: lload 9
    //   121: lload 20
    //   123: lload 7
    //   125: lsub
    //   126: lsub
    //   127: pop2
    //   128: iconst_0
    //   129: istore 24
    //   131: iload 13
    //   133: ifeq -22 -> 111
    //   136: invokestatic 105	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   139: invokevirtual 108	java/lang/Thread:interrupt	()V
    //   142: iconst_0
    //   143: ireturn
    //   144: astore 18
    //   146: iconst_1
    //   147: istore 13
    //   149: lload 9
    //   151: invokestatic 123	java/lang/System:nanoTime	()J
    //   154: lload 7
    //   156: lsub
    //   157: lsub
    //   158: lstore 11
    //   160: goto -111 -> 49
    //   163: astore 14
    //   165: lload 9
    //   167: invokestatic 123	java/lang/System:nanoTime	()J
    //   170: lload 7
    //   172: lsub
    //   173: lsub
    //   174: pop2
    //   175: aload 14
    //   177: athrow
    //   178: astore 15
    //   180: iload 13
    //   182: ifeq +9 -> 191
    //   185: invokestatic 105	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   188: invokevirtual 108	java/lang/Thread:interrupt	()V
    //   191: aload 15
    //   193: athrow
    //   194: astore 29
    //   196: aload 5
    //   198: invokevirtual 151	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   201: aload 29
    //   203: invokestatic 86	com/google/common/base/Throwables:propagate	(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;
    //   206: athrow
    //   207: aload 5
    //   209: invokevirtual 151	java/util/concurrent/locks/ReentrantLock:unlock	()V
    //   212: iconst_0
    //   213: istore 24
    //   215: iload 13
    //   217: ifeq -106 -> 111
    //   220: invokestatic 105	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   223: invokevirtual 108	java/lang/Thread:interrupt	()V
    //   226: iconst_0
    //   227: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   49	61	144	java/lang/InterruptedException
    //   49	61	163	finally
    //   66	71	178	finally
    //   81	92	178	finally
    //   114	119	178	finally
    //   149	160	178	finally
    //   165	178	178	finally
    //   196	207	178	finally
    //   207	212	178	finally
    //   81	92	194	java/lang/Throwable
  }

  public int getOccupiedDepth()
  {
    return this.lock.getHoldCount();
  }

  public int getQueueLength()
  {
    return this.lock.getQueueLength();
  }

  public int getWaitQueueLength(Guard paramGuard)
  {
    if (paramGuard.monitor != this)
      throw new IllegalMonitorStateException();
    this.lock.lock();
    try
    {
      int i = paramGuard.waiterCount;
      return i;
    }
    finally
    {
      this.lock.unlock();
    }
    throw localObject;
  }

  public boolean hasQueuedThread(Thread paramThread)
  {
    return this.lock.hasQueuedThread(paramThread);
  }

  public boolean hasQueuedThreads()
  {
    return this.lock.hasQueuedThreads();
  }

  public boolean hasWaiters(Guard paramGuard)
  {
    if (paramGuard.monitor != this)
      throw new IllegalMonitorStateException();
    this.lock.lock();
    try
    {
      int i = paramGuard.waiterCount;
      if (i > 0)
      {
        j = 1;
        return j;
      }
      int j = 0;
    }
    finally
    {
      this.lock.unlock();
    }
  }

  public boolean isFair()
  {
    return this.lock.isFair();
  }

  public boolean isOccupied()
  {
    return this.lock.isLocked();
  }

  public boolean isOccupiedByCurrentThread()
  {
    return this.lock.isHeldByCurrentThread();
  }

  @GuardedBy("lock")
  public void leave()
  {
    ReentrantLock localReentrantLock = this.lock;
    if (!localReentrantLock.isHeldByCurrentThread())
      throw new IllegalMonitorStateException();
    try
    {
      signalConditionsOfSatisfiedGuards(null);
      return;
    }
    finally
    {
      localReentrantLock.unlock();
    }
    throw localObject;
  }

  public void reevaluateGuards()
  {
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      signalConditionsOfSatisfiedGuards(null);
      return;
    }
    finally
    {
      localReentrantLock.unlock();
    }
    throw localObject;
  }

  public boolean tryEnter()
  {
    return this.lock.tryLock();
  }

  public boolean tryEnterIf(Guard paramGuard)
  {
    if (paramGuard.monitor != this)
      throw new IllegalMonitorStateException();
    ReentrantLock localReentrantLock = this.lock;
    if (!localReentrantLock.tryLock())
      return false;
    try
    {
      boolean bool = paramGuard.isSatisfied();
      if (bool)
        return true;
    }
    catch (Throwable localThrowable)
    {
      localReentrantLock.unlock();
      throw Throwables.propagate(localThrowable);
    }
    localReentrantLock.unlock();
    return false;
  }

  @GuardedBy("lock")
  public void waitFor(Guard paramGuard)
    throws InterruptedException
  {
    if (paramGuard.monitor != this)
      throw new IllegalMonitorStateException();
    if (!this.lock.isHeldByCurrentThread())
      throw new IllegalMonitorStateException();
    waitInterruptibly(paramGuard, true);
  }

  @GuardedBy("lock")
  public boolean waitFor(Guard paramGuard, long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    if (paramGuard.monitor != this)
      throw new IllegalMonitorStateException();
    if (!this.lock.isHeldByCurrentThread())
      throw new IllegalMonitorStateException();
    return waitInterruptibly(paramGuard, paramTimeUnit.toNanos(paramLong), true);
  }

  @GuardedBy("lock")
  public void waitForUninterruptibly(Guard paramGuard)
  {
    if (paramGuard.monitor != this)
      throw new IllegalMonitorStateException();
    if (!this.lock.isHeldByCurrentThread())
      throw new IllegalMonitorStateException();
    waitUninterruptibly(paramGuard, true);
  }

  @GuardedBy("lock")
  public boolean waitForUninterruptibly(Guard paramGuard, long paramLong, TimeUnit paramTimeUnit)
  {
    if (paramGuard.monitor != this)
      throw new IllegalMonitorStateException();
    if (!this.lock.isHeldByCurrentThread())
      throw new IllegalMonitorStateException();
    return waitUninterruptibly(paramGuard, paramTimeUnit.toNanos(paramLong), true);
  }

  @Beta
  public static abstract class Guard
  {
    final Condition condition;
    final Monitor monitor;

    @GuardedBy("monitor.lock")
    int waiterCount = 0;

    protected Guard(Monitor paramMonitor)
    {
      this.monitor = ((Monitor)Preconditions.checkNotNull(paramMonitor, "monitor"));
      this.condition = paramMonitor.lock.newCondition();
    }

    public final boolean equals(Object paramObject)
    {
      return this == paramObject;
    }

    public final int hashCode()
    {
      return super.hashCode();
    }

    public abstract boolean isSatisfied();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.util.concurrent.Monitor
 * JD-Core Version:    0.6.0
 */