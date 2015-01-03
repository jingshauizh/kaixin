package com.tencent.tauth;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import java.io.IOException;
import java.net.MalformedURLException;
import org.json.JSONException;
import org.json.JSONObject;

class UploadFileToWeiyun$2 extends Thread
{
  public void run()
  {
    Uri localUri = Uri.parse(UploadFileToWeiyun.access$700(this.this$0));
    String str = SystemClock.elapsedRealtime() + "__" + localUri.getLastPathSegment();
    Bundle localBundle = new Bundle();
    localBundle.putString("access_token", UploadFileToWeiyun.access$800(this.this$0).getAccessToken() + "");
    localBundle.putString("oauth_consumer_key", "222222");
    localBundle.putString("openid", UploadFileToWeiyun.access$800(this.this$0).getOpenId() + "");
    localBundle.putString("format", "json");
    localBundle.putString("sha", this.this$0.str_file_key);
    localBundle.putString("md5", UploadFileToWeiyun.access$900(this.this$0));
    localBundle.putString("size", UploadFileToWeiyun.access$1000(this.this$0) + "");
    localBundle.putString("name", str);
    localBundle.putString("upload_type", "control");
    Log.i("weiyun_test", "uploadFileToWeiyun getUploadPermission parames = " + localBundle.toString());
    try
    {
      JSONObject localJSONObject = UploadFileToWeiyun.access$800(this.this$0).request(UploadFileToWeiyun.access$1100(this.this$0), localBundle, "GET");
      Message localMessage6 = UploadFileToWeiyun.access$1200(this.this$0).obtainMessage();
      localMessage6.what = 0;
      localMessage6.obj = localJSONObject;
      UploadFileToWeiyun.access$1200(this.this$0).sendMessage(localMessage6);
      return;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
      Message localMessage5 = UploadFileToWeiyun.access$1200(this.this$0).obtainMessage();
      localMessage5.what = -1;
      localMessage5.obj = "getUploadPermission MalformedURLException";
      UploadFileToWeiyun.access$1200(this.this$0).sendMessage(localMessage5);
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      Message localMessage4 = UploadFileToWeiyun.access$1200(this.this$0).obtainMessage();
      localMessage4.what = -1;
      localMessage4.obj = "getUploadPermission IOException";
      UploadFileToWeiyun.access$1200(this.this$0).sendMessage(localMessage4);
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      Message localMessage3 = UploadFileToWeiyun.access$1200(this.this$0).obtainMessage();
      localMessage3.what = -1;
      localMessage3.obj = "getUploadPermission JSONException";
      UploadFileToWeiyun.access$1200(this.this$0).sendMessage(localMessage3);
      return;
    }
    catch (NetworkUnavailableException localNetworkUnavailableException)
    {
      localNetworkUnavailableException.printStackTrace();
      Message localMessage2 = UploadFileToWeiyun.access$1200(this.this$0).obtainMessage();
      localMessage2.what = -1;
      localMessage2.obj = "getUploadPermission NetworkUnavailableException";
      UploadFileToWeiyun.access$1200(this.this$0).sendMessage(localMessage2);
      return;
    }
    catch (HttpStatusException localHttpStatusException)
    {
      localHttpStatusException.printStackTrace();
      Message localMessage1 = UploadFileToWeiyun.access$1200(this.this$0).obtainMessage();
      localMessage1.what = -1;
      localMessage1.obj = "getUploadPermission HttpStatusException";
      UploadFileToWeiyun.access$1200(this.this$0).sendMessage(localMessage1);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.UploadFileToWeiyun.2
 * JD-Core Version:    0.6.0
 */