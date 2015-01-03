package com.tencent.tauth;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import org.json.JSONObject;

class DownloadFileFromWeiyun$1 extends Handler
{
  public void handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
      return;
    case 0:
      JSONObject localJSONObject1 = (JSONObject)paramMessage.obj;
      while (true)
      {
        try
        {
          int j = localJSONObject1.getInt("ret");
          if (j != 0)
          {
            DownloadFileFromWeiyun.access$000(this.this$0).onError("server error, ret = " + j + "");
            return;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          DownloadFileFromWeiyun.access$000(this.this$0).onDownloadStart();
          DownloadFileFromWeiyun.access$800(this.this$0);
          return;
        }
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject("data");
        DownloadFileFromWeiyun.access$102(this.this$0, localJSONObject2.getString("dl_encrypt_url"));
        DownloadFileFromWeiyun.access$202(this.this$0, localJSONObject2.getString("dl_cookie_name"));
        DownloadFileFromWeiyun.access$302(this.this$0, localJSONObject2.getString("dl_cookie_value"));
        DownloadFileFromWeiyun.access$402(this.this$0, localJSONObject2.getInt("dl_svr_port"));
        DownloadFileFromWeiyun.access$502(this.this$0, localJSONObject2.getString("dl_svr_host"));
        if ((DownloadFileFromWeiyun.access$600(this.this$0) == null) || (DownloadFileFromWeiyun.access$600(this.this$0).length() <= 0))
          continue;
        DownloadFileFromWeiyun.access$702(this.this$0, localJSONObject2.getString("dl_thumb_size"));
      }
    case 1:
      DownloadFileFromWeiyun.access$000(this.this$0).onDownloadSuccess(DownloadFileFromWeiyun.access$900(this.this$0) + '/' + DownloadFileFromWeiyun.access$1000(this.this$0));
      return;
    case 2:
      int i = Integer.parseInt((String)paramMessage.obj);
      DownloadFileFromWeiyun.access$000(this.this$0).onDownloadProgress(i);
      return;
    case 3:
    }
    DownloadFileFromWeiyun.access$000(this.this$0).onError((String)paramMessage.obj);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.DownloadFileFromWeiyun.1
 * JD-Core Version:    0.6.0
 */