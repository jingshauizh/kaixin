package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.Collections;
import com.google.zxing.common.Comparator;
import java.util.Hashtable;
import java.util.Vector;

public class FinderPatternFinder
{
  private static final int CENTER_QUORUM = 2;
  private static final int INTEGER_MATH_SHIFT = 8;
  protected static final int MAX_MODULES = 57;
  protected static final int MIN_SKIP = 3;
  private final int[] crossCheckStateCount;
  private boolean hasSkipped;
  private final BitMatrix image;
  private final Vector possibleCenters;
  private final ResultPointCallback resultPointCallback;

  public FinderPatternFinder(BitMatrix paramBitMatrix)
  {
    this(paramBitMatrix, null);
  }

  public FinderPatternFinder(BitMatrix paramBitMatrix, ResultPointCallback paramResultPointCallback)
  {
    this.image = paramBitMatrix;
    this.possibleCenters = new Vector();
    this.crossCheckStateCount = new int[5];
    this.resultPointCallback = paramResultPointCallback;
  }

  private static float centerFromEnd(int[] paramArrayOfInt, int paramInt)
  {
    return paramInt - paramArrayOfInt[4] - paramArrayOfInt[3] - paramArrayOfInt[2] / 2.0F;
  }

  private float crossCheckHorizontal(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    BitMatrix localBitMatrix = this.image;
    int i = localBitMatrix.getWidth();
    int[] arrayOfInt = getCrossCheckStateCount();
    for (int j = paramInt1; ; j--)
    {
      if ((j < 0) || (!localBitMatrix.get(j, paramInt2)))
      {
        if (j >= 0)
          break;
        return (0.0F / 0.0F);
      }
      arrayOfInt[2] = (1 + arrayOfInt[2]);
    }
    do
    {
      arrayOfInt[1] = (1 + arrayOfInt[1]);
      j--;
    }
    while ((j >= 0) && (!localBitMatrix.get(j, paramInt2)) && (arrayOfInt[1] <= paramInt3));
    if ((j < 0) || (arrayOfInt[1] > paramInt3))
      return (0.0F / 0.0F);
    do
    {
      arrayOfInt[0] = (1 + arrayOfInt[0]);
      j--;
    }
    while ((j >= 0) && (localBitMatrix.get(j, paramInt2)) && (arrayOfInt[0] <= paramInt3));
    if (arrayOfInt[0] > paramInt3)
      return (0.0F / 0.0F);
    for (int k = paramInt1 + 1; ; k++)
    {
      if ((k >= i) || (!localBitMatrix.get(k, paramInt2)))
      {
        if (k != i)
          break;
        return (0.0F / 0.0F);
      }
      arrayOfInt[2] = (1 + arrayOfInt[2]);
    }
    do
    {
      arrayOfInt[3] = (1 + arrayOfInt[3]);
      k++;
    }
    while ((k < i) && (!localBitMatrix.get(k, paramInt2)) && (arrayOfInt[3] < paramInt3));
    if ((k == i) || (arrayOfInt[3] >= paramInt3))
      return (0.0F / 0.0F);
    do
    {
      arrayOfInt[4] = (1 + arrayOfInt[4]);
      k++;
    }
    while ((k < i) && (localBitMatrix.get(k, paramInt2)) && (arrayOfInt[4] < paramInt3));
    if (arrayOfInt[4] >= paramInt3)
      return (0.0F / 0.0F);
    if (5 * Math.abs(arrayOfInt[0] + arrayOfInt[1] + arrayOfInt[2] + arrayOfInt[3] + arrayOfInt[4] - paramInt4) >= paramInt4)
      return (0.0F / 0.0F);
    if (foundPatternCross(arrayOfInt))
      return centerFromEnd(arrayOfInt, k);
    return (0.0F / 0.0F);
  }

  private float crossCheckVertical(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    BitMatrix localBitMatrix = this.image;
    int i = localBitMatrix.getHeight();
    int[] arrayOfInt = getCrossCheckStateCount();
    for (int j = paramInt1; ; j--)
    {
      if ((j < 0) || (!localBitMatrix.get(paramInt2, j)))
      {
        if (j >= 0)
          break;
        return (0.0F / 0.0F);
      }
      arrayOfInt[2] = (1 + arrayOfInt[2]);
    }
    do
    {
      arrayOfInt[1] = (1 + arrayOfInt[1]);
      j--;
    }
    while ((j >= 0) && (!localBitMatrix.get(paramInt2, j)) && (arrayOfInt[1] <= paramInt3));
    if ((j < 0) || (arrayOfInt[1] > paramInt3))
      return (0.0F / 0.0F);
    do
    {
      arrayOfInt[0] = (1 + arrayOfInt[0]);
      j--;
    }
    while ((j >= 0) && (localBitMatrix.get(paramInt2, j)) && (arrayOfInt[0] <= paramInt3));
    if (arrayOfInt[0] > paramInt3)
      return (0.0F / 0.0F);
    for (int k = paramInt1 + 1; ; k++)
    {
      if ((k >= i) || (!localBitMatrix.get(paramInt2, k)))
      {
        if (k != i)
          break;
        return (0.0F / 0.0F);
      }
      arrayOfInt[2] = (1 + arrayOfInt[2]);
    }
    do
    {
      arrayOfInt[3] = (1 + arrayOfInt[3]);
      k++;
    }
    while ((k < i) && (!localBitMatrix.get(paramInt2, k)) && (arrayOfInt[3] < paramInt3));
    if ((k == i) || (arrayOfInt[3] >= paramInt3))
      return (0.0F / 0.0F);
    do
    {
      arrayOfInt[4] = (1 + arrayOfInt[4]);
      k++;
    }
    while ((k < i) && (localBitMatrix.get(paramInt2, k)) && (arrayOfInt[4] < paramInt3));
    if (arrayOfInt[4] >= paramInt3)
      return (0.0F / 0.0F);
    if (5 * Math.abs(arrayOfInt[0] + arrayOfInt[1] + arrayOfInt[2] + arrayOfInt[3] + arrayOfInt[4] - paramInt4) >= paramInt4 * 2)
      return (0.0F / 0.0F);
    if (foundPatternCross(arrayOfInt))
      return centerFromEnd(arrayOfInt, k);
    return (0.0F / 0.0F);
  }

  private int findRowSkip()
  {
    int i = this.possibleCenters.size();
    if (i <= 1);
    Object localObject;
    FinderPattern localFinderPattern;
    while (true)
    {
      return 0;
      localObject = null;
      for (int j = 0; j < i; j++)
      {
        localFinderPattern = (FinderPattern)this.possibleCenters.elementAt(j);
        if (localFinderPattern.getCount() < 2)
          continue;
        if (localObject != null)
          break label59;
        localObject = localFinderPattern;
      }
    }
    label59: this.hasSkipped = true;
    return (int)(Math.abs(localObject.getX() - localFinderPattern.getX()) - Math.abs(localObject.getY() - localFinderPattern.getY())) / 2;
  }

  protected static boolean foundPatternCross(int[] paramArrayOfInt)
  {
    int i = 0;
    int j = 0;
    if (j >= 5)
      if (i >= 7)
        break label35;
    label35: int m;
    int n;
    do
    {
      int k;
      do
      {
        return false;
        k = paramArrayOfInt[j];
      }
      while (k == 0);
      i += k;
      j++;
      break;
      m = (i << 8) / 7;
      n = m / 2;
    }
    while ((Math.abs(m - (paramArrayOfInt[0] << 8)) >= n) || (Math.abs(m - (paramArrayOfInt[1] << 8)) >= n) || (Math.abs(m * 3 - (paramArrayOfInt[2] << 8)) >= n * 3) || (Math.abs(m - (paramArrayOfInt[3] << 8)) >= n) || (Math.abs(m - (paramArrayOfInt[4] << 8)) >= n));
    return true;
  }

  private int[] getCrossCheckStateCount()
  {
    this.crossCheckStateCount[0] = 0;
    this.crossCheckStateCount[1] = 0;
    this.crossCheckStateCount[2] = 0;
    this.crossCheckStateCount[3] = 0;
    this.crossCheckStateCount[4] = 0;
    return this.crossCheckStateCount;
  }

  private boolean haveMultiplyConfirmedCenters()
  {
    int i = 0;
    float f1 = 0.0F;
    int j = this.possibleCenters.size();
    for (int k = 0; ; k++)
    {
      if (k >= j)
      {
        if (i >= 3)
          break;
        return false;
      }
      FinderPattern localFinderPattern = (FinderPattern)this.possibleCenters.elementAt(k);
      if (localFinderPattern.getCount() < 2)
        continue;
      i++;
      f1 += localFinderPattern.getEstimatedModuleSize();
    }
    float f2 = f1 / j;
    float f3 = 0.0F;
    for (int m = 0; ; m++)
    {
      if (m >= j)
      {
        if (f3 > 0.05F * f1)
          break;
        return true;
      }
      f3 += Math.abs(((FinderPattern)this.possibleCenters.elementAt(m)).getEstimatedModuleSize() - f2);
    }
  }

  private FinderPattern[] selectBestPatterns()
    throws NotFoundException
  {
    int i = this.possibleCenters.size();
    if (i < 3)
      throw NotFoundException.getNotFoundInstance();
    float f3;
    float f4;
    int k;
    float f6;
    float f8;
    int m;
    label94: float f1;
    if (i > 3)
    {
      f3 = 0.0F;
      f4 = 0.0F;
      k = 0;
      if (k >= i)
      {
        f6 = f3 / i;
        float f7 = (float)Math.sqrt(f4 / i - f6 * f6);
        Collections.insertionSort(this.possibleCenters, new FurthestFromAverageComparator(f6, null));
        f8 = Math.max(0.2F * f6, f7);
        m = 0;
        if ((m < this.possibleCenters.size()) && (this.possibleCenters.size() > 3))
          break label271;
      }
    }
    else if (this.possibleCenters.size() > 3)
    {
      f1 = 0.0F;
    }
    for (int j = 0; ; j++)
    {
      if (j >= this.possibleCenters.size())
      {
        float f2 = f1 / this.possibleCenters.size();
        Collections.insertionSort(this.possibleCenters, new CenterComparator(f2, null));
        this.possibleCenters.setSize(3);
        FinderPattern[] arrayOfFinderPattern = new FinderPattern[3];
        arrayOfFinderPattern[0] = ((FinderPattern)this.possibleCenters.elementAt(0));
        arrayOfFinderPattern[1] = ((FinderPattern)this.possibleCenters.elementAt(1));
        arrayOfFinderPattern[2] = ((FinderPattern)this.possibleCenters.elementAt(2));
        return arrayOfFinderPattern;
        float f5 = ((FinderPattern)this.possibleCenters.elementAt(k)).getEstimatedModuleSize();
        f3 += f5;
        f4 += f5 * f5;
        k++;
        break;
        label271: if (Math.abs(((FinderPattern)this.possibleCenters.elementAt(m)).getEstimatedModuleSize() - f6) > f8)
        {
          this.possibleCenters.removeElementAt(m);
          m--;
        }
        m++;
        break label94;
      }
      f1 += ((FinderPattern)this.possibleCenters.elementAt(j)).getEstimatedModuleSize();
    }
  }

  FinderPatternInfo find(Hashtable paramHashtable)
    throws NotFoundException
  {
    int i;
    int j;
    int k;
    int m;
    boolean bool;
    int[] arrayOfInt;
    int n;
    if ((paramHashtable != null) && (paramHashtable.containsKey(DecodeHintType.TRY_HARDER)))
    {
      i = 1;
      j = this.image.getHeight();
      k = this.image.getWidth();
      m = j * 3 / 228;
      if ((m < 3) || (i != 0))
        m = 3;
      bool = false;
      arrayOfInt = new int[5];
      n = m - 1;
    }
    int i1;
    int i2;
    while (true)
    {
      if ((n >= j) || (bool))
      {
        FinderPattern[] arrayOfFinderPattern = selectBestPatterns();
        ResultPoint.orderBestPatterns(arrayOfFinderPattern);
        return new FinderPatternInfo(arrayOfFinderPattern);
        i = 0;
        break;
      }
      arrayOfInt[0] = 0;
      arrayOfInt[1] = 0;
      arrayOfInt[2] = 0;
      arrayOfInt[3] = 0;
      arrayOfInt[4] = 0;
      i1 = 0;
      i2 = 0;
      if (i2 < k)
        break label194;
      if ((foundPatternCross(arrayOfInt)) && (handlePossibleCenter(arrayOfInt, n, k)))
      {
        m = arrayOfInt[0];
        if (this.hasSkipped)
          bool = haveMultiplyConfirmedCenters();
      }
      n += m;
    }
    label194: if (this.image.get(i2, n))
    {
      if ((i1 & 0x1) == 1)
        i1++;
      arrayOfInt[i1] = (1 + arrayOfInt[i1]);
    }
    while (true)
    {
      i2++;
      break;
      if ((i1 & 0x1) == 0)
      {
        if (i1 == 4)
        {
          if (foundPatternCross(arrayOfInt))
          {
            if (handlePossibleCenter(arrayOfInt, n, i2))
            {
              m = 2;
              if (this.hasSkipped)
                bool = haveMultiplyConfirmedCenters();
              while (true)
              {
                arrayOfInt[0] = 0;
                arrayOfInt[1] = 0;
                arrayOfInt[2] = 0;
                arrayOfInt[3] = 0;
                arrayOfInt[4] = 0;
                i1 = 0;
                break;
                int i3 = findRowSkip();
                if (i3 <= arrayOfInt[2])
                  continue;
                n += i3 - arrayOfInt[2] - m;
                i2 = k - 1;
              }
            }
            arrayOfInt[0] = arrayOfInt[2];
            arrayOfInt[1] = arrayOfInt[3];
            arrayOfInt[2] = arrayOfInt[4];
            arrayOfInt[3] = 1;
            arrayOfInt[4] = 0;
            i1 = 3;
            continue;
          }
          arrayOfInt[0] = arrayOfInt[2];
          arrayOfInt[1] = arrayOfInt[3];
          arrayOfInt[2] = arrayOfInt[4];
          arrayOfInt[3] = 1;
          arrayOfInt[4] = 0;
          i1 = 3;
          continue;
        }
        i1++;
        arrayOfInt[i1] = (1 + arrayOfInt[i1]);
        continue;
      }
      arrayOfInt[i1] = (1 + arrayOfInt[i1]);
    }
  }

  protected BitMatrix getImage()
  {
    return this.image;
  }

  protected Vector getPossibleCenters()
  {
    return this.possibleCenters;
  }

  protected boolean handlePossibleCenter(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfInt[0] + paramArrayOfInt[1] + paramArrayOfInt[2] + paramArrayOfInt[3] + paramArrayOfInt[4];
    float f1 = centerFromEnd(paramArrayOfInt, paramInt2);
    float f2 = crossCheckVertical(paramInt1, (int)f1, paramArrayOfInt[2], i);
    if (!Float.isNaN(f2))
    {
      float f3 = crossCheckHorizontal((int)f1, (int)f2, paramArrayOfInt[2], i);
      if (!Float.isNaN(f3))
      {
        float f4 = i / 7.0F;
        int j = this.possibleCenters.size();
        for (int k = 0; ; k++)
        {
          int m = 0;
          if (k >= j);
          while (true)
          {
            if (m == 0)
            {
              FinderPattern localFinderPattern2 = new FinderPattern(f3, f2, f4);
              this.possibleCenters.addElement(localFinderPattern2);
              if (this.resultPointCallback != null)
                this.resultPointCallback.foundPossibleResultPoint(localFinderPattern2);
            }
            return true;
            FinderPattern localFinderPattern1 = (FinderPattern)this.possibleCenters.elementAt(k);
            if (!localFinderPattern1.aboutEquals(f4, f2, f3))
              break;
            localFinderPattern1.incrementCount();
            m = 1;
          }
        }
      }
    }
    return false;
  }

  private static class CenterComparator
    implements Comparator
  {
    private final float average;

    private CenterComparator(float paramFloat)
    {
      this.average = paramFloat;
    }

    public int compare(Object paramObject1, Object paramObject2)
    {
      if (((FinderPattern)paramObject2).getCount() == ((FinderPattern)paramObject1).getCount())
      {
        float f1 = Math.abs(((FinderPattern)paramObject2).getEstimatedModuleSize() - this.average);
        float f2 = Math.abs(((FinderPattern)paramObject1).getEstimatedModuleSize() - this.average);
        if (f1 < f2)
          return 1;
        if (f1 == f2)
          return 0;
        return -1;
      }
      return ((FinderPattern)paramObject2).getCount() - ((FinderPattern)paramObject1).getCount();
    }
  }

  private static class FurthestFromAverageComparator
    implements Comparator
  {
    private final float average;

    private FurthestFromAverageComparator(float paramFloat)
    {
      this.average = paramFloat;
    }

    public int compare(Object paramObject1, Object paramObject2)
    {
      float f1 = Math.abs(((FinderPattern)paramObject2).getEstimatedModuleSize() - this.average);
      float f2 = Math.abs(((FinderPattern)paramObject1).getEstimatedModuleSize() - this.average);
      if (f1 < f2)
        return -1;
      if (f1 == f2)
        return 0;
      return 1;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.detector.FinderPatternFinder
 * JD-Core Version:    0.6.0
 */