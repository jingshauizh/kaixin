package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.util.NoSuchElementException;

@Beta
@GwtCompatible
public abstract class DiscreteDomain<C extends Comparable>
{
  public abstract long distance(C paramC1, C paramC2);

  public C maxValue()
  {
    throw new NoSuchElementException();
  }

  public C minValue()
  {
    throw new NoSuchElementException();
  }

  public abstract C next(C paramC);

  public abstract C previous(C paramC);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.DiscreteDomain
 * JD-Core Version:    0.6.0
 */