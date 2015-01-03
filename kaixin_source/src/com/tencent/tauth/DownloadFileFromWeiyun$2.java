package com.tencent.tauth;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import java.io.IOException;
import java.net.MalformedURLException;
import org.json.JSONException;
import org.json.JSONObject;

class DownloadFileFromWeiyun$2 extends Thread
{
  public void run()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("oauth_consumer_key", "222222");
    localBundle.putString("file_id", DownloadFileFromWeiyun.access$1100(this.this$0));
    localBundle.putString("access_token", DownloadFileFromWeiyun.access$1200(this.this$0).getAccessToken() + "");
    localBundle.putString("openid", DownloadFileFromWeiyun.access$1200(this.this$0).getOpenId() + "");
    localBundle.putString("format", "json");
    if ((DownloadFileFromWeiyun.access$600(this.this$0) != null) && (DownloadFileFromWeiyun.access$600(this.this$0).length() > 0))
      localBundle.putString("thumb", DownloadFileFromWeiyun.access$600(this.this$0) + "");
    try
    {
      JSONObject localJSONObject = DownloadFileFromWeiyun.access$1200(this.this$0).request(DownloadFileFromWeiyun.access$1300(this.this$0), localBundle, "GET");
      Message localMessage6 = DownloadFileFromWeiyun.access$1400(this.this$0).obtainMessage();
      localMessage6.what = 0;
      localMessage6.obj = localJSONObject;
      DownloadFileFromWeiyun.access$1400(this.this$0).sendMessage(localMessage6);
      return;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
      Message localMessage5 = DownloadFileFromWeiyun.access$1400(this.this$0).obtainMessage();
      localMessage5.what = 3;
      localMessage5.obj = "getUploadPermission MalformedURLException";
      DownloadFileFromWeiyun.access$1400(this.this$0).sendMessage(localMessage5);
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      Message localMessage4 = DownloadFileFromWeiyun.access$1400(this.this$0).obtainMessage();
      localMessage4.what = 3;
      localMessage4.obj = "getUploadPermission IOException";
      DownloadFileFromWeiyun.access$1400(this.this$0).sendMessage(localMessage4);
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      Message localMessage3 = DownloadFileFromWeiyun.access$1400(this.this$0).obtainMessage();
      localMessage3.what = 3;
      localMessage3.obj = "getUploadPermission JSONException";
      DownloadFileFromWeiyun.access$1400(this.this$0).sendMessage(localMessage3);
      return;
    }
    catch (NetworkUnavailableException localNetworkUnavailableException)
    {
      localNetworkUnavailableException.printStackTrace();
      Message localMessage2 = DownloadFileFromWeiyun.access$1400(this.this$0).obtainMessage();
      localMessage2.what = 3;
      localMessage2.obj = "getUploadPermission NetworkUnavailableException";
      DownloadFileFromWeiyun.access$1400(this.this$0).sendMessage(localMessage2);
      return;
    }
    catch (HttpStatusException localHttpStatusException)
    {
      localHttpStatusException.printStackTrace();
      Message localMessage1 = DownloadFileFromWeiyun.access$1400(this.this$0).obtainMessage();
      localMessage1.what = 3;
      localMessage1.obj = "getUploadPermission HttpStatusException";
      DownloadFileFromWeiyun.access$1400(this.this$0).sendMessage(localMessage1);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.DownloadFileFromWeiyun.2
 * JD-Core Version:    0.6.0
 */