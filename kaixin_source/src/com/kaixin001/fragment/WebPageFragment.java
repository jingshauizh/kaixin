package com.kaixin001.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.engine.GetUrlEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.RecordUploadTask;
import com.kaixin001.model.EventModel;
import com.kaixin001.model.EventModel.EventData;
import com.kaixin001.model.GameUserInfo;
import com.kaixin001.model.ShareInfo;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.ContentListWindow;
import com.kaixin001.view.ContentListWindow.DoSelect;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;

public class WebPageFragment extends BaseFragment
  implements ContentListWindow.DoSelect, PopupWindow.OnDismissListener, View.OnClickListener
{
  private static final int ALBUMDINDEX = 8;
  private static final int ALBUMTITLEINDEX = 11;
  private static final int ALBUMTITLLEINDEX = 53;
  public static final String EXTRA_ID = "id";
  public static final String EXTRA_TITLE = "title";
  public static final String EXTRA_UID = "uid";
  public static final String EXTRA_URL = "url";
  private static final int FNAMEINDEX = 6;
  private static final int FPIDINDEX = 4;
  private static final String FROM = "from";
  private static final String FROM_WEBPAGE = "from_webpage";
  private static final int FUIDINDEX = 5;
  private static final String GAME_START_URL = "http://iphone.kaixin001.com/game/";
  private static final String GAME_START_URL_2 = "http://iphone.kaixin002.com/game/";
  private Button backButton = null;
  private TextView centerText = null;
  private Button forwardButton = null;
  private GetGameConfigTask gameConfigtask;
  private String mActionTitle = null;
  private int mCurrentSelectType = 301;
  private EventModel.EventData mEventData = null;
  private int mEventType = 0;
  private RelativeLayout mFootbarLayout;
  private boolean mFromPicture = false;
  private GetURLTask mGetUrlTask;
  private String mId = null;
  private String mKeyWord = null;
  private String mOptionTitle = null;
  private ContentListWindow mPopupWindow;
  private ProgressBar mProgressBar = null;
  private String mTitle = null;
  private String mUid = null;
  private String mUrl = null;
  private GetUrlEngine mWebEngineDangle = new GetUrlEngine();
  private WebView mWebView = null;
  private String message;
  private Button rankingTextView;
  private Button refreshButton = null;
  private String shareImg;
  private UploadScoreTask uploadScoretask;

  private void getGameConfigs()
  {
    this.gameConfigtask = new GetGameConfigTask(null);
    this.gameConfigtask.execute(new Void[0]);
  }

  private void goToThirdPartPage(String paramString)
  {
    if ((this.mGetUrlTask != null) && (!this.mGetUrlTask.isCancelled()) && (this.mGetUrlTask.getStatus() != AsyncTask.Status.FINISHED))
      return;
    this.mGetUrlTask = null;
    if (this.mGetUrlTask == null)
      this.mGetUrlTask = new GetURLTask(null);
    this.mGetUrlTask.execute(new String[] { paramString });
  }

  private boolean isWapGame()
  {
    return (!TextUtils.isEmpty(this.mUrl)) && ((this.mUrl.startsWith("http://iphone.kaixin001.com/game/")) || (this.mUrl.startsWith("http://iphone.kaixin002.com/game/")));
  }

  private void loadGameUrl()
  {
    if (TextUtils.isEmpty(this.mUrl))
    {
      finish();
      return;
    }
    initActionView();
    this.mWebView.setVisibility(4);
    this.mWebView.setWebViewClient(new WebViewListener(null));
    if (isWapGame())
    {
      UserHabitUtils.getInstance(getActivity()).addUserHabit("visit_page_wapgame");
      String str = this.mUrl + "?from=Android";
      getGameConfigs();
      this.mWebView.loadUrl(str);
      this.rankingTextView.setVisibility(0);
      this.rankingTextView.setText("排行");
      this.rankingTextView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          WebPageFragment.this.gotoRanking();
        }
      });
      return;
    }
    this.mWebView.loadUrl(this.mUrl);
  }

  private void setBottom()
  {
    this.backButton = ((Button)findViewById(2131364034));
    this.forwardButton = ((Button)findViewById(2131364035));
    this.refreshButton = ((Button)findViewById(2131364036));
    this.backButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (WebPageFragment.this.mWebView.canGoBack())
          WebPageFragment.this.mWebView.goBack();
      }
    });
    this.forwardButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (WebPageFragment.this.mWebView.canGoForward())
          WebPageFragment.this.mWebView.goForward();
      }
    });
    this.refreshButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        WebPageFragment.this.mWebView.reload();
      }
    });
  }

  private void showPopUpWindow(View paramView)
  {
    View localView = ((LayoutInflater)getActivity().getSystemService("layout_inflater")).inflate(2130903239, null, false);
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
    float f = getResources().getDisplayMetrics().density;
    int i = getResources().getConfiguration().orientation;
    Resources localResources = getResources();
    ContentListWindow.mEditContent = new String[2];
    ContentListWindow.mEditContent[0] = localResources.getString(2131428493);
    ContentListWindow.mEditContent[1] = localResources.getString(2131428494);
    if (i == 1);
    for (this.mPopupWindow = new ContentListWindow(localView, (int)(180.0F * f), -2, true, getActivity(), this.mCurrentSelectType); ; this.mPopupWindow = new ContentListWindow(localView, (int)(180.0F * f), -2, true, getActivity(), this.mCurrentSelectType))
    {
      this.mPopupWindow.setDoSelectListener(this);
      this.mPopupWindow.setOnDismissListener(this);
      this.mPopupWindow.setOutsideTouchable(true);
      this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
      this.mPopupWindow.setClippingEnabled(false);
      int[] arrayOfInt = new int[2];
      paramView.getLocationInWindow(arrayOfInt);
      int j = 6 * (int)f;
      this.mPopupWindow.showAtLocation(paramView, 53, -(12 * (int)f), 13 + (arrayOfInt[1] + paramView.getHeight() - j));
      return;
    }
  }

  private void showUploadPhotoOptions()
  {
    selectPictureFromBulk();
  }

  protected void dealPhotoResult(String paramString1, String paramString2)
  {
    try
    {
      if (!new File(paramString1).exists())
      {
        Toast.makeText(getActivity(), 2131427841, 0).show();
        return;
      }
      Bundle localBundle = new Bundle();
      localBundle.putString("filePathName", paramString1);
      localBundle.putString("fileFrom", paramString2);
      if ((this.mEventData != null) && (!TextUtils.isEmpty(this.mEventData.getKeyWord())))
        localBundle.putSerializable("event_activity", this.mEventData);
      IntentUtil.launchEditPhotoForResult(getActivity(), this, 104, localBundle);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void doSelect(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return;
    case 0:
      ((ClipboardManager)getActivity().getSystemService("clipboard")).setText(this.mWebView.getUrl());
      Toast.makeText(getActivity(), 2131428497, 0).show();
      return;
    case 1:
    }
    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.mWebView.getUrl().toString())));
  }

  public void gotoRanking()
  {
    Intent localIntent = new Intent(getActivity(), RankingFragment.class);
    localIntent.putExtra("shareUrl", this.mUrl);
    startFragment(localIntent, true, 1);
  }

  protected void initActionView()
  {
    View localView = findViewById(2131364032);
    if ((1 != this.mEventType) && (2 != this.mEventType))
      localView.setVisibility(8);
    ImageView localImageView;
    do
    {
      return;
      localView.findViewById(2131362020).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          switch (WebPageFragment.this.mEventType)
          {
          default:
            return;
          case 1:
            WebPageFragment.this.showUploadPhotoDialog(WebPageFragment.this.mOptionTitle);
            return;
          case 2:
          }
          WebPageFragment.this.writeNewRecord(WebPageFragment.this.mKeyWord);
        }
      });
      ((TextView)localView.findViewById(2131362022)).setText(this.mActionTitle);
      localImageView = (ImageView)localView.findViewById(2131362021);
      if (1 != this.mEventType)
        continue;
      localImageView.setImageResource(2130837756);
      return;
    }
    while (2 != this.mEventType);
    localImageView.setImageResource(2130837757);
  }

  protected void initWebView()
  {
    this.mFootbarLayout = ((RelativeLayout)findViewById(2131364033));
    if (this.mFromPicture)
      this.mFootbarLayout.setVisibility(8);
    this.mWebView = ((WebView)findViewById(2131362166));
    if (Build.VERSION.SDK_INT >= 11);
    try
    {
      this.mWebView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(this.mWebView, new Object[] { "searchBoxJavaBridge_" });
      this.mWebView.getClass().getMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(this.mWebView, new Object[] { "accessibility" });
      label132: this.mWebView.setWebViewClient(new WebViewListener(null));
      this.mWebView.setPictureListener(new WebViewPictureListener(null));
      this.mWebView.setHorizontalScrollBarEnabled(false);
      this.mWebView.setHorizontalScrollbarOverlay(false);
      this.mWebView.setScrollBarStyle(0);
      this.mWebView.setDownloadListener(new DownloadListener()
      {
        public void onDownloadStart(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong)
        {
          Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString1));
          WebPageFragment.this.startActivity(localIntent);
        }
      });
      this.mWebView.setWebChromeClient(new WebChromeClient()
      {
        public void onProgressChanged(WebView paramWebView, int paramInt)
        {
          if (WebPageFragment.this.mProgressBar != null)
            WebPageFragment.this.mProgressBar.setProgress(paramInt);
          super.onProgressChanged(paramWebView, paramInt);
        }

        public void onReceivedTitle(WebView paramWebView, String paramString)
        {
          if ((paramString != null) && (!WebPageFragment.this.mFromPicture))
            WebPageFragment.this.centerText.setText(paramString);
          super.onReceivedTitle(paramWebView, paramString);
        }
      });
      this.mWebView.setOnKeyListener(new View.OnKeyListener()
      {
        public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
        {
          if ((paramKeyEvent.getAction() == 0) && (paramInt == 4) && (WebPageFragment.this.mWebView.canGoBack()))
          {
            WebPageFragment.this.mWebView.goBack();
            return true;
          }
          return false;
        }
      });
      WebSettings localWebSettings = this.mWebView.getSettings();
      localWebSettings.setSupportZoom(true);
      localWebSettings.setBuiltInZoomControls(false);
      localWebSettings.setJavaScriptEnabled(true);
      localWebSettings.setCacheMode(1);
      localWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
      this.mWebView.addJavascriptInterface(new JsInterface(), "Android");
      return;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
        localIllegalAccessException.printStackTrace();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
        localInvocationTargetException.printStackTrace();
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      break label132;
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt2 == -1) && (paramInt1 == 10))
    {
      EventModel.getInstance();
      String[] arrayOfString = new String[2];
      arrayOfString[0] = getString(2131427975);
      arrayOfString[1] = getString(2131427597);
      DialogUtil.showSelectListDlg(getActivity(), 2131427976, arrayOfString, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          if (paramInt == 0)
            IntentUtil.showWebPage(WebPageFragment.this.getActivity(), WebPageFragment.this, WebPageFragment.this.mUrl, WebPageFragment.this.getString(2131427974), WebPageFragment.this.mEventData);
        }
      }
      , 1);
    }
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362928:
    }
    SharedPreferences.Editor localEditor = getActivity().getApplicationContext().getSharedPreferences("from_webpage", 0).edit();
    localEditor.putBoolean("fromwebpage", true);
    localEditor.putString("id", this.mId);
    localEditor.commit();
    if (UploadTaskListEngine.getInstance().getWaitingTaskCount() < 20)
    {
      showUploadPhotoOptions();
      return;
    }
    showCantUploadOptions();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903419, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if (this.mWebView != null)
      this.mWebView.setVisibility(8);
    try
    {
      this.mWebView.getSettings().setBuiltInZoomControls(true);
      this.mWebView.clearCache(true);
      this.mWebView.destroy();
      cancelTask(this.mGetUrlTask);
      super.onDestroy();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void onDismiss()
  {
  }

  public void onPause()
  {
    super.onPause();
    if (this.mWebView != null)
      this.mWebView.loadUrl("");
    if (this.mGetUrlTask != null)
      this.mGetUrlTask.cancel(false);
    if (this.gameConfigtask != null)
      this.gameConfigtask.cancel(false);
    if (this.uploadScoretask != null)
      this.uploadScoretask.cancel(false);
  }

  public void onResume()
  {
    super.onResume();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      this.mFromPicture = localBundle.getBoolean("from", false);
      this.mId = localBundle.getString("id");
    }
    initWebView();
    setBottom();
    if (localBundle != null)
    {
      this.mEventData = ((EventModel.EventData)localBundle.getSerializable("event_activity"));
      this.mUrl = localBundle.getString("url");
      this.mTitle = localBundle.getString("title");
      this.mUid = localBundle.getString("uid");
      if (this.mEventData != null)
      {
        this.mEventType = this.mEventData.getEventType();
        this.mActionTitle = this.mEventData.getActionTitle();
        this.mOptionTitle = this.mEventData.getOptionTitle();
        this.mKeyWord = this.mEventData.getKeyWord();
      }
    }
    setTitleBar();
    this.mProgressBar = ((ProgressBar)findViewById(2131363639));
    this.rankingTextView = ((Button)findViewById(2131362923));
    loadGameUrl();
  }

  public void requestFinish()
  {
  }

  protected void setTitleBar()
  {
    ImageView localImageView1 = (ImageView)findViewById(2131362914);
    Activity localActivity = getActivity().getParent();
    if (localActivity != null)
      (localActivity instanceof MainActivity);
    localImageView1.setVisibility(0);
    localImageView1.setOnClickListener(new View.OnClickListener(localActivity)
    {
      public void onClick(View paramView)
      {
        if ((this.val$parent != null) && ((this.val$parent instanceof MainActivity)))
        {
          if (WebPageFragment.this.isMenuListVisibleInMain())
          {
            WebPageFragment.this.showSubActivityInMain();
            return;
          }
          WebPageFragment.this.showMenuListInMain();
          return;
        }
        WebPageFragment.this.finish();
      }
    });
    if (this.mFromPicture)
    {
      ImageView localImageView3 = (ImageView)findViewById(2131362928);
      localImageView3.setImageResource(2130839048);
      localImageView3.setVisibility(0);
      localImageView3.setOnClickListener(this);
    }
    while (true)
    {
      ((ImageView)findViewById(2131362919)).setVisibility(8);
      this.centerText = ((TextView)findViewById(2131362920));
      this.centerText.setText(2131427328);
      if (this.mFromPicture)
        this.centerText.setText(this.mTitle);
      this.centerText.setTextColor(-65794);
      this.centerText.setVisibility(0);
      return;
      ImageView localImageView2 = (ImageView)findViewById(2131362928);
      localImageView2.setImageResource(2130838622);
      localImageView2.setVisibility(0);
      localImageView2.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          WebPageFragment.this.showPopUpWindow(paramView);
        }
      });
    }
  }

  public void shareToRecord(String paramString)
  {
    String str = paramString.trim() + this.mUrl;
    if (!super.checkNetworkAndHint(true))
      return;
    RecordUploadTask localRecordUploadTask = new RecordUploadTask(getActivity().getApplicationContext());
    localRecordUploadTask.initRecordUploadTask("0", str, null, "2", "", "", "", null, 1);
    UploadTaskListEngine.getInstance().addUploadTask(localRecordUploadTask);
  }

  public void uploadScoreToServer(String paramString)
  {
    this.uploadScoretask = new UploadScoreTask(paramString);
    this.uploadScoretask.execute(new Void[0]);
  }

  private class GetGameConfigTask extends AsyncTask<Void, Void, Integer>
  {
    private GetGameConfigTask()
    {
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      return WebPageFragment.this.mWebEngineDangle.getGameConfigs(WebPageFragment.this.getActivity(), WebPageFragment.this.mUrl);
    }

    protected void onPostExecute(Integer paramInteger)
    {
      super.onPostExecute(paramInteger);
      if ((paramInteger == null) || (paramInteger.intValue() != 1))
        return;
      ShareInfo localShareInfo = ShareInfo.getInstance();
      WebPageFragment.this.message = localShareInfo.getShareMessage();
      WebPageFragment.this.shareImg = localShareInfo.getShareImg();
    }
  }

  private class GetURLTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private GetURLTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        String str = paramArrayOfString[0];
        boolean bool = WebPageFragment.this.mWebEngineDangle.getThirdPartyInfo(WebPageFragment.this.getActivity().getApplicationContext(), str);
        if (bool);
        for (i = 1; ; i = 0)
          return Integer.valueOf(i);
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        while (true)
          int i = 0;
      }
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      try
      {
        if (paramInteger.intValue() == 1)
        {
          WebPageFragment.this.mWebView.loadUrl(WebPageFragment.this.mWebEngineDangle.getUrl());
          return;
        }
        Toast.makeText(WebPageFragment.this.getActivity(), 2131427387, 0).show();
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("KaixinDesktop", "onPostExecute", localException);
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  class JsInterface
  {
    public JsInterface()
    {
    }

    public void relscore(String paramString)
    {
      if (paramString != null)
        WebPageFragment.this.uploadScoreToServer(paramString);
    }
  }

  private class UploadScoreTask extends AsyncTask<Void, Void, Integer>
  {
    private String score;

    public UploadScoreTask(String arg2)
    {
      Object localObject;
      this.score = localObject;
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      return WebPageFragment.this.mWebEngineDangle.uploadGameScore(WebPageFragment.this.getActivity(), this.score, WebPageFragment.this.mUrl);
    }

    protected void onPostExecute(Integer paramInteger)
    {
      super.onPostExecute(paramInteger);
      if (paramInteger.intValue() != 1)
        return;
      GameUserInfo localGameUserInfo = GameUserInfo.getInstance();
      String str1 = localGameUserInfo.getTopNum();
      String str2 = localGameUserInfo.getScore();
      if ((WebPageFragment.this.message.contains("score")) && (!TextUtils.isEmpty(str2)) && (!TextUtils.isEmpty(str1)))
      {
        WebPageFragment.this.message = WebPageFragment.this.message.replace("score", str2);
        WebPageFragment.this.message = WebPageFragment.this.message.replace("number", str1);
      }
      DialogUtil.showMsgDlg(WebPageFragment.this.getActivity(), null, WebPageFragment.this.message, "再来一次", "分享出去", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          WebPageFragment.this.loadGameUrl();
        }
      }
      , new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          paramDialogInterface.dismiss();
          WebPageFragment.this.loadGameUrl();
          WebPageFragment.this.shareToRecord(WebPageFragment.this.message);
        }
      });
    }
  }

  private class WebViewListener extends WebViewClient
  {
    private WebViewListener()
    {
    }

    public void onPageFinished(WebView paramWebView, String paramString)
    {
      super.onPageFinished(paramWebView, paramString);
      if (WebPageFragment.this.isNeedReturn())
        return;
      WebPageFragment.this.findViewById(2131362925).setVisibility(8);
      WebPageFragment.this.mWebView.setVisibility(0);
      if (WebPageFragment.this.mProgressBar != null)
        WebPageFragment.this.mProgressBar.setVisibility(8);
      if (WebPageFragment.this.refreshButton != null)
      {
        WebPageFragment.this.refreshButton.setBackgroundResource(2130839336);
        WebPageFragment.this.refreshButton.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            WebPageFragment.this.mWebView.reload();
          }
        });
      }
      if (paramWebView.canGoForward())
        WebPageFragment.this.forwardButton.setEnabled(true);
      while (paramWebView.canGoBack())
      {
        WebPageFragment.this.backButton.setEnabled(true);
        return;
        WebPageFragment.this.forwardButton.setEnabled(false);
      }
      WebPageFragment.this.backButton.setEnabled(false);
    }

    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      super.onPageStarted(paramWebView, paramString, paramBitmap);
      if (WebPageFragment.this.isNeedReturn());
      do
      {
        return;
        if (WebPageFragment.this.mProgressBar == null)
          continue;
        WebPageFragment.this.mProgressBar.setVisibility(0);
      }
      while (WebPageFragment.this.refreshButton == null);
      WebPageFragment.this.refreshButton.setBackgroundResource(2130837607);
      WebPageFragment.this.refreshButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          WebPageFragment.this.mWebView.stopLoading();
        }
      });
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      if (WebPageFragment.this.isNeedReturn())
        return false;
      if ((paramString.endsWith("jpg")) || (paramString.endsWith("gif")))
      {
        if (paramString.substring(10, 11).equals("r"))
        {
          String str5 = paramString.substring(5 + paramString.indexOf("fuid"), paramString.indexOf(",fname"));
          String str6 = URLDecoder.decode(paramString.substring(6 + paramString.indexOf("fname"), paramString.indexOf(",isstar")));
          IntentUtil.showHomeFragment(WebPageFragment.this, str5, str6);
        }
        while (true)
        {
          return true;
          if (paramString.indexOf(",albumtitle") < 53)
            return true;
          String str1 = paramString.substring(8 + paramString.indexOf("albumid"), paramString.indexOf(",pid"));
          String str2 = paramString.substring(4 + paramString.indexOf("pid"), paramString.indexOf(",albumtitle"));
          String str3 = URLDecoder.decode(paramString.substring(11 + paramString.indexOf("albumtitle"), paramString.indexOf(",fname")));
          String str4 = paramString.substring(5 + paramString.indexOf("fuid"), paramString.indexOf(",flogo"));
          Bundle localBundle = new Bundle();
          localBundle.putString("albumId", str1);
          localBundle.putString("pid", str2);
          localBundle.putString("title", str3);
          localBundle.putString("fuid", str4);
          IntentUtil.showViewPhotoForActivity(WebPageFragment.this, localBundle);
          UserHabitUtils.getInstance(WebPageFragment.this.getActivity()).addUserHabit("Photo_Activity_Photo_Click_" + WebPageFragment.this.mId);
        }
      }
      return false;
    }
  }

  private class WebViewPictureListener
    implements WebView.PictureListener
  {
    private WebViewPictureListener()
    {
    }

    public void onNewPicture(WebView paramWebView, Picture paramPicture)
    {
      if (WebPageFragment.this.getView() == null)
        return;
      WebPageFragment.this.findViewById(2131362925).setVisibility(8);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.WebPageFragment
 * JD-Core Version:    0.6.0
 */