package com.kaixin001.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.common.RationalNumber;
import org.apache.sanselan.common.RationalNumberUtilities;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.TiffImageMetadata.GPSInfo;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.apache.sanselan.formats.tiff.constants.TiffFieldTypeConstants;
import org.apache.sanselan.formats.tiff.write.TiffOutputDirectory;
import org.apache.sanselan.formats.tiff.write.TiffOutputField;
import org.apache.sanselan.formats.tiff.write.TiffOutputSet;

public class KXExifInterface
{
  private static final boolean DEBUG = false;
  private static final String TAG = "KXExifInterface";
  public static final TagInfo TAG_DATETIME;
  public static final TagInfo TAG_FLASH;
  public static final TagInfo TAG_GPS_LATITUDE;
  public static final TagInfo TAG_GPS_LATITUDE_REF;
  public static final TagInfo TAG_GPS_LONGITUDE;
  public static final TagInfo TAG_GPS_LONGITUDE_REF;
  public static final TagInfo TAG_IMAGE_LENGTH;
  public static final TagInfo TAG_IMAGE_WIDTH;
  public static final TagInfo TAG_MAKE;
  public static final TagInfo TAG_MODEL;
  public static final TagInfo TAG_ORIENTATION = TiffConstants.EXIF_TAG_ORIENTATION;
  public static final TagInfo TAG_WHITE_BALANCE;
  private TiffImageMetadata mExif = null;
  private boolean mGpsVersionSaved = false;
  private File mJpegFile = null;
  private JpegImageMetadata mJpegMetadata = null;
  private IImageMetadata mMetadata = null;
  private TiffOutputSet mOutputSet = null;

  static
  {
    TAG_DATETIME = TiffConstants.EXIF_TAG_DATE_TIME_ORIGINAL;
    TAG_MAKE = TiffConstants.EXIF_TAG_MAKE;
    TAG_MODEL = TiffConstants.EXIF_TAG_MODEL;
    TAG_FLASH = TiffConstants.EXIF_TAG_FLASH;
    TAG_IMAGE_WIDTH = TiffConstants.EXIF_TAG_IMAGE_WIDTH_IFD0;
    TAG_IMAGE_LENGTH = TiffConstants.EXIF_TAG_IMAGE_HEIGHT_IFD0;
    TAG_GPS_LATITUDE = TiffConstants.GPS_TAG_GPS_LATITUDE;
    TAG_GPS_LONGITUDE = TiffConstants.GPS_TAG_GPS_LONGITUDE;
    TAG_GPS_LATITUDE_REF = TiffConstants.GPS_TAG_GPS_LATITUDE_REF;
    TAG_GPS_LONGITUDE_REF = TiffConstants.GPS_TAG_GPS_LONGITUDE_REF;
    TAG_WHITE_BALANCE = TiffConstants.EXIF_TAG_WHITE_BALANCE_1;
  }

  public KXExifInterface(String paramString)
    throws IOException
  {
    this.mJpegFile = new File(paramString);
    if (!this.mJpegFile.exists());
    while (true)
    {
      return;
      try
      {
        this.mMetadata = Sanselan.getMetadata(this.mJpegFile);
        label68: if (this.mMetadata == null)
        {
          this.mOutputSet = new TiffOutputSet();
          return;
        }
      }
      catch (StringIndexOutOfBoundsException localStringIndexOutOfBoundsException)
      {
        while (true)
          localStringIndexOutOfBoundsException.printStackTrace();
        if (!(this.mMetadata instanceof JpegImageMetadata))
          continue;
        this.mJpegMetadata = ((JpegImageMetadata)this.mMetadata);
        TiffImageMetadata localTiffImageMetadata = this.mJpegMetadata.getExif();
        if (localTiffImageMetadata == null)
          continue;
        try
        {
          this.mOutputSet = localTiffImageMetadata.getOutputSet();
          return;
        }
        catch (ImageWriteException localImageWriteException)
        {
          localImageWriteException.printStackTrace();
          return;
        }
      }
      catch (IOException localIOException)
      {
        break label68;
      }
      catch (ImageReadException localImageReadException)
      {
        break label68;
      }
    }
  }

  // ERROR //
  private static boolean copyFile(File paramFile1, File paramFile2)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 160	java/io/FileInputStream
    //   5: dup
    //   6: aload_0
    //   7: invokespecial 163	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   10: astore_3
    //   11: aload_3
    //   12: aload_1
    //   13: invokestatic 167	com/kaixin001/util/KXExifInterface:copyToFile	(Ljava/io/InputStream;Ljava/io/File;)Z
    //   16: istore 6
    //   18: aload_3
    //   19: invokestatic 173	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   22: iload 6
    //   24: ireturn
    //   25: astore 7
    //   27: aload_2
    //   28: invokestatic 173	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   31: iconst_0
    //   32: ireturn
    //   33: astore 5
    //   35: aload_2
    //   36: invokestatic 173	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   39: aload 5
    //   41: athrow
    //   42: astore 5
    //   44: aload_3
    //   45: astore_2
    //   46: goto -11 -> 35
    //   49: astore 4
    //   51: aload_3
    //   52: astore_2
    //   53: goto -26 -> 27
    //
    // Exception table:
    //   from	to	target	type
    //   2	11	25	java/io/IOException
    //   2	11	33	finally
    //   11	18	42	finally
    //   11	18	49	java/io/IOException
  }

  // ERROR //
  private static boolean copyToFile(java.io.InputStream paramInputStream, File paramFile)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 179	com/kaixin001/util/FileUtil:makeEmptyFile	(Ljava/io/File;)Z
    //   4: ifne +5 -> 9
    //   7: iconst_0
    //   8: ireturn
    //   9: aconst_null
    //   10: astore_2
    //   11: new 181	java/io/FileOutputStream
    //   14: dup
    //   15: aload_1
    //   16: invokespecial 182	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   19: astore_3
    //   20: sipush 4096
    //   23: newarray byte
    //   25: astore 7
    //   27: aload_0
    //   28: aload 7
    //   30: invokevirtual 188	java/io/InputStream:read	([B)I
    //   33: istore 8
    //   35: iload 8
    //   37: ifge +9 -> 46
    //   40: aload_3
    //   41: invokestatic 173	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   44: iconst_1
    //   45: ireturn
    //   46: aload_3
    //   47: aload 7
    //   49: iconst_0
    //   50: iload 8
    //   52: invokevirtual 194	java/io/OutputStream:write	([BII)V
    //   55: goto -28 -> 27
    //   58: astore 5
    //   60: aload_3
    //   61: astore 6
    //   63: aload 6
    //   65: invokestatic 173	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   68: iconst_0
    //   69: ireturn
    //   70: astore 4
    //   72: aload_2
    //   73: invokestatic 173	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   76: aload 4
    //   78: athrow
    //   79: astore 4
    //   81: aload_3
    //   82: astore_2
    //   83: goto -11 -> 72
    //   86: astore 9
    //   88: aconst_null
    //   89: astore 6
    //   91: goto -28 -> 63
    //
    // Exception table:
    //   from	to	target	type
    //   20	27	58	java/io/IOException
    //   27	35	58	java/io/IOException
    //   46	55	58	java/io/IOException
    //   11	20	70	finally
    //   20	27	79	finally
    //   27	35	79	finally
    //   46	55	79	finally
    //   11	20	86	java/io/IOException
  }

  private byte[] getBytesWithZeroEnding(String paramString)
  {
    if (paramString == null)
      return new byte[0];
    int i = paramString.length();
    byte[] arrayOfByte1 = new byte[i + 1];
    byte[] arrayOfByte2 = paramString.getBytes();
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        arrayOfByte1[i] = 0;
        return arrayOfByte1;
      }
      arrayOfByte1[j] = arrayOfByte2[j];
    }
  }

  private static byte[] getRationalArray(double paramDouble1, double paramDouble2, double paramDouble3)
    throws ImageWriteException
  {
    TagInfo localTagInfo = new TagInfo("GPSLongitude", 4, TiffFieldTypeConstants.FIELD_TYPE_RATIONAL);
    RationalNumber localRationalNumber1 = RationalNumberUtilities.getRationalNumber(paramDouble1);
    RationalNumber localRationalNumber2 = RationalNumberUtilities.getRationalNumber(paramDouble2);
    RationalNumber localRationalNumber3 = RationalNumberUtilities.getRationalNumber(paramDouble3);
    ArrayList localArrayList = new ArrayList();
    byte[] arrayOfByte1 = localTagInfo.encodeValue(TiffFieldTypeConstants.FIELD_TYPE_RATIONAL, localRationalNumber1, 77);
    int i = arrayOfByte1.length;
    int j = 0;
    byte[] arrayOfByte2;
    int m;
    label94: byte[] arrayOfByte3;
    int i1;
    label123: byte[] arrayOfByte4;
    if (j >= i)
    {
      arrayOfByte2 = localTagInfo.encodeValue(TiffFieldTypeConstants.FIELD_TYPE_RATIONAL, localRationalNumber2, 77);
      int k = arrayOfByte2.length;
      m = 0;
      if (m < k)
        break label181;
      arrayOfByte3 = localTagInfo.encodeValue(TiffFieldTypeConstants.FIELD_TYPE_RATIONAL, localRationalNumber3, 77);
      int n = arrayOfByte3.length;
      i1 = 0;
      if (i1 < n)
        break label203;
      arrayOfByte4 = new byte[localArrayList.size()];
    }
    for (int i2 = 0; ; i2++)
    {
      if (i2 >= localArrayList.size())
      {
        return arrayOfByte4;
        localArrayList.add(Byte.valueOf(arrayOfByte1[j]));
        j++;
        break;
        label181: localArrayList.add(Byte.valueOf(arrayOfByte2[m]));
        m++;
        break label94;
        label203: localArrayList.add(Byte.valueOf(arrayOfByte3[i1]));
        i1++;
        break label123;
      }
      arrayOfByte4[i2] = ((Byte)localArrayList.get(i2)).byteValue();
    }
  }

  private static String getTagString(TagInfo paramTagInfo, KXExifInterface paramKXExifInterface)
  {
    return paramTagInfo + " : >" + paramKXExifInterface.getAttribute(paramTagInfo) + "<\n";
  }

  private double parseLonLatString(String paramString)
    throws Exception
  {
    if ((paramString == null) || (paramString.equals("")))
      throw new Exception("LonLat String is empty!");
    String[] arrayOfString;
    try
    {
      KXLog.e("LONLATSTR>>>", ">>>" + paramString + "<<<\n");
      arrayOfString = paramString.split(",");
      if (arrayOfString.length != 3)
        throw new Exception("LonLat String does not have 3 part!");
    }
    catch (Exception localException)
    {
      throw localException;
    }
    double d1 = Double.parseDouble(arrayOfString[0].trim()) + Double.parseDouble(arrayOfString[1].trim()) / 60.0D;
    int i = arrayOfString[2].indexOf("/");
    int j = arrayOfString[2].indexOf("(");
    if ((i > 0) && (j > 0) && (i < j))
    {
      double d2 = Double.parseDouble(arrayOfString[2].substring(0, i).trim());
      double d3 = Double.parseDouble(arrayOfString[2].substring(i + 1, j).trim());
      return d1 + d2 / d3 / 3600.0D;
    }
    return d1;
  }

  private void setExifAttribute(TagInfo paramTagInfo, String paramString)
  {
    if ((this.mOutputSet == null) || (paramTagInfo == null) || (paramString == null));
    while (true)
    {
      return;
      if ((paramTagInfo.tag != TAG_ORIENTATION.tag) && (paramTagInfo.tag != TAG_DATETIME.tag) && (paramTagInfo.tag != TAG_FLASH.tag) && (paramTagInfo.tag != TAG_WHITE_BALANCE.tag))
        continue;
      try
      {
        TiffOutputDirectory localTiffOutputDirectory = this.mOutputSet.getOrCreateExifDirectory();
        if (localTiffOutputDirectory == null)
          continue;
        TiffOutputField localTiffOutputField = new TiffOutputField(paramTagInfo, TagInfo.FIELD_TYPE_ASCII, 1 + paramString.length(), getBytesWithZeroEnding(paramString));
        localTiffOutputDirectory.removeField(paramTagInfo.tag);
        localTiffOutputDirectory.add(localTiffOutputField);
        return;
      }
      catch (ImageWriteException localImageWriteException)
      {
        localImageWriteException.printStackTrace();
      }
    }
  }

  // ERROR //
  private void setGpsAttribute(TagInfo paramTagInfo, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 121	com/kaixin001/util/KXExifInterface:mOutputSet	Lorg/apache/sanselan/formats/tiff/write/TiffOutputSet;
    //   4: ifnull +11 -> 15
    //   7: aload_1
    //   8: ifnull +7 -> 15
    //   11: aload_2
    //   12: ifnonnull +4 -> 16
    //   15: return
    //   16: aload_1
    //   17: getfield 344	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   20: getstatic 79	com/kaixin001/util/KXExifInterface:TAG_GPS_LATITUDE	Lorg/apache/sanselan/formats/tiff/constants/TagInfo;
    //   23: getfield 344	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   26: if_icmpeq +42 -> 68
    //   29: aload_1
    //   30: getfield 344	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   33: getstatic 89	com/kaixin001/util/KXExifInterface:TAG_GPS_LATITUDE_REF	Lorg/apache/sanselan/formats/tiff/constants/TagInfo;
    //   36: getfield 344	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   39: if_icmpeq +29 -> 68
    //   42: aload_1
    //   43: getfield 344	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   46: getstatic 84	com/kaixin001/util/KXExifInterface:TAG_GPS_LONGITUDE	Lorg/apache/sanselan/formats/tiff/constants/TagInfo;
    //   49: getfield 344	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   52: if_icmpeq +16 -> 68
    //   55: aload_1
    //   56: getfield 344	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   59: getstatic 94	com/kaixin001/util/KXExifInterface:TAG_GPS_LONGITUDE_REF	Lorg/apache/sanselan/formats/tiff/constants/TagInfo;
    //   62: getfield 344	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   65: if_icmpne -50 -> 15
    //   68: aload_0
    //   69: getfield 121	com/kaixin001/util/KXExifInterface:mOutputSet	Lorg/apache/sanselan/formats/tiff/write/TiffOutputSet;
    //   72: invokevirtual 374	org/apache/sanselan/formats/tiff/write/TiffOutputSet:getOrCreateGPSDirectory	()Lorg/apache/sanselan/formats/tiff/write/TiffOutputDirectory;
    //   75: astore 4
    //   77: aload 4
    //   79: ifnull -64 -> 15
    //   82: aload_0
    //   83: getfield 123	com/kaixin001/util/KXExifInterface:mGpsVersionSaved	Z
    //   86: ifne +75 -> 161
    //   89: aload_0
    //   90: iconst_1
    //   91: putfield 123	com/kaixin001/util/KXExifInterface:mGpsVersionSaved	Z
    //   94: new 210	org/apache/sanselan/formats/tiff/constants/TagInfo
    //   97: dup
    //   98: ldc_w 376
    //   101: iconst_0
    //   102: getstatic 380	org/apache/sanselan/formats/tiff/constants/TiffFieldTypeConstants:FIELD_TYPE_BYTE	Lorg/apache/sanselan/formats/tiff/fieldtypes/FieldTypeByte;
    //   105: invokespecial 221	org/apache/sanselan/formats/tiff/constants/TagInfo:<init>	(Ljava/lang/String;ILorg/apache/sanselan/formats/tiff/fieldtypes/FieldType;)V
    //   108: astore 16
    //   110: getstatic 380	org/apache/sanselan/formats/tiff/constants/TiffFieldTypeConstants:FIELD_TYPE_BYTE	Lorg/apache/sanselan/formats/tiff/fieldtypes/FieldTypeByte;
    //   113: astore 17
    //   115: iconst_4
    //   116: newarray byte
    //   118: astore 18
    //   120: aload 18
    //   122: iconst_0
    //   123: iconst_2
    //   124: bastore
    //   125: aload 18
    //   127: iconst_1
    //   128: iconst_2
    //   129: bastore
    //   130: new 350	org/apache/sanselan/formats/tiff/write/TiffOutputField
    //   133: dup
    //   134: aload 16
    //   136: aload 17
    //   138: iconst_4
    //   139: aload 18
    //   141: invokespecial 359	org/apache/sanselan/formats/tiff/write/TiffOutputField:<init>	(Lorg/apache/sanselan/formats/tiff/constants/TagInfo;Lorg/apache/sanselan/formats/tiff/fieldtypes/FieldType;I[B)V
    //   144: astore 19
    //   146: aload 4
    //   148: getstatic 383	org/apache/sanselan/formats/tiff/constants/TiffConstants:GPS_TAG_GPS_VERSION_ID	Lorg/apache/sanselan/formats/tiff/constants/TagInfo;
    //   151: invokevirtual 386	org/apache/sanselan/formats/tiff/write/TiffOutputDirectory:removeField	(Lorg/apache/sanselan/formats/tiff/constants/TagInfo;)V
    //   154: aload 4
    //   156: aload 19
    //   158: invokevirtual 368	org/apache/sanselan/formats/tiff/write/TiffOutputDirectory:add	(Lorg/apache/sanselan/formats/tiff/write/TiffOutputField;)V
    //   161: aload_1
    //   162: getfield 344	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   165: getstatic 79	com/kaixin001/util/KXExifInterface:TAG_GPS_LATITUDE	Lorg/apache/sanselan/formats/tiff/constants/TagInfo;
    //   168: getfield 344	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   171: if_icmpeq +16 -> 187
    //   174: aload_1
    //   175: getfield 344	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   178: getstatic 84	com/kaixin001/util/KXExifInterface:TAG_GPS_LONGITUDE	Lorg/apache/sanselan/formats/tiff/constants/TagInfo;
    //   181: getfield 344	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   184: if_icmpne +101 -> 285
    //   187: aload_2
    //   188: invokestatic 322	java/lang/Double:parseDouble	(Ljava/lang/String;)D
    //   191: dstore 6
    //   193: ldc2_w 323
    //   196: dload 6
    //   198: dload 6
    //   200: d2i
    //   201: i2d
    //   202: dsub
    //   203: dmul
    //   204: dstore 8
    //   206: ldc2_w 323
    //   209: dload 8
    //   211: dload 8
    //   213: d2i
    //   214: i2d
    //   215: dsub
    //   216: dmul
    //   217: dstore 10
    //   219: new 350	org/apache/sanselan/formats/tiff/write/TiffOutputField
    //   222: dup
    //   223: aload_1
    //   224: getstatic 218	org/apache/sanselan/formats/tiff/constants/TiffFieldTypeConstants:FIELD_TYPE_RATIONAL	Lorg/apache/sanselan/formats/tiff/fieldtypes/FieldTypeRational;
    //   227: iconst_3
    //   228: dload 6
    //   230: d2i
    //   231: i2d
    //   232: dload 8
    //   234: d2i
    //   235: i2d
    //   236: dload 10
    //   238: invokestatic 388	com/kaixin001/util/KXExifInterface:getRationalArray	(DDD)[B
    //   241: invokespecial 359	org/apache/sanselan/formats/tiff/write/TiffOutputField:<init>	(Lorg/apache/sanselan/formats/tiff/constants/TagInfo;Lorg/apache/sanselan/formats/tiff/fieldtypes/FieldType;I[B)V
    //   244: astore 12
    //   246: aload 4
    //   248: aload_1
    //   249: getfield 344	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   252: invokevirtual 365	org/apache/sanselan/formats/tiff/write/TiffOutputDirectory:removeField	(I)V
    //   255: aload 4
    //   257: aload 12
    //   259: invokevirtual 368	org/apache/sanselan/formats/tiff/write/TiffOutputDirectory:add	(Lorg/apache/sanselan/formats/tiff/write/TiffOutputField;)V
    //   262: return
    //   263: astore_3
    //   264: aload_3
    //   265: invokevirtual 156	org/apache/sanselan/ImageWriteException:printStackTrace	()V
    //   268: return
    //   269: astore 5
    //   271: aload 5
    //   273: invokevirtual 389	java/lang/NumberFormatException:printStackTrace	()V
    //   276: return
    //   277: astore 13
    //   279: aload 13
    //   281: invokevirtual 156	org/apache/sanselan/ImageWriteException:printStackTrace	()V
    //   284: return
    //   285: aload_2
    //   286: invokevirtual 392	java/lang/String:toUpperCase	()Ljava/lang/String;
    //   289: astore 14
    //   291: aload_1
    //   292: getfield 344	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   295: getstatic 89	com/kaixin001/util/KXExifInterface:TAG_GPS_LATITUDE_REF	Lorg/apache/sanselan/formats/tiff/constants/TagInfo;
    //   298: getfield 344	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   301: if_icmpne +56 -> 357
    //   304: aload 14
    //   306: ldc_w 394
    //   309: invokevirtual 290	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   312: ifne +37 -> 349
    //   315: ldc_w 396
    //   318: astore 15
    //   320: new 350	org/apache/sanselan/formats/tiff/write/TiffOutputField
    //   323: dup
    //   324: aload_1
    //   325: getstatic 397	org/apache/sanselan/formats/tiff/constants/TiffFieldTypeConstants:FIELD_TYPE_ASCII	Lorg/apache/sanselan/formats/tiff/fieldtypes/FieldTypeASCII;
    //   328: iconst_1
    //   329: aload 15
    //   331: invokevirtual 202	java/lang/String:length	()I
    //   334: iadd
    //   335: aload_0
    //   336: aload 15
    //   338: invokespecial 356	com/kaixin001/util/KXExifInterface:getBytesWithZeroEnding	(Ljava/lang/String;)[B
    //   341: invokespecial 359	org/apache/sanselan/formats/tiff/write/TiffOutputField:<init>	(Lorg/apache/sanselan/formats/tiff/constants/TagInfo;Lorg/apache/sanselan/formats/tiff/fieldtypes/FieldType;I[B)V
    //   344: astore 12
    //   346: goto -100 -> 246
    //   349: ldc_w 394
    //   352: astore 15
    //   354: goto -34 -> 320
    //   357: aload 14
    //   359: ldc_w 399
    //   362: invokevirtual 290	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   365: ifne +11 -> 376
    //   368: ldc_w 401
    //   371: astore 15
    //   373: goto -53 -> 320
    //   376: ldc_w 399
    //   379: astore 15
    //   381: goto -61 -> 320
    //
    // Exception table:
    //   from	to	target	type
    //   68	77	263	org/apache/sanselan/ImageWriteException
    //   187	193	269	java/lang/NumberFormatException
    //   219	246	277	org/apache/sanselan/ImageWriteException
  }

  private void setRootAttribute(TagInfo paramTagInfo, String paramString)
  {
    if ((this.mOutputSet == null) || (paramTagInfo == null) || (paramString == null));
    while (true)
    {
      return;
      if ((paramTagInfo.tag != TAG_MAKE.tag) && (paramTagInfo.tag != TAG_MODEL.tag) && (paramTagInfo.tag != TAG_IMAGE_WIDTH.tag) && (paramTagInfo.tag != TAG_IMAGE_LENGTH.tag))
        continue;
      try
      {
        TiffOutputDirectory localTiffOutputDirectory = this.mOutputSet.getOrCreateRootDirectory();
        if (localTiffOutputDirectory == null)
          continue;
        TiffOutputField localTiffOutputField = new TiffOutputField(paramTagInfo, TagInfo.FIELD_TYPE_ASCII, 1 + paramString.length(), getBytesWithZeroEnding(paramString));
        localTiffOutputDirectory.removeField(paramTagInfo.tag);
        localTiffOutputDirectory.add(localTiffOutputField);
        return;
      }
      catch (ImageWriteException localImageWriteException)
      {
        localImageWriteException.printStackTrace();
      }
    }
  }

  public static void showExif(KXExifInterface paramKXExifInterface)
  {
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("Exif information ---\n");
      localStringBuffer.append(getTagString(TAG_DATETIME, paramKXExifInterface)).append(getTagString(TAG_FLASH, paramKXExifInterface)).append(getTagString(TAG_GPS_LATITUDE, paramKXExifInterface)).append(getTagString(TAG_GPS_LATITUDE_REF, paramKXExifInterface)).append(getTagString(TAG_GPS_LONGITUDE, paramKXExifInterface)).append(getTagString(TAG_GPS_LONGITUDE_REF, paramKXExifInterface)).append(getTagString(TAG_IMAGE_LENGTH, paramKXExifInterface)).append(getTagString(TAG_IMAGE_WIDTH, paramKXExifInterface)).append(getTagString(TAG_MAKE, paramKXExifInterface)).append(getTagString(TAG_MODEL, paramKXExifInterface)).append(getTagString(TAG_ORIENTATION, paramKXExifInterface)).append(getTagString(TAG_WHITE_BALANCE, paramKXExifInterface));
      KXLog.d("KXExifInterface ShowExif>>>", localStringBuffer.toString());
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public String getAttribute(TagInfo paramTagInfo)
  {
    String str1;
    if (this.mJpegMetadata == null)
      str1 = null;
    do
    {
      return str1;
      TiffField localTiffField = this.mJpegMetadata.findEXIFValue(paramTagInfo);
      if (localTiffField == null)
        return null;
      str1 = localTiffField.getValueDescription();
      if (str1 == null)
        return null;
      if (paramTagInfo.tag == TAG_GPS_LATITUDE_REF.tag)
      {
        if (str1.toUpperCase().indexOf("N") >= 0)
          return "N";
        return "N";
      }
      if (paramTagInfo.tag == TAG_GPS_LONGITUDE_REF.tag)
      {
        if (str1.toUpperCase().indexOf("W") >= 0)
          return "W";
        return "E";
      }
      if ((paramTagInfo.tag == TAG_GPS_LATITUDE.tag) || (paramTagInfo.tag == TAG_GPS_LONGITUDE.tag))
      {
        if (str1.equals(""))
          return null;
        try
        {
          String str2 = String.valueOf(parseLonLatString(str1));
          return str2;
        }
        catch (Exception localException)
        {
          return null;
        }
      }
      if (!str1.startsWith("'"))
        continue;
      str1 = str1.substring(1);
    }
    while (!str1.endsWith("'"));
    return str1.substring(0, -1 + str1.length());
  }

  public boolean getLatLong(float[] paramArrayOfFloat)
  {
    if ((paramArrayOfFloat == null) || (this.mExif == null));
    while (true)
    {
      return false;
      if (paramArrayOfFloat.length < 2)
        continue;
      try
      {
        TiffImageMetadata.GPSInfo localGPSInfo = this.mExif.getGPS();
        if (localGPSInfo == null)
          continue;
        double d = localGPSInfo.getLongitudeAsDegreesEast();
        paramArrayOfFloat[0] = (float)localGPSInfo.getLatitudeAsDegreesNorth();
        paramArrayOfFloat[1] = (float)d;
        return true;
      }
      catch (ImageReadException localImageReadException)
      {
        localImageReadException.printStackTrace();
      }
    }
    return false;
  }

  // ERROR //
  public void saveAttributes()
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 113	com/kaixin001/util/KXExifInterface:mJpegFile	Ljava/io/File;
    //   4: ifnonnull +14 -> 18
    //   7: new 103	java/io/IOException
    //   10: dup
    //   11: ldc_w 469
    //   14: invokespecial 470	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   17: athrow
    //   18: aload_0
    //   19: getfield 113	com/kaixin001/util/KXExifInterface:mJpegFile	Ljava/io/File;
    //   22: invokevirtual 473	java/io/File:canWrite	()Z
    //   25: ifne +14 -> 39
    //   28: new 103	java/io/IOException
    //   31: dup
    //   32: ldc_w 475
    //   35: invokespecial 470	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   38: athrow
    //   39: aload_0
    //   40: getfield 121	com/kaixin001/util/KXExifInterface:mOutputSet	Lorg/apache/sanselan/formats/tiff/write/TiffOutputSet;
    //   43: ifnonnull +14 -> 57
    //   46: new 103	java/io/IOException
    //   49: dup
    //   50: ldc_w 477
    //   53: invokespecial 470	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   56: athrow
    //   57: aconst_null
    //   58: astore_1
    //   59: aconst_null
    //   60: astore_2
    //   61: new 261	java/lang/StringBuilder
    //   64: dup
    //   65: ldc_w 479
    //   68: invokespecial 298	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   71: invokestatic 485	java/lang/System:currentTimeMillis	()J
    //   74: invokevirtual 488	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   77: invokevirtual 281	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   80: ldc_w 490
    //   83: invokestatic 494	java/io/File:createTempFile	(Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
    //   86: astore_1
    //   87: new 496	java/io/BufferedOutputStream
    //   90: dup
    //   91: new 181	java/io/FileOutputStream
    //   94: dup
    //   95: aload_1
    //   96: invokespecial 182	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   99: invokespecial 499	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   102: astore 10
    //   104: new 501	org/apache/sanselan/formats/jpeg/exifRewrite/ExifRewriter
    //   107: dup
    //   108: invokespecial 502	org/apache/sanselan/formats/jpeg/exifRewrite/ExifRewriter:<init>	()V
    //   111: aload_0
    //   112: getfield 113	com/kaixin001/util/KXExifInterface:mJpegFile	Ljava/io/File;
    //   115: aload 10
    //   117: aload_0
    //   118: getfield 121	com/kaixin001/util/KXExifInterface:mOutputSet	Lorg/apache/sanselan/formats/tiff/write/TiffOutputSet;
    //   121: invokevirtual 506	org/apache/sanselan/formats/jpeg/exifRewrite/ExifRewriter:updateExifMetadataLossless	(Ljava/io/File;Ljava/io/OutputStream;Lorg/apache/sanselan/formats/tiff/write/TiffOutputSet;)V
    //   124: iconst_1
    //   125: istore 5
    //   127: aload 10
    //   129: invokestatic 173	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   132: iload 5
    //   134: ifeq +16 -> 150
    //   137: aload_1
    //   138: aload_0
    //   139: getfield 113	com/kaixin001/util/KXExifInterface:mJpegFile	Ljava/io/File;
    //   142: invokestatic 508	com/kaixin001/util/KXExifInterface:copyFile	(Ljava/io/File;Ljava/io/File;)Z
    //   145: pop
    //   146: aload_1
    //   147: invokestatic 511	com/kaixin001/util/FileUtil:deleteFileWithoutCheckReturnValue	(Ljava/io/File;)V
    //   150: return
    //   151: astore 9
    //   153: ldc 11
    //   155: ldc_w 513
    //   158: aload 9
    //   160: invokestatic 516	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   163: aload_2
    //   164: invokestatic 173	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   167: iconst_0
    //   168: istore 5
    //   170: goto -38 -> 132
    //   173: astore 8
    //   175: ldc 11
    //   177: ldc_w 513
    //   180: aload 8
    //   182: invokestatic 516	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   185: aload_2
    //   186: invokestatic 173	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   189: iconst_0
    //   190: istore 5
    //   192: goto -60 -> 132
    //   195: astore 4
    //   197: ldc 11
    //   199: ldc_w 513
    //   202: aload 4
    //   204: invokestatic 516	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   207: aload_2
    //   208: invokestatic 173	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   211: iconst_0
    //   212: istore 5
    //   214: goto -82 -> 132
    //   217: astore_3
    //   218: aload_2
    //   219: invokestatic 173	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   222: aload_3
    //   223: athrow
    //   224: astore 6
    //   226: ldc 11
    //   228: ldc_w 513
    //   231: aload 6
    //   233: invokestatic 516	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   236: return
    //   237: astore_3
    //   238: aload 10
    //   240: astore_2
    //   241: goto -23 -> 218
    //   244: astore 4
    //   246: aload 10
    //   248: astore_2
    //   249: goto -52 -> 197
    //   252: astore 8
    //   254: aload 10
    //   256: astore_2
    //   257: goto -82 -> 175
    //   260: astore 9
    //   262: aload 10
    //   264: astore_2
    //   265: goto -112 -> 153
    //
    // Exception table:
    //   from	to	target	type
    //   61	104	151	java/io/IOException
    //   61	104	173	org/apache/sanselan/ImageReadException
    //   61	104	195	org/apache/sanselan/ImageWriteException
    //   61	104	217	finally
    //   153	163	217	finally
    //   175	185	217	finally
    //   197	207	217	finally
    //   137	150	224	java/io/IOException
    //   104	124	237	finally
    //   104	124	244	org/apache/sanselan/ImageWriteException
    //   104	124	252	org/apache/sanselan/ImageReadException
    //   104	124	260	java/io/IOException
  }

  public void setAttribute(TagInfo paramTagInfo, String paramString)
  {
    if ((this.mOutputSet == null) || (paramTagInfo == null) || (paramString == null))
      return;
    if ((paramTagInfo.tag == TAG_MAKE.tag) || (paramTagInfo.tag == TAG_MODEL.tag) || (paramTagInfo.tag == TAG_IMAGE_WIDTH.tag) || (paramTagInfo.tag == TAG_IMAGE_LENGTH.tag))
    {
      setRootAttribute(paramTagInfo, paramString);
      return;
    }
    if ((paramTagInfo.tag == TAG_GPS_LATITUDE.tag) || (paramTagInfo.tag == TAG_GPS_LATITUDE_REF.tag) || (paramTagInfo.tag == TAG_GPS_LONGITUDE.tag) || (paramTagInfo.tag == TAG_GPS_LONGITUDE_REF.tag))
    {
      setGpsAttribute(paramTagInfo, paramString);
      return;
    }
    setExifAttribute(paramTagInfo, paramString);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.KXExifInterface
 * JD-Core Version:    0.6.0
 */