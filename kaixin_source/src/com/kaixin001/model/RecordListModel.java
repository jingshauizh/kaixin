package com.kaixin001.model;

import android.text.TextUtils;
import com.kaixin001.item.NewsInfo;
import java.util.ArrayList;

public class RecordListModel extends KXModel
{
  private static RecordListModel instance;
  private ArrayList<NewsInfo> mRecordList = new ArrayList();
  private int total = 0;

  public static RecordListModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new RecordListModel();
      RecordListModel localRecordListModel = instance;
      return localRecordListModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    if (this.mRecordList != null)
      this.mRecordList.clear();
    this.mRecordList = null;
    this.total = 0;
  }

  public ArrayList<NewsInfo> getRecordList()
  {
    return this.mRecordList;
  }

  public int getTotal()
  {
    return this.total;
  }

  public void setRecordList(ArrayList<NewsInfo> paramArrayList)
  {
    if (this.mRecordList == null)
      this.mRecordList = new ArrayList();
    if ((paramArrayList != null) && (paramArrayList.size() > 0))
    {
      int i = this.mRecordList.size();
      if ((i > 0) && (TextUtils.isEmpty(((NewsInfo)this.mRecordList.get(i - 1)).mRid)))
        this.mRecordList.remove(i - 1);
    }
    for (int j = 0; ; j++)
    {
      if (j >= paramArrayList.size())
        return;
      this.mRecordList.add((NewsInfo)paramArrayList.get(j));
    }
  }

  public void setTotal(int paramInt)
  {
    this.total = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.RecordListModel
 * JD-Core Version:    0.6.0
 */