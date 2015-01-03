package com.tencent.mm.sdk.platformtools;

import java.nio.ByteBuffer;

public class LVBuffer
{
  public static final int LENGTH_ALLOC_PER_NEW = 4096;
  public static final int MAX_STRING_LENGTH = 2048;
  private ByteBuffer U;
  private boolean V;

  private int b(int paramInt)
  {
    if (this.U.limit() - this.U.position() > paramInt)
      return 0;
    ByteBuffer localByteBuffer = ByteBuffer.allocate(4096 + this.U.limit());
    localByteBuffer.put(this.U.array(), 0, this.U.position());
    this.U = localByteBuffer;
    return 0;
  }

  public byte[] buildFinish()
  {
    if (!this.V)
      throw new Exception("Buffer For Parse");
    b(1);
    this.U.put(125);
    byte[] arrayOfByte = new byte[this.U.position()];
    System.arraycopy(this.U.array(), 0, arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }

  public boolean checkGetFinish()
  {
    return this.U.limit() - this.U.position() <= 1;
  }

  public int getInt()
  {
    if (this.V)
      throw new Exception("Buffer For Build");
    return this.U.getInt();
  }

  public long getLong()
  {
    if (this.V)
      throw new Exception("Buffer For Build");
    return this.U.getLong();
  }

  public String getString()
  {
    if (this.V)
      throw new Exception("Buffer For Build");
    int i = this.U.getShort();
    if (i > 2048)
    {
      this.U = null;
      throw new Exception("Buffer String Length Error");
    }
    if (i == 0)
      return "";
    byte[] arrayOfByte = new byte[i];
    this.U.get(arrayOfByte, 0, i);
    return new String(arrayOfByte);
  }

  public int initBuild()
  {
    this.U = ByteBuffer.allocate(4096);
    this.U.put(123);
    this.V = true;
    return 0;
  }

  public int initParse(byte[] paramArrayOfByte)
  {
    int i;
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      i = -1;
    while (i != 0)
    {
      this.U = null;
      return -1;
      if (paramArrayOfByte[0] != 123)
      {
        i = -2;
        continue;
      }
      if (paramArrayOfByte[(-1 + paramArrayOfByte.length)] != 125)
      {
        i = -3;
        continue;
      }
      i = 0;
    }
    this.U = ByteBuffer.wrap(paramArrayOfByte);
    this.U.position(1);
    this.V = false;
    return 0;
  }

  public int putInt(int paramInt)
  {
    if (!this.V)
      throw new Exception("Buffer For Parse");
    b(4);
    this.U.putInt(paramInt);
    return 0;
  }

  public int putLong(long paramLong)
  {
    if (!this.V)
      throw new Exception("Buffer For Parse");
    b(8);
    this.U.putLong(paramLong);
    return 0;
  }

  public int putString(String paramString)
  {
    if (!this.V)
      throw new Exception("Buffer For Parse");
    byte[] arrayOfByte = null;
    if (paramString != null)
      arrayOfByte = paramString.getBytes();
    if (arrayOfByte == null)
      arrayOfByte = new byte[0];
    if (arrayOfByte.length > 2048)
      throw new Exception("Buffer String Length Error");
    b(2 + arrayOfByte.length);
    this.U.putShort((short)arrayOfByte.length);
    if (arrayOfByte.length > 0)
      this.U.put(arrayOfByte);
    return 0;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.LVBuffer
 * JD-Core Version:    0.6.0
 */