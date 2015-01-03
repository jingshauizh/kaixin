package com.kaixin001.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.text.TextUtils;
import com.kaixin001.db.KaixinDBHelper;
import com.kaixin001.engine.FriendsInfoEngine;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.model.User;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import java.util.List;
import org.json.JSONArray;

public class KaixinContentProvider extends ContentProvider
{
  public static final String AUTHORITY = "com.kaixin001.provider";
  public static final String CONTENT_FRIENDSINFO_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.kaixin001.friendsinfo";
  public static final String CONTENT_FRIENDSINFO_TYPE = "vnd.android.cursor.dir/vnd.com.kaixin001.friendsinfo";
  public static final Uri CONTENT_FRIENDSINFO_URI;
  public static final String CONTENT_FRIENDS_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.kaixin001.friends";
  public static final String CONTENT_FRIENDS_TYPE = "vnd.android.cursor.dir/vnd.com.kaixin001.friends";
  public static final Uri CONTENT_FRIENDS_URI;
  public static final String CONTENT_HOME_INFO_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.kaixin001.homeinfo";
  public static final String CONTENT_HOME_INFO_TYPE = "vnd.android.cursor.dir/vnd.com.kaixin001.homeinfo";
  public static final Uri CONTENT_HOME_INFO_URI;
  public static final String CONTENT_LOGIN_TYPE = "vnd.android.cursor.dir/vnd.com.kaixin001.login";
  public static final Uri CONTENT_LOGIN_URI;
  public static final Uri CONTENT_NEWMESSAGE_URI;
  public static final String CONTENT_NEWS_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.kaixin001.news";
  public static final String CONTENT_NEWS_TYPE = "vnd.android.cursor.dir/vnd.com.kaixin001.news";
  public static final Uri CONTENT_NEWS_URI;
  public static final String CONTENT_NEW_MESSAGE_TYPE = "vnd.android.cursor.dir/vnd.com.kaixin001.newmessage";
  public static final String CONTENT_USER_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.kaixin001.user";
  public static final String CONTENT_USER_TYPE = "vnd.android.cursor.dir/vnd.com.kaixin001.user";
  public static final Uri CONTENT_USER_URI = Uri.parse("content://com.kaixin001.provider/user");
  public static final int FRIENDS = 5;
  public static final int FRIENDSINFO = 8;
  public static final int FRIENDSINFO_ID = 9;
  public static final int FRIENDS_ID = 6;
  public static final int HOMEINFO = 10;
  public static final int HOMEINFO_ID = 11;
  public static final int LOGIN = 7;
  public static final int NEWMESSAGE_TYPE = 12;
  public static final int NEWS = 3;
  public static final int NEWS_ID = 4;
  public static final int USER = 1;
  public static final int USER_ID = 2;
  public static final UriMatcher uriMatcher;
  private KaixinDBHelper kaixinDBHelper;

  static
  {
    CONTENT_NEWS_URI = Uri.parse("content://com.kaixin001.provider/news");
    CONTENT_FRIENDS_URI = Uri.parse("content://com.kaixin001.provider/friends");
    CONTENT_LOGIN_URI = Uri.parse("content://com.kaixin001.provider/login");
    CONTENT_NEWMESSAGE_URI = Uri.parse("content://com.kaixin001.provider/newmessage");
    CONTENT_FRIENDSINFO_URI = Uri.parse("content://com.kaixin001.provider/friendsinfo");
    CONTENT_HOME_INFO_URI = Uri.parse("content://com.kaixin001.provider/homeinfo");
    uriMatcher = new UriMatcher(-1);
    uriMatcher.addURI("com.kaixin001.provider", "user", 1);
    uriMatcher.addURI("com.kaixin001.provider", "user/#", 2);
    uriMatcher.addURI("com.kaixin001.provider", "news", 3);
    uriMatcher.addURI("com.kaixin001.provider", "news/#", 4);
    uriMatcher.addURI("com.kaixin001.provider", "friends", 5);
    uriMatcher.addURI("com.kaixin001.provider", "friends/#", 6);
    uriMatcher.addURI("com.kaixin001.provider", "login", 7);
    uriMatcher.addURI("com.kaixin001.provider", "friendsinfo", 8);
    uriMatcher.addURI("com.kaixin001.provider", "friendsinfo/#", 9);
    uriMatcher.addURI("com.kaixin001.provider", "homeinfo", 10);
    uriMatcher.addURI("com.kaixin001.provider", "homeinfo/#", 11);
    uriMatcher.addURI("com.kaixin001.provider", "newmessage/#", 12);
  }

  private Cursor queryFriends()
  {
    User.getInstance().loadUserData(getContext());
    String str = FileUtil.getCacheData(FileUtil.getKXCacheDir(getContext()), User.getInstance().getUID(), "friends.kx");
    MatrixCursor localMatrixCursor = new MatrixCursor(new String[] { "friends" });
    String[] arrayOfString = { str };
    localMatrixCursor.moveToFirst();
    localMatrixCursor.addRow(arrayOfString);
    return localMatrixCursor;
  }

  private Cursor queryFriendsInfo()
  {
    User.getInstance().loadUserData(getContext());
    String str = FileUtil.getCacheData(FileUtil.getKXCacheDir(getContext()), User.getInstance().getUID(), FriendsInfoEngine.FRIENDS_INFO_FILE);
    MatrixCursor localMatrixCursor = new MatrixCursor(new String[] { "friendsinfo" });
    String[] arrayOfString = { str };
    localMatrixCursor.moveToFirst();
    localMatrixCursor.addRow(arrayOfString);
    return localMatrixCursor;
  }

  private Cursor queryHomeInfo()
  {
    User.getInstance().loadUserData(getContext());
    String str = FileUtil.getCacheData(FileUtil.getKXCacheDir(getContext()), User.getInstance().getUID(), "home_info.kx");
    MatrixCursor localMatrixCursor = new MatrixCursor(new String[] { "homeinfo" });
    String[] arrayOfString = { str };
    localMatrixCursor.moveToFirst();
    localMatrixCursor.addRow(arrayOfString);
    return localMatrixCursor;
  }

  private Cursor queryLogin()
  {
    String[] arrayOfString1 = { "Login", "Uid" };
    if (TextUtils.isEmpty(User.getInstance().getOauthToken()))
      User.getInstance().loadUserData(getContext());
    String str = "1";
    if (TextUtils.isEmpty(User.getInstance().getOauthToken()))
      str = "0";
    MatrixCursor localMatrixCursor = new MatrixCursor(arrayOfString1);
    String[] arrayOfString2 = new String[2];
    arrayOfString2[0] = str;
    arrayOfString2[1] = User.getInstance().getUID();
    localMatrixCursor.moveToFirst();
    localMatrixCursor.addRow(arrayOfString2);
    return localMatrixCursor;
  }

  private Cursor queryNewMessage(int paramInt)
  {
    JSONArray localJSONArray = null;
    switch (paramInt)
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    }
    while (true)
    {
      String str = "\"newmsg\":";
      if (localJSONArray != null)
        str = str + localJSONArray.toString();
      MatrixCursor localMatrixCursor = new MatrixCursor(new String[] { "newmsg" });
      String[] arrayOfString = { str };
      localMatrixCursor.moveToFirst();
      localMatrixCursor.addRow(arrayOfString);
      return localMatrixCursor;
      localJSONArray = MessageCenterModel.getInstance().getMessageInboxArray();
      continue;
      localJSONArray = MessageCenterModel.getInstance().getUserCommentArray();
      continue;
      localJSONArray = MessageCenterModel.getInstance().getCommentArray();
      continue;
      localJSONArray = MessageCenterModel.getInstance().getSystemMessageArray();
      continue;
      localJSONArray = MessageCenterModel.getInstance().getSentUserCommentArray();
      continue;
      localJSONArray = MessageCenterModel.getInstance().getReplyCommentArray();
    }
  }

  private Cursor queryNews(String paramString)
  {
    String str1 = "";
    User localUser = User.getInstance();
    if ((TextUtils.isEmpty(localUser.getUID())) || (TextUtils.isEmpty(localUser.getOauthToken())))
      localUser.loadUserData(getContext());
    String str2 = FileUtil.getKXCacheDir(getContext());
    if (paramString.equals("1"))
      str1 = FileUtil.getCacheData(str2, localUser.getUID(), "news_all.kx");
    MatrixCursor localMatrixCursor = new MatrixCursor(new String[] { "news" });
    String[] arrayOfString = { str1 };
    localMatrixCursor.moveToFirst();
    localMatrixCursor.addRow(arrayOfString);
    return localMatrixCursor;
  }

  private Cursor queryUser()
  {
    User.getInstance().loadUserData(getContext());
    MatrixCursor localMatrixCursor = new MatrixCursor(new String[] { "email", "uid", "verify", "wapverify", "oauth_token" });
    User localUser = User.getInstance();
    String str1 = "";
    String str2 = localUser.getReserved();
    if (!TextUtils.isEmpty(str2))
    {
      int i = str2.indexOf("verify=");
      if (i >= 0)
      {
        int j = 1 + str2.indexOf("=", i);
        str1 = str2.substring(j, str2.indexOf(";", j));
      }
    }
    String[] arrayOfString = new String[5];
    arrayOfString[0] = localUser.getAccount();
    arrayOfString[1] = localUser.getUID();
    arrayOfString[2] = str1;
    arrayOfString[3] = localUser.getWapVerify();
    arrayOfString[4] = localUser.getOauthToken();
    localMatrixCursor.moveToFirst();
    localMatrixCursor.addRow(arrayOfString);
    return localMatrixCursor;
  }

  private long saveUser(ContentValues paramContentValues)
  {
    if (paramContentValues == null)
      return -1L;
    String str1 = paramContentValues.getAsString("account");
    String str2 = paramContentValues.getAsString("password");
    String str3 = paramContentValues.getAsString("uid");
    String str4 = paramContentValues.getAsString("access_token");
    User.getInstance().logoutClear();
    if (str1 != null)
      User.getInstance().setAccount(str1);
    if (str2 != null)
      User.getInstance().setPassword(str2);
    if (str3 != null)
      User.getInstance().setUID(str3);
    if (str4 != null)
      User.getInstance().setOauthToken(str4);
    return User.getInstance().saveUserLoginInfo(getContext());
  }

  private void sendChangeUserBroadcastToWidget()
  {
    Intent localIntent = new Intent("com.kaixin001.WIDGET_CHANGE_USER");
    getContext().sendBroadcast(localIntent);
  }

  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    return 0;
  }

  public String getType(Uri paramUri)
  {
    switch (uriMatcher.match(paramUri))
    {
    default:
      throw new IllegalArgumentException("Unknown URI " + paramUri);
    case 1:
      return "vnd.android.cursor.dir/vnd.com.kaixin001.user";
    case 2:
      return "vnd.android.cursor.item/vnd.com.kaixin001.user";
    case 3:
      return "vnd.android.cursor.dir/vnd.com.kaixin001.news";
    case 4:
      return "vnd.android.cursor.item/vnd.com.kaixin001.news";
    case 5:
      return "vnd.android.cursor.dir/vnd.com.kaixin001.friends";
    case 6:
      return "vnd.android.cursor.item/vnd.com.kaixin001.friends";
    case 7:
      return "vnd.android.cursor.dir/vnd.com.kaixin001.login";
    case 8:
      return "vnd.android.cursor.dir/vnd.com.kaixin001.friendsinfo";
    case 9:
      return "vnd.android.cursor.item/vnd.com.kaixin001.friendsinfo";
    case 10:
      return "vnd.android.cursor.dir/vnd.com.kaixin001.homeinfo";
    case 11:
    }
    return "vnd.android.cursor.item/vnd.com.kaixin001.homeinfo";
  }

  public Uri insert(Uri paramUri, ContentValues paramContentValues)
  {
    long l;
    do
    {
      try
      {
        if (uriMatcher.match(paramUri) != 1)
          throw new IllegalArgumentException("Unknown URI " + paramUri);
      }
      catch (Exception localException)
      {
        KXLog.e("KaixinContentProvider", "insert", localException);
        return null;
      }
      l = saveUser(paramContentValues);
    }
    while (l < 0L);
    Uri localUri = ContentUris.withAppendedId(CONTENT_USER_URI, l);
    getContext().getContentResolver().notifyChange(CONTENT_USER_URI, null);
    sendChangeUserBroadcastToWidget();
    return localUri;
  }

  public boolean onCreate()
  {
    this.kaixinDBHelper = KaixinDBHelper.getInstance(getContext());
    return true;
  }

  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    try
    {
      switch (uriMatcher.match(paramUri))
      {
      case 3:
      case 5:
      case 11:
      default:
        throw new IllegalArgumentException("Unknown URI " + paramUri);
      case 1:
      case 2:
      case 4:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 12:
      }
    }
    catch (Exception localException)
    {
      KXLog.e("KaixinContentProvider", "query", localException);
      return null;
    }
    Object localObject = queryUser();
    while (true)
    {
      ((Cursor)localObject).setNotificationUri(getContext().getContentResolver(), paramUri);
      return localObject;
      localObject = queryUser();
      continue;
      localObject = queryNews((String)paramUri.getPathSegments().get(1));
      continue;
      localObject = queryFriends();
      continue;
      localObject = queryLogin();
      continue;
      localObject = queryFriendsInfo();
      continue;
      localObject = queryFriendsInfo();
      continue;
      localObject = queryHomeInfo();
      continue;
      Cursor localCursor = queryNewMessage(Integer.parseInt((String)paramUri.getPathSegments().get(1)));
      localObject = localCursor;
    }
  }

  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
  {
    return 0;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.provider.KaixinContentProvider
 * JD-Core Version:    0.6.0
 */