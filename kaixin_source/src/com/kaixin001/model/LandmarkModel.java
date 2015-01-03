package com.kaixin001.model;

import android.location.Location;
import com.kaixin001.item.Landmark;
import java.util.ArrayList;

public class LandmarkModel extends KXModel
{
  private static LandmarkModel instance = null;
  private static final long serialVersionUID = 4615460538057591669L;
  private Location location;
  private ArrayList<Landmark> mLocationBuildings;
  public int selectIndex = 0;

  public static LandmarkModel getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new LandmarkModel();
      LandmarkModel localLandmarkModel = instance;
      return localLandmarkModel;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void clear()
  {
    if (this.mLocationBuildings != null)
    {
      this.mLocationBuildings.clear();
      this.mLocationBuildings = null;
      this.location = null;
      this.selectIndex = 0;
    }
  }

  public ArrayList<Landmark> getLandmarks()
  {
    return this.mLocationBuildings;
  }

  public Location getLocation()
  {
    return this.location;
  }

  public void setLocation(Location paramLocation)
  {
    this.location = paramLocation;
  }

  public void setLocationBuildings(ArrayList<Landmark> paramArrayList)
  {
    this.mLocationBuildings = paramArrayList;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.LandmarkModel
 * JD-Core Version:    0.6.0
 */