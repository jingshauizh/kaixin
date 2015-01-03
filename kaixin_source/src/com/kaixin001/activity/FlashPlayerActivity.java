package com.kaixin001.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.model.User;
import com.kaixin001.util.CheckSupportFlashUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FlashPlayerActivity extends KXActivity
{
  public static final String EXTRA_TITLE = "title";
  public static final String EXTRA_URL = "url";
  private String mTitle = null;
  private WebView mWebView = null;

  protected void initWebView()
  {
    this.mWebView = ((WebView)findViewById(2131362346));
    if (Build.VERSION.SDK_INT >= 11);
    try
    {
      this.mWebView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(this.mWebView, new Object[] { "searchBoxJavaBridge_" });
      this.mWebView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(this.mWebView, new Object[] { "accessibility" });
      label97: this.mWebView.getSettings().setJavaScriptEnabled(true);
      this.mWebView.setWebViewClient(new WebViewListener(null));
      this.mWebView.setPictureListener(new WebViewPictureListener(null));
      WebSettings localWebSettings = this.mWebView.getSettings();
      localWebSettings.setSupportZoom(true);
      localWebSettings.setBuiltInZoomControls(true);
      localWebSettings.setJavaScriptEnabled(true);
      this.mWebView.setHorizontalScrollBarEnabled(true);
      this.mWebView.setHorizontalScrollbarOverlay(true);
      if (Build.VERSION.SDK_INT >= 8)
        localWebSettings.setPluginState(WebSettings.PluginState.ON);
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
      break label97;
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903124);
    User.getInstance().loadDataIfEmpty(getApplicationContext());
    initWebView();
    setTitleBar();
    String str = getIntent().getStringExtra("url");
    this.mTitle = getIntent().getStringExtra("title");
    if (TextUtils.isEmpty(str))
    {
      finish();
      return;
    }
    if (CheckSupportFlashUtil.getInstance(getApplicationContext()).isSupportFlash() != 1)
    {
      if (CheckSupportFlashUtil.getInstance(getApplicationContext()).getFlashStatus() != 0)
        break label118;
      Toast.makeText(this, 2131427364, 0).show();
    }
    while (true)
    {
      finish();
      this.mWebView.loadUrl(str);
      setTitleBar();
      return;
      label118: Toast.makeText(this, 2131427363, 0).show();
    }
  }

  protected void onDestroy()
  {
    try
    {
      if (this.mWebView != null)
        this.mWebView.setVisibility(8);
      try
      {
        this.mWebView.getSettings().setBuiltInZoomControls(true);
        this.mWebView.clearCache(true);
        this.mWebView.destroy();
        super.onDestroy();
        System.gc();
        return;
      }
      catch (Exception localException2)
      {
        while (true)
          localException2.printStackTrace();
      }
    }
    catch (Exception localException1)
    {
      while (true)
        localException1.printStackTrace();
    }
  }

  protected void onPause()
  {
    super.onPause();
    try
    {
      Class.forName("android.webkit.WebView").getMethod("onPause", null).invoke(this.mWebView, null);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  protected void onResume()
  {
    super.onResume();
    if (this.mWebView != null)
    {
      View localView = this.mWebView.getFocusedChild();
      this.mWebView.clearChildFocus(localView);
      this.mWebView.clearFocus();
    }
    try
    {
      Class.forName("android.webkit.WebView").getMethod("onResume", null).invoke(this.mWebView, null);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  protected void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        FlashPlayerActivity.this.finish();
      }
    });
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    String str = getIntent().getStringExtra("title");
    if (!TextUtils.isEmpty(str))
    {
      ((ImageView)findViewById(2131362919)).setVisibility(8);
      TextView localTextView = (TextView)findViewById(2131362920);
      localTextView.setText(str);
      localTextView.setVisibility(0);
    }
  }

  private class WebViewListener extends WebViewClient
  {
    private WebViewListener()
    {
    }

    public void onPageFinished(WebView paramWebView, String paramString)
    {
      super.onPageFinished(paramWebView, paramString);
      FlashPlayerActivity.this.findViewById(2131362925).setVisibility(8);
    }

    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      super.onPageStarted(paramWebView, paramString, paramBitmap);
      FlashPlayerActivity.this.findViewById(2131362925).setVisibility(0);
    }
  }

  private class WebViewPictureListener
    implements WebView.PictureListener
  {
    private WebViewPictureListener()
    {
    }

    public void onNewPicture(WebView paramWebView, Picture paramPicture)
    {
      FlashPlayerActivity.this.findViewById(2131362925).setVisibility(8);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.FlashPlayerActivity
 * JD-Core Version:    0.6.0
 */