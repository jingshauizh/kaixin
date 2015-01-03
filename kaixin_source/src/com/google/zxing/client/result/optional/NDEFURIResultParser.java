package com.google.zxing.client.result.optional;

import com.google.zxing.Result;
import com.google.zxing.client.result.URIParsedResult;

final class NDEFURIResultParser extends AbstractNDEFResultParser
{
  private static final String[] URI_PREFIXES;

  static
  {
    String[] arrayOfString = new String[36];
    arrayOfString[1] = "http://www.";
    arrayOfString[2] = "https://www.";
    arrayOfString[3] = "http://";
    arrayOfString[4] = "https://";
    arrayOfString[5] = "tel:";
    arrayOfString[6] = "mailto:";
    arrayOfString[7] = "ftp://anonymous:anonymous@";
    arrayOfString[8] = "ftp://ftp.";
    arrayOfString[9] = "ftps://";
    arrayOfString[10] = "sftp://";
    arrayOfString[11] = "smb://";
    arrayOfString[12] = "nfs://";
    arrayOfString[13] = "ftp://";
    arrayOfString[14] = "dav://";
    arrayOfString[15] = "news:";
    arrayOfString[16] = "telnet://";
    arrayOfString[17] = "imap:";
    arrayOfString[18] = "rtsp://";
    arrayOfString[19] = "urn:";
    arrayOfString[20] = "pop:";
    arrayOfString[21] = "sip:";
    arrayOfString[22] = "sips:";
    arrayOfString[23] = "tftp:";
    arrayOfString[24] = "btspp://";
    arrayOfString[25] = "btl2cap://";
    arrayOfString[26] = "btgoep://";
    arrayOfString[27] = "tcpobex://";
    arrayOfString[28] = "irdaobex://";
    arrayOfString[29] = "file://";
    arrayOfString[30] = "urn:epc:id:";
    arrayOfString[31] = "urn:epc:tag:";
    arrayOfString[32] = "urn:epc:pat:";
    arrayOfString[33] = "urn:epc:raw:";
    arrayOfString[34] = "urn:epc:";
    arrayOfString[35] = "urn:nfc:";
    URI_PREFIXES = arrayOfString;
  }

  static String decodeURIPayload(byte[] paramArrayOfByte)
  {
    int i = 0xFF & paramArrayOfByte[0];
    int j = URI_PREFIXES.length;
    String str1 = null;
    if (i < j)
      str1 = URI_PREFIXES[i];
    String str2 = bytesToString(paramArrayOfByte, 1, -1 + paramArrayOfByte.length, "UTF8");
    if (str1 == null)
      return str2;
    return str1 + str2;
  }

  public static URIParsedResult parse(Result paramResult)
  {
    byte[] arrayOfByte = paramResult.getRawBytes();
    if (arrayOfByte == null);
    NDEFRecord localNDEFRecord;
    do
    {
      return null;
      localNDEFRecord = NDEFRecord.readRecord(arrayOfByte, 0);
    }
    while ((localNDEFRecord == null) || (!localNDEFRecord.isMessageBegin()) || (!localNDEFRecord.isMessageEnd()) || (!localNDEFRecord.getType().equals("U")));
    return new URIParsedResult(decodeURIPayload(localNDEFRecord.getPayload()), null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.optional.NDEFURIResultParser
 * JD-Core Version:    0.6.0
 */