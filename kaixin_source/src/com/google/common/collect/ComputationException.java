package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public class ComputationException extends RuntimeException
{
  private static final long serialVersionUID;

  public ComputationException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.collect.ComputationException
 * JD-Core Version:    0.6.0
 */