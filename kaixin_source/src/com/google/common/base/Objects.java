package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import java.util.Arrays;
import javax.annotation.Nullable;

@GwtCompatible
public final class Objects
{
  public static boolean equal(@Nullable Object paramObject1, @Nullable Object paramObject2)
  {
    return (paramObject1 == paramObject2) || ((paramObject1 != null) && (paramObject1.equals(paramObject2)));
  }

  public static <T> T firstNonNull(@Nullable T paramT1, @Nullable T paramT2)
  {
    if (paramT1 != null)
      return paramT1;
    return Preconditions.checkNotNull(paramT2);
  }

  public static int hashCode(@Nullable Object[] paramArrayOfObject)
  {
    return Arrays.hashCode(paramArrayOfObject);
  }

  private static String simpleName(Class<?> paramClass)
  {
    String str = paramClass.getName().replaceAll("\\$[0-9]+", "\\$");
    int i = str.lastIndexOf('$');
    if (i == -1)
      i = str.lastIndexOf('.');
    return str.substring(i + 1);
  }

  public static ToStringHelper toStringHelper(Class<?> paramClass)
  {
    return new ToStringHelper(simpleName(paramClass), null);
  }

  public static ToStringHelper toStringHelper(Object paramObject)
  {
    return new ToStringHelper(simpleName(paramObject.getClass()), null);
  }

  public static ToStringHelper toStringHelper(String paramString)
  {
    return new ToStringHelper(paramString, null);
  }

  public static final class ToStringHelper
  {
    private final StringBuilder builder;
    private boolean needsSeparator = false;

    private ToStringHelper(String paramString)
    {
      Preconditions.checkNotNull(paramString);
      this.builder = new StringBuilder(32).append(paramString).append('{');
    }

    private StringBuilder maybeAppendSeparator()
    {
      if (this.needsSeparator)
        return this.builder.append(", ");
      this.needsSeparator = true;
      return this.builder;
    }

    public ToStringHelper add(String paramString, @Nullable Object paramObject)
    {
      Preconditions.checkNotNull(paramString);
      maybeAppendSeparator().append(paramString).append('=').append(paramObject);
      return this;
    }

    public ToStringHelper addValue(@Nullable Object paramObject)
    {
      maybeAppendSeparator().append(paramObject);
      return this;
    }

    public String toString()
    {
      try
      {
        String str = '}';
        return str;
      }
      finally
      {
        this.builder.setLength(-1 + this.builder.length());
      }
      throw localObject;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.base.Objects
 * JD-Core Version:    0.6.0
 */