package com.tencent.tauth;

import org.json.JSONObject;

public abstract interface IUiListener
{
  public abstract void onCancel();

  public abstract void onComplete(JSONObject paramJSONObject);

  public abstract void onError(UiError paramUiError);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.IUiListener
 * JD-Core Version:    0.6.0
 */