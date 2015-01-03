package com.tencent.mm.sdk.platformtools;

class SyncTask$1
  implements Runnable
{
  public void run()
  {
    SyncTask.a(this.bk, Util.ticksToNow(SyncTask.a(this.bk)));
    this.bk.setResult(this.bk.run());
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.SyncTask.1
 * JD-Core Version:    0.6.0
 */