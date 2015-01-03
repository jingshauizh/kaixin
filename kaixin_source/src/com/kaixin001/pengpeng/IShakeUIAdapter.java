package com.kaixin001.pengpeng;

import com.kaixin001.pengpeng.data.BumpFriend;

public abstract interface IShakeUIAdapter
{
  public abstract void setBumpTimeout();

  public abstract void setHttpError();

  public abstract void setNotMatched();

  public abstract void setOtherUserCanceled(BumpFriend paramBumpFriend);

  public abstract void setStatusConfirm(BumpFriend paramBumpFriend);

  public abstract void setStatusExchange(BumpFriend paramBumpFriend);

  public abstract void setStatusIdle(int paramInt);

  public abstract void setStatusIntializeing();

  public abstract void setStatusMatching();

  public abstract void setStatusWaiting(BumpFriend paramBumpFriend);

  public abstract void showBumpToast(int paramInt, String paramString);

  public abstract void showBumpToast(String paramString);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.pengpeng.IShakeUIAdapter
 * JD-Core Version:    0.6.0
 */