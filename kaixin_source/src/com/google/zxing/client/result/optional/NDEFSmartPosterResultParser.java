package com.google.zxing.client.result.optional;

import com.google.zxing.Result;

final class NDEFSmartPosterResultParser extends AbstractNDEFResultParser
{
  public static NDEFSmartPosterParsedResult parse(Result paramResult)
  {
    byte[] arrayOfByte1 = paramResult.getRawBytes();
    if (arrayOfByte1 == null);
    int i;
    int j;
    NDEFRecord localNDEFRecord2;
    int k;
    String str1;
    String str2;
    do
      while (true)
      {
        return null;
        NDEFRecord localNDEFRecord1 = NDEFRecord.readRecord(arrayOfByte1, 0);
        if ((localNDEFRecord1 == null) || (!localNDEFRecord1.isMessageBegin()) || (!localNDEFRecord1.isMessageEnd()) || (!localNDEFRecord1.getType().equals("Sp")))
          continue;
        i = 0;
        j = 0;
        localNDEFRecord2 = null;
        byte[] arrayOfByte2 = localNDEFRecord1.getPayload();
        k = -1;
        str1 = null;
        str2 = null;
        if (i < arrayOfByte2.length)
        {
          localNDEFRecord2 = NDEFRecord.readRecord(arrayOfByte2, i);
          if (localNDEFRecord2 != null)
            break;
        }
        else if ((j != 0) && ((localNDEFRecord2 == null) || (localNDEFRecord2.isMessageEnd())))
        {
          return new NDEFSmartPosterParsedResult(k, str2, str1);
        }
      }
    while ((j == 0) && (!localNDEFRecord2.isMessageBegin()));
    String str3 = localNDEFRecord2.getType();
    if ("T".equals(str3))
      str1 = NDEFTextResultParser.decodeTextPayload(localNDEFRecord2.getPayload())[1];
    while (true)
    {
      j++;
      i += localNDEFRecord2.getTotalRecordLength();
      break;
      if ("U".equals(str3))
      {
        str2 = NDEFURIResultParser.decodeURIPayload(localNDEFRecord2.getPayload());
        continue;
      }
      if (!"act".equals(str3))
        continue;
      k = localNDEFRecord2.getPayload()[0];
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.optional.NDEFSmartPosterResultParser
 * JD-Core Version:    0.6.0
 */