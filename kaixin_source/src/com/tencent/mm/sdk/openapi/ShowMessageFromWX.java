package com.tencent.mm.sdk.openapi;

import android.os.Bundle;

public class ShowMessageFromWX
{
  public static class Req extends BaseReq
  {
    public WXMediaMessage message;

    public Req()
    {
    }

    public Req(Bundle paramBundle)
    {
      fromBundle(paramBundle);
    }

    final boolean checkArgs()
    {
      if (this.message == null)
        return false;
      return this.message.checkArgs();
    }

    public void fromBundle(Bundle paramBundle)
    {
      super.fromBundle(paramBundle);
      this.message = WXMediaMessage.Builder.fromBundle(paramBundle);
    }

    public int getType()
    {
      return 4;
    }

    public void toBundle(Bundle paramBundle)
    {
      Bundle localBundle = WXMediaMessage.Builder.toBundle(this.message);
      super.toBundle(localBundle);
      paramBundle.putAll(localBundle);
    }
  }

  public static class Resp extends BaseResp
  {
    public Resp()
    {
    }

    public Resp(Bundle paramBundle)
    {
      fromBundle(paramBundle);
    }

    final boolean checkArgs()
    {
      return true;
    }

    public int getType()
    {
      return 4;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.openapi.ShowMessageFromWX
 * JD-Core Version:    0.6.0
 */