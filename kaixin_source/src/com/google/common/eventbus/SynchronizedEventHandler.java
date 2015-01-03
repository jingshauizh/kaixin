package com.google.common.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class SynchronizedEventHandler extends EventHandler
{
  public SynchronizedEventHandler(Object paramObject, Method paramMethod)
  {
    super(paramObject, paramMethod);
  }

  public void handleEvent(Object paramObject)
    throws InvocationTargetException
  {
    monitorenter;
    try
    {
      super.handleEvent(paramObject);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.eventbus.SynchronizedEventHandler
 * JD-Core Version:    0.6.0
 */