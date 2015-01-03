package org.apache.sanselan.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class ZLibUtils extends BinaryFileFunctions
{
  public final byte[] deflate(byte[] paramArrayOfByte)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DeflaterOutputStream localDeflaterOutputStream = new DeflaterOutputStream(localByteArrayOutputStream);
    localDeflaterOutputStream.write(paramArrayOfByte);
    localDeflaterOutputStream.close();
    return localByteArrayOutputStream.toByteArray();
  }

  public final byte[] inflate(byte[] paramArrayOfByte)
    throws IOException
  {
    return getStreamBytes(new InflaterInputStream(new ByteArrayInputStream(paramArrayOfByte)));
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.ZLibUtils
 * JD-Core Version:    0.6.0
 */