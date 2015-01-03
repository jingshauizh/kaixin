package com.amap.mapapi.offlinemap;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import com.amap.mapapi.core.b;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

public final class OfflineMapManager
{
  c a = new c(paramContext, this.d);
  OfflineMapDownloadListener b;
  ArrayList<City> c = new ArrayList();
  Handler d = new d(this);

  public OfflineMapManager(Context paramContext, OfflineMapDownloadListener paramOfflineMapDownloadListener)
  {
    b.a(paramContext);
    this.b = paramOfflineMapDownloadListener;
    a(paramContext);
  }

  private void a(Context paramContext)
  {
    InputStreamReader localInputStreamReader;
    StringBuffer localStringBuffer;
    try
    {
      localInputStreamReader = new InputStreamReader(paramContext.getAssets().open("1010.png"));
      localStringBuffer = new StringBuffer();
      char[] arrayOfChar = new char[1024];
      while (localInputStreamReader.read(arrayOfChar) > 0)
        localStringBuffer.append(arrayOfChar);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    localInputStreamReader.close();
    this.a.a(new JSONObject(localStringBuffer.toString()));
  }

  private void a(i parami)
  {
    this.c.add(parami);
    g localg = new g(parami);
    localg.a(this.a.b.size());
    localg.a = 2;
    this.a.b.add(localg);
    this.a.a(-1 + this.a.b.size());
  }

  public boolean downloadByCityCode(String paramString)
  {
    i locali = getItemByCityCode(paramString);
    if (locali != null)
    {
      a(locali);
      return true;
    }
    return false;
  }

  public boolean downloadByCityName(String paramString)
  {
    i locali = getItemByCityName(paramString);
    if (locali != null)
    {
      a(locali);
      return true;
    }
    return false;
  }

  public ArrayList<City> getDownloadingCityList()
  {
    return this.c;
  }

  public i getItemByCityCode(String paramString)
  {
    Iterator localIterator = this.a.c.iterator();
    while (localIterator.hasNext())
    {
      i locali = (i)localIterator.next();
      if (locali.getCode().equals(paramString))
        return locali;
    }
    return null;
  }

  public i getItemByCityName(String paramString)
  {
    Iterator localIterator = this.a.c.iterator();
    while (localIterator.hasNext())
    {
      i locali = (i)localIterator.next();
      String str = locali.getCity();
      if ((str.contains(paramString)) || (paramString.contains(str)))
        return locali;
    }
    return null;
  }

  public ArrayList<City> getOfflineCityList()
  {
    return this.a.d;
  }

  public void getUpdateInfo(String paramString)
  {
  }

  public void pause()
  {
    this.a.b(0);
  }

  public void remove(String paramString)
  {
    i locali = getItemByCityName(paramString);
    if (locali != null)
    {
      g localg = new g(locali);
      this.a.a(localg);
    }
  }

  public void restart()
  {
    this.a.a(-1 + this.a.b.size());
  }

  public void stop()
  {
    this.a.b();
  }

  public static abstract interface OfflineMapDownloadListener
  {
    public abstract void onDownload(int paramInt1, int paramInt2);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.offlinemap.OfflineMapManager
 * JD-Core Version:    0.6.0
 */