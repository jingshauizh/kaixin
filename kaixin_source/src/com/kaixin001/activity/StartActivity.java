package com.kaixin001.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.db.ConfigDBAdapter;
import com.kaixin001.engine.GetStartAdvertEngine;
import com.kaixin001.model.FaceModel;
import com.kaixin001.model.GiftListModel;
import com.kaixin001.model.Setting;
import com.kaixin001.model.StartAdvert;
import com.kaixin001.model.User;
import com.kaixin001.network.Protocol;
import com.kaixin001.task.GetFriendsListTask;
import com.kaixin001.task.GetLoginUserRealNameTask;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CloseUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownLoaderManager;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.umeng.analytics.MobclickAgent;
import java.io.File;
import java.util.ArrayList;

public class StartActivity extends Activity
{
  public static final int EDIT_PHOTO = 103;
  private static final int REQUEST_SHARE_TEXT = 1001;
  private final int PROGRESS_ADV = 1234;
  private final int RESULT_NO_USER = 0;
  private final int RESULT_TO_DO = 2;
  private final int RESULT_TO_MAIN = 1;
  private boolean isClick;
  private ImageView ivAdvert;
  private String mUploadPhotoFilePath = "";
  private StartTask startTask = null;

  private void clearCacheWhileUpdate(Context paramContext)
  {
    ConfigDBAdapter localConfigDBAdapter = new ConfigDBAdapter(paramContext);
    try
    {
      String str1 = localConfigDBAdapter.getConfig("client_uid", "kxversion:");
      String str2 = Protocol.getShortVersion();
      if ((TextUtils.isEmpty(str1)) || (str2.compareToIgnoreCase(str1) > 0))
      {
        if (Environment.getExternalStorageState().equals("mounted"))
          FileUtil.deleteDirectory(new File(FileUtil.getKXCacheDir(paramContext)));
        localConfigDBAdapter.addConfig("client_uid", "kxversion:", str2, "");
      }
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("StartActivity", "clearCacheWhileUpdate", localException);
      return;
    }
    finally
    {
      localConfigDBAdapter.close();
    }
    throw localObject;
  }

  private void initView()
  {
    this.ivAdvert = ((ImageView)findViewById(2131363651));
    this.ivAdvert.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        StartActivity.this.isClick = true;
        Intent localIntent = new Intent(StartActivity.this, MainActivity.class);
        localIntent.setFlags(67108864);
        localIntent.putExtra("returnDesktop", "1");
        localIntent.putExtra("actionlink", StartAdvert.getInstance().getActionlink());
        StartActivity.this.startActivity(localIntent);
        StartActivity.this.finish();
      }
    });
    ImageView localImageView = (ImageView)findViewById(2131363650);
    TextView localTextView = (TextView)findViewById(2131363649);
    if (Setting.getInstance().getCType().equals("24403AndroidClient"))
    {
      localImageView.setImageResource(2130839003);
      localImageView.setVisibility(0);
      localTextView.setVisibility(8);
      return;
    }
    if (Setting.getInstance().getCType().equals("15803AndroidClient"))
    {
      localImageView.setImageResource(2130838567);
      localImageView.setVisibility(0);
      localTextView.setText("360独家首发");
      localTextView.setVisibility(0);
      return;
    }
    if (Setting.getInstance().getCType().equals("04103AndroidClient"))
    {
      localImageView.setVisibility(8);
      localTextView.setText("天翼首发");
      localTextView.setVisibility(0);
      return;
    }
    if (Setting.getInstance().getCType().equals("14003AndroidClient"))
    {
      localImageView.setImageResource(2130838569);
      localImageView.setVisibility(0);
      localTextView.setText(2131428532);
      localTextView.setVisibility(0);
      return;
    }
    if (Setting.getInstance().getCType().equals("13803AndroidClient"))
    {
      localImageView.setImageResource(2130837549);
      localImageView.setVisibility(0);
      localTextView.setVisibility(8);
      KXEnvironment.saveBooleanParams(this, "isHasRecommendApps", false, false);
      return;
    }
    if (Setting.getInstance().getCType().equals("10703AndroidClient"))
    {
      localImageView.setImageResource(2130839376);
      localImageView.setVisibility(0);
      localTextView.setVisibility(8);
      return;
    }
    if (Setting.getInstance().getCType().equals("11703AndroidClient"))
    {
      localImageView.setImageResource(2130837559);
      localImageView.setVisibility(0);
      localTextView.setVisibility(8);
      return;
    }
    if (Setting.getInstance().getCType().equals("30903AndroidClient"))
    {
      localImageView.setImageResource(2130837559);
      localImageView.setVisibility(0);
      localTextView.setVisibility(8);
      return;
    }
    if (Setting.getInstance().getCType().equals("33703AndroidClient"))
    {
      localImageView.setImageResource(2130837559);
      localImageView.setVisibility(0);
      localTextView.setVisibility(8);
      return;
    }
    if (Setting.getInstance().getCType().equals("21403AndroidClient"))
    {
      localImageView.setImageResource(2130838505);
      localImageView.setVisibility(0);
      localTextView.setVisibility(8);
      return;
    }
    if (Setting.getInstance().getCType().equals("23803AndroidClient"))
    {
      localImageView.setImageResource(2130839383);
      localImageView.setVisibility(0);
      localTextView.setVisibility(8);
      return;
    }
    if (Setting.getInstance().getCType().equals("37603AndroidClient"))
    {
      localImageView.setImageResource(2130838981);
      localImageView.setVisibility(0);
      localTextView.setVisibility(8);
      return;
    }
    if (Setting.getInstance().getCType().equals("21203AndroidClient"))
    {
      localImageView.setVisibility(8);
      localTextView.setVisibility(8);
      KXEnvironment.saveBooleanParams(this, "isHasRecommendApps", false, false);
      return;
    }
    if (Setting.getInstance().getCType().equals("32103AndroidClient"))
    {
      localImageView.setImageResource(2130839002);
      localImageView.setVisibility(0);
      localTextView.setVisibility(8);
      KXEnvironment.saveBooleanParams(this, "isHasRecommendApps", false, false);
      return;
    }
    localImageView.setVisibility(8);
    localTextView.setVisibility(8);
  }

  // ERROR //
  private void sendLoggedInNotification()
  {
    // Byte code:
    //   0: invokestatic 170	com/kaixin001/model/Setting:getInstance	()Lcom/kaixin001/model/Setting;
    //   3: invokevirtual 242	com/kaixin001/model/Setting:showLoginNotification	()Z
    //   6: ifne +4 -> 10
    //   9: return
    //   10: aload_0
    //   11: ldc 244
    //   13: invokevirtual 248	com/kaixin001/activity/StartActivity:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   16: checkcast 250	android/app/NotificationManager
    //   19: astore_2
    //   20: new 252	android/app/Notification
    //   23: dup
    //   24: invokespecial 253	android/app/Notification:<init>	()V
    //   27: astore_3
    //   28: aload_0
    //   29: iconst_0
    //   30: new 255	android/content/Intent
    //   33: dup
    //   34: aload_0
    //   35: ldc_w 257
    //   38: invokespecial 260	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
    //   41: iconst_0
    //   42: invokestatic 266	android/app/PendingIntent:getActivity	(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;
    //   45: astore 4
    //   47: ldc_w 268
    //   50: astore 5
    //   52: aconst_null
    //   53: astore 6
    //   55: ldc_w 270
    //   58: astore 7
    //   60: aload_3
    //   61: ldc_w 271
    //   64: putfield 274	android/app/Notification:icon	I
    //   67: aload_0
    //   68: invokevirtual 278	com/kaixin001/activity/StartActivity:getResources	()Landroid/content/res/Resources;
    //   71: ldc_w 279
    //   74: invokevirtual 285	android/content/res/Resources:getString	(I)Ljava/lang/String;
    //   77: astore 5
    //   79: invokestatic 291	com/kaixin001/model/NewsModel:getMyHomeModel	()Lcom/kaixin001/model/NewsModel;
    //   82: invokevirtual 294	com/kaixin001/model/NewsModel:getRealname	()Ljava/lang/String;
    //   85: astore 6
    //   87: aload_0
    //   88: invokevirtual 278	com/kaixin001/activity/StartActivity:getResources	()Landroid/content/res/Resources;
    //   91: ldc_w 295
    //   94: invokevirtual 285	android/content/res/Resources:getString	(I)Ljava/lang/String;
    //   97: astore 10
    //   99: aload 10
    //   101: astore 7
    //   103: aload 6
    //   105: ifnull +93 -> 198
    //   108: aload 6
    //   110: invokevirtual 299	java/lang/String:length	()I
    //   113: ifle +85 -> 198
    //   116: new 301	java/lang/StringBuilder
    //   119: dup
    //   120: aload 7
    //   122: invokestatic 305	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   125: invokespecial 306	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   128: ldc_w 308
    //   131: invokevirtual 312	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: aload 6
    //   136: invokevirtual 312	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   139: ldc_w 314
    //   142: invokevirtual 312	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: invokevirtual 317	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   148: astore 7
    //   150: aload_3
    //   151: aload 5
    //   153: putfield 321	android/app/Notification:tickerText	Ljava/lang/CharSequence;
    //   156: aload_3
    //   157: aload_0
    //   158: aload 5
    //   160: aload 7
    //   162: aload 4
    //   164: invokevirtual 325	android/app/Notification:setLatestEventInfo	(Landroid/content/Context;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Landroid/app/PendingIntent;)V
    //   167: aload_3
    //   168: bipush 32
    //   170: putfield 328	android/app/Notification:flags	I
    //   173: aload_2
    //   174: getstatic 333	com/kaixin001/model/KaixinConst:ID_ONLINE_NOTIFICATION	I
    //   177: aload_3
    //   178: invokevirtual 337	android/app/NotificationManager:notify	(ILandroid/app/Notification;)V
    //   181: return
    //   182: astore_1
    //   183: aload_1
    //   184: invokevirtual 340	java/lang/Exception:printStackTrace	()V
    //   187: return
    //   188: astore 8
    //   190: aload 8
    //   192: invokevirtual 340	java/lang/Exception:printStackTrace	()V
    //   195: goto -92 -> 103
    //   198: new 342	com/kaixin001/task/GetLoginUserRealNameTask
    //   201: dup
    //   202: invokespecial 343	com/kaixin001/task/GetLoginUserRealNameTask:<init>	()V
    //   205: iconst_0
    //   206: anewarray 345	java/lang/Void
    //   209: invokevirtual 349	com/kaixin001/task/GetLoginUserRealNameTask:execute	([Ljava/lang/Object;)Landroid/os/AsyncTask;
    //   212: pop
    //   213: goto -63 -> 150
    //
    // Exception table:
    //   from	to	target	type
    //   10	47	182	java/lang/Exception
    //   108	150	182	java/lang/Exception
    //   150	181	182	java/lang/Exception
    //   190	195	182	java/lang/Exception
    //   198	213	182	java/lang/Exception
    //   60	99	188	java/lang/Exception
  }

  private boolean shortCutAdded()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(Uri.parse("content://com.android.launcher.settings/favorites?notify=true"));
    localArrayList.add(Uri.parse("content://com.android.launcher2.settings/favorites?notify=true"));
    localArrayList.add(Uri.parse("content://com.htc.launcher.settings/favorites?notify=true"));
    localArrayList.add(Uri.parse("content://com.sec.android.app.twlauncher.settings/favorites?notify=true"));
    ContentResolver localContentResolver = getContentResolver();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = getString(2131427328);
    int i = 0;
    Cursor localCursor = null;
    while (true)
    {
      if ((localCursor != null) || (i >= localArrayList.size()))
      {
        if ((localCursor == null) || (localCursor.getCount() <= 0))
          break;
        return true;
      }
      localCursor = localContentResolver.query((Uri)localArrayList.get(i), null, "title=?", arrayOfString, null);
      i++;
    }
    return !TextUtils.isEmpty(ConfigDBAdapter.getConfig(this, "client_uid", "shortcut added:android-3.9.9"));
  }

  private void start()
  {
    Intent localIntent = getIntent();
    Bundle localBundle = localIntent.getExtras();
    if (localBundle != null)
      Setting.getInstance().calledByUnicom = localBundle.getBoolean("calledByUnicom", false);
    if ((localBundle != null) && ("android.intent.action.SEND".equals(localIntent.getAction())))
      if (localIntent.getType().contains("image"))
      {
        Uri localUri = (Uri)localBundle.getParcelable("android.intent.extra.STREAM");
        if (localUri == null)
        {
          Toast.makeText(getApplicationContext(), 2131427333, 0).show();
          finish();
          return;
        }
        uploadPhoto(localUri);
      }
    while (true)
    {
      Setting.getInstance().setNeedRefreshFlag(true);
      if (shortCutAdded())
        break;
      addShortCut();
      return;
      if ((localIntent.getType().contains("text")) || (localIntent.getType().contains("plain")))
      {
        if (TextUtils.isEmpty(User.getInstance().getOauthToken()))
        {
          this.startTask = new StartTask(null);
          this.startTask.execute(new Void[0]);
          continue;
        }
        startShareText();
        continue;
      }
      Toast.makeText(getApplicationContext(), 2131427423, 0).show();
      finish();
      return;
      this.startTask = new StartTask(null);
      this.startTask.execute(new Void[0]);
    }
  }

  private void startShareText()
  {
    Intent localIntent = new Intent(this, MainActivity.class);
    Bundle localBundle = new Bundle();
    localBundle.putString("fragment", "WriteWeiboFragment");
    localIntent.putExtras(localBundle);
    localIntent.setType(getIntent().getType());
    localIntent.putExtra("android.intent.extra.TEXT", getIntent().getStringExtra("android.intent.extra.TEXT"));
    startActivity(localIntent);
    finish();
  }

  private void startUploadPhoto()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("filePathName", this.mUploadPhotoFilePath);
    localBundle.putString("fileFrom", "gallery");
    Intent localIntent = new Intent(this, MainActivity.class);
    localBundle.putString("fragment", "UploadPhotoFragment");
    localIntent.putExtras(localBundle);
    AnimationUtil.startActivity(this, localIntent, 1);
    finish();
  }

  private boolean uploadPhoto(Uri paramUri)
  {
    if (paramUri == null)
      return false;
    Cursor localCursor = null;
    while (true)
    {
      try
      {
        if (!paramUri.toString().contains("content"))
          continue;
        localObject2 = "";
      }
      catch (Exception localException1)
      {
        Object localObject2;
        int i;
        String str;
        this.mUploadPhotoFilePath = paramUri.getPath();
        localCursor = null;
        continue;
      }
      finally
      {
        CloseUtil.close(localCursor);
      }
      this.startTask = new StartTask(null);
      this.startTask.execute(new Void[0]);
    }
  }

  public void addShortCut()
  {
    Intent localIntent1 = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
    Intent.ShortcutIconResource localShortcutIconResource = Intent.ShortcutIconResource.fromContext(this, 2130838348);
    Intent localIntent2 = new Intent();
    localIntent2.setAction("android.intent.action.MAIN");
    localIntent2.addCategory("android.intent.category.LAUNCHER");
    String str = getPackageName();
    localIntent2.setComponent(new ComponentName(str, str + ".StartActivity"));
    localIntent1.putExtra("android.intent.extra.shortcut.NAME", getString(2131427328));
    localIntent1.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", localShortcutIconResource);
    localIntent1.putExtra("android.intent.extra.shortcut.INTENT", localIntent2);
    localIntent1.putExtra("duplicate", false);
    sendBroadcast(localIntent1);
    ConfigDBAdapter.setConfig(this, "client_uid", "shortcut added:android-3.9.9", "1", "");
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (1001 == paramInt1)
    {
      if (-1 == paramInt2)
      {
        startShareText();
        return;
      }
      finish();
      return;
    }
    if (103 == paramInt1)
    {
      if (-1 == paramInt2)
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
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(2130903339);
    initView();
    start();
  }

  protected void onDestroy()
  {
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
      return true;
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

  private class StartTask extends AsyncTask<Void, Integer, Integer>
  {
    private StartTask()
    {
    }

    protected Integer doInBackground(Void[] paramArrayOfVoid)
    {
      StartActivity.this.clearCacheWhileUpdate(StartActivity.this.getApplicationContext());
      try
      {
        Thread.sleep(1000L);
        if (!User.getInstance().loadUserData(StartActivity.this.getApplicationContext()))
          return Integer.valueOf(0);
      }
      catch (InterruptedException localInterruptedException1)
      {
        while (true)
          localInterruptedException1.printStackTrace();
        if ((!TextUtils.isEmpty(User.getInstance().getOauthToken())) && (!User.getInstance().GetLookAround()))
        {
          GetStartAdvertEngine.getInstance(StartActivity.this).getStartAdvert();
          Integer[] arrayOfInteger = new Integer[1];
          arrayOfInteger[0] = Integer.valueOf(1234);
          publishProgress(arrayOfInteger);
        }
        long l = StartAdvert.getInstance().getDuration();
        try
        {
          Thread.sleep(l);
          GiftListModel.getInstance().loadGiftData(StartActivity.this.getApplicationContext());
          ImageCache.getInstance().setContext(StartActivity.this.getApplicationContext());
          if (TextUtils.isEmpty(StartActivity.this.mUploadPhotoFilePath))
            FaceModel.getInstance();
          if ((!TextUtils.isEmpty(User.getInstance().getOauthToken())) && (!User.getInstance().GetLookAround()))
            return Integer.valueOf(2);
        }
        catch (InterruptedException localInterruptedException2)
        {
          while (true)
            localInterruptedException2.printStackTrace();
        }
      }
      return Integer.valueOf(1);
    }

    protected void onPostExecute(Integer paramInteger)
    {
      while (true)
      {
        try
        {
          if (paramInteger.intValue() != 1)
            continue;
          MainActivity.isRefresh = true;
          User.getInstance().setLookAround(Boolean.valueOf(true));
          IntentUtil.returnToDesktop(StartActivity.this);
          StartActivity.this.finish();
          return;
          if (paramInteger.intValue() != 2)
            break;
          if (!TextUtils.isEmpty(StartActivity.this.getIntent().getStringExtra("android.intent.extra.TEXT")))
          {
            StartActivity.this.startShareText();
            GetFriendsListTask localGetFriendsListTask = new GetFriendsListTask(StartActivity.this);
            Integer[] arrayOfInteger = new Integer[1];
            arrayOfInteger[0] = Integer.valueOf(1);
            localGetFriendsListTask.execute(arrayOfInteger);
            return;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return;
        }
        if (TextUtils.isEmpty(StartActivity.this.mUploadPhotoFilePath))
        {
          new GetLoginUserRealNameTask().execute(new Void[0]);
          StartActivity.this.sendLoggedInNotification();
          if (StartActivity.this.isClick)
            continue;
          IntentUtil.returnToDesktop(StartActivity.this);
          continue;
        }
        StartActivity.this.startUploadPhoto();
      }
      DialogUtil.showMsgDlgStdConfirm(StartActivity.this, 2131427329, 2131427332, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          StartActivity.this.finish();
        }
      });
    }

    protected void onProgressUpdate(Integer[] paramArrayOfInteger)
    {
      super.onProgressUpdate(paramArrayOfInteger);
      if (paramArrayOfInteger[0].intValue() == 1234)
      {
        String str = StartAdvert.getInstance().getImage();
        if (!TextUtils.isEmpty(str))
        {
          StartActivity.this.ivAdvert.setVisibility(0);
          ImageDownLoaderManager.getInstance().displayPicture(this, StartActivity.this.ivAdvert, str, 0, null);
        }
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.StartActivity
 * JD-Core Version:    0.6.0
 */