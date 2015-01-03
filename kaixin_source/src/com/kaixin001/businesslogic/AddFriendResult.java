package com.kaixin001.businesslogic;

public class AddFriendResult
{
  public int code;
  public String fuid;
  public boolean inProgress;
  public String msg;

  public AddFriendResult(String paramString1, int paramInt, String paramString2)
  {
    this.fuid = paramString1;
    this.code = paramInt;
    this.msg = paramString2;
    this.inProgress = false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.businesslogic.AddFriendResult
 * JD-Core Version:    0.6.0
 */