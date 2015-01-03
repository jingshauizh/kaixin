package com.tencent.mm.sdk.event;

public abstract interface IEvent
{
  public static final int EVENT_SCOPE_APPLICATION = 2;
  public static final int EVENT_SCOPE_SESSION = 1;
  public static final int EVENT_SCOPE_TEMPL;

  public abstract String getId();

  public abstract String getScope();

  public abstract boolean isOrder();

  public abstract void oncomplete();
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.event.IEvent
 * JD-Core Version:    0.6.0
 */