package com.google.zxing.qrcode.decoder;

public final class Mode
{
  public static final Mode ALPHANUMERIC;
  public static final Mode BYTE;
  public static final Mode ECI;
  public static final Mode FNC1_FIRST_POSITION;
  public static final Mode FNC1_SECOND_POSITION;
  public static final Mode HANZI;
  public static final Mode KANJI;
  public static final Mode NUMERIC;
  public static final Mode STRUCTURED_APPEND;
  public static final Mode TERMINATOR = new Mode(new int[3], 0, "TERMINATOR");
  private final int bits;
  private final int[] characterCountBitsForVersions;
  private final String name;

  static
  {
    NUMERIC = new Mode(new int[] { 10, 12, 14 }, 1, "NUMERIC");
    ALPHANUMERIC = new Mode(new int[] { 9, 11, 13 }, 2, "ALPHANUMERIC");
    STRUCTURED_APPEND = new Mode(new int[3], 3, "STRUCTURED_APPEND");
    BYTE = new Mode(new int[] { 8, 16, 16 }, 4, "BYTE");
    ECI = new Mode(null, 7, "ECI");
    KANJI = new Mode(new int[] { 8, 10, 12 }, 8, "KANJI");
    FNC1_FIRST_POSITION = new Mode(null, 5, "FNC1_FIRST_POSITION");
    FNC1_SECOND_POSITION = new Mode(null, 9, "FNC1_SECOND_POSITION");
    HANZI = new Mode(new int[] { 8, 10, 12 }, 13, "HANZI");
  }

  private Mode(int[] paramArrayOfInt, int paramInt, String paramString)
  {
    this.characterCountBitsForVersions = paramArrayOfInt;
    this.bits = paramInt;
    this.name = paramString;
  }

  public static Mode forBits(int paramInt)
  {
    switch (paramInt)
    {
    case 6:
    case 10:
    case 11:
    case 12:
    default:
      throw new IllegalArgumentException();
    case 0:
      return TERMINATOR;
    case 1:
      return NUMERIC;
    case 2:
      return ALPHANUMERIC;
    case 3:
      return STRUCTURED_APPEND;
    case 4:
      return BYTE;
    case 5:
      return FNC1_FIRST_POSITION;
    case 7:
      return ECI;
    case 8:
      return KANJI;
    case 9:
      return FNC1_SECOND_POSITION;
    case 13:
    }
    return HANZI;
  }

  public int getBits()
  {
    return this.bits;
  }

  public int getCharacterCountBits(Version paramVersion)
  {
    if (this.characterCountBitsForVersions == null)
      throw new IllegalArgumentException("Character count doesn't apply to this mode");
    int i = paramVersion.getVersionNumber();
    int j;
    if (i <= 9)
      j = 0;
    while (true)
    {
      return this.characterCountBitsForVersions[j];
      if (i <= 26)
      {
        j = 1;
        continue;
      }
      j = 2;
    }
  }

  public String getName()
  {
    return this.name;
  }

  public String toString()
  {
    return this.name;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.decoder.Mode
 * JD-Core Version:    0.6.0
 */