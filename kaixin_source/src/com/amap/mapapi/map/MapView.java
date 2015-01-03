package com.amap.mapapi.map;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.Scroller;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.c;
import com.amap.mapapi.core.c.a;
import com.amap.mapapi.core.e;
import com.amap.mapapi.core.j;
import com.amap.mapapi.core.s;
import com.mapabc.minimap.map.vmap.NativeMap;
import com.mapabc.minimap.map.vmap.NativeMapEngine;
import com.mapabc.minimap.map.vmap.VMapProjection;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy.Type;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class MapView extends ViewGroup
{
  private Context A;
  public boolean VMapMode = false;
  public boolean VisInited = false;
  NativeMap a;
  Bitmap b;
  protected boolean bfirstDrawed;
  int[] c = new int[2];
  public int centerX;
  public int centerY;
  ZoomButtonsController d;
  boolean e = false;
  NativeMapEngine f = null;
  ByteBuffer g = null;
  Bitmap h = null;
  public int height;
  n i = null;
  public boolean isInited = false;
  ao j = null;
  private MapActivity k;
  private ai l;
  private c m;
  public e mRouteCtrl;
  public int mapAngle = 0;
  public int mapLevel = 12;
  private MapController n;
  private g o;
  private boolean p = false;
  private v q;
  private boolean r = true;
  private boolean s;
  private final int[] t = { 3000000, 1500000, 800000, 400000, 200000, 100000, 50000, 25000, 12000, 6000, 3000, 1500, 800, 400, 200, 100, 50, 25 };
  public ax tileDownloadCtrl = null;
  private boolean u = true;
  private TrackballGestureDetector v;
  private TrackballGestureDetector.OnTrackballListener w;
  public int width;
  private boolean x = false;
  private int y = 0;
  private d z = null;

  public MapView(Context paramContext)
  {
    super(paramContext);
    m();
    setClickable(true);
    if ((paramContext instanceof MapActivity))
    {
      ((MapActivity)paramContext).a(this, paramContext, null, null);
      return;
    }
    throw new IllegalArgumentException("MapViews can only be created inside instances of MapActivity.");
  }

  public MapView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public MapView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    m();
    this.A = paramContext;
    int i1 = paramAttributeSet.getAttributeCount();
    int i2 = 0;
    Object localObject2 = null;
    if (i2 < i1)
    {
      String str1 = paramAttributeSet.getAttributeName(i2).toLowerCase();
      Object localObject4;
      Object localObject3;
      if (str1.equals("amapapikey"))
      {
        String str2 = paramAttributeSet.getAttributeValue(i2);
        Object localObject5 = localObject1;
        localObject4 = str2;
        localObject3 = localObject5;
      }
      while (true)
      {
        i2++;
        localObject2 = localObject4;
        localObject1 = localObject3;
        break;
        if (str1.equals("useragent"))
        {
          localObject3 = paramAttributeSet.getAttributeValue(i2);
          localObject4 = localObject2;
          continue;
        }
        if (str1.equals("clickable"))
          this.p = paramAttributeSet.getAttributeValue(i2).equals("true");
        localObject3 = localObject1;
        localObject4 = localObject2;
      }
    }
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, new int[] { 16843281 });
    if ((localObject2 == null) || (((String)localObject2).length() < 15))
      localObject2 = localTypedArray.getString(0);
    if ((paramContext instanceof MapActivity))
    {
      ((MapActivity)paramContext).a(this, paramContext, localObject1, (String)localObject2);
      return;
    }
    throw new IllegalArgumentException("MapViews can only be created inside instances of MapActivity.");
  }

  public MapView(Context paramContext, String paramString)
  {
    super(paramContext);
    m();
    setClickable(true);
    if ((paramContext instanceof MapActivity))
    {
      ((MapActivity)paramContext).a(this, paramContext, null, paramString);
      return;
    }
    throw new IllegalArgumentException("MapViews can only be created inside instances of MapActivity.");
  }

  private int c(int paramInt)
  {
    int i1 = 2;
    switch (paramInt)
    {
    default:
      i1 = 0;
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
      return i1;
    case 6:
      return 6;
    case 7:
      return 6;
    case 8:
      return 6;
    case 9:
      return 6;
    case 10:
      return 10;
    case 11:
      return 10;
    case 12:
      return 12;
    case 13:
      return 12;
    case 14:
      return 14;
    case 15:
      return 14;
    case 16:
      return 14;
    case 17:
      return 14;
    case 18:
      return 14;
    case 19:
      return 14;
    case 20:
    }
    return 14;
  }

  private void m()
  {
    Method[] arrayOfMethod = View.class.getMethods();
    int i1 = arrayOfMethod.length;
    int i2 = 0;
    Method localMethod;
    if (i2 < i1)
    {
      localMethod = arrayOfMethod[i2];
      if (!localMethod.getName().equals("setLayerType"));
    }
    while (true)
    {
      while (true)
      {
        if (localMethod != null);
        try
        {
          Field localField = View.class.getField("LAYER_TYPE_SOFTWARE");
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(localField.getInt(null));
          arrayOfObject[1] = null;
          localMethod.invoke(this, arrayOfObject);
          return;
          i2++;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return;
        }
      }
      localMethod = null;
    }
  }

  private void n()
  {
    this.m = new c(this.k);
    ViewGroup.LayoutParams localLayoutParams = new ViewGroup.LayoutParams(-1, -1);
    addView(this.m, 0, localLayoutParams);
  }

  protected void VdestoryMap()
  {
    if (this.i != null)
      this.i.b();
    if (this.tileDownloadCtrl != null)
      this.tileDownloadCtrl.c();
    int i1;
    if (this.j != null)
    {
      this.j.a();
      i1 = 1;
    }
    while (true)
    {
      if (i1 != 0);
      try
      {
        this.j.join();
        i1 = 0;
        continue;
        if (this.h != null)
        {
          this.h.recycle();
          this.h = null;
          this.g = null;
        }
        this.isInited = false;
        if (this.f != null)
        {
          this.f.destory();
          this.f = null;
        }
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
      }
    }
  }

  protected void Vinit()
  {
    if (!this.isInited)
    {
      this.g = ByteBuffer.allocate(131072);
      this.h = Bitmap.createBitmap(256, 256, Bitmap.Config.RGB_565);
      this.f = new NativeMapEngine(getContext());
      this.f.initIconData(this.A);
      this.f.initStyleData(this.A);
      this.i = new n();
      this.tileDownloadCtrl = new ax(this);
      this.i.e = this;
      this.i.start();
      this.tileDownloadCtrl.start();
      this.j = new ao(this);
      this.j.start();
      this.isInited = true;
    }
  }

  ai a()
  {
    return this.l;
  }

  void a(int paramInt)
  {
    this.y = paramInt;
  }

  void a(Context paramContext, String paramString)
  {
    this.A = paramContext;
    try
    {
      this.k = ((MapActivity)paramContext);
      n();
      this.e = this.k.b;
      if (this.k.getMapMode() == MapActivity.MAP_MODE_VECTOR)
        this.VMapMode = true;
      setBackgroundColor(Color.rgb(222, 215, 214));
      this.l = new ai(this.k, this, paramString);
      this.o = new g(this.A);
      this.mRouteCtrl = new e();
      this.n = new MapController(this.l);
      setEnabled(true);
      return;
    }
    catch (Exception localException)
    {
    }
    throw new IllegalArgumentException("can only be created inside instances of MapActivity");
  }

  void a(be parambe1, be parambe2)
  {
    double d1 = 3.141592653589793D * this.mapAngle / 180.0D;
    int i1 = (int)(this.width * Math.abs(Math.cos(d1)) + this.height * Math.abs(Math.sin(d1)));
    int i2 = (int)(this.width * Math.abs(Math.sin(d1)) + this.height * Math.abs(Math.cos(d1)));
    be localbe = new be();
    getPixelPnt(new Point((this.width - i1) / 2, (this.height - i2) / 2), localbe);
    int i3 = Math.min(2147483647, localbe.a);
    int i4 = Math.min(2147483647, localbe.b);
    int i5 = Math.max(-2147483648, localbe.a);
    int i6 = Math.max(-2147483648, localbe.b);
    getPixelPnt(new Point((i1 + this.width) / 2, (this.height - i2) / 2), localbe);
    int i7 = Math.min(i3, localbe.a);
    int i8 = Math.min(i4, localbe.b);
    int i9 = Math.max(i5, localbe.a);
    int i10 = Math.max(i6, localbe.b);
    getPixelPnt(new Point((i1 + this.width) / 2, (i2 + this.height) / 2), localbe);
    int i11 = Math.min(i7, localbe.a);
    int i12 = Math.min(i8, localbe.b);
    int i13 = Math.max(i9, localbe.a);
    int i14 = Math.max(i10, localbe.b);
    getPixelPnt(new Point((this.width - i1) / 2, (i2 + this.height) / 2), localbe);
    int i15 = Math.min(i11, localbe.a);
    int i16 = Math.min(i12, localbe.b);
    int i17 = Math.max(i13, localbe.a);
    int i18 = Math.max(i14, localbe.b);
    parambe1.a = i15;
    parambe1.b = i16;
    parambe2.a = i17;
    parambe2.b = i18;
  }

  boolean a(String paramString)
  {
    be localbe1 = new be();
    be localbe2 = new be();
    if (this.mapLevel != paramString.length());
    be localbe3;
    be localbe4;
    Point localPoint;
    do
    {
      return false;
      a(localbe1, localbe2);
      localbe3 = VMapProjection.PixelsToTile(localbe1.a, localbe1.b);
      localbe4 = VMapProjection.PixelsToTile(localbe2.a, localbe2.b);
      localPoint = VMapProjection.QuadKeyToTile(paramString);
    }
    while ((localPoint.x < localbe3.a) || (localPoint.x > localbe4.a) || (localPoint.y < localbe3.b) || (localPoint.y > localbe4.b));
    return true;
  }

  int b(int paramInt)
  {
    if (paramInt < this.l.b.b())
      paramInt = this.l.b.b();
    if (paramInt > this.l.b.a())
      paramInt = this.l.b.a();
    return paramInt;
  }

  c b()
  {
    return this.m;
  }

  void c()
  {
    this.o.e();
  }

  public boolean canCoverCenter()
  {
    return this.l.d.a(getMapCenter());
  }

  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }

  public void computeScroll()
  {
    if (c.a(this.m).computeScrollOffset())
    {
      int i1 = c.a(this.m).getCurrX() - c.b(this.m);
      int i2 = c.a(this.m).getCurrY() - c.c(this.m);
      c.a(this.m, c.a(this.m).getCurrX());
      c.b(this.m, c.a(this.m).getCurrY());
      GeoPoint localGeoPoint = this.l.a.fromPixels(i1 + this.l.g.l.x, i2 + this.l.g.l.y);
      if (c.a(this.m).isFinished())
      {
        if ((this.l.b.b.size() > 0) && (!this.m.a))
        {
          Iterator localIterator = this.l.b.b.iterator();
          while (localIterator.hasNext())
            ((MapViewListener)localIterator.next()).onMapMoveFinish();
        }
        this.l.b.a(false, false);
        return;
      }
      this.l.b.b(localGeoPoint);
      return;
    }
    super.computeScroll();
  }

  void d()
  {
    if (t.a != null)
      t.a.c();
    VdestoryMap();
    this.l.a();
    if (this.n != null)
    {
      this.n.stopAnimation(true);
      this.n.stopPanning();
    }
    this.n = null;
    this.m = null;
    this.l = null;
    if (this.mRouteCtrl != null)
    {
      this.mRouteCtrl.a();
      this.mRouteCtrl = null;
    }
    if (this.o != null)
    {
      this.o.c();
      this.o = null;
    }
  }

  protected void dispatchDraw(Canvas paramCanvas)
  {
    if (-1 == indexOfChild(this.m))
    {
      ViewGroup.LayoutParams localLayoutParams = new ViewGroup.LayoutParams(-1, -1);
      addView(this.m, 0, localLayoutParams);
    }
    super.dispatchDraw(paramCanvas);
  }

  public void displayZoomControls(boolean paramBoolean)
  {
    this.o.c(paramBoolean);
  }

  ArrayList<String> e()
  {
    be localbe1 = new be();
    be localbe2 = new be();
    ArrayList localArrayList = new ArrayList();
    int i1 = c(this.mapLevel);
    int i2 = this.mapLevel - i1;
    a(localbe1, localbe2);
    be localbe3 = VMapProjection.PixelsToTile(localbe1.a >> i2, localbe1.b >> i2);
    be localbe4 = VMapProjection.PixelsToTile(localbe2.a >> i2, localbe2.b >> i2);
    int i3 = localbe4.a - localbe3.a;
    int i4 = localbe4.b - localbe3.b;
    localArrayList.clear();
    for (int i5 = 0; i5 <= i4; i5++)
      for (int i6 = 0; i6 <= i3; i6++)
        localArrayList.add(VMapProjection.TileToQuadKey(i6 + localbe3.a, i5 + localbe3.b, i1));
    return localArrayList;
  }

  ArrayList<String> f()
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    be localbe1 = new be();
    be localbe2 = new be();
    be localbe3 = new be();
    a(localbe1, localbe2);
    getPixelPnt(new Point(this.width / 2, this.height / 2), localbe3);
    be localbe4 = VMapProjection.PixelsToTile(localbe1.a, localbe1.b);
    be localbe5 = VMapProjection.PixelsToTile(localbe2.a, localbe2.b);
    be localbe6 = VMapProjection.PixelsToTile(localbe3.a, localbe3.b);
    int i1 = localbe5.a - localbe4.a;
    int i2 = localbe5.b - localbe4.b;
    a locala = new a(localbe6.a, localbe6.b);
    for (int i3 = 0; i3 <= i2; i3++)
      for (int i5 = 0; i5 <= i1; i5++)
        localArrayList1.add(new be(i5 + localbe4.a, i3 + localbe4.b));
    Collections.sort(localArrayList1, locala);
    for (int i4 = 0; i4 < localArrayList1.size(); i4++)
    {
      be localbe7 = (be)localArrayList1.get(i4);
      localArrayList2.add(VMapProjection.TileToQuadKey(localbe7.a, localbe7.b, this.mapLevel));
    }
    return localArrayList2;
  }

  protected PointF fromScreenPoint(PointF paramPointF)
  {
    PointF localPointF = new PointF();
    int i1 = getWidth();
    int i2 = getHeight();
    float f1 = paramPointF.x - (i1 >> 1);
    float f2 = paramPointF.y - (i2 >> 1);
    double d1 = Math.atan2(f2, f1);
    double d2 = Math.sqrt(Math.pow(f1, 2.0D) + Math.pow(f2, 2.0D));
    double d3 = d1 + 3.141592653589793D * getMapAngle() / 180.0D;
    localPointF.x = (float)(d2 * Math.cos(d3) + (i1 >> 1));
    localPointF.y = (float)(d2 * Math.sin(d3) + (i2 >> 1));
    return localPointF;
  }

  protected LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-2, -2, 0, 0, 51);
  }

  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(this.k, paramAttributeSet);
  }

  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new LayoutParams(paramLayoutParams);
  }

  protected HttpURLConnection getConnection(String paramString)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)this.A.getSystemService("connectivity")).getActiveNetworkInfo();
    if (localNetworkInfo != null)
      if (localNetworkInfo.getType() == 1)
      {
        android.net.Proxy.getHost(this.A);
        android.net.Proxy.getPort(this.A);
      }
    for (java.net.Proxy localProxy = null; ; localProxy = null)
      try
      {
        while (true)
        {
          URL localURL = new URL(j.a().f() + "/bmserver/VMMV2?" + paramString);
          if (localProxy != null)
          {
            HttpURLConnection localHttpURLConnection1 = (HttpURLConnection)localURL.openConnection(localProxy);
            return localHttpURLConnection1;
            String str = android.net.Proxy.getDefaultHost();
            int i1 = android.net.Proxy.getDefaultPort();
            if (str == null)
              break;
            localProxy = new java.net.Proxy(Proxy.Type.HTTP, new InetSocketAddress(str, i1));
            continue;
          }
          else
          {
            HttpURLConnection localHttpURLConnection2 = (HttpURLConnection)localURL.openConnection();
            return localHttpURLConnection2;
          }
        }
      }
      catch (IOException localIOException)
      {
        return null;
      }
  }

  public MapController getController()
  {
    return this.n;
  }

  public String getDebugVersion()
  {
    return c.k;
  }

  protected int getGridLevelOff(int paramInt)
  {
    int i1 = 4;
    switch (paramInt)
    {
    default:
      i1 = 0;
    case 2:
    case 6:
      return i1;
    case 10:
      return 2;
    case 12:
      return 2;
    case 14:
    }
    return 7;
  }

  public int getLatitudeSpan()
  {
    return this.l.a.a();
  }

  public v getLayerMgr()
  {
    if (isVectorMap() == true)
      return null;
    if (this.q == null)
    {
      this.q = new v();
      this.q.a(this);
    }
    return this.q;
  }

  public int getLongitudeSpan()
  {
    return this.l.a.b();
  }

  public int getMapAngle()
  {
    if (this.VMapMode)
      return this.mapAngle;
    return 0;
  }

  public GeoPoint getMapCenter()
  {
    return this.l.b.f();
  }

  public int getMaxZoomLevel()
  {
    return this.l.b.a();
  }

  public double getMetersPerPixel(int paramInt)
  {
    if ((paramInt >= getMinZoomLevel()) && (paramInt <= getMaxZoomLevel()))
      return a().g.h[paramInt];
    return 0.0D;
  }

  public int getMinZoomLevel()
  {
    return this.l.b.b();
  }

  public boolean getOverlayDrawing()
  {
    return this.r;
  }

  public final List<Overlay> getOverlays()
  {
    return this.l.d.c();
  }

  protected void getPixel20Pnt(Point paramPoint, be parambe, int paramInt1, int paramInt2)
  {
    int i1 = 20 - this.mapLevel;
    parambe.a = (paramInt1 + (paramPoint.x - (this.width >> 1) << i1));
    parambe.b = (paramInt2 + (paramPoint.y - (this.height >> 1) << i1));
  }

  protected void getPixel20Pnt2(Point paramPoint, be parambe, int paramInt1, int paramInt2)
  {
    int i1 = 20 - this.mapLevel;
    int i2 = paramPoint.x - (this.width >> 1) << i1;
    int i3 = paramPoint.y - (this.height >> 1) << i1;
    double d1 = Math.atan2(i3, i2);
    double d2 = Math.sqrt(Math.pow(i2, 2.0D) + Math.pow(i3, 2.0D));
    double d3 = d1 - 3.141592653589793D * this.mapAngle / 180.0D;
    parambe.a = (paramInt1 + (int)(0.5D + d2 * Math.cos(d3)));
    parambe.b = (paramInt2 + (int)(0.5D + d2 * Math.sin(d3)));
  }

  protected void getPixelPnt(Point paramPoint, be parambe)
  {
    getPixel20Pnt2(paramPoint, parambe, this.centerX, this.centerY);
    parambe.a >>= 20 - this.mapLevel;
    parambe.b >>= 20 - this.mapLevel;
  }

  public Projection getProjection()
  {
    return this.l.a;
  }

  public String getReleaseVersion()
  {
    return c.l;
  }

  public int getScale(int paramInt)
  {
    if ((paramInt >= getMinZoomLevel()) && (paramInt <= getMaxZoomLevel()) && (paramInt >= 0) && (paramInt <= this.t.length))
      return this.t[(paramInt - 1)];
    return -1;
  }

  protected void getScreenPnt(int paramInt1, int paramInt2, Point paramPoint)
  {
    getScreenPntBy20Pixel(paramInt1 << 20 - this.mapLevel, paramInt2 << 20 - this.mapLevel, paramPoint);
  }

  protected void getScreenPntBy20Pixel(int paramInt1, int paramInt2, int paramInt3, Point paramPoint)
  {
    int i1 = 20 - paramInt3;
    paramPoint.x = (paramInt1 >> i1);
    paramPoint.y = (paramInt2 >> i1);
    paramPoint.x = ((this.width >> 1) - (this.centerX >> i1) + paramPoint.x);
    paramPoint.y = ((this.height >> 1) - (this.centerY >> i1) + paramPoint.y);
  }

  protected void getScreenPntBy20Pixel(int paramInt1, int paramInt2, Point paramPoint)
  {
    int i1 = 20 - this.mapLevel;
    paramPoint.x = (paramInt1 >> i1);
    paramPoint.y = (paramInt2 >> i1);
    new be();
    paramPoint.x = ((this.width >> 1) - (this.centerX >> i1) + paramPoint.x);
    paramPoint.y = ((this.height >> 1) - (this.centerY >> i1) + paramPoint.y);
  }

  public ZoomButtonsController getZoomButtonsController()
  {
    if (this.d == null)
      this.d = new ZoomButtonsController(this);
    return this.d;
  }

  public int getZoomLevel()
  {
    return this.l.b.e();
  }

  public g getZoomMgr()
  {
    return this.o;
  }

  protected boolean isAGridsInScreen(ArrayList<String> paramArrayList)
  {
    be localbe1 = new be();
    be localbe2 = new be();
    int i1 = c(this.mapLevel);
    int i2 = this.mapLevel - i1;
    a(localbe1, localbe2);
    be localbe3 = VMapProjection.PixelsToTile(localbe1.a >> i2, localbe1.b >> i2);
    be localbe4 = VMapProjection.PixelsToTile(localbe2.a >> i2, localbe2.b >> i2);
    int i3 = 0;
    int i4 = paramArrayList.size();
    int i5 = 0;
    if (i3 < i4)
    {
      if (((String)paramArrayList.get(i3)).length() != i1);
      Point localPoint;
      do
      {
        i3++;
        break;
        localPoint = VMapProjection.QuadKeyToTile((String)paramArrayList.get(i3));
      }
      while ((localPoint.x < localbe3.a) || (localPoint.x > localbe4.a) || (localPoint.y < localbe3.b) || (localPoint.y > localbe4.b));
      i5 = 1;
    }
    return i5;
  }

  public boolean isDoubleClickZooming()
  {
    return this.u;
  }

  protected boolean isGridInScreen(String paramString)
  {
    be localbe1 = new be();
    be localbe2 = new be();
    int i1 = c(this.mapLevel);
    int i2 = this.mapLevel - i1;
    a(localbe1, localbe2);
    be localbe3 = VMapProjection.PixelsToTile(localbe1.a >> i2, localbe1.b >> i2);
    be localbe4 = VMapProjection.PixelsToTile(localbe2.a >> i2, localbe2.b >> i2);
    Point localPoint = VMapProjection.QuadKeyToTile(paramString);
    if (paramString.length() != i1);
    do
      return false;
    while ((localPoint.x < localbe3.a) || (localPoint.x > localbe4.a) || (localPoint.y < localbe3.b) || (localPoint.y > localbe4.b));
    return true;
  }

  public boolean isSatellite()
  {
    w localw = a().d.a(a().d.h);
    if (localw != null)
      return localw.f;
    return false;
  }

  public boolean isStreetView()
  {
    return false;
  }

  public boolean isTraffic()
  {
    boolean bool1 = isSatellite();
    boolean bool2 = false;
    if (!bool1)
    {
      String str = a().d.i;
      w localw = a().d.a(str);
      bool2 = false;
      if (localw != null)
        bool2 = localw.f;
    }
    return bool2;
  }

  public boolean isVectorMap()
  {
    return this.VMapMode;
  }

  protected void loadBMtilesData2(ArrayList paramArrayList, boolean paramBoolean)
  {
    monitorenter;
    label163: label168: label174: label178: 
    while (true)
    {
      ArrayList localArrayList;
      int i1;
      int i2;
      try
      {
        localArrayList = new ArrayList();
        i1 = 0;
        if (i1 < paramArrayList.size())
        {
          String str = (String)paramArrayList.get(i1);
          if ((this.f == null) || (!this.f.hasGridData(str)))
            break label174;
          i2 = 1;
          break label163;
          if (this.tileDownloadCtrl.b(str))
            break label168;
          localArrayList.add(str);
        }
      }
      finally
      {
        monitorexit;
      }
      if (localArrayList.size() > 0)
        if (paramBoolean)
        {
          this.tileDownloadCtrl.a = 0;
          this.tileDownloadCtrl.b();
          sendMapDataRequest(localArrayList);
        }
      while (true)
      {
        monitorexit;
        return;
        ax localax = this.tileDownloadCtrl;
        localax.a += localArrayList.size();
        this.tileDownloadCtrl.b();
        continue;
        this.tileDownloadCtrl.a = 0;
      }
      while (true)
      {
        if (i2 == 0)
          break label178;
        i1++;
        break;
        i2 = 0;
      }
    }
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
  }

  protected void onDetachedFromWindow()
  {
    if ((this.VMapMode) && (this.isInited))
      VdestoryMap();
    super.onDetachedFromWindow();
  }

  protected final void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
  }

  public void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    int i1;
    if (this.l == null)
      i1 = 1;
    boolean bool2;
    do
    {
      boolean bool1;
      do
      {
        return i1;
        bool1 = this.p;
        i1 = 0;
      }
      while (!bool1);
      if (this.l.d.a(paramInt, paramKeyEvent))
        break;
      bool2 = this.n.onKey(this, paramInt, paramKeyEvent);
      i1 = 0;
    }
    while (!bool2);
    return true;
  }

  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    int i1;
    if (this.l == null)
      i1 = 1;
    boolean bool2;
    do
    {
      boolean bool1;
      do
      {
        return i1;
        bool1 = this.p;
        i1 = 0;
      }
      while (!bool1);
      if (this.l.d.b(paramInt, paramKeyEvent))
        break;
      bool2 = this.n.onKey(this, paramInt, paramKeyEvent);
      i1 = 0;
    }
    while (!bool2);
    return true;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.l != null)
      this.l.b.h();
  }

  protected final void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
  }

  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    super.onRestoreInstanceState(paramParcelable);
  }

  protected Parcelable onSaveInstanceState()
  {
    return super.onSaveInstanceState();
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.o.d();
    if (this.VMapMode)
    {
      if (this.isInited)
        VdestoryMap();
      this.width = getWidth();
      this.height = getHeight();
      Vinit();
      this.isInited = true;
    }
    Point localPoint = new Point(paramInt1 / 2, paramInt2 / 2);
    this.l.g.a(localPoint);
    g.a(this.o, paramInt1, paramInt2);
    this.mRouteCtrl.a(paramInt1, paramInt2);
    this.l.b.a(paramInt1, paramInt2);
    if ((this.n.getReqLatSpan() != 0) && (this.n.getReqLngSpan() != 0))
    {
      this.n.zoomToSpan(this.n.getReqLatSpan(), this.n.getReqLngSpan());
      this.n.setReqLatSpan(0);
      this.n.setReqLngSpan(0);
    }
    if (this.z != null)
      this.z.a(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (!c.m);
    do
    {
      do
        return true;
      while (this.l == null);
      if (!this.p)
        return false;
    }
    while (this.l.d.b(paramMotionEvent));
    this.m.b(paramMotionEvent);
    return super.onTouchEvent(paramMotionEvent);
  }

  public boolean onTrackballEvent(MotionEvent paramMotionEvent)
  {
    if (this.l == null);
    do
    {
      return true;
      if ((!this.p) || (this.w == null))
        return false;
      this.v = TrackballGestureDetector.getInstance();
      this.v.analyze(paramMotionEvent);
      this.w.onTrackballChange(this.v);
    }
    while (this.l.d.a(paramMotionEvent));
    return this.m.a(paramMotionEvent);
  }

  public void onWindowFocusChanged(boolean paramBoolean)
  {
    super.onWindowFocusChanged(paramBoolean);
  }

  protected void onWindowVisibilityChanged(int paramInt)
  {
    if (!this.VMapMode)
    {
      super.onWindowVisibilityChanged(paramInt);
      return;
    }
    if (paramInt == 8)
      if (this.isInited)
        VdestoryMap();
    while (true)
    {
      super.onWindowVisibilityChanged(paramInt);
      return;
      if (paramInt != 0)
        continue;
      this.bfirstDrawed = false;
      int i1 = getWidth();
      int i2 = getHeight();
      if ((i1 <= 0) || (i2 <= 0))
        continue;
      this.width = getWidth();
      this.height = getHeight();
      Vinit();
      this.isInited = true;
    }
  }

  public void paintVectorMap(Canvas paramCanvas)
  {
    if (!this.x)
    {
      setBackgroundColor(0xFF000000 | this.f.getBKColor(this.mapLevel));
      this.x = true;
    }
    loadBMtilesData2(e(), false);
    ArrayList localArrayList1 = f();
    paramCanvas.save();
    Matrix localMatrix1 = paramCanvas.getMatrix();
    localMatrix1.preRotate(-this.mapAngle, this.width / 2, this.height / 2);
    paramCanvas.setMatrix(localMatrix1);
    ArrayList localArrayList2 = new ArrayList();
    Hashtable localHashtable = new Hashtable();
    int i1 = 0;
    String str3;
    if (i1 < localArrayList1.size())
    {
      str3 = (String)localArrayList1.get(i1);
      if (this.f.hasBitMapData(str3))
        localArrayList2.add(str3);
    }
    label448: label743: 
    while (true)
    {
      i1++;
      break;
      if (!this.j.a(str3))
      {
        ap localap = new ap();
        localap.a = str3;
        this.j.a(localap);
      }
      if (str3.length() != 0);
      for (String str4 = str3.substring(0, -1 + str3.length()); ; str4 = "")
      {
        if (!this.f.hasBitMapData(str4))
          break label743;
        localHashtable.put(str4, str4);
        break;
        Enumeration localEnumeration;
        if (localHashtable.size() > 0)
          localEnumeration = localHashtable.elements();
        while (true)
        {
          String str2;
          Point localPoint2;
          if (localEnumeration.hasMoreElements())
          {
            str2 = (String)localEnumeration.nextElement();
            if (!this.f.hasBitMapData(str2))
              continue;
            be localbe2 = VMapProjection.QuadKeyToTitle(str2);
            int i6 = 256 * localbe2.a << 20 - str2.length();
            int i7 = 256 * localbe2.b << 20 - str2.length();
            localPoint2 = new Point();
            getScreenPntBy20Pixel(i6, i7, -1 + this.mapLevel, localPoint2);
            paramCanvas.save();
            Matrix localMatrix2 = paramCanvas.getMatrix();
            localMatrix2.preScale(2.0F, 2.0F, this.width / 2, this.height / 2);
            paramCanvas.setMatrix(localMatrix2);
          }
          try
          {
            this.f.fillBitmapBufferData(str2, this.g.array());
            this.h.copyPixelsFromBuffer(this.g);
            paramCanvas.drawBitmap(this.h, localPoint2.x, localPoint2.y, null);
            paramCanvas.restore();
            continue;
            int i2 = 0;
            while (true)
              if (i2 < localArrayList2.size())
              {
                String str1 = (String)localArrayList2.get(i2);
                Point localPoint1;
                if (this.f.hasBitMapData(str1))
                {
                  be localbe1 = VMapProjection.QuadKeyToTitle(str1);
                  int i4 = 256 * localbe1.a << 20 - str1.length();
                  int i5 = 256 * localbe1.b << 20 - str1.length();
                  localPoint1 = new Point();
                  getScreenPntBy20Pixel(i4, i5, this.mapLevel, localPoint1);
                }
                try
                {
                  this.f.fillBitmapBufferData(str1, this.g.array());
                  this.h.copyPixelsFromBuffer(this.g);
                  paramCanvas.drawBitmap(this.h, localPoint1.x, localPoint1.y, null);
                  i2++;
                }
                catch (Exception localException1)
                {
                  while (true)
                    localException1.printStackTrace();
                }
              }
            paramCanvas.restore();
            for (int i3 = this.mapAngle; i3 < 0; i3 += 360);
            while (i3 > 360)
              i3 -= 360;
            NativeMap localNativeMap = new NativeMap();
            localNativeMap.initMap(null, this.width, this.height);
            localNativeMap.setMapParameter(this.centerX, this.centerY, this.mapLevel, i3);
            localNativeMap.paintMap(this.f, 0);
            localNativeMap.paintLables(this.f, paramCanvas, 2);
            return;
          }
          catch (Exception localException2)
          {
            break label448;
          }
        }
      }
    }
  }

  public void preLoad()
  {
  }

  public void registerMapViewListener(MapViewListener paramMapViewListener)
  {
    this.l.b.b.add(paramMapViewListener);
  }

  public void registerTrackballListener(TrackballGestureDetector.OnTrackballListener paramOnTrackballListener)
  {
    this.w = paramOnTrackballListener;
  }

  protected void sendMapDataRequest(ArrayList paramArrayList)
  {
    if (paramArrayList.size() == 0)
      return;
    ac localac = new ac(this);
    localac.e = this.mapLevel;
    this.i.a();
    for (int i1 = 0; i1 < paramArrayList.size(); i1++)
    {
      String str = (String)paramArrayList.get(i1);
      this.tileDownloadCtrl.c(str);
      localac.b(str);
    }
    this.i.a(localac);
  }

  public void setBuiltInZoomControls(boolean paramBoolean)
  {
    this.o.b(paramBoolean);
  }

  public void setClickable(boolean paramBoolean)
  {
    this.p = paramBoolean;
    super.setClickable(paramBoolean);
  }

  public void setDoubleClickZooming(boolean paramBoolean)
  {
    this.u = paramBoolean;
  }

  public void setEnabled(boolean paramBoolean)
  {
    c.m = paramBoolean;
    super.setEnabled(paramBoolean);
  }

  public void setMapAngle(int paramInt)
  {
    if (this.VMapMode)
    {
      this.mapAngle = paramInt;
      this.l.b.a(true, false);
    }
  }

  protected void setMapCenter(int paramInt1, int paramInt2)
  {
    if ((paramInt1 >= 0) && (paramInt1 <= 268435455) && (paramInt2 >= 20) && (paramInt2 <= 268435431))
    {
      this.centerX = paramInt1;
      this.centerY = paramInt2;
    }
  }

  protected void setMapCenterScreen(int paramInt1, int paramInt2)
  {
    be localbe = new be();
    getPixel20Pnt2(new Point(paramInt1, paramInt2), localbe, this.centerX, this.centerY);
    setMapCenter(localbe.a, localbe.b);
  }

  public void setMapMoveEnable(boolean paramBoolean)
  {
    c.n = paramBoolean;
  }

  public void setMapProjectSetting(ae paramae)
  {
    this.l.g.a(paramae);
  }

  public void setMapviewSizeChangedListener(d paramd)
  {
    this.z = paramd;
  }

  public void setOverlayDrawing(boolean paramBoolean)
  {
    this.r = paramBoolean;
  }

  public void setReticleDrawMode(ReticleDrawMode paramReticleDrawMode)
  {
    this.l.d.a(paramReticleDrawMode);
  }

  public void setSatellite(boolean paramBoolean)
  {
    if (isSatellite() == paramBoolean)
      return;
    boolean bool = isTraffic();
    setTraffic(false);
    if (!paramBoolean)
    {
      a().d.a(a().d.h, false);
      a().d.a(a().d.g, true);
      if (this.s)
        setVectorMap(true);
      while (true)
      {
        if (bool == true)
          setTraffic(true);
        a().b.a(false, false);
        return;
        setVectorMap(false);
      }
    }
    this.s = isVectorMap();
    setVectorMap(false);
    a().b(true);
    if (a().d.a(a().d.h) != null)
    {
      a().d.a(a().d.h, true);
      if (bool == true)
        setTraffic(true);
      a().b.a(false, false);
      return;
    }
    w localw = new w();
    localw.j = new bd()
    {
      public String a(int paramInt1, int paramInt2, int paramInt3)
      {
        return j.a().e() + "/appmaptile?z=" + paramInt3 + "&x=" + paramInt1 + "&y=" + paramInt2 + "&lang=zh_cn&size=1&scale=1&style=6";
      }
    };
    localw.a = a().d.h;
    localw.e = true;
    localw.d = true;
    localw.f = true;
    localw.g = true;
    localw.b = c.a;
    localw.c = c.b;
    a().d.a(localw, getContext());
    a().d.a(a().d.h, true);
    if (bool == true)
      setTraffic(true);
    a().b.a(false, false);
  }

  public void setScreenHotPoint(Point paramPoint)
  {
    this.l.g.a(paramPoint);
    this.l.b.a(false, false);
  }

  public void setServerUrl(s params)
  {
    if (params == null);
    do
    {
      return;
      if ((params.d != null) && (!params.d.equals("")))
        j.a().a(params.d);
      if ((params.e != null) && (!params.e.equals("")))
        j.a().b(params.e);
      if ((params.c != null) && (!params.c.equals("")))
        j.a().e(params.c);
      if ((params.a != null) && (!params.a.equals("")))
        j.a().c(params.a);
      if ((params.b == null) || (params.b.equals("")))
        continue;
      j.a().d(params.b);
    }
    while ((params.f == null) || (params.f.equals("")));
    j.a().f(params.f);
  }

  public void setStreetView(boolean paramBoolean)
  {
  }

  public void setTraffic(boolean paramBoolean)
  {
    if (paramBoolean == isTraffic());
    boolean bool;
    do
    {
      return;
      bool = isSatellite();
    }
    while (bool);
    String str = a().d.i;
    if (!paramBoolean)
    {
      a().d.a(str, false);
      a().b.a(false, false);
      return;
    }
    if (a().d.a(str) != null)
    {
      a().d.a(str, true);
      a().b.a(false, false);
      return;
    }
    if (bool == true)
    {
      w localw1 = new w();
      localw1.h = true;
      localw1.i = 120000L;
      localw1.a = str;
      localw1.e = false;
      localw1.d = true;
      localw1.f = true;
      localw1.g = false;
      localw1.b = 18;
      localw1.c = 9;
      a().d.a(localw1, getContext());
    }
    while (true)
    {
      a().d.a(str, true);
      a().b.a(false, false);
      return;
      w localw2 = new w();
      localw2.h = true;
      localw2.i = 120000L;
      localw2.j = new bd()
      {
        public String a(int paramInt1, int paramInt2, int paramInt3)
        {
          int i = 17 - paramInt3;
          return j.a().c() + "/trafficengine/mapabc/traffictile?v=w2.61&zoom=" + i + "&x=" + paramInt1 + "&y=" + paramInt2;
        }
      };
      localw2.a = str;
      localw2.e = false;
      localw2.d = true;
      localw2.f = true;
      localw2.g = false;
      localw2.b = 18;
      localw2.c = 9;
      a().d.a(localw2, getContext());
    }
  }

  public void setVectorMap(boolean paramBoolean)
  {
    a().b(false);
    if (this.VMapMode == paramBoolean)
      return;
    GeoPoint localGeoPoint = getMapCenter();
    int i1 = a().b.e();
    this.VMapMode = paramBoolean;
    if (paramBoolean == true)
    {
      a().b.b(c.c);
      a().b.c(c.d);
      a().g.a();
      ay localay2 = (ay)a().e.a(0);
      localay2.b();
      localay2.c();
      localay2.a();
      a().e.a(null, 0);
      if ((bf)a().e.a(1) == null)
      {
        bf localbf2 = new bf(a(), getContext());
        a().e.a(localbf2, 1);
      }
      this.width = getWidth();
      this.height = getHeight();
      Vinit();
      if (isSatellite())
        setSatellite(false);
      this.n.setCenter(localGeoPoint);
      a().b.a(i1);
      a().b.a(false, false);
      getZoomMgr().d();
      return;
    }
    if (i1 <= c.b);
    for (int i2 = c.b; ; i2 = i1)
    {
      if (i2 >= c.a);
      for (i1 = c.a; ; i1 = i2)
      {
        a().b.b(c.a);
        a().b.c(c.b);
        a().g.a();
        bf localbf1 = (bf)a().e.a(1);
        if (localbf1 != null)
        {
          localbf1.b();
          localbf1.c();
          localbf1.a();
          a().e.a(null, 1);
          VdestoryMap();
        }
        if ((ay)a().e.a(0) != null)
          break;
        ay localay1 = new ay(a(), getContext());
        localay1.a_();
        localay1.d();
        a().e.a(localay1, 0);
        break;
      }
    }
  }

  protected PointF toScreenPoint(PointF paramPointF)
  {
    PointF localPointF = new PointF();
    int i1 = getWidth();
    int i2 = getHeight();
    float f1 = paramPointF.x - (i1 >> 1);
    float f2 = paramPointF.y - (i2 >> 1);
    double d1 = Math.atan2(f2, f1);
    double d2 = Math.sqrt(Math.pow(f1, 2.0D) + Math.pow(f2, 2.0D));
    double d3 = d1 - 3.141592653589793D * getMapAngle() / 180.0D;
    localPointF.x = (float)(d2 * Math.cos(d3) + (i1 >> 1));
    localPointF.y = (float)(d2 * Math.sin(d3) + (i2 >> 1));
    return localPointF;
  }

  public void unRegisterMapViewListener(MapViewListener paramMapViewListener)
  {
    this.l.b.b.remove(paramMapViewListener);
  }

  public static class LayoutParams extends ViewGroup.LayoutParams
  {
    public static final int BOTTOM = 80;
    public static final int BOTTOM_CENTER = 81;
    public static final int CENTER = 17;
    public static final int CENTER_HORIZONTAL = 1;
    public static final int CENTER_VERTICAL = 16;
    public static final int LEFT = 3;
    public static final int MODE_MAP = 0;
    public static final int MODE_VIEW = 1;
    public static final int RIGHT = 5;
    public static final int TOP = 48;
    public static final int TOP_LEFT = 51;
    public int alignment = 51;
    public int mode = 1;
    public GeoPoint point = null;
    public int x = 0;
    public int y = 0;

    public LayoutParams(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      super(paramInt2);
      this.x = paramInt3;
      this.y = paramInt4;
      this.alignment = paramInt5;
    }

    public LayoutParams(int paramInt1, int paramInt2, GeoPoint paramGeoPoint, int paramInt3)
    {
      this(paramInt1, paramInt2, paramGeoPoint, 0, 0, paramInt3);
    }

    public LayoutParams(int paramInt1, int paramInt2, GeoPoint paramGeoPoint, int paramInt3, int paramInt4, int paramInt5)
    {
      super(paramInt2);
      this.mode = 0;
      this.point = paramGeoPoint;
      this.x = paramInt3;
      this.y = paramInt4;
      this.alignment = paramInt5;
    }

    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }

    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
  }

  public static enum ReticleDrawMode
  {
    static
    {
      ReticleDrawMode[] arrayOfReticleDrawMode = new ReticleDrawMode[3];
      arrayOfReticleDrawMode[0] = DRAW_RETICLE_NEVER;
      arrayOfReticleDrawMode[1] = DRAW_RETICLE_OVER;
      arrayOfReticleDrawMode[2] = DRAW_RETICLE_UNDER;
      a = arrayOfReticleDrawMode;
    }
  }

  class a
    implements Comparator
  {
    int a;
    int b;

    public a(int paramInt1, int arg3)
    {
      this.a = paramInt1;
      int i;
      this.b = i;
    }

    public int compare(Object paramObject1, Object paramObject2)
    {
      be localbe1 = (be)paramObject1;
      be localbe2 = (be)paramObject2;
      int i = localbe1.a - this.a;
      int j = localbe1.b - this.b;
      int k = localbe2.a - this.a;
      int m = localbe2.b - this.b;
      int n = i * i + j * j;
      int i1 = k * k + m * m;
      if (n > i1)
        return 1;
      if (n < i1)
        return -1;
      return 0;
    }
  }

  public static abstract interface b
  {
    public abstract void a(int paramInt);
  }

  public class c extends ImageView
    implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener, aj.b
  {
    protected boolean a = false;
    private Point c = null;
    private GestureDetector d = new GestureDetector(this);
    private aj e = aj.a(localContext, this);
    private boolean f = false;
    private ArrayList<GestureDetector.OnGestureListener> g = new ArrayList();
    private ArrayList<aj.b> h = new ArrayList();
    private Scroller i;
    private int j = 0;
    private int k = 0;
    private Matrix l = new Matrix();
    private float m = 1.0F;
    private boolean n = false;
    private float o;
    private float p;
    private int q;
    private int r;
    private long s = 0L;
    private int t = 0;
    private int u = 0;
    private final long v = 300L;

    public c(Context arg2)
    {
      super();
      this.i = new Scroller(localContext);
      new DisplayMetrics();
      DisplayMetrics localDisplayMetrics = localContext.getApplicationContext().getResources().getDisplayMetrics();
      this.q = localDisplayMetrics.widthPixels;
      this.r = localDisplayMetrics.heightPixels;
      this.j = (localDisplayMetrics.widthPixels / 2);
      this.k = (localDisplayMetrics.heightPixels / 2);
    }

    private void a(float paramFloat1, PointF paramPointF, float paramFloat2, float paramFloat3)
    {
      int i1 = MapView.b(MapView.this).b.c() / 2;
      int i2 = MapView.b(MapView.this).b.d() / 2;
      int i3;
      boolean bool;
      ZoomButtonsController.OnZoomListener localOnZoomListener;
      if (paramFloat1 > 0.0F)
      {
        i3 = (int)Math.floor(paramFloat1);
        bool = true;
        localOnZoomListener = MapView.this.getZoomButtonsController().getOnZoomListener();
        if (!bool)
          break label252;
      }
      label252: for (int i4 = i3 + MapView.b(MapView.this).b.e(); ; i4 = MapView.b(MapView.this).b.e() - i3)
      {
        int i5 = MapView.this.b(i4);
        if (i5 != MapView.b(MapView.this).b.e())
        {
          MapView.this.c[0] = MapView.this.c[1];
          MapView.this.c[1] = i5;
          if (MapView.this.c[0] != MapView.this.c[1])
          {
            if (localOnZoomListener != null)
              localOnZoomListener.onZoom(bool);
            GeoPoint localGeoPoint = MapView.b(MapView.this).a.fromPixels(i1, i2);
            MapView.b(MapView.this).b.a(i5);
            MapView.b(MapView.this).b.a(localGeoPoint);
          }
        }
        do
          return;
        while (paramFloat1 >= 0.0F);
        i3 = (int)Math.floor(Math.abs(paramFloat1));
        bool = false;
        break;
      }
    }

    private void a(int paramInt1, int paramInt2)
    {
      if (this.c == null)
        return;
      this.t = paramInt1;
      this.u = paramInt2;
      e();
    }

    private void e()
    {
      if (this.c == null)
        return;
      int i1 = this.c.x - this.t;
      int i2 = this.c.y - this.u;
      this.c.x = this.t;
      this.c.y = this.u;
      MapView.d(MapView.this).scrollBy(i1, i2);
    }

    public aj a()
    {
      return this.e;
    }

    public void a(float paramFloat)
    {
      this.m = paramFloat;
    }

    public void a(long paramLong)
    {
      this.s = paramLong;
    }

    public void a(GestureDetector.OnGestureListener paramOnGestureListener)
    {
      this.g.add(paramOnGestureListener);
    }

    public boolean a(float paramFloat1, float paramFloat2)
    {
      MapView.d(MapView.this).stopAnimation(true);
      if (this.n)
      {
        this.o = (paramFloat1 + this.o);
        this.p = (paramFloat2 + this.p);
      }
      invalidate();
      return this.n;
    }

    public boolean a(float paramFloat, PointF paramPointF)
    {
      MapView.b(MapView.this).d.f = false;
      a(paramFloat, paramPointF, this.o, this.p);
      this.n = false;
      postInvalidateDelayed(8L);
      MapView.b(MapView.this).a(true);
      return true;
    }

    public boolean a(Matrix paramMatrix)
    {
      return false;
    }

    public boolean a(PointF paramPointF)
    {
      MapView.b(MapView.this).a(MapView.e(MapView.this));
      MapView.b(MapView.this).d.a(true);
      MapView.b(MapView.this).d.f = true;
      this.n = true;
      return true;
    }

    public boolean a(MotionEvent paramMotionEvent)
    {
      MapView.c(MapView.this).a();
      int i1 = paramMotionEvent.getAction();
      int i2 = (int)paramMotionEvent.getX();
      int i3 = (int)paramMotionEvent.getY();
      this.c = null;
      switch (i1)
      {
      case 1:
      default:
      case 0:
      case 2:
      }
      while (true)
      {
        return false;
        this.c = new Point(i2, i3);
        continue;
        MapView.d(MapView.this).scrollBy((int)(25.0F * paramMotionEvent.getX()), (int)(25.0F * paramMotionEvent.getY()));
      }
    }

    public float b()
    {
      return this.m;
    }

    public void b(GestureDetector.OnGestureListener paramOnGestureListener)
    {
      this.g.remove(paramOnGestureListener);
    }

    public boolean b(float paramFloat)
    {
      a(paramFloat);
      return false;
    }

    public boolean b(Matrix paramMatrix)
    {
      this.l.set(paramMatrix);
      postInvalidate();
      return true;
    }

    public boolean b(MotionEvent paramMotionEvent)
    {
      MapView.c(MapView.this).a();
      boolean bool1 = this.e.a(paramMotionEvent);
      if (!bool1);
      for (boolean bool2 = this.d.onTouchEvent(paramMotionEvent); ; bool2 = bool1)
      {
        if ((paramMotionEvent.getAction() == 1) && (this.a) && (MapView.b(MapView.this).b.b.size() > 0))
        {
          Iterator localIterator = MapView.b(MapView.this).b.b.iterator();
          while (localIterator.hasNext())
            ((MapViewListener)localIterator.next()).onMapMoveFinish();
        }
        return bool2;
      }
    }

    public void c()
    {
      this.o = 0.0F;
      this.p = 0.0F;
    }

    public long d()
    {
      return this.s;
    }

    public boolean onDoubleTap(MotionEvent paramMotionEvent)
    {
      if (MapView.f(MapView.this))
        MapView.d(MapView.this).zoomInFixing((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
      return true;
    }

    public boolean onDoubleTapEvent(MotionEvent paramMotionEvent)
    {
      return false;
    }

    public boolean onDown(MotionEvent paramMotionEvent)
    {
      this.a = false;
      if (this.c == null)
        this.c = new Point((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
      while (true)
      {
        return true;
        this.c.set((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
      }
    }

    protected void onDraw(Canvas paramCanvas)
    {
      MapView.b(MapView.this).b.a(getWidth(), getHeight());
      MapView.b(MapView.this).d.a(paramCanvas, this.l, this.o, this.p);
    }

    public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
    {
      invalidate();
      this.a = false;
      this.i.fling(this.j, this.k, 3 * (int)(-paramFloat1) / 5, 3 * (int)(-paramFloat2) / 5, -this.q, this.q, -this.r, this.r);
      return true;
    }

    public void onLongPress(MotionEvent paramMotionEvent)
    {
    }

    public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
    {
      this.a = true;
      a((int)paramMotionEvent2.getX(), (int)paramMotionEvent2.getY());
      postInvalidate();
      return true;
    }

    public void onShowPress(MotionEvent paramMotionEvent)
    {
    }

    public boolean onSingleTapConfirmed(MotionEvent paramMotionEvent)
    {
      if ((t.a != null) && (e.a() - d() > 1000L))
        t.a.c();
      return false;
    }

    public boolean onSingleTapUp(MotionEvent paramMotionEvent)
    {
      MapView.b(MapView.this).d.c(paramMotionEvent);
      Iterator localIterator = this.g.iterator();
      while (localIterator.hasNext())
        ((GestureDetector.OnGestureListener)localIterator.next()).onSingleTapUp(paramMotionEvent);
      return false;
    }
  }

  public static abstract class d
  {
    public abstract void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  }

  public class e
  {
    public boolean a = false;
    private ImageView[] c = null;
    private Drawable[] d = null;
    private MapView.b e = null;
    private boolean f = false;
    private int g = 130;
    private int h = 85;
    private int i = 50;
    private int j = 35;
    private int k = 30;
    private int l = 25;
    private View.OnClickListener m = new ag(this);

    public e()
    {
    }

    private void a(int paramInt, boolean paramBoolean)
    {
      if (paramBoolean);
      for (int n = paramInt; ; n = paramInt + 4)
      {
        this.c[paramInt].setImageDrawable(this.d[n]);
        return;
      }
    }

    private void b(int paramInt1, int paramInt2)
    {
      String[] arrayOfString = { "overview.png", "detail.png", "prev.png", "next.png", "overview_disable.png", "detail_disable.png", "prev_disable.png", "next_disable.png" };
      int i1;
      for (int n = 0; ; n++)
      {
        i1 = 0;
        if (n >= 8)
          break;
        this.d[n] = c.g.b(MapView.a(MapView.this), arrayOfString[n]);
      }
      while (i1 < 4)
      {
        this.c[i1] = new ImageView(MapView.a(MapView.this));
        this.c[i1].setImageDrawable(this.d[i1]);
        MapView.this.addView(this.c[i1], MapView.this.generateDefaultLayoutParams());
        this.c[i1].setVisibility(4);
        this.c[i1].setOnClickListener(this.m);
        i1++;
      }
    }

    public void a()
    {
      if (this.d != null)
      {
        int i2 = this.d.length;
        int i3 = 0;
        if (i3 < i2)
        {
          if (this.d[i3] == null);
          while (true)
          {
            i3++;
            break;
            BitmapDrawable localBitmapDrawable = (BitmapDrawable)this.d[i3];
            if (localBitmapDrawable == null)
              continue;
            Bitmap localBitmap = localBitmapDrawable.getBitmap();
            if ((localBitmap == null) || (localBitmap.isRecycled() == true))
              continue;
            localBitmap.recycle();
          }
        }
      }
      this.d = null;
      if (this.c != null)
      {
        int n = this.c.length;
        for (int i1 = 0; i1 < n; i1++)
          this.c[i1] = null;
        this.c = null;
      }
    }

    public void a(int paramInt1, int paramInt2)
    {
      if ((this.c == null) || (this.d == null))
        return;
      int n = this.c[0].getDrawable().getIntrinsicWidth();
      int i1;
      int i2;
      if (MapView.a(MapView.this).getResources().getConfiguration().orientation == 1)
      {
        i1 = paramInt1 / 2 - (n + 8);
        i2 = paramInt1 / 2 + (n + 8);
      }
      while (true)
      {
        MapView.LayoutParams localLayoutParams1 = new MapView.LayoutParams(-2, -2, i1, paramInt2, 85);
        MapView.LayoutParams localLayoutParams2 = new MapView.LayoutParams(-2, -2, i1, paramInt2, 83);
        MapView.LayoutParams localLayoutParams3 = new MapView.LayoutParams(-2, -2, i2, paramInt2, 85);
        MapView.LayoutParams localLayoutParams4 = new MapView.LayoutParams(-2, -2, i2, paramInt2, 83);
        MapView.this.updateViewLayout(this.c[0], localLayoutParams1);
        MapView.this.updateViewLayout(this.c[1], localLayoutParams2);
        MapView.this.updateViewLayout(this.c[2], localLayoutParams3);
        MapView.this.updateViewLayout(this.c[3], localLayoutParams4);
        return;
        if (MapView.a(MapView.this).getResources().getConfiguration().orientation == 2)
        {
          i1 = paramInt1 / 2 - (n + 15);
          i2 = paramInt1 / 2 + (n + 15);
          continue;
        }
        i1 = 0;
        i2 = 0;
      }
    }

    public void a(boolean paramBoolean)
    {
      a(2, paramBoolean);
    }

    public void a(boolean paramBoolean, MapView.b paramb)
    {
      int n;
      label87: ImageView localImageView;
      if ((this.c == null) || (this.d == null))
      {
        this.c = new ImageView[4];
        this.d = new Drawable[8];
        if (c.e == 2)
        {
          b(this.g, this.h);
          a(MapView.b(MapView.this).b.c(), MapView.b(MapView.this).b.d());
        }
      }
      else
      {
        this.e = paramb;
        n = 0;
        if (n >= 4)
          break label177;
        localImageView = this.c[n];
        if (!paramBoolean)
          break label171;
      }
      label171: for (int i1 = 0; ; i1 = 4)
      {
        localImageView.setVisibility(i1);
        if (paramBoolean == true)
          this.c[n].bringToFront();
        n++;
        break label87;
        if (c.e == 1)
        {
          b(this.k, this.l);
          break;
        }
        b(this.i, this.j);
        break;
      }
      label177: MapView.g localg = MapView.c(MapView.this);
      boolean bool = false;
      if (!paramBoolean)
        bool = true;
      localg.a(bool);
      this.f = paramBoolean;
    }

    public void b(boolean paramBoolean)
    {
      a(3, paramBoolean);
    }

    public boolean b()
    {
      return this.f;
    }

    public void c(boolean paramBoolean)
    {
      a(1, paramBoolean);
    }

    public void d(boolean paramBoolean)
    {
      a(0, paramBoolean);
    }
  }

  class f extends ImageView
    implements View.OnClickListener
  {
    private int b;

    public f(int arg2)
    {
      super();
      int i;
      this.b = i;
      setClickable(true);
      setOnClickListener(this);
    }

    public void a(boolean paramBoolean)
    {
      if (paramBoolean);
      for (int i = 0; ; i = 4)
      {
        if (getVisibility() != i)
          setVisibility(i);
        return;
      }
    }

    public void b(boolean paramBoolean)
    {
      setFocusable(paramBoolean);
    }

    public void onClick(View paramView)
    {
      if (!c.m);
      do
      {
        do
        {
          return;
          MapView.g(MapView.this);
          if (4097 != this.b)
            continue;
          if ((MapView.b(MapView.this).b.g().VMapMode) && (MapView.b(MapView.this).b.e() < MapView.this.mapLevel) && (MapView.this.mapLevel < MapView.b(MapView.this).b.a()))
          {
            MapView localMapView2 = MapView.this;
            localMapView2.mapLevel += MapView.h(MapView.this);
          }
          MapView.d(MapView.this).a(MapView.h(MapView.this));
          if (MapView.d(MapView.this).a())
            continue;
          MapView.a(MapView.this, 0);
        }
        while (4098 != this.b);
        if ((MapView.b(MapView.this).b.g().VMapMode) && (MapView.b(MapView.this).b.e() < MapView.this.mapLevel) && (MapView.this.mapLevel > MapView.b(MapView.this).b.b()))
        {
          MapView localMapView1 = MapView.this;
          localMapView1.mapLevel -= MapView.h(MapView.this);
        }
        MapView.d(MapView.this).b(MapView.h(MapView.this));
      }
      while (MapView.d(MapView.this).a());
      MapView.a(MapView.this, 0);
    }
  }

  public class g
  {
    Bitmap a = null;
    Bitmap b = null;
    Bitmap c = null;
    Bitmap d = null;
    Bitmap e = null;
    Bitmap f = null;
    StateListDrawable g = null;
    StateListDrawable h = null;
    private MapView.f j;
    private MapView.f k;
    private q l;
    private boolean m = true;
    private boolean n = true;
    private Handler o = new Handler();
    private Runnable p = new ah(this);

    public g(Context arg2)
    {
      h();
      b(false);
    }

    private void a(int paramInt1, int paramInt2)
    {
      MapView.LayoutParams localLayoutParams1 = new MapView.LayoutParams(-2, -2, 1 + paramInt1 / 2, paramInt2 - 8, 83);
      MapView.LayoutParams localLayoutParams2 = new MapView.LayoutParams(-2, -2, -1 + paramInt1 / 2, paramInt2 - 8, 85);
      if (-1 == MapView.this.indexOfChild(this.k))
        MapView.this.addView(this.k, localLayoutParams1);
      while (-1 == MapView.this.indexOfChild(this.j))
      {
        MapView.this.addView(this.j, localLayoutParams2);
        return;
        MapView.this.updateViewLayout(this.k, localLayoutParams1);
      }
      MapView.this.updateViewLayout(this.j, localLayoutParams2);
    }

    private void h()
    {
      this.k = new MapView.f(MapView.this, 4097);
      this.j = new MapView.f(MapView.this, 4098);
      MapView.this.addView(this.k, MapView.this.generateDefaultLayoutParams());
      MapView.this.addView(this.j, MapView.this.generateDefaultLayoutParams());
      this.l = new q(new Rect(0, 0, 0, 0));
      MapView.b(MapView.this).d.a(this.l, true);
      this.g = i();
      this.h = j();
      this.j.setBackgroundDrawable(this.h);
      this.k.setBackgroundDrawable(this.g);
      d();
    }

    private StateListDrawable i()
    {
      StateListDrawable localStateListDrawable = new StateListDrawable();
      if ((this.a == null) || (this.a.isRecycled() == true))
        this.a = c.g.a(c.a.i.ordinal());
      if ((this.b == null) || (this.b.isRecycled() == true))
        this.b = c.g.a(c.a.k.ordinal());
      if ((this.c == null) || (this.c.isRecycled() == true))
        this.c = c.g.a(c.a.m.ordinal());
      BitmapDrawable localBitmapDrawable1 = new BitmapDrawable(this.a);
      BitmapDrawable localBitmapDrawable2 = new BitmapDrawable(this.c);
      BitmapDrawable localBitmapDrawable3 = new BitmapDrawable(this.b);
      localStateListDrawable.addState(MapView.g(), localBitmapDrawable2);
      localStateListDrawable.addState(MapView.h(), localBitmapDrawable1);
      localStateListDrawable.addState(MapView.i(), localBitmapDrawable3);
      return localStateListDrawable;
    }

    private StateListDrawable j()
    {
      StateListDrawable localStateListDrawable = new StateListDrawable();
      if ((this.d == null) || (this.d.isRecycled() == true))
        this.d = c.g.a(c.a.j.ordinal());
      if ((this.e == null) || (this.e.isRecycled() == true))
        this.e = c.g.a(c.a.l.ordinal());
      if ((this.f == null) || (this.f.isRecycled() == true))
        this.f = c.g.a(c.a.n.ordinal());
      BitmapDrawable localBitmapDrawable1 = new BitmapDrawable(this.d);
      BitmapDrawable localBitmapDrawable2 = new BitmapDrawable(this.f);
      BitmapDrawable localBitmapDrawable3 = new BitmapDrawable(this.e);
      localStateListDrawable.addState(MapView.j(), localBitmapDrawable2);
      localStateListDrawable.addState(MapView.k(), localBitmapDrawable1);
      localStateListDrawable.addState(MapView.l(), localBitmapDrawable3);
      return localStateListDrawable;
    }

    public void a()
    {
      a(true);
    }

    public void a(boolean paramBoolean)
    {
      if ((!this.m) || ((MapView.this.mRouteCtrl != null) && (MapView.this.mRouteCtrl.b())));
      do
      {
        return;
        if (paramBoolean)
        {
          this.o.removeCallbacks(this.p);
          this.o.postDelayed(this.p, 10000L);
        }
        d();
        this.k.a(paramBoolean);
        this.j.a(paramBoolean);
      }
      while ((MapView.this.d == null) || (MapView.this.d.getOnZoomListener() == null) || (this.n == paramBoolean));
      MapView.this.d.getOnZoomListener().onVisibilityChanged(paramBoolean);
      this.n = paramBoolean;
    }

    public void b(boolean paramBoolean)
    {
      this.m = true;
      a(paramBoolean);
      this.m = paramBoolean;
      MapView.b(MapView.this).d.a(this.l, paramBoolean);
    }

    public boolean b()
    {
      return this.k.isShown();
    }

    public void c()
    {
      if (this.a != null)
        this.a = null;
      if (this.b != null)
        this.b = null;
      if (this.c != null)
        this.c = null;
      if (this.d != null)
        this.d = null;
      if (this.e != null)
        this.e = null;
      if (this.f != null)
        this.f = null;
      if (this.g != null)
        this.g = null;
      if (this.h != null)
        this.h = null;
    }

    public void c(boolean paramBoolean)
    {
      this.k.b(paramBoolean);
      this.j.b(paramBoolean);
    }

    public void d()
    {
      if (MapView.b(MapView.this).b.e() == MapView.b(MapView.this).b.b())
      {
        this.j.setPressed(false);
        this.j.setEnabled(false);
      }
      while (MapView.b(MapView.this).b.e() == MapView.b(MapView.this).b.a())
      {
        this.k.setPressed(false);
        this.k.setEnabled(false);
        return;
        this.j.setEnabled(true);
      }
      this.k.setEnabled(true);
    }

    public void e()
    {
      this.k.bringToFront();
      this.j.bringToFront();
    }

    public MapView.f f()
    {
      return this.k;
    }

    public MapView.f g()
    {
      return this.j;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.MapView
 * JD-Core Version:    0.6.0
 */