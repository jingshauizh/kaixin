package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

final class AI01AndOtherAIs extends AI01decoder
{
  private static final int HEADER_SIZE = 4;

  AI01AndOtherAIs(BitArray paramBitArray)
  {
    super(paramBitArray);
  }

  public String parseInformation()
    throws NotFoundException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("(01)");
    int i = localStringBuffer.length();
    localStringBuffer.append(this.generalDecoder.extractNumericValueFromBitArray(4, 4));
    encodeCompressedGtinWithoutAI(localStringBuffer, 8, i);
    return this.generalDecoder.decodeAllCodes(localStringBuffer, 48);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.expanded.decoders.AI01AndOtherAIs
 * JD-Core Version:    0.6.0
 */