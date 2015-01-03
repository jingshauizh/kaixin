package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.kaixin001.activity.R.styleable;
import java.io.InputStream;
import java.lang.reflect.Field;

public class KXGifImageView extends ImageView
  implements View.OnClickListener
{
  private boolean isAutoPlay;
  private boolean isPlaying;
  private int mImageHeight;
  private int mImageWidth;
  private Movie mMovie;
  private long mMovieStart;
  private Bitmap mStartButton;

  public KXGifImageView(Context paramContext)
  {
    super(paramContext);
  }

  public KXGifImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public KXGifImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.KXGifImageView);
    int i = getResourceId(localTypedArray, paramContext, paramAttributeSet);
    if (i != 0)
    {
      InputStream localInputStream = getResources().openRawResource(i);
      this.mMovie = Movie.decodeStream(localInputStream);
      if (this.mMovie != null)
      {
        this.isAutoPlay = localTypedArray.getBoolean(0, false);
        Bitmap localBitmap = BitmapFactory.decodeStream(localInputStream);
        this.mImageWidth = localBitmap.getWidth();
        this.mImageHeight = localBitmap.getHeight();
        localBitmap.recycle();
        if (!this.isAutoPlay)
        {
          this.mStartButton = BitmapFactory.decodeResource(getResources(), 2130838154);
          setOnClickListener(this);
        }
      }
    }
  }

  private int getResourceId(TypedArray paramTypedArray, Context paramContext, AttributeSet paramAttributeSet)
  {
    try
    {
      Field localField = TypedArray.class.getDeclaredField("mValue");
      localField.setAccessible(true);
      int i = ((TypedValue)localField.get(paramTypedArray)).resourceId;
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return 0;
    }
    finally
    {
      if (paramTypedArray != null)
        paramTypedArray.recycle();
    }
    throw localObject;
  }

  private boolean playMovie(Canvas paramCanvas)
  {
    long l = SystemClock.uptimeMillis();
    if (this.mMovieStart == 0L)
      this.mMovieStart = l;
    int i = this.mMovie.duration();
    if (i == 0)
      i = 1000;
    int j = (int)((l - this.mMovieStart) % i);
    this.mMovie.setTime(j);
    this.mMovie.draw(paramCanvas, 0.0F, 0.0F);
    if (l - this.mMovieStart >= i)
    {
      this.mMovieStart = 0L;
      return true;
    }
    return false;
  }

  public void onClick(View paramView)
  {
    if (paramView.getId() == getId())
    {
      this.isPlaying = true;
      invalidate();
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.mMovie == null)
    {
      super.onDraw(paramCanvas);
      return;
    }
    if (this.isAutoPlay)
    {
      playMovie(paramCanvas);
      invalidate();
      return;
    }
    if (this.isPlaying)
    {
      if (playMovie(paramCanvas))
        this.isPlaying = false;
      invalidate();
      return;
    }
    this.mMovie.setTime(0);
    this.mMovie.draw(paramCanvas, 0.0F, 0.0F);
    int i = (this.mImageWidth - this.mStartButton.getWidth()) / 2;
    int j = (this.mImageHeight - this.mStartButton.getHeight()) / 2;
    paramCanvas.drawBitmap(this.mStartButton, i, j, null);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if (this.mMovie != null)
      setMeasuredDimension(this.mImageWidth, this.mImageHeight);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXGifImageView
 * JD-Core Version:    0.6.0
 */