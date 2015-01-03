package com.amap.mapapi.map;

import android.content.Context;
import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.t;
import com.mapabc.minimap.map.vmap.VMapProjection;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

class bf extends c<av.a, av.a>
  implements bh
{
  private u g = new u();

  public bf(ai paramai, Context paramContext)
  {
    super(paramai, paramContext);
    this.c = new aw();
    paramai.b.a(this);
  }

  private ArrayList<av.a> a(ArrayList<av.a> paramArrayList, w paramw, int paramInt, boolean paramBoolean)
  {
    if ((paramArrayList == null) || (paramw == null));
    int i;
    do
    {
      do
      {
        do
          return null;
        while ((!paramw.f) || (paramw.o == null));
        paramw.o.clear();
      }
      while ((paramInt > paramw.b) || (paramInt < paramw.c));
      i = paramArrayList.size();
    }
    while (i <= 0);
    ArrayList localArrayList = new ArrayList();
    int j = 0;
    if (j < i)
    {
      av.a locala1 = (av.a)paramArrayList.get(j);
      if (locala1 == null);
      while (true)
      {
        j++;
        break;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(locala1.b);
        localStringBuilder.append("-");
        localStringBuilder.append(locala1.c);
        localStringBuilder.append("-");
        localStringBuilder.append(locala1.d);
        int k = paramw.m.a(localStringBuilder.toString());
        av.a locala2 = new av.a(locala1.b, locala1.c, locala1.d, paramw.k);
        locala2.g = k;
        locala2.f = locala1.f;
        paramw.o.add(locala2);
        if ((!a(locala2)) || (paramBoolean) || (this.g.contains(locala2)))
          continue;
        if (!paramw.g)
          locala2.a = -1;
        localArrayList.add(locala2);
      }
    }
    return localArrayList;
  }

  private void a(ArrayList<av.a> paramArrayList, boolean paramBoolean)
  {
    if ((this.c == null) || (paramArrayList == null));
    do
      return;
    while (paramArrayList.size() == 0);
    this.c.a(paramArrayList, paramBoolean);
  }

  private boolean a(av.a parama)
  {
    return (parama == null) || (parama.g < 0);
  }

  private void b(ArrayList<av.a> paramArrayList)
  {
    if ((paramArrayList == null) || (this.g == null));
    while (true)
    {
      return;
      int i = paramArrayList.size();
      if (i == 0)
        continue;
      for (int j = 0; j < i; j++)
        this.g.a((av.a)paramArrayList.get(j));
    }
  }

  protected ArrayList<av.a> a(ArrayList<av.a> paramArrayList)
  {
    if (paramArrayList == null)
    {
      localObject1 = null;
      return localObject1;
    }
    int i = paramArrayList.size();
    if (i == 0)
      return null;
    int j = 0;
    Object localObject1 = null;
    label26: av.a locala1;
    int n;
    int i1;
    if (j < i)
    {
      locala1 = (av.a)paramArrayList.get(j);
      if (locala1 != null)
        break label75;
      n = j;
      localObject2 = localObject1;
      i1 = i;
    }
    while (true)
    {
      int i2 = n + 1;
      localObject1 = localObject2;
      i = i1;
      j = i2;
      break label26;
      break;
      label75: if ((this.e == null) || (this.e.d == null) || (this.e.d.a == null))
        return null;
      int k = this.e.d.a.size();
      if (locala1.e >= k)
      {
        n = j;
        localObject2 = localObject1;
        i1 = i;
        continue;
      }
      if (!((w)this.e.d.a.get(locala1.e)).g)
      {
        n = j;
        localObject2 = localObject1;
        i1 = i;
        continue;
      }
      int m = ((w)this.e.d.a.get(locala1.e)).n.a(locala1);
      if (m < 0)
        break label365;
      paramArrayList.remove(j);
      int i3 = i - 1;
      int i4 = j - 1;
      t localt = ((w)this.e.d.a.get(locala1.e)).o;
      if (localt == null)
      {
        n = i4;
        localObject2 = localObject1;
        i1 = i3;
        continue;
      }
      int i5 = localt.size();
      int i6 = 0;
      if (i6 < i5)
      {
        av.a locala3 = (av.a)localt.get(i6);
        if (locala3 == null);
        do
        {
          i6++;
          break;
        }
        while (!locala3.equals(locala1));
        locala3.g = m;
        this.e.d.d();
      }
      n = i4;
      localObject2 = localObject1;
      i1 = i3;
    }
    label365: if (localObject1 == null);
    for (Object localObject2 = new ArrayList(); ; localObject2 = localObject1)
    {
      av.a locala2 = new av.a(locala1);
      locala2.a = -1;
      ((ArrayList)localObject2).add(locala2);
      n = j;
      i1 = i;
      break;
    }
  }

  protected ArrayList<av.a> a(ArrayList<av.a> paramArrayList, Proxy paramProxy)
    throws AMapException
  {
    if ((paramArrayList == null) || (paramArrayList.size() == 0));
    do
    {
      int i;
      do
      {
        do
          return null;
        while ((this.e == null) || (this.e.d == null) || (this.e.d.a == null));
        i = this.e.d.a.size();
      }
      while (((av.a)paramArrayList.get(0)).e >= i);
      a(paramArrayList);
    }
    while ((paramArrayList.size() == 0) || (this.e.d.a.size() == 0));
    int j = ((av.a)paramArrayList.get(0)).e;
    ArrayList localArrayList;
    if (((w)this.e.d.a.get(j)).j != null)
    {
      az localaz = new az(paramArrayList, paramProxy, this.e.e.a(), this.e.e.b());
      localaz.a((w)this.e.d.a.get(((av.a)paramArrayList.get(0)).e));
      localArrayList = (ArrayList)localaz.g();
      localaz.a(null);
    }
    while (true)
    {
      b(paramArrayList);
      if ((this.e == null) || (this.e.d == null))
        return localArrayList;
      this.e.d.d();
      return localArrayList;
      localArrayList = null;
    }
  }

  public void a()
  {
    super.a();
    this.g.clear();
  }

  public void a(List<av.a> paramList)
  {
    if (paramList == null);
    int i;
    do
    {
      return;
      i = paramList.size();
    }
    while (i == 0);
    int j = 0;
    label18: int k;
    if (j < i)
    {
      if (this.g.b((av.a)paramList.get(j)))
        break label76;
      paramList.remove(j);
      k = j - 1;
    }
    for (int m = i - 1; ; m = i)
    {
      int n = k + 1;
      i = m;
      j = n;
      break label18;
      break;
      label76: k = j;
    }
  }

  public void a(boolean paramBoolean1, boolean paramBoolean2)
  {
    ArrayList localArrayList1;
    if (this.e != null)
    {
      GeoPoint localGeoPoint = this.e.b.f();
      be localbe = VMapProjection.LatLongToPixels(localGeoPoint.getLatitudeE6() / 1000000.0D, localGeoPoint.getLongitudeE6() / 1000000.0D, 20);
      this.e.b.g().centerX = localbe.a;
      this.e.b.g().centerY = localbe.b;
      this.e.b.g().mapLevel = this.e.b.e();
      this.e.g.g = this.e.b.e();
      localArrayList1 = this.e.g.a(this.e.g.j, this.e.g.g, this.e.b.c(), this.e.b.d());
      if (localArrayList1 != null)
        break label180;
    }
    label180: 
    do
      return;
    while (localArrayList1.size() <= 0);
    if ((this.e.d != null) && (this.e.d.a != null));
    for (int i = this.e.d.a.size(); ; i = 0)
    {
      int j = 0;
      boolean bool1 = true;
      ArrayList localArrayList2;
      if (j < i)
      {
        w localw = (w)this.e.d.a.get(j);
        if ((localw.a.equalsIgnoreCase("GridTmc")) && (localw.f))
        {
          localArrayList2 = a(localArrayList1, localw, this.e.b.e(), paramBoolean2);
          if (localArrayList2 == null)
            break label358;
          a(localArrayList2, bool1);
          if (bool1 != true)
            break label365;
        }
      }
      label358: label365: for (boolean bool2 = false; ; bool2 = bool1)
      {
        localArrayList2.clear();
        while (true)
        {
          bool1 = bool2;
          j++;
          break;
          localArrayList1.clear();
          this.e.b.g().invalidate();
          return;
          bool2 = bool1;
        }
      }
    }
  }

  public void a_()
  {
  }

  public void c()
  {
  }

  protected int f()
  {
    return 4;
  }

  protected int g()
  {
    return 1;
  }

  public void h()
  {
    a(false, false);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.bf
 * JD-Core Version:    0.6.0
 */