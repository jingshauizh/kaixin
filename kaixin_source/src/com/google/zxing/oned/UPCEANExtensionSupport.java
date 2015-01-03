package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Hashtable;

final class UPCEANExtensionSupport
{
  private static final int[] CHECK_DIGIT_ENCODINGS;
  private static final int[] EXTENSION_START_PATTERN = { 1, 1, 2 };
  private final int[] decodeMiddleCounters = new int[4];
  private final StringBuffer decodeRowStringBuffer = new StringBuffer();

  static
  {
    CHECK_DIGIT_ENCODINGS = new int[] { 24, 20, 18, 17, 12, 6, 3, 10, 9, 5 };
  }

  private static int determineCheckDigit(int paramInt)
    throws NotFoundException
  {
    for (int i = 0; ; i++)
    {
      if (i >= 10)
        throw NotFoundException.getNotFoundInstance();
      if (paramInt == CHECK_DIGIT_ENCODINGS[i])
        return i;
    }
  }

  private static int extensionChecksum(String paramString)
  {
    int i = paramString.length();
    int j = 0;
    int k = i - 2;
    int m;
    if (k < 0)
      m = j * 3;
    for (int n = i - 1; ; n -= 2)
    {
      if (n < 0)
      {
        return m * 3 % 10;
        j += '￐' + paramString.charAt(k);
        k -= 2;
        break;
      }
      m += '￐' + paramString.charAt(n);
    }
  }

  private static Integer parseExtension2String(String paramString)
  {
    return Integer.valueOf(paramString);
  }

  private static String parseExtension5String(String paramString)
  {
    String str1;
    String str2;
    int j;
    switch (paramString.charAt(0))
    {
    default:
      str1 = "";
      int i = Integer.parseInt(paramString.substring(1));
      str2 = String.valueOf(i / 100);
      j = i % 100;
      if (j >= 10)
        break;
    case '0':
    case '5':
    case '9':
    }
    for (String str3 = "0" + j; ; str3 = String.valueOf(j))
    {
      return str1 + str2 + '.' + str3;
      str1 = "拢";
      break;
      str1 = "$";
      break;
      if ("90000".equals(paramString))
        return null;
      if ("99991".equals(paramString))
        return "0.00";
      if ("99990".equals(paramString))
        return "Used";
      str1 = "";
      break;
    }
  }

  private static Hashtable parseExtensionString(String paramString)
  {
    ResultMetadataType localResultMetadataType;
    switch (paramString.length())
    {
    case 3:
    case 4:
    default:
      return null;
    case 2:
      localResultMetadataType = ResultMetadataType.ISSUE_NUMBER;
    case 5:
    }
    for (Object localObject = parseExtension2String(paramString); localObject != null; localObject = parseExtension5String(paramString))
    {
      Hashtable localHashtable = new Hashtable(1);
      localHashtable.put(localResultMetadataType, localObject);
      return localHashtable;
      localResultMetadataType = ResultMetadataType.SUGGESTED_PRICE;
    }
  }

  int decodeMiddle(BitArray paramBitArray, int[] paramArrayOfInt, StringBuffer paramStringBuffer)
    throws NotFoundException
  {
    int[] arrayOfInt = this.decodeMiddleCounters;
    arrayOfInt[0] = 0;
    arrayOfInt[1] = 0;
    arrayOfInt[2] = 0;
    arrayOfInt[3] = 0;
    int i = paramBitArray.getSize();
    int j = paramArrayOfInt[1];
    int k = 0;
    int m = 0;
    if ((m >= 5) || (j >= i))
    {
      if (paramStringBuffer.length() != 5)
        throw NotFoundException.getNotFoundInstance();
    }
    else
    {
      int i1 = UPCEANReader.decodeDigit(paramBitArray, arrayOfInt, j, UPCEANReader.L_AND_G_PATTERNS);
      paramStringBuffer.append((char)(48 + i1 % 10));
      int i2 = 0;
      label98: if (i2 >= arrayOfInt.length)
      {
        if (i1 >= 10)
          k |= 1 << 4 - m;
        if (m != 4)
          if ((j < i) && (!paramBitArray.get(j)))
            break label184;
      }
      while (true)
      {
        label130: if ((j >= i) || (!paramBitArray.get(j)))
        {
          m++;
          break;
          j += arrayOfInt[i2];
          i2++;
          break label98;
          label184: j++;
          break label130;
        }
        j++;
      }
    }
    int n = determineCheckDigit(k);
    if (extensionChecksum(paramStringBuffer.toString()) != n)
      throw NotFoundException.getNotFoundInstance();
    return j;
  }

  Result decodeRow(int paramInt1, BitArray paramBitArray, int paramInt2)
    throws NotFoundException
  {
    int[] arrayOfInt = UPCEANReader.findGuardPattern(paramBitArray, paramInt2, false, EXTENSION_START_PATTERN);
    StringBuffer localStringBuffer = this.decodeRowStringBuffer;
    localStringBuffer.setLength(0);
    int i = decodeMiddle(paramBitArray, arrayOfInt, localStringBuffer);
    String str = localStringBuffer.toString();
    Hashtable localHashtable = parseExtensionString(str);
    ResultPoint[] arrayOfResultPoint = new ResultPoint[2];
    arrayOfResultPoint[0] = new ResultPoint((arrayOfInt[0] + arrayOfInt[1]) / 2.0F, paramInt1);
    arrayOfResultPoint[1] = new ResultPoint(i, paramInt1);
    Result localResult = new Result(str, null, arrayOfResultPoint, BarcodeFormat.UPC_EAN_EXTENSION);
    if (localHashtable != null)
      localResult.putAllMetadata(localHashtable);
    return localResult;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.UPCEANExtensionSupport
 * JD-Core Version:    0.6.0
 */