package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.oned.UPCEReader;

final class ProductResultParser extends ResultParser
{
  public static ProductParsedResult parse(Result paramResult)
  {
    BarcodeFormat localBarcodeFormat = paramResult.getBarcodeFormat();
    if ((!BarcodeFormat.UPC_A.equals(localBarcodeFormat)) && (!BarcodeFormat.UPC_E.equals(localBarcodeFormat)) && (!BarcodeFormat.EAN_8.equals(localBarcodeFormat)) && (!BarcodeFormat.EAN_13.equals(localBarcodeFormat)));
    String str1;
    do
    {
      return null;
      str1 = paramResult.getText();
    }
    while (str1 == null);
    int i = str1.length();
    int j = 0;
    label64: if (j >= i)
      if (!BarcodeFormat.UPC_E.equals(localBarcodeFormat))
        break label125;
    label125: for (String str2 = UPCEReader.convertUPCEtoUPCA(str1); ; str2 = str1)
    {
      return new ProductParsedResult(str1, str2);
      int k = str1.charAt(j);
      if ((k < 48) || (k > 57))
        break;
      j++;
      break label64;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.ProductResultParser
 * JD-Core Version:    0.6.0
 */