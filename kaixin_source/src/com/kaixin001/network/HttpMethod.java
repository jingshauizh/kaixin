package com.kaixin001.network;

public enum HttpMethod
{
  static
  {
    HttpMethod[] arrayOfHttpMethod = new HttpMethod[2];
    arrayOfHttpMethod[0] = GET;
    arrayOfHttpMethod[1] = POST;
    ENUM$VALUES = arrayOfHttpMethod;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.network.HttpMethod
 * JD-Core Version:    0.6.0
 */