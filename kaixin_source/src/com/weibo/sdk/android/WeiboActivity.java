package com.weibo.sdk.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.weibo.sdk.android.util.Utility;

public class WeiboActivity extends Activity
{
  static FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(-1, -1);
  private static final String TAG = "Weibo-WebView";
  private RelativeLayout mContent;
  private WeiboAuthListener mListener;
  private ProgressDialog mSpinner;
  private String mUrl;
  private WebView mWebView;
  private RelativeLayout webViewContainer;

  private void handleRedirectUrl(WebView paramWebView, String paramString)
  {
    Bundle localBundle = Utility.parseUrl(paramString);
    String str1 = localBundle.getString("error");
    String str2 = localBundle.getString("error_code");
    if ((str1 == null) && (str2 == null))
    {
      Intent localIntent = new Intent("com.weibo.android.sdk.auth");
      localIntent.putExtras(localBundle);
      sendBroadcast(localIntent);
      Log.d("wb--", localBundle.toString());
      return;
    }
    if (str1.equals("access_denied"))
    {
      Log.d("Weibo-WebView", "rejected");
      return;
    }
    if (str2 == null)
    {
      Log.d("Weibo-WebView", "exception");
      return;
    }
    Log.d("Weibo-WebView", "exception2");
  }

  private void setUpWebView()
  {
    this.webViewContainer = new RelativeLayout(this);
    this.mWebView = new WebView(this);
    this.mWebView.setVerticalScrollBarEnabled(false);
    this.mWebView.setHorizontalScrollBarEnabled(false);
    this.mWebView.getSettings().setJavaScriptEnabled(true);
    this.mWebView.setWebViewClient(new WeiboWebViewClient(null));
    this.mWebView.loadUrl(this.mUrl);
    this.mWebView.setLayoutParams(FILL);
    this.mWebView.setVisibility(4);
    this.mContent.setBackgroundColor(0);
    this.webViewContainer.addView(this.mWebView);
    this.webViewContainer.setGravity(17);
    this.mContent.addView(this.webViewContainer);
  }

  protected void onBack()
  {
    try
    {
      if (this.mWebView != null)
      {
        this.mWebView.stopLoading();
        this.mWebView.destroy();
      }
      label21: this.mSpinner.dismiss();
      finish();
      return;
    }
    catch (Exception localException)
    {
      break label21;
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    this.mUrl = getIntent().getExtras().getString("url");
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    getWindow().setFeatureDrawableAlpha(0, 0);
    this.mContent = new RelativeLayout(this);
    setUpWebView();
    addContentView(this.mContent, new ViewGroup.LayoutParams(-1, -1));
  }

  private class WeiboWebViewClient extends WebViewClient
  {
    private WeiboWebViewClient()
    {
    }

    public void onPageFinished(WebView paramWebView, String paramString)
    {
      Log.d("Weibo-WebView", "onPageFinished URL: " + paramString);
      super.onPageFinished(paramWebView, paramString);
      WeiboActivity.this.mWebView.setVisibility(0);
      WeiboActivity.this.mSpinner.dismiss();
    }

    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      Log.d("Weibo-WebView", "onPageStarted URL: " + paramString);
      if (paramString.startsWith(Weibo.redirecturl))
      {
        WeiboActivity.this.handleRedirectUrl(paramWebView, paramString);
        paramWebView.stopLoading();
        WeiboActivity.this.finish();
        return;
      }
      WeiboActivity.this.mSpinner = new ProgressDialog(paramWebView.getContext());
      WeiboActivity.this.mSpinner.setMessage("请求中,请稍候...");
      WeiboActivity.this.mSpinner.show();
      WeiboActivity.this.mSpinner.setOnKeyListener(new DialogInterface.OnKeyListener()
      {
        public boolean onKey(DialogInterface paramDialogInterface, int paramInt, KeyEvent paramKeyEvent)
        {
          WeiboActivity.this.onBack();
          return false;
        }
      });
      super.onPageStarted(paramWebView, paramString, paramBitmap);
    }

    public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
    {
      super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
      WeiboActivity.this.mListener.onError(new WeiboDialogError(paramString1, paramInt, paramString2));
      WeiboActivity.this.finish();
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      Log.d("Weibo-WebView", "Redirect URL: " + paramString);
      if (paramString.startsWith("sms:"))
      {
        Intent localIntent = new Intent("android.intent.action.VIEW");
        localIntent.putExtra("address", paramString.replace("sms:", ""));
        localIntent.setType("vnd.android-dir/mms-sms");
        WeiboActivity.this.startActivity(localIntent);
        return true;
      }
      return super.shouldOverrideUrlLoading(paramWebView, paramString);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.weibo.sdk.android.WeiboActivity
 * JD-Core Version:    0.6.0
 */