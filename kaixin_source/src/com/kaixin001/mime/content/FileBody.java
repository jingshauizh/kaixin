package com.kaixin001.mime.content;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileBody extends AbstractContentBody
{
  private final String charset;
  private final File file;
  private final String filename;

  public FileBody(File paramFile)
  {
    this(paramFile, "application/octet-stream");
  }

  public FileBody(File paramFile, String paramString)
  {
    this(paramFile, paramString, null);
  }

  public FileBody(File paramFile, String paramString1, String paramString2)
  {
    this(paramFile, null, paramString1, paramString2);
  }

  public FileBody(File paramFile, String paramString1, String paramString2, String paramString3)
  {
    super(paramString2);
    if (paramFile == null)
      throw new IllegalArgumentException("File may not be null");
    this.file = paramFile;
    if (paramString1 != null);
    for (this.filename = paramString1; ; this.filename = paramFile.getName())
    {
      this.charset = paramString3;
      return;
    }
  }

  public String getCharset()
  {
    return this.charset;
  }

  public long getContentLength()
  {
    return this.file.length();
  }

  public File getFile()
  {
    return this.file;
  }

  public String getFilename()
  {
    return this.filename;
  }

  public InputStream getInputStream()
    throws IOException
  {
    return new FileInputStream(this.file);
  }

  public String getTransferEncoding()
  {
    return "binary";
  }

  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    if (paramOutputStream == null)
      throw new IllegalArgumentException("Output stream may not be null");
    FileInputStream localFileInputStream = new FileInputStream(this.file);
    try
    {
      byte[] arrayOfByte = new byte[4096];
      while (true)
      {
        int i = localFileInputStream.read(arrayOfByte);
        if (i == -1)
        {
          paramOutputStream.flush();
          return;
        }
        paramOutputStream.write(arrayOfByte, 0, i);
      }
    }
    finally
    {
      localFileInputStream.close();
    }
    throw localObject;
  }

  @Deprecated
  public void writeTo(OutputStream paramOutputStream, int paramInt)
    throws IOException
  {
    writeTo(paramOutputStream);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.mime.content.FileBody
 * JD-Core Version:    0.6.0
 */