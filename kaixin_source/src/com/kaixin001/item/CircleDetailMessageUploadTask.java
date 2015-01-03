package com.kaixin001.item;

import android.content.ContentValues;
import android.content.Context;
import android.os.Message;
import com.kaixin001.activity.MessageHandlerHolder;
import com.kaixin001.engine.CircleReplyNewsEngine;
import com.kaixin001.util.KXLog;

public class CircleDetailMessageUploadTask extends KXUploadTask
{
  private static final int NO_PERSSION = -3002;
  private static final String TAG = "CircleDetailMessageUploadTask";
  private int mnMode = -1;
  private String msContent = "";
  private String msGid = "";
  private String msTid = "";

  public CircleDetailMessageUploadTask(Context paramContext)
  {
    super(paramContext);
  }

  public void deleteDraft()
  {
  }

  public void doCancel()
  {
    CircleReplyNewsEngine.getInstance().cancel();
  }

  public boolean doUpload()
  {
    int i = 0;
    try
    {
      int j = this.mnMode;
      i = 0;
      int k;
      if (j == 10)
      {
        k = CircleReplyNewsEngine.getInstance().doPostCircleNewsReply(getContext(), this.msGid, this.msTid, this.msContent);
        if (k != 1)
          break label78;
      }
      label78: for (i = 1; ; i = 0)
      {
        if (k == -3002)
        {
          Message localMessage = Message.obtain();
          localMessage.what = -3002;
          MessageHandlerHolder.getInstance().fireMessage(localMessage);
        }
        return i;
      }
    }
    catch (Exception localException)
    {
      KXLog.e("CircleDetailMessageUploadTask", localException.toString());
    }
    return i;
  }

  public String getContent()
  {
    return this.msContent;
  }

  public String getGid()
  {
    return this.msGid;
  }

  public int getMode()
  {
    return this.mnMode;
  }

  public String getTid()
  {
    return this.msTid;
  }

  public void initCircleMessageDetailTask(String paramString1, String paramString2, String paramString3)
  {
    setTaskType(5);
    this.msGid = paramString2;
    this.msContent = paramString1;
    this.msTid = paramString3;
  }

  public void setContent(String paramString)
  {
    this.msContent = paramString;
  }

  public void setGid(String paramString)
  {
    this.msGid = paramString;
  }

  public void setMode(int paramInt)
  {
    this.mnMode = paramInt;
  }

  public void setTid(String paramString)
  {
    this.msTid = paramString;
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
      if (this.msGid != null)
        localContentValues.put("DATA4", this.msGid);
      if (this.mnMode == -1)
        continue;
      localContentValues.put("DATA21", Integer.toString(this.mnMode));
    }
    while (this.msTid == null);
    localContentValues.put("DATA19", this.msTid);
    return localContentValues;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.CircleDetailMessageUploadTask
 * JD-Core Version:    0.6.0
 */