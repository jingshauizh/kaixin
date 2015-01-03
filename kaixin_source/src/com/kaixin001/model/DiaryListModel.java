package com.kaixin001.model;

import com.kaixin001.item.DiaryInfo;
import java.util.ArrayList;

public class DiaryListModel extends KXModel
{
  private static DiaryListModel instance;
  private ArrayList<DiaryInfo> m_DiaryList = new ArrayList();
  private int total = 0;

  public static DiaryListModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new DiaryListModel();
      DiaryListModel localDiaryListModel = instance;
      return localDiaryListModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    this.m_DiaryList = null;
    this.total = 0;
  }

  public ArrayList<DiaryInfo> getDiaryList()
  {
    return this.m_DiaryList;
  }

  public int getTotal()
  {
    return this.total;
  }

  public void setDiaryList(ArrayList<DiaryInfo> paramArrayList)
  {
    if (this.m_DiaryList == null)
      this.m_DiaryList = new ArrayList();
    if (paramArrayList != null)
      this.m_DiaryList.addAll(paramArrayList);
  }

  public void setTotal(int paramInt)
  {
    this.total = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.DiaryListModel
 * JD-Core Version:    0.6.0
 */