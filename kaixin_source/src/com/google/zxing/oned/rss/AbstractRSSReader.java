package com.google.zxing.oned.rss;

import com.google.zxing.NotFoundException;
import com.google.zxing.oned.OneDReader;

public abstract class AbstractRSSReader extends OneDReader
{
  private static final int MAX_AVG_VARIANCE = 51;
  private static final float MAX_FINDER_PATTERN_RATIO = 0.8928571F;
  private static final int MAX_INDIVIDUAL_VARIANCE = 102;
  private static final float MIN_FINDER_PATTERN_RATIO = 0.7916667F;
  protected final int[] dataCharacterCounters = new int[8];
  protected final int[] decodeFinderCounters = new int[4];
  protected final int[] evenCounts = new int[this.dataCharacterCounters.length / 2];
  protected final float[] evenRoundingErrors = new float[4];
  protected final int[] oddCounts = new int[this.dataCharacterCounters.length / 2];
  protected final float[] oddRoundingErrors = new float[4];

  protected static int count(int[] paramArrayOfInt)
  {
    int i = 0;
    for (int j = 0; ; j++)
    {
      if (j >= paramArrayOfInt.length)
        return i;
      i += paramArrayOfInt[j];
    }
  }

  protected static void decrement(int[] paramArrayOfInt, float[] paramArrayOfFloat)
  {
    int i = 0;
    float f = paramArrayOfFloat[0];
    for (int j = 1; ; j++)
    {
      if (j >= paramArrayOfInt.length)
      {
        paramArrayOfInt[i] = (-1 + paramArrayOfInt[i]);
        return;
      }
      if (paramArrayOfFloat[j] >= f)
        continue;
      f = paramArrayOfFloat[j];
      i = j;
    }
  }

  protected static void increment(int[] paramArrayOfInt, float[] paramArrayOfFloat)
  {
    int i = 0;
    float f = paramArrayOfFloat[0];
    for (int j = 1; ; j++)
    {
      if (j >= paramArrayOfInt.length)
      {
        paramArrayOfInt[i] = (1 + paramArrayOfInt[i]);
        return;
      }
      if (paramArrayOfFloat[j] <= f)
        continue;
      f = paramArrayOfFloat[j];
      i = j;
    }
  }

  protected static boolean isFinderPattern(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0] + paramArrayOfInt[1];
    int j = i + paramArrayOfInt[2] + paramArrayOfInt[3];
    float f = i / j;
    if ((f >= 0.7916667F) && (f <= 0.8928571F))
    {
      int k = 2147483647;
      int m = -2147483648;
      for (int n = 0; ; n++)
      {
        if (n >= paramArrayOfInt.length)
        {
          if (m >= k * 10)
            break;
          return true;
        }
        int i1 = paramArrayOfInt[n];
        if (i1 > m)
          m = i1;
        if (i1 >= k)
          continue;
        k = i1;
      }
      return false;
    }
    return false;
  }

  protected static int parseFinderValue(int[] paramArrayOfInt, int[][] paramArrayOfInt1)
    throws NotFoundException
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfInt1.length)
        throw NotFoundException.getNotFoundInstance();
      if (patternMatchVariance(paramArrayOfInt, paramArrayOfInt1[i], 102) < 51)
        return i;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.AbstractRSSReader
 * JD-Core Version:    0.6.0
 */