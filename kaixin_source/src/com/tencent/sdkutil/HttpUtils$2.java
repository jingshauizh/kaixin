package com.tencent.sdkutil;

import com.tencent.jsutil.JsBridge;
import com.tencent.record.debug.WnsClientLog;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

class HttpUtils$2
  implements AsynLoadImg.AsynLoadImgBack
{
  public void batchSaved(int paramInt, ArrayList<String> paramArrayList)
  {
    JSONObject localJSONObject = this.val$MessageJson;
    String str2;
    if (paramInt == 0)
      str2 = "";
    for (int i = 0; ; i++)
      try
      {
        if (i < paramArrayList.size())
        {
          if (i == -1 + paramArrayList.size())
            str2 = str2 + (String)paramArrayList.get(i);
          else
            str2 = str2 + (String)paramArrayList.get(i) + ";";
        }
        else
        {
          localJSONObject.put("ImageLocalPath", str2);
          localJSONObject.put("compress_ret", "compress_success");
          while (true)
          {
            localJSONObject.put("checkedflag", "checked");
            JsBridge localJsBridge = JsBridge.getInstance(HttpUtils.access$000(this.this$0), "file:///android_asset/tencent/js/tencent.html");
            String str1 = this.val$JScallback;
            String[] arrayOfString = new String[1];
            arrayOfString[0] = localJSONObject.toString();
            localJsBridge.executeMethod(str1, arrayOfString);
            return;
            localJSONObject.put("compress_ret", "compress_fail");
          }
        }
      }
      catch (JSONException localJSONException)
      {
        WnsClientLog.getInstance().d("HttpUtils", localJSONException.toString());
        return;
      }
  }

  public void saved(int paramInt, String paramString)
  {
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.sdkutil.HttpUtils.2
 * JD-Core Version:    0.6.0
 */