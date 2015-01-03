package com.kaixin001.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.fragment.NewsFragment;
import com.kaixin001.item.CircleRecordUploadTask;
import com.kaixin001.item.DetailItem;
import com.kaixin001.item.DiaryUploadTask;
import com.kaixin001.item.KXUploadTask;
import com.kaixin001.item.PhotoUploadTask;
import com.kaixin001.item.RecordUploadTask;
import com.kaixin001.model.User;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UploadNotificationHandler extends Handler
{
  private static final int NOTIFICATION_BASE = 11000;
  private static UploadNotificationHandler instance = null;
  private Context context = null;
  private final List<KXUploadTask> endListTasks = new ArrayList();
  private boolean isNotifyEnd = true;
  private NotificationManager notificationManager = null;
  private final List<KXUploadTask> processingListTasks = new ArrayList();

  private UploadNotificationHandler(Context paramContext)
  {
    this.context = paramContext.getApplicationContext();
    this.notificationManager = ((NotificationManager)this.context.getSystemService("notification"));
  }

  private void addProcessingElementFromList(List<KXUploadTask> paramList)
  {
    int i = 0;
    monitorenter;
    if (paramList != null);
    try
    {
      KXUploadTask[] arrayOfKXUploadTask = (KXUploadTask[])paramList.toArray(new KXUploadTask[0]);
      int j = arrayOfKXUploadTask.length;
      while (true)
      {
        if (i >= j)
          return;
        KXUploadTask localKXUploadTask = arrayOfKXUploadTask[i];
        if ((!UploadTaskListEngine.getInstance().isChatTask(localKXUploadTask)) && (localKXUploadTask.getTaskType() != 5) && (localKXUploadTask.getTaskType() != 4) && ((localKXUploadTask.getTaskStatus() == 0) || (localKXUploadTask.getTaskStatus() == 1)))
          this.processingListTasks.add(localKXUploadTask);
        i++;
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public static UploadNotificationHandler getInstance(Context paramContext)
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new UploadNotificationHandler(paramContext);
      UploadNotificationHandler localUploadNotificationHandler = instance;
      return localUploadNotificationHandler;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private void syncProcessingTaskList()
  {
    monitorenter;
    try
    {
      this.processingListTasks.clear();
      UploadTaskListEngine localUploadTaskListEngine = UploadTaskListEngine.getInstance();
      addProcessingElementFromList(localUploadTaskListEngine.getUploadingTaskList());
      addProcessingElementFromList(localUploadTaskListEngine.getWaitTaskList());
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  private void updateEndNotification(KXUploadTask paramKXUploadTask)
  {
    monitorenter;
    try
    {
      this.endListTasks.add(paramKXUploadTask);
      updateNotification(paramKXUploadTask);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  private void updateFailedNotification(KXUploadTask paramKXUploadTask)
  {
    if (paramKXUploadTask.getTitle().length() > 5);
    for (String str1 = paramKXUploadTask.getTitle().substring(0, 3) + "..."; ; str1 = paramKXUploadTask.getTitle())
      switch (paramKXUploadTask.getTaskType())
      {
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      default:
        return;
      case 0:
      case 1:
      case 2:
      case 3:
      case 10:
      }
    String str2 = this.context.getString(2131428288) + str1 + this.context.getString(2131428294);
    while (true)
    {
      Notification localNotification = new Notification(2130839258, this.context.getString(2131428282) + str2, System.currentTimeMillis());
      localNotification.flags = (0x10 | localNotification.flags);
      Intent localIntent = new Intent(this.context, MainActivity.class);
      localIntent.putExtra("fragment", "UploadTaskListFragment");
      PendingIntent localPendingIntent = PendingIntent.getActivity(this.context, 0, localIntent, 0);
      String str3 = this.context.getResources().getString(2131427329);
      localNotification.setLatestEventInfo(this.context, str3, str2, localPendingIntent);
      this.notificationManager.notify(11000 + paramKXUploadTask.getTaskId(), localNotification);
      return;
      str2 = this.context.getString(2131428289) + str1 + this.context.getString(2131428295);
      continue;
      str2 = this.context.getString(2131428290) + str1 + this.context.getString(2131428295);
      continue;
      str2 = this.context.getString(2131428291) + str1 + this.context.getString(2131428295);
      continue;
      str2 = this.context.getString(2131428292) + str1 + this.context.getString(2131428295);
    }
  }

  private void updateFinishedNotification(KXUploadTask paramKXUploadTask)
  {
    if (paramKXUploadTask.getTitle().length() > 5);
    for (String str1 = paramKXUploadTask.getTitle().substring(0, 3) + "..."; ; str1 = paramKXUploadTask.getTitle())
      switch (paramKXUploadTask.getTaskType())
      {
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      default:
        return;
      case 0:
      case 1:
      case 2:
      case 3:
      case 10:
      }
    String str2 = this.context.getString(2131428283) + str1 + this.context.getString(2131428293);
    Notification localNotification = new Notification(2130839260, this.context.getString(2131428282) + str2, System.currentTimeMillis());
    localNotification.flags = (0x10 | localNotification.flags);
    Intent localIntent = new Intent();
    localIntent.setAction(System.currentTimeMillis());
    switch (paramKXUploadTask.getTaskType())
    {
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    case 9:
    default:
    case 0:
    case 1:
    case 2:
    case 3:
    case 10:
    }
    while (true)
    {
      PendingIntent localPendingIntent = PendingIntent.getActivity(this.context, 0, localIntent, 0);
      String str3 = this.context.getResources().getString(2131427329);
      localNotification.setLatestEventInfo(this.context, str3, str2, localPendingIntent);
      this.notificationManager.notify(11000 + paramKXUploadTask.getTaskId(), localNotification);
      return;
      str2 = this.context.getString(2131428284) + str1 + this.context.getString(2131428293);
      break;
      str2 = this.context.getString(2131428285) + str1 + this.context.getString(2131428293);
      break;
      str2 = this.context.getString(2131428286) + str1 + this.context.getString(2131428293);
      break;
      str2 = this.context.getString(2131428287) + str1 + this.context.getString(2131428293);
      break;
      RecordUploadTask localRecordUploadTask2 = (RecordUploadTask)paramKXUploadTask;
      localIntent.setClass(this.context, MainActivity.class);
      DetailItem localDetailItem2 = DetailItem.makeWeiboDetailItem("1018", User.getInstance().getUID(), localRecordUploadTask2.getRecordId());
      Bundle localBundle4 = new Bundle();
      localBundle4.putSerializable("data", localDetailItem2);
      localIntent.putExtras(localBundle4);
      localIntent.putExtra("fragment", "NewsDetailFragment");
      localIntent.putExtra("prefragment", NewsFragment.class.getName());
      continue;
      RecordUploadTask localRecordUploadTask1 = (RecordUploadTask)paramKXUploadTask;
      localIntent.setClass(this.context, MainActivity.class);
      DetailItem localDetailItem1 = DetailItem.makeWeiboDetailItem("1018", User.getInstance().getUID(), localRecordUploadTask1.getRecordId());
      Bundle localBundle3 = new Bundle();
      localBundle3.putSerializable("data", localDetailItem1);
      localIntent.putExtras(localBundle3);
      localIntent.putExtra("fragment", "NewsDetailFragment");
      localIntent.putExtra("prefragment", NewsFragment.class.getName());
      continue;
      PhotoUploadTask localPhotoUploadTask = (PhotoUploadTask)paramKXUploadTask;
      if ((localPhotoUploadTask.getAlbumId() == null) || (localPhotoUploadTask.getAlbumId().length() == 0) || (localPhotoUploadTask.getImageIndex() == null) || (localPhotoUploadTask.getImageIndex().length() == 0))
        continue;
      localIntent.setClass(this.context, MainActivity.class);
      localIntent.putExtra("small", localPhotoUploadTask.getAlbumId());
      localIntent.putExtra("large", localPhotoUploadTask.getImageIndex());
      localIntent.putExtra("action", "com.kaixin001.VIEW_SINGLE_PHOTO");
      localIntent.putExtra("fragment", "PreviewUploadPhotoFragment");
      localIntent.putExtra("prefragment", NewsFragment.class.getName());
      continue;
      DiaryUploadTask localDiaryUploadTask = (DiaryUploadTask)paramKXUploadTask;
      localIntent.setClass(this.context, MainActivity.class);
      Bundle localBundle2 = new Bundle();
      localBundle2.putString("fuid", User.getInstance().getUID());
      localBundle2.putString("did", localDiaryUploadTask.getDiaryId());
      localBundle2.putString("title", localDiaryUploadTask.getTitle());
      localBundle2.putString("intro", localDiaryUploadTask.getContent());
      localIntent.putExtras(localBundle2);
      localIntent.putExtra("fragment", "DiaryDetailFragment");
      localIntent.putExtra("prefragment", NewsFragment.class.getName());
      continue;
      CircleRecordUploadTask localCircleRecordUploadTask = (CircleRecordUploadTask)paramKXUploadTask;
      localIntent.setClass(this.context, MainActivity.class);
      Bundle localBundle1 = new Bundle();
      localBundle1.putSerializable("tid", localCircleRecordUploadTask.getRecordId());
      localBundle1.putSerializable("gid", localCircleRecordUploadTask.getGid());
      localIntent.putExtras(localBundle1);
      localIntent.putExtra("fragment", "CircleDetailsFragment");
      localIntent.putExtra("prefragment", NewsFragment.class.getName());
    }
  }

  private void updateNotification(KXUploadTask paramKXUploadTask)
  {
    switch (paramKXUploadTask.getTaskStatus())
    {
    default:
      return;
    case 3:
      updateFailedNotification(paramKXUploadTask);
      return;
    case 2:
      updateFinishedNotification(paramKXUploadTask);
      return;
    case 1:
      updateUploadingNotification(paramKXUploadTask);
      return;
    case 0:
    }
    updateWaitingNotification(paramKXUploadTask);
  }

  private void updateUploadingNotification(KXUploadTask paramKXUploadTask)
  {
    if (paramKXUploadTask.getTitle().length() > 5);
    for (String str1 = paramKXUploadTask.getTitle().substring(0, 3) + "..."; ; str1 = paramKXUploadTask.getTitle())
      switch (paramKXUploadTask.getTaskType())
      {
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      default:
        return;
      case 0:
      case 1:
      case 2:
      case 3:
      case 10:
      }
    String str2 = this.context.getString(2131428277) + str1 + this.context.getString(2131428293);
    while (true)
    {
      Notification localNotification = new Notification(2130839259, str2, System.currentTimeMillis());
      localNotification.flags = (0x20 | localNotification.flags);
      localNotification.flags = (0x2 | localNotification.flags);
      RemoteViews localRemoteViews = new RemoteViews(this.context.getPackageName(), 2130903386);
      localRemoteViews.setTextViewText(2131363847, str2);
      localRemoteViews.setProgressBar(2131362773, 100, paramKXUploadTask.getProgress(), false);
      localRemoteViews.setImageViewResource(2131363845, 2130839259);
      localRemoteViews.setTextViewText(2131363846, paramKXUploadTask.getProgress() + "%");
      localNotification.contentView = localRemoteViews;
      Intent localIntent = new Intent(this.context, MainActivity.class);
      localIntent.putExtra("fragment", "UploadTaskListFragment");
      localNotification.contentIntent = PendingIntent.getActivity(this.context, 0, localIntent, 0);
      this.notificationManager.notify(11000 + paramKXUploadTask.getTaskId(), localNotification);
      return;
      str2 = this.context.getString(2131428278) + str1 + this.context.getString(2131428293);
      continue;
      str2 = this.context.getString(2131428279) + str1 + this.context.getString(2131428293);
      continue;
      str2 = this.context.getString(2131428280) + str1 + this.context.getString(2131428293);
      continue;
      str2 = this.context.getString(2131428281) + str1 + this.context.getString(2131428293);
    }
  }

  private void updateWaitingNotification(KXUploadTask paramKXUploadTask)
  {
    if (paramKXUploadTask.getTitle().length() > 5);
    for (String str1 = paramKXUploadTask.getTitle().substring(0, 3) + "..."; ; str1 = paramKXUploadTask.getTitle())
      switch (paramKXUploadTask.getTaskType())
      {
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      default:
        return;
      case 0:
      case 1:
      case 2:
      case 3:
      case 10:
      }
    String str2 = this.context.getString(2131428277) + str1 + this.context.getString(2131428293);
    while (true)
    {
      Notification localNotification = new Notification(2130839259, str2, System.currentTimeMillis());
      localNotification.flags = (0x20 | localNotification.flags);
      localNotification.flags = (0x2 | localNotification.flags);
      RemoteViews localRemoteViews = new RemoteViews(this.context.getPackageName(), 2130903386);
      localRemoteViews.setTextViewText(2131363847, str2);
      localRemoteViews.setProgressBar(2131362773, 1, 0, false);
      localRemoteViews.setImageViewResource(2131363845, 2130839259);
      localRemoteViews.setTextViewText(2131363846, "0%");
      localNotification.contentView = localRemoteViews;
      Intent localIntent = new Intent(this.context, MainActivity.class);
      localIntent.putExtra("fragment", "UploadTaskListFragment");
      localNotification.contentIntent = PendingIntent.getActivity(this.context, 0, localIntent, 0);
      this.notificationManager.notify(11000 + paramKXUploadTask.getTaskId(), localNotification);
      return;
      str2 = this.context.getString(2131428278) + str1 + this.context.getString(2131428293);
      continue;
      str2 = this.context.getString(2131428279) + str1 + this.context.getString(2131428293);
      continue;
      str2 = this.context.getString(2131428280) + str1 + this.context.getString(2131428293);
      continue;
      str2 = this.context.getString(2131428281) + str1 + this.context.getString(2131428293);
    }
  }

  public void deleteAllEndNotifications()
  {
    monitorenter;
    try
    {
      Iterator localIterator = this.endListTasks.iterator();
      while (true)
      {
        if (!localIterator.hasNext())
        {
          this.endListTasks.clear();
          return;
        }
        deleteNotification((KXUploadTask)localIterator.next());
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void deleteAllNotifications()
  {
    monitorenter;
    while (true)
    {
      Iterator localIterator2;
      try
      {
        Iterator localIterator1 = this.processingListTasks.iterator();
        if (localIterator1.hasNext())
          continue;
        localIterator2 = this.endListTasks.iterator();
        if (!localIterator2.hasNext())
        {
          this.processingListTasks.clear();
          this.endListTasks.clear();
          return;
          deleteNotification((KXUploadTask)localIterator1.next());
          continue;
        }
      }
      finally
      {
        monitorexit;
      }
      deleteNotification((KXUploadTask)localIterator2.next());
    }
  }

  public void deleteNotification(KXUploadTask paramKXUploadTask)
  {
    this.notificationManager.cancel(11000 + paramKXUploadTask.getTaskId());
  }

  public void handleMessage(Message paramMessage)
  {
    if (paramMessage == null);
    do
    {
      return;
      switch (paramMessage.what)
      {
      default:
        return;
      case 10001:
      case 10002:
        updateAllProcessingNotifications();
        return;
      case 10003:
      case 10004:
      case 10005:
      case 10006:
      case 10007:
      }
    }
    while ((UploadTaskListEngine.getInstance().isChatTask((KXUploadTask)paramMessage.obj)) || (((KXUploadTask)paramMessage.obj).getTaskType() == 5) || (((KXUploadTask)paramMessage.obj).getTaskType() == 4));
    if (this.isNotifyEnd)
    {
      updateEndNotification((KXUploadTask)paramMessage.obj);
      return;
    }
    deleteNotification((KXUploadTask)paramMessage.obj);
  }

  public void setIsNotifyEndTask(boolean paramBoolean)
  {
    this.isNotifyEnd = paramBoolean;
  }

  public void updateAllProcessingNotifications()
  {
    monitorenter;
    try
    {
      syncProcessingTaskList();
      int i = this.processingListTasks.size();
      for (int j = i - 1; ; j--)
      {
        if (j < 0)
          return;
        updateNotification((KXUploadTask)this.processingListTasks.get(j));
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.UploadNotificationHandler
 * JD-Core Version:    0.6.0
 */