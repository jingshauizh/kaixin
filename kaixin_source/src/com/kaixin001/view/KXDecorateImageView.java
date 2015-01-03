package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.kaixin001.util.KXLog;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class KXDecorateImageView extends ImageView
{
  private static final int MAX_ACCESSORIES_COUNT = 10;
  private static final String TAG = "KXDecorateImageView";
  private final LinkedList<KXAccessoryView> mAccessories = new LinkedList();
  private RectF mDispImageBounds = null;
  private final Point mPtMouseDown = new Point();
  private KXAccessoryView mTouchingAcc;

  public KXDecorateImageView(Context paramContext)
  {
    super(paramContext);
  }

  public KXDecorateImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  private void resetAllHighLight()
  {
    Iterator localIterator = this.mAccessories.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      KXAccessoryView localKXAccessoryView = (KXAccessoryView)localIterator.next();
      if (localKXAccessoryView == null)
        continue;
      localKXAccessoryView.setFocus(false);
    }
  }

  public boolean addAccessory(KXAccessoryView paramKXAccessoryView)
  {
    if (this.mAccessories.size() < 10)
    {
      resetAllHighLight();
      this.mAccessories.add(paramKXAccessoryView);
      invalidate();
      return true;
    }
    return false;
  }

  public LinkedList<KXAccessoryView> getAccessories()
  {
    return this.mAccessories;
  }

  public RectF getDispImageBounds()
  {
    if (this.mDispImageBounds != null)
      return this.mDispImageBounds;
    RectF localRectF = new RectF(getDrawable().getBounds());
    getImageMatrix().mapRect(localRectF);
    localRectF.offset(getPaddingLeft(), getPaddingTop());
    this.mDispImageBounds = localRectF;
    return localRectF;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    paramCanvas.save();
    Iterator localIterator;
    if (this.mAccessories != null)
      localIterator = this.mAccessories.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        paramCanvas.restore();
        return;
      }
      ((KXAccessoryView)localIterator.next()).draw(paramCanvas);
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getAction();
    while (true)
    {
      float f;
      try
      {
        RectF localRectF = getDispImageBounds();
        int j = paramMotionEvent.getPointerCount();
        int k = 0;
        if (k < j)
          continue;
        switch (i & 0xFF)
        {
        default:
          if (this.mTouchingAcc == null)
            continue;
          this.mTouchingAcc.touchEvent(paramMotionEvent);
          f = 5.0F * getResources().getDisplayMetrics().density;
          switch (i & 0xFF)
          {
          case 2:
          default:
            return true;
            if (localRectF.contains(paramMotionEvent.getX(k), paramMotionEvent.getY(k)))
              continue;
            if ((this.mTouchingAcc == null) || ((i != 3) && (i != 1) && (i != 6) && (i != 262)))
              continue;
            this.mTouchingAcc.touchEvent(paramMotionEvent);
            return false;
            k++;
            continue;
          case 3:
          case 1:
          }
        case 0:
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return false;
      }
      resetAllHighLight();
      this.mPtMouseDown.x = (int)paramMotionEvent.getX();
      this.mPtMouseDown.y = (int)paramMotionEvent.getY();
      ListIterator localListIterator = this.mAccessories.listIterator();
      label229: if (!localListIterator.hasNext())
        label239: if (localListIterator.hasPrevious())
          break label297;
      while (true)
      {
        if (this.mTouchingAcc == null)
          break label364;
        this.mAccessories.remove(this.mTouchingAcc);
        this.mAccessories.addLast(this.mTouchingAcc);
        invalidate();
        break;
        localListIterator.next();
        break label229;
        label297: KXAccessoryView localKXAccessoryView1 = (KXAccessoryView)localListIterator.previous();
        if (!localKXAccessoryView1.computeHit(paramMotionEvent.getX(), paramMotionEvent.getY()))
          break label239;
        this.mTouchingAcc = localKXAccessoryView1;
        this.mTouchingAcc.touchEvent(paramMotionEvent);
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = this.mTouchingAcc;
        KXLog.w("KXDecorateImageView", "~ ~ ~ %s touched @ @ @ @ @", arrayOfObject1);
      }
      label364: continue;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = this.mTouchingAcc;
      KXLog.w("KXDecorateImageView", "~ ~ ~ %s will deTouched ", arrayOfObject3);
      this.mTouchingAcc = null;
      continue;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = this.mTouchingAcc;
      KXLog.w("KXDecorateImageView", "~ ~ ~ %s deTouched @ @ @ @ @", arrayOfObject2);
      if ((this.mAccessories != null) && (!this.mAccessories.isEmpty()))
      {
        KXAccessoryView localKXAccessoryView2 = (KXAccessoryView)this.mAccessories.getLast();
        if ((localKXAccessoryView2.clickedClose(this.mPtMouseDown.x, this.mPtMouseDown.y)) && (localKXAccessoryView2.clickedClose(paramMotionEvent.getX(), paramMotionEvent.getY())) && (Math.abs(this.mPtMouseDown.x - paramMotionEvent.getX()) < f) && (Math.abs(this.mPtMouseDown.y - paramMotionEvent.getY()) < f))
        {
          this.mAccessories.remove(localKXAccessoryView2);
          invalidate();
          continue;
        }
      }
      this.mTouchingAcc = null;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXDecorateImageView
 * JD-Core Version:    0.6.0
 */