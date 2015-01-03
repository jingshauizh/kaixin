package com.tencent.sdkutil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.QQToken;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.json.JSONException;
import org.json.JSONObject;

public class PKDialog extends Dialog
  implements KeyboardDetectorRelativeLayout.IKeyboardChanged
{
  public static final String CANCEL_URI = "auth://cancel";
  public static final String CLOSE_URI = "auth://close";
  public static final String DOWNLOAD_URI = "download://";
  private static final int MSG_CANCEL = 2;
  private static final int MSG_COMPLETE = 1;
  private static final int MSG_ON_LOAD = 4;
  private static final int MSG_SHOW_PROCESS = 5;
  private static final int MSG_SHOW_TIPS = 3;
  public static final String REDIRECT_BROWSER_URI = "auth://browser";
  private static final String TAG = PKDialog.class.getName();
  private static final int WEBVIEW_HEIGHT = 185;
  static Toast sToast = null;
  private static WeakReference<Context> sWeakContext;
  private KeyboardDetectorRelativeLayout mFlMain;
  private Handler mHandler;
  private PKDialog.OnTimeListener mListener;
  private String mUrl;
  private WebView mWebView;
  private int mWebviewHeight;

  public PKDialog(Context paramContext, String paramString1, String paramString2, IUiListener paramIUiListener, QQToken paramQQToken)
  {
    super(paramContext, 16973840);
    sWeakContext = new WeakReference(paramContext);
    this.mUrl = paramString2;
    this.mListener = new PKDialog.OnTimeListener(paramContext, paramString1, paramString2, paramQQToken.getAppId(), paramIUiListener);
    this.mHandler = new PKDialog.THandler(this.mListener, paramContext.getMainLooper());
    this.mWebviewHeight = Math.round(185.0F * paramContext.getResources().getDisplayMetrics().density);
    Log.e(TAG, "density=" + paramContext.getResources().getDisplayMetrics().density + "; webviewHeight=" + this.mWebviewHeight);
  }

  private void createViews()
  {
    this.mFlMain = new KeyboardDetectorRelativeLayout((Context)sWeakContext.get());
    this.mFlMain.setBackgroundColor(1711276032);
    this.mFlMain.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
    this.mWebView = new WebView((Context)sWeakContext.get());
    this.mWebView.setBackgroundColor(0);
    this.mWebView.setBackgroundDrawable(null);
    if (Build.VERSION.SDK_INT >= 11);
    try
    {
      Class[] arrayOfClass = new Class[2];
      arrayOfClass[0] = Integer.TYPE;
      arrayOfClass[1] = Paint.class;
      Method localMethod = View.class.getMethod("setLayerType", arrayOfClass);
      WebView localWebView = this.mWebView;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(1);
      arrayOfObject[1] = new Paint();
      localMethod.invoke(localWebView, arrayOfObject);
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, this.mWebviewHeight);
      localLayoutParams.addRule(13, -1);
      this.mWebView.setLayoutParams(localLayoutParams);
      this.mFlMain.addView(this.mWebView);
      this.mFlMain.addKeyboardStateChangedListener(this);
      setContentView(this.mFlMain);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  @SuppressLint({"SetJavaScriptEnabled"})
  private void initViews()
  {
    this.mWebView.setVerticalScrollBarEnabled(false);
    this.mWebView.setHorizontalScrollBarEnabled(false);
    this.mWebView.setWebViewClient(new PKDialog.FbWebViewClient(this, null));
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
      arrayOfObject[0] = new PKDialog.JsListener(this, null);
      arrayOfObject[1] = "sdk_js_if";
      localMethod.invoke(localWebView, arrayOfObject);
      this.mWebView.clearView();
      this.mWebView.loadUrl(this.mUrl);
      this.mWebView.getSettings().setSavePassword(false);
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      while (true)
      {
        Log.e(TAG, localNoSuchMethodException.getMessage());
        localNoSuchMethodException.printStackTrace();
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
      {
        Log.e(TAG, localIllegalArgumentException.getMessage());
        localIllegalArgumentException.printStackTrace();
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
      {
        Log.e(TAG, localIllegalAccessException.getMessage());
        localIllegalAccessException.printStackTrace();
      }
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
      {
        Log.e(TAG, localInvocationTargetException.getMessage());
        localInvocationTargetException.printStackTrace();
      }
    }
    catch (Exception localException)
    {
      while (true)
        Log.e(TAG, localException.getMessage());
    }
  }

  private static void showProcessDialog(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (paramString == null));
    while (true)
    {
      return;
      try
      {
        int i = JsonUtil.parseJson(paramString).getInt("action");
        if (i == 1)
          return;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
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
    String str = "javascript:" + paramString1 + "(" + paramString2 + ")";
    this.mWebView.loadUrl(str);
  }

  public void onBackPressed()
  {
    super.onBackPressed();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    getWindow().setSoftInputMode(16);
    getWindow().setSoftInputMode(1);
    createViews();
    initViews();
  }

  public void onKeyboardHidden()
  {
    this.mWebView.getLayoutParams().height = this.mWebviewHeight;
    Log.e(TAG, "keyboard hide");
  }

  public void onKeyboardShown(int paramInt)
  {
    if ((sWeakContext != null) && (sWeakContext.get() != null))
      if ((paramInt >= this.mWebviewHeight) || (2 != ((Context)sWeakContext.get()).getResources().getConfiguration().orientation))
        break label67;
    label67: for (this.mWebView.getLayoutParams().height = paramInt; ; this.mWebView.getLayoutParams().height = this.mWebviewHeight)
    {
      Log.e(TAG, "keyboard show");
      return;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.PKDialog
 * JD-Core Version:    0.6.0
 */