package org.apache.sanselan.common;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class RationalNumber extends Number
{
  private static final NumberFormat nf = DecimalFormat.getInstance();
  private static final long serialVersionUID = -1L;
  public final int divisor;
  public final int numerator;

  public RationalNumber(int paramInt1, int paramInt2)
  {
    this.numerator = paramInt1;
    this.divisor = paramInt2;
  }

  public static final RationalNumber factoryMethod(long paramLong1, long paramLong2)
  {
    if ((paramLong1 > 2147483647L) || (paramLong1 < -2147483648L) || (paramLong2 > 2147483647L) || (paramLong2 < -2147483648L))
      while (true)
      {
        if (((paramLong1 <= 2147483647L) && (paramLong1 >= -2147483648L) && (paramLong2 <= 2147483647L) && (paramLong2 >= -2147483648L)) || (Math.abs(paramLong1) <= 1L) || (Math.abs(paramLong2) <= 1L))
        {
          if (paramLong2 != 0L)
            break;
          throw new NumberFormatException("Invalid value, numerator: " + paramLong1 + ", divisor: " + paramLong2);
        }
        paramLong1 >>= 1;
        paramLong2 >>= 1;
      }
    long l1 = gcd(paramLong1, paramLong2);
    long l2 = paramLong2 / l1;
    return new RationalNumber((int)(paramLong1 / l1), (int)l2);
  }

  private static long gcd(long paramLong1, long paramLong2)
  {
    if (paramLong2 == 0L)
      return paramLong1;
    return gcd(paramLong2, paramLong1 % paramLong2);
  }

  public double doubleValue()
  {
    return this.numerator / this.divisor;
  }

  public float floatValue()
  {
    return this.numerator / this.divisor;
  }

  public int intValue()
  {
    return this.numerator / this.divisor;
  }

  public boolean isValid()
  {
    return this.divisor != 0;
  }

  public long longValue()
  {
    return this.numerator / this.divisor;
  }

  public RationalNumber negate()
  {
    return new RationalNumber(-this.numerator, this.divisor);
  }

  public String toDisplayString()
  {
    if (this.numerator % this.divisor == 0)
      return this.numerator / this.divisor;
    NumberFormat localNumberFormat = DecimalFormat.getInstance();
    localNumberFormat.setMaximumFractionDigits(3);
    return localNumberFormat.format(this.numerator / this.divisor);
  }

  public String toString()
  {
    if (this.divisor == 0)
      return "Invalid rational (" + this.numerator + "/" + this.divisor + ")";
    if (this.numerator % this.divisor == 0)
      return nf.format(this.numerator / this.divisor);
    return this.numerator + "/" + this.divisor + " (" + nf.format(this.numerator / this.divisor) + ")";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     org.apache.sanselan.common.RationalNumber
 * JD-Core Version:    0.6.0
 */