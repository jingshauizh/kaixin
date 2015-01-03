package com.tencent.sdkutil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.QQToken;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class TDialog extends Dialog
{
  public static final String CANCEL_URI = "auth://cancel";
  public static final String CLOSE_URI = "auth://close";
  static final String DISPLAY_STRING = "touch";
  public static final String DOWNLOAD_URI = "download://";
  static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(-1, -1);
  private static final int MSG_CANCEL = 2;
  private static final int MSG_COMPLETE = 1;
  private static final int MSG_ON_LOAD = 4;
  private static final int MSG_SHOW_PROCESS = 5;
  private static final int MSG_SHOW_TIPS = 3;
  public static final String REDIRECT_BROWSER_URI = "auth://browser";
  private static final String TAG = "TDialog";
  static Toast sToast = null;
  private static WeakReference<Context> sWeakContext;
  private static WeakReference<View> sWeakProcessBar;
  private static WeakReference<ProgressDialog> sWeakProcessDialog;
  private boolean isBrowserStarted = false;
  private IUiListener listener;
  private String mAction;
  private FrameLayout mFlMain;
  private LinearLayout mFlProcess;
  private Handler mHandler;
  private TDialog.OnTimeListener mListener;
  private ProgressBar mPbProcess;
  private FrameLayout mProcessContainer;
  private QQToken mQQToken = null;
  private String mUrl;
  private WebView mWebView;

  public TDialog(Context paramContext, String paramString1, String paramString2, IUiListener paramIUiListener, QQToken paramQQToken)
  {
    super(paramContext, 16973840);
    sWeakContext = new WeakReference(paramContext);
    this.mUrl = paramString2;
    this.mListener = new TDialog.OnTimeListener(paramContext, paramString1, paramString2, paramQQToken.getAppId(), paramIUiListener);
    this.mHandler = new TDialog.THandler(this.mListener, paramContext.getMainLooper());
    this.listener = paramIUiListener;
    this.mQQToken = paramQQToken;
    this.mAction = paramString1;
  }

  private boolean authWithBrowser()
  {
    BrowserAuth localBrowserAuth = BrowserAuth.getInstance();
    String str1 = localBrowserAuth.makeKey();
    BrowserAuth.Auth localAuth = new BrowserAuth.Auth();
    localAuth.listener = this.listener;
    localAuth.dialog = this;
    localAuth.key = str1;
    String str2 = localBrowserAuth.set(localAuth);
    String str3 = this.mUrl.substring(0, this.mUrl.indexOf("?"));
    Log.v("rootUrl", str3);
    Bundle localBundle = Util.parseUrl(this.mUrl);
    localBundle.putString("token_key", str1);
    localBundle.putString("serial", str2);
    localBundle.putString("browser", "1");
    this.mUrl = (str3 + "?" + Util.encodeUrl(localBundle));
    WeakReference localWeakReference = sWeakContext;
    boolean bool = false;
    if (localWeakReference != null)
    {
      Object localObject = sWeakContext.get();
      bool = false;
      if (localObject != null)
        bool = new HttpUtils((Context)sWeakContext.get(), this.mQQToken).openBrowser(this.mUrl);
    }
    return bool;
  }

  private void createProgress()
  {
    this.mPbProcess = new ProgressBar((Context)sWeakContext.get());
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-2, -2);
    this.mPbProcess.setLayoutParams(localLayoutParams1);
    this.mFlProcess = new LinearLayout((Context)sWeakContext.get());
    boolean bool = this.mAction.equals("action_login");
    Object localObject = null;
    LinearLayout.LayoutParams localLayoutParams2;
    TextView localTextView;
    if (bool)
    {
      localLayoutParams2 = new LinearLayout.LayoutParams(-2, -2);
      localLayoutParams2.gravity = 16;
      localLayoutParams2.leftMargin = 5;
      localTextView = new TextView((Context)sWeakContext.get());
      if (!Locale.getDefault().getLanguage().equals("zh"))
        break label329;
      localTextView.setText("登录中...");
    }
    while (true)
    {
      localTextView.setTextColor(Color.rgb(255, 255, 255));
      localTextView.setTextSize(18.0F);
      localTextView.setLayoutParams(localLayoutParams2);
      localObject = localTextView;
      FrameLayout.LayoutParams localLayoutParams3 = new FrameLayout.LayoutParams(-2, -2);
      localLayoutParams3.gravity = 17;
      this.mFlProcess.setLayoutParams(localLayoutParams3);
      this.mFlProcess.addView(this.mPbProcess);
      if (localObject != null)
        this.mFlProcess.addView(localObject);
      this.mProcessContainer = new FrameLayout((Context)sWeakContext.get());
      FrameLayout.LayoutParams localLayoutParams4 = new FrameLayout.LayoutParams(-1, -2);
      localLayoutParams4.leftMargin = 80;
      localLayoutParams4.rightMargin = 80;
      localLayoutParams4.topMargin = 40;
      localLayoutParams4.bottomMargin = 40;
      localLayoutParams4.gravity = 17;
      this.mProcessContainer.setLayoutParams(localLayoutParams4);
      this.mProcessContainer.setBackgroundResource(17301504);
      this.mProcessContainer.addView(this.mFlProcess);
      return;
      label329: localTextView.setText("Logging in...");
    }
  }

  private void createViews()
  {
    createProgress();
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -1);
    this.mWebView = new WebView((Context)sWeakContext.get());
    this.mWebView.setLayoutParams(localLayoutParams);
    this.mFlMain = new FrameLayout((Context)sWeakContext.get());
    localLayoutParams.gravity = 17;
    this.mFlMain.setLayoutParams(localLayoutParams);
    this.mFlMain.addView(this.mWebView);
    this.mFlMain.addView(this.mProcessContainer);
    sWeakProcessBar = new WeakReference(this.mProcessContainer);
    setContentView(this.mFlMain);
  }

  @SuppressLint({"SetJavaScriptEnabled"})
  private void initViews()
  {
    this.mWebView.setVerticalScrollBarEnabled(false);
    this.mWebView.setHorizontalScrollBarEnabled(false);
    this.mWebView.setWebViewClient(new TDialog.FbWebViewClient(this, null));
    this.mWebView.setWebChromeClient(new WebChromeClient());
    this.mWebView.clearFormData();
    WebSettings localWebSettings = this.mWebView.getSettings();
    localWebSettings.setSavePassword(false);
    localWebSettings.setSaveFormData(false);
    localWebSettings.setCacheMode(-1);
    localWebSettings.setNeedInitialFocus(false);
    localWebSettings.setBuiltInZoomControls(true);
    localWebSettings.setSupportZoom(true);
    localWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
    localWebSettings.setJavaScriptEnabled(true);
    if ((sWeakContext != null) && (sWeakContext.get() != null))
    {
      localWebSettings.setDatabaseEnabled(true);
      localWebSettings.setDatabasePath(((Context)sWeakContext.get()).getApplicationContext().getDir("databases", 0).getPath());
    }
    localWebSettings.setDomStorageEnabled(true);
    try
    {
      Method localMethod = WebView.class.getMethod("addJavascriptInterface", new Class[] { Object.class, String.class });
      WebView localWebView = this.mWebView;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = new TDialog.JsListener(this, null);
      arrayOfObject[1] = "sdk_js_if";
      localMethod.invoke(localWebView, arrayOfObject);
      this.mWebView.loadUrl(this.mUrl);
      this.mWebView.setLayoutParams(FILL);
      this.mWebView.setVisibility(4);
      this.mWebView.getSettings().setSavePassword(false);
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      while (true)
      {
        Log.e("TDialog", localNoSuchMethodException.getMessage());
        localNoSuchMethodException.printStackTrace();
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
      {
        Log.e("TDialog", localIllegalArgumentException.getMessage());
        localIllegalArgumentException.printStackTrace();
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
      {
        Log.e("TDialog", localIllegalAccessException.getMessage());
        localIllegalAccessException.printStackTrace();
      }
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
      {
        Log.e("TDialog", localInvocationTargetException.getMessage());
        localInvocationTargetException.printStackTrace();
      }
    }
    catch (Exception localException)
    {
      while (true)
        Log.e("TDialog", localException.getMessage());
    }
  }

  private static void showProcessDialog(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (paramString == null));
    int i;
    do
      while (true)
      {
        return;
        String str;
        try
        {
          JSONObject localJSONObject = JsonUtil.parseJson(paramString);
          i = localJSONObject.getInt("action");
          str = localJSONObject.getString("msg");
          if (i != 1)
            break;
          if (sWeakProcessDialog == null)
          {
            ProgressDialog localProgressDialog = new ProgressDialog(paramContext);
            localProgressDialog.setMessage(str);
            sWeakProcessDialog = new WeakReference(localProgressDialog);
            localProgressDialog.show();
            return;
          }
        }
        catch (JSONException localJSONException)
        {
          localJSONException.printStackTrace();
          return;
        }
        ((ProgressDialog)sWeakProcessDialog.get()).setMessage(str);
        if (((ProgressDialog)sWeakProcessDialog.get()).isShowing())
          continue;
        ((ProgressDialog)sWeakProcessDialog.get()).show();
        return;
      }
    while ((i != 0) || (sWeakProcessDialog == null) || (sWeakProcessDialog.get() == null) || (!((ProgressDialog)sWeakProcessDialog.get()).isShowing()));
    ((ProgressDialog)sWeakProcessDialog.get()).dismiss();
    sWeakProcessDialog = null;
  }

  private static void showTips(Context paramContext, String paramString)
  {
    int i;
    String str;
    try
    {
      JSONObject localJSONObject = JsonUtil.parseJson(paramString);
      i = localJSONObject.getInt("type");
      str = localJSONObject.getString("msg");
      if (i == 0)
      {
        if (sToast == null)
          sToast = Toast.makeText(paramContext, str, 0);
        while (true)
        {
          sToast.show();
          return;
          sToast.setView(sToast.getView());
          sToast.setText(str);
          sToast.setDuration(0);
        }
      }
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      return;
    }
    if (i == 1)
    {
      if (sToast == null)
        sToast = Toast.makeText(paramContext, str, 1);
      while (true)
      {
        sToast.show();
        return;
        sToast.setView(sToast.getView());
        sToast.setText(str);
        sToast.setDuration(1);
      }
    }
  }

  public void callJs(String paramString1, String paramString2)
  {
    String str = "javascript:" + paramString1 + "(" + paramString2 + ");void(" + System.currentTimeMillis() + ");";
    this.mWebView.loadUrl(str);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    createViews();
    initViews();
  }

  protected void onStop()
  {
    if (!this.isBrowserStarted)
      this.mListener.onCancel();
    super.onStop();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.TDialog
 * JD-Core Version:    0.6.0
 */