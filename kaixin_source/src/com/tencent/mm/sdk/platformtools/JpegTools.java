package com.tencent.mm.sdk.platformtools;

import java.nio.ByteBuffer;

public class JpegTools
{
  public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
  public static final int ORIENTATION_FLIP_VERTICAL = 4;
  public static final int ORIENTATION_NORMAL = 1;
  public static final int ORIENTATION_ROTATE_180 = 3;
  public static final int ORIENTATION_ROTATE_270 = 8;
  public static final int ORIENTATION_ROTATE_90 = 6;
  public static final int ORIENTATION_TRANSPOSE = 5;
  public static final int ORIENTATION_TRANSVERSE = 7;
  public static final int ORIENTATION_UNDEFINED = 0;
  public static final String TAG = "MicroMsg.JpegTools";
  private MBuf B = null;
  private int C = -1;
  private boolean D = true;

  public JpegTools(byte[] paramArrayOfByte)
  {
    this.B.setBuffer(paramArrayOfByte);
  }

  private void a(int paramInt)
  {
    int i = this.B.getBuffer().position();
    this.B.getBuffer().position(i + paramInt);
  }

  public static String byte2HexString(byte paramByte)
  {
    String str = Integer.toHexString(paramByte & 0xFF);
    if (str.length() == 1)
      str = "0" + str;
    return "" + str.toUpperCase();
  }

  public int getOreiValue()
  {
    if (this.C != -1);
    switch (this.C)
    {
    case 2:
    case 4:
    case 5:
    case 7:
    default:
      return -1;
    case 6:
      return 90;
    case 3:
      return 180;
    case 8:
      return 270;
    case 1:
    }
    return 0;
  }

  public int parserJpeg()
  {
    while (true)
    {
      int i5;
      int i6;
      int i13;
      try
      {
        byte b1 = this.B.getBuffer().get();
        byte b2 = this.B.getBuffer().get();
        if ((byte2HexString(b1).equals("FF")) && (byte2HexString(b2).equals("D8")))
        {
          i = 1;
          if (i != 0)
            break label922;
          Log.w("MicroMsg.JpegTools", "this is not jpeg or no exif data!!!");
          return -1;
          byte b3 = this.B.getBuffer().get();
          byte b4 = this.B.getBuffer().get();
          this.B.getBuffer().get();
          int k = this.B.getBuffer().get();
          if (byte2HexString(b3).equals("FF"))
            continue;
          int m = -1;
          if (m >= 0)
            continue;
          Log.w("MicroMsg.JpegTools", "datalen is error ");
          return -1;
          if ((!byte2HexString(b3).equals("FF")) || (!byte2HexString(b4).equals("E1")))
            continue;
          m = -2 + (k & 0xFF);
          continue;
          if ((!byte2HexString(b3).equals("FF")) || (!byte2HexString(b4).equals("D9")))
            continue;
          m = -1;
          continue;
          int i16 = -2 + (k + this.B.getOffset());
          this.B.getBuffer().position(i16);
          j++;
          if (j <= 100)
            continue;
          Log.e("MicroMsg.JpegTools", "error while!");
          m = -1;
          continue;
          int n = this.B.getBuffer().get();
          int i1 = this.B.getBuffer().get();
          int i2 = this.B.getBuffer().get();
          int i3 = this.B.getBuffer().get();
          if (!((char)n + (char)i1 + (char)i2 + (char)i3).equals("Exif"))
            break label928;
          i4 = 1;
          if (i4 != 0)
            continue;
          Log.w("MicroMsg.JpegTools", "checkExifTag is error");
          return -1;
          a(2);
          i5 = this.B.getBuffer().get();
          i6 = this.B.getBuffer().get();
          if (((char)i5 != 'M') || ((char)i6 != 'M'))
            break label934;
          str = "MM";
          if ((str.equals("MM")) || (str.equals("II")))
            continue;
          Log.w("MicroMsg.JpegTools", "byteOrder  is error " + str);
          return -1;
          this.D = str.equals("MM");
          boolean bool1 = this.D;
          byte b5 = this.B.getBuffer().get();
          byte b6 = this.B.getBuffer().get();
          if ((!bool1) || (!byte2HexString(b5).equals("00")) || (!byte2HexString(b6).equals("2A")))
            continue;
          int i7 = 1;
          if (i7 != 0)
            continue;
          Log.w("MicroMsg.JpegTools", "checkTiffTag  is error ");
          return -1;
          if ((!byte2HexString(b5).equals("2A")) || (!byte2HexString(b6).equals("00")))
            continue;
          i7 = 1;
          continue;
          Log.w("MicroMsg.JpegTools", "checkTiffTag: " + byte2HexString(b5) + " " + byte2HexString(b6));
          i7 = 0;
          continue;
          a(4);
          int i8 = this.B.getBuffer().get();
          int i9 = this.B.getBuffer().get();
          int i10 = i8 & 0xFF;
          if (!this.D)
            break label964;
          i10 = i9 & 0xFF;
          break label964;
          if ((i12 >= i10) || (i12 >= 255))
            continue;
          byte b7 = this.B.getBuffer().get();
          byte b8 = this.B.getBuffer().get();
          if ((!this.D) || (!byte2HexString(b7).equals("01")) || (!byte2HexString(b8).equals("12")))
            continue;
          i11 = 1;
          a(2);
          a(4);
          if (i11 == 0)
            continue;
          boolean bool2 = this.D;
          i13 = this.B.getBuffer().get();
          int i14 = this.B.getBuffer().get();
          a(2);
          if (!bool2)
            break label973;
          i15 = i14 & 0xFF;
          this.C = i15;
          Log.d("MicroMsg.JpegTools", "orei " + this.C);
          return getOreiValue();
          if ((this.D) || (!byte2HexString(b7).equals("12")) || (!byte2HexString(b8).equals("01")))
            continue;
          i11 = 1;
          continue;
          a(4);
          i12++;
          continue;
        }
      }
      catch (Exception localException)
      {
        Log.e("MicroMsg.JpegTools", "parser jpeg error");
        return -1;
      }
      int i = 0;
      continue;
      label922: int j = 0;
      continue;
      label928: int i4 = 0;
      continue;
      label934: if (((char)i5 == 'I') && ((char)i6 == 'I'))
      {
        str = "II";
        continue;
      }
      String str = "";
      continue;
      label964: int i11 = 0;
      int i12 = 0;
      continue;
      label973: int i15 = i13 & 0xFF;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.platformtools.JpegTools
 * JD-Core Version:    0.6.0
 */