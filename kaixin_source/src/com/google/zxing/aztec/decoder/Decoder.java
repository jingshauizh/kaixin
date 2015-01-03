package com.google.zxing.aztec.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

public final class Decoder
{
  private static final int BINARY = 5;
  private static final int DIGIT = 3;
  private static final String[] DIGIT_TABLE;
  private static final int LOWER = 1;
  private static final String[] LOWER_TABLE;
  private static final int MIXED = 2;
  private static final String[] MIXED_TABLE;
  private static final int[] NB_BITS;
  private static final int[] NB_BITS_COMPACT;
  private static final int[] NB_DATABLOCK;
  private static final int[] NB_DATABLOCK_COMPACT;
  private static final int PUNCT = 4;
  private static final String[] PUNCT_TABLE;
  private static final int UPPER;
  private static final String[] UPPER_TABLE;
  private int codewordSize;
  private AztecDetectorResult ddata;
  private int invertedBitCount;
  private int numCodewords;

  static
  {
    int[] arrayOfInt1 = new int[5];
    arrayOfInt1[1] = 104;
    arrayOfInt1[2] = 240;
    arrayOfInt1[3] = 408;
    arrayOfInt1[4] = 608;
    NB_BITS_COMPACT = arrayOfInt1;
    int[] arrayOfInt2 = new int[33];
    arrayOfInt2[1] = 128;
    arrayOfInt2[2] = 288;
    arrayOfInt2[3] = 480;
    arrayOfInt2[4] = 704;
    arrayOfInt2[5] = 960;
    arrayOfInt2[6] = 1248;
    arrayOfInt2[7] = 1568;
    arrayOfInt2[8] = 1920;
    arrayOfInt2[9] = 2304;
    arrayOfInt2[10] = 2720;
    arrayOfInt2[11] = 3168;
    arrayOfInt2[12] = 3648;
    arrayOfInt2[13] = 4160;
    arrayOfInt2[14] = 4704;
    arrayOfInt2[15] = 5280;
    arrayOfInt2[16] = 5888;
    arrayOfInt2[17] = 6528;
    arrayOfInt2[18] = 7200;
    arrayOfInt2[19] = 7904;
    arrayOfInt2[20] = 8640;
    arrayOfInt2[21] = 9408;
    arrayOfInt2[22] = 10208;
    arrayOfInt2[23] = 11040;
    arrayOfInt2[24] = 11904;
    arrayOfInt2[25] = 12800;
    arrayOfInt2[26] = 13728;
    arrayOfInt2[27] = 14688;
    arrayOfInt2[28] = 15680;
    arrayOfInt2[29] = 16704;
    arrayOfInt2[30] = 17760;
    arrayOfInt2[31] = 18848;
    arrayOfInt2[32] = 19968;
    NB_BITS = arrayOfInt2;
    int[] arrayOfInt3 = new int[5];
    arrayOfInt3[1] = 17;
    arrayOfInt3[2] = 40;
    arrayOfInt3[3] = 51;
    arrayOfInt3[4] = 76;
    NB_DATABLOCK_COMPACT = arrayOfInt3;
    int[] arrayOfInt4 = new int[33];
    arrayOfInt4[1] = 21;
    arrayOfInt4[2] = 48;
    arrayOfInt4[3] = 60;
    arrayOfInt4[4] = 88;
    arrayOfInt4[5] = 120;
    arrayOfInt4[6] = 156;
    arrayOfInt4[7] = 196;
    arrayOfInt4[8] = 240;
    arrayOfInt4[9] = 230;
    arrayOfInt4[10] = 272;
    arrayOfInt4[11] = 316;
    arrayOfInt4[12] = 364;
    arrayOfInt4[13] = 416;
    arrayOfInt4[14] = 470;
    arrayOfInt4[15] = 528;
    arrayOfInt4[16] = 588;
    arrayOfInt4[17] = 652;
    arrayOfInt4[18] = 720;
    arrayOfInt4[19] = 790;
    arrayOfInt4[20] = 864;
    arrayOfInt4[21] = 940;
    arrayOfInt4[22] = 1020;
    arrayOfInt4[23] = 920;
    arrayOfInt4[24] = 992;
    arrayOfInt4[25] = 1066;
    arrayOfInt4[26] = 1144;
    arrayOfInt4[27] = 1224;
    arrayOfInt4[28] = 1306;
    arrayOfInt4[29] = 1392;
    arrayOfInt4[30] = 1480;
    arrayOfInt4[31] = 1570;
    arrayOfInt4[32] = 1664;
    NB_DATABLOCK = arrayOfInt4;
    UPPER_TABLE = new String[] { "CTRL_PS", " ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "CTRL_LL", "CTRL_ML", "CTRL_DL", "CTRL_BS" };
    LOWER_TABLE = new String[] { "CTRL_PS", " ", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "CTRL_US", "CTRL_ML", "CTRL_DL", "CTRL_BS" };
    MIXED_TABLE = new String[] { "CTRL_PS", " ", "\001", "\002", "\003", "\004", "\005", "\006", "\007", "\b", "\t", "\n", "\013", "\f", "\r", "\033", "\034", "\035", "\036", "\037", "@", "\\", "^", "_", "`", "|", "~", "", "CTRL_LL", "CTRL_UL", "CTRL_PL", "CTRL_BS" };
    PUNCT_TABLE = new String[] { "", "\r", "\r\n", ". ", ", ", ": ", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "<", "=", ">", "?", "[", "]", "{", "}", "CTRL_UL" };
    DIGIT_TABLE = new String[] { "CTRL_PS", " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ",", ".", "CTRL_UL", "CTRL_US" };
  }

  private boolean[] correctBits(boolean[] paramArrayOfBoolean)
    throws FormatException
  {
    GenericGF localGenericGF;
    if (this.ddata.getNbLayers() <= 2)
    {
      this.codewordSize = 6;
      localGenericGF = GenericGF.AZTEC_DATA_6;
    }
    label77: int[] arrayOfInt;
    int i2;
    boolean[] arrayOfBoolean;
    int i3;
    while (true)
    {
      int i = this.ddata.getNbDatablocks();
      int j;
      int k;
      int m;
      if (this.ddata.isCompact())
      {
        j = NB_BITS_COMPACT[this.ddata.getNbLayers()] - this.numCodewords * this.codewordSize;
        k = NB_DATABLOCK_COMPACT[this.ddata.getNbLayers()] - i;
        arrayOfInt = new int[this.numCodewords];
        m = 0;
        if (m < this.numCodewords)
          break label246;
      }
      try
      {
        new ReedSolomonDecoder(localGenericGF).decode(arrayOfInt, k);
        i2 = 0;
        this.invertedBitCount = 0;
        arrayOfBoolean = new boolean[i * this.codewordSize];
        i3 = 0;
        if (i3 < i)
          break;
        return arrayOfBoolean;
        if (this.ddata.getNbLayers() <= 8)
        {
          this.codewordSize = 8;
          localGenericGF = GenericGF.AZTEC_DATA_8;
          continue;
        }
        if (this.ddata.getNbLayers() <= 22)
        {
          this.codewordSize = 10;
          localGenericGF = GenericGF.AZTEC_DATA_10;
          continue;
        }
        this.codewordSize = 12;
        localGenericGF = GenericGF.AZTEC_DATA_12;
        continue;
        j = NB_BITS[this.ddata.getNbLayers()] - this.numCodewords * this.codewordSize;
        k = NB_DATABLOCK[this.ddata.getNbLayers()] - i;
        break label77;
        label246: int n = 1;
        for (int i1 = 1; ; i1++)
        {
          if (i1 > this.codewordSize)
          {
            m++;
            break;
          }
          if (paramArrayOfBoolean[(j + (m * this.codewordSize + this.codewordSize - i1))] != 0)
            arrayOfInt[m] = (n + arrayOfInt[m]);
          n <<= 1;
        }
      }
      catch (ReedSolomonException localReedSolomonException)
      {
        throw FormatException.getFormatInstance();
      }
    }
    int i4 = 0;
    int i5 = 0;
    int i6 = 1 << -1 + this.codewordSize;
    int i8;
    for (int i7 = 0; ; i7++)
    {
      if (i7 >= this.codewordSize)
      {
        i3++;
        break;
      }
      if ((i6 & arrayOfInt[i3]) == i6);
      for (i8 = 1; ; i8 = 0)
      {
        if (i5 != -1 + this.codewordSize)
          break label430;
        if (i8 != i4)
          break;
        throw FormatException.getFormatInstance();
      }
      i4 = 0;
      i5 = 0;
      i2++;
      this.invertedBitCount = (1 + this.invertedBitCount);
      i6 >>>= 1;
    }
    label430: if (i4 == i8)
      i5++;
    while (true)
    {
      arrayOfBoolean[(i7 + i3 * this.codewordSize - i2)] = i8;
      break;
      i5 = 1;
      i4 = i8;
    }
  }

  private boolean[] extractBits(BitMatrix paramBitMatrix)
    throws FormatException
  {
    boolean[] arrayOfBoolean;
    if (this.ddata.isCompact())
    {
      if (this.ddata.getNbLayers() > NB_BITS_COMPACT.length)
        throw FormatException.getFormatInstance();
      arrayOfBoolean = new boolean[NB_BITS_COMPACT[this.ddata.getNbLayers()]];
    }
    int i;
    int j;
    int k;
    int m;
    for (this.numCodewords = NB_DATABLOCK_COMPACT[this.ddata.getNbLayers()]; ; this.numCodewords = NB_DATABLOCK[this.ddata.getNbLayers()])
    {
      i = this.ddata.getNbLayers();
      j = paramBitMatrix.height;
      k = 0;
      m = 0;
      if (i != 0)
        break;
      return arrayOfBoolean;
      if (this.ddata.getNbLayers() > NB_BITS.length)
        throw FormatException.getFormatInstance();
      arrayOfBoolean = new boolean[NB_BITS[this.ddata.getNbLayers()]];
    }
    int n = 0;
    int i1 = 0;
    label139: int i2;
    if (i1 >= -4 + j * 2)
      i2 = 0;
    for (int i3 = 1 + j * 2; ; i3--)
    {
      if (i3 <= 5)
      {
        m += 2;
        k += -16 + j * 8;
        i--;
        j -= 4;
        break;
        arrayOfBoolean[(k + i1)] = paramBitMatrix.get(m + n, m + i1 / 2);
        arrayOfBoolean[(i1 + (-4 + (k + j * 2)))] = paramBitMatrix.get(m + i1 / 2, -1 + (m + j) - n);
        n = (n + 1) % 2;
        i1++;
        break label139;
      }
      arrayOfBoolean[(1 + (-8 + (k + j * 4) + (j * 2 - i3)))] = paramBitMatrix.get(-1 + (m + j) - i2, -1 + (m + i3 / 2));
      arrayOfBoolean[(1 + (-12 + (k + j * 6) + (j * 2 - i3)))] = paramBitMatrix.get(-1 + (m + i3 / 2), m + i2);
      i2 = (i2 + 1) % 2;
    }
  }

  private static String getCharacter(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    default:
      return "";
    case 0:
      return UPPER_TABLE[paramInt2];
    case 1:
      return LOWER_TABLE[paramInt2];
    case 2:
      return MIXED_TABLE[paramInt2];
    case 4:
      return PUNCT_TABLE[paramInt2];
    case 3:
    }
    return DIGIT_TABLE[paramInt2];
  }

  private String getEncodedData(boolean[] paramArrayOfBoolean)
    throws FormatException
  {
    int i = this.codewordSize * this.ddata.getNbDatablocks() - this.invertedBitCount;
    if (i > paramArrayOfBoolean.length)
      throw FormatException.getFormatInstance();
    int j = 0;
    int k = 0;
    int m = 0;
    StringBuffer localStringBuffer = new StringBuffer(20);
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    if (n != 0)
      return localStringBuffer.toString();
    label75: int i4;
    if (i1 != 0)
    {
      i2 = 1;
      switch (k)
      {
      default:
        i4 = 5;
        if (k == 3)
          i4 = 4;
        if (i - m >= i4)
          break;
        n = 1;
      case 5:
      }
    }
    while (i2 != 0)
    {
      k = j;
      i1 = 0;
      i2 = 0;
      break;
      j = k;
      break label75;
      if (i - m < 8)
      {
        n = 1;
        continue;
      }
      int i3 = readCode(paramArrayOfBoolean, m, 8);
      m += 8;
      localStringBuffer.append((char)i3);
      continue;
      int i5 = readCode(paramArrayOfBoolean, m, i4);
      m += i4;
      String str = getCharacter(k, i5);
      if (str.startsWith("CTRL_"))
      {
        k = getTable(str.charAt(5));
        if (str.charAt(6) != 'S')
          continue;
        i1 = 1;
        continue;
      }
      localStringBuffer.append(str);
    }
  }

  private static int getTable(char paramChar)
  {
    switch (paramChar)
    {
    default:
      return 0;
    case 'U':
      return 0;
    case 'L':
      return 1;
    case 'P':
      return 4;
    case 'M':
      return 2;
    case 'D':
      return 3;
    case 'B':
    }
    return 5;
  }

  private static int readCode(boolean[] paramArrayOfBoolean, int paramInt1, int paramInt2)
  {
    int i = 0;
    for (int j = paramInt1; ; j++)
    {
      if (j >= paramInt1 + paramInt2)
        return i;
      i <<= 1;
      if (paramArrayOfBoolean[j] == 0)
        continue;
      i++;
    }
  }

  private static BitMatrix removeDashedLines(BitMatrix paramBitMatrix)
  {
    int i = 1 + 2 * ((-1 + paramBitMatrix.width) / 2 / 16);
    BitMatrix localBitMatrix = new BitMatrix(paramBitMatrix.width - i, paramBitMatrix.height - i);
    int j = 0;
    int k = 0;
    if (k >= paramBitMatrix.width)
      return localBitMatrix;
    if ((paramBitMatrix.width / 2 - k) % 16 == 0);
    int m;
    int n;
    while (true)
    {
      k++;
      break;
      m = 0;
      n = 0;
      if (n < paramBitMatrix.height)
        break label94;
      j++;
    }
    label94: if ((paramBitMatrix.width / 2 - n) % 16 == 0);
    while (true)
    {
      n++;
      break;
      if (paramBitMatrix.get(k, n))
        localBitMatrix.set(j, m);
      m++;
    }
  }

  public DecoderResult decode(AztecDetectorResult paramAztecDetectorResult)
    throws FormatException
  {
    this.ddata = paramAztecDetectorResult;
    BitMatrix localBitMatrix = paramAztecDetectorResult.getBits();
    if (!this.ddata.isCompact())
      localBitMatrix = removeDashedLines(this.ddata.getBits());
    return new DecoderResult(null, getEncodedData(correctBits(extractBits(localBitMatrix))), null, null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.aztec.decoder.Decoder
 * JD-Core Version:    0.6.0
 */