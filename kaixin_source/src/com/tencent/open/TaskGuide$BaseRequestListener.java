package com.tencent.open;

import com.tencent.tauth.IRequestListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

abstract class TaskGuide$BaseRequestListener
  implements IRequestListener
{
  private TaskGuide$BaseRequestListener(TaskGuide paramTaskGuide)
  {
  }

  protected abstract void handleException(Exception paramException);

  public void onConnectTimeoutException(ConnectTimeoutException paramConnectTimeoutException, Object paramObject)
  {
    handleException(paramConnectTimeoutException);
  }

  public void onHttpStatusException(HttpStatusException paramHttpStatusException, Object paramObject)
  {
    handleException(paramHttpStatusException);
  }

  public void onIOException(IOException paramIOException, Object paramObject)
  {
    handleException(paramIOException);
  }

  public void onJSONException(JSONException paramJSONException, Object paramObject)
  {
    handleException(paramJSONException);
  }

  public void onMalformedURLException(MalformedURLException paramMalformedURLException, Object paramObject)
  {
    handleException(paramMalformedURLException);
  }

  public void onNetworkUnavailableException(NetworkUnavailableException paramNetworkUnavailableException, Object paramObject)
  {
    handleException(paramNetworkUnavailableException);
  }

  public void onSocketTimeoutException(SocketTimeoutException paramSocketTimeoutException, Object paramObject)
  {
    handleException(paramSocketTimeoutException);
  }

  public void onUnknowException(Exception paramException, Object paramObject)
  {
    handleException(paramException);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.TaskGuide.BaseRequestListener
 * JD-Core Version:    0.6.0
 */