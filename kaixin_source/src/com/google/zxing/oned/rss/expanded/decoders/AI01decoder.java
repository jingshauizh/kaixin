package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

abstract class AI01decoder extends AbstractExpandedDecoder
{
  protected static final int gtinSize = 40;

  AI01decoder(BitArray paramBitArray)
  {
    super(paramBitArray);
  }

  private static void appendCheckDigit(StringBuffer paramStringBuffer, int paramInt)
  {
    int i = 0;
    for (int j = 0; ; j++)
    {
      if (j >= 13)
      {
        int m = 10 - i % 10;
        if (m == 10)
          m = 0;
        paramStringBuffer.append(m);
        return;
      }
      int k = '￐' + paramStringBuffer.charAt(j + paramInt);
      if ((j & 0x1) == 0)
        k *= 3;
      i += k;
    }
  }

  protected void encodeCompressedGtin(StringBuffer paramStringBuffer, int paramInt)
  {
    paramStringBuffer.append("(01)");
    int i = paramStringBuffer.length();
    paramStringBuffer.append('9');
    encodeCompressedGtinWithoutAI(paramStringBuffer, paramInt, i);
  }

  protected void encodeCompressedGtinWithoutAI(StringBuffer paramStringBuffer, int paramInt1, int paramInt2)
  {
    for (int i = 0; ; i++)
    {
      if (i >= 4)
      {
        appendCheckDigit(paramStringBuffer, paramInt2);
        return;
      }
      int j = this.generalDecoder.extractNumericValueFromBitArray(paramInt1 + i * 10, 10);
      if (j / 100 == 0)
        paramStringBuffer.append('0');
      if (j / 10 == 0)
        paramStringBuffer.append('0');
      paramStringBuffer.append(j);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.expanded.decoders.AI01decoder
 * JD-Core Version:    0.6.0
 */