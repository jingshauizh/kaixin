package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

final class GeneralAppIdDecoder
{
  private final StringBuffer buffer = new StringBuffer();
  private final CurrentParsingState current = new CurrentParsingState();
  private final BitArray information;

  GeneralAppIdDecoder(BitArray paramBitArray)
  {
    this.information = paramBitArray;
  }

  private DecodedChar decodeAlphanumeric(int paramInt)
  {
    int i = extractNumericValueFromBitArray(paramInt, 5);
    if (i == 15)
      return new DecodedChar(paramInt + 5, '$');
    if ((i >= 5) && (i < 15))
      return new DecodedChar(paramInt + 5, (char)(-5 + (i + 48)));
    int j = extractNumericValueFromBitArray(paramInt, 6);
    if ((j >= 32) && (j < 58))
      return new DecodedChar(paramInt + 6, (char)(j + 33));
    switch (j)
    {
    default:
      throw new RuntimeException("Decoding invalid alphanumeric value: " + j);
    case 58:
      return new DecodedChar(paramInt + 6, '*');
    case 59:
      return new DecodedChar(paramInt + 6, ',');
    case 60:
      return new DecodedChar(paramInt + 6, '-');
    case 61:
      return new DecodedChar(paramInt + 6, '.');
    case 62:
    }
    return new DecodedChar(paramInt + 6, '/');
  }

  private DecodedChar decodeIsoIec646(int paramInt)
  {
    int i = extractNumericValueFromBitArray(paramInt, 5);
    if (i == 15)
      return new DecodedChar(paramInt + 5, '$');
    if ((i >= 5) && (i < 15))
      return new DecodedChar(paramInt + 5, (char)(-5 + (i + 48)));
    int j = extractNumericValueFromBitArray(paramInt, 7);
    if ((j >= 64) && (j < 90))
      return new DecodedChar(paramInt + 7, (char)(j + 1));
    if ((j >= 90) && (j < 116))
      return new DecodedChar(paramInt + 7, (char)(j + 7));
    int k = extractNumericValueFromBitArray(paramInt, 8);
    switch (k)
    {
    default:
      throw new RuntimeException("Decoding invalid ISO/IEC 646 value: " + k);
    case 232:
      return new DecodedChar(paramInt + 8, '!');
    case 233:
      return new DecodedChar(paramInt + 8, '"');
    case 234:
      return new DecodedChar(paramInt + 8, '%');
    case 235:
      return new DecodedChar(paramInt + 8, '&');
    case 236:
      return new DecodedChar(paramInt + 8, '\'');
    case 237:
      return new DecodedChar(paramInt + 8, '(');
    case 238:
      return new DecodedChar(paramInt + 8, ')');
    case 239:
      return new DecodedChar(paramInt + 8, '*');
    case 240:
      return new DecodedChar(paramInt + 8, '+');
    case 241:
      return new DecodedChar(paramInt + 8, ',');
    case 242:
      return new DecodedChar(paramInt + 8, '-');
    case 243:
      return new DecodedChar(paramInt + 8, '.');
    case 244:
      return new DecodedChar(paramInt + 8, '/');
    case 245:
      return new DecodedChar(paramInt + 8, ':');
    case 246:
      return new DecodedChar(paramInt + 8, ';');
    case 247:
      return new DecodedChar(paramInt + 8, '<');
    case 248:
      return new DecodedChar(paramInt + 8, '=');
    case 249:
      return new DecodedChar(paramInt + 8, '>');
    case 250:
      return new DecodedChar(paramInt + 8, '?');
    case 251:
      return new DecodedChar(paramInt + 8, '_');
    case 252:
    }
    return new DecodedChar(paramInt + 8, ' ');
  }

  private DecodedNumeric decodeNumeric(int paramInt)
  {
    if (paramInt + 7 > this.information.size)
    {
      int m = extractNumericValueFromBitArray(paramInt, 4);
      if (m == 0)
        return new DecodedNumeric(this.information.size, 10, 10);
      return new DecodedNumeric(this.information.size, m - 1, 10);
    }
    int i = extractNumericValueFromBitArray(paramInt, 7);
    int j = (i - 8) / 11;
    int k = (i - 8) % 11;
    return new DecodedNumeric(paramInt + 7, j, k);
  }

  static int extractNumericValueFromBitArray(BitArray paramBitArray, int paramInt1, int paramInt2)
  {
    if (paramInt2 > 32)
      throw new IllegalArgumentException("extractNumberValueFromBitArray can't handle more than 32 bits");
    int i = 0;
    for (int j = 0; ; j++)
    {
      if (j >= paramInt2)
        return i;
      if (!paramBitArray.get(paramInt1 + j))
        continue;
      i |= 1 << -1 + (paramInt2 - j);
    }
  }

  private boolean isAlphaOr646ToNumericLatch(int paramInt)
  {
    if (paramInt + 3 > this.information.size)
      return false;
    for (int i = paramInt; ; i++)
    {
      if (i >= paramInt + 3)
        return true;
      if (this.information.get(i))
        break;
    }
  }

  private boolean isAlphaTo646ToAlphaLatch(int paramInt)
  {
    if (paramInt + 1 > this.information.size);
    int i;
    do
    {
      return false;
      i = 0;
      if ((i >= 5) || (i + paramInt >= this.information.size))
        return true;
      if (i != 2)
        break;
    }
    while (!this.information.get(paramInt + 2));
    do
    {
      i++;
      break;
    }
    while (!this.information.get(paramInt + i));
    return false;
  }

  private boolean isNumericToAlphaNumericLatch(int paramInt)
  {
    if (paramInt + 1 > this.information.size)
      return false;
    for (int i = 0; ; i++)
    {
      if ((i >= 4) || (i + paramInt >= this.information.size))
        return true;
      if (this.information.get(paramInt + i))
        break;
    }
  }

  private boolean isStillAlpha(int paramInt)
  {
    if (paramInt + 5 > this.information.size);
    int j;
    do
    {
      do
      {
        return false;
        int i = extractNumericValueFromBitArray(paramInt, 5);
        if ((i >= 5) && (i < 16))
          return true;
      }
      while (paramInt + 6 > this.information.size);
      j = extractNumericValueFromBitArray(paramInt, 6);
    }
    while ((j < 16) || (j >= 63));
    return true;
  }

  private boolean isStillIsoIec646(int paramInt)
  {
    if (paramInt + 5 > this.information.size);
    int k;
    do
    {
      do
      {
        do
        {
          return false;
          int i = extractNumericValueFromBitArray(paramInt, 5);
          if ((i >= 5) && (i < 16))
            return true;
        }
        while (paramInt + 7 > this.information.size);
        int j = extractNumericValueFromBitArray(paramInt, 7);
        if ((j >= 64) && (j < 116))
          return true;
      }
      while (paramInt + 8 > this.information.size);
      k = extractNumericValueFromBitArray(paramInt, 8);
    }
    while ((k < 232) || (k >= 253));
    return true;
  }

  private boolean isStillNumeric(int paramInt)
  {
    if (paramInt + 7 > this.information.size)
      return paramInt + 4 <= this.information.size;
    for (int i = paramInt; ; i++)
    {
      if (i >= paramInt + 3)
        return this.information.get(paramInt + 3);
      if (this.information.get(i))
        break;
    }
  }

  private BlockParsedResult parseAlphaBlock()
  {
    if (!isStillAlpha(this.current.position))
    {
      if (!isAlphaOr646ToNumericLatch(this.current.position))
        break label137;
      CurrentParsingState localCurrentParsingState2 = this.current;
      localCurrentParsingState2.position = (3 + localCurrentParsingState2.position);
      this.current.setNumeric();
    }
    label137: 
    do
    {
      return new BlockParsedResult(false);
      DecodedChar localDecodedChar = decodeAlphanumeric(this.current.position);
      this.current.position = localDecodedChar.getNewPosition();
      if (localDecodedChar.isFNC1())
        return new BlockParsedResult(new DecodedInformation(this.current.position, this.buffer.toString()), true);
      this.buffer.append(localDecodedChar.getValue());
      break;
    }
    while (!isAlphaTo646ToAlphaLatch(this.current.position));
    CurrentParsingState localCurrentParsingState1;
    if (5 + this.current.position < this.information.size)
      localCurrentParsingState1 = this.current;
    for (localCurrentParsingState1.position = (5 + localCurrentParsingState1.position); ; this.current.position = this.information.size)
    {
      this.current.setIsoIec646();
      break;
    }
  }

  private DecodedInformation parseBlocks()
  {
    int i = this.current.position;
    BlockParsedResult localBlockParsedResult;
    boolean bool;
    label28: int j;
    if (this.current.isAlpha())
    {
      localBlockParsedResult = parseAlphaBlock();
      bool = localBlockParsedResult.isFinished();
      if (i == this.current.position)
        break label92;
      j = 1;
      label42: if ((j != 0) || (bool))
        break label98;
    }
    while (true)
    {
      return localBlockParsedResult.getDecodedInformation();
      if (this.current.isIsoIec646())
      {
        localBlockParsedResult = parseIsoIec646Block();
        bool = localBlockParsedResult.isFinished();
        break label28;
      }
      localBlockParsedResult = parseNumericBlock();
      bool = localBlockParsedResult.isFinished();
      break label28;
      label92: j = 0;
      break label42;
      label98: if (!bool)
        break;
    }
  }

  private BlockParsedResult parseIsoIec646Block()
  {
    if (!isStillIsoIec646(this.current.position))
    {
      if (!isAlphaOr646ToNumericLatch(this.current.position))
        break label137;
      CurrentParsingState localCurrentParsingState2 = this.current;
      localCurrentParsingState2.position = (3 + localCurrentParsingState2.position);
      this.current.setNumeric();
    }
    label137: 
    do
    {
      return new BlockParsedResult(false);
      DecodedChar localDecodedChar = decodeIsoIec646(this.current.position);
      this.current.position = localDecodedChar.getNewPosition();
      if (localDecodedChar.isFNC1())
        return new BlockParsedResult(new DecodedInformation(this.current.position, this.buffer.toString()), true);
      this.buffer.append(localDecodedChar.getValue());
      break;
    }
    while (!isAlphaTo646ToAlphaLatch(this.current.position));
    CurrentParsingState localCurrentParsingState1;
    if (5 + this.current.position < this.information.size)
      localCurrentParsingState1 = this.current;
    for (localCurrentParsingState1.position = (5 + localCurrentParsingState1.position); ; this.current.position = this.information.size)
    {
      this.current.setAlpha();
      break;
    }
  }

  private BlockParsedResult parseNumericBlock()
  {
    while (true)
    {
      if (!isStillNumeric(this.current.position))
      {
        if (isNumericToAlphaNumericLatch(this.current.position))
        {
          this.current.setAlpha();
          CurrentParsingState localCurrentParsingState = this.current;
          localCurrentParsingState.position = (4 + localCurrentParsingState.position);
        }
        return new BlockParsedResult(false);
      }
      DecodedNumeric localDecodedNumeric = decodeNumeric(this.current.position);
      this.current.position = localDecodedNumeric.getNewPosition();
      if (localDecodedNumeric.isFirstDigitFNC1())
      {
        if (localDecodedNumeric.isSecondDigitFNC1());
        for (DecodedInformation localDecodedInformation = new DecodedInformation(this.current.position, this.buffer.toString()); ; localDecodedInformation = new DecodedInformation(this.current.position, this.buffer.toString(), localDecodedNumeric.getSecondDigit()))
          return new BlockParsedResult(localDecodedInformation, true);
      }
      this.buffer.append(localDecodedNumeric.getFirstDigit());
      if (localDecodedNumeric.isSecondDigitFNC1())
        return new BlockParsedResult(new DecodedInformation(this.current.position, this.buffer.toString()), true);
      this.buffer.append(localDecodedNumeric.getSecondDigit());
    }
  }

  String decodeAllCodes(StringBuffer paramStringBuffer, int paramInt)
    throws NotFoundException
  {
    int i = paramInt;
    String str = null;
    while (true)
    {
      DecodedInformation localDecodedInformation = decodeGeneralPurposeField(i, str);
      paramStringBuffer.append(FieldParser.parseFieldsInGeneralPurpose(localDecodedInformation.getNewString()));
      if (localDecodedInformation.isRemaining());
      for (str = String.valueOf(localDecodedInformation.getRemainingValue()); i == localDecodedInformation.getNewPosition(); str = null)
        return paramStringBuffer.toString();
      i = localDecodedInformation.getNewPosition();
    }
  }

  DecodedInformation decodeGeneralPurposeField(int paramInt, String paramString)
  {
    this.buffer.setLength(0);
    if (paramString != null)
      this.buffer.append(paramString);
    this.current.position = paramInt;
    DecodedInformation localDecodedInformation = parseBlocks();
    if ((localDecodedInformation != null) && (localDecodedInformation.isRemaining()))
      return new DecodedInformation(this.current.position, this.buffer.toString(), localDecodedInformation.getRemainingValue());
    return new DecodedInformation(this.current.position, this.buffer.toString());
  }

  int extractNumericValueFromBitArray(int paramInt1, int paramInt2)
  {
    return extractNumericValueFromBitArray(this.information, paramInt1, paramInt2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.expanded.decoders.GeneralAppIdDecoder
 * JD-Core Version:    0.6.0
 */