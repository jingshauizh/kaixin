package com.tencent.stat;

abstract interface StatDispatchCallback
{
  public abstract void onDispatchFailure();

  public abstract void onDispatchSuccess();
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.stat.StatDispatchCallback
 * JD-Core Version:    0.6.0
 */