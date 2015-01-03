package com.amap.mapapi.busline;

import com.amap.mapapi.core.AMapException;
import java.util.ArrayList;
import java.util.List;

public final class BusPagedResult
{
  private int a;
  private ArrayList<ArrayList<BusLineItem>> b;
  private a c;

  private BusPagedResult(a parama, ArrayList<BusLineItem> paramArrayList)
  {
    this.c = parama;
    this.a = a(parama.c());
    a(paramArrayList);
  }

  private int a(int paramInt)
  {
    int i = this.c.a();
    int j = (-1 + (paramInt + i)) / i;
    if (j > 30)
      return 30;
    return j;
  }

  static BusPagedResult a(a parama, ArrayList<BusLineItem> paramArrayList)
  {
    return new BusPagedResult(parama, paramArrayList);
  }

  private void a(ArrayList<BusLineItem> paramArrayList)
  {
    this.b = new ArrayList();
    for (int i = 0; i <= this.a; i++)
      this.b.add(null);
    if (this.a > 0)
      this.b.set(1, paramArrayList);
  }

  private boolean b(int paramInt)
  {
    return (paramInt <= this.a) && (paramInt > 0);
  }

  public List<BusLineItem> getPage(int paramInt)
    throws AMapException
  {
    ArrayList localArrayList1;
    if (this.a == 0)
      localArrayList1 = null;
    do
    {
      return localArrayList1;
      if (!b(paramInt))
        throw new IllegalArgumentException("page out of range");
      localArrayList1 = (ArrayList)getPageLocal(paramInt);
    }
    while (localArrayList1 != null);
    this.c.a(paramInt);
    ArrayList localArrayList2 = (ArrayList)this.c.g();
    this.b.set(paramInt, localArrayList2);
    return localArrayList2;
  }

  public int getPageCount()
  {
    return this.a;
  }

  public List<BusLineItem> getPageLocal(int paramInt)
  {
    if (!b(paramInt))
      throw new IllegalArgumentException("page out of range");
    return (List)this.b.get(paramInt);
  }

  public BusQuery getQuery()
  {
    return this.c.b();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.busline.BusPagedResult
 * JD-Core Version:    0.6.0
 */