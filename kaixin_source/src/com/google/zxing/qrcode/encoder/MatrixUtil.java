package com.google.zxing.qrcode.encoder;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public final class MatrixUtil
{
  private static final int[][] HORIZONTAL_SEPARATION_PATTERN;
  private static final int[][] POSITION_ADJUSTMENT_PATTERN;
  private static final int[][] POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE;
  private static final int[][] POSITION_DETECTION_PATTERN;
  private static final int[][] TYPE_INFO_COORDINATES;
  private static final int TYPE_INFO_MASK_PATTERN = 21522;
  private static final int TYPE_INFO_POLY = 1335;
  private static final int VERSION_INFO_POLY = 7973;
  private static final int[][] VERTICAL_SEPARATION_PATTERN;

  static
  {
    int[][] arrayOfInt1 = new int[7][];
    arrayOfInt1[0] = { 1, 1, 1, 1, 1, 1, 1 };
    int[] arrayOfInt2 = new int[7];
    arrayOfInt2[0] = 1;
    arrayOfInt2[6] = 1;
    arrayOfInt1[1] = arrayOfInt2;
    int[] arrayOfInt3 = new int[7];
    arrayOfInt3[0] = 1;
    arrayOfInt3[2] = 1;
    arrayOfInt3[3] = 1;
    arrayOfInt3[4] = 1;
    arrayOfInt3[6] = 1;
    arrayOfInt1[2] = arrayOfInt3;
    int[] arrayOfInt4 = new int[7];
    arrayOfInt4[0] = 1;
    arrayOfInt4[2] = 1;
    arrayOfInt4[3] = 1;
    arrayOfInt4[4] = 1;
    arrayOfInt4[6] = 1;
    arrayOfInt1[3] = arrayOfInt4;
    int[] arrayOfInt5 = new int[7];
    arrayOfInt5[0] = 1;
    arrayOfInt5[2] = 1;
    arrayOfInt5[3] = 1;
    arrayOfInt5[4] = 1;
    arrayOfInt5[6] = 1;
    arrayOfInt1[4] = arrayOfInt5;
    int[] arrayOfInt6 = new int[7];
    arrayOfInt6[0] = 1;
    arrayOfInt6[6] = 1;
    arrayOfInt1[5] = arrayOfInt6;
    arrayOfInt1[6] = { 1, 1, 1, 1, 1, 1, 1 };
    POSITION_DETECTION_PATTERN = arrayOfInt1;
    int[][] arrayOfInt7 = new int[1][];
    arrayOfInt7[0] = new int[8];
    HORIZONTAL_SEPARATION_PATTERN = arrayOfInt7;
    int[][] arrayOfInt8 = new int[7][];
    arrayOfInt8[0] = new int[1];
    arrayOfInt8[1] = new int[1];
    arrayOfInt8[2] = new int[1];
    arrayOfInt8[3] = new int[1];
    arrayOfInt8[4] = new int[1];
    arrayOfInt8[5] = new int[1];
    arrayOfInt8[6] = new int[1];
    VERTICAL_SEPARATION_PATTERN = arrayOfInt8;
    int[][] arrayOfInt9 = new int[5][];
    arrayOfInt9[0] = { 1, 1, 1, 1, 1 };
    int[] arrayOfInt10 = new int[5];
    arrayOfInt10[0] = 1;
    arrayOfInt10[4] = 1;
    arrayOfInt9[1] = arrayOfInt10;
    int[] arrayOfInt11 = new int[5];
    arrayOfInt11[0] = 1;
    arrayOfInt11[2] = 1;
    arrayOfInt11[4] = 1;
    arrayOfInt9[2] = arrayOfInt11;
    int[] arrayOfInt12 = new int[5];
    arrayOfInt12[0] = 1;
    arrayOfInt12[4] = 1;
    arrayOfInt9[3] = arrayOfInt12;
    arrayOfInt9[4] = { 1, 1, 1, 1, 1 };
    POSITION_ADJUSTMENT_PATTERN = arrayOfInt9;
    POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE = new int[][] { { -1, -1, -1, -1, -1, -1, -1 }, { 6, 18, -1, -1, -1, -1, -1 }, { 6, 22, -1, -1, -1, -1, -1 }, { 6, 26, -1, -1, -1, -1, -1 }, { 6, 30, -1, -1, -1, -1, -1 }, { 6, 34, -1, -1, -1, -1, -1 }, { 6, 22, 38, -1, -1, -1, -1 }, { 6, 24, 42, -1, -1, -1, -1 }, { 6, 26, 46, -1, -1, -1, -1 }, { 6, 28, 50, -1, -1, -1, -1 }, { 6, 30, 54, -1, -1, -1, -1 }, { 6, 32, 58, -1, -1, -1, -1 }, { 6, 34, 62, -1, -1, -1, -1 }, { 6, 26, 46, 66, -1, -1, -1 }, { 6, 26, 48, 70, -1, -1, -1 }, { 6, 26, 50, 74, -1, -1, -1 }, { 6, 30, 54, 78, -1, -1, -1 }, { 6, 30, 56, 82, -1, -1, -1 }, { 6, 30, 58, 86, -1, -1, -1 }, { 6, 34, 62, 90, -1, -1, -1 }, { 6, 28, 50, 72, 94, -1, -1 }, { 6, 26, 50, 74, 98, -1, -1 }, { 6, 30, 54, 78, 102, -1, -1 }, { 6, 28, 54, 80, 106, -1, -1 }, { 6, 32, 58, 84, 110, -1, -1 }, { 6, 30, 58, 86, 114, -1, -1 }, { 6, 34, 62, 90, 118, -1, -1 }, { 6, 26, 50, 74, 98, 122, -1 }, { 6, 30, 54, 78, 102, 126, -1 }, { 6, 26, 52, 78, 104, 130, -1 }, { 6, 30, 56, 82, 108, 134, -1 }, { 6, 34, 60, 86, 112, 138, -1 }, { 6, 30, 58, 86, 114, 142, -1 }, { 6, 34, 62, 90, 118, 146, -1 }, { 6, 30, 54, 78, 102, 126, 150 }, { 6, 24, 50, 76, 102, 128, 154 }, { 6, 28, 54, 80, 106, 132, 158 }, { 6, 32, 58, 84, 110, 136, 162 }, { 6, 26, 54, 82, 110, 138, 166 }, { 6, 30, 58, 86, 114, 142, 170 } };
    int[][] arrayOfInt13 = new int[15][];
    int[] arrayOfInt14 = new int[2];
    arrayOfInt14[0] = 8;
    arrayOfInt13[0] = arrayOfInt14;
    arrayOfInt13[1] = { 8, 1 };
    arrayOfInt13[2] = { 8, 2 };
    arrayOfInt13[3] = { 8, 3 };
    arrayOfInt13[4] = { 8, 4 };
    arrayOfInt13[5] = { 8, 5 };
    arrayOfInt13[6] = { 8, 7 };
    arrayOfInt13[7] = { 8, 8 };
    arrayOfInt13[8] = { 7, 8 };
    arrayOfInt13[9] = { 5, 8 };
    arrayOfInt13[10] = { 4, 8 };
    arrayOfInt13[11] = { 3, 8 };
    arrayOfInt13[12] = { 2, 8 };
    arrayOfInt13[13] = { 1, 8 };
    int[] arrayOfInt15 = new int[2];
    arrayOfInt15[1] = 8;
    arrayOfInt13[14] = arrayOfInt15;
    TYPE_INFO_COORDINATES = arrayOfInt13;
  }

  public static void buildMatrix(BitArray paramBitArray, ErrorCorrectionLevel paramErrorCorrectionLevel, int paramInt1, int paramInt2, ByteMatrix paramByteMatrix)
    throws WriterException
  {
    clearMatrix(paramByteMatrix);
    embedBasicPatterns(paramInt1, paramByteMatrix);
    embedTypeInfo(paramErrorCorrectionLevel, paramInt2, paramByteMatrix);
    maybeEmbedVersionInfo(paramInt1, paramByteMatrix);
    embedDataBits(paramBitArray, paramInt2, paramByteMatrix);
  }

  public static int calculateBCHCode(int paramInt1, int paramInt2)
  {
    int i = findMSBSet(paramInt2);
    int j = paramInt1 << i - 1;
    while (true)
    {
      if (findMSBSet(j) < i)
        return j;
      j ^= paramInt2 << findMSBSet(j) - i;
    }
  }

  public static void clearMatrix(ByteMatrix paramByteMatrix)
  {
    paramByteMatrix.clear(-1);
  }

  public static void embedBasicPatterns(int paramInt, ByteMatrix paramByteMatrix)
    throws WriterException
  {
    embedPositionDetectionPatternsAndSeparators(paramByteMatrix);
    embedDarkDotAtLeftBottomCorner(paramByteMatrix);
    maybeEmbedPositionAdjustmentPatterns(paramInt, paramByteMatrix);
    embedTimingPatterns(paramByteMatrix);
  }

  private static void embedDarkDotAtLeftBottomCorner(ByteMatrix paramByteMatrix)
    throws WriterException
  {
    if (paramByteMatrix.get(8, -8 + paramByteMatrix.getHeight()) == 0)
      throw new WriterException();
    paramByteMatrix.set(8, -8 + paramByteMatrix.getHeight(), 1);
  }

  public static void embedDataBits(BitArray paramBitArray, int paramInt, ByteMatrix paramByteMatrix)
    throws WriterException
  {
    int i = 0;
    int j = -1;
    int k = -1 + paramByteMatrix.getWidth();
    int m = -1 + paramByteMatrix.getHeight();
    while (true)
      if (k <= 0)
      {
        if (i == paramBitArray.getSize())
          break;
        throw new WriterException("Not all bits consumed: " + i + '/' + paramBitArray.getSize());
      }
      else
      {
        if (k == 6)
          k--;
        if ((m < 0) || (m >= paramByteMatrix.getHeight()))
        {
          j = -j;
          m += j;
          k -= 2;
          continue;
        }
        int i1;
        for (int n = 0; ; n++)
        {
          if (n >= 2)
          {
            m += j;
            break;
          }
          i1 = k - n;
          if (isEmpty(paramByteMatrix.get(i1, m)))
            break label158;
        }
        label158: if (i < paramBitArray.getSize())
        {
          bool = paramBitArray.get(i);
          i++;
          label176: if ((paramInt != -1) && (MaskUtil.getDataMaskBit(paramInt, i1, m)))
            if (!bool)
              break label219;
        }
        label219: for (boolean bool = false; ; bool = true)
        {
          paramByteMatrix.set(i1, m, bool);
          break;
          bool = false;
          break label176;
        }
      }
  }

  private static void embedHorizontalSeparationPattern(int paramInt1, int paramInt2, ByteMatrix paramByteMatrix)
    throws WriterException
  {
    if ((HORIZONTAL_SEPARATION_PATTERN[0].length != 8) || (HORIZONTAL_SEPARATION_PATTERN.length != 1))
      throw new WriterException("Bad horizontal separation pattern");
    for (int i = 0; ; i++)
    {
      if (i >= 8)
        return;
      if (!isEmpty(paramByteMatrix.get(paramInt1 + i, paramInt2)))
        throw new WriterException();
      paramByteMatrix.set(paramInt1 + i, paramInt2, HORIZONTAL_SEPARATION_PATTERN[0][i]);
    }
  }

  private static void embedPositionAdjustmentPattern(int paramInt1, int paramInt2, ByteMatrix paramByteMatrix)
    throws WriterException
  {
    if ((POSITION_ADJUSTMENT_PATTERN[0].length != 5) || (POSITION_ADJUSTMENT_PATTERN.length != 5))
      throw new WriterException("Bad position adjustment");
    int i = 0;
    if (i >= 5)
      return;
    for (int j = 0; ; j++)
    {
      if (j >= 5)
      {
        i++;
        break;
      }
      if (!isEmpty(paramByteMatrix.get(paramInt1 + j, paramInt2 + i)))
        throw new WriterException();
      paramByteMatrix.set(paramInt1 + j, paramInt2 + i, POSITION_ADJUSTMENT_PATTERN[i][j]);
    }
  }

  private static void embedPositionDetectionPattern(int paramInt1, int paramInt2, ByteMatrix paramByteMatrix)
    throws WriterException
  {
    if ((POSITION_DETECTION_PATTERN[0].length != 7) || (POSITION_DETECTION_PATTERN.length != 7))
      throw new WriterException("Bad position detection pattern");
    int i = 0;
    if (i >= 7)
      return;
    for (int j = 0; ; j++)
    {
      if (j >= 7)
      {
        i++;
        break;
      }
      if (!isEmpty(paramByteMatrix.get(paramInt1 + j, paramInt2 + i)))
        throw new WriterException();
      paramByteMatrix.set(paramInt1 + j, paramInt2 + i, POSITION_DETECTION_PATTERN[i][j]);
    }
  }

  private static void embedPositionDetectionPatternsAndSeparators(ByteMatrix paramByteMatrix)
    throws WriterException
  {
    int i = POSITION_DETECTION_PATTERN[0].length;
    embedPositionDetectionPattern(0, 0, paramByteMatrix);
    embedPositionDetectionPattern(paramByteMatrix.getWidth() - i, 0, paramByteMatrix);
    embedPositionDetectionPattern(0, paramByteMatrix.getWidth() - i, paramByteMatrix);
    int j = HORIZONTAL_SEPARATION_PATTERN[0].length;
    embedHorizontalSeparationPattern(0, j - 1, paramByteMatrix);
    embedHorizontalSeparationPattern(paramByteMatrix.getWidth() - j, j - 1, paramByteMatrix);
    embedHorizontalSeparationPattern(0, paramByteMatrix.getWidth() - j, paramByteMatrix);
    int k = VERTICAL_SEPARATION_PATTERN.length;
    embedVerticalSeparationPattern(k, 0, paramByteMatrix);
    embedVerticalSeparationPattern(-1 + (paramByteMatrix.getHeight() - k), 0, paramByteMatrix);
    embedVerticalSeparationPattern(k, paramByteMatrix.getHeight() - k, paramByteMatrix);
  }

  private static void embedTimingPatterns(ByteMatrix paramByteMatrix)
    throws WriterException
  {
    for (int i = 8; ; i++)
    {
      if (i >= -8 + paramByteMatrix.getWidth())
        return;
      int j = (i + 1) % 2;
      if (!isValidValue(paramByteMatrix.get(i, 6)))
        throw new WriterException();
      if (isEmpty(paramByteMatrix.get(i, 6)))
        paramByteMatrix.set(i, 6, j);
      if (!isValidValue(paramByteMatrix.get(6, i)))
        throw new WriterException();
      if (!isEmpty(paramByteMatrix.get(6, i)))
        continue;
      paramByteMatrix.set(6, i, j);
    }
  }

  public static void embedTypeInfo(ErrorCorrectionLevel paramErrorCorrectionLevel, int paramInt, ByteMatrix paramByteMatrix)
    throws WriterException
  {
    BitArray localBitArray = new BitArray();
    makeTypeInfoBits(paramErrorCorrectionLevel, paramInt, localBitArray);
    int i = 0;
    if (i >= localBitArray.getSize())
      return;
    boolean bool = localBitArray.get(-1 + localBitArray.getSize() - i);
    paramByteMatrix.set(TYPE_INFO_COORDINATES[i][0], TYPE_INFO_COORDINATES[i][1], bool);
    if (i < 8)
      paramByteMatrix.set(-1 + (paramByteMatrix.getWidth() - i), 8, bool);
    while (true)
    {
      i++;
      break;
      paramByteMatrix.set(8, -7 + paramByteMatrix.getHeight() + (i - 8), bool);
    }
  }

  private static void embedVerticalSeparationPattern(int paramInt1, int paramInt2, ByteMatrix paramByteMatrix)
    throws WriterException
  {
    if ((VERTICAL_SEPARATION_PATTERN[0].length != 1) || (VERTICAL_SEPARATION_PATTERN.length != 7))
      throw new WriterException("Bad vertical separation pattern");
    for (int i = 0; ; i++)
    {
      if (i >= 7)
        return;
      if (!isEmpty(paramByteMatrix.get(paramInt1, paramInt2 + i)))
        throw new WriterException();
      paramByteMatrix.set(paramInt1, paramInt2 + i, VERTICAL_SEPARATION_PATTERN[i][0]);
    }
  }

  public static int findMSBSet(int paramInt)
  {
    for (int i = 0; ; i++)
    {
      if (paramInt == 0)
        return i;
      paramInt >>>= 1;
    }
  }

  private static boolean isEmpty(int paramInt)
  {
    return paramInt == -1;
  }

  private static boolean isValidValue(int paramInt)
  {
    int i = 1;
    if ((paramInt != -1) && (paramInt != 0) && (paramInt != i))
      i = 0;
    return i;
  }

  public static void makeTypeInfoBits(ErrorCorrectionLevel paramErrorCorrectionLevel, int paramInt, BitArray paramBitArray)
    throws WriterException
  {
    if (!QRCode.isValidMaskPattern(paramInt))
      throw new WriterException("Invalid mask pattern");
    int i = paramInt | paramErrorCorrectionLevel.getBits() << 3;
    paramBitArray.appendBits(i, 5);
    paramBitArray.appendBits(calculateBCHCode(i, 1335), 10);
    BitArray localBitArray = new BitArray();
    localBitArray.appendBits(21522, 15);
    paramBitArray.xor(localBitArray);
    if (paramBitArray.getSize() != 15)
      throw new WriterException("should not happen but we got: " + paramBitArray.getSize());
  }

  public static void makeVersionInfoBits(int paramInt, BitArray paramBitArray)
    throws WriterException
  {
    paramBitArray.appendBits(paramInt, 6);
    paramBitArray.appendBits(calculateBCHCode(paramInt, 7973), 12);
    if (paramBitArray.getSize() != 18)
      throw new WriterException("should not happen but we got: " + paramBitArray.getSize());
  }

  private static void maybeEmbedPositionAdjustmentPatterns(int paramInt, ByteMatrix paramByteMatrix)
    throws WriterException
  {
    if (paramInt < 2);
    int[] arrayOfInt;
    int k;
    int m;
    while (true)
    {
      return;
      int i = paramInt - 1;
      arrayOfInt = POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE[i];
      int j = POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE[i].length;
      for (k = 0; k < j; k++)
      {
        m = 0;
        if (m < j)
          break label50;
      }
    }
    label50: int n = arrayOfInt[k];
    int i1 = arrayOfInt[m];
    if ((i1 == -1) || (n == -1));
    while (true)
    {
      m++;
      break;
      if (!isEmpty(paramByteMatrix.get(i1, n)))
        continue;
      embedPositionAdjustmentPattern(i1 - 2, n - 2, paramByteMatrix);
    }
  }

  public static void maybeEmbedVersionInfo(int paramInt, ByteMatrix paramByteMatrix)
    throws WriterException
  {
    if (paramInt < 7)
      return;
    BitArray localBitArray = new BitArray();
    makeVersionInfoBits(paramInt, localBitArray);
    int i = 17;
    int j = 0;
    label26: if (j < 6);
    for (int k = 0; ; k++)
    {
      if (k >= 3)
      {
        j++;
        break label26;
        break;
      }
      boolean bool = localBitArray.get(i);
      i--;
      paramByteMatrix.set(j, k + (-11 + paramByteMatrix.getHeight()), bool);
      paramByteMatrix.set(k + (-11 + paramByteMatrix.getHeight()), j, bool);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.encoder.MatrixUtil
 * JD-Core Version:    0.6.0
 */