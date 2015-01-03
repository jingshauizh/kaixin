package org.apache.sanselan.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;

public class BinaryFileFunctions
  implements BinaryConstants
{
  protected boolean debug = false;

  public static final int CharsToQuad(char paramChar1, char paramChar2, char paramChar3, char paramChar4)
  {
    return (paramChar1 & 0xFF) << '\030' | (paramChar2 & 0xFF) << '\020' | (paramChar3 & 0xFF) << '\b' | (paramChar4 & 0xFF) << '\000';
  }

  public static final boolean compareBytes(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3)
  {
    if (paramArrayOfByte1.length < paramInt1 + paramInt3);
    do
      return false;
    while (paramArrayOfByte2.length < paramInt2 + paramInt3);
    for (int i = 0; ; i++)
    {
      if (i >= paramInt3)
        return true;
      if (paramArrayOfByte1[(paramInt1 + i)] != paramArrayOfByte2[(paramInt2 + i)])
        break;
    }
  }

  public static final boolean compareBytes(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    if (paramArrayOfByte1.length != paramArrayOfByte2.length)
      return false;
    return compareBytes(paramArrayOfByte1, 0, paramArrayOfByte2, 0, paramArrayOfByte1.length);
  }

  public static final byte[] head(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramInt > paramArrayOfByte.length)
      paramInt = paramArrayOfByte.length;
    return slice(paramArrayOfByte, 0, paramInt);
  }

  protected static final byte[] int2ToByteArray(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 77)
    {
      byte[] arrayOfByte2 = new byte[2];
      arrayOfByte2[0] = (byte)(paramInt1 >> 8);
      arrayOfByte2[1] = (byte)(paramInt1 >> 0);
      return arrayOfByte2;
    }
    byte[] arrayOfByte1 = new byte[2];
    arrayOfByte1[0] = (byte)(paramInt1 >> 0);
    arrayOfByte1[1] = (byte)(paramInt1 >> 8);
    return arrayOfByte1;
  }

  public static final byte[] slice(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramArrayOfByte.length < paramInt1 + paramInt2)
      return null;
    byte[] arrayOfByte = new byte[paramInt2];
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
    return arrayOfByte;
  }

  public static final byte[] tail(byte[] paramArrayOfByte, int paramInt)
  {
    if (paramInt > paramArrayOfByte.length)
      paramInt = paramArrayOfByte.length;
    return slice(paramArrayOfByte, paramArrayOfByte.length - paramInt, paramInt);
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
      if (paramArrayOfByte1[(paramInt1 + i)] != paramArrayOfByte2[(paramInt2 + i)])
        break;
    }
  }

  public final boolean compareByteArrays(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    if (paramArrayOfByte1.length != paramArrayOfByte2.length)
      return false;
    return compareByteArrays(paramArrayOfByte1, 0, paramArrayOfByte2, 0, paramArrayOfByte1.length);
  }

  protected final double convertByteArrayToDouble(String paramString, byte[] paramArrayOfByte, int paramInt)
  {
    return convertByteArrayToDouble(paramString, paramArrayOfByte, 0, paramInt);
  }

  protected final double convertByteArrayToDouble(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfByte[(paramInt1 + 0)];
    int j = paramArrayOfByte[(paramInt1 + 1)];
    int k = paramArrayOfByte[(paramInt1 + 2)];
    int m = paramArrayOfByte[(paramInt1 + 3)];
    int n = paramArrayOfByte[(paramInt1 + 4)];
    int i1 = paramArrayOfByte[(paramInt1 + 5)];
    int i2 = paramArrayOfByte[(paramInt1 + 6)];
    int i3 = paramArrayOfByte[(paramInt1 + 7)];
    long l;
    if (paramInt2 == 77)
      l = (i & 0xFF) << 56 | (j & 0xFF) << 48 | (k & 0xFF) << 40 | (m & 0xFF) << 32 | (n & 0xFF) << 24 | (i1 & 0xFF) << 16 | (i2 & 0xFF) << 8 | (i3 & 0xFF) << 0;
    while (true)
    {
      return Double.longBitsToDouble(l);
      l = (i3 & 0xFF) << 56 | (i2 & 0xFF) << 48 | (i1 & 0xFF) << 40 | (n & 0xFF) << 32 | (m & 0xFF) << 24 | (k & 0xFF) << 16 | (j & 0xFF) << 8 | (i & 0xFF) << 0;
    }
  }

  protected final double[] convertByteArrayToDoubleArray(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt1 + paramInt2 * 8;
    double[] arrayOfDouble;
    if (paramArrayOfByte.length < i)
    {
      System.out.println(paramString + ": expected length: " + i + ", actual length: " + paramArrayOfByte.length);
      arrayOfDouble = null;
    }
    while (true)
    {
      return arrayOfDouble;
      arrayOfDouble = new double[paramInt2];
      for (int j = 0; j < paramInt2; j++)
        arrayOfDouble[j] = convertByteArrayToDouble(paramString, paramArrayOfByte, paramInt1 + j * 8, paramInt3);
    }
  }

  protected final float convertByteArrayToFloat(String paramString, byte[] paramArrayOfByte, int paramInt)
  {
    return convertByteArrayToFloat(paramString, paramArrayOfByte, 0, paramInt);
  }

  protected final float convertByteArrayToFloat(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfByte[(paramInt1 + 0)];
    int j = paramArrayOfByte[(paramInt1 + 1)];
    int k = paramArrayOfByte[(paramInt1 + 2)];
    int m = paramArrayOfByte[(paramInt1 + 3)];
    if (paramInt2 == 77);
    for (int n = (i & 0xFF) << 24 | (j & 0xFF) << 16 | (k & 0xFF) << 8 | (m & 0xFF) << 0; ; n = (m & 0xFF) << 24 | (k & 0xFF) << 16 | (j & 0xFF) << 8 | (i & 0xFF) << 0)
      return Float.intBitsToFloat(n);
  }

  protected final float[] convertByteArrayToFloatArray(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt1 + paramInt2 * 4;
    float[] arrayOfFloat;
    if (paramArrayOfByte.length < i)
    {
      System.out.println(paramString + ": expected length: " + i + ", actual length: " + paramArrayOfByte.length);
      arrayOfFloat = null;
    }
    while (true)
    {
      return arrayOfFloat;
      arrayOfFloat = new float[paramInt2];
      for (int j = 0; j < paramInt2; j++)
        arrayOfFloat[j] = convertByteArrayToFloat(paramString, paramArrayOfByte, paramInt1 + j * 4, paramInt3);
    }
  }

  protected final int convertByteArrayToInt(String paramString, byte[] paramArrayOfByte, int paramInt)
  {
    return convertByteArrayToInt(paramString, paramArrayOfByte, 0, paramInt);
  }

  protected final int convertByteArrayToInt(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfByte[(paramInt1 + 0)];
    int j = paramArrayOfByte[(paramInt1 + 1)];
    int k = paramArrayOfByte[(paramInt1 + 2)];
    int m = paramArrayOfByte[(paramInt1 + 3)];
    if (paramInt2 == 77);
    for (int n = (i & 0xFF) << 24 | (j & 0xFF) << 16 | (k & 0xFF) << 8 | (m & 0xFF) << 0; ; n = (m & 0xFF) << 24 | (k & 0xFF) << 16 | (j & 0xFF) << 8 | (i & 0xFF) << 0)
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
        arrayOfInt[j] = convertByteArrayToInt(paramString, paramArrayOfByte, paramInt1 + j * 4, paramInt3);
    }
  }

  protected final RationalNumber convertByteArrayToRational(String paramString, byte[] paramArrayOfByte, int paramInt)
  {
    return convertByteArrayToRational(paramString, paramArrayOfByte, 0, paramInt);
  }

  protected final RationalNumber convertByteArrayToRational(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return new RationalNumber(convertByteArrayToInt(paramString, paramArrayOfByte, paramInt1 + 0, paramInt2), convertByteArrayToInt(paramString, paramArrayOfByte, paramInt1 + 4, paramInt2));
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

  protected final int convertByteArrayToShort(String paramString, int paramInt1, byte[] paramArrayOfByte, int paramInt2)
    throws ImageReadException
  {
    if (paramInt1 + 1 >= paramArrayOfByte.length)
      throw new ImageReadException("Index out of bounds. Array size: " + paramArrayOfByte.length + ", index: " + paramInt1);
    int i = 0xFF & paramArrayOfByte[(paramInt1 + 0)];
    int j = 0xFF & paramArrayOfByte[(paramInt1 + 1)];
    if (paramInt2 == 77);
    for (int k = j | i << 8; ; k = i | j << 8)
    {
      if (this.debug)
        debugNumber(paramString, k, 2);
      return k;
    }
  }

  protected final int convertByteArrayToShort(String paramString, byte[] paramArrayOfByte, int paramInt)
    throws ImageReadException
  {
    return convertByteArrayToShort(paramString, 0, paramArrayOfByte, paramInt);
  }

  protected final int[] convertByteArrayToShortArray(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
    throws ImageReadException
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

  protected final byte[] convertDoubleArrayToByteArray(double[] paramArrayOfDouble, int paramInt)
  {
    byte[] arrayOfByte = new byte[8 * paramArrayOfDouble.length];
    int i = 0;
    if (i >= paramArrayOfDouble.length)
      return arrayOfByte;
    long l = Double.doubleToRawLongBits(paramArrayOfDouble[i]);
    int j = i * 8;
    if (paramInt == 77)
    {
      arrayOfByte[(j + 0)] = (byte)(int)(0xFF & l >> 0);
      arrayOfByte[(j + 1)] = (byte)(int)(0xFF & l >> 8);
      arrayOfByte[(j + 2)] = (byte)(int)(0xFF & l >> 16);
      arrayOfByte[(j + 3)] = (byte)(int)(0xFF & l >> 24);
      arrayOfByte[(j + 4)] = (byte)(int)(0xFF & l >> 32);
      arrayOfByte[(j + 5)] = (byte)(int)(0xFF & l >> 40);
      arrayOfByte[(j + 6)] = (byte)(int)(0xFF & l >> 48);
      arrayOfByte[(j + 7)] = (byte)(int)(0xFF & l >> 56);
    }
    while (true)
    {
      i++;
      break;
      arrayOfByte[(j + 7)] = (byte)(int)(0xFF & l >> 0);
      arrayOfByte[(j + 6)] = (byte)(int)(0xFF & l >> 8);
      arrayOfByte[(j + 5)] = (byte)(int)(0xFF & l >> 16);
      arrayOfByte[(j + 4)] = (byte)(int)(0xFF & l >> 24);
      arrayOfByte[(j + 3)] = (byte)(int)(0xFF & l >> 32);
      arrayOfByte[(j + 2)] = (byte)(int)(0xFF & l >> 40);
      arrayOfByte[(j + 1)] = (byte)(int)(0xFF & l >> 48);
      arrayOfByte[(j + 0)] = (byte)(int)(0xFF & l >> 56);
    }
  }

  protected final byte[] convertDoubleToByteArray(double paramDouble, int paramInt)
  {
    byte[] arrayOfByte = new byte[8];
    long l = Double.doubleToRawLongBits(paramDouble);
    if (paramInt == 77)
    {
      arrayOfByte[0] = (byte)(int)(0xFF & l >> 0);
      arrayOfByte[1] = (byte)(int)(0xFF & l >> 8);
      arrayOfByte[2] = (byte)(int)(0xFF & l >> 16);
      arrayOfByte[3] = (byte)(int)(0xFF & l >> 24);
      arrayOfByte[4] = (byte)(int)(0xFF & l >> 32);
      arrayOfByte[5] = (byte)(int)(0xFF & l >> 40);
      arrayOfByte[6] = (byte)(int)(0xFF & l >> 48);
      arrayOfByte[7] = (byte)(int)(0xFF & l >> 56);
      return arrayOfByte;
    }
    arrayOfByte[7] = (byte)(int)(0xFF & l >> 0);
    arrayOfByte[6] = (byte)(int)(0xFF & l >> 8);
    arrayOfByte[5] = (byte)(int)(0xFF & l >> 16);
    arrayOfByte[4] = (byte)(int)(0xFF & l >> 24);
    arrayOfByte[3] = (byte)(int)(0xFF & l >> 32);
    arrayOfByte[2] = (byte)(int)(0xFF & l >> 40);
    arrayOfByte[1] = (byte)(int)(0xFF & l >> 48);
    arrayOfByte[0] = (byte)(int)(0xFF & l >> 56);
    return arrayOfByte;
  }

  protected final byte[] convertFloatArrayToByteArray(float[] paramArrayOfFloat, int paramInt)
  {
    byte[] arrayOfByte = new byte[4 * paramArrayOfFloat.length];
    int i = 0;
    if (i >= paramArrayOfFloat.length)
      return arrayOfByte;
    int j = Float.floatToRawIntBits(paramArrayOfFloat[i]);
    int k = i * 4;
    if (paramInt == 77)
    {
      arrayOfByte[(k + 0)] = (byte)(0xFF & j >> 0);
      arrayOfByte[(k + 1)] = (byte)(0xFF & j >> 8);
      arrayOfByte[(k + 2)] = (byte)(0xFF & j >> 16);
      arrayOfByte[(k + 3)] = (byte)(0xFF & j >> 24);
    }
    while (true)
    {
      i++;
      break;
      arrayOfByte[(k + 3)] = (byte)(0xFF & j >> 0);
      arrayOfByte[(k + 2)] = (byte)(0xFF & j >> 8);
      arrayOfByte[(k + 1)] = (byte)(0xFF & j >> 16);
      arrayOfByte[(k + 0)] = (byte)(0xFF & j >> 24);
    }
  }

  protected final byte[] convertFloatToByteArray(float paramFloat, int paramInt)
  {
    byte[] arrayOfByte = new byte[4];
    int i = Float.floatToRawIntBits(paramFloat);
    if (paramInt == 77)
    {
      arrayOfByte[0] = (byte)(0xFF & i >> 0);
      arrayOfByte[1] = (byte)(0xFF & i >> 8);
      arrayOfByte[2] = (byte)(0xFF & i >> 16);
      arrayOfByte[3] = (byte)(0xFF & i >> 24);
      return arrayOfByte;
    }
    arrayOfByte[3] = (byte)(0xFF & i >> 0);
    arrayOfByte[2] = (byte)(0xFF & i >> 8);
    arrayOfByte[1] = (byte)(0xFF & i >> 16);
    arrayOfByte[0] = (byte)(0xFF & i >> 24);
    return arrayOfByte;
  }

  protected final byte[] convertIntArrayToByteArray(int[] paramArrayOfInt, int paramInt)
  {
    byte[] arrayOfByte = new byte[4 * paramArrayOfInt.length];
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfInt.length)
        return arrayOfByte;
      writeIntInToByteArray(paramArrayOfInt[i], arrayOfByte, i * 4, paramInt);
    }
  }

  protected final byte[] convertIntArrayToRationalArray(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
    throws ImageWriteException
  {
    if (paramArrayOfInt1.length != paramArrayOfInt2.length)
      throw new ImageWriteException("numerators.length (" + paramArrayOfInt1.length + " != denominators.length (" + paramArrayOfInt2.length + ")");
    byte[] arrayOfByte = new byte[8 * paramArrayOfInt1.length];
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfInt1.length)
        return arrayOfByte;
      writeIntInToByteArray(paramArrayOfInt1[i], arrayOfByte, i * 8, paramInt);
      writeIntInToByteArray(paramArrayOfInt2[i], arrayOfByte, 4 + i * 8, paramInt);
    }
  }

  protected final byte[] convertRationalArrayToByteArray(RationalNumber[] paramArrayOfRationalNumber, int paramInt)
    throws ImageWriteException
  {
    byte[] arrayOfByte = new byte[8 * paramArrayOfRationalNumber.length];
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfRationalNumber.length)
        return arrayOfByte;
      writeIntInToByteArray(paramArrayOfRationalNumber[i].numerator, arrayOfByte, i * 8, paramInt);
      writeIntInToByteArray(paramArrayOfRationalNumber[i].divisor, arrayOfByte, 4 + i * 8, paramInt);
    }
  }

  protected final byte[] convertRationalToByteArray(RationalNumber paramRationalNumber, int paramInt)
    throws ImageWriteException
  {
    byte[] arrayOfByte = new byte[8];
    writeIntInToByteArray(paramRationalNumber.numerator, arrayOfByte, 0, paramInt);
    writeIntInToByteArray(paramRationalNumber.divisor, arrayOfByte, 4, paramInt);
    return arrayOfByte;
  }

  protected final byte[] convertShortArrayToByteArray(int[] paramArrayOfInt, int paramInt)
  {
    byte[] arrayOfByte = new byte[2 * paramArrayOfInt.length];
    int i = 0;
    if (i >= paramArrayOfInt.length)
      return arrayOfByte;
    int j = paramArrayOfInt[i];
    if (paramInt == 77)
    {
      arrayOfByte[(0 + i * 2)] = (byte)(j >> 8);
      arrayOfByte[(1 + i * 2)] = (byte)(j >> 0);
    }
    while (true)
    {
      i++;
      break;
      arrayOfByte[(1 + i * 2)] = (byte)(j >> 8);
      arrayOfByte[(0 + i * 2)] = (byte)(j >> 0);
    }
  }

  protected final byte[] convertShortToByteArray(int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = new byte[2];
    if (paramInt2 == 77)
    {
      arrayOfByte[0] = (byte)(paramInt1 >> 8);
      arrayOfByte[1] = (byte)(paramInt1 >> 0);
      return arrayOfByte;
    }
    arrayOfByte[1] = (byte)(paramInt1 >> 8);
    arrayOfByte[0] = (byte)(paramInt1 >> 0);
    return arrayOfByte;
  }

  public final void copyStreamToStream(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    byte[] arrayOfByte = new byte[1024];
    while (true)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i <= 0)
        return;
      paramOutputStream.write(arrayOfByte, 0, i);
    }
  }

  public final void debugByteArray(String paramString, byte[] paramArrayOfByte)
  {
    System.out.println(paramString + ": " + paramArrayOfByte.length);
    for (int i = 0; ; i++)
    {
      if ((i >= paramArrayOfByte.length) || (i >= 50))
        return;
      debugNumber("\t (" + i + ")", 0xFF & paramArrayOfByte[i]);
    }
  }

  public final void debugNumber(PrintWriter paramPrintWriter, String paramString, int paramInt)
  {
    debugNumber(paramPrintWriter, paramString, paramInt, 1);
  }

  public final void debugNumber(PrintWriter paramPrintWriter, String paramString, int paramInt1, int paramInt2)
  {
    paramPrintWriter.print(paramString + ": " + paramInt1 + " (");
    int i = paramInt1;
    for (int j = 0; ; j++)
    {
      if (j >= paramInt2)
      {
        paramPrintWriter.println(") [0x" + Integer.toHexString(paramInt1) + ", " + Integer.toBinaryString(paramInt1) + "]");
        paramPrintWriter.flush();
        return;
      }
      if (j > 0)
        paramPrintWriter.print(",");
      int k = i & 0xFF;
      paramPrintWriter.print((char)k + " [" + k + "]");
      i >>= 8;
    }
  }

  public final void debugNumber(String paramString, int paramInt)
  {
    debugNumber(paramString, paramInt, 1);
  }

  public final void debugNumber(String paramString, int paramInt1, int paramInt2)
  {
    PrintWriter localPrintWriter = new PrintWriter(System.out);
    debugNumber(localPrintWriter, paramString, paramInt1, paramInt2);
    localPrintWriter.flush();
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

  protected final byte[] getByteArrayTail(String paramString, byte[] paramArrayOfByte, int paramInt)
    throws ImageReadException
  {
    return readBytearray(paramString, paramArrayOfByte, paramInt, paramArrayOfByte.length - paramInt);
  }

  protected final byte[] getBytearrayHead(String paramString, byte[] paramArrayOfByte, int paramInt)
    throws ImageReadException
  {
    return readBytearray(paramString, paramArrayOfByte, 0, paramArrayOfByte.length - paramInt);
  }

  public final boolean getDebug()
  {
    return this.debug;
  }

  protected final byte[] getRAFBytes(RandomAccessFile paramRandomAccessFile, long paramLong, int paramInt, String paramString)
    throws IOException
  {
    if (this.debug)
    {
      System.out.println("getRAFBytes pos: " + paramLong);
      System.out.println("getRAFBytes length: " + paramInt);
    }
    byte[] arrayOfByte = new byte[paramInt];
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

  public final byte[] getStreamBytes(InputStream paramInputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    copyStreamToStream(paramInputStream, localByteArrayOutputStream);
    return localByteArrayOutputStream.toByteArray();
  }

  protected final void printByteBits(String paramString, byte paramByte)
  {
    System.out.println(paramString + ": '" + Integer.toBinaryString(paramByte & 0xFF));
  }

  protected final void printCharQuad(PrintWriter paramPrintWriter, String paramString, int paramInt)
  {
    paramPrintWriter.println(paramString + ": '" + (char)(0xFF & paramInt >> 24) + (char)(0xFF & paramInt >> 16) + (char)(0xFF & paramInt >> 8) + (char)(0xFF & paramInt >> 0) + "'");
  }

  protected final void printCharQuad(String paramString, int paramInt)
  {
    System.out.println(paramString + ": '" + (char)(0xFF & paramInt >> 24) + (char)(0xFF & paramInt >> 16) + (char)(0xFF & paramInt >> 8) + (char)(0xFF & paramInt >> 0) + "'");
  }

  protected final int read2Bytes(String paramString1, InputStream paramInputStream, String paramString2, int paramInt)
    throws ImageReadException, IOException
  {
    byte[] arrayOfByte = new byte[2];
    int i = 0;
    while (true)
    {
      if (i >= 2)
        return convertByteArrayToShort(paramString1, arrayOfByte, paramInt);
      int j = paramInputStream.read(arrayOfByte, i, 2 - i);
      if (j < 1)
        throw new IOException(paramString2);
      i += j;
    }
  }

  protected final int read3Bytes(String paramString1, InputStream paramInputStream, String paramString2, int paramInt)
    throws ImageReadException, IOException
  {
    int i = (byte)paramInputStream.read();
    int j = (byte)paramInputStream.read();
    int k = (byte)paramInputStream.read();
    if (paramInt == 77);
    for (int m = (i & 0xFF) << 16 | (j & 0xFF) << 8 | (k & 0xFF) << 0; ; m = (k & 0xFF) << 16 | (j & 0xFF) << 8 | (i & 0xFF) << 0)
    {
      if (this.debug)
        debugNumber(paramString1, m, 3);
      return m;
    }
  }

  protected final int read4Bytes(String paramString1, InputStream paramInputStream, String paramString2, int paramInt)
    throws ImageReadException, IOException
  {
    byte[] arrayOfByte = new byte[4];
    int i = 0;
    while (true)
    {
      if (i >= 4)
        return convertByteArrayToInt(paramString1, arrayOfByte, paramInt);
      int j = paramInputStream.read(arrayOfByte, i, 4 - i);
      if (j < 1)
        throw new IOException(paramString2);
      i += j;
    }
  }

  public final void readAndVerifyBytes(InputStream paramInputStream, byte[] paramArrayOfByte, String paramString)
    throws ImageReadException, IOException
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfByte.length)
        return;
      int j = paramInputStream.read();
      int k = (byte)(j & 0xFF);
      if (j < 0)
        throw new ImageReadException("Unexpected EOF.");
      if (k == paramArrayOfByte[i])
        continue;
      throw new ImageReadException(paramString);
    }
  }

  protected final void readAndVerifyBytes(String paramString1, InputStream paramInputStream, byte[] paramArrayOfByte, String paramString2)
    throws ImageReadException, IOException
  {
    byte[] arrayOfByte = readByteArray(paramString1, paramArrayOfByte.length, paramInputStream, paramString2);
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfByte.length)
        return;
      if (arrayOfByte[i] == paramArrayOfByte[i])
        continue;
      throw new ImageReadException(paramString2);
    }
  }

  public final byte readByte(String paramString1, InputStream paramInputStream, String paramString2)
    throws ImageReadException, IOException
  {
    int i = paramInputStream.read();
    if (i < 0)
    {
      System.out.println(paramString1 + ": " + i);
      throw new IOException(paramString2);
    }
    if (this.debug)
      debugNumber(paramString1, i);
    return (byte)(i & 0xFF);
  }

  public final byte[] readByteArray(String paramString, int paramInt, InputStream paramInputStream)
    throws IOException
  {
    return readByteArray(paramString, paramInt, paramInputStream, paramString + " could not be read.");
  }

  public final byte[] readByteArray(String paramString1, int paramInt, InputStream paramInputStream, String paramString2)
    throws IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    int i = 0;
    if (i >= paramInt)
      if (!this.debug);
    for (int k = 0; ; k++)
    {
      if ((k >= paramInt) || (k >= 50))
      {
        return arrayOfByte;
        int j = paramInputStream.read(arrayOfByte, i, paramInt - i);
        if (j < 1)
          throw new IOException(paramString2);
        i += j;
        break;
      }
      debugNumber(paramString1 + " (" + k + ")", 0xFF & arrayOfByte[k]);
    }
  }

  public final byte[] readBytearray(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws ImageReadException
  {
    if (paramArrayOfByte.length < paramInt1 + paramInt2)
      throw new ImageReadException("Invalid read. bytes.length: " + paramArrayOfByte.length + ", start: " + paramInt1 + ", count: " + paramInt2);
    byte[] arrayOfByte = new byte[paramInt2];
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
    if (this.debug)
      debugByteArray(paramString, arrayOfByte);
    return arrayOfByte;
  }

  public final byte[] readBytes(InputStream paramInputStream, int paramInt)
    throws ImageReadException, IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    for (int i = 0; ; i++)
    {
      if (i >= paramInt)
        return arrayOfByte;
      arrayOfByte[i] = (byte)paramInputStream.read();
    }
  }

  protected final void readRandomBytes(InputStream paramInputStream)
    throws ImageReadException, IOException
  {
    for (int i = 0; ; i++)
    {
      if (i >= 100)
        return;
      readByte(i, paramInputStream, "Random Data");
    }
  }

  protected final void scanForByte(InputStream paramInputStream, byte paramByte)
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
        k = paramInputStream.read();
      }
      while (k < 0);
      if ((k & 0xFF) != paramByte)
        continue;
      System.out.println("\t" + j + ": match.");
      i++;
    }
  }

  public final void setDebug(boolean paramBoolean)
  {
    this.debug = paramBoolean;
  }

  protected void skipBytes(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    skipBytes(paramInputStream, paramInt, "Couldn't skip bytes");
  }

  public final void skipBytes(InputStream paramInputStream, int paramInt, String paramString)
    throws IOException
  {
    long l1 = 0L;
    while (true)
    {
      if (paramInt == l1)
        return;
      long l2 = paramInputStream.skip(paramInt - l1);
      if (l2 < 1L)
        throw new IOException(paramString + " (" + l2 + ")");
      l1 += l2;
    }
  }

  public final boolean startsWith(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    if (paramArrayOfByte2 == null);
    do
      return false;
    while ((paramArrayOfByte1 == null) || (paramArrayOfByte2.length > paramArrayOfByte1.length));
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfByte2.length)
        return true;
      if (paramArrayOfByte2[i] != paramArrayOfByte1[i])
        break;
    }
  }

  protected final void writeIntInToByteArray(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    if (paramInt3 == 77)
    {
      paramArrayOfByte[(paramInt2 + 0)] = (byte)(paramInt1 >> 24);
      paramArrayOfByte[(paramInt2 + 1)] = (byte)(paramInt1 >> 16);
      paramArrayOfByte[(paramInt2 + 2)] = (byte)(paramInt1 >> 8);
      paramArrayOfByte[(paramInt2 + 3)] = (byte)(paramInt1 >> 0);
      return;
    }
    paramArrayOfByte[(paramInt2 + 3)] = (byte)(paramInt1 >> 24);
    paramArrayOfByte[(paramInt2 + 2)] = (byte)(paramInt1 >> 16);
    paramArrayOfByte[(paramInt2 + 1)] = (byte)(paramInt1 >> 8);
    paramArrayOfByte[(paramInt2 + 0)] = (byte)(paramInt1 >> 0);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.BinaryFileFunctions
 * JD-Core Version:    0.6.0
 */