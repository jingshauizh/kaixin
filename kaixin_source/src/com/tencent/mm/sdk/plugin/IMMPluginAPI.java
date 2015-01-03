package com.tencent.mm.sdk.plugin;

import com.tencent.mm.sdk.channel.MMessage.CallBack;

public abstract interface IMMPluginAPI
{
  public abstract boolean appendNetStat(int paramInt1, int paramInt2, int paramInt3);

  public abstract void createMsgController(String paramString);

  public abstract void createQRCodeController(String paramString);

  public abstract void createQRCodeController(String paramString, MMessage.CallBack paramCallBack);

  public abstract void createQRCodeController(String paramString1, MMessage.CallBack paramCallBack, String paramString2);

  public abstract Profile getCurrentProfile(String paramString);

  public abstract String getPluginKey(String paramString);

  public abstract boolean installPlugin(String paramString);

  public abstract boolean isPluginInstalled(String paramString);

  public abstract void jumpToBindEmail(String paramString);

  public abstract void jumpToBindMobile(String paramString);

  public abstract void jumpToBindQQ(String paramString);

  public abstract void jumpToChattingUI(String paramString1, String paramString2);

  public abstract void jumpToSettingView(String paramString1, String paramString2);

  public abstract boolean registerAutoMsg(String paramString1, String paramString2);

  public abstract boolean registerPattern(String paramString1, MMessage.CallBack paramCallBack, String paramString2);

  public abstract boolean registerQRCodePattern(String paramString1, MMessage.CallBack paramCallBack, String paramString2);

  public abstract void release();

  public abstract boolean sendMsgNotify(String paramString1, String paramString2, int paramInt, String paramString3, Class<?> paramClass);

  public abstract boolean unregisterAutoMsg(String paramString1, String paramString2);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.plugin.IMMPluginAPI
 * JD-Core Version:    0.6.0
 */