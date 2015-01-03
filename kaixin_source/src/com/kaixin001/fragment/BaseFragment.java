package com.kaixin001.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
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
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.InputFaceActivity;
import com.kaixin001.activity.LoginActivity;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.activity.MessageHandlerHolder;
import com.kaixin001.activity.PhoneRegisterActivity;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.model.RequestVo;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.network.HttpUtils;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXUBLog;
import com.kaixin001.util.StringUtil;
import com.kaixin001.view.KXSliderLayout2;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;

public class BaseFragment extends KXFragment
{
  private static int DISTANCE = 0;
  public static final int EDIT_PHOTO = 104;
  public static final int EDIT_PHOTO_FROMWEIBO = 105;
  protected static final String FROM_CAMERA = "camera";
  protected static final String FROM_GALLERY = "gallery";
  private static final String FROM_WEBPAGE = "from_webpage";
  public static final String FROM_WIDGET = "from_widget";
  public static final int RESULT_CANCELED = 0;
  public static final int RESULT_FIRST_USER = 1;
  public static final int RESULT_OK = -1;
  private static final float SPEED = 1.0F;
  private static final String TAG = "FRAGMENT";
  public static final int UPLOAD_PHOTO_CAMERA = 101;
  public static final int UPLOAD_PHOTO_GALLERY = 102;
  public static final int UPLOAD_PHOTO_NEW = 103;
  private static BaseFragment mFragment;
  private static Animation mPreFragmentHideAnimation;
  private static Animation mPreFragmentShowAnimation;
  private String LevelContent;
  private String extraPraise;
  private boolean finished = false;
  private boolean isShowLevelToast = true;
  private String levleType;
  private Activity mActivity;
  public boolean mBackKeyNotify = false;
  private BaseFragment mCallbackFragment = null;
  private int mCreateViewTimes = 0;
  protected int mCurrVolume;
  private boolean mDetectHorizontalSlide = false;
  private boolean mDisallowDetectSlide = false;
  private boolean mEnableSlideBack = true;
  private Toast mGlobalToast = null;
  protected Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      if (1 == paramMessage.what)
        BaseFragment.this.showLevelToast();
      if (BaseFragment.this.interceptHandleMessage(paramMessage));
      do
        return;
      while (!BaseFragment.this.handleMessage(paramMessage));
      super.handleMessage(paramMessage);
    }
  };
  private Intent mIntent;
  private int mPageLevel = 2;
  protected BaseFragment mPreFragment;
  private int mRequestCode = 0;
  private int mResultCode;
  private long startTime;
  private float startX;
  private float startY;

  public static BaseFragment getBaseFragment()
  {
    return mFragment;
  }

  private void handleCantAddmoreDialogOptions(int paramInt)
  {
    if (paramInt == -1)
      AnimationUtil.startFragment(this, new Intent(getActivity(), UploadTaskListFragment.class), 1);
  }

  public static boolean isAtEditView(View paramView, MotionEvent paramMotionEvent)
  {
    if ((paramView != null) && ((paramView instanceof EditText)))
    {
      int[] arrayOfInt = new int[2];
      paramView.getLocationInWindow(arrayOfInt);
      int i = arrayOfInt[0];
      int j = arrayOfInt[1];
      int k = j + paramView.getHeight();
      int m = i + paramView.getWidth();
      if ((paramMotionEvent.getX() > i) && (paramMotionEvent.getX() < m) && (paramMotionEvent.getY() > j) && (paramMotionEvent.getY() < k))
        return true;
    }
    return false;
  }

  private void printFragmentStackSize()
  {
  }

  protected boolean checkNetworkAndHint(boolean paramBoolean)
  {
    return HttpConnection.checkNetworkAndHint(paramBoolean, getActivity());
  }

  protected void closeSpeaker()
  {
    try
    {
      AudioManager localAudioManager = (AudioManager)getActivity().getSystemService("audio");
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

  public boolean dataInited()
  {
    return this.mCreateViewTimes > 1;
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
  }

  public void disallowDetectSlide(boolean paramBoolean)
  {
    this.mDisallowDetectSlide = paramBoolean;
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mPageLevel == 1);
    float f3;
    float f4;
    do
    {
      do
      {
        do
        {
          return false;
          if (paramMotionEvent.getAction() != 0)
            continue;
          this.mDisallowDetectSlide = false;
        }
        while ((!this.mEnableSlideBack) || (this.mDisallowDetectSlide));
        if (DISTANCE < 0)
          DISTANCE = getResources().getDisplayMetrics().widthPixels / 4;
        if (paramMotionEvent.getAction() != 0)
          continue;
        this.mDetectHorizontalSlide = true;
        this.startX = paramMotionEvent.getX();
        this.startY = paramMotionEvent.getY();
        this.startTime = System.currentTimeMillis();
      }
      while ((!this.mDetectHorizontalSlide) || (paramMotionEvent.getAction() != 2));
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      if (f1 < this.startX)
        this.mDetectHorizontalSlide = false;
      if (Math.abs(f1 - this.startX) < Math.abs(f2 - this.startY))
      {
        this.mDetectHorizontalSlide = false;
        return false;
      }
      long l = System.currentTimeMillis();
      f3 = f1 - this.startX;
      f4 = f3 / (float)(l - this.startTime);
    }
    while ((f3 <= DISTANCE) || (f4 <= 1.0F));
    this.mDetectHorizontalSlide = false;
    paramMotionEvent.setAction(3);
    requestFinish();
    return true;
  }

  public void downloadFile(String paramString, ProgressCallBack paramProgressCallBack)
  {
    new Thread(new KXDownRun(paramString, new KXDownHandler(paramProgressCallBack))).start();
  }

  public void enableSlideBack(boolean paramBoolean)
  {
    this.mEnableSlideBack = paramBoolean;
  }

  public void finish()
  {
    int i = 1;
    while (true)
    {
      try
      {
        hideKeyboard();
        KXLog.d("FRAGMENT", getClass().getName() + "(" + toString() + ")" + " finish()");
        printFragmentStackSize();
        if (getArguments() == null)
          continue;
        String str1 = getArguments().getString("callfrom");
        String str2 = getArguments().getString("boardcast");
        int k = getArguments().getInt("callcode");
        if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2)) || (!str1.equals("activity")))
          continue;
        if (this.mIntent != null)
          continue;
        this.mIntent = new Intent();
        this.mIntent.putExtra("callcode", k);
        Intent localIntent = new Intent(str2);
        localIntent.putExtras(this.mIntent.getExtras());
        getActivity().sendBroadcast(localIntent);
        getActivity().finish();
        return;
        FragmentTransaction localFragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0)
        {
          AnimationUtil.setFragmentAnimations(localFragmentTransaction, 2, true);
          getActivity().getSupportFragmentManager().popBackStack();
          if (this.mCallbackFragment == null)
            break;
          this.mCallbackFragment.onFragmentResult(this.mRequestCode, this.mResultCode, this.mIntent);
          this.mCallbackFragment.onResume();
          if (this.mCallbackFragment.getPageLevel() != i)
            break label318;
          KXSliderLayout2.setSlideEnable(i);
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      startFragment(new Intent(getActivity(), NewsFragment.class));
      continue;
      label318: int j = 0;
    }
  }

  public boolean finishAndRedirect()
  {
    int i = getActivity().getSupportFragmentManager().getBackStackEntryCount();
    int j = 0;
    String str;
    if (i == 0)
    {
      Bundle localBundle = getArguments();
      j = 0;
      if (localBundle != null)
      {
        str = getArguments().getString("prefragment");
        boolean bool = TextUtils.isEmpty(str);
        j = 0;
        if (bool);
      }
    }
    try
    {
      Class localClass = Class.forName(str);
      j = 0;
      if (localClass != null)
      {
        startFragment(new Intent(getActivity(), localClass), false, 0);
        j = 1;
      }
      return j;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public void finishFragment(int paramInt)
  {
    this.mRequestCode = paramInt;
  }

  public void getDataFromeServer(RequestVo paramRequestVo, DataCallback paramDataCallback)
  {
    KXHandler localKXHandler = new KXHandler(paramDataCallback);
    new Thread(new KXRun(getActivity(), paramRequestVo, localKXHandler)).start();
  }

  public Handler getHandler()
  {
    return this.mHandler;
  }

  public Intent getIntent()
  {
    return this.mIntent;
  }

  public int getPageLevel()
  {
    return this.mPageLevel;
  }

  public int getRequestConde()
  {
    return this.mRequestCode;
  }

  public int getResult()
  {
    return this.mResultCode;
  }

  public int getScreenHeight()
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics.heightPixels;
  }

  public int getScreenWidth()
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics.widthPixels;
  }

  protected boolean handleMessage(Message paramMessage)
  {
    return false;
  }

  public void initFragmentData(Bundle paramBundle)
  {
  }

  protected void insertFaceIcon(int paramInt)
  {
  }

  protected boolean interceptHandleMessage(Message paramMessage)
  {
    return getView() == null;
  }

  public boolean isMenuListVisibleInMain()
  {
    FragmentActivity localFragmentActivity = getActivity();
    if ((localFragmentActivity != null) && ((localFragmentActivity instanceof MainActivity)))
      return ((MainActivity)localFragmentActivity).isMenuListVisible();
    return false;
  }

  public void isShowLevelToast(boolean paramBoolean)
  {
    this.isShowLevelToast = paramBoolean;
  }

  // ERROR //
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    // Byte code:
    //   0: iload_2
    //   1: iconst_m1
    //   2: if_icmpne +31 -> 33
    //   5: iload_1
    //   6: sipush 209
    //   9: if_icmpne +32 -> 41
    //   12: aload_3
    //   13: ldc_w 490
    //   16: iconst_m1
    //   17: invokevirtual 494	android/content/Intent:getIntExtra	(Ljava/lang/String;I)I
    //   20: istore 37
    //   22: iload 37
    //   24: iflt +9 -> 33
    //   27: aload_0
    //   28: iload 37
    //   30: invokevirtual 496	com/kaixin001/fragment/BaseFragment:insertFaceIcon	(I)V
    //   33: aload_0
    //   34: iload_1
    //   35: iload_2
    //   36: aload_3
    //   37: invokespecial 498	com/kaixin001/fragment/KXFragment:onActivityResult	(IILandroid/content/Intent;)V
    //   40: return
    //   41: iload_1
    //   42: bipush 101
    //   44: if_icmpne +461 -> 505
    //   47: new 277	java/lang/StringBuilder
    //   50: dup
    //   51: invokespecial 499	java/lang/StringBuilder:<init>	()V
    //   54: invokestatic 505	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   57: invokevirtual 508	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   60: ldc_w 510
    //   63: invokevirtual 304	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: ldc_w 512
    //   69: invokevirtual 304	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: ldc_w 514
    //   75: invokevirtual 304	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: invokevirtual 312	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   81: astore 14
    //   83: iconst_0
    //   84: istore 15
    //   86: aload_3
    //   87: ifnull +38 -> 125
    //   90: aload_3
    //   91: invokevirtual 362	android/content/Intent:getExtras	()Landroid/os/Bundle;
    //   94: astore 35
    //   96: iconst_0
    //   97: istore 15
    //   99: aload 35
    //   101: ifnull +24 -> 125
    //   104: aload 35
    //   106: ldc_w 516
    //   109: invokevirtual 519	android/os/Bundle:get	(Ljava/lang/String;)Ljava/lang/Object;
    //   112: astore 36
    //   114: iconst_0
    //   115: istore 15
    //   117: aload 36
    //   119: ifnull +6 -> 125
    //   122: iconst_1
    //   123: istore 15
    //   125: iload 15
    //   127: ifne +163 -> 290
    //   130: aconst_null
    //   131: astore 23
    //   133: aload 14
    //   135: sipush 300
    //   138: invokestatic 525	com/kaixin001/util/FileUtil:loadBitmapFromFile	(Ljava/lang/String;I)Landroid/graphics/Bitmap;
    //   141: astore 23
    //   143: aload_0
    //   144: invokevirtual 127	com/kaixin001/fragment/BaseFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   147: invokevirtual 529	android/support/v4/app/FragmentActivity:getContentResolver	()Landroid/content/ContentResolver;
    //   150: aload 23
    //   152: aconst_null
    //   153: aconst_null
    //   154: invokestatic 535	android/provider/MediaStore$Images$Media:insertImage	(Landroid/content/ContentResolver;Landroid/graphics/Bitmap;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   157: invokestatic 345	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   160: ifne +120 -> 280
    //   163: aload_0
    //   164: invokevirtual 127	com/kaixin001/fragment/BaseFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   167: invokestatic 539	com/kaixin001/util/FileUtil:loadLatestPic	(Landroid/content/Context;)Ljava/lang/String;
    //   170: astore 27
    //   172: iconst_1
    //   173: istore 15
    //   175: new 541	java/io/File
    //   178: dup
    //   179: new 277	java/lang/StringBuilder
    //   182: dup
    //   183: invokespecial 499	java/lang/StringBuilder:<init>	()V
    //   186: invokestatic 505	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   189: invokevirtual 508	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   192: ldc_w 510
    //   195: invokevirtual 304	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   198: invokevirtual 312	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   201: ldc_w 514
    //   204: invokespecial 543	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   207: astore 28
    //   209: aload 28
    //   211: new 541	java/io/File
    //   214: dup
    //   215: aload 27
    //   217: invokespecial 544	java/io/File:<init>	(Ljava/lang/String;)V
    //   220: invokevirtual 548	java/io/File:renameTo	(Ljava/io/File;)Z
    //   223: pop
    //   224: new 550	android/media/ExifInterface
    //   227: dup
    //   228: aload 27
    //   230: invokespecial 551	android/media/ExifInterface:<init>	(Ljava/lang/String;)V
    //   233: astore 30
    //   235: aload 30
    //   237: astore 31
    //   239: aload 31
    //   241: ifnull +39 -> 280
    //   244: invokestatic 557	com/kaixin001/model/Setting:getInstance	()Lcom/kaixin001/model/Setting;
    //   247: invokevirtual 560	com/kaixin001/model/Setting:getManufacturerName	()Ljava/lang/String;
    //   250: astore 32
    //   252: aload 31
    //   254: ldc_w 562
    //   257: aload 32
    //   259: invokevirtual 565	android/media/ExifInterface:setAttribute	(Ljava/lang/String;Ljava/lang/String;)V
    //   262: invokestatic 557	com/kaixin001/model/Setting:getInstance	()Lcom/kaixin001/model/Setting;
    //   265: invokevirtual 568	com/kaixin001/model/Setting:getDeviceName	()Ljava/lang/String;
    //   268: astore 33
    //   270: aload 31
    //   272: ldc_w 570
    //   275: aload 33
    //   277: invokevirtual 565	android/media/ExifInterface:setAttribute	(Ljava/lang/String;Ljava/lang/String;)V
    //   280: aload 23
    //   282: ifnull +8 -> 290
    //   285: aload 23
    //   287: invokevirtual 575	android/graphics/Bitmap:recycle	()V
    //   290: iload 15
    //   292: ifeq +169 -> 461
    //   295: aconst_null
    //   296: astore 18
    //   298: aload_0
    //   299: invokevirtual 127	com/kaixin001/fragment/BaseFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   302: getstatic 579	android/provider/MediaStore$Images$Media:EXTERNAL_CONTENT_URI	Landroid/net/Uri;
    //   305: aconst_null
    //   306: aconst_null
    //   307: aconst_null
    //   308: aconst_null
    //   309: invokevirtual 583	android/support/v4/app/FragmentActivity:managedQuery	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   312: astore 18
    //   314: aload 18
    //   316: invokeinterface 588 1 0
    //   321: istore 21
    //   323: aconst_null
    //   324: astore 17
    //   326: iload 21
    //   328: ifeq +26 -> 354
    //   331: aload 18
    //   333: aload 18
    //   335: ldc_w 590
    //   338: invokeinterface 593 2 0
    //   343: invokeinterface 596 2 0
    //   348: astore 22
    //   350: aload 22
    //   352: astore 17
    //   354: aload 18
    //   356: invokestatic 602	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   359: aload_0
    //   360: aload 17
    //   362: ldc 15
    //   364: invokevirtual 604	com/kaixin001/fragment/BaseFragment:dealPhotoResult	(Ljava/lang/String;Ljava/lang/String;)V
    //   367: goto -334 -> 33
    //   370: astore 34
    //   372: aload 34
    //   374: invokevirtual 202	java/lang/Exception:printStackTrace	()V
    //   377: aconst_null
    //   378: astore 31
    //   380: goto -141 -> 239
    //   383: astore 26
    //   385: aload 26
    //   387: invokevirtual 202	java/lang/Exception:printStackTrace	()V
    //   390: aload 23
    //   392: ifnull -102 -> 290
    //   395: aload 23
    //   397: invokevirtual 575	android/graphics/Bitmap:recycle	()V
    //   400: goto -110 -> 290
    //   403: astore 25
    //   405: aload 25
    //   407: invokevirtual 605	java/lang/OutOfMemoryError:printStackTrace	()V
    //   410: aload 23
    //   412: ifnull -122 -> 290
    //   415: aload 23
    //   417: invokevirtual 575	android/graphics/Bitmap:recycle	()V
    //   420: goto -130 -> 290
    //   423: astore 24
    //   425: aload 23
    //   427: ifnull +8 -> 435
    //   430: aload 23
    //   432: invokevirtual 575	android/graphics/Bitmap:recycle	()V
    //   435: aload 24
    //   437: athrow
    //   438: astore 20
    //   440: aload 18
    //   442: invokestatic 602	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   445: aconst_null
    //   446: astore 17
    //   448: goto -89 -> 359
    //   451: astore 19
    //   453: aload 18
    //   455: invokestatic 602	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   458: aload 19
    //   460: athrow
    //   461: new 541	java/io/File
    //   464: dup
    //   465: new 277	java/lang/StringBuilder
    //   468: dup
    //   469: invokespecial 499	java/lang/StringBuilder:<init>	()V
    //   472: invokestatic 505	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   475: invokevirtual 508	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   478: ldc_w 510
    //   481: invokevirtual 304	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   484: invokevirtual 312	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   487: ldc_w 514
    //   490: invokespecial 543	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   493: astore 16
    //   495: aload 16
    //   497: invokevirtual 608	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   500: astore 17
    //   502: goto -143 -> 359
    //   505: iload_1
    //   506: bipush 102
    //   508: if_icmpne +146 -> 654
    //   511: aload_3
    //   512: invokevirtual 612	android/content/Intent:getData	()Landroid/net/Uri;
    //   515: astore 6
    //   517: aload 6
    //   519: invokevirtual 617	android/net/Uri:getScheme	()Ljava/lang/String;
    //   522: ldc_w 618
    //   525: invokevirtual 351	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   528: ifeq +95 -> 623
    //   531: iconst_1
    //   532: anewarray 291	java/lang/String
    //   535: dup
    //   536: iconst_0
    //   537: ldc_w 590
    //   540: aastore
    //   541: astore 7
    //   543: aconst_null
    //   544: astore 8
    //   546: aload_0
    //   547: invokevirtual 127	com/kaixin001/fragment/BaseFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   550: invokevirtual 529	android/support/v4/app/FragmentActivity:getContentResolver	()Landroid/content/ContentResolver;
    //   553: aload 6
    //   555: aload 7
    //   557: aconst_null
    //   558: aconst_null
    //   559: aconst_null
    //   560: invokevirtual 623	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   563: astore 8
    //   565: aload 8
    //   567: invokeinterface 626 1 0
    //   572: pop
    //   573: aload 8
    //   575: aload 8
    //   577: aload 7
    //   579: iconst_0
    //   580: aaload
    //   581: invokeinterface 629 2 0
    //   586: invokeinterface 596 2 0
    //   591: astore 11
    //   593: aload 11
    //   595: astore 12
    //   597: aload 8
    //   599: invokestatic 602	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   602: aload_0
    //   603: aload 12
    //   605: ldc 18
    //   607: invokevirtual 604	com/kaixin001/fragment/BaseFragment:dealPhotoResult	(Ljava/lang/String;Ljava/lang/String;)V
    //   610: goto -577 -> 33
    //   613: astore 9
    //   615: aload 8
    //   617: invokestatic 602	com/kaixin001/util/CloseUtil:close	(Landroid/database/Cursor;)V
    //   620: aload 9
    //   622: athrow
    //   623: aload 6
    //   625: invokevirtual 617	android/net/Uri:getScheme	()Ljava/lang/String;
    //   628: ldc_w 631
    //   631: invokevirtual 351	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   634: istore 13
    //   636: aconst_null
    //   637: astore 12
    //   639: iload 13
    //   641: ifeq -39 -> 602
    //   644: aload 6
    //   646: invokevirtual 634	android/net/Uri:getPath	()Ljava/lang/String;
    //   649: astore 12
    //   651: goto -49 -> 602
    //   654: iload_1
    //   655: bipush 103
    //   657: if_icmpne +43 -> 700
    //   660: aload_3
    //   661: invokevirtual 362	android/content/Intent:getExtras	()Landroid/os/Bundle;
    //   664: astore 4
    //   666: aload 4
    //   668: ifnull -635 -> 33
    //   671: aload 4
    //   673: ldc_w 636
    //   676: invokevirtual 331	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   679: astore 5
    //   681: aload 5
    //   683: invokestatic 345	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   686: ifne -653 -> 33
    //   689: aload_0
    //   690: aload 5
    //   692: ldc 18
    //   694: invokevirtual 604	com/kaixin001/fragment/BaseFragment:dealPhotoResult	(Ljava/lang/String;Ljava/lang/String;)V
    //   697: goto -664 -> 33
    //   700: iload_1
    //   701: bipush 104
    //   703: if_icmpne -670 -> 33
    //   706: aload_0
    //   707: aload_3
    //   708: invokevirtual 362	android/content/Intent:getExtras	()Landroid/os/Bundle;
    //   711: invokestatic 642	com/kaixin001/util/IntentUtil:showUploadPhotoFragment	(Lcom/kaixin001/fragment/BaseFragment;Landroid/os/Bundle;)V
    //   714: goto -681 -> 33
    //
    // Exception table:
    //   from	to	target	type
    //   224	235	370	java/lang/Exception
    //   133	172	383	java/lang/Exception
    //   175	224	383	java/lang/Exception
    //   244	280	383	java/lang/Exception
    //   372	377	383	java/lang/Exception
    //   133	172	403	java/lang/OutOfMemoryError
    //   175	224	403	java/lang/OutOfMemoryError
    //   224	235	403	java/lang/OutOfMemoryError
    //   244	280	403	java/lang/OutOfMemoryError
    //   372	377	403	java/lang/OutOfMemoryError
    //   133	172	423	finally
    //   175	224	423	finally
    //   224	235	423	finally
    //   244	280	423	finally
    //   372	377	423	finally
    //   385	390	423	finally
    //   405	410	423	finally
    //   298	323	438	java/lang/Exception
    //   331	350	438	java/lang/Exception
    //   298	323	451	finally
    //   331	350	451	finally
    //   546	593	613	finally
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mActivity = paramActivity;
  }

  public void onCreate(Bundle paramBundle)
  {
    MessageHandlerHolder.getInstance().addHandler(this.mHandler);
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
  }

  public void onDestroy()
  {
    MessageHandlerHolder.getInstance().removeHandler(this.mHandler);
    super.onDestroy();
  }

  public void onDestroyView()
  {
    super.onDestroyView();
    if (mPreFragmentShowAnimation == null)
      mPreFragmentShowAnimation = AnimationUtils.loadAnimation(getActivity(), 2130968577);
    if ((this.mPreFragment != null) && (this.mPreFragment.getView() != null))
      this.mPreFragment.getView().startAnimation(mPreFragmentShowAnimation);
  }

  protected void onFragmentResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    return false;
  }

  public void onResume()
  {
    super.onResume();
    mFragment = this;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return false;
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mCreateViewTimes = (1 + this.mCreateViewTimes);
    if (getPageLevel() == 1);
    for (boolean bool = true; ; bool = false)
    {
      KXSliderLayout2.setSlideEnable(bool);
      paramView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
        }
      });
      if (mPreFragmentHideAnimation == null)
        mPreFragmentHideAnimation = AnimationUtils.loadAnimation(getActivity(), 2130968582);
      if ((this.mPreFragment != null) && (this.mPreFragment.getView() != null))
        this.mPreFragment.getView().startAnimation(mPreFragmentHideAnimation);
      if (this.mCreateViewTimes == 1)
        initFragmentData(paramBundle);
      return;
    }
  }

  protected void openSpeaker()
  {
    try
    {
      AudioManager localAudioManager = (AudioManager)getActivity().getSystemService("audio");
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

  public void requestFinish()
  {
    finish();
  }

  public void selectPictureFromBulk()
  {
    AnimationUtil.startFragment(this, new Intent(getActivity(), PhotoSelectFragment.class), 3);
  }

  public void selectPictureFromGallery()
  {
    Intent localIntent = new Intent();
    localIntent.setClass(getActivity(), PhotoSelectFragment.class);
    localIntent.putExtra("single", true);
    AnimationUtil.startFragmentForResult(this, localIntent, 103, 1);
  }

  public void setCallbackFragment(BaseFragment paramBaseFragment)
  {
    this.mCallbackFragment = paramBaseFragment;
  }

  public void setIntent(Intent paramIntent)
  {
    this.mIntent = paramIntent;
  }

  public void setPageLevel(int paramInt)
  {
    this.mPageLevel = paramInt;
    KXLog.d("FRAGMENT", getClass().getName() + "(" + toString() + ")" + " setPageLevel(" + paramInt + ")");
  }

  public void setRequestCode(int paramInt)
  {
    this.mRequestCode = paramInt;
  }

  public void setResult(int paramInt)
  {
    this.mResultCode = paramInt;
  }

  public void setResult(int paramInt, Intent paramIntent)
  {
    this.mResultCode = paramInt;
    this.mIntent = paramIntent;
  }

  public void setUpgradeInfoData(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null);
    try
    {
      this.isShowLevelToast = false;
      return;
      this.LevelContent = paramJSONObject.optString("content", "");
      this.levleType = paramJSONObject.optString("type", "");
      this.extraPraise = paramJSONObject.optString("extraPraise", "");
      return;
    }
    catch (Exception localException)
    {
      this.isShowLevelToast = false;
      localException.printStackTrace();
    }
  }

  public Dialog showCantUploadOptions()
  {
    String str = StringUtil.replaceTokenWith(getActivity().getApplicationContext().getResources().getString(2131427640), "*", String.valueOf(UploadTaskListEngine.getInstance().getWaitingTaskCount()));
    return DialogUtil.showMsgDlg(getActivity(), 2131427329, str, 2131427639, 2131427587, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        BaseFragment.this.handleCantAddmoreDialogOptions(paramInt);
      }
    }
    , new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        BaseFragment.this.handleCantAddmoreDialogOptions(paramInt);
      }
    });
  }

  public void showFaceListDialog(boolean paramBoolean)
  {
    startActivityForResult(new Intent(getActivity(), InputFaceActivity.class), 209);
  }

  public void showLevelToast()
  {
    if (!this.isShowLevelToast);
    do
    {
      do
        return;
      while (TextUtils.isEmpty(this.levleType));
      if (!this.levleType.equals("level"))
        continue;
      showLevelToast(ToastInfo.LEVEL_UP, this.LevelContent);
      return;
    }
    while (!this.levleType.equals("exp"));
    showLevelToast(ToastInfo.ADD_EXP, this.LevelContent);
  }

  public void showLevelToast(ToastInfo paramToastInfo, String paramString)
  {
    View localView = this.mActivity.getLayoutInflater().inflate(2130903344, (ViewGroup)findViewById(2131363712));
    ((ImageView)localView.findViewById(2131363713)).setImageResource(paramToastInfo.getResId());
    ((TextView)localView.findViewById(2131363714)).setText(paramString);
    TextView localTextView = (TextView)localView.findViewById(2131363715);
    if (!TextUtils.isEmpty(this.extraPraise))
    {
      localTextView.setVisibility(0);
      localTextView.setText(this.extraPraise);
    }
    while (true)
    {
      Toast localToast = new Toast(this.mActivity.getApplicationContext());
      localToast.setGravity(16, 0, 0);
      localToast.setDuration(1);
      localToast.setView(localView);
      localToast.show();
      return;
      localTextView.setVisibility(8);
    }
  }

  public void showLoginPage(int paramInt)
  {
    if (paramInt == 0)
    {
      Intent localIntent1 = new Intent(getActivity(), LoginActivity.class);
      Bundle localBundle = new Bundle();
      localBundle.putInt("login", 1);
      localIntent1.putExtras(localBundle);
      AnimationUtil.startActivity(getActivity(), localIntent1, 3);
      return;
    }
    Intent localIntent2 = new Intent(getActivity(), PhoneRegisterActivity.class);
    AnimationUtil.startActivity(getActivity(), localIntent2, 3);
  }

  public void showMenuListInMain()
  {
    FragmentActivity localFragmentActivity = getActivity();
    if ((localFragmentActivity != null) && ((localFragmentActivity instanceof MainActivity)))
      ((MainActivity)localFragmentActivity).showKaixinList();
  }

  public void showSubActivityInMain()
  {
    FragmentActivity localFragmentActivity = getActivity();
    if ((localFragmentActivity != null) && ((localFragmentActivity instanceof MainActivity)))
      ((MainActivity)localFragmentActivity).showSubFragment();
  }

  protected void showToast(int paramInt)
  {
    showToast(getResources().getString(paramInt));
  }

  public void showToast(int paramInt1, int paramInt2)
  {
    View localView = getActivity().getLayoutInflater().inflate(2130903261, null);
    ImageView localImageView = (ImageView)localView.findViewById(2131363278);
    TextView localTextView = (TextView)localView.findViewById(2131363279);
    if (paramInt1 != 0)
    {
      localImageView.setImageResource(paramInt1);
      localImageView.setVisibility(0);
      if (paramInt2 == 0)
        break label118;
      localTextView.setText(paramInt2);
      localTextView.setVisibility(0);
    }
    while (true)
    {
      Toast localToast = new Toast(getActivity().getApplicationContext());
      localToast.setGravity(17, 0, 0);
      localToast.setView(localView);
      localToast.show();
      return;
      localImageView.setVisibility(8);
      break;
      label118: localTextView.setVisibility(8);
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
      this.mGlobalToast = Toast.makeText(getActivity(), paramString, 0);
    }
  }

  protected void showUploadPhotoDialog(String paramString)
  {
    getActivity().getApplicationContext().getSharedPreferences("from_webpage", 0).edit().putBoolean("fromwebpage", false).commit();
    if (UploadTaskListEngine.getInstance().getWaitingTaskCount() < 20)
    {
      selectPictureFromBulk();
      KXUBLog.log("homepage_pic");
      return;
    }
    showCantUploadOptions();
  }

  public void showVideoThumbnail(ImageView paramImageView, String paramString)
  {
    if (paramImageView == null)
      return;
    paramImageView.setVisibility(0);
    paramImageView.setTag("BG");
    displayPicture(paramImageView, paramString, 2130838784);
    paramImageView.setImageResource(2130838791);
    paramImageView.setScaleType(ImageView.ScaleType.CENTER);
  }

  public void startActivityForResult(Intent paramIntent, int paramInt)
  {
    ComponentName localComponentName = paramIntent.getComponent();
    String str = null;
    if (localComponentName != null)
      str = paramIntent.getComponent().getClassName();
    if (TextUtils.isEmpty(str))
    {
      super.startActivityForResult(paramIntent, paramInt);
      return;
    }
    AnimationUtil.startFragmentForResult(this, paramIntent, paramInt, 1);
  }

  public void startActivityForResult(Intent paramIntent, int paramInt1, int paramInt2)
  {
    ComponentName localComponentName = paramIntent.getComponent();
    String str = null;
    if (localComponentName != null)
      str = paramIntent.getComponent().getClassName();
    if (TextUtils.isEmpty(str))
    {
      super.startActivityForResult(paramIntent, paramInt1);
      return;
    }
    AnimationUtil.startFragmentForResult(this, paramIntent, paramInt1, 1);
  }

  public void startFragment(Intent paramIntent)
  {
    startFragment(paramIntent, false, 0);
  }

  public void startFragment(Intent paramIntent, int paramInt)
  {
    startFragment(paramIntent, false, paramInt);
  }

  public void startFragment(Intent paramIntent, boolean paramBoolean)
  {
    startFragment(paramIntent, paramBoolean, 0);
  }

  public void startFragment(Intent paramIntent, boolean paramBoolean, int paramInt)
  {
    hideKeyboard();
    String str1 = paramIntent.getComponent().getClassName();
    Bundle localBundle = paramIntent.getExtras();
    String str2 = paramIntent.getAction();
    KXLog.d("FRAGMENT", getClass().getName() + "(" + toString() + ")" + " startFragment:" + str1);
    printFragmentStackSize();
    try
    {
      BaseFragment localBaseFragment = (BaseFragment)Class.forName(str1).newInstance();
      if (str2 != null)
        localBundle.putString("action", str2);
      if (localBundle != null)
        localBaseFragment.setArguments(localBundle);
      FragmentTransaction localFragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
      AnimationUtil.setFragmentAnimations(localFragmentTransaction, paramInt, paramBoolean);
      if (paramInt == 3)
        localFragmentTransaction.add(2131361999, localBaseFragment);
      while (true)
      {
        if (paramBoolean)
          localFragmentTransaction.addToBackStack(null);
        localFragmentTransaction.commit();
        return;
        localFragmentTransaction.replace(2131361999, localBaseFragment);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void startFragmentForResult(Intent paramIntent, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    String str = paramIntent.getComponent().getClassName();
    Bundle localBundle = paramIntent.getExtras();
    KXLog.d("FRAGMENT", getClass().getName() + "(" + toString() + ")" + " startFragmentForResult:" + str);
    printFragmentStackSize();
    try
    {
      BaseFragment localBaseFragment1 = (BaseFragment)Class.forName(str).newInstance();
      if (localBundle != null)
        localBaseFragment1.setArguments(localBundle);
      localBaseFragment1.setCallbackFragment(this);
      localBaseFragment1.setResult(0, null);
      localBaseFragment1.setRequestCode(paramInt1);
      FragmentTransaction localFragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
      AnimationUtil.setFragmentAnimations(localFragmentTransaction, paramInt2, paramBoolean);
      BaseFragment localBaseFragment2 = (BaseFragment)getActivity().getSupportFragmentManager().findFragmentById(2131361999);
      localBaseFragment1.mPreFragment = null;
      if (paramBoolean)
      {
        localFragmentTransaction.add(2131361999, localBaseFragment1);
        if (paramInt2 != 3)
          localBaseFragment1.mPreFragment = localBaseFragment2;
      }
      while (true)
      {
        if (paramBoolean)
          localFragmentTransaction.addToBackStack(null);
        localFragmentTransaction.commit();
        return;
        localFragmentTransaction.replace(2131361999, localBaseFragment1);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void startFragmentForResultNew(Intent paramIntent, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    String str = paramIntent.getComponent().getClassName();
    Bundle localBundle = paramIntent.getExtras();
    KXLog.d("FRAGMENT", getClass().getName() + "(" + toString() + ")" + " startFragmentForResultNew:" + str);
    printFragmentStackSize();
    try
    {
      BaseFragment localBaseFragment = (BaseFragment)Class.forName(str).newInstance();
      if (localBundle != null)
        localBaseFragment.setArguments(localBundle);
      localBaseFragment.setCallbackFragment(this);
      localBaseFragment.setResult(0, null);
      localBaseFragment.setRequestCode(paramInt1);
      FragmentTransaction localFragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
      AnimationUtil.setFragmentAnimations(localFragmentTransaction, paramInt2, paramBoolean);
      localBaseFragment.mPreFragment = null;
      localFragmentTransaction.replace(2131361999, localBaseFragment);
      if (paramBoolean)
        localFragmentTransaction.addToBackStack(null);
      localFragmentTransaction.commit();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public boolean takePictureWithCamera()
  {
    if (!getActivity().getPackageManager().hasSystemFeature("android.hardware.camera"))
    {
      Toast.makeText(getActivity(), 2131428469, 0).show();
      return false;
    }
    File localFile = new File(Environment.getExternalStorageDirectory() + "/kaixin001", "kx_upload_tmp.jpg");
    if (!FileUtil.makeEmptyFile(localFile))
    {
      Toast.makeText(getActivity(), 2131427883, 0).show();
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
      Toast.makeText(getActivity(), 2131428469, 0).show();
    }
    return false;
  }

  public void writeNewRecord(String paramString)
  {
    if (UploadTaskListEngine.getInstance().getWaitingTaskCount() < 20)
    {
      Intent localIntent = new Intent(getActivity(), WriteWeiboFragment.class);
      if (!TextUtils.isEmpty(paramString))
      {
        localIntent.setType("text");
        localIntent.putExtra("android.intent.extra.TEXT", paramString);
      }
      AnimationUtil.startFragment(this, localIntent, 3);
      return;
    }
    showCantUploadOptions();
  }

  static abstract interface DataCallback
  {
    public abstract void requestFalse();

    public abstract void requestSuccess(Object paramObject);
  }

  class KXDownHandler extends Handler
  {
    private BaseFragment.ProgressCallBack callback;

    public KXDownHandler(BaseFragment.ProgressCallBack arg2)
    {
      Object localObject;
      this.callback = localObject;
    }

    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
        return;
      case 0:
        this.callback.downloadStart();
        return;
      case 1:
        int i = paramMessage.arg1;
        this.callback.downloading(i);
        return;
      case 2:
      }
      this.callback.downloadEnd((File)paramMessage.obj);
    }
  }

  class KXDownRun
    implements Runnable
  {
    private BaseFragment.KXDownHandler handler;
    private String urlPath;

    public KXDownRun(String paramKXDownHandler, BaseFragment.KXDownHandler arg3)
    {
      this.urlPath = paramKXDownHandler;
      Object localObject;
      this.handler = localObject;
    }

    public void run()
    {
      while (true)
      {
        int i;
        FileOutputStream localFileOutputStream;
        byte[] arrayOfByte;
        int k;
        int m;
        try
        {
          URL localURL = new URL(this.urlPath);
          str1 = "ad" + System.currentTimeMillis() + ".apk";
          str2 = Environment.getExternalStorageDirectory().toString() + "/kaixin001";
          URLConnection localURLConnection = localURL.openConnection();
          localURLConnection.connect();
          localInputStream = localURLConnection.getInputStream();
          i = localURLConnection.getContentLength();
          j = 0;
          if (i > 0)
            continue;
          throw new RuntimeException("无法获知文件大小 ");
        }
        catch (MalformedURLException localMalformedURLException)
        {
          String str1;
          String str2;
          InputStream localInputStream;
          localMalformedURLException.printStackTrace();
          return;
          localFileOutputStream = new FileOutputStream(str2 + "/" + str1);
          arrayOfByte = new byte[1024];
          Message localMessage1 = this.handler.obtainMessage();
          localMessage1.what = 0;
          this.handler.sendMessage(localMessage1);
          k = 0;
          m = localInputStream.read(arrayOfByte);
          if (m == -1)
          {
            Message localMessage2 = this.handler.obtainMessage();
            localMessage2.what = 2;
            localMessage2.obj = new File(str2 + "/" + str1);
            this.handler.sendMessage(localMessage2);
            return;
          }
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
          return;
        }
        localFileOutputStream.write(arrayOfByte, 0, m);
        k += m;
        int n = k * 100 / i;
        if (n == j + 1)
        {
          Message localMessage3 = this.handler.obtainMessage();
          localMessage3.what = 1;
          localMessage3.arg1 = n;
          this.handler.sendMessage(localMessage3);
        }
        int j = n;
      }
    }
  }

  class KXHandler extends Handler
  {
    private BaseFragment.DataCallback callback;

    public KXHandler(BaseFragment.DataCallback arg2)
    {
      Object localObject;
      this.callback = localObject;
    }

    public void handleMessage(Message paramMessage)
    {
      if (paramMessage.what == 1)
      {
        Object localObject = paramMessage.obj;
        this.callback.requestSuccess(localObject);
        return;
      }
      this.callback.requestFalse();
    }
  }

  class KXRun
    implements Runnable
  {
    private Context context;
    private BaseFragment.KXHandler handler;
    private RequestVo vo;

    public KXRun(Context paramRequestVo, RequestVo paramKXHandler, BaseFragment.KXHandler arg4)
    {
      this.context = paramRequestVo;
      this.vo = paramKXHandler;
      Object localObject;
      this.handler = localObject;
    }

    public void run()
    {
      Message localMessage = new Message();
      if (HttpConnection.checkNetworkAvailable(this.context) == 1)
      {
        Object localObject = HttpUtils.get(this.vo);
        localMessage.what = 1;
        localMessage.obj = localObject;
        this.handler.sendMessage(localMessage);
        return;
      }
      localMessage.what = -1;
      this.handler.sendMessage(localMessage);
    }
  }

  static abstract interface ProgressCallBack
  {
    public abstract void downloadEnd(File paramFile);

    public abstract void downloadStart();

    public abstract void downloading(int paramInt);
  }

  public static enum ToastInfo
  {
    private int resId = 0;
    private String text = "";

    static
    {
      ToastInfo[] arrayOfToastInfo = new ToastInfo[2];
      arrayOfToastInfo[0] = ADD_EXP;
      arrayOfToastInfo[1] = LEVEL_UP;
      ENUM$VALUES = arrayOfToastInfo;
    }

    private ToastInfo(int arg3, String arg4)
    {
      int i;
      this.resId = i;
      Object localObject;
      this.text = localObject;
    }

    public int getResId()
    {
      return this.resId;
    }

    public String getText()
    {
      return this.text;
    }

    public void setResId(int paramInt)
    {
      this.resId = paramInt;
    }

    public void setText(String paramString)
    {
      this.text = paramString;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.BaseFragment
 * JD-Core Version:    0.6.0
 */