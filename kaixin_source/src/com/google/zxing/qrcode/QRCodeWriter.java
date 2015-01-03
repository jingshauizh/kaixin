package com.google.zxing.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import java.util.Hashtable;

public final class QRCodeWriter
  implements Writer
{
  private static final int QUIET_ZONE_SIZE = 4;

  private static BitMatrix renderResult(QRCode paramQRCode, int paramInt1, int paramInt2)
  {
    ByteMatrix localByteMatrix = paramQRCode.getMatrix();
    int i = localByteMatrix.getWidth();
    int j = localByteMatrix.getHeight();
    int k = i + 8;
    int m = j + 8;
    int n = Math.max(paramInt1, k);
    int i1 = Math.max(paramInt2, m);
    int i2 = Math.min(n / k, i1 / m);
    int i3 = (n - i * i2) / 2;
    int i4 = (i1 - j * i2) / 2;
    BitMatrix localBitMatrix = new BitMatrix(n, i1);
    int i5 = 0;
    int i6 = i4;
    if (i5 >= j)
      return localBitMatrix;
    int i7 = 0;
    int i8 = i3;
    while (true)
    {
      if (i7 >= i)
      {
        i5++;
        i6 += i2;
        break;
      }
      if (localByteMatrix.get(i7, i5) == 1)
        localBitMatrix.setRegion(i8, i6, i2, i2);
      i7++;
      i8 += i2;
    }
  }

  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2)
    throws WriterException
  {
    return encode(paramString, paramBarcodeFormat, paramInt1, paramInt2, null);
  }

  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2, Hashtable paramHashtable)
    throws WriterException
  {
    if ((paramString == null) || (paramString.length() == 0))
      throw new IllegalArgumentException("Found empty contents");
    if (paramBarcodeFormat != BarcodeFormat.QR_CODE)
      throw new IllegalArgumentException("Can only encode QR_CODE, but got " + paramBarcodeFormat);
    if ((paramInt1 < 0) || (paramInt2 < 0))
      throw new IllegalArgumentException("Requested dimensions are too small: " + paramInt1 + 'x' + paramInt2);
    Object localObject = ErrorCorrectionLevel.L;
    if (paramHashtable != null)
    {
      ErrorCorrectionLevel localErrorCorrectionLevel = (ErrorCorrectionLevel)paramHashtable.get(EncodeHintType.ERROR_CORRECTION);
      if (localErrorCorrectionLevel != null)
        localObject = localErrorCorrectionLevel;
    }
    QRCode localQRCode = new QRCode();
    Encoder.encode(paramString, (ErrorCorrectionLevel)localObject, paramHashtable, localQRCode);
    return (BitMatrix)renderResult(localQRCode, paramInt1, paramInt2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.QRCodeWriter
 * JD-Core Version:    0.6.0
 */