package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;
import java.util.Hashtable;
import java.util.Vector;

public final class MultiFormatUPCEANReader extends OneDReader
{
  private final Vector readers;

  public MultiFormatUPCEANReader(Hashtable paramHashtable)
  {
    Vector localVector;
    if (paramHashtable == null)
    {
      localVector = null;
      this.readers = new Vector();
      if (localVector != null)
      {
        if (!localVector.contains(BarcodeFormat.EAN_13))
          break label164;
        this.readers.addElement(new EAN13Reader());
      }
    }
    while (true)
    {
      if (localVector.contains(BarcodeFormat.EAN_8))
        this.readers.addElement(new EAN8Reader());
      if (localVector.contains(BarcodeFormat.UPC_E))
        this.readers.addElement(new UPCEReader());
      if (this.readers.isEmpty())
      {
        this.readers.addElement(new EAN13Reader());
        this.readers.addElement(new EAN8Reader());
        this.readers.addElement(new UPCEReader());
      }
      return;
      localVector = (Vector)paramHashtable.get(DecodeHintType.POSSIBLE_FORMATS);
      break;
      label164: if (!localVector.contains(BarcodeFormat.UPC_A))
        continue;
      this.readers.addElement(new UPCAReader());
    }
  }

  public Result decodeRow(int paramInt, BitArray paramBitArray, Hashtable paramHashtable)
    throws NotFoundException
  {
    int[] arrayOfInt = UPCEANReader.findStartGuardPattern(paramBitArray);
    int i = this.readers.size();
    int j = 0;
    if (j >= i)
      throw NotFoundException.getNotFoundInstance();
    UPCEANReader localUPCEANReader = (UPCEANReader)this.readers.elementAt(j);
    while (true)
    {
      try
      {
        Result localResult1 = localUPCEANReader.decodeRow(paramInt, paramBitArray, arrayOfInt, paramHashtable);
        Result localResult2 = localResult1;
        if ((!BarcodeFormat.EAN_13.equals(localResult2.getBarcodeFormat())) || (localResult2.getText().charAt(0) != '0'))
          break label164;
        k = 1;
        if (paramHashtable != null)
          break label170;
        localVector = null;
        if ((localVector == null) || (localVector.contains(BarcodeFormat.UPC_A)))
          break label185;
        m = 0;
        if ((k == 0) || (m == 0))
          continue;
        localResult2 = new Result(localResult2.getText().substring(1), null, localResult2.getResultPoints(), BarcodeFormat.UPC_A);
        return localResult2;
      }
      catch (ReaderException localReaderException)
      {
        j++;
      }
      break;
      label164: int k = 0;
      continue;
      label170: Vector localVector = (Vector)paramHashtable.get(DecodeHintType.POSSIBLE_FORMATS);
      continue;
      label185: int m = 1;
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
 * Qualified Name:     com.google.zxing.oned.MultiFormatUPCEANReader
 * JD-Core Version:    0.6.0
 */