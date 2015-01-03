package org.apache.sanselan.util;

import java.io.UnsupportedEncodingException;
import org.apache.sanselan.common.BinaryConstants;

public abstract class UnicodeUtils
  implements BinaryConstants
{
  public static final int CHAR_ENCODING_CODE_AMBIGUOUS = -1;
  public static final int CHAR_ENCODING_CODE_ISO_8859_1 = 0;
  public static final int CHAR_ENCODING_CODE_UTF_16_BIG_ENDIAN_NO_BOM = 3;
  public static final int CHAR_ENCODING_CODE_UTF_16_BIG_ENDIAN_WITH_BOM = 1;
  public static final int CHAR_ENCODING_CODE_UTF_16_LITTLE_ENDIAN_NO_BOM = 4;
  public static final int CHAR_ENCODING_CODE_UTF_16_LITTLE_ENDIAN_WITH_BOM = 2;
  public static final int CHAR_ENCODING_CODE_UTF_8 = 5;

  private static int findFirstDoubleByteTerminator(byte[] paramArrayOfByte, int paramInt)
  {
    for (int i = paramInt; ; i += 2)
    {
      if (i >= -1 + paramArrayOfByte.length)
        i = -1;
      int j;
      int k;
      do
      {
        return i;
        j = 0xFF & paramArrayOfByte[paramInt];
        k = 0xFF & paramArrayOfByte[(paramInt + 1)];
      }
      while ((j == 0) && (k == 0));
    }
  }

  public static UnicodeUtils getInstance(int paramInt)
    throws UnicodeUtils.UnicodeException
  {
    switch (paramInt)
    {
    default:
      throw new UnicodeException("Unknown char encoding code: " + paramInt);
    case 0:
      return new UnicodeMetricsASCII(null);
    case 5:
      return new UnicodeMetricsUTF8(null);
    case 1:
    case 2:
      return new UnicodeMetricsUTF16WithBOM();
    case 3:
      return new UnicodeMetricsUTF16NoBOM(77);
    case 4:
    }
    return new UnicodeMetricsUTF16NoBOM(73);
  }

  public static final boolean isValidISO_8859_1(String paramString)
  {
    try
    {
      boolean bool = paramString.equals(new String(paramString.getBytes("ISO-8859-1"), "ISO-8859-1"));
      return bool;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    throw new RuntimeException("Error parsing string.", localUnsupportedEncodingException);
  }

  protected abstract int findEnd(byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
    throws UnicodeUtils.UnicodeException;

  public final int findEndWithTerminator(byte[] paramArrayOfByte, int paramInt)
    throws UnicodeUtils.UnicodeException
  {
    return findEnd(paramArrayOfByte, paramInt, true);
  }

  public final int findEndWithoutTerminator(byte[] paramArrayOfByte, int paramInt)
    throws UnicodeUtils.UnicodeException
  {
    return findEnd(paramArrayOfByte, paramInt, false);
  }

  public static class UnicodeException extends Exception
  {
    public UnicodeException(String paramString)
    {
      super();
    }
  }

  private static class UnicodeMetricsASCII extends UnicodeUtils
  {
    private UnicodeMetricsASCII()
    {
      super();
    }

    public int findEnd(byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
      throws UnicodeUtils.UnicodeException
    {
      for (int i = paramInt; ; i++)
      {
        if (i >= paramArrayOfByte.length)
          i = paramArrayOfByte.length;
        while (true)
        {
          return i;
          if (paramArrayOfByte[i] != 0)
            break;
          if (paramBoolean)
            return i + 1;
        }
      }
    }
  }

  private static abstract class UnicodeMetricsUTF16 extends UnicodeUtils
  {
    protected static final int BYTE_ORDER_BIG_ENDIAN = 0;
    protected static final int BYTE_ORDER_LITTLE_ENDIAN = 1;
    protected int byteOrder = 0;

    public UnicodeMetricsUTF16(int paramInt)
    {
      super();
      this.byteOrder = paramInt;
    }

    public int findEnd(byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
      throws UnicodeUtils.UnicodeException
    {
      int m;
      do
      {
        if (paramInt == paramArrayOfByte.length)
          return paramArrayOfByte.length;
        if (paramInt > -1 + paramArrayOfByte.length)
          throw new UnicodeUtils.UnicodeException("Terminator not found.");
        int i = paramInt + 1;
        int j = 0xFF & paramArrayOfByte[paramInt];
        paramInt = i + 1;
        int k = 0xFF & paramArrayOfByte[i];
        if (this.byteOrder == 0)
          m = j;
        while ((j == 0) && (k == 0))
        {
          if (paramBoolean)
          {
            return paramInt;
            m = k;
            continue;
          }
          return paramInt - 2;
        }
      }
      while (m < 216);
      if (paramInt > -1 + paramArrayOfByte.length)
        throw new UnicodeUtils.UnicodeException("Terminator not found.");
      int n = paramInt + 1;
      int i1 = 0xFF & paramArrayOfByte[paramInt];
      paramInt = n + 1;
      int i2 = 0xFF & paramArrayOfByte[n];
      if (this.byteOrder == 0);
      for (int i3 = i1; i3 < 220; i3 = i2)
        throw new UnicodeUtils.UnicodeException("Invalid code point.");
    }

    public boolean isValid(byte[] paramArrayOfByte, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
      throws UnicodeUtils.UnicodeException
    {
      if (paramInt == paramArrayOfByte.length)
        if (!paramBoolean2);
      int m;
      do
      {
        do
        {
          return false;
          return true;
        }
        while (paramInt >= -1 + paramArrayOfByte.length);
        int i = paramInt + 1;
        int j = 0xFF & paramArrayOfByte[paramInt];
        paramInt = i + 1;
        int k = 0xFF & paramArrayOfByte[i];
        if (this.byteOrder == 0);
        for (m = j; (j == 0) && (k == 0); m = k)
          return paramBoolean1;
        if (m < 216)
          break;
      }
      while ((m >= 220) || (paramInt >= -1 + paramArrayOfByte.length));
      int n = paramInt + 1;
      int i1 = 0xFF & paramArrayOfByte[paramInt];
      paramInt = n + 1;
      int i2 = 0xFF & paramArrayOfByte[n];
      if (this.byteOrder == 0);
      for (int i3 = i1; i3 < 220; i3 = i2)
        return false;
    }
  }

  private static class UnicodeMetricsUTF16NoBOM extends UnicodeUtils.UnicodeMetricsUTF16
  {
    public UnicodeMetricsUTF16NoBOM(int paramInt)
    {
      super();
    }
  }

  private static class UnicodeMetricsUTF16WithBOM extends UnicodeUtils.UnicodeMetricsUTF16
  {
    public UnicodeMetricsUTF16WithBOM()
    {
      super();
    }

    public int findEnd(byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
      throws UnicodeUtils.UnicodeException
    {
      if (paramInt >= -1 + paramArrayOfByte.length)
        throw new UnicodeUtils.UnicodeException("Missing BOM.");
      int i = paramInt + 1;
      int j = 0xFF & paramArrayOfByte[paramInt];
      int k = i + 1;
      int m = 0xFF & paramArrayOfByte[i];
      if ((j == 255) && (m == 254));
      for (this.byteOrder = 1; ; this.byteOrder = 0)
      {
        return super.findEnd(paramArrayOfByte, k, paramBoolean);
        if ((j != 254) || (m != 255))
          break;
      }
      throw new UnicodeUtils.UnicodeException("Invalid byte order mark.");
    }
  }

  private static class UnicodeMetricsUTF8 extends UnicodeUtils
  {
    private UnicodeMetricsUTF8()
    {
      super();
    }

    public int findEnd(byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
      throws UnicodeUtils.UnicodeException
    {
      while (true)
      {
        if (paramInt == paramArrayOfByte.length)
          return paramArrayOfByte.length;
        if (paramInt > paramArrayOfByte.length)
          throw new UnicodeUtils.UnicodeException("Terminator not found.");
        int i = paramInt + 1;
        int j = 0xFF & paramArrayOfByte[paramInt];
        if (j == 0)
        {
          if (paramBoolean);
          for (int i8 = i; ; i8 = i - 1)
            return i8;
        }
        if (j <= 127)
        {
          paramInt = i;
          continue;
        }
        if (j <= 223)
        {
          if (i >= paramArrayOfByte.length)
            throw new UnicodeUtils.UnicodeException("Invalid unicode.");
          paramInt = i + 1;
          int i7 = 0xFF & paramArrayOfByte[i];
          if ((i7 >= 128) && (i7 <= 191))
            continue;
          throw new UnicodeUtils.UnicodeException("Invalid code point.");
        }
        int i5;
        if (j <= 239)
        {
          if (i >= -1 + paramArrayOfByte.length)
            throw new UnicodeUtils.UnicodeException("Invalid unicode.");
          int i3 = i + 1;
          int i4 = 0xFF & paramArrayOfByte[i];
          if ((i4 < 128) || (i4 > 191))
            throw new UnicodeUtils.UnicodeException("Invalid code point.");
          i5 = i3 + 1;
          int i6 = 0xFF & paramArrayOfByte[i3];
          if ((i6 < 128) || (i6 > 191))
            throw new UnicodeUtils.UnicodeException("Invalid code point.");
        }
        else
        {
          if (j <= 244)
          {
            if (i >= -2 + paramArrayOfByte.length)
              throw new UnicodeUtils.UnicodeException("Invalid unicode.");
            int k = i + 1;
            int m = 0xFF & paramArrayOfByte[i];
            if ((m < 128) || (m > 191))
              throw new UnicodeUtils.UnicodeException("Invalid code point.");
            int n = k + 1;
            int i1 = 0xFF & paramArrayOfByte[k];
            if ((i1 < 128) || (i1 > 191))
              throw new UnicodeUtils.UnicodeException("Invalid code point.");
            paramInt = n + 1;
            int i2 = 0xFF & paramArrayOfByte[n];
            if ((i2 >= 128) && (i2 <= 191))
              continue;
            throw new UnicodeUtils.UnicodeException("Invalid code point.");
          }
          throw new UnicodeUtils.UnicodeException("Invalid code point.");
        }
        paramInt = i5;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.util.UnicodeUtils
 * JD-Core Version:    0.6.0
 */