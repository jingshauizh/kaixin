package org.apache.sanselan.common;

public abstract class RationalNumberUtilities extends Number
{
  private static final double TOLERANCE = 1.0E-008D;

  public static final RationalNumber getRationalNumber(double paramDouble)
  {
    if (paramDouble >= 2147483647.0D)
      return new RationalNumber(2147483647, 1);
    if (paramDouble <= -2147483647.0D)
      return new RationalNumber(-2147483647, 1);
    boolean bool = paramDouble < 0.0D;
    int i = 0;
    if (bool)
    {
      i = 1;
      paramDouble = Math.abs(paramDouble);
    }
    if (paramDouble == 0.0D)
      return new RationalNumber(0, 1);
    int m;
    RationalNumber localRationalNumber1;
    RationalNumber localRationalNumber2;
    Object localObject1;
    Object localObject3;
    label149: int k;
    if (paramDouble >= 1.0D)
    {
      m = (int)paramDouble;
      if (m < paramDouble)
      {
        localRationalNumber1 = new RationalNumber(m, 1);
        localRationalNumber2 = new RationalNumber(m + 1, 1);
        localObject1 = Option.factory(localRationalNumber1, paramDouble);
        localObject2 = Option.factory(localRationalNumber2, paramDouble);
        if (((Option)localObject1).error >= ((Option)localObject2).error)
          break label287;
        localObject3 = localObject1;
        k = 0;
        if ((localObject3.error > 1.0E-008D) && (k < 100))
          break label294;
      }
    }
    label171: Option localOption;
    label287: label294: 
    do
    {
      if (i == 0)
        break label418;
      return localObject3.rationalNumber.negate();
      localRationalNumber1 = new RationalNumber(m - 1, 1);
      localRationalNumber2 = new RationalNumber(m, 1);
      break;
      int j = (int)(1.0D / paramDouble);
      if (1.0D / j < paramDouble)
      {
        localRationalNumber1 = new RationalNumber(1, j);
        localRationalNumber2 = new RationalNumber(1, j - 1);
        break;
      }
      localRationalNumber1 = new RationalNumber(1, j + 1);
      localRationalNumber2 = new RationalNumber(1, j);
      break;
      localObject3 = localObject2;
      break label149;
      RationalNumber localRationalNumber3 = RationalNumber.factoryMethod(((Option)localObject1).rationalNumber.numerator + ((Option)localObject2).rationalNumber.numerator, ((Option)localObject1).rationalNumber.divisor + ((Option)localObject2).rationalNumber.divisor);
      localOption = Option.factory(localRationalNumber3, paramDouble);
      if (paramDouble >= localRationalNumber3.doubleValue())
        break label397;
    }
    while (((Option)localObject2).error <= localOption.error);
    Object localObject2 = localOption;
    while (true)
    {
      if (localOption.error < localObject3.error)
        localObject3 = localOption;
      k++;
      break;
      label397: if (((Option)localObject1).error <= localOption.error)
        break label171;
      localObject1 = localOption;
    }
    label418: return (RationalNumber)(RationalNumber)localObject3.rationalNumber;
  }

  private static class Option
  {
    public final double error;
    public final RationalNumber rationalNumber;

    private Option(RationalNumber paramRationalNumber, double paramDouble)
    {
      this.rationalNumber = paramRationalNumber;
      this.error = paramDouble;
    }

    public static final Option factory(RationalNumber paramRationalNumber, double paramDouble)
    {
      return new Option(paramRationalNumber, Math.abs(paramRationalNumber.doubleValue() - paramDouble));
    }

    public String toString()
    {
      return this.rationalNumber.toString();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.RationalNumberUtilities
 * JD-Core Version:    0.6.0
 */