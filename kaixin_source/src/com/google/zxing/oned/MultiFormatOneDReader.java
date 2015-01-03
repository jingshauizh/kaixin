package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.RSS14Reader;
import com.google.zxing.oned.rss.expanded.RSSExpandedReader;
import java.util.Hashtable;
import java.util.Vector;

public final class MultiFormatOneDReader extends OneDReader
{
  private final Vector readers;

  public MultiFormatOneDReader(Hashtable paramHashtable)
  {
    Vector localVector;
    if (paramHashtable == null)
    {
      localVector = null;
      if ((paramHashtable == null) || (paramHashtable.get(DecodeHintType.ASSUME_CODE_39_CHECK_DIGIT) == null))
        break label389;
    }
    label389: for (boolean bool = true; ; bool = false)
    {
      this.readers = new Vector();
      if (localVector != null)
      {
        if ((localVector.contains(BarcodeFormat.EAN_13)) || (localVector.contains(BarcodeFormat.UPC_A)) || (localVector.contains(BarcodeFormat.EAN_8)) || (localVector.contains(BarcodeFormat.UPC_E)))
          this.readers.addElement(new MultiFormatUPCEANReader(paramHashtable));
        if (localVector.contains(BarcodeFormat.CODE_39))
          this.readers.addElement(new Code39Reader(bool));
        if (localVector.contains(BarcodeFormat.CODE_93))
          this.readers.addElement(new Code93Reader());
        if (localVector.contains(BarcodeFormat.CODE_128))
          this.readers.addElement(new Code128Reader());
        if (localVector.contains(BarcodeFormat.ITF))
          this.readers.addElement(new ITFReader());
        if (localVector.contains(BarcodeFormat.CODABAR))
          this.readers.addElement(new CodaBarReader());
        if (localVector.contains(BarcodeFormat.RSS_14))
          this.readers.addElement(new RSS14Reader());
        if (localVector.contains(BarcodeFormat.RSS_EXPANDED))
          this.readers.addElement(new RSSExpandedReader());
      }
      if (this.readers.isEmpty())
      {
        this.readers.addElement(new MultiFormatUPCEANReader(paramHashtable));
        this.readers.addElement(new Code39Reader());
        this.readers.addElement(new Code93Reader());
        this.readers.addElement(new Code128Reader());
        this.readers.addElement(new ITFReader());
        this.readers.addElement(new RSS14Reader());
        this.readers.addElement(new RSSExpandedReader());
      }
      return;
      localVector = (Vector)paramHashtable.get(DecodeHintType.POSSIBLE_FORMATS);
      break;
    }
  }

  public Result decodeRow(int paramInt, BitArray paramBitArray, Hashtable paramHashtable)
    throws NotFoundException
  {
    int i = this.readers.size();
    int j = 0;
    while (true)
    {
      if (j >= i)
        throw NotFoundException.getNotFoundInstance();
      OneDReader localOneDReader = (OneDReader)this.readers.elementAt(j);
      try
      {
        Result localResult = localOneDReader.decodeRow(paramInt, paramBitArray, paramHashtable);
        return localResult;
      }
      catch (ReaderException localReaderException)
      {
        j++;
      }
    }
  }

  public void reset()
  {
    int i = this.readers.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      ((Reader)this.readers.elementAt(j)).reset();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.MultiFormatOneDReader
 * JD-Core Version:    0.6.0
 */