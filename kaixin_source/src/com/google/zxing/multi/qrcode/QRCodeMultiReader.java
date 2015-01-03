package com.google.zxing.multi.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.multi.qrcode.detector.MultiDetector;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.Decoder;
import java.util.Hashtable;
import java.util.Vector;

public final class QRCodeMultiReader extends QRCodeReader
  implements MultipleBarcodeReader
{
  private static final Result[] EMPTY_RESULT_ARRAY = new Result[0];

  public Result[] decodeMultiple(BinaryBitmap paramBinaryBitmap)
    throws NotFoundException
  {
    return decodeMultiple(paramBinaryBitmap, null);
  }

  public Result[] decodeMultiple(BinaryBitmap paramBinaryBitmap, Hashtable paramHashtable)
    throws NotFoundException
  {
    Vector localVector = new Vector();
    DetectorResult[] arrayOfDetectorResult = new MultiDetector(paramBinaryBitmap.getBlackMatrix()).detectMulti(paramHashtable);
    int i = 0;
    while (true)
    {
      Result[] arrayOfResult;
      if (i >= arrayOfDetectorResult.length)
      {
        if (!localVector.isEmpty())
          break label159;
        arrayOfResult = EMPTY_RESULT_ARRAY;
        return arrayOfResult;
      }
      try
      {
        DecoderResult localDecoderResult = getDecoder().decode(arrayOfDetectorResult[i].getBits());
        ResultPoint[] arrayOfResultPoint = arrayOfDetectorResult[i].getPoints();
        Result localResult = new Result(localDecoderResult.getText(), localDecoderResult.getRawBytes(), arrayOfResultPoint, BarcodeFormat.QR_CODE);
        if (localDecoderResult.getByteSegments() != null)
          localResult.putMetadata(ResultMetadataType.BYTE_SEGMENTS, localDecoderResult.getByteSegments());
        if (localDecoderResult.getECLevel() != null)
          localResult.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, localDecoderResult.getECLevel().toString());
        localVector.addElement(localResult);
        label153: i++;
        continue;
        label159: arrayOfResult = new Result[localVector.size()];
        for (int j = 0; j < localVector.size(); j++)
          arrayOfResult[j] = ((Result)localVector.elementAt(j));
      }
      catch (ReaderException localReaderException)
      {
        break label153;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.multi.qrcode.QRCodeMultiReader
 * JD-Core Version:    0.6.0
 */