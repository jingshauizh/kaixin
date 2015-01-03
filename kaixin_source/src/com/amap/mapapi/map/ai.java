package com.amap.mapapi.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.GeoPoint.EnumMapProjection;
import com.amap.mapapi.core.c;
import com.amap.mapapi.core.c.a;
import com.amap.mapapi.core.e;
import com.amap.mapapi.core.n;
import com.amap.mapapi.core.r;
import com.amap.mapapi.core.t;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

class ai
{
  public e a;
  public d b;
  public b c;
  public a d;
  public c e;
  public bi f;
  public ad g = null;

  public ai(MapActivity paramMapActivity, MapView paramMapView, String paramString)
  {
    c.h = GeoPoint.EnumMapProjection.projection_900913;
    this.b = new d(paramMapView);
    this.g = new ad(this.b);
    if (paramMapView.VMapMode)
    {
      this.b.b(c.c);
      this.b.c(c.d);
      this.b.a(this.b.e());
    }
    this.g.a();
    if (c.g == null)
    {
      c.g = new n(paramMapActivity.getApplicationContext());
      c.g.b();
    }
    this.e = new c(this, paramMapActivity, paramString);
    this.d = new a(paramMapActivity);
    this.a = new e();
    this.c = new b();
    a(this);
    this.b.a(false, false);
  }

  private void a(ai paramai)
  {
    this.f = new bi(paramai, c.g.a(c.a.b.ordinal()));
    this.d.a(this.f, true);
  }

  public void a()
  {
    this.d.a();
    this.d.b();
    if (c.f == 1)
    {
      r.a();
      c.g.a();
      c.g = null;
    }
    this.a = null;
    this.b = null;
    this.c = null;
    this.d = null;
    this.e = null;
  }

  public void a(boolean paramBoolean)
  {
    this.d.b(paramBoolean);
  }

  public void b(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      Bitmap localBitmap2 = c.g.a(c.a.c.ordinal());
      this.f.a(localBitmap2);
      return;
    }
    Bitmap localBitmap1 = c.g.a(c.a.b.ordinal());
    this.f.a(localBitmap1);
  }

  public class a
  {
    public t<w> a = null;
    public t<Overlay> b = new t();
    long c;
    f d;
    public boolean e = false;
    public boolean f = false;
    String g = "GridMapV1";
    String h = "SatelliteMap";
    String i = "GridTmc";
    String j = "SateliteTmc";
    private boolean l = false;
    private boolean m = true;
    private com.amap.mapapi.core.h<p> n = new com.amap.mapapi.core.h();
    private MapView.ReticleDrawMode o = MapView.ReticleDrawMode.DRAW_RETICLE_NEVER;
    private int p = 0;
    private int q = 0;
    private boolean r = false;

    public a(MapActivity arg2)
    {
      Context localContext;
      if (localContext == null)
        return;
      f();
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      localContext.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
      int i1 = 2 + localDisplayMetrics.widthPixels / 256;
      int i2 = 2 + localDisplayMetrics.heightPixels / 256;
      this.p = (i2 + (i1 + i1 * i2));
      this.q = (1 + this.p / 8);
      if (this.q == 0)
        this.q = 1;
      while (true)
      {
        a(localContext);
        return;
        if (this.q <= 5)
          continue;
        this.q = 5;
      }
    }

    private void a(Context paramContext)
    {
      if (this.a == null)
        this.a = new t();
      w localw = new w();
      localw.j = new bd()
      {
        public String a(int paramInt1, int paramInt2, int paramInt3)
        {
          return com.amap.mapapi.core.j.a().b() + "/appmaptile?z=" + paramInt3 + "&x=" + paramInt1 + "&y=" + paramInt2 + "&lang=zh_cn&size=1&scale=1&style=7";
        }
      };
      localw.a = this.g;
      localw.e = true;
      localw.d = true;
      localw.f = true;
      localw.g = true;
      localw.b = 18;
      localw.c = 3;
      a(localw, paramContext);
    }

    private void a(Canvas paramCanvas)
    {
      if (ai.d.a(ai.this.b).VMapMode)
      {
        if (!ai.d.a(ai.this.b).isInited)
          return;
        ai.d.a(ai.this.b).paintVectorMap(paramCanvas);
        paramCanvas.save();
        Matrix localMatrix = paramCanvas.getMatrix();
        localMatrix.preRotate(-ai.d.a(ai.this.b).getMapAngle(), ai.this.b.c() / 2, ai.this.b.d() / 2);
        paramCanvas.setMatrix(localMatrix);
        int i3 = this.a.size();
        for (int i4 = 0; i4 < i3; i4++)
        {
          w localw2 = (w)this.a.get(i4);
          if ((!localw2.a.equals("GridTmc")) || (!localw2.f))
            continue;
          localw2.a(paramCanvas);
        }
        paramCanvas.restore();
        return;
      }
      int i1 = this.a.size();
      int i2 = 0;
      label192: w localw1;
      if (i2 < i1)
      {
        localw1 = (w)this.a.get(i2);
        if (localw1 != null)
          break label221;
      }
      while (true)
      {
        i2++;
        break label192;
        break;
        label221: if (!localw1.f)
          continue;
        localw1.a(paramCanvas);
      }
    }

    private boolean a(long paramLong)
    {
      return System.currentTimeMillis() - paramLong > 300L;
    }

    private void b(Canvas paramCanvas)
    {
      long l1 = e.a();
      RouteOverlay localRouteOverlay;
      Object localObject1;
      if (this.m)
      {
        Iterator localIterator2 = this.b.iterator();
        localRouteOverlay = null;
        Object localObject2 = null;
        while (localIterator2.hasNext())
        {
          Overlay localOverlay = (Overlay)localIterator2.next();
          if (localOverlay == null)
            continue;
          if ((localOverlay instanceof RouteOverlay))
          {
            localRouteOverlay = (RouteOverlay)localOverlay;
            continue;
          }
          if ((localOverlay instanceof MyLocationOverlay));
          for (Object localObject3 = (MyLocationOverlay)localOverlay; ; localObject3 = localObject2)
          {
            localObject2 = localObject3;
            break;
            localOverlay.draw(paramCanvas, ai.d.a(ai.this.b), false, l1);
          }
        }
        localObject1 = localObject2;
      }
      while (true)
      {
        if (this.o == MapView.ReticleDrawMode.DRAW_RETICLE_OVER)
          c(paramCanvas);
        if (localObject1 != null)
          localObject1.draw(paramCanvas, ai.d.a(ai.this.b), false, l1);
        if (localRouteOverlay != null)
        {
          MapView localMapView = ai.d.a(ai.this.b);
          localRouteOverlay.draw(paramCanvas, localMapView, false, l1);
        }
        Iterator localIterator1 = this.n.iterator();
        while (localIterator1.hasNext())
          ((p)localIterator1.next()).draw(paramCanvas, ai.d.a(ai.this.b), false, l1);
        return;
        localObject1 = null;
        localRouteOverlay = null;
      }
    }

    private void b(String paramString)
    {
      if (paramString.equals("") == true)
        return;
      int i1 = this.a.size();
      int i2 = 0;
      label22: w localw;
      if (i2 < i1)
      {
        localw = (w)this.a.get(i2);
        if (localw != null)
          break label51;
      }
      while (true)
      {
        i2++;
        break label22;
        break;
        label51: if ((localw.a.equals(paramString)) || (localw.e != true) || (localw.f != true))
          continue;
        localw.f = false;
      }
    }

    private void c(Canvas paramCanvas)
    {
      int i1 = this.a.size();
      int i2 = 0;
      if (i2 < i1)
      {
        w localw = (w)this.a.get(i2);
        if (localw == null);
        while (true)
        {
          i2++;
          break;
          if ((!localw.f) || (!localw.e))
            continue;
          int i3 = localw.o.size();
          for (int i4 = 0; i4 < i3; i4++)
            a(paramCanvas, ai.d.a(ai.this.b), (av.a)localw.o.get(i4));
        }
      }
    }

    private boolean c(String paramString)
    {
      if (this.a == null)
        return false;
      int i1 = this.a.size();
      int i2 = 0;
      if (i2 < i1)
      {
        w localw = (w)this.a.get(i2);
        if (localw == null);
        do
        {
          i2++;
          break;
        }
        while (localw.a.equals(paramString) != true);
        return true;
      }
      return false;
    }

    private void e()
    {
      int i1 = this.a.size();
      int i2 = 0;
      if (i2 < i1)
      {
        w localw = (w)this.a.get(i2);
        if (localw == null);
        while (true)
        {
          i2++;
          break;
          localw.k = i2;
        }
      }
    }

    private void f()
    {
      String str = Build.MODEL;
      if ((str != null) && ((str.indexOf("OMAP_SS") > -1) || (str.indexOf("omap_ss") > -1) || (str.indexOf("MT810") > -1) || (str.indexOf("MT720") > -1) || (str.indexOf("GT-I9008") > -1)))
        c.o = true;
    }

    public PointF a(av.a parama)
    {
      if (parama == null)
        return null;
      return parama.f;
    }

    w a(String paramString)
    {
      if ((paramString.equals("") == true) || (this.a == null) || (this.a.size() == 0))
        return null;
      int i1 = this.a.size();
      int i2 = 0;
      if (i2 < i1)
      {
        w localw = (w)this.a.get(i2);
        if (localw == null);
        do
        {
          i2++;
          break;
        }
        while (localw.a.equals(paramString) != true);
        return localw;
      }
      return null;
    }

    public void a()
    {
      int i1 = this.b.size();
      for (int i2 = 0; i2 < i1; i2++)
      {
        Overlay localOverlay = (Overlay)this.b.remove(0);
        if (!(localOverlay instanceof MyLocationOverlay))
          continue;
        MyLocationOverlay localMyLocationOverlay = (MyLocationOverlay)localOverlay;
        if (localMyLocationOverlay == null)
          continue;
        localMyLocationOverlay.a();
      }
      int i3 = this.n.size();
      for (int i4 = 0; i4 < i3; i4++)
      {
        p localp = (p)this.n.remove(0);
        if (localp == null)
          continue;
        localp.b();
      }
    }

    public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      if (a(this.c))
      {
        b(paramInt1, paramInt2, paramInt3, paramInt4);
        return;
      }
      this.r = true;
    }

    public void a(Canvas paramCanvas, Matrix paramMatrix, float paramFloat1, float paramFloat2)
    {
      if (this.l)
      {
        paramCanvas.save();
        paramCanvas.translate(paramFloat1, paramFloat2);
        paramCanvas.concat(paramMatrix);
        a(paramCanvas);
        paramCanvas.restore();
        if ((!this.e) && (!this.f))
        {
          a(false);
          ai.d.a(ai.this.b).b().b(new Matrix());
          ai.d.a(ai.this.b).b().b(1.0F);
          ai.d.a(ai.this.b).b().c();
        }
      }
      while (true)
      {
        ai.this.b.h();
        b(paramCanvas);
        return;
        a(paramCanvas);
      }
    }

    public void a(Canvas paramCanvas, MapView paramMapView, av.a parama)
    {
      PointF localPointF = a(parama);
      Rect localRect = new Rect((int)localPointF.x, (int)localPointF.y, (int)(256.0F + localPointF.x), (int)(256.0F + localPointF.y));
      Paint localPaint = new Paint();
      localPaint.setStyle(Paint.Style.STROKE);
      localPaint.setColor(-7829368);
      paramCanvas.drawRect(localRect, localPaint);
    }

    public void a(MapView.ReticleDrawMode paramReticleDrawMode)
    {
      this.o = paramReticleDrawMode;
    }

    public void a(p paramp, boolean paramBoolean)
    {
      if (paramBoolean)
      {
        this.n.a(paramp);
        return;
      }
      this.n.b(paramp);
    }

    public void a(boolean paramBoolean)
    {
      this.l = paramBoolean;
    }

    public boolean a(int paramInt, KeyEvent paramKeyEvent)
    {
      boolean bool = false;
      Iterator localIterator = this.b.iterator();
      do
      {
        if (!localIterator.hasNext())
          break;
        bool = ((Overlay)localIterator.next()).onKeyDown(paramInt, paramKeyEvent, ai.d.a(ai.this.b));
      }
      while (!bool);
      return bool;
    }

    public boolean a(MotionEvent paramMotionEvent)
    {
      boolean bool = false;
      Iterator localIterator = this.b.iterator();
      do
      {
        if (!localIterator.hasNext())
          break;
        bool = ((Overlay)localIterator.next()).onTrackballEvent(paramMotionEvent, ai.d.a(ai.this.b));
      }
      while (!bool);
      return bool;
    }

    public boolean a(GeoPoint paramGeoPoint)
    {
      av.a locala1 = ai.this.g.b();
      int i1 = this.a.size();
      int i2 = 0;
      if (i2 < i1)
        if (((w)this.a.get(i2)).e != true);
      for (t localt = ((w)this.a.get(i2)).o; ; localt = null)
      {
        if (localt == null)
        {
          return false;
          i2++;
          break;
        }
        Iterator localIterator = localt.iterator();
        while (localIterator.hasNext())
        {
          av.a locala2 = (av.a)localIterator.next();
          if ((locala2.b != locala1.b) || (locala2.c != locala1.c) || (locala2.d != locala1.d))
            continue;
          if (locala2.g < 0)
            break;
        }
        for (int i3 = 1; ; i3 = 0)
          return i3;
      }
    }

    boolean a(w paramw, Context paramContext)
    {
      if (paramw == null);
      do
        return false;
      while ((paramw.a.equals("") == true) || (c(paramw.a) == true));
      paramw.o = new t();
      paramw.m = new h(this.p, this.q, paramw.h, paramw.i);
      paramw.n = new j(paramContext, ai.d.a(ai.this.b).e, paramw);
      paramw.n.a(paramw.m);
      int i1 = this.a.size();
      boolean bool;
      if ((!paramw.e) || (i1 == 0))
        bool = this.a.add(paramw);
      while (true)
      {
        e();
        if (paramw.f == true)
          a(paramw.a, true);
        return bool;
        int i2 = i1 - 1;
        if (i2 >= 0)
        {
          w localw = (w)this.a.get(i2);
          if (localw == null);
          do
          {
            i2--;
            break;
          }
          while (localw.e != true);
          this.a.add(i2, paramw);
          bool = false;
          continue;
        }
        bool = false;
      }
    }

    boolean a(String paramString, boolean paramBoolean)
    {
      if (paramString.equals("") == true)
        return false;
      int i1 = this.a.size();
      int i2 = 0;
      if (i2 < i1)
      {
        w localw = (w)this.a.get(i2);
        if (localw == null);
        do
        {
          do
          {
            i2++;
            break;
          }
          while (localw.a.equals(paramString) != true);
          localw.f = paramBoolean;
          if (!localw.e)
            return true;
        }
        while (paramBoolean != true);
        if (localw.b > localw.c)
        {
          ai.this.b.b(localw.b);
          ai.this.b.c(localw.c);
        }
        b(paramString);
        ai.this.b.a(false, false);
        return true;
      }
      return false;
    }

    public void b()
    {
      int i1 = ai.this.d.a.size();
      int i2 = 0;
      if (i2 < i1)
      {
        w localw = (w)ai.this.d.a.get(0);
        if (localw == null);
        while (true)
        {
          i2++;
          break;
          localw.a();
          ai.this.d.a.remove(0);
        }
      }
      ai.this.d.a = null;
    }

    public void b(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      ai.d.a(ai.this.b).postInvalidate(paramInt1, paramInt2, paramInt3, paramInt4);
      this.c = System.currentTimeMillis();
      this.r = false;
    }

    public void b(boolean paramBoolean)
    {
      this.m = paramBoolean;
    }

    public boolean b(int paramInt, KeyEvent paramKeyEvent)
    {
      boolean bool = false;
      Iterator localIterator = this.b.iterator();
      do
      {
        if (!localIterator.hasNext())
          break;
        bool = ((Overlay)localIterator.next()).onKeyUp(paramInt, paramKeyEvent, ai.d.a(ai.this.b));
      }
      while (!bool);
      return bool;
    }

    public boolean b(MotionEvent paramMotionEvent)
    {
      Iterator localIterator = this.b.iterator();
      boolean bool1 = false;
      if (localIterator.hasNext())
      {
        Overlay localOverlay = (Overlay)localIterator.next();
        if (localOverlay != null);
        for (boolean bool2 = localOverlay.onTouchEvent(paramMotionEvent, ai.d.a(ai.this.b)); ; bool2 = bool1)
        {
          if (bool2)
            return bool2;
          bool1 = bool2;
          break;
        }
      }
      return bool1;
    }

    public List<Overlay> c()
    {
      return this.b;
    }

    protected boolean c(MotionEvent paramMotionEvent)
    {
      GeoPoint localGeoPoint = ai.this.a.fromPixels((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
      for (int i1 = -1 + this.b.size(); ; i1--)
      {
        Overlay localOverlay;
        if (i1 >= 0)
        {
          localOverlay = (Overlay)this.b.get(i1);
          if (localOverlay == null)
            continue;
          if (!(localOverlay instanceof ItemizedOverlay))
            break label85;
          if (!((ItemizedOverlay)localOverlay).onTap(localGeoPoint, ai.d.a(ai.this.b)))
            continue;
        }
        label85: 
        do
          return false;
        while (localOverlay.onTap(localGeoPoint, ai.d.a(ai.this.b)));
      }
    }

    public void d()
    {
      if ((ai.this.b == null) || (ai.d.a(ai.this.b) == null))
        return;
      ai.d.a(ai.this.b).postInvalidate();
    }
  }

  public class b
  {
    public boolean a = false;
    int b = 0;

    public b()
    {
      f();
    }

    public void a()
    {
      int i = 0;
      if (i < ai.c.a(ai.this.e).size())
      {
        af localaf = (af)ai.c.a(ai.this.e).valueAt(i);
        if (localaf == null);
        while (true)
        {
          i++;
          break;
          localaf.c();
        }
      }
      ai.c.b(ai.this.e);
    }

    public void b()
    {
      if (ai.a.a(ai.this.d))
        ai.this.d.d();
      this.b = (1 + this.b);
      if ((this.b < 20) || (this.b % 20 != 0))
        return;
      int i = 0;
      label55: af localaf;
      if (i < ai.c.a(ai.this.e).size())
      {
        localaf = (af)ai.c.a(ai.this.e).valueAt(i);
        if (localaf != null)
          break label100;
      }
      while (true)
      {
        i++;
        break label55;
        break;
        label100: localaf.h();
      }
    }

    public void c()
    {
      ai.this.b.a = false;
      int i = 0;
      if (i < ai.c.a(ai.this.e).size())
      {
        af localaf = (af)ai.c.a(ai.this.e).valueAt(i);
        if (localaf == null);
        while (true)
        {
          i++;
          break;
          localaf.a();
        }
      }
      if ((ai.this.d != null) && (ai.this.d.d != null))
        ai.this.d.d.a();
    }

    public void d()
    {
      int i = 0;
      if (i < ai.c.a(ai.this.e).size())
      {
        af localaf = (af)ai.c.a(ai.this.e).valueAt(i);
        if (localaf == null);
        while (true)
        {
          i++;
          break;
          localaf.d();
        }
      }
    }

    public void e()
    {
      int i = 0;
      if (i < ai.c.a(ai.this.e).size())
      {
        af localaf = (af)ai.c.a(ai.this.e).valueAt(i);
        if (localaf == null);
        while (true)
        {
          i++;
          break;
          localaf.b();
        }
      }
    }

    public void f()
    {
      int i = 0;
      if (i < ai.c.a(ai.this.e).size())
      {
        af localaf = (af)ai.c.a(ai.this.e).valueAt(i);
        if (localaf == null);
        while (true)
        {
          i++;
          break;
          localaf.a_();
        }
      }
      ai.c.c(ai.this.e);
    }
  }

  public class c
  {
    private String b = "";
    private String c = "android";
    private final MapActivity d;
    private Proxy e;
    private SparseArray<af> f = new SparseArray();

    public c(ai paramMapActivity, MapActivity paramString, String arg4)
    {
      this.d = paramString;
      Object localObject;
      if (localObject != null)
        this.c = localObject;
      if (c.e == 2)
        this.c = "androidh";
      while (true)
      {
        this.b = e.a(this.d);
        if (!ai.d.a(paramMapActivity.b).VMapMode)
          break;
        this.f.put(1, new bf(paramMapActivity, paramString));
        this.f.put(2, new m(paramMapActivity, paramString));
        return;
        if (c.e == 1)
        {
          this.c = "androidl";
          continue;
        }
        this.c = "android";
      }
      this.f.put(0, new ay(paramMapActivity, paramString));
      this.f.put(2, new m(paramMapActivity, paramString));
    }

    private void d()
    {
      this.e = e.b(this.d);
    }

    private void e()
    {
      this.e = null;
    }

    public af a(int paramInt)
    {
      return (af)this.f.get(paramInt);
    }

    public String a()
    {
      return this.b;
    }

    public void a(af paramaf, int paramInt)
    {
      this.f.put(paramInt, paramaf);
    }

    public String b()
    {
      return this.c;
    }

    public Proxy c()
    {
      return this.e;
    }
  }

  public class d
  {
    public boolean a = true;
    public ArrayList<MapViewListener> b;
    private MapView d;
    private ArrayList<bh> e;

    public d(MapView arg2)
    {
      Object localObject;
      this.d = localObject;
      this.e = new ArrayList();
      this.b = new ArrayList();
    }

    private void a(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      if ((paramView instanceof ListView))
      {
        View localView = (View)paramView.getParent();
        if (localView != null)
        {
          paramInt1 = localView.getWidth();
          paramInt2 = localView.getHeight();
        }
      }
      paramView.measure(paramInt1, paramInt2);
      label61: int i;
      int j;
      if (paramInt1 == -2)
      {
        paramInt1 = paramView.getMeasuredWidth();
        if (paramInt2 != -2)
          break label133;
        paramInt2 = paramView.getMeasuredHeight();
        i = paramInt5 & 0x7;
        j = paramInt5 & 0x70;
        if (i != 5)
          break label149;
        paramInt3 -= paramInt1;
        label87: if (j != 80)
          break label166;
        paramInt4 -= paramInt2;
      }
      while (true)
      {
        paramView.layout(paramInt3, paramInt4, paramInt3 + paramInt1, paramInt4 + paramInt2);
        return;
        if (paramInt1 != -1)
          break;
        paramInt1 = this.d.getMeasuredWidth();
        break;
        label133: if (paramInt2 != -1)
          break label61;
        paramInt2 = this.d.getMeasuredHeight();
        break label61;
        label149: if (i != 1)
          break label87;
        paramInt3 -= paramInt1 / 2;
        break label87;
        label166: if (j != 16)
          continue;
        paramInt4 -= paramInt2 / 2;
      }
    }

    private void a(View paramView, MapView.LayoutParams paramLayoutParams)
    {
      if (paramLayoutParams.point != null)
      {
        Point localPoint = ai.this.a.toPixels(paramLayoutParams.point, null);
        localPoint.x += paramLayoutParams.x;
        localPoint.y += paramLayoutParams.y;
        a(paramView, paramLayoutParams.width, paramLayoutParams.height, localPoint.x, localPoint.y, paramLayoutParams.alignment);
      }
    }

    private void b(View paramView, MapView.LayoutParams paramLayoutParams)
    {
      a(paramView, paramLayoutParams.width, paramLayoutParams.height, paramLayoutParams.x, paramLayoutParams.y, paramLayoutParams.alignment);
    }

    public int a()
    {
      return ai.this.g.f;
    }

    public void a(int paramInt)
    {
      if (!this.d.VMapMode)
        if (paramInt != ai.this.g.g)
          ai.this.g.g = paramInt;
      while (true)
      {
        a(false, false);
        return;
        if (paramInt == this.d.mapLevel)
          continue;
        this.d.mapLevel = paramInt;
      }
    }

    public void a(int paramInt1, int paramInt2)
    {
      if ((paramInt1 != c.i) || (paramInt2 != c.j))
      {
        c.i = paramInt1;
        c.j = paramInt2;
        a(true, false);
      }
    }

    public void a(GeoPoint paramGeoPoint)
    {
      if (paramGeoPoint == null)
        return;
      if (c.n == true)
      {
        GeoPoint localGeoPoint = ai.this.g.a(paramGeoPoint);
        ai.this.g.j = localGeoPoint;
      }
      a(false, false);
    }

    public void a(bh parambh)
    {
      this.e.add(parambh);
    }

    public void a(boolean paramBoolean1, boolean paramBoolean2)
    {
      Iterator localIterator = this.e.iterator();
      while (localIterator.hasNext())
        ((bh)localIterator.next()).a(paramBoolean1, paramBoolean2);
    }

    public int b()
    {
      return ai.this.g.e;
    }

    public void b(int paramInt)
    {
      if (paramInt <= 0)
        return;
      if (this.d.VMapMode)
      {
        ad localad2 = ai.this.g;
        c.c = paramInt;
        localad2.f = paramInt;
        return;
      }
      ad localad1 = ai.this.g;
      c.a = paramInt;
      localad1.f = paramInt;
    }

    public void b(GeoPoint paramGeoPoint)
    {
      GeoPoint localGeoPoint1 = ai.this.b.f();
      if ((paramGeoPoint != null) && (!paramGeoPoint.equals(localGeoPoint1)))
      {
        if (c.n == true)
        {
          GeoPoint localGeoPoint2 = ai.this.g.a(paramGeoPoint);
          ai.this.g.j = localGeoPoint2;
        }
        a(false, true);
      }
    }

    public int c()
    {
      return c.i;
    }

    public void c(int paramInt)
    {
      if (paramInt <= 0)
        return;
      if (this.d.VMapMode)
      {
        ad localad2 = ai.this.g;
        c.d = paramInt;
        localad2.e = paramInt;
        return;
      }
      ad localad1 = ai.this.g;
      c.b = paramInt;
      localad1.e = paramInt;
    }

    public int d()
    {
      return c.j;
    }

    public int e()
    {
      if (!this.d.VMapMode)
        return ai.this.g.g;
      return this.d.mapLevel;
    }

    public GeoPoint f()
    {
      GeoPoint localGeoPoint = ai.this.g.b(ai.this.g.j);
      if ((ai.this.c != null) && (ai.this.c.a))
        localGeoPoint = ai.this.g.k;
      return localGeoPoint;
    }

    public MapView g()
    {
      return this.d;
    }

    public void h()
    {
      int i = this.d.getChildCount();
      int j = 0;
      if (j < i)
      {
        View localView = this.d.getChildAt(j);
        if (localView == null);
        while (true)
        {
          j++;
          break;
          if (!(localView.getLayoutParams() instanceof MapView.LayoutParams))
            continue;
          MapView.LayoutParams localLayoutParams = (MapView.LayoutParams)localView.getLayoutParams();
          if (localLayoutParams.mode == 0)
          {
            a(localView, localLayoutParams);
            continue;
          }
          b(localView, localLayoutParams);
        }
      }
      ai.this.b.d.c();
    }
  }

  public class e
    implements Projection
  {
    private int b = 0;
    private HashMap<Float, Float> c = new HashMap();

    public e()
    {
    }

    private int a(boolean paramBoolean)
    {
      double d = 3.141592653589793D * ai.this.b.g().getMapAngle() / 180.0D;
      int i = (int)(ai.this.b.c() * Math.abs(Math.cos(d)) + ai.this.b.d() * Math.abs(Math.sin(d)));
      GeoPoint localGeoPoint1 = fromPixels(0, (int)(ai.this.b.c() * Math.abs(Math.sin(d)) + ai.this.b.d() * Math.abs(Math.cos(d))));
      GeoPoint localGeoPoint2 = fromPixels(i, 0);
      if (paramBoolean)
        return Math.abs(localGeoPoint1.getLongitudeE6() - localGeoPoint2.getLongitudeE6());
      return Math.abs(localGeoPoint1.getLatitudeE6() - localGeoPoint2.getLatitudeE6());
    }

    public int a()
    {
      return a(false);
    }

    public int b()
    {
      return a(true);
    }

    public GeoPoint fromPixels(int paramInt1, int paramInt2)
    {
      int i = ai.this.b.e();
      PointF localPointF = new PointF(paramInt1, paramInt2);
      return ai.this.g.a(localPointF, ai.this.g.j, ai.this.g.l, ai.this.g.h[i], ai.this.g.m);
    }

    public float metersToEquatorPixels(float paramFloat)
    {
      int i = ai.this.b.e();
      if ((this.c.size() > 30) || (i != this.b))
      {
        this.b = i;
        this.c.clear();
      }
      if (!this.c.containsKey(Float.valueOf(paramFloat)))
      {
        GeoPoint localGeoPoint1 = fromPixels(0, 0);
        GeoPoint localGeoPoint2 = fromPixels(0, 10);
        float f1 = ai.this.g.a(localGeoPoint1, localGeoPoint2);
        if (f1 <= 0.0F)
          return 0.0F;
        float f2 = 10.0F * (paramFloat / f1);
        this.c.put(Float.valueOf(paramFloat), Float.valueOf(f2));
      }
      return ((Float)this.c.get(Float.valueOf(paramFloat))).floatValue();
    }

    public Point toPixels(GeoPoint paramGeoPoint, Point paramPoint)
    {
      int i = ai.this.b.e();
      PointF localPointF = ai.this.g.b(paramGeoPoint, ai.this.g.j, ai.this.g.l, ai.this.g.h[i]);
      aj localaj = ai.d.a(ai.this.b).b().a();
      Point localPoint1 = ai.d.a(ai.this.b).a().g.l;
      int j;
      int k;
      if (localaj.m)
        if (localaj.l)
        {
          float f3 = aj.j * ((int)localPointF.x - localaj.f.x) + localaj.f.x + (localaj.g.x - localaj.f.x);
          float f4 = aj.j * ((int)localPointF.y - localaj.f.y) + localaj.f.y + (localaj.g.y - localaj.f.y);
          j = (int)f3;
          k = (int)f4;
          if (f3 >= 0.5D + j)
            j++;
          if (f4 >= 0.5D + k)
            k++;
        }
      while (true)
      {
        Point localPoint2 = new Point(j, k);
        if (paramPoint != null)
        {
          paramPoint.x = localPoint2.x;
          paramPoint.y = localPoint2.y;
        }
        return localPoint2;
        j = (int)localPointF.x;
        k = (int)localPointF.y;
        continue;
        float f1 = bj.h * ((int)localPointF.x - localPoint1.x) + localPoint1.x;
        float f2 = bj.h * ((int)localPointF.y - localPoint1.y) + localPoint1.y;
        j = (int)f1;
        k = (int)f2;
        if (f1 >= 0.5D + j)
          j++;
        if (f2 < 0.5D + k)
          continue;
        k++;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.ai
 * JD-Core Version:    0.6.0
 */