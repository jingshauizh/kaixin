package com.tencent.sdkutil;

import com.tencent.jsutil.JsBridge;
import com.tencent.record.debug.WnsClientLog;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

class HttpUtils$1
  implements AsynLoadImg.AsynLoadImgBack
{
  public void batchSaved(int paramInt, ArrayList<String> paramArrayList)
  {
  }

  public void saved(int paramInt, String paramString)
  {
    JSONObject localJSONObject = this.val$MessageJson;
    if (paramInt == 0);
    try
    {
      localJSONObject.put("ImageLocalPath", paramString);
      localJSONObject.put("download_ret", "download_success");
      while (true)
      {
        localJSONObject.put("checkedflag", "checked");
        JsBridge localJsBridge = JsBridge.getInstance(HttpUtils.access$000(this.this$0), "file:///android_asset/tencent/js/tencent.html");
        String str = this.val$JScallback;
        String[] arrayOfString = new String[1];
        arrayOfString[0] = localJSONObject.toString();
        localJsBridge.executeMethod(str, arrayOfString);
        return;
        localJSONObject.put("download_ret", "download-fail");
      }
    }
    catch (JSONException localJSONException)
    {
      WnsClientLog.getInstance().d("HttpUtils", localJSONException.toString());
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.HttpUtils.1
 * JD-Core Version:    0.6.0
 */