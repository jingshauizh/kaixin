package com.tencent.jsutil;

import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class IntentMap
{
  private Intent mIntent;

  public IntentMap(Intent paramIntent)
  {
    this.mIntent = paramIntent;
  }

  @JavascriptInterface
  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    return this.mIntent.getBooleanExtra(paramString, paramBoolean);
  }

  @JavascriptInterface
  public float getFloat(String paramString, float paramFloat)
  {
    return this.mIntent.getFloatExtra(paramString, paramFloat);
  }

  @JavascriptInterface
  public int getInt(String paramString, int paramInt)
  {
    Log.i("IntentMap", "getInt key =" + paramString);
    return this.mIntent.getIntExtra(paramString, paramInt);
  }

  @JavascriptInterface
  public String getString(String paramString)
  {
    Log.i("IntentMap", "getString key =" + paramString);
    return this.mIntent.getStringExtra(paramString);
  }

  @JavascriptInterface
  public String getString(String paramString, int paramInt)
  {
    Log.i("IntentMap", "getString key =" + paramString);
    return this.mIntent.getStringExtra(paramString);
  }

  public void setIntentMap(Intent paramIntent)
  {
    this.mIntent = paramIntent;
  }

  @JavascriptInterface
  public String test(String paramString)
  {
    Log.i("sss", this.mIntent.getStringExtra("hello"));
    Log.i("ssss", "getInt key =" + this.mIntent.getStringExtra(paramString));
    return "";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.IntentMap
 * JD-Core Version:    0.6.0
 */