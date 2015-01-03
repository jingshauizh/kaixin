package com.tencent.jsutil;

import android.util.Log;
import android.webkit.WebChromeClient;

class JsBridge$MyWebViewClient$MyChromeClient extends WebChromeClient
{
  JsBridge$MyWebViewClient$MyChromeClient(JsBridge.MyWebViewClient paramMyWebViewClient)
  {
  }

  public void onConsoleMessage(String paramString1, int paramInt, String paramString2)
  {
    super.onConsoleMessage(paramString1, paramInt, paramString2);
    Log.e("onConsoleMessage", "message:" + paramString1 + "-----lineNum:" + paramInt + "sourceId:" + paramString2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.JsBridge.MyWebViewClient.MyChromeClient
 * JD-Core Version:    0.6.0
 */