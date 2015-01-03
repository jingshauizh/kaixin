package com.kaixin001.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;
import com.kaixin001.model.RequestVo;
import com.kaixin001.network.HttpUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class KXDownloadService extends Service
  implements Runnable
{
  private String clickid;
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      case -1:
      case 1:
      default:
        return;
      case 0:
        KXDownloadService.this.handleStart();
        return;
      case 2:
      }
      ((NotificationManager)KXDownloadService.this.getSystemService("notification")).cancel(1);
      File localFile = (File)paramMessage.obj;
      KXDownloadService.this.handleEnd(localFile);
    }
  };
  private String targetid;
  private String urlStr;

  protected void handleEnd(File paramFile)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.setDataAndType(Uri.fromFile(paramFile), "application/vnd.android.package-archive");
    localIntent.addFlags(268435456);
    startActivity(localIntent);
    RequestVo localRequestVo = new RequestVo();
    localRequestVo.setUrl("http://c.gdt.qq.com/gdt_trace_a.fcg" + "?actionid=7&targettype=6&tagetid=" + this.targetid + "&clickid=" + this.clickid);
    localRequestVo.setSuccessCode(200);
    new Thread(new KXDownRun(localRequestVo)).start();
  }

  protected void handleStart()
  {
    RequestVo localRequestVo = new RequestVo();
    localRequestVo.setUrl("http://c.gdt.qq.com/gdt_trace_a.fcg" + "?actionid=5&targettype=6&tagetid=" + this.targetid + "&clickid=" + this.clickid);
    localRequestVo.setSuccessCode(200);
    new Thread(new KXDownRun(localRequestVo)).start();
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    this.urlStr = paramIntent.getExtras().getString("url");
    this.clickid = paramIntent.getExtras().getString("clickid");
    this.targetid = paramIntent.getExtras().getString("targetid");
    new Thread(this).start();
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }

  public void run()
  {
    Message localMessage = new Message();
    Intent localIntent = new Intent(getApplicationContext(), getClass());
    localIntent.addFlags(536870912);
    PendingIntent localPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, localIntent, 0);
    Notification localNotification = new Notification(2130838920, "正在下载", System.currentTimeMillis());
    RemoteViews localRemoteViews = new RemoteViews(getPackageName(), 2130903264);
    localRemoteViews.setImageViewBitmap(2131363286, BitmapFactory.decodeResource(getResources(), 2130838920));
    localRemoteViews.setTextViewText(2131363287, "下载中");
    localRemoteViews.setProgressBar(2131363288, 100, 0, false);
    localNotification.contentView = localRemoteViews;
    localNotification.contentIntent = localPendingIntent;
    startForeground(1, localNotification);
    String str1;
    String str2;
    InputStream localInputStream;
    try
    {
      URL localURL = new URL(this.urlStr);
      str1 = "kaixinAd" + System.currentTimeMillis() + ".apk";
      str2 = Environment.getExternalStorageDirectory().toString();
      URLConnection localURLConnection = localURL.openConnection();
      localURLConnection.connect();
      localInputStream = localURLConnection.getInputStream();
      if (localURLConnection.getContentLength() <= 0)
        throw new RuntimeException("无法获知文件大小 ");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    FileOutputStream localFileOutputStream = new FileOutputStream(str2 + "/" + str1);
    byte[] arrayOfByte = new byte[1024];
    localMessage.what = 0;
    this.handler.sendMessage(localMessage);
    while (true)
    {
      int i = localInputStream.read(arrayOfByte);
      if (i == -1)
      {
        localMessage.what = 2;
        localMessage.obj = new File(str2 + "/" + str1);
        this.handler.sendMessage(localMessage);
        return;
      }
      localFileOutputStream.write(arrayOfByte, 0, i);
    }
  }

  class KXDownRun
    implements Runnable
  {
    private RequestVo vo;

    public KXDownRun(RequestVo arg2)
    {
      Object localObject;
      this.vo = localObject;
    }

    public void run()
    {
      HttpUtils.get(this.vo);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.service.KXDownloadService
 * JD-Core Version:    0.6.0
 */