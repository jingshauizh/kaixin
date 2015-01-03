package com.mapabc.minimap.map.vmap;

import android.graphics.Point;
import com.amap.mapapi.map.be;

public class VMapProjection
{
  public static final double EarthCircumferenceInMeters = 40075016.685578488D;
  public static final int EarthRadiusInMeters = 6378137;
  public static final int MAXZOOMLEVEL = 20;
  public static final double MaxLatitude = 85.051128779799996D;
  public static final double MaxLongitude = 180.0D;
  public static final double MinLatitude = -85.051128779799996D;
  public static final double MinLongitude = -180.0D;
  public static final int PixelsPerTile = 256;
  public static final int TileSplitLevel;

  public static double Clip(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    return Math.min(Math.max(paramDouble1, paramDouble2), paramDouble3);
  }

  public static be LatLongToPixels(double paramDouble1, double paramDouble2, int paramInt)
  {
    be localbe = new be();
    double d1 = 3.141592653589793D * Clip(paramDouble1, -85.051128779799996D, 85.051128779799996D) / 180.0D;
    double d2 = 3.141592653589793D * Clip(paramDouble2, -180.0D, 180.0D) / 180.0D;
    double d3 = Math.sin(d1);
    double d4 = d2 * 6378137.0D;
    double d5 = 3189068.0D * Math.log((1.0D + d3) / (1.0D - d3));
    long l = 256L << paramInt;
    double d6 = 40075016.685578488D / l;
    localbe.a = (int)Clip(0.5D + (d4 + 20037508.342789244D) / d6, 0.0D, l - 1L);
    localbe.b = (int)Clip(0.5D + ()(20037508.342789244D - d5) / d6, 0.0D, l - 1L);
    return localbe;
  }

  public static be LatLongToPixels(int paramInt1, int paramInt2, int paramInt3)
  {
    return LatLongToPixels(paramInt2 / 3600000.0D, paramInt1 / 3600000.0D, paramInt3);
  }

  public static a PixelsToLatLong(long paramLong1, long paramLong2, int paramInt)
  {
    a locala = new a();
    double d1 = 40075016.685578488D / (256 * (1 << paramInt));
    double d2 = d1 * paramLong1 - 20037508.342789244D;
    locala.b = (1.570796326794897D - 2.0D * Math.atan(Math.exp(-(20037508.342789244D - d1 * paramLong2) / 6378137.0D)));
    locala.b = (57.295779513082323D * locala.b);
    locala.a = (d2 / 6378137.0D);
    locala.a = (57.295779513082323D * locala.a);
    return locala;
  }

  public static be PixelsToPixels(long paramLong1, long paramLong2, int paramInt1, int paramInt2)
  {
    int i = paramInt2 - paramInt1;
    if (i > 0)
    {
      paramLong1 >>= i;
      paramLong2 >>= i;
    }
    while (true)
    {
      be localbe = new be();
      localbe.a = (int)paramLong1;
      localbe.b = (int)paramLong2;
      return localbe;
      if (i >= 0)
        continue;
      paramLong1 <<= i;
      paramLong2 <<= i;
    }
  }

  public static be PixelsToTile(int paramInt1, int paramInt2)
  {
    be localbe = new be();
    localbe.a = (paramInt1 / 256);
    localbe.b = (paramInt2 / 256);
    return localbe;
  }

  public static Point QuadKeyToTile(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    int k = 1;
    int m = 0;
    if (k <= j)
    {
      int n = paramString.charAt(k - 1);
      int i1 = 1 << j - k;
      switch (n)
      {
      default:
      case 48:
      case 49:
      case 50:
      case 51:
      }
      while (true)
      {
        k++;
        break;
        m &= (i1 ^ 0xFFFFFFFF);
        i &= (i1 ^ 0xFFFFFFFF);
        continue;
        m |= i1;
        i &= (i1 ^ 0xFFFFFFFF);
        continue;
        m &= (i1 ^ 0xFFFFFFFF);
        i |= i1;
        continue;
        m |= i1;
        i |= i1;
      }
    }
    Point localPoint = new Point();
    localPoint.x = m;
    localPoint.y = i;
    return localPoint;
  }

  public static be QuadKeyToTitle(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    int k = 1;
    int m = 0;
    if (k <= j)
    {
      int n = paramString.charAt(k - 1);
      int i1 = 1 << j - k;
      switch (n)
      {
      default:
      case 48:
      case 49:
      case 50:
      case 51:
      }
      while (true)
      {
        k++;
        break;
        m &= (i1 ^ 0xFFFFFFFF);
        i &= (i1 ^ 0xFFFFFFFF);
        continue;
        m |= i1;
        i &= (i1 ^ 0xFFFFFFFF);
        continue;
        m &= (i1 ^ 0xFFFFFFFF);
        i |= i1;
        continue;
        m |= i1;
        i |= i1;
      }
    }
    be localbe = new be();
    localbe.a = m;
    localbe.b = i;
    return localbe;
  }

  public static String TileToQuadKey(int paramInt1, int paramInt2, int paramInt3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = paramInt3 + 0; i > 0; i--)
    {
      long l = 1 << i - 1;
      boolean bool = (l & paramInt1) < 0L;
      int j = 0;
      if (bool)
        j = 1;
      if ((l & paramInt2) != 0L)
        j += 2;
      localStringBuffer.append(j);
    }
    return localStringBuffer.toString();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.mapabc.minimap.map.vmap.VMapProjection
 * JD-Core Version:    0.6.0
 */