package com.google.zxing.pdf417;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.pdf417.decoder.Decoder;
import com.google.zxing.pdf417.detector.Detector;
import java.util.Hashtable;

public final class PDF417Reader
  implements Reader
{
  private static final ResultPoint[] NO_POINTS = new ResultPoint[0];
  private final Decoder decoder = new Decoder();

  private static BitMatrix extractPureBits(BitMatrix paramBitMatrix)
    throws NotFoundException
  {
    int[] arrayOfInt1 = paramBitMatrix.getTopLeftOnBit();
    int[] arrayOfInt2 = paramBitMatrix.getBottomRightOnBit();
    if ((arrayOfInt1 == null) || (arrayOfInt2 == null))
      throw NotFoundException.getNotFoundInstance();
    int i = moduleSize(arrayOfInt1, paramBitMatrix);
    int j = arrayOfInt1[1];
    int k = arrayOfInt2[1];
    int m = findPatternStart(arrayOfInt1[0], j, paramBitMatrix);
    int n = (1 + (findPatternEnd(arrayOfInt1[0], j, paramBitMatrix) - m)) / i;
    int i1 = (1 + (k - j)) / i;
    if ((n == 0) || (i1 == 0))
      throw NotFoundException.getNotFoundInstance();
    int i2 = i >> 1;
    int i3 = j + i2;
    int i4 = m + i2;
    BitMatrix localBitMatrix = new BitMatrix(n, i1);
    int i5 = 0;
    if (i5 >= i1)
      return localBitMatrix;
    int i6 = i3 + i5 * i;
    for (int i7 = 0; ; i7++)
    {
      if (i7 >= n)
      {
        i5++;
        break;
      }
      if (!paramBitMatrix.get(i4 + i7 * i, i6))
        continue;
      localBitMatrix.set(i7, i5);
    }
  }

  private static int findPatternEnd(int paramInt1, int paramInt2, BitMatrix paramBitMatrix)
    throws NotFoundException
  {
    int i = -1 + paramBitMatrix.getWidth();
    int j;
    if ((i <= paramInt1) || (paramBitMatrix.get(i, paramInt2)))
      j = 0;
    boolean bool2;
    for (boolean bool1 = true; ; bool1 = bool2)
    {
      if ((i <= paramInt1) || (j >= 9))
      {
        if (i != paramInt1)
          break label82;
        throw NotFoundException.getNotFoundInstance();
        i--;
        break;
      }
      i--;
      bool2 = paramBitMatrix.get(i, paramInt2);
      if (bool1 == bool2)
        continue;
      j++;
    }
    label82: return i;
  }

  private static int findPatternStart(int paramInt1, int paramInt2, BitMatrix paramBitMatrix)
    throws NotFoundException
  {
    int i = paramBitMatrix.getWidth();
    int j = paramInt1;
    int k = 0;
    boolean bool2;
    for (boolean bool1 = true; ; bool1 = bool2)
    {
      if ((j >= i - 1) || (k >= 8))
      {
        if (j != i - 1)
          break;
        throw NotFoundException.getNotFoundInstance();
      }
      j++;
      bool2 = paramBitMatrix.get(j, paramInt2);
      if (bool1 == bool2)
        continue;
      k++;
    }
    return j;
  }

  private static int moduleSize(int[] paramArrayOfInt, BitMatrix paramBitMatrix)
    throws NotFoundException
  {
    int i = paramArrayOfInt[0];
    int j = paramArrayOfInt[1];
    int k = paramBitMatrix.getWidth();
    while (true)
    {
      if ((i >= k) || (!paramBitMatrix.get(i, j)))
      {
        if (i != k)
          break;
        throw NotFoundException.getNotFoundInstance();
      }
      i++;
    }
    int m = i - paramArrayOfInt[0] >>> 3;
    if (m == 0)
      throw NotFoundException.getNotFoundInstance();
    return m;
  }

  public Result decode(BinaryBitmap paramBinaryBitmap)
    throws NotFoundException, FormatException
  {
    return decode(paramBinaryBitmap, null);
  }

  public Result decode(BinaryBitmap paramBinaryBitmap, Hashtable paramHashtable)
    throws NotFoundException, FormatException
  {
    DecoderResult localDecoderResult;
    if ((paramHashtable != null) && (paramHashtable.containsKey(DecodeHintType.PURE_BARCODE)))
    {
      BitMatrix localBitMatrix = extractPureBits(paramBinaryBitmap.getBlackMatrix());
      localDecoderResult = this.decoder.decode(localBitMatrix);
    }
    DetectorResult localDetectorResult;
    for (ResultPoint[] arrayOfResultPoint = NO_POINTS; ; arrayOfResultPoint = localDetectorResult.getPoints())
    {
      return new Result(localDecoderResult.getText(), localDecoderResult.getRawBytes(), arrayOfResultPoint, BarcodeFormat.PDF_417);
      localDetectorResult = new Detector(paramBinaryBitmap).detect();
      localDecoderResult = this.decoder.decode(localDetectorResult.getBits());
    }
  }

  public void reset()
  {
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.pdf417.PDF417Reader
 * JD-Core Version:    0.6.0
 */