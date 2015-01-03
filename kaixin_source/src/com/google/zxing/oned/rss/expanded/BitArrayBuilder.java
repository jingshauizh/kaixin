package com.google.zxing.oned.rss.expanded;

import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.DataCharacter;
import java.util.Vector;

final class BitArrayBuilder
{
  static BitArray buildBitArray(Vector paramVector)
  {
    int i = -1 + (paramVector.size() << 1);
    if (((ExpandedPair)paramVector.lastElement()).getRightChar() == null)
      i--;
    BitArray localBitArray = new BitArray(i * 12);
    int j = 0;
    int k = ((ExpandedPair)paramVector.elementAt(0)).getRightChar().getValue();
    int n;
    for (int m = 11; ; m--)
    {
      if (m < 0)
      {
        n = 1;
        if (n < paramVector.size())
          break;
        return localBitArray;
      }
      if ((k & 1 << m) != 0)
        localBitArray.set(j);
      j++;
    }
    ExpandedPair localExpandedPair = (ExpandedPair)paramVector.elementAt(n);
    int i1 = localExpandedPair.getLeftChar().getValue();
    int i2 = 11;
    label127: int i3;
    if (i2 < 0)
      if (localExpandedPair.getRightChar() != null)
        i3 = localExpandedPair.getRightChar().getValue();
    for (int i4 = 11; ; i4--)
    {
      if (i4 < 0)
      {
        n++;
        break;
        if ((i1 & 1 << i2) != 0)
          localBitArray.set(j);
        j++;
        i2--;
        break label127;
      }
      if ((i3 & 1 << i4) != 0)
        localBitArray.set(j);
      j++;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.expanded.BitArrayBuilder
 * JD-Core Version:    0.6.0
 */