package com.amap.mapapi.map;

import android.graphics.Point;
import com.mapabc.minimap.map.vmap.NativeMap;
import com.mapabc.minimap.map.vmap.NativeMapEngine;
import com.mapabc.minimap.map.vmap.VMapProjection;
import java.nio.ByteBuffer;

class ap
{
  public String a;
  boolean b = false;

  public boolean a(NativeMapEngine paramNativeMapEngine)
  {
    try
    {
      ByteBuffer localByteBuffer = ByteBuffer.allocate(131072);
      if (paramNativeMapEngine.hasBitMapData(this.a))
        paramNativeMapEngine.fillBitmapBufferData(this.a, localByteBuffer.array());
      while (true)
      {
        this.b = true;
        return true;
        NativeMap localNativeMap = new NativeMap();
        localNativeMap.initMap(localByteBuffer.array(), 256, 256);
        Point localPoint = VMapProjection.QuadKeyToTile(this.a);
        int i = this.a.length();
        localNativeMap.setMapParameter(128 + 256 * localPoint.x << 20 - i, 128 + 256 * localPoint.y << 20 - i, i, 0);
        localNativeMap.paintMap(paramNativeMapEngine, 1);
        paramNativeMapEngine.putBitmapData(this.a, localByteBuffer.array(), 256, 256);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return false;
    }
    finally
    {
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.ap
 * JD-Core Version:    0.6.0
 */