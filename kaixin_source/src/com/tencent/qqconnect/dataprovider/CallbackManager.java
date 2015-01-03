package com.tencent.qqconnect.dataprovider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import com.tencent.qqconnect.dataprovider.datatype.TextAndMediaPath;
import com.tencent.qqconnect.dataprovider.datatype.TextOnly;
import java.io.File;
import java.lang.ref.WeakReference;

public final class CallbackManager
{
  private static final String HTTP_PREFIX = "http://";
  private String mAppid;
  private WeakReference<Context> mContext;
  private Uri mData;
  private String mFromActivityClassName;
  private String mFromPackageName;
  private boolean mIsCallFromTencentApp = false;
  private int mRequestDataTypeFlag;
  private String mSrcAction;

  public CallbackManager(Activity paramActivity)
  {
    this.mContext = new WeakReference(paramActivity.getApplicationContext());
    Intent localIntent = paramActivity.getIntent();
    if (localIntent != null)
    {
      this.mData = localIntent.getData();
      this.mFromPackageName = localIntent.getStringExtra("srcPackageName");
      this.mFromActivityClassName = localIntent.getStringExtra("srcClassName");
      this.mSrcAction = localIntent.getStringExtra("srcAction");
      this.mRequestDataTypeFlag = localIntent.getIntExtra("requestDataTypeFlag", 0);
      this.mAppid = localIntent.getStringExtra("params_appid");
      if ((this.mData != null) && (this.mFromActivityClassName != null))
        this.mIsCallFromTencentApp = true;
    }
  }

  private int BackToTencentApp(Bundle paramBundle)
  {
    if (!this.mIsCallFromTencentApp)
      return -2;
    Intent localIntent = new Intent();
    localIntent.setClassName(this.mFromPackageName, this.mFromActivityClassName);
    localIntent.setAction(this.mSrcAction);
    paramBundle.putString("params_appid", this.mAppid);
    localIntent.putExtras(paramBundle);
    localIntent.setFlags(268435456);
    ((Context)this.mContext.get()).startActivity(localIntent);
    return 0;
  }

  private int checkFilePath(String paramString)
  {
    int i;
    if (paramString == null)
      i = -7;
    boolean bool2;
    do
    {
      String str;
      boolean bool1;
      do
      {
        return i;
        str = paramString.toLowerCase();
        bool1 = str.startsWith("http://");
        i = 0;
      }
      while (bool1);
      if (Environment.getExternalStorageState().equals("mounted"))
      {
        if (!str.startsWith(Environment.getExternalStorageDirectory().toString().toLowerCase()))
          return -5;
      }
      else
        return -10;
      File localFile = new File(paramString);
      if ((!localFile.exists()) || (localFile.isDirectory()))
        return -8;
      long l = localFile.length();
      if (l == 0L)
        return -9;
      bool2 = l < 1073741824L;
      i = 0;
    }
    while (!bool2);
    return -6;
  }

  public int getRequestDateTypeFlag()
  {
    return this.mRequestDataTypeFlag;
  }

  public boolean isCallFromTencentApp()
  {
    return this.mIsCallFromTencentApp;
  }

  public boolean isSupportType(int paramInt)
  {
    return (paramInt & getRequestDateTypeFlag()) != 0;
  }

  public int sendTextAndImagePath(String paramString1, String paramString2)
  {
    int i;
    if (!isSupportType(1))
      i = -1;
    do
    {
      return i;
      i = checkFilePath(paramString2);
    }
    while (i != 0);
    TextAndMediaPath localTextAndMediaPath = new TextAndMediaPath(paramString1, paramString2);
    Bundle localBundle = new Bundle();
    localBundle.putInt("contentDataType", 1);
    localBundle.putParcelable("contentData", localTextAndMediaPath);
    return BackToTencentApp(localBundle);
  }

  public int sendTextAndVideoPath(String paramString1, String paramString2)
  {
    int i;
    if (!isSupportType(2))
      i = -1;
    do
    {
      return i;
      i = checkFilePath(paramString2);
    }
    while (i != 0);
    TextAndMediaPath localTextAndMediaPath = new TextAndMediaPath(paramString1, paramString2);
    Bundle localBundle = new Bundle();
    localBundle.putInt("contentDataType", 2);
    localBundle.putParcelable("contentData", localTextAndMediaPath);
    return BackToTencentApp(localBundle);
  }

  public int sendTextOnly(String paramString)
  {
    if (!isSupportType(4))
      return -1;
    TextOnly localTextOnly = new TextOnly(paramString);
    Bundle localBundle = new Bundle();
    localBundle.putInt("contentDataType", 4);
    localBundle.putParcelable("contentData", localTextOnly);
    return BackToTencentApp(localBundle);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.qqconnect.dataprovider.CallbackManager
 * JD-Core Version:    0.6.0
 */