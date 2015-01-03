package com.kaixin001.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.GetUrlEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.model.EventModel.EventData;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.view.ContentListWindow;
import com.kaixin001.view.ContentListWindow.DoSelect;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WebPageActivity extends KXActivity
  implements ContentListWindow.DoSelect, PopupWindow.OnDismissListener, View.OnClickListener
{
  private static final int ALBUMDINDEX = 8;
  private static final int ALBUMTITLEINDEX = 11;
  private static final int ALBUMTITLLEINDEX = 53;
  public static final String EXTRA_ID = "id";
  public static final String EXTRA_TITLE = "title";
  public static final String EXTRA_UID = "uid";
  public static final String EXTRA_URL = "url";
  private static final int FNAMEINDEX = 6;
  private static final int FPIDINDEX = 4;
  private static final String FROM = "from";
  private static final String FROM_WEBPAGE = "from_webpage";
  private static final int FUIDINDEX = 5;
  private Button backButton = null;
  private TextView centerText = null;
  private Button forwardButton = null;
  private String mActionTitle = null;
  private int mCurrentSelectType = 301;
  private EventModel.EventData mEventData = null;
  private int mEventType = 0;
  private RelativeLayout mFootbarLayout;
  private boolean mFromPicture = false;
  private GetURLTask mGetUrlTask;
  private String mId = null;
  private String mKeyWord = null;
  private String mOptionTitle = null;
  private ContentListWindow mPopupWindow;
  private ProgressBar mProgressBar = null;
  private String mTitle = null;
  private String mUid = null;
  private String mUrl = null;
  private GetUrlEngine mWebEngineDangle = new GetUrlEngine();
  private WebView mWebView = null;
  private Button refreshButton = null;

  private void goToThirdPartPage(String paramString)
  {
    if ((this.mGetUrlTask != null) && (!this.mGetUrlTask.isCancelled()) && (this.mGetUrlTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    this.mGetUrlTask = null;
    if (this.mGetUrlTask == null)
      this.mGetUrlTask = new GetURLTask(null);
    this.mGetUrlTask.execute(new String[] { paramString });
  }

  private void setBottom()
  {
    this.backButton = ((Button)findViewById(2131364034));
    this.forwardButton = ((Button)findViewById(2131364035));
    this.refreshButton = ((Button)findViewById(2131364036));
    this.backButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (WebPageActivity.this.mWebView.canGoBack())
          WebPageActivity.this.mWebView.goBack();
      }
    });
    this.forwardButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (WebPageActivity.this.mWebView.canGoForward())
          WebPageActivity.this.mWebView.goForward();
      }
    });
    this.refreshButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        WebPageActivity.this.mWebView.reload();
      }
    });
  }

  private void showPopUpWindow(View paramView)
  {
    View localView = ((LayoutInflater)getSystemService("layout_inflater")).inflate(2130903239, null, false);
    if ((this.mPopupWindow != null) && (this.mPopupWindow.isShowing()))
      try
      {
        this.mPopupWindow.dismiss();
        this.mPopupWindow = null;
        return;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    float f = getResources().getDisplayMetrics().density;
    int i = getResources().getConfiguration().orientation;
    Resources localResources = getResources();
    ContentListWindow.mEditContent = new String[2];
    ContentListWindow.mEditContent[0] = localResources.getString(2131428493);
    ContentListWindow.mEditContent[1] = localResources.getString(2131428494);
    if (i == 1);
    for (this.mPopupWindow = new ContentListWindow(localView, (int)(180.0F * f), -2, true, this, this.mCurrentSelectType); ; this.mPopupWindow = new ContentListWindow(localView, (int)(180.0F * f), -2, true, this, this.mCurrentSelectType))
    {
      this.mPopupWindow.setDoSelectListener(this);
      this.mPopupWindow.setOnDismissListener(this);
      this.mPopupWindow.setOutsideTouchable(true);
      this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
      this.mPopupWindow.setClippingEnabled(false);
      int[] arrayOfInt = new int[2];
      paramView.getLocationInWindow(arrayOfInt);
      int j = 6 * (int)f;
      this.mPopupWindow.showAtLocation(paramView, 53, -(12 * (int)f), 13 + (arrayOfInt[1] + paramView.getHeight() - j));
      return;
    }
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
  }

  public void doSelect(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return;
    case 0:
      ((ClipboardManager)getSystemService("clipboard")).setText(this.mWebView.getUrl());
      Toast.makeText(this, 2131428497, 0).show();
      return;
    case 1:
    }
    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.mWebView.getUrl().toString())));
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(this, 2);
  }

  protected void initActionView()
  {
    View localView = findViewById(2131364032);
    if ((1 != this.mEventType) && (2 != this.mEventType))
      localView.setVisibility(8);
    ImageView localImageView;
    do
    {
      return;
      localView.findViewById(2131362020).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          switch (WebPageActivity.this.mEventType)
          {
          case 1:
          default:
            return;
          case 2:
          }
          WebPageActivity.this.writeNewRecord(WebPageActivity.this.mKeyWord);
        }
      });
      ((TextView)localView.findViewById(2131362022)).setText(this.mActionTitle);
      localImageView = (ImageView)localView.findViewById(2131362021);
      if (1 != this.mEventType)
        continue;
      localImageView.setImageResource(2130837756);
      return;
    }
    while (2 != this.mEventType);
    localImageView.setImageResource(2130837757);
  }

  protected void initWebView()
  {
    this.mFootbarLayout = ((RelativeLayout)findViewById(2131364033));
    if (this.mFromPicture)
      this.mFootbarLayout.setVisibility(8);
    this.mWebView = ((WebView)findViewById(2131362166));
    if (Build.VERSION.SDK_INT >= 11);
    try
    {
      this.mWebView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(this.mWebView, new Object[] { "searchBoxJavaBridge_" });
      this.mWebView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(this.mWebView, new Object[] { "accessibility" });
      label132: this.mWebView.setWebViewClient(new WebViewListener(null));
      this.mWebView.setPictureListener(new WebViewPictureListener(null));
      this.mWebView.setHorizontalScrollBarEnabled(false);
      this.mWebView.setHorizontalScrollbarOverlay(false);
      this.mWebView.setScrollBarStyle(0);
      this.mWebView.setDownloadListener(new DownloadListener()
      {
        public void onDownloadStart(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong)
        {
          Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString1));
          WebPageActivity.this.startActivity(localIntent);
        }
      });
      this.mWebView.setWebChromeClient(new WebChromeClient()
      {
        public void onProgressChanged(WebView paramWebView, int paramInt)
        {
          if (WebPageActivity.this.mProgressBar != null)
            WebPageActivity.this.mProgressBar.setProgress(paramInt);
          super.onProgressChanged(paramWebView, paramInt);
        }

        public void onReceivedTitle(WebView paramWebView, String paramString)
        {
          if ((paramString != null) && (!WebPageActivity.this.mFromPicture))
            WebPageActivity.this.centerText.setText(paramString);
          super.onReceivedTitle(paramWebView, paramString);
        }
      });
      this.mWebView.setOnKeyListener(new View.OnKeyListener()
      {
        public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
        {
          if ((paramKeyEvent.getAction() == 0) && (paramInt == 4) && (WebPageActivity.this.mWebView.canGoBack()))
          {
            WebPageActivity.this.mWebView.goBack();
            return true;
          }
          return false;
        }
      });
      WebSettings localWebSettings = this.mWebView.getSettings();
      localWebSettings.setSupportZoom(true);
      localWebSettings.setBuiltInZoomControls(false);
      localWebSettings.setJavaScriptEnabled(true);
      localWebSettings.setCacheMode(1);
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
      break label132;
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onClick(View paramView)
  {
    paramView.getId();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903419);
    enableSlideBack(false);
    this.mFromPicture = getIntent().getBooleanExtra("from", false);
    this.mId = getIntent().getStringExtra("id");
    initWebView();
    setBottom();
    Bundle localBundle = getIntent().getExtras();
    if (localBundle != null)
    {
      this.mUrl = localBundle.getString("url");
      this.mTitle = localBundle.getString("title");
      this.mUid = localBundle.getString("uid");
      if (this.mEventData != null)
      {
        this.mEventType = this.mEventData.getEventType();
        this.mActionTitle = this.mEventData.getActionTitle();
        this.mOptionTitle = this.mEventData.getOptionTitle();
        this.mKeyWord = this.mEventData.getKeyWord();
      }
    }
    setTitleBar();
    this.mProgressBar = ((ProgressBar)findViewById(2131363639));
    if (TextUtils.isEmpty(this.mUrl))
    {
      finish();
      return;
    }
    initActionView();
    this.mWebView.setVisibility(4);
    this.mWebView.setWebViewClient(new WebViewListener(null));
    this.mWebView.loadUrl(this.mUrl);
  }

  protected void onDestroy()
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

  public void onDismiss()
  {
  }

  public void requestFinish()
  {
  }

  protected void setTitleBar()
  {
    ImageView localImageView1 = (ImageView)findViewById(2131362914);
    Activity localActivity = getParent();
    if (localActivity != null)
      (localActivity instanceof MainActivity);
    localImageView1.setVisibility(0);
    localImageView1.setOnClickListener(new View.OnClickListener(localActivity)
    {
      public void onClick(View paramView)
      {
        if ((this.val$parent != null) && ((this.val$parent instanceof MainActivity)))
        {
          if (WebPageActivity.this.isMenuListVisibleInMain())
          {
            WebPageActivity.this.showSubActivityInMain();
            return;
          }
          WebPageActivity.this.showMenuListInMain();
          return;
        }
        WebPageActivity.this.finish();
      }
    });
    if (this.mFromPicture)
    {
      ImageView localImageView3 = (ImageView)findViewById(2131362928);
      localImageView3.setImageResource(2130839048);
      localImageView3.setVisibility(0);
      localImageView3.setOnClickListener(this);
    }
    while (true)
    {
      ((ImageView)findViewById(2131362919)).setVisibility(8);
      this.centerText = ((TextView)findViewById(2131362920));
      this.centerText.setText(2131427328);
      if (this.mFromPicture)
        this.centerText.setText(this.mTitle);
      this.centerText.setTextColor(-65794);
      this.centerText.setVisibility(0);
      return;
      ImageView localImageView2 = (ImageView)findViewById(2131362928);
      localImageView2.setImageResource(2130838622);
      localImageView2.setVisibility(0);
      localImageView2.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          WebPageActivity.this.showPopUpWindow(paramView);
        }
      });
    }
  }

  private class GetURLTask extends AsyncTask<String, Void, Integer>
  {
    private GetURLTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      try
      {
        String str = paramArrayOfString[0];
        boolean bool = WebPageActivity.this.mWebEngineDangle.getThirdPartyInfo(WebPageActivity.this.getApplicationContext(), str);
        if (bool);
        for (i = 1; ; i = 0)
          return Integer.valueOf(i);
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        while (true)
          int i = 0;
      }
    }

    protected void onPostExecute(Integer paramInteger)
    {
      try
      {
        if (paramInteger.intValue() == 1)
        {
          WebPageActivity.this.mWebView.loadUrl(WebPageActivity.this.mWebEngineDangle.getUrl());
          return;
        }
        Toast.makeText(WebPageActivity.this, 2131427387, 0).show();
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("KaixinDesktop", "onPostExecute", localException);
      }
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
      WebPageActivity.this.findViewById(2131362925).setVisibility(8);
      WebPageActivity.this.mWebView.setVisibility(0);
      if (WebPageActivity.this.mProgressBar != null)
        WebPageActivity.this.mProgressBar.setVisibility(8);
      if (WebPageActivity.this.refreshButton != null)
      {
        WebPageActivity.this.refreshButton.setBackgroundResource(2130839336);
        WebPageActivity.this.refreshButton.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            WebPageActivity.this.mWebView.reload();
          }
        });
      }
      if (paramWebView.canGoForward())
        WebPageActivity.this.forwardButton.setEnabled(true);
      while (paramWebView.canGoBack())
      {
        WebPageActivity.this.backButton.setEnabled(true);
        return;
        WebPageActivity.this.forwardButton.setEnabled(false);
      }
      WebPageActivity.this.backButton.setEnabled(false);
    }

    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      super.onPageStarted(paramWebView, paramString, paramBitmap);
      if (WebPageActivity.this.mProgressBar != null)
        WebPageActivity.this.mProgressBar.setVisibility(0);
      if (WebPageActivity.this.refreshButton != null)
      {
        WebPageActivity.this.refreshButton.setBackgroundResource(2130837607);
        WebPageActivity.this.refreshButton.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            WebPageActivity.this.mWebView.stopLoading();
          }
        });
      }
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      return false;
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
      WebPageActivity.this.findViewById(2131362925).setVisibility(8);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.WebPageActivity
 * JD-Core Version:    0.6.0
 */