package com.google.common.base;

import java.lang.ref.SoftReference;

public abstract class FinalizableSoftReference<T> extends SoftReference<T>
  implements FinalizableReference
{
  protected FinalizableSoftReference(T paramT, FinalizableReferenceQueue paramFinalizableReferenceQueue)
  {
    super(paramT, paramFinalizableReferenceQueue.queue);
    paramFinalizableReferenceQueue.cleanUp();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.google.common.base.FinalizableSoftReference
 * JD-Core Version:    0.6.0
 */