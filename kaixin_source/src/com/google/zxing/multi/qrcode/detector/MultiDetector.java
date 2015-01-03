package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.qrcode.detector.Detector;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.util.Hashtable;
import java.util.Vector;

public final class MultiDetector extends Detector
{
  private static final DetectorResult[] EMPTY_DETECTOR_RESULTS = new DetectorResult[0];

  public MultiDetector(BitMatrix paramBitMatrix)
  {
    super(paramBitMatrix);
  }

  public DetectorResult[] detectMulti(Hashtable paramHashtable)
    throws NotFoundException
  {
    FinderPatternInfo[] arrayOfFinderPatternInfo = new MultiFinderPatternFinder(getImage()).findMulti(paramHashtable);
    if ((arrayOfFinderPatternInfo == null) || (arrayOfFinderPatternInfo.length == 0))
      throw NotFoundException.getNotFoundInstance();
    Vector localVector = new Vector();
    int i = 0;
    while (true)
    {
      DetectorResult[] arrayOfDetectorResult;
      if (i >= arrayOfFinderPatternInfo.length)
      {
        if (!localVector.isEmpty())
          break label80;
        arrayOfDetectorResult = EMPTY_DETECTOR_RESULTS;
        return arrayOfDetectorResult;
      }
      try
      {
        localVector.addElement(processFinderPatternInfo(arrayOfFinderPatternInfo[i]));
        label74: i++;
        continue;
        label80: arrayOfDetectorResult = new DetectorResult[localVector.size()];
        for (int j = 0; j < localVector.size(); j++)
          arrayOfDetectorResult[j] = ((DetectorResult)localVector.elementAt(j));
      }
      catch (ReaderException localReaderException)
      {
        break label74;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.multi.qrcode.detector.MultiDetector
 * JD-Core Version:    0.6.0
 */