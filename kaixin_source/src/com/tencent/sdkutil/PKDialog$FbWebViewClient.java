package com.tencent.sdkutil;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.tencent.tauth.UiError;
import java.lang.ref.WeakReference;

class PKDialog$FbWebViewClient extends WebViewClient
{
  private PKDialog$FbWebViewClient(PKDialog paramPKDialog)
  {
  }

  public void onPageFinished(WebView paramWebView, String paramString)
  {
    super.onPageFinished(paramWebView, paramString);
    PKDialog.access$600(this.this$0).setVisibility(0);
  }

  public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
  {
    Log.d(PKDialog.access$300(), "Webview loading URL: " + paramString);
    super.onPageStarted(paramWebView, paramString, paramBitmap);
  }

  public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
  {
    super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
    PKDialog.access$500(this.this$0).onError(new UiError(paramInt, paramString1, paramString2));
    if ((PKDialog.access$400() != null) && (PKDialog.access$400().get() != null))
      Toast.makeText((Context)PKDialog.access$400().get(), "网络连接异常或系统错误", 0).show();
    this.this$0.dismiss();
  }

  @TargetApi(8)
  public void onReceivedSslError(WebView paramWebView, SslErrorHandler paramSslErrorHandler, SslError paramSslError)
  {
    paramSslErrorHandler.proceed();
  }

  public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
  {
    Log.d(PKDialog.access$300(), "Redirect URL: " + paramString);
    if (paramString.startsWith(ServerSetting.getInstance().getSettingUrl((Context)PKDialog.access$400().get(), 1)))
    {
      PKDialog.access$500(this.this$0).onComplete(Util.parseUrlToJson(paramString));
      this.this$0.dismiss();
      return true;
    }
    if (paramString.startsWith("auth://cancel"))
    {
      PKDialog.access$500(this.this$0).onCancel();
      this.this$0.dismiss();
      return true;
    }
    if (paramString.startsWith("auth://close"))
    {
      this.this$0.dismiss();
      return true;
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.PKDialog.FbWebViewClient
 * JD-Core Version:    0.6.0
 */