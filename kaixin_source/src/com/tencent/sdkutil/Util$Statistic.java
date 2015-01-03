package com.tencent.sdkutil;

public class Util$Statistic
{
  public long reqSize;
  public String response;
  public long rspSize;

  public Util$Statistic(String paramString, int paramInt)
  {
    this.response = paramString;
    this.reqSize = paramInt;
    if (this.response != null)
      this.rspSize = this.response.length();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.Util.Statistic
 * JD-Core Version:    0.6.0
 */