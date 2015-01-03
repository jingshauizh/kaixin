package com.kaixin001.businesslogic;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.Toast;
import com.kaixin001.engine.ApplistEngine;
import com.kaixin001.engine.NavigatorApplistEngine.ThirdAppItem;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.MoreItemDetailFragment;
import com.kaixin001.menu.MenuItem;
import com.kaixin001.model.ApplistModel;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.KXLog;
import java.util.List;

public class ThirdPartyAppUtil
{
  static List<PackageInfo> installedApplicationList;
  Context ctx;
  private GetThirdAppTokenTask mGetThirdAppTokenTask;

  public ThirdPartyAppUtil(Context paramContext)
  {
    this.ctx = paramContext;
  }

  private boolean checkApkExist(Context paramContext, String paramString)
  {
    if ((installedApplicationList == null) || (installedApplicationList.isEmpty()) || (TextUtils.isEmpty(paramString)))
      return false;
    int i = installedApplicationList.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return false;
      if (paramString.equals(((PackageInfo)installedApplicationList.get(j)).packageName))
        return true;
    }
  }

  private void dealOthers(BaseFragment paramBaseFragment, NavigatorApplistEngine.ThirdAppItem paramThirdAppItem)
  {
    FragmentActivity localFragmentActivity = paramBaseFragment.getActivity();
    String str = paramThirdAppItem.getPakageName();
    if (checkApkExist(this.ctx, str))
    {
      if (!TextUtils.isEmpty(paramThirdAppItem.getAppId()))
      {
        String[] arrayOfString = new String[3];
        arrayOfString[0] = paramThirdAppItem.getAppId();
        arrayOfString[1] = str;
        arrayOfString[2] = paramThirdAppItem.getAppEntry();
        this.mGetThirdAppTokenTask = new GetThirdAppTokenTask(localFragmentActivity);
        this.mGetThirdAppTokenTask.execute(arrayOfString);
        return;
      }
      try
      {
        Intent localIntent2 = new Intent();
        localIntent2.setComponent(new ComponentName(str, paramThirdAppItem.getAppEntry()));
        localFragmentActivity.startActivity(localIntent2);
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("onClickAppItem", "Start third APP", localException);
        return;
      }
    }
    Intent localIntent1 = new Intent(this.ctx, MoreItemDetailFragment.class);
    localIntent1.putExtra("label", paramThirdAppItem.getName());
    localIntent1.putExtra("link", paramThirdAppItem.getUrl());
    AnimationUtil.startFragment(paramBaseFragment, localIntent1, 1);
  }

  public static boolean isApkExist(Context paramContext, String paramString)
  {
    List localList = paramContext.getPackageManager().getInstalledPackages(0);
    if ((localList == null) || (localList.isEmpty()) || (TextUtils.isEmpty(paramString)))
      return false;
    int i = localList.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return false;
      if (paramString.equals(((PackageInfo)localList.get(j)).packageName))
        return true;
    }
  }

  public void onClickAppItem(BaseFragment paramBaseFragment, NavigatorApplistEngine.ThirdAppItem paramThirdAppItem)
  {
    if (paramThirdAppItem.getPakageName().equals("html5"))
    {
      Intent localIntent = new Intent(this.ctx, MoreItemDetailFragment.class);
      localIntent.putExtra("label", paramThirdAppItem.getName());
      localIntent.putExtra("link", paramThirdAppItem.getDownloadUrl());
      MenuItem.startRightFragment(paramBaseFragment, localIntent);
      return;
    }
    new AsyncTask(paramBaseFragment, paramThirdAppItem)
    {
      protected Void doInBackground(Void[] paramArrayOfVoid)
      {
        ThirdPartyAppUtil.installedApplicationList = ThirdPartyAppUtil.this.ctx.getPackageManager().getInstalledPackages(0);
        return null;
      }

      protected void onPostExecute(Void paramVoid)
      {
        ThirdPartyAppUtil.this.dealOthers(this.val$fragment, this.val$item);
      }
    }
    .execute(new Void[0]);
  }

  private class GetThirdAppTokenTask extends AsyncTask<String, Void, Boolean>
  {
    private String appEntry;
    private String packageName;
    final Context srcActivity;

    public GetThirdAppTokenTask(Context arg2)
    {
      Object localObject;
      this.srcActivity = localObject;
    }

    protected Boolean doInBackground(String[] paramArrayOfString)
    {
      try
      {
        String str = paramArrayOfString[0];
        this.packageName = paramArrayOfString[1];
        this.appEntry = paramArrayOfString[2];
        Boolean localBoolean = Boolean.valueOf(ApplistEngine.getInstance().getThirdAppToken(ThirdPartyAppUtil.this.ctx, str));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      if (paramBoolean == null)
        return;
      try
      {
        if (paramBoolean.booleanValue())
        {
          Intent localIntent = new Intent();
          localIntent.putExtra("oauth_token", ApplistModel.getInstance().getOauthToken());
          localIntent.putExtra("oauth_token_secret", ApplistModel.getInstance().getOauthTokenSecret());
          localIntent.putExtra("access_token", ApplistModel.getInstance().getOauthTokenSecret());
          localIntent.putExtra("expires_in", ApplistModel.getInstance().getOauthTokenSecret());
          localIntent.putExtra("refresh_token", ApplistModel.getInstance().getOauthTokenSecret());
          localIntent.setComponent(new ComponentName(this.packageName, this.appEntry));
          this.srcActivity.startActivity(localIntent);
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("onClickAppItem", "onPostExecute", localException);
        Toast.makeText(ThirdPartyAppUtil.this.ctx, 2131427727, 0).show();
        return;
      }
      Toast.makeText(ThirdPartyAppUtil.this.ctx, 2131427727, 0).show();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.businesslogic.ThirdPartyAppUtil
 * JD-Core Version:    0.6.0
 */