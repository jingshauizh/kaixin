package com.kaixin001.item;

import android.content.ContentValues;
import android.content.Context;
import com.kaixin001.db.UpLoadTaskListDBAdapter.TaskParameters;
import com.kaixin001.engine.CircleRecordEngine;
import com.kaixin001.engine.RecordEngine;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.util.FileUtil;

public class CircleRecordUploadTask extends KXUploadTask
{
  private String gid = null;
  private String mImageFileName = null;
  private String mRecordID = null;
  private String mStatus = null;

  static
  {
    if (!CircleRecordUploadTask.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public CircleRecordUploadTask(Context paramContext)
  {
    super(paramContext);
  }

  public void deleteDraft()
  {
    if (this.mImageFileName != null)
      FileUtil.deleteCacheFile(this.mImageFileName);
  }

  public void doCancel()
  {
    RecordEngine.getInstance().cancel();
  }

  public boolean doUpload()
  {
    try
    {
      if (getTaskType() == 10)
      {
        int i = CircleRecordEngine.getInstance().postCircleRecordRequest(getContext(), this.gid, getTitle(), this.mImageFileName, this);
        if (i == 1)
        {
          this.mRecordID = CircleRecordEngine.getInstance().getRetRecordId();
          deleteDraft();
          UploadTaskListEngine.getInstance().broadcastMessage(10006, i, this);
          return true;
        }
        UploadTaskListEngine.getInstance().broadcastMessage(10007, i, this);
        return false;
      }
    }
    catch (SecurityErrorException localSecurityErrorException)
    {
    }
    return false;
  }

  public String getGid()
  {
    return this.gid;
  }

  public String getRecordId()
  {
    return this.mRecordID;
  }

  public void initCircleRecordUploadTask(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    setTaskType(10);
    this.mImageFileName = paramString2;
    this.mStatus = paramString3;
    this.gid = paramString4;
    setTitle(paramString1);
  }

  public void initUploadTask(UpLoadTaskListDBAdapter.TaskParameters paramTaskParameters)
  {
    assert (paramTaskParameters != null);
    super.initUploadTask(paramTaskParameters);
    setTitle(paramTaskParameters.mData2);
    this.mImageFileName = paramTaskParameters.mData3;
    this.gid = paramTaskParameters.mData4;
    this.mRecordID = paramTaskParameters.mData19;
  }

  public void setRecordId(String paramString)
  {
    this.mRecordID = paramString;
  }

  public ContentValues toContentValues()
  {
    ContentValues localContentValues = super.toContentValues();
    localContentValues.put("DATA1", "");
    if (getTitle() != null)
      localContentValues.put("DATA2", getTitle());
    if (this.mImageFileName != null)
      localContentValues.put("DATA3", this.mImageFileName);
    if (this.gid != null)
      localContentValues.put("DATA4", this.gid);
    localContentValues.put("DATA18", "");
    if (this.mRecordID != null)
      localContentValues.put("DATA19", this.mRecordID);
    return localContentValues;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.CircleRecordUploadTask
 * JD-Core Version:    0.6.0
 */