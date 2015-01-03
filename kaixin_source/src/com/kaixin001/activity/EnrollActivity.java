package com.kaixin001.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnrollActivity extends Activity
  implements View.OnClickListener
{
  private static final String REGISTER_URL = "http://iphone.kaixin001.com/reg/client-reg.html";
  private ImageView btnLeft;
  private ImageView btnRight;
  private TextView tvTitle;
  private WebView webView;

  private void doEnroll(String paramString)
  {
    Intent localIntent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + paramString));
    localIntent.putExtra("sms_body", "");
    startActivity(localIntent);
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362914:
    }
    finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903338);
    setTitleBar();
    this.webView = ((WebView)findViewById(2131363645));
    if (Build.VERSION.SDK_INT >= 11);
    try
    {
      this.webView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(this.webView, new Object[] { "searchBoxJavaBridge_" });
      this.webView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(this.webView, new Object[] { "accessibility" });
      label112: this.webView.getSettings().setJavaScriptEnabled(true);
      this.webView.setWebViewClient(new WebViewClient()
      {
        public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
        {
          if ((paramString.startsWith("sms:")) || (paramString.startsWith("smsto:")))
            EnrollActivity.this.doEnroll(paramString.substring(1 + paramString.indexOf(":"), paramString.length()));
          return true;
        }
      });
      this.webView.loadUrl("http://iphone.kaixin001.com/reg/client-reg.html");
      return;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
        localIllegalAccessException.printStackTrace();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
        localInvocationTargetException.printStackTrace();
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      break label112;
    }
  }

  protected void setTitleBar()
  {
    this.btnLeft = ((ImageView)findViewById(2131362914));
    this.btnLeft.setOnClickListener(this);
    this.btnLeft.setOnClickListener(this);
    this.tvTitle = ((TextView)findViewById(2131362920));
    this.tvTitle.setText(2131427944);
    this.tvTitle.setVisibility(0);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    this.btnRight = ((ImageView)findViewById(2131362928));
    this.btnRight.setVisibility(8);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.EnrollActivity
 * JD-Core Version:    0.6.0
 */