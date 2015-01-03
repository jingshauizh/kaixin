package com.kaixin001.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.model.FaceModel;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class KXScrollText extends TextView
{
  public static final int TEXT_TIMER = 100;
  private Paint.FontMetrics fontMetrics;
  Handler handler;
  private boolean isStarting = false;
  private int left = 0;
  private boolean mInit = false;
  private Paint paint = null;
  private int right = 0;
  private ArrayList<KXLinkInfo> stateList;
  private float step = 0.0F;
  TimerTask task = new TimerTask()
  {
    public void run()
    {
      if ((KXScrollText.this.handler != null) && (KXScrollText.this.isStarting))
      {
        Message localMessage = Message.obtain();
        localMessage.what = 100;
        KXScrollText.this.handler.sendMessage(localMessage);
      }
    }
  };
  private float temp_view_plus_text_length = 0.0F;
  private float temp_view_plus_two_text_length = 0.0F;
  private float textLength = 0.0F;
  private Timer timer = new Timer();
  private Bitmap txtBmp;
  private Canvas txtCanvas;
  private float viewWidth = 0.0F;
  private float y = 0.0F;

  public KXScrollText(Context paramContext)
  {
    super(paramContext);
  }

  public KXScrollText(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public KXScrollText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private void init()
  {
    while (true)
    {
      int k;
      try
      {
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setTextSize(getTextSize());
        this.paint.setColor(getCurrentTextColor());
        this.textLength = 0.0F;
        int i = this.stateList.size();
        int j = getHeight() - getPaddingTop() - getPaddingBottom();
        k = 0;
        if (k < i)
          continue;
        this.left = getPaddingLeft();
        this.right = getPaddingRight();
        this.step = this.textLength;
        this.fontMetrics = this.paint.getFontMetrics();
        this.y = getPaddingTop();
        this.txtBmp = null;
        return;
        KXLinkInfo localKXLinkInfo = (KXLinkInfo)this.stateList.get(k);
        if (!localKXLinkInfo.isFace())
          continue;
        Bitmap localBitmap = FaceModel.getInstance().getFaceIcon(localKXLinkInfo.getContent());
        int m = j * localBitmap.getWidth() / localBitmap.getHeight();
        this.textLength += m + 4;
        break label239;
        String str = localKXLinkInfo.getContent();
        this.textLength = (this.paint.measureText(str) + this.textLength);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      label239: k++;
    }
  }

  private void setTxtBmp()
  {
    if ((this.txtBmp == null) && (this.fontMetrics != null))
    {
      this.viewWidth = (getWidth() - this.left - this.right);
      this.temp_view_plus_text_length = (this.viewWidth + this.textLength);
      this.temp_view_plus_two_text_length = (this.viewWidth + 2.0F * this.textLength);
      this.txtCanvas = new Canvas();
      int i = (int)this.viewWidth;
      int j = getHeight() - getPaddingTop() - getPaddingBottom();
      this.txtBmp = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
      this.y = (-this.paint.ascent() + (j - getTextSize()) / 2.0F);
      this.txtCanvas.setBitmap(this.txtBmp);
    }
  }

  public void onDraw(Canvas paramCanvas)
  {
    while (true)
    {
      Paint localPaint1;
      float f2;
      Paint localPaint2;
      int i;
      int k;
      try
      {
        if (this.mInit)
          continue;
        init();
        this.mInit = true;
        setTxtBmp();
        if (this.txtBmp == null)
          return;
        localPaint1 = new Paint();
        localPaint1.setColor(-1);
        localPaint1.setStyle(Paint.Style.FILL);
        this.txtCanvas.drawRect(0.0F, 0.0F, this.txtBmp.getWidth(), this.txtBmp.getHeight(), localPaint1);
        localPaint1.setAntiAlias(true);
        localPaint1.setStyle(Paint.Style.STROKE);
        localPaint1.setTextSize(getTextSize());
        localPaint1.setColor(getCurrentTextColor());
        boolean bool = this.viewWidth < this.textLength;
        float f1 = 0.0F;
        if (!bool)
          continue;
        f1 = this.temp_view_plus_text_length - this.step;
        f2 = f1;
        localPaint2 = new Paint(6);
        i = this.txtBmp.getHeight();
        int j = this.stateList.size();
        k = 0;
        if (k >= j)
        {
          paramCanvas.drawBitmap(this.txtBmp, this.left, getPaddingTop(), this.paint);
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      KXLinkInfo localKXLinkInfo = (KXLinkInfo)this.stateList.get(k);
      if (localKXLinkInfo.isFace())
      {
        Bitmap localBitmap = FaceModel.getInstance().getFaceIcon(localKXLinkInfo.getContent());
        Rect localRect = new Rect();
        localRect.left = (2 + (int)f2);
        localRect.top = 0;
        localRect.bottom = i;
        localRect.right = (localRect.left + i * localBitmap.getWidth() / localBitmap.getHeight());
        this.txtCanvas.drawBitmap(localBitmap, null, localRect, localPaint2);
        f2 = 2 + localRect.right;
      }
      else
      {
        String str = localKXLinkInfo.getContent().replaceAll("\n", " ");
        float f3 = localPaint1.measureText(str);
        this.txtCanvas.drawText(str, f2, this.y, localPaint1);
        f2 += f3;
      }
      k++;
    }
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.txtBmp = null;
    setTxtBmp();
  }

  public void scrollText()
  {
    if (!this.isStarting);
    do
    {
      do
      {
        return;
        invalidate();
      }
      while (this.viewWidth >= this.textLength);
      this.step = (float)(0.5D + this.step);
    }
    while (this.step <= this.temp_view_plus_two_text_length);
    this.step = this.textLength;
  }

  public void setHandler(Handler paramHandler)
  {
    this.handler = paramHandler;
  }

  public void setStateList(ArrayList<KXLinkInfo> paramArrayList)
  {
    this.stateList = paramArrayList;
    this.mInit = false;
  }

  public void start()
  {
    this.timer.schedule(this.task, 10L, 20L);
  }

  public void startScroll()
  {
    this.isStarting = true;
  }

  public void stop()
  {
    this.timer.cancel();
  }

  public void stopScroll()
  {
    this.isStarting = false;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXScrollText
 * JD-Core Version:    0.6.0
 */