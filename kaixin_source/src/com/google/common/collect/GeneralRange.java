package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Comparator;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true)
final class GeneralRange<T>
  implements Serializable
{
  private final Comparator<? super T> comparator;
  private final BoundType lowerBoundType;
  private final Optional<T> lowerEndpoint;
  private transient GeneralRange<T> reverse;
  private final BoundType upperBoundType;
  private final Optional<T> upperEndpoint;

  private GeneralRange(Comparator<? super T> paramComparator, Optional<T> paramOptional1, BoundType paramBoundType1, Optional<T> paramOptional2, BoundType paramBoundType2)
  {
    this.comparator = ((Comparator)Preconditions.checkNotNull(paramComparator));
    this.lowerEndpoint = ((Optional)Preconditions.checkNotNull(paramOptional1));
    this.lowerBoundType = ((BoundType)Preconditions.checkNotNull(paramBoundType1));
    this.upperEndpoint = ((Optional)Preconditions.checkNotNull(paramOptional2));
    this.upperBoundType = ((BoundType)Preconditions.checkNotNull(paramBoundType2));
    boolean bool2;
    int j;
    if ((paramOptional1.isPresent()) && (paramOptional2.isPresent()))
    {
      int i = paramComparator.compare(paramOptional1.get(), paramOptional2.get());
      if (i > 0)
        break label165;
      bool2 = bool1;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramOptional1;
      arrayOfObject[bool1] = paramOptional2;
      Preconditions.checkArgument(bool2, "lowerEndpoint (%s) > upperEndpoint (%s)", arrayOfObject);
      if (i == 0)
      {
        if (paramBoundType1 == BoundType.OPEN)
          break label171;
        j = bool1;
        label148: if (paramBoundType2 == BoundType.OPEN)
          break label177;
      }
    }
    while (true)
    {
      Preconditions.checkArgument(j | bool1);
      return;
      label165: bool2 = false;
      break;
      label171: j = 0;
      break label148;
      label177: bool1 = false;
    }
  }

  static <T> GeneralRange<T> all(Comparator<? super T> paramComparator)
  {
    return new GeneralRange(paramComparator, Optional.absent(), BoundType.OPEN, Optional.absent(), BoundType.OPEN);
  }

  static <T> GeneralRange<T> downTo(Comparator<? super T> paramComparator, T paramT, BoundType paramBoundType)
  {
    return new GeneralRange(paramComparator, Optional.of(paramT), paramBoundType, Optional.absent(), BoundType.OPEN);
  }

  static <T extends Comparable> GeneralRange<T> from(Range<T> paramRange)
  {
    Optional localOptional1;
    BoundType localBoundType1;
    label27: Optional localOptional2;
    if (paramRange.hasLowerBound())
    {
      localOptional1 = Optional.of(paramRange.lowerEndpoint());
      if (!paramRange.hasLowerBound())
        break label78;
      localBoundType1 = paramRange.lowerBoundType();
      if (!paramRange.hasUpperBound())
        break label85;
      localOptional2 = Optional.of(paramRange.upperEndpoint());
      label42: if (!paramRange.hasUpperBound())
        break label92;
    }
    label78: label85: label92: for (BoundType localBoundType2 = paramRange.upperBoundType(); ; localBoundType2 = BoundType.OPEN)
    {
      return new GeneralRange(Ordering.natural(), localOptional1, localBoundType1, localOptional2, localBoundType2);
      localOptional1 = Optional.absent();
      break;
      localBoundType1 = BoundType.OPEN;
      break label27;
      localOptional2 = Optional.absent();
      break label42;
    }
  }

  static <T> GeneralRange<T> range(Comparator<? super T> paramComparator, T paramT1, BoundType paramBoundType1, T paramT2, BoundType paramBoundType2)
  {
    return new GeneralRange(paramComparator, Optional.of(paramT1), paramBoundType1, Optional.of(paramT2), paramBoundType2);
  }

  static <T> GeneralRange<T> upTo(Comparator<? super T> paramComparator, T paramT, BoundType paramBoundType)
  {
    return new GeneralRange(paramComparator, Optional.absent(), BoundType.OPEN, Optional.of(paramT), paramBoundType);
  }

  Comparator<? super T> comparator()
  {
    return this.comparator;
  }

  boolean contains(T paramT)
  {
    Preconditions.checkNotNull(paramT);
    return (!tooLow(paramT)) && (!tooHigh(paramT));
  }

  public boolean equals(@Nullable Object paramObject)
  {
    boolean bool1 = paramObject instanceof GeneralRange;
    int i = 0;
    if (bool1)
    {
      GeneralRange localGeneralRange = (GeneralRange)paramObject;
      boolean bool2 = this.comparator.equals(localGeneralRange.comparator);
      i = 0;
      if (bool2)
      {
        boolean bool3 = this.lowerEndpoint.equals(localGeneralRange.lowerEndpoint);
        i = 0;
        if (bool3)
        {
          boolean bool4 = this.lowerBoundType.equals(localGeneralRange.lowerBoundType);
          i = 0;
          if (bool4)
          {
            boolean bool5 = this.upperEndpoint.equals(localGeneralRange.upperEndpoint);
            i = 0;
            if (bool5)
            {
              boolean bool6 = this.upperBoundType.equals(localGeneralRange.upperBoundType);
              i = 0;
              if (bool6)
                i = 1;
            }
          }
        }
      }
    }
    return i;
  }

  boolean hasLowerBound()
  {
    return this.lowerEndpoint.isPresent();
  }

  boolean hasUpperBound()
  {
    return this.upperEndpoint.isPresent();
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[5];
    arrayOfObject[0] = this.comparator;
    arrayOfObject[1] = this.lowerEndpoint;
    arrayOfObject[2] = this.lowerBoundType;
    arrayOfObject[3] = this.upperEndpoint;
    arrayOfObject[4] = this.upperBoundType;
    return Objects.hashCode(arrayOfObject);
  }

  GeneralRange<T> intersect(GeneralRange<T> paramGeneralRange)
  {
    Preconditions.checkNotNull(paramGeneralRange);
    Preconditions.checkArgument(this.comparator.equals(paramGeneralRange.comparator));
    Object localObject = this.lowerEndpoint;
    BoundType localBoundType1 = this.lowerBoundType;
    Optional localOptional;
    BoundType localBoundType2;
    if (!hasLowerBound())
    {
      localObject = paramGeneralRange.lowerEndpoint;
      localBoundType1 = paramGeneralRange.lowerBoundType;
      localOptional = this.upperEndpoint;
      localBoundType2 = this.upperBoundType;
      if (hasUpperBound())
        break label238;
      localOptional = paramGeneralRange.upperEndpoint;
      localBoundType2 = paramGeneralRange.upperBoundType;
    }
    while (true)
    {
      if ((((Optional)localObject).isPresent()) && (localOptional.isPresent()))
      {
        int k = this.comparator.compare(((Optional)localObject).get(), localOptional.get());
        if ((k > 0) || ((k == 0) && (localBoundType1 == BoundType.OPEN) && (localBoundType2 == BoundType.OPEN)))
        {
          localObject = localOptional;
          localBoundType1 = BoundType.OPEN;
          localBoundType2 = BoundType.CLOSED;
        }
      }
      return new GeneralRange(this.comparator, (Optional)localObject, localBoundType1, localOptional, localBoundType2);
      if (!paramGeneralRange.hasLowerBound())
        break;
      int i = this.comparator.compare(this.lowerEndpoint.get(), paramGeneralRange.lowerEndpoint.get());
      if ((i >= 0) && ((i != 0) || (paramGeneralRange.lowerBoundType != BoundType.OPEN)))
        break;
      localObject = paramGeneralRange.lowerEndpoint;
      localBoundType1 = paramGeneralRange.lowerBoundType;
      break;
      label238: if (!paramGeneralRange.hasUpperBound())
        continue;
      int j = this.comparator.compare(this.upperEndpoint.get(), paramGeneralRange.upperEndpoint.get());
      if ((j <= 0) && ((j != 0) || (paramGeneralRange.upperBoundType != BoundType.OPEN)))
        continue;
      localOptional = paramGeneralRange.upperEndpoint;
      localBoundType2 = paramGeneralRange.upperBoundType;
    }
  }

  boolean isEmpty()
  {
    return ((hasUpperBound()) && (tooLow(this.upperEndpoint.get()))) || ((hasLowerBound()) && (tooHigh(this.lowerEndpoint.get())));
  }

  public GeneralRange<T> reverse()
  {
    GeneralRange localGeneralRange1 = this.reverse;
    if (localGeneralRange1 == null)
    {
      GeneralRange localGeneralRange2 = new GeneralRange(Ordering.from(this.comparator).reverse(), this.upperEndpoint, this.upperBoundType, this.lowerEndpoint, this.lowerBoundType);
      localGeneralRange2.reverse = this;
      this.reverse = localGeneralRange2;
      return localGeneralRange2;
    }
    return localGeneralRange1;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.comparator).append(":");
    switch (1.$SwitchMap$com$google$common$collect$BoundType[this.lowerBoundType.ordinal()])
    {
    default:
      if (!hasLowerBound())
        break;
      localStringBuilder.append(this.lowerEndpoint.get());
      label75: localStringBuilder.append(',');
      if (hasUpperBound())
      {
        localStringBuilder.append(this.upperEndpoint.get());
        label101: switch (1.$SwitchMap$com$google$common$collect$BoundType[this.upperBoundType.ordinal()])
        {
        default:
        case 1:
        case 2:
        }
      }
    case 1:
    case 2:
    }
    while (true)
    {
      return localStringBuilder.toString();
      localStringBuilder.append('[');
      break;
      localStringBuilder.append('(');
      break;
      localStringBuilder.append("-∞");
      break label75;
      localStringBuilder.append("∞");
      break label101;
      localStringBuilder.append(']');
      continue;
      localStringBuilder.append(')');
    }
  }

  boolean tooHigh(T paramT)
  {
    int i = 1;
    if (!hasUpperBound())
      return false;
    Object localObject = this.upperEndpoint.get();
    int j = this.comparator.compare(paramT, localObject);
    int k;
    int m;
    if (j > 0)
    {
      k = i;
      if (j != 0)
        break label72;
      m = i;
      label48: if (this.upperBoundType != BoundType.OPEN)
        break label78;
    }
    while (true)
    {
      return k | m & i;
      k = 0;
      break;
      label72: m = 0;
      break label48;
      label78: i = 0;
    }
  }

  boolean tooLow(T paramT)
  {
    int i = 1;
    if (!hasLowerBound())
      return false;
    Object localObject = this.lowerEndpoint.get();
    int j = this.comparator.compare(paramT, localObject);
    int k;
    int m;
    if (j < 0)
    {
      k = i;
      if (j != 0)
        break label72;
      m = i;
      label48: if (this.lowerBoundType != BoundType.OPEN)
        break label78;
    }
    while (true)
    {
      return k | m & i;
      k = 0;
      break;
      label72: m = 0;
      break label48;
      label78: i = 0;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.GeneralRange
 * JD-Core Version:    0.6.0
 */