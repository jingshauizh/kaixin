package com.tencent.mm.sdk.plugin;

import android.net.Uri;
import com.tencent.mm.sdk.storage.IAutoDBItem.MAutoDBInfo;

public class Profile extends BaseProfile
{
  public static final Uri CONTENT_URI = Uri.parse("content://com.tencent.mm.sdk.plugin.provider/profile");
  protected static IAutoDBItem.MAutoDBInfo bO;
  public static String[] columns = { "username", "bindqq", "bindmobile", "bindemail", "alias", "nickname", "signature", "province", "city", "weibo", "avatar" };

  static
  {
    bO = BaseProfile.initAutoDBInfo(Profile.class);
  }

  protected IAutoDBItem.MAutoDBInfo getDBInfo()
  {
    return bO;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.plugin.Profile
 * JD-Core Version:    0.6.0
 */