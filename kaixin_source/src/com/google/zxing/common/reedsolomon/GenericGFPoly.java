package com.google.zxing.common.reedsolomon;

final class GenericGFPoly
{
  private final int[] coefficients;
  private final GenericGF field;

  GenericGFPoly(GenericGF paramGenericGF, int[] paramArrayOfInt)
  {
    if ((paramArrayOfInt == null) || (paramArrayOfInt.length == 0))
      throw new IllegalArgumentException();
    this.field = paramGenericGF;
    int i = paramArrayOfInt.length;
    if ((i > 1) && (paramArrayOfInt[0] == 0))
    {
      for (int j = 1; ; j++)
      {
        if ((j < i) && (paramArrayOfInt[j] == 0))
          continue;
        if (j != i)
          break;
        this.coefficients = paramGenericGF.getZero().coefficients;
        return;
      }
      this.coefficients = new int[i - j];
      System.arraycopy(paramArrayOfInt, j, this.coefficients, 0, this.coefficients.length);
      return;
    }
    this.coefficients = paramArrayOfInt;
  }

  GenericGFPoly addOrSubtract(GenericGFPoly paramGenericGFPoly)
  {
    if (!this.field.equals(paramGenericGFPoly.field))
      throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
    if (isZero())
      return paramGenericGFPoly;
    if (paramGenericGFPoly.isZero())
      return this;
    Object localObject1 = this.coefficients;
    Object localObject2 = paramGenericGFPoly.coefficients;
    if (localObject1.length > localObject2.length)
    {
      Object localObject3 = localObject1;
      localObject1 = localObject2;
      localObject2 = localObject3;
    }
    int[] arrayOfInt = new int[localObject2.length];
    int i = localObject2.length - localObject1.length;
    System.arraycopy(localObject2, 0, arrayOfInt, 0, i);
    for (int j = i; ; j++)
    {
      if (j >= localObject2.length)
        return new GenericGFPoly(this.field, arrayOfInt);
      arrayOfInt[j] = GenericGF.addOrSubtract(localObject1[(j - i)], localObject2[j]);
    }
  }

  GenericGFPoly[] divide(GenericGFPoly paramGenericGFPoly)
  {
    if (!this.field.equals(paramGenericGFPoly.field))
      throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
    if (paramGenericGFPoly.isZero())
      throw new IllegalArgumentException("Divide by 0");
    GenericGFPoly localGenericGFPoly1 = this.field.getZero();
    GenericGFPoly localGenericGFPoly2 = this;
    int i = paramGenericGFPoly.getCoefficient(paramGenericGFPoly.getDegree());
    int j = this.field.inverse(i);
    while (true)
    {
      if ((localGenericGFPoly2.getDegree() < paramGenericGFPoly.getDegree()) || (localGenericGFPoly2.isZero()))
        return new GenericGFPoly[] { localGenericGFPoly1, localGenericGFPoly2 };
      int k = localGenericGFPoly2.getDegree() - paramGenericGFPoly.getDegree();
      int m = this.field.multiply(localGenericGFPoly2.getCoefficient(localGenericGFPoly2.getDegree()), j);
      GenericGFPoly localGenericGFPoly3 = paramGenericGFPoly.multiplyByMonomial(k, m);
      localGenericGFPoly1 = localGenericGFPoly1.addOrSubtract(this.field.buildMonomial(k, m));
      localGenericGFPoly2 = localGenericGFPoly2.addOrSubtract(localGenericGFPoly3);
    }
  }

  int evaluateAt(int paramInt)
  {
    int j;
    if (paramInt == 0)
      j = getCoefficient(0);
    while (true)
    {
      return j;
      int i = this.coefficients.length;
      if (paramInt == 1)
      {
        j = 0;
        for (int m = 0; m < i; m++)
          j = GenericGF.addOrSubtract(j, this.coefficients[m]);
        continue;
      }
      j = this.coefficients[0];
      for (int k = 1; k < i; k++)
        j = GenericGF.addOrSubtract(this.field.multiply(paramInt, j), this.coefficients[k]);
    }
  }

  int getCoefficient(int paramInt)
  {
    return this.coefficients[(-1 + this.coefficients.length - paramInt)];
  }

  int[] getCoefficients()
  {
    return this.coefficients;
  }

  int getDegree()
  {
    return -1 + this.coefficients.length;
  }

  boolean isZero()
  {
    int i = this.coefficients[0];
    int j = 0;
    if (i == 0)
      j = 1;
    return j;
  }

  GenericGFPoly multiply(int paramInt)
  {
    if (paramInt == 0)
      this = this.field.getZero();
    do
      return this;
    while (paramInt == 1);
    int i = this.coefficients.length;
    int[] arrayOfInt = new int[i];
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return new GenericGFPoly(this.field, arrayOfInt);
      arrayOfInt[j] = this.field.multiply(this.coefficients[j], paramInt);
    }
  }

  GenericGFPoly multiply(GenericGFPoly paramGenericGFPoly)
  {
    if (!this.field.equals(paramGenericGFPoly.field))
      throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
    if ((isZero()) || (paramGenericGFPoly.isZero()))
      return this.field.getZero();
    int[] arrayOfInt1 = this.coefficients;
    int i = arrayOfInt1.length;
    int[] arrayOfInt2 = paramGenericGFPoly.coefficients;
    int j = arrayOfInt2.length;
    int[] arrayOfInt3 = new int[-1 + (i + j)];
    int k = 0;
    if (k >= i)
      return new GenericGFPoly(this.field, arrayOfInt3);
    int m = arrayOfInt1[k];
    for (int n = 0; ; n++)
    {
      if (n >= j)
      {
        k++;
        break;
      }
      arrayOfInt3[(k + n)] = GenericGF.addOrSubtract(arrayOfInt3[(k + n)], this.field.multiply(m, arrayOfInt2[n]));
    }
  }

  GenericGFPoly multiplyByMonomial(int paramInt1, int paramInt2)
  {
    if (paramInt1 < 0)
      throw new IllegalArgumentException();
    if (paramInt2 == 0)
      return this.field.getZero();
    int i = this.coefficients.length;
    int[] arrayOfInt = new int[i + paramInt1];
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return new GenericGFPoly(this.field, arrayOfInt);
      arrayOfInt[j] = this.field.multiply(this.coefficients[j], paramInt2);
    }
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer(8 * getDegree());
    int i = getDegree();
    if (i < 0)
      return localStringBuffer.toString();
    int j = getCoefficient(i);
    label53: int k;
    if (j != 0)
    {
      if (j >= 0)
        break label106;
      localStringBuffer.append(" - ");
      j = -j;
      if ((i == 0) || (j != 1))
      {
        k = this.field.log(j);
        if (k != 0)
          break label123;
        localStringBuffer.append('1');
      }
      label84: if (i != 0)
      {
        if (i != 1)
          break label156;
        localStringBuffer.append('x');
      }
    }
    while (true)
    {
      i--;
      break;
      label106: if (localStringBuffer.length() <= 0)
        break label53;
      localStringBuffer.append(" + ");
      break label53;
      label123: if (k == 1)
      {
        localStringBuffer.append('a');
        break label84;
      }
      localStringBuffer.append("a^");
      localStringBuffer.append(k);
      break label84;
      label156: localStringBuffer.append("x^");
      localStringBuffer.append(i);
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.reedsolomon.GenericGFPoly
 * JD-Core Version:    0.6.0
 */