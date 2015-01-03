package com.weibo.sdk.android;

import android.text.TextUtils;
import java.util.ArrayList;

public class WeiboParameters
{
  private ArrayList<String> mKeys = new ArrayList();
  private ArrayList<String> mValues = new ArrayList();

  private int getLocation(String paramString)
  {
    if (this.mKeys.contains(paramString))
      return this.mKeys.indexOf(paramString);
    return -1;
  }

  public void add(String paramString, int paramInt)
  {
    this.mKeys.add(paramString);
    this.mValues.add(String.valueOf(paramInt));
  }

  public void add(String paramString, long paramLong)
  {
    this.mKeys.add(paramString);
    this.mValues.add(String.valueOf(paramLong));
  }

  public void add(String paramString1, String paramString2)
  {
    if ((!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2)))
    {
      this.mKeys.add(paramString1);
      this.mValues.add(paramString2);
    }
  }

  public void addAll(WeiboParameters paramWeiboParameters)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramWeiboParameters.size())
        return;
      add(paramWeiboParameters.getKey(i), paramWeiboParameters.getValue(i));
    }
  }

  public void clear()
  {
    this.mKeys.clear();
    this.mValues.clear();
  }

  public String getKey(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.mKeys.size()))
      return (String)this.mKeys.get(paramInt);
    return "";
  }

  public String getValue(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.mKeys.size()))
      return (String)this.mValues.get(paramInt);
    return null;
  }

  public String getValue(String paramString)
  {
    int i = getLocation(paramString);
    if ((i >= 0) && (i < this.mKeys.size()))
      return (String)this.mValues.get(i);
    return null;
  }

  public void remove(int paramInt)
  {
    if (paramInt < this.mKeys.size())
    {
      this.mKeys.remove(paramInt);
      this.mValues.remove(paramInt);
    }
  }

  public void remove(String paramString)
  {
    int i = this.mKeys.indexOf(paramString);
    if (i >= 0)
    {
      this.mKeys.remove(i);
      this.mValues.remove(i);
    }
  }

  public int size()
  {
    return this.mKeys.size();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.weibo.sdk.android.WeiboParameters
 * JD-Core Version:    0.6.0
 */