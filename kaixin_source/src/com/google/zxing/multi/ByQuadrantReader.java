package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import java.util.Hashtable;

public final class ByQuadrantReader
  implements Reader
{
  private final Reader delegate;

  public ByQuadrantReader(Reader paramReader)
  {
    this.delegate = paramReader;
  }

  public Result decode(BinaryBitmap paramBinaryBitmap)
    throws NotFoundException, ChecksumException, FormatException
  {
    return decode(paramBinaryBitmap, null);
  }

  public Result decode(BinaryBitmap paramBinaryBitmap, Hashtable paramHashtable)
    throws NotFoundException, ChecksumException, FormatException
  {
    int i = paramBinaryBitmap.getWidth();
    int j = paramBinaryBitmap.getHeight();
    int k = i / 2;
    int m = j / 2;
    BinaryBitmap localBinaryBitmap1 = paramBinaryBitmap.crop(0, 0, k, m);
    BinaryBitmap localBinaryBitmap5;
    try
    {
      Result localResult4 = this.delegate.decode(localBinaryBitmap1, paramHashtable);
      return localResult4;
    }
    catch (NotFoundException localNotFoundException1)
    {
      BinaryBitmap localBinaryBitmap2 = paramBinaryBitmap.crop(k, 0, k, m);
      try
      {
        Result localResult3 = this.delegate.decode(localBinaryBitmap2, paramHashtable);
        return localResult3;
      }
      catch (NotFoundException localNotFoundException2)
      {
        BinaryBitmap localBinaryBitmap3 = paramBinaryBitmap.crop(0, m, k, m);
        try
        {
          Result localResult2 = this.delegate.decode(localBinaryBitmap3, paramHashtable);
          return localResult2;
        }
        catch (NotFoundException localNotFoundException3)
        {
          BinaryBitmap localBinaryBitmap4 = paramBinaryBitmap.crop(k, m, k, m);
          try
          {
            Result localResult1 = this.delegate.decode(localBinaryBitmap4, paramHashtable);
            return localResult1;
          }
          catch (NotFoundException localNotFoundException4)
          {
            localBinaryBitmap5 = paramBinaryBitmap.crop(k / 2, m / 2, k, m);
          }
        }
      }
    }
    return this.delegate.decode(localBinaryBitmap5, paramHashtable);
  }

  public void reset()
  {
    this.delegate.reset();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.multi.ByQuadrantReader
 * JD-Core Version:    0.6.0
 */