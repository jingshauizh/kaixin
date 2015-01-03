package com.tencent.plus;

class e
  implements Runnable
{
  e(a parama)
  {
  }

  public void run()
  {
    try
    {
      Thread.sleep(300L);
      this.a.post(new d(this));
      a.a(this.a, false);
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      while (true)
        localInterruptedException.printStackTrace();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.plus.e
 * JD-Core Version:    0.6.0
 */