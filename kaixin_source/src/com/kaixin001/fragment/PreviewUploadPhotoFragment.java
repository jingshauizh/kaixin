package com.kaixin001.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.model.WriteWeiboModel;
import com.kaixin001.network.HttpConnection;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.FileUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageDownLoaderManager;
import com.kaixin001.util.ImageDownloadCallback;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class PreviewUploadPhotoFragment extends BaseFragment
  implements View.OnClickListener
{
  public static final String ACTION_VIEW_SINGLE = "com.kaixin001.VIEW_SINGLE_PHOTO";
  public static final String ACTION_VIEW_UPLOADING = "com.kaixin001.VIEW_UPLOADING_PHOTO";
  public static final int LOAD_PHOTO_SUCCESS = 1;
  private static final int MSG_HIDE_BUTTONS = 113;
  public static final int PREVIEW_RECORD_PHOTO = 0;
  public static final int RESULT_EDIT_PHOTO = 1001;
  private static final String TAG = "PreviewUploadPhotoActivity";
  public static final int USER_ACTION_BACK = 102;
  public static final int USER_ACTION_EDIT_PHOTO = 105;
  public static final String USER_ACTION_KEY = "userAction";
  public static final int USER_ACTION_REMOVE = 101;
  private static final String mUploadPhotoFrom = "preview_photo";
  private String action;
  TranslateAnimation mBottomHideAnim = null;
  TranslateAnimation mBottomShowAnim = null;
  private long mButtonShowDuration = 3000L;
  private ImageView mDeleteBtn;
  private String mFilePath;
  private Timer mHideButtonTimer = new Timer();
  private HideButtonsTask mHideButtonsTask = new HideButtonsTask(null);
  ImageView mImageView;
  private ImageView mIvEditPhoto;
  private ImageView mIvSaves;
  private ImageView mLeftBtn;
  private LoadPhotoTask mLoadPhotoTask;
  private Bitmap mPhotoBitmap;
  private String mPhotoUrl;
  private View mProgressBar;
  TranslateAnimation mTitleHideAnim = null;
  TranslateAnimation mTitleShowAnim = null;

  private void constructAnimations()
  {
    this.mTitleShowAnim = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, -1.0F, 1, 0.0F);
    this.mTitleShowAnim.setDuration(600L);
    this.mTitleHideAnim = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 0.0F, 1, -1.0F);
    this.mTitleHideAnim.setDuration(600L);
    this.mBottomShowAnim = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 0.71F, 1, 0.0F);
    this.mBottomShowAnim.setDuration(600L);
    this.mBottomHideAnim = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 0.0F, 1, 0.71F);
    this.mBottomHideAnim.setDuration(600L);
  }

  private void handlePhotoOperationOptions(int paramInt)
  {
    switch (paramInt)
    {
    default:
    case 0:
    case 1:
    }
    do
    {
      do
        return;
      while (this.mPhotoUrl == null);
      try
      {
        String str = FileUtil.savePicture(this.mPhotoUrl);
        if (str != null)
        {
          getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(str))));
          Toast.makeText(getActivity(), getResources().getString(2131427492), 0).show();
          return;
        }
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        Toast.makeText(getActivity(), getResources().getString(2131427494), 0).show();
        return;
      }
      Toast.makeText(getActivity(), getResources().getString(2131427494), 0).show();
      return;
    }
    while (this.mPhotoUrl == null);
    try
    {
      Bitmap localBitmap = ImageCache.getInstance().createSafeImage(this.mPhotoUrl);
      if (localBitmap != null)
      {
        getActivity().setWallpaper(localBitmap);
        Toast.makeText(getActivity(), getResources().getString(2131427495), 0).show();
        return;
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Toast.makeText(getActivity(), getResources().getString(2131427496), 0).show();
      return;
    }
    Toast.makeText(getActivity(), getResources().getString(2131427496), 0).show();
  }

  private void initBottom(View paramView)
  {
    if ("com.kaixin001.VIEW_SINGLE_PHOTO".equals(this.action))
    {
      LinearLayout localLinearLayout = (LinearLayout)paramView.findViewById(2131363480);
      localLinearLayout.setVisibility(0);
      this.mIvSaves = ((ImageView)localLinearLayout.findViewById(2131363481));
      this.mIvSaves.setOnClickListener(this);
      this.mIvSaves.setEnabled(false);
    }
  }

  private void initDataRemote(Bundle paramBundle)
  {
    if (paramBundle == null)
      return;
    if (HttpConnection.checkNetworkAvailable(getActivity().getApplicationContext()) < 0)
    {
      DialogUtil.showKXAlertDialog(getActivity(), 2131427387, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          PreviewUploadPhotoFragment.this.finish();
        }
      });
      return;
    }
    if (HttpConnection.checkNetworkAvailable(getActivity().getApplicationContext()) == 0)
    {
      DialogUtil.showKXAlertDialog(getActivity(), 2131427388, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          PreviewUploadPhotoFragment.this.finish();
        }
      });
      return;
    }
    String str1 = paramBundle.getString("small");
    String str2 = paramBundle.getString("large");
    if (str2 == null)
      str2 = str1;
    this.mPhotoUrl = str2;
    ImageCache localImageCache = ImageCache.getInstance();
    Bitmap localBitmap1 = localImageCache.createSafeImage(this.mPhotoUrl);
    if (localBitmap1 != null)
    {
      showProgressDialog(false);
      this.mImageView.setImageBitmap(localBitmap1);
      return;
    }
    showProgressDialog(true);
    ImageDownLoaderManager.getInstance().displayPicture(this, this.mImageView, str2, 2130838766, new ImageDownloadCallback()
    {
      public void onImageDownloadFailed()
      {
      }

      public void onImageDownloadSuccess()
      {
        PreviewUploadPhotoFragment.this.mImageView.setScaleType(ImageView.ScaleType.MATRIX);
        PreviewUploadPhotoFragment.this.mIvSaves.setEnabled(true);
        PreviewUploadPhotoFragment.this.showProgressDialog(false);
      }

      public void onImageDownloading()
      {
      }
    });
    displayPicture(this.mImageView, str2, 2130838766);
    if (localImageCache.isCacheFileExists(str1))
    {
      Bitmap localBitmap2 = localImageCache.createSafeImage(str1);
      this.mImageView.setImageBitmap(localBitmap2);
      this.mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
      return;
    }
    this.mImageView.setImageResource(2130838766);
  }

  private void initPhoto(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    showProgressDialog(true);
    this.mLoadPhotoTask = new LoadPhotoTask(null);
    String[] arrayOfString = { paramString };
    this.mLoadPhotoTask.execute(arrayOfString);
  }

  private void initTitleBar(Bundle paramBundle)
  {
    RelativeLayout localRelativeLayout = (RelativeLayout)findViewById(2131361802);
    localRelativeLayout.setBackgroundResource(2130838107);
    localRelativeLayout.setVisibility(0);
    localRelativeLayout.findViewById(2131362919).setVisibility(8);
    ((ImageView)localRelativeLayout.findViewById(2131362916)).setImageResource(2130838220);
    localRelativeLayout.findViewById(2131362920).setVisibility(8);
    this.mLeftBtn = ((ImageView)localRelativeLayout.findViewById(2131362914));
    this.mLeftBtn.setOnClickListener(this);
    this.mDeleteBtn = ((ImageView)localRelativeLayout.findViewById(2131362928));
    if ("com.kaixin001.VIEW_SINGLE_PHOTO".equals(this.action))
      this.mDeleteBtn.setVisibility(8);
    while (true)
    {
      this.mIvEditPhoto = ((ImageView)findViewById(2131362924));
      if ((paramBundle.containsKey("imgFilePath")) && (paramBundle.getString("imgFilePath").equals(WriteWeiboModel.getInstance().getLastPicPath())))
      {
        TextView localTextView = (TextView)findViewById(2131362920);
        localTextView.setText(getResources().getString(2131428574));
        localTextView.setVisibility(0);
        this.mIvEditPhoto.setImageResource(2130838752);
        this.mIvEditPhoto.setVisibility(0);
        this.mIvEditPhoto.setOnClickListener(new View.OnClickListener(paramBundle)
        {
          public void onClick(View paramView)
          {
            String str = this.val$extras.getString("imgFilePath");
            if (!new File(str).exists())
            {
              Toast.makeText(PreviewUploadPhotoFragment.this.getActivity(), 2131427841, 0).show();
              return;
            }
            Intent localIntent = new Intent();
            localIntent.setClass(PreviewUploadPhotoFragment.this.getActivity(), IFEditPhotoFragment.class);
            Bundle localBundle = new Bundle();
            localBundle.putString("filePathName", str);
            localBundle.putString("fileFrom", "preview_photo");
            localIntent.putExtras(localBundle);
            PreviewUploadPhotoFragment.this.startActivityForResult(localIntent, 0);
          }
        });
        this.mDeleteBtn.setVisibility(0);
      }
      return;
      this.mDeleteBtn.setOnClickListener(this);
      this.mDeleteBtn.setImageResource(2130838758);
      this.mDeleteBtn.setBackgroundResource(2130839019);
    }
  }

  private void setImage()
  {
    if (this.mPhotoBitmap != null)
    {
      this.mImageView.setImageBitmap(this.mPhotoBitmap);
      if (this.mIvSaves != null)
        this.mIvSaves.setEnabled(true);
      return;
    }
    this.mImageView.setImageResource(2130838766);
  }

  private void showBottomButtons(boolean paramBoolean)
  {
    if (!"com.kaixin001.VIEW_SINGLE_PHOTO".equals(this.action));
    View localView;
    int i;
    do
    {
      return;
      localView = getView().findViewById(2131363480);
      i = localView.getVisibility();
    }
    while (((i == 0) && (paramBoolean)) || ((i != 0) && (!paramBoolean)));
    if (paramBoolean)
    {
      localView.startAnimation(this.mBottomShowAnim);
      localView.setVisibility(0);
      return;
    }
    localView.startAnimation(this.mBottomHideAnim);
    localView.setVisibility(8);
  }

  private void showProgressDialog(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mProgressBar.setVisibility(0);
      return;
    }
    this.mProgressBar.setVisibility(8);
  }

  private void showSavePhotoOptions()
  {
    DialogUtil.showSelectListDlg(getActivity(), 2131427491, getResources().getStringArray(2131492870), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        PreviewUploadPhotoFragment.this.handlePhotoOperationOptions(paramInt);
      }
    }
    , 1);
  }

  private void showTitleBar(boolean paramBoolean)
  {
    View localView = getView().findViewById(2131361802);
    int i = localView.getVisibility();
    if ((i == 0) && (paramBoolean));
    do
      return;
    while ((i != 0) && (!paramBoolean));
    if (paramBoolean)
    {
      localView.startAnimation(this.mTitleShowAnim);
      localView.setVisibility(0);
      return;
    }
    localView.startAnimation(this.mTitleHideAnim);
    localView.setVisibility(8);
  }

  private void startHideButtonsTimer()
  {
    if (this.mHideButtonTimer != null)
    {
      this.mHideButtonTimer.cancel();
      this.mHideButtonTimer = null;
      this.mHideButtonsTask = null;
    }
    this.mHideButtonTimer = new Timer();
    this.mHideButtonsTask = new HideButtonsTask(null);
    this.mHideButtonTimer.schedule(this.mHideButtonsTask, this.mButtonShowDuration);
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if ((paramMessage != null) && (paramMessage.what == 113))
    {
      this.mHideButtonTimer.cancel();
      showTitleBar(false);
      showBottomButtons(false);
      return true;
    }
    return super.handleMessage(paramMessage);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt2 == -1) && (paramInt1 == 0))
      paramIntent.putExtra("userAction", 105);
    setResult(-1, paramIntent);
    finishFragment(0);
    finish();
  }

  public void onClick(View paramView)
  {
    if (paramView == this.mIvSaves)
    {
      showSavePhotoOptions();
      return;
    }
    if (paramView == this.mImageView)
    {
      if (getView().findViewById(2131361802).getVisibility() != 0)
      {
        showTitleBar(true);
        showBottomButtons(true);
        startHideButtonsTimer();
        return;
      }
      showTitleBar(false);
      showBottomButtons(false);
      return;
    }
    Intent localIntent = new Intent();
    if (paramView.equals(this.mLeftBtn))
      localIntent.putExtra("userAction", 102);
    while (true)
    {
      setResult(-1, localIntent);
      finishFragment(0);
      if (finishAndRedirect())
        break;
      finish();
      return;
      if (!paramView.equals(this.mDeleteBtn))
        continue;
      localIntent.putExtra("userAction", 101);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903302, paramViewGroup, false);
  }

  public void onDestroy()
  {
    if (this.mHideButtonsTask != null)
      this.mHideButtonsTask.cancel();
    cancelTask(this.mLoadPhotoTask);
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
      this.action = localBundle.getString("action");
    this.mImageView = ((ImageView)paramView.findViewById(2131363479));
    this.mImageView.setOnClickListener(this);
    this.mProgressBar = paramView.findViewById(2131361851);
    initTitleBar(localBundle);
    initBottom(paramView);
    if (("com.kaixin001.VIEW_SINGLE_PHOTO".equals(this.action)) && (localBundle != null))
    {
      Object localObject = localBundle.getString("large");
      String str = localBundle.getString("small");
      if (localObject == null)
        localObject = str;
      if (ImageCache.getInstance().isCacheFileExists((String)localObject))
      {
        this.mPhotoUrl = ((String)localObject);
        initPhoto(ImageCache.getInstance().getFileByUrl((String)localObject));
      }
    }
    while (true)
    {
      constructAnimations();
      return;
      initDataRemote(localBundle);
      continue;
      this.mFilePath = localBundle.getString("imgFilePath");
      KXLog.d("PreviewUploadPhotoActivity", "imgFilePath" + this.mFilePath);
      initPhoto(this.mFilePath);
    }
  }

  private class HideButtonsTask extends TimerTask
  {
    private HideButtonsTask()
    {
    }

    public void run()
    {
      if (PreviewUploadPhotoFragment.this.isNeedReturn())
        return;
      Message localMessage = Message.obtain();
      localMessage.what = 113;
      PreviewUploadPhotoFragment.this.mHandler.sendMessage(localMessage);
    }
  }

  private class LoadPhotoTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    private LoadPhotoTask()
    {
      super();
    }

    // ERROR //
    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      // Byte code:
      //   0: aload_1
      //   1: ifnull +8 -> 9
      //   4: aload_1
      //   5: arraylength
      //   6: ifne +8 -> 14
      //   9: iconst_0
      //   10: invokestatic 29	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   13: areturn
      //   14: aconst_null
      //   15: astore_2
      //   16: aload_1
      //   17: iconst_0
      //   18: aaload
      //   19: astore 8
      //   21: ldc 31
      //   23: new 33	java/lang/StringBuilder
      //   26: dup
      //   27: ldc 35
      //   29: invokespecial 38	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   32: aload 8
      //   34: invokevirtual 42	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   37: invokevirtual 46	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   40: invokestatic 52	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   43: aload 8
      //   45: invokestatic 58	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   48: ifeq +16 -> 64
      //   51: iconst_0
      //   52: invokestatic 29	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   55: astore 21
      //   57: aconst_null
      //   58: invokestatic 64	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
      //   61: aload 21
      //   63: areturn
      //   64: new 66	java/io/File
      //   67: dup
      //   68: aload 8
      //   70: invokespecial 67	java/io/File:<init>	(Ljava/lang/String;)V
      //   73: astore 9
      //   75: aload 9
      //   77: invokevirtual 71	java/io/File:exists	()Z
      //   80: ifne +16 -> 96
      //   83: iconst_0
      //   84: invokestatic 29	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   87: astore 20
      //   89: aconst_null
      //   90: invokestatic 64	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
      //   93: aload 20
      //   95: areturn
      //   96: new 73	android/graphics/BitmapFactory$Options
      //   99: dup
      //   100: invokespecial 76	android/graphics/BitmapFactory$Options:<init>	()V
      //   103: astore 10
      //   105: aload 9
      //   107: invokevirtual 80	java/io/File:length	()J
      //   110: lstore 11
      //   112: lload 11
      //   114: ldc 81
      //   116: i2l
      //   117: lcmp
      //   118: ifle +18 -> 136
      //   121: aload_0
      //   122: getfield 11	com/kaixin001/fragment/PreviewUploadPhotoFragment$LoadPhotoTask:this$0	Lcom/kaixin001/fragment/PreviewUploadPhotoFragment;
      //   125: invokevirtual 87	com/kaixin001/fragment/PreviewUploadPhotoFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
      //   128: aload 8
      //   130: invokestatic 93	com/kaixin001/util/FileUtil:isScreenShot	(Landroid/content/Context;Ljava/lang/String;)Z
      //   133: ifeq +71 -> 204
      //   136: aload 10
      //   138: iconst_1
      //   139: putfield 97	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   142: new 99	java/io/FileInputStream
      //   145: dup
      //   146: aload 9
      //   148: invokespecial 102	java/io/FileInputStream:<init>	(Ljava/io/File;)V
      //   151: astore 13
      //   153: aload_0
      //   154: getfield 11	com/kaixin001/fragment/PreviewUploadPhotoFragment$LoadPhotoTask:this$0	Lcom/kaixin001/fragment/PreviewUploadPhotoFragment;
      //   157: aload 13
      //   159: aconst_null
      //   160: aload 10
      //   162: invokestatic 108	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
      //   165: invokestatic 112	com/kaixin001/fragment/PreviewUploadPhotoFragment:access$0	(Lcom/kaixin001/fragment/PreviewUploadPhotoFragment;Landroid/graphics/Bitmap;)V
      //   168: aload_0
      //   169: getfield 11	com/kaixin001/fragment/PreviewUploadPhotoFragment$LoadPhotoTask:this$0	Lcom/kaixin001/fragment/PreviewUploadPhotoFragment;
      //   172: invokestatic 116	com/kaixin001/fragment/PreviewUploadPhotoFragment:access$1	(Lcom/kaixin001/fragment/PreviewUploadPhotoFragment;)Landroid/graphics/Bitmap;
      //   175: invokevirtual 122	android/graphics/Bitmap:getWidth	()I
      //   178: pop
      //   179: aload_0
      //   180: getfield 11	com/kaixin001/fragment/PreviewUploadPhotoFragment$LoadPhotoTask:this$0	Lcom/kaixin001/fragment/PreviewUploadPhotoFragment;
      //   183: invokestatic 116	com/kaixin001/fragment/PreviewUploadPhotoFragment:access$1	(Lcom/kaixin001/fragment/PreviewUploadPhotoFragment;)Landroid/graphics/Bitmap;
      //   186: invokevirtual 125	android/graphics/Bitmap:getHeight	()I
      //   189: pop
      //   190: iconst_1
      //   191: invokestatic 29	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   194: astore 17
      //   196: aload 13
      //   198: invokestatic 64	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
      //   201: aload 17
      //   203: areturn
      //   204: lload 11
      //   206: ldc2_w 126
      //   209: ldc 81
      //   211: i2l
      //   212: lmul
      //   213: lcmp
      //   214: ifgt +36 -> 250
      //   217: aload 10
      //   219: iconst_2
      //   220: putfield 97	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   223: goto -81 -> 142
      //   226: astore 6
      //   228: ldc 31
      //   230: ldc 35
      //   232: aload 6
      //   234: invokestatic 131	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      //   237: iconst_0
      //   238: invokestatic 29	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   241: astore 7
      //   243: aload_2
      //   244: invokestatic 64	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
      //   247: aload 7
      //   249: areturn
      //   250: ldc 81
      //   252: i2l
      //   253: lstore 18
      //   255: aload 10
      //   257: iconst_1
      //   258: lload 11
      //   260: lload 18
      //   262: ldiv
      //   263: l2d
      //   264: invokestatic 137	java/lang/Math:log	(D)D
      //   267: ldc2_w 138
      //   270: invokestatic 137	java/lang/Math:log	(D)D
      //   273: ddiv
      //   274: d2i
      //   275: iadd
      //   276: putfield 97	android/graphics/BitmapFactory$Options:inSampleSize	I
      //   279: goto -137 -> 142
      //   282: astore 4
      //   284: iconst_0
      //   285: invokestatic 29	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   288: astore 5
      //   290: aload_2
      //   291: invokestatic 64	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
      //   294: aload 5
      //   296: areturn
      //   297: astore_3
      //   298: aload_2
      //   299: invokestatic 64	com/kaixin001/util/CloseUtil:close	(Ljava/io/Closeable;)V
      //   302: aload_3
      //   303: athrow
      //   304: astore_3
      //   305: aload 13
      //   307: astore_2
      //   308: goto -10 -> 298
      //   311: astore 14
      //   313: aload 13
      //   315: astore_2
      //   316: goto -32 -> 284
      //   319: astore 6
      //   321: aload 13
      //   323: astore_2
      //   324: goto -96 -> 228
      //
      // Exception table:
      //   from	to	target	type
      //   16	57	226	java/lang/Exception
      //   64	89	226	java/lang/Exception
      //   96	112	226	java/lang/Exception
      //   121	136	226	java/lang/Exception
      //   136	142	226	java/lang/Exception
      //   142	153	226	java/lang/Exception
      //   217	223	226	java/lang/Exception
      //   255	279	226	java/lang/Exception
      //   16	57	282	java/lang/OutOfMemoryError
      //   64	89	282	java/lang/OutOfMemoryError
      //   96	112	282	java/lang/OutOfMemoryError
      //   121	136	282	java/lang/OutOfMemoryError
      //   136	142	282	java/lang/OutOfMemoryError
      //   142	153	282	java/lang/OutOfMemoryError
      //   217	223	282	java/lang/OutOfMemoryError
      //   255	279	282	java/lang/OutOfMemoryError
      //   16	57	297	finally
      //   64	89	297	finally
      //   96	112	297	finally
      //   121	136	297	finally
      //   136	142	297	finally
      //   142	153	297	finally
      //   217	223	297	finally
      //   228	243	297	finally
      //   255	279	297	finally
      //   284	290	297	finally
      //   153	196	304	finally
      //   153	196	311	java/lang/OutOfMemoryError
      //   153	196	319	java/lang/Exception
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if ((paramBoolean == null) || (PreviewUploadPhotoFragment.this.getView() == null) || (PreviewUploadPhotoFragment.this.getActivity() == null))
        return;
      try
      {
        if (paramBoolean.booleanValue())
          PreviewUploadPhotoFragment.this.setImage();
        PreviewUploadPhotoFragment.this.showProgressDialog(false);
        return;
      }
      catch (Exception localException)
      {
        while (true)
          KXLog.e("PreviewUploadPhotoActivity", "onPostExecute", localException);
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.PreviewUploadPhotoFragment
 * JD-Core Version:    0.6.0
 */