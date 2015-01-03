package com.amap.mapapi.route;

import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.e;
import com.amap.mapapi.map.i;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class c extends d
{
  List<GeoPoint> i;

  public c(f paramf, Proxy paramProxy, String paramString1, String paramString2)
  {
    super(paramf, paramProxy, paramString1, paramString2);
  }

  protected ArrayList<Route> a(InputStream paramInputStream)
    throws AMapException
  {
    int j = 0;
    ArrayList localArrayList = new ArrayList();
    try
    {
      str1 = new String(i.a(paramInputStream));
      e.c(str1);
    }
    catch (Exception localException2)
    {
      try
      {
        while (true)
        {
          String str1;
          JSONObject localJSONObject1 = new JSONObject(str1);
          if (localJSONObject1.getInt("count") > 0)
          {
            JSONArray localJSONArray = localJSONObject1.getJSONArray("list");
            localRoute = new Route(((f)this.b).b);
            localLinkedList = new LinkedList();
            label86: if (j >= localJSONArray.length())
              break label401;
            JSONObject localJSONObject2 = localJSONArray.getJSONObject(j);
            DriveWalkSegment localDriveWalkSegment = new DriveWalkSegment();
            String str2 = localJSONObject2.getString("roadLength");
            if (str2.contains("千米"))
            {
              String str4 = str2.substring(0, -2 + str2.length());
              str2 = (int)(1000.0D * Double.parseDouble(str4)) + "";
              label177: localDriveWalkSegment.setLength(Integer.parseInt(str2));
              localDriveWalkSegment.setRoadName(localJSONObject2.getString("roadName"));
              localDriveWalkSegment.setActionDescription(localJSONObject2.getString("action"));
            }
            try
            {
              localDriveWalkSegment.setShapes(a(localJSONObject2.getString("coor").split(",|;")));
              localDriveWalkSegment.setConsumeTime(localJSONObject2.getString("driveTime"));
              if (localDriveWalkSegment.getShapes().length != 0)
                localLinkedList.add(localDriveWalkSegment);
              j++;
              break label86;
              localException2 = localException2;
              localException2.printStackTrace();
              str1 = null;
              continue;
              if (str2.contains("米"))
              {
                str2 = str2.substring(0, -1 + str2.length());
                break label177;
              }
              if (!str2.contains("公里"))
                break label177;
              String str3 = str2.substring(0, -2 + str2.length());
              str2 = (int)(1000.0D * Double.parseDouble(str3)) + "";
            }
            catch (Exception localException1)
            {
              while (true)
                localException1.printStackTrace();
            }
          }
        }
      }
      catch (JSONException localJSONException)
      {
        Route localRoute;
        LinkedList localLinkedList;
        localJSONException.printStackTrace();
        while (localArrayList.size() == 0)
        {
          throw new AMapException("IO 操作异常 - IOException");
          label401: if (localLinkedList.size() == 0)
            return null;
          localRoute.a(localLinkedList);
          a(localRoute);
          Iterator localIterator = localRoute.a().iterator();
          while (localIterator.hasNext())
            ((Segment)localIterator.next()).setRoute(localRoute);
          if (localRoute != null)
          {
            localRoute.setStartPlace(this.j);
            localRoute.setTargetPlace(this.k);
          }
          localArrayList.add(localRoute);
        }
        if (paramInputStream != null);
        try
        {
          paramInputStream.close();
          return localArrayList;
        }
        catch (IOException localIOException)
        {
        }
      }
    }
    throw new AMapException("IO 操作异常 - IOException");
  }

  public void a(List<GeoPoint> paramList)
  {
    this.i = paramList;
  }

  protected byte[] d()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("sid=8000");
    localStringBuilder.append("&encode=utf-8");
    localStringBuilder.append("&xys=" + ((f)this.b).a() + "," + ((f)this.b).c() + ";");
    if ((this.i != null) && (this.i.size() > 0))
    {
      int j = this.i.size();
      String str = "";
      for (int k = 0; k < j; k++)
      {
        GeoPoint localGeoPoint = (GeoPoint)this.i.get(k);
        str = str + localGeoPoint.getLongitudeE6() / 1000000.0D + "," + localGeoPoint.getLatitudeE6() / 1000000.0D + ";";
      }
      localStringBuilder.append(str);
    }
    localStringBuilder.append(((f)this.b).b() + "," + ((f)this.b).d());
    localStringBuilder.append("&resType=json");
    localStringBuilder.append("&RouteType=" + ((f)this.b).b);
    localStringBuilder.append("&per=");
    localStringBuilder.append(50);
    return localStringBuilder.toString().getBytes();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.route.c
 * JD-Core Version:    0.6.0
 */