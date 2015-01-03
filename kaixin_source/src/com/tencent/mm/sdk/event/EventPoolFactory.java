package com.tencent.mm.sdk.event;

import android.os.Looper;

public final class EventPoolFactory
{
  public static IEventPool impl = null;

  public static final IEventPool getImpl()
  {
    return impl;
  }

  public static final void setImpl(IEventPool paramIEventPool)
  {
    impl = paramIEventPool;
  }

  public static abstract interface IEventPool
  {
    public abstract boolean add(String paramString, IListener paramIListener);

    public abstract void asyncPublish(IEvent paramIEvent);

    public abstract void asyncPublish(IEvent paramIEvent, Looper paramLooper);

    public abstract void publish(IEvent paramIEvent);

    public abstract void release(int paramInt);

    public abstract boolean remove(String paramString, IListener paramIListener);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.event.EventPoolFactory
 * JD-Core Version:    0.6.0
 */