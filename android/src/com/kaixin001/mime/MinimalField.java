package com.kaixin001.mime;

public class MinimalField
{
  private final String name;
  private final String value;

  MinimalField(String paramString1, String paramString2)
  {
    this.name = paramString1;
    this.value = paramString2;
  }

  public String getBody()
  {
    return this.value;
  }

  public String getName()
  {
    return this.name;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.name);
    localStringBuilder.append(": ");
    localStringBuilder.append(this.value);
    return localStringBuilder.toString();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.mime.MinimalField
 * JD-Core Version:    0.6.0
 */