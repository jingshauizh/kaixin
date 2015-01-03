package com.google.zxing.qrcode.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import java.util.Hashtable;

public final class Decoder
{
  private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

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
    throws ChecksumException, FormatException
  {
    return decode(paramBitMatrix, null);
  }

  public DecoderResult decode(BitMatrix paramBitMatrix, Hashtable paramHashtable)
    throws FormatException, ChecksumException
  {
    BitMatrixParser localBitMatrixParser = new BitMatrixParser(paramBitMatrix);
    Version localVersion = localBitMatrixParser.readVersion();
    ErrorCorrectionLevel localErrorCorrectionLevel = localBitMatrixParser.readFormatInformation().getErrorCorrectionLevel();
    DataBlock[] arrayOfDataBlock = DataBlock.getDataBlocks(localBitMatrixParser.readCodewords(), localVersion, localErrorCorrectionLevel);
    int i = 0;
    byte[] arrayOfByte1;
    int k;
    int m;
    for (int j = 0; ; j++)
    {
      if (j >= arrayOfDataBlock.length)
      {
        arrayOfByte1 = new byte[i];
        k = 0;
        m = 0;
        if (m < arrayOfDataBlock.length)
          break;
        return DecodedBitStreamParser.decode(arrayOfByte1, localVersion, localErrorCorrectionLevel, paramHashtable);
      }
      i += arrayOfDataBlock[j].getNumDataCodewords();
    }
    DataBlock localDataBlock = arrayOfDataBlock[m];
    byte[] arrayOfByte2 = localDataBlock.getCodewords();
    int n = localDataBlock.getNumDataCodewords();
    correctErrors(arrayOfByte2, n);
    int i1 = 0;
    int i3;
    for (int i2 = k; ; i2 = i3)
    {
      if (i1 >= n)
      {
        m++;
        k = i2;
        break;
      }
      i3 = i2 + 1;
      arrayOfByte1[i2] = arrayOfByte2[i1];
      i1++;
    }
  }

  public DecoderResult decode(boolean[][] paramArrayOfBoolean)
    throws ChecksumException, FormatException
  {
    return decode(paramArrayOfBoolean, null);
  }

  public DecoderResult decode(boolean[][] paramArrayOfBoolean, Hashtable paramHashtable)
    throws ChecksumException, FormatException
  {
    int i = paramArrayOfBoolean.length;
    BitMatrix localBitMatrix = new BitMatrix(i);
    int j = 0;
    if (j >= i)
      return decode(localBitMatrix, paramHashtable);
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
 * Qualified Name:     com.google.zxing.qrcode.decoder.Decoder
 * JD-Core Version:    0.6.0
 */