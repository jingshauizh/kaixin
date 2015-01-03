package com.google.zxing.oned.rss;

public final class RSSUtils
{
  static int combins(int paramInt1, int paramInt2)
  {
    int i;
    int j;
    int k;
    int m;
    int n;
    if (paramInt1 - paramInt2 > paramInt2)
    {
      i = paramInt2;
      j = paramInt1 - paramInt2;
      k = 1;
      m = 1;
      n = paramInt1;
      label22: if (n > j)
        break label46;
    }
    while (true)
    {
      if (m > i)
      {
        return k;
        i = paramInt1 - paramInt2;
        j = paramInt2;
        break;
        label46: k *= n;
        if (m <= i)
        {
          k /= m;
          m++;
        }
        n--;
        break label22;
      }
      k /= m;
      m++;
    }
  }

  static int[] elements(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = new int[2 + paramArrayOfInt.length];
    int i = paramInt2 << 1;
    arrayOfInt[0] = 1;
    int j = 10;
    int k = 1;
    int m = 1;
    if (m >= i - 2)
    {
      arrayOfInt[(i - 1)] = (paramInt1 - k);
      if (arrayOfInt[(i - 1)] < j)
        j = arrayOfInt[(i - 1)];
      if (j <= 1);
    }
    for (int n = 0; ; n += 2)
    {
      if (n >= i)
      {
        return arrayOfInt;
        arrayOfInt[m] = (paramArrayOfInt[(m - 1)] - arrayOfInt[(m - 1)]);
        arrayOfInt[(m + 1)] = (paramArrayOfInt[m] - arrayOfInt[m]);
        k += arrayOfInt[m] + arrayOfInt[(m + 1)];
        if (arrayOfInt[m] < j)
          j = arrayOfInt[m];
        m += 2;
        break;
      }
      arrayOfInt[n] += j - 1;
      int i1 = n + 1;
      arrayOfInt[i1] -= j - 1;
    }
  }

  public static int getRSSvalue(int[] paramArrayOfInt, int paramInt, boolean paramBoolean)
  {
    int i = paramArrayOfInt.length;
    int j = 0;
    int k = 0;
    int m;
    int n;
    if (k >= i)
    {
      m = 0;
      n = 0;
    }
    int i2;
    for (int i1 = 0; ; i1++)
    {
      if (i1 >= i - 1)
      {
        return m;
        j += paramArrayOfInt[k];
        k++;
        break;
      }
      i2 = 1;
      n |= 1 << i1;
      if (i2 < paramArrayOfInt[i1])
        break label84;
      j -= i2;
    }
    label84: int i3 = combins(-1 + (j - i2), -2 + (i - i1));
    if ((paramBoolean) && (n == 0) && (j - i2 - (-1 + (i - i1)) >= -1 + (i - i1)))
      i3 -= combins(j - i2 - (i - i1), -2 + (i - i1));
    int i4;
    int i5;
    if (-1 + (i - i1) > 1)
    {
      i4 = 0;
      i5 = j - i2 - (-2 + (i - i1));
      label186: if (i5 <= paramInt)
        i3 -= i4 * (i - 1 - i1);
    }
    while (true)
    {
      m += i3;
      i2++;
      n &= (0xFFFFFFFF ^ 1 << i1);
      break;
      i4 += combins(-1 + (j - i2 - i5), -3 + (i - i1));
      i5--;
      break label186;
      if (j - i2 <= paramInt)
        continue;
      i3--;
    }
  }

  static int[] getRSSwidths(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    int[] arrayOfInt = new int[paramInt3];
    int i = 0;
    int j = 0;
    if (j >= paramInt3 - 1)
    {
      arrayOfInt[j] = paramInt2;
      return arrayOfInt;
    }
    i |= 1 << j;
    int k = 1;
    while (true)
    {
      int m = combins(-1 + (paramInt2 - k), -2 + (paramInt3 - j));
      if ((paramBoolean) && (i == 0) && (paramInt2 - k - (-1 + (paramInt3 - j)) >= -1 + (paramInt3 - j)))
        m -= combins(paramInt2 - k - (paramInt3 - j), -2 + (paramInt3 - j));
      int n;
      int i1;
      if (-1 + (paramInt3 - j) > 1)
      {
        n = 0;
        i1 = paramInt2 - k - (-2 + (paramInt3 - j));
        label139: if (i1 <= paramInt4)
          m -= n * (paramInt3 - 1 - j);
      }
      while (true)
      {
        paramInt1 -= m;
        if (paramInt1 >= 0)
          break label235;
        paramInt1 += m;
        paramInt2 -= k;
        arrayOfInt[j] = k;
        j++;
        break;
        n += combins(-1 + (paramInt2 - k - i1), -3 + (paramInt3 - j));
        i1--;
        break label139;
        if (paramInt2 - k <= paramInt4)
          continue;
        m--;
      }
      label235: k++;
      i &= (0xFFFFFFFF ^ 1 << j);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.RSSUtils
 * JD-Core Version:    0.6.0
 */