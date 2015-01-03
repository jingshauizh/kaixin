package org.apache.sanselan.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.common.mylzw.MyLZWCompressor;
import org.apache.sanselan.common.mylzw.MyLZWDecompressor;

public class Compression
{
  public byte[] compressLZW(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
    throws IOException
  {
    return new MyLZWCompressor(paramInt1, paramInt2, paramBoolean).compress(paramArrayOfByte);
  }

  public byte[] decompressLZW(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
    throws IOException
  {
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
    return new MyLZWDecompressor(paramInt1, paramInt3).decompress(localByteArrayInputStream, paramInt2);
  }

  public byte[] decompressPackBits(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws ImageReadException, IOException
  {
    return new PackBits().decompress(paramArrayOfByte, paramInt1);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.Compression
 * JD-Core Version:    0.6.0
 */