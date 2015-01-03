package com.tencent.open;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

class SocialApiIml$1
  implements VoiceHelper.ImageCallback
{
  public void onFailed(String paramString)
  {
    this.val$params.remove("image_date");
    UiError localUiError = new UiError(-5, "图片读取失败，请检查该图片是否有效", "图片读取失败，请检查该图片是否有效");
    this.val$listener.onError(localUiError);
    SocialApiIml.access$000(this.this$0);
  }

  public void onSuccess(String paramString)
  {
    this.val$params.remove("image_date");
    if (!TextUtils.isEmpty(paramString))
      this.val$params.putString("image_date", paramString);
    SocialApiIml.access$300(this.this$0, this.val$activity, this.val$params, this.val$listener);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.SocialApiIml.1
 * JD-Core Version:    0.6.0
 */