package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.Nullable;

@Beta
@GwtCompatible
final class SortedLists
{
  public static <E, K extends Comparable> int binarySearch(List<E> paramList, Function<? super E, K> paramFunction, K paramK, KeyPresentBehavior paramKeyPresentBehavior, KeyAbsentBehavior paramKeyAbsentBehavior)
  {
    return binarySearch(paramList, paramFunction, paramK, Ordering.natural(), paramKeyPresentBehavior, paramKeyAbsentBehavior);
  }

  public static <E, K> int binarySearch(List<E> paramList, Function<? super E, K> paramFunction, K paramK, Comparator<? super K> paramComparator, KeyPresentBehavior paramKeyPresentBehavior, KeyAbsentBehavior paramKeyAbsentBehavior)
  {
    return binarySearch(Lists.transform(paramList, paramFunction), paramK, paramComparator, paramKeyPresentBehavior, paramKeyAbsentBehavior);
  }

  public static <E extends Comparable> int binarySearch(List<? extends E> paramList, E paramE, KeyPresentBehavior paramKeyPresentBehavior, KeyAbsentBehavior paramKeyAbsentBehavior)
  {
    Preconditions.checkNotNull(paramE);
    return binarySearch(paramList, Preconditions.checkNotNull(paramE), Ordering.natural(), paramKeyPresentBehavior, paramKeyAbsentBehavior);
  }

  public static <E> int binarySearch(List<? extends E> paramList, @Nullable E paramE, Comparator<? super E> paramComparator, KeyPresentBehavior paramKeyPresentBehavior, KeyAbsentBehavior paramKeyAbsentBehavior)
  {
    Preconditions.checkNotNull(paramComparator);
    Preconditions.checkNotNull(paramList);
    Preconditions.checkNotNull(paramKeyPresentBehavior);
    Preconditions.checkNotNull(paramKeyAbsentBehavior);
    if (!(paramList instanceof RandomAccess))
      paramList = Lists.newArrayList(paramList);
    int i = 0;
    int j = -1 + paramList.size();
    while (i <= j)
    {
      int k = i + j >>> 1;
      int m = paramComparator.compare(paramE, paramList.get(k));
      if (m < 0)
      {
        j = k - 1;
        continue;
      }
      if (m > 0)
      {
        i = k + 1;
        continue;
      }
      return i + paramKeyPresentBehavior.resultIndex(paramComparator, paramE, paramList.subList(i, j + 1), k - i);
    }
    return paramKeyAbsentBehavior.resultIndex(i);
  }

  public static abstract enum KeyAbsentBehavior
  {
    static
    {
      NEXT_HIGHER = new KeyAbsentBehavior("NEXT_HIGHER", 1)
      {
        public <E> int resultIndex(int paramInt)
        {
          return paramInt;
        }
      };
      INVERTED_INSERTION_INDEX = new KeyAbsentBehavior("INVERTED_INSERTION_INDEX", 2)
      {
        public <E> int resultIndex(int paramInt)
        {
          return paramInt ^ 0xFFFFFFFF;
        }
      };
      KeyAbsentBehavior[] arrayOfKeyAbsentBehavior = new KeyAbsentBehavior[3];
      arrayOfKeyAbsentBehavior[0] = NEXT_LOWER;
      arrayOfKeyAbsentBehavior[1] = NEXT_HIGHER;
      arrayOfKeyAbsentBehavior[2] = INVERTED_INSERTION_INDEX;
      $VALUES = arrayOfKeyAbsentBehavior;
    }

    abstract <E> int resultIndex(int paramInt);
  }

  public static abstract enum KeyPresentBehavior
  {
    static
    {
      FIRST_PRESENT = new KeyPresentBehavior("FIRST_PRESENT", 2)
      {
        <E> int resultIndex(Comparator<? super E> paramComparator, E paramE, List<? extends E> paramList, int paramInt)
        {
          int i = 0;
          int j = paramInt;
          while (i < j)
          {
            int k = i + j >>> 1;
            if (paramComparator.compare(paramList.get(k), paramE) < 0)
            {
              i = k + 1;
              continue;
            }
            j = k;
          }
          return i;
        }
      };
      FIRST_AFTER = new KeyPresentBehavior("FIRST_AFTER", 3)
      {
        public <E> int resultIndex(Comparator<? super E> paramComparator, E paramE, List<? extends E> paramList, int paramInt)
        {
          return 1 + LAST_PRESENT.resultIndex(paramComparator, paramE, paramList, paramInt);
        }
      };
      LAST_BEFORE = new KeyPresentBehavior("LAST_BEFORE", 4)
      {
        public <E> int resultIndex(Comparator<? super E> paramComparator, E paramE, List<? extends E> paramList, int paramInt)
        {
          return -1 + FIRST_PRESENT.resultIndex(paramComparator, paramE, paramList, paramInt);
        }
      };
      KeyPresentBehavior[] arrayOfKeyPresentBehavior = new KeyPresentBehavior[5];
      arrayOfKeyPresentBehavior[0] = ANY_PRESENT;
      arrayOfKeyPresentBehavior[1] = LAST_PRESENT;
      arrayOfKeyPresentBehavior[2] = FIRST_PRESENT;
      arrayOfKeyPresentBehavior[3] = FIRST_AFTER;
      arrayOfKeyPresentBehavior[4] = LAST_BEFORE;
      $VALUES = arrayOfKeyPresentBehavior;
    }

    abstract <E> int resultIndex(Comparator<? super E> paramComparator, E paramE, List<? extends E> paramList, int paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.SortedLists
 * JD-Core Version:    0.6.0
 */