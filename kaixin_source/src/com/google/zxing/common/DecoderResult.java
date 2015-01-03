package com.google.zxing.common;

import java.util.Vector;

public final class DecoderResult
{
  private final Vector byteSegments;
  private final String ecLevel;
  private final byte[] rawBytes;
  private final String text;

  public DecoderResult(byte[] paramArrayOfByte, String paramString1, Vector paramVector, String paramString2)
  {
    if ((paramArrayOfByte == null) && (paramString1 == null))
      throw new IllegalArgumentException();
    this.rawBytes = paramArrayOfByte;
    this.text = paramString1;
    this.byteSegments = paramVector;
    this.ecLevel = paramString2;
  }

  public Vector getByteSegments()
  {
    return this.byteSegments;
  }

  public String getECLevel()
  {
    return this.ecLevel;
  }

  public byte[] getRawBytes()
  {
    return this.rawBytes;
  }

  public String getText()
  {
    return this.text;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.DecoderResult
 * JD-Core Version:    0.6.0
 */