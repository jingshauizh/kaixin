package com.google.common.eventbus;

import com.google.common.collect.Multimap;

abstract interface HandlerFindingStrategy
{
  public abstract Multimap<Class<?>, EventHandler> findAllHandlers(Object paramObject);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.eventbus.HandlerFindingStrategy
 * JD-Core Version:    0.6.0
 */