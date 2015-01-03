package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

abstract class AI01weightDecoder extends AI01decoder
{
  AI01weightDecoder(BitArray paramBitArray)
  {
    super(paramBitArray);
  }

  protected abstract void addWeightCode(StringBuffer paramStringBuffer, int paramInt);

  protected abstract int checkWeight(int paramInt);

  protected void encodeCompressedWeight(StringBuffer paramStringBuffer, int paramInt1, int paramInt2)
  {
    int i = this.generalDecoder.extractNumericValueFromBitArray(paramInt1, paramInt2);
    addWeightCode(paramStringBuffer, i);
    int j = checkWeight(i);
    int k = 100000;
    for (int m = 0; ; m++)
    {
      if (m >= 5)
      {
        paramStringBuffer.append(j);
        return;
      }
      if (j / k == 0)
        paramStringBuffer.append('0');
      k /= 10;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.expanded.decoders.AI01weightDecoder
 * JD-Core Version:    0.6.0
 */