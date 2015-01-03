package com.kaixin001.item;

import android.content.ContentValues;
import android.content.Context;
import com.kaixin001.db.UpLoadTaskListDBAdapter.TaskParameters;
import com.kaixin001.engine.DiaryEngine;
import com.kaixin001.engine.MessageEngine;
import com.kaixin001.engine.ReplyEngine;
import com.kaixin001.util.KXLog;

public class MessageUploadTask extends KXUploadTask
{
  private static final String TAG = "MessageUploadTask";
  private String mUploadThreadId = "";
  private int mnMode = -1;
  private String msContent = "";
  private String msMainThreadFuid = "";
  private String msThreadId = "";

  static
  {
    if (!MessageUploadTask.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public MessageUploadTask(Context paramContext, int paramInt)
  {
    super(paramContext);
    assert ((paramInt == 4) || (5 == paramInt));
    setTaskType(paramInt);
  }

  public static String getWhereClause(String paramString, int paramInt)
  {
    return new StringBuilder("DATA3=").append(paramString).toString() + "andDATA21=" + Integer.toString(paramInt);
  }

  public void deleteDraft()
  {
  }

  public void doCancel()
  {
    DiaryEngine.getInstance().cancel();
  }

  public boolean doUpload()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      if (this.mnMode == 3)
      {
        int k = ReplyEngine.getInstance().postReply(getContext(), this.msThreadId, this.msMainThreadFuid, this.msContent, localStringBuilder);
        if (k == 1)
        {
          this.mUploadThreadId = localStringBuilder.toString();
          return true;
        }
        this.mLastError = k;
        return false;
      }
    }
    catch (Exception localException)
    {
      KXLog.e("MessageUploadtask", localException.toString());
      return false;
    }
    int i = this.mnMode;
    int j = 0;
    if (i == 5)
    {
      boolean bool = MessageEngine.getInstance().doReplyMessage(getContext(), this.msThreadId, this.msContent, localStringBuilder);
      j = 0;
      if (bool)
      {
        this.mUploadThreadId = localStringBuilder.toString();
        j = 1;
      }
    }
    return j;
  }

  public String getContent()
  {
    return this.msContent;
  }

  public String getMainThreadFuid()
  {
    return this.msMainThreadFuid;
  }

  public int getMode()
  {
    return this.mnMode;
  }

  public String getThreadId()
  {
    return this.msThreadId;
  }

  public String getUploadThreadId()
  {
    return this.mUploadThreadId;
  }

  public void initUploadTask(UpLoadTaskListDBAdapter.TaskParameters paramTaskParameters)
  {
    setTaskType(5);
    super.initUploadTask(paramTaskParameters);
    if (paramTaskParameters == null)
      return;
    setTitle(paramTaskParameters.mData1);
    this.msContent = paramTaskParameters.mData2;
    this.msThreadId = paramTaskParameters.mData3;
    this.msMainThreadFuid = paramTaskParameters.mData20;
    try
    {
      this.mnMode = Integer.parseInt(paramTaskParameters.mData21);
      this.mUploadThreadId = paramTaskParameters.mData19;
      return;
    }
    catch (Exception localException)
    {
      while (true)
        KXLog.e("MessageUploadTask", localException.toString());
    }
  }

  public void setContent(String paramString)
  {
    this.msContent = paramString;
  }

  public void setMainThreadFuid(String paramString)
  {
    this.msMainThreadFuid = paramString;
  }

  public void setThreadId(String paramString)
  {
    this.msThreadId = paramString;
  }

  public void setnMode(int paramInt)
  {
    this.mnMode = paramInt;
  }

  public ContentValues toContentValues()
  {
    ContentValues localContentValues = super.toContentValues();
    if (localContentValues == null);
    do
    {
      return localContentValues;
      if (getTitle() != null)
        localContentValues.put("DATA1", getTitle());
      if (this.msContent != null)
        localContentValues.put("DATA2", this.msContent);
      if (this.msThreadId != null)
        localContentValues.put("DATA3", this.msThreadId);
      if (this.mUploadThreadId != null)
        localContentValues.put("DATA19", this.mUploadThreadId);
      if (this.msMainThreadFuid == null)
        continue;
      localContentValues.put("DATA20", this.msMainThreadFuid);
    }
    while (this.mnMode == -1);
    localContentValues.put("DATA21", Integer.toString(this.mnMode));
    return localContentValues;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.MessageUploadTask
 * JD-Core Version:    0.6.0
 */