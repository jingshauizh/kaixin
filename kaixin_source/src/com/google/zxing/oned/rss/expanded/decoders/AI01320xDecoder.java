package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

final class AI01320xDecoder extends AI013x0xDecoder
{
  AI01320xDecoder(BitArray paramBitArray)
  {
    super(paramBitArray);
  }

  protected void addWeightCode(StringBuffer paramStringBuffer, int paramInt)
  {
    if (paramInt < 10000)
    {
      paramStringBuffer.append("(3202)");
      return;
    }
    paramStringBuffer.append("(3203)");
  }

  protected int checkWeight(int paramInt)
  {
    if (paramInt < 10000)
      return paramInt;
    return paramInt - 10000;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.expanded.decoders.AI01320xDecoder
 * JD-Core Version:    0.6.0
 */