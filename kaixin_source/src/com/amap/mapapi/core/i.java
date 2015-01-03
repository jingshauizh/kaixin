package com.amap.mapapi.core;

import com.autonavi.util.Shifting;
import java.io.InputStream;
import java.net.Proxy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class i extends m<GeoPoint.b, GeoPoint.b>
{
  private GeoPoint.b i;

  public i(GeoPoint.b paramb, Proxy paramProxy, String paramString1, String paramString2)
  {
    super(paramb, paramProxy, paramString1, paramString2);
    this.i = paramb;
  }

  public GeoPoint.b a()
  {
    double[] arrayOfDouble = Shifting.table_offset(this.i.a, this.i.b);
    return new GeoPoint.b(arrayOfDouble[0], arrayOfDouble[1]);
  }

  protected GeoPoint.b a(InputStream paramInputStream)
    throws AMapException
  {
    GeoPoint.b localb = this.i;
    try
    {
      str = new String(com.amap.mapapi.map.i.a(paramInputStream));
    }
    catch (Exception localException)
    {
      try
      {
        while (true)
        {
          JSONArray localJSONArray = new JSONObject(str).getJSONArray("list");
          for (int j = 0; j < localJSONArray.length(); j++)
          {
            JSONObject localJSONObject = localJSONArray.getJSONObject(j);
            localb.a = localJSONObject.getDouble("x");
            localb.b = localJSONObject.getDouble("y");
          }
          localException = localException;
          localException.printStackTrace();
          String str = null;
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
    return localb;
  }

  protected byte[] d()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("config=RGC&resType=json&flag=true&enc=utf-8&coors=");
    localStringBuilder.append(((GeoPoint.b)this.b).a);
    localStringBuilder.append(",");
    localStringBuilder.append(((GeoPoint.b)this.b).b);
    return localStringBuilder.toString().getBytes();
  }

  protected String e()
  {
    return j.a().d();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.i
 * JD-Core Version:    0.6.0
 */