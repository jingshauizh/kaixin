package com.kaixin001.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.activity.wxapi.WXManager;
import com.kaixin001.engine.ApplistEngine;
import com.kaixin001.engine.KXAppShareEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.model.ApplistModel;
import com.kaixin001.model.KXAppShareModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.HttpUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXPopupListWindow;
import com.kaixin001.view.KXPopupListWindow.DoSelect;
import com.tencent.mm.sdk.openapi.IWXAPI;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MoreItemDetailFragment extends BaseFragment
  implements PopupWindow.OnDismissListener, KXPopupListWindow.DoSelect
{
  private static final String LOGTAG = "MoreItemDetailFragment";
  public static final String UC_DOWNLOAD_URL = "http://down2.uc.cn/ucbrowser/?pub=chens@kxyy&url=&title=";
  private WebView contentView = null;
  private AddScoreTask mAddScoreTask;
  private String mAppEntry = "";
  private String mAppId = "";
  private KXAppShareEngine mAppShareEngine = new KXAppShareEngine();
  private KXAppShareModel mAppShareModel = new KXAppShareModel();
  private String mDownUrl = "";
  private GetShareWxInfoTask mGetShareWxInfoTask;
  private GetThirdAppTokenTask mGetThirdAppTokenTask;
  private String mLabel = "";
  private String mLink = "";
  private int mOperation = -1;
  private String mPackageName = "";
  private KXPopupListWindow mPopupWindow;
  private ProgressDialog mProgressDialog;
  private ArrayList<Integer> mShareList;
  private boolean mShowShareBtn = false;
  private WxReceiver mWxReceiver;

  private void addScore()
  {
    this.mAddScoreTask = new AddScoreTask(null);
    this.mAddScoreTask.execute(new Void[0]);
  }

  private void downloadByUc(String paramString)
  {
    Intent localIntent = new Intent();
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.setData(Uri.parse(this.mDownUrl));
    if (paramString.equals("com.uc.browser"))
    {
      localIntent.setClassName("com.uc.browser", "com.uc.browser.ActivityUpdate");
      startActivity(localIntent);
      return;
    }
    if (paramString.equals("com.UCMobile"))
    {
      localIntent.setClassName("com.UCMobile", "com.UCMobile.main.UCMobile");
      startActivity(localIntent);
      return;
    }
    startActivity(localIntent);
  }

  private void getShareInfo()
  {
    String str = getResources().getString(2131428506);
    this.mProgressDialog = ProgressDialog.show(getActivity(), null, str);
    this.mGetShareWxInfoTask = new GetShareWxInfoTask(null);
    this.mGetShareWxInfoTask.execute(new Void[0]);
  }

  public static String getUcVersion(Context paramContext)
  {
    Iterator localIterator = paramContext.getPackageManager().getInstalledPackages(0).iterator();
    PackageInfo localPackageInfo;
    do
    {
      if (!localIterator.hasNext())
        return null;
      localPackageInfo = (PackageInfo)localIterator.next();
    }
    while ((!localPackageInfo.packageName.equals("com.uc.browser")) && (!localPackageInfo.packageName.equals("com.UCMobile")));
    return localPackageInfo.packageName;
  }

  private void openUrl()
  {
    this.contentView.loadUrl(this.mDownUrl);
    this.mOperation = -1;
  }

  private void registerWxReceivers()
  {
    if (getActivity() != null)
    {
      this.mWxReceiver = new WxReceiver();
      IntentFilter localIntentFilter = new IntentFilter();
      localIntentFilter.addAction("com.kx.share.wx");
      getActivity().registerReceiver(this.mWxReceiver, localIntentFilter);
    }
  }

  private void showPopUpWindow(View paramView)
  {
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(Integer.valueOf(2130838213));
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(Integer.valueOf(2131428582));
    if ((this.mPopupWindow != null) && (this.mPopupWindow.isShowing()))
      try
      {
        this.mPopupWindow.dismiss();
        this.mPopupWindow = null;
        return;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    View localView = getActivity().getLayoutInflater().inflate(2130903295, null, false);
    float f = getResources().getDisplayMetrics().density;
    int i = getResources().getConfiguration().orientation;
    int j = (int)(159.0F * f);
    if (i == 1);
    for (this.mPopupWindow = new KXPopupListWindow(localView, j, -2, true, getActivity(), localArrayList1, localArrayList2, null); ; this.mPopupWindow = new KXPopupListWindow(localView, j, -2, true, getActivity(), localArrayList1, localArrayList2, null))
    {
      this.mPopupWindow.setDoSelectListener(this);
      this.mPopupWindow.setOnDismissListener(this);
      this.mPopupWindow.setOutsideTouchable(true);
      this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
      this.mPopupWindow.setClippingEnabled(false);
      int[] arrayOfInt = new int[2];
      paramView.getLocationOnScreen(arrayOfInt);
      int k = arrayOfInt[1] + (int)(48.0F * f);
      int m = (int)(getActivity().getResources().getDisplayMetrics().widthPixels - j - (int)(2.0F * KXEnvironment.DENSITY));
      this.mPopupWindow.showAtLocation(paramView, 51, m, k);
      return;
    }
  }

  private void showProgressBar(boolean paramBoolean)
  {
    if (isNeedReturn())
      return;
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131362165);
    if (paramBoolean)
    {
      localLinearLayout.setVisibility(0);
      return;
    }
    localLinearLayout.setVisibility(8);
  }

  private void unregisterWxReceivers()
  {
    if ((this.mWxReceiver != null) && (getActivity() != null))
      getActivity().unregisterReceiver(this.mWxReceiver);
  }

  public void doSelect(int paramInt)
  {
    if (paramInt == 0)
    {
      UserHabitUtils.getInstance(getActivity()).addUserHabit("kxapp_share_wx");
      getShareInfo();
    }
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    String str = null;
    Iterator localIterator;
    if (localBundle != null)
    {
      str = localBundle.getString("action");
      this.mShareList = localBundle.getIntegerArrayList("share");
      if (this.mShareList != null)
      {
        localIterator = this.mShareList.iterator();
        if (localIterator.hasNext())
          break label99;
      }
    }
    while (true)
    {
      if ((str != null) && (!str.equalsIgnoreCase("com.kaixin001.VIEW_PUSH_DETAIL")) && (CrashRecoverUtil.isCrashBefore()))
      {
        CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
        IntentUtil.returnToDesktop(getActivity());
      }
      return;
      label99: if (((Integer)localIterator.next()).intValue() != 1)
        break;
      this.mShowShareBtn = true;
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903231, paramViewGroup, false);
  }

  public void onDestroy()
  {
    WebView localWebView = (WebView)findViewById(2131363096);
    if (localWebView != null)
    {
      localWebView.setVisibility(8);
      localWebView.clearCache(true);
      localWebView.destroy();
    }
    cancelTask(this.mGetThirdAppTokenTask);
    super.onDestroy();
  }

  public void onDestroyView()
  {
    cancelTask(this.mGetShareWxInfoTask);
    cancelTask(this.mGetThirdAppTokenTask);
    cancelTask(this.mAddScoreTask);
    super.onDestroyView();
  }

  public void onDismiss()
  {
  }

  public void onPause()
  {
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
    WebView localWebView = (WebView)findViewById(2131363096);
    if (localWebView != null)
    {
      localWebView.clearChildFocus(localWebView.getFocusedChild());
      localWebView.clearFocus();
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      String str1 = localBundle.getString("label");
      if (str1 != null)
        this.mLabel = str1;
      String str2 = localBundle.getString("link");
      if (str2 != null)
        this.mLink = str2;
      String str3 = localBundle.getString("operation");
      if (str3 != null)
        this.mOperation = Integer.valueOf(str3).intValue();
      String str4 = localBundle.getString("downloadUrl");
      if (str4 != null)
        this.mDownUrl = str4;
      String str5 = localBundle.getString("packageName");
      if (str5 != null)
        this.mPackageName = str5;
      String str6 = localBundle.getString("appEntry");
      if (str6 != null)
        this.mAppEntry = str6;
      String str7 = localBundle.getString("appId");
      if (str7 != null)
        this.mAppId = str7;
    }
    if (!dataInited())
      UserHabitUtils.getInstance(getActivity()).addUserHabit("enter_kxapp_" + this.mLabel);
    enableSlideBack(false);
    setTitleBar();
    this.contentView = ((WebView)paramView.findViewById(2131363096));
    if (Build.VERSION.SDK_INT >= 11);
    try
    {
      this.contentView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(this.contentView, new Object[] { "searchBoxJavaBridge_" });
      this.contentView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(this.contentView, new Object[] { "accessibility" });
      label309: this.contentView.setVisibility(4);
      this.contentView.setWebViewClient(new WebViewListener(null));
      this.contentView.setDownloadListener(new DownloadListener()
      {
        public void onDownloadStart(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong)
        {
          Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString1));
          MoreItemDetailFragment.this.startActivity(localIntent);
          MoreItemDetailFragment.this.finish();
        }
      });
      if (this.mLink.startsWith("http://wap.kaixin001.com"))
      {
        this.mLink = this.mLink.replace("http://wap.kaixin001.com", "");
        this.mLink = HttpProxy.getWapProxyURL(this.mLink);
      }
      this.contentView.loadUrl(this.mLink);
      WebSettings localWebSettings = this.contentView.getSettings();
      localWebSettings.setSupportZoom(true);
      localWebSettings.setBuiltInZoomControls(true);
      localWebSettings.setJavaScriptEnabled(true);
      this.contentView.setHorizontalScrollBarEnabled(true);
      this.contentView.setHorizontalScrollbarOverlay(true);
      this.contentView.setOnTouchListener(new View.OnTouchListener()
      {
        public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
        {
          switch (paramMotionEvent.getAction())
          {
          default:
          case 0:
          case 1:
          }
          while (true)
          {
            return false;
            if (paramView.hasFocus())
              continue;
            paramView.requestFocus();
          }
        }
      });
      if (this.mOperation > -1)
      {
        RelativeLayout localRelativeLayout = (RelativeLayout)paramView.findViewById(2131363097);
        localImageView = (ImageView)paramView.findViewById(2131363098);
        localRelativeLayout.setVisibility(0);
      }
      switch (this.mOperation)
      {
      default:
        return;
      case 0:
      case 1:
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
        localIllegalAccessException.printStackTrace();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      ImageView localImageView;
      while (true)
        localInvocationTargetException.printStackTrace();
      localImageView.setImageResource(2130837552);
      localImageView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (MoreItemDetailFragment.this.mPackageName.equals("html5"))
          {
            MoreItemDetailFragment.this.contentView.loadUrl(MoreItemDetailFragment.this.mDownUrl);
            MoreItemDetailFragment.this.mOperation = -1;
            return;
          }
          if (!TextUtils.isEmpty(MoreItemDetailFragment.this.mAppId))
          {
            String[] arrayOfString = new String[3];
            arrayOfString[0] = MoreItemDetailFragment.access$4(MoreItemDetailFragment.this);
            arrayOfString[1] = MoreItemDetailFragment.access$11(MoreItemDetailFragment.this);
            arrayOfString[2] = MoreItemDetailFragment.access$15(MoreItemDetailFragment.this);
            MoreItemDetailFragment.this.mGetThirdAppTokenTask = new MoreItemDetailFragment.GetThirdAppTokenTask(MoreItemDetailFragment.this, null);
            MoreItemDetailFragment.this.mGetThirdAppTokenTask.execute(arrayOfString);
            return;
          }
          try
          {
            Intent localIntent = new Intent();
            localIntent.setComponent(new ComponentName(MoreItemDetailFragment.this.mPackageName, MoreItemDetailFragment.this.mAppEntry));
            MoreItemDetailFragment.this.startActivity(localIntent);
            return;
          }
          catch (Exception localException)
          {
            KXLog.e("MoreItemDetailFragment", "Start third APP", localException);
          }
        }
      });
      return;
      localImageView.setImageResource(2130837555);
      localImageView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          MoreItemDetailFragment.this.openUrl();
        }
      });
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      break label309;
    }
  }

  protected void setTitleBar()
  {
    ImageView localImageView1 = (ImageView)findViewById(2131362914);
    localImageView1.setVisibility(0);
    localImageView1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        MoreItemDetailFragment.this.finish();
      }
    });
    ImageView localImageView2 = (ImageView)findViewById(2131362928);
    if (!WXManager.getInstance().getWxApi().isWXAppInstalled())
      this.mShowShareBtn = false;
    if (this.mShowShareBtn)
    {
      localImageView2.setVisibility(0);
      localImageView2.setImageResource(2130838153);
      localImageView2.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (MoreItemDetailFragment.this.getActivity() != null)
          {
            UserHabitUtils.getInstance(MoreItemDetailFragment.this.getActivity()).addUserHabit("kxapp_share");
            MoreItemDetailFragment.this.showPopUpWindow(paramView);
          }
        }
      });
    }
    while (true)
    {
      ((ImageView)findViewById(2131362919)).setVisibility(8);
      TextView localTextView = (TextView)findViewById(2131362920);
      localTextView.setText(this.mLabel);
      localTextView.setVisibility(0);
      return;
      localImageView2.setVisibility(8);
    }
  }

  private class AddScoreTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    private AddScoreTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      try
      {
        Integer localInteger = Integer.valueOf(MoreItemDetailFragment.this.mAppShareEngine.getShareWxAddScore(MoreItemDetailFragment.this.getActivity(), MoreItemDetailFragment.this.mAppShareModel, User.getInstance().getUID(), MoreItemDetailFragment.this.mAppId, MoreItemDetailFragment.this.mAppShareModel.mGiftInfo));
        return localInteger;
      }
      catch (Exception localException)
      {
      }
      return Integer.valueOf(-1);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      MoreItemDetailFragment.this.unregisterWxReceivers();
      if (MoreItemDetailFragment.this.getActivity() == null)
        return;
      if (paramInteger.intValue() == 1)
      {
        if ((!TextUtils.isEmpty(MoreItemDetailFragment.this.mAppShareModel.mResultInfo)) && (!MoreItemDetailFragment.this.mAppShareModel.mResultInfo.startsWith("null")))
        {
          Toast.makeText(MoreItemDetailFragment.this.getActivity(), MoreItemDetailFragment.this.mAppShareModel.mResultInfo, 1).show();
          return;
        }
        Toast.makeText(MoreItemDetailFragment.this.getActivity(), "分享成功！", 1).show();
        return;
      }
      Toast.makeText(MoreItemDetailFragment.this.getActivity(), "发送失败！", 1).show();
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  private class GetShareWxInfoTask extends AsyncTask<Void, Void, Integer>
  {
    private GetShareWxInfoTask()
    {
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      try
      {
        MoreItemDetailFragment.this.mAppShareModel.init();
        int i = MoreItemDetailFragment.this.mAppShareEngine.getShareWxGetInfo(MoreItemDetailFragment.this.getActivity(), MoreItemDetailFragment.this.mAppShareModel, User.getInstance().getUID(), MoreItemDetailFragment.this.mAppId);
        boolean bool = TextUtils.isEmpty(MoreItemDetailFragment.this.mAppShareModel.mImgUrl);
        if (!bool);
        try
        {
          byte[] arrayOfByte = HttpUtil.doGetData(MoreItemDetailFragment.this.mAppShareModel.mImgUrl);
          MoreItemDetailFragment.this.mAppShareModel.mIcon = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
          return Integer.valueOf(i);
        }
        catch (Exception localException2)
        {
          while (true)
            localException2.printStackTrace();
        }
      }
      catch (Exception localException1)
      {
      }
      return Integer.valueOf(-1);
    }

    protected void onPostExecute(Integer paramInteger)
    {
      if (MoreItemDetailFragment.this.mProgressDialog != null)
      {
        MoreItemDetailFragment.this.mProgressDialog.dismiss();
        MoreItemDetailFragment.this.mProgressDialog = null;
      }
      if (paramInteger.intValue() != 1)
      {
        MoreItemDetailFragment.this.mAppShareModel.mTitle = "开心网游戏";
        MoreItemDetailFragment.this.mAppShareModel.mDes = "偷菜、城市等大翻新喽~想知道有什么？快来体验吧~";
        MoreItemDetailFragment.this.mAppShareModel.mImgUrl = "";
        MoreItemDetailFragment.this.mAppShareModel.mLinkUrl = MoreItemDetailFragment.this.mLink;
        MoreItemDetailFragment.this.mAppShareModel.mGiftInfo = "";
      }
      try
      {
        if (MoreItemDetailFragment.this.mAppShareModel.mIcon == null)
          MoreItemDetailFragment.this.mAppShareModel.mIcon = BitmapFactory.decodeResource(MoreItemDetailFragment.this.getActivity().getResources(), 2130838461);
        if ((TextUtils.isEmpty(MoreItemDetailFragment.this.mAppShareModel.mLinkUrl)) || (MoreItemDetailFragment.this.mAppShareModel.mLinkUrl.startsWith("null")))
          MoreItemDetailFragment.this.mAppShareModel.mLinkUrl = "http://wap.kaixin001.com";
        MoreItemDetailFragment.this.registerWxReceivers();
        WXManager.getInstance().sendMsgToFriendCircle(MoreItemDetailFragment.this.getActivity(), MoreItemDetailFragment.this.mAppShareModel.mTitle, MoreItemDetailFragment.this.mAppShareModel.mDes, MoreItemDetailFragment.this.mAppShareModel.mIcon, MoreItemDetailFragment.this.mAppShareModel.mLinkUrl);
        return;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
  }

  private class GetThirdAppTokenTask extends AsyncTask<String, Void, Boolean>
  {
    private String appEntry;
    private String packageName;

    private GetThirdAppTokenTask()
    {
    }

    protected Boolean doInBackground(String[] paramArrayOfString)
    {
      try
      {
        String str = paramArrayOfString[0];
        this.packageName = paramArrayOfString[1];
        this.appEntry = paramArrayOfString[2];
        Boolean localBoolean = Boolean.valueOf(ApplistEngine.getInstance().getThirdAppToken(MoreItemDetailFragment.this.getActivity().getApplicationContext(), str));
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
      {
        MoreItemDetailFragment.this.finish();
        return;
      }
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
          try
          {
            localIntent.setComponent(new ComponentName(this.packageName, this.appEntry));
            MoreItemDetailFragment.this.startActivity(localIntent);
            return;
          }
          catch (Exception localException2)
          {
            KXLog.e("MoreItemDetailFragment", "Start third APP", localException2);
            return;
          }
        }
      }
      catch (Exception localException1)
      {
        KXLog.e("MoreItemDetailFragment", "onPostExecute", localException1);
        Toast.makeText(MoreItemDetailFragment.this.getActivity(), 2131427727, 0).show();
        return;
      }
      Toast.makeText(MoreItemDetailFragment.this.getActivity(), 2131427727, 0).show();
    }
  }

  private class WebViewListener extends WebViewClient
  {
    private WebViewListener()
    {
    }

    public void onPageFinished(WebView paramWebView, String paramString)
    {
      if (MoreItemDetailFragment.this.isNeedReturn())
        return;
      MoreItemDetailFragment.this.showProgressBar(false);
      paramWebView.setVisibility(0);
      paramWebView.loadUrl("javascript:javascript:document.getElementById('head').style.display='none';javascript:document.getElementById('menu').style.display='none';");
      super.onPageFinished(paramWebView, paramString);
    }

    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      if (MoreItemDetailFragment.this.isNeedReturn());
      do
      {
        return;
        super.onPageStarted(paramWebView, paramString, paramBitmap);
        MoreItemDetailFragment.this.showProgressBar(true);
      }
      while (MoreItemDetailFragment.this.mOperation >= 0);
      ((RelativeLayout)MoreItemDetailFragment.this.findViewById(2131363097)).setVisibility(8);
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      if (MoreItemDetailFragment.this.isNeedReturn());
      do
        return false;
      while (paramString.indexOf("tel:") <= -1);
      MoreItemDetailFragment.this.startActivity(new Intent("android.intent.action.DIAL", Uri.parse(paramString)));
      return true;
    }
  }

  public class WxReceiver extends BroadcastReceiver
  {
    public WxReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      MoreItemDetailFragment.this.addScore();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.MoreItemDetailFragment
 * JD-Core Version:    0.6.0
 */