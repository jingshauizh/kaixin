package com.kaixin001.model;

import android.text.TextUtils;
import com.kaixin001.item.AtListItem;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;

public class AtListModel
{
  private static final int BAIJIAXING_JIANPIN_TYPE = 6;
  private static final int BAIJIAXING_QUANPIN_TYPE = 7;
  private static final int BEIZHU_TYPE = 5;
  private static final int DEFAULT_AT_NUM = 10;
  private static final int JIANPIN_TYPE = 1;
  private static final int QUANPIN_TYPE = 2;
  private static final int REALNAME_TYPE = 3;
  private static final int UNAME_TYPE = 4;
  private static AtListModel mInstance;
  private ArrayList<AtListItem> atList = new ArrayList();
  private int total = 0;

  public static AtListModel getInstance()
  {
    monitorenter;
    try
    {
      if (mInstance == null)
        mInstance = new AtListModel();
      AtListModel localAtListModel = mInstance;
      return localAtListModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private boolean searchValue(String paramString, ArrayList<String> paramArrayList)
  {
    int i = paramArrayList.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return false;
      if (((String)paramArrayList.get(j)).indexOf(paramString) == 0)
        return true;
    }
  }

  public void clear()
  {
    if (this.atList != null)
    {
      this.atList.clear();
      this.atList = null;
    }
  }

  public ArrayList<AtListItem> getAtList()
  {
    return this.atList;
  }

  public ArrayList<AtListItem> getSearchList(String paramString)
  {
    ArrayList localArrayList2;
    if (TextUtils.isEmpty(paramString))
    {
      localArrayList2 = null;
      return localArrayList2;
    }
    ArrayList localArrayList1 = new ArrayList();
    int i = this.atList.size();
    int j = 0;
    int k = 0;
    label41: int m;
    int n;
    if (k >= i)
    {
      KXLog.d("total = ", j);
      localArrayList2 = new ArrayList();
      m = localArrayList1.size();
      n = 1;
      label79: if (n > 7);
    }
    for (int i1 = 0; ; i1++)
    {
      if (i1 >= j)
      {
        n++;
        break label79;
        break;
        if (j == 10)
          break label41;
        if (searchValue(paramString, ((AtListItem)this.atList.get(k)).getPinyinCapital()))
        {
          ((AtListItem)this.atList.get(k)).setType(1);
          localArrayList1.add((AtListItem)this.atList.get(k));
          j++;
        }
        while (true)
        {
          k++;
          break;
          if (searchValue(paramString, ((AtListItem)this.atList.get(k)).getShortCapital()))
          {
            ((AtListItem)this.atList.get(k)).setType(6);
            localArrayList1.add((AtListItem)this.atList.get(k));
            j++;
            continue;
          }
          if (searchValue(paramString, ((AtListItem)this.atList.get(k)).getPinyinFull()))
          {
            ((AtListItem)this.atList.get(k)).setType(2);
            localArrayList1.add((AtListItem)this.atList.get(k));
            j++;
            continue;
          }
          if (((AtListItem)this.atList.get(k)).getRealName().indexOf(paramString) == 0)
          {
            ((AtListItem)this.atList.get(k)).setType(3);
            localArrayList1.add((AtListItem)this.atList.get(k));
            j++;
            continue;
          }
          if (((AtListItem)this.atList.get(k)).getUname().indexOf(paramString) == 0)
          {
            ((AtListItem)this.atList.get(k)).setType(4);
            localArrayList1.add((AtListItem)this.atList.get(k));
            j++;
            continue;
          }
          if (!searchValue(paramString, ((AtListItem)this.atList.get(k)).getShortFull()))
            continue;
          ((AtListItem)this.atList.get(k)).setType(7);
          localArrayList1.add((AtListItem)this.atList.get(k));
          j++;
        }
      }
      if ((i1 >= m) || (localArrayList1.get(i1) == null) || (((AtListItem)localArrayList1.get(i1)).getType() != n))
        continue;
      localArrayList2.add((AtListItem)localArrayList1.get(i1));
    }
  }

  public int getTotal()
  {
    return this.total;
  }

  public int isInAtList(String paramString)
  {
    int i = this.atList.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        j = -1;
      do
        return j;
      while (((AtListItem)this.atList.get(j)).getUname().equals(paramString));
    }
  }

  public void setAtList(ArrayList<AtListItem> paramArrayList)
  {
    this.atList = paramArrayList;
  }

  public void setTotal(int paramInt)
  {
    this.total = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.AtListModel
 * JD-Core Version:    0.6.0
 */