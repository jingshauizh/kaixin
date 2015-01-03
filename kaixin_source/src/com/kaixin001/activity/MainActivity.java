package com.kaixin001.activity;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.kaixin001.businesslogic.LogoutAndExitProxy;
import com.kaixin001.engine.KXDialogNoticeEngine;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.CloudAlbumFragment;
import com.kaixin001.fragment.GamesFragment;
import com.kaixin001.fragment.GiftNewsFragment;
import com.kaixin001.fragment.KaixinMenuListFragment;
import com.kaixin001.fragment.NewsFragment;
import com.kaixin001.fragment.PicturesFragment;
import com.kaixin001.fragment.SharedPostFragment;
import com.kaixin001.model.KXDialogNoticeModel;
import com.kaixin001.model.KXDialogNoticeModel.ButtonData;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.service.RefreshNewMessageService;
import com.kaixin001.service.UpdateContactsService;
import com.kaixin001.service.UpgradeDownloadService;
import com.kaixin001.service.UploadClientInfoService;
import com.kaixin001.ugc.UGCMenuLayout;
import com.kaixin001.util.ActivityUtil;
import com.kaixin001.util.CloudAlbumManager;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.InnerPushManager;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.PopupWindowUtil;
import com.kaixin001.util.ScreenUtil;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXAbsoluteLayout.LayoutParams;
import com.kaixin001.view.KXSliderLayout2;
import com.kaixin001.view.KXSliderLayout2.OnScreenSlideListener;
import com.umeng.analytics.MobclickAgent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class MainActivity extends FragmentActivity
  implements Animation.AnimationListener
{
  private static final int ANIMATION_DURATION = 300;
  public static final String KEY_FRAGMENT = "fragment";
  public static final String KEY_IS_ADDTO_BACKSTACK = "isAddToBackStack";
  private static final int RIGHT_ACTIVITY_GAP_DP = 270;
  public static boolean isRefresh = false;
  public static MainActivity mInstance;
  public static boolean mNeedCheckDialogNotice = true;
  private String actionLink;
  private boolean animationRunning = false;
  private boolean bBackKeyPressed = false;
  public Dialog dialog;
  private String from;
  private AnimationEndListener hideAnimationEndListender;
  private LogoutAndExitProxy logoutTask;
  public Handler mHandler = new Handler();
  private boolean mKeyDownEnabled = false;
  private boolean mKeyDownLocked = false;
  private KXSliderLayout2 mMainLayout;
  private PopupWindow mSettingOptionPopupWindow;
  private String myCls = null;
  private int orientation = 0;
  private int rightGapInPx = -1;

  static
  {
    mInstance = null;
  }

  public static void destroySelf()
  {
    if (mInstance != null)
      mInstance.finish();
  }

  private void removeAllFragments()
  {
    FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
    BaseFragment localBaseFragment = (BaseFragment)getSupportFragmentManager().findFragmentById(2131361999);
    if (localBaseFragment != null)
    {
      localFragmentTransaction.remove(localBaseFragment);
      KXLog.d("FRAGMENT", "removeAllFragments:" + localBaseFragment.getClass().getName());
      localFragmentTransaction.commit();
    }
  }

  private void setLeftFragment()
  {
    KaixinMenuListFragment localKaixinMenuListFragment = (KaixinMenuListFragment)getSupportFragmentManager().findFragmentById(2131363004);
    localKaixinMenuListFragment.doResume();
    refreshLeftMenuInfo();
    this.mMainLayout.setOnSlideListener(localKaixinMenuListFragment);
  }

  private void startRefreshNewMessageService()
  {
    startService(new Intent(this, RefreshNewMessageService.class));
  }

  private void startRightFragment(Intent paramIntent)
  {
    Bundle localBundle = paramIntent.getExtras();
    this.actionLink = localBundle.getString("actionlink");
    String str = null;
    boolean bool = false;
    if (localBundle != null)
    {
      str = localBundle.getString("fragment");
      if ((!TextUtils.isEmpty(str)) && (str.indexOf(".") <= 0))
        break label75;
    }
    while (true)
    {
      bool = localBundle.getBoolean("isAddToBackStack");
      try
      {
        if (!TextUtils.isEmpty(str))
        {
          startRightFragment(paramIntent.getAction(), localBundle, str, bool);
          return;
          label75: str = "com.kaixin001.fragment." + str;
          continue;
        }
        startRightFragment(paramIntent.getAction(), localBundle, null, bool);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  // ERROR //
  private void startRightFragment(String paramString1, Bundle paramBundle, String paramString2, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   4: ldc 142
    //   6: invokevirtual 94	android/support/v4/app/FragmentManager:findFragmentById	(I)Landroid/support/v4/app/Fragment;
    //   9: checkcast 144	com/kaixin001/fragment/KaixinMenuListFragment
    //   12: astore 5
    //   14: aload_0
    //   15: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   18: invokevirtual 89	android/support/v4/app/FragmentManager:beginTransaction	()Landroid/support/v4/app/FragmentTransaction;
    //   21: astore 6
    //   23: ldc 221
    //   25: aload_1
    //   26: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   29: ifeq +161 -> 190
    //   32: aload_0
    //   33: ldc 90
    //   35: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   38: ifnull +106 -> 144
    //   41: new 231	com/kaixin001/fragment/HomeFragment
    //   44: dup
    //   45: invokespecial 232	com/kaixin001/fragment/HomeFragment:<init>	()V
    //   48: astore 63
    //   50: aload 63
    //   52: iconst_1
    //   53: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   56: new 182	android/os/Bundle
    //   59: dup
    //   60: invokespecial 237	android/os/Bundle:<init>	()V
    //   63: astore 64
    //   65: invokestatic 243	com/kaixin001/model/User:getInstance	()Lcom/kaixin001/model/User;
    //   68: astore 65
    //   70: aload 64
    //   72: ldc 245
    //   74: aload 65
    //   76: invokevirtual 248	com/kaixin001/model/User:getUID	()Ljava/lang/String;
    //   79: invokevirtual 251	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   82: aload 64
    //   84: ldc 253
    //   86: aload 65
    //   88: invokevirtual 256	com/kaixin001/model/User:getRealName	()Ljava/lang/String;
    //   91: invokevirtual 251	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   94: aload 65
    //   96: invokevirtual 259	com/kaixin001/model/User:getLogo120	()Ljava/lang/String;
    //   99: ifnull +67 -> 166
    //   102: aload 64
    //   104: ldc_w 261
    //   107: aload 65
    //   109: invokevirtual 259	com/kaixin001/model/User:getLogo120	()Ljava/lang/String;
    //   112: invokevirtual 251	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   115: aload 64
    //   117: ldc_w 263
    //   120: aload_1
    //   121: invokevirtual 251	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   124: aload 63
    //   126: aload 64
    //   128: invokevirtual 267	com/kaixin001/fragment/BaseFragment:setArguments	(Landroid/os/Bundle;)V
    //   131: aload 6
    //   133: ldc 90
    //   135: aload 63
    //   137: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   140: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   143: pop
    //   144: ldc 231
    //   146: astore 10
    //   148: aload 5
    //   150: ifnull +15 -> 165
    //   153: aload 10
    //   155: ifnull +10 -> 165
    //   158: aload 5
    //   160: aload 10
    //   162: invokevirtual 278	com/kaixin001/fragment/KaixinMenuListFragment:setSelectedMenuItem	(Ljava/lang/Class;)V
    //   165: return
    //   166: aload 65
    //   168: invokevirtual 281	com/kaixin001/model/User:getLogo	()Ljava/lang/String;
    //   171: ifnull -56 -> 115
    //   174: aload 64
    //   176: ldc_w 261
    //   179: aload 65
    //   181: invokevirtual 281	com/kaixin001/model/User:getLogo	()Ljava/lang/String;
    //   184: invokevirtual 251	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   187: goto -72 -> 115
    //   190: ldc_w 283
    //   193: aload_1
    //   194: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   197: ifne +13 -> 210
    //   200: ldc_w 285
    //   203: aload_1
    //   204: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   207: ifeq +73 -> 280
    //   210: aload_0
    //   211: ldc 90
    //   213: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   216: ifnull +56 -> 272
    //   219: new 287	com/kaixin001/fragment/MessageCenterFragment
    //   222: dup
    //   223: invokespecial 288	com/kaixin001/fragment/MessageCenterFragment:<init>	()V
    //   226: astore 7
    //   228: aload 7
    //   230: iconst_1
    //   231: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   234: new 182	android/os/Bundle
    //   237: dup
    //   238: invokespecial 237	android/os/Bundle:<init>	()V
    //   241: astore 8
    //   243: aload 8
    //   245: ldc_w 263
    //   248: aload_1
    //   249: invokevirtual 251	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   252: aload 7
    //   254: aload 8
    //   256: invokevirtual 267	com/kaixin001/fragment/BaseFragment:setArguments	(Landroid/os/Bundle;)V
    //   259: aload 6
    //   261: ldc 90
    //   263: aload 7
    //   265: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   268: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   271: pop
    //   272: ldc_w 287
    //   275: astore 10
    //   277: goto -129 -> 148
    //   280: ldc_w 290
    //   283: aload_1
    //   284: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   287: ifeq +53 -> 340
    //   290: aload_0
    //   291: ldc 90
    //   293: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   296: ifnull +36 -> 332
    //   299: new 292	com/kaixin001/fragment/FriendsFragment
    //   302: dup
    //   303: invokespecial 293	com/kaixin001/fragment/FriendsFragment:<init>	()V
    //   306: astore 61
    //   308: aload 61
    //   310: iconst_1
    //   311: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   314: aload_0
    //   315: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   318: invokevirtual 89	android/support/v4/app/FragmentManager:beginTransaction	()Landroid/support/v4/app/FragmentTransaction;
    //   321: ldc 90
    //   323: aload 61
    //   325: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   328: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   331: pop
    //   332: ldc_w 292
    //   335: astore 10
    //   337: goto -189 -> 148
    //   340: ldc_w 295
    //   343: aload_1
    //   344: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   347: ifeq +53 -> 400
    //   350: aload_0
    //   351: ldc 90
    //   353: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   356: ifnull +36 -> 392
    //   359: new 297	com/kaixin001/fragment/PositionMainFragment
    //   362: dup
    //   363: invokespecial 298	com/kaixin001/fragment/PositionMainFragment:<init>	()V
    //   366: astore 59
    //   368: aload 59
    //   370: iconst_1
    //   371: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   374: aload_0
    //   375: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   378: invokevirtual 89	android/support/v4/app/FragmentManager:beginTransaction	()Landroid/support/v4/app/FragmentTransaction;
    //   381: ldc 90
    //   383: aload 59
    //   385: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   388: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   391: pop
    //   392: ldc_w 297
    //   395: astore 10
    //   397: goto -249 -> 148
    //   400: ldc_w 300
    //   403: aload_1
    //   404: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   407: ifeq +53 -> 460
    //   410: aload_0
    //   411: ldc 90
    //   413: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   416: ifnull +36 -> 452
    //   419: new 302	com/kaixin001/fragment/PicturesFragment
    //   422: dup
    //   423: invokespecial 303	com/kaixin001/fragment/PicturesFragment:<init>	()V
    //   426: astore 57
    //   428: aload 57
    //   430: iconst_1
    //   431: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   434: aload_0
    //   435: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   438: invokevirtual 89	android/support/v4/app/FragmentManager:beginTransaction	()Landroid/support/v4/app/FragmentTransaction;
    //   441: ldc 90
    //   443: aload 57
    //   445: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   448: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   451: pop
    //   452: ldc_w 302
    //   455: astore 10
    //   457: goto -309 -> 148
    //   460: ldc_w 305
    //   463: aload_1
    //   464: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   467: ifeq +150 -> 617
    //   470: aload_0
    //   471: ldc 90
    //   473: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   476: ifnull +133 -> 609
    //   479: new 307	com/kaixin001/fragment/RepostDetailFragment
    //   482: dup
    //   483: invokespecial 308	com/kaixin001/fragment/RepostDetailFragment:<init>	()V
    //   486: astore 49
    //   488: new 310	java/util/ArrayList
    //   491: dup
    //   492: invokespecial 311	java/util/ArrayList:<init>	()V
    //   495: astore 50
    //   497: new 313	com/kaixin001/item/RepItem
    //   500: dup
    //   501: invokespecial 314	com/kaixin001/item/RepItem:<init>	()V
    //   504: astore 51
    //   506: aload 51
    //   508: aload_2
    //   509: ldc 253
    //   511: invokevirtual 186	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   514: putfield 316	com/kaixin001/item/RepItem:fname	Ljava/lang/String;
    //   517: aload 51
    //   519: aload_2
    //   520: ldc 245
    //   522: invokevirtual 186	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   525: putfield 318	com/kaixin001/item/RepItem:fuid	Ljava/lang/String;
    //   528: aload 51
    //   530: aload_2
    //   531: ldc_w 320
    //   534: invokevirtual 186	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   537: putfield 323	com/kaixin001/item/RepItem:id	Ljava/lang/String;
    //   540: aload 50
    //   542: aload 51
    //   544: invokeinterface 328 2 0
    //   549: pop
    //   550: aload 50
    //   552: checkcast 330	java/io/Serializable
    //   555: astore 53
    //   557: aload_2
    //   558: ldc_w 332
    //   561: aload 53
    //   563: invokevirtual 336	android/os/Bundle:putSerializable	(Ljava/lang/String;Ljava/io/Serializable;)V
    //   566: aload_2
    //   567: ldc_w 338
    //   570: iconst_0
    //   571: invokevirtual 342	android/os/Bundle:putInt	(Ljava/lang/String;I)V
    //   574: aload 49
    //   576: iconst_1
    //   577: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   580: aload 49
    //   582: aload_2
    //   583: invokevirtual 267	com/kaixin001/fragment/BaseFragment:setArguments	(Landroid/os/Bundle;)V
    //   586: aload 6
    //   588: ldc 90
    //   590: aload 49
    //   592: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   595: pop
    //   596: aload 6
    //   598: aconst_null
    //   599: invokevirtual 346	android/support/v4/app/FragmentTransaction:addToBackStack	(Ljava/lang/String;)Landroid/support/v4/app/FragmentTransaction;
    //   602: pop
    //   603: aload 6
    //   605: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   608: pop
    //   609: ldc_w 307
    //   612: astore 10
    //   614: goto -466 -> 148
    //   617: ldc_w 348
    //   620: aload_1
    //   621: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   624: ifeq +54 -> 678
    //   627: aload_0
    //   628: ldc 90
    //   630: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   633: ifnull +37 -> 670
    //   636: new 350	com/kaixin001/fragment/SharedPostFragment
    //   639: dup
    //   640: invokespecial 351	com/kaixin001/fragment/SharedPostFragment:<init>	()V
    //   643: astore 47
    //   645: aload 47
    //   647: iconst_1
    //   648: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   651: aload 47
    //   653: aload_2
    //   654: invokevirtual 267	com/kaixin001/fragment/BaseFragment:setArguments	(Landroid/os/Bundle;)V
    //   657: aload 6
    //   659: ldc 90
    //   661: aload 47
    //   663: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   666: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   669: pop
    //   670: ldc_w 350
    //   673: astore 10
    //   675: goto -527 -> 148
    //   678: ldc_w 353
    //   681: aload_1
    //   682: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   685: ifeq +53 -> 738
    //   688: aload_0
    //   689: ldc 90
    //   691: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   694: ifnull +36 -> 730
    //   697: new 355	com/kaixin001/fragment/FindFriendFragment
    //   700: dup
    //   701: invokespecial 356	com/kaixin001/fragment/FindFriendFragment:<init>	()V
    //   704: astore 45
    //   706: aload 45
    //   708: iconst_1
    //   709: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   712: aload_0
    //   713: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   716: invokevirtual 89	android/support/v4/app/FragmentManager:beginTransaction	()Landroid/support/v4/app/FragmentTransaction;
    //   719: ldc 90
    //   721: aload 45
    //   723: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   726: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   729: pop
    //   730: ldc_w 355
    //   733: astore 10
    //   735: goto -587 -> 148
    //   738: ldc_w 358
    //   741: aload_1
    //   742: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   745: ifeq +53 -> 798
    //   748: aload_0
    //   749: ldc 90
    //   751: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   754: ifnull +36 -> 790
    //   757: new 360	com/kaixin001/fragment/HoroscopeFragment
    //   760: dup
    //   761: invokespecial 361	com/kaixin001/fragment/HoroscopeFragment:<init>	()V
    //   764: astore 43
    //   766: aload 43
    //   768: iconst_1
    //   769: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   772: aload_0
    //   773: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   776: invokevirtual 89	android/support/v4/app/FragmentManager:beginTransaction	()Landroid/support/v4/app/FragmentTransaction;
    //   779: ldc 90
    //   781: aload 43
    //   783: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   786: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   789: pop
    //   790: ldc_w 360
    //   793: astore 10
    //   795: goto -647 -> 148
    //   798: ldc_w 363
    //   801: aload_1
    //   802: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   805: ifeq +53 -> 858
    //   808: aload_0
    //   809: ldc 90
    //   811: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   814: ifnull +36 -> 850
    //   817: new 365	com/kaixin001/fragment/CirclesFragment
    //   820: dup
    //   821: invokespecial 366	com/kaixin001/fragment/CirclesFragment:<init>	()V
    //   824: astore 41
    //   826: aload 41
    //   828: iconst_1
    //   829: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   832: aload_0
    //   833: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   836: invokevirtual 89	android/support/v4/app/FragmentManager:beginTransaction	()Landroid/support/v4/app/FragmentTransaction;
    //   839: ldc 90
    //   841: aload 41
    //   843: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   846: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   849: pop
    //   850: ldc_w 365
    //   853: astore 10
    //   855: goto -707 -> 148
    //   858: ldc_w 368
    //   861: aload_1
    //   862: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   865: ifeq +78 -> 943
    //   868: aload_0
    //   869: ldc 90
    //   871: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   874: ifnull +61 -> 935
    //   877: new 302	com/kaixin001/fragment/PicturesFragment
    //   880: dup
    //   881: invokespecial 303	com/kaixin001/fragment/PicturesFragment:<init>	()V
    //   884: astore 38
    //   886: aload 38
    //   888: iconst_1
    //   889: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   892: new 182	android/os/Bundle
    //   895: dup
    //   896: invokespecial 237	android/os/Bundle:<init>	()V
    //   899: astore 39
    //   901: aload 39
    //   903: ldc_w 370
    //   906: iconst_2
    //   907: invokevirtual 342	android/os/Bundle:putInt	(Ljava/lang/String;I)V
    //   910: aload 38
    //   912: aload 39
    //   914: invokevirtual 267	com/kaixin001/fragment/BaseFragment:setArguments	(Landroid/os/Bundle;)V
    //   917: aload_0
    //   918: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   921: invokevirtual 89	android/support/v4/app/FragmentManager:beginTransaction	()Landroid/support/v4/app/FragmentTransaction;
    //   924: ldc 90
    //   926: aload 38
    //   928: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   931: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   934: pop
    //   935: ldc_w 372
    //   938: astore 10
    //   940: goto -792 -> 148
    //   943: ldc_w 374
    //   946: aload_1
    //   947: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   950: ifeq +53 -> 1003
    //   953: aload_0
    //   954: ldc 90
    //   956: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   959: ifnull +36 -> 995
    //   962: new 376	com/kaixin001/fragment/GamesFragment
    //   965: dup
    //   966: invokespecial 377	com/kaixin001/fragment/GamesFragment:<init>	()V
    //   969: astore 36
    //   971: aload 36
    //   973: iconst_1
    //   974: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   977: aload_0
    //   978: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   981: invokevirtual 89	android/support/v4/app/FragmentManager:beginTransaction	()Landroid/support/v4/app/FragmentTransaction;
    //   984: ldc 90
    //   986: aload 36
    //   988: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   991: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   994: pop
    //   995: ldc_w 376
    //   998: astore 10
    //   1000: goto -852 -> 148
    //   1003: ldc_w 379
    //   1006: aload_1
    //   1007: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1010: ifeq +53 -> 1063
    //   1013: aload_0
    //   1014: ldc 90
    //   1016: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   1019: ifnull +36 -> 1055
    //   1022: new 381	com/kaixin001/fragment/GiftNewsFragment
    //   1025: dup
    //   1026: invokespecial 382	com/kaixin001/fragment/GiftNewsFragment:<init>	()V
    //   1029: astore 34
    //   1031: aload 34
    //   1033: iconst_1
    //   1034: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   1037: aload_0
    //   1038: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   1041: invokevirtual 89	android/support/v4/app/FragmentManager:beginTransaction	()Landroid/support/v4/app/FragmentTransaction;
    //   1044: ldc 90
    //   1046: aload 34
    //   1048: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   1051: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   1054: pop
    //   1055: ldc_w 381
    //   1058: astore 10
    //   1060: goto -912 -> 148
    //   1063: ldc_w 384
    //   1066: aload_1
    //   1067: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1070: ifeq +72 -> 1142
    //   1073: aload_0
    //   1074: ldc 90
    //   1076: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   1079: ifnull +55 -> 1134
    //   1082: new 386	com/kaixin001/fragment/TopicGroupFragment
    //   1085: dup
    //   1086: invokespecial 387	com/kaixin001/fragment/TopicGroupFragment:<init>	()V
    //   1089: astore 31
    //   1091: aload 31
    //   1093: iconst_1
    //   1094: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   1097: getstatic 392	com/kaixin001/model/KXCityModel:mTopic	Ljava/lang/String;
    //   1100: astore 32
    //   1102: aload_2
    //   1103: ldc_w 394
    //   1106: aload 32
    //   1108: invokevirtual 251	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   1111: aload 31
    //   1113: aload_2
    //   1114: invokevirtual 267	com/kaixin001/fragment/BaseFragment:setArguments	(Landroid/os/Bundle;)V
    //   1117: aload 6
    //   1119: ldc 90
    //   1121: aload 31
    //   1123: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   1126: aconst_null
    //   1127: invokevirtual 346	android/support/v4/app/FragmentTransaction:addToBackStack	(Ljava/lang/String;)Landroid/support/v4/app/FragmentTransaction;
    //   1130: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   1133: pop
    //   1134: ldc_w 386
    //   1137: astore 10
    //   1139: goto -991 -> 148
    //   1142: ldc_w 396
    //   1145: aload_1
    //   1146: invokevirtual 225	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1149: ifeq +53 -> 1202
    //   1152: aload_0
    //   1153: ldc 90
    //   1155: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   1158: ifnull +36 -> 1194
    //   1161: new 398	com/kaixin001/fragment/NewsFragment
    //   1164: dup
    //   1165: invokespecial 399	com/kaixin001/fragment/NewsFragment:<init>	()V
    //   1168: astore 29
    //   1170: aload 29
    //   1172: iconst_1
    //   1173: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   1176: aload_0
    //   1177: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   1180: invokevirtual 89	android/support/v4/app/FragmentManager:beginTransaction	()Landroid/support/v4/app/FragmentTransaction;
    //   1183: ldc 90
    //   1185: aload 29
    //   1187: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   1190: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   1193: pop
    //   1194: ldc_w 398
    //   1197: astore 10
    //   1199: goto -1051 -> 148
    //   1202: aload_3
    //   1203: invokestatic 194	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   1206: ifne +264 -> 1470
    //   1209: aload_0
    //   1210: ldc 90
    //   1212: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   1215: astore 14
    //   1217: aconst_null
    //   1218: astore 10
    //   1220: aload 14
    //   1222: ifnull -1074 -> 148
    //   1225: aload_3
    //   1226: invokestatic 403	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   1229: astore 16
    //   1231: aload 16
    //   1233: invokevirtual 407	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   1236: checkcast 96	com/kaixin001/fragment/BaseFragment
    //   1239: astore 28
    //   1241: aload 16
    //   1243: astore 10
    //   1245: aload 28
    //   1247: astore 18
    //   1249: aload 18
    //   1251: ifnonnull +313 -> 1564
    //   1254: new 398	com/kaixin001/fragment/NewsFragment
    //   1257: dup
    //   1258: invokespecial 399	com/kaixin001/fragment/NewsFragment:<init>	()V
    //   1261: astore 19
    //   1263: ldc_w 398
    //   1266: astore 10
    //   1268: aload 19
    //   1270: iconst_1
    //   1271: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   1274: aload_2
    //   1275: ifnonnull +11 -> 1286
    //   1278: new 182	android/os/Bundle
    //   1281: dup
    //   1282: invokespecial 237	android/os/Bundle:<init>	()V
    //   1285: astore_2
    //   1286: aload_2
    //   1287: ldc_w 263
    //   1290: invokevirtual 186	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   1293: invokestatic 194	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   1296: ifeq +11 -> 1307
    //   1299: aload_2
    //   1300: ldc_w 263
    //   1303: aload_1
    //   1304: invokevirtual 251	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   1307: aload_0
    //   1308: invokevirtual 411	com/kaixin001/activity/MainActivity:getIntent	()Landroid/content/Intent;
    //   1311: ifnull +21 -> 1332
    //   1314: aload_0
    //   1315: invokevirtual 411	com/kaixin001/activity/MainActivity:getIntent	()Landroid/content/Intent;
    //   1318: invokevirtual 414	android/content/Intent:getFlags	()I
    //   1321: istore 26
    //   1323: aload_2
    //   1324: ldc_w 416
    //   1327: iload 26
    //   1329: invokevirtual 342	android/os/Bundle:putInt	(Ljava/lang/String;I)V
    //   1332: aload_0
    //   1333: invokevirtual 411	com/kaixin001/activity/MainActivity:getIntent	()Landroid/content/Intent;
    //   1336: ifnull +31 -> 1367
    //   1339: aload_0
    //   1340: invokevirtual 411	com/kaixin001/activity/MainActivity:getIntent	()Landroid/content/Intent;
    //   1343: invokevirtual 419	android/content/Intent:getType	()Ljava/lang/String;
    //   1346: ifnull +21 -> 1367
    //   1349: aload_0
    //   1350: invokevirtual 411	com/kaixin001/activity/MainActivity:getIntent	()Landroid/content/Intent;
    //   1353: invokevirtual 419	android/content/Intent:getType	()Ljava/lang/String;
    //   1356: astore 25
    //   1358: aload_2
    //   1359: ldc_w 421
    //   1362: aload 25
    //   1364: invokevirtual 251	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   1367: aload 19
    //   1369: aload_2
    //   1370: invokevirtual 267	com/kaixin001/fragment/BaseFragment:setArguments	(Landroid/os/Bundle;)V
    //   1373: aload_0
    //   1374: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   1377: ldc 90
    //   1379: invokevirtual 94	android/support/v4/app/FragmentManager:findFragmentById	(I)Landroid/support/v4/app/Fragment;
    //   1382: checkcast 96	com/kaixin001/fragment/BaseFragment
    //   1385: ifnull +64 -> 1449
    //   1388: aload_0
    //   1389: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   1392: invokevirtual 89	android/support/v4/app/FragmentManager:beginTransaction	()Landroid/support/v4/app/FragmentTransaction;
    //   1395: astore 21
    //   1397: aload 21
    //   1399: ldc 90
    //   1401: aload 19
    //   1403: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   1406: pop
    //   1407: aload 21
    //   1409: aconst_null
    //   1410: invokevirtual 346	android/support/v4/app/FragmentTransaction:addToBackStack	(Ljava/lang/String;)Landroid/support/v4/app/FragmentTransaction;
    //   1413: pop
    //   1414: aload 21
    //   1416: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   1419: pop
    //   1420: goto -1272 -> 148
    //   1423: astore 15
    //   1425: aload 15
    //   1427: invokevirtual 422	java/lang/ClassNotFoundException:printStackTrace	()V
    //   1430: goto -1282 -> 148
    //   1433: astore 17
    //   1435: aload 17
    //   1437: invokevirtual 217	java/lang/Exception:printStackTrace	()V
    //   1440: aconst_null
    //   1441: astore 10
    //   1443: aconst_null
    //   1444: astore 18
    //   1446: goto -197 -> 1249
    //   1449: aload_0
    //   1450: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   1453: invokevirtual 89	android/support/v4/app/FragmentManager:beginTransaction	()Landroid/support/v4/app/FragmentTransaction;
    //   1456: ldc 90
    //   1458: aload 19
    //   1460: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   1463: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   1466: pop
    //   1467: goto -1319 -> 148
    //   1470: aload_0
    //   1471: ldc 90
    //   1473: invokevirtual 229	com/kaixin001/activity/MainActivity:findViewById	(I)Landroid/view/View;
    //   1476: ifnull +72 -> 1548
    //   1479: new 398	com/kaixin001/fragment/NewsFragment
    //   1482: dup
    //   1483: invokespecial 399	com/kaixin001/fragment/NewsFragment:<init>	()V
    //   1486: astore 11
    //   1488: aload 11
    //   1490: iconst_1
    //   1491: invokevirtual 236	com/kaixin001/fragment/BaseFragment:setPageLevel	(I)V
    //   1494: new 182	android/os/Bundle
    //   1497: dup
    //   1498: invokespecial 237	android/os/Bundle:<init>	()V
    //   1501: astore 12
    //   1503: aload 12
    //   1505: ldc_w 263
    //   1508: aload_1
    //   1509: invokevirtual 251	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   1512: aload 12
    //   1514: ldc 180
    //   1516: aload_0
    //   1517: getfield 188	com/kaixin001/activity/MainActivity:actionLink	Ljava/lang/String;
    //   1520: invokevirtual 251	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   1523: aload 11
    //   1525: aload 12
    //   1527: invokevirtual 267	com/kaixin001/fragment/BaseFragment:setArguments	(Landroid/os/Bundle;)V
    //   1530: aload_0
    //   1531: invokevirtual 83	com/kaixin001/activity/MainActivity:getSupportFragmentManager	()Landroid/support/v4/app/FragmentManager;
    //   1534: invokevirtual 89	android/support/v4/app/FragmentManager:beginTransaction	()Landroid/support/v4/app/FragmentTransaction;
    //   1537: ldc 90
    //   1539: aload 11
    //   1541: invokevirtual 271	android/support/v4/app/FragmentTransaction:replace	(ILandroid/support/v4/app/Fragment;)Landroid/support/v4/app/FragmentTransaction;
    //   1544: invokevirtual 274	android/support/v4/app/FragmentTransaction:commitAllowingStateLoss	()I
    //   1547: pop
    //   1548: ldc_w 398
    //   1551: astore 10
    //   1553: goto -1405 -> 148
    //   1556: astore 15
    //   1558: aload 18
    //   1560: pop
    //   1561: goto -136 -> 1425
    //   1564: aload 18
    //   1566: astore 19
    //   1568: goto -294 -> 1274
    //
    // Exception table:
    //   from	to	target	type
    //   1225	1231	1423	java/lang/ClassNotFoundException
    //   1231	1241	1423	java/lang/ClassNotFoundException
    //   1268	1274	1423	java/lang/ClassNotFoundException
    //   1278	1286	1423	java/lang/ClassNotFoundException
    //   1286	1307	1423	java/lang/ClassNotFoundException
    //   1307	1332	1423	java/lang/ClassNotFoundException
    //   1332	1367	1423	java/lang/ClassNotFoundException
    //   1367	1420	1423	java/lang/ClassNotFoundException
    //   1435	1440	1423	java/lang/ClassNotFoundException
    //   1449	1467	1423	java/lang/ClassNotFoundException
    //   1231	1241	1433	java/lang/Exception
    //   1254	1263	1556	java/lang/ClassNotFoundException
  }

  private void startService()
  {
    startRefreshNewMessageService();
    startUpdateContactsService();
    this.logoutTask = new LogoutAndExitProxy(this);
    mNeedCheckDialogNotice = true;
  }

  private void startUpdateContactsService()
  {
    startService(new Intent(this, UpdateContactsService.class));
  }

  private void startUploadClientInfoService()
  {
    if (!isServiceRun("com.kaixin001.service.UploadClientInfoService"))
      startService(new Intent(this, UploadClientInfoService.class));
  }

  private void stopDownloadService()
  {
    stopService(new Intent(getApplicationContext(), UpgradeDownloadService.class));
  }

  public void addNoViewFragment(Fragment paramFragment, String paramString)
  {
    FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
    if (paramFragment != null)
      localFragmentTransaction.add(paramFragment, paramString).commit();
  }

  public void clearBackStack()
  {
    FragmentManager localFragmentManager = getSupportFragmentManager();
    int i = localFragmentManager.getBackStackEntryCount();
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        removeAllFragments();
        return;
      }
      localFragmentManager.popBackStack();
    }
  }

  public void dismissDialog()
  {
    if ((this.dialog != null) && (this.dialog.isShowing()))
      this.dialog.dismiss();
    this.dialog = null;
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    this.bBackKeyPressed = false;
    if (this.animationRunning);
    BaseFragment localBaseFragment;
    do
    {
      return true;
      localBaseFragment = (BaseFragment)getSupportFragmentManager().findFragmentById(2131361999);
    }
    while ((localBaseFragment != null) && (localBaseFragment.dispatchTouchEvent(paramMotionEvent)));
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  public int getSubFragmentGap()
  {
    if (-1 == this.rightGapInPx)
      this.rightGapInPx = (int)(270.0F * getResources().getDisplayMetrics().density);
    return this.rightGapInPx;
  }

  public boolean isMenuListVisible()
  {
    View localView = findViewById(2131361999);
    KXAbsoluteLayout.LayoutParams localLayoutParams = (KXAbsoluteLayout.LayoutParams)localView.getLayoutParams();
    return (localView.getVisibility() == 0) && (localLayoutParams.x > 0);
  }

  public boolean isMenuListVisibleAll()
  {
    View localView1 = findViewById(2131361999);
    View localView2 = findViewById(2131363004);
    KXAbsoluteLayout.LayoutParams localLayoutParams = (KXAbsoluteLayout.LayoutParams)localView1.getLayoutParams();
    return (localView1.getVisibility() == 0) && (localLayoutParams.x >= localView2.getWidth());
  }

  public boolean isSelectLogo()
  {
    return (!TextUtils.isEmpty(this.from)) && (this.from.equals("InfoCompletedActivity_select_flogo"));
  }

  public boolean isServiceRun(String paramString)
  {
    Iterator localIterator = ((ActivityManager)getSystemService("activity")).getRunningServices(100).iterator();
    do
      if (!localIterator.hasNext())
        return false;
    while (!((ActivityManager.RunningServiceInfo)localIterator.next()).service.getClassName().equals(paramString));
    return true;
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    BaseFragment localBaseFragment = (BaseFragment)getSupportFragmentManager().findFragmentById(2131361999);
    if (localBaseFragment != null)
      localBaseFragment.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onAnimationEnd(Animation paramAnimation)
  {
    this.animationRunning = false;
    UGCMenuLayout.resetAnimationState();
    if (this.hideAnimationEndListender != null)
    {
      int i = getResources().getDisplayMetrics().widthPixels;
      if (((KXAbsoluteLayout.LayoutParams)findViewById(2131361999).getLayoutParams()).x == i)
      {
        this.hideAnimationEndListender.onAnimationEnd();
        this.hideAnimationEndListender = null;
      }
    }
    if (isRefresh)
      refreshLeftMenuInfo();
  }

  public void onAnimationRepeat(Animation paramAnimation)
  {
  }

  public void onAnimationStart(Animation paramAnimation)
  {
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    if (this.orientation != paramConfiguration.orientation)
    {
      View localView = findViewById(2131361999);
      KXAbsoluteLayout.LayoutParams localLayoutParams = (KXAbsoluteLayout.LayoutParams)localView.getLayoutParams();
      if (localLayoutParams.x > getSubFragmentGap())
      {
        localLayoutParams.x = getResources().getDisplayMetrics().widthPixels;
        localView.setLayoutParams(localLayoutParams);
      }
    }
    super.onConfigurationChanged(paramConfiguration);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903210);
    mInstance = this;
    User localUser = User.getInstance();
    localUser.loadUserData(this);
    if (TextUtils.isEmpty(localUser.getOauthToken()))
      localUser.setLookAround(Boolean.valueOf(true));
    this.orientation = ScreenUtil.getOrientation(this);
    this.mMainLayout = ((KXSliderLayout2)findViewById(2131361934));
    this.mMainLayout.initLayout();
    this.mMainLayout.setOnScreenSliderListener(new KXSliderLayout2.OnScreenSlideListener()
    {
      public void onSlideTo(int paramInt1, int paramInt2)
      {
        if (paramInt1 == 0)
          MainActivity.this.showSubFragment();
        do
          return;
        while (paramInt1 != 1);
        MainActivity.this.showKaixinList();
      }
    });
    Intent localIntent1 = getIntent();
    String str = localIntent1.getAction();
    if (localIntent1.getExtras() != null)
    {
      if (localIntent1.getExtras().getBoolean("updatestate"))
        localIntent1.getExtras().putString("fragment", "WriteWeiboFragment");
      if (localIntent1.getExtras().getBoolean("InnerPushManager", false))
        InnerPushManager.getInstance(this).addCount();
      this.from = localIntent1.getExtras().getString("from");
    }
    if (TextUtils.isEmpty(str));
    startRightFragment(localIntent1);
    setLeftFragment();
    startService();
    startUploadClientInfoService();
    CloudAlbumManager.getInstance().init(this);
    CloudAlbumManager.getInstance().clearLocalPictures();
    CloudAlbumManager.getInstance().startUploadDeamon(this, 10L);
    if ((!TextUtils.isEmpty(this.from)) && (this.from.equals("InfoCompletedActivity_select_flogo")));
    for (int i = 1; ; i = 0)
    {
      boolean bool1 = KXEnvironment.loadBooleanParams(getApplicationContext(), "needCompleteInfo", true, false);
      boolean bool2 = false;
      if (bool1)
      {
        bool2 = false;
        if (i == 0)
          bool2 = true;
      }
      KXEnvironment.mNeedCompleteInfo = bool2;
      isRefresh = true;
      if (KXEnvironment.mNeedCompleteInfo)
      {
        Intent localIntent2 = new Intent(this, InfoCompletedActivity.class);
        Bundle localBundle = new Bundle();
        localBundle.putInt("mode", 1);
        localBundle.putString("from", "MainActivity");
        localIntent2.putExtras(localBundle);
        startActivity(localIntent2);
        InnerPushManager.getInstance(this).setLoged(true);
        finish();
      }
      if (!User.getInstance().GetLookAround())
      {
        InnerPushManager.getInstance(this).setLoginTime();
        InnerPushManager.getInstance(this).setLoged(true);
      }
      if (isRefresh)
        refreshLeftMenuInfo();
      return;
    }
  }

  protected void onDestroy()
  {
    mInstance = null;
    stopDownloadService();
    this.logoutTask.exit(false);
    isRefresh = true;
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 82)
    {
      if (this.mKeyDownEnabled)
      {
        this.mKeyDownEnabled = false;
        this.mKeyDownLocked = false;
        if (this.mSettingOptionPopupWindow == null)
          this.mSettingOptionPopupWindow = PopupWindowUtil.createPopupWindow(getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight(), false, this, PopupWindowUtil.createSettingAdapter(this));
        if (!this.mSettingOptionPopupWindow.isShowing())
          this.mSettingOptionPopupWindow.showAtLocation(getWindow().getDecorView(), 48, 0, 0);
        while (true)
        {
          return true;
          this.mSettingOptionPopupWindow.dismiss();
        }
      }
      this.mKeyDownLocked = true;
      return false;
    }
    if (paramInt == 4)
    {
      if ((this.mSettingOptionPopupWindow != null) && (this.mSettingOptionPopupWindow.isShowing()))
      {
        this.mSettingOptionPopupWindow.dismiss();
        return true;
      }
      if (!Setting.getInstance().isTestVersion())
      {
        BaseFragment localBaseFragment = (BaseFragment)getSupportFragmentManager().findFragmentById(2131361999);
        if ((localBaseFragment != null) && (localBaseFragment.finishAndRedirect()))
          return true;
        if ((getSupportFragmentManager().getBackStackEntryCount() > 0) && (localBaseFragment != null))
        {
          localBaseFragment.finish();
          return true;
        }
        if ((localBaseFragment != null) && (localBaseFragment.mBackKeyNotify))
        {
          localBaseFragment.finish();
          return true;
        }
        if (this.bBackKeyPressed)
        {
          this.logoutTask.exit(true);
          KXApplication.getInstance().clearCountLogin();
          InnerPushManager.getInstance(this).setLoginTime();
        }
        while (true)
        {
          return true;
          Toast.makeText(this, 2131427399, 0).show();
          this.bBackKeyPressed = true;
        }
      }
    }
    if (((BaseFragment)getSupportFragmentManager().findFragmentById(2131361999)).onKeyDown(paramInt, paramKeyEvent))
      return true;
    this.bBackKeyPressed = false;
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  protected void onNewIntent(Intent paramIntent)
  {
    ActivityUtil.checkActivityExist(this, getClass());
    String str = null;
    if (paramIntent != null)
      str = paramIntent.getAction();
    if (TextUtils.isEmpty(str));
    Bundle localBundle1 = paramIntent.getExtras();
    if ((paramIntent != null) && (localBundle1 != null) && (localBundle1.getString("logout") != null) && (localBundle1.getString("logout").equals("1")))
    {
      this.logoutTask.logout(3, null);
      this.logoutTask.exit(true);
      startSubFragment(new Intent(this, LoginActivity.class));
    }
    while (true)
    {
      ((KaixinMenuListFragment)getSupportFragmentManager().findFragmentById(2131363004)).refreshData();
      ((KaixinMenuListFragment)getSupportFragmentManager().findFragmentById(2131363004)).refreshMenuItems();
      isRefresh = true;
      if (isMenuListVisible())
        showSubFragment();
      super.onNewIntent(paramIntent);
      CloudAlbumManager.getInstance().init(this);
      CloudAlbumManager.getInstance().clearLocalPictures();
      CloudAlbumManager.getInstance().startUploadDeamon(this, 10L);
      KXEnvironment.mNeedCompleteInfo = KXEnvironment.loadBooleanParams(getApplicationContext(), "needCompleteInfo", true, false);
      if (KXEnvironment.mNeedCompleteInfo)
      {
        Intent localIntent = new Intent(this, InfoCompletedActivity.class);
        Bundle localBundle2 = new Bundle();
        localBundle2.putInt("mode", 1);
        localBundle2.putString("from", "MainActivity");
        localIntent.putExtras(localBundle2);
        startActivity(localIntent);
        InnerPushManager.getInstance(this).setLoged(true);
        finish();
      }
      if (isRefresh)
        refreshLeftMenuInfo();
      startUploadClientInfoService();
      return;
      this.logoutTask.clearAllData();
      startRightFragment(paramIntent);
    }
  }

  protected void onPause()
  {
    super.onPause();
    InnerPushManager.getInstance(this).setLoginTime();
    if (!Setting.getInstance().isTestVersion())
      MobclickAgent.onPause(this);
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    if (!User.getInstance().GetLookAround())
    {
      this.mKeyDownEnabled = true;
      if (this.mKeyDownLocked)
        onKeyDown(82, null);
      return false;
    }
    return super.onPrepareOptionsMenu(paramMenu);
  }

  protected void onResume()
  {
    super.onResume();
    if (!Setting.getInstance().isTestVersion())
      MobclickAgent.onResume(this);
    InnerPushManager.getInstance(this).setLoginTime();
    if ((!isSelectLogo()) && (mNeedCheckDialogNotice) && (!TextUtils.isEmpty(User.getInstance().getUID())) && (!KXEnvironment.mNeedCompleteInfo))
      new CheckDialogNoticeTask().execute(new Void[0]);
    mNeedCheckDialogNotice = false;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.animationRunning)
      return true;
    BaseFragment localBaseFragment = (BaseFragment)getSupportFragmentManager().findFragmentById(2131361999);
    if (localBaseFragment != null)
      localBaseFragment.onTouchEvent(paramMotionEvent);
    return super.onTouchEvent(paramMotionEvent);
  }

  public void refreshLeftMenuInfo()
  {
    ((KaixinMenuListFragment)getSupportFragmentManager().findFragmentById(2131363004)).updateSelfView();
    isRefresh = false;
  }

  public void showKaixinList()
  {
    ActivityUtil.hideInputMethod(this);
    int i = getSubFragmentGap();
    View localView = findViewById(2131361999);
    KXAbsoluteLayout.LayoutParams localLayoutParams = (KXAbsoluteLayout.LayoutParams)localView.getLayoutParams();
    if (localLayoutParams.x != i)
    {
      KXTranslateAnimation localKXTranslateAnimation = new KXTranslateAnimation(localLayoutParams.x, i, 0.0F, 0.0F);
      float f = this.mMainLayout.getLastSpeed(-1.0F);
      localKXTranslateAnimation.setDuration((int)((i - Math.min(i, localLayoutParams.x)) / f));
      localKXTranslateAnimation.setRepeatCount(0);
      localKXTranslateAnimation.setAnimationListener(this);
      localView.startAnimation(localKXTranslateAnimation);
      this.animationRunning = true;
    }
    KaixinMenuListFragment localKaixinMenuListFragment = (KaixinMenuListFragment)getSupportFragmentManager().findFragmentById(2131363004);
    if (localKaixinMenuListFragment != null)
    {
      localKaixinMenuListFragment.doResume();
      localKaixinMenuListFragment.updateMenuListVisiable(true);
    }
  }

  public void showSettingMenu()
  {
    this.mKeyDownEnabled = true;
    onKeyDown(82, new KeyEvent(0, 82));
  }

  public void showSubFragment()
  {
    ActivityUtil.hideInputMethod(this);
    int i = getSubFragmentGap();
    View localView = findViewById(2131361999);
    KXAbsoluteLayout.LayoutParams localLayoutParams = (KXAbsoluteLayout.LayoutParams)localView.getLayoutParams();
    if (localLayoutParams.x != 0)
    {
      KXTranslateAnimation localKXTranslateAnimation = new KXTranslateAnimation(localLayoutParams.x, 0.0F, 0.0F, 0.0F);
      localKXTranslateAnimation.setDuration(300 * localLayoutParams.x / i);
      localKXTranslateAnimation.setRepeatCount(0);
      localKXTranslateAnimation.setAnimationListener(this);
      localView.startAnimation(localKXTranslateAnimation);
      this.animationRunning = true;
    }
    this.mHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        KaixinMenuListFragment localKaixinMenuListFragment = (KaixinMenuListFragment)MainActivity.this.getSupportFragmentManager().findFragmentById(2131363004);
        if (localKaixinMenuListFragment != null)
          localKaixinMenuListFragment.updateMenuListVisiable(false);
      }
    }
    , 500L);
  }

  public boolean startSubFragment(Intent paramIntent)
  {
    if (paramIntent == null)
      return false;
    KaixinMenuListFragment localKaixinMenuListFragment = (KaixinMenuListFragment)getSupportFragmentManager().findFragmentById(2131363004);
    if (localKaixinMenuListFragment != null)
      localKaixinMenuListFragment.dismissDialog();
    this.myCls = paramIntent.getComponent().getClassName();
    Bundle localBundle = paramIntent.getExtras();
    try
    {
      BaseFragment localBaseFragment = (BaseFragment)Class.forName(this.myCls).newInstance();
      localBaseFragment.setPageLevel(1);
      if (localBundle != null)
        localBaseFragment.setArguments(localBundle);
      getSupportFragmentManager().beginTransaction().replace(2131361999, localBaseFragment).commit();
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static abstract interface AnimationEndListener
  {
    public abstract void onAnimationEnd();
  }

  public class CheckDialogNoticeTask extends AsyncTask<Void, Void, Void>
  {
    public CheckDialogNoticeTask()
    {
    }

    private void actionButtonData(KXDialogNoticeModel.ButtonData paramButtonData)
    {
      Fragment localFragment;
      Intent localIntent2;
      Object localObject;
      Iterator localIterator;
      if (paramButtonData != null)
      {
        UserHabitUtils.getInstance(MainActivity.this).addUserHabit("Zhichi_yixia");
        localFragment = MainActivity.this.getSupportFragmentManager().findFragmentById(2131363004);
        if ((TextUtils.isEmpty(paramButtonData.mPageId)) || (!paramButtonData.mPageId.equals("id_cloud_album")))
          break label149;
        localIntent2 = new Intent(MainActivity.this, PicturesFragment.class);
        localIntent2.putExtra("index", 2);
        localObject = CloudAlbumFragment.class;
        if ((paramButtonData.mParams != null) && (paramButtonData.mParams.size() > 0))
          localIterator = paramButtonData.mParams.entrySet().iterator();
      }
      while (true)
      {
        if (!localIterator.hasNext())
        {
          MainActivity.this.startSubFragment(localIntent2);
          if ((localFragment != null) && (localObject != null))
            ((KaixinMenuListFragment)localFragment).setSelectedMenuItem((Class)localObject);
          return;
          label149: if ((!TextUtils.isEmpty(paramButtonData.mPageId)) && (paramButtonData.mPageId.equals("id_share_repost")))
          {
            localIntent2 = new Intent(MainActivity.this, SharedPostFragment.class);
            localObject = SharedPostFragment.class;
            break;
          }
          if ((!TextUtils.isEmpty(paramButtonData.mPageId)) && (paramButtonData.mPageId.equals("id_pictures")))
          {
            localIntent2 = new Intent(MainActivity.this, PicturesFragment.class);
            localObject = PicturesFragment.class;
            break;
          }
          if ((!TextUtils.isEmpty(paramButtonData.mPageId)) && (paramButtonData.mPageId.equals("id_recommend_apps")))
          {
            localIntent2 = new Intent(MainActivity.this, GamesFragment.class);
            localObject = GamesFragment.class;
            break;
          }
          if ((!TextUtils.isEmpty(paramButtonData.mPageId)) && (paramButtonData.mPageId.equals("id_gift_news")))
          {
            localIntent2 = new Intent(MainActivity.this, GiftNewsFragment.class);
            localObject = GiftNewsFragment.class;
            break;
          }
          if (!TextUtils.isEmpty(paramButtonData.mUrl))
          {
            Intent localIntent1 = new Intent(MainActivity.this, WebPageActivity.class);
            localIntent1.putExtra("url", paramButtonData.mUrl);
            MainActivity.this.startActivity(localIntent1);
            return;
          }
          localIntent2 = new Intent(MainActivity.this, NewsFragment.class);
          localObject = NewsFragment.class;
          break;
        }
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localIntent2.putExtra((String)localEntry.getKey(), (String)localEntry.getValue());
      }
    }

    protected Void doInBackground(Void[] paramArrayOfVoid)
    {
      KXDialogNoticeEngine.getInstance().getDialogNotice(MainActivity.this.getApplicationContext());
      return null;
    }

    protected void onPostExecute(Void paramVoid)
    {
      KXDialogNoticeModel localKXDialogNoticeModel = KXDialogNoticeModel.getInstance();
      1 local1;
      if (localKXDialogNoticeModel.haveData())
      {
        KXDialogNoticeModel.ButtonData localButtonData1 = localKXDialogNoticeModel.getButtonData1();
        local1 = null;
        if (localButtonData1 != null)
          local1 = new DialogInterface.OnClickListener(localKXDialogNoticeModel)
          {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
              MainActivity.CheckDialogNoticeTask.this.actionButtonData(this.val$model.getButtonData2());
            }
          };
        KXDialogNoticeModel.ButtonData localButtonData2 = localKXDialogNoticeModel.getButtonData2();
        2 local2 = null;
        if (localButtonData2 != null)
          local2 = new DialogInterface.OnClickListener(localKXDialogNoticeModel)
          {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
              MainActivity.CheckDialogNoticeTask.this.actionButtonData(this.val$model.getButtonData1());
            }
          };
        if ((localKXDialogNoticeModel.getButtonData1() == null) || (localKXDialogNoticeModel.getButtonData2() == null))
          break label108;
        DialogUtil.showMsgDlg(MainActivity.this, localKXDialogNoticeModel.getTitle(), localKXDialogNoticeModel.getContent(), localKXDialogNoticeModel.getButtonData2().mName, localKXDialogNoticeModel.getButtonData1().mName, local1, local2);
      }
      label108: 
      do
        return;
      while (localKXDialogNoticeModel.getButtonData1() == null);
      DialogUtil.showMsgDlg(MainActivity.this, localKXDialogNoticeModel.getTitle(), localKXDialogNoticeModel.getContent(), localKXDialogNoticeModel.getButtonData1().mName, local1);
    }
  }

  private class KXTranslateAnimation extends Animation
  {
    private float mFromXValue = 0.0F;
    private float mFromYValue = 0.0F;
    private float mToXValue = 0.0F;
    private float mToYValue = 0.0F;

    public KXTranslateAnimation(float paramFloat1, float paramFloat2, float paramFloat3, float arg5)
    {
      this.mFromXValue = paramFloat1;
      this.mToXValue = paramFloat2;
      this.mFromYValue = paramFloat3;
      Object localObject;
      this.mToYValue = localObject;
      this.mFromXValue = Math.min(this.mFromXValue, MainActivity.this.getResources().getDisplayMetrics().widthPixels);
    }

    protected void applyTransformation(float paramFloat, Transformation paramTransformation)
    {
      boolean bool1 = this.mFromXValue < this.mToXValue;
      float f1 = 0.0F;
      if (bool1)
        f1 = paramFloat * (this.mToXValue - this.mFromXValue) + this.mFromXValue;
      boolean bool2 = this.mFromYValue < this.mToYValue;
      float f2 = 0.0F;
      if (bool2)
        f2 = this.mFromYValue + paramFloat * (this.mToYValue - this.mFromYValue);
      View localView1 = MainActivity.this.findViewById(2131361999);
      View localView2 = MainActivity.this.findViewById(2131363004);
      KXAbsoluteLayout.LayoutParams localLayoutParams1 = (KXAbsoluteLayout.LayoutParams)localView1.getLayoutParams();
      localLayoutParams1.x = (int)f1;
      localLayoutParams1.y = (int)f2;
      localView1.setLayoutParams(localLayoutParams1);
      if (localLayoutParams1.x >= MainActivity.this.getSubFragmentGap())
      {
        KXAbsoluteLayout.LayoutParams localLayoutParams2 = (KXAbsoluteLayout.LayoutParams)localView2.getLayoutParams();
        localLayoutParams2.width = (int)f1;
        localView2.setLayoutParams(localLayoutParams2);
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.MainActivity
 * JD-Core Version:    0.6.0
 */