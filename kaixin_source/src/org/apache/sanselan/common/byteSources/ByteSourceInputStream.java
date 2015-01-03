package org.apache.sanselan.common.byteSources;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteSourceInputStream extends ByteSource
{
  private static final int BLOCK_SIZE = 1024;
  private CacheBlock cacheHead = null;
  private final InputStream is;
  private Long length = null;
  private byte[] readBuffer = null;

  public ByteSourceInputStream(InputStream paramInputStream, String paramString)
  {
    super(paramString);
    this.is = new BufferedInputStream(paramInputStream);
  }

  private CacheBlock getFirstBlock()
    throws IOException
  {
    if (this.cacheHead == null)
      this.cacheHead = readBlock();
    return this.cacheHead;
  }

  private CacheBlock readBlock()
    throws IOException
  {
    if (this.readBuffer == null)
      this.readBuffer = new byte[1024];
    int i = this.is.read(this.readBuffer);
    if (i < 1)
      return null;
    if (i < 1024)
    {
      byte[] arrayOfByte2 = new byte[i];
      System.arraycopy(this.readBuffer, 0, arrayOfByte2, 0, i);
      return new CacheBlock(arrayOfByte2);
    }
    byte[] arrayOfByte1 = this.readBuffer;
    this.readBuffer = null;
    return new CacheBlock(arrayOfByte1);
  }

  public byte[] getAll()
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    for (CacheBlock localCacheBlock = getFirstBlock(); ; localCacheBlock = localCacheBlock.getNext())
    {
      if (localCacheBlock == null)
        return localByteArrayOutputStream.toByteArray();
      localByteArrayOutputStream.write(localCacheBlock.bytes);
    }
  }

  public byte[] getBlock(int paramInt1, int paramInt2)
    throws IOException
  {
    InputStream localInputStream = getInputStream();
    localInputStream.skip(paramInt1);
    byte[] arrayOfByte = new byte[paramInt2];
    int i = 0;
    do
    {
      int j = localInputStream.read(arrayOfByte, i, arrayOfByte.length - i);
      if (j < 1)
        throw new IOException("Could not read block.");
      i += j;
    }
    while (i < paramInt2);
    return arrayOfByte;
  }

  public String getDescription()
  {
    return "Inputstream: '" + this.filename + "'";
  }

  public InputStream getInputStream()
    throws IOException
  {
    return new CacheReadingInputStream(null);
  }

  public long getLength()
    throws IOException
  {
    if (this.length != null)
      return this.length.longValue();
    InputStream localInputStream = getInputStream();
    long l1 = 0L;
    while (true)
    {
      long l2 = localInputStream.skip(1024L);
      if (l2 <= 0L)
      {
        this.length = new Long(l1);
        return l1;
      }
      l1 += l2;
    }
  }

  private class CacheBlock
  {
    public final byte[] bytes;
    private CacheBlock next = null;
    private boolean triedNext = false;

    public CacheBlock(byte[] arg2)
    {
      Object localObject;
      this.bytes = localObject;
    }

    public CacheBlock getNext()
      throws IOException
    {
      if (this.next != null)
        return this.next;
      if (this.triedNext)
        return null;
      this.triedNext = true;
      this.next = ByteSourceInputStream.this.readBlock();
      return this.next;
    }
  }

  private class CacheReadingInputStream extends InputStream
  {
    private ByteSourceInputStream.CacheBlock block = null;
    private int blockIndex = 0;
    private boolean readFirst = false;

    private CacheReadingInputStream()
    {
    }

    public int read()
      throws IOException
    {
      if (this.block == null)
        if (!this.readFirst);
      do
      {
        return -1;
        this.block = ByteSourceInputStream.this.getFirstBlock();
        this.readFirst = true;
        if ((this.block == null) || (this.blockIndex < this.block.bytes.length))
          continue;
        this.block = this.block.getNext();
        this.blockIndex = 0;
      }
      while ((this.block == null) || (this.blockIndex >= this.block.bytes.length));
      byte[] arrayOfByte = this.block.bytes;
      int i = this.blockIndex;
      this.blockIndex = (i + 1);
      return 0xFF & arrayOfByte[i];
    }

    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      if (paramArrayOfByte == null)
        throw new NullPointerException();
      if ((paramInt1 < 0) || (paramInt1 > paramArrayOfByte.length) || (paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfByte.length) || (paramInt1 + paramInt2 < 0))
        throw new IndexOutOfBoundsException();
      if (paramInt2 == 0)
        return 0;
      if (this.block == null)
      {
        if (this.readFirst)
          return -1;
        this.block = ByteSourceInputStream.this.getFirstBlock();
        this.readFirst = true;
      }
      if ((this.block != null) && (this.blockIndex >= this.block.bytes.length))
      {
        this.block = this.block.getNext();
        this.blockIndex = 0;
      }
      if (this.block == null)
        return -1;
      if (this.blockIndex >= this.block.bytes.length)
        return -1;
      int i = Math.min(paramInt2, this.block.bytes.length - this.blockIndex);
      System.arraycopy(this.block.bytes, this.blockIndex, paramArrayOfByte, paramInt1, i);
      this.blockIndex = (i + this.blockIndex);
      return i;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.byteSources.ByteSourceInputStream
 * JD-Core Version:    0.6.0
 */