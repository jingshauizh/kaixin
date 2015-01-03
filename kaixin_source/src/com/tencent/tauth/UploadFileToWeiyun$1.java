package com.tencent.tauth;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import org.json.JSONObject;

class UploadFileToWeiyun$1 extends Handler
{
  public void handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    case 1:
    default:
      return;
    case 0:
      JSONObject localJSONObject1 = (JSONObject)paramMessage.obj;
      try
      {
        int i = localJSONObject1.getInt("ret");
        if (i != 0)
        {
          UploadFileToWeiyun.access$000(this.this$0).onError("server error, ret = " + i + "");
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        UploadFileToWeiyun.access$000(this.this$0).onError("server return json error :" + localException.getMessage() + "");
        return;
      }
      JSONObject localJSONObject2 = localJSONObject1.getJSONObject("data");
      UploadFileToWeiyun.access$102(this.this$0, localJSONObject2.getString("file_id"));
      UploadFileToWeiyun.access$202(this.this$0, localJSONObject2.getString("csum"));
      UploadFileToWeiyun.access$302(this.this$0, DataConvert.string2bytes(UploadFileToWeiyun.access$200(this.this$0)));
      UploadFileToWeiyun.access$402(this.this$0, localJSONObject2.getInt("port"));
      UploadFileToWeiyun.access$502(this.this$0, localJSONObject2.getString("host"));
      UploadFileToWeiyun.access$600(this.this$0);
      return;
    case -1:
      long l4 = Thread.currentThread().getId();
      Log.i("weiyun_test", "uploadFileToWeiyun onError thread id = " + l4 + "");
      UploadFileToWeiyun.access$000(this.this$0).onError(paramMessage.obj.toString());
      return;
    case 2:
      long l3 = Thread.currentThread().getId();
      Log.i("weiyun_test", "uploadFileToWeiyun onUploadProgress thread id = " + l3 + "");
      UploadFileToWeiyun.access$000(this.this$0).onUploadProgress(Integer.parseInt((String)paramMessage.obj));
      return;
    case 3:
      long l2 = Thread.currentThread().getId();
      Log.i("weiyun_test", "uploadFileToWeiyun onUploadSuccess thread id = " + l2 + "");
      UploadFileToWeiyun.access$000(this.this$0).onUploadSuccess();
      return;
    case -2:
    }
    long l1 = Thread.currentThread().getId();
    Log.i("weiyun_test", "uploadFileToWeiyun onError thread id = " + l1 + "");
    UploadFileToWeiyun.access$000(this.this$0).onError((String)paramMessage.obj);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.UploadFileToWeiyun.1
 * JD-Core Version:    0.6.0
 */