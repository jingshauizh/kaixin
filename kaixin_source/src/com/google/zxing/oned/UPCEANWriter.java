package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Hashtable;

public abstract class UPCEANWriter
  implements Writer
{
  protected static int appendPattern(byte[] paramArrayOfByte, int paramInt1, int[] paramArrayOfInt, int paramInt2)
  {
    if ((paramInt2 != 0) && (paramInt2 != 1))
      throw new IllegalArgumentException("startColor must be either 0 or 1, but got: " + paramInt2);
    int i = (byte)paramInt2;
    int j = 0;
    int k = 0;
    if (k >= paramArrayOfInt.length)
      return j;
    for (int m = 0; ; m++)
    {
      if (m >= paramArrayOfInt[k])
      {
        i = (byte)(i ^ 0x1);
        k++;
        break;
      }
      paramArrayOfByte[paramInt1] = i;
      paramInt1++;
      j++;
    }
  }

  private static BitMatrix renderResult(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfByte.length;
    int j = i + (UPCEANReader.START_END_PATTERN.length << 1);
    int k = Math.max(paramInt1, j);
    int m = Math.max(1, paramInt2);
    int n = k / j;
    int i1 = (k - i * n) / 2;
    BitMatrix localBitMatrix = new BitMatrix(k, m);
    int i2 = 0;
    int i3 = i1;
    while (true)
    {
      if (i2 >= i)
        return localBitMatrix;
      if (paramArrayOfByte[i2] == 1)
        localBitMatrix.setRegion(i3, 0, n, m);
      i2++;
      i3 += n;
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
    if ((paramInt1 < 0) || (paramInt2 < 0))
      throw new IllegalArgumentException("Requested dimensions are too small: " + paramInt1 + 'x' + paramInt2);
    return renderResult(encode(paramString), paramInt1, paramInt2);
  }

  public abstract byte[] encode(String paramString);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.UPCEANWriter
 * JD-Core Version:    0.6.0
 */