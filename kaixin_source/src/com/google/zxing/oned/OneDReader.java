package com.google.zxing.oned;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Enumeration;
import java.util.Hashtable;

public abstract class OneDReader
  implements Reader
{
  protected static final int INTEGER_MATH_SHIFT = 8;
  protected static final int PATTERN_MATCH_RESULT_SCALE_FACTOR = 256;

  private Result doDecode(BinaryBitmap paramBinaryBitmap, Hashtable paramHashtable)
    throws NotFoundException
  {
    int i = paramBinaryBitmap.getWidth();
    int j = paramBinaryBitmap.getHeight();
    Object localObject1 = new BitArray(i);
    int k = j >> 1;
    int m;
    int n;
    label57: int i1;
    int i2;
    label77: int i3;
    if (paramHashtable != null)
    {
      DecodeHintType localDecodeHintType2 = DecodeHintType.TRY_HARDER;
      if (paramHashtable.containsKey(localDecodeHintType2))
      {
        m = 1;
        if (m == 0)
          break label97;
        n = 8;
        i1 = Math.max(1, j >> n);
        if (m == 0)
          break label103;
        i2 = j;
        i3 = 0;
      }
    }
    while (true)
    {
      if (i3 >= i2);
      label97: label103: int i4;
      int i5;
      label128: int i6;
      do
      {
        throw NotFoundException.getNotFoundInstance();
        m = 0;
        break;
        n = 5;
        break label57;
        i2 = 15;
        break label77;
        i4 = i3 + 1 >> 1;
        if ((i3 & 0x1) != 0)
          break label184;
        i5 = 1;
        if (i5 == 0)
          break label190;
        i6 = k + i1 * i4;
      }
      while ((i6 < 0) || (i6 >= j));
      try
      {
        BitArray localBitArray = paramBinaryBitmap.getBlackRow(i6, (BitArray)localObject1);
        localObject1 = localBitArray;
        i7 = 0;
        if (i7 >= 2)
        {
          i3++;
          continue;
          label184: i5 = 0;
          break label128;
          label190: i4 = -i4;
        }
      }
      catch (NotFoundException localNotFoundException)
      {
        while (true)
        {
          int i7;
          continue;
          Hashtable localHashtable;
          Enumeration localEnumeration;
          if (i7 == 1)
          {
            ((BitArray)localObject1).reverse();
            if (paramHashtable != null)
            {
              DecodeHintType localDecodeHintType1 = DecodeHintType.NEED_RESULT_POINT_CALLBACK;
              if (paramHashtable.containsKey(localDecodeHintType1))
              {
                localHashtable = new Hashtable();
                localEnumeration = paramHashtable.keys();
              }
            }
          }
          while (true)
          {
            if (!localEnumeration.hasMoreElements())
              paramHashtable = localHashtable;
            try
            {
              Result localResult = decodeRow(i6, (BitArray)localObject1, paramHashtable);
              if (i7 == 1)
              {
                localResult.putMetadata(ResultMetadataType.ORIENTATION, new Integer(180));
                ResultPoint[] arrayOfResultPoint = localResult.getResultPoints();
                arrayOfResultPoint[0] = new ResultPoint(i - arrayOfResultPoint[0].getX() - 1.0F, arrayOfResultPoint[0].getY());
                arrayOfResultPoint[1] = new ResultPoint(i - arrayOfResultPoint[1].getX() - 1.0F, arrayOfResultPoint[1].getY());
              }
              return localResult;
              Object localObject2 = localEnumeration.nextElement();
              if (localObject2.equals(DecodeHintType.NEED_RESULT_POINT_CALLBACK))
                continue;
              localHashtable.put(localObject2, paramHashtable.get(localObject2));
            }
            catch (ReaderException localReaderException)
            {
              i7++;
            }
          }
        }
      }
    }
  }

  protected static int patternMatchVariance(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
  {
    int i = paramArrayOfInt1.length;
    int j = 0;
    int k = 0;
    int m = 0;
    if (m >= i)
      if (j >= k)
        break label52;
    label149: 
    while (true)
    {
      return 2147483647;
      j += paramArrayOfInt1[m];
      k += paramArrayOfInt2[m];
      m++;
      break;
      label52: int n = (j << 8) / k;
      int i1 = paramInt * n >> 8;
      int i2 = 0;
      int i3 = 0;
      if (i3 >= i)
        return i2 / j;
      int i4 = paramArrayOfInt1[i3] << 8;
      int i5 = n * paramArrayOfInt2[i3];
      if (i4 > i5);
      for (int i6 = i4 - i5; ; i6 = i5 - i4)
      {
        if (i6 > i1)
          break label149;
        i2 += i6;
        i3++;
        break;
      }
    }
  }

  protected static void recordPattern(BitArray paramBitArray, int paramInt, int[] paramArrayOfInt)
    throws NotFoundException
  {
    int i = paramArrayOfInt.length;
    int k;
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        k = paramBitArray.getSize();
        if (paramInt < k)
          break;
        throw NotFoundException.getNotFoundInstance();
      }
      paramArrayOfInt[j] = 0;
    }
    int n;
    int i1;
    if (paramBitArray.get(paramInt))
    {
      m = 0;
      n = 0;
      i1 = paramInt;
      label56: if (i1 < k)
        break label94;
    }
    label94: 
    do
    {
      if ((n == i) || ((n == i - 1) && (i1 == k)))
        return;
      throw NotFoundException.getNotFoundInstance();
      m = 1;
      break;
      if ((m ^ paramBitArray.get(i1)) != 0)
      {
        paramArrayOfInt[n] = (1 + paramArrayOfInt[n]);
        i1++;
        break label56;
      }
      n++;
    }
    while (n == i);
    paramArrayOfInt[n] = 1;
    if (m != 0);
    for (int m = 0; ; m = 1)
      break;
  }

  protected static void recordPatternInReverse(BitArray paramBitArray, int paramInt, int[] paramArrayOfInt)
    throws NotFoundException
  {
    int i = paramArrayOfInt.length;
    boolean bool = paramBitArray.get(paramInt);
    while (true)
      if ((paramInt <= 0) || (i < 0))
      {
        if (i < 0)
          break;
        throw NotFoundException.getNotFoundInstance();
      }
      else
      {
        paramInt--;
        if (paramBitArray.get(paramInt) == bool)
          continue;
        i--;
        if (bool);
        for (bool = false; ; bool = true)
          break;
      }
    recordPattern(paramBitArray, paramInt + 1, paramArrayOfInt);
  }

  public Result decode(BinaryBitmap paramBinaryBitmap)
    throws NotFoundException, FormatException
  {
    return decode(paramBinaryBitmap, null);
  }

  public Result decode(BinaryBitmap paramBinaryBitmap, Hashtable paramHashtable)
    throws NotFoundException, FormatException
  {
    try
    {
      Result localResult2 = doDecode(paramBinaryBitmap, paramHashtable);
      localResult1 = localResult2;
      return localResult1;
    }
    catch (NotFoundException localNotFoundException)
    {
      Result localResult1;
      if ((paramHashtable != null) && (paramHashtable.containsKey(DecodeHintType.TRY_HARDER)));
      for (int i = 1; ; i = 0)
      {
        if ((i == 0) || (!paramBinaryBitmap.isRotateSupported()))
          break label199;
        BinaryBitmap localBinaryBitmap = paramBinaryBitmap.rotateCounterClockwise();
        localResult1 = doDecode(localBinaryBitmap, paramHashtable);
        Hashtable localHashtable = localResult1.getResultMetadata();
        int j = 270;
        if ((localHashtable != null) && (localHashtable.containsKey(ResultMetadataType.ORIENTATION)))
          j = (j + ((Integer)localHashtable.get(ResultMetadataType.ORIENTATION)).intValue()) % 360;
        localResult1.putMetadata(ResultMetadataType.ORIENTATION, new Integer(j));
        ResultPoint[] arrayOfResultPoint = localResult1.getResultPoints();
        int k = localBinaryBitmap.getHeight();
        for (int m = 0; m < arrayOfResultPoint.length; m++)
          arrayOfResultPoint[m] = new ResultPoint(k - arrayOfResultPoint[m].getY() - 1.0F, arrayOfResultPoint[m].getX());
      }
    }
    label199: throw localNotFoundException;
  }

  public abstract Result decodeRow(int paramInt, BitArray paramBitArray, Hashtable paramHashtable)
    throws NotFoundException, ChecksumException, FormatException;

  public void reset()
  {
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.OneDReader
 * JD-Core Version:    0.6.0
 */