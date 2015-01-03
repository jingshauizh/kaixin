package com.amap.mapapi.map;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.GeoPoint.a;
import com.amap.mapapi.core.OverlayItem;
import com.amap.mapapi.core.c;
import com.amap.mapapi.core.c.a;
import com.amap.mapapi.core.n;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public abstract class ItemizedOverlay<Item extends OverlayItem> extends Overlay
  implements Overlay.Snappable
{
  private static int d = -1;
  private boolean a = true;
  private Drawable b;
  private Drawable c;
  private ItemizedOverlay<Item>.b e = null;
  private OnFocusChangeListener f = null;
  private int g = -1;
  private int h = -1;

  public ItemizedOverlay(Drawable paramDrawable)
  {
    this.b = paramDrawable;
    if (this.b == null)
      this.b = new BitmapDrawable(c.g.a(c.a.d.ordinal()));
    int i = this.b.getIntrinsicWidth();
    int j = this.b.getIntrinsicHeight();
    this.b.setBounds(0, 0, i, j);
    this.c = new as().a(this.b);
    if (1 == d)
    {
      boundCenterBottom(this.b);
      return;
    }
    if (2 == d)
    {
      boundCenter(this.b);
      return;
    }
    boundCenterBottom(this.b);
  }

  private static Drawable a(Drawable paramDrawable, a parama)
  {
    if ((paramDrawable == null) || (a.a == parama))
      return null;
    paramDrawable.setBounds(0, 0, paramDrawable.getIntrinsicWidth(), paramDrawable.getIntrinsicHeight());
    Rect localRect = paramDrawable.getBounds();
    int i = localRect.width() / 2;
    int j = -localRect.height();
    a locala = a.b;
    int k = 0;
    if (parama == locala)
    {
      j /= 2;
      k = -j;
    }
    paramDrawable.setBounds(-i, j, i, k);
    return paramDrawable;
  }

  private Item a(int paramInt)
  {
    if (paramInt == 0)
      return null;
    return this.e.a(paramInt - 1);
  }

  private void a(Canvas paramCanvas, MapView paramMapView, boolean paramBoolean, Item paramItem, int paramInt)
  {
    Drawable localDrawable = paramItem.getMarker(paramInt);
    boolean bool;
    if (localDrawable == null)
    {
      bool = true;
      if (localDrawable != null)
        break label107;
      label22: if (bool)
      {
        if (!paramBoolean)
          break label121;
        localDrawable = this.c;
        this.c.setBounds(this.b.copyBounds());
        as.a(this.c, this.b);
      }
    }
    Point localPoint;
    while (true)
    {
      localPoint = paramMapView.getProjection().toPixels(paramItem.getPoint(), null);
      if (!bool)
        break label130;
      Overlay.a(paramCanvas, localDrawable, localPoint.x, localPoint.y);
      return;
      bool = false;
      break;
      label107: bool = localDrawable.equals(this.b);
      break label22;
      label121: localDrawable = this.b;
    }
    label130: Overlay.drawAt(paramCanvas, localDrawable, localPoint.x, localPoint.y, paramBoolean);
  }

  private Item b(int paramInt)
  {
    if (paramInt == -1 + this.e.a())
      return null;
    return this.e.a(paramInt + 1);
  }

  public static Drawable boundCenter(Drawable paramDrawable)
  {
    d = 2;
    return a(paramDrawable, a.b);
  }

  public static Drawable boundCenterBottom(Drawable paramDrawable)
  {
    d = 1;
    return a(paramDrawable, a.c);
  }

  protected abstract Item createItem(int paramInt);

  public void draw(Canvas paramCanvas, MapView paramMapView, boolean paramBoolean)
  {
    for (int i = 0; i < this.e.a(); i++)
    {
      int j = getIndexToDraw(i);
      if (j == this.h)
        continue;
      a(paramCanvas, paramMapView, paramBoolean, getItem(j), 0);
    }
    OverlayItem localOverlayItem = getFocus();
    if ((this.a) && (localOverlayItem != null))
    {
      a(paramCanvas, paramMapView, true, localOverlayItem, 4);
      a(paramCanvas, paramMapView, false, localOverlayItem, 4);
    }
  }

  public GeoPoint getCenter()
  {
    return getItem(getIndexToDraw(0)).getPoint();
  }

  protected Drawable getDefaultMarker()
  {
    BitmapDrawable localBitmapDrawable = null;
    if (0 == 0)
      localBitmapDrawable = new BitmapDrawable(c.g.a(c.a.d.ordinal()));
    localBitmapDrawable.setBounds(0, 0, localBitmapDrawable.getIntrinsicWidth(), localBitmapDrawable.getIntrinsicHeight());
    return localBitmapDrawable;
  }

  public Item getFocus()
  {
    int i = this.h;
    OverlayItem localOverlayItem = null;
    if (i != -1)
      localOverlayItem = this.e.a(this.h);
    return localOverlayItem;
  }

  protected int getIndexToDraw(int paramInt)
  {
    return this.e.b(paramInt);
  }

  public final Item getItem(int paramInt)
  {
    return this.e.a(paramInt);
  }

  public final int getLastFocusedIndex()
  {
    return this.g;
  }

  public int getLatSpanE6()
  {
    return this.e.a(true);
  }

  public int getLonSpanE6()
  {
    return this.e.a(false);
  }

  protected boolean hitTest(Item paramItem, Drawable paramDrawable, int paramInt1, int paramInt2)
  {
    return paramDrawable.getBounds().contains(paramInt1, paramInt2);
  }

  public Item nextFocus(boolean paramBoolean)
  {
    if (this.e.a() == 0);
    while (true)
    {
      return null;
      if (this.g != -1)
        break;
      if (paramBoolean)
        return this.e.a(0);
    }
    if (this.h == -1);
    for (int i = this.g; paramBoolean; i = this.h)
      return b(i);
    return a(i);
  }

  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent, MapView paramMapView)
  {
    return false;
  }

  public boolean onSnapToItem(int paramInt1, int paramInt2, Point paramPoint, MapView paramMapView)
  {
    for (int i = 0; ; i++)
    {
      int j = this.e.a();
      int k = 0;
      if (i < j)
      {
        OverlayItem localOverlayItem = this.e.a(i);
        Point localPoint = paramMapView.getProjection().toPixels(localOverlayItem.getPoint(), null);
        paramPoint.x = localPoint.x;
        paramPoint.y = localPoint.y;
        double d1 = paramInt1 - localPoint.x;
        double d2 = paramInt2 - localPoint.y;
        if (d1 * d1 + d2 * d2 >= 64.0D)
          break label122;
      }
      label122: for (int m = 1; m != 0; m = 0)
      {
        k = m;
        return k;
      }
    }
  }

  protected boolean onTap(int paramInt)
  {
    if (paramInt != this.h)
      setFocus(getItem(paramInt));
    return false;
  }

  public boolean onTap(GeoPoint paramGeoPoint, MapView paramMapView)
  {
    return this.e.a(paramGeoPoint, paramMapView);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent, MapView paramMapView)
  {
    return false;
  }

  public boolean onTrackballEvent(MotionEvent paramMotionEvent, MapView paramMapView)
  {
    return false;
  }

  protected final void populate()
  {
    this.e = new b();
    this.g = -1;
    this.h = -1;
  }

  public void setDrawFocusedItem(boolean paramBoolean)
  {
    this.a = paramBoolean;
  }

  public void setFocus(Item paramItem)
  {
    if ((paramItem != null) && (this.h == this.e.a(paramItem)));
    do
    {
      do
      {
        return;
        if ((paramItem == null) && (this.h != -1))
        {
          if (this.f != null)
            this.f.onFocusChanged(this, paramItem);
          this.h = -1;
          return;
        }
        this.h = this.e.a(paramItem);
      }
      while (this.h == -1);
      setLastFocusedIndex(this.h);
    }
    while (this.f == null);
    this.f.onFocusChanged(this, paramItem);
  }

  protected void setLastFocusedIndex(int paramInt)
  {
    this.g = paramInt;
  }

  public void setOnFocusChangeListener(OnFocusChangeListener paramOnFocusChangeListener)
  {
    this.f = paramOnFocusChangeListener;
  }

  public abstract int size();

  public static abstract interface OnFocusChangeListener
  {
    public abstract void onFocusChanged(ItemizedOverlay<?> paramItemizedOverlay, OverlayItem paramOverlayItem);
  }

  static enum a
  {
    static
    {
      a[] arrayOfa = new a[3];
      arrayOfa[0] = a;
      arrayOfa[1] = b;
      arrayOfa[2] = c;
      d = arrayOfa;
    }
  }

  class b
    implements Comparator<Integer>
  {
    private ArrayList<Item> b;
    private ArrayList<Integer> c;

    public b()
    {
      int i = ItemizedOverlay.this.size();
      this.b = new ArrayList(i);
      this.c = new ArrayList(i);
      for (int j = 0; j < i; j++)
      {
        this.c.add(Integer.valueOf(j));
        OverlayItem localOverlayItem = ItemizedOverlay.this.createItem(j);
        this.b.add(localOverlayItem);
      }
      Collections.sort(this.c, this);
    }

    private double a(Item paramItem, Projection paramProjection, Point paramPoint, int paramInt)
    {
      double d = -1.0D;
      if (b(paramItem, paramProjection, paramPoint, paramInt))
      {
        GeoPoint.a locala = a(paramItem, paramProjection, paramPoint);
        d = locala.a * locala.a + locala.b * locala.b;
      }
      return d;
    }

    private GeoPoint.a a(Item paramItem, Projection paramProjection, Point paramPoint)
    {
      Point localPoint = paramProjection.toPixels(paramItem.getPoint(), null);
      return new GeoPoint.a(paramPoint.x - localPoint.x, paramPoint.y - localPoint.y);
    }

    private boolean b(Item paramItem, Projection paramProjection, Point paramPoint, int paramInt)
    {
      GeoPoint.a locala = a(paramItem, paramProjection, paramPoint);
      Drawable localDrawable = paramItem.getmMarker();
      if (localDrawable == null)
        localDrawable = ItemizedOverlay.a(ItemizedOverlay.this);
      return ItemizedOverlay.this.hitTest(paramItem, localDrawable, locala.a, locala.b);
    }

    public int a()
    {
      return this.b.size();
    }

    public int a(Item paramItem)
    {
      if (paramItem != null)
        for (int i = 0; i < a(); i++)
          if (paramItem.equals(this.b.get(i)))
            return i;
      return -1;
    }

    public int a(Integer paramInteger1, Integer paramInteger2)
    {
      GeoPoint localGeoPoint1 = ((OverlayItem)this.b.get(paramInteger1.intValue())).getPoint();
      GeoPoint localGeoPoint2 = ((OverlayItem)this.b.get(paramInteger2.intValue())).getPoint();
      if (localGeoPoint1.getLatitudeE6() > localGeoPoint2.getLatitudeE6())
        return -1;
      if (localGeoPoint1.getLatitudeE6() < localGeoPoint2.getLatitudeE6())
        return 1;
      if (localGeoPoint1.getLongitudeE6() < localGeoPoint2.getLongitudeE6())
        return -1;
      if (localGeoPoint1.getLongitudeE6() > localGeoPoint2.getLongitudeE6())
        return 1;
      return 0;
    }

    public int a(boolean paramBoolean)
    {
      if (this.b.size() == 0)
        return 0;
      Iterator localIterator = this.b.iterator();
      int i = -2147483648;
      int j = 2147483647;
      GeoPoint localGeoPoint;
      int k;
      if (localIterator.hasNext())
      {
        localGeoPoint = ((OverlayItem)localIterator.next()).getPoint();
        if (paramBoolean)
        {
          k = localGeoPoint.getLatitudeE6();
          label61: if (k > i)
            i = k;
          if (k >= j)
            break label99;
        }
      }
      while (true)
      {
        j = k;
        break;
        k = localGeoPoint.getLongitudeE6();
        break label61;
        return i - j;
        label99: k = j;
      }
    }

    public Item a(int paramInt)
    {
      return (OverlayItem)this.b.get(paramInt);
    }

    public boolean a(GeoPoint paramGeoPoint, MapView paramMapView)
    {
      Projection localProjection = paramMapView.getProjection();
      Point localPoint = localProjection.toPixels(paramGeoPoint, null);
      int i = -1;
      int j = 0;
      double d1 = 1.7976931348623157E+308D;
      int k = 2147483647;
      if (j < a())
      {
        double d2 = a((OverlayItem)this.b.get(j), localProjection, localPoint, j);
        if ((d2 >= 0.0D) && (d2 < d1))
        {
          k = b(j);
          d1 = d2;
        }
        for (i = j; ; i = j)
          do
          {
            j++;
            break;
          }
          while ((d2 != d1) || (b(j) <= k));
      }
      if (-1 != i);
      for (boolean bool = ItemizedOverlay.this.onTap(i); ; bool = false)
      {
        paramMapView.a().d.d();
        return bool;
        ItemizedOverlay.this.setFocus(null);
      }
    }

    public int b(int paramInt)
    {
      return ((Integer)this.c.get(paramInt)).intValue();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.ItemizedOverlay
 * JD-Core Version:    0.6.0
 */