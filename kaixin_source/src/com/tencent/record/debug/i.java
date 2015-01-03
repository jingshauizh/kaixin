package com.tencent.record.debug;

import com.tencent.record.b.c;

public abstract class i
{
  private volatile int a = 63;
  private volatile boolean b = true;
  private b c = b.a;

  public i()
  {
    this(63, true, b.a);
  }

  public i(int paramInt, boolean paramBoolean, b paramb)
  {
    a(paramInt);
    a(paramBoolean);
    a(paramb);
  }

  public void a(int paramInt)
  {
    this.a = paramInt;
  }

  protected abstract void a(int paramInt, Thread paramThread, long paramLong, String paramString1, String paramString2, Throwable paramThrowable);

  public void a(b paramb)
  {
    this.c = paramb;
  }

  public void a(boolean paramBoolean)
  {
    this.b = paramBoolean;
  }

  public void b(int paramInt, Thread paramThread, long paramLong, String paramString1, String paramString2, Throwable paramThrowable)
  {
    if ((d()) && (c.a(this.a, paramInt)))
      a(paramInt, paramThread, paramLong, paramString1, paramString2, paramThrowable);
  }

  public boolean d()
  {
    return this.b;
  }

  public b e()
  {
    return this.c;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.record.debug.i
 * JD-Core Version:    0.6.0
 */