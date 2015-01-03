package com.tencent.record.debug;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class a extends i
  implements Handler.Callback
{
  private e a;
  private FileWriter b;
  private File c;
  private char[] d;
  private volatile c e;
  private volatile c f;
  private volatile c g;
  private volatile c h;
  private volatile boolean i = false;
  private HandlerThread j;
  private Handler k;

  public a(int paramInt, boolean paramBoolean, b paramb, e parame)
  {
    super(paramInt, paramBoolean, paramb);
    a(parame);
    this.e = new c();
    this.f = new c();
    this.g = this.e;
    this.h = this.f;
    this.d = new char[parame.f()];
    parame.b();
    h();
    this.j = new HandlerThread(parame.c(), parame.i());
    if (this.j != null)
      this.j.start();
    if (this.j.isAlive())
      this.k = new Handler(this.j.getLooper(), this);
    f();
  }

  public a(e parame)
  {
    this(63, true, b.a, parame);
  }

  private void f()
  {
    this.k.sendEmptyMessageDelayed(1024, c().g());
  }

  // ERROR //
  private void g()
  {
    // Byte code:
    //   0: invokestatic 117	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   3: aload_0
    //   4: getfield 72	com/tencent/record/debug/a:j	Landroid/os/HandlerThread;
    //   7: if_acmpeq +4 -> 11
    //   10: return
    //   11: aload_0
    //   12: getfield 32	com/tencent/record/debug/a:i	Z
    //   15: ifne -5 -> 10
    //   18: aload_0
    //   19: iconst_1
    //   20: putfield 32	com/tencent/record/debug/a:i	Z
    //   23: aload_0
    //   24: invokespecial 119	com/tencent/record/debug/a:j	()V
    //   27: aload_0
    //   28: getfield 48	com/tencent/record/debug/a:h	Lcom/tencent/record/debug/c;
    //   31: aload_0
    //   32: invokespecial 60	com/tencent/record/debug/a:h	()Ljava/io/Writer;
    //   35: aload_0
    //   36: getfield 55	com/tencent/record/debug/a:d	[C
    //   39: invokevirtual 122	com/tencent/record/debug/c:a	(Ljava/io/Writer;[C)V
    //   42: aload_0
    //   43: getfield 48	com/tencent/record/debug/a:h	Lcom/tencent/record/debug/c;
    //   46: invokevirtual 123	com/tencent/record/debug/c:b	()V
    //   49: aload_0
    //   50: iconst_0
    //   51: putfield 32	com/tencent/record/debug/a:i	Z
    //   54: return
    //   55: astore_2
    //   56: aload_0
    //   57: getfield 48	com/tencent/record/debug/a:h	Lcom/tencent/record/debug/c;
    //   60: invokevirtual 123	com/tencent/record/debug/c:b	()V
    //   63: goto -14 -> 49
    //   66: astore_1
    //   67: aload_0
    //   68: getfield 48	com/tencent/record/debug/a:h	Lcom/tencent/record/debug/c;
    //   71: invokevirtual 123	com/tencent/record/debug/c:b	()V
    //   74: aload_1
    //   75: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   27	42	55	java/io/IOException
    //   27	42	66	finally
  }

  private Writer h()
  {
    File localFile = c().a();
    if ((localFile != null) && (!localFile.equals(this.c)))
    {
      this.c = localFile;
      i();
    }
    try
    {
      this.b = new FileWriter(this.c, true);
      return this.b;
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  private void i()
  {
    try
    {
      if (this.b != null)
      {
        this.b.flush();
        this.b.close();
      }
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  private void j()
  {
    monitorenter;
    try
    {
      if (this.g == this.e)
        this.g = this.f;
      for (this.h = this.e; ; this.h = this.f)
      {
        return;
        this.g = this.e;
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void a()
  {
    if (this.k.hasMessages(1024))
      this.k.removeMessages(1024);
  }

  protected void a(int paramInt, Thread paramThread, long paramLong, String paramString1, String paramString2, Throwable paramThrowable)
  {
    a(e().a(paramInt, paramThread, paramLong, paramString1, paramString2, paramThrowable));
  }

  public void a(e parame)
  {
    this.a = parame;
  }

  protected void a(String paramString)
  {
    this.g.a(paramString);
    if (this.g.a() >= c().f())
      a();
  }

  public void b()
  {
    i();
    this.j.quit();
  }

  public e c()
  {
    return this.a;
  }

  public boolean handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
    case 1024:
    }
    while (true)
    {
      return true;
      g();
      f();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.record.debug.a
 * JD-Core Version:    0.6.0
 */