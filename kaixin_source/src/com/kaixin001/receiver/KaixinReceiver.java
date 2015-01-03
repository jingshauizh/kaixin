package com.kaixin001.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.service.KaixinService;
import com.kaixin001.service.RefreshNewMessageService;
import com.kaixin001.util.CloudAlbumManager;
import com.kaixin001.util.KXLog;

public class KaixinReceiver extends BroadcastReceiver
{
  public static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
  public static final String ACTION_GET_MY_ALBUM_LIST = "com.kaixin001.GET_MY_ALBUM_LIST";
  public static final String ACTION_GET_MY_ALBUM_LIST_COMPLETED = "com.kaixin001.GET_MY_ALBUM_LIST_COMPLETED";
  public static final String ACTION_GET_NEW_MESSAGE_STATE = "com.kaixin001.GET_NEW_MESSAGE_STATE";
  public static final String ACTION_GOTO_NEW_MESSAGE_LIST = "com.kaixin001.GOTO_NEW_MESSAGE_LIST";
  public static final String ACTION_QUERY_HOME_INFO = "com.kaixin001.QUERY_HOME_INFO";
  public static final String ACTION_QUERY_HOME_INFO_COMPLETED = "com.kaixin001.QUERY_HOME_INFO_COMPLETED";
  public static final String ACTION_SET_NOTIFICATION = "com.kaixin001.ACTION_SET_NOTIFICATION";
  public static final String ACTION_SUBMIT_STATUS = "com.kaixin001.SUBMIT_STATUS";
  public static final String ACTION_SUBMIT_STATUS_COMPLETED = "com.kaixin001.SUBMIT_STATUS_COMPLETED";
  public static final String ACTION_UPDATE_FRIENDS = "com.kaixin001.UPDATE_FRIENDS";
  public static final String ACTION_UPDATE_FRIENDS_COMPLETED = "com.kaixin001.UPDATE_FRIENDS_COMPLETED";
  public static final String ACTION_UPDATE_FRIENDS_INFO = "com.kaixin001.UPDATE_FRIENDS_INFO";
  public static final String ACTION_UPDATE_FRIENDS_INFO_COMPLETED = "com.kaixin001.UPDATE_FRIENDS_INFO_COMPLETED";
  public static final String ACTION_UPDATE_NEWS = "com.kaixin001.UPDATE_NEWS";
  public static final String ACTION_UPDATE_NEWS_COMPLETED = "com.kaixin001.UPDATE_NEWS_COMPLETED";
  public static final String ACTION_UPDATE_NEW_MESSAGE = "com.kaixin001.UPDATE_NEW_MESSAGE";
  public static final String ACTION_UPDATE_NEW_MESSAGE_COMPLETED = "com.kaixin001.UPDATE_NEW_MESSAGE_COMPLETED";
  public static final String ACTION_UPLOAD_DIARY = "com.kaixin001.UPLOAD_DIARY";
  public static final String ACTION_UPLOAD_DIARY_COMPLETED = "com.kaixin001.UPLOAD_DIARY_COMPLETED";
  public static final String ACTION_UPLOAD_PHOTO = "com.kaixin001.UPLOAD_PHOTO";
  public static final String ACTION_UPLOAD_PHOTO_COMPLETED = "com.kaixin001.UPLOAD_PHOTO_COMPLETED";
  public static final String BUNDLE_KEY_ALBUM_ID = "album_id";
  public static final String BUNDLE_KEY_ALBUM_NAME = "albumname";
  public static final String BUNDLE_KEY_CONTENT = "content";
  public static final String BUNDLE_KEY_DIARY_CONTENT = "diary_content";
  public static final String BUNDLE_KEY_DIARY_PHOTO = "diary_photo";
  public static final String BUNDLE_KEY_DIARY_TITLE = "diary_title";
  public static final String BUNDLE_KEY_MSGNUM = "msgnum";
  public static final String BUNDLE_KEY_MSGTYPE = "msgtype";
  public static final String BUNDLE_KEY_PHOTO_TITLE = "photo_title";
  public static final String BUNDLE_KEY_UIDS = "uids";
  public static final String INITIAL_USER_ACTION = "com.kaixin001.INITIAL_USER";
  public static final String LOGOUT_ACTION = "com.kaixin001.LOGOUT";
  final String _COLUMN_AUTO = "autologin";
  final String _COLUMN_EMAIL = "account";
  final String _COLUMN_PWD = "password";
  final String _COLUMN_SAVEPWD = "savepwd";
  final String _COLUMN_UID = "uid";
  final String _COLUMN_VERIFY = "verify";
  final String _COLUMN_WAPVERIFY = "wapverify";

  private void gotoNewMsgList(Context paramContext, Intent paramIntent)
  {
    int i = paramIntent.getIntExtra("msgtype", 0);
    Intent localIntent = new Intent(paramContext, MainActivity.class);
    Bundle localBundle = new Bundle();
    switch (i)
    {
    default:
      return;
    case 1:
      localIntent.setFlags(268435456);
      localBundle.putString("fragment", "MessageListFragment.class");
      localIntent.putExtras(localBundle);
      paramContext.startActivity(localIntent);
      return;
    case 2:
      MessageCenterModel.getInstance().setActiveUserCommentType(2);
      localIntent.setFlags(268435456);
      localBundle.putString("fragment", "UserCommentListFragment.class");
      localIntent.putExtras(localBundle);
      paramContext.startActivity(localIntent);
      return;
    case 3:
      MessageCenterModel.getInstance().setActiveCommentType(3);
      localIntent.setFlags(268435456);
      localBundle.putString("fragment", "CommentListFragment.class");
      localIntent.putExtras(localBundle);
      paramContext.startActivity(localIntent);
      return;
    case 4:
      localIntent.setFlags(268435456);
      localBundle.putString("fragment", "SystemMessageListFragment.class");
      localIntent.putExtras(localBundle);
      paramContext.startActivity(localIntent);
      return;
    case 5:
      MessageCenterModel.getInstance().setActiveUserCommentType(5);
      localIntent.setFlags(268435456);
      localBundle.putString("fragment", "UserCommentListFragment.class");
      localIntent.putExtras(localBundle);
      paramContext.startActivity(localIntent);
      return;
    case 6:
    }
    MessageCenterModel.getInstance().setActiveCommentType(6);
    localIntent.setFlags(268435456);
    localBundle.putString("fragment", "CommentListFragment.class");
    localIntent.putExtras(localBundle);
    paramContext.startActivity(localIntent);
  }

  private void initialUserAction(Context paramContext, Bundle paramBundle)
  {
    if (paramBundle == null);
    Uri localUri;
    ContentValues localContentValues;
    do
    {
      String str1;
      do
      {
        return;
        str1 = paramBundle.getString("from");
      }
      while ((!TextUtils.isEmpty(str1)) && (str1.equals("kaixin001Client")));
      String str2 = paramBundle.getString("id");
      String str3 = paramBundle.getString("pwd");
      String str4 = paramBundle.getString("uid");
      String str5 = paramBundle.getString("verify");
      String str6 = paramBundle.getString("wapverify");
      localUri = Uri.parse("content://com.kaixin001.provider/user");
      localContentValues = new ContentValues();
      localContentValues.put("account", str2);
      localContentValues.put("password", str3);
      localContentValues.put("uid", str4);
      localContentValues.put("autologin", Integer.valueOf(1));
      localContentValues.put("savepwd", "");
      localContentValues.put("verify", str5);
      localContentValues.put("wapverify", str6);
    }
    while (paramContext == null);
    paramContext.getContentResolver().insert(localUri, localContentValues);
  }

  private void logoutAction(Context paramContext, Bundle paramBundle)
  {
    if (paramBundle != null)
    {
      String str = paramBundle.getString("from");
      if ((TextUtils.isEmpty(str)) || (!str.equals("kaixin001Client")));
    }
    Uri localUri;
    ContentValues localContentValues;
    do
    {
      return;
      localUri = Uri.parse("content://com.kaixin001.provider/user");
      localContentValues = new ContentValues();
      localContentValues.put("password", "");
      localContentValues.put("uid", "");
      localContentValues.put("autologin", Integer.valueOf(0));
      localContentValues.put("savepwd", "");
      localContentValues.put("verify", "");
      localContentValues.put("wapverify", "");
    }
    while (paramContext == null);
    paramContext.getContentResolver().insert(localUri, localContentValues);
  }

  private void notificationAction(Context paramContext, Bundle paramBundle)
  {
    if (paramBundle == null)
      return;
    boolean bool = paramBundle.getBoolean("enable_notification");
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
    localEditor.putBoolean("new_msg_notification_preference", bool);
    localEditor.commit();
    if (bool)
    {
      paramContext.startService(new Intent(paramContext, RefreshNewMessageService.class));
      return;
    }
    paramContext.stopService(new Intent(paramContext, RefreshNewMessageService.class));
  }

  private void startKaixinService(Context paramContext, Intent paramIntent)
  {
    Intent localIntent = new Intent(paramContext, KaixinService.class);
    localIntent.setAction(paramIntent.getAction());
    try
    {
      localIntent.putExtra("content", paramIntent.getStringExtra("content"));
      localIntent.putExtra("uids", paramIntent.getStringExtra("uids"));
      localIntent.putExtra("msgtype", paramIntent.getIntExtra("msgtype", 0));
      localIntent.putExtra("msgnum", paramIntent.getIntExtra("msgnum", 0));
      localIntent.putExtra("photo_title", paramIntent.getStringExtra("photo_title"));
      localIntent.putExtra("album_id", paramIntent.getStringExtra("album_id"));
      localIntent.putExtra("albumname", paramIntent.getStringExtra("albumname"));
      localIntent.putExtra("android.intent.extra.STREAM", paramIntent.getParcelableExtra("android.intent.extra.STREAM"));
      localIntent.putExtra("diary_title", paramIntent.getStringExtra("diary_title"));
      localIntent.putExtra("diary_content", paramIntent.getStringExtra("diary_content"));
      localIntent.putExtra("diary_photo", paramIntent.getParcelableExtra("diary_photo"));
      KXLog.d("start service", "start service");
      paramContext.startService(localIntent);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Bundle localBundle = paramIntent.getExtras();
    String str = paramIntent.getAction();
    if (str.compareTo("com.kaixin001.INITIAL_USER") == 0)
    {
      initialUserAction(paramContext, localBundle);
      return;
    }
    if (str.compareTo("com.kaixin001.LOGOUT") == 0)
    {
      logoutAction(paramContext, localBundle);
      return;
    }
    if (str.compareTo("com.kaixin001.ACTION_SET_NOTIFICATION") == 0)
    {
      notificationAction(paramContext, localBundle);
      return;
    }
    if (str.compareTo("com.kaixin001.GOTO_NEW_MESSAGE_LIST") == 0)
    {
      gotoNewMsgList(paramContext, paramIntent);
      return;
    }
    if (str.compareTo("android.intent.action.BOOT_COMPLETED") == 0)
    {
      if (PreferenceManager.getDefaultSharedPreferences(paramContext).getBoolean("new_msg_notification_preference", true))
        paramContext.startService(new Intent(paramContext, RefreshNewMessageService.class));
      CloudAlbumManager.getInstance().init(paramContext);
      CloudAlbumManager.getInstance().startUploadDeamon(paramContext);
      return;
    }
    startKaixinService(paramContext, paramIntent);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.receiver.KaixinReceiver
 * JD-Core Version:    0.6.0
 */