package com.amap.mapapi.busline;

import android.content.Context;
import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.core.b;
import com.amap.mapapi.core.e;
import java.util.ArrayList;

public class BusSearch
{
  private Context a;
  private BusQuery b;
  private int c = 10;

  public BusSearch(Context paramContext, BusQuery paramBusQuery)
  {
    b.a(paramContext);
    this.a = paramContext;
    this.b = paramBusQuery;
  }

  public BusSearch(Context paramContext, String paramString, BusQuery paramBusQuery)
  {
    b.a(paramContext);
    this.a = paramContext;
    this.b = paramBusQuery;
  }

  public BusQuery getQuery()
  {
    return this.b;
  }

  public BusPagedResult searchBusLine()
    throws AMapException
  {
    a locala = new a(this.b, e.b(this.a), e.a(this.a), null);
    locala.a(1);
    locala.b(this.c);
    return BusPagedResult.a(locala, (ArrayList)locala.g());
  }

  public void setPageSize(int paramInt)
  {
    this.c = paramInt;
  }

  public void setQuery(BusQuery paramBusQuery)
  {
    this.b = paramBusQuery;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.busline.BusSearch
 * JD-Core Version:    0.6.0
 */