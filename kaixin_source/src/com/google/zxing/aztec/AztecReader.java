package com.google.zxing.aztec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.aztec.decoder.Decoder;
import com.google.zxing.aztec.detector.Detector;
import com.google.zxing.common.DecoderResult;
import java.util.Hashtable;

public final class AztecReader
  implements Reader
{
  public Result decode(BinaryBitmap paramBinaryBitmap)
    throws NotFoundException, FormatException
  {
    return decode(paramBinaryBitmap, null);
  }

  public Result decode(BinaryBitmap paramBinaryBitmap, Hashtable paramHashtable)
    throws NotFoundException, FormatException
  {
    AztecDetectorResult localAztecDetectorResult = new Detector(paramBinaryBitmap.getBlackMatrix()).detect();
    ResultPoint[] arrayOfResultPoint = localAztecDetectorResult.getPoints();
    ResultPointCallback localResultPointCallback;
    if ((paramHashtable != null) && (localAztecDetectorResult.getPoints() != null))
    {
      localResultPointCallback = (ResultPointCallback)paramHashtable.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
      if (localResultPointCallback == null);
    }
    for (int i = 0; ; i++)
    {
      if (i >= localAztecDetectorResult.getPoints().length)
      {
        DecoderResult localDecoderResult = new Decoder().decode(localAztecDetectorResult);
        Result localResult = new Result(localDecoderResult.getText(), localDecoderResult.getRawBytes(), arrayOfResultPoint, BarcodeFormat.AZTEC);
        if (localDecoderResult.getByteSegments() != null)
          localResult.putMetadata(ResultMetadataType.BYTE_SEGMENTS, localDecoderResult.getByteSegments());
        if (localDecoderResult.getECLevel() != null)
          localResult.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, localDecoderResult.getECLevel().toString());
        return localResult;
      }
      localResultPointCallback.foundPossibleResultPoint(localAztecDetectorResult.getPoints()[i]);
    }
  }

  public void reset()
  {
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.aztec.AztecReader
 * JD-Core Version:    0.6.0
 */