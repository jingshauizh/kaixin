package com.tencent.mm.sdk.event;

public abstract class IListener
{
  private final int priority;

  public IListener(int paramInt)
  {
    this.priority = paramInt;
  }

  public abstract boolean callback(IEvent paramIEvent);

  public int getPriority()
  {
    return this.priority;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.event.IListener
 * JD-Core Version:    0.6.0
 */