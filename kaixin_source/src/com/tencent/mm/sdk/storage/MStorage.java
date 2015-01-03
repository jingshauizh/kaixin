package com.tencent.mm.sdk.storage;

import android.os.Looper;

public abstract class MStorage
{
  private final MStorageEvent<IOnStorageChange, String> bV = new MStorage.1(this);
  private final MStorageEvent<IOnStorageLoaded, String> bW = new MStorage.2(this);

  public void add(IOnStorageChange paramIOnStorageChange)
  {
    this.bV.add(paramIOnStorageChange, Looper.getMainLooper());
  }

  public void addLoadedListener(IOnStorageLoaded paramIOnStorageLoaded)
  {
    this.bW.add(paramIOnStorageLoaded, Looper.getMainLooper());
  }

  public void doNotify()
  {
    this.bV.event("*");
    this.bV.doNotify();
  }

  public void doNotify(String paramString)
  {
    this.bV.event(paramString);
    this.bV.doNotify();
  }

  public void lock()
  {
    this.bV.lock();
  }

  public void remove(IOnStorageChange paramIOnStorageChange)
  {
    this.bV.remove(paramIOnStorageChange);
  }

  public void removeLoadedListener(IOnStorageLoaded paramIOnStorageLoaded)
  {
    this.bW.remove(paramIOnStorageLoaded);
  }

  public void unlock()
  {
    this.bV.unlock();
  }

  public static abstract interface IOnStorageChange
  {
    public abstract void onNotifyChange(String paramString);
  }

  public static abstract interface IOnStorageLoaded
  {
    public abstract void onNotifyLoaded();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.storage.MStorage
 * JD-Core Version:    0.6.0
 */