package com.tencent.jsutil;

import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class BundleMap
{
  Bundle mBundle;

  public BundleMap(Bundle paramBundle)
  {
    this.mBundle = paramBundle;
  }

  @JavascriptInterface
  public boolean containsKey(String paramString)
  {
    return this.mBundle.containsKey(paramString);
  }

  @JavascriptInterface
  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    return this.mBundle.getBoolean(paramString, paramBoolean);
  }

  @JavascriptInterface
  public float getFloat(String paramString, float paramFloat)
  {
    return this.mBundle.getFloat(paramString, paramFloat);
  }

  @JavascriptInterface
  public int getInt(String paramString, int paramInt)
  {
    Log.i("BundleMap", "getInt key =" + paramString);
    return this.mBundle.getInt(paramString, paramInt);
  }

  @JavascriptInterface
  public String getString(String paramString)
  {
    Log.i("BundleMap", "getString key =" + paramString + " value = " + this.mBundle.getString(paramString));
    return this.mBundle.getString(paramString);
  }

  @JavascriptInterface
  public String getStringArray(String paramString)
  {
    Log.i("BundleMap", "getStringArray key = " + paramString);
    StringBuffer localStringBuffer = new StringBuffer("[");
    String[] arrayOfString = this.mBundle.getStringArray(paramString);
    if (arrayOfString == null)
      return null;
    int i = 0;
    if (i < arrayOfString.length)
    {
      Log.i("BundleMap", arrayOfString[i]);
      if (i != -1 + arrayOfString.length)
        localStringBuffer.append(arrayOfString[i]).append(",");
      while (true)
      {
        i++;
        break;
        localStringBuffer.append(arrayOfString[i]);
      }
    }
    localStringBuffer.append("]");
    Log.d("BundleMap", "buffer = " + localStringBuffer.toString());
    return localStringBuffer.toString();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.BundleMap
 * JD-Core Version:    0.6.0
 */