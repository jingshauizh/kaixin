package com.tencent.mm.sdk.platformtools;

import java.nio.ByteBuffer;

class MBuf
{
  private ByteBuffer al = null;

  public ByteBuffer getBuffer()
  {
    return this.al;
  }

  public int getLen()
  {
    return this.al.capacity();
  }

  public int getOffset()
  {
    return this.al.position();
  }

  public void setBuffer(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    this.al = ByteBuffer.allocateDirect(i);
    this.al.position(0);
    this.al.put(paramArrayOfByte, 0, i);
    this.al.position(0);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.MBuf
 * JD-Core Version:    0.6.0
 */