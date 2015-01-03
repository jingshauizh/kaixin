package com.tencent.jsutil;

import android.webkit.JavascriptInterface;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import com.tencent.sdkutil.JsonUtil;
import com.tencent.sdkutil.TemporaryStorage;
import com.tencent.tauth.IRequestListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

public class PackIRequestListener
  implements JsIRequestListener
{
  private IRequestListener mIRequestListener;

  @JavascriptInterface
  public void onComplete(String paramString1, String paramString2)
  {
    try
    {
      JSONObject localJSONObject = JsonUtil.parseJson((String)TemporaryStorage.get(paramString1));
      this.mIRequestListener = ((IRequestListener)TemporaryStorage.get(paramString2));
      if (this.mIRequestListener != null)
        this.mIRequestListener.onComplete(localJSONObject, null);
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
  }

  @JavascriptInterface
  public void onConnectTimeoutException(String paramString1, String paramString2)
  {
    this.mIRequestListener = ((IRequestListener)TemporaryStorage.get(paramString2));
    if (this.mIRequestListener != null)
    {
      ConnectTimeoutException localConnectTimeoutException = new ConnectTimeoutException(paramString1);
      this.mIRequestListener.onConnectTimeoutException(localConnectTimeoutException, null);
    }
  }

  @JavascriptInterface
  public void onHttpStatusException(String paramString1, String paramString2)
  {
    this.mIRequestListener = ((IRequestListener)TemporaryStorage.get(paramString2));
    if (this.mIRequestListener != null)
    {
      HttpStatusException localHttpStatusException = new HttpStatusException(paramString1);
      this.mIRequestListener.onHttpStatusException(localHttpStatusException, null);
    }
  }

  @JavascriptInterface
  public void onIOException(String paramString1, String paramString2)
  {
    this.mIRequestListener = ((IRequestListener)TemporaryStorage.get(paramString2));
    if (this.mIRequestListener != null)
    {
      IOException localIOException = new IOException(paramString1);
      this.mIRequestListener.onIOException(localIOException, null);
    }
  }

  @JavascriptInterface
  public void onJSONException(String paramString1, String paramString2)
  {
    this.mIRequestListener = ((IRequestListener)TemporaryStorage.get(paramString2));
    if (this.mIRequestListener != null)
    {
      JSONException localJSONException = new JSONException(paramString1);
      this.mIRequestListener.onJSONException(localJSONException, null);
    }
  }

  @JavascriptInterface
  public void onMalformedURLException(String paramString1, String paramString2)
  {
    this.mIRequestListener = ((IRequestListener)TemporaryStorage.get(paramString2));
    if (this.mIRequestListener != null)
    {
      MalformedURLException localMalformedURLException = new MalformedURLException(paramString1);
      this.mIRequestListener.onMalformedURLException(localMalformedURLException, null);
    }
  }

  @JavascriptInterface
  public void onNetworkUnavailableException(String paramString1, String paramString2)
  {
    this.mIRequestListener = ((IRequestListener)TemporaryStorage.get(paramString2));
    if (this.mIRequestListener != null)
    {
      NetworkUnavailableException localNetworkUnavailableException = new NetworkUnavailableException(paramString1);
      this.mIRequestListener.onNetworkUnavailableException(localNetworkUnavailableException, null);
    }
  }

  @JavascriptInterface
  public void onSocketTimeoutException(String paramString1, String paramString2)
  {
    this.mIRequestListener = ((IRequestListener)TemporaryStorage.get(paramString2));
    if (this.mIRequestListener != null)
    {
      SocketTimeoutException localSocketTimeoutException = new SocketTimeoutException(paramString1);
      this.mIRequestListener.onSocketTimeoutException(localSocketTimeoutException, null);
    }
  }

  @JavascriptInterface
  public void onUnknowException(String paramString1, String paramString2)
  {
    this.mIRequestListener = ((IRequestListener)TemporaryStorage.get(paramString2));
    if (this.mIRequestListener != null)
    {
      Exception localException = new Exception(paramString1);
      this.mIRequestListener.onUnknowException(localException, null);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.jsutil.PackIRequestListener
 * JD-Core Version:    0.6.0
 */