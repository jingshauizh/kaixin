package com.tencent.open;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.tencent.sdkutil.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.QQToken;

public class SocialApi
{
  private SocialApiIml mSoApiIml;

  public SocialApi(Context paramContext, QQToken paramQQToken)
  {
    this.mSoApiIml = new SocialApiIml(paramContext, paramQQToken);
  }

  public void ask(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.mSoApiIml.ask(paramActivity, paramBundle, paramIUiListener);
  }

  public void brag(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.mSoApiIml.brag(paramActivity, paramBundle, paramIUiListener);
  }

  public void challenge(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.mSoApiIml.challenge(paramActivity, paramBundle, paramIUiListener);
  }

  public boolean checkVoiceApi(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    paramBundle.putString("version", Util.getAppVersion(paramActivity));
    this.mSoApiIml.grade(paramActivity, paramBundle, paramIUiListener);
    return true;
  }

  public void gift(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.mSoApiIml.gift(paramActivity, paramBundle, paramIUiListener);
  }

  public void grade(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    paramBundle.putString("version", Util.getAppVersion(paramActivity));
    this.mSoApiIml.grade(paramActivity, paramBundle, paramIUiListener);
  }

  public void invite(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.mSoApiIml.invite(paramActivity, paramBundle, paramIUiListener);
  }

  public void story(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    this.mSoApiIml.story(paramActivity, paramBundle, paramIUiListener);
  }

  public void voice(Activity paramActivity, Bundle paramBundle, IUiListener paramIUiListener)
  {
    paramBundle.putString("version", Util.getAppVersion(paramActivity));
    this.mSoApiIml.voice(paramActivity, paramBundle, paramIUiListener);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.open.SocialApi
 * JD-Core Version:    0.6.0
 */