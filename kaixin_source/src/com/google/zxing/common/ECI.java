package com.google.zxing.common;

public abstract class ECI
{
  private final int value;

  ECI(int paramInt)
  {
    this.value = paramInt;
  }

  public static ECI getECIByValue(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > 999999))
      throw new IllegalArgumentException("Bad ECI value: " + paramInt);
    if (paramInt < 900)
      return CharacterSetECI.getCharacterSetECIByValue(paramInt);
    return null;
  }

  public int getValue()
  {
    return this.value;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.ECI
 * JD-Core Version:    0.6.0
 */