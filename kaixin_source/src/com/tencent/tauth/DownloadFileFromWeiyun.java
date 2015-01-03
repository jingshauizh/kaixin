package com.tencent.tauth;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import java.io.File;

public class DownloadFileFromWeiyun
{
  private static final int DOWNLOAD_GETPERMISSION_SUCCESS = 0;
  private static final String DOWNLOAD_MUSIC_URL = "https://openmobile.qq.com/weiyun/download_music";
  private static final String DOWNLOAD_PIC_URL = "https://openmobile.qq.com/weiyun/download_photo";
  private static final int DOWNLOAD_STATUS_ERROR = 3;
  private static final int DOWNLOAD_STATUS_PROGRESS = 2;
  private static final int DOWNLOAD_STATUS_SUCCESS = 1;
  private static final String DOWNLOAD_THUMB_URL = "https://openmobile.qq.com/weiyun/get_photo_thumb";
  private static final String DOWNLOAD_VIDEO_URL = "https://openmobile.qq.com/weiyun/download_video";
  private static final String oauth_consumer_key = "222222";
  private int MAX_ERROR_TIMES = 10;
  private int current_actiontype;
  private String dir;
  private String dl_cookie_name;
  private String dl_cookie_value;
  private String dl_encrypt_url;
  private String dl_svr_host;
  private int dl_svr_port;
  private String dl_thumb_size;
  private boolean isOriginal;
  private String mFileid;
  private String mFilename;
  private long mFilesize;

  @SuppressLint({"HandlerLeak"})
  private Handler mHandler = new DownloadFileFromWeiyun.1(this, Looper.getMainLooper());
  private IDownloadFileFromWeiyunStatus mListener;
  private String mRequestUrl;
  private Tencent mTencent;
  private String mThumb;

  public DownloadFileFromWeiyun(Tencent paramTencent, String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, IDownloadFileFromWeiyunStatus paramIDownloadFileFromWeiyunStatus)
  {
    this.mFileid = (paramString1 + "");
    this.mListener = paramIDownloadFileFromWeiyunStatus;
    this.mFilesize = paramInt2;
    this.mFilename = paramString3;
    this.isOriginal = true;
    this.mTencent = paramTencent;
    this.dir = (Environment.getExternalStorageDirectory() + "/" + paramString2);
    File localFile = new File(this.dir);
    if (!localFile.exists())
      localFile.mkdirs();
    this.current_actiontype = paramInt1;
    switch (this.current_actiontype)
    {
    case 1003:
    default:
      return;
    case 1001:
      this.mRequestUrl = "https://openmobile.qq.com/weiyun/download_photo";
      return;
    case 1002:
      this.mRequestUrl = "https://openmobile.qq.com/weiyun/download_music";
      return;
    case 1004:
    }
    this.mRequestUrl = "https://openmobile.qq.com/weiyun/download_video";
  }

  public DownloadFileFromWeiyun(Tencent paramTencent, String paramString1, String paramString2, String paramString3, String paramString4, IDownloadFileFromWeiyunStatus paramIDownloadFileFromWeiyunStatus)
  {
    this.mFileid = (paramString1 + "");
    this.mThumb = paramString2;
    this.mListener = paramIDownloadFileFromWeiyunStatus;
    this.mFilename = ("thumb__" + paramString4);
    this.mTencent = paramTencent;
    this.isOriginal = false;
    this.dir = (Environment.getExternalStorageDirectory() + "/" + paramString3);
    File localFile = new File(this.dir);
    if (!localFile.exists())
      localFile.mkdirs();
    this.current_actiontype = 1001;
    this.mRequestUrl = "https://openmobile.qq.com/weiyun/get_photo_thumb";
  }

  private void doDownload()
  {
    new DownloadFileFromWeiyun.3(this).start();
  }

  private void getDownloadPermission()
  {
    new DownloadFileFromWeiyun.2(this).start();
  }

  public void start()
  {
    if (new File(this.dir, this.mFilename).exists())
    {
      this.mListener.onDownloadSuccess(this.dir + "/" + this.mFilename);
      return;
    }
    this.mListener.onPrepareStart();
    getDownloadPermission();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.DownloadFileFromWeiyun
 * JD-Core Version:    0.6.0
 */