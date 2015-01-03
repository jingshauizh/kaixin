package com.google.zxing.oned.rss;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public final class RSS14Reader extends AbstractRSSReader
{
  private static final int[][] FINDER_PATTERNS;
  private static final int[] INSIDE_GSUM;
  private static final int[] INSIDE_ODD_TOTAL_SUBSET;
  private static final int[] INSIDE_ODD_WIDEST;
  private static final int[] OUTSIDE_EVEN_TOTAL_SUBSET = { 1, 10, 34, 70, 126 };
  private static final int[] OUTSIDE_GSUM;
  private static final int[] OUTSIDE_ODD_WIDEST;
  private final Vector possibleLeftPairs = new Vector();
  private final Vector possibleRightPairs = new Vector();

  static
  {
    INSIDE_ODD_TOTAL_SUBSET = new int[] { 4, 20, 48, 81 };
    int[] arrayOfInt1 = new int[5];
    arrayOfInt1[1] = 161;
    arrayOfInt1[2] = 961;
    arrayOfInt1[3] = 2015;
    arrayOfInt1[4] = 2715;
    OUTSIDE_GSUM = arrayOfInt1;
    int[] arrayOfInt2 = new int[4];
    arrayOfInt2[1] = 336;
    arrayOfInt2[2] = 1036;
    arrayOfInt2[3] = 1516;
    INSIDE_GSUM = arrayOfInt2;
    OUTSIDE_ODD_WIDEST = new int[] { 8, 6, 4, 3, 1 };
    INSIDE_ODD_WIDEST = new int[] { 2, 4, 6, 8 };
    FINDER_PATTERNS = new int[][] { { 3, 8, 2, 1 }, { 3, 5, 5, 1 }, { 3, 3, 7, 1 }, { 3, 1, 9, 1 }, { 2, 7, 4, 1 }, { 2, 5, 6, 1 }, { 2, 3, 8, 1 }, { 1, 5, 7, 1 }, { 1, 3, 9, 1 } };
  }

  private static void addOrTally(Vector paramVector, Pair paramPair)
  {
    if (paramPair == null);
    label67: 
    while (true)
    {
      return;
      Enumeration localEnumeration = paramVector.elements();
      boolean bool = localEnumeration.hasMoreElements();
      int i = 0;
      if (!bool);
      while (true)
      {
        if (i != 0)
          break label67;
        paramVector.addElement(paramPair);
        return;
        Pair localPair = (Pair)localEnumeration.nextElement();
        if (localPair.getValue() != paramPair.getValue())
          break;
        localPair.incrementCount();
        i = 1;
      }
    }
  }

  private void adjustOddEvenCounts(boolean paramBoolean, int paramInt)
    throws NotFoundException
  {
    int i = count(this.oddCounts);
    int j = count(this.evenCounts);
    int k = i + j - paramInt;
    int m = i & 0x1;
    int n;
    int i1;
    label47: int i2;
    label58: int i3;
    int i4;
    label77: int i6;
    if (paramBoolean)
    {
      n = 1;
      if (m != n)
        break label113;
      i1 = 1;
      if ((j & 0x1) != 1)
        break label119;
      i2 = 1;
      i3 = 0;
      i4 = 0;
      if (!paramBoolean)
        break label166;
      if (i <= 12)
        break label125;
      i5 = 1;
      if (j <= 12)
        break label145;
      i6 = 1;
    }
    while (true)
    {
      if (k != 1)
        break label264;
      if (i1 == 0)
        break label249;
      if (i2 == 0)
        break label232;
      throw NotFoundException.getNotFoundInstance();
      n = 0;
      break;
      label113: i1 = 0;
      break label47;
      label119: i2 = 0;
      break label58;
      label125: i5 = 0;
      i3 = 0;
      if (i >= 4)
        break label77;
      i3 = 1;
      i5 = 0;
      break label77;
      label145: i6 = 0;
      i4 = 0;
      if (j >= 4)
        continue;
      i4 = 1;
      i6 = 0;
      continue;
      label166: if (i > 11)
        i5 = 1;
      while (true)
      {
        if (j <= 10)
          break label211;
        i6 = 1;
        i4 = 0;
        break;
        i5 = 0;
        i3 = 0;
        if (i >= 5)
          continue;
        i3 = 1;
        i5 = 0;
      }
      label211: i6 = 0;
      i4 = 0;
      if (j >= 4)
        continue;
      i4 = 1;
      i6 = 0;
    }
    label232: int i5 = 1;
    while (true)
    {
      if (i3 == 0)
        break label372;
      if (i5 == 0)
        break label361;
      throw NotFoundException.getNotFoundInstance();
      label249: if (i2 == 0)
        throw NotFoundException.getNotFoundInstance();
      i6 = 1;
      continue;
      label264: if (k == -1)
      {
        if (i1 != 0)
        {
          if (i2 != 0)
            throw NotFoundException.getNotFoundInstance();
          i3 = 1;
          continue;
        }
        if (i2 == 0)
          throw NotFoundException.getNotFoundInstance();
        i4 = 1;
        continue;
      }
      if (k != 0)
        break;
      if (i1 != 0)
      {
        if (i2 == 0)
          throw NotFoundException.getNotFoundInstance();
        if (i < j)
        {
          i3 = 1;
          i6 = 1;
          continue;
        }
        i5 = 1;
        i4 = 1;
        continue;
      }
      if (i2 == 0)
        continue;
      throw NotFoundException.getNotFoundInstance();
    }
    throw NotFoundException.getNotFoundInstance();
    label361: increment(this.oddCounts, this.oddRoundingErrors);
    label372: if (i5 != 0)
      decrement(this.oddCounts, this.oddRoundingErrors);
    if (i4 != 0)
    {
      if (i6 != 0)
        throw NotFoundException.getNotFoundInstance();
      increment(this.evenCounts, this.oddRoundingErrors);
    }
    if (i6 != 0)
      decrement(this.evenCounts, this.evenRoundingErrors);
  }

  private static boolean checkChecksum(Pair paramPair1, Pair paramPair2)
  {
    int i = paramPair1.getFinderPattern().getValue();
    int j = paramPair2.getFinderPattern().getValue();
    if (((i != 0) || (j != 8)) && (i == 8));
    int k = (paramPair1.getChecksumPortion() + 16 * paramPair2.getChecksumPortion()) % 79;
    int m = 9 * paramPair1.getFinderPattern().getValue() + paramPair2.getFinderPattern().getValue();
    if (m > 72)
      m--;
    if (m > 8)
      m--;
    return k == m;
  }

  private static Result constructResult(Pair paramPair1, Pair paramPair2)
  {
    String str1 = String.valueOf(4537077L * paramPair1.getValue() + paramPair2.getValue());
    StringBuffer localStringBuffer = new StringBuffer(14);
    int i = 13 - str1.length();
    int j;
    if (i <= 0)
    {
      localStringBuffer.append(str1);
      j = 0;
    }
    for (int k = 0; ; k++)
    {
      if (k >= 13)
      {
        int n = 10 - j % 10;
        if (n == 10)
          n = 0;
        localStringBuffer.append(n);
        ResultPoint[] arrayOfResultPoint1 = paramPair1.getFinderPattern().getResultPoints();
        ResultPoint[] arrayOfResultPoint2 = paramPair2.getFinderPattern().getResultPoints();
        String str2 = String.valueOf(localStringBuffer.toString());
        ResultPoint[] arrayOfResultPoint3 = new ResultPoint[4];
        arrayOfResultPoint3[0] = arrayOfResultPoint1[0];
        arrayOfResultPoint3[1] = arrayOfResultPoint1[1];
        arrayOfResultPoint3[2] = arrayOfResultPoint2[0];
        arrayOfResultPoint3[3] = arrayOfResultPoint2[1];
        return new Result(str2, null, arrayOfResultPoint3, BarcodeFormat.RSS_14);
        localStringBuffer.append('0');
        i--;
        break;
      }
      int m = '￐' + localStringBuffer.charAt(k);
      if ((k & 0x1) == 0)
        m *= 3;
      j += m;
    }
  }

  private DataCharacter decodeDataCharacter(BitArray paramBitArray, FinderPattern paramFinderPattern, boolean paramBoolean)
    throws NotFoundException
  {
    int[] arrayOfInt1 = this.dataCharacterCounters;
    arrayOfInt1[0] = 0;
    arrayOfInt1[1] = 0;
    arrayOfInt1[2] = 0;
    arrayOfInt1[3] = 0;
    arrayOfInt1[4] = 0;
    arrayOfInt1[5] = 0;
    arrayOfInt1[6] = 0;
    arrayOfInt1[7] = 0;
    int m;
    label72: float f1;
    int[] arrayOfInt2;
    int[] arrayOfInt3;
    float[] arrayOfFloat1;
    float[] arrayOfFloat2;
    int n;
    int i3;
    int i4;
    int i5;
    label139: int i6;
    int i7;
    if (paramBoolean)
    {
      recordPatternInReverse(paramBitArray, paramFinderPattern.getStartEnd()[0], arrayOfInt1);
      if (!paramBoolean)
        break label263;
      m = 16;
      f1 = count(arrayOfInt1) / m;
      arrayOfInt2 = this.oddCounts;
      arrayOfInt3 = this.evenCounts;
      arrayOfFloat1 = this.oddRoundingErrors;
      arrayOfFloat2 = this.evenRoundingErrors;
      n = 0;
      if (n < arrayOfInt1.length)
        break label270;
      adjustOddEvenCounts(paramBoolean, m);
      i3 = 0;
      i4 = 0;
      i5 = -1 + arrayOfInt2.length;
      if (i5 >= 0)
        break label370;
      i6 = 0;
      i7 = 0;
    }
    int i9;
    for (int i8 = -1 + arrayOfInt3.length; ; i8--)
    {
      if (i8 < 0)
      {
        i9 = i4 + i6 * 3;
        if (!paramBoolean)
          break label513;
        if (((i3 & 0x1) == 0) && (i3 <= 12) && (i3 >= 4))
          break label428;
        throw NotFoundException.getNotFoundInstance();
        recordPattern(paramBitArray, 1 + paramFinderPattern.getStartEnd()[1], arrayOfInt1);
        int i = 0;
        for (int j = -1 + arrayOfInt1.length; i < j; j--)
        {
          int k = arrayOfInt1[i];
          arrayOfInt1[i] = arrayOfInt1[j];
          arrayOfInt1[j] = k;
          i++;
        }
        break;
        label263: m = 15;
        break label72;
        label270: float f2 = arrayOfInt1[n] / f1;
        int i1 = (int)(0.5F + f2);
        label298: int i2;
        if (i1 < 1)
        {
          i1 = 1;
          i2 = n >> 1;
          if ((n & 0x1) != 0)
            break label349;
          arrayOfInt2[i2] = i1;
          arrayOfFloat1[i2] = (f2 - i1);
        }
        while (true)
        {
          n++;
          break;
          if (i1 <= 8)
            break label298;
          i1 = 8;
          break label298;
          label349: arrayOfInt3[i2] = i1;
          arrayOfFloat2[i2] = (f2 - i1);
        }
        label370: i4 = i4 * 9 + arrayOfInt2[i5];
        i3 += arrayOfInt2[i5];
        i5--;
        break label139;
      }
      i6 = i6 * 9 + arrayOfInt3[i8];
      i7 += arrayOfInt3[i8];
    }
    label428: int i17 = (12 - i3) / 2;
    int i18 = OUTSIDE_ODD_WIDEST[i17];
    int i19 = 9 - i18;
    int i20 = RSSUtils.getRSSvalue(arrayOfInt2, i18, false);
    int i21 = RSSUtils.getRSSvalue(arrayOfInt3, i19, true);
    int i22 = OUTSIDE_EVEN_TOTAL_SUBSET[i17];
    int i23 = OUTSIDE_GSUM[i17];
    DataCharacter localDataCharacter2 = new DataCharacter(i23 + (i21 + i20 * i22), i9);
    return localDataCharacter2;
    label513: if (((i7 & 0x1) != 0) || (i7 > 10) || (i7 < 4))
      throw NotFoundException.getNotFoundInstance();
    int i10 = (10 - i7) / 2;
    int i11 = INSIDE_ODD_WIDEST[i10];
    int i12 = 9 - i11;
    int i13 = RSSUtils.getRSSvalue(arrayOfInt2, i11, true);
    int i14 = RSSUtils.getRSSvalue(arrayOfInt3, i12, false);
    int i15 = INSIDE_ODD_TOTAL_SUBSET[i10];
    int i16 = INSIDE_GSUM[i10];
    DataCharacter localDataCharacter1 = new DataCharacter(i16 + (i13 + i14 * i15), i9);
    return localDataCharacter1;
  }

  private Pair decodePair(BitArray paramBitArray, boolean paramBoolean, int paramInt, Hashtable paramHashtable)
  {
    try
    {
      int[] arrayOfInt = findFinderPattern(paramBitArray, 0, paramBoolean);
      FinderPattern localFinderPattern = parseFoundFinderPattern(paramBitArray, paramInt, paramBoolean, arrayOfInt);
      if (paramHashtable == null);
      ResultPointCallback localResultPointCallback;
      for (Object localObject = null; ; localObject = localResultPointCallback)
      {
        if (localObject != null)
        {
          float f = (arrayOfInt[0] + arrayOfInt[1]) / 2.0F;
          if (paramBoolean)
            f = -1 + paramBitArray.getSize() - f;
          localObject.foundPossibleResultPoint(new ResultPoint(f, paramInt));
        }
        DataCharacter localDataCharacter1 = decodeDataCharacter(paramBitArray, localFinderPattern, true);
        DataCharacter localDataCharacter2 = decodeDataCharacter(paramBitArray, localFinderPattern, false);
        return new Pair(1597 * localDataCharacter1.getValue() + localDataCharacter2.getValue(), localDataCharacter1.getChecksumPortion() + 4 * localDataCharacter2.getChecksumPortion(), localFinderPattern);
        localResultPointCallback = (ResultPointCallback)paramHashtable.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
      }
    }
    catch (NotFoundException localNotFoundException)
    {
    }
    return null;
  }

  private int[] findFinderPattern(BitArray paramBitArray, int paramInt, boolean paramBoolean)
    throws NotFoundException
  {
    int[] arrayOfInt = this.decodeFinderCounters;
    arrayOfInt[0] = 0;
    arrayOfInt[1] = 0;
    arrayOfInt[2] = 0;
    arrayOfInt[3] = 0;
    int i = paramBitArray.getSize();
    boolean bool = false;
    label41: int j;
    int k;
    if (paramInt >= i)
    {
      j = 0;
      k = paramInt;
    }
    for (int m = paramInt; ; m++)
    {
      if (m >= i)
      {
        throw NotFoundException.getNotFoundInstance();
        if (paramBitArray.get(paramInt));
        for (bool = false; ; bool = true)
        {
          if (paramBoolean == bool)
            break label88;
          paramInt++;
          break;
        }
        label88: break label41;
      }
      if (!(bool ^ paramBitArray.get(m)))
        break label120;
      arrayOfInt[j] = (1 + arrayOfInt[j]);
    }
    label120: if (j == 3)
    {
      if (isFinderPattern(arrayOfInt))
        return new int[] { k, m };
      k += arrayOfInt[0] + arrayOfInt[1];
      arrayOfInt[0] = arrayOfInt[2];
      arrayOfInt[1] = arrayOfInt[3];
      arrayOfInt[2] = 0;
      arrayOfInt[3] = 0;
      j--;
      arrayOfInt[j] = 1;
      if (!bool)
        break label214;
    }
    label191: label214: for (bool = false; ; bool = true)
    {
      break;
      j++;
      break label191;
    }
  }

  private FinderPattern parseFoundFinderPattern(BitArray paramBitArray, int paramInt, boolean paramBoolean, int[] paramArrayOfInt)
    throws NotFoundException
  {
    boolean bool = paramBitArray.get(paramArrayOfInt[0]);
    int i = -1 + paramArrayOfInt[0];
    int j;
    int k;
    int[] arrayOfInt1;
    if ((i < 0) || (!(bool ^ paramBitArray.get(i))))
    {
      j = i + 1;
      k = paramArrayOfInt[0] - j;
      arrayOfInt1 = this.decodeFinderCounters;
    }
    for (int m = -1 + arrayOfInt1.length; ; m--)
    {
      if (m <= 0)
      {
        arrayOfInt1[0] = k;
        int n = parseFinderValue(arrayOfInt1, FINDER_PATTERNS);
        int i1 = j;
        int i2 = paramArrayOfInt[1];
        if (paramBoolean)
        {
          i1 = -1 + paramBitArray.getSize() - i1;
          i2 = -1 + paramBitArray.getSize() - i2;
        }
        int[] arrayOfInt2 = new int[2];
        arrayOfInt2[0] = j;
        arrayOfInt2[1] = paramArrayOfInt[1];
        return new FinderPattern(n, arrayOfInt2, i1, i2, paramInt);
        i--;
        break;
      }
      arrayOfInt1[m] = arrayOfInt1[(m - 1)];
    }
  }

  public Result decodeRow(int paramInt, BitArray paramBitArray, Hashtable paramHashtable)
    throws NotFoundException
  {
    Pair localPair1 = decodePair(paramBitArray, false, paramInt, paramHashtable);
    addOrTally(this.possibleLeftPairs, localPair1);
    paramBitArray.reverse();
    Pair localPair2 = decodePair(paramBitArray, true, paramInt, paramHashtable);
    addOrTally(this.possibleRightPairs, localPair2);
    paramBitArray.reverse();
    int i = this.possibleLeftPairs.size();
    int j = this.possibleRightPairs.size();
    int k = 0;
    if (k >= i)
      throw NotFoundException.getNotFoundInstance();
    Pair localPair3 = (Pair)this.possibleLeftPairs.elementAt(k);
    if (localPair3.getCount() > 1);
    for (int m = 0; ; m++)
    {
      if (m >= j)
      {
        k++;
        break;
      }
      Pair localPair4 = (Pair)this.possibleRightPairs.elementAt(m);
      if ((localPair4.getCount() > 1) && (checkChecksum(localPair3, localPair4)))
        return constructResult(localPair3, localPair4);
    }
  }

  public void reset()
  {
    this.possibleLeftPairs.setSize(0);
    this.possibleRightPairs.setSize(0);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.RSS14Reader
 * JD-Core Version:    0.6.0
 */