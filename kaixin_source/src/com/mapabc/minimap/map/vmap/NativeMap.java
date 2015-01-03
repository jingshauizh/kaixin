package com.mapabc.minimap.map.vmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import com.amap.mapapi.core.d;
import com.amap.mapapi.map.be;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class NativeMap
{
  public static final String MINIMAP_VERSION = "minimapv320";
  byte[] a = ByteBuffer.allocate(48000).array();
  private int b = 0;

  static
  {
    try
    {
      System.loadLibrary("minimapv320");
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public NativeMap()
  {
  }

  public NativeMap(Context paramContext)
  {
  }

  private static native int nativeCreate();

  private static native void nativeFinalizer(int paramInt);

  private static native void nativeGetLabelStruct(int paramInt, byte[] paramArrayOfByte);

  private static native int nativeGetMapAngle(int paramInt);

  private static native int nativeGetMapCenterX(int paramInt);

  private static native int nativeGetMapCenterY(int paramInt);

  private static native int nativeGetMapHeight(int paramInt);

  private static native int nativeGetMapLevel(int paramInt);

  private static native int nativeGetMapWidth(int paramInt);

  private static native void nativeGetScreenGridNames(int paramInt, byte[] paramArrayOfByte);

  private static native void nativeInitMap(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3);

  private static native boolean nativePaint(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3);

  private static native void nativePx20ToScreen(int paramInt1, int paramInt2, int paramInt3, be parambe);

  private static native void nativePxToScreen(int paramInt1, int paramInt2, int paramInt3, be parambe);

  private static native void nativeResetLabelManager(int paramInt);

  private static native void nativeScreenToPx(int paramInt1, int paramInt2, int paramInt3, be parambe);

  private static native void nativeScreenToPx20(int paramInt1, int paramInt2, int paramInt3, be parambe);

  private static native void nativeSetMapLevel(int paramInt1, int paramInt2);

  private static native void nativeSetMapParameter(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public void ScreenToPx(int paramInt1, int paramInt2, be parambe)
  {
    nativeScreenToPx(this.b, paramInt1, paramInt2, parambe);
  }

  public void ScreenToPx20(int paramInt1, int paramInt2, be parambe)
  {
    nativeScreenToPx20(this.b, paramInt1, paramInt2, parambe);
  }

  protected void finalize()
    throws Throwable
  {
    nativeFinalizer(this.b);
    this.b = 0;
    this.a = null;
  }

  public byte[] getLabelBuffer()
  {
    return this.a;
  }

  public void getLabelStruct(byte[] paramArrayOfByte)
  {
    nativeGetLabelStruct(this.b, paramArrayOfByte);
  }

  public int getMapAngle()
  {
    return nativeGetMapAngle(this.b);
  }

  public int getMapCenterX()
  {
    return nativeGetMapCenterX(this.b);
  }

  public int getMapCenterY()
  {
    return nativeGetMapCenterY(this.b);
  }

  public int getMapHeight()
  {
    return nativeGetMapHeight(this.b);
  }

  public int getMapLevel()
  {
    return nativeGetMapLevel(this.b);
  }

  public int getMapWidth()
  {
    return nativeGetMapWidth(this.b);
  }

  public String[] getScreenGridNames()
  {
    int i = 0;
    byte[] arrayOfByte = new byte[2048];
    nativeGetScreenGridNames(this.b, arrayOfByte);
    int j = 1;
    int k = arrayOfByte[0];
    String[] arrayOfString = new String[k];
    while (i < k)
    {
      int m = j + 1;
      int n = arrayOfByte[j];
      arrayOfString[i] = new String(arrayOfByte, m, n);
      j = n + m;
      i++;
    }
    return arrayOfString;
  }

  public void initMap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    nativeInitMap(this.b, paramArrayOfByte, paramInt1, paramInt2);
  }

  public boolean paint(NativeMapEngine paramNativeMapEngine, byte[] paramArrayOfByte, int paramInt)
  {
    return nativePaint(paramNativeMapEngine.b, this.b, paramArrayOfByte, paramInt);
  }

  public void paintLables(NativeMapEngine paramNativeMapEngine, Canvas paramCanvas, int paramInt)
  {
    Paint localPaint = new Paint();
    ArrayList localArrayList = new ArrayList();
    byte[] arrayOfByte = getLabelBuffer();
    int i = d.b(arrayOfByte, 0);
    int j = 0;
    int k = 2;
    if (j < i)
    {
      b localb1 = new b();
      localArrayList.add(localb1);
      int m = k + 1;
      int n = arrayOfByte[k];
      StringBuffer localStringBuffer = new StringBuffer();
      for (int i1 = 0; i1 < n; i1++)
      {
        localStringBuffer.append((char)d.b(arrayOfByte, m));
        m += 2;
      }
      localb1.a = localStringBuffer.toString();
      int i2 = m + 1;
      localb1.p = arrayOfByte[m];
      localb1.l = (0xFF000000 | d.a(arrayOfByte, i2));
      int i3 = i2 + 4;
      localb1.m = (0xFF000000 | d.a(arrayOfByte, i3));
      int i4 = i3 + 4;
      localb1.b = d.b(arrayOfByte, i4);
      int i5 = i4 + 4;
      localb1.c = d.b(arrayOfByte, i5);
      int i6 = i5 + 4;
      k = i6 + 1;
      localb1.n = arrayOfByte[i6];
      if (localb1.n == 0)
      {
        localb1.d = d.b(arrayOfByte, k);
        k += 4;
      }
      while (true)
      {
        j++;
        break;
        if (localb1.n != 1)
          continue;
        int i7 = k + 1;
        localb1.g = arrayOfByte[k];
        localb1.j = d.a(arrayOfByte, i7);
        int i8 = i7 + 4;
        localb1.k = d.a(arrayOfByte, i8);
        int i9 = i8 + 4;
        int i10 = i9 + 1;
        localb1.o = arrayOfByte[i9];
        localb1.e = d.b(arrayOfByte, i10);
        int i11 = i10 + 2;
        localb1.f = d.b(arrayOfByte, i11);
        k = i11 + 2;
        if (localb1.o > 0)
        {
          Bitmap localBitmap = paramNativeMapEngine.getIconBitmap(localb1.g);
          if (localBitmap != null)
            paramCanvas.drawBitmap(localBitmap, -6 + localb1.b, -6 + localb1.c, null);
        }
        if (localb1.o == 0)
        {
          localb1.h = (localb1.b - (localb1.e >> 1));
          localb1.i = (localb1.c - (localb1.f >> 1));
        }
        if (localb1.o == 1)
        {
          localb1.h = (localb1.b - (localb1.e >> 1));
          localb1.i = localb1.c;
          continue;
        }
        if (localb1.o == 2)
        {
          localb1.h = (6 + localb1.b);
          localb1.i = (localb1.c - (localb1.f >> 1));
          continue;
        }
        if (localb1.o != 3)
          continue;
        localb1.h = (-6 + localb1.b - localb1.e);
        localb1.i = (localb1.c - (localb1.f >> 1));
      }
    }
    localPaint.setAntiAlias(true);
    Paint.FontMetrics localFontMetrics = new Paint.FontMetrics();
    Matrix localMatrix1 = new Matrix();
    Matrix localMatrix2 = new Matrix();
    int i12 = 0;
    int i13 = 0;
    float[] arrayOfFloat1 = new float[2];
    float[] arrayOfFloat2 = new float[2];
    int[][] arrayOfInt = { { -1, -1 }, { 1, -1 }, { -1, 1 }, { 1, 1 }, { 0, 0 } };
    int i14 = localArrayList.size();
    int i15 = 0;
    label792: int i16;
    int i17;
    int i18;
    label813: b localb2;
    int i20;
    int i24;
    int i21;
    while (true)
      if (i15 < 5)
      {
        if ((paramInt < 2) && ((i15 == 1) || (i15 == 2) || (i15 == 3)))
        {
          i15++;
          continue;
        }
        if (i15 < 4)
        {
          localPaint.setAntiAlias(false);
          localPaint.setFakeBoldText(true);
          i16 = arrayOfInt[i15][0];
          i17 = arrayOfInt[i15][1];
          i18 = 0;
          if (i18 >= i14)
            break;
          localb2 = (b)localArrayList.get(i18);
          int i19 = localb2.l;
          if (i15 < 4)
            i19 = localb2.m;
          localPaint.setTextSize(localb2.p);
          localPaint.setColor(i19);
          localPaint.getFontMetrics(localFontMetrics);
          i20 = (int)(localFontMetrics.bottom - localFontMetrics.top);
          if (localb2.n != 1)
            break label1018;
          localPaint.setTextAlign(Paint.Align.LEFT);
          i24 = i16 + localb2.h;
          i21 = i17 + localb2.i + localb2.p;
        }
      }
    while (true)
    {
      label935: localMatrix1.reset();
      int i27 = localb2.d;
      int i29;
      if (localb2.n > 0)
      {
        i29 = localb2.a.length();
        if (i29 <= 7)
          paramCanvas.drawText(localb2.a, i24, i21, localPaint);
      }
      label1018: int i25;
      int i22;
      do
      {
        i18++;
        i12 = i24;
        i13 = i21;
        break label813;
        break;
        localPaint.setAntiAlias(true);
        localPaint.setFakeBoldText(false);
        break label792;
        if (localb2.n != 0)
          break label1345;
        localPaint.setTextAlign(Paint.Align.CENTER);
        i25 = i16 + localb2.b;
        i22 = i17 + localb2.c;
        break label935;
        int i30 = i29 / 7;
        if (i29 % 7 > 0)
          i30++;
        if (i29 % i30 == 0);
        for (int i31 = i29 / i30; ; i31 = 1 + i29 / i30)
        {
          int i32 = 0;
          int i33 = i22;
          int i34 = 0;
          while (i34 < i30)
          {
            int i35 = i32 + i31;
            if (i35 >= i29)
              i35 = i29;
            paramCanvas.drawText(localb2.a.substring(i32, i35), localb2.h, i33, localPaint);
            int i36 = i33 + (4 + localb2.p);
            i34++;
            i33 = i36;
            i32 = i35;
          }
          break;
        }
      }
      while (paramInt < 1);
      if (i27 < -45)
        i27 += 90;
      while (true)
      {
        localMatrix1.postRotate(-i27, i25, i22);
        int i28 = i22 - ((int)localFontMetrics.top + i20 / 2);
        arrayOfFloat2[0] = i25;
        arrayOfFloat2[1] = i28;
        localMatrix1.mapPoints(arrayOfFloat1, arrayOfFloat2);
        i26 = (int)arrayOfFloat1[0];
        i23 = (int)arrayOfFloat1[1];
        paramCanvas.save();
        paramCanvas.getMatrix(localMatrix2);
        localMatrix2.preRotate(-i27, i26, i23);
        paramCanvas.setMatrix(localMatrix2);
        paramCanvas.drawText(localb2.a, i26, i23, localPaint);
        paramCanvas.restore();
        break;
        if (i27 <= 45)
          continue;
        i27 -= 90;
      }
      return;
      label1345: int i23 = i13;
      int i26 = i12;
    }
  }

  public boolean paintMap(NativeMapEngine paramNativeMapEngine, int paramInt)
  {
    return paint(paramNativeMapEngine, this.a, paramInt);
  }

  public void px20ToScreen(int paramInt1, int paramInt2, be parambe)
  {
    nativePx20ToScreen(this.b, paramInt1, paramInt2, parambe);
  }

  public void pxToScreen(int paramInt1, int paramInt2, be parambe)
  {
    nativePxToScreen(this.b, paramInt1, paramInt2, parambe);
  }

  public void resetLabelManager()
  {
    nativeResetLabelManager(this.b);
  }

  public void setDrawMode(int paramInt)
  {
  }

  public void setMapLevel(int paramInt)
  {
    nativeSetMapLevel(this.b, paramInt);
  }

  public void setMapParameter(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    nativeSetMapParameter(this.b, paramInt1, paramInt2, paramInt3, paramInt4);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.mapabc.minimap.map.vmap.NativeMap
 * JD-Core Version:    0.6.0
 */