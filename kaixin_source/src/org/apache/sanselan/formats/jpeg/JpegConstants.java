package org.apache.sanselan.formats.jpeg;

public abstract interface JpegConstants
{
  public static final byte[] CONST_8BIM;
  public static final byte[] EOI;
  public static final byte[] EXIF_IDENTIFIER_CODE;
  public static final byte[] JFIF0_SIGNATURE;
  public static final byte[] JFIF0_SIGNATURE_ALTERNATIVE;
  public static final int JFIFMarker = 65504;
  public static final int JPEG_APP0 = 224;
  public static final int JPEG_APP0_Marker = 65504;
  public static final int JPEG_APP13_Marker = 65517;
  public static final int JPEG_APP14_Marker = 65518;
  public static final int JPEG_APP15_Marker = 65519;
  public static final int JPEG_APP1_Marker = 65505;
  public static final int JPEG_APP2_Marker = 65506;
  public static final int[] MARKERS;
  public static final int MAX_SEGMENT_SIZE = 65535;
  public static final byte[] PHOTOSHOP_IDENTIFICATION_STRING;
  public static final int SOF0Marker = 65472;
  public static final int SOF10Marker = 65482;
  public static final int SOF11Marker = 65483;
  public static final int SOF12Marker = 65484;
  public static final int SOF13Marker = 65485;
  public static final int SOF14Marker = 65486;
  public static final int SOF15Marker = 65487;
  public static final int SOF1Marker = 65473;
  public static final int SOF2Marker = 65474;
  public static final int SOF3Marker = 65475;
  public static final int SOF4Marker = 65476;
  public static final int SOF5Marker = 65477;
  public static final int SOF6Marker = 65478;
  public static final int SOF7Marker = 65479;
  public static final int SOF8Marker = 65480;
  public static final int SOF9Marker = 65481;
  public static final byte[] SOI;
  public static final int SOS_Marker = 65498;
  public static final byte[] XMP_IDENTIFIER;
  public static final byte[] icc_profile_label;

  static
  {
    byte[] arrayOfByte1 = new byte[5];
    arrayOfByte1[0] = 74;
    arrayOfByte1[1] = 70;
    arrayOfByte1[2] = 73;
    arrayOfByte1[3] = 70;
    JFIF0_SIGNATURE = arrayOfByte1;
    JFIF0_SIGNATURE_ALTERNATIVE = new byte[] { 74, 70, 73, 70, 32 };
    EXIF_IDENTIFIER_CODE = new byte[] { 69, 120, 105, 102 };
    byte[] arrayOfByte2 = new byte[29];
    arrayOfByte2[0] = 104;
    arrayOfByte2[1] = 116;
    arrayOfByte2[2] = 116;
    arrayOfByte2[3] = 112;
    arrayOfByte2[4] = 58;
    arrayOfByte2[5] = 47;
    arrayOfByte2[6] = 47;
    arrayOfByte2[7] = 110;
    arrayOfByte2[8] = 115;
    arrayOfByte2[9] = 46;
    arrayOfByte2[10] = 97;
    arrayOfByte2[11] = 100;
    arrayOfByte2[12] = 111;
    arrayOfByte2[13] = 98;
    arrayOfByte2[14] = 101;
    arrayOfByte2[15] = 46;
    arrayOfByte2[16] = 99;
    arrayOfByte2[17] = 111;
    arrayOfByte2[18] = 109;
    arrayOfByte2[19] = 47;
    arrayOfByte2[20] = 120;
    arrayOfByte2[21] = 97;
    arrayOfByte2[22] = 112;
    arrayOfByte2[23] = 47;
    arrayOfByte2[24] = 49;
    arrayOfByte2[25] = 46;
    arrayOfByte2[26] = 48;
    arrayOfByte2[27] = 47;
    XMP_IDENTIFIER = arrayOfByte2;
    SOI = new byte[] { -1, -40 };
    EOI = new byte[] { -1, -39 };
    MARKERS = new int[] { 65498, 224, 65504, 65505, 65506, 65517, 65518, 65519, 65504, 65472, 65473, 65474, 65475, 65476, 65477, 65478, 65479, 65480, 65481, 65482, 65483, 65484, 65485, 65486, 65487 };
    byte[] arrayOfByte3 = new byte[12];
    arrayOfByte3[0] = 73;
    arrayOfByte3[1] = 67;
    arrayOfByte3[2] = 67;
    arrayOfByte3[3] = 95;
    arrayOfByte3[4] = 80;
    arrayOfByte3[5] = 82;
    arrayOfByte3[6] = 79;
    arrayOfByte3[7] = 70;
    arrayOfByte3[8] = 73;
    arrayOfByte3[9] = 76;
    arrayOfByte3[10] = 69;
    icc_profile_label = arrayOfByte3;
    byte[] arrayOfByte4 = new byte[14];
    arrayOfByte4[0] = 80;
    arrayOfByte4[1] = 104;
    arrayOfByte4[2] = 111;
    arrayOfByte4[3] = 116;
    arrayOfByte4[4] = 111;
    arrayOfByte4[5] = 115;
    arrayOfByte4[6] = 104;
    arrayOfByte4[7] = 111;
    arrayOfByte4[8] = 112;
    arrayOfByte4[9] = 32;
    arrayOfByte4[10] = 51;
    arrayOfByte4[11] = 46;
    arrayOfByte4[12] = 48;
    PHOTOSHOP_IDENTIFICATION_STRING = arrayOfByte4;
    CONST_8BIM = new byte[] { 56, 66, 73, 77 };
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.jpeg.JpegConstants
 * JD-Core Version:    0.6.0
 */