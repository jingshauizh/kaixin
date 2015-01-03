package com.google.zxing.datamatrix.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

public final class Decoder
{
  private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.DATA_MATRIX_FIELD_256);

  private void correctErrors(byte[] paramArrayOfByte, int paramInt)
    throws ChecksumException
  {
    int i = paramArrayOfByte.length;
    int[] arrayOfInt = new int[i];
    int j = 0;
    int k;
    if (j >= i)
      k = paramArrayOfByte.length - paramInt;
    while (true)
    {
      int m;
      try
      {
        this.rsDecoder.decode(arrayOfInt, k);
        m = 0;
        if (m >= paramInt)
        {
          return;
          arrayOfInt[j] = (0xFF & paramArrayOfByte[j]);
          j++;
        }
      }
      catch (ReedSolomonException localReedSolomonException)
      {
        throw ChecksumException.getChecksumInstance();
      }
      paramArrayOfByte[m] = (byte)arrayOfInt[m];
      m++;
    }
  }

  public DecoderResult decode(BitMatrix paramBitMatrix)
    throws FormatException, ChecksumException
  {
    BitMatrixParser localBitMatrixParser = new BitMatrixParser(paramBitMatrix);
    Version localVersion = localBitMatrixParser.getVersion();
    DataBlock[] arrayOfDataBlock = DataBlock.getDataBlocks(localBitMatrixParser.readCodewords(), localVersion);
    int i = arrayOfDataBlock.length;
    int j = 0;
    byte[] arrayOfByte1;
    int m;
    for (int k = 0; ; k++)
    {
      if (k >= i)
      {
        arrayOfByte1 = new byte[j];
        m = 0;
        if (m < i)
          break;
        return DecodedBitStreamParser.decode(arrayOfByte1);
      }
      j += arrayOfDataBlock[k].getNumDataCodewords();
    }
    DataBlock localDataBlock = arrayOfDataBlock[m];
    byte[] arrayOfByte2 = localDataBlock.getCodewords();
    int n = localDataBlock.getNumDataCodewords();
    correctErrors(arrayOfByte2, n);
    for (int i1 = 0; ; i1++)
    {
      if (i1 >= n)
      {
        m++;
        break;
      }
      arrayOfByte1[(m + i1 * i)] = arrayOfByte2[i1];
    }
  }

  public DecoderResult decode(boolean[][] paramArrayOfBoolean)
    throws FormatException, ChecksumException
  {
    int i = paramArrayOfBoolean.length;
    BitMatrix localBitMatrix = new BitMatrix(i);
    int j = 0;
    if (j >= i)
      return decode(localBitMatrix);
    for (int k = 0; ; k++)
    {
      if (k >= i)
      {
        j++;
        break;
      }
      if (paramArrayOfBoolean[j][k] == 0)
        continue;
      localBitMatrix.set(k, j);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.datamatrix.decoder.Decoder
 * JD-Core Version:    0.6.0
 */