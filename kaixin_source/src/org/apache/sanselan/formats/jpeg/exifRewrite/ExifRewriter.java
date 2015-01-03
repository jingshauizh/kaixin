package org.apache.sanselan.formats.jpeg.exifRewrite;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.common.BinaryFileParser;
import org.apache.sanselan.common.byteSources.ByteSource;
import org.apache.sanselan.common.byteSources.ByteSourceArray;
import org.apache.sanselan.common.byteSources.ByteSourceFile;
import org.apache.sanselan.common.byteSources.ByteSourceInputStream;
import org.apache.sanselan.formats.jpeg.JpegConstants;
import org.apache.sanselan.formats.jpeg.JpegUtils;
import org.apache.sanselan.formats.jpeg.JpegUtils.Visitor;
import org.apache.sanselan.formats.tiff.write.TiffImageWriterBase;
import org.apache.sanselan.formats.tiff.write.TiffImageWriterLossless;
import org.apache.sanselan.formats.tiff.write.TiffImageWriterLossy;
import org.apache.sanselan.formats.tiff.write.TiffOutputSet;
import org.apache.sanselan.util.Debug;

public class ExifRewriter extends BinaryFileParser
  implements JpegConstants
{
  public ExifRewriter()
  {
    setByteOrder(77);
  }

  public ExifRewriter(int paramInt)
  {
    setByteOrder(paramInt);
  }

  private JFIFPieces analyzeJFIF(ByteSource paramByteSource)
    throws ImageReadException, IOException
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    1 local1 = new JpegUtils.Visitor(localArrayList1, localArrayList2)
    {
      public boolean beginSOS()
      {
        return true;
      }

      public void visitSOS(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
      {
        this.val$pieces.add(new ExifRewriter.JFIFPieceImageData(paramArrayOfByte1, paramArrayOfByte2));
      }

      public boolean visitSOS(int paramInt, byte[] paramArrayOfByte, InputStream paramInputStream)
      {
        this.val$pieces.add(new ExifRewriter.JFIFPieceImageData(paramArrayOfByte, paramInputStream));
        return true;
      }

      public boolean visitSegment(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
        throws ImageReadException, IOException
      {
        if (paramInt1 != 65505)
          this.val$pieces.add(new ExifRewriter.JFIFPieceSegment(paramInt1, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3));
        while (true)
        {
          return true;
          if (!ExifRewriter.byteArrayHasPrefix(paramArrayOfByte3, ExifRewriter.EXIF_IDENTIFIER_CODE))
          {
            this.val$pieces.add(new ExifRewriter.JFIFPieceSegment(paramInt1, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3));
            continue;
          }
          ExifRewriter.JFIFPieceSegmentExif localJFIFPieceSegmentExif = new ExifRewriter.JFIFPieceSegmentExif(paramInt1, paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3);
          this.val$pieces.add(localJFIFPieceSegmentExif);
          this.val$exifPieces.add(localJFIFPieceSegmentExif);
        }
      }
    };
    new JpegUtils().traverseJFIF(paramByteSource, local1);
    return new JFIFPieces(localArrayList1, localArrayList2);
  }

  private byte[] writeExifSegment(TiffImageWriterBase paramTiffImageWriterBase, TiffOutputSet paramTiffOutputSet, boolean paramBoolean)
    throws IOException, ImageWriteException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    if (paramBoolean)
    {
      localByteArrayOutputStream.write(EXIF_IDENTIFIER_CODE);
      localByteArrayOutputStream.write(0);
      localByteArrayOutputStream.write(0);
    }
    paramTiffImageWriterBase.write(localByteArrayOutputStream, paramTiffOutputSet);
    return localByteArrayOutputStream.toByteArray();
  }

  private void writeSegmentsReplacingExif(OutputStream paramOutputStream, List paramList, byte[] paramArrayOfByte)
    throws ImageWriteException, IOException
  {
    int i = getByteOrder();
    int j;
    int k;
    byte[] arrayOfByte3;
    try
    {
      paramOutputStream.write(SOI);
      j = 0;
      k = 0;
      if (k >= paramList.size())
      {
        if ((j != 0) || (paramArrayOfByte == null))
          break label330;
        arrayOfByte3 = convertShortToByteArray(65505, i);
        if (paramArrayOfByte.length <= 65535)
          break label113;
        throw new ExifOverflowException("APP1 Segment is too long: " + paramArrayOfByte.length);
      }
    }
    finally
    {
    }
    while (true)
    {
      try
      {
        paramOutputStream.close();
        throw localObject;
        if (((JFIFPiece)paramList.get(k) instanceof JFIFPieceSegmentExif))
        {
          j = 1;
          break label324;
          label113: byte[] arrayOfByte4 = convertShortToByteArray(2 + paramArrayOfByte.length, i);
          if (((JFIFPieceSegment)paramList.get(0)).marker != 65504)
            continue;
          paramList.add(0, new JFIFPieceSegmentExif(65505, arrayOfByte3, arrayOfByte4, paramArrayOfByte));
          break label330;
          int i1 = paramList.size();
          if (n < i1)
            continue;
        }
      }
      catch (Exception localException1)
      {
        try
        {
          paramOutputStream.close();
          return;
          JFIFPiece localJFIFPiece = (JFIFPiece)paramList.get(n);
          if (!(localJFIFPiece instanceof JFIFPieceSegmentExif))
            continue;
          if (m != 0)
            break label339;
          m = 1;
          if (paramArrayOfByte == null)
            break label339;
          byte[] arrayOfByte1 = convertShortToByteArray(65505, i);
          if (paramArrayOfByte.length <= 65535)
            continue;
          throw new ExifOverflowException("APP1 Segment is too long: " + paramArrayOfByte.length);
          byte[] arrayOfByte2 = convertShortToByteArray(2 + paramArrayOfByte.length, i);
          paramOutputStream.write(arrayOfByte1);
          paramOutputStream.write(arrayOfByte2);
          paramOutputStream.write(paramArrayOfByte);
          break label339;
          localJFIFPiece.write(paramOutputStream);
          break label339;
          localException1 = localException1;
          Debug.debug(localException1);
        }
        catch (Exception localException2)
        {
          Debug.debug(localException2);
          return;
        }
      }
      label324: k++;
      break;
      label330: int m = 0;
      int n = 0;
      continue;
      label339: n++;
    }
  }

  public void removeExifMetadata(File paramFile, OutputStream paramOutputStream)
    throws ImageReadException, IOException, ImageWriteException
  {
    removeExifMetadata(new ByteSourceFile(paramFile), paramOutputStream);
  }

  public void removeExifMetadata(InputStream paramInputStream, OutputStream paramOutputStream)
    throws ImageReadException, IOException, ImageWriteException
  {
    removeExifMetadata(new ByteSourceInputStream(paramInputStream, null), paramOutputStream);
  }

  public void removeExifMetadata(ByteSource paramByteSource, OutputStream paramOutputStream)
    throws ImageReadException, IOException, ImageWriteException
  {
    writeSegmentsReplacingExif(paramOutputStream, analyzeJFIF(paramByteSource).pieces, null);
  }

  public void removeExifMetadata(byte[] paramArrayOfByte, OutputStream paramOutputStream)
    throws ImageReadException, IOException, ImageWriteException
  {
    removeExifMetadata(new ByteSourceArray(paramArrayOfByte), paramOutputStream);
  }

  public void updateExifMetadataLossless(File paramFile, OutputStream paramOutputStream, TiffOutputSet paramTiffOutputSet)
    throws ImageReadException, IOException, ImageWriteException
  {
    updateExifMetadataLossless(new ByteSourceFile(paramFile), paramOutputStream, paramTiffOutputSet);
  }

  public void updateExifMetadataLossless(InputStream paramInputStream, OutputStream paramOutputStream, TiffOutputSet paramTiffOutputSet)
    throws ImageReadException, IOException, ImageWriteException
  {
    updateExifMetadataLossless(new ByteSourceInputStream(paramInputStream, null), paramOutputStream, paramTiffOutputSet);
  }

  public void updateExifMetadataLossless(ByteSource paramByteSource, OutputStream paramOutputStream, TiffOutputSet paramTiffOutputSet)
    throws ImageReadException, IOException, ImageWriteException
  {
    JFIFPieces localJFIFPieces = analyzeJFIF(paramByteSource);
    List localList = localJFIFPieces.pieces;
    byte[] arrayOfByte;
    if (localJFIFPieces.exifPieces.size() > 0)
      arrayOfByte = getByteArrayTail("trimmed exif bytes", ((JFIFPieceSegment)localJFIFPieces.exifPieces.get(0)).segmentData, 6);
    for (Object localObject = new TiffImageWriterLossless(paramTiffOutputSet.byteOrder, arrayOfByte); ; localObject = new TiffImageWriterLossy(paramTiffOutputSet.byteOrder))
    {
      writeSegmentsReplacingExif(paramOutputStream, localList, writeExifSegment((TiffImageWriterBase)localObject, paramTiffOutputSet, true));
      return;
    }
  }

  public void updateExifMetadataLossless(byte[] paramArrayOfByte, OutputStream paramOutputStream, TiffOutputSet paramTiffOutputSet)
    throws ImageReadException, IOException, ImageWriteException
  {
    updateExifMetadataLossless(new ByteSourceArray(paramArrayOfByte), paramOutputStream, paramTiffOutputSet);
  }

  public void updateExifMetadataLossy(File paramFile, OutputStream paramOutputStream, TiffOutputSet paramTiffOutputSet)
    throws ImageReadException, IOException, ImageWriteException
  {
    updateExifMetadataLossy(new ByteSourceFile(paramFile), paramOutputStream, paramTiffOutputSet);
  }

  public void updateExifMetadataLossy(InputStream paramInputStream, OutputStream paramOutputStream, TiffOutputSet paramTiffOutputSet)
    throws ImageReadException, IOException, ImageWriteException
  {
    updateExifMetadataLossy(new ByteSourceInputStream(paramInputStream, null), paramOutputStream, paramTiffOutputSet);
  }

  public void updateExifMetadataLossy(ByteSource paramByteSource, OutputStream paramOutputStream, TiffOutputSet paramTiffOutputSet)
    throws ImageReadException, IOException, ImageWriteException
  {
    writeSegmentsReplacingExif(paramOutputStream, analyzeJFIF(paramByteSource).pieces, writeExifSegment(new TiffImageWriterLossy(paramTiffOutputSet.byteOrder), paramTiffOutputSet, true));
  }

  public void updateExifMetadataLossy(byte[] paramArrayOfByte, OutputStream paramOutputStream, TiffOutputSet paramTiffOutputSet)
    throws ImageReadException, IOException, ImageWriteException
  {
    updateExifMetadataLossy(new ByteSourceArray(paramArrayOfByte), paramOutputStream, paramTiffOutputSet);
  }

  public static class ExifOverflowException extends ImageWriteException
  {
    public ExifOverflowException(String paramString)
    {
      super();
    }
  }

  private static abstract class JFIFPiece
  {
    protected abstract void write(OutputStream paramOutputStream)
      throws IOException;
  }

  private static class JFIFPieceImageData extends ExifRewriter.JFIFPiece
  {
    public final byte[] imageData;
    public final InputStream isImageData;
    public final byte[] markerBytes;

    public JFIFPieceImageData(byte[] paramArrayOfByte, InputStream paramInputStream)
    {
      super();
      this.markerBytes = paramArrayOfByte;
      this.imageData = null;
      this.isImageData = paramInputStream;
    }

    public JFIFPieceImageData(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    {
      super();
      this.markerBytes = paramArrayOfByte1;
      this.imageData = paramArrayOfByte2;
      this.isImageData = null;
    }

    protected void write(OutputStream paramOutputStream)
      throws IOException
    {
      paramOutputStream.write(this.markerBytes);
      if (this.imageData != null)
      {
        paramOutputStream.write(this.imageData);
        return;
      }
      byte[] arrayOfByte = new byte[1024];
      while (true)
      {
        int i = this.isImageData.read(arrayOfByte);
        if (i <= 0)
          try
          {
            this.isImageData.close();
            return;
          }
          catch (Exception localException)
          {
            return;
          }
        paramOutputStream.write(arrayOfByte, 0, i);
      }
    }
  }

  private static class JFIFPieceSegment extends ExifRewriter.JFIFPiece
  {
    public final int marker;
    public final byte[] markerBytes;
    public final byte[] markerLengthBytes;
    public final byte[] segmentData;

    public JFIFPieceSegment(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
    {
      super();
      this.marker = paramInt;
      this.markerBytes = paramArrayOfByte1;
      this.markerLengthBytes = paramArrayOfByte2;
      this.segmentData = paramArrayOfByte3;
    }

    protected void write(OutputStream paramOutputStream)
      throws IOException
    {
      paramOutputStream.write(this.markerBytes);
      paramOutputStream.write(this.markerLengthBytes);
      paramOutputStream.write(this.segmentData);
    }
  }

  private static class JFIFPieceSegmentExif extends ExifRewriter.JFIFPieceSegment
  {
    public JFIFPieceSegmentExif(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
    {
      super(paramArrayOfByte1, paramArrayOfByte2, paramArrayOfByte3);
    }
  }

  private static class JFIFPieces
  {
    public final List exifPieces;
    public final List pieces;

    public JFIFPieces(List paramList1, List paramList2)
    {
      this.pieces = paramList1;
      this.exifPieces = paramList2;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.formats.jpeg.exifRewrite.ExifRewriter
 * JD-Core Version:    0.6.0
 */