package com.kaixin001.zxing;

import android.content.Intent;
import android.net.Uri;
import com.google.zxing.BarcodeFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

public final class DecodeFormatManager
{
  private static final Pattern COMMA_PATTERN = Pattern.compile(",");
  public static final Vector<BarcodeFormat> DATA_MATRIX_FORMATS;
  public static final Vector<BarcodeFormat> ONE_D_FORMATS;
  public static final Vector<BarcodeFormat> PRODUCT_FORMATS = new Vector(5);
  public static final Vector<BarcodeFormat> QR_CODE_FORMATS;

  static
  {
    PRODUCT_FORMATS.add(BarcodeFormat.UPC_A);
    PRODUCT_FORMATS.add(BarcodeFormat.UPC_E);
    PRODUCT_FORMATS.add(BarcodeFormat.EAN_13);
    PRODUCT_FORMATS.add(BarcodeFormat.EAN_8);
    PRODUCT_FORMATS.add(BarcodeFormat.RSS_14);
    ONE_D_FORMATS = new Vector(4 + PRODUCT_FORMATS.size());
    ONE_D_FORMATS.addAll(PRODUCT_FORMATS);
    ONE_D_FORMATS.add(BarcodeFormat.CODE_39);
    ONE_D_FORMATS.add(BarcodeFormat.CODE_93);
    ONE_D_FORMATS.add(BarcodeFormat.CODE_128);
    ONE_D_FORMATS.add(BarcodeFormat.ITF);
    QR_CODE_FORMATS = new Vector(1);
    QR_CODE_FORMATS.add(BarcodeFormat.QR_CODE);
    DATA_MATRIX_FORMATS = new Vector(1);
    DATA_MATRIX_FORMATS.add(BarcodeFormat.DATA_MATRIX);
  }

  public static Vector<BarcodeFormat> parseDecodeFormats(Intent paramIntent)
  {
    String str = paramIntent.getStringExtra("SCAN_FORMATS");
    List localList = null;
    if (str != null)
      localList = Arrays.asList(COMMA_PATTERN.split(str));
    return parseDecodeFormats(localList, paramIntent.getStringExtra("SCAN_MODE"));
  }

  public static Vector<BarcodeFormat> parseDecodeFormats(Uri paramUri)
  {
    List localList = paramUri.getQueryParameters("SCAN_FORMATS");
    if ((localList != null) && (localList.size() == 1) && (localList.get(0) != null))
      localList = Arrays.asList(COMMA_PATTERN.split((CharSequence)localList.get(0)));
    return parseDecodeFormats(localList, paramUri.getQueryParameter("SCAN_MODE"));
  }

  private static Vector<BarcodeFormat> parseDecodeFormats(Iterable<String> paramIterable, String paramString)
  {
    if (paramIterable != null)
    {
      Vector localVector = new Vector();
      try
      {
        Iterator localIterator = paramIterable.iterator();
        while (true)
        {
          if (!localIterator.hasNext())
            return localVector;
          localVector.add(BarcodeFormat.valueOf((String)localIterator.next()));
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
      }
    }
    if (paramString != null)
    {
      if ("PRODUCT_MODE".equals(paramString))
        return PRODUCT_FORMATS;
      if ("QR_CODE_MODE".equals(paramString))
        return QR_CODE_FORMATS;
      if ("DATA_MATRIX_MODE".equals(paramString))
        return DATA_MATRIX_FORMATS;
      if ("ONE_D_MODE".equals(paramString))
        return ONE_D_FORMATS;
    }
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.zxing.DecodeFormatManager
 * JD-Core Version:    0.6.0
 */