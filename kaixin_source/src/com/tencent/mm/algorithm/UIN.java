package com.tencent.mm.algorithm;

public class UIN extends Number
{
  private int h = 0;

  public UIN(int paramInt)
  {
    this.h = paramInt;
  }

  public UIN(long paramLong)
  {
    this.h = (int)(0xFFFFFFFF & paramLong);
  }

  public static int valueOf(String paramString)
  {
    try
    {
      int i = new UIN(Long.valueOf(paramString).longValue()).intValue();
      return i;
    }
    catch (Exception localException)
    {
    }
    return 0;
  }

  public double doubleValue()
  {
    return 0.0D + (0L | this.h);
  }

  public float floatValue()
  {
    return (float)(0.0D + (0L | this.h));
  }

  public int intValue()
  {
    return this.h;
  }

  public long longValue()
  {
    return 0xFFFFFFFF & this.h;
  }

  public String toString()
  {
    return String.valueOf(0xFFFFFFFF & this.h);
  }

  public int value()
  {
    return this.h;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.algorithm.UIN
 * JD-Core Version:    0.6.0
 */