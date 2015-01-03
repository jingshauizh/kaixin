package com.tencent.jsutil;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class JsBridge$MyWebViewClient extends WebViewClient
{
  JsBridge$MyWebViewClient(JsBridge paramJsBridge)
  {
  }

  public void onPageFinished(WebView paramWebView, String paramString)
  {
    super.onPageFinished(paramWebView, paramString);
    JsBridge.access$102(this.this$0, 100);
    (System.nanoTime() / 1000000L);
  }

  public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
  {
    super.onPageStarted(paramWebView, paramString, paramBitmap);
    JsBridge.access$102(this.this$0, 0);
    (System.nanoTime() / 1000000L);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.JsBridge.MyWebViewClient
 * JD-Core Version:    0.6.0
 */