package com.google.zxing.client.result;

abstract class AbstractDoCoMoResultParser extends ResultParser
{
  static String[] matchDoCoMoPrefixedField(String paramString1, String paramString2, boolean paramBoolean)
  {
    return matchPrefixedField(paramString1, paramString2, ';', paramBoolean);
  }

  static String matchSingleDoCoMoPrefixedField(String paramString1, String paramString2, boolean paramBoolean)
  {
    return matchSinglePrefixedField(paramString1, paramString2, ';', paramBoolean);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.AbstractDoCoMoResultParser
 * JD-Core Version:    0.6.0
 */