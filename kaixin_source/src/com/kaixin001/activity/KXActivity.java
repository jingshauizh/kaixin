package com.kaixin001.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.db.UserContentObserver;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.fragment.UploadTaskListFragment;
import com.kaixin001.fragment.WriteDiaryFragment;
import com.kaixin001.fragment.WriteWeiboFragment;
import com.kaixin001.model.ChatModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.task.GetChatContextTask;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXUBLog;
import com.kaixin001.util.StringUtil;
import com.umeng.analytics.MobclickAgent;
import java.io.File;
import java.util.ArrayList;

public abstract class KXActivity extends Activity
{
  private static int DISTANCE = 0;
  public static final int EDIT_PHOTO = 104;
  public static final int EDIT_PHOTO_FROMWEIBO = 105;
  protected static final String FROM_CAMERA = "camera";
  protected static final String FROM_GALLERY = "gallery";
  private static final String FROM_WEBPAGE = "from_webpage";
  public static final String FROM_WIDGET = "from_widget";
  private static final float SPEED = 1.0F;
  private static final String TAG = "KXActivity";
  protected static final String TEMP_CAMERA_PICTURE_FILENAME = "kx_upload_tmp.jpg";
  public static final int UPLOAD_PHOTO_CAMERA = 101;
  public static final int UPLOAD_PHOTO_GALLERY = 102;
  public static final int UPLOAD_PHOTO_NEW = 103;
  protected static String mActivityId = "KXActivity";
  public Dialog dialog;
  private UserContentObserver mContentObserver = null;
  protected int mCurrVolume;
  private boolean mDetectHorizontalSlide = false;
  private boolean mDisallowDetectSlide = false;
  private boolean mEnableSlideBack = true;
  private Toast mGlobalToast = null;
  protected Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      if (!KXActivity.this.handleMessage(paramMessage))
        super.handleMessage(paramMessage);
    }
  };
  private boolean mLevel1Page = false;
  private PopupWindow mSettingOptionPopupWindow;
  private long startTime;
  private float startX;
  private float startY;

  static
  {
    DISTANCE = -1;
  }

  private void closeSpeaker()
  {
    try
    {
      AudioManager localAudioManager = (AudioManager)getSystemService("audio");
      if ((localAudioManager != null) && (localAudioManager.isSpeakerphoneOn()))
      {
        localAudioManager.setSpeakerphoneOn(false);
        localAudioManager.setStreamVolume(0, this.mCurrVolume, 0);
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void handleCantAddmoreDialogOptions(int paramInt)
  {
    if (paramInt == -1)
      AnimationUtil.startActivity(this, new Intent(this, UploadTaskListFragment.class), 1);
  }

  private void openSpeaker()
  {
    try
    {
      AudioManager localAudioManager = (AudioManager)getSystemService("audio");
      localAudioManager.setMode(2);
      this.mCurrVolume = localAudioManager.getStreamVolume(0);
      if (!localAudioManager.isSpeakerphoneOn())
      {
        localAudioManager.setSpeakerphoneOn(true);
        localAudioManager.setStreamVolume(0, localAudioManager.getStreamMaxVolume(0), 0);
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  protected boolean cancelTask(AsyncTask paramAsyncTask)
  {
    if ((paramAsyncTask != null) && (paramAsyncTask.getStatus() != AsyncTask.Status.FINISHED) && (!paramAsyncTask.isCancelled()))
    {
      paramAsyncTask.cancel(true);
      return true;
    }
    return false;
  }

  protected void changePhoneVoiceType()
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    if (localSharedPreferences.getBoolean("openspeaker_set", false));
    for (boolean bool = false; ; bool = true)
    {
      localSharedPreferences.edit().putBoolean("openspeaker_set", bool);
      if (!bool)
        break;
      openSpeaker();
      return;
    }
    closeSpeaker();
  }

  protected boolean checkNetworkAndHint(boolean paramBoolean)
  {
    return HttpConnection.checkNetworkAndHint(paramBoolean, this);
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
  }

  public int dipToPx(float paramFloat)
  {
    return (int)(0.5F + paramFloat * getResources().getDisplayMetrics().density);
  }

  public void disallowDetectSlide(boolean paramBoolean)
  {
    this.mDisallowDetectSlide = paramBoolean;
  }

  public void dismissDialog()
  {
    if ((this.dialog != null) && (this.dialog.isShowing()))
      this.dialog.dismiss();
    this.dialog = null;
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0)
      this.mDisallowDetectSlide = false;
    if ((!this.mEnableSlideBack) || (this.mDisallowDetectSlide))
      return super.dispatchTouchEvent(paramMotionEvent);
    if (DISTANCE < 0)
      DISTANCE = getResources().getDisplayMetrics().widthPixels / 4;
    float f1;
    if (paramMotionEvent.getAction() == 0)
    {
      if ((getParent() != null) && ((getParent() instanceof MainActivity)))
      {
        this.mLevel1Page = true;
        this.mDetectHorizontalSlide = false;
      }
    }
    else if ((!this.mLevel1Page) && (this.mDetectHorizontalSlide) && (paramMotionEvent.getAction() == 2))
    {
      f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      if (f1 < this.startX)
        this.mDetectHorizontalSlide = false;
      if (Math.abs(f1 - this.startX) >= Math.abs(f2 - this.startY))
        break label202;
      this.mDetectHorizontalSlide = false;
    }
    while (true)
    {
      return super.dispatchTouchEvent(paramMotionEvent);
      this.mLevel1Page = false;
      this.mDetectHorizontalSlide = true;
      this.startX = paramMotionEvent.getX();
      this.startY = paramMotionEvent.getY();
      this.startTime = System.currentTimeMillis();
      break;
      label202: long l = System.currentTimeMillis();
      float f3 = f1 - this.startX;
      float f4 = f3 / (float)(l - this.startTime);
      if ((f3 <= DISTANCE) || (f4 <= 1.0F))
        continue;
      this.mDetectHorizontalSlide = false;
      paramMotionEvent.setAction(3);
      requestFinish();
    }
  }

  public void doPauseFromRightSide()
  {
  }

  public void doResumeFromRightSide()
  {
  }

  public void enableSlideBack(boolean paramBoolean)
  {
    this.mEnableSlideBack = paramBoolean;
  }

  protected boolean getCurrentPhoneVoiceType()
  {
    return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("openspeaker_set", false);
  }

  protected boolean handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
      return false;
    case 2:
      finish();
      return true;
    case 10000:
      finish();
      return true;
    case 40051:
      finish();
      return true;
    case 90010:
      GetChatContextTask localGetChatContextTask;
      ArrayList localArrayList;
      StringBuilder localStringBuilder;
      if ((paramMessage.obj != null) && ((paramMessage.obj instanceof ArrayList)))
      {
        localGetChatContextTask = new GetChatContextTask(this);
        localArrayList = (ArrayList)paramMessage.obj;
        localStringBuilder = new StringBuilder("ready to GetChatContextTask:");
        if (localArrayList != null)
          break label170;
      }
      for (String str = "0"; ; str = localArrayList.size())
      {
        KXLog.d("TEST", str);
        localGetChatContextTask.execute(new ArrayList[] { localArrayList });
        return true;
      }
    case 90007:
      label170: return ChatModel.getInstance().ensureFriendsConversiton();
    case 90006:
    }
    return ChatModel.getInstance().ensureCirclesConversiton();
  }

  public void hideSubActivityInMain()
  {
    Activity localActivity = getParent();
    if ((localActivity != null) && ((localActivity instanceof MainActivity)))
      ((MainActivity)localActivity).showKaixinList();
  }

  protected void insertFaceIcon(int paramInt)
  {
  }

  public boolean isMenuListVisibleInMain()
  {
    Activity localActivity = getParent();
    if ((localActivity != null) && ((localActivity instanceof MainActivity)))
      return ((MainActivity)localActivity).isMenuListVisible();
    return false;
  }

  // ERROR //
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    // Byte code:
    //   0: iload_2
    //   1: iconst_m1
    //   2: if_icmpne +31 -> 33
    //   5: iload_1
    //   6: sipush 209
    //   9: if_icmpne +32 -> 41
    //   12: aload_3
    //   13: ldc_w 373
    //   16: iconst_m1
    //   17: invokevirtual 377	android/content/Intent:getIntExtra	(Ljava/lang/String;I)I
    //   20: istore 36
    //   22: iload 36
    //   24: iflt +9 -> 33
    //   27: aload_0
    //   28: iload 36
    //   30: invokevirtual 379	com/kaixin001/activity/KXActivity:insertFaceIcon	(I)V
    //   33: aload_0
    //   34: iload_1
    //   35: iload_2
    //   36: aload_3
    //   37: invokespecial 381	android/app/Activity:onActivityResult	(IILandroid/content/Intent;)V
    //   40: return
    //   41: iload_1
    //   42: bipush 101
    //   44: if_icmpne +459 -> 503
    //   47: new 311	java/lang/StringBuilder
    //   50: dup
    //   51: invokespecial 382	java/lang/StringBuilder:<init>	()V
    //   54: invokestatic 388	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   57: invokevirtual 391	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   60: ldc_w 393
    //   63: invokevirtual 324	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: ldc_w 395
    //   69: invokevirtual 324	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: ldc 33
    //   74: invokevirtual 324	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: invokevirtual 328	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   80: astore 13
    //   82: iconst_0
    //   83: istore 14
    //   85: aload_3
    //   86: ifnull +38 -> 124
    //   89: aload_3
    //   90: invokevirtual 399	android/content/Intent:getExtras	()Landroid/os/Bundle;
    //   93: astore 34
    //   95: iconst_0
    //   96: istore 14
    //   98: aload 34
    //   100: ifnull +24 -> 124
    //   103: aload 34
    //   105: ldc_w 401
    //   108: invokevirtual 406	android/os/Bundle:get	(Ljava/lang/String;)Ljava/lang/Object;
    //   111: astore 35
    //   113: iconst_0
    //   114: istore 14
    //   116: aload 35
    //   118: ifnull +6 -> 124
    //   121: iconst_1
    //   122: istore 14
    //   124: iload 14
    //   126: ifne +156 -> 282
    //   129: aconst_null
    //   130: astore 22
    //   132: aload 13
    //   134: sipush 300
    //   137: invokestatic 412	com/kaixin001/util/FileUtil:loadBitmapFromFile	(Ljava/lang/String;I)Landroid/graphics/Bitmap;
    //   140: astore 22
    //   142: aload_0
    //   143: invokevirtual 416	com/kaixin001/activity/KXActivity:getContentResolver	()Landroid/content/ContentResolver;
    //   146: aload 22
    //   148: aconst_null
    //   149: aconst_null
    //   150: invokestatic 422	android/provider/MediaStore$Images$Media:insertImage	(Landroid/content/ContentResolver;Landroid/graphics/Bitmap;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   153: invokestatic 428	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   156: ifne +116 -> 272
    //   159: aload_0
    //   160: invokestatic 432	com/kaixin001/util/FileUtil:loadLatestPic	(Landroid/content/Context;)Ljava/lang/String;
    //   163: astore 26
    //   165: iconst_1
    //   166: istore 14
    //   168: new 434	java/io/File
    //   171: dup
    //   172: new 311	java/lang/StringBuilder
    //   175: dup
    //   176: invokespecial 382	java/lang/StringBuilder:<init>	()V
    //   179: invokestatic 388	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   182: invokevirtual 391	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   185: ldc_w 393
    //   188: invokevirtual 324	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   191: invokevirtual 328	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   194: ldc 33
    //   196: invokespecial 436	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   199: astore 27
    //   201: aload 27
    //   203: new 434	java/io/File
    //   206: dup
    //   207: aload 26
    //   209: invokespecial 437	java/io/File:<init>	(Ljava/lang/String;)V
    //   212: invokevirtual 441	java/io/File:renameTo	(Ljava/io/File;)Z
    //   215: pop
    //   216: new 443	android/media/ExifInterface
    //   219: dup
    //   220: aload 26
    //   222: invokespecial 444	android/media/ExifInterface:<init>	(Ljava/lang/String;)V
    //   225: astore 29
    //   227: aload 29
    //   229: astore 30
    //   231: aload 30
    //   233: ifnull +39 -> 272
    //   236: invokestatic 449	com/kaixin001/model/Setting:getInstance	()Lcom/kaixin001/model/Setting;
    //   239: invokevirtual 452	com/kaixin001/model/Setting:getManufacturerName	()Ljava/lang/String;
    //   242: astore 31
    //   244: aload 30
    //   246: ldc_w 454
    //   249: aload 31
    //   251: invokevirtual 457	android/media/ExifInterface:setAttribute	(Ljava/lang/String;Ljava/lang/String;)V
    //   254: invokestatic 449	com/kaixin001/model/Setting:getInstance	()Lcom/kaixin001/model/Setting;
    //   257: invokevirtual 460	com/kaixin001/model/Setting:getDeviceName	()Ljava/lang/String;
    //   260: astore 32
    //   262: aload 30
    //   264: ldc_w 462
    //   267: aload 32
    //   269: invokevirtual 457	android/media/ExifInterface:setAttribute	(Ljava/lang/String;Ljava/lang/String;)V
    //   272: aload 22
    //   274: ifnull +8 -> 282
    //   277: aload 22
    //   279: invokevirtual 467	android/graphics/Bitmap:recycle	()V
    //   282: iload 14
    //   284: ifeq +176 -> 460
    //   287: aconst_null
    //   288: astore 17
    //   290: aload_0
    //   291: getstatic 471	android/provider/MediaStore$Images$Media:EXTERNAL_CONTENT_URI	Landroid/net/Uri;
    //   294: aconst_null
    //   295: aconst_null
    //   296: aconst_null
    //   297: aconst_null
    //   298: invokevirtual 475	com/kaixin001/activity/KXActivity:managedQuery	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   301: astore 17
    //   303: aload 17
    //   305: invokeinterface 480 1 0
    //   310: istore 20
    //   312: aconst_null
    //   313: astore 16
    //   315: iload 20
    //   317: ifeq +26 -> 343
    //   320: aload 17
    //   322: aload 17
    //   324: ldc_w 482
    //   327: invokeinterface 486 2 0
    //   332: invokeinterface 489 2 0
    //   337: astore 21
    //   339: aload 21
    //   341: astore 16
    //   343: aload 17
    //   345: invokestatic 495	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   348: aload_0
    //   349: aload 16
    //   351: ldc 15
    //   353: invokevirtual 497	com/kaixin001/activity/KXActivity:dealPhotoResult	(Ljava/lang/String;Ljava/lang/String;)V
    //   356: goto -323 -> 33
    //   359: astore 33
    //   361: aload 33
    //   363: invokevirtual 122	java/lang/Exception:printStackTrace	()V
    //   366: aconst_null
    //   367: astore 30
    //   369: goto -138 -> 231
    //   372: astore 25
    //   374: aload 25
    //   376: invokevirtual 122	java/lang/Exception:printStackTrace	()V
    //   379: aload 22
    //   381: ifnull -99 -> 282
    //   384: aload 22
    //   386: invokevirtual 467	android/graphics/Bitmap:recycle	()V
    //   389: goto -107 -> 282
    //   392: astore 24
    //   394: aload 24
    //   396: invokevirtual 498	java/lang/OutOfMemoryError:printStackTrace	()V
    //   399: aload 22
    //   401: ifnull -119 -> 282
    //   404: aload 22
    //   406: invokevirtual 467	android/graphics/Bitmap:recycle	()V
    //   409: goto -127 -> 282
    //   412: astore 23
    //   414: aload 22
    //   416: ifnull +8 -> 424
    //   419: aload 22
    //   421: invokevirtual 467	android/graphics/Bitmap:recycle	()V
    //   424: aload 23
    //   426: athrow
    //   427: astore 19
    //   429: ldc 30
    //   431: ldc_w 499
    //   434: aload 19
    //   436: invokestatic 503	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   439: aload 17
    //   441: invokestatic 495	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   444: aconst_null
    //   445: astore 16
    //   447: goto -99 -> 348
    //   450: astore 18
    //   452: aload 17
    //   454: invokestatic 495	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   457: aload 18
    //   459: athrow
    //   460: new 434	java/io/File
    //   463: dup
    //   464: new 311	java/lang/StringBuilder
    //   467: dup
    //   468: invokespecial 382	java/lang/StringBuilder:<init>	()V
    //   471: invokestatic 388	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   474: invokevirtual 391	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   477: ldc_w 393
    //   480: invokevirtual 324	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   483: invokevirtual 328	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   486: ldc 33
    //   488: invokespecial 436	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   491: astore 15
    //   493: aload 15
    //   495: invokevirtual 506	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   498: astore 16
    //   500: goto -152 -> 348
    //   503: iload_1
    //   504: bipush 102
    //   506: if_icmpne +168 -> 674
    //   509: aload_3
    //   510: invokevirtual 510	android/content/Intent:getData	()Landroid/net/Uri;
    //   513: astore 6
    //   515: aload 6
    //   517: invokevirtual 515	android/net/Uri:getScheme	()Ljava/lang/String;
    //   520: ldc_w 517
    //   523: invokevirtual 521	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   526: ifeq +92 -> 618
    //   529: iconst_1
    //   530: anewarray 342	java/lang/String
    //   533: dup
    //   534: iconst_0
    //   535: ldc_w 482
    //   538: aastore
    //   539: astore 7
    //   541: aconst_null
    //   542: astore 8
    //   544: aload_0
    //   545: invokevirtual 416	com/kaixin001/activity/KXActivity:getContentResolver	()Landroid/content/ContentResolver;
    //   548: aload 6
    //   550: aload 7
    //   552: aconst_null
    //   553: aconst_null
    //   554: aconst_null
    //   555: invokevirtual 526	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   558: astore 8
    //   560: aload 8
    //   562: invokeinterface 529 1 0
    //   567: pop
    //   568: aload 8
    //   570: aload 8
    //   572: aload 7
    //   574: iconst_0
    //   575: aaload
    //   576: invokeinterface 532 2 0
    //   581: invokeinterface 489 2 0
    //   586: astore 11
    //   588: aload 11
    //   590: astore 12
    //   592: aload 8
    //   594: invokestatic 495	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   597: aload_0
    //   598: aload 12
    //   600: ldc 18
    //   602: invokevirtual 497	com/kaixin001/activity/KXActivity:dealPhotoResult	(Ljava/lang/String;Ljava/lang/String;)V
    //   605: goto -572 -> 33
    //   608: astore 9
    //   610: aload 8
    //   612: invokestatic 495	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   615: aload 9
    //   617: athrow
    //   618: aload 6
    //   620: invokevirtual 515	android/net/Uri:getScheme	()Ljava/lang/String;
    //   623: ldc_w 534
    //   626: invokevirtual 521	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   629: ifeq +13 -> 642
    //   632: aload 6
    //   634: invokevirtual 537	android/net/Uri:getPath	()Ljava/lang/String;
    //   637: astore 12
    //   639: goto -42 -> 597
    //   642: ldc 30
    //   644: new 311	java/lang/StringBuilder
    //   647: dup
    //   648: ldc_w 539
    //   651: invokespecial 316	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   654: aload 6
    //   656: invokevirtual 515	android/net/Uri:getScheme	()Ljava/lang/String;
    //   659: invokevirtual 324	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   662: invokevirtual 328	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   665: invokestatic 541	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   668: aconst_null
    //   669: astore 12
    //   671: goto -74 -> 597
    //   674: iload_1
    //   675: bipush 103
    //   677: if_icmpne -644 -> 33
    //   680: aload_3
    //   681: invokevirtual 399	android/content/Intent:getExtras	()Landroid/os/Bundle;
    //   684: astore 4
    //   686: aload 4
    //   688: ifnull -655 -> 33
    //   691: aload 4
    //   693: ldc_w 543
    //   696: invokevirtual 546	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   699: astore 5
    //   701: aload 5
    //   703: invokestatic 428	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   706: ifne -673 -> 33
    //   709: aload_0
    //   710: aload 5
    //   712: ldc 18
    //   714: invokevirtual 497	com/kaixin001/activity/KXActivity:dealPhotoResult	(Ljava/lang/String;Ljava/lang/String;)V
    //   717: goto -684 -> 33
    //
    // Exception table:
    //   from	to	target	type
    //   216	227	359	java/lang/Exception
    //   132	165	372	java/lang/Exception
    //   168	216	372	java/lang/Exception
    //   236	272	372	java/lang/Exception
    //   361	366	372	java/lang/Exception
    //   132	165	392	java/lang/OutOfMemoryError
    //   168	216	392	java/lang/OutOfMemoryError
    //   216	227	392	java/lang/OutOfMemoryError
    //   236	272	392	java/lang/OutOfMemoryError
    //   361	366	392	java/lang/OutOfMemoryError
    //   132	165	412	finally
    //   168	216	412	finally
    //   216	227	412	finally
    //   236	272	412	finally
    //   361	366	412	finally
    //   374	379	412	finally
    //   394	399	412	finally
    //   290	312	427	java/lang/Exception
    //   320	339	427	java/lang/Exception
    //   290	312	450	finally
    //   320	339	450	finally
    //   429	439	450	finally
    //   544	588	608	finally
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    MessageHandlerHolder.getInstance().addHandler(this.mHandler);
    Uri localUri = Uri.parse("content://com.kaixin001.provider/user");
    this.mContentObserver = new UserContentObserver(this.mHandler);
    getContentResolver().registerContentObserver(localUri, false, this.mContentObserver);
    User.getInstance().loadDataIfEmpty(getApplicationContext());
    Bundle localBundle = getIntent().getExtras();
    if ((localBundle != null) && (localBundle.getBoolean("from_widget", false)))
      KXUBLog.log("open_Droid");
  }

  protected void onDestroy()
  {
    MessageHandlerHolder.getInstance().removeHandler(this.mHandler);
    if (this.mContentObserver != null)
    {
      getContentResolver().unregisterContentObserver(this.mContentObserver);
      this.mContentObserver = null;
    }
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    Activity localActivity = getParent();
    if ((localActivity != null) && ((localActivity instanceof MainActivity)))
    {
      MainActivity localMainActivity = (MainActivity)localActivity;
      if ((paramInt == 4) || (paramInt == 82))
        return localMainActivity.onKeyDown(paramInt, paramKeyEvent);
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  protected void onPause()
  {
    super.onPause();
    if (!Setting.getInstance().isTestVersion())
      MobclickAgent.onPause(this);
  }

  protected void onResume()
  {
    super.onResume();
    if (!Setting.getInstance().isTestVersion())
      MobclickAgent.onResume(this);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return super.onTouchEvent(paramMotionEvent);
  }

  public int pxTodip(float paramFloat)
  {
    return (int)(0.5F + paramFloat / getResources().getDisplayMetrics().density);
  }

  public void requestFinish()
  {
    finish();
  }

  public Dialog showCantUploadOptions()
  {
    return DialogUtil.showKXAlertDialog(this, StringUtil.replaceTokenWith(getApplicationContext().getResources().getString(2131427640), "*", String.valueOf(UploadTaskListEngine.getInstance().getWaitingTaskCount())), 2131427639, 2131427587, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        KXActivity.this.handleCantAddmoreDialogOptions(paramInt);
      }
    }
    , new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        KXActivity.this.handleCantAddmoreDialogOptions(paramInt);
      }
    });
  }

  public void showMenuListInMain()
  {
    Activity localActivity = getParent();
    if ((localActivity != null) && ((localActivity instanceof MainActivity)))
      ((MainActivity)localActivity).showKaixinList();
  }

  public void showSubActivityInMain()
  {
    Activity localActivity = getParent();
    if ((localActivity != null) && ((localActivity instanceof MainActivity)))
      ((MainActivity)localActivity).showSubFragment();
  }

  protected void showToast(int paramInt)
  {
    showToast(getResources().getString(paramInt));
  }

  public void showToast(int paramInt1, int paramInt2)
  {
    View localView = getLayoutInflater().inflate(2130903261, null);
    ImageView localImageView = (ImageView)localView.findViewById(2131363278);
    TextView localTextView = (TextView)localView.findViewById(2131363279);
    if (paramInt1 != 0)
    {
      localImageView.setImageResource(paramInt1);
      localImageView.setVisibility(0);
      if (paramInt2 == 0)
        break label112;
      localTextView.setText(paramInt2);
      localTextView.setVisibility(0);
    }
    while (true)
    {
      Toast localToast = new Toast(getApplicationContext());
      localToast.setGravity(17, 0, 0);
      localToast.setView(localView);
      localToast.show();
      return;
      localImageView.setVisibility(8);
      break;
      label112: localTextView.setVisibility(8);
    }
  }

  protected void showToast(String paramString)
  {
    if (this.mGlobalToast != null)
    {
      this.mGlobalToast.cancel();
      this.mGlobalToast.setText(paramString);
      this.mGlobalToast.setDuration(0);
    }
    while (true)
    {
      this.mGlobalToast.show();
      return;
      this.mGlobalToast = Toast.makeText(this, paramString, 0);
    }
  }

  public boolean takePictureWithCamera()
  {
    if (!getPackageManager().hasSystemFeature("android.hardware.camera"))
    {
      Toast.makeText(this, 2131428469, 0).show();
      return false;
    }
    File localFile = new File(Environment.getExternalStorageDirectory() + "/kaixin001", "kx_upload_tmp.jpg");
    if (!FileUtil.makeEmptyFile(localFile))
    {
      Toast.makeText(this, 2131427883, 0).show();
      return false;
    }
    Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
    localIntent.putExtra("output", Uri.fromFile(localFile));
    try
    {
      startActivityForResult(localIntent, 101);
      return true;
    }
    catch (Exception localException)
    {
      Toast.makeText(this, 2131428469, 0).show();
    }
    return false;
  }

  public void writeNewDiary()
  {
    if (UploadTaskListEngine.getInstance().getWaitingTaskCount() < 20)
    {
      startActivity(new Intent(this, WriteDiaryFragment.class));
      return;
    }
    showCantUploadOptions();
  }

  public void writeNewRecord(String paramString)
  {
    if (UploadTaskListEngine.getInstance().getWaitingTaskCount() < 20)
    {
      Intent localIntent = new Intent(this, WriteWeiboFragment.class);
      if (!TextUtils.isEmpty(paramString))
      {
        localIntent.setType("text");
        localIntent.putExtra("android.intent.extra.TEXT", paramString);
      }
      AnimationUtil.startActivity(this, localIntent, 1);
      return;
    }
    showCantUploadOptions();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.KXActivity
 * JD-Core Version:    0.6.0
 */