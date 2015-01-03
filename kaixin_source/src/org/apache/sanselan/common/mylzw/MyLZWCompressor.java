package org.apache.sanselan.common.mylzw;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyLZWCompressor
{
  private final int byteOrder;
  private final int clearCode;
  private int codeSize;
  private int codes = -1;
  private final boolean earlyLimit;
  private final int eoiCode;
  private final int initialCodeSize;
  private final Listener listener;
  private final Map map = new HashMap();

  public MyLZWCompressor(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this(paramInt1, paramInt2, paramBoolean, null);
  }

  public MyLZWCompressor(int paramInt1, int paramInt2, boolean paramBoolean, Listener paramListener)
  {
    this.listener = paramListener;
    this.byteOrder = paramInt2;
    this.earlyLimit = paramBoolean;
    this.initialCodeSize = paramInt1;
    this.clearCode = (1 << paramInt1);
    this.eoiCode = (1 + this.clearCode);
    if (paramListener != null)
      paramListener.init(this.clearCode, this.eoiCode);
    InitializeStringTable();
  }

  private final void InitializeStringTable()
  {
    this.codeSize = this.initialCodeSize;
    int i = 2 + (1 << this.codeSize);
    this.map.clear();
    for (this.codes = 0; ; this.codes = (1 + this.codes))
    {
      if (this.codes >= i)
        return;
      if ((this.codes == this.clearCode) || (this.codes == this.eoiCode))
        continue;
      Object localObject = arrayToKey((byte)this.codes);
      this.map.put(localObject, new Integer(this.codes));
    }
  }

  private final boolean addTableEntry(MyBitOutputStream paramMyBitOutputStream, Object paramObject)
    throws IOException
  {
    int i = 1 << this.codeSize;
    if (this.earlyLimit)
      i--;
    int j = this.codes;
    int k = 0;
    if (j == i)
    {
      if (this.codeSize >= 12)
        break label85;
      incrementCodeSize();
    }
    while (true)
    {
      if (k == 0)
      {
        this.map.put(paramObject, new Integer(this.codes));
        this.codes = (1 + this.codes);
      }
      return k;
      label85: writeClearCode(paramMyBitOutputStream);
      clearTable();
      k = 1;
    }
  }

  private final boolean addTableEntry(MyBitOutputStream paramMyBitOutputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    return addTableEntry(paramMyBitOutputStream, arrayToKey(paramArrayOfByte, paramInt1, paramInt2));
  }

  private final Object arrayToKey(byte paramByte)
  {
    return arrayToKey(new byte[] { paramByte }, 0, 1);
  }

  private final Object arrayToKey(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return new ByteArray(paramArrayOfByte, paramInt1, paramInt2);
  }

  private final void clearTable()
  {
    InitializeStringTable();
    incrementCodeSize();
  }

  private final int codeFromString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    Object localObject1 = arrayToKey(paramArrayOfByte, paramInt1, paramInt2);
    Object localObject2 = this.map.get(localObject1);
    if (localObject2 == null)
      throw new IOException("CodeFromString");
    return ((Integer)localObject2).intValue();
  }

  private final void incrementCodeSize()
  {
    if (this.codeSize != 12)
      this.codeSize = (1 + this.codeSize);
  }

  private final boolean isInTable(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Object localObject = arrayToKey(paramArrayOfByte, paramInt1, paramInt2);
    return this.map.containsKey(localObject);
  }

  private final void writeClearCode(MyBitOutputStream paramMyBitOutputStream)
    throws IOException
  {
    if (this.listener != null)
      this.listener.dataCode(this.clearCode);
    writeCode(paramMyBitOutputStream, this.clearCode);
  }

  private final void writeCode(MyBitOutputStream paramMyBitOutputStream, int paramInt)
    throws IOException
  {
    paramMyBitOutputStream.writeBits(paramInt, this.codeSize);
  }

  private final void writeDataCode(MyBitOutputStream paramMyBitOutputStream, int paramInt)
    throws IOException
  {
    if (this.listener != null)
      this.listener.dataCode(paramInt);
    writeCode(paramMyBitOutputStream, paramInt);
  }

  private final void writeEoiCode(MyBitOutputStream paramMyBitOutputStream)
    throws IOException
  {
    if (this.listener != null)
      this.listener.eoiCode(this.eoiCode);
    writeCode(paramMyBitOutputStream, this.eoiCode);
  }

  public byte[] compress(byte[] paramArrayOfByte)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(paramArrayOfByte.length);
    MyBitOutputStream localMyBitOutputStream = new MyBitOutputStream(localByteArrayOutputStream, this.byteOrder);
    InitializeStringTable();
    clearTable();
    writeClearCode(localMyBitOutputStream);
    int i = 0;
    int j = 0;
    int k = 0;
    if (k >= paramArrayOfByte.length)
    {
      writeDataCode(localMyBitOutputStream, codeFromString(paramArrayOfByte, i, j));
      writeEoiCode(localMyBitOutputStream);
      localMyBitOutputStream.flushCache();
      return localByteArrayOutputStream.toByteArray();
    }
    if (isInTable(paramArrayOfByte, i, j + 1))
      j++;
    while (true)
    {
      k++;
      break;
      writeDataCode(localMyBitOutputStream, codeFromString(paramArrayOfByte, i, j));
      addTableEntry(localMyBitOutputStream, paramArrayOfByte, i, j + 1);
      i = k;
      j = 1;
    }
  }

  private static final class ByteArray
  {
    private final byte[] bytes;
    private final int hash;
    private final int length;
    private final int start;

    public ByteArray(byte[] paramArrayOfByte)
    {
      this(paramArrayOfByte, 0, paramArrayOfByte.length);
    }

    public ByteArray(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      this.bytes = paramArrayOfByte;
      this.start = paramInt1;
      this.length = paramInt2;
      int i = paramInt2;
      for (int j = 0; ; j++)
      {
        if (j >= paramInt2)
        {
          this.hash = i;
          return;
        }
        i = j ^ (0xFF & paramArrayOfByte[(j + paramInt1)] ^ i + (i << 8));
      }
    }

    public final boolean equals(Object paramObject)
    {
      ByteArray localByteArray = (ByteArray)paramObject;
      if (localByteArray.hash != this.hash);
      do
        return false;
      while (localByteArray.length != this.length);
      for (int i = 0; ; i++)
      {
        if (i >= this.length)
          return true;
        if (localByteArray.bytes[(i + localByteArray.start)] != this.bytes[(i + this.start)])
          break;
      }
    }

    public final int hashCode()
    {
      return this.hash;
    }
  }

  public static abstract interface Listener
  {
    public abstract void clearCode(int paramInt);

    public abstract void dataCode(int paramInt);

    public abstract void eoiCode(int paramInt);

    public abstract void init(int paramInt1, int paramInt2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.mylzw.MyLZWCompressor
 * JD-Core Version:    0.6.0
 */