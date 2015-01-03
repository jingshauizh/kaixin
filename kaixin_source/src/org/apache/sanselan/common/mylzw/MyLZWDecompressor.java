package org.apache.sanselan.common.mylzw;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class MyLZWDecompressor
{
  private static final int MAX_TABLE_SIZE = 4096;
  private final int byteOrder;
  private final int clearCode;
  private int codeSize;
  private int codes = -1;
  private final int eoiCode;
  private final int initialCodeSize;
  private final Listener listener;
  private final byte[][] table;
  private boolean tiffLZWMode = false;
  private int written = 0;

  public MyLZWDecompressor(int paramInt1, int paramInt2)
  {
    this(paramInt1, paramInt2, null);
  }

  public MyLZWDecompressor(int paramInt1, int paramInt2, Listener paramListener)
  {
    this.listener = paramListener;
    this.byteOrder = paramInt2;
    this.initialCodeSize = paramInt1;
    this.table = new byte[4096][];
    this.clearCode = (1 << paramInt1);
    this.eoiCode = (1 + this.clearCode);
    if (paramListener != null)
      paramListener.init(this.clearCode, this.eoiCode);
    InitializeTable();
  }

  private final void InitializeTable()
  {
    this.codeSize = this.initialCodeSize;
    int i = 1 << 2 + this.codeSize;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      byte[][] arrayOfByte = this.table;
      byte[] arrayOfByte1 = new byte[1];
      arrayOfByte1[0] = (byte)j;
      arrayOfByte[j] = arrayOfByte1;
    }
  }

  private final void addStringToTable(byte[] paramArrayOfByte)
    throws IOException
  {
    if (this.codes < 1 << this.codeSize)
    {
      this.table[this.codes] = paramArrayOfByte;
      this.codes = (1 + this.codes);
      checkCodeSize();
      return;
    }
    throw new IOException("AddStringToTable: codes: " + this.codes + " code_size: " + this.codeSize);
  }

  private final byte[] appendBytes(byte[] paramArrayOfByte, byte paramByte)
  {
    byte[] arrayOfByte = new byte[1 + paramArrayOfByte.length];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramArrayOfByte.length);
    arrayOfByte[(-1 + arrayOfByte.length)] = paramByte;
    return arrayOfByte;
  }

  private final void checkCodeSize()
  {
    int i = 1 << this.codeSize;
    if (this.tiffLZWMode)
      i--;
    if (this.codes == i)
      incrementCodeSize();
  }

  private final void clearTable()
  {
    this.codes = (2 + (1 << this.initialCodeSize));
    this.codeSize = this.initialCodeSize;
    incrementCodeSize();
  }

  private final byte firstChar(byte[] paramArrayOfByte)
  {
    return paramArrayOfByte[0];
  }

  private final int getNextCode(MyBitInputStream paramMyBitInputStream)
    throws IOException
  {
    int i = paramMyBitInputStream.readBits(this.codeSize);
    if (this.listener != null)
      this.listener.code(i);
    return i;
  }

  private final void incrementCodeSize()
  {
    if (this.codeSize != 12)
      this.codeSize = (1 + this.codeSize);
  }

  private final boolean isInTable(int paramInt)
  {
    return paramInt < this.codes;
  }

  private final byte[] stringFromCode(int paramInt)
    throws IOException
  {
    if ((paramInt >= this.codes) || (paramInt < 0))
      throw new IOException("Bad Code: " + paramInt + " codes: " + this.codes + " code_size: " + this.codeSize + ", table: " + this.table.length);
    return this.table[paramInt];
  }

  private final void writeToResult(OutputStream paramOutputStream, byte[] paramArrayOfByte)
    throws IOException
  {
    paramOutputStream.write(paramArrayOfByte);
    this.written += paramArrayOfByte.length;
  }

  public byte[] decompress(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    int i = -1;
    MyBitInputStream localMyBitInputStream = new MyBitInputStream(paramInputStream, this.byteOrder);
    if (this.tiffLZWMode)
      localMyBitInputStream.setTiffLZWMode();
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(paramInt);
    clearTable();
    label217: 
    while (true)
    {
      int j = getNextCode(localMyBitInputStream);
      if (j == this.eoiCode);
      while (true)
      {
        return localByteArrayOutputStream.toByteArray();
        if (j != this.clearCode)
          break;
        clearTable();
        if (this.written >= paramInt)
          continue;
        int k = getNextCode(localMyBitInputStream);
        if (k == this.eoiCode)
          continue;
        writeToResult(localByteArrayOutputStream, stringFromCode(k));
        i = k;
      }
      while (true)
      {
        if (this.written < paramInt)
          break label217;
        break;
        if (isInTable(j))
        {
          writeToResult(localByteArrayOutputStream, stringFromCode(j));
          addStringToTable(appendBytes(stringFromCode(i), firstChar(stringFromCode(j))));
          i = j;
          continue;
        }
        byte[] arrayOfByte = appendBytes(stringFromCode(i), firstChar(stringFromCode(i)));
        writeToResult(localByteArrayOutputStream, arrayOfByte);
        addStringToTable(arrayOfByte);
        i = j;
      }
    }
  }

  public void setTiffLZWMode()
  {
    this.tiffLZWMode = true;
  }

  public static abstract interface Listener
  {
    public abstract void code(int paramInt);

    public abstract void init(int paramInt1, int paramInt2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.mylzw.MyLZWDecompressor
 * JD-Core Version:    0.6.0
 */