package com.google.zxing;

public class ResultPoint
{
  private final float x;
  private final float y;

  public ResultPoint(float paramFloat1, float paramFloat2)
  {
    this.x = paramFloat1;
    this.y = paramFloat2;
  }

  private static float crossProductZ(ResultPoint paramResultPoint1, ResultPoint paramResultPoint2, ResultPoint paramResultPoint3)
  {
    float f1 = paramResultPoint2.x;
    float f2 = paramResultPoint2.y;
    return (paramResultPoint3.x - f1) * (paramResultPoint1.y - f2) - (paramResultPoint3.y - f2) * (paramResultPoint1.x - f1);
  }

  public static float distance(ResultPoint paramResultPoint1, ResultPoint paramResultPoint2)
  {
    float f1 = paramResultPoint1.getX() - paramResultPoint2.getX();
    float f2 = paramResultPoint1.getY() - paramResultPoint2.getY();
    return (float)Math.sqrt(f1 * f1 + f2 * f2);
  }

  public static void orderBestPatterns(ResultPoint[] paramArrayOfResultPoint)
  {
    float f1 = distance(paramArrayOfResultPoint[0], paramArrayOfResultPoint[1]);
    float f2 = distance(paramArrayOfResultPoint[1], paramArrayOfResultPoint[2]);
    float f3 = distance(paramArrayOfResultPoint[0], paramArrayOfResultPoint[2]);
    ResultPoint localResultPoint;
    Object localObject1;
    Object localObject2;
    if ((f2 >= f1) && (f2 >= f3))
    {
      localResultPoint = paramArrayOfResultPoint[0];
      localObject1 = paramArrayOfResultPoint[1];
      localObject2 = paramArrayOfResultPoint[2];
    }
    while (true)
    {
      if (crossProductZ((ResultPoint)localObject1, localResultPoint, (ResultPoint)localObject2) < 0.0F)
      {
        Object localObject3 = localObject1;
        localObject1 = localObject2;
        localObject2 = localObject3;
      }
      paramArrayOfResultPoint[0] = localObject1;
      paramArrayOfResultPoint[1] = localResultPoint;
      paramArrayOfResultPoint[2] = localObject2;
      return;
      if ((f3 >= f2) && (f3 >= f1))
      {
        localResultPoint = paramArrayOfResultPoint[1];
        localObject1 = paramArrayOfResultPoint[0];
        localObject2 = paramArrayOfResultPoint[2];
        continue;
      }
      localResultPoint = paramArrayOfResultPoint[2];
      localObject1 = paramArrayOfResultPoint[0];
      localObject2 = paramArrayOfResultPoint[1];
    }
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof ResultPoint;
    int i = 0;
    if (bool1)
    {
      ResultPoint localResultPoint = (ResultPoint)paramObject;
      boolean bool2 = this.x < localResultPoint.x;
      i = 0;
      if (!bool2)
      {
        boolean bool3 = this.y < localResultPoint.y;
        i = 0;
        if (!bool3)
          i = 1;
      }
    }
    return i;
  }

  public final float getX()
  {
    return this.x;
  }

  public final float getY()
  {
    return this.y;
  }

  public int hashCode()
  {
    return 31 * Float.floatToIntBits(this.x) + Float.floatToIntBits(this.y);
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer(25);
    localStringBuffer.append('(');
    localStringBuffer.append(this.x);
    localStringBuffer.append(',');
    localStringBuffer.append(this.y);
    localStringBuffer.append(')');
    return localStringBuffer.toString();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.ResultPoint
 * JD-Core Version:    0.6.0
 */