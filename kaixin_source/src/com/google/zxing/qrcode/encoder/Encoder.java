package com.google.zxing.qrcode.encoder;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.ECI;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.decoder.Version.ECBlocks;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Vector;

public final class Encoder
{
  private static final int[] ALPHANUMERIC_TABLE;
  static final String DEFAULT_BYTE_MODE_ENCODING = "ISO-8859-1";

  static
  {
    int[] arrayOfInt = new int[96];
    arrayOfInt[0] = -1;
    arrayOfInt[1] = -1;
    arrayOfInt[2] = -1;
    arrayOfInt[3] = -1;
    arrayOfInt[4] = -1;
    arrayOfInt[5] = -1;
    arrayOfInt[6] = -1;
    arrayOfInt[7] = -1;
    arrayOfInt[8] = -1;
    arrayOfInt[9] = -1;
    arrayOfInt[10] = -1;
    arrayOfInt[11] = -1;
    arrayOfInt[12] = -1;
    arrayOfInt[13] = -1;
    arrayOfInt[14] = -1;
    arrayOfInt[15] = -1;
    arrayOfInt[16] = -1;
    arrayOfInt[17] = -1;
    arrayOfInt[18] = -1;
    arrayOfInt[19] = -1;
    arrayOfInt[20] = -1;
    arrayOfInt[21] = -1;
    arrayOfInt[22] = -1;
    arrayOfInt[23] = -1;
    arrayOfInt[24] = -1;
    arrayOfInt[25] = -1;
    arrayOfInt[26] = -1;
    arrayOfInt[27] = -1;
    arrayOfInt[28] = -1;
    arrayOfInt[29] = -1;
    arrayOfInt[30] = -1;
    arrayOfInt[31] = -1;
    arrayOfInt[32] = 36;
    arrayOfInt[33] = -1;
    arrayOfInt[34] = -1;
    arrayOfInt[35] = -1;
    arrayOfInt[36] = 37;
    arrayOfInt[37] = 38;
    arrayOfInt[38] = -1;
    arrayOfInt[39] = -1;
    arrayOfInt[40] = -1;
    arrayOfInt[41] = -1;
    arrayOfInt[42] = 39;
    arrayOfInt[43] = 40;
    arrayOfInt[44] = -1;
    arrayOfInt[45] = 41;
    arrayOfInt[46] = 42;
    arrayOfInt[47] = 43;
    arrayOfInt[49] = 1;
    arrayOfInt[50] = 2;
    arrayOfInt[51] = 3;
    arrayOfInt[52] = 4;
    arrayOfInt[53] = 5;
    arrayOfInt[54] = 6;
    arrayOfInt[55] = 7;
    arrayOfInt[56] = 8;
    arrayOfInt[57] = 9;
    arrayOfInt[58] = 44;
    arrayOfInt[59] = -1;
    arrayOfInt[60] = -1;
    arrayOfInt[61] = -1;
    arrayOfInt[62] = -1;
    arrayOfInt[63] = -1;
    arrayOfInt[64] = -1;
    arrayOfInt[65] = 10;
    arrayOfInt[66] = 11;
    arrayOfInt[67] = 12;
    arrayOfInt[68] = 13;
    arrayOfInt[69] = 14;
    arrayOfInt[70] = 15;
    arrayOfInt[71] = 16;
    arrayOfInt[72] = 17;
    arrayOfInt[73] = 18;
    arrayOfInt[74] = 19;
    arrayOfInt[75] = 20;
    arrayOfInt[76] = 21;
    arrayOfInt[77] = 22;
    arrayOfInt[78] = 23;
    arrayOfInt[79] = 24;
    arrayOfInt[80] = 25;
    arrayOfInt[81] = 26;
    arrayOfInt[82] = 27;
    arrayOfInt[83] = 28;
    arrayOfInt[84] = 29;
    arrayOfInt[85] = 30;
    arrayOfInt[86] = 31;
    arrayOfInt[87] = 32;
    arrayOfInt[88] = 33;
    arrayOfInt[89] = 34;
    arrayOfInt[90] = 35;
    arrayOfInt[91] = -1;
    arrayOfInt[92] = -1;
    arrayOfInt[93] = -1;
    arrayOfInt[94] = -1;
    arrayOfInt[95] = -1;
    ALPHANUMERIC_TABLE = arrayOfInt;
  }

  static void append8BitBytes(String paramString1, BitArray paramBitArray, String paramString2)
    throws WriterException
  {
    while (true)
    {
      byte[] arrayOfByte;
      int i;
      try
      {
        arrayOfByte = paramString1.getBytes(paramString2);
        i = 0;
        if (i >= arrayOfByte.length)
          return;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        throw new WriterException(localUnsupportedEncodingException.toString());
      }
      paramBitArray.appendBits(arrayOfByte[i], 8);
      i++;
    }
  }

  static void appendAlphanumericBytes(String paramString, BitArray paramBitArray)
    throws WriterException
  {
    int i = paramString.length();
    int j = 0;
    while (true)
    {
      if (j >= i)
        return;
      int k = getAlphanumericCode(paramString.charAt(j));
      if (k == -1)
        throw new WriterException();
      if (j + 1 < i)
      {
        int m = getAlphanumericCode(paramString.charAt(j + 1));
        if (m == -1)
          throw new WriterException();
        paramBitArray.appendBits(m + k * 45, 11);
        j += 2;
        continue;
      }
      paramBitArray.appendBits(k, 6);
      j++;
    }
  }

  static void appendBytes(String paramString1, Mode paramMode, BitArray paramBitArray, String paramString2)
    throws WriterException
  {
    if (paramMode.equals(Mode.NUMERIC))
    {
      appendNumericBytes(paramString1, paramBitArray);
      return;
    }
    if (paramMode.equals(Mode.ALPHANUMERIC))
    {
      appendAlphanumericBytes(paramString1, paramBitArray);
      return;
    }
    if (paramMode.equals(Mode.BYTE))
    {
      append8BitBytes(paramString1, paramBitArray, paramString2);
      return;
    }
    if (paramMode.equals(Mode.KANJI))
    {
      appendKanjiBytes(paramString1, paramBitArray);
      return;
    }
    throw new WriterException("Invalid mode: " + paramMode);
  }

  private static void appendECI(ECI paramECI, BitArray paramBitArray)
  {
    paramBitArray.appendBits(Mode.ECI.getBits(), 4);
    paramBitArray.appendBits(paramECI.getValue(), 8);
  }

  static void appendKanjiBytes(String paramString, BitArray paramBitArray)
    throws WriterException
  {
    while (true)
    {
      byte[] arrayOfByte;
      int j;
      try
      {
        arrayOfByte = paramString.getBytes("Shift_JIS");
        int i = arrayOfByte.length;
        j = 0;
        if (j >= i)
          return;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        throw new WriterException(localUnsupportedEncodingException.toString());
      }
      int k = 0xFF & arrayOfByte[j];
      int m = 0xFF & arrayOfByte[(j + 1)] | k << 8;
      int n = -1;
      if ((m >= 33088) && (m <= 40956));
      for (n = m - 33088; n == -1; n = m - 49472)
      {
        label87: throw new WriterException("Invalid byte sequence");
        if ((m < 57408) || (m > 60351))
          break label87;
      }
      paramBitArray.appendBits(192 * (n >> 8) + (n & 0xFF), 13);
      j += 2;
    }
  }

  static void appendLengthInfo(int paramInt1, int paramInt2, Mode paramMode, BitArray paramBitArray)
    throws WriterException
  {
    int i = paramMode.getCharacterCountBits(Version.getVersionForNumber(paramInt2));
    if (paramInt1 > -1 + (1 << i))
      throw new WriterException(paramInt1 + "is bigger than" + (-1 + (1 << i)));
    paramBitArray.appendBits(paramInt1, i);
  }

  static void appendModeInfo(Mode paramMode, BitArray paramBitArray)
  {
    paramBitArray.appendBits(paramMode.getBits(), 4);
  }

  static void appendNumericBytes(String paramString, BitArray paramBitArray)
  {
    int i = paramString.length();
    int j = 0;
    while (true)
    {
      if (j >= i)
        return;
      int k = '￐' + paramString.charAt(j);
      if (j + 2 < i)
      {
        int m = '￐' + paramString.charAt(j + 1);
        paramBitArray.appendBits('￐' + paramString.charAt(j + 2) + (k * 100 + m * 10), 10);
        j += 3;
        continue;
      }
      if (j + 1 < i)
      {
        paramBitArray.appendBits('￐' + paramString.charAt(j + 1) + k * 10, 7);
        j += 2;
        continue;
      }
      paramBitArray.appendBits(k, 4);
      j++;
    }
  }

  private static int calculateMaskPenalty(ByteMatrix paramByteMatrix)
  {
    return 0 + MaskUtil.applyMaskPenaltyRule1(paramByteMatrix) + MaskUtil.applyMaskPenaltyRule2(paramByteMatrix) + MaskUtil.applyMaskPenaltyRule3(paramByteMatrix) + MaskUtil.applyMaskPenaltyRule4(paramByteMatrix);
  }

  private static int chooseMaskPattern(BitArray paramBitArray, ErrorCorrectionLevel paramErrorCorrectionLevel, int paramInt, ByteMatrix paramByteMatrix)
    throws WriterException
  {
    int i = 2147483647;
    int j = -1;
    for (int k = 0; ; k++)
    {
      if (k >= 8)
        return j;
      MatrixUtil.buildMatrix(paramBitArray, paramErrorCorrectionLevel, paramInt, k, paramByteMatrix);
      int m = calculateMaskPenalty(paramByteMatrix);
      if (m >= i)
        continue;
      i = m;
      j = k;
    }
  }

  public static Mode chooseMode(String paramString)
  {
    return chooseMode(paramString, null);
  }

  public static Mode chooseMode(String paramString1, String paramString2)
  {
    if ("Shift_JIS".equals(paramString2))
    {
      if (isOnlyDoubleByteKanji(paramString1))
        return Mode.KANJI;
      return Mode.BYTE;
    }
    int i = 0;
    int j = 0;
    int k = 0;
    if (k >= paramString1.length())
    {
      if (j != 0)
        return Mode.ALPHANUMERIC;
    }
    else
    {
      int m = paramString1.charAt(k);
      if ((m >= 48) && (m <= 57))
        i = 1;
      while (true)
      {
        k++;
        break;
        if (getAlphanumericCode(m) == -1)
          break label92;
        j = 1;
      }
      label92: return Mode.BYTE;
    }
    if (i != 0)
      return Mode.NUMERIC;
    return Mode.BYTE;
  }

  public static void encode(String paramString, ErrorCorrectionLevel paramErrorCorrectionLevel, QRCode paramQRCode)
    throws WriterException
  {
    encode(paramString, paramErrorCorrectionLevel, null, paramQRCode);
  }

  public static void encode(String paramString, ErrorCorrectionLevel paramErrorCorrectionLevel, Hashtable paramHashtable, QRCode paramQRCode)
    throws WriterException
  {
    String str;
    Mode localMode;
    BitArray localBitArray1;
    BitArray localBitArray2;
    if (paramHashtable == null)
    {
      str = null;
      if (str == null)
        str = "ISO-8859-1";
      localMode = chooseMode(paramString, str);
      localBitArray1 = new BitArray();
      appendBytes(paramString, localMode, localBitArray1, str);
      initQRCode(localBitArray1.getSizeInBytes(), paramErrorCorrectionLevel, localMode, paramQRCode);
      localBitArray2 = new BitArray();
      if ((localMode == Mode.BYTE) && (!"ISO-8859-1".equals(str)))
      {
        CharacterSetECI localCharacterSetECI = CharacterSetECI.getCharacterSetECIByName(str);
        if (localCharacterSetECI != null)
          appendECI(localCharacterSetECI, localBitArray2);
      }
      appendModeInfo(localMode, localBitArray2);
      if (!localMode.equals(Mode.BYTE))
        break label294;
    }
    label294: for (int i = localBitArray1.getSizeInBytes(); ; i = paramString.length())
    {
      appendLengthInfo(i, paramQRCode.getVersion(), localMode, localBitArray2);
      localBitArray2.appendBitArray(localBitArray1);
      terminateBits(paramQRCode.getNumDataBytes(), localBitArray2);
      BitArray localBitArray3 = new BitArray();
      interleaveWithECBytes(localBitArray2, paramQRCode.getNumTotalBytes(), paramQRCode.getNumDataBytes(), paramQRCode.getNumRSBlocks(), localBitArray3);
      ByteMatrix localByteMatrix = new ByteMatrix(paramQRCode.getMatrixWidth(), paramQRCode.getMatrixWidth());
      paramQRCode.setMaskPattern(chooseMaskPattern(localBitArray3, paramQRCode.getECLevel(), paramQRCode.getVersion(), localByteMatrix));
      MatrixUtil.buildMatrix(localBitArray3, paramQRCode.getECLevel(), paramQRCode.getVersion(), paramQRCode.getMaskPattern(), localByteMatrix);
      paramQRCode.setMatrix(localByteMatrix);
      if (paramQRCode.isValid())
        return;
      throw new WriterException("Invalid QR code: " + paramQRCode.toString());
      str = (String)paramHashtable.get(EncodeHintType.CHARACTER_SET);
      break;
    }
  }

  static byte[] generateECBytes(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramArrayOfByte.length;
    int[] arrayOfInt = new int[i + paramInt];
    int j = 0;
    byte[] arrayOfByte;
    if (j >= i)
    {
      new ReedSolomonEncoder(GenericGF.QR_CODE_FIELD_256).encode(arrayOfInt, paramInt);
      arrayOfByte = new byte[paramInt];
    }
    for (int k = 0; ; k++)
    {
      if (k >= paramInt)
      {
        return arrayOfByte;
        arrayOfInt[j] = (0xFF & paramArrayOfByte[j]);
        j++;
        break;
      }
      arrayOfByte[k] = (byte)arrayOfInt[(i + k)];
    }
  }

  static int getAlphanumericCode(int paramInt)
  {
    if (paramInt < ALPHANUMERIC_TABLE.length)
      return ALPHANUMERIC_TABLE[paramInt];
    return -1;
  }

  static void getNumDataBytesAndNumECBytesForBlockID(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
    throws WriterException
  {
    if (paramInt4 >= paramInt3)
      throw new WriterException("Block ID too large");
    int i = paramInt1 % paramInt3;
    int j = paramInt3 - i;
    int k = paramInt1 / paramInt3;
    int m = k + 1;
    int n = paramInt2 / paramInt3;
    int i1 = n + 1;
    int i2 = k - n;
    int i3 = m - i1;
    if (i2 != i3)
      throw new WriterException("EC bytes mismatch");
    if (paramInt3 != j + i)
      throw new WriterException("RS blocks mismatch");
    if (paramInt1 != j * (n + i2) + i * (i1 + i3))
      throw new WriterException("Total bytes mismatch");
    if (paramInt4 < j)
    {
      paramArrayOfInt1[0] = n;
      paramArrayOfInt2[0] = i2;
      return;
    }
    paramArrayOfInt1[0] = i1;
    paramArrayOfInt2[0] = i3;
  }

  private static void initQRCode(int paramInt, ErrorCorrectionLevel paramErrorCorrectionLevel, Mode paramMode, QRCode paramQRCode)
    throws WriterException
  {
    paramQRCode.setECLevel(paramErrorCorrectionLevel);
    paramQRCode.setMode(paramMode);
    for (int i = 1; ; i++)
    {
      if (i > 40)
        throw new WriterException("Cannot find proper rs block info (input data too big?)");
      Version localVersion = Version.getVersionForNumber(i);
      int j = localVersion.getTotalCodewords();
      Version.ECBlocks localECBlocks = localVersion.getECBlocksForLevel(paramErrorCorrectionLevel);
      int k = localECBlocks.getTotalECCodewords();
      int m = localECBlocks.getNumBlocks();
      int n = j - k;
      if (n < paramInt + 3)
        continue;
      paramQRCode.setVersion(i);
      paramQRCode.setNumTotalBytes(j);
      paramQRCode.setNumDataBytes(n);
      paramQRCode.setNumRSBlocks(m);
      paramQRCode.setNumECBytes(k);
      paramQRCode.setMatrixWidth(localVersion.getDimensionForVersion());
      return;
    }
  }

  static void interleaveWithECBytes(BitArray paramBitArray1, int paramInt1, int paramInt2, int paramInt3, BitArray paramBitArray2)
    throws WriterException
  {
    if (paramBitArray1.getSizeInBytes() != paramInt2)
      throw new WriterException("Number of bits and data bytes does not match");
    int i = 0;
    int j = 0;
    int k = 0;
    Vector localVector = new Vector(paramInt3);
    for (int m = 0; ; m++)
    {
      if (m >= paramInt3)
      {
        if (paramInt2 == i)
          break;
        throw new WriterException("Data bytes does not match offset");
      }
      int[] arrayOfInt1 = new int[1];
      int[] arrayOfInt2 = new int[1];
      getNumDataBytesAndNumECBytesForBlockID(paramInt1, paramInt2, paramInt3, m, arrayOfInt1, arrayOfInt2);
      int n = arrayOfInt1[0];
      byte[] arrayOfByte1 = new byte[n];
      paramBitArray1.toBytes(i * 8, arrayOfByte1, 0, n);
      byte[] arrayOfByte2 = generateECBytes(arrayOfByte1, arrayOfInt2[0]);
      localVector.addElement(new BlockPair(arrayOfByte1, arrayOfByte2));
      j = Math.max(j, n);
      k = Math.max(k, arrayOfByte2.length);
      i += arrayOfInt1[0];
    }
    int i1 = 0;
    int i3;
    if (i1 >= j)
    {
      i3 = 0;
      if (i3 >= k)
      {
        if (paramInt1 == paramBitArray2.getSizeInBytes())
          return;
        throw new WriterException("Interleaving error: " + paramInt1 + " and " + paramBitArray2.getSizeInBytes() + " differ.");
      }
    }
    else
    {
      for (int i2 = 0; ; i2++)
      {
        if (i2 >= localVector.size())
        {
          i1++;
          break;
        }
        byte[] arrayOfByte3 = ((BlockPair)localVector.elementAt(i2)).getDataBytes();
        if (i1 >= arrayOfByte3.length)
          continue;
        paramBitArray2.appendBits(arrayOfByte3[i1], 8);
      }
    }
    for (int i4 = 0; ; i4++)
    {
      if (i4 >= localVector.size())
      {
        i3++;
        break;
      }
      byte[] arrayOfByte4 = ((BlockPair)localVector.elementAt(i4)).getErrorCorrectionBytes();
      if (i3 >= arrayOfByte4.length)
        continue;
      paramBitArray2.appendBits(arrayOfByte4[i3], 8);
    }
  }

  private static boolean isOnlyDoubleByteKanji(String paramString)
  {
    byte[] arrayOfByte;
    int i;
    try
    {
      arrayOfByte = paramString.getBytes("Shift_JIS");
      i = arrayOfByte.length;
      if (i % 2 != 0)
        return false;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      return false;
    }
    for (int j = 0; ; j += 2)
    {
      if (j >= i)
        return true;
      int k = 0xFF & arrayOfByte[j];
      if (((k < 129) || (k > 159)) && ((k < 224) || (k > 235)))
        break;
    }
  }

  static void terminateBits(int paramInt, BitArray paramBitArray)
    throws WriterException
  {
    int i = paramInt << 3;
    if (paramBitArray.getSize() > i)
      throw new WriterException("data bits cannot fit in the QR Code" + paramBitArray.getSize() + " > " + i);
    int j = 0;
    int k;
    if ((j >= 4) || (paramBitArray.getSize() >= i))
    {
      k = 0x7 & paramBitArray.getSize();
      if (k <= 0);
    }
    int n;
    for (int i2 = k; ; i2++)
    {
      if (i2 >= 8)
      {
        int m = paramInt - paramBitArray.getSizeInBytes();
        n = 0;
        if (n < m)
          break label149;
        if (paramBitArray.getSize() == i)
          return;
        throw new WriterException("Bits size does not equal capacity");
        paramBitArray.appendBit(false);
        j++;
        break;
      }
      paramBitArray.appendBit(false);
    }
    label149: if ((n & 0x1) == 0);
    for (int i1 = 236; ; i1 = 17)
    {
      paramBitArray.appendBits(i1, 8);
      n++;
      break;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.encoder.Encoder
 * JD-Core Version:    0.6.0
 */