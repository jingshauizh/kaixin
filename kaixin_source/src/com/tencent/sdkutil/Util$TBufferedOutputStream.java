package com.tencent.sdkutil;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

class Util$TBufferedOutputStream extends BufferedOutputStream
{
  private int mLength = 0;

  public Util$TBufferedOutputStream(OutputStream paramOutputStream)
  {
    super(paramOutputStream);
  }

  public int getLength()
  {
    return this.mLength;
  }

  public void write(byte[] paramArrayOfByte)
  {
    super.write(paramArrayOfByte);
    this.mLength += paramArrayOfByte.length;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.Util.TBufferedOutputStream
 * JD-Core Version:    0.6.0
 */