package com.google.common.eventbus;

import com.google.common.annotations.Beta;

@Beta
public class DeadEvent
{
  private final Object event;
  private final Object source;

  public DeadEvent(Object paramObject1, Object paramObject2)
  {
    this.source = paramObject1;
    this.event = paramObject2;
  }

  public Object getEvent()
  {
    return this.event;
  }

  public Object getSource()
  {
    return this.source;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.eventbus.DeadEvent
 * JD-Core Version:    0.6.0
 */