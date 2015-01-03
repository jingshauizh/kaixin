package com.google.common.cache;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Preconditions;
import javax.annotation.Nullable;

@Beta
public final class CacheStats
{
  private final long evictionCount;
  private final long hitCount;
  private final long loadExceptionCount;
  private final long loadSuccessCount;
  private final long missCount;
  private final long totalLoadTime;

  public CacheStats(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6)
  {
    boolean bool1;
    boolean bool2;
    label27: boolean bool3;
    label42: boolean bool4;
    label57: boolean bool5;
    if (paramLong1 >= 0L)
    {
      bool1 = true;
      Preconditions.checkArgument(bool1);
      if (paramLong2 < 0L)
        break label133;
      bool2 = true;
      Preconditions.checkArgument(bool2);
      if (paramLong3 < 0L)
        break label139;
      bool3 = true;
      Preconditions.checkArgument(bool3);
      if (paramLong4 < 0L)
        break label145;
      bool4 = true;
      Preconditions.checkArgument(bool4);
      if (paramLong5 < 0L)
        break label151;
      bool5 = true;
      label72: Preconditions.checkArgument(bool5);
      if (paramLong6 < 0L)
        break label157;
    }
    label133: label139: label145: label151: label157: for (boolean bool6 = true; ; bool6 = false)
    {
      Preconditions.checkArgument(bool6);
      this.hitCount = paramLong1;
      this.missCount = paramLong2;
      this.loadSuccessCount = paramLong3;
      this.loadExceptionCount = paramLong4;
      this.totalLoadTime = paramLong5;
      this.evictionCount = paramLong6;
      return;
      bool1 = false;
      break;
      bool2 = false;
      break label27;
      bool3 = false;
      break label42;
      bool4 = false;
      break label57;
      bool5 = false;
      break label72;
    }
  }

  public double averageLoadPenalty()
  {
    long l = this.loadSuccessCount + this.loadExceptionCount;
    if (l == 0L)
      return 0.0D;
    return this.totalLoadTime / l;
  }

  public boolean equals(@Nullable Object paramObject)
  {
    boolean bool1 = paramObject instanceof CacheStats;
    int i = 0;
    if (bool1)
    {
      CacheStats localCacheStats = (CacheStats)paramObject;
      boolean bool2 = this.hitCount < localCacheStats.hitCount;
      i = 0;
      if (!bool2)
      {
        boolean bool3 = this.missCount < localCacheStats.missCount;
        i = 0;
        if (!bool3)
        {
          boolean bool4 = this.loadSuccessCount < localCacheStats.loadSuccessCount;
          i = 0;
          if (!bool4)
          {
            boolean bool5 = this.loadExceptionCount < localCacheStats.loadExceptionCount;
            i = 0;
            if (!bool5)
            {
              boolean bool6 = this.totalLoadTime < localCacheStats.totalLoadTime;
              i = 0;
              if (!bool6)
              {
                boolean bool7 = this.evictionCount < localCacheStats.evictionCount;
                i = 0;
                if (!bool7)
                  i = 1;
              }
            }
          }
        }
      }
    }
    return i;
  }

  public long evictionCount()
  {
    return this.evictionCount;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[6];
    arrayOfObject[0] = Long.valueOf(this.hitCount);
    arrayOfObject[1] = Long.valueOf(this.missCount);
    arrayOfObject[2] = Long.valueOf(this.loadSuccessCount);
    arrayOfObject[3] = Long.valueOf(this.loadExceptionCount);
    arrayOfObject[4] = Long.valueOf(this.totalLoadTime);
    arrayOfObject[5] = Long.valueOf(this.evictionCount);
    return Objects.hashCode(arrayOfObject);
  }

  public long hitCount()
  {
    return this.hitCount;
  }

  public double hitRate()
  {
    long l = requestCount();
    if (l == 0L)
      return 1.0D;
    return this.hitCount / l;
  }

  public long loadCount()
  {
    return this.loadSuccessCount + this.loadExceptionCount;
  }

  public long loadExceptionCount()
  {
    return this.loadExceptionCount;
  }

  public double loadExceptionRate()
  {
    long l = this.loadSuccessCount + this.loadExceptionCount;
    if (l == 0L)
      return 0.0D;
    return this.loadExceptionCount / l;
  }

  public long loadSuccessCount()
  {
    return this.loadSuccessCount;
  }

  public CacheStats minus(CacheStats paramCacheStats)
  {
    return new CacheStats(Math.max(0L, this.hitCount - paramCacheStats.hitCount), Math.max(0L, this.missCount - paramCacheStats.missCount), Math.max(0L, this.loadSuccessCount - paramCacheStats.loadSuccessCount), Math.max(0L, this.loadExceptionCount - paramCacheStats.loadExceptionCount), Math.max(0L, this.totalLoadTime - paramCacheStats.totalLoadTime), Math.max(0L, this.evictionCount - paramCacheStats.evictionCount));
  }

  public long missCount()
  {
    return this.missCount;
  }

  public double missRate()
  {
    long l = requestCount();
    if (l == 0L)
      return 0.0D;
    return this.missCount / l;
  }

  public long requestCount()
  {
    return this.hitCount + this.missCount;
  }

  public String toString()
  {
    return Objects.toStringHelper(this).add("hitCount", Long.valueOf(this.hitCount)).add("missCount", Long.valueOf(this.missCount)).add("loadSuccessCount", Long.valueOf(this.loadSuccessCount)).add("loadExceptionCount", Long.valueOf(this.loadExceptionCount)).add("totalLoadTime", Long.valueOf(this.totalLoadTime)).add("evictionCount", Long.valueOf(this.evictionCount)).toString();
  }

  public long totalLoadTime()
  {
    return this.totalLoadTime;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.cache.CacheStats
 * JD-Core Version:    0.6.0
 */