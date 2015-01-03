package com.kaixin001.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class UploadFile
{
  private String contentType = "image/jpeg";
  private String filname;
  private String formname = "filedata";

  public UploadFile(String paramString1, String paramString2, String paramString3)
  {
    this.filname = paramString1;
    this.formname = paramString2;
    if (paramString3 != null)
      this.contentType = paramString3;
    KXLog.d("", this.filname + this.formname + this.contentType);
  }

  private byte[] getFileData(String paramString)
  {
    try
    {
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(paramString));
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(1024);
      byte[] arrayOfByte = new byte[1024];
      while (true)
      {
        int i = localBufferedInputStream.read(arrayOfByte);
        if (i == -1)
        {
          localBufferedInputStream.close();
          return localByteArrayOutputStream.toByteArray();
        }
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public void clear()
  {
    this.filname = null;
    this.formname = null;
    this.contentType = null;
  }

  public String getContentType()
  {
    return this.contentType;
  }

  public String getFilname()
  {
    return this.filname;
  }

  public String getFormname()
  {
    return this.formname;
  }

  public void setContentType(String paramString)
  {
    this.contentType = paramString;
  }

  public void setFilname(String paramString)
  {
    this.filname = paramString;
  }

  public void setFormname(String paramString)
  {
    this.formname = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.UploadFile
 * JD-Core Version:    0.6.0
 */