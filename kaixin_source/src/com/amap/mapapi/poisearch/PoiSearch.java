package com.amap.mapapi.poisearch;

import android.content.Context;
import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.c;
import com.amap.mapapi.core.e;
import com.amap.mapapi.map.MapView;
import com.amap.mapapi.map.Projection;
import java.util.ArrayList;

public class PoiSearch
{
  private SearchBound a;
  private Query b;
  private Context c;
  private int d = 20;

  public PoiSearch(Context paramContext, Query paramQuery)
  {
    com.amap.mapapi.core.b.a(paramContext);
    this.c = paramContext;
    setQuery(paramQuery);
  }

  public PoiSearch(Context paramContext, String paramString, Query paramQuery)
  {
    com.amap.mapapi.core.b.a(paramContext);
    this.c = paramContext;
    setQuery(paramQuery);
  }

  public SearchBound getBound()
  {
    return this.a;
  }

  public Query getQuery()
  {
    return this.b;
  }

  public PoiPagedResult searchPOI()
    throws AMapException
  {
    a locala = new a(new b(this.b, this.a), e.b(this.c), e.a(this.c), null);
    locala.a(1);
    locala.b(this.d);
    return PoiPagedResult.a(locala, (ArrayList)locala.g());
  }

  public void setBound(SearchBound paramSearchBound)
  {
    this.a = paramSearchBound;
  }

  public void setPageSize(int paramInt)
  {
    this.d = paramInt;
  }

  @Deprecated
  public void setPoiNumber(int paramInt)
  {
    setPageSize(paramInt);
  }

  public void setQuery(Query paramQuery)
  {
    this.b = paramQuery;
  }

  public static class Query
  {
    private String a;
    private String b;
    private String c;

    public Query(String paramString1, String paramString2)
    {
      this(paramString1, paramString2, null);
    }

    public Query(String paramString1, String paramString2, String paramString3)
    {
      this.a = paramString1;
      this.b = paramString2;
      this.c = paramString3;
      if (!b())
        throw new IllegalArgumentException("Empty  query and catagory");
    }

    private boolean b()
    {
      return (!e.a(this.a)) || (!e.a(this.b));
    }

    String a()
    {
      return "";
    }

    public String getCategory()
    {
      if ((this.b == null) || (this.b.equals("00")) || (this.b.equals("00|")))
        return a();
      return this.b;
    }

    public String getCity()
    {
      return this.c;
    }

    public String getQueryString()
    {
      return this.a;
    }
  }

  public static class SearchBound
  {
    public static final String BOUND_SHAPE = "bound";
    public static final String ELLIPSE_SHAPE = "Ellipse";
    public static final String POLYGON_SHAPE = "Polygon";
    public static final String RECTANGLE_SHAPE = "Rectangle";
    private GeoPoint a;
    private GeoPoint b;
    private int c;
    private GeoPoint d;
    private String e = "Rectangle";

    public SearchBound(GeoPoint paramGeoPoint, int paramInt)
    {
      this.c = paramInt;
      this.d = paramGeoPoint;
      a(paramGeoPoint, e.b(paramInt), e.b(paramInt));
    }

    public SearchBound(GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2)
    {
      a(paramGeoPoint1, paramGeoPoint2);
    }

    public SearchBound(MapView paramMapView)
    {
      a(paramMapView.getProjection().fromPixels(0, c.j), paramMapView.getProjection().fromPixels(c.i, 0));
    }

    private void a(GeoPoint paramGeoPoint, int paramInt1, int paramInt2)
    {
      int i = paramInt1 / 2;
      int j = paramInt2 / 2;
      long l1 = paramGeoPoint.b();
      long l2 = paramGeoPoint.a();
      a(new GeoPoint(l1 - i, l2 - j), new GeoPoint(l1 + i, l2 + j));
    }

    private void a(GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2)
    {
      this.a = paramGeoPoint1;
      this.b = paramGeoPoint2;
      if ((this.a.b() >= this.b.b()) || (this.a.a() >= this.b.a()))
        throw new IllegalArgumentException("invalid rect ");
      this.d = new GeoPoint((this.a.b() + this.b.b()) / 2L, (this.a.a() + this.b.a()) / 2L);
    }

    public GeoPoint getCenter()
    {
      return this.d;
    }

    public int getLatSpanInMeter()
    {
      return e.a(this.b.getLatitudeE6() - this.a.getLatitudeE6());
    }

    public int getLonSpanInMeter()
    {
      return e.a(this.b.getLongitudeE6() - this.a.getLongitudeE6());
    }

    public GeoPoint getLowerLeft()
    {
      return this.a;
    }

    public int getRange()
    {
      return this.c;
    }

    public String getShape()
    {
      return this.e;
    }

    public GeoPoint getUpperRight()
    {
      return this.b;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.poisearch.PoiSearch
 * JD-Core Version:    0.6.0
 */