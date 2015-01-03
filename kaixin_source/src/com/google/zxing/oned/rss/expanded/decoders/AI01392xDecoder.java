package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

final class AI01392xDecoder extends AI01decoder
{
  private static final int headerSize = 8;
  private static final int lastDigitSize = 2;

  AI01392xDecoder(BitArray paramBitArray)
  {
    super(paramBitArray);
  }

  public String parseInformation()
    throws NotFoundException
  {
    if (this.information.size < 48)
      throw NotFoundException.getNotFoundInstance();
    StringBuffer localStringBuffer = new StringBuffer();
    encodeCompressedGtin(localStringBuffer, 8);
    int i = this.generalDecoder.extractNumericValueFromBitArray(48, 2);
    localStringBuffer.append("(392");
    localStringBuffer.append(i);
    localStringBuffer.append(')');
    localStringBuffer.append(this.generalDecoder.decodeGeneralPurposeField(50, null).getNewString());
    return localStringBuffer.toString();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.expanded.decoders.AI01392xDecoder
 * JD-Core Version:    0.6.0
 */