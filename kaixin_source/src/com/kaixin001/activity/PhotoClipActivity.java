package com.kaixin001.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import com.kaixin001.view.PhotoClipView;
import java.io.ByteArrayOutputStream;

public class PhotoClipActivity extends Activity
  implements View.OnTouchListener, View.OnClickListener
{
  private static final int DRAG = 1;
  private static final int NONE = 0;
  public static final String PARAM_PATH = "filePath";
  private static final String TAG = "PhotoClipActivity";
  private static final int ZOOM = 2;
  private Bitmap bmp;
  private Button cancelBtn;
  private PhotoClipView clipview;
  private String filePath;
  private Matrix matrix = new Matrix();
  private int matrixScale;
  PointF mid = new PointF();
  private int mode = 0;
  float oldDist = 1.0F;
  private Matrix savedMatrix = new Matrix();
  private ImageView srcPic;
  PointF start = new PointF();
  int statusBarHeight = 0;
  private Button sureBtn;
  int titleBarHeight = 0;

  private void getBarHeight()
  {
    Rect localRect = new Rect();
    getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
    this.statusBarHeight = localRect.top;
    this.titleBarHeight = (getWindow().findViewById(16908290).getTop() - this.statusBarHeight);
    Log.v("PhotoClipActivity", "statusBarHeight = " + this.statusBarHeight + ", titleBarHeight = " + this.titleBarHeight);
  }

  private Bitmap getBitmap()
  {
    getBarHeight();
    Bitmap localBitmap = takeScreenShot();
    int i = this.clipview.getSidePx();
    return Bitmap.createBitmap(localBitmap, this.clipview.getRectLeft(), this.clipview.getRectTop() + this.statusBarHeight, i, i);
  }

  private void getScale()
  {
    int[] arrayOfInt1 = new int[2];
    this.clipview.getLocationOnScreen(arrayOfInt1);
    arrayOfInt1[0];
    arrayOfInt1[1];
    int[] arrayOfInt2 = new int[2];
    this.srcPic.getLocationOnScreen(arrayOfInt2);
    arrayOfInt2[0];
    arrayOfInt2[1];
  }

  private void getScale2()
  {
    Matrix localMatrix = this.srcPic.getImageMatrix();
    Rect localRect = this.srcPic.getDrawable().getBounds();
    float[] arrayOfFloat = new float[9];
    localMatrix.getValues(arrayOfFloat);
    ImageState localImageState = new ImageState();
    localImageState.setLeft(arrayOfFloat[2]);
    localImageState.setTop(arrayOfFloat[5]);
    localImageState.setRight(localImageState.getLeft() + localRect.width() * arrayOfFloat[0]);
    localImageState.setBottom(localImageState.getTop() + localRect.height() * arrayOfFloat[0]);
  }

  private void initContentView()
  {
    this.clipview = ((PhotoClipView)findViewById(2131363363));
    this.srcPic = ((ImageView)findViewById(2131363362));
    this.srcPic.setOnTouchListener(this);
    this.sureBtn = ((Button)findViewById(2131363361));
    this.sureBtn.setOnClickListener(this);
    this.cancelBtn = ((Button)findViewById(2131363360));
    this.cancelBtn.setOnClickListener(this);
  }

  private void initData()
  {
    Bundle localBundle = getIntent().getExtras();
    if (localBundle != null)
    {
      if (localBundle.containsKey("filePath"))
        this.filePath = localBundle.getString("filePath");
      if (this.filePath != null)
        this.bmp = BitmapFactory.decodeFile(this.filePath);
      if (this.bmp != null)
        this.srcPic.setImageBitmap(this.bmp);
    }
  }

  private void midPoint(PointF paramPointF, MotionEvent paramMotionEvent)
  {
    float f1 = paramMotionEvent.getX(0) + paramMotionEvent.getX(1);
    float f2 = paramMotionEvent.getY(0) + paramMotionEvent.getY(1);
    paramPointF.set(f1 / 2.0F, f2 / 2.0F);
  }

  private float spacing(MotionEvent paramMotionEvent)
  {
    float f1 = paramMotionEvent.getX(0) - paramMotionEvent.getX(1);
    float f2 = paramMotionEvent.getY(0) - paramMotionEvent.getY(1);
    return FloatMath.sqrt(f1 * f1 + f2 * f2);
  }

  private Bitmap takeScreenShot()
  {
    View localView = getWindow().getDecorView();
    localView.setDrawingCacheEnabled(true);
    localView.buildDrawingCache();
    return localView.getDrawingCache();
  }

  public void onClick(View paramView)
  {
    if (paramView == this.cancelBtn)
    {
      setResult(0);
      getScale();
    }
    do
      return;
    while (paramView != this.sureBtn);
    Bitmap localBitmap = getBitmap();
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    localBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream);
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    Intent localIntent = new Intent();
    localIntent.putExtra("bitmap", arrayOfByte);
    setResult(-1, localIntent);
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(2130903275);
    initContentView();
    initData();
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    ImageView localImageView = (ImageView)paramView;
    switch (0xFF & paramMotionEvent.getAction())
    {
    case 3:
    case 4:
    default:
    case 0:
    case 5:
    case 1:
    case 6:
    case 2:
    }
    while (true)
    {
      localImageView.setImageMatrix(this.matrix);
      return true;
      this.savedMatrix.set(this.matrix);
      this.start.set(paramMotionEvent.getX(), paramMotionEvent.getY());
      Log.d("PhotoClipActivity", "mode=DRAG");
      this.mode = 1;
      continue;
      this.oldDist = spacing(paramMotionEvent);
      Log.d("PhotoClipActivity", "oldDist=" + this.oldDist);
      if (this.oldDist <= 10.0F)
        continue;
      this.savedMatrix.set(this.matrix);
      midPoint(this.mid, paramMotionEvent);
      this.mode = 2;
      Log.d("PhotoClipActivity", "mode=ZOOM");
      continue;
      this.mode = 0;
      Log.d("PhotoClipActivity", "mode=NONE");
      continue;
      if (this.mode == 1)
      {
        this.matrix.set(this.savedMatrix);
        this.matrix.postTranslate(paramMotionEvent.getX() - this.start.x, paramMotionEvent.getY() - this.start.y);
        continue;
      }
      if (this.mode != 2)
        continue;
      float f1 = spacing(paramMotionEvent);
      Log.d("PhotoClipActivity", "newDist=" + f1);
      if (f1 <= 10.0F)
        continue;
      this.matrix.set(this.savedMatrix);
      float f2 = f1 / this.oldDist;
      this.matrix.postScale(f2, f2, this.mid.x, this.mid.y);
    }
  }

  public class ImageState
  {
    private float bottom;
    private float left;
    private float right;
    private float top;

    public ImageState()
    {
    }

    public float getBottom()
    {
      return this.bottom;
    }

    public float getLeft()
    {
      return this.left;
    }

    public float getRight()
    {
      return this.right;
    }

    public float getTop()
    {
      return this.top;
    }

    public void setBottom(float paramFloat)
    {
      this.bottom = paramFloat;
    }

    public void setLeft(float paramFloat)
    {
      this.left = paramFloat;
    }

    public void setRight(float paramFloat)
    {
      this.right = paramFloat;
    }

    public void setTop(float paramFloat)
    {
      this.top = paramFloat;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.PhotoClipActivity
 * JD-Core Version:    0.6.0
 */