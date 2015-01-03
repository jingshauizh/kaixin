package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import java.util.Hashtable;

public abstract class UPCEANReader extends OneDReader
{
  static final int[][] L_AND_G_PATTERNS;
  static final int[][] L_PATTERNS;
  private static final int MAX_AVG_VARIANCE = 107;
  private static final int MAX_INDIVIDUAL_VARIANCE = 179;
  static final int[] MIDDLE_PATTERN;
  static final int[] START_END_PATTERN = { 1, 1, 1 };
  private final StringBuffer decodeRowStringBuffer = new StringBuffer(20);
  private final EANManufacturerOrgSupport eanManSupport = new EANManufacturerOrgSupport();
  private final UPCEANExtensionSupport extensionReader = new UPCEANExtensionSupport();

  static
  {
    MIDDLE_PATTERN = new int[] { 1, 1, 1, 1, 1 };
    L_PATTERNS = new int[][] { { 3, 2, 1, 1 }, { 2, 2, 2, 1 }, { 2, 1, 2, 2 }, { 1, 4, 1, 1 }, { 1, 1, 3, 2 }, { 1, 2, 3, 1 }, { 1, 1, 1, 4 }, { 1, 3, 1, 2 }, { 1, 2, 1, 3 }, { 3, 1, 1, 2 } };
    L_AND_G_PATTERNS = new int[20][];
    int j;
    for (int i = 0; ; i++)
    {
      if (i >= 10)
      {
        j = 10;
        if (j < 20)
          break;
        return;
      }
      L_AND_G_PATTERNS[i] = L_PATTERNS[i];
    }
    int[] arrayOfInt1 = L_PATTERNS[(j - 10)];
    int[] arrayOfInt2 = new int[arrayOfInt1.length];
    for (int k = 0; ; k++)
    {
      if (k >= arrayOfInt1.length)
      {
        L_AND_G_PATTERNS[j] = arrayOfInt2;
        j++;
        break;
      }
      arrayOfInt2[k] = arrayOfInt1[(-1 + (arrayOfInt1.length - k))];
    }
  }

  private static boolean checkStandardUPCEANChecksum(String paramString)
    throws FormatException
  {
    int i = paramString.length();
    if (i == 0)
      return false;
    int j = 0;
    int k = i - 2;
    label17: int n;
    if (k < 0)
      n = j * 3;
    for (int i1 = i - 1; ; i1 -= 2)
    {
      if (i1 < 0)
      {
        if (n % 10 != 0)
          break;
        return true;
        int m = '￐' + paramString.charAt(k);
        if ((m < 0) || (m > 9))
          throw FormatException.getFormatInstance();
        j += m;
        k -= 2;
        break label17;
      }
      int i2 = '￐' + paramString.charAt(i1);
      if ((i2 < 0) || (i2 > 9))
        throw FormatException.getFormatInstance();
      n += i2;
    }
  }

  static int decodeDigit(BitArray paramBitArray, int[] paramArrayOfInt, int paramInt, int[][] paramArrayOfInt1)
    throws NotFoundException
  {
    recordPattern(paramBitArray, paramInt, paramArrayOfInt);
    int i = 107;
    int j = -1;
    int k = paramArrayOfInt1.length;
    for (int m = 0; ; m++)
    {
      if (m >= k)
      {
        if (j < 0)
          break;
        return j;
      }
      int n = patternMatchVariance(paramArrayOfInt, paramArrayOfInt1[m], 179);
      if (n >= i)
        continue;
      i = n;
      j = m;
    }
    throw NotFoundException.getNotFoundInstance();
  }

  static int[] findGuardPattern(BitArray paramBitArray, int paramInt, boolean paramBoolean, int[] paramArrayOfInt)
    throws NotFoundException
  {
    int i = paramArrayOfInt.length;
    int[] arrayOfInt = new int[i];
    int j = paramBitArray.getSize();
    boolean bool = false;
    label25: int k;
    int m;
    if (paramInt >= j)
    {
      k = 0;
      m = paramInt;
    }
    for (int n = paramInt; ; n++)
    {
      if (n >= j)
      {
        throw NotFoundException.getNotFoundInstance();
        if (paramBitArray.get(paramInt));
        for (bool = false; ; bool = true)
        {
          if (paramBoolean == bool)
            break label72;
          paramInt++;
          break;
        }
        label72: break label25;
      }
      if (!(bool ^ paramBitArray.get(n)))
        break label104;
      arrayOfInt[k] = (1 + arrayOfInt[k]);
    }
    label104: int i1;
    if (k == i - 1)
    {
      if (patternMatchVariance(arrayOfInt, paramArrayOfInt, 179) < 107)
        return new int[] { m, n };
      m += arrayOfInt[0] + arrayOfInt[1];
      i1 = 2;
      label158: if (i1 >= i)
      {
        arrayOfInt[(i - 2)] = 0;
        arrayOfInt[(i - 1)] = 0;
        k--;
        label184: arrayOfInt[k] = 1;
        if (!bool)
          break label225;
      }
    }
    label225: for (bool = false; ; bool = true)
    {
      break;
      arrayOfInt[(i1 - 2)] = arrayOfInt[i1];
      i1++;
      break label158;
      k++;
      break label184;
    }
  }

  static int[] findStartGuardPattern(BitArray paramBitArray)
    throws NotFoundException
  {
    boolean bool = false;
    int[] arrayOfInt = (int[])null;
    int i = 0;
    while (true)
    {
      if (bool)
        return arrayOfInt;
      arrayOfInt = findGuardPattern(paramBitArray, i, false, START_END_PATTERN);
      int j = arrayOfInt[0];
      i = arrayOfInt[1];
      int k = j - (i - j);
      if (k < 0)
        continue;
      bool = paramBitArray.isRange(k, j, false);
    }
  }

  boolean checkChecksum(String paramString)
    throws ChecksumException, FormatException
  {
    return checkStandardUPCEANChecksum(paramString);
  }

  int[] decodeEnd(BitArray paramBitArray, int paramInt)
    throws NotFoundException
  {
    return findGuardPattern(paramBitArray, paramInt, false, START_END_PATTERN);
  }

  protected abstract int decodeMiddle(BitArray paramBitArray, int[] paramArrayOfInt, StringBuffer paramStringBuffer)
    throws NotFoundException;

  public Result decodeRow(int paramInt, BitArray paramBitArray, Hashtable paramHashtable)
    throws NotFoundException, ChecksumException, FormatException
  {
    return decodeRow(paramInt, paramBitArray, findStartGuardPattern(paramBitArray), paramHashtable);
  }

  public Result decodeRow(int paramInt, BitArray paramBitArray, int[] paramArrayOfInt, Hashtable paramHashtable)
    throws NotFoundException, ChecksumException, FormatException
  {
    if (paramHashtable == null);
    StringBuffer localStringBuffer;
    int[] arrayOfInt;
    for (ResultPointCallback localResultPointCallback = null; ; localResultPointCallback = (ResultPointCallback)paramHashtable.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK))
    {
      if (localResultPointCallback != null)
        localResultPointCallback.foundPossibleResultPoint(new ResultPoint((paramArrayOfInt[0] + paramArrayOfInt[1]) / 2.0F, paramInt));
      localStringBuffer = this.decodeRowStringBuffer;
      localStringBuffer.setLength(0);
      int i = decodeMiddle(paramBitArray, paramArrayOfInt, localStringBuffer);
      if (localResultPointCallback != null)
        localResultPointCallback.foundPossibleResultPoint(new ResultPoint(i, paramInt));
      arrayOfInt = decodeEnd(paramBitArray, i);
      if (localResultPointCallback != null)
        localResultPointCallback.foundPossibleResultPoint(new ResultPoint((arrayOfInt[0] + arrayOfInt[1]) / 2.0F, paramInt));
      int j = arrayOfInt[1];
      int k = j + (j - arrayOfInt[0]);
      if ((k < paramBitArray.getSize()) && (paramBitArray.isRange(j, k, false)))
        break;
      throw NotFoundException.getNotFoundInstance();
    }
    String str1 = localStringBuffer.toString();
    if (!checkChecksum(str1))
      throw ChecksumException.getChecksumInstance();
    float f1 = (paramArrayOfInt[1] + paramArrayOfInt[0]) / 2.0F;
    float f2 = (arrayOfInt[1] + arrayOfInt[0]) / 2.0F;
    BarcodeFormat localBarcodeFormat = getBarcodeFormat();
    ResultPoint[] arrayOfResultPoint = new ResultPoint[2];
    ResultPoint localResultPoint1 = new ResultPoint(f1, paramInt);
    arrayOfResultPoint[0] = localResultPoint1;
    ResultPoint localResultPoint2 = new ResultPoint(f2, paramInt);
    arrayOfResultPoint[1] = localResultPoint2;
    Result localResult1 = new Result(str1, null, arrayOfResultPoint, localBarcodeFormat);
    try
    {
      Result localResult2 = this.extensionReader.decodeRow(paramInt, paramBitArray, arrayOfInt[1]);
      localResult1.putAllMetadata(localResult2.getResultMetadata());
      localResult1.addResultPoints(localResult2.getResultPoints());
      label333: if ((BarcodeFormat.EAN_13.equals(localBarcodeFormat)) || (BarcodeFormat.UPC_A.equals(localBarcodeFormat)))
      {
        String str2 = this.eanManSupport.lookupCountryIdentifier(str1);
        if (str2 != null)
          localResult1.putMetadata(ResultMetadataType.POSSIBLE_COUNTRY, str2);
      }
      return localResult1;
    }
    catch (ReaderException localReaderException)
    {
      break label333;
    }
  }

  abstract BarcodeFormat getBarcodeFormat();
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.UPCEANReader
 * JD-Core Version:    0.6.0
 */