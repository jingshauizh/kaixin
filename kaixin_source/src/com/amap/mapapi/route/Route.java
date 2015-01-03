package com.amap.mapapi.route;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.GeoPoint.b;
import com.amap.mapapi.core.i;
import com.amap.mapapi.core.r;
import com.amap.mapapi.map.MapView;
import com.amap.mapapi.map.RouteMessageHandler;
import com.amap.mapapi.map.RouteOverlay;
import java.net.Proxy;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

public class Route
{
  public static final int BusDefault = 0;
  public static final int BusLeaseChange = 2;
  public static final int BusLeaseWalk = 3;
  public static final int BusMostComfortable = 4;
  public static final int BusSaveMoney = 1;
  public static final int DrivingDefault = 10;
  public static final int DrivingLeastDistance = 12;
  public static final int DrivingNoFastRoad = 13;
  public static final int DrivingSaveMoney = 11;
  private GeoPoint a = null;
  private GeoPoint b = null;
  private int c;
  public d mHelper;
  protected List<Segment> mSegs;
  protected String mStartPlace;
  protected String mTargetPlace;

  public Route(int paramInt)
  {
    this.c = paramInt;
    if (isBus(paramInt))
    {
      this.mHelper = new a();
      return;
    }
    if (isDrive(paramInt))
    {
      this.mHelper = new b();
      return;
    }
    if (isWalk(paramInt))
    {
      this.mHelper = new c();
      return;
    }
    throw new IllegalArgumentException("Unkown mode");
  }

  static String a(int paramInt)
  {
    if (paramInt > 10000)
    {
      int k = paramInt / 1000;
      return k + "公里";
    }
    if (paramInt > 1000)
    {
      float f = paramInt / 1000.0F;
      String str = new DecimalFormat("##0.0").format(f);
      return str + "公里";
    }
    if (paramInt > 100)
    {
      int j = 50 * (paramInt / 50);
      return j + "米";
    }
    int i = 10 * (paramInt / 10);
    if (i == 0)
      i = 10;
    return i + "米";
  }

  private void b()
  {
    int i = -2147483648;
    Iterator localIterator1 = this.mSegs.iterator();
    int j = 2147483647;
    int k = 2147483647;
    int i3;
    if (localIterator1.hasNext())
    {
      GeoPoint localGeoPoint2 = ((Segment)localIterator1.next()).getLowerLeftPoint();
      int i2 = localGeoPoint2.getLongitudeE6();
      i3 = localGeoPoint2.getLatitudeE6();
      if (i2 < k)
        k = i2;
      if (i3 >= j)
        break label191;
    }
    while (true)
    {
      j = i3;
      break;
      Iterator localIterator2 = this.mSegs.iterator();
      int m = i;
      int i1;
      if (localIterator2.hasNext())
      {
        GeoPoint localGeoPoint1 = ((Segment)localIterator2.next()).getUpperRightPoint();
        int n = localGeoPoint1.getLongitudeE6();
        i1 = localGeoPoint1.getLatitudeE6();
        if (n > m)
          m = n;
        if (i1 <= i)
          break label185;
      }
      while (true)
      {
        i = i1;
        break;
        this.a = new GeoPoint(j, k);
        this.b = new GeoPoint(i, m);
        return;
        label185: i1 = i;
      }
      label191: i3 = j;
    }
  }

  public static List<Route> calculateRoute(Context paramContext, FromAndTo paramFromAndTo, int paramInt)
    throws AMapException
  {
    com.amap.mapapi.core.b.a(paramContext);
    f localf = new f(paramFromAndTo, paramInt);
    Proxy localProxy = com.amap.mapapi.core.e.b(paramContext);
    String str = com.amap.mapapi.core.e.a(paramContext);
    FromAndTo.a(paramFromAndTo, paramContext, paramFromAndTo.mFrom, paramFromAndTo.mTo, paramFromAndTo.mTrans);
    Object localObject;
    if (isBus(paramInt))
      localObject = new a(localf, localProxy, str, null);
    while (true)
    {
      return (List)((e)localObject).g();
      if (isWalk(paramInt))
      {
        localObject = new g(localf, localProxy, str, null);
        continue;
      }
      localObject = new c(localf, localProxy, str, null);
    }
  }

  public static List<Route> calculateRoute(Context paramContext, FromAndTo paramFromAndTo, int paramInt, List<GeoPoint> paramList)
    throws AMapException
  {
    com.amap.mapapi.core.b.a(paramContext);
    f localf = new f(paramFromAndTo, paramInt);
    Proxy localProxy = com.amap.mapapi.core.e.b(paramContext);
    String str = com.amap.mapapi.core.e.a(paramContext);
    FromAndTo.a(paramFromAndTo, paramContext, paramFromAndTo.mFrom, paramFromAndTo.mTo, paramFromAndTo.mTrans);
    Object localObject;
    if (isBus(paramInt))
      localObject = new a(localf, localProxy, str, null);
    while (true)
    {
      ((e)localObject).a(paramList);
      return (List)((e)localObject).g();
      if (isWalk(paramInt))
      {
        localObject = new g(localf, localProxy, str, null);
        continue;
      }
      localObject = new c(localf, localProxy, str, null);
    }
  }

  public static boolean isBus(int paramInt)
  {
    return (paramInt >= 0) && (paramInt <= 4);
  }

  public static boolean isDrive(int paramInt)
  {
    return (paramInt >= 10) && (paramInt <= 13);
  }

  public static boolean isWalk(int paramInt)
  {
    return false;
  }

  List<Segment> a()
  {
    return this.mSegs;
  }

  void a(List<Segment> paramList)
  {
    this.mSegs = paramList;
  }

  public int getLength()
  {
    Iterator localIterator = this.mSegs.iterator();
    int i = 0;
    while (localIterator.hasNext())
      i += ((Segment)localIterator.next()).getLength();
    return i;
  }

  public GeoPoint getLowerLeftPoint()
  {
    if (this.a == null)
      b();
    return this.a;
  }

  public int getMode()
  {
    return this.c;
  }

  public String getOverview()
  {
    return this.mHelper.a();
  }

  public int getSegmentIndex(Segment paramSegment)
  {
    return this.mSegs.indexOf(paramSegment);
  }

  public String getStartPlace()
  {
    return this.mStartPlace;
  }

  public GeoPoint getStartPos()
  {
    return ((Segment)this.mSegs.get(0)).getFirstPoint();
  }

  public Segment getStep(int paramInt)
  {
    return (Segment)this.mSegs.get(paramInt);
  }

  public int getStepCount()
  {
    return this.mSegs.size();
  }

  public String getStepedDescription(int paramInt)
  {
    return this.mHelper.b(paramInt);
  }

  public String getTargetPlace()
  {
    return this.mTargetPlace;
  }

  public GeoPoint getTargetPos()
  {
    return ((Segment)this.mSegs.get(-1 + getStepCount())).getLastPoint();
  }

  public GeoPoint getUpperRightPoint()
  {
    if (this.b == null)
      b();
    return this.b;
  }

  public void setStartPlace(String paramString)
  {
    this.mStartPlace = paramString;
  }

  public void setTargetPlace(String paramString)
  {
    this.mTargetPlace = paramString;
  }

  public static class FromAndTo
  {
    public static final int NoTrans = 0;
    public static final int TransBothPoint = 3;
    public static final int TransFromPoint = 1;
    public static final int TransToPoint = 2;
    public GeoPoint mFrom;
    public GeoPoint mTo;
    public int mTrans;

    public FromAndTo(GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2)
    {
      this(paramGeoPoint1, paramGeoPoint2, 0);
    }

    public FromAndTo(GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2, int paramInt)
    {
      this.mFrom = paramGeoPoint1;
      this.mTo = paramGeoPoint2;
      this.mTrans = paramInt;
    }

    private GeoPoint.b a(Context paramContext, GeoPoint paramGeoPoint)
      throws AMapException
    {
      double d = com.amap.mapapi.core.e.a(paramGeoPoint.b());
      return (GeoPoint.b)new i(new GeoPoint.b(com.amap.mapapi.core.e.a(paramGeoPoint.a()), d), com.amap.mapapi.core.e.b(paramContext), com.amap.mapapi.core.e.a(paramContext), null).g();
    }

    private void a(Context paramContext, GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2, int paramInt)
      throws AMapException
    {
      switch (this.mTrans)
      {
      default:
        return;
      case 0:
        this.mFrom = paramGeoPoint1;
        this.mTo = paramGeoPoint2;
        return;
      case 1:
        GeoPoint.b localb4 = a(paramContext, paramGeoPoint1);
        this.mFrom = new GeoPoint(com.amap.mapapi.core.e.a(localb4.b), com.amap.mapapi.core.e.a(localb4.a));
        return;
      case 2:
        GeoPoint.b localb3 = a(paramContext, paramGeoPoint2);
        this.mTo = new GeoPoint(com.amap.mapapi.core.e.a(localb3.b), com.amap.mapapi.core.e.a(localb3.a));
        return;
      case 3:
      }
      GeoPoint.b localb1 = a(paramContext, paramGeoPoint1);
      this.mFrom = new GeoPoint(com.amap.mapapi.core.e.a(localb1.b), com.amap.mapapi.core.e.a(localb1.a));
      GeoPoint.b localb2 = a(paramContext, paramGeoPoint2);
      this.mTo = new GeoPoint(com.amap.mapapi.core.e.a(localb2.b), com.amap.mapapi.core.e.a(localb2.a));
    }
  }

  public class a extends Route.d
  {
    public a()
    {
      super();
    }

    private String h(int paramInt)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("步行").append("去往");
      if (paramInt == -1 + Route.this.getStepCount())
        localStringBuilder.append("目的地");
      while (true)
      {
        localStringBuilder.append("\n大约" + Route.a(Route.this.getStep(paramInt).getLength()));
        return localStringBuilder.toString();
        localStringBuilder.append(((BusSegment)Route.this.getStep(paramInt + 1)).getLineName() + "车站");
      }
    }

    private String i(int paramInt)
    {
      BusSegment localBusSegment = (BusSegment)Route.this.getStep(paramInt);
      StringBuffer localStringBuffer = new StringBuffer();
      Object[] arrayOfObject1 = new Object[5];
      arrayOfObject1[0] = localBusSegment.getLineName();
      arrayOfObject1[1] = localBusSegment.getFirstStationName();
      arrayOfObject1[2] = localBusSegment.getLastStationName();
      arrayOfObject1[3] = localBusSegment.getLastStationName();
      arrayOfObject1[4] = "方向";
      localStringBuffer.append(String.format("%s ( %s -- %s ) - %s%s\n", arrayOfObject1));
      localStringBuffer.append("上车 : " + localBusSegment.getOnStationName() + "\n");
      localStringBuffer.append("下车 : " + localBusSegment.getOffStationName() + "\n");
      Object[] arrayOfObject2 = new Object[4];
      arrayOfObject2[0] = "公交";
      arrayOfObject2[1] = Integer.valueOf(-1 + localBusSegment.getStopNumber());
      arrayOfObject2[2] = "站";
      arrayOfObject2[3] = ("大约" + Route.a(localBusSegment.getLength()));
      localStringBuffer.append(String.format("%s%d%s (%s)", arrayOfObject2));
      return localStringBuffer.toString();
    }

    private Spanned j(int paramInt)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("步行").append("去往");
      if (paramInt == -1 + Route.this.getStepCount())
        localStringBuilder.append(com.amap.mapapi.core.e.a("目的地", "#808080"));
      while (true)
      {
        localStringBuilder.append(com.amap.mapapi.core.e.c());
        localStringBuilder.append("大约" + Route.a(Route.this.getStep(paramInt).getLength()));
        return com.amap.mapapi.core.e.b(localStringBuilder.toString());
        localStringBuilder.append(com.amap.mapapi.core.e.a(((BusSegment)Route.this.getStep(paramInt + 1)).getLineName() + "车站", "#808080"));
      }
    }

    private Spanned k(int paramInt)
    {
      BusSegment localBusSegment = (BusSegment)Route.this.getStep(paramInt);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append(com.amap.mapapi.core.e.a(localBusSegment.getLineName(), "#000000"));
      localStringBuffer.append(com.amap.mapapi.core.e.c(3));
      localStringBuffer.append(com.amap.mapapi.core.e.a(localBusSegment.getLastStationName(), "#000000"));
      localStringBuffer.append("方向");
      localStringBuffer.append(com.amap.mapapi.core.e.c());
      localStringBuffer.append("上车 : ");
      localStringBuffer.append(com.amap.mapapi.core.e.a(localBusSegment.getOnStationName(), "#000000"));
      localStringBuffer.append(com.amap.mapapi.core.e.c(3));
      localStringBuffer.append(com.amap.mapapi.core.e.c());
      localStringBuffer.append("下车 : ");
      localStringBuffer.append(com.amap.mapapi.core.e.a(localBusSegment.getOffStationName(), "#000000"));
      localStringBuffer.append(com.amap.mapapi.core.e.c());
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = "公交";
      arrayOfObject[1] = Integer.valueOf(-1 + localBusSegment.getStopNumber());
      arrayOfObject[2] = "站";
      localStringBuffer.append(String.format("%s%d%s , ", arrayOfObject));
      localStringBuffer.append("大约" + Route.a(localBusSegment.getLength()));
      return com.amap.mapapi.core.e.b(localStringBuffer.toString());
    }

    public Paint a(int paramInt)
    {
      Paint localPaint = r.k;
      if ((Route.this.getStep(paramInt) instanceof BusSegment))
        localPaint = r.l;
      return localPaint;
    }

    public View a(MapView paramMapView, Context paramContext, RouteMessageHandler paramRouteMessageHandler, RouteOverlay paramRouteOverlay, int paramInt)
    {
      Drawable localDrawable = f(paramInt);
      if ((paramInt == 0) || (paramInt == Route.this.getStepCount()))
        return null;
      Segment localSegment = Route.this.getStep(paramInt);
      if ((localSegment instanceof BusSegment));
      for (String str = ((BusSegment)localSegment).getLineName(); ; str = null)
      {
        if ((str == null) && (localDrawable == null))
          return null;
        LinearLayout localLinearLayout = new LinearLayout(paramContext);
        localLinearLayout.setOrientation(0);
        ImageView localImageView = new ImageView(paramContext);
        localImageView.setImageDrawable(localDrawable);
        localImageView.setPadding(3, 3, 1, 5);
        localLinearLayout.addView(localImageView, new LinearLayout.LayoutParams(-2, -2));
        if (str != null)
        {
          TextView localTextView = new TextView(paramContext);
          localTextView.setText(str);
          localTextView.setTextColor(-16777216);
          localTextView.setPadding(3, 0, 3, 3);
          localLinearLayout.addView(localTextView, new LinearLayout.LayoutParams(-2, -2));
        }
        new b(paramMapView, paramRouteMessageHandler, paramRouteOverlay, paramInt, 4).a(localLinearLayout);
        return localLinearLayout;
      }
    }

    public String a()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      int i = Route.this.getStepCount();
      int j = 1;
      int k = 0;
      while (j < i)
      {
        BusSegment localBusSegment = (BusSegment)Route.this.getStep(j);
        if (j != 1)
          localStringBuilder.append(" -> ");
        localStringBuilder.append(localBusSegment.getLineName());
        k += localBusSegment.getLength();
        j += 2;
      }
      if (k != 0)
        localStringBuilder.append("\n");
      int m = 0;
      int n = 0;
      while (m < i)
      {
        n += Route.this.getStep(m).getLength();
        m += 2;
      }
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = "乘车";
      arrayOfObject[1] = Route.a(k);
      arrayOfObject[2] = "步行";
      arrayOfObject[3] = Route.a(n);
      localStringBuilder.append(String.format("%s%s  %s%s", arrayOfObject));
      return localStringBuilder.toString();
    }

    public String b(int paramInt)
    {
      String str = super.b(paramInt);
      if (str != null)
        return str;
      if ((Route.this.getStep(paramInt) instanceof BusSegment))
        return i(paramInt);
      return h(paramInt);
    }

    public Spanned c(int paramInt)
    {
      Spanned localSpanned = super.c(paramInt);
      if (localSpanned != null)
        return localSpanned;
      if ((Route.this.getStep(paramInt) instanceof BusSegment))
        return k(paramInt);
      return j(paramInt);
    }

    public int d(int paramInt)
    {
      do
        paramInt++;
      while ((paramInt < -1 + Route.this.getStepCount()) && (!(Route.this.getStep(paramInt) instanceof BusSegment)));
      return paramInt;
    }

    public int e(int paramInt)
    {
      int i;
      if (paramInt == Route.this.getStepCount())
      {
        i = paramInt - 1;
        return i;
      }
      do
      {
        paramInt = i;
        i = paramInt - 1;
        if (i <= 0)
          break;
      }
      while (!(Route.this.getStep(i) instanceof BusSegment));
      return i;
    }

    protected Drawable f(int paramInt)
    {
      Drawable localDrawable;
      if (paramInt == -1 + Route.this.getStepCount())
        localDrawable = r.c;
      int i;
      do
      {
        return localDrawable;
        if ((paramInt < Route.this.getStepCount()) && ((Route.this.getStep(paramInt) instanceof BusSegment)))
          return r.e;
        if (paramInt == 0)
          return r.f;
        i = Route.this.getStepCount();
        localDrawable = null;
      }
      while (paramInt != i);
      return r.g;
    }
  }

  class b extends Route.e
  {
    b()
    {
      super();
    }

    protected Drawable f(int paramInt)
    {
      return r.d;
    }
  }

  class c extends Route.e
  {
    c()
    {
      super();
    }

    public Paint a(int paramInt)
    {
      return r.k;
    }

    protected Drawable f(int paramInt)
    {
      return r.c;
    }
  }

  public abstract class d
  {
    public d()
    {
    }

    private String h(int paramInt)
    {
      String str;
      if (paramInt == 0)
        str = Route.this.mStartPlace;
      int i;
      do
      {
        return str;
        i = Route.this.getStepCount();
        str = null;
      }
      while (paramInt != i);
      return Route.this.mTargetPlace;
    }

    public abstract Paint a(int paramInt);

    public View a(MapView paramMapView, Context paramContext, RouteMessageHandler paramRouteMessageHandler, RouteOverlay paramRouteOverlay, int paramInt)
    {
      return null;
    }

    public abstract String a();

    public View b(MapView paramMapView, Context paramContext, RouteMessageHandler paramRouteMessageHandler, RouteOverlay paramRouteOverlay, int paramInt)
    {
      LinearLayout localLinearLayout1;
      if ((paramInt < 0) || (paramInt > Route.this.getStepCount()))
        localLinearLayout1 = null;
      DisplayMetrics localDisplayMetrics;
      do
      {
        return localLinearLayout1;
        localLinearLayout1 = new LinearLayout(paramContext);
        localLinearLayout1.setOrientation(1);
        localLinearLayout1.setBackgroundColor(Color.argb(255, 255, 255, 255));
        LinearLayout localLinearLayout2 = new LinearLayout(paramContext);
        localLinearLayout2.setOrientation(0);
        localLinearLayout2.setBackgroundColor(-1);
        localLinearLayout2.setGravity(2);
        ImageView localImageView = new ImageView(paramContext);
        localImageView.setBackgroundColor(-1);
        localImageView.setImageDrawable(f(paramInt));
        localImageView.setPadding(3, 3, 0, 0);
        localLinearLayout2.addView(localImageView, new LinearLayout.LayoutParams(-2, -2));
        TextView localTextView1 = new TextView(paramContext);
        localTextView1.setBackgroundColor(-1);
        String[] arrayOfString = c(paramInt).toString().split("\\n", 2);
        localTextView1.setTextColor(-16777216);
        localTextView1.setText(com.amap.mapapi.core.e.b(arrayOfString[0]));
        localTextView1.setPadding(3, 0, 0, 3);
        localLinearLayout2.addView(localTextView1, new LinearLayout.LayoutParams(-1, -2));
        TextView localTextView2 = new TextView(paramContext);
        localTextView2.setBackgroundColor(Color.rgb(165, 166, 165));
        localTextView2.setLayoutParams(new LinearLayout.LayoutParams(-1, 1));
        LinearLayout localLinearLayout3 = new LinearLayout(paramContext);
        localLinearLayout3.setOrientation(1);
        localLinearLayout3.setBackgroundColor(-1);
        TextView localTextView3 = new TextView(paramContext);
        localTextView3.setBackgroundColor(-1);
        if (arrayOfString.length == 2)
        {
          localLinearLayout3.addView(localTextView2, new LinearLayout.LayoutParams(-1, 1));
          localTextView3.setText(com.amap.mapapi.core.e.b(arrayOfString[1]));
          localTextView3.setTextColor(Color.rgb(82, 85, 82));
          localLinearLayout3.addView(localTextView3, new LinearLayout.LayoutParams(-1, -2));
        }
        LinearLayout localLinearLayout4 = new LinearLayout(paramContext);
        localLinearLayout4.setOrientation(0);
        localLinearLayout4.setGravity(1);
        localLinearLayout4.setBackgroundColor(-1);
        localLinearLayout1.addView(localLinearLayout2, new LinearLayout.LayoutParams(-1, -2));
        localLinearLayout1.addView(localLinearLayout3, new LinearLayout.LayoutParams(-1, 1));
        localLinearLayout1.addView(localLinearLayout4, new LinearLayout.LayoutParams(-1, -2));
        new DisplayMetrics();
        localDisplayMetrics = paramContext.getApplicationContext().getResources().getDisplayMetrics();
      }
      while (localDisplayMetrics.widthPixels * localDisplayMetrics.heightPixels <= 153600L);
      TextView localTextView4 = new TextView(paramContext);
      localTextView4.setText("");
      localTextView4.setHeight(5);
      localTextView4.setWidth(1);
      localLinearLayout1.addView(localTextView4);
      return localLinearLayout1;
    }

    public String b(int paramInt)
    {
      return h(paramInt);
    }

    public Spanned c(int paramInt)
    {
      String str = h(paramInt);
      if (str == null)
        return null;
      return com.amap.mapapi.core.e.b(com.amap.mapapi.core.e.a(str, "#000000"));
    }

    public int d(int paramInt)
    {
      if (paramInt >= Route.this.getStepCount())
        return -1;
      return paramInt + 1;
    }

    public int e(int paramInt)
    {
      if (paramInt <= 0)
        return -1;
      return paramInt - 1;
    }

    protected abstract Drawable f(int paramInt);

    public GeoPoint g(int paramInt)
    {
      if (paramInt == Route.this.getStepCount())
        return Route.this.getStep(paramInt - 1).getLastPoint();
      return Route.this.getStep(paramInt).getFirstPoint();
    }
  }

  abstract class e extends Route.d
  {
    e()
    {
      super();
    }

    public Paint a(int paramInt)
    {
      return r.m;
    }

    public String a()
    {
      StringBuffer localStringBuffer = new StringBuffer();
      Object localObject1 = "";
      int i = Route.this.getStepCount();
      int j = 0;
      int k = 0;
      DriveWalkSegment localDriveWalkSegment;
      if (j < i)
      {
        localDriveWalkSegment = (DriveWalkSegment)Route.this.getStep(j);
        k += localDriveWalkSegment.getLength();
        if ((com.amap.mapapi.core.e.a(localDriveWalkSegment.getRoadName())) || (localDriveWalkSegment.getRoadName().equals(localObject1)))
          break label188;
        if (!com.amap.mapapi.core.e.a(localStringBuffer.toString()))
          localStringBuffer.append(" -> ");
        localStringBuffer.append(localDriveWalkSegment.getRoadName());
      }
      label188: for (Object localObject2 = localDriveWalkSegment.getRoadName(); ; localObject2 = localObject1)
      {
        j++;
        localObject1 = localObject2;
        break;
        if (!com.amap.mapapi.core.e.a(localStringBuffer.toString()))
          localStringBuffer.append("\n");
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = ("大约" + Route.a(k));
        localStringBuffer.append(String.format("%s", arrayOfObject));
        return localStringBuffer.toString();
      }
    }

    public String b(int paramInt)
    {
      String str1 = super.b(paramInt);
      if (str1 != null)
        return str1;
      String str2 = "";
      DriveWalkSegment localDriveWalkSegment = (DriveWalkSegment)Route.this.getStep(paramInt);
      if (!com.amap.mapapi.core.e.a(localDriveWalkSegment.getRoadName()))
        str2 = localDriveWalkSegment.getRoadName() + " ";
      String str3 = str2 + localDriveWalkSegment.getActionDescription() + " ";
      StringBuilder localStringBuilder = new StringBuilder().append(str3);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = "大约";
      arrayOfObject[1] = Route.a(localDriveWalkSegment.getLength());
      return String.format("%s%s", arrayOfObject);
    }

    public Spanned c(int paramInt)
    {
      Spanned localSpanned = super.c(paramInt);
      if (localSpanned != null)
        return localSpanned;
      DriveWalkSegment localDriveWalkSegment = (DriveWalkSegment)Route.this.getStep(paramInt);
      if ((!com.amap.mapapi.core.e.a(localDriveWalkSegment.getRoadName())) && (!com.amap.mapapi.core.e.a(localDriveWalkSegment.getActionDescription())));
      for (String str1 = localDriveWalkSegment.getActionDescription() + " --> " + localDriveWalkSegment.getRoadName(); ; str1 = localDriveWalkSegment.getActionDescription() + localDriveWalkSegment.getRoadName())
      {
        String str2 = com.amap.mapapi.core.e.a(str1, "#808080");
        String str3 = str2 + com.amap.mapapi.core.e.c();
        StringBuilder localStringBuilder = new StringBuilder().append(str3);
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = "大约";
        arrayOfObject[1] = Route.a(localDriveWalkSegment.getLength());
        return com.amap.mapapi.core.e.b(String.format("%s%s", arrayOfObject));
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.route.Route
 * JD-Core Version:    0.6.0
 */