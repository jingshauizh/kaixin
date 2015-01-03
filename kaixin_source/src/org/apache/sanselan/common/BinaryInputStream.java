package org.apache.sanselan.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import org.apache.sanselan.ImageReadException;

public class BinaryInputStream extends InputStream
  implements BinaryConstants
{
  private int byteOrder = 77;
  protected boolean debug = false;
  private final InputStream is;

  public BinaryInputStream(InputStream paramInputStream)
  {
    this.is = paramInputStream;
  }

  public BinaryInputStream(InputStream paramInputStream, int paramInt)
  {
    this.byteOrder = paramInt;
    this.is = paramInputStream;
  }

  public BinaryInputStream(byte[] paramArrayOfByte, int paramInt)
  {
    this.byteOrder = paramInt;
    this.is = new ByteArrayInputStream(paramArrayOfByte);
  }

  protected static final int CharsToQuad(char paramChar1, char paramChar2, char paramChar3, char paramChar4)
  {
    return (paramChar1 & 0xFF) << '\030' | (paramChar2 & 0xFF) << '\020' | (paramChar3 & 0xFF) << '\b' | (paramChar4 & 0xFF) << '\000';
  }

  public final boolean compareByteArrays(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3)
  {
    if (paramArrayOfByte1.length < paramInt1 + paramInt3);
    do
      return false;
    while (paramArrayOfByte2.length < paramInt2 + paramInt3);
    for (int i = 0; ; i++)
    {
      if (i >= paramInt3)
        return true;
      if (paramArrayOfByte1[(paramInt1 + i)] == paramArrayOfByte2[(paramInt2 + i)])
        continue;
      debugNumber("a[" + (paramInt1 + i) + "]", paramArrayOfByte1[(paramInt1 + i)]);
      debugNumber("b[" + (paramInt2 + i) + "]", paramArrayOfByte2[(paramInt2 + i)]);
      return false;
    }
  }

  protected final int convertByteArrayToInt(String paramString, byte[] paramArrayOfByte)
  {
    return convertByteArrayToInt(paramString, paramArrayOfByte, this.byteOrder);
  }

  protected final int convertByteArrayToInt(String paramString, byte[] paramArrayOfByte, int paramInt)
  {
    return convertByteArrayToInt(paramString, paramArrayOfByte, 0, 4, paramInt);
  }

  protected final int convertByteArrayToInt(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramArrayOfByte[(paramInt1 + 0)];
    int j = paramArrayOfByte[(paramInt1 + 1)];
    int k = paramArrayOfByte[(paramInt1 + 2)];
    int m = 0;
    if (paramInt2 == 4)
      m = paramArrayOfByte[(paramInt1 + 3)];
    if (paramInt3 == 77);
    for (int n = ((i & 0xFF) << 24) + ((j & 0xFF) << 16) + ((k & 0xFF) << 8) + ((m & 0xFF) << 0); ; n = ((m & 0xFF) << 24) + ((k & 0xFF) << 16) + ((j & 0xFF) << 8) + ((i & 0xFF) << 0))
    {
      if (this.debug)
        debugNumber(paramString, n, 4);
      return n;
    }
  }

  protected final int[] convertByteArrayToIntArray(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt1 + paramInt2 * 4;
    int[] arrayOfInt;
    if (paramArrayOfByte.length < i)
    {
      System.out.println(paramString + ": expected length: " + i + ", actual length: " + paramArrayOfByte.length);
      arrayOfInt = null;
    }
    while (true)
    {
      return arrayOfInt;
      arrayOfInt = new int[paramInt2];
      for (int j = 0; j < paramInt2; j++)
        arrayOfInt[j] = convertByteArrayToInt(paramString, paramArrayOfByte, paramInt1 + j * 4, 4, paramInt3);
    }
  }

  protected final RationalNumber convertByteArrayToRational(String paramString, byte[] paramArrayOfByte, int paramInt)
  {
    return convertByteArrayToRational(paramString, paramArrayOfByte, 0, paramInt);
  }

  protected final RationalNumber convertByteArrayToRational(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return new RationalNumber(convertByteArrayToInt(paramString, paramArrayOfByte, paramInt1 + 0, 4, paramInt2), convertByteArrayToInt(paramString, paramArrayOfByte, paramInt1 + 4, 4, paramInt2));
  }

  protected final RationalNumber[] convertByteArrayToRationalArray(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt1 + paramInt2 * 8;
    RationalNumber[] arrayOfRationalNumber;
    if (paramArrayOfByte.length < i)
    {
      System.out.println(paramString + ": expected length: " + i + ", actual length: " + paramArrayOfByte.length);
      arrayOfRationalNumber = null;
    }
    while (true)
    {
      return arrayOfRationalNumber;
      arrayOfRationalNumber = new RationalNumber[paramInt2];
      for (int j = 0; j < paramInt2; j++)
        arrayOfRationalNumber[j] = convertByteArrayToRational(paramString, paramArrayOfByte, paramInt1 + j * 8, paramInt3);
    }
  }

  public final int convertByteArrayToShort(String paramString, int paramInt, byte[] paramArrayOfByte)
  {
    return convertByteArrayToShort(paramString, paramInt, paramArrayOfByte, this.byteOrder);
  }

  protected final int convertByteArrayToShort(String paramString, int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    int i = paramArrayOfByte[(paramInt1 + 0)];
    int j = paramArrayOfByte[(paramInt1 + 1)];
    if (paramInt2 == 77);
    for (int k = ((i & 0xFF) << 8) + ((j & 0xFF) << 0); ; k = ((j & 0xFF) << 8) + ((i & 0xFF) << 0))
    {
      if (this.debug)
        debugNumber(paramString, k, 2);
      return k;
    }
  }

  public final int convertByteArrayToShort(String paramString, byte[] paramArrayOfByte)
  {
    return convertByteArrayToShort(paramString, paramArrayOfByte, this.byteOrder);
  }

  protected final int convertByteArrayToShort(String paramString, byte[] paramArrayOfByte, int paramInt)
  {
    return convertByteArrayToShort(paramString, 0, paramArrayOfByte, paramInt);
  }

  protected final int[] convertByteArrayToShortArray(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt1 + paramInt2 * 2;
    int[] arrayOfInt;
    if (paramArrayOfByte.length < i)
    {
      System.out.println(paramString + ": expected length: " + i + ", actual length: " + paramArrayOfByte.length);
      arrayOfInt = null;
    }
    while (true)
    {
      return arrayOfInt;
      arrayOfInt = new int[paramInt2];
      for (int j = 0; j < paramInt2; j++)
        arrayOfInt[j] = convertByteArrayToShort(paramString, paramInt1 + j * 2, paramArrayOfByte, paramInt3);
    }
  }

  protected final void debugByteArray(String paramString, byte[] paramArrayOfByte)
  {
    System.out.println(paramString + ": " + paramArrayOfByte.length);
    for (int i = 0; ; i++)
    {
      if ((i >= paramArrayOfByte.length) || (i >= 50))
        return;
      debugNumber(paramString + " (" + i + ")", paramArrayOfByte[i]);
    }
  }

  public final void debugNumber(String paramString, int paramInt)
  {
    debugNumber(paramString, paramInt, 1);
  }

  public final void debugNumber(String paramString, int paramInt1, int paramInt2)
  {
    System.out.print(paramString + ": " + paramInt1 + " (");
    int i = paramInt1;
    for (int j = 0; ; j++)
    {
      if (j >= paramInt2)
      {
        System.out.println(") [0x" + Integer.toHexString(paramInt1) + ", " + Integer.toBinaryString(paramInt1) + "]");
        return;
      }
      if (j > 0)
        System.out.print(",");
      int k = i & 0xFF;
      System.out.print((char)k + " [" + k + "]");
      i >>= 8;
    }
  }

  protected final void debugNumberArray(String paramString, int[] paramArrayOfInt, int paramInt)
  {
    System.out.println(paramString + ": " + paramArrayOfInt.length);
    for (int i = 0; ; i++)
    {
      if ((i >= paramArrayOfInt.length) || (i >= 50))
        return;
      debugNumber(paramString + " (" + i + ")", paramArrayOfInt[i], paramInt);
    }
  }

  public final int findNull(byte[] paramArrayOfByte)
  {
    return findNull(paramArrayOfByte, 0);
  }

  public final int findNull(byte[] paramArrayOfByte, int paramInt)
  {
    for (int i = paramInt; ; i++)
    {
      if (i >= paramArrayOfByte.length)
        i = -1;
      do
        return i;
      while (paramArrayOfByte[i] == 0);
    }
  }

  protected int getByteOrder()
  {
    return this.byteOrder;
  }

  protected final byte[] getBytearrayHead(String paramString, byte[] paramArrayOfByte, int paramInt)
  {
    return readBytearray(paramString, paramArrayOfByte, 0, paramArrayOfByte.length - paramInt);
  }

  protected final byte[] getBytearrayTail(String paramString, byte[] paramArrayOfByte, int paramInt)
  {
    return readBytearray(paramString, paramArrayOfByte, paramInt, paramArrayOfByte.length - paramInt);
  }

  public final boolean getDebug()
  {
    return this.debug;
  }

  protected final byte[] getRAFBytes(RandomAccessFile paramRandomAccessFile, long paramLong, int paramInt, String paramString)
    throws IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    if (this.debug)
    {
      System.out.println("getRAFBytes pos: " + paramLong);
      System.out.println("getRAFBytes length: " + paramInt);
    }
    paramRandomAccessFile.seek(paramLong);
    int i = 0;
    while (true)
    {
      if (i >= paramInt)
        return arrayOfByte;
      int j = paramRandomAccessFile.read(arrayOfByte, i, paramInt - i);
      if (j < 1)
        throw new IOException(paramString);
      i += j;
    }
  }

  protected final void printByteBits(String paramString, byte paramByte)
  {
    System.out.println(paramString + ": '" + Integer.toBinaryString(paramByte & 0xFF));
  }

  protected final void printCharQuad(String paramString, int paramInt)
  {
    System.out.println(paramString + ": '" + (char)(0xFF & paramInt >> 24) + (char)(0xFF & paramInt >> 16) + (char)(0xFF & paramInt >> 8) + (char)(0xFF & paramInt >> 0) + "'");
  }

  public int read()
    throws IOException
  {
    return this.is.read();
  }

  public final int read1ByteInteger(String paramString)
    throws ImageReadException, IOException
  {
    int i = this.is.read();
    if (i < 0)
      throw new ImageReadException(paramString);
    return i & 0xFF;
  }

  public final int read2ByteInteger(String paramString)
    throws ImageReadException, IOException
  {
    int i = this.is.read();
    int j = this.is.read();
    if ((i < 0) || (j < 0))
      throw new ImageReadException(paramString);
    if (this.byteOrder == 77)
      return ((i & 0xFF) << 8) + ((j & 0xFF) << 0);
    return ((j & 0xFF) << 8) + ((i & 0xFF) << 0);
  }

  public final int read2Bytes(String paramString1, String paramString2)
    throws ImageReadException, IOException
  {
    return read2Bytes(paramString1, paramString2, this.byteOrder);
  }

  protected final int read2Bytes(String paramString1, String paramString2, int paramInt)
    throws ImageReadException, IOException
  {
    byte[] arrayOfByte = new byte[2];
    int i = 0;
    while (true)
    {
      if (i >= 2)
        return convertByteArrayToShort(paramString1, arrayOfByte, paramInt);
      int j = this.is.read(arrayOfByte, i, 2 - i);
      if (j < 1)
        throw new IOException(paramString2);
      i += j;
    }
  }

  public final int read3Bytes(String paramString1, String paramString2)
    throws ImageReadException, IOException
  {
    return read3Bytes(paramString1, paramString2, this.byteOrder);
  }

  protected final int read3Bytes(String paramString1, String paramString2, int paramInt)
    throws ImageReadException, IOException
  {
    byte[] arrayOfByte = new byte[3];
    int i = 0;
    while (true)
    {
      if (i >= 3)
        return convertByteArrayToInt(paramString1, arrayOfByte, 0, 3, paramInt);
      int j = this.is.read(arrayOfByte, i, 3 - i);
      if (j < 1)
        throw new IOException(paramString2);
      i += j;
    }
  }

  public final int read4ByteInteger(String paramString)
    throws ImageReadException, IOException
  {
    int i = this.is.read();
    int j = this.is.read();
    int k = this.is.read();
    int m = this.is.read();
    if ((i < 0) || (j < 0) || (k < 0) || (m < 0))
      throw new ImageReadException(paramString);
    if (this.byteOrder == 77)
      return ((i & 0xFF) << 24) + ((j & 0xFF) << 16) + ((k & 0xFF) << 8) + ((m & 0xFF) << 0);
    return ((m & 0xFF) << 24) + ((k & 0xFF) << 16) + ((j & 0xFF) << 8) + ((i & 0xFF) << 0);
  }

  public final int read4Bytes(String paramString1, String paramString2)
    throws ImageReadException, IOException
  {
    return read4Bytes(paramString1, paramString2, this.byteOrder);
  }

  protected final int read4Bytes(String paramString1, String paramString2, int paramInt)
    throws ImageReadException, IOException
  {
    byte[] arrayOfByte = new byte[4];
    int i = 0;
    while (true)
    {
      if (i >= 4)
        return convertByteArrayToInt(paramString1, arrayOfByte, paramInt);
      int j = this.is.read(arrayOfByte, i, 4 - i);
      if (j < 1)
        throw new IOException(paramString2);
      i += j;
    }
  }

  protected final void readAndVerifyBytes(String paramString1, byte[] paramArrayOfByte, String paramString2)
    throws ImageReadException, IOException
  {
    byte[] arrayOfByte = readByteArray(paramString1, paramArrayOfByte.length, paramString2);
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfByte.length)
        return;
      if (arrayOfByte[i] == paramArrayOfByte[i])
        continue;
      System.out.println("i: " + i);
      debugNumber("bytes[" + i + "]", arrayOfByte[i]);
      debugNumber("expected[" + i + "]", paramArrayOfByte[i]);
      throw new ImageReadException(paramString2);
    }
  }

  public final void readAndVerifyBytes(byte[] paramArrayOfByte, String paramString)
    throws ImageReadException, IOException
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfByte.length)
        return;
      int j = this.is.read();
      int k = (byte)(j & 0xFF);
      if ((j >= 0) && (k == paramArrayOfByte[i]))
        continue;
      System.out.println("i: " + i);
      debugByteArray("expected", paramArrayOfByte);
      debugNumber("data[" + i + "]", k);
      throw new ImageReadException(paramString);
    }
  }

  public final byte readByte(String paramString1, String paramString2)
    throws IOException
  {
    int i = this.is.read();
    if (i < 0)
    {
      System.out.println(paramString1 + ": " + i);
      throw new IOException(paramString2);
    }
    if (this.debug)
      debugNumber(paramString1, i);
    return (byte)(i & 0xFF);
  }

  public final byte[] readByteArray(int paramInt, String paramString)
    throws ImageReadException, IOException
  {
    return readByteArray(paramInt, paramString, false, true);
  }

  public final byte[] readByteArray(int paramInt, String paramString, boolean paramBoolean1, boolean paramBoolean2)
    throws ImageReadException, IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    int i = 0;
    while (true)
    {
      int j = read(arrayOfByte, i, paramInt - i);
      if (j <= 0)
      {
        if (i >= paramInt)
          break;
        if (paramBoolean2)
          throw new ImageReadException(paramString);
      }
      else
      {
        i += j;
        continue;
      }
      if (paramBoolean1)
        System.out.println(paramString);
      arrayOfByte = null;
    }
    return arrayOfByte;
  }

  public final byte[] readByteArray(String paramString1, int paramInt, String paramString2)
    throws ImageReadException, IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    int i = 0;
    if (i >= paramInt)
      if (!this.debug);
    for (int k = 0; ; k++)
    {
      if ((k >= paramInt) || (k >= 150))
      {
        return arrayOfByte;
        int j = this.is.read(arrayOfByte, i, paramInt - i);
        if (j < 1)
          throw new IOException(paramString2);
        i += j;
        break;
      }
      debugNumber(paramString1 + " (" + k + ")", 0xFF & arrayOfByte[k]);
    }
  }

  public final byte[] readBytearray(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte;
    if (paramArrayOfByte.length < paramInt1 + paramInt2)
      arrayOfByte = null;
    do
    {
      return arrayOfByte;
      arrayOfByte = new byte[paramInt2];
      System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
    }
    while (!this.debug);
    debugByteArray(paramString, arrayOfByte);
    return arrayOfByte;
  }

  protected final void readRandomBytes()
    throws ImageReadException, IOException
  {
    for (int i = 0; ; i++)
    {
      if (i >= 100)
        return;
      readByte(i, "Random Data");
    }
  }

  protected final void scanForByte(byte paramByte)
    throws IOException
  {
    int i = 0;
    for (int j = 0; ; j++)
    {
      if (i >= 3);
      int k;
      do
      {
        return;
        k = this.is.read();
      }
      while (k < 0);
      if ((k & 0xFF) != paramByte)
        continue;
      System.out.println("\t" + j + ": match.");
      i++;
    }
  }

  protected void setByteOrder(int paramInt)
  {
    this.byteOrder = paramInt;
  }

  protected void setByteOrder(int paramInt1, int paramInt2)
    throws ImageReadException, IOException
  {
    if (paramInt1 != paramInt2)
      throw new ImageReadException("Byte Order bytes don't match (" + paramInt1 + ", " + paramInt2 + ").");
    if (paramInt1 == 77)
    {
      this.byteOrder = paramInt1;
      return;
    }
    if (paramInt1 == 73)
    {
      this.byteOrder = paramInt1;
      return;
    }
    throw new ImageReadException("Unknown Byte Order hint: " + paramInt1);
  }

  public final void setDebug(boolean paramBoolean)
  {
    this.debug = paramBoolean;
  }

  protected void skipBytes(int paramInt)
    throws IOException
  {
    skipBytes(paramInt, "Couldn't skip bytes");
  }

  public final void skipBytes(int paramInt, String paramString)
    throws IOException
  {
    long l1 = 0L;
    while (true)
    {
      if (paramInt == l1)
        return;
      long l2 = this.is.skip(paramInt - l1);
      if (l2 < 1L)
        throw new IOException(paramString + " (" + l2 + ")");
      l1 += l2;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.BinaryInputStream
 * JD-Core Version:    0.6.0
 */