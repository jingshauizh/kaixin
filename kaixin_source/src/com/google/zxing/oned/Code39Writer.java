package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Hashtable;

public final class Code39Writer extends UPCEANWriter
{
  private static void toIntArray(int paramInt, int[] paramArrayOfInt)
  {
    int i = 0;
    if (i >= 9)
      return;
    if ((paramInt & 1 << i) == 0);
    for (int j = 1; ; j = 2)
    {
      paramArrayOfInt[i] = j;
      i++;
      break;
    }
  }

  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2, Hashtable paramHashtable)
    throws WriterException
  {
    if (paramBarcodeFormat != BarcodeFormat.CODE_39)
      throw new IllegalArgumentException("Can only encode CODE_39, but got " + paramBarcodeFormat);
    return super.encode(paramString, paramBarcodeFormat, paramInt1, paramInt2, paramHashtable);
  }

  public byte[] encode(String paramString)
  {
    int i = paramString.length();
    if (i > 80)
      throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + i);
    int[] arrayOfInt1 = new int[9];
    int j = i + 25;
    int k = 0;
    byte[] arrayOfByte;
    int[] arrayOfInt2;
    int i2;
    if (k >= i)
    {
      arrayOfByte = new byte[j];
      toIntArray(Code39Reader.CHARACTER_ENCODINGS[39], arrayOfInt1);
      int i1 = appendPattern(arrayOfByte, 0, arrayOfInt1, 1);
      arrayOfInt2 = new int[] { 1 };
      i2 = i1 + appendPattern(arrayOfByte, i1, arrayOfInt2, 0);
    }
    for (int i3 = i - 1; ; i3--)
    {
      if (i3 < 0)
      {
        toIntArray(Code39Reader.CHARACTER_ENCODINGS[39], arrayOfInt1);
        (i2 + appendPattern(arrayOfByte, i2, arrayOfInt1, 1));
        return arrayOfByte;
        int m = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(paramString.charAt(k));
        toIntArray(Code39Reader.CHARACTER_ENCODINGS[m], arrayOfInt1);
        for (int n = 0; ; n++)
        {
          if (n >= arrayOfInt1.length)
          {
            k++;
            break;
          }
          j += arrayOfInt1[n];
        }
      }
      int i4 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(paramString.charAt(i3));
      toIntArray(Code39Reader.CHARACTER_ENCODINGS[i4], arrayOfInt1);
      int i5 = i2 + appendPattern(arrayOfByte, i2, arrayOfInt1, 1);
      i2 = i5 + appendPattern(arrayOfByte, i5, arrayOfInt2, 0);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.Code39Writer
 * JD-Core Version:    0.6.0
 */