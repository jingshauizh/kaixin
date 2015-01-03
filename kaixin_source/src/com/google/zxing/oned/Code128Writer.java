package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public final class Code128Writer extends UPCEANWriter
{
  private static final int CODE_CODE_B = 100;
  private static final int CODE_CODE_C = 99;
  private static final int CODE_START_B = 104;
  private static final int CODE_START_C = 105;
  private static final int CODE_STOP = 106;

  private static boolean isDigits(String paramString, int paramInt1, int paramInt2)
  {
    int i = paramInt1 + paramInt2;
    for (int j = paramInt1; ; j++)
    {
      if (j >= i)
        return true;
      int k = paramString.charAt(j);
      if ((k < 48) || (k > 57))
        return false;
    }
  }

  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2, Hashtable paramHashtable)
    throws WriterException
  {
    if (paramBarcodeFormat != BarcodeFormat.CODE_128)
      throw new IllegalArgumentException("Can only encode CODE_128, but got " + paramBarcodeFormat);
    return super.encode(paramString, paramBarcodeFormat, paramInt1, paramInt2, paramHashtable);
  }

  public byte[] encode(String paramString)
  {
    int i = paramString.length();
    if ((i < 1) || (i > 80))
      throw new IllegalArgumentException("Contents length should be between 1 and 80 characters, but got " + i);
    int j = 0;
    Vector localVector;
    int k;
    int m;
    int n;
    int i1;
    int i6;
    Enumeration localEnumeration1;
    label113: byte[] arrayOfByte;
    Enumeration localEnumeration2;
    int i8;
    if (j >= i)
    {
      localVector = new Vector();
      k = 0;
      m = 1;
      n = 0;
      i1 = 0;
      if (i1 < i)
        break label189;
      int i5 = k % 103;
      localVector.addElement(Code128Reader.CODE_PATTERNS[i5]);
      localVector.addElement(Code128Reader.CODE_PATTERNS[106]);
      i6 = 0;
      localEnumeration1 = localVector.elements();
      if (localEnumeration1.hasMoreElements())
        break label354;
      arrayOfByte = new byte[i6];
      localEnumeration2 = localVector.elements();
      i8 = 0;
    }
    while (true)
    {
      if (!localEnumeration2.hasMoreElements())
      {
        return arrayOfByte;
        int i9 = paramString.charAt(j);
        if ((i9 < 32) || (i9 > 126))
          throw new IllegalArgumentException("Contents should only contain characters between ' ' and '~'");
        j++;
        break;
        label189: int i2;
        label199: int i3;
        label223: int i4;
        if (n == 99)
        {
          i2 = 2;
          if ((i - i1 < i2) || (!isDigits(paramString, i1, i2)))
            break label289;
          i3 = 99;
          if (i3 != n)
            break label317;
          if (n != 100)
            break label296;
          i4 = '￠' + paramString.charAt(i1);
          i1++;
        }
        while (true)
        {
          localVector.addElement(Code128Reader.CODE_PATTERNS[i4]);
          k += i4 * m;
          if (i1 == 0)
            break;
          m++;
          break;
          i2 = 4;
          break label199;
          label289: i3 = 100;
          break label223;
          label296: i4 = Integer.parseInt(paramString.substring(i1, i1 + 2));
          i1 += 2;
        }
        label317: if (n == 0)
          if (i3 == 100)
            i4 = 104;
        while (true)
        {
          n = i3;
          break;
          i4 = 105;
          continue;
          i4 = i3;
        }
        label354: int[] arrayOfInt = (int[])localEnumeration1.nextElement();
        for (int i7 = 0; i7 < arrayOfInt.length; i7++)
          i6 += arrayOfInt[i7];
        break label113;
      }
      i8 += appendPattern(arrayOfByte, i8, (int[])localEnumeration2.nextElement(), 1);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.Code128Writer
 * JD-Core Version:    0.6.0
 */