package com.kaixin001.mime.content;

import java.io.IOException;
import java.io.OutputStream;

public class ByteArrayBody extends AbstractContentBody
{
  private final byte[] data;
  private final String filename;

  public ByteArrayBody(byte[] paramArrayOfByte, String paramString)
  {
    this(paramArrayOfByte, "application/octet-stream", paramString);
  }

  public ByteArrayBody(byte[] paramArrayOfByte, String paramString1, String paramString2)
  {
    super(paramString1);
    if (paramArrayOfByte == null)
      throw new IllegalArgumentException("byte[] may not be null");
    this.data = paramArrayOfByte;
    this.filename = paramString2;
  }

  public String getCharset()
  {
    return null;
  }

  public long getContentLength()
  {
    return this.data.length;
  }

  public String getFilename()
  {
    return this.filename;
  }

  public String getTransferEncoding()
  {
    return "binary";
  }

  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    paramOutputStream.write(this.data);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.mime.content.ByteArrayBody
 * JD-Core Version:    0.6.0
 */