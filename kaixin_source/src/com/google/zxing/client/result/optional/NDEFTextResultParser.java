package com.google.zxing.client.result.optional;

import com.google.zxing.Result;
import com.google.zxing.client.result.TextParsedResult;

final class NDEFTextResultParser extends AbstractNDEFResultParser
{
  static String[] decodeTextPayload(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte[0];
    int j;
    int k;
    String str1;
    if ((i & 0x80) != 0)
    {
      j = 1;
      k = i & 0x1F;
      str1 = bytesToString(paramArrayOfByte, 1, k, "US-ASCII");
      if (j == 0)
        break label70;
    }
    label70: for (String str2 = "UTF-16"; ; str2 = "UTF8")
    {
      return new String[] { str1, bytesToString(paramArrayOfByte, k + 1, -1 + (paramArrayOfByte.length - k), str2) };
      j = 0;
      break;
    }
  }

  public static TextParsedResult parse(Result paramResult)
  {
    byte[] arrayOfByte = paramResult.getRawBytes();
    if (arrayOfByte == null);
    NDEFRecord localNDEFRecord;
    do
    {
      return null;
      localNDEFRecord = NDEFRecord.readRecord(arrayOfByte, 0);
    }
    while ((localNDEFRecord == null) || (!localNDEFRecord.isMessageBegin()) || (!localNDEFRecord.isMessageEnd()) || (!localNDEFRecord.getType().equals("T")));
    String[] arrayOfString = decodeTextPayload(localNDEFRecord.getPayload());
    return new TextParsedResult(arrayOfString[0], arrayOfString[1]);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.optional.NDEFTextResultParser
 * JD-Core Version:    0.6.0
 */