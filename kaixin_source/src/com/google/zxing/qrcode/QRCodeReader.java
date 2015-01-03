package com.google.zxing.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.qrcode.decoder.Decoder;
import com.google.zxing.qrcode.detector.Detector;
import java.util.Hashtable;

public class QRCodeReader
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
    int m = arrayOfInt1[0];
    int n = (1 + (arrayOfInt2[0] - m)) / i;
    int i1 = (1 + (k - j)) / i;
    if ((n == 0) || (i1 == 0))
      throw NotFoundException.getNotFoundInstance();
    if (i1 != n)
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

  private static int moduleSize(int[] paramArrayOfInt, BitMatrix paramBitMatrix)
    throws NotFoundException
  {
    int i = paramBitMatrix.getHeight();
    int j = paramBitMatrix.getWidth();
    int k = paramArrayOfInt[0];
    for (int m = paramArrayOfInt[1]; ; m++)
    {
      if ((k >= j) || (m >= i) || (!paramBitMatrix.get(k, m)))
      {
        if ((k != j) && (m != i))
          break;
        throw NotFoundException.getNotFoundInstance();
      }
      k++;
    }
    int n = k - paramArrayOfInt[0];
    if (n == 0)
      throw NotFoundException.getNotFoundInstance();
    return n;
  }

  public Result decode(BinaryBitmap paramBinaryBitmap)
    throws NotFoundException, ChecksumException, FormatException
  {
    return decode(paramBinaryBitmap, null);
  }

  public Result decode(BinaryBitmap paramBinaryBitmap, Hashtable paramHashtable)
    throws NotFoundException, ChecksumException, FormatException
  {
    DecoderResult localDecoderResult;
    if ((paramHashtable != null) && (paramHashtable.containsKey(DecodeHintType.PURE_BARCODE)))
    {
      BitMatrix localBitMatrix = extractPureBits(paramBinaryBitmap.getBlackMatrix());
      localDecoderResult = this.decoder.decode(localBitMatrix, paramHashtable);
    }
    DetectorResult localDetectorResult;
    for (ResultPoint[] arrayOfResultPoint = NO_POINTS; ; arrayOfResultPoint = localDetectorResult.getPoints())
    {
      Result localResult = new Result(localDecoderResult.getText(), localDecoderResult.getRawBytes(), arrayOfResultPoint, BarcodeFormat.QR_CODE);
      if (localDecoderResult.getByteSegments() != null)
        localResult.putMetadata(ResultMetadataType.BYTE_SEGMENTS, localDecoderResult.getByteSegments());
      if (localDecoderResult.getECLevel() != null)
        localResult.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, localDecoderResult.getECLevel().toString());
      return localResult;
      localDetectorResult = new Detector(paramBinaryBitmap.getBlackMatrix()).detect(paramHashtable);
      localDecoderResult = this.decoder.decode(localDetectorResult.getBits(), paramHashtable);
    }
  }

  protected Decoder getDecoder()
  {
    return this.decoder;
  }

  public void reset()
  {
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.QRCodeReader
 * JD-Core Version:    0.6.0
 */