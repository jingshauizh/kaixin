package com.kaixin001.zxing;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.google.zxing.ResultPoint;
import com.kaixin001.zxing.camera.CameraManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ViewfinderView extends View
{
  private static final long ANIMATION_DELAY = 80L;
  private static final int CURRENT_POINT_OPACITY = 160;
  private static final int MAX_RESULT_POINTS = 20;
  private static final int[] SCANNER_ALPHA;
  private final int frameColor;
  private final int laserColor;
  private List<ResultPoint> lastPossibleResultPoints;
  private final int maskColor;
  private final Paint paint = new Paint();
  private List<ResultPoint> possibleResultPoints;
  private Bitmap resultBitmap;
  private final int resultColor;
  private final int resultPointColor;
  private int scannerAlpha;

  static
  {
    int[] arrayOfInt = new int[8];
    arrayOfInt[1] = 64;
    arrayOfInt[2] = 128;
    arrayOfInt[3] = 192;
    arrayOfInt[4] = 255;
    arrayOfInt[5] = 192;
    arrayOfInt[6] = 128;
    arrayOfInt[7] = 64;
    SCANNER_ALPHA = arrayOfInt;
  }

  public ViewfinderView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = getResources();
    this.maskColor = localResources.getColor(2131165191);
    this.resultColor = localResources.getColor(2131165187);
    this.frameColor = localResources.getColor(2131165189);
    this.laserColor = localResources.getColor(2131165190);
    this.resultPointColor = localResources.getColor(2131165192);
    this.scannerAlpha = 0;
    this.possibleResultPoints = new ArrayList(5);
    this.lastPossibleResultPoints = null;
  }

  public void addPossibleResultPoint(ResultPoint paramResultPoint)
  {
    List localList = this.possibleResultPoints;
    monitorenter;
    try
    {
      localList.add(paramResultPoint);
      int i = localList.size();
      if (i > 20)
        localList.subList(0, i - 10).clear();
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void drawResultBitmap(Bitmap paramBitmap)
  {
    this.resultBitmap = paramBitmap;
    invalidate();
  }

  public void drawViewfinder()
  {
    this.resultBitmap = null;
    invalidate();
  }

  public void onDraw(Canvas paramCanvas)
  {
    Rect localRect1 = CameraManager.get().getFramingRect();
    if (localRect1 == null)
      return;
    int i = paramCanvas.getWidth();
    int j = paramCanvas.getHeight();
    Paint localPaint = this.paint;
    if (this.resultBitmap != null);
    for (int k = this.resultColor; ; k = this.maskColor)
    {
      localPaint.setColor(k);
      paramCanvas.drawRect(0.0F, 0.0F, i, localRect1.top, this.paint);
      paramCanvas.drawRect(0.0F, localRect1.top, localRect1.left, 1 + localRect1.bottom, this.paint);
      paramCanvas.drawRect(1 + localRect1.right, localRect1.top, i, 1 + localRect1.bottom, this.paint);
      paramCanvas.drawRect(0.0F, 1 + localRect1.bottom, i, j, this.paint);
      if (this.resultBitmap == null)
        break;
      this.paint.setAlpha(160);
      paramCanvas.drawBitmap(this.resultBitmap, null, localRect1, this.paint);
      return;
    }
    this.paint.setColor(this.frameColor);
    paramCanvas.drawRect(localRect1.left, localRect1.top, 1 + localRect1.right, 2 + localRect1.top, this.paint);
    paramCanvas.drawRect(localRect1.left, 2 + localRect1.top, 2 + localRect1.left, -1 + localRect1.bottom, this.paint);
    paramCanvas.drawRect(-1 + localRect1.right, localRect1.top, 1 + localRect1.right, -1 + localRect1.bottom, this.paint);
    paramCanvas.drawRect(localRect1.left, -1 + localRect1.bottom, 1 + localRect1.right, 1 + localRect1.bottom, this.paint);
    this.paint.setColor(this.laserColor);
    this.paint.setAlpha(SCANNER_ALPHA[this.scannerAlpha]);
    this.scannerAlpha = ((1 + this.scannerAlpha) % SCANNER_ALPHA.length);
    int m = localRect1.height() / 2 + localRect1.top;
    paramCanvas.drawRect(2 + localRect1.left, m - 1, -1 + localRect1.right, m + 2, this.paint);
    Rect localRect2 = CameraManager.get().getFramingRectInPreview();
    float f1 = localRect1.width() / localRect2.width();
    float f2 = localRect1.height() / localRect2.height();
    List localList1 = this.possibleResultPoints;
    List localList2 = this.lastPossibleResultPoints;
    if (localList1.isEmpty())
      this.lastPossibleResultPoints = null;
    while (true)
    {
      if (localList2 != null)
      {
        this.paint.setAlpha(80);
        this.paint.setColor(this.resultPointColor);
        monitorenter;
      }
      try
      {
        Iterator localIterator2 = localList2.iterator();
        while (true)
        {
          if (!localIterator2.hasNext())
          {
            monitorexit;
            postInvalidateDelayed(80L, localRect1.left, localRect1.top, localRect1.right, localRect1.bottom);
            return;
            this.possibleResultPoints = new ArrayList(5);
            this.lastPossibleResultPoints = localList1;
            this.paint.setAlpha(160);
            this.paint.setColor(this.resultPointColor);
            monitorenter;
            while (true)
            {
              Iterator localIterator1;
              try
              {
                localIterator1 = localList1.iterator();
                if (!localIterator1.hasNext())
                {
                  monitorexit;
                  break;
                }
              }
              finally
              {
                monitorexit;
              }
              ResultPoint localResultPoint1 = (ResultPoint)localIterator1.next();
              paramCanvas.drawCircle(localRect1.left + (int)(f1 * localResultPoint1.getX()), localRect1.top + (int)(f2 * localResultPoint1.getY()), 6.0F, this.paint);
            }
          }
          ResultPoint localResultPoint2 = (ResultPoint)localIterator2.next();
          paramCanvas.drawCircle(localRect1.left + (int)(f1 * localResultPoint2.getX()), localRect1.top + (int)(f2 * localResultPoint2.getY()), 3.0F, this.paint);
        }
      }
      finally
      {
        monitorexit;
      }
    }
    throw localObject2;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.zxing.ViewfinderView
 * JD-Core Version:    0.6.0
 */