package com.tencent.jsutil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@SuppressLint({"SetJavaScriptEnabled"})
public class JsBridge
{
  private static final String TAG = "JsBridge";
  public static JsBridge instance = null;
  Handler handler = new JsBridge.1(this);
  private List<String> jsNameList;
  private Context mContext;
  private String mJsPath;
  private int mLoadProgress = 0;
  private WebView mWebView;

  public JsBridge(Context paramContext, String paramString)
  {
    this.mContext = paramContext;
    this.mWebView = new WebView(this.mContext);
    this.mWebView.setWebViewClient(new JsBridge.MyWebViewClient(this));
    this.mWebView.setWebChromeClient(new WebChromeClient());
    this.mJsPath = paramString;
    this.jsNameList = new ArrayList();
    setJsEnabled();
  }

  public static JsBridge getInstance(Context paramContext, String paramString)
  {
    if (instance == null)
      monitorenter;
    try
    {
      if (instance == null)
        instance = new JsBridge(paramContext, paramString);
      return instance;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private String transSpec(String paramString)
  {
    return paramString.replace("\\", "\\\\").replace("'", "\\'").replace("\"", "\\\"");
  }

  public void WebViewReload()
  {
    this.mLoadProgress = 0;
    this.mWebView.reload();
  }

  public void clearCache()
  {
    instance = null;
    this.mWebView.clearCache(true);
    this.mWebView.clearFormData();
    this.mWebView.clearHistory();
    this.mWebView.removeAllViews();
    this.mWebView.destroy();
  }

  public void excuteMethod(String paramString)
  {
    Log.i("JsBridge", "excuteMethod(method)");
    new Thread(new JsBridge.3(this, paramString)).start();
  }

  public void executeMethod(String paramString, String[] paramArrayOfString)
  {
    Log.i("JsBridge", "excuteMethod(method,params)");
    new Thread(new JsBridge.2(this, paramString, paramArrayOfString)).start();
  }

  public List<String> getAllObjNames()
  {
    return new ArrayList(this.jsNameList);
  }

  public int getDownloadProgress()
  {
    return this.mLoadProgress;
  }

  public String getWebViewCurrentUrl()
  {
    return this.mWebView.getUrl();
  }

  public String getmJsPath()
  {
    return this.mJsPath;
  }

  public void jsReload()
  {
    this.mLoadProgress = 0;
    this.mWebView.loadUrl("javascript:window.location.reload( true )");
  }

  public void loadUrl(String paramString)
  {
    Log.d("loadurl", paramString);
    this.mWebView.loadUrl(paramString);
  }

  public void regiesterObj(Object paramObject, String paramString)
  {
    if (paramObject != null);
    try
    {
      WebView.class.getMethod("addJavascriptInterface", new Class[] { Object.class, String.class }).invoke(this.mWebView, new Object[] { paramObject, paramString });
      this.jsNameList.add(paramString);
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      while (true)
      {
        Log.e("JsBridge", localNoSuchMethodException.getMessage());
        localNoSuchMethodException.printStackTrace();
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
      {
        Log.e("JsBridge", localIllegalArgumentException.getMessage());
        localIllegalArgumentException.printStackTrace();
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
      {
        Log.e("JsBridge", localIllegalAccessException.getMessage());
        localIllegalAccessException.printStackTrace();
      }
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
      {
        Log.e("JsBridge", localInvocationTargetException.getMessage());
        localInvocationTargetException.printStackTrace();
      }
    }
    catch (Exception localException)
    {
      while (true)
        Log.e("JsBridge", localException.getMessage());
    }
  }

  public void registerObj(Object paramObject)
  {
    String str1;
    char c;
    String str2;
    if (paramObject != null)
    {
      str1 = paramObject.getClass().getSimpleName();
      c = Character.toLowerCase(str1.charAt(0));
      if (str1.length() != 1)
        break label133;
      str2 = "" + c;
    }
    while (true)
    {
      Log.i("JsBridge", "register:" + str2);
      try
      {
        WebView.class.getMethod("addJavascriptInterface", new Class[] { Object.class, String.class }).invoke(this.mWebView, new Object[] { paramObject, str2 });
        this.jsNameList.add(str2);
        return;
        label133: str2 = "" + c + str1.substring(1);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        while (true)
        {
          Log.e("JsBridge", localNoSuchMethodException.getMessage());
          localNoSuchMethodException.printStackTrace();
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        while (true)
        {
          Log.e("JsBridge", localIllegalArgumentException.getMessage());
          localIllegalArgumentException.printStackTrace();
        }
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        while (true)
        {
          Log.e("JsBridge", localIllegalAccessException.getMessage());
          localIllegalAccessException.printStackTrace();
        }
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        while (true)
        {
          Log.e("JsBridge", localInvocationTargetException.getMessage());
          localInvocationTargetException.printStackTrace();
        }
      }
      catch (Exception localException)
      {
        while (true)
          Log.e("JsBridge", localException.getMessage());
      }
    }
  }

  public void registerObjs(Map<Object, String> paramMap)
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    while (true)
    {
      Object localObject;
      String str;
      if (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localObject = localEntry.getKey();
        str = ((String)localEntry.getValue()).toString();
        if ((localObject != null) && (str != null));
      }
      else
      {
        return;
      }
      regiesterObj(localObject, str);
    }
  }

  public void setJsDisabled()
  {
    this.mWebView.getSettings().setJavaScriptEnabled(false);
  }

  public void setJsEnabled()
  {
    Log.i("JsBridge", "setJsEnabled");
    WebSettings localWebSettings = this.mWebView.getSettings();
    localWebSettings.setJavaScriptEnabled(true);
    localWebSettings.setDefaultTextEncodingName("utf-8");
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.JsBridge
 * JD-Core Version:    0.6.0
 */