package com.google.zxing.common.reedsolomon;

public final class ReedSolomonDecoder
{
  private final GenericGF field;

  public ReedSolomonDecoder(GenericGF paramGenericGF)
  {
    this.field = paramGenericGF;
  }

  private int[] findErrorLocations(GenericGFPoly paramGenericGFPoly)
    throws ReedSolomonException
  {
    int i = paramGenericGFPoly.getDegree();
    if (i == 1)
    {
      arrayOfInt = new int[1];
      arrayOfInt[0] = paramGenericGFPoly.getCoefficient(1);
      return arrayOfInt;
    }
    int[] arrayOfInt = new int[i];
    int j = 0;
    for (int k = 1; ; k++)
    {
      if ((k >= this.field.getSize()) || (j >= i))
      {
        if (j == i)
          break;
        throw new ReedSolomonException("Error locator degree does not match number of roots");
      }
      if (paramGenericGFPoly.evaluateAt(k) != 0)
        continue;
      arrayOfInt[j] = this.field.inverse(k);
      j++;
    }
  }

  private int[] findErrorMagnitudes(GenericGFPoly paramGenericGFPoly, int[] paramArrayOfInt, boolean paramBoolean)
  {
    int i = paramArrayOfInt.length;
    int[] arrayOfInt = new int[i];
    int k;
    int m;
    int n;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return arrayOfInt;
      k = this.field.inverse(paramArrayOfInt[j]);
      m = 1;
      n = 0;
      if (n < i)
        break;
      arrayOfInt[j] = this.field.multiply(paramGenericGFPoly.evaluateAt(k), this.field.inverse(m));
      if (!paramBoolean)
        continue;
      arrayOfInt[j] = this.field.multiply(arrayOfInt[j], k);
    }
    int i1;
    if (j != n)
    {
      i1 = this.field.multiply(paramArrayOfInt[n], k);
      if ((i1 & 0x1) != 0)
        break label159;
    }
    label159: for (int i2 = i1 | 0x1; ; i2 = i1 & 0xFFFFFFFE)
    {
      m = this.field.multiply(m, i2);
      n++;
      break;
    }
  }

  private GenericGFPoly[] runEuclideanAlgorithm(GenericGFPoly paramGenericGFPoly1, GenericGFPoly paramGenericGFPoly2, int paramInt)
    throws ReedSolomonException
  {
    if (paramGenericGFPoly1.getDegree() < paramGenericGFPoly2.getDegree())
    {
      GenericGFPoly localGenericGFPoly4 = paramGenericGFPoly1;
      paramGenericGFPoly1 = paramGenericGFPoly2;
      paramGenericGFPoly2 = localGenericGFPoly4;
    }
    Object localObject1 = paramGenericGFPoly1;
    Object localObject2 = paramGenericGFPoly2;
    Object localObject3 = this.field.getOne();
    GenericGFPoly localGenericGFPoly1 = this.field.getZero();
    Object localObject4 = this.field.getZero();
    GenericGFPoly localGenericGFPoly2 = this.field.getOne();
    int n;
    if (((GenericGFPoly)localObject2).getDegree() < paramInt / 2)
    {
      n = localGenericGFPoly2.getCoefficient(0);
      if (n == 0)
        throw new ReedSolomonException("sigmaTilde(0) was zero");
    }
    else
    {
      Object localObject5 = localObject1;
      Object localObject6 = localObject3;
      Object localObject7 = localObject4;
      localObject1 = localObject2;
      localObject3 = localGenericGFPoly1;
      localObject4 = localGenericGFPoly2;
      if (((GenericGFPoly)localObject1).isZero())
        throw new ReedSolomonException("r_{i-1} was zero");
      localObject2 = localObject5;
      GenericGFPoly localGenericGFPoly3 = this.field.getZero();
      int i = ((GenericGFPoly)localObject1).getCoefficient(((GenericGFPoly)localObject1).getDegree());
      int j = this.field.inverse(i);
      while (true)
      {
        if ((((GenericGFPoly)localObject2).getDegree() < ((GenericGFPoly)localObject1).getDegree()) || (((GenericGFPoly)localObject2).isZero()))
        {
          localGenericGFPoly1 = localGenericGFPoly3.multiply((GenericGFPoly)localObject3).addOrSubtract(localObject6);
          localGenericGFPoly2 = localGenericGFPoly3.multiply((GenericGFPoly)localObject4).addOrSubtract(localObject7);
          break;
        }
        int k = ((GenericGFPoly)localObject2).getDegree() - ((GenericGFPoly)localObject1).getDegree();
        int m = this.field.multiply(((GenericGFPoly)localObject2).getCoefficient(((GenericGFPoly)localObject2).getDegree()), j);
        localGenericGFPoly3 = localGenericGFPoly3.addOrSubtract(this.field.buildMonomial(k, m));
        localObject2 = ((GenericGFPoly)localObject2).addOrSubtract(((GenericGFPoly)localObject1).multiplyByMonomial(k, m));
      }
    }
    int i1 = this.field.inverse(n);
    return (GenericGFPoly)(GenericGFPoly)(GenericGFPoly)(GenericGFPoly)new GenericGFPoly[] { localGenericGFPoly2.multiply(i1), ((GenericGFPoly)localObject2).multiply(i1) };
  }

  public void decode(int[] paramArrayOfInt, int paramInt)
    throws ReedSolomonException
  {
    GenericGFPoly localGenericGFPoly1 = new GenericGFPoly(this.field, paramArrayOfInt);
    int[] arrayOfInt1 = new int[paramInt];
    boolean bool = this.field.equals(GenericGF.DATA_MATRIX_FIELD_256);
    int i = 1;
    int j = 0;
    if (j >= paramInt)
      if (i == 0)
        break label112;
    while (true)
    {
      return;
      GenericGF localGenericGF = this.field;
      if (bool);
      for (int k = j + 1; ; k = j)
      {
        int m = localGenericGFPoly1.evaluateAt(localGenericGF.exp(k));
        arrayOfInt1[(-1 + arrayOfInt1.length - j)] = m;
        if (m != 0)
          i = 0;
        j++;
        break;
      }
      label112: GenericGFPoly localGenericGFPoly2 = new GenericGFPoly(this.field, arrayOfInt1);
      GenericGFPoly[] arrayOfGenericGFPoly = runEuclideanAlgorithm(this.field.buildMonomial(paramInt, 1), localGenericGFPoly2, paramInt);
      GenericGFPoly localGenericGFPoly3 = arrayOfGenericGFPoly[0];
      GenericGFPoly localGenericGFPoly4 = arrayOfGenericGFPoly[1];
      int[] arrayOfInt2 = findErrorLocations(localGenericGFPoly3);
      int[] arrayOfInt3 = findErrorMagnitudes(localGenericGFPoly4, arrayOfInt2, bool);
      for (int n = 0; n < arrayOfInt2.length; n++)
      {
        int i1 = -1 + paramArrayOfInt.length - this.field.log(arrayOfInt2[n]);
        if (i1 < 0)
          throw new ReedSolomonException("Bad error location");
        paramArrayOfInt[i1] = GenericGF.addOrSubtract(paramArrayOfInt[i1], arrayOfInt3[n]);
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.reedsolomon.ReedSolomonDecoder
 * JD-Core Version:    0.6.0
 */