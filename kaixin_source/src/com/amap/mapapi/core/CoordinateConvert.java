package com.amap.mapapi.core;

import com.autonavi.util.Shifting;

public class CoordinateConvert
{
  public static GeoPoint fromGpsToAMap(double paramDouble1, double paramDouble2)
  {
    double[] arrayOfDouble = Shifting.table_offset(paramDouble2, paramDouble1);
    return new GeoPoint((int)(1000000.0D * arrayOfDouble[1]), (int)(1000000.0D * arrayOfDouble[0]));
  }

  public static double[] fromSeveralGpsToAMap(String paramString)
  {
    String[] arrayOfString = paramString.split(",");
    int i = arrayOfString.length;
    double[] arrayOfDouble1 = new double[i];
    for (int j = 0; j < i / 2; j++)
    {
      double[] arrayOfDouble2 = Shifting.table_offset(Double.parseDouble(arrayOfString[(j * 2)]), Double.parseDouble(arrayOfString[(1 + j * 2)]));
      arrayOfDouble1[(j * 2)] = arrayOfDouble2[0];
      arrayOfDouble1[(1 + j * 2)] = arrayOfDouble2[1];
    }
    return arrayOfDouble1;
  }

  public static double[] fromSeveralGpsToAMap(double[] paramArrayOfDouble)
  {
    int i = paramArrayOfDouble.length;
    double[] arrayOfDouble1 = new double[i];
    for (int j = 0; j < i / 2; j++)
    {
      double[] arrayOfDouble2 = Shifting.table_offset(paramArrayOfDouble[(j * 2)], paramArrayOfDouble[(1 + j * 2)]);
      arrayOfDouble1[(j * 2)] = arrayOfDouble2[0];
      arrayOfDouble1[(1 + j * 2)] = arrayOfDouble2[1];
    }
    return arrayOfDouble1;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.core.CoordinateConvert
 * JD-Core Version:    0.6.0
 */