package com.tencent.sdkutil;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.tencent.tauth.UiError;
import java.lang.ref.WeakReference;
import org.json.JSONObject;

class TDialog$FbWebViewClient extends WebViewClient
{
  private TDialog$FbWebViewClient(TDialog paramTDialog)
  {
  }

  public void onPageFinished(WebView paramWebView, String paramString)
  {
    super.onPageFinished(paramWebView, paramString);
    if ((TDialog.access$300() != null) && (TDialog.access$300().get() != null))
      ((View)TDialog.access$300().get()).setVisibility(8);
    TDialog.access$1100(this.this$0).setVisibility(0);
  }

  public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
  {
    super.onPageStarted(paramWebView, paramString, paramBitmap);
    if ((TDialog.access$300() != null) && (TDialog.access$300().get() != null))
      ((View)TDialog.access$300().get()).setVisibility(0);
  }

  public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
  {
    super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
    TDialog.access$1200(this.this$0).onError(new UiError(paramInt, paramString1, paramString2));
    if ((TDialog.access$100() != null) && (TDialog.access$100().get() != null))
      Toast.makeText((Context)TDialog.access$100().get(), "网络连接异常或系统错误", 0).show();
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
      JSONObject localJSONObject = Util.parseUrlToJson(paramString);
      Log.i("olddata", localJSONObject.toString());
      TDialog.access$802(this.this$0, TDialog.access$900(this.this$0));
      if (TDialog.access$800(this.this$0));
      while (true)
      {
        return true;
        if (localJSONObject.optString("fail_cb", null) != null)
        {
          this.this$0.callJs(localJSONObject.optString("fail_cb"), "");
          continue;
        }
        if (localJSONObject.optInt("fall_to_wv") == 1)
        {
          TDialog localTDialog = this.this$0;
          if (TDialog.access$1000(this.this$0).indexOf("?") > -1);
          for (String str2 = "&"; ; str2 = "?")
          {
            TDialog.access$1084(localTDialog, str2);
            TDialog.access$1084(this.this$0, "browser_error=1");
            TDialog.access$1100(this.this$0).loadUrl(TDialog.access$1000(this.this$0));
            break;
          }
        }
        String str1 = localJSONObject.optString("redir", null);
        if (str1 == null)
          continue;
        TDialog.access$1100(this.this$0).loadUrl(str1);
      }
    }
    if (paramString.startsWith(ServerSetting.getInstance().getSettingUrl((Context)TDialog.access$100().get(), 1)))
    {
      TDialog.access$1200(this.this$0).onComplete(Util.parseUrlToJson(paramString));
      this.this$0.dismiss();
      return true;
    }
    if (paramString.startsWith("auth://cancel"))
    {
      TDialog.access$1200(this.this$0).onCancel();
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
      if ((TDialog.access$100() != null) && (TDialog.access$100().get() != null))
        ((Context)TDialog.access$100().get()).startActivity(localIntent);
      return true;
    }
    return false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.TDialog.FbWebViewClient
 * JD-Core Version:    0.6.0
 */