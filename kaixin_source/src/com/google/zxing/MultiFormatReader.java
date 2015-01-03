package com.google.zxing;

import com.google.zxing.aztec.AztecReader;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.oned.MultiFormatOneDReader;
import com.google.zxing.pdf417.PDF417Reader;
import com.google.zxing.qrcode.QRCodeReader;
import java.util.Hashtable;
import java.util.Vector;

public final class MultiFormatReader
  implements Reader
{
  private Hashtable hints;
  private Vector readers;

  private Result decodeInternal(BinaryBitmap paramBinaryBitmap)
    throws NotFoundException
  {
    int i = this.readers.size();
    int j = 0;
    while (true)
    {
      if (j >= i)
        throw NotFoundException.getNotFoundInstance();
      Reader localReader = (Reader)this.readers.elementAt(j);
      try
      {
        Result localResult = localReader.decode(paramBinaryBitmap, this.hints);
        return localResult;
      }
      catch (ReaderException localReaderException)
      {
        j++;
      }
    }
  }

  public Result decode(BinaryBitmap paramBinaryBitmap)
    throws NotFoundException
  {
    setHints(null);
    return decodeInternal(paramBinaryBitmap);
  }

  public Result decode(BinaryBitmap paramBinaryBitmap, Hashtable paramHashtable)
    throws NotFoundException
  {
    setHints(paramHashtable);
    return decodeInternal(paramBinaryBitmap);
  }

  public Result decodeWithState(BinaryBitmap paramBinaryBitmap)
    throws NotFoundException
  {
    if (this.readers == null)
      setHints(null);
    return decodeInternal(paramBinaryBitmap);
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

  public void setHints(Hashtable paramHashtable)
  {
    this.hints = paramHashtable;
    int i;
    Vector localVector;
    label27: int j;
    if ((paramHashtable != null) && (paramHashtable.containsKey(DecodeHintType.TRY_HARDER)))
    {
      i = 1;
      if (paramHashtable != null)
        break label403;
      localVector = null;
      this.readers = new Vector();
      if (localVector != null)
      {
        if ((localVector.contains(BarcodeFormat.UPC_A)) || (localVector.contains(BarcodeFormat.UPC_E)) || (localVector.contains(BarcodeFormat.EAN_13)) || (localVector.contains(BarcodeFormat.EAN_8)) || (localVector.contains(BarcodeFormat.CODE_39)) || (localVector.contains(BarcodeFormat.CODE_93)) || (localVector.contains(BarcodeFormat.CODE_128)) || (localVector.contains(BarcodeFormat.ITF)) || (localVector.contains(BarcodeFormat.RSS_14)))
          break label417;
        boolean bool = localVector.contains(BarcodeFormat.RSS_EXPANDED);
        j = 0;
        if (bool)
          break label417;
      }
    }
    while (true)
    {
      if ((j != 0) && (i == 0))
        this.readers.addElement(new MultiFormatOneDReader(paramHashtable));
      if (localVector.contains(BarcodeFormat.QR_CODE))
        this.readers.addElement(new QRCodeReader());
      if (localVector.contains(BarcodeFormat.DATA_MATRIX))
        this.readers.addElement(new DataMatrixReader());
      if (localVector.contains(BarcodeFormat.AZTEC))
        this.readers.addElement(new AztecReader());
      if (localVector.contains(BarcodeFormat.PDF_417))
        this.readers.addElement(new PDF417Reader());
      if ((j != 0) && (i != 0))
        this.readers.addElement(new MultiFormatOneDReader(paramHashtable));
      if (this.readers.isEmpty())
      {
        if (i == 0)
          this.readers.addElement(new MultiFormatOneDReader(paramHashtable));
        this.readers.addElement(new QRCodeReader());
        this.readers.addElement(new DataMatrixReader());
        this.readers.addElement(new AztecReader());
        this.readers.addElement(new PDF417Reader());
        if (i != 0)
          this.readers.addElement(new MultiFormatOneDReader(paramHashtable));
      }
      return;
      i = 0;
      break;
      label403: localVector = (Vector)paramHashtable.get(DecodeHintType.POSSIBLE_FORMATS);
      break label27;
      label417: j = 1;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.MultiFormatReader
 * JD-Core Version:    0.6.0
 */