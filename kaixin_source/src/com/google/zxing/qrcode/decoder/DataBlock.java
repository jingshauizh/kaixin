package com.google.zxing.qrcode.decoder;

final class DataBlock
{
  private final byte[] codewords;
  private final int numDataCodewords;

  private DataBlock(int paramInt, byte[] paramArrayOfByte)
  {
    this.numDataCodewords = paramInt;
    this.codewords = paramArrayOfByte;
  }

  static DataBlock[] getDataBlocks(byte[] paramArrayOfByte, Version paramVersion, ErrorCorrectionLevel paramErrorCorrectionLevel)
  {
    if (paramArrayOfByte.length != paramVersion.getTotalCodewords())
      throw new IllegalArgumentException();
    Version.ECBlocks localECBlocks = paramVersion.getECBlocksForLevel(paramErrorCorrectionLevel);
    int i = 0;
    Version.ECB[] arrayOfECB = localECBlocks.getECBlocks();
    int j = 0;
    DataBlock[] arrayOfDataBlock;
    int k;
    int m;
    int i4;
    int i5;
    label81: label86: int i6;
    int i7;
    int i8;
    int i9;
    int i13;
    int i14;
    label122: int i16;
    int i17;
    if (j >= arrayOfECB.length)
    {
      arrayOfDataBlock = new DataBlock[i];
      k = 0;
      m = 0;
      if (m < arrayOfECB.length)
        break label176;
      i4 = arrayOfDataBlock[0].codewords.length;
      i5 = -1 + arrayOfDataBlock.length;
      if (i5 >= 0)
        break label256;
      i6 = i5 + 1;
      i7 = i4 - localECBlocks.getECCodewordsPerBlock();
      i8 = 0;
      i9 = 0;
      if (i9 < i7)
        break label276;
      i13 = i6;
      i14 = i8;
      if (i13 < k)
        break label335;
      i16 = arrayOfDataBlock[0].codewords.length;
      i17 = i7;
    }
    label176: label335: int i19;
    label256: label276: int i20;
    for (int i18 = i14; ; i18 = i20)
    {
      if (i17 >= i16)
      {
        return arrayOfDataBlock;
        i += arrayOfECB[j].getCount();
        j++;
        break;
        Version.ECB localECB = arrayOfECB[m];
        int n = 0;
        while (true)
        {
          if (n >= localECB.getCount())
          {
            m++;
            break;
          }
          int i1 = localECB.getDataCodewords();
          int i2 = i1 + localECBlocks.getECCodewordsPerBlock();
          int i3 = k + 1;
          DataBlock localDataBlock = new DataBlock(i1, new byte[i2]);
          arrayOfDataBlock[k] = localDataBlock;
          n++;
          k = i3;
        }
        if (arrayOfDataBlock[i5].codewords.length == i4)
          break label86;
        i5--;
        break label81;
        int i10 = 0;
        int i12;
        for (int i11 = i8; ; i11 = i12)
        {
          if (i10 >= k)
          {
            i9++;
            i8 = i11;
            break;
          }
          byte[] arrayOfByte1 = arrayOfDataBlock[i10].codewords;
          i12 = i11 + 1;
          arrayOfByte1[i9] = paramArrayOfByte[i11];
          i10++;
        }
        byte[] arrayOfByte2 = arrayOfDataBlock[i13].codewords;
        int i15 = i14 + 1;
        arrayOfByte2[i7] = paramArrayOfByte[i14];
        i13++;
        i14 = i15;
        break label122;
      }
      i19 = 0;
      i20 = i18;
      if (i19 < k)
        break label394;
      i17++;
    }
    label394: if (i19 < i6);
    for (int i21 = i17; ; i21 = i17 + 1)
    {
      byte[] arrayOfByte3 = arrayOfDataBlock[i19].codewords;
      int i22 = i20 + 1;
      arrayOfByte3[i21] = paramArrayOfByte[i20];
      i19++;
      i20 = i22;
      break;
    }
  }

  byte[] getCodewords()
  {
    return this.codewords;
  }

  int getNumDataCodewords()
  {
    return this.numDataCodewords;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.decoder.DataBlock
 * JD-Core Version:    0.6.0
 */