package com.google.zxing.datamatrix.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.Collections;
import com.google.zxing.common.Comparator;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public final class Detector
{
  private static final Integer[] INTEGERS;
  private final BitMatrix image;
  private final WhiteRectangleDetector rectangleDetector;

  static
  {
    Integer[] arrayOfInteger = new Integer[5];
    arrayOfInteger[0] = new Integer(0);
    arrayOfInteger[1] = new Integer(1);
    arrayOfInteger[2] = new Integer(2);
    arrayOfInteger[3] = new Integer(3);
    arrayOfInteger[4] = new Integer(4);
    INTEGERS = arrayOfInteger;
  }

  public Detector(BitMatrix paramBitMatrix)
    throws NotFoundException
  {
    this.image = paramBitMatrix;
    this.rectangleDetector = new WhiteRectangleDetector(paramBitMatrix);
  }

  private ResultPoint correctTopRight(ResultPoint paramResultPoint1, ResultPoint paramResultPoint2, ResultPoint paramResultPoint3, ResultPoint paramResultPoint4, int paramInt)
  {
    float f1 = distance(paramResultPoint1, paramResultPoint2) / paramInt;
    int i = distance(paramResultPoint3, paramResultPoint4);
    float f2 = (paramResultPoint4.getX() - paramResultPoint3.getX()) / i;
    float f3 = (paramResultPoint4.getY() - paramResultPoint3.getY()) / i;
    Object localObject = new ResultPoint(paramResultPoint4.getX() + f1 * f2, paramResultPoint4.getY() + f1 * f3);
    float f4 = distance(paramResultPoint1, paramResultPoint2) / paramInt;
    int j = distance(paramResultPoint2, paramResultPoint4);
    float f5 = (paramResultPoint4.getX() - paramResultPoint2.getX()) / j;
    float f6 = (paramResultPoint4.getY() - paramResultPoint2.getY()) / j;
    ResultPoint localResultPoint = new ResultPoint(paramResultPoint4.getX() + f4 * f5, paramResultPoint4.getY() + f4 * f6);
    if (!isValid((ResultPoint)localObject))
      if (isValid(localResultPoint))
        localObject = localResultPoint;
    do
    {
      return localObject;
      return null;
    }
    while ((!isValid(localResultPoint)) || (Math.abs(transitionsBetween(paramResultPoint3, (ResultPoint)localObject).getTransitions() - transitionsBetween(paramResultPoint2, (ResultPoint)localObject).getTransitions()) <= Math.abs(transitionsBetween(paramResultPoint3, localResultPoint).getTransitions() - transitionsBetween(paramResultPoint2, localResultPoint).getTransitions())));
    return (ResultPoint)localResultPoint;
  }

  private ResultPoint correctTopRightRectangular(ResultPoint paramResultPoint1, ResultPoint paramResultPoint2, ResultPoint paramResultPoint3, ResultPoint paramResultPoint4, int paramInt1, int paramInt2)
  {
    float f1 = distance(paramResultPoint1, paramResultPoint2) / paramInt1;
    int i = distance(paramResultPoint3, paramResultPoint4);
    float f2 = (paramResultPoint4.getX() - paramResultPoint3.getX()) / i;
    float f3 = (paramResultPoint4.getY() - paramResultPoint3.getY()) / i;
    ResultPoint localResultPoint1 = new ResultPoint(paramResultPoint4.getX() + f1 * f2, paramResultPoint4.getY() + f1 * f3);
    float f4 = distance(paramResultPoint1, paramResultPoint3) / paramInt2;
    int j = distance(paramResultPoint2, paramResultPoint4);
    float f5 = (paramResultPoint4.getX() - paramResultPoint2.getX()) / j;
    float f6 = (paramResultPoint4.getY() - paramResultPoint2.getY()) / j;
    ResultPoint localResultPoint2 = new ResultPoint(paramResultPoint4.getX() + f4 * f5, paramResultPoint4.getY() + f4 * f6);
    if (!isValid(localResultPoint1))
      if (!isValid(localResultPoint2));
    do
    {
      return localResultPoint2;
      return null;
      if (!isValid(localResultPoint2))
        return localResultPoint1;
    }
    while (Math.abs(paramInt1 - transitionsBetween(paramResultPoint3, localResultPoint1).getTransitions()) + Math.abs(paramInt2 - transitionsBetween(paramResultPoint2, localResultPoint1).getTransitions()) > Math.abs(paramInt1 - transitionsBetween(paramResultPoint3, localResultPoint2).getTransitions()) + Math.abs(paramInt2 - transitionsBetween(paramResultPoint2, localResultPoint2).getTransitions()));
    return localResultPoint1;
  }

  private static int distance(ResultPoint paramResultPoint1, ResultPoint paramResultPoint2)
  {
    return round((float)Math.sqrt((paramResultPoint1.getX() - paramResultPoint2.getX()) * (paramResultPoint1.getX() - paramResultPoint2.getX()) + (paramResultPoint1.getY() - paramResultPoint2.getY()) * (paramResultPoint1.getY() - paramResultPoint2.getY())));
  }

  private static void increment(Hashtable paramHashtable, ResultPoint paramResultPoint)
  {
    Integer localInteger1 = (Integer)paramHashtable.get(paramResultPoint);
    if (localInteger1 == null);
    for (Integer localInteger2 = INTEGERS[1]; ; localInteger2 = INTEGERS[(1 + localInteger1.intValue())])
    {
      paramHashtable.put(paramResultPoint, localInteger2);
      return;
    }
  }

  private boolean isValid(ResultPoint paramResultPoint)
  {
    return (paramResultPoint.getX() >= 0.0F) && (paramResultPoint.getX() < this.image.width) && (paramResultPoint.getY() > 0.0F) && (paramResultPoint.getY() < this.image.height);
  }

  private static int round(float paramFloat)
  {
    return (int)(0.5F + paramFloat);
  }

  private static BitMatrix sampleGrid(BitMatrix paramBitMatrix, ResultPoint paramResultPoint1, ResultPoint paramResultPoint2, ResultPoint paramResultPoint3, ResultPoint paramResultPoint4, int paramInt1, int paramInt2)
    throws NotFoundException
  {
    return GridSampler.getInstance().sampleGrid(paramBitMatrix, paramInt1, paramInt2, 0.5F, 0.5F, paramInt1 - 0.5F, 0.5F, paramInt1 - 0.5F, paramInt2 - 0.5F, 0.5F, paramInt2 - 0.5F, paramResultPoint1.getX(), paramResultPoint1.getY(), paramResultPoint4.getX(), paramResultPoint4.getY(), paramResultPoint3.getX(), paramResultPoint3.getY(), paramResultPoint2.getX(), paramResultPoint2.getY());
  }

  private ResultPointsAndTransitions transitionsBetween(ResultPoint paramResultPoint1, ResultPoint paramResultPoint2)
  {
    int i = (int)paramResultPoint1.getX();
    int j = (int)paramResultPoint1.getY();
    int k = (int)paramResultPoint2.getX();
    int m = (int)paramResultPoint2.getY();
    int n;
    int i1;
    int i2;
    int i3;
    int i4;
    label111: int i5;
    label120: int i6;
    BitMatrix localBitMatrix1;
    int i7;
    if (Math.abs(m - j) > Math.abs(k - i))
    {
      n = 1;
      if (n != 0)
      {
        int i13 = i;
        i = j;
        j = i13;
        int i14 = k;
        k = m;
        m = i14;
      }
      i1 = Math.abs(k - i);
      i2 = Math.abs(m - j);
      i3 = -i1 >> 1;
      if (j >= m)
        break label194;
      i4 = 1;
      if (i >= k)
        break label200;
      i5 = 1;
      i6 = 0;
      localBitMatrix1 = this.image;
      if (n == 0)
        break label206;
      i7 = j;
      label138: if (n == 0)
        break label212;
    }
    boolean bool1;
    int i9;
    int i10;
    label171: label194: label200: label206: label212: for (int i8 = i; ; i8 = j)
    {
      bool1 = localBitMatrix1.get(i7, i8);
      i9 = i;
      i10 = j;
      if (i9 != k)
        break label219;
      ResultPointsAndTransitions localResultPointsAndTransitions = new ResultPointsAndTransitions(paramResultPoint1, paramResultPoint2, i6, null);
      return localResultPointsAndTransitions;
      n = 0;
      break;
      i4 = -1;
      break label111;
      i5 = -1;
      break label120;
      i7 = i;
      break label138;
    }
    label219: BitMatrix localBitMatrix2 = this.image;
    int i11;
    if (n != 0)
    {
      i11 = i10;
      label234: if (n == 0)
        break label318;
    }
    label318: for (int i12 = i9; ; i12 = i10)
    {
      boolean bool2 = localBitMatrix2.get(i11, i12);
      if (bool2 != bool1)
      {
        i6++;
        bool1 = bool2;
      }
      i3 += i2;
      if (i3 > 0)
      {
        if (i10 == m)
          break label171;
        i10 += i4;
        i3 -= i1;
      }
      i9 += i5;
      break;
      i11 = i9;
      break label234;
    }
  }

  public DetectorResult detect()
    throws NotFoundException
  {
    ResultPoint[] arrayOfResultPoint1 = this.rectangleDetector.detect();
    ResultPoint localResultPoint1 = arrayOfResultPoint1[0];
    ResultPoint localResultPoint2 = arrayOfResultPoint1[1];
    ResultPoint localResultPoint3 = arrayOfResultPoint1[2];
    ResultPoint localResultPoint4 = arrayOfResultPoint1[3];
    Vector localVector = new Vector(4);
    localVector.addElement(transitionsBetween(localResultPoint1, localResultPoint2));
    localVector.addElement(transitionsBetween(localResultPoint1, localResultPoint3));
    localVector.addElement(transitionsBetween(localResultPoint2, localResultPoint4));
    localVector.addElement(transitionsBetween(localResultPoint3, localResultPoint4));
    Collections.insertionSort(localVector, new ResultPointsAndTransitionsComparator(null));
    ResultPointsAndTransitions localResultPointsAndTransitions1 = (ResultPointsAndTransitions)localVector.elementAt(0);
    ResultPointsAndTransitions localResultPointsAndTransitions2 = (ResultPointsAndTransitions)localVector.elementAt(1);
    Hashtable localHashtable = new Hashtable();
    increment(localHashtable, localResultPointsAndTransitions1.getFrom());
    increment(localHashtable, localResultPointsAndTransitions1.getTo());
    increment(localHashtable, localResultPointsAndTransitions2.getFrom());
    increment(localHashtable, localResultPointsAndTransitions2.getTo());
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Enumeration localEnumeration = localHashtable.keys();
    while (true)
    {
      if (!localEnumeration.hasMoreElements())
      {
        if ((localObject1 != null) && (localObject2 != null) && (localObject3 != null))
          break;
        throw NotFoundException.getNotFoundInstance();
      }
      ResultPoint localResultPoint5 = (ResultPoint)localEnumeration.nextElement();
      if (((Integer)localHashtable.get(localResultPoint5)).intValue() == 2)
      {
        localObject2 = localResultPoint5;
        continue;
      }
      if (localObject1 == null)
      {
        localObject1 = localResultPoint5;
        continue;
      }
      localObject3 = localResultPoint5;
    }
    ResultPoint[] arrayOfResultPoint2 = { localObject1, localObject2, localObject3 };
    ResultPoint.orderBestPatterns(arrayOfResultPoint2);
    ResultPoint localResultPoint6 = arrayOfResultPoint2[0];
    ResultPoint localResultPoint7 = arrayOfResultPoint2[1];
    ResultPoint localResultPoint8 = arrayOfResultPoint2[2];
    ResultPoint localResultPoint9;
    int k;
    int m;
    ResultPoint localResultPoint10;
    int n;
    int i1;
    if (!localHashtable.containsKey(localResultPoint1))
    {
      localResultPoint9 = localResultPoint1;
      int i = transitionsBetween(localResultPoint8, localResultPoint9).getTransitions();
      int j = transitionsBetween(localResultPoint6, localResultPoint9).getTransitions();
      if ((i & 0x1) == 1)
        i++;
      k = i + 2;
      if ((j & 0x1) == 1)
        j++;
      m = j + 2;
      if ((k * 4 < m * 7) && (m * 4 < k * 7))
        break label581;
      localResultPoint10 = correctTopRightRectangular(localResultPoint7, localResultPoint6, localResultPoint8, localResultPoint9, k, m);
      if (localResultPoint10 == null)
        localResultPoint10 = localResultPoint9;
      n = transitionsBetween(localResultPoint8, localResultPoint10).getTransitions();
      i1 = transitionsBetween(localResultPoint6, localResultPoint10).getTransitions();
      if ((n & 0x1) == 1)
        n++;
      if ((i1 & 0x1) == 1)
        i1++;
    }
    label581: int i3;
    for (BitMatrix localBitMatrix = sampleGrid(this.image, localResultPoint8, localResultPoint7, localResultPoint6, localResultPoint10, n, i1); ; localBitMatrix = sampleGrid(this.image, localResultPoint8, localResultPoint7, localResultPoint6, localResultPoint10, i3, i3))
    {
      ResultPoint[] arrayOfResultPoint3 = { localResultPoint8, localResultPoint7, localResultPoint6, localResultPoint10 };
      return new DetectorResult(localBitMatrix, arrayOfResultPoint3);
      if (!localHashtable.containsKey(localResultPoint2))
      {
        localResultPoint9 = localResultPoint2;
        break;
      }
      if (!localHashtable.containsKey(localResultPoint3))
      {
        localResultPoint9 = localResultPoint3;
        break;
      }
      localResultPoint9 = localResultPoint4;
      break;
      int i2 = Math.min(m, k);
      localResultPoint10 = correctTopRight(localResultPoint7, localResultPoint6, localResultPoint8, localResultPoint9, i2);
      if (localResultPoint10 == null)
        localResultPoint10 = localResultPoint9;
      i3 = 1 + Math.max(transitionsBetween(localResultPoint8, localResultPoint10).getTransitions(), transitionsBetween(localResultPoint6, localResultPoint10).getTransitions());
      if ((i3 & 0x1) != 1)
        continue;
      i3++;
    }
  }

  private static class ResultPointsAndTransitions
  {
    private final ResultPoint from;
    private final ResultPoint to;
    private final int transitions;

    private ResultPointsAndTransitions(ResultPoint paramResultPoint1, ResultPoint paramResultPoint2, int paramInt)
    {
      this.from = paramResultPoint1;
      this.to = paramResultPoint2;
      this.transitions = paramInt;
    }

    public ResultPoint getFrom()
    {
      return this.from;
    }

    public ResultPoint getTo()
    {
      return this.to;
    }

    public int getTransitions()
    {
      return this.transitions;
    }

    public String toString()
    {
      return this.from + "/" + this.to + '/' + this.transitions;
    }
  }

  private static class ResultPointsAndTransitionsComparator
    implements Comparator
  {
    public int compare(Object paramObject1, Object paramObject2)
    {
      return ((Detector.ResultPointsAndTransitions)paramObject1).getTransitions() - ((Detector.ResultPointsAndTransitions)paramObject2).getTransitions();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.datamatrix.detector.Detector
 * JD-Core Version:    0.6.0
 */