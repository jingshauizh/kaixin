package com.tencent.mm.sdk.plugin;

import android.content.Context;
import android.content.Intent;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX.Req;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXAppExtendObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.platformtools.Util;

public class MMPluginMsg
{
  public static final String ACTION_AUTO_MSG = "ACTION_AUTO_MSG";
  public static final String RECV_MSG = "recv_msg";
  public static final String RECV_PKG = "recv_pkg";
  public static final String RECV_THUMB = "recv_thumb";
  public static final String SEND_ERR_CODE = "send_err_code";
  public static final String SEND_ERR_TYPE = "send_err_type";
  public static final String SEND_ID = "send_id";
  public static final String TYPE = "type";
  public static final int TYPE_RECV_MSG = 2;
  public static final int TYPE_SEND_RET = 1;
  public String content;
  public long msgClientId;

  public static MMPluginMsg WXAppExtentObjectToPluginMsg(WXAppExtendObject paramWXAppExtendObject)
  {
    if (paramWXAppExtendObject == null);
    MMPluginMsg localMMPluginMsg;
    do
    {
      do
      {
        return null;
        localMMPluginMsg = new MMPluginMsg();
        localMMPluginMsg.msgClientId = Util.getLong(paramWXAppExtendObject.extInfo, -1L);
      }
      while ((localMMPluginMsg.msgClientId == -1L) || (Util.isNullOrNil(paramWXAppExtendObject.fileData)));
      localMMPluginMsg.content = new String(paramWXAppExtendObject.fileData);
    }
    while (Util.isNullOrNil(localMMPluginMsg.content));
    return localMMPluginMsg;
  }

  public static WXAppExtendObject pluginMsgToWXAppExtendObject(MMPluginMsg paramMMPluginMsg)
  {
    if (paramMMPluginMsg == null)
      return null;
    WXAppExtendObject localWXAppExtendObject = new WXAppExtendObject();
    localWXAppExtendObject.extInfo = paramMMPluginMsg.msgClientId;
    localWXAppExtendObject.fileData = paramMMPluginMsg.content.getBytes();
    return localWXAppExtendObject;
  }

  public static long sendMessage(Context paramContext, String paramString)
  {
    if (Util.isNullOrNil(paramString))
      return -1L;
    MMPluginMsg localMMPluginMsg = new MMPluginMsg();
    localMMPluginMsg.msgClientId = Util.nowMilliSecond();
    localMMPluginMsg.content = paramString;
    WXAppExtendObject localWXAppExtendObject = pluginMsgToWXAppExtendObject(localMMPluginMsg);
    WXMediaMessage localWXMediaMessage = new WXMediaMessage();
    localWXMediaMessage.mediaObject = localWXAppExtendObject;
    localWXMediaMessage.description = "";
    IWXAPI localIWXAPI = WXAPIFactory.createWXAPI(paramContext, null);
    if (localIWXAPI == null)
      return -2L;
    SendMessageToWX.Req localReq = new SendMessageToWX.Req();
    localReq.transaction = ("appdata" + localMMPluginMsg.msgClientId);
    localReq.message = localWXMediaMessage;
    if (!localIWXAPI.sendReq(localReq))
      return -3L;
    return localMMPluginMsg.msgClientId;
  }

  public static class ReceiverHelper
  {
    Intent intent;
    int type;

    public ReceiverHelper(Intent paramIntent)
    {
      this.type = paramIntent.getIntExtra("type", 0);
      this.intent = paramIntent;
    }

    public String getMsgContent()
    {
      if (isRecvNew())
        return this.intent.getStringExtra("recv_msg");
      return null;
    }

    public Integer getSendErrCode()
    {
      if (isSendRet())
        return Integer.valueOf(this.intent.getIntExtra("send_err_code", 0));
      return null;
    }

    public Integer getSendErrType()
    {
      if (isSendRet())
        return Integer.valueOf(this.intent.getIntExtra("send_err_type", 0));
      return null;
    }

    public Long getSendMsgId()
    {
      if (isSendRet())
        return Long.valueOf(this.intent.getLongExtra("send_id", 0L));
      return null;
    }

    public boolean isRecvNew()
    {
      return this.type == 2;
    }

    public boolean isSendRet()
    {
      return this.type == 1;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.plugin.MMPluginMsg
 * JD-Core Version:    0.6.0
 */