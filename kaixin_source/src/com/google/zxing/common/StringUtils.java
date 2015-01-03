package com.google.zxing.common;

import com.google.zxing.DecodeHintType;
import java.util.Hashtable;

public final class StringUtils
{
  private static final boolean ASSUME_SHIFT_JIS = false;
  private static final String EUC_JP = "EUC_JP";
  public static final String GB2312 = "GB2312";
  private static final String ISO88591 = "ISO8859_1";
  private static final String PLATFORM_DEFAULT_ENCODING = System.getProperty("file.encoding");
  public static final String SHIFT_JIS = "SJIS";
  private static final String UTF8 = "UTF8";

  static
  {
    if ((!"SJIS".equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING)) && (!"EUC_JP".equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING)));
    for (boolean bool = false; ; bool = true)
    {
      ASSUME_SHIFT_JIS = bool;
      return;
    }
  }

  public static String guessEncoding(byte[] paramArrayOfByte, Hashtable paramHashtable)
  {
    if (paramHashtable != null)
    {
      String str = (String)paramHashtable.get(DecodeHintType.CHARACTER_SET);
      if (str != null)
        return str;
    }
    if ((paramArrayOfByte.length > 3) && (paramArrayOfByte[0] == -17) && (paramArrayOfByte[1] == -69) && (paramArrayOfByte[2] == -65))
      return "UTF8";
    int i = paramArrayOfByte.length;
    int j = 0;
    int k = 1;
    int m = 1;
    int n = 1;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    int i6 = 0;
    int i7 = 0;
    if ((i7 >= i) || ((k == 0) && (m == 0) && (n == 0)))
    {
      if (i1 > 0)
        n = 0;
      if ((m != 0) && (ASSUME_SHIFT_JIS))
        return "SJIS";
    }
    else
    {
      int i8 = 0xFF & paramArrayOfByte[i7];
      if ((i8 >= 128) && (i8 <= 191))
      {
        if (i1 > 0)
          i1--;
        label169: if ((i8 >= 161) || (i8 <= 254))
          j = 1;
        if (((i8 == 194) || (i8 == 195)) && (i7 < i - 1))
        {
          int i11 = 0xFF & paramArrayOfByte[(i7 + 1)];
          if ((i11 <= 191) && (((i8 == 194) && (i11 >= 160)) || ((i8 == 195) && (i11 >= 128))))
            i4 = 1;
        }
        if ((i8 >= 127) && (i8 <= 159))
          k = 0;
        if ((i8 >= 161) && (i8 <= 223) && (i6 == 0))
          i3++;
        if ((i6 == 0) && (((i8 >= 240) && (i8 <= 255)) || (i8 == 128) || (i8 == 160)))
          m = 0;
        if (((i8 < 129) || (i8 > 159)) && ((i8 < 224) || (i8 > 239)))
          break label502;
        if (i6 == 0)
          break label445;
        i6 = 0;
      }
      while (true)
      {
        i7++;
        break;
        if (i1 > 0)
          n = 0;
        if ((i8 < 192) || (i8 > 253))
          break label169;
        i5 = 1;
        int i9 = i8;
        while ((i9 & 0x40) != 0)
        {
          i1++;
          i9 <<= 1;
        }
        break label169;
        label445: i6 = 1;
        if (i7 >= -1 + paramArrayOfByte.length)
        {
          m = 0;
          continue;
        }
        int i10 = 0xFF & paramArrayOfByte[(i7 + 1)];
        if ((i10 < 64) || (i10 > 252))
        {
          m = 0;
          continue;
        }
        i2++;
        continue;
        label502: i6 = 0;
      }
    }
    if ((n != 0) && (i5 != 0))
      return "UTF8";
    if (j != 0)
      return "GB2312";
    if ((m != 0) && ((i2 >= 3) || (i3 * 20 > i)))
      return "SJIS";
    if ((i4 == 0) && (k != 0))
      return "ISO8859_1";
    return PLATFORM_DEFAULT_ENCODING;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.StringUtils
 * JD-Core Version:    0.6.0
 */