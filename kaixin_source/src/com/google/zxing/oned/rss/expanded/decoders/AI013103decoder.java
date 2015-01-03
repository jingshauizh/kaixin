package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

final class AI013103decoder extends AI013x0xDecoder
{
  AI013103decoder(BitArray paramBitArray)
  {
    super(paramBitArray);
  }

  protected void addWeightCode(StringBuffer paramStringBuffer, int paramInt)
  {
    paramStringBuffer.append("(3103)");
  }

  protected int checkWeight(int paramInt)
  {
    return paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.expanded.decoders.AI013103decoder
 * JD-Core Version:    0.6.0
 */