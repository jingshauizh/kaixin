package com.tencent.mm.sdk.plugin;

class MMPluginOAuth$1
  implements Runnable
{
  public void run()
  {
    if (MMPluginOAuth.b(this.bJ) != null)
      MMPluginOAuth.b(this.bJ).onResult(this.bJ);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.mm.sdk.plugin.MMPluginOAuth.1
 * JD-Core Version:    0.6.0
 */