package com.google.zxing.oned.rss.expanded;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;
import com.google.zxing.oned.rss.RSSUtils;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;
import java.util.Hashtable;
import java.util.Vector;

public final class RSSExpandedReader extends AbstractRSSReader
{
  private static final int[] EVEN_TOTAL_SUBSET;
  private static final int[][] FINDER_PATTERNS;
  private static final int[][] FINDER_PATTERN_SEQUENCES;
  private static final int FINDER_PAT_A = 0;
  private static final int FINDER_PAT_B = 1;
  private static final int FINDER_PAT_C = 2;
  private static final int FINDER_PAT_D = 3;
  private static final int FINDER_PAT_E = 4;
  private static final int FINDER_PAT_F = 5;
  private static final int[] GSUM;
  private static final int LONGEST_SEQUENCE_SIZE = 0;
  private static final int MAX_PAIRS = 11;
  private static final int[] SYMBOL_WIDEST = { 7, 5, 4, 3, 1 };
  private static final int[][] WEIGHTS;
  private final int[] currentSequence = new int[LONGEST_SEQUENCE_SIZE];
  private final Vector pairs = new Vector(11);
  private final int[] startEnd = new int[2];

  static
  {
    EVEN_TOTAL_SUBSET = new int[] { 4, 20, 52, 104, 204 };
    int[] arrayOfInt1 = new int[5];
    arrayOfInt1[1] = 348;
    arrayOfInt1[2] = 1388;
    arrayOfInt1[3] = 2948;
    arrayOfInt1[4] = 3988;
    GSUM = arrayOfInt1;
    FINDER_PATTERNS = new int[][] { { 1, 8, 4, 1 }, { 3, 6, 4, 1 }, { 3, 4, 6, 1 }, { 3, 2, 8, 1 }, { 2, 6, 5, 1 }, { 2, 2, 9, 1 } };
    WEIGHTS = new int[][] { { 1, 3, 9, 27, 81, 32, 96, 77 }, { 20, 60, 180, 118, 143, 7, 21, 63 }, { 189, 145, 13, 39, 117, 140, 209, 205 }, { 193, 157, 49, 147, 19, 57, 171, 91 }, { 62, 186, 136, 197, 169, 85, 44, 132 }, { 185, 133, 188, 142, 4, 12, 36, 108 }, { 113, 128, 173, 97, 80, 29, 87, 50 }, { 150, 28, 84, 41, 123, 158, 52, 156 }, { 46, 138, 203, 187, 139, 206, 196, 166 }, { 76, 17, 51, 153, 37, 111, 122, 155 }, { 43, 129, 176, 106, 107, 110, 119, 146 }, { 16, 48, 144, 10, 30, 90, 59, 177 }, { 109, 116, 137, 200, 178, 112, 125, 164 }, { 70, 210, 208, 202, 184, 130, 179, 115 }, { 134, 191, 151, 31, 93, 68, 204, 190 }, { 148, 22, 66, 198, 172, 94, 71, 2 }, { 6, 18, 54, 162, 64, 192, 154, 40 }, { 120, 149, 25, 75, 14, 42, 126, 167 }, { 79, 26, 78, 23, 69, 207, 199, 175 }, { 103, 98, 83, 38, 114, 131, 182, 124 }, { 161, 61, 183, 127, 170, 88, 53, 159 }, { 55, 165, 73, 8, 24, 72, 5, 15 }, { 45, 135, 194, 160, 58, 174, 100, 89 } };
    int[][] arrayOfInt = new int[10][];
    arrayOfInt[0] = new int[2];
    int[] arrayOfInt2 = new int[3];
    arrayOfInt2[1] = 1;
    arrayOfInt2[2] = 1;
    arrayOfInt[1] = arrayOfInt2;
    int[] arrayOfInt3 = new int[4];
    arrayOfInt3[1] = 2;
    arrayOfInt3[2] = 1;
    arrayOfInt3[3] = 3;
    arrayOfInt[2] = arrayOfInt3;
    int[] arrayOfInt4 = new int[5];
    arrayOfInt4[1] = 4;
    arrayOfInt4[2] = 1;
    arrayOfInt4[3] = 3;
    arrayOfInt4[4] = 2;
    arrayOfInt[3] = arrayOfInt4;
    int[] arrayOfInt5 = new int[6];
    arrayOfInt5[1] = 4;
    arrayOfInt5[2] = 1;
    arrayOfInt5[3] = 3;
    arrayOfInt5[4] = 3;
    arrayOfInt5[5] = 5;
    arrayOfInt[4] = arrayOfInt5;
    int[] arrayOfInt6 = new int[7];
    arrayOfInt6[1] = 4;
    arrayOfInt6[2] = 1;
    arrayOfInt6[3] = 3;
    arrayOfInt6[4] = 4;
    arrayOfInt6[5] = 5;
    arrayOfInt6[6] = 5;
    arrayOfInt[5] = arrayOfInt6;
    int[] arrayOfInt7 = new int[8];
    arrayOfInt7[2] = 1;
    arrayOfInt7[3] = 1;
    arrayOfInt7[4] = 2;
    arrayOfInt7[5] = 2;
    arrayOfInt7[6] = 3;
    arrayOfInt7[7] = 3;
    arrayOfInt[6] = arrayOfInt7;
    int[] arrayOfInt8 = new int[9];
    arrayOfInt8[2] = 1;
    arrayOfInt8[3] = 1;
    arrayOfInt8[4] = 2;
    arrayOfInt8[5] = 2;
    arrayOfInt8[6] = 3;
    arrayOfInt8[7] = 4;
    arrayOfInt8[8] = 4;
    arrayOfInt[7] = arrayOfInt8;
    int[] arrayOfInt9 = new int[10];
    arrayOfInt9[2] = 1;
    arrayOfInt9[3] = 1;
    arrayOfInt9[4] = 2;
    arrayOfInt9[5] = 2;
    arrayOfInt9[6] = 3;
    arrayOfInt9[7] = 4;
    arrayOfInt9[8] = 5;
    arrayOfInt9[9] = 5;
    arrayOfInt[8] = arrayOfInt9;
    int[] arrayOfInt10 = new int[11];
    arrayOfInt10[2] = 1;
    arrayOfInt10[3] = 1;
    arrayOfInt10[4] = 2;
    arrayOfInt10[5] = 3;
    arrayOfInt10[6] = 3;
    arrayOfInt10[7] = 4;
    arrayOfInt10[8] = 4;
    arrayOfInt10[9] = 5;
    arrayOfInt10[10] = 5;
    arrayOfInt[9] = arrayOfInt10;
    FINDER_PATTERN_SEQUENCES = arrayOfInt;
    LONGEST_SEQUENCE_SIZE = FINDER_PATTERN_SEQUENCES[(-1 + FINDER_PATTERN_SEQUENCES.length)].length;
  }

  private void adjustOddEvenCounts(int paramInt)
    throws NotFoundException
  {
    int i = count(this.oddCounts);
    int j = count(this.evenCounts);
    int k = i + j - paramInt;
    int m;
    int i1;
    int i2;
    label61: int i4;
    int i5;
    if ((i & 0x1) == 1)
    {
      m = 1;
      int n = j & 0x1;
      i1 = 0;
      if (n == 0)
        i1 = 1;
      i2 = 0;
      if (i <= 13)
        break label99;
      i3 = 1;
      i4 = 0;
      if (j <= 13)
        break label119;
      i5 = 1;
    }
    while (true)
    {
      if (k != 1)
        break label171;
      if (m == 0)
        break label156;
      if (i1 == 0)
        break label139;
      throw NotFoundException.getNotFoundInstance();
      m = 0;
      break;
      label99: i3 = 0;
      i2 = 0;
      if (i >= 4)
        break label61;
      i2 = 1;
      i3 = 0;
      break label61;
      label119: i5 = 0;
      i4 = 0;
      if (j >= 4)
        continue;
      i4 = 1;
      i5 = 0;
    }
    label139: int i3 = 1;
    while (true)
    {
      if (i2 == 0)
        break label278;
      if (i3 == 0)
        break label267;
      throw NotFoundException.getNotFoundInstance();
      label156: if (i1 == 0)
        throw NotFoundException.getNotFoundInstance();
      i5 = 1;
      continue;
      label171: if (k == -1)
      {
        if (m != 0)
        {
          if (i1 != 0)
            throw NotFoundException.getNotFoundInstance();
          i2 = 1;
          continue;
        }
        if (i1 == 0)
          throw NotFoundException.getNotFoundInstance();
        i4 = 1;
        continue;
      }
      if (k != 0)
        break;
      if (m != 0)
      {
        if (i1 == 0)
          throw NotFoundException.getNotFoundInstance();
        if (i < j)
        {
          i2 = 1;
          i5 = 1;
          continue;
        }
        i3 = 1;
        i4 = 1;
        continue;
      }
      if (i1 == 0)
        continue;
      throw NotFoundException.getNotFoundInstance();
    }
    throw NotFoundException.getNotFoundInstance();
    label267: increment(this.oddCounts, this.oddRoundingErrors);
    label278: if (i3 != 0)
      decrement(this.oddCounts, this.oddRoundingErrors);
    if (i4 != 0)
    {
      if (i5 != 0)
        throw NotFoundException.getNotFoundInstance();
      increment(this.evenCounts, this.oddRoundingErrors);
    }
    if (i5 != 0)
      decrement(this.evenCounts, this.evenRoundingErrors);
  }

  private boolean checkChecksum()
  {
    ExpandedPair localExpandedPair1 = (ExpandedPair)this.pairs.elementAt(0);
    DataCharacter localDataCharacter = localExpandedPair1.getLeftChar();
    int i = localExpandedPair1.getRightChar().getChecksumPortion();
    int j = 2;
    for (int k = 1; ; k++)
    {
      if (k >= this.pairs.size())
      {
        int m = i % 211 + 211 * (j - 4);
        int n = localDataCharacter.getValue();
        int i1 = 0;
        if (m == n)
          i1 = 1;
        return i1;
      }
      ExpandedPair localExpandedPair2 = (ExpandedPair)this.pairs.elementAt(k);
      i += localExpandedPair2.getLeftChar().getChecksumPortion();
      j++;
      if (localExpandedPair2.getRightChar() == null)
        continue;
      i += localExpandedPair2.getRightChar().getChecksumPortion();
      j++;
    }
  }

  private boolean checkPairSequence(Vector paramVector, FinderPattern paramFinderPattern)
    throws NotFoundException
  {
    int i = 1 + paramVector.size();
    if (i > this.currentSequence.length)
      throw NotFoundException.getNotFoundInstance();
    int j = 0;
    if (j >= paramVector.size())
      this.currentSequence[(i - 1)] = paramFinderPattern.getValue();
    label158: for (int k = 0; ; k++)
    {
      if (k >= FINDER_PATTERN_SEQUENCES.length)
      {
        throw NotFoundException.getNotFoundInstance();
        this.currentSequence[j] = ((ExpandedPair)paramVector.elementAt(j)).getFinderPattern().getValue();
        j++;
        break;
      }
      int[] arrayOfInt = FINDER_PATTERN_SEQUENCES[k];
      if (arrayOfInt.length < i)
        continue;
      int m = 1;
      int n = 0;
      while (true)
      {
        if (n >= i);
        while (true)
        {
          if (m == 0)
            break label158;
          if (i == arrayOfInt.length)
          {
            return true;
            if (this.currentSequence[n] != arrayOfInt[n])
            {
              m = 0;
              continue;
            }
            n++;
            break;
          }
        }
        return false;
      }
    }
  }

  private static Result constructResult(Vector paramVector)
    throws NotFoundException
  {
    String str = AbstractExpandedDecoder.createDecoder(BitArrayBuilder.buildBitArray(paramVector)).parseInformation();
    ResultPoint[] arrayOfResultPoint1 = ((ExpandedPair)paramVector.elementAt(0)).getFinderPattern().getResultPoints();
    ResultPoint[] arrayOfResultPoint2 = ((ExpandedPair)paramVector.lastElement()).getFinderPattern().getResultPoints();
    ResultPoint[] arrayOfResultPoint3 = new ResultPoint[4];
    arrayOfResultPoint3[0] = arrayOfResultPoint1[0];
    arrayOfResultPoint3[1] = arrayOfResultPoint1[1];
    arrayOfResultPoint3[2] = arrayOfResultPoint2[0];
    arrayOfResultPoint3[3] = arrayOfResultPoint2[1];
    return new Result(str, null, arrayOfResultPoint3, BarcodeFormat.RSS_EXPANDED);
  }

  private void findNextPair(BitArray paramBitArray, Vector paramVector, int paramInt)
    throws NotFoundException
  {
    int[] arrayOfInt = this.decodeFinderCounters;
    arrayOfInt[0] = 0;
    arrayOfInt[1] = 0;
    arrayOfInt[2] = 0;
    arrayOfInt[3] = 0;
    int i = paramBitArray.getSize();
    int j;
    int k;
    label51: label61: int n;
    int i1;
    if (paramInt >= 0)
    {
      j = paramInt;
      if (paramVector.size() % 2 == 0)
        break label116;
      k = 1;
      m = 0;
      if (j < i)
        break label122;
      n = 0;
      i1 = j;
    }
    for (int i2 = j; ; i2++)
    {
      if (i2 >= i)
      {
        throw NotFoundException.getNotFoundInstance();
        if (paramVector.isEmpty())
        {
          j = 0;
          break;
        }
        j = ((ExpandedPair)paramVector.lastElement()).getFinderPattern().getStartEnd()[1];
        break;
        label116: k = 0;
        break label51;
        label122: if (paramBitArray.get(j));
        for (m = 0; ; m = 1)
        {
          if (m == 0)
            break label149;
          j++;
          break;
        }
        label149: break label61;
      }
      if ((m ^ paramBitArray.get(i2)) == 0)
        break label181;
      arrayOfInt[n] = (1 + arrayOfInt[n]);
    }
    label181: if (n == 3)
    {
      if (k != 0)
        reverseCounters(arrayOfInt);
      if (isFinderPattern(arrayOfInt))
      {
        this.startEnd[0] = i1;
        this.startEnd[1] = i2;
        return;
      }
      if (k != 0)
        reverseCounters(arrayOfInt);
      i1 += arrayOfInt[0] + arrayOfInt[1];
      arrayOfInt[0] = arrayOfInt[2];
      arrayOfInt[1] = arrayOfInt[3];
      arrayOfInt[2] = 0;
      arrayOfInt[3] = 0;
      n--;
      arrayOfInt[n] = 1;
      if (m == 0)
        break label298;
    }
    label275: label298: for (int m = 0; ; m = 1)
    {
      break;
      n++;
      break label275;
    }
  }

  private static int getNextSecondBar(BitArray paramBitArray, int paramInt)
  {
    int i = paramInt;
    boolean bool = paramBitArray.get(i);
    int j;
    if ((i >= paramBitArray.size) || (paramBitArray.get(i) != bool))
    {
      if (!bool)
        break label58;
      j = 0;
    }
    while (true)
    {
      if ((i >= paramBitArray.size) || (paramBitArray.get(i) != j))
      {
        return i;
        i++;
        break;
        label58: j = 1;
        continue;
      }
      i++;
    }
  }

  private static boolean isNotA1left(FinderPattern paramFinderPattern, boolean paramBoolean1, boolean paramBoolean2)
  {
    return (paramFinderPattern.getValue() != 0) || (!paramBoolean1) || (!paramBoolean2);
  }

  private FinderPattern parseFoundFinderPattern(BitArray paramBitArray, int paramInt, boolean paramBoolean)
  {
    int i2;
    if (paramBoolean)
      i2 = -1 + this.startEnd[0];
    while (true)
    {
      int m;
      int i;
      int k;
      int[] arrayOfInt;
      int n;
      if ((i2 < 0) || (paramBitArray.get(i2)))
      {
        int i3 = i2 + 1;
        m = this.startEnd[0] - i3;
        i = i3;
        k = this.startEnd[1];
        arrayOfInt = this.decodeFinderCounters;
        n = -1 + arrayOfInt.length;
        if (n > 0)
          break label185;
        arrayOfInt[0] = m;
      }
      try
      {
        int i1 = parseFinderValue(arrayOfInt, FINDER_PATTERNS);
        return new FinderPattern(i1, new int[] { i, k }, i, k, paramInt);
        i2--;
        continue;
        i = this.startEnd[0];
        for (int j = 1 + this.startEnd[1]; ; j++)
        {
          if ((paramBitArray.get(j)) && (j < paramBitArray.size))
            continue;
          k = j;
          m = k - this.startEnd[1];
          break;
        }
        label185: arrayOfInt[n] = arrayOfInt[(n - 1)];
        n--;
      }
      catch (NotFoundException localNotFoundException)
      {
      }
    }
    return null;
  }

  private static void reverseCounters(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    for (int j = 0; ; j++)
    {
      if (j >= i / 2)
        return;
      int k = paramArrayOfInt[j];
      paramArrayOfInt[j] = paramArrayOfInt[(-1 + (i - j))];
      paramArrayOfInt[(-1 + (i - j))] = k;
    }
  }

  DataCharacter decodeDataCharacter(BitArray paramBitArray, FinderPattern paramFinderPattern, boolean paramBoolean1, boolean paramBoolean2)
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
    float f1;
    int[] arrayOfInt2;
    int[] arrayOfInt3;
    float[] arrayOfFloat1;
    float[] arrayOfFloat2;
    int m;
    int i3;
    label133: int i5;
    label148: int i6;
    int i7;
    int i8;
    int i9;
    label170: int i10;
    int i11;
    if (paramBoolean2)
    {
      recordPatternInReverse(paramBitArray, paramFinderPattern.getStartEnd()[0], arrayOfInt1);
      f1 = count(arrayOfInt1) / 17;
      arrayOfInt2 = this.oddCounts;
      arrayOfInt3 = this.evenCounts;
      arrayOfFloat1 = this.oddRoundingErrors;
      arrayOfFloat2 = this.evenRoundingErrors;
      m = 0;
      if (m < arrayOfInt1.length)
        break label288;
      adjustOddEvenCounts(17);
      int i2 = 4 * paramFinderPattern.getValue();
      if (!paramBoolean1)
        break label390;
      i3 = 0;
      int i4 = i2 + i3;
      if (!paramBoolean2)
        break label396;
      i5 = 0;
      i6 = -1 + (i5 + i4);
      i7 = 0;
      i8 = 0;
      i9 = -1 + arrayOfInt2.length;
      if (i9 >= 0)
        break label402;
      i10 = 0;
      i11 = 0;
    }
    int i13;
    for (int i12 = -1 + arrayOfInt3.length; ; i12--)
    {
      if (i12 < 0)
      {
        i13 = i8 + i10;
        if (((i7 & 0x1) == 0) && (i7 <= 13) && (i7 >= 4))
          break label500;
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
        label288: float f2 = 1.0F * arrayOfInt1[m] / f1;
        int n = (int)(0.5F + f2);
        label318: int i1;
        if (n < 1)
        {
          n = 1;
          i1 = m >> 1;
          if ((m & 0x1) != 0)
            break label369;
          arrayOfInt2[i1] = n;
          arrayOfFloat1[i1] = (f2 - n);
        }
        while (true)
        {
          m++;
          break;
          if (n <= 8)
            break label318;
          n = 8;
          break label318;
          label369: arrayOfInt3[i1] = n;
          arrayOfFloat2[i1] = (f2 - n);
        }
        label390: i3 = 2;
        break label133;
        label396: i5 = 1;
        break label148;
        label402: if (isNotA1left(paramFinderPattern, paramBoolean1, paramBoolean2))
          i8 += WEIGHTS[i6][(i9 * 2)] * arrayOfInt2[i9];
        i7 += arrayOfInt2[i9];
        i9--;
        break label170;
      }
      if (isNotA1left(paramFinderPattern, paramBoolean1, paramBoolean2))
        i10 += WEIGHTS[i6][(1 + i12 * 2)] * arrayOfInt3[i12];
      i11 += arrayOfInt3[i12];
    }
    label500: int i14 = (13 - i7) / 2;
    int i15 = SYMBOL_WIDEST[i14];
    int i16 = 9 - i15;
    int i17 = RSSUtils.getRSSvalue(arrayOfInt2, i15, true);
    int i18 = RSSUtils.getRSSvalue(arrayOfInt3, i16, false);
    int i19 = EVEN_TOTAL_SUBSET[i14];
    int i20 = GSUM[i14] + (i18 + i17 * i19);
    DataCharacter localDataCharacter = new DataCharacter(i20, i13);
    return localDataCharacter;
  }

  public Result decodeRow(int paramInt, BitArray paramBitArray, Hashtable paramHashtable)
    throws NotFoundException
  {
    reset();
    decodeRow2pairs(paramInt, paramBitArray);
    return constructResult(this.pairs);
  }

  Vector decodeRow2pairs(int paramInt, BitArray paramBitArray)
    throws NotFoundException
  {
    ExpandedPair localExpandedPair;
    do
    {
      do
      {
        localExpandedPair = retrieveNextPair(paramBitArray, this.pairs, paramInt);
        this.pairs.addElement(localExpandedPair);
      }
      while (!localExpandedPair.mayBeLast());
      if (checkChecksum())
        return this.pairs;
    }
    while (!localExpandedPair.mustBeLast());
    throw NotFoundException.getNotFoundInstance();
  }

  public void reset()
  {
    this.pairs.setSize(0);
  }

  ExpandedPair retrieveNextPair(BitArray paramBitArray, Vector paramVector, int paramInt)
    throws NotFoundException
  {
    boolean bool1;
    if (paramVector.size() % 2 == 0)
      bool1 = true;
    while (true)
    {
      int i = 1;
      int j = -1;
      FinderPattern localFinderPattern;
      boolean bool2;
      DataCharacter localDataCharacter1;
      while (true)
      {
        findNextPair(paramBitArray, paramVector, j);
        localFinderPattern = parseFoundFinderPattern(paramBitArray, paramInt, bool1);
        if (localFinderPattern != null)
          break;
        j = getNextSecondBar(paramBitArray, this.startEnd[0]);
        if (i != 0)
          continue;
        bool2 = checkPairSequence(paramVector, localFinderPattern);
        localDataCharacter1 = decodeDataCharacter(paramBitArray, localFinderPattern, bool1, true);
      }
      try
      {
        DataCharacter localDataCharacter3 = decodeDataCharacter(paramBitArray, localFinderPattern, bool1, false);
        localDataCharacter2 = localDataCharacter3;
        return new ExpandedPair(localDataCharacter1, localDataCharacter2, localFinderPattern, bool2);
        bool1 = false;
        continue;
        i = 0;
      }
      catch (NotFoundException localNotFoundException)
      {
        DataCharacter localDataCharacter2;
        while (bool2)
          localDataCharacter2 = null;
      }
    }
    throw localNotFoundException;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.expanded.RSSExpandedReader
 * JD-Core Version:    0.6.0
 */