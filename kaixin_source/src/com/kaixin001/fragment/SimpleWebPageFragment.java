package com.kaixin001.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.model.User;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.UserHabitUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SimpleWebPageFragment extends BaseFragment
{
  public static final String EXTRA_TITLE = "title";
  public static final String EXTRA_URL = "url";
  private String mAccount = "";
  private TextView mCenterText = null;
  private boolean mLogin = false;
  private String mPassword = "";
  private ProgressBar mProgressBar = null;
  private String mTitle = null;
  private String mToken = "";
  private String mTokenSecret = "";
  private String mUid = "";
  private String mUrl = null;
  private WebView mWebView = null;
  private Button tvRightText = null;

  private boolean parseUrl(String paramString)
  {
    if ((!TextUtils.isEmpty(paramString)) && (paramString.indexOf("oauth_token_secret") > 0))
    {
      String[] arrayOfString = paramString.split("&");
      int i = arrayOfString.length;
      int j = 0;
      if (j >= i)
      {
        User localUser = User.getInstance();
        localUser.setLookAround(Boolean.valueOf(false));
        localUser.logoutClear();
        localUser.setUID(this.mUid);
        localUser.setAccount(this.mAccount);
        localUser.setOauthToken(this.mToken);
        localUser.setOauthTokenSecret(this.mTokenSecret);
        localUser.setImcomplete(1);
        localUser.saveUserLoginInfo(getActivity());
        localUser.loadUserData(getActivity());
        if (!this.mLogin)
        {
          this.mLogin = true;
          UserHabitUtils.getInstance(getActivity()).addUserHabit("Wap_Register_Complete");
          KXEnvironment.saveBooleanParams(getActivity(), "needCompleteInfo", true, true);
          IntentUtil.returnToDesktop(getActivity());
        }
        return true;
      }
      String str = arrayOfString[j];
      if (str == null);
      while (true)
      {
        j++;
        break;
        if (str.startsWith("uid="))
        {
          this.mUid = str.substring("uid=".length());
          continue;
        }
        if (str.startsWith("account="))
        {
          this.mAccount = str.substring("account=".length());
          continue;
        }
        if (str.startsWith("password="))
        {
          this.mPassword = str.substring("password=".length());
          continue;
        }
        if (str.startsWith("oauth_token="))
        {
          this.mToken = str.substring("oauth_token=".length());
          continue;
        }
        if (!str.startsWith("oauth_token_secret="))
          continue;
        this.mTokenSecret = str.substring("oauth_token_secret=".length());
      }
    }
    return false;
  }

  protected void initWebView()
  {
    this.mWebView = ((WebView)findViewById(2131362166));
    if (Build.VERSION.SDK_INT >= 11);
    try
    {
      this.mWebView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(this.mWebView, new Object[] { "searchBoxJavaBridge_" });
      this.mWebView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(this.mWebView, new Object[] { "accessibility" });
      this.mWebView.setWebViewClient(new WebViewListener(null));
      this.mWebView.setHorizontalScrollBarEnabled(false);
      this.mWebView.setHorizontalScrollbarOverlay(false);
      this.mWebView.setScrollBarStyle(0);
      this.mWebView.setDownloadListener(new DownloadListener()
      {
        public void onDownloadStart(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong)
        {
          Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString1));
          SimpleWebPageFragment.this.startActivity(localIntent);
        }
      });
      this.mWebView.setWebChromeClient(new WebChromeClient()
      {
        public void onProgressChanged(WebView paramWebView, int paramInt)
        {
          if (SimpleWebPageFragment.this.mProgressBar != null)
            SimpleWebPageFragment.this.mProgressBar.setProgress(paramInt);
          super.onProgressChanged(paramWebView, paramInt);
        }

        public void onReceivedTitle(WebView paramWebView, String paramString)
        {
          if (TextUtils.isEmpty(SimpleWebPageFragment.this.mTitle))
            SimpleWebPageFragment.this.mCenterText.setText(paramString);
          super.onReceivedTitle(paramWebView, paramString);
        }
      });
      this.mWebView.setOnKeyListener(new View.OnKeyListener()
      {
        public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
        {
          if ((paramKeyEvent.getAction() == 0) && (paramInt == 4) && (SimpleWebPageFragment.this.mWebView.canGoBack()))
          {
            SimpleWebPageFragment.this.mWebView.goBack();
            return true;
          }
          return false;
        }
      });
      WebSettings localWebSettings = this.mWebView.getSettings();
      localWebSettings.setSupportZoom(true);
      localWebSettings.setBuiltInZoomControls(false);
      localWebSettings.setJavaScriptEnabled(true);
      localWebSettings.setCacheMode(2);
      localWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
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
      while (true)
        localNoSuchMethodException.printStackTrace();
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mUrl = localBundle.getString("url");
      this.mTitle = localBundle.getString("title");
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903336, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if (this.mWebView != null)
      this.mWebView.setVisibility(8);
    try
    {
      this.mWebView.getSettings().setBuiltInZoomControls(true);
      this.mWebView.clearCache(true);
      this.mWebView.destroy();
      super.onDestroy();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mProgressBar = ((ProgressBar)findViewById(2131363639));
    setTitleBar();
    initWebView();
    this.mWebView.setVisibility(4);
    this.mWebView.setWebViewClient(new WebViewListener(null));
    this.mWebView.loadUrl(this.mUrl);
  }

  protected void setTitleBar()
  {
    ImageView localImageView1 = (ImageView)findViewById(2131362914);
    getActivity().getParent();
    localImageView1.setVisibility(0);
    localImageView1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        SimpleWebPageFragment.this.finish();
      }
    });
    ImageView localImageView2 = (ImageView)findViewById(2131362928);
    localImageView2.setImageResource(2130839048);
    localImageView2.setVisibility(8);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    this.mCenterText = ((TextView)findViewById(2131362920));
    this.mCenterText.setText(2131427328);
    if (!TextUtils.isEmpty(this.mTitle))
      this.mCenterText.setText(this.mTitle);
    this.mCenterText.setTextColor(-65794);
    this.mCenterText.setVisibility(0);
    ImageView localImageView3 = (ImageView)findViewById(2131362926);
    localImageView3.setVisibility(0);
    localImageView3.setImageResource(2130838295);
    this.tvRightText = ((Button)findViewById(2131362931));
    this.tvRightText.setVisibility(0);
    this.tvRightText.setPadding(dipToPx(7.0F), 0, dipToPx(12.0F), 0);
    this.tvRightText.setText(2131428592);
    Drawable localDrawable = getResources().getDrawable(2130838557);
    this.tvRightText.setCompoundDrawablesWithIntrinsicBounds(localDrawable, null, null, null);
    this.tvRightText.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        SimpleWebPageFragment.this.getActivity().getSupportFragmentManager().popBackStack();
      }
    });
  }

  private class WebViewListener extends WebViewClient
  {
    private WebViewListener()
    {
    }

    public void onPageFinished(WebView paramWebView, String paramString)
    {
      if ((SimpleWebPageFragment.this.getActivity() == null) || (SimpleWebPageFragment.this.getView() == null))
        return;
      if (SimpleWebPageFragment.this.mProgressBar != null)
        SimpleWebPageFragment.this.mProgressBar.setVisibility(8);
      SimpleWebPageFragment.this.mWebView.setVisibility(0);
      super.onPageFinished(paramWebView, paramString);
    }

    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      if ((SimpleWebPageFragment.this.getActivity() == null) || (SimpleWebPageFragment.this.getView() == null))
        return;
      if (SimpleWebPageFragment.this.mProgressBar != null)
        SimpleWebPageFragment.this.mProgressBar.setVisibility(0);
      super.onPageStarted(paramWebView, paramString, paramBitmap);
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      if (SimpleWebPageFragment.this.parseUrl(paramString))
        return true;
      return super.shouldOverrideUrlLoading(paramWebView, paramString);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.SimpleWebPageFragment
 * JD-Core Version:    0.6.0
 */