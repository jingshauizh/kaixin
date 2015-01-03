package com.google.zxing.qrcode.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Vector;

final class DecodedBitStreamParser
{
  private static final char[] ALPHANUMERIC_CHARS = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 32, 36, 37, 42, 43, 45, 46, 47, 58 };
  private static final int GB2312_SUBSET = 1;

  static DecoderResult decode(byte[] paramArrayOfByte, Version paramVersion, ErrorCorrectionLevel paramErrorCorrectionLevel, Hashtable paramHashtable)
    throws FormatException
  {
    BitSource localBitSource = new BitSource(paramArrayOfByte);
    StringBuffer localStringBuffer = new StringBuffer(50);
    CharacterSetECI localCharacterSetECI = null;
    boolean bool = false;
    Vector localVector = new Vector(1);
    Object localObject;
    label87: String str1;
    if (localBitSource.available() < 4)
    {
      localObject = Mode.TERMINATOR;
      if (!localObject.equals(Mode.TERMINATOR))
      {
        if ((!localObject.equals(Mode.FNC1_FIRST_POSITION)) && (!localObject.equals(Mode.FNC1_SECOND_POSITION)))
          break label162;
        bool = true;
      }
      if (!localObject.equals(Mode.TERMINATOR))
        break label374;
      str1 = localStringBuffer.toString();
      if (localVector.isEmpty())
        localVector = null;
      if (paramErrorCorrectionLevel != null)
        break label380;
    }
    label162: label374: label380: for (String str2 = null; ; str2 = paramErrorCorrectionLevel.toString())
    {
      while (true)
      {
        return new DecoderResult(paramArrayOfByte, str1, localVector, str2);
        try
        {
          Mode localMode = Mode.forBits(localBitSource.readBits(4));
          localObject = localMode;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          throw FormatException.getFormatInstance();
        }
      }
      if (localObject.equals(Mode.STRUCTURED_APPEND))
      {
        localBitSource.readBits(16);
        break label87;
      }
      if (localObject.equals(Mode.ECI))
      {
        localCharacterSetECI = CharacterSetECI.getCharacterSetECIByValue(parseECIValue(localBitSource));
        if (localCharacterSetECI != null)
          break label87;
        throw FormatException.getFormatInstance();
      }
      if (localObject.equals(Mode.HANZI))
      {
        int j = localBitSource.readBits(4);
        int k = localBitSource.readBits(((Mode)localObject).getCharacterCountBits(paramVersion));
        if (j != 1)
          break label87;
        decodeHanziSegment(localBitSource, localStringBuffer, k);
        break label87;
      }
      int i = localBitSource.readBits(((Mode)localObject).getCharacterCountBits(paramVersion));
      if (localObject.equals(Mode.NUMERIC))
      {
        decodeNumericSegment(localBitSource, localStringBuffer, i);
        break label87;
      }
      if (localObject.equals(Mode.ALPHANUMERIC))
      {
        decodeAlphanumericSegment(localBitSource, localStringBuffer, i, bool);
        break label87;
      }
      if (localObject.equals(Mode.BYTE))
      {
        decodeByteSegment(localBitSource, localStringBuffer, i, localCharacterSetECI, localVector, paramHashtable);
        break label87;
      }
      if (localObject.equals(Mode.KANJI))
      {
        decodeKanjiSegment(localBitSource, localStringBuffer, i);
        break label87;
        break;
      }
      throw FormatException.getFormatInstance();
    }
  }

  private static void decodeAlphanumericSegment(BitSource paramBitSource, StringBuffer paramStringBuffer, int paramInt, boolean paramBoolean)
    throws FormatException
  {
    int i = paramStringBuffer.length();
    int k;
    while (true)
    {
      if (paramInt <= 1)
      {
        if (paramInt == 1)
          paramStringBuffer.append(toAlphaNumericChar(paramBitSource.readBits(6)));
        if (paramBoolean)
        {
          k = i;
          if (k < paramStringBuffer.length())
            break;
        }
        return;
      }
      int j = paramBitSource.readBits(11);
      paramStringBuffer.append(toAlphaNumericChar(j / 45));
      paramStringBuffer.append(toAlphaNumericChar(j % 45));
      paramInt -= 2;
    }
    if (paramStringBuffer.charAt(k) == '%')
    {
      if ((k >= -1 + paramStringBuffer.length()) || (paramStringBuffer.charAt(k + 1) != '%'))
        break label138;
      paramStringBuffer.deleteCharAt(k + 1);
    }
    while (true)
    {
      k++;
      break;
      label138: paramStringBuffer.setCharAt(k, '\035');
    }
  }

  private static void decodeByteSegment(BitSource paramBitSource, StringBuffer paramStringBuffer, int paramInt, CharacterSetECI paramCharacterSetECI, Vector paramVector, Hashtable paramHashtable)
    throws FormatException
  {
    if (paramInt << 3 > paramBitSource.available())
      throw FormatException.getFormatInstance();
    byte[] arrayOfByte = new byte[paramInt];
    int i = 0;
    while (true)
    {
      String str;
      if (i >= paramInt)
      {
        if (paramCharacterSetECI != null)
          break label83;
        str = StringUtils.guessEncoding(arrayOfByte, paramHashtable);
      }
      try
      {
        while (true)
        {
          paramStringBuffer.append(new String(arrayOfByte, str));
          paramVector.addElement(arrayOfByte);
          return;
          arrayOfByte[i] = (byte)paramBitSource.readBits(8);
          i++;
          break;
          label83: str = paramCharacterSetECI.getEncodingName();
        }
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
      }
    }
    throw FormatException.getFormatInstance();
  }

  private static void decodeHanziSegment(BitSource paramBitSource, StringBuffer paramStringBuffer, int paramInt)
    throws FormatException
  {
    if (paramInt * 13 > paramBitSource.available())
      throw FormatException.getFormatInstance();
    byte[] arrayOfByte = new byte[paramInt * 2];
    int i = 0;
    while (true)
    {
      if (paramInt <= 0);
      try
      {
        paramStringBuffer.append(new String(arrayOfByte, "GB2312"));
        return;
        int j = paramBitSource.readBits(13);
        int k = j / 96 << 8 | j % 96;
        if (k < 959);
        for (int m = k + 41377; ; m = k + 42657)
        {
          arrayOfByte[i] = (byte)(0xFF & m >> 8);
          arrayOfByte[(i + 1)] = (byte)(m & 0xFF);
          i += 2;
          paramInt--;
          break;
        }
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
      }
    }
    throw FormatException.getFormatInstance();
  }

  private static void decodeKanjiSegment(BitSource paramBitSource, StringBuffer paramStringBuffer, int paramInt)
    throws FormatException
  {
    if (paramInt * 13 > paramBitSource.available())
      throw FormatException.getFormatInstance();
    byte[] arrayOfByte = new byte[paramInt * 2];
    int i = 0;
    while (true)
    {
      if (paramInt <= 0);
      try
      {
        paramStringBuffer.append(new String(arrayOfByte, "SJIS"));
        return;
        int j = paramBitSource.readBits(13);
        int k = j / 192 << 8 | j % 192;
        if (k < 7936);
        for (int m = k + 33088; ; m = k + 49472)
        {
          arrayOfByte[i] = (byte)(m >> 8);
          arrayOfByte[(i + 1)] = (byte)m;
          i += 2;
          paramInt--;
          break;
        }
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
      }
    }
    throw FormatException.getFormatInstance();
  }

  private static void decodeNumericSegment(BitSource paramBitSource, StringBuffer paramStringBuffer, int paramInt)
    throws FormatException
  {
    while (true)
    {
      int k;
      if (paramInt < 3)
      {
        if (paramInt != 2)
          break;
        k = paramBitSource.readBits(7);
        if (k >= 100)
          throw FormatException.getFormatInstance();
      }
      else
      {
        int i = paramBitSource.readBits(10);
        if (i >= 1000)
          throw FormatException.getFormatInstance();
        paramStringBuffer.append(toAlphaNumericChar(i / 100));
        paramStringBuffer.append(toAlphaNumericChar(i / 10 % 10));
        paramStringBuffer.append(toAlphaNumericChar(i % 10));
        paramInt -= 3;
        continue;
      }
      paramStringBuffer.append(toAlphaNumericChar(k / 10));
      paramStringBuffer.append(toAlphaNumericChar(k % 10));
    }
    do
      return;
    while (paramInt != 1);
    int j = paramBitSource.readBits(4);
    if (j >= 10)
      throw FormatException.getFormatInstance();
    paramStringBuffer.append(toAlphaNumericChar(j));
  }

  private static int parseECIValue(BitSource paramBitSource)
  {
    int i = paramBitSource.readBits(8);
    if ((i & 0x80) == 0)
      return i & 0x7F;
    if ((i & 0xC0) == 128)
      return paramBitSource.readBits(8) | (i & 0x3F) << 8;
    if ((i & 0xE0) == 192)
      return paramBitSource.readBits(16) | (i & 0x1F) << 16;
    throw new IllegalArgumentException("Bad ECI bits starting with byte " + i);
  }

  private static char toAlphaNumericChar(int paramInt)
    throws FormatException
  {
    if (paramInt >= ALPHANUMERIC_CHARS.length)
      throw FormatException.getFormatInstance();
    return ALPHANUMERIC_CHARS[paramInt];
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.decoder.DecodedBitStreamParser
 * JD-Core Version:    0.6.0
 */