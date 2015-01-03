package com.tencent.open;

import android.os.Bundle;
import android.util.Log;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

public class SocialApiIml$CheckListener
  implements IUiListener
{
  SocialApiIml.DelayStartParam delayStartParam;

  public SocialApiIml$CheckListener(SocialApiIml paramSocialApiIml, SocialApiIml.DelayStartParam paramDelayStartParam)
  {
    this.delayStartParam = paramDelayStartParam;
  }

  public void onCancel()
  {
    SocialApiIml.access$000(this.this$0);
    VoiceHelper.clearImageCacheFile(this.delayStartParam.params.getString("image_date"));
    SocialApiIml.access$200(this.this$0, SocialApiIml.access$100(this.this$0), null, this.delayStartParam.action, this.delayStartParam.params, this.delayStartParam.h5Url, this.delayStartParam.listener);
  }

  public void onComplete(JSONObject paramJSONObject)
  {
    Log.d("TAG", "CheckListener--onComplete--response = " + paramJSONObject.toString());
    int i = 0;
    if (paramJSONObject != null);
    try
    {
      boolean bool = paramJSONObject.getBoolean("check_result");
      i = bool;
      SocialApiIml.access$000(this.this$0);
      if (i != 0)
      {
        Log.d("TAG", "CheckListener---delayStartParam.agentIntent = " + this.delayStartParam.agentIntent + " delayStartParam.action = " + this.delayStartParam.action);
        SocialApiIml.access$200(this.this$0, SocialApiIml.access$100(this.this$0), this.delayStartParam.agentIntent, this.delayStartParam.action, this.delayStartParam.params, this.delayStartParam.h5Url, this.delayStartParam.listener);
        return;
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
      {
        localJSONException.printStackTrace();
        i = 0;
      }
      VoiceHelper.clearImageCacheFile(this.delayStartParam.params.getString("image_date"));
      SocialApiIml.access$200(this.this$0, SocialApiIml.access$100(this.this$0), null, this.delayStartParam.action, this.delayStartParam.params, this.delayStartParam.h5Url, this.delayStartParam.listener);
    }
  }

  public void onError(UiError paramUiError)
  {
    SocialApiIml.access$000(this.this$0);
    VoiceHelper.clearImageCacheFile(this.delayStartParam.params.getString("image_date"));
    SocialApiIml.access$200(this.this$0, SocialApiIml.access$100(this.this$0), null, this.delayStartParam.action, this.delayStartParam.params, this.delayStartParam.h5Url, this.delayStartParam.listener);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.SocialApiIml.CheckListener
 * JD-Core Version:    0.6.0
 */