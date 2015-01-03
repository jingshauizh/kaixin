package com.kaixin001.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.engine.ActiveClientEngine;
import com.kaixin001.engine.CloudAlbumEngine;
import com.kaixin001.engine.LoginEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.model.AlbumListModel;
import com.kaixin001.model.AlbumPhotoModel;
import com.kaixin001.model.DiaryModel;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.KaixinConst;
import com.kaixin001.model.MessageCenterModel;
import com.kaixin001.model.NewsModel;
import com.kaixin001.model.RepostModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.model.UserInfoModel;
import com.kaixin001.model.VoteModel;
import com.kaixin001.task.GetFriendsListTask;
import com.kaixin001.task.GetLoginUserRealNameTask;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CloudAlbumManager;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.InnerPushManager;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.SocialShareManager;
import com.kaixin001.util.StringUtil;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.KXScrollView;
import com.kaixin001.view.KXScrollView.SizeChangeListener;
import com.tencent.tauth.TAuthView;
import com.tencent.tauth.TencentOpenAPI;
import com.tencent.tauth.bean.OpenId;
import com.tencent.tauth.http.Callback;
import com.weibo.sdk.android.Weibo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONObject;

public class LoginActivity extends KXActivity
  implements View.OnClickListener, TextWatcher
{
  public static final String ACCESS_TOKEN = "access_token";
  public static final String ACTION = "action";
  public static final String AUTH_BROADCAST = "com.tencent.auth.BROWSER";
  private static final String BOARDCAST_TAG = "com.kx.login.verify";
  public static final String CALLBACK1 = "callback";
  public static final String CLIENT_ID = "client_id";
  public static final String ERROR_DES = "error_description";
  public static final String ERROR_RET = "error";
  public static final String EXPIRES_IN = "expires_in";
  public static final String FROM_LOOKROUND = "fromlook";
  public static final String LOGIN_ACTION = "login";
  public static final String LOOK_ACTION = "look";
  protected static final int MENU_CLEAR_ACCOUNT_ID = 404;
  protected static final int MENU_EXIT_ID = 403;
  protected static final int MENU_REGISTER_ID = 401;
  protected static final int MENU_SETTINGS_ID = 402;
  private static final String QQ_LOGIN = "qq_login";
  private static final int REQUEST_ADD_FRIENDS = 101;
  private static final int REQUEST_CAPTCHA = 102;
  private static final int REQUEST_USERINFO_UPDATE = 100;
  public static final String SCOPE = "scope";
  private static final String SP_ACCOUNTS_NAME = "sharedpreferences_accounts";
  private static final String TAG = "LoginActivity";
  public static final String TAG_FINDFRIEND = "tag_findfriend";
  private static final String TAG_QQ = "QQ";
  private static final String TAG_WEIBO = "WEIBO";
  public static final String TARGET = "target";
  public static boolean isSwitchAccountLogin = false;
  protected final int DIALOG_PASSWORD_CHANGED = 1;
  private boolean bIsCanceled = false;
  private boolean bIsFromShareText = false;
  Button btn;
  private String from = null;
  private int isRegist = 0;
  private boolean isfromFindFriend = false;
  DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
  {
    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      switch (paramInt)
      {
      default:
        return;
      case 0:
        LoginActivity.this.registerFromWeb();
        if (!User.getInstance().GetLookAround())
        {
          UserHabitUtils.getInstance(LoginActivity.this.getApplicationContext()).addUserHabit("<Login_RegisterEmail>");
          return;
        }
        if (LoginActivity.this.isfromFindFriend)
        {
          UserHabitUtils.getInstance(LoginActivity.this.getApplicationContext()).addUserHabit("<FindFriend_RegisterEmail>");
          return;
        }
        UserHabitUtils.getInstance(LoginActivity.this.getApplicationContext()).addUserHabit("<JustLook_RegisterEmail>");
        return;
      case 1:
      }
      Intent localIntent = new Intent(LoginActivity.this, PhoneRegisterActivity.class);
      LoginActivity.this.startActivity(localIntent);
      if (!User.getInstance().GetLookAround())
      {
        UserHabitUtils.getInstance(LoginActivity.this.getApplicationContext()).addUserHabit("<Login_RegisterSMS>");
        return;
      }
      if (LoginActivity.this.isfromFindFriend)
      {
        UserHabitUtils.getInstance(LoginActivity.this.getApplicationContext()).addUserHabit("<FindFriend_RegisterSMS>");
        return;
      }
      UserHabitUtils.getInstance(LoginActivity.this.getApplicationContext()).addUserHabit("<JustLool_RegisterSMS>");
    }
  };
  DialogInterface.OnClickListener listenerPwd = new DialogInterface.OnClickListener()
  {
    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      switch (paramInt)
      {
      default:
        return;
      case 0:
        Intent localIntent3 = new Intent(LoginActivity.this, PwdByEmailActivity.class);
        LoginActivity.this.startActivity(localIntent3);
        UserHabitUtils.getInstance(LoginActivity.this.getApplicationContext()).addUserHabit("<FindPwd_Email>");
        return;
      case 1:
        Intent localIntent2 = new Intent(LoginActivity.this, GetPwdByPhoneActivity.class);
        LoginActivity.this.startActivity(localIntent2);
        UserHabitUtils.getInstance(LoginActivity.this.getApplicationContext()).addUserHabit("<FindPwd_PhoneMsg>");
        return;
      case 2:
      }
      Intent localIntent1 = new Intent(LoginActivity.this, PwdOtherActivity.class);
      LoginActivity.this.startActivity(localIntent1);
      UserHabitUtils.getInstance(LoginActivity.this.getApplicationContext()).addUserHabit("<FindPwd_Other>");
    }
  };
  private LoginEngine loginEngine = LoginEngine.getInstance();
  private View.OnClickListener loginQQOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramView)
    {
      LoginActivity.this.tencentAuth("100228505", "_self");
      UserHabitUtils.getInstance(LoginActivity.this.getApplicationContext()).addUserHabit("<Login_QQ>");
    }
  };
  private LoginTask loginTask = null;
  private View.OnClickListener loginWeiboOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramView)
    {
      LoginActivity.this.mWeibo.authorize(LoginActivity.this);
      UserHabitUtils.getInstance(LoginActivity.this.getApplicationContext()).addUserHabit("<Login_WEIBO>");
    }
  };
  private boolean mAddFriend = false;
  private String mAlbumId = "";
  private String mAlbumTitle = "";
  private View mBottomLayout;
  private String mFname = "";
  private String mFuid = "";
  private View mIdDeleteBtn;
  private KXScrollView mScrollView;
  private String mUploadPhotoFilePath = "";
  private String mUploadText = "";
  private VerifyReceiver mVerifyReceiver;
  private Weibo mWeibo;
  private ProgressDialog progressDialog = null;
  private AuthReceiver receiver;
  private AuthReceiver2 receiver2;
  private SocialShareManager shareManager;
  private TextView tvCenterText = null;
  private TextView tvRightText = null;
  private AutoCompleteTextView txtID;
  private EditText txtPassword;

  private void addAccountToSharedPreferences()
  {
    String str1 = User.getInstance().getUID();
    String str2 = User.getInstance().getAccount();
    SharedPreferences localSharedPreferences = getSharedPreferences("sharedpreferences_accounts", 0);
    if (!localSharedPreferences.contains(str1))
    {
      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
      localEditor.putString(str1, str2);
      localEditor.commit();
    }
  }

  private void cancelLogin()
  {
    if ((this.loginTask != null) && (this.loginTask.getStatus() != AsyncTask.Status.FINISHED) && (!this.loginTask.isCancelled()))
    {
      this.bIsCanceled = true;
      this.loginEngine.cancel();
      this.loginTask.cancel(true);
      this.loginTask = null;
    }
  }

  private void cancelNotification()
  {
    if (!Setting.getInstance().showLoginNotification())
      return;
    ((NotificationManager)getSystemService("notification")).cancelAll();
  }

  private void clearAccount()
  {
    User.getInstance().clearAccout();
    this.txtID.setText("");
    this.txtID.requestFocus();
  }

  private void clearAllModels()
  {
    NewsModel.getInstance().clear();
    NewsModel.clearAllHomeModels();
    NewsModel.getMyHomeModel().clear();
    FriendsModel.getInstance().clear();
    UserInfoModel.getInstance().clear();
    AlbumListModel.getInstance().clear();
    AlbumListModel.getMyAlbumList().clear();
    AlbumPhotoModel.getInstance().clear();
    DiaryModel.getInstance().clear();
    MessageCenterModel.getInstance().clear();
    RepostModel.getInstance().clear();
    VoteModel.getInstance().clear();
  }

  private static List convertMap2List(Map paramMap)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramMap.entrySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localArrayList;
      localArrayList.add(((Map.Entry)localIterator.next()).getValue());
    }
  }

  private void doLogin(String paramString1, String paramString2, String paramString3)
  {
    String str1 = String.valueOf(this.txtID.getText()).trim();
    String str2 = String.valueOf(this.txtPassword.getText());
    if (paramString3.equals("login"))
    {
      if (str1.length() == 0)
      {
        DialogUtil.showKXAlertDialog(this, 2131427389, null);
        this.txtID.requestFocus();
        return;
      }
      if (str2.length() == 0)
      {
        DialogUtil.showKXAlertDialog(this, 2131427390, null);
        this.txtPassword.requestFocus();
        return;
      }
    }
    View localView = getCurrentFocus();
    if (localView != null)
      ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(localView.getWindowToken(), 2);
    doLogin(false, str1, str2, null, paramString1, paramString2, null, paramString3);
  }

  private void doLogin(boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    if (!super.checkNetworkAndHint(true)){
       return;
       cancelLogin();
    }
   
     
    this.loginEngine.enableNewMessageNotification(false);
    this.bIsCanceled = false;
    this.loginTask = new LoginTask(null);
    this.loginTask.execute(new String[] { paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7 });
    DialogUtil.dismissDialog(this.progressDialog);
    if (paramString7.equals("look"));
    for (int i = 2131428245; !isFinishing(); i = 2131427360)
    {
      this.progressDialog = ProgressDialog.show(this, "", getResources().getText(i), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          LoginActivity.this.cancelLogin();
        }
      });
      return;
    }
    
  }

  private void forgetPwd()
  {
    DialogUtil.showSelectListDlg(this, 2131427341, new String[] { "电子邮件", "手机号码", "其他" }, this.listenerPwd, 1);
  }

  private ArrayList<String> getAllAccounts()
  {
    return (ArrayList)convertMap2List(getSharedPreferences("sharedpreferences_accounts", 0).getAll());
  }

  private void initBottomBtnsLayout()
  {
    findViewById(2131362991).setOnClickListener(this.loginQQOnClickListener);
    registerIntentReceivers();
    this.mWeibo = Weibo.getInstance("3370195860", "http://www.kaixin001.com");
    this.shareManager = new SocialShareManager(this);
    findViewById(2131362992).setOnClickListener(this.loginWeiboOnClickListener);
    registerIntentReceivers2();
  }

  private void loginSuccess()
  {
    try
    {
      UploadTaskListEngine.getInstance().register(this.mHandler);
      str = getIntent().getAction();
      if (!TextUtils.isEmpty(this.mUploadPhotoFilePath))
      {
        Bundle localBundle1 = new Bundle();
        localBundle1.putString("filePathName", this.mUploadPhotoFilePath);
        localBundle1.putString("fileFrom", "gallery");
        localBundle1.putString("fragment", "UploadPhotoFragment");
        Intent localIntent1 = new Intent(this, MainActivity.class);
        localIntent1.putExtras(localBundle1);
        AnimationUtil.startActivity(this, localIntent1, 1);
        sendBroadcast(new Intent("com.kaixin001.WIDGET_CHANGE_USER"));
        sendBroadcast(new Intent("com.kaixin001.WIDGET_UPDATE"));
        sendLoginBroadcast();
        addAccountToSharedPreferences();
        InnerPushManager.getInstance(this).setLoginTime();
        GetFriendsListTask localGetFriendsListTask = new GetFriendsListTask(this);
        Integer[] arrayOfInteger = new Integer[1];
        arrayOfInteger[0] = Integer.valueOf(1);
        localGetFriendsListTask.execute(arrayOfInteger);
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        String str;
        KXLog.e("login success", localException.toString());
        continue;
        if (!TextUtils.isEmpty(this.mAlbumId))
        {
          Intent localIntent2 = new Intent(this, MainActivity.class);
          Bundle localBundle2 = new Bundle();
          localBundle2.putString("fragment", "AlbumViewFragment");
          localBundle2.putString("albumId", this.mAlbumId);
          localBundle2.putString("title", this.mAlbumTitle);
          localBundle2.putString("fuid", this.mFuid);
          localBundle2.putString("fname", this.mFname);
          localBundle2.putString("pwd", "");
          localIntent2.putExtras(localBundle2);
          AnimationUtil.startActivity(this, localIntent2, 1);
          finish();
          continue;
        }
        if (!TextUtils.isEmpty(this.mUploadText))
        {
          Intent localIntent3 = new Intent(this, MainActivity.class);
          Bundle localBundle3 = new Bundle();
          localBundle3.putString("fragment", "WriteWeiboFragment");
          localIntent3.putExtras(localBundle3);
          localIntent3.setType("text");
          localIntent3.putExtra("android.intent.extra.TEXT", this.mUploadText);
          startActivity(localIntent3);
          finish();
          continue;
        }
        if ((!TextUtils.isEmpty(str)) && (str.equals("com.kaixin001.VIEW_LOGIN")))
        {
          finish();
          continue;
        }
        if (this.bIsFromShareText)
        {
          setResult(-1);
          finish();
          continue;
        }
        sendLoggedInNotification();
        if (User.getInstance().imcomplete == 1)
        {
          KXEnvironment.saveBooleanParams(KXApplication.getInstance(), "needCompleteInfo", true, true);
          Intent localIntent4 = new Intent(this, InfoCompletedActivity.class);
          Bundle localBundle4 = new Bundle();
          localBundle4.putInt("mode", 1);
          localBundle4.putString("from", "LoginActivity");
          localIntent4.putExtras(localBundle4);
          AnimationUtil.startActivityForResult(this, localIntent4, 100, 3);
          return;
        }
        User.getInstance().loadUserData(this);
        MainActivity.mNeedCheckDialogNotice = true;
        CloudAlbumManager.getInstance().loadIgnoreFileList(this);
        IntentUtil.returnToDesktop(this);
        InnerPushManager.getInstance(this).setLoged(true);
      }
    }
  }

  private void register()
  {
    startActivity(new Intent(this, PhoneRegisterActivity.class));
    if (!User.getInstance().GetLookAround())
    {
      UserHabitUtils.getInstance(getApplicationContext()).addUserHabit("<Login_RegisterSMS>");
      return;
    }
    if (this.isfromFindFriend)
    {
      UserHabitUtils.getInstance(getApplicationContext()).addUserHabit("<FindFriend_RegisterSMS>");
      return;
    }
    UserHabitUtils.getInstance(getApplicationContext()).addUserHabit("<JustLool_RegisterSMS>");
  }

  private void registerFromWeb()
  {
    Setting localSetting = Setting.getInstance();
    String str1 = localSetting.getCType();
    if (str1 != null);
    for (String str2 = str1.substring(0, 5); ; str2 = "")
    {
      startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://wap.kaixin001.com/reg/reg_step1.php?id=" + localSetting.getFrom() + "-" + str2)));
      return;
    }
  }

  private void registerIntentReceivers()
  {
    this.receiver = new AuthReceiver();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.tencent.auth.BROWSER");
    registerReceiver(this.receiver, localIntentFilter);
  }

  private void registerIntentReceivers2()
  {
    this.receiver2 = new AuthReceiver2();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.weibo.android.sdk.auth");
    registerReceiver(this.receiver2, localIntentFilter);
  }

  private void registerVerifyReceivers()
  {
    this.mVerifyReceiver = new VerifyReceiver();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.kx.login.verify");
    registerReceiver(this.mVerifyReceiver, localIntentFilter);
  }

  private void sendLoggedInNotification()
  {
    if (!Setting.getInstance().showLoginNotification())
      return;
    NotificationManager localNotificationManager = (NotificationManager)getSystemService("notification");
    Notification localNotification = new Notification();
    PendingIntent localPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, StubActivity.class), 0);
    localNotification.icon = 2130838348;
    String str1 = getResources().getString(2131427329);
    String str2 = NewsModel.getMyHomeModel().getRealname();
    String str3 = getResources().getString(2131427660);
    if ((str2 != null) && (str2.length() > 0))
      str3 = str3 + "(" + str2 + ")";
    localNotification.tickerText = str1;
    localNotification.setLatestEventInfo(this, str1, str3, localPendingIntent);
    localNotification.flags = 32;
    localNotificationManager.notify(KaixinConst.ID_ONLINE_NOTIFICATION, localNotification);
  }

  private void showLoginFailedAlert()
  {
    if (this.loginEngine.getErrMsg().contains(String.valueOf(4001105)))
    {
      Toast.makeText(this, getResources().getString(2131428298), 0).show();
      this.txtPassword.requestFocus();
      return;
    }
    if (this.loginEngine.getErrMsg().contains(String.valueOf(4001104)))
    {
      Toast.makeText(this, getResources().getString(2131428297), 0).show();
      this.txtID.requestFocus();
      return;
    }
    if (this.loginEngine.getErrMsg().contains(String.valueOf(4001106)))
    {
      Toast.makeText(this, getResources().getString(2131428299), 0).show();
      this.txtID.requestFocus();
      return;
    }
    if (this.loginEngine.getErrMsg().contains(String.valueOf(4001107)))
    {
      Toast.makeText(this, getResources().getString(2131428300), 0).show();
      this.txtID.requestFocus();
      return;
    }
    if (this.loginEngine.getErrMsg().contains("wap.kaixin"))
    {
      DialogUtil.showKXAlertDialog(this, 2131428369, 2131427382, 2131427383, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(LoginActivity.this.loginEngine.getErrMsg()));
          LoginActivity.this.startActivity(localIntent);
        }
      }
      , new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
        }
      });
      return;
    }
    Toast.makeText(this, getResources().getString(2131427387), 0).show();
  }

  private void showWarningDialog()
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle(2131427831);
    if (Setting.getInstance().getCType().equals("04703AndroidClient"))
      localBuilder.setIcon(17301543).setMessage(2131427833);
    while (true)
    {
      localBuilder.setCancelable(true).setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          LoginActivity.this.finish();
        }
      });
      localBuilder.setOnKeyListener(new DialogInterface.OnKeyListener()
      {
        public boolean onKey(DialogInterface paramDialogInterface, int paramInt, KeyEvent paramKeyEvent)
        {
          if (paramInt == 84)
          {
            paramKeyEvent.isCanceled();
            return true;
          }
          return false;
        }
      });
      localBuilder.setPositiveButton(2131427834, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          ActiveClientEngine.getInstance().saveActiveFlag(LoginActivity.this, "1", "");
        }
      });
      localBuilder.setNegativeButton(2131427835, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          ActiveClientEngine.getInstance().saveActiveFlag(LoginActivity.this, "0", "");
          LoginActivity.this.finish();
        }
      }).show();
      return;
      localBuilder.setIcon(17301543).setMessage(2131427832);
    }
  }

  private void tencentAuth(String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(this, TAuthView.class);
    localIntent.putExtra("client_id", paramString1);
    localIntent.putExtra("scope", "get_user_info, get_user_profile, add_share, add_topic, list_album, upload_pic, add_album");
    localIntent.putExtra("target", paramString2);
    localIntent.putExtra("callback", "tencentauth://auth.qq.com");
    startActivity(localIntent);
  }

  private void unregisterIntentReceivers()
  {
    if (this.receiver != null)
      unregisterReceiver(this.receiver);
    if (this.receiver2 != null)
      unregisterReceiver(this.receiver2);
  }

  private void unregisterVeiryReceivers()
  {
    if (this.mVerifyReceiver != null)
      unregisterReceiver(this.mVerifyReceiver);
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.callFinishActivity(this, 4);
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (paramMessage == null);
    do
    {
      return true;
      if (paramMessage.what != 2)
        continue;
      cancelNotification();
      return true;
    }
    while (paramMessage.what == 10000);
    return super.handleMessage(paramMessage);
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (100 == paramInt1)
      if (paramInt2 == -1)
        if (User.getInstance().fnum == 0)
        {
          this.mAddFriend = true;
          KXEnvironment.saveBooleanParams(this, "needCompleteInfo", true, false);
          IntentUtil.returnToDesktop(this);
        }
    while (true)
    {
      return;
      finish();
      return;
      if (101 == paramInt1)
      {
        this.mAddFriend = false;
        IntentUtil.returnToDesktop(this);
        return;
      }
      if (104 == paramInt1)
      {
        if (paramInt2 == -1)
        {
          Intent localIntent = new Intent(this, MainActivity.class);
          Bundle localBundle = new Bundle();
          localBundle.putString("fragment", "UploadPhotoFragment");
          localIntent.putExtras(localBundle);
          startActivity(localIntent);
          finish();
          return;
        }
        finish();
        return;
      }
      if (102 != paramInt1)
        break;
      if (paramInt2 != -1)
        continue;
      doLogin(paramIntent.getStringExtra("rcode"), paramIntent.getStringExtra("code"), "login");
      return;
    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362914:
      Activity localActivity = getParent();
      if ((localActivity != null) && ((localActivity instanceof MainActivity)))
      {
        if (super.isMenuListVisibleInMain())
        {
          super.showSubActivityInMain();
          return;
        }
        super.showMenuListInMain();
        return;
      }
      finish();
      return;
    case 2131362988:
      MainActivity.isRefresh = true;
      doLogin(null, null, "login");
      return;
    case 2131362987:
    }
    forgetPwd();
    UserHabitUtils.getInstance(getApplicationContext()).addUserHabit("Login_ForgetPwd");
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(2130903206);
    KXLog.d("LoginActivity", "LoginActivity onCreate");
    InnerPushManager.getInstance(this).setLoginTime();
    Intent localIntent = getIntent();
    Bundle localBundle = localIntent.getExtras();
    setTitleBar();
    initBottomBtnsLayout();
    registerVerifyReceivers();
    this.bIsFromShareText = localIntent.getBooleanExtra("from_share_text", false);
    boolean bool1 = false;
    if (localBundle != null)
    {
      if ((localBundle.containsKey("from")) && (localBundle.getString("from").equals("AccountSwitchActivity")))
        isSwitchAccountLogin = true;
      String str = localBundle.getString("UploadPhotoFilePath");
      if (str != null)
        this.mUploadPhotoFilePath = str;
      this.mUploadText = localBundle.getString("mUploadText");
      bool1 = localBundle.getBoolean("needactive", false);
      this.mFuid = localBundle.getString("fuid");
      this.mFname = localBundle.getString("fname");
      this.mAlbumTitle = localBundle.getString("title");
      this.mAlbumId = localBundle.getString("albumId");
      this.isRegist = localBundle.getInt("regist");
      this.from = localBundle.getString("from");
      localBundle.getBoolean("fromlook", false);
      if (localBundle.getBoolean("InnerPushManager", false))
        InnerPushManager.getInstance(this).addCount();
    }
    getWindow().setSoftInputMode(3);
    clearAllModels();
    ((ImageView)findViewById(2131362989)).setVisibility(4);
    this.btn = ((Button)findViewById(2131362988));
    this.txtID = ((AutoCompleteTextView)findViewById(2131362984));
    ArrayList localArrayList = getAllAccounts();
    this.txtID.setAdapter(new ArrayAdapter(this, 17367043, localArrayList));
    this.txtID.addTextChangedListener(this);
    this.mIdDeleteBtn = findViewById(2131362985);
    this.mIdDeleteBtn.setVisibility(8);
    this.mIdDeleteBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        LoginActivity.this.txtID.setText("");
        LoginActivity.this.txtID.requestFocus();
      }
    });
    this.txtPassword = ((EditText)findViewById(2131362986));
    this.txtPassword.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
        if ((paramInt3 > 0) && (LoginActivity.this.txtID.getText() != null) && (!LoginActivity.this.txtID.getText().toString().equals("")))
        {
          LoginActivity.this.btn.setClickable(true);
          LoginActivity.this.btn.setTextColor(-1);
          return;
        }
        LoginActivity.this.btn.setClickable(false);
        LoginActivity.this.btn.setTextColor(2147483647);
      }
    });
    this.btn.setOnClickListener(this);
    ((TextView)findViewById(2131362987)).setOnClickListener(this);
    this.mBottomLayout = findViewById(2131362990);
    this.mScrollView = ((KXScrollView)findViewById(2131362980));
    int i = (int)(112.0F * getResources().getDisplayMetrics().density);
    this.mScrollView.setSizeChangeListener(new KXScrollView.SizeChangeListener(i)
    {
      public void onSizeChanged(int paramInt)
      {
        if (paramInt == 2)
        {
          LoginActivity.this.mBottomLayout.setVisibility(4);
          LoginActivity.this.mScrollView.scrollTo(0, this.val$offset);
          return;
        }
        LoginActivity.this.mBottomLayout.setVisibility(0);
        LoginActivity.this.mScrollView.scrollTo(0, 0);
      }
    });
    if ((bool1) && (super.checkNetworkAndHint(true)))
    {
      ActiveClientTask localActiveClientTask = new ActiveClientTask(null);
      localActiveClientTask.execute(new Void[] { null });
      DialogUtil.dismissDialog(this.progressDialog);
      this.progressDialog = ProgressDialog.show(this, "", getResources().getText(2131427361), true, true, new DialogInterface.OnCancelListener(localActiveClientTask)
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          DialogUtil.dismissDialog(LoginActivity.this.progressDialog);
          this.val$activeTask.cancel(true);
        }
      });
    }
    boolean bool2 = Setting.getInstance().needDisclaimer();
    int j = 0;
    if (bool2)
    {
      if (ActiveClientEngine.getInstance().loadActiveFlag(this))
        j = 0;
    }
    else
    {
      if (j != 0)
        showWarningDialog();
      localIntent.getIntExtra("action", -1);
      Setting.getInstance().setNeedRefreshFlag(true);
      if ("android.intent.action.VIEW".equals(localIntent.getAction()))
      {
        Uri localUri = localIntent.getData();
        if ((localUri != null) && ("qq_login".equals(StringUtil.getUrlHost(localUri.toString()))))
          doLogin(false, localUri.getQueryParameter("access_token"), localUri.getQueryParameter("expires_in"), localUri.getQueryParameter("openid"), null, null, "QQ", "login");
      }
      this.isfromFindFriend = localIntent.getBooleanExtra("tag_findfriend", false);
      if (!this.isfromFindFriend)
        break label742;
      if ((User.getInstance().GetLookAround()) && (this.isRegist == 0))
        UserHabitUtils.getInstance(getApplicationContext()).addUserHabit("FindFriend_Login");
    }
    label742: 
    do
    {
      return;
      j = 1;
      break;
    }
    while ((!User.getInstance().GetLookAround()) || (this.isRegist != 0));
    UserHabitUtils.getInstance(getApplicationContext()).addUserHabit("JustLook_Login");
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    if (paramInt == 1)
      return DialogUtil.createAlertDialog(this, 2131427842, null);
    return super.onCreateDialog(paramInt);
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    super.onCreateOptionsMenu(paramMenu);
    paramMenu.add(0, 404, 0, 2131427692).setIcon(2130838603);
    paramMenu.add(0, 402, 0, 2131427683).setIcon(17301577);
    paramMenu.add(0, 401, 0, 2131427693).setIcon(2130838608);
    paramMenu.add(0, 403, 0, 2131427696).setIcon(2130838605);
    return true;
  }

  public void onDestroy()
  {
    DialogUtil.dismissDialog(this.progressDialog);
    if ((this.bIsFromShareText) && (TextUtils.isEmpty(User.getInstance().getOauthToken())))
      setResult(0);
    UploadTaskListEngine.getInstance().unRegister(this.mHandler);
    unregisterIntentReceivers();
    unregisterVeiryReceivers();
    KXLog.d("LoginActivity", "onDestroy");
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
      finish();
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 401:
      register();
      return true;
    case 402:
      startActivity(new Intent(this, SettingActivity.class));
      return true;
    case 403:
      MessageCenterModel.getInstance().clear();
      cancelNotification();
      finish();
      return true;
    case 404:
    }
    clearAccount();
    return true;
  }

  protected void onPause()
  {
    super.onPause();
    InnerPushManager.getInstance(this).setLoginTime();
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
    paramMenu.clear();
    if (("".equals(User.getInstance().getAccount())) && ("".equals(String.valueOf(this.txtID.getText()))) && ("".equals(String.valueOf(this.txtPassword.getText()))))
    {
      paramMenu.add(0, 404, 0, 2131427692).setIcon(2130838603).setEnabled(false);
      paramMenu.add(0, 402, 0, 2131427683).setIcon(17301577);
      paramMenu.add(0, 401, 0, 2131427693).setIcon(2130838608);
      if (!User.getInstance().GetLookAround())
        break label207;
    }
    label207: for (int i = 2131427597; ; i = 2131427696)
    {
      paramMenu.add(0, 403, 0, i).setIcon(2130838605);
      return true;
      paramMenu.add(0, 404, 0, 2131427692).setIcon(2130838603).setEnabled(true);
      break;
    }
  }

  protected void onResume()
  {
    super.onResume();
    InnerPushManager.getInstance(this).setLoginTime();
    if ((this.from == null) && (!TextUtils.isEmpty(User.getInstance().getOauthToken())) && (!this.mAddFriend) && (!User.getInstance().GetLookAround()))
    {
      sendLoggedInNotification();
      IntentUtil.returnToDesktop(this);
    }
    if ((this.from != null) && (this.from.equals("AccountSwitchActivity")))
      this.tvCenterText.setText(getResources().getString(2131428550));
    if (this.isRegist == 1)
    {
      this.mHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          LoginActivity.this.register();
        }
      }
      , 500L);
      this.isRegist = 0;
    }
  }

  public boolean onSearchRequested()
  {
    return false;
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    this.txtPassword.setText(null);
    String str = this.txtID.getText().toString().trim();
    if ((TextUtils.isEmpty(str)) && (this.mIdDeleteBtn.getVisibility() != 8))
      this.mIdDeleteBtn.setVisibility(8);
    do
      return;
    while ((TextUtils.isEmpty(str)) || (this.mIdDeleteBtn.getVisibility() == 0));
    this.mIdDeleteBtn.setVisibility(0);
  }

  public void sendLoginBroadcast()
  {
    Intent localIntent = new Intent("com.kaixin001.INITIAL_USER");
    localIntent.putExtra("id", User.getInstance().getAccount());
    localIntent.putExtra("pwd", "");
    localIntent.putExtra("uid", User.getInstance().getUID());
    localIntent.putExtra("verify", "");
    localIntent.putExtra("wapverify", "");
    localIntent.putExtra("from", "kaixin001Client");
    sendBroadcast(localIntent);
  }

  protected void setTitleBar()
  {
    ((RelativeLayout)findViewById(2131361840)).setVisibility(0);
    findViewById(2131362914).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        LoginActivity.this.finish();
      }
    });
    this.tvCenterText = ((TextView)findViewById(2131362920));
    this.tvCenterText.setText(getResources().getString(2131427338));
    this.tvCenterText.setVisibility(0);
    this.tvRightText = ((TextView)findViewById(2131362930));
    this.tvRightText.setText(getResources().getString(2131427339));
    this.tvRightText.setVisibility(0);
    this.tvRightText.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        LoginActivity.this.register();
        UserHabitUtils.getInstance(LoginActivity.this.getApplicationContext()).addUserHabit("Login_Register");
      }
    });
    findViewById(2131362928).setVisibility(8);
  }

  private class ActiveClientTask extends AsyncTask<Void, Void, Boolean>
  {
    private ActiveClientTask()
    {
    }

    protected Boolean doInBackground(Void[] paramArrayOfVoid)
    {
      String str = ((TelephonyManager)LoginActivity.this.getSystemService("phone")).getDeviceId();
      try
      {
        Boolean localBoolean = Boolean.valueOf(ActiveClientEngine.getInstance().activeClient(LoginActivity.this, str, Setting.getInstance().getFrom(), Setting.getInstance().getCType()));
        return localBoolean;
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        localSecurityErrorException.printStackTrace();
        return Boolean.valueOf(false);
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }

    protected void onPostExecute(Boolean paramBoolean)
    {
      DialogUtil.dismissDialog(LoginActivity.this.progressDialog);
      if (paramBoolean.booleanValue())
        ActiveClientEngine.getInstance().saveActiveFlag(LoginActivity.this, "1", "");
    }
  }

  public class AuthReceiver extends BroadcastReceiver
  {
    public AuthReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      Bundle localBundle = paramIntent.getExtras();
      String str1 = localBundle.getString("access_token");
      String str2 = localBundle.getString("expires_in");
      if (str1 != null)
        TencentOpenAPI.openid(str1, new Callback(str1, str2)
        {
          public void onCancel(int paramInt)
          {
          }

          public void onFail(int paramInt, String paramString)
          {
          }

          public void onSuccess(Object paramObject)
          {
            String str = ((OpenId)paramObject).getOpenId();
            LoginActivity.this.shareManager.saveQQToken(str, this.val$access_token);
            LoginActivity.this.runOnUiThread(new Runnable(this.val$access_token, this.val$expires_in, str)
            {
              public void run()
              {
                LoginActivity.this.doLogin(false, this.val$access_token, this.val$expires_in, this.val$openId, null, null, "QQ", "login");
              }
            });
          }
        });
    }
  }

  public class AuthReceiver2 extends BroadcastReceiver
  {
    public AuthReceiver2()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      Bundle localBundle = paramIntent.getExtras();
      String str = localBundle.getString("access_token");
      LoginActivity.this.shareManager.saveWeiboToken(str);
      LoginActivity.this.doLogin(false, localBundle.getString("access_token"), localBundle.getString("expires_in"), localBundle.getString("uid"), null, null, "WEIBO", "login");
    }
  }

  private class LoginTask extends AsyncTask<String, Void, Integer>
  {
    private LoginTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      if (paramArrayOfString == null)
        return null;
      while (true)
      {
        try
        {
          int i = LoginActivity.this.loginEngine.doLogin(LoginActivity.this, LoginActivity.this.getApplicationContext(), paramArrayOfString[0], paramArrayOfString[1], paramArrayOfString[2], paramArrayOfString[3], paramArrayOfString[4], paramArrayOfString[5], paramArrayOfString[6]);
          if (TextUtils.isEmpty(User.getInstance().getUID()))
            continue;
          j = CloudAlbumEngine.getInstance().checkWhiteUser(LoginActivity.this.getApplicationContext());
          if ((!"99688312".equals(User.getInstance().getUID())) && (!"101940966".equals(User.getInstance().getUID())) && (!"150784446".equals(User.getInstance().getUID())) && (!"150784460".equals(User.getInstance().getUID())))
          {
            CloudAlbumManager.getInstance().setWhiteUser(LoginActivity.this.getApplicationContext(), User.getInstance().getUID(), j);
            Integer localInteger = Integer.valueOf(i);
            return localInteger;
          }
        }
        catch (SecurityErrorException localSecurityErrorException)
        {
          return null;
        }
        int j = 1;
      }
    }

    protected void onPostExecute(Integer paramInteger)
    {
      if (paramInteger == null)
        LoginActivity.this.finish();
      String str1;
      String str2;
      Object localObject;
      while (true)
      {
        return;
        int i = paramInteger.intValue();
        try
        {
          DialogUtil.dismissDialog(LoginActivity.this.progressDialog);
          if (LoginActivity.this.bIsCanceled)
            continue;
          if (i == 0)
          {
            new GetLoginUserRealNameTask().execute(new Void[0]);
            LoginActivity.this.loginSuccess();
            return;
          }
        }
        catch (Exception localException1)
        {
          KXLog.e("LoginActivity", "onPostExecute", localException1);
          return;
        }
        if (i != -1004)
          break;
        str1 = LoginEngine.getInstance().getErrMsg();
        boolean bool = TextUtils.isEmpty(str1);
        if (bool)
          continue;
        str2 = "";
        localObject = "";
      }
      try
      {
        JSONObject localJSONObject = new JSONObject(str1);
        str2 = localJSONObject.optString("rcode");
        String str3 = localJSONObject.optString("captcha_url");
        localObject = str3;
        try
        {
          label142: Intent localIntent = new Intent(LoginActivity.this, VerifyActivity.class);
          Bundle localBundle = new Bundle();
          localBundle.putString("fragment", "VerifyFragment");
          localIntent.putExtras(localBundle);
          localIntent.putExtra("rcode", str2);
          localIntent.putExtra("imageURL", (String)localObject);
          localIntent.putExtra("callfrom", "activity");
          localIntent.putExtra("callcode", 1);
          localIntent.putExtra("boardcast", "com.kx.login.verify");
          localIntent.putExtra("mode", 1);
          LoginActivity.this.startActivityForResult(localIntent, 102);
          return;
        }
        catch (Exception localException3)
        {
          localException3.printStackTrace();
          return;
        }
        if (LoginActivity.this.bIsFromShareText)
          LoginActivity.this.setResult(0);
        LoginActivity.this.showLoginFailedAlert();
        return;
      }
      catch (Exception localException2)
      {
        break label142;
      }
    }
  }

  public class VerifyReceiver extends BroadcastReceiver
  {
    public VerifyReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      Bundle localBundle = paramIntent.getExtras();
      if (localBundle != null)
      {
        int i = localBundle.getInt("callcode");
        LoginActivity.this.onActivityResult(i, -1, paramIntent);
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.LoginActivity
 * JD-Core Version:    0.6.0
 */