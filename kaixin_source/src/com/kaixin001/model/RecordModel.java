package com.kaixin001.model;

import com.kaixin001.item.RecordInfo;

public class RecordModel extends KXModel
{
  private static RecordModel instance = null;
  private int mErrorNum = 0;
  private RecordInfo mRecordInfo = null;

  public static RecordModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new RecordModel();
      RecordModel localRecordModel = instance;
      return localRecordModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.mRecordInfo = null;
  }

  public int getErrorNum()
  {
    return this.mErrorNum;
  }

  public RecordInfo getRecordInfo()
  {
    return this.mRecordInfo;
  }

  public void setErrorNum(int paramInt)
  {
    this.mErrorNum = paramInt;
  }

  public void setRecordInfo(RecordInfo paramRecordInfo)
  {
    this.mRecordInfo = paramRecordInfo;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.RecordModel
 * JD-Core Version:    0.6.0
 */