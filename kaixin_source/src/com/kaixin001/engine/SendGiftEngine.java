package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;

public class SendGiftEngine extends KXEngine
{
  private static final String LOGTAG = "SendGiftEngine";
  public static final int SEND_FAILED = -2001;
  public static final int SEND_FAILL = -6;
  public static final int SEND_SUC = 1;
  private static SendGiftEngine instance;
  protected String strErrMsg = "";

  public static SendGiftEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new SendGiftEngine();
      SendGiftEngine localSendGiftEngine = instance;
      return localSendGiftEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int parseRecordJSON(Context paramContext, String paramString)
    throws SecurityErrorException
  {
    if (super.parseJSON(paramContext, paramString) == null)
      return 0;
    if (this.ret == 1)
      return this.ret;
    return this.ret;
  }

  public int sendGift(Context paramContext, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makePostGiftRequest(User.getInstance().getUID(), paramString1, paramString2, paramString3, paramBoolean1, paramBoolean2, paramInt);
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    String str2;
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      if (TextUtils.isEmpty(str2))
        return 0;
    }
    catch (Exception localException)
    {
      while (true)
      {
        KXLog.e("SendGiftEngine", "sendGift error", localException);
        str2 = null;
      }
    }
    return parseRecordJSON(paramContext, str2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.SendGiftEngine
 * JD-Core Version:    0.6.0
 */