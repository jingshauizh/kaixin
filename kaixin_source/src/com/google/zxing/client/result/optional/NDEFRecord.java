package com.google.zxing.client.result.optional;

final class NDEFRecord
{
  public static final String ACTION_WELL_KNOWN_TYPE = "act";
  public static final String SMART_POSTER_WELL_KNOWN_TYPE = "Sp";
  private static final int SUPPORTED_HEADER = 17;
  private static final int SUPPORTED_HEADER_MASK = 63;
  public static final String TEXT_WELL_KNOWN_TYPE = "T";
  public static final String URI_WELL_KNOWN_TYPE = "U";
  private final int header;
  private final byte[] payload;
  private final int totalRecordLength;
  private final String type;

  private NDEFRecord(int paramInt1, String paramString, byte[] paramArrayOfByte, int paramInt2)
  {
    this.header = paramInt1;
    this.type = paramString;
    this.payload = paramArrayOfByte;
    this.totalRecordLength = paramInt2;
  }

  static NDEFRecord readRecord(byte[] paramArrayOfByte, int paramInt)
  {
    int i = 0xFF & paramArrayOfByte[paramInt];
    if ((0x3F & (i ^ 0x11)) != 0)
      return null;
    int j = 0xFF & paramArrayOfByte[(paramInt + 1)];
    int k = 0xFF & paramArrayOfByte[(paramInt + 2)];
    String str = AbstractNDEFResultParser.bytesToString(paramArrayOfByte, paramInt + 3, j, "US-ASCII");
    byte[] arrayOfByte = new byte[k];
    System.arraycopy(paramArrayOfByte, j + (paramInt + 3), arrayOfByte, 0, k);
    return new NDEFRecord(i, str, arrayOfByte, k + (j + 3));
  }

  byte[] getPayload()
  {
    return this.payload;
  }

  int getTotalRecordLength()
  {
    return this.totalRecordLength;
  }

  String getType()
  {
    return this.type;
  }

  boolean isMessageBegin()
  {
    return (0x80 & this.header) != 0;
  }

  boolean isMessageEnd()
  {
    return (0x40 & this.header) != 0;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.optional.NDEFRecord
 * JD-Core Version:    0.6.0
 */