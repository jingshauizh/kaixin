package com.amap.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.core.m;
import com.amap.mapapi.core.t;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Iterator;

class az extends m<ArrayList<av.a>, ArrayList<av.a>>
{
  private w i = null;

  public az(ArrayList<av.a> paramArrayList, Proxy paramProxy, String paramString1, String paramString2)
  {
    super(paramArrayList, paramProxy, paramString1, paramString2);
  }

  private void a(av.a parama, int paramInt)
  {
    if ((parama == null) || (paramInt < 0));
    do
      return;
    while ((this.i == null) || (this.i.o == null));
    t localt = this.i.o;
    int j = localt.size();
    int k = 0;
    label43: av.a locala;
    if (k < j)
    {
      locala = (av.a)localt.get(k);
      if (locala != null)
        break label72;
    }
    label72: 
    do
    {
      k++;
      break label43;
      break;
    }
    while (!locala.equals(parama));
    locala.g = paramInt;
  }

  private byte[] a(Bitmap paramBitmap)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
    return localByteArrayOutputStream.toByteArray();
  }

  public int a(InputStream paramInputStream, av.a parama)
  {
    int j;
    if ((parama == null) || (paramInputStream == null))
      j = -1;
    byte[] arrayOfByte;
    do
    {
      do
      {
        return j;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(parama.b);
        localStringBuilder.append("-");
        localStringBuilder.append(parama.c);
        localStringBuilder.append("-");
        localStringBuilder.append(parama.d);
        if ((this.i == null) || (this.i.m == null))
          return -1;
        j = this.i.m.a(null, paramInputStream, false, null, localStringBuilder.toString());
        if (j < 0)
          return -1;
        a(parama, j);
      }
      while ((this.i == null) || (this.i.g != true));
      arrayOfByte = a(this.i.m.a(j));
    }
    while ((this.i == null) || (this.i.n == null));
    this.i.n.a(arrayOfByte, parama.b, parama.c, parama.d);
    return j;
  }

  protected ArrayList<av.a> a()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = ((ArrayList)this.b).iterator();
    while (localIterator.hasNext())
      localArrayList.add(new av.a((av.a)localIterator.next()));
    return localArrayList;
  }

  protected ArrayList<av.a> a(InputStream paramInputStream)
    throws AMapException
  {
    Object localObject1 = this.b;
    Object localObject2 = null;
    if (localObject1 == null);
    do
    {
      return localObject2;
      int j = ((ArrayList)this.b).size();
      int k = 0;
      while (k < j)
      {
        av.a locala = (av.a)((ArrayList)this.b).get(k);
        if (a(paramInputStream, locala) < 0)
        {
          if (localObject2 == null)
            localObject2 = new ArrayList();
          ((ArrayList)localObject2).add(new av.a(locala));
        }
        Object localObject3 = localObject2;
        k++;
        localObject2 = localObject3;
      }
    }
    while (paramInputStream == null);
    try
    {
      paramInputStream.close();
      return localObject2;
    }
    catch (IOException localIOException)
    {
    }
    throw new AMapException("IO 操作异常 - IOException");
  }

  public void a(w paramw)
  {
    this.i = paramw;
  }

  protected byte[] d()
  {
    return null;
  }

  protected String e()
  {
    return this.i.j.a(((av.a)((ArrayList)this.b).get(0)).b, ((av.a)((ArrayList)this.b).get(0)).c, ((av.a)((ArrayList)this.b).get(0)).d);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.az
 * JD-Core Version:    0.6.0
 */