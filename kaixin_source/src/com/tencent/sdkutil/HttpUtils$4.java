package com.tencent.sdkutil;

import android.content.Context;
import android.os.Bundle;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import com.tencent.record.debug.WnsClientLog;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.QQToken;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

final class HttpUtils$4 extends Thread
{
  public void run()
  {
    try
    {
      JSONObject localJSONObject = HttpUtils.access$100(this.val$token, this.val$ctx, this.val$graphPath, this.val$params, this.val$httpMethod);
      if (this.val$listener != null)
      {
        this.val$listener.onComplete(localJSONObject, this.val$object);
        WnsClientLog.getInstance().d("openSDK_LOG", "OpenApi onComplete");
      }
      return;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      while (this.val$listener == null);
      this.val$listener.onMalformedURLException(localMalformedURLException, this.val$object);
      WnsClientLog.getInstance().e("openSDK_LOG", "OpenApi requestAsync MalformedURLException", localMalformedURLException);
      return;
    }
    catch (ConnectTimeoutException localConnectTimeoutException)
    {
      while (this.val$listener == null);
      this.val$listener.onConnectTimeoutException(localConnectTimeoutException, this.val$object);
      WnsClientLog.getInstance().e("openSDK_LOG", "OpenApi requestAsync onConnectTimeoutException", localConnectTimeoutException);
      return;
    }
    catch (SocketTimeoutException localSocketTimeoutException)
    {
      while (this.val$listener == null);
      this.val$listener.onSocketTimeoutException(localSocketTimeoutException, this.val$object);
      WnsClientLog.getInstance().e("openSDK_LOG", "OpenApi requestAsync onSocketTimeoutException", localSocketTimeoutException);
      return;
    }
    catch (NetworkUnavailableException localNetworkUnavailableException)
    {
      while (this.val$listener == null);
      this.val$listener.onNetworkUnavailableException(localNetworkUnavailableException, this.val$object);
      WnsClientLog.getInstance().e("openSDK_LOG", "OpenApi requestAsync onNetworkUnavailableException", localNetworkUnavailableException);
      return;
    }
    catch (HttpStatusException localHttpStatusException)
    {
      while (this.val$listener == null);
      this.val$listener.onHttpStatusException(localHttpStatusException, this.val$object);
      WnsClientLog.getInstance().e("openSDK_LOG", "OpenApi requestAsync onHttpStatusException", localHttpStatusException);
      return;
    }
    catch (IOException localIOException)
    {
      while (this.val$listener == null);
      this.val$listener.onIOException(localIOException, this.val$object);
      WnsClientLog.getInstance().e("openSDK_LOG", "OpenApi requestAsync IOException", localIOException);
      return;
    }
    catch (JSONException localJSONException)
    {
      while (this.val$listener == null);
      this.val$listener.onJSONException(localJSONException, this.val$object);
      WnsClientLog.getInstance().e("openSDK_LOG", "OpenApi requestAsync JSONException", localJSONException);
      return;
    }
    catch (Exception localException)
    {
      while (this.val$listener == null);
      this.val$listener.onUnknowException(localException, this.val$object);
      WnsClientLog.getInstance().e("openSDK_LOG", "OpenApi requestAsync onUnknowException", localException);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.HttpUtils.4
 * JD-Core Version:    0.6.0
 */