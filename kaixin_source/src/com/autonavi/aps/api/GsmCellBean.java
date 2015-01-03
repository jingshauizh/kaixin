package com.autonavi.aps.api;

public class GsmCellBean
{
  private String a;
  private String b;
  private int c;
  private int d;
  private int e = 10;

  public int getCellid()
  {
    return this.d;
  }

  public int getLac()
  {
    return this.c;
  }

  public String getMcc()
  {
    return this.a;
  }

  public String getMnc()
  {
    return this.b;
  }

  public int getSignal()
  {
    return this.e;
  }

  public void setCellid(int paramInt)
  {
    this.d = paramInt;
  }

  public void setLac(int paramInt)
  {
    this.c = paramInt;
  }

  public void setMcc(String paramString)
  {
    this.a = paramString;
  }

  public void setMnc(String paramString)
  {
    this.b = paramString;
  }

  public void setSignal(int paramInt)
  {
    this.e = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.GsmCellBean
 * JD-Core Version:    0.6.0
 */