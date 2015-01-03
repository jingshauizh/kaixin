package com.umeng.common.net;

import org.json.JSONObject;

public abstract class r
{
  protected static String a = "POST";
  protected static String b = "GET";
  protected String c;

  public r(String paramString)
  {
    this.c = paramString;
  }

  public abstract JSONObject a();

  public void a(String paramString)
  {
    this.c = paramString;
  }

  public abstract String b();

  protected String c()
  {
    return a;
  }

  public String d()
  {
    return this.c;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.umeng.common.net.r
 * JD-Core Version:    0.6.0
 */