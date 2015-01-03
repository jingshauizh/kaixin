package com.google.zxing.qrcode.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import java.util.Vector;

final class AlignmentPatternFinder
{
  private final int[] crossCheckStateCount;
  private final int height;
  private final BitMatrix image;
  private final float moduleSize;
  private final Vector possibleCenters;
  private final ResultPointCallback resultPointCallback;
  private final int startX;
  private final int startY;
  private final int width;

  AlignmentPatternFinder(BitMatrix paramBitMatrix, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, ResultPointCallback paramResultPointCallback)
  {
    this.image = paramBitMatrix;
    this.possibleCenters = new Vector(5);
    this.startX = paramInt1;
    this.startY = paramInt2;
    this.width = paramInt3;
    this.height = paramInt4;
    this.moduleSize = paramFloat;
    this.crossCheckStateCount = new int[3];
    this.resultPointCallback = paramResultPointCallback;
  }

  private static float centerFromEnd(int[] paramArrayOfInt, int paramInt)
  {
    return paramInt - paramArrayOfInt[2] - paramArrayOfInt[1] / 2.0F;
  }

  private float crossCheckVertical(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    BitMatrix localBitMatrix = this.image;
    int i = localBitMatrix.getHeight();
    int[] arrayOfInt = this.crossCheckStateCount;
    arrayOfInt[0] = 0;
    arrayOfInt[1] = 0;
    arrayOfInt[2] = 0;
    int j = paramInt1;
    if ((j < 0) || (!localBitMatrix.get(paramInt2, j)) || (arrayOfInt[1] > paramInt3))
      if ((j >= 0) && (arrayOfInt[1] <= paramInt3))
        break label106;
    label106: int k;
    label143: 
    do
    {
      do
      {
        return (0.0F / 0.0F);
        arrayOfInt[1] = (1 + arrayOfInt[1]);
        j--;
        break;
        do
        {
          arrayOfInt[0] = (1 + arrayOfInt[0]);
          j--;
        }
        while ((j >= 0) && (!localBitMatrix.get(paramInt2, j)) && (arrayOfInt[0] <= paramInt3));
      }
      while (arrayOfInt[0] > paramInt3);
      k = paramInt1 + 1;
      if ((k < i) && (localBitMatrix.get(paramInt2, k)) && (arrayOfInt[1] <= paramInt3))
        break label264;
    }
    while ((k == i) || (arrayOfInt[1] > paramInt3));
    while (true)
    {
      if ((k >= i) || (localBitMatrix.get(paramInt2, k)) || (arrayOfInt[2] > paramInt3))
      {
        if ((arrayOfInt[2] > paramInt3) || (5 * Math.abs(arrayOfInt[0] + arrayOfInt[1] + arrayOfInt[2] - paramInt4) >= paramInt4 * 2) || (!foundPatternCross(arrayOfInt)))
          break;
        return centerFromEnd(arrayOfInt, k);
        label264: arrayOfInt[1] = (1 + arrayOfInt[1]);
        k++;
        break label143;
      }
      arrayOfInt[2] = (1 + arrayOfInt[2]);
      k++;
    }
  }

  private boolean foundPatternCross(int[] paramArrayOfInt)
  {
    float f1 = this.moduleSize;
    float f2 = f1 / 2.0F;
    for (int i = 0; ; i++)
    {
      if (i >= 3)
        return true;
      if (Math.abs(f1 - paramArrayOfInt[i]) >= f2)
        return false;
    }
  }

  private AlignmentPattern handlePossibleCenter(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfInt[0] + paramArrayOfInt[1] + paramArrayOfInt[2];
    float f1 = centerFromEnd(paramArrayOfInt, paramInt2);
    float f2 = crossCheckVertical(paramInt1, (int)f1, 2 * paramArrayOfInt[1], i);
    float f3;
    int j;
    if (!Float.isNaN(f2))
    {
      f3 = (paramArrayOfInt[0] + paramArrayOfInt[1] + paramArrayOfInt[2]) / 3.0F;
      j = this.possibleCenters.size();
    }
    for (int k = 0; ; k++)
    {
      if (k >= j)
      {
        AlignmentPattern localAlignmentPattern = new AlignmentPattern(f1, f2, f3);
        this.possibleCenters.addElement(localAlignmentPattern);
        if (this.resultPointCallback != null)
          this.resultPointCallback.foundPossibleResultPoint(localAlignmentPattern);
        return null;
      }
      if (((AlignmentPattern)this.possibleCenters.elementAt(k)).aboutEquals(f3, f2, f1))
        return new AlignmentPattern(f1, f2, f3);
    }
  }

  AlignmentPattern find()
    throws NotFoundException
  {
    int i = this.startX;
    int j = this.height;
    int k = i + this.width;
    int m = this.startY + (j >> 1);
    int[] arrayOfInt = new int[3];
    int n = 0;
    AlignmentPattern localAlignmentPattern;
    if (n >= j)
    {
      if (!this.possibleCenters.isEmpty())
      {
        localAlignmentPattern = (AlignmentPattern)this.possibleCenters.elementAt(0);
        label64: return localAlignmentPattern;
      }
    }
    else
    {
      int i1;
      label82: int i2;
      if ((n & 0x1) == 0)
      {
        i1 = n + 1 >> 1;
        i2 = m + i1;
        arrayOfInt[0] = 0;
        arrayOfInt[1] = 0;
        arrayOfInt[2] = 0;
      }
      int i4;
      for (int i3 = i; ; i3++)
      {
        if ((i3 < k) && (!this.image.get(i3, i2)))
          continue;
        i4 = 0;
        label130: if (i3 < k)
          break label185;
        if (foundPatternCross(arrayOfInt))
        {
          localAlignmentPattern = handlePossibleCenter(arrayOfInt, i2, k);
          if (localAlignmentPattern != null)
            break label64;
        }
        n++;
        break;
        i1 = -(n + 1 >> 1);
        break label82;
      }
      label185: if (this.image.get(i3, i2))
        if (i4 == 1)
          arrayOfInt[i4] = (1 + arrayOfInt[i4]);
      while (true)
      {
        i3++;
        break label130;
        if (i4 == 2)
        {
          if (foundPatternCross(arrayOfInt))
          {
            localAlignmentPattern = handlePossibleCenter(arrayOfInt, i2, i3);
            if (localAlignmentPattern != null)
              break;
          }
          arrayOfInt[0] = arrayOfInt[2];
          arrayOfInt[1] = 1;
          arrayOfInt[2] = 0;
          i4 = 1;
          continue;
        }
        i4++;
        arrayOfInt[i4] = (1 + arrayOfInt[i4]);
        continue;
        if (i4 == 1)
          i4++;
        arrayOfInt[i4] = (1 + arrayOfInt[i4]);
      }
    }
    throw NotFoundException.getNotFoundInstance();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.detector.AlignmentPatternFinder
 * JD-Core Version:    0.6.0
 */