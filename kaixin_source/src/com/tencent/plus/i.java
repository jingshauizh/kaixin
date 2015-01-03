package com.tencent.plus;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import com.tencent.tauth.IRequestListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

class i
  implements IRequestListener
{
  i(ImageActivity paramImageActivity)
  {
  }

  private void a(int paramInt, String paramString)
  {
    ImageActivity.k(this.a).post(new o(this));
    if (paramString == null)
      if (paramInt == -2)
        ImageActivity.b(this.a, "网络有问题呢，检查一下网络再重试吧：）", 1);
    while (true)
    {
      this.a.a("10660", 0L);
      Log.i("ImageActivity", "setAvatar failure, errorCode: " + paramInt);
      return;
      ImageActivity.b(this.a, "设置出错了，请重新登录再尝试下呢：）", 1);
      continue;
      ImageActivity.b(this.a, paramString, 1);
    }
  }

  public void onComplete(JSONObject paramJSONObject, Object paramObject)
  {
    ImageActivity.k(this.a).post(new p(this));
    try
    {
      int j = paramJSONObject.getInt("ret");
      i = j;
      if (i == 0)
      {
        ImageActivity.b(this.a, "设置成功", 0);
        this.a.a("10658", 0L);
        ImageActivity localImageActivity = this.a;
        if ((ImageActivity.l(this.a) != null) && (!"".equals(ImageActivity.l(this.a))))
        {
          Intent localIntent = new Intent();
          localIntent.setClassName(localImageActivity, ImageActivity.l(this.a));
          if (localImageActivity.getPackageManager().resolveActivity(localIntent, 0) != null)
            localImageActivity.startActivity(localIntent);
        }
        ImageActivity.a(this.a, 0, paramJSONObject.toString(), null, null);
        ImageActivity.j(this.a);
        return;
      }
    }
    catch (JSONException localJSONException)
    {
      int i;
      while (true)
      {
        localJSONException.printStackTrace();
        i = -1;
      }
      a(i, null);
    }
  }

  public void onConnectTimeoutException(ConnectTimeoutException paramConnectTimeoutException, Object paramObject)
  {
    a(-2, null);
  }

  public void onHttpStatusException(HttpStatusException paramHttpStatusException, Object paramObject)
  {
    a(-2, null);
  }

  public void onIOException(IOException paramIOException, Object paramObject)
  {
    paramIOException.printStackTrace();
    a(-2, null);
  }

  public void onJSONException(JSONException paramJSONException, Object paramObject)
  {
    paramJSONException.printStackTrace();
    a(-1, null);
  }

  public void onMalformedURLException(MalformedURLException paramMalformedURLException, Object paramObject)
  {
    paramMalformedURLException.printStackTrace();
    a(0, null);
  }

  public void onNetworkUnavailableException(NetworkUnavailableException paramNetworkUnavailableException, Object paramObject)
  {
    a(-2, null);
  }

  public void onSocketTimeoutException(SocketTimeoutException paramSocketTimeoutException, Object paramObject)
  {
    a(-2, null);
  }

  public void onUnknowException(Exception paramException, Object paramObject)
  {
    a(1, null);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.plus.i
 * JD-Core Version:    0.6.0
 */