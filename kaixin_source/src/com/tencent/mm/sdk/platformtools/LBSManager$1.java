package com.tencent.mm.sdk.platformtools;

class LBSManager$1
  implements MTimerHandler.CallBack
{
  public boolean onTimerExpired()
  {
    Log.v("MicroMsg.LBSManager", "get location by GPS failed.");
    this.P.K = true;
    this.P.start();
    LBSManager.a(this.P);
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.LBSManager.1
 * JD-Core Version:    0.6.0
 */