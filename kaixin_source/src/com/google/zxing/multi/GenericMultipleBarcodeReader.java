package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.Hashtable;
import java.util.Vector;

public final class GenericMultipleBarcodeReader
  implements MultipleBarcodeReader
{
  private static final int MIN_DIMENSION_TO_RECUR = 100;
  private final Reader delegate;

  public GenericMultipleBarcodeReader(Reader paramReader)
  {
    this.delegate = paramReader;
  }

  private void doDecodeMultiple(BinaryBitmap paramBinaryBitmap, Hashtable paramHashtable, Vector paramVector, int paramInt1, int paramInt2)
  {
    ResultPoint[] arrayOfResultPoint;
    do
    {
      Result localResult;
      while (true)
      {
        int i;
        int k;
        try
        {
          localResult = this.delegate.decode(paramBinaryBitmap, paramHashtable);
          i = 0;
          int j = paramVector.size();
          k = 0;
          if (i >= j)
          {
            if (k == 0)
              break;
            return;
          }
        }
        catch (ReaderException localReaderException)
        {
          return;
        }
        if (((Result)paramVector.elementAt(i)).getText().equals(localResult.getText()))
        {
          k = 1;
          continue;
        }
        i++;
      }
      paramVector.addElement(translateResultPoints(localResult, paramInt1, paramInt2));
      arrayOfResultPoint = localResult.getResultPoints();
    }
    while ((arrayOfResultPoint == null) || (arrayOfResultPoint.length == 0));
    int m = paramBinaryBitmap.getWidth();
    int n = paramBinaryBitmap.getHeight();
    float f1 = m;
    float f2 = n;
    float f3 = 0.0F;
    float f4 = 0.0F;
    for (int i1 = 0; ; i1++)
    {
      if (i1 >= arrayOfResultPoint.length)
      {
        if (f1 > 100.0F)
          doDecodeMultiple(paramBinaryBitmap.crop(0, 0, (int)f1, n), paramHashtable, paramVector, paramInt1, paramInt2);
        if (f2 > 100.0F)
          doDecodeMultiple(paramBinaryBitmap.crop(0, 0, m, (int)f2), paramHashtable, paramVector, paramInt1, paramInt2);
        if (f3 < m - 100)
          doDecodeMultiple(paramBinaryBitmap.crop((int)f3, 0, m - (int)f3, n), paramHashtable, paramVector, paramInt1 + (int)f3, paramInt2);
        if (f4 >= n - 100)
          break;
        doDecodeMultiple(paramBinaryBitmap.crop(0, (int)f4, m, n - (int)f4), paramHashtable, paramVector, paramInt1, paramInt2 + (int)f4);
        return;
      }
      ResultPoint localResultPoint = arrayOfResultPoint[i1];
      float f5 = localResultPoint.getX();
      float f6 = localResultPoint.getY();
      if (f5 < f1)
        f1 = f5;
      if (f6 < f2)
        f2 = f6;
      if (f5 > f3)
        f3 = f5;
      if (f6 <= f4)
        continue;
      f4 = f6;
    }
  }

  private static Result translateResultPoints(Result paramResult, int paramInt1, int paramInt2)
  {
    ResultPoint[] arrayOfResultPoint1 = paramResult.getResultPoints();
    ResultPoint[] arrayOfResultPoint2 = new ResultPoint[arrayOfResultPoint1.length];
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfResultPoint1.length)
        return new Result(paramResult.getText(), paramResult.getRawBytes(), arrayOfResultPoint2, paramResult.getBarcodeFormat());
      ResultPoint localResultPoint = arrayOfResultPoint1[i];
      arrayOfResultPoint2[i] = new ResultPoint(localResultPoint.getX() + paramInt1, localResultPoint.getY() + paramInt2);
    }
  }

  public Result[] decodeMultiple(BinaryBitmap paramBinaryBitmap)
    throws NotFoundException
  {
    return decodeMultiple(paramBinaryBitmap, null);
  }

  public Result[] decodeMultiple(BinaryBitmap paramBinaryBitmap, Hashtable paramHashtable)
    throws NotFoundException
  {
    Vector localVector = new Vector();
    doDecodeMultiple(paramBinaryBitmap, paramHashtable, localVector, 0, 0);
    if (localVector.isEmpty())
      throw NotFoundException.getNotFoundInstance();
    int i = localVector.size();
    Result[] arrayOfResult = new Result[i];
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return arrayOfResult;
      arrayOfResult[j] = ((Result)localVector.elementAt(j));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.multi.GenericMultipleBarcodeReader
 * JD-Core Version:    0.6.0
 */