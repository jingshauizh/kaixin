package com.kaixin001.engine;

import android.content.Context;
import android.location.Location;
import com.kaixin001.item.Landmark;
import com.kaixin001.lbs.LocationService;
import com.kaixin001.model.LandmarkModel;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationEngine extends KXEngine
{
  private static final String LOGTAG = "LocationEngine";
  private static LocationEngine instance = null;

  public static LocationEngine getInstance()
  {
    monitorenter;
    try
    {
      if (instance == null)
        instance = new LocationEngine();
      LocationEngine localLocationEngine = instance;
      return localLocationEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int doGetLandmarks(Context paramContext, Location paramLocation1, Location paramLocation2)
    throws SecurityErrorException
  {
    String str1;
    String str2;
    String str3;
    String str4;
    LandmarkModel localLandmarkModel;
    String str5;
    HttpProxy localHttpProxy;
    if (LocationService.isMapABCLocation(paramLocation1))
    {
      str1 = "0";
      str2 = "0";
      str3 = String.valueOf(paramLocation1.getLongitude());
      str4 = String.valueOf(paramLocation1.getLatitude());
      localLandmarkModel = LandmarkModel.getInstance();
      str5 = Protocol.getInstance().makeGetLocationBuildingRequest(str1, str2, str3, str4, User.getInstance().getUID());
      localHttpProxy = new HttpProxy(paramContext);
    }
    while (true)
    {
      JSONObject localJSONObject1;
      try
      {
        String str6 = localHttpProxy.httpGet(str5, null, null);
        localJSONObject1 = super.parseJSON(paramContext, str6);
        if (localJSONObject1 == null)
        {
          this.ret = 0;
          if (this.ret == 1)
            continue;
          localLandmarkModel.clear();
          return this.ret;
          str1 = String.valueOf(paramLocation1.getLongitude());
          str2 = String.valueOf(paramLocation1.getLatitude());
          if (paramLocation2 != null)
            continue;
          str3 = null;
          if (paramLocation2 != null)
            continue;
          str4 = null;
          break;
          str3 = String.valueOf(paramLocation2.getLongitude());
          continue;
          str4 = String.valueOf(paramLocation2.getLatitude());
          continue;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("LocationEngine", "doGetLocationBuilding error", localException);
        localLandmarkModel.clear();
        return 0;
      }
      if (this.ret == 1)
        try
        {
          JSONArray localJSONArray = localJSONObject1.optJSONArray("blist");
          int i;
          label220: ArrayList localArrayList;
          if (localJSONArray == null)
          {
            i = 0;
            localArrayList = new ArrayList(i);
          }
          for (int j = 0; ; j++)
          {
            if (j >= i)
            {
              localLandmarkModel.setLocationBuildings(localArrayList);
              localLandmarkModel.setLocation(paramLocation1);
              this.ret = 1;
              break;
              i = localJSONArray.length();
              break label220;
            }
            JSONObject localJSONObject2 = localJSONArray.getJSONObject(j);
            Landmark localLandmark = new Landmark();
            localLandmark.name = localJSONObject2.optString("name");
            localLandmark.address = localJSONObject2.optString("address");
            localArrayList.add(localLandmark);
          }
        }
        catch (JSONException localJSONException)
        {
          while (true)
          {
            this.ret = 0;
            localJSONException.printStackTrace();
          }
        }
      this.ret = 0;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.LocationEngine
 * JD-Core Version:    0.6.0
 */