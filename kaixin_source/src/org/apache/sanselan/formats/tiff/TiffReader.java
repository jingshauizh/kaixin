package org.apache.sanselan.formats.tiff;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.sanselan.FormatCompliance;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.common.BinaryFileParser;
import org.apache.sanselan.common.byteSources.ByteSource;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.apache.sanselan.util.Debug;

public class TiffReader extends BinaryFileParser
  implements TiffConstants
{
  private final boolean strict;

  public TiffReader(boolean paramBoolean)
  {
    this.strict = paramBoolean;
  }

  private JpegImageData getJpegRawImageData(ByteSource paramByteSource, TiffDirectory paramTiffDirectory)
    throws ImageReadException, IOException
  {
    TiffDirectory.ImageDataElement localImageDataElement = paramTiffDirectory.getJpegRawImageDataElement();
    int i = localImageDataElement.offset;
    int j = localImageDataElement.length;
    if (i + j == 1L + paramByteSource.getLength())
      j--;
    return new JpegImageData(i, j, paramByteSource.getBlock(i, j));
  }

  private void readDirectories(ByteSource paramByteSource, FormatCompliance paramFormatCompliance, Listener paramListener)
    throws ImageReadException, IOException
  {
    TiffHeader localTiffHeader = readTiffHeader(paramByteSource, paramFormatCompliance);
    if (!paramListener.setTiffHeader(localTiffHeader))
      return;
    readDirectory(paramByteSource, localTiffHeader.offsetToFirstIFD, 0, paramFormatCompliance, paramListener, new ArrayList());
  }

  private boolean readDirectory(ByteSource paramByteSource, int paramInt1, int paramInt2, FormatCompliance paramFormatCompliance, Listener paramListener, List paramList)
    throws ImageReadException, IOException
  {
    return readDirectory(paramByteSource, paramInt1, paramInt2, paramFormatCompliance, paramListener, false, paramList);
  }

  // ERROR //
  private boolean readDirectory(ByteSource paramByteSource, int paramInt1, int paramInt2, FormatCompliance paramFormatCompliance, Listener paramListener, boolean paramBoolean, List paramList)
    throws ImageReadException, IOException
  {
    // Byte code:
    //   0: new 82	java/lang/Integer
    //   3: dup
    //   4: iload_2
    //   5: invokespecial 85	java/lang/Integer:<init>	(I)V
    //   8: astore 8
    //   10: aload 7
    //   12: aload 8
    //   14: invokeinterface 91 2 0
    //   19: ifeq +5 -> 24
    //   22: iconst_0
    //   23: ireturn
    //   24: aload 7
    //   26: aload 8
    //   28: invokeinterface 94 2 0
    //   33: pop
    //   34: aconst_null
    //   35: astore 10
    //   37: aload_1
    //   38: invokevirtual 98	org/apache/sanselan/common/byteSources/ByteSource:getInputStream	()Ljava/io/InputStream;
    //   41: astore 10
    //   43: iload_2
    //   44: ifle +15 -> 59
    //   47: iload_2
    //   48: i2l
    //   49: lstore 46
    //   51: aload 10
    //   53: lload 46
    //   55: invokevirtual 104	java/io/InputStream:skip	(J)J
    //   58: pop2
    //   59: new 70	java/util/ArrayList
    //   62: dup
    //   63: invokespecial 71	java/util/ArrayList:<init>	()V
    //   66: astore 13
    //   68: iload_2
    //   69: i2l
    //   70: lstore 14
    //   72: aload_1
    //   73: invokevirtual 42	org/apache/sanselan/common/byteSources/ByteSource:getLength	()J
    //   76: lstore 16
    //   78: lload 14
    //   80: lload 16
    //   82: lcmp
    //   83: iflt +25 -> 108
    //   86: aload 10
    //   88: ifnull +8 -> 96
    //   91: aload 10
    //   93: invokevirtual 107	java/io/InputStream:close	()V
    //   96: iconst_1
    //   97: ireturn
    //   98: astore 45
    //   100: aload 45
    //   102: invokestatic 113	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   105: goto -9 -> 96
    //   108: aload_0
    //   109: ldc 115
    //   111: aload 10
    //   113: ldc 117
    //   115: invokevirtual 121	org/apache/sanselan/formats/tiff/TiffReader:read2Bytes	(Ljava/lang/String;Ljava/io/InputStream;Ljava/lang/String;)I
    //   118: istore 20
    //   120: iconst_0
    //   121: istore 21
    //   123: iload 21
    //   125: iload 20
    //   127: if_icmplt +137 -> 264
    //   130: aload_0
    //   131: ldc 123
    //   133: aload 10
    //   135: ldc 117
    //   137: invokevirtual 126	org/apache/sanselan/formats/tiff/TiffReader:read4Bytes	(Ljava/lang/String;Ljava/io/InputStream;Ljava/lang/String;)I
    //   140: istore 31
    //   142: new 23	org/apache/sanselan/formats/tiff/TiffDirectory
    //   145: dup
    //   146: iload_3
    //   147: aload 13
    //   149: iload_2
    //   150: iload 31
    //   152: invokespecial 129	org/apache/sanselan/formats/tiff/TiffDirectory:<init>	(ILjava/util/ArrayList;II)V
    //   155: astore 32
    //   157: aload 5
    //   159: invokeinterface 133 1 0
    //   164: ifeq +23 -> 187
    //   167: aload 32
    //   169: invokevirtual 136	org/apache/sanselan/formats/tiff/TiffDirectory:hasJpegImageData	()Z
    //   172: ifeq +15 -> 187
    //   175: aload 32
    //   177: aload_0
    //   178: aload_1
    //   179: aload 32
    //   181: invokespecial 138	org/apache/sanselan/formats/tiff/TiffReader:getJpegRawImageData	(Lorg/apache/sanselan/common/byteSources/ByteSource;Lorg/apache/sanselan/formats/tiff/TiffDirectory;)Lorg/apache/sanselan/formats/tiff/JpegImageData;
    //   184: invokevirtual 142	org/apache/sanselan/formats/tiff/TiffDirectory:setJpegImageData	(Lorg/apache/sanselan/formats/tiff/JpegImageData;)V
    //   187: aload 5
    //   189: aload 32
    //   191: invokeinterface 146 2 0
    //   196: istore 33
    //   198: iload 33
    //   200: ifne +224 -> 424
    //   203: aload 10
    //   205: ifnull +8 -> 213
    //   208: aload 10
    //   210: invokevirtual 107	java/io/InputStream:close	()V
    //   213: iconst_1
    //   214: ireturn
    //   215: astore 18
    //   217: aload_0
    //   218: getfield 15	org/apache/sanselan/formats/tiff/TiffReader:strict	Z
    //   221: ifeq +21 -> 242
    //   224: aload 18
    //   226: athrow
    //   227: astore 11
    //   229: aload 10
    //   231: ifnull +8 -> 239
    //   234: aload 10
    //   236: invokevirtual 107	java/io/InputStream:close	()V
    //   239: aload 11
    //   241: athrow
    //   242: aload 10
    //   244: ifnull +8 -> 252
    //   247: aload 10
    //   249: invokevirtual 107	java/io/InputStream:close	()V
    //   252: iconst_1
    //   253: ireturn
    //   254: astore 19
    //   256: aload 19
    //   258: invokestatic 113	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   261: goto -9 -> 252
    //   264: aload_0
    //   265: ldc 148
    //   267: aload 10
    //   269: ldc 117
    //   271: invokevirtual 121	org/apache/sanselan/formats/tiff/TiffReader:read2Bytes	(Ljava/lang/String;Ljava/io/InputStream;Ljava/lang/String;)I
    //   274: istore 22
    //   276: aload_0
    //   277: ldc 150
    //   279: aload 10
    //   281: ldc 117
    //   283: invokevirtual 121	org/apache/sanselan/formats/tiff/TiffReader:read2Bytes	(Ljava/lang/String;Ljava/io/InputStream;Ljava/lang/String;)I
    //   286: istore 23
    //   288: aload_0
    //   289: ldc 152
    //   291: aload 10
    //   293: ldc 117
    //   295: invokevirtual 126	org/apache/sanselan/formats/tiff/TiffReader:read4Bytes	(Ljava/lang/String;Ljava/io/InputStream;Ljava/lang/String;)I
    //   298: istore 24
    //   300: aload_0
    //   301: ldc 154
    //   303: iconst_4
    //   304: aload 10
    //   306: ldc 117
    //   308: invokevirtual 158	org/apache/sanselan/formats/tiff/TiffReader:readByteArray	(Ljava/lang/String;ILjava/io/InputStream;Ljava/lang/String;)[B
    //   311: astore 25
    //   313: aload_0
    //   314: ldc 154
    //   316: aload 25
    //   318: invokevirtual 162	org/apache/sanselan/formats/tiff/TiffReader:convertByteArrayToInt	(Ljava/lang/String;[B)I
    //   321: istore 26
    //   323: iload 22
    //   325: ifne +6 -> 331
    //   328: goto +374 -> 702
    //   331: new 164	org/apache/sanselan/formats/tiff/TiffField
    //   334: dup
    //   335: iload 22
    //   337: iload_3
    //   338: iload 23
    //   340: iload 24
    //   342: iload 26
    //   344: aload 25
    //   346: aload_0
    //   347: invokevirtual 168	org/apache/sanselan/formats/tiff/TiffReader:getByteOrder	()I
    //   350: invokespecial 171	org/apache/sanselan/formats/tiff/TiffField:<init>	(IIIII[BI)V
    //   353: astore 27
    //   355: aload 27
    //   357: iload 21
    //   359: invokevirtual 174	org/apache/sanselan/formats/tiff/TiffField:setSortHint	(I)V
    //   362: aload 27
    //   364: aload_1
    //   365: invokevirtual 178	org/apache/sanselan/formats/tiff/TiffField:fillInValue	(Lorg/apache/sanselan/common/byteSources/ByteSource;)V
    //   368: aload 13
    //   370: aload 27
    //   372: invokevirtual 179	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   375: pop
    //   376: aload 5
    //   378: aload 27
    //   380: invokeinterface 183 2 0
    //   385: istore 29
    //   387: iload 29
    //   389: ifne +313 -> 702
    //   392: aload 10
    //   394: ifnull +8 -> 402
    //   397: aload 10
    //   399: invokevirtual 107	java/io/InputStream:close	()V
    //   402: iconst_1
    //   403: ireturn
    //   404: astore 30
    //   406: aload 30
    //   408: invokestatic 113	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   411: goto -9 -> 402
    //   414: astore 44
    //   416: aload 44
    //   418: invokestatic 113	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   421: goto -208 -> 213
    //   424: aload 5
    //   426: invokeinterface 186 1 0
    //   431: ifeq +37 -> 468
    //   434: new 70	java/util/ArrayList
    //   437: dup
    //   438: invokespecial 71	java/util/ArrayList:<init>	()V
    //   441: astore 34
    //   443: iconst_0
    //   444: istore 35
    //   446: aload 13
    //   448: invokevirtual 189	java/util/ArrayList:size	()I
    //   451: istore 36
    //   453: iload 35
    //   455: iload 36
    //   457: if_icmplt +56 -> 513
    //   460: aload 13
    //   462: aload 34
    //   464: invokevirtual 193	java/util/ArrayList:removeAll	(Ljava/util/Collection;)Z
    //   467: pop
    //   468: iload 6
    //   470: ifne +31 -> 501
    //   473: aload 32
    //   475: getfield 195	org/apache/sanselan/formats/tiff/TiffDirectory:nextDirectoryOffset	I
    //   478: ifle +23 -> 501
    //   481: aload_0
    //   482: aload_1
    //   483: aload 32
    //   485: getfield 195	org/apache/sanselan/formats/tiff/TiffDirectory:nextDirectoryOffset	I
    //   488: iload_3
    //   489: iconst_1
    //   490: iadd
    //   491: aload 4
    //   493: aload 5
    //   495: aload 7
    //   497: invokespecial 75	org/apache/sanselan/formats/tiff/TiffReader:readDirectory	(Lorg/apache/sanselan/common/byteSources/ByteSource;IILorg/apache/sanselan/FormatCompliance;Lorg/apache/sanselan/formats/tiff/TiffReader$Listener;Ljava/util/List;)Z
    //   500: pop
    //   501: aload 10
    //   503: ifnull +8 -> 511
    //   506: aload 10
    //   508: invokevirtual 107	java/io/InputStream:close	()V
    //   511: iconst_1
    //   512: ireturn
    //   513: aload 13
    //   515: iload 35
    //   517: invokevirtual 199	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   520: checkcast 164	org/apache/sanselan/formats/tiff/TiffField
    //   523: astore 40
    //   525: aload 40
    //   527: getfield 202	org/apache/sanselan/formats/tiff/TiffField:tag	I
    //   530: getstatic 206	org/apache/sanselan/formats/tiff/constants/TiffConstants:EXIF_TAG_EXIF_OFFSET	Lorg/apache/sanselan/formats/tiff/constants/TagInfo;
    //   533: getfield 209	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   536: if_icmpeq +31 -> 567
    //   539: aload 40
    //   541: getfield 202	org/apache/sanselan/formats/tiff/TiffField:tag	I
    //   544: getstatic 212	org/apache/sanselan/formats/tiff/constants/TiffConstants:EXIF_TAG_GPSINFO	Lorg/apache/sanselan/formats/tiff/constants/TagInfo;
    //   547: getfield 209	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   550: if_icmpeq +17 -> 567
    //   553: aload 40
    //   555: getfield 202	org/apache/sanselan/formats/tiff/TiffField:tag	I
    //   558: getstatic 215	org/apache/sanselan/formats/tiff/constants/TiffConstants:EXIF_TAG_INTEROP_OFFSET	Lorg/apache/sanselan/formats/tiff/constants/TagInfo;
    //   561: getfield 209	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   564: if_icmpne +144 -> 708
    //   567: aload 40
    //   569: invokevirtual 219	org/apache/sanselan/formats/tiff/TiffField:getValue	()Ljava/lang/Object;
    //   572: checkcast 221	java/lang/Number
    //   575: invokevirtual 224	java/lang/Number:intValue	()I
    //   578: istore 41
    //   580: aload 40
    //   582: getfield 202	org/apache/sanselan/formats/tiff/TiffField:tag	I
    //   585: getstatic 206	org/apache/sanselan/formats/tiff/constants/TiffConstants:EXIF_TAG_EXIF_OFFSET	Lorg/apache/sanselan/formats/tiff/constants/TagInfo;
    //   588: getfield 209	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   591: if_icmpne +39 -> 630
    //   594: bipush 254
    //   596: istore 42
    //   598: aload_0
    //   599: aload_1
    //   600: iload 41
    //   602: iload 42
    //   604: aload 4
    //   606: aload 5
    //   608: iconst_1
    //   609: aload 7
    //   611: invokespecial 78	org/apache/sanselan/formats/tiff/TiffReader:readDirectory	(Lorg/apache/sanselan/common/byteSources/ByteSource;IILorg/apache/sanselan/FormatCompliance;Lorg/apache/sanselan/formats/tiff/TiffReader$Listener;ZLjava/util/List;)Z
    //   614: ifne +94 -> 708
    //   617: aload 34
    //   619: aload 40
    //   621: invokeinterface 94 2 0
    //   626: pop
    //   627: goto +81 -> 708
    //   630: aload 40
    //   632: getfield 202	org/apache/sanselan/formats/tiff/TiffField:tag	I
    //   635: getstatic 212	org/apache/sanselan/formats/tiff/constants/TiffConstants:EXIF_TAG_GPSINFO	Lorg/apache/sanselan/formats/tiff/constants/TagInfo;
    //   638: getfield 209	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   641: if_icmpne +10 -> 651
    //   644: bipush 253
    //   646: istore 42
    //   648: goto -50 -> 598
    //   651: aload 40
    //   653: getfield 202	org/apache/sanselan/formats/tiff/TiffField:tag	I
    //   656: getstatic 215	org/apache/sanselan/formats/tiff/constants/TiffConstants:EXIF_TAG_INTEROP_OFFSET	Lorg/apache/sanselan/formats/tiff/constants/TagInfo;
    //   659: getfield 209	org/apache/sanselan/formats/tiff/constants/TagInfo:tag	I
    //   662: if_icmpne +10 -> 672
    //   665: bipush 252
    //   667: istore 42
    //   669: goto -71 -> 598
    //   672: new 19	org/apache/sanselan/ImageReadException
    //   675: dup
    //   676: ldc 226
    //   678: invokespecial 229	org/apache/sanselan/ImageReadException:<init>	(Ljava/lang/String;)V
    //   681: athrow
    //   682: astore 38
    //   684: aload 38
    //   686: invokestatic 113	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   689: goto -178 -> 511
    //   692: astore 12
    //   694: aload 12
    //   696: invokestatic 113	org/apache/sanselan/util/Debug:debug	(Ljava/lang/Throwable;)V
    //   699: goto -460 -> 239
    //   702: iinc 21 1
    //   705: goto -582 -> 123
    //   708: iinc 35 1
    //   711: goto -265 -> 446
    //
    // Exception table:
    //   from	to	target	type
    //   91	96	98	java/lang/Exception
    //   108	120	215	java/io/IOException
    //   37	43	227	finally
    //   51	59	227	finally
    //   59	68	227	finally
    //   72	78	227	finally
    //   108	120	227	finally
    //   130	187	227	finally
    //   187	198	227	finally
    //   217	227	227	finally
    //   264	323	227	finally
    //   331	387	227	finally
    //   424	443	227	finally
    //   446	453	227	finally
    //   460	468	227	finally
    //   473	501	227	finally
    //   513	567	227	finally
    //   567	594	227	finally
    //   598	627	227	finally
    //   630	644	227	finally
    //   651	665	227	finally
    //   672	682	227	finally
    //   247	252	254	java/lang/Exception
    //   397	402	404	java/lang/Exception
    //   208	213	414	java/lang/Exception
    //   506	511	682	java/lang/Exception
    //   234	239	692	java/lang/Exception
  }

  private TiffHeader readTiffHeader(InputStream paramInputStream, FormatCompliance paramFormatCompliance)
    throws ImageReadException, IOException
  {
    int i = readByte("BYTE_ORDER_1", paramInputStream, "Not a Valid TIFF File");
    setByteOrder(i, readByte("BYTE_ORDER_2", paramInputStream, "Not a Valid TIFF File"));
    int j = read2Bytes("tiffVersion", paramInputStream, "Not a Valid TIFF File");
    if (j != 42)
      throw new ImageReadException("Unknown Tiff Version: " + j);
    int k = read4Bytes("offsetToFirstIFD", paramInputStream, "Not a Valid TIFF File");
    skipBytes(paramInputStream, k - 8, "Not a Valid TIFF File: couldn't find IFDs");
    if (this.debug)
      System.out.println("");
    return new TiffHeader(i, j, k);
  }

  private TiffHeader readTiffHeader(ByteSource paramByteSource, FormatCompliance paramFormatCompliance)
    throws ImageReadException, IOException
  {
    InputStream localInputStream = null;
    try
    {
      localInputStream = paramByteSource.getInputStream();
      TiffHeader localTiffHeader = readTiffHeader(localInputStream, paramFormatCompliance);
      if (localInputStream != null);
      try
      {
        localInputStream.close();
        return localTiffHeader;
      }
      catch (Exception localException2)
      {
        Debug.debug(localException2);
        return localTiffHeader;
      }
    }
    finally
    {
      if (localInputStream == null);
    }
    try
    {
      localInputStream.close();
      throw localObject;
    }
    catch (Exception localException1)
    {
      while (true)
        Debug.debug(localException1);
    }
  }

  public void read(ByteSource paramByteSource, Map paramMap, FormatCompliance paramFormatCompliance, Listener paramListener)
    throws ImageReadException, IOException
  {
    readDirectories(paramByteSource, paramFormatCompliance, paramListener);
  }

  public TiffContents readContents(ByteSource paramByteSource, Map paramMap, FormatCompliance paramFormatCompliance)
    throws ImageReadException, IOException
  {
    Collector localCollector = new Collector(paramMap);
    read(paramByteSource, paramMap, paramFormatCompliance, localCollector);
    return localCollector.getContents();
  }

  public TiffContents readDirectories(ByteSource paramByteSource, boolean paramBoolean, FormatCompliance paramFormatCompliance)
    throws ImageReadException, IOException
  {
    FirstDirectoryCollector localFirstDirectoryCollector = new FirstDirectoryCollector(paramBoolean);
    readDirectories(paramByteSource, paramFormatCompliance, localFirstDirectoryCollector);
    TiffContents localTiffContents = localFirstDirectoryCollector.getContents();
    if (localTiffContents.directories.size() < 1)
      throw new ImageReadException("Image did not contain any directories.");
    return localTiffContents;
  }

  public TiffContents readFirstDirectory(ByteSource paramByteSource, Map paramMap, boolean paramBoolean, FormatCompliance paramFormatCompliance)
    throws ImageReadException, IOException
  {
    FirstDirectoryCollector localFirstDirectoryCollector = new FirstDirectoryCollector(paramBoolean);
    read(paramByteSource, paramMap, paramFormatCompliance, localFirstDirectoryCollector);
    TiffContents localTiffContents = localFirstDirectoryCollector.getContents();
    if (localTiffContents.directories.size() < 1)
      throw new ImageReadException("Image did not contain any directories.");
    return localTiffContents;
  }

  private static class Collector
    implements TiffReader.Listener
  {
    private ArrayList directories = new ArrayList();
    private ArrayList fields = new ArrayList();
    private final boolean readThumbnails;
    private TiffHeader tiffHeader = null;

    public Collector()
    {
      this(null);
    }

    public Collector(Map paramMap)
    {
      boolean bool = true;
      if ((paramMap != null) && (paramMap.containsKey("READ_THUMBNAILS")))
        bool = Boolean.TRUE.equals(paramMap.get("READ_THUMBNAILS"));
      this.readThumbnails = bool;
    }

    public boolean addDirectory(TiffDirectory paramTiffDirectory)
    {
      this.directories.add(paramTiffDirectory);
      return true;
    }

    public boolean addField(TiffField paramTiffField)
    {
      this.fields.add(paramTiffField);
      return true;
    }

    public TiffContents getContents()
    {
      return new TiffContents(this.tiffHeader, this.directories);
    }

    public boolean readImageData()
    {
      return this.readThumbnails;
    }

    public boolean readOffsetDirectories()
    {
      return true;
    }

    public boolean setTiffHeader(TiffHeader paramTiffHeader)
    {
      this.tiffHeader = paramTiffHeader;
      return true;
    }
  }

  private static class DirectoryCollector extends TiffReader.Collector
  {
    private final boolean readImageData;

    public DirectoryCollector(boolean paramBoolean)
    {
      this.readImageData = paramBoolean;
    }

    public boolean addDirectory(TiffDirectory paramTiffDirectory)
    {
      super.addDirectory(paramTiffDirectory);
      return false;
    }

    public boolean readImageData()
    {
      return this.readImageData;
    }
  }

  private static class FirstDirectoryCollector extends TiffReader.Collector
  {
    private final boolean readImageData;

    public FirstDirectoryCollector(boolean paramBoolean)
    {
      this.readImageData = paramBoolean;
    }

    public boolean addDirectory(TiffDirectory paramTiffDirectory)
    {
      super.addDirectory(paramTiffDirectory);
      return false;
    }

    public boolean readImageData()
    {
      return this.readImageData;
    }
  }

  public static abstract interface Listener
  {
    public abstract boolean addDirectory(TiffDirectory paramTiffDirectory);

    public abstract boolean addField(TiffField paramTiffField);

    public abstract boolean readImageData();

    public abstract boolean readOffsetDirectories();

    public abstract boolean setTiffHeader(TiffHeader paramTiffHeader);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.tiff.TiffReader
 * JD-Core Version:    0.6.0
 */