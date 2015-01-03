package com.kaixin001.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import com.kaixin001.businesslogic.ThirdPartyAppUtil;
import com.kaixin001.model.UpgradeModel;
import com.kaixin001.service.UpgradeDownloadService;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UpgradeDownloadFile;
import com.kaixin001.util.UserHabitUtils;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpgradeDialogActivity extends KXActivity
{
  public static final int DIALOG_TYPE_APP_DOWNLOAD = 5;
  public static final int DIALOG_TYPE_CANCEL = 2;
  public static final int DIALOG_TYPE_CONTINUE = 4;
  public static final int DIALOG_TYPE_DOWNLOAD = 1;
  public static final int DIALOG_TYPE_INSTALL = 3;
  public static final String KEY_DIALOG_TYPE = "dialogtype";
  public static final String KEY_STRING_MESSAGE = "stringMessage";
  private static final String TAG = "DialogActivity";
  private int mDialogType;

  private void askForAppDownload(Bundle paramBundle)
  {
    String str = paramBundle.getString("appname");
    DialogUtil.showAppDlg(this, str, paramBundle.getString("appauthor"), paramBundle.getString("appdetail"), paramBundle.getString("applogourl"), new DialogInterface.OnClickListener(paramBundle.getString("downloadurl"), str)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if (Patterns.WEB_URL.matcher(this.val$downloadUrl).matches())
        {
          UserHabitUtils.getInstance(UpgradeDialogActivity.this).addUserHabit("recommendGame_download_" + this.val$appname);
          Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(this.val$downloadUrl));
          if (ThirdPartyAppUtil.isApkExist(UpgradeDialogActivity.this, "com.android.browser"))
            localIntent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
          UpgradeDialogActivity.this.startActivity(localIntent);
          UpgradeDialogActivity.this.finish();
        }
      }
    }
    , new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        UpgradeModel.getInstance().ignoreUpgrade(UpgradeDialogActivity.this);
        UpgradeDialogActivity.this.finish();
      }
    }).setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramDialogInterface, int paramInt, KeyEvent paramKeyEvent)
      {
        if ((paramInt == 4) || (paramInt == 84))
        {
          UpgradeDialogActivity.this.dismissDialog();
          UpgradeDialogActivity.this.finish();
          return true;
        }
        return false;
      }
    });
    UserHabitUtils.getInstance(this).addUserHabit("upgrade_ask_for_download");
  }

  private void cancelDownloadService()
  {
    KXLog.d("DialogActivity", "cancelDownloadService");
    stopService(new Intent(getApplicationContext(), UpgradeDownloadService.class));
  }

  public static void chmod(String paramString1, String paramString2)
  {
    try
    {
      String str = "chmod " + paramString1 + " " + paramString2;
      Runtime.getRuntime().exec(str);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void installAPK()
  {
    KXLog.d("DialogActivity", "install " + UpgradeDownloadFile.getInstance().mFileFullName);
    Intent localIntent = new Intent();
    localIntent.addFlags(268435456);
    localIntent.setAction("android.intent.action.VIEW");
    String str = UpgradeDownloadFile.getInstance().mFileFullName;
    if (str == null)
      return;
    File localFile = new File(str);
    if (str.equals(getCacheDir().getAbsolutePath() + "/Update/" + "Kaixin-For-Android.apk"))
      chmod("777", localFile.getAbsolutePath());
    localIntent.setDataAndType(Uri.fromFile(localFile), "application/vnd.android.package-archive");
    startActivity(localIntent);
    UpgradeDownloadFile.getInstance().setInstall(true);
    cancelDownloadService();
  }

  private void pauseDownloadService()
  {
    KXLog.d("DialogActivity", "pauseDownloadService");
    UpgradeDownloadFile.getInstance().pause(true);
  }

  private void resumeDownloadService()
  {
    KXLog.d("DialogActivity", "resumeDownloadService");
    UpgradeDownloadFile.getInstance().pause(false);
  }

  private void startDownloadService()
  {
    KXLog.d("DialogActivity", "startDownloadService");
    startService(new Intent(getApplicationContext(), UpgradeDownloadService.class));
  }

  public void askForCancelDownload()
  {
    DialogUtil.showKXAlertDialog(this, 2131428014, 2131428028, 2131427382, 2131427383, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        UpgradeDialogActivity.this.cancelDownloadService();
        UpgradeDialogActivity.this.finish();
      }
    }
    , new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        UpgradeDialogActivity.this.finish();
      }
    }).setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramDialogInterface, int paramInt, KeyEvent paramKeyEvent)
      {
        return (paramInt == 4) || (paramInt == 84);
      }
    });
  }

  public void askForContinueDownload()
  {
    DialogUtil.showKXAlertDialog(this, 2131428014, 2131428029, 2131428022, 2131428023, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        UpgradeDialogActivity.this.cancelDownloadService();
        UpgradeDialogActivity.this.startDownloadService();
        UpgradeDialogActivity.this.finish();
      }
    }
    , new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        UpgradeDialogActivity.this.cancelDownloadService();
        UpgradeDialogActivity.this.finish();
      }
    }).setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramDialogInterface, int paramInt, KeyEvent paramKeyEvent)
      {
        return (paramInt == 4) || (paramInt == 84);
      }
    });
  }

  public void askForDownload()
  {
    getString(2131428025);
    getString(2131428026);
    getString(2131428027);
    if (UpgradeDownloadFile.getInstance().getVersion() == null)
      return;
    String str1 = UpgradeDownloadFile.getInstance().getNewVersionContent();
    String str2 = UpgradeDownloadFile.getInstance().getNewVersionDialogTitle();
    String str3 = UpgradeDownloadFile.getInstance().getLbtnTitle();
    DialogUtil.showMsgDlg(this, str2, str1, UpgradeDownloadFile.getInstance().getRbtnTitle(), str3, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        ConnectivityManager localConnectivityManager = (ConnectivityManager)UpgradeDialogActivity.this.getSystemService("connectivity");
        NetworkInfo.State localState1 = localConnectivityManager.getNetworkInfo(0).getState();
        NetworkInfo.State localState2 = localConnectivityManager.getNetworkInfo(1).getState();
        if ((localState2 == NetworkInfo.State.CONNECTED) || (localState2 == NetworkInfo.State.CONNECTING))
        {
          UpgradeDialogActivity.this.startDownloadService();
          UpgradeDialogActivity.this.finish();
        }
        do
          return;
        while ((localState1 != NetworkInfo.State.CONNECTED) && (localState1 != NetworkInfo.State.CONNECTING));
        UpgradeDialogActivity.this.askForDownloadUseFlow();
      }
    }
    , new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        UpgradeModel.getInstance().ignoreUpgrade(UpgradeDialogActivity.this);
        UpgradeDialogActivity.this.finish();
      }
    }).setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramDialogInterface, int paramInt, KeyEvent paramKeyEvent)
      {
        return (paramInt == 4) || (paramInt == 84);
      }
    });
    UserHabitUtils.getInstance(this).addUserHabit("upgrade_askForDownload");
  }

  public void askForDownloadUseFlow()
  {
    DialogUtil.showMsgDlg(this, getString(2131427788), getString(2131427789), 2131428019, 2131428021, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        UpgradeDialogActivity.this.startDownloadService();
        UpgradeDialogActivity.this.finish();
      }
    }
    , new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        UpgradeModel.getInstance().ignoreUpgrade(UpgradeDialogActivity.this);
        UpgradeDialogActivity.this.finish();
      }
    }).setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramDialogInterface, int paramInt, KeyEvent paramKeyEvent)
      {
        return (paramInt == 4) || (paramInt == 84);
      }
    });
  }

  public void askForInstall()
  {
    DialogUtil.showKXAlertDialog(this, 2131428014, 2131428030, 2131428020, 2131427383, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        UpgradeDialogActivity.this.installAPK();
        UpgradeDialogActivity.this.finish();
      }
    }
    , new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        UpgradeDialogActivity.this.finish();
      }
    }).setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramDialogInterface, int paramInt, KeyEvent paramKeyEvent)
      {
        return (paramInt == 4) || (paramInt == 84);
      }
    });
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903384);
    setTitle("");
    super.setTheme(16973835);
    Bundle localBundle = getIntent().getExtras();
    if (localBundle != null)
      this.mDialogType = localBundle.getInt("dialogtype", 0);
    KXLog.d("DialogActivity", "mDialogType=" + this.mDialogType);
    switch (this.mDialogType)
    {
    default:
      finish();
      return;
    case 1:
      askForDownload();
      return;
    case 2:
      askForCancelDownload();
      return;
    case 4:
      askForContinueDownload();
      return;
    case 3:
      askForInstall();
      return;
    case 5:
    }
    askForAppDownload(localBundle);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.UpgradeDialogActivity
 * JD-Core Version:    0.6.0
 */