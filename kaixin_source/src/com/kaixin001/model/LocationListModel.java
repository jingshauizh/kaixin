package com.kaixin001.model;

import android.text.TextUtils;
import com.kaixin001.item.NewsInfo;
import java.util.ArrayList;

public class LocationListModel
{
  private static LocationListModel instance;
  private ArrayList<NewsInfo> mLocationList = new ArrayList();
  private int total = 0;

  public static LocationListModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new LocationListModel();
      LocationListModel localLocationListModel = instance;
      return localLocationListModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    if (this.mLocationList != null)
      this.mLocationList.clear();
    this.mLocationList = null;
    this.total = 0;
  }

  public ArrayList<NewsInfo> getLocationList()
  {
    return this.mLocationList;
  }

  public int getTotal()
  {
    return this.total;
  }

  public void setLocationList(ArrayList<NewsInfo> paramArrayList)
  {
    if (this.mLocationList == null)
      this.mLocationList = new ArrayList();
    if ((paramArrayList != null) && (paramArrayList.size() > 0))
    {
      int i = this.mLocationList.size();
      if ((i > 0) && (TextUtils.isEmpty(((NewsInfo)this.mLocationList.get(i - 1)).mRid)))
        this.mLocationList.remove(i - 1);
    }
    for (int j = 0; ; j++)
    {
      if (j >= paramArrayList.size())
        return;
      this.mLocationList.add((NewsInfo)paramArrayList.get(j));
    }
  }

  public void setTotal(int paramInt)
  {
    this.total = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.LocationListModel
 * JD-Core Version:    0.6.0
 */