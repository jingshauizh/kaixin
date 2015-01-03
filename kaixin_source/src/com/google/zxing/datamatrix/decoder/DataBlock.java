package com.google.zxing.datamatrix.decoder;

final class DataBlock
{
  private final byte[] codewords;
  private final int numDataCodewords;

  private DataBlock(int paramInt, byte[] paramArrayOfByte)
  {
    this.numDataCodewords = paramInt;
    this.codewords = paramArrayOfByte;
  }

  static DataBlock[] getDataBlocks(byte[] paramArrayOfByte, Version paramVersion)
  {
    Version.ECBlocks localECBlocks = paramVersion.getECBlocks();
    int i = 0;
    Version.ECB[] arrayOfECB = localECBlocks.getECBlocks();
    int j = 0;
    DataBlock[] arrayOfDataBlock;
    int k;
    int m;
    int i4;
    int i6;
    int i7;
    int i11;
    label90: int i12;
    label99: int i13;
    int i14;
    label106: int i17;
    int i18;
    if (j >= arrayOfECB.length)
    {
      arrayOfDataBlock = new DataBlock[i];
      k = 0;
      m = 0;
      if (m < arrayOfECB.length)
        break label174;
      i4 = arrayOfDataBlock[0].codewords.length - localECBlocks.getECCodewords();
      int i5 = i4 - 1;
      i6 = 0;
      i7 = 0;
      if (i7 < i5)
        break label254;
      if (paramVersion.getVersionNumber() != 24)
        break label313;
      i11 = 1;
      if (i11 == 0)
        break label319;
      i12 = 8;
      i13 = 0;
      i14 = i6;
      if (i13 < i12)
        break label326;
      i17 = arrayOfDataBlock[0].codewords.length;
      i18 = i4;
    }
    label174: label254: int i20;
    label313: label319: label326: int i21;
    for (int i19 = i14; ; i19 = i21)
    {
      if (i18 >= i17)
      {
        int i24 = paramArrayOfByte.length;
        if (i19 == i24)
          break label451;
        throw new IllegalArgumentException();
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
          int i2 = i1 + localECBlocks.getECCodewords();
          int i3 = k + 1;
          DataBlock localDataBlock = new DataBlock(i1, new byte[i2]);
          arrayOfDataBlock[k] = localDataBlock;
          n++;
          k = i3;
        }
        int i8 = 0;
        int i10;
        for (int i9 = i6; ; i9 = i10)
        {
          if (i8 >= k)
          {
            i7++;
            i6 = i9;
            break;
          }
          byte[] arrayOfByte1 = arrayOfDataBlock[i8].codewords;
          i10 = i9 + 1;
          arrayOfByte1[i7] = paramArrayOfByte[i9];
          i8++;
        }
        i11 = 0;
        break label90;
        i12 = k;
        break label99;
        byte[] arrayOfByte2 = arrayOfDataBlock[i13].codewords;
        int i15 = i4 - 1;
        int i16 = i14 + 1;
        arrayOfByte2[i15] = paramArrayOfByte[i14];
        i13++;
        i14 = i16;
        break label106;
      }
      i20 = 0;
      i21 = i19;
      if (i20 < k)
        break label391;
      i18++;
    }
    label391: if ((i11 != 0) && (i20 > 7));
    for (int i22 = i18 - 1; ; i22 = i18)
    {
      byte[] arrayOfByte3 = arrayOfDataBlock[i20].codewords;
      int i23 = i21 + 1;
      arrayOfByte3[i22] = paramArrayOfByte[i21];
      i20++;
      i21 = i23;
      break;
    }
    label451: return arrayOfDataBlock;
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
 * Qualified Name:     com.google.zxing.datamatrix.decoder.DataBlock
 * JD-Core Version:    0.6.0
 */