package com.umeng.common.net;

import android.os.AsyncTask;
import android.util.Log;
import com.umeng.xp.common.ExchangeConstants;

public class m extends q
{
  public o.a a(n paramn)
  {
    o localo = (o)a(paramn, o.class);
    if (localo == null)
      return o.a.b;
    return localo.a;
  }

  public void a(n paramn, a parama)
  {
    try
    {
      new b(paramn, parama).execute(new Integer[0]);
      return;
    }
    catch (Exception localException)
    {
      do
        Log.e(ExchangeConstants.LOG_TAG, "", localException);
      while (parama == null);
      parama.a(o.a.b);
    }
  }

  public static abstract interface a
  {
    public abstract void a();

    public abstract void a(o.a parama);
  }

  private class b extends AsyncTask<Integer, Integer, o.a>
  {
    private n b;
    private m.a c;

    public b(n parama, m.a arg3)
    {
      this.b = parama;
      Object localObject;
      this.c = localObject;
    }

    protected o.a a(Integer[] paramArrayOfInteger)
    {
      return m.this.a(this.b);
    }

    protected void a(o.a parama)
    {
      if (this.c != null)
        this.c.a(parama);
    }

    protected void onPreExecute()
    {
      if (this.c != null)
        this.c.a();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.net.m
 * JD-Core Version:    0.6.0
 */