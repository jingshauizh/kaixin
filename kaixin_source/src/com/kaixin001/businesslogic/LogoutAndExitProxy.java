package com.kaixin001.businesslogic;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.text.TextUtils;
import com.kaixin001.activity.KXActivity;
import com.kaixin001.activity.KXApplication;
import com.kaixin001.activity.LoginActivity;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.engine.LogoutEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UpdateEngine;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.lbs.LocationService;
import com.kaixin001.model.AdvertModel;
import com.kaixin001.model.AlbumListModel;
import com.kaixin001.model.AlbumPhotoModel;
import com.kaixin001.model.ChatModel;
import com.kaixin001.model.CircleMessageModel;
import com.kaixin001.model.CloudAlbumModel;
import com.kaixin001.model.DiaryModel;
import com.kaixin001.model.EventModel;
import com.kaixin001.model.FriendPhotoModel;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.KaixinConst;
import com.kaixin001.model.LbsModel;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.RepostModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.SharedPostModel;
import com.kaixin001.model.User;
import com.kaixin001.model.UserInfoModel;
import com.kaixin001.model.VoteListModel;
import com.kaixin001.model.VoteModel;
import com.kaixin001.service.RefreshNewMessageService;
import com.kaixin001.util.CloudAlbumManager;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownloader;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXUBLog;
import com.kaixin001.util.UploadNotificationHandler;
import java.util.HashMap;

public class LogoutAndExitProxy
{
  public static final int LOGOUT_MODE_EXIT = 0;
  public static final int LOGOUT_MODE_FAST_LOGOUT = 3;
  public static final int LOGOUT_MODE_LOGOUT = 1;
  public static final int LOGOUT_MODE_PASSWORD_CHANGED = 2;
  private boolean bStop = false;
  public Activity ctx;
  private LogoutTask logoutTask;
  private ClearDataTask mClearDataTask = null;

  public LogoutAndExitProxy(Activity paramActivity)
  {
    this.ctx = paramActivity;
  }

  private void cancelNotification()
  {
    NotificationManager localNotificationManager = (NotificationManager)this.ctx.getSystemService("notification");
    localNotificationManager.cancel(KaixinConst.ID_ONLINE_NOTIFICATION);
    localNotificationManager.cancel(KaixinConst.ID_NEW_MESSAGE_NOTIFICATION);
    localNotificationManager.cancel(KaixinConst.ID_NEW_PUSH_NOTIFICATION);
    localNotificationManager.cancel(KaixinConst.ID_DOWNLOADING_NOTIFICATION);
    localNotificationManager.cancel(KaixinConst.ID_DOWNLOAD_COMPLETE_NOTIFICATION);
    localNotificationManager.cancel(KaixinConst.ID_NEW_CHAT_NOTIFICATION);
    localNotificationManager.cancel(5999);
    localNotificationManager.cancel(KaixinConst.ID_VIEW_CLOUDALBUM);
  }

  private void clearAllModels()
  {
    if (TextUtils.isEmpty(User.getInstance().getAccount()))
      NewsModel.getInstance().clear();
    NewsModel.clearAllHomeModels();
    NewsModel.getMyHomeModel().clear();
    FriendsModel.getInstance().clear();
    AlbumListModel.getInstance().clear();
    AlbumListModel.getMyAlbumList().clear();
    AlbumPhotoModel.getInstance().clear();
    UserInfoModel.getInstance().clear();
    LbsModel.getInstance().clear();
    DiaryModel.getInstance().clear();
    MessageCenterModel.getInstance().clear();
    CircleMessageModel.getInstance().clear();
    RepostModel.getInstance().clear();
    VoteModel.getInstance().clear();
    VoteListModel.getInstance().clear();
    SharedPostModel.getInstance("normal").clear();
    SharedPostModel.getInstance("hot").clear();
    EventModel.getInstance().clear();
    FriendPhotoModel.getInstance().clear();
    ChatModel.getInstance().clear();
    ChatModel.getInstance().clearAllMessage();
    AdvertModel.getInstance().clear();
    CloudAlbumManager.getInstance().stopUploadDeamon();
    CloudAlbumModel.getInstance().clear();
    Setting.getInstance().clear();
  }

  private void clearData()
  {
    cancelNotification();
    if ((this.mClearDataTask != null) && (!this.mClearDataTask.isCancelled()) && (this.mClearDataTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.mClearDataTask.cancel(true);
      this.mClearDataTask = null;
    }
    this.mClearDataTask = new ClearDataTask(null);
    this.mClearDataTask.execute(null);
  }

  private void sendLogoutBroadcast()
  {
    Intent localIntent = new Intent("com.kaixin001.LOGOUT");
    localIntent.putExtra("from", "kaixin001Client");
    localIntent.putExtra("id", User.getInstance().getAccount());
    this.ctx.sendBroadcast(localIntent);
  }

  private void sendUpdateWidgetBroadcast()
  {
    Intent localIntent = new Intent("com.kaixin001.WIDGET_UPDATE");
    this.ctx.sendBroadcast(localIntent);
  }

  private void stopRefreshNewMessageService()
  {
    Intent localIntent = new Intent(this.ctx, RefreshNewMessageService.class);
    this.ctx.stopService(localIntent);
  }

  public void clearAllData()
  {
    CloudAlbumManager.getInstance().stopUploadDeamon();
    ImageDownloader.getInstance().clear();
    clearAllModels();
    ImageCache.getInstance().clear();
    UpdateEngine.getInstance().clear();
  }

  public void exit(boolean paramBoolean)
  {
    KXUBLog.log("quit_Droid");
    if (!User.getInstance().GetLookAround())
    {
      User.getInstance().saveUserLoginInfo(KXApplication.getInstance());
      User.getInstance().saveUserInfo(KXApplication.getInstance());
    }
    while (true)
    {
      clearData();
      LocationService.getLocationService().stopRefreshLocation(true);
      if (paramBoolean)
        this.ctx.finish();
      return;
      User.getInstance().clearAccout();
      User.getInstance().setLookAround(Boolean.valueOf(false));
    }
  }

  public void logout(int paramInt, IOnLogoutListener paramIOnLogoutListener)
  {
    this.bStop = false;
    KXUBLog.log("homepage_cancel");
    if (paramInt == 2)
    {
      logoutProc();
      Intent localIntent = new Intent();
      localIntent.putExtra("action", 10000);
      turnLogin(localIntent);
      this.ctx.finish();
      return;
    }
    if ((this.ctx instanceof KXActivity))
    {
      KXActivity localKXActivity = (KXActivity)this.ctx;
      localKXActivity.dismissDialog();
      localKXActivity.dialog = ProgressDialog.show(this.ctx, "", this.ctx.getResources().getText(2131427368), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          LogoutAndExitProxy.this.bStop = true;
          LogoutEngine.getInstance().cancel();
          LogoutAndExitProxy.this.logoutTask.cancel(true);
        }
      });
    }
    while (true)
    {
      logoutProc();
      this.logoutTask = new LogoutTask(paramIOnLogoutListener);
      this.logoutTask.logoutMode = paramInt;
      this.logoutTask.execute(new Void[0]);
      return;
      if (!(this.ctx instanceof MainActivity))
        continue;
      MainActivity localMainActivity = (MainActivity)this.ctx;
      localMainActivity.dismissDialog();
      localMainActivity.dialog = ProgressDialog.show(this.ctx, "", this.ctx.getResources().getText(2131427368), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          LogoutAndExitProxy.this.bStop = true;
          LogoutEngine.getInstance().cancel();
          LogoutAndExitProxy.this.logoutTask.cancel(true);
        }
      });
    }
  }

  protected void logoutProc()
  {
    clearData();
    stopRefreshNewMessageService();
    User.getInstance().logout();
    UpdateEngine.getInstance().setFriendLoaded(false);
    UploadTaskListEngine.getInstance().clear();
    sendLogoutBroadcast();
    sendUpdateWidgetBroadcast();
    UploadTaskListEngine.getInstance().unRegister(UploadNotificationHandler.getInstance(this.ctx.getApplicationContext()));
    UploadNotificationHandler.getInstance(this.ctx.getApplicationContext()).deleteAllNotifications();
    CloudAlbumModel.getInstance().getIgnoreFileList().clear();
  }

  public void showLogoutOrExistDialog()
  {
    KXActivity localKXActivity = (KXActivity)this.ctx;
    localKXActivity.dismissDialog();
    localKXActivity.dialog = DialogUtil.showSelectListDlg(this.ctx, 2131427509, this.ctx.getResources().getStringArray(2131492864), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if (!Setting.getInstance().isTestVersion())
        {
          if (paramInt == 0)
            LogoutAndExitProxy.this.exit(true);
        }
        else
          return;
        LogoutAndExitProxy.this.logout(1, null);
      }
    }
    , 1);
  }

  protected void turnLogin(Intent paramIntent)
  {
    paramIntent.setClass(this.ctx, LoginActivity.class);
    this.ctx.startActivity(paramIntent);
  }

  private class ClearDataTask extends AsyncTask<Void, Void, Void>
  {
    private ClearDataTask()
    {
    }

    protected Void doInBackground(Void[] paramArrayOfVoid)
    {
      LogoutAndExitProxy.this.clearAllData();
      return null;
    }
  }

  public static abstract interface IOnLogoutListener
  {
    public abstract void onLogout();
  }

  private class LogoutTask extends AsyncTask<Void, Void, Integer>
  {
    private LogoutAndExitProxy.IOnLogoutListener listener;
    protected int logoutMode = -1;

    public LogoutTask(LogoutAndExitProxy.IOnLogoutListener arg2)
    {
      Object localObject;
      this.listener = localObject;
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      try
      {
        LogoutEngine.getInstance().logout(LogoutAndExitProxy.this.ctx.getApplicationContext());
        return Integer.valueOf(0);
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
      }
      return null;
    }

    protected void onPostExecute(Integer paramInteger)
    {
      if (paramInteger == null)
      {
        LogoutAndExitProxy.this.ctx.finish();
        return;
      }
      while (true)
      {
        try
        {
          if (LogoutAndExitProxy.this.bStop)
            break;
          LogoutAndExitProxy.this.logoutProc();
          if ((LogoutAndExitProxy.this.ctx instanceof KXActivity))
          {
            ((KXActivity)LogoutAndExitProxy.this.ctx).dismissDialog();
            if (this.logoutMode != 1)
              continue;
            LogoutAndExitProxy.this.exit(true);
            LogoutAndExitProxy.this.turnLogin(new Intent());
            if (this.listener == null)
              break;
            this.listener.onLogout();
            return;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("KaixinDesktop", "onPostExecute", localException);
          return;
        }
        if (!(LogoutAndExitProxy.this.ctx instanceof MainActivity))
          continue;
        ((MainActivity)LogoutAndExitProxy.this.ctx).dismissDialog();
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.businesslogic.LogoutAndExitProxy
 * JD-Core Version:    0.6.0
 */