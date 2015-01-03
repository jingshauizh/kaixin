package com.kaixin001.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StyleBoxDiaryDetailFragment extends BaseFragment
{
  private String mCommentFlag = null;
  private String mDelete = "0";
  private String mFname = null;
  private String mFuid = null;
  private String mId = null;
  private String mIntro = null;
  private String mReplyContent = "";
  private String mTitle = null;
  private String mVisible = "1";
  private WebView mWebView = null;

  protected void initWebView()
  {
    this.mWebView = ((WebView)findViewById(2131362166));
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

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903094, paramViewGroup, false);
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

  public void onPause()
  {
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
    WebView localWebView = (WebView)findViewById(2131362166);
    if (localWebView != null)
    {
      localWebView.clearChildFocus(localWebView.getFocusedChild());
      localWebView.clearFocus();
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
    String str1 = "";
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mFname = localBundle.getString("fname");
      if (this.mFname == null)
        this.mFname = getString(2131427565);
      this.mFuid = localBundle.getString("fuid");
      this.mId = localBundle.getString("id");
      this.mTitle = localBundle.getString("title");
      this.mIntro = localBundle.getString("intro");
      this.mCommentFlag = localBundle.getString("commentflag");
      str1 = localBundle.getString("cnum");
      if (TextUtils.isEmpty(str1))
        str1 = "0";
      this.mDelete = localBundle.getString("delete");
      this.mVisible = localBundle.getString("visible");
    }
    initWebView();
    setTitleBar();
    Button localButton = (Button)findViewById(2131362168);
    if (("1".equals(this.mDelete)) || ("0".equals(this.mVisible)))
      localButton.setVisibility(8);
    while (true)
    {
      findViewById(2131362169).setVisibility(8);
      findViewById(2131362165).setVisibility(8);
      if ((!TextUtils.isEmpty(this.mId)) && (!TextUtils.isEmpty(this.mFuid)))
      {
        this.mWebView.setVisibility(0);
        String str2 = HttpProxy.getWapProxyURL(Protocol.getInstance().makeGetStyleBoxDiaryRequest(this.mFuid, this.mId));
        this.mWebView.loadUrl(str2);
      }
      return;
      localButton.setText(str1);
      localButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if ((TextUtils.isEmpty(StyleBoxDiaryDetailFragment.this.mId)) || (TextUtils.isEmpty(StyleBoxDiaryDetailFragment.this.mFuid)))
            return;
          Intent localIntent = new Intent(StyleBoxDiaryDetailFragment.this.getActivity(), ObjCommentFragment.class);
          localIntent.putExtra("fuid", StyleBoxDiaryDetailFragment.this.mFuid);
          localIntent.putExtra("id", StyleBoxDiaryDetailFragment.this.mId);
          localIntent.putExtra("type", "1210");
          localIntent.putExtra("commentflag", StyleBoxDiaryDetailFragment.this.mCommentFlag);
          localIntent.putExtra("title", StyleBoxDiaryDetailFragment.this.mTitle);
          localIntent.putExtra("intro", StyleBoxDiaryDetailFragment.this.mIntro);
          localIntent.putExtra("fname", StyleBoxDiaryDetailFragment.this.mFname);
          localIntent.putExtra("mode", 3);
          StyleBoxDiaryDetailFragment.this.startActivityForResult(localIntent, 3);
        }
      });
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
        if (!TextUtils.isEmpty(StyleBoxDiaryDetailFragment.this.mReplyContent))
        {
          Intent localIntent = new Intent();
          localIntent.putExtra("content", StyleBoxDiaryDetailFragment.this.mReplyContent);
          StyleBoxDiaryDetailFragment.this.setResult(-1, localIntent);
          StyleBoxDiaryDetailFragment.this.finishFragment(3);
        }
        StyleBoxDiaryDetailFragment.this.finish();
      }
    });
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    String str = this.mFname;
    if ((this.mFname != null) && (str.length() > 5))
      str = str.substring(0, 5) + "...";
    localTextView.setText(str + getResources().getString(2131427596));
    localTextView.setVisibility(0);
  }

  private class WebViewListener extends WebViewClient
  {
    private WebViewListener()
    {
    }

    public void onPageFinished(WebView paramWebView, String paramString)
    {
      super.onPageFinished(paramWebView, paramString);
      if (StyleBoxDiaryDetailFragment.this.isNeedReturn())
        return;
      StyleBoxDiaryDetailFragment.this.findViewById(2131362925).setVisibility(8);
    }

    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      super.onPageStarted(paramWebView, paramString, paramBitmap);
      if (StyleBoxDiaryDetailFragment.this.isNeedReturn())
        return;
      ProgressBar localProgressBar = (ProgressBar)StyleBoxDiaryDetailFragment.this.findViewById(2131362925);
      localProgressBar.setIndeterminateDrawable(StyleBoxDiaryDetailFragment.this.getResources().getDrawable(2130838835));
      localProgressBar.setVisibility(0);
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
      StyleBoxDiaryDetailFragment.this.findViewById(2131362925).setVisibility(8);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.StyleBoxDiaryDetailFragment
 * JD-Core Version:    0.6.0
 */