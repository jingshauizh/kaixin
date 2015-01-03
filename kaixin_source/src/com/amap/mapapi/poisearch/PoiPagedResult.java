package com.amap.mapapi.poisearch;

import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.core.PoiItem;
import java.util.ArrayList;
import java.util.List;

public final class PoiPagedResult
{
  private int a;
  private ArrayList<ArrayList<PoiItem>> b;
  private a c;

  private PoiPagedResult(a parama, ArrayList<PoiItem> paramArrayList)
  {
    this.c = parama;
    this.a = a(parama.b());
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

  static PoiPagedResult a(a parama, ArrayList<PoiItem> paramArrayList)
  {
    return new PoiPagedResult(parama, paramArrayList);
  }

  private void a(ArrayList<PoiItem> paramArrayList)
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

  public PoiSearch.SearchBound getBound()
  {
    return this.c.i();
  }

  public List<PoiItem> getPage(int paramInt)
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

  public List<PoiItem> getPageLocal(int paramInt)
  {
    if (!b(paramInt))
      throw new IllegalArgumentException("page out of range");
    return (List)this.b.get(paramInt);
  }

  public PoiSearch.Query getQuery()
  {
    return this.c.c();
  }

  public List<String> getSearchSuggestions()
  {
    return this.c.j();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.poisearch.PoiPagedResult
 * JD-Core Version:    0.6.0
 */