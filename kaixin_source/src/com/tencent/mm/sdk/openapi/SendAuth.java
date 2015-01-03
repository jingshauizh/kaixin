package com.tencent.mm.sdk.openapi;

import android.os.Bundle;
import com.tencent.mm.sdk.platformtools.Log;

public final class SendAuth
{
  public static class Req extends BaseReq
  {
    public String scope;
    public String state;

    public Req()
    {
    }

    public Req(Bundle paramBundle)
    {
      fromBundle(paramBundle);
    }

    final boolean checkArgs()
    {
      if ((this.scope == null) || (this.scope.length() == 0) || (this.scope.length() > 1024))
      {
        Log.e("MicroMsg.SDK.SendAuth.Req", "checkArgs fail, scope is invalid");
        return false;
      }
      if ((this.state != null) && (this.state.length() > 1024))
      {
        Log.e("MicroMsg.SDK.SendAuth.Req", "checkArgs fail, state is invalid");
        return false;
      }
      return true;
    }

    public void fromBundle(Bundle paramBundle)
    {
      super.fromBundle(paramBundle);
      this.scope = paramBundle.getString("_wxapi_sendauth_req_scope");
      this.state = paramBundle.getString("_wxapi_sendauth_req_state");
    }

    public int getType()
    {
      return 1;
    }

    public void toBundle(Bundle paramBundle)
    {
      super.toBundle(paramBundle);
      paramBundle.putString("_wxapi_sendauth_req_scope", this.scope);
      paramBundle.putString("_wxapi_sendauth_req_state", this.state);
    }
  }

  public static class Resp extends BaseResp
  {
    public int expireDate;
    public String resultUrl;
    public String state;
    public String token;
    public String userName;

    public Resp()
    {
    }

    public Resp(Bundle paramBundle)
    {
      fromBundle(paramBundle);
    }

    final boolean checkArgs()
    {
      if ((this.state != null) && (this.state.length() > 1024))
      {
        Log.e("MicroMsg.SDK.SendAuth.Resp", "checkArgs fail, state is invalid");
        return false;
      }
      return true;
    }

    public void fromBundle(Bundle paramBundle)
    {
      super.fromBundle(paramBundle);
      this.userName = paramBundle.getString("_wxapi_sendauth_resp_userName");
      this.token = paramBundle.getString("_wxapi_sendauth_resp_token");
      this.expireDate = paramBundle.getInt("_wxapi_sendauth_resp_expireDate", 0);
      this.state = paramBundle.getString("_wxapi_sendauth_resp_state");
    }

    public int getType()
    {
      return 1;
    }

    public void toBundle(Bundle paramBundle)
    {
      super.toBundle(paramBundle);
      paramBundle.putString("_wxapi_sendauth_resp_userName", this.userName);
      paramBundle.putString("_wxapi_sendauth_resp_token", this.token);
      paramBundle.putInt("_wxapi_sendauth_resp_expireDate", this.expireDate);
      paramBundle.putString("_wxapi_sendauth_resp_state", this.state);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.openapi.SendAuth
 * JD-Core Version:    0.6.0
 */