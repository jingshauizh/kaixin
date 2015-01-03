package com.kaixin001.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import com.kaixin001.engine.DiaryEngine;
import com.kaixin001.model.User;
import com.kaixin001.util.CloseUtil;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.StringUtil;
import java.io.File;
import java.io.FileOutputStream;
import org.json.JSONArray;

public class KaixinService extends Service
{
  private static final String LOGTAG = "KaixinService";
  private KaixinServiceThread mThread = null;

  private String SaveBitmapToFile(Bitmap paramBitmap)
  {
    File localFile = getFileStreamPath("kx_upload_tmp.jpg");
    if (!FileUtil.makeEmptyFile(localFile))
      return null;
    FileOutputStream localFileOutputStream = null;
    try
    {
      int i = paramBitmap.getWidth();
      localFileOutputStream = null;
      if (i > 780)
        paramBitmap = Bitmap.createScaledBitmap(paramBitmap, 780, 780 * paramBitmap.getHeight() / i, true);
      localFileOutputStream = openFileOutput("kx_upload_tmp.jpg", 0);
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, 70, localFileOutputStream);
      localFileOutputStream.flush();
      String str = localFile.getAbsolutePath();
      return str;
    }
    catch (Exception localException)
    {
      KXLog.e("KaixinService", "failed to save image", localException);
      return null;
    }
    finally
    {
      CloseUtil.close(localFileOutputStream);
    }
    throw localObject;
  }

  private void getMyAlbumListCompleted(String paramString, boolean paramBoolean)
  {
    Intent localIntent = new Intent("com.kaixin001.GET_MY_ALBUM_LIST_COMPLETED");
    Bundle localBundle = new Bundle();
    localBundle.putString("albums", paramString);
    localBundle.putBoolean("success", paramBoolean);
    localIntent.putExtras(localBundle);
    KXLog.d("send", "send");
    sendBroadcast(localIntent);
  }

  // ERROR //
  private String loadPhoto(String paramString)
  {
    // Byte code:
    //   0: ldc 188
    //   2: astore_2
    //   3: aload_1
    //   4: invokevirtual 193	java/lang/String:length	()I
    //   7: ifle +59 -> 66
    //   10: new 66	java/io/File
    //   13: dup
    //   14: aload_1
    //   15: invokespecial 194	java/io/File:<init>	(Ljava/lang/String;)V
    //   18: astore 4
    //   20: new 196	android/graphics/BitmapFactory$Options
    //   23: dup
    //   24: invokespecial 197	android/graphics/BitmapFactory$Options:<init>	()V
    //   27: astore 5
    //   29: aload 4
    //   31: invokevirtual 200	java/io/File:length	()J
    //   34: lstore 6
    //   36: lload 6
    //   38: ldc 201
    //   40: i2l
    //   41: lcmp
    //   42: ifgt +26 -> 68
    //   45: aload 5
    //   47: iconst_1
    //   48: putfield 205	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   51: aload_0
    //   52: aload_1
    //   53: aload 5
    //   55: invokestatic 211	android/graphics/BitmapFactory:decodeFile	(Ljava/lang/String;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   58: invokespecial 213	com/kaixin001/service/KaixinService:SaveBitmapToFile	(Landroid/graphics/Bitmap;)Ljava/lang/String;
    //   61: astore 9
    //   63: aload 9
    //   65: astore_2
    //   66: aload_2
    //   67: areturn
    //   68: lload 6
    //   70: ldc2_w 214
    //   73: ldc 201
    //   75: i2l
    //   76: lmul
    //   77: lcmp
    //   78: ifgt +19 -> 97
    //   81: aload 5
    //   83: iconst_2
    //   84: putfield 205	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   87: goto -36 -> 51
    //   90: astore_3
    //   91: aload_3
    //   92: invokevirtual 218	java/lang/Exception:printStackTrace	()V
    //   95: aconst_null
    //   96: areturn
    //   97: ldc 201
    //   99: i2l
    //   100: lstore 10
    //   102: aload 5
    //   104: iconst_1
    //   105: lload 6
    //   107: lload 10
    //   109: ldiv
    //   110: l2d
    //   111: invokestatic 224	java/lang/Math:log	(D)D
    //   114: ldc2_w 225
    //   117: invokestatic 224	java/lang/Math:log	(D)D
    //   120: ddiv
    //   121: d2i
    //   122: iadd
    //   123: putfield 205	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   126: goto -75 -> 51
    //   129: astore 8
    //   131: aload 8
    //   133: invokevirtual 227	java/lang/OutOfMemoryError:printStackTrace	()V
    //   136: aconst_null
    //   137: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   3	36	90	java/lang/Exception
    //   45	51	90	java/lang/Exception
    //   51	63	90	java/lang/Exception
    //   81	87	90	java/lang/Exception
    //   102	126	90	java/lang/Exception
    //   131	136	90	java/lang/Exception
    //   51	63	129	java/lang/OutOfMemoryError
  }

  private String parsePhotoUri(Uri paramUri)
  {
    Object localObject = "";
    if (paramUri == null)
      return null;
    while (true)
    {
      try
      {
        boolean bool = paramUri.toString().contains("content");
        if (!bool);
      }
      catch (Exception localException1)
      {
        try
        {
          Bitmap localBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), paramUri);
          String str2 = FileUtil.getKXCacheDir(getApplicationContext());
          StringBuffer localStringBuffer = new StringBuffer();
          localStringBuffer.append(str2).append("/data/").append(StringUtil.MD5Encode(paramUri.toString())).append(".kxjpg");
          localObject = localStringBuffer.toString();
          saveMyBitmap((String)localObject, localBitmap);
          return localObject;
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
          continue;
          localException1 = localException1;
          KXLog.e("KaixinService", "parsePhotoUri", localException1);
          continue;
        }
        catch (OutOfMemoryError localOutOfMemoryError)
        {
          return null;
        }
      }
      String str1 = paramUri.getPath();
      localObject = str1;
    }
  }

  private boolean postDirayTextContent(String paramString1, String paramString2)
  {
    if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)))
      return false;
    try
    {
      boolean bool = DiaryEngine.getInstance().doPostDiary(getApplicationContext(), paramString1, paramString2, "", "", "", "");
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  private void queryHomeInfoCompleted(boolean paramBoolean)
  {
    Intent localIntent = new Intent("com.kaixin001.QUERY_HOME_INFO_COMPLETED");
    localIntent.putExtra("success", paramBoolean);
    sendBroadcast(localIntent);
  }

  // ERROR //
  private boolean saveMyBitmap(String paramString, Bitmap paramBitmap)
    throws java.io.IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: new 66	java/io/File
    //   5: dup
    //   6: aload_1
    //   7: invokespecial 194	java/io/File:<init>	(Ljava/lang/String;)V
    //   10: astore 4
    //   12: aload 4
    //   14: invokestatic 32	com/kaixin001/util/FileUtil:makeEmptyFile	(Ljava/io/File;)Z
    //   17: istore 7
    //   19: iload 7
    //   21: ifne +9 -> 30
    //   24: aconst_null
    //   25: invokestatic 76	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   28: iconst_0
    //   29: ireturn
    //   30: new 61	java/io/FileOutputStream
    //   33: dup
    //   34: aload 4
    //   36: invokespecial 307	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   39: astore 8
    //   41: aload_2
    //   42: getstatic 55	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   45: bipush 100
    //   47: aload 8
    //   49: invokevirtual 59	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   52: pop
    //   53: aload 8
    //   55: invokevirtual 64	java/io/FileOutputStream:flush	()V
    //   58: aload 8
    //   60: invokestatic 76	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   63: iconst_1
    //   64: ireturn
    //   65: astore 6
    //   67: ldc 8
    //   69: ldc 78
    //   71: aload 6
    //   73: invokestatic 84	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   76: aload_3
    //   77: invokestatic 76	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   80: iconst_0
    //   81: ireturn
    //   82: astore 5
    //   84: aload_3
    //   85: invokestatic 76	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
    //   88: aload 5
    //   90: athrow
    //   91: astore 5
    //   93: aload 8
    //   95: astore_3
    //   96: goto -12 -> 84
    //   99: astore 6
    //   101: aload 8
    //   103: astore_3
    //   104: goto -37 -> 67
    //
    // Exception table:
    //   from	to	target	type
    //   2	19	65	java/io/IOException
    //   30	41	65	java/io/IOException
    //   2	19	82	finally
    //   30	41	82	finally
    //   67	76	82	finally
    //   41	58	91	finally
    //   41	58	99	java/io/IOException
  }

  private void sendNewMsgNotificationBroadcast(JSONArray paramJSONArray)
  {
    Intent localIntent = new Intent("com.kaixin001.NEW_MSG_NOTIFICATION");
    Bundle localBundle = new Bundle();
    localBundle.putString("notices", paramJSONArray.toString());
    localIntent.putExtras(localBundle);
    sendBroadcast(localIntent);
  }

  private void submitStatusCompleted(boolean paramBoolean)
  {
    Intent localIntent = new Intent("com.kaixin001.SUBMIT_STATUS_COMPLETED");
    localIntent.putExtra("success", paramBoolean);
    sendBroadcast(localIntent);
  }

  private void updateFriendsCompleted(boolean paramBoolean)
  {
    Intent localIntent = new Intent("com.kaixin001.UPDATE_FRIENDS_COMPLETED");
    localIntent.putExtra("success", paramBoolean);
    sendBroadcast(localIntent);
  }

  private void updateFriendsInfoCompleted(boolean paramBoolean)
  {
    Intent localIntent = new Intent("com.kaixin001.UPDATE_FRIENDS_INFO_COMPLETED");
    localIntent.putExtra("success", paramBoolean);
    sendBroadcast(localIntent);
  }

  private void updateNewMessageCompleted(boolean paramBoolean, int paramInt)
  {
    Intent localIntent = new Intent("com.kaixin001.UPDATE_NEW_MESSAGE_COMPLETED");
    localIntent.putExtra("success", paramBoolean);
    localIntent.putExtra("msgtype", paramInt);
    sendBroadcast(localIntent);
  }

  private void updateNewsCompleted(boolean paramBoolean)
  {
    Intent localIntent = new Intent("com.kaixin001.UPDATE_NEWS_COMPLETED");
    localIntent.putExtra("success", paramBoolean);
    sendBroadcast(localIntent);
  }

  private void uploadDiaryCompleted(boolean paramBoolean)
  {
    Intent localIntent = new Intent("com.kaixin001.UPLOAD_DIARY_COMPLETED");
    localIntent.putExtra("success", paramBoolean);
    sendBroadcast(localIntent);
  }

  private void uploadPhotoCompleted(boolean paramBoolean)
  {
    Intent localIntent = new Intent("com.kaixin001.UPLOAD_PHOTO_COMPLETED");
    localIntent.putExtra("success", paramBoolean);
    sendBroadcast(localIntent);
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onDestroy()
  {
    try
    {
      if (this.mThread != null)
      {
        boolean bool = this.mThread.isAlive();
        if (!bool);
      }
    }
    catch (Exception localException1)
    {
      try
      {
        this.mThread.stop();
        label26: this.mThread = null;
        while (true)
        {
          super.onDestroy();
          return;
          localException1 = localException1;
          localException1.printStackTrace();
        }
      }
      catch (Exception localException2)
      {
        break label26;
      }
    }
  }

  public void onStart(Intent paramIntent, int paramInt)
  {
    super.onStart(paramIntent, paramInt);
    User.getInstance().loadUserData(getApplicationContext());
    if (paramIntent == null);
    String str1;
    do
    {
      return;
      str1 = paramIntent.getAction();
    }
    while (TextUtils.isEmpty(str1));
    this.mThread = new KaixinServiceThread(null);
    this.mThread.setAction(str1);
    Bundle localBundle = paramIntent.getExtras();
    String str2 = "";
    String str3 = "";
    String str4 = "";
    String str5 = "";
    String str6 = "";
    String str7 = "";
    String str8 = "";
    Uri localUri1 = null;
    int i = 0;
    int j = 0;
    Uri localUri2 = null;
    if (localBundle != null)
    {
      str2 = localBundle.getString("content");
      str3 = localBundle.getString("uids");
      j = localBundle.getInt("msgtype");
      i = localBundle.getInt("msgnum");
      str4 = localBundle.getString("photo_title");
      str5 = localBundle.getString("album_id");
      str8 = localBundle.getString("albumname");
      localUri2 = (Uri)localBundle.getParcelable("android.intent.extra.STREAM");
      str6 = localBundle.getString("diary_title");
      str7 = localBundle.getString("diary_content");
      localUri1 = (Uri)localBundle.getParcelable("diary_photo");
    }
    if (str1.equals("com.kaixin001.SUBMIT_STATUS"))
      this.mThread.setContent(str2);
    if (str1.equals("com.kaixin001.UPDATE_FRIENDS_INFO"))
      this.mThread.setUids(str3);
    if (str1.equals("com.kaixin001.UPDATE_NEW_MESSAGE"))
    {
      this.mThread.setMsgType(j);
      this.mThread.setMsgNum(i);
    }
    if (str1.equals("com.kaixin001.UPLOAD_PHOTO"))
    {
      this.mThread.setPhotoTitle(str4);
      this.mThread.setPhotoUri(localUri2);
      this.mThread.setAlbumId(str5);
      this.mThread.setAlbumName(str8);
    }
    if (str1.equals("com.kaixin001.UPLOAD_DIARY"))
    {
      this.mThread.setDiaryTitle(str6);
      this.mThread.setDiaryContent(str7);
      this.mThread.setDiaryPhotoUri(localUri1);
    }
    this.mThread.start();
  }

  private class KaixinServiceThread extends Thread
  {
    private String mAction = "";
    private String mAlbumId = "";
    private String mAlbumName = "";
    private String mContent = "";
    private String mDiaryContent = "";
    private Uri mDiaryPhotoUri = null;
    private String mDiaryTitle = "";
    private int mMsgNum;
    private int mMsgType;
    private String mPhotoTitle = "";
    private String mUids = "";
    private Uri mUri = null;

    private KaixinServiceThread()
    {
    }

    // ERROR //
    public void run()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial 60	java/lang/Thread:run	()V
      //   4: aload_0
      //   5: getfield 32	com/kaixin001/service/KaixinService$KaixinServiceThread:mAction	Ljava/lang/String;
      //   8: ldc 62
      //   10: invokevirtual 68	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   13: ifeq +46 -> 59
      //   16: invokestatic 74	com/kaixin001/engine/NewsEngine:getInstance	()Lcom/kaixin001/engine/NewsEngine;
      //   19: aload_0
      //   20: getfield 25	com/kaixin001/service/KaixinService$KaixinServiceThread:this$0	Lcom/kaixin001/service/KaixinService;
      //   23: invokevirtual 80	com/kaixin001/service/KaixinService:getApplicationContext	()Landroid/content/Context;
      //   26: invokestatic 85	com/kaixin001/model/NewsModel:getInstance	()Lcom/kaixin001/model/NewsModel;
      //   29: ldc 87
      //   31: bipush 20
      //   33: invokestatic 91	java/lang/String:valueOf	(I)Ljava/lang/String;
      //   36: ldc 30
      //   38: ldc 30
      //   40: ldc 30
      //   42: ldc 30
      //   44: invokevirtual 95	com/kaixin001/engine/NewsEngine:getNewsData	(Landroid/content/Context;Lcom/kaixin001/model/NewsModel;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
      //   47: istore 52
      //   49: aload_0
      //   50: getfield 25	com/kaixin001/service/KaixinService$KaixinServiceThread:this$0	Lcom/kaixin001/service/KaixinService;
      //   53: iload 52
      //   55: invokestatic 99	com/kaixin001/service/KaixinService:access$0	(Lcom/kaixin001/service/KaixinService;Z)V
      //   58: return
      //   59: aload_0
      //   60: getfield 32	com/kaixin001/service/KaixinService$KaixinServiceThread:mAction	Ljava/lang/String;
      //   63: ldc 101
      //   65: invokevirtual 68	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   68: ifeq +42 -> 110
      //   71: invokestatic 106	com/kaixin001/engine/StatusEngine:getInstance	()Lcom/kaixin001/engine/StatusEngine;
      //   74: aload_0
      //   75: getfield 25	com/kaixin001/service/KaixinService$KaixinServiceThread:this$0	Lcom/kaixin001/service/KaixinService;
      //   78: invokevirtual 80	com/kaixin001/service/KaixinService:getApplicationContext	()Landroid/content/Context;
      //   81: aload_0
      //   82: getfield 34	com/kaixin001/service/KaixinService$KaixinServiceThread:mContent	Ljava/lang/String;
      //   85: invokevirtual 110	com/kaixin001/engine/StatusEngine:updateStatus	(Landroid/content/Context;Ljava/lang/String;)Z
      //   88: istore 51
      //   90: aload_0
      //   91: getfield 25	com/kaixin001/service/KaixinService$KaixinServiceThread:this$0	Lcom/kaixin001/service/KaixinService;
      //   94: iload 51
      //   96: invokestatic 113	com/kaixin001/service/KaixinService:access$1	(Lcom/kaixin001/service/KaixinService;Z)V
      //   99: return
      //   100: astore_1
      //   101: ldc 115
      //   103: ldc 116
      //   105: aload_1
      //   106: invokestatic 122	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      //   109: return
      //   110: aload_0
      //   111: getfield 32	com/kaixin001/service/KaixinService$KaixinServiceThread:mAction	Ljava/lang/String;
      //   114: ldc 124
      //   116: invokevirtual 68	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   119: ifeq +29 -> 148
      //   122: invokestatic 129	com/kaixin001/engine/FriendsEngine:getInstance	()Lcom/kaixin001/engine/FriendsEngine;
      //   125: aload_0
      //   126: getfield 25	com/kaixin001/service/KaixinService$KaixinServiceThread:this$0	Lcom/kaixin001/service/KaixinService;
      //   129: invokevirtual 80	com/kaixin001/service/KaixinService:getApplicationContext	()Landroid/content/Context;
      //   132: iconst_1
      //   133: invokevirtual 133	com/kaixin001/engine/FriendsEngine:getFriendsList	(Landroid/content/Context;I)Z
      //   136: istore 50
      //   138: aload_0
      //   139: getfield 25	com/kaixin001/service/KaixinService$KaixinServiceThread:this$0	Lcom/kaixin001/service/KaixinService;
      //   142: iload 50
      //   144: invokestatic 136	com/kaixin001/service/KaixinService:access$2	(Lcom/kaixin001/service/KaixinService;Z)V
      //   147: return
      //   148: aload_0
      //   149: getfield 32	com/kaixin001/service/KaixinService$KaixinServiceThread:mAction	Ljava/lang/String;
      //   152: ldc 138
      //   154: invokevirtual 68	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   157: ifeq +32 -> 189
      //   160: invokestatic 143	com/kaixin001/engine/FriendsInfoEngine:getInstance	()Lcom/kaixin001/engine/FriendsInfoEngine;
      //   163: aload_0
      //   164: getfield 25	com/kaixin001/service/KaixinService$KaixinServiceThread:this$0	Lcom/kaixin001/service/KaixinService;
      //   167: invokevirtual 80	com/kaixin001/service/KaixinService:getApplicationContext	()Landroid/content/Context;
      //   170: aload_0
      //   171: getfield 36	com/kaixin001/service/KaixinService$KaixinServiceThread:mUids	Ljava/lang/String;
      //   174: invokevirtual 146	com/kaixin001/engine/FriendsInfoEngine:getFriendsStatus	(Landroid/content/Context;Ljava/lang/String;)Z
      //   177: istore 49
      //   179: aload_0
      //   180: getfield 25	com/kaixin001/service/KaixinService$KaixinServiceThread:this$0	Lcom/kaixin001/service/KaixinService;
      //   183: iload 49
      //   185: invokestatic 149	com/kaixin001/service/KaixinService:access$3	(Lcom/kaixin001/service/KaixinService;Z)V
      //   188: return
      //   189: aload_0
      //   190: getfield 32	com/kaixin001/service/KaixinService$KaixinServiceThread:mAction	Ljava/lang/String;
      //   193: ldc 151
      //   195: invokevirtual 68	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   198: ifeq +50 -> 248
      //   201: invokestatic 74	com/kaixin001/engine/NewsEngine:getInstance	()Lcom/kaixin001/engine/NewsEngine;
      //   204: aload_0
      //   205: getfield 25	com/kaixin001/service/KaixinService$KaixinServiceThread:this$0	Lcom/kaixin001/service/KaixinService;
      //   208: invokevirtual 80	com/kaixin001/service/KaixinService:getApplicationContext	()Landroid/content/Context;
      //   211: invokestatic 154	com/kaixin001/model/NewsModel:getMyHomeModel	()Lcom/kaixin001/model/NewsModel;
      //   214: ldc 87
      //   216: bipush 20
      //   218: invokestatic 91	java/lang/String:valueOf	(I)Ljava/lang/String;
      //   221: ldc 30
      //   223: ldc 30
      //   225: ldc 30
      //   227: invokestatic 159	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
      //   230: invokevirtual 163	com/kaixin001/model/User:getUID	()Ljava/lang/String;
      //   233: invokevirtual 166	com/kaixin001/engine/NewsEngine:getHomeForData	(Landroid/content/Context;Lcom/kaixin001/model/NewsModel;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
      //   236: istore 48
      //   238: aload_0
      //   239: getfield 25	com/kaixin001/service/KaixinService$KaixinServiceThread:this$0	Lcom/kaixin001/service/KaixinService;
      //   242: iload 48
      //   244: invokestatic 169	com/kaixin001/service/KaixinService:access$4	(Lcom/kaixin001/service/KaixinService;Z)V
      //   247: return
      //   248: aload_0
      //   249: getfield 32	com/kaixin001/service/KaixinService$KaixinServiceThread:mAction	Ljava/lang/String;
      //   252: ldc 171
      //   254: invokevirtual 68	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   257: ifeq +252 -> 509
      //   260: aload_0
      //   261: getfield 173	com/kaixin001/service/KaixinService$KaixinServiceThread:mMsgNum	I
      //   264: ifle +1163 -> 1427
      //   267: aload_0
      //   268: getfield 175	com/kaixin001/service/KaixinService$KaixinServiceThread:mMsgType	I
      //   271: tableswitch	default:+1157 -> 1428, 1:+37->308, 2:+73->344, 3:+109->380, 4:+140->411, 5:+170->441, 6:+206->477
      //   309: nop
      //   310: getfield 10932	package com.kaixin001.service;

      import android.app.Service;
      import android.content.Intent;
      import android.content.SharedPreferences;
      import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
      import android.os.IBinder;
      import android.preference.PreferenceManager;
      import com.kaixin001.engine.KXEngine;
      import com.kaixin001.engine.KXPushEngine;
      import com.kaixin001.model.ChatModel;
      import com.kaixin001.util.ActivityUtil;
      import com.kaixin001.util.BlockedCircleDBManager;
      import com.kaixin001.util.InnerPushManager;
      import com.kaixin001.util.KXLog;
      import com.kaixin001.util.TimeUtil;
      import java.util.Calendar;

      public class RefreshNewMessageService extends Service
      {
        private static final int ACTIVE_REFRESH_INTERVAL = 30000;
        private static final int CHAT_MODE_NORMAL = 0;
        private static final int CHAT_MODE_SILENT = 1;
        private static int CURRENT_MODE = 0;
        private static final int DEFAULT_REFRESH_INTERVAL = 600000;
        private static final int NEW_MESSAGE_NOTIFICATION = 201;
        private static final int SLEEP_INTERVAL = 2000;
        private static final int[] getPushTimes;
        private static volatile int mInterval;
        private static boolean needWakeUp;
        private static int presetRefreshInterval;
        private static RefreshNewMessageService sInstance = null;
        private int mLastGetPushHour = 0;
        private int mLastGetPushMinute = 0;
        private RefreshNewMessageThread mThread = null;
        private SharedPreferences preference;
        private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

        static
        {
          mInterval = 600000;
          presetRefreshInterval = 600000;
          CURRENT_MODE = 0;
          needWakeUp = false;
          getPushTimes = new int[] { 1005, 1205, 1405, 1605, 1805, 2005, 2105, 1935 };
        }

        private void getHoroscopePushMessage(boolean paramBoolean)
        {
          int i = TimeUtil.getIntHour();
          if (!paramBoolean);
          do
            return;
          while ((i < 9) || (i > 11) || (InnerPushManager.getInstance(this).isTodayGetHorosd()));
          try
          {
            KXPushEngine.getInstance().getHoroscopePush(this);
            InnerPushManager.getInstance(this).setTodayGetHorosd(true);
            return;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }

        public static RefreshNewMessageService getInstance()
        {
          return sInstance;
        }

        private void getKXCityPushMesssage(boolean paramBoolean)
        {
          if (!paramBoolean);
          int i;
          int j;
          do
          {
            return;
            Calendar localCalendar = Calendar.getInstance();
            i = localCalendar.get(11);
            j = localCalendar.get(12);
          }
          while ((i == this.mLastGetPushHour) && (j == this.mLastGetPushMinute));
          int k = j + i * 100;
          int m = 100 * this.mLastGetPushHour + this.mLastGetPushMinute;
          if (k < m)
          {
            m = 0;
            this.mLastGetPushHour = 0;
            this.mLastGetPushMinute = 0;
          }
          label171: label173: for (int n = 0; ; n++)
          {
            int i1 = getPushTimes.length;
            int i2 = 0;
            if (n >= i1);
            while (true)
            {
              while (true)
              {
                if (i2 == 0)
                  break label171;
                try
                {
                  if ((!ActivityUtil.needNotification(this)) || (!KXPushEngine.getInstance().getPushMessage(this)))
                    break;
                  this.mLastGetPushHour = i;
                  this.mLastGetPushMinute = j;
                  return;
                }
                catch (Exception localException)
                {
                  localException.printStackTrace();
                  return;
                }
              }
              if ((k < getPushTimes[n]) || (m >= getPushTimes[n]))
                break label173;
              i2 = 1;
            }
            break;
          }
        }

        public static boolean isNormalMode()
        {
          return CURRENT_MODE == 0;
        }

        private static void setInterval(String paramString)
        {
          mInterval = Integer.parseInt(paramString);
          presetRefreshInterval = mInterval;
        }

        public static void setWakeUp(boolean paramBoolean)
        {
          monitorenter;
          try
          {
            needWakeUp = paramBoolean;
            monitorexit;
            return;
          }
          finally
          {
            localObject = finally;
            monitorexit;
          }
          throw localObject;
        }

        public IBinder onBind(Intent paramIntent)
        {
          return null;
        }

        public void onCreate()
        {
          super.onCreate();
          sInstance = this;
          this.preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
          setInterval(this.preference.getString("notification_refresh_interval", String.valueOf(600000)));
          this.preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener()
          {
            public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
            {
              RefreshNewMessageService.access$5(RefreshNewMessageService.this.preference.getString("notification_refresh_interval", String.valueOf(600000)));
              KXLog.d("preferenceChangeListener", "mInterval=" + RefreshNewMessageService.mInterval);
            }
          };
          this.preference.registerOnSharedPreferenceChangeListener(this.preferenceChangeListener);
          try
          {
            ChatModel.getInstance().setBlockedCircleManager(new BlockedCircleDBManager(this));
            setInterval(true);
            return;
          }
          catch (Exception localException)
          {
            while (true)
              localException.printStackTrace();
          }
        }

        public void onDestroy()
        {
          this.preference.unregisterOnSharedPreferenceChangeListener(this.preferenceChangeListener);
          this.preferenceChangeListener = null;
          if (this.mThread != null)
          {
            this.mThread.mStopFlag = true;
            this.mThread = null;
          }
          sInstance = null;
          super.onDestroy();
        }

        public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
        {
          super.onStartCommand(paramIntent, paramInt1, paramInt2);
          if (this.mThread != null)
            this.mThread.mStopFlag = true;
          this.mThread = new RefreshNewMessageThread(null);
          this.mThread.start();
          KXLog.d("TEST", "SERVICE  ON START COMMAND");
          return 1;
        }

        public void setInterval(boolean paramBoolean)
        {
          if (paramBoolean)
          {
            KXLog.d("TEST", "switchInterval to active");
            mInterval = 30000;
            setWakeUp(true);
            return;
          }
          KXLog.d("TEST", "switchInterval to normal");
          mInterval = presetRefreshInterval;
        }

        public void switchMode(boolean paramBoolean)
        {
          if (paramBoolean)
          {
            KXLog.d("TEST", "switchMode to silent");
            CURRENT_MODE = 1;
            KXEngine.cancelNewChatNotification(this);
            return;
          }
          KXLog.d("TEST", "switchMode to nomal");
          CURRENT_MODE = 0;
        }

        private class RefreshNewMessageThread extends Thread
        {
          public volatile boolean mStopFlag = false;

          private RefreshNewMessageThread()
          {
          }

          // ERROR //
          public void run()
          {
            // Byte code:
            //   0: aload_0
            //   1: invokespecial 25	java/lang/Thread:run	()V
            //   4: aload_0
            //   5: getfield 17	com/kaixin001/service/RefreshNewMessageService$RefreshNewMessageThread:mStopFlag	Z
            //   8: ifeq +4 -> 12
            //   11: return
            //   12: ldc 27
            //   14: ldc 28
            //   16: invokestatic 34	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
            //   19: aload_0
            //   20: getfield 12	com/kaixin001/service/RefreshNewMessageService$RefreshNewMessageThread:this$0	Lcom/kaixin001/service/RefreshNewMessageService;
            //   23: invokestatic 40	com/kaixin001/util/InnerPushManager:getInstance	(Landroid/content/Context;)Lcom/kaixin001/util/InnerPushManager;
            //   26: invokevirtual 43	com/kaixin001/util/InnerPushManager:beginInnerPush	()V
            //   29: aload_0
            //   30: getfield 12	com/kaixin001/service/RefreshNewMessageService$RefreshNewMessageThread:this$0	Lcom/kaixin001/service/RefreshNewMessageService;
            //   33: invokestatic 49	com/kaixin001/util/ActivityUtil:needNotification	(Landroid/content/Context;)Z
            //   36: istore_2
            //   37: aload_0
            //   38: getfield 12	com/kaixin001/service/RefreshNewMessageService$RefreshNewMessageThread:this$0	Lcom/kaixin001/service/RefreshNewMessageService;
            //   41: iload_2
            //   42: invokestatic 55	com/kaixin001/service/RefreshNewMessageService:access$0	(Lcom/kaixin001/service/RefreshNewMessageService;Z)V
            //   45: aload_0
            //   46: getfield 12	com/kaixin001/service/RefreshNewMessageService$RefreshNewMessageThread:this$0	Lcom/kaixin001/service/RefreshNewMessageService;
            //   49: iload_2
            //   50: invokestatic 58	com/kaixin001/service/RefreshNewMessageService:access$1	(Lcom/kaixin001/service/RefreshNewMessageService;Z)V
            //   53: iload_2
            //   54: ifeq +60 -> 114
            //   57: invokestatic 63	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
            //   60: invokevirtual 67	com/kaixin001/model/User:getUID	()Ljava/lang/String;
            //   63: invokestatic 73	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
            //   66: ifeq +14 -> 80
            //   69: invokestatic 63	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
            //   72: aload_0
            //   73: getfield 12	com/kaixin001/service/RefreshNewMessageService$RefreshNewMessageThread:this$0	Lcom/kaixin001/service/RefreshNewMessageService;
            //   76: invokevirtual 76	com/kaixin001/model/User:loadUserData	(Landroid/content/Context;)Z
            //   79: pop
            //   80: invokestatic 81	com/kaixin001/engine/ChatEngine:getInstance	()Lcom/kaixin001/engine/ChatEngine;
            //   83: astore_3
            //   84: invokestatic 86	com/kaixin001/engine/MessageCenterEngine:getInstance	()Lcom/kaixin001/engine/MessageCenterEngine;
            //   87: astore 4
            //   89: aload 4
            //   91: aload_0
            //   92: getfield 12	com/kaixin001/service/RefreshNewMessageService$RefreshNewMessageThread:this$0	Lcom/kaixin001/service/RefreshNewMessageService;
            //   95: invokestatic 63	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
            //   98: invokevirtual 67	com/kaixin001/model/User:getUID	()Ljava/lang/String;
            //   101: invokevirtual 90	com/kaixin001/engine/MessageCenterEngine:checkNewMessage	(Landroid/content/Context;Ljava/lang/String;)Z
            //   104: pop
            //   105: aload_3
            //   106: aload_0
            //   107: getfield 12	com/kaixin001/service/RefreshNewMessageService$RefreshNewMessageThread:this$0	Lcom/kaixin001/service/RefreshNewMessageService;
            //   110: invokevirtual 94	com/kaixin001/engine/ChatEngine:getNewChatMessages	(Landroid/content/Context;)I
            //   113: pop
            //   114: invokestatic 100	android/os/SystemClock:elapsedRealtime	()J
            //   117: invokestatic 104	com/kaixin001/service/RefreshNewMessageService:access$2	()I
            //   120: i2l
            //   121: ladd
            //   122: lstore 6
            //   124: invokestatic 108	com/kaixin001/service/RefreshNewMessageService:access$3	()Z
            //   127: ifne +19 -> 146
            //   130: aload_0
            //   131: getfield 17	com/kaixin001/service/RefreshNewMessageService$RefreshNewMessageThread:mStopFlag	Z
            //   134: ifne +12 -> 146
            //   137: lload 6
            //   139: invokestatic 100	android/os/SystemClock:elapsedRealtime	()J
            //   142: lcmp
            //   143: ifgt +18 -> 161
            //   146: iconst_0
            //   147: invokestatic 112	com/kaixin001/service/RefreshNewMessageService:setWakeUp	(Z)V
            //   150: goto -146 -> 4
            //   153: astore_1
            //   154: aload_1
            //   155: invokevirtual 115	java/lang/Exception:printStackTrace	()V
            //   158: goto -154 -> 4
            //   161: ldc2_w 116
            //   164: invokestatic 121	com/kaixin001/service/RefreshNewMessageService$RefreshNewMessageThread:sleep	(J)V
            //   167: goto -43 -> 124
            //   170: astore 5
            //   172: goto -58 -> 114
            //
            // Exception table:
            //   from	to	target	type
            //   19	53	153	java/lang/Exception
            //   57	80	153	java/lang/Exception
            //   80	89	153	java/lang/Exception
            //   114	124	153	java/lang/Exception
            //   124	146	153	java/lang/Exception
            //   146	150	153	java/lang/Exception
            //   161	167	153	java/lang/Exception
            //   89	114	170	java/lang/Exception
          }
        }
      }

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.service.RefreshNewMessageService
 * JD-Core Version:    0.6.0
 */