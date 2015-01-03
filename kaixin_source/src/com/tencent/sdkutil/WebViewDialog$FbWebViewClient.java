package com.tencent.sdkutil;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.tencent.tauth.UiError;
import java.lang.ref.WeakReference;

class WebViewDialog$FbWebViewClient extends WebViewClient
{
  private WebViewDialog$FbWebViewClient(WebViewDialog paramWebViewDialog)
  {
  }

  public void onPageFinished(WebView paramWebView, String paramString)
  {
    super.onPageFinished(paramWebView, paramString);
    if ((WebViewDialog.access$300() != null) && (WebViewDialog.access$300().get() != null))
      ((View)WebViewDialog.access$300().get()).setVisibility(8);
    WebViewDialog.access$900(this.this$0).setVisibility(0);
  }

  public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
  {
    super.onPageStarted(paramWebView, paramString, paramBitmap);
    if ((WebViewDialog.access$300() != null) && (WebViewDialog.access$300().get() != null))
      ((View)WebViewDialog.access$300().get()).setVisibility(0);
  }

  public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
  {
    super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
    WebViewDialog.access$800(this.this$0).onError(new UiError(paramInt, paramString1, paramString2));
    if ((WebViewDialog.access$100() != null) && (WebViewDialog.access$100().get() != null))
      Toast.makeText((Context)WebViewDialog.access$100().get(), "网络连接异常或系统错误", 0).show();
    this.this$0.dismiss();
  }

  @TargetApi(8)
  public void onReceivedSslError(WebView paramWebView, SslErrorHandler paramSslErrorHandler, SslError paramSslError)
  {
    paramSslErrorHandler.proceed();
  }

  public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
  {
    if (paramString.startsWith("auth://browser"))
    {
      Util.parseUrlToJson(paramString);
      return true;
    }
    if (paramString.startsWith(ServerSetting.getInstance().getSettingUrl((Context)WebViewDialog.access$100().get(), 1)))
    {
      WebViewDialog.access$800(this.this$0).onComplete(Util.parseUrlToJson(paramString));
      this.this$0.dismiss();
      return true;
    }
    if (paramString.startsWith("auth://cancel"))
    {
      WebViewDialog.access$800(this.this$0).onCancel();
      this.this$0.dismiss();
      return true;
    }
    if (paramString.startsWith("auth://close"))
    {
      this.this$0.dismiss();
      return true;
    }
    if (paramString.startsWith("download://"))
    {
      Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(paramString.substring("download://".length()))));
      if ((WebViewDialog.access$100() != null) && (WebViewDialog.access$100().get() != null))
        ((Context)WebViewDialog.access$100().get()).startActivity(localIntent);
      return true;
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.WebViewDialog.FbWebViewClient
 * JD-Core Version:    0.6.0
 */