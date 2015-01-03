package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.Collections;
import com.google.zxing.common.Comparator;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternFinder;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.util.Hashtable;
import java.util.Vector;

final class MultiFinderPatternFinder extends FinderPatternFinder
{
  private static final float DIFF_MODSIZE_CUTOFF = 0.5F;
  private static final float DIFF_MODSIZE_CUTOFF_PERCENT = 0.05F;
  private static final FinderPatternInfo[] EMPTY_RESULT_ARRAY = new FinderPatternInfo[0];
  private static final float MAX_MODULE_COUNT_PER_EDGE = 180.0F;
  private static final float MIN_MODULE_COUNT_PER_EDGE = 9.0F;

  MultiFinderPatternFinder(BitMatrix paramBitMatrix)
  {
    super(paramBitMatrix);
  }

  MultiFinderPatternFinder(BitMatrix paramBitMatrix, ResultPointCallback paramResultPointCallback)
  {
    super(paramBitMatrix, paramResultPointCallback);
  }

  private FinderPattern[][] selectBestPatterns()
    throws NotFoundException
  {
    Vector localVector1 = getPossibleCenters();
    int i = localVector1.size();
    if (i < 3)
      throw NotFoundException.getNotFoundInstance();
    FinderPattern[][] arrayOfFinderPattern;;
    if (i == 3)
    {
      arrayOfFinderPattern; = new FinderPattern[1][];
      FinderPattern[] arrayOfFinderPattern2 = new FinderPattern[3];
      arrayOfFinderPattern2[0] = ((FinderPattern)localVector1.elementAt(0));
      arrayOfFinderPattern2[1] = ((FinderPattern)localVector1.elementAt(1));
      arrayOfFinderPattern2[2] = ((FinderPattern)localVector1.elementAt(2));
      arrayOfFinderPattern;[0] = arrayOfFinderPattern2;
    }
    while (true)
    {
      return arrayOfFinderPattern;;
      Collections.insertionSort(localVector1, new ModuleSizeComparator(null));
      Vector localVector2 = new Vector();
      int j = 0;
      if (j >= i - 2)
      {
        if (localVector2.isEmpty())
          break;
        arrayOfFinderPattern; = new FinderPattern[localVector2.size()][];
        for (int n = 0; n < localVector2.size(); n++)
          arrayOfFinderPattern;[n] = ((FinderPattern[])localVector2.elementAt(n));
        continue;
      }
      else
      {
        FinderPattern localFinderPattern1 = (FinderPattern)localVector1.elementAt(j);
        if (localFinderPattern1 == null);
        int k;
        FinderPattern localFinderPattern2;
        label218: float f1;
        do
        {
          while (true)
          {
            j++;
            break;
            for (k = j + 1; k < i - 1; k++)
            {
              localFinderPattern2 = (FinderPattern)localVector1.elementAt(k);
              if (localFinderPattern2 != null)
                break label218;
            }
          }
          f1 = (localFinderPattern1.getEstimatedModuleSize() - localFinderPattern2.getEstimatedModuleSize()) / Math.min(localFinderPattern1.getEstimatedModuleSize(), localFinderPattern2.getEstimatedModuleSize());
        }
        while ((Math.abs(localFinderPattern1.getEstimatedModuleSize() - localFinderPattern2.getEstimatedModuleSize()) > 0.5F) && (f1 >= 0.05F));
        int m = k + 1;
        label279: FinderPattern localFinderPattern3;
        if (m < i)
        {
          localFinderPattern3 = (FinderPattern)localVector1.elementAt(m);
          if (localFinderPattern3 != null)
            break label307;
        }
        while (true)
        {
          m++;
          break label279;
          break;
          label307: float f2 = (localFinderPattern2.getEstimatedModuleSize() - localFinderPattern3.getEstimatedModuleSize()) / Math.min(localFinderPattern2.getEstimatedModuleSize(), localFinderPattern3.getEstimatedModuleSize());
          if ((Math.abs(localFinderPattern2.getEstimatedModuleSize() - localFinderPattern3.getEstimatedModuleSize()) > 0.5F) && (f2 >= 0.05F))
            break;
          FinderPattern[] arrayOfFinderPattern1 = { localFinderPattern1, localFinderPattern2, localFinderPattern3 };
          ResultPoint.orderBestPatterns(arrayOfFinderPattern1);
          FinderPatternInfo localFinderPatternInfo = new FinderPatternInfo(arrayOfFinderPattern1);
          float f3 = ResultPoint.distance(localFinderPatternInfo.getTopLeft(), localFinderPatternInfo.getBottomLeft());
          float f4 = ResultPoint.distance(localFinderPatternInfo.getTopRight(), localFinderPatternInfo.getBottomLeft());
          float f5 = ResultPoint.distance(localFinderPatternInfo.getTopLeft(), localFinderPatternInfo.getTopRight());
          float f6 = (f3 + f5) / (2.0F * localFinderPattern1.getEstimatedModuleSize());
          if ((f6 > 180.0F) || (f6 < 9.0F) || (Math.abs((f3 - f5) / Math.min(f3, f5)) >= 0.1F))
            continue;
          float f7 = (float)Math.sqrt(f3 * f3 + f5 * f5);
          if (Math.abs((f4 - f7) / Math.min(f4, f7)) >= 0.1F)
            continue;
          localVector2.addElement(arrayOfFinderPattern1);
        }
      }
    }
    throw NotFoundException.getNotFoundInstance();
  }

  public FinderPatternInfo[] findMulti(Hashtable paramHashtable)
    throws NotFoundException
  {
    int i;
    BitMatrix localBitMatrix;
    int k;
    int m;
    int[] arrayOfInt;
    int n;
    label69: FinderPattern[][] arrayOfFinderPattern;
    Vector localVector;
    int i3;
    label94: FinderPatternInfo[] arrayOfFinderPatternInfo;
    if ((paramHashtable != null) && (paramHashtable.containsKey(DecodeHintType.TRY_HARDER)))
    {
      i = 1;
      localBitMatrix = getImage();
      int j = localBitMatrix.getHeight();
      k = localBitMatrix.getWidth();
      m = (int)(3.0F * (j / 228.0F));
      if ((m < 3) || (i != 0))
        m = 3;
      arrayOfInt = new int[5];
      n = m - 1;
      if (n < j)
        break label123;
      arrayOfFinderPattern = selectBestPatterns();
      localVector = new Vector();
      i3 = 0;
      if (i3 < arrayOfFinderPattern.length)
        break label392;
      if (!localVector.isEmpty())
        break label424;
      arrayOfFinderPatternInfo = EMPTY_RESULT_ARRAY;
    }
    while (true)
    {
      return arrayOfFinderPatternInfo;
      i = 0;
      break;
      label123: arrayOfInt[0] = 0;
      arrayOfInt[1] = 0;
      arrayOfInt[2] = 0;
      arrayOfInt[3] = 0;
      arrayOfInt[4] = 0;
      int i1 = 0;
      int i2 = 0;
      if (i2 >= k)
      {
        if (foundPatternCross(arrayOfInt))
          handlePossibleCenter(arrayOfInt, n, k);
        n += m;
        break label69;
      }
      if (localBitMatrix.get(i2, n))
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
              if (!handlePossibleCenter(arrayOfInt, n, i2))
              {
                do
                  i2++;
                while ((i2 < k) && (!localBitMatrix.get(i2, n)));
                i2--;
              }
              arrayOfInt[0] = 0;
              arrayOfInt[1] = 0;
              arrayOfInt[2] = 0;
              arrayOfInt[3] = 0;
              arrayOfInt[4] = 0;
              i1 = 0;
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
      label392: FinderPattern[] arrayOfFinderPattern1 = arrayOfFinderPattern[i3];
      ResultPoint.orderBestPatterns(arrayOfFinderPattern1);
      localVector.addElement(new FinderPatternInfo(arrayOfFinderPattern1));
      i3++;
      break label94;
      label424: arrayOfFinderPatternInfo = new FinderPatternInfo[localVector.size()];
      for (int i4 = 0; i4 < localVector.size(); i4++)
        arrayOfFinderPatternInfo[i4] = ((FinderPatternInfo)localVector.elementAt(i4));
    }
  }

  private static class ModuleSizeComparator
    implements Comparator
  {
    public int compare(Object paramObject1, Object paramObject2)
    {
      float f = ((FinderPattern)paramObject2).getEstimatedModuleSize() - ((FinderPattern)paramObject1).getEstimatedModuleSize();
      if (f < 0.0D)
        return -1;
      if (f > 0.0D)
        return 1;
      return 0;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.multi.qrcode.detector.MultiFinderPatternFinder
 * JD-Core Version:    0.6.0
 */