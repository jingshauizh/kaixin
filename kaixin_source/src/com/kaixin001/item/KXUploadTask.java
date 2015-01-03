package com.kaixin001.item;

import android.content.ContentValues;
import android.content.Context;
import com.kaixin001.db.UpLoadTaskListDBAdapter.TaskParameters;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProgressListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class KXUploadTask
  implements HttpProgressListener
{
  private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final int GROUPMESSAGECLEAR = 11;
  public static final int GROUPMESSAGEINPUT = 8;
  public static final int GROUPTYPINGINPUT = 9;
  public static final int MESSAGECLEAR = 12;
  public static final int MESSAGEINPUT = 6;
  public static final int STATUS_FAILED = 3;
  public static final int STATUS_FINISHED = 2;
  public static final int STATUS_UPLOADING = 1;
  public static final int STATUS_WAITING = 0;
  private static final String TIME_FORMAT = "HH:mm";
  public static final int TYPE_CHAT_CIRCLE_RECORD = 10;
  public static final int TYPE_FORWARD_RECORD = 0;
  public static final int TYPE_REPLY_MESSAGE = 5;
  public static final int TYPE_REPLY_MODEL = 4;
  public static final int TYPE_UPLOAD_DIARY = 3;
  public static final int TYPE_UPLOAD_PHOTO = 2;
  public static final int TYPE_UPLOAD_RECORD = 1;
  public static final int TYPINGINPUT = 7;
  protected Context mContext = null;
  private String mFinishTime = null;
  private String mFinishTimeWithoutDate = null;
  private int mId = 0;
  protected int mLastError = 1;
  private String mLatitude = null;
  private String mLocationName = null;
  private String mLongitude = null;
  private int mNType = 0;
  private int mOldProgress = 0;
  private long mOldProgressTime = 0L;
  private int mProgress = 0;
  private String mStartTime = null;
  private int mStatus = 0;
  protected Object mTag = null;
  private String mTitle = null;
  private int mType = 1;
  private int mUploadTimes = 0;

  static
  {
    if (!KXUploadTask.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public KXUploadTask(Context paramContext)
  {
    setTaskStatus(0);
    this.mContext = paramContext;
  }

  public abstract void deleteDraft();

  public abstract void doCancel();

  public abstract boolean doUpload();

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    KXUploadTask localKXUploadTask;
    do
    {
      return true;
      if (paramObject == null)
        return false;
      if (getClass() != paramObject.getClass())
        return false;
      localKXUploadTask = (KXUploadTask)paramObject;
    }
    while (this.mId == localKXUploadTask.mId);
    return false;
  }

  public Context getContext()
  {
    return this.mContext;
  }

  public String getFinishTime()
  {
    return this.mFinishTime;
  }

  public String getFinishTimeWithoutDate()
  {
    return this.mFinishTimeWithoutDate;
  }

  public int getLastError()
  {
    return this.mLastError;
  }

  public String getLatitude()
  {
    return this.mLatitude;
  }

  public String getLocationName()
  {
    return this.mLocationName;
  }

  public String getLongitude()
  {
    return this.mLongitude;
  }

  public int getNType()
  {
    return this.mNType;
  }

  public int getProgress()
  {
    return this.mProgress;
  }

  public String getStartTime()
  {
    return this.mStartTime;
  }

  public Object getTag()
  {
    return this.mTag;
  }

  public int getTaskId()
  {
    return this.mId;
  }

  public int getTaskOperTimes()
  {
    return this.mUploadTimes;
  }

  public int getTaskStatus()
  {
    return this.mStatus;
  }

  public int getTaskType()
  {
    return this.mType;
  }

  public String getTitle()
  {
    return this.mTitle;
  }

  public int hashCode()
  {
    return 31 + this.mId;
  }

  public void initUploadTask(UpLoadTaskListDBAdapter.TaskParameters paramTaskParameters)
  {
    assert (paramTaskParameters != null);
    setLocationName(paramTaskParameters.mData5);
    setLatitude(paramTaskParameters.mData6);
    setLongitude(paramTaskParameters.mData7);
    setTaskStatus(paramTaskParameters.status);
    setFinishTime(paramTaskParameters.mData11);
    setStartTime(paramTaskParameters.mData12);
    setTaskType(paramTaskParameters.mData16);
    setTaskId(paramTaskParameters.mId);
  }

  public void setFinishTime(String paramString)
  {
    this.mFinishTime = paramString;
    if (this.mFinishTime != null)
      this.mFinishTimeWithoutDate = paramString.substring(11, 16);
  }

  public void setFinishTime(Date paramDate)
  {
    this.mFinishTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(paramDate);
    this.mFinishTimeWithoutDate = new SimpleDateFormat("HH:mm").format(new Date());
  }

  public void setLatitude(String paramString)
  {
    this.mLatitude = paramString;
  }

  public void setLocationName(String paramString)
  {
    this.mLocationName = paramString;
  }

  public void setLongitude(String paramString)
  {
    this.mLongitude = paramString;
  }

  public void setNType(int paramInt)
  {
    this.mNType = paramInt;
  }

  public void setProgress(int paramInt)
  {
    this.mProgress = paramInt;
  }

  public void setStartTime(String paramString)
  {
    this.mStartTime = paramString;
  }

  public void setTag(Object paramObject)
  {
    this.mTag = paramObject;
  }

  public void setTaskId(int paramInt)
  {
    this.mId = paramInt;
  }

  public void setTaskOperTimes(int paramInt)
  {
    this.mUploadTimes = paramInt;
  }

  public void setTaskStatus(int paramInt)
  {
    this.mStatus = paramInt;
  }

  public void setTaskType(int paramInt)
  {
    this.mType = paramInt;
  }

  public void setTitle(String paramString)
  {
    this.mTitle = paramString;
  }

  public ContentValues toContentValues()
  {
    ContentValues localContentValues = new ContentValues();
    if (getLocationName() != null)
      localContentValues.put("DATA5", getLocationName());
    if (getLatitude() != null)
      localContentValues.put("DATA6", getLatitude());
    if (getLongitude() != null)
      localContentValues.put("DATA7", getLongitude());
    localContentValues.put("DATA8", Integer.valueOf(getTaskStatus()));
    if (getFinishTime() != null)
      localContentValues.put("DATA11", getFinishTime());
    if (getStartTime() != null)
      localContentValues.put("DATA12", getStartTime());
    if (User.getInstance().getUID() != null)
      localContentValues.put("DATA15", User.getInstance().getUID());
    localContentValues.put("DATA16", Integer.valueOf(getTaskType()));
    return localContentValues;
  }

  public void transferred(long paramLong1, long paramLong2)
  {
    this.mProgress = (int)(paramLong1 * 100L / paramLong2);
    long l1 = System.currentTimeMillis();
    long l2 = l1 - this.mOldProgressTime;
    int i = this.mProgress - this.mOldProgress;
    if ((l2 > 100L) && (i > 5))
    {
      UploadTaskListEngine.getInstance().broadcastMessage(10002, this.mProgress, this);
      this.mOldProgressTime = l1;
      this.mOldProgress = this.mProgress;
    }
  }

  public void updateFinishTime()
  {
    this.mFinishTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    this.mFinishTimeWithoutDate = new SimpleDateFormat("HH:mm").format(new Date());
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.KXUploadTask
 * JD-Core Version:    0.6.0
 */