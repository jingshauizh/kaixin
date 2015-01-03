package com.amap.mapapi.offlinemap;

public class City
  implements Comparable<Object>
{
  private String a;
  private String b;
  private String c;
  private String d;
  private String e;

  public City(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    setProvince(paramString1);
    setCity(paramString2);
    setInitial(paramString4);
    this.c = paramString3;
    setPinyin(paramString5);
  }

  public int compareTo(Object paramObject)
  {
    String str = ((City)paramObject).d;
    int k;
    if (str.charAt(0) > this.d.charAt(0))
      k = -1;
    int i;
    int j;
    do
    {
      return k;
      i = str.charAt(0);
      j = this.d.charAt(0);
      k = 0;
    }
    while (i >= j);
    return 1;
  }

  public String getCity()
  {
    return this.b;
  }

  public String getCode()
  {
    return this.c;
  }

  public String getInitial()
  {
    return this.d;
  }

  public String getPinyin()
  {
    return this.e;
  }

  public String getProvince()
  {
    return this.a;
  }

  public void setCity(String paramString)
  {
    this.b = paramString;
  }

  public void setCode(String paramString)
  {
    this.c = paramString;
  }

  public void setInitial(String paramString)
  {
    this.d = paramString;
  }

  public void setPinyin(String paramString)
  {
    this.e = paramString;
  }

  public void setProvince(String paramString)
  {
    this.a = paramString;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.offlinemap.City
 * JD-Core Version:    0.6.0
 */