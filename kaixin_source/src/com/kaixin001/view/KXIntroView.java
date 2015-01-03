package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.KXLinkInfoRect;
import com.kaixin001.model.FaceModel;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.ImageUtil;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.Iterator;

public class KXIntroView extends TextView
{
  private static final String ELLIPSIS = "...";
  private static final float FACE_BMP_MARGIN_LEFT = 2.0F;
  private static final float FACE_BMP_MARGIN_RIGHT = 2.0F;
  private static final String FLAG_NEW_LINE = "\n";
  private static final String FLAG_NEW_LINE_R = "\r";
  private static final String FLAG_NEW_LINE_R_N = "\r\n";
  private static final float LOCATION_BMP_MARGIN_LEFT = 4.0F;
  private static final float LOCATION_BMP_MARGIN_RIGHT = 1.0F;
  private static final String TAG = "KXIntroView";
  private static Bitmap mBmpLocation1;
  private static Bitmap mBmpLocation2;
  private static Bitmap mBmpStar = null;
  private static Bitmap mBmpVideo;
  private OnClickLinkListener Listener;
  private float mDensity = 1.0F;
  private float mDisplayHeight = 0.0F;
  private int mDisplayWidth = 0;
  private boolean mEllipsed = false;
  private float mEllipsisWidth = 0.0F;
  private float mEllipsisWidth2 = 0.0F;
  private float mFaceBmpMarginLeft = 2.0F;
  private float mFaceBmpMarginRight = 2.0F;
  private boolean mIsLbs = false;
  private boolean mLbsEllipse = false;
  private ArrayList<KXLinkInfo> mListInfo = null;
  private float mLocationBmpMarginLeft = 4.0F;
  private float mLocationBmpMarginRight = 1.0F;
  private int mMaxLines = -1;
  private int mMaxWidPix;
  private boolean mNewEllipse = false;
  private KXLinkInfo mSelectedInfo = null;
  private boolean mShowShadow = true;
  private boolean mViewMore = false;

  static
  {
    mBmpLocation1 = null;
    mBmpLocation2 = null;
    mBmpVideo = null;
  }

  public KXIntroView(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public KXIntroView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
    readAttrs(paramAttributeSet);
  }

  public KXIntroView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
    readAttrs(paramAttributeSet);
  }

  private void cancelCurInfo()
  {
    if (this.mSelectedInfo != null)
    {
      this.mSelectedInfo.setSelected(false);
      invalidate();
      this.mSelectedInfo = null;
    }
  }

  private boolean cancelCurInfo(float paramFloat1, float paramFloat2)
  {
    if (this.mSelectedInfo == null)
      return true;
    if (!this.mSelectedInfo.contains(paramFloat1, paramFloat2))
    {
      this.mSelectedInfo.setSelected(false);
      invalidate();
      this.mSelectedInfo = null;
      return true;
    }
    return false;
  }

  private boolean clickLink(ArrayList<KXLinkInfo> paramArrayList, float paramFloat1, float paramFloat2, int paramInt)
  {
    if (paramArrayList == null)
      return false;
    int i = paramArrayList.size();
    int j = 0;
    label15: KXLinkInfo localKXLinkInfo;
    if (j < i)
    {
      localKXLinkInfo = (KXLinkInfo)paramArrayList.get(j);
      if ((localKXLinkInfo.isStar()) || (localKXLinkInfo.isUserName()) || (localKXLinkInfo.isPraiseOriTitle()) || (localKXLinkInfo.isRepostAlbumOrVote()) || (localKXLinkInfo.isVideo()) || (localKXLinkInfo.isUrlLink()) || (localKXLinkInfo.isLbsPoi()) || (localKXLinkInfo.isTopic()) || ((localKXLinkInfo.isLocation()) && (this.mViewMore)) || (localKXLinkInfo.isClickEnable()))
        break label126;
    }
    label126: 
    do
    {
      j++;
      break label15;
      break;
    }
    while (!localKXLinkInfo.contains(paramFloat1, paramFloat2));
    if (paramInt == 0)
    {
      localKXLinkInfo.setSelected(true);
      invalidate();
      this.mSelectedInfo = localKXLinkInfo;
    }
    while (true)
    {
      return true;
      if (paramInt != 1)
        continue;
      this.mSelectedInfo = null;
      localKXLinkInfo.setSelected(false);
      invalidate();
      this.Listener.onClick(localKXLinkInfo);
    }
  }

  private float drawList(Canvas paramCanvas, Paint paramPaint, ArrayList<KXLinkInfo> paramArrayList, float paramFloat)
  {
    if ((paramArrayList == null) || (paramArrayList.size() == 0))
      return paramFloat;
    float f1 = paramPaint.ascent();
    float f2 = paramPaint.descent() - f1 + 2.0F * getResources().getDisplayMetrics().density;
    int i = getCurrentTextColor();
    KXLinkInfoRect localKXLinkInfoRect = null;
    Paint localPaint = new Paint(6);
    int j = 0;
    Iterator localIterator = paramArrayList.iterator();
    KXLinkInfo localKXLinkInfo;
    ArrayList localArrayList;
    while (true)
    {
      if (!localIterator.hasNext())
      {
        if ((!this.mNewEllipse) && (this.mEllipsed) && (localKXLinkInfoRect != null))
        {
          paramPaint.setColor(i);
          paramCanvas.drawText("...", localKXLinkInfoRect.right, localKXLinkInfoRect.top - f1, paramPaint);
        }
        return paramFloat + f2;
      }
      localKXLinkInfo = (KXLinkInfo)localIterator.next();
      j++;
      if ((this.mNewEllipse) && (this.mEllipsed))
      {
        int i3 = paramArrayList.size();
        if ((j == i3) && (localKXLinkInfoRect != null))
          paramCanvas.drawText("...", 0, "...".length(), localKXLinkInfoRect.right, localKXLinkInfoRect.top - f1, paramPaint);
      }
      localArrayList = localKXLinkInfo.getRectFList();
      if ((localArrayList == null) || (localArrayList.size() == 0))
        continue;
      if (!localKXLinkInfo.isFace())
        break;
      Bitmap localBitmap = FaceModel.getInstance().getFaceIcon(localKXLinkInfo.getContent());
      localKXLinkInfoRect = (KXLinkInfoRect)localArrayList.get(0);
      if ((localBitmap == null) || (localKXLinkInfoRect == null))
        continue;
      Rect localRect4 = new Rect();
      localRect4.set((int)localKXLinkInfoRect.left, (int)localKXLinkInfoRect.top, (int)localKXLinkInfoRect.right, (int)localKXLinkInfoRect.bottom);
      paramCanvas.drawBitmap(localBitmap, null, localRect4, localPaint);
    }
    boolean bool = localKXLinkInfo.isLocation();
    int k = 0;
    Rect localRect3;
    label441: String str;
    if (bool)
    {
      localKXLinkInfoRect = (KXLinkInfoRect)localArrayList.get(0);
      localRect3 = new Rect();
      localRect3.set((int)localKXLinkInfoRect.left, (int)localKXLinkInfoRect.top, (int)localKXLinkInfoRect.right, (int)localKXLinkInfoRect.bottom);
      if (mBmpLocation1 == null)
        mBmpLocation1 = getBitmap(2130838203);
      if (mBmpLocation2 == null)
        mBmpLocation2 = getBitmap(2130838204);
      if (!this.mViewMore)
      {
        if (this.mIsLbs)
          paramCanvas.drawBitmap(mBmpLocation2, null, localRect3, localPaint);
      }
      else
        k = 0 + 1;
    }
    else
    {
      if (localKXLinkInfo.isVideo())
      {
        localKXLinkInfoRect = (KXLinkInfoRect)localArrayList.get(k);
        Rect localRect2 = new Rect();
        localRect2.set((int)localKXLinkInfoRect.left, (int)localKXLinkInfoRect.top, (int)localKXLinkInfoRect.right, (int)localKXLinkInfoRect.bottom);
        if (mBmpVideo == null)
          mBmpVideo = BitmapFactory.decodeResource(getResources(), 2130838700);
        paramCanvas.drawBitmap(mBmpVideo, null, localRect2, localPaint);
        k++;
      }
      str = localKXLinkInfo.getContent();
      if (str == null)
        str = "";
      if (((localKXLinkInfo.isUserName()) || (localKXLinkInfo.isStar()) || (localKXLinkInfo.isPraiseOriTitle()) || (localKXLinkInfo.isVideo()) || (localKXLinkInfo.isUrlLink()) || (localKXLinkInfo.isLbsPoi()) || (localKXLinkInfo.isTopic()) || ((localKXLinkInfo.isLocation()) && (this.mViewMore))) && (localKXLinkInfo.isSelected()))
        paintSelectRectF(paramCanvas, paramPaint, localKXLinkInfo);
      if ((!localKXLinkInfo.isStar()) && (!localKXLinkInfo.isVideo()) && (!localKXLinkInfo.isUserName()) && (!localKXLinkInfo.isTitle()) && (!localKXLinkInfo.isPraiseOriTitle()) && (!localKXLinkInfo.isUrlLink()) && (!localKXLinkInfo.isLbsPoi()) && (!localKXLinkInfo.isTopic()))
        break label884;
      paramPaint.setColor(getResources().getColor(2130839429));
      if (this.mShowShadow)
        paramPaint.setShadowLayer(1.0F, 1.0F, 0.0F, -1);
    }
    while (true)
    {
      int m = localArrayList.size();
      if ((localKXLinkInfo.isStar()) && (m > 1))
        m--;
      if (k < m)
        break label999;
      if (!localKXLinkInfo.isStar())
        break;
      int n = localArrayList.size();
      if (k >= n)
        break;
      localKXLinkInfoRect = (KXLinkInfoRect)localArrayList.get(k);
      Rect localRect1 = new Rect();
      localRect1.set((int)localKXLinkInfoRect.left, (int)localKXLinkInfoRect.top, (int)localKXLinkInfoRect.right, (int)localKXLinkInfoRect.bottom);
      if (mBmpStar == null)
        mBmpStar = getBitmap(2130838823);
      paramCanvas.drawBitmap(mBmpStar, null, localRect1, localPaint);
      break;
      paramCanvas.drawBitmap(mBmpLocation1, null, localRect3, localPaint);
      break label441;
      label884: if ((localKXLinkInfo.isColorText()) || ((localKXLinkInfo.isLocation()) && (this.mViewMore)))
      {
        int i1 = getResources().getColor(2130839397);
        try
        {
          if (!TextUtils.isEmpty(localKXLinkInfo.getId()))
          {
            int i2 = Integer.parseInt(localKXLinkInfo.getId().trim(), 10);
            i1 = i2 | 0xFF000000;
          }
          if (localKXLinkInfo.getColor() != null)
            i1 = localKXLinkInfo.getColor().intValue();
          paramPaint.setColor(i1);
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
      }
      paramPaint.setColor(i);
    }
    label999: localKXLinkInfoRect = (KXLinkInfoRect)localArrayList.get(k);
    if ((localKXLinkInfoRect.start < 0) || (localKXLinkInfoRect.end < 0));
    while (true)
    {
      k++;
      break;
      paramCanvas.drawText(str, localKXLinkInfoRect.start, localKXLinkInfoRect.end, localKXLinkInfoRect.left, localKXLinkInfoRect.top - f1, paramPaint);
    }
  }

  private Bitmap getBitmap(int paramInt)
  {
    int i = (int)getTextSize();
    Bitmap localBitmap = ImageCache.getInstance().getLoadingBitmap(paramInt, i, i);
    if ((localBitmap != null) && (localBitmap.getHeight() < i))
      localBitmap = ImageUtil.resizeBitmap(localBitmap, i * localBitmap.getWidth() / localBitmap.getHeight(), i);
    return localBitmap;
  }

  private void init()
  {
    this.mDensity = getContext().getResources().getDisplayMetrics().density;
    this.mFaceBmpMarginLeft = (2.0F * this.mDensity);
    this.mFaceBmpMarginRight = (2.0F * this.mDensity);
    this.mLocationBmpMarginLeft = (4.0F * this.mDensity);
    this.mLocationBmpMarginRight = (1.0F * this.mDensity);
  }

  private void measureView(int paramInt1, int paramInt2)
  {
    if (this.mListInfo == null)
    {
      this.mDisplayHeight = (getPaddingTop() + getPaddingBottom());
      this.mDisplayWidth = (getPaddingLeft() + getPaddingLeft());
    }
    int j;
    int i18;
    do
    {
      int i19;
      do
      {
        return;
        int i = View.MeasureSpec.getMode(paramInt1);
        j = View.MeasureSpec.getSize(paramInt1);
        if (this.mMaxWidPix > 0)
        {
          int i20 = this.mMaxWidPix;
          j = Math.min(j, i20);
        }
        if (i != 1073741824)
          break;
        i19 = this.mDisplayWidth;
      }
      while ((j == i19) && (0.0F != this.mDisplayHeight));
      if (!this.mViewMore)
        break;
      i18 = this.mDisplayWidth;
    }
    while ((j == i18) && (0.0F != this.mDisplayHeight));
    this.mDisplayWidth = j;
    float f1 = 0.0F;
    float f2 = getPaddingTop();
    TextPaint localTextPaint = getPaint();
    float f3 = -localTextPaint.ascent() + localTextPaint.descent() + 2.0F * getResources().getDisplayMetrics().density;
    float f4 = f3;
    this.mEllipsisWidth = localTextPaint.measureText("...");
    this.mEllipsisWidth2 = this.mEllipsisWidth;
    this.mEllipsed = false;
    this.mNewEllipse = false;
    int k = 0;
    int m = j - getPaddingRight();
    int n = getPaddingLeft();
    FaceModel localFaceModel = FaceModel.getInstance();
    int i1 = this.mMaxLines;
    boolean bool1 = this.mLbsEllipse;
    float f5 = 0.0F;
    String str4;
    float[] arrayOfFloat;
    int i16;
    label359: int i17;
    if (bool1)
    {
      int i14 = this.mListInfo.size();
      f5 = 0.0F;
      if (i14 > 0)
      {
        KXLinkInfo localKXLinkInfo2 = (KXLinkInfo)this.mListInfo.get(-1 + this.mListInfo.size());
        boolean bool2 = localKXLinkInfo2.isLocation();
        f5 = 0.0F;
        if (bool2)
        {
          this.mNewEllipse = true;
          float f30 = localTextPaint.measureText("一");
          str4 = localKXLinkInfo2.getContent();
          int i15 = str4.length();
          f5 = getPaddingLeft();
          arrayOfFloat = new float[i15 + 3];
          i16 = i15 - 1;
          if (i16 >= 0)
            break label530;
          if (mBmpLocation1 == null)
            mBmpLocation1 = getBitmap(2130838203);
          arrayOfFloat[i15] = mBmpLocation1.getWidth();
          arrayOfFloat[(i15 + 1)] = this.mLocationBmpMarginLeft;
          arrayOfFloat[(i15 + 2)] = (f30 + this.mEllipsisWidth);
          i17 = 0;
          label420: if (i17 < arrayOfFloat.length)
            break label554;
        }
      }
    }
    if (this.mNewEllipse)
      this.mEllipsisWidth2 = f5;
    int i2 = 0;
    Iterator localIterator = this.mListInfo.iterator();
    label463: KXLinkInfo localKXLinkInfo1;
    label530: label554: 
    do
    {
      if (!localIterator.hasNext())
      {
        float f20 = f2 + getPaddingBottom();
        if (f1 > n)
          f20 += f4;
        this.mDisplayHeight = (2.0F + f20);
        if ((this.mMaxWidPix <= 0) || (k != 0))
          break;
        this.mDisplayWidth = Math.min(this.mDisplayWidth, (int)(f1 + getPaddingRight()));
        return;
        arrayOfFloat[i16] = localTextPaint.measureText(str4, i16, i16 + 1);
        i16--;
        break label359;
        f5 += arrayOfFloat[i17];
        if (f5 > m)
        {
          i1--;
          f5 = getPaddingLeft() + arrayOfFloat[i17];
        }
        i17++;
        break label420;
      }
      localKXLinkInfo1 = (KXLinkInfo)localIterator.next();
      i2++;
    }
    while ((this.mEllipsed) && (this.mNewEllipse) && (i2 < this.mListInfo.size()));
    if ((this.mEllipsed) && (this.mNewEllipse) && (i2 == this.mListInfo.size()))
    {
      k--;
      f1 += this.mEllipsisWidth;
    }
    int i3;
    label697: int i4;
    if ((this.mNewEllipse) && (i2 < this.mListInfo.size()))
    {
      i3 = i1;
      if ((i3 <= 0) || (k + 1 != i3))
        break label892;
      if ((!this.mNewEllipse) || (i2 >= this.mListInfo.size()))
        break label879;
      i4 = m - (int)this.mEllipsisWidth2;
    }
    float f25;
    float f26;
    while (true)
    {
      localKXLinkInfo1.getRectFList().clear();
      if (!localKXLinkInfo1.isFace())
        break label1056;
      Bitmap localBitmap2 = localFaceModel.getFaceIcon(localKXLinkInfo1.getContent());
      if (localBitmap2 == null)
        break;
      f25 = localBitmap2.getWidth();
      f26 = localBitmap2.getHeight();
      if (this.mDensity > 1.0F)
      {
        f25 /= this.mDensity;
        f26 /= this.mDensity;
      }
      if (f1 + f25 + this.mFaceBmpMarginLeft < i4)
        break label1035;
      k++;
      if (k != i3)
        break label899;
      this.mEllipsed = true;
      if ((!this.mNewEllipse) || (i2 >= this.mListInfo.size()))
        break label463;
      break;
      i3 = this.mMaxLines;
      break label697;
      label879: i4 = m - (int)this.mEllipsisWidth;
      continue;
      label892: i4 = m;
    }
    label899: if (k + 1 == i3)
      if ((this.mNewEllipse) && (i2 < this.mListInfo.size()))
      {
        (m - (int)this.mEllipsisWidth2);
        label936: f2 += f4;
        f4 = f3;
        f1 = n;
      }
    while (true)
    {
      KXLinkInfoRect localKXLinkInfoRect6 = new KXLinkInfoRect();
      float f27 = f25 * (f4 / f26);
      float f28 = f1 + f27;
      float f29 = f2 + f4;
      localKXLinkInfoRect6.set(f1, f2, f28, f29);
      localKXLinkInfo1.addRectF(localKXLinkInfoRect6);
      f1 += f27 + this.mFaceBmpMarginRight;
      break;
      (m - (int)this.mEllipsisWidth);
      break label936;
      break label936;
      label1035: if (f1 <= n)
        continue;
      f1 += this.mFaceBmpMarginLeft;
    }
    label1056: float f6;
    float f7;
    if ((localKXLinkInfo1.isLocation()) || (localKXLinkInfo1.isVideo()))
    {
      if (mBmpLocation1 == null)
        mBmpLocation1 = getBitmap(2130838203);
      if (mBmpVideo == null)
        mBmpVideo = getBitmap(2130838700);
      if (localKXLinkInfo1.isLocation());
      for (Bitmap localBitmap1 = mBmpLocation1; ; localBitmap1 = mBmpVideo)
      {
        f6 = localBitmap1.getWidth();
        f7 = localBitmap1.getHeight();
        if (f4 < f7)
        {
          f4 = f7;
          f6 *= f4 / f7;
        }
        if (f1 + f6 + this.mLocationBmpMarginLeft < i4)
          break label1429;
        k++;
        if (k != i3)
          break label1217;
        this.mEllipsed = true;
        if ((!this.mNewEllipse) || (i2 >= this.mListInfo.size()))
          break label463;
        break;
      }
      label1217: if (k + 1 != i3)
        break label1422;
      if ((!this.mNewEllipse) || (i2 >= this.mListInfo.size()))
        break label1409;
      i4 = m - (int)this.mEllipsisWidth2;
      label1255: f2 += f4;
      f4 = f3;
      f1 = n;
    }
    String str1;
    while (true)
    {
      KXLinkInfoRect localKXLinkInfoRect1 = new KXLinkInfoRect();
      float f8 = (f4 - f7) / 2.0F;
      float f9 = f2 + f8;
      float f10 = f1 + f6;
      float f11 = f2 + f4 - f8;
      localKXLinkInfoRect1.set(f1, f9, f10, f11);
      localKXLinkInfo1.addRectF(localKXLinkInfoRect1);
      f1 += f6 + this.mLocationBmpMarginRight;
      str1 = localKXLinkInfo1.getContent();
      if (str1 == null)
        str1 = "";
      if (!str1.startsWith("\n"))
        break label1504;
      k++;
      if (k != i3)
        break label1450;
      this.mEllipsed = true;
      if ((!this.mNewEllipse) || (i2 >= this.mListInfo.size()))
        break label463;
      break;
      label1409: i4 = m - (int)this.mEllipsisWidth;
      break label1255;
      label1422: i4 = m;
      break label1255;
      label1429: if (f1 <= n)
        continue;
      f1 += this.mLocationBmpMarginLeft;
    }
    label1450: label1488: label1504: String[] arrayOfString;
    int i5;
    int i6;
    if (k + 1 == i3)
      if ((this.mNewEllipse) && (i2 < this.mListInfo.size()))
      {
        i4 = m - (int)this.mEllipsisWidth2;
        f2 += f4;
        f4 = f3;
        f1 = n;
        String str2 = str1.replaceAll("\r\n", "\n").replaceAll("\r", "\n");
        localKXLinkInfo1.setContent(str2);
        arrayOfString = str2.split("\n");
        i5 = 0;
        i6 = 0;
        int i7 = arrayOfString.length;
        if (i6 < i7)
          break label1668;
        label1555: break label1767;
      }
    label1556: int i9;
    int i10;
    label1668: 
    do
    {
      if (!localKXLinkInfo1.isStar())
        break label1895;
      if (mBmpStar == null)
        mBmpStar = getBitmap(2130838823);
      i9 = mBmpStar.getWidth();
      i10 = mBmpStar.getHeight();
      if (f1 + i9 < this.mDisplayWidth)
        break label2468;
      k++;
      if (k != i3)
        break label2300;
      this.mEllipsed = true;
      if ((!this.mNewEllipse) || (i2 >= this.mListInfo.size()))
        break label463;
      break;
      i4 = m - (int)this.mEllipsisWidth;
      break label1488;
      i4 = m;
      break label1488;
    }
    while (((i3 > 0) && (k > i3)) || ((this.mEllipsed) && (this.mNewEllipse) && (i2 < this.mListInfo.size())));
    String str3 = arrayOfString[i6];
    if (!TextUtils.isEmpty(str3))
    {
      float f12 = localTextPaint.measureText(str3);
      float f21;
      int i12;
      int i13;
      if (f1 + f12 >= i4)
      {
        f21 = f1;
        int i11 = str3.length();
        i12 = i5;
        i13 = 0;
        if (i13 >= i11)
        {
          label1767: if ((this.mEllipsed) && (this.mNewEllipse) && (i2 < this.mListInfo.size()))
            break label2012;
          KXLinkInfoRect localKXLinkInfoRect5 = new KXLinkInfoRect();
          float f24 = f2 + f4;
          localKXLinkInfoRect5.set(f21, f2, f1, f24);
          localKXLinkInfoRect5.start = i12;
          localKXLinkInfoRect5.end = (i5 + i11);
          localKXLinkInfo1.addRectF(localKXLinkInfoRect5);
        }
      }
      while (true)
      {
        int i8 = -1 + arrayOfString.length;
        if (i6 >= i8)
          break label2258;
        k++;
        if (k != i3)
          break label2204;
        this.mEllipsed = true;
        if ((!this.mNewEllipse) || (i2 >= this.mListInfo.size()))
          break label463;
        break label1556;
        label1895: break;
        float f22 = localTextPaint.measureText(str3, i13, i13 + 1);
        if (f1 + f22 >= i4)
        {
          KXLinkInfoRect localKXLinkInfoRect4 = new KXLinkInfoRect();
          float f23 = f2 + f4;
          localKXLinkInfoRect4.set(f21, f2, f1, f23);
          localKXLinkInfoRect4.start = i12;
          localKXLinkInfoRect4.end = (i5 + i13);
          localKXLinkInfo1.addRectF(localKXLinkInfoRect4);
          k++;
          if (k == i3)
          {
            this.mEllipsed = true;
            if ((!this.mNewEllipse) || (i2 >= this.mListInfo.size()))
              break label463;
            break label1767;
            label2012: break label1556;
          }
          if ((i3 > 0) && (k > i3))
            break label1555;
          if (k + 1 == i3)
            if ((this.mNewEllipse) && (i2 < this.mListInfo.size()))
            {
              i4 = m - (int)this.mEllipsisWidth2;
              label2064: i12 = localKXLinkInfoRect4.end;
              f2 += f4;
              f4 = f3;
              f1 = f22 + n;
              f21 = n;
            }
        }
        while (true)
        {
          i13++;
          break;
          i4 = m - (int)this.mEllipsisWidth;
          break label2064;
          i4 = m;
          break label2064;
          f1 += f22;
        }
        KXLinkInfoRect localKXLinkInfoRect2 = new KXLinkInfoRect();
        float f13 = f1 + f12;
        float f14 = f2 + f4;
        localKXLinkInfoRect2.set(f1, f2, f13, f14);
        localKXLinkInfoRect2.start = i5;
        localKXLinkInfoRect2.end = (i5 + str3.length());
        localKXLinkInfo1.addRectF(localKXLinkInfoRect2);
        f1 += f12;
      }
      label2204: if (k + 1 != i3)
        break label2293;
      if ((!this.mNewEllipse) || (i2 >= this.mListInfo.size()))
        break label2280;
      i4 = m - (int)this.mEllipsisWidth2;
    }
    while (true)
    {
      f1 = n;
      f2 += f4;
      f4 = f3;
      label2258: i5 += str3.length() + "\n".length();
      i6++;
      break;
      label2280: i4 = m - (int)this.mEllipsisWidth;
      continue;
      label2293: i4 = m;
    }
    label2300: if (k + 1 == i3)
      if ((this.mNewEllipse) && (i2 < this.mListInfo.size()))
      {
        (m - (int)this.mEllipsisWidth2);
        f2 += f4;
        f4 = f3;
        f1 = n + i9;
      }
    while (true)
    {
      label2337: KXLinkInfoRect localKXLinkInfoRect3 = new KXLinkInfoRect();
      float f15 = mBmpStar.getHeight();
      f4 = Math.max(f4, f15);
      float f16 = (f4 - i10) / 2.0F;
      float f17 = f1 - i9;
      float f18 = f2 + f16;
      float f19 = f2 + f4 - f16;
      localKXLinkInfoRect3.set(f17, f18, f1, f19);
      localKXLinkInfoRect3.start = -1;
      localKXLinkInfoRect3.end = -1;
      localKXLinkInfo1.addRectF(localKXLinkInfoRect3);
      break;
      (m - (int)this.mEllipsisWidth);
      break label2337;
      break label2337;
      label2468: f1 += i9;
    }
  }

  private void paintSelectRectF(Canvas paramCanvas, Paint paramPaint, KXLinkInfo paramKXLinkInfo)
  {
    Iterator localIterator = paramKXLinkInfo.getRectFList().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      KXLinkInfoRect localKXLinkInfoRect = (KXLinkInfoRect)localIterator.next();
      if ((localKXLinkInfoRect.start < 0) || (localKXLinkInfoRect.end < 0))
        continue;
      paramPaint.setColor(getContext().getResources().getColor(2130839406));
      paramCanvas.drawRect(localKXLinkInfoRect.left, localKXLinkInfoRect.top, localKXLinkInfoRect.right, localKXLinkInfoRect.bottom, paramPaint);
    }
  }

  private void readAttrs(AttributeSet paramAttributeSet)
  {
    boolean bool = paramAttributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "singleLine", false);
    int i = paramAttributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "lines", -1);
    if (bool)
      this.mMaxLines = 1;
    do
      return;
    while (i <= 0);
    this.mMaxLines = i;
  }

  public String getContentString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator;
    if (this.mListInfo != null)
      localIterator = this.mListInfo.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localStringBuffer.toString();
      KXLinkInfo localKXLinkInfo = (KXLinkInfo)localIterator.next();
      if (TextUtils.isEmpty(localKXLinkInfo.getContent()))
        continue;
      localStringBuffer.append(localKXLinkInfo.getContent());
    }
  }

  public ArrayList<KXLinkInfo> getTitleList()
  {
    return this.mListInfo;
  }

  public boolean isEllipsed()
  {
    return this.mEllipsed;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    while (true)
    {
      Paint localPaint;
      try
      {
        if (this.mListInfo == null)
          break;
        if (this.mListInfo.size() == 0)
          return;
        localPaint = new Paint();
        localPaint.setColor(0);
        Rect localRect = new Rect();
        localRect.set(0, 0, getWidth(), getHeight());
        paramCanvas.drawRect(localRect, localPaint);
        if (!this.mShowShadow)
          continue;
        if (getId() == 2131363018)
        {
          localPaint.setShadowLayer(1.0F, 0.0F, 1.0F, 1543503872);
          localPaint.setTextSize(getTextSize());
          localPaint.setAntiAlias(true);
          localPaint.setStyle(Paint.Style.FILL);
          drawList(paramCanvas, localPaint, this.mListInfo, 0.0F);
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("KXIntroView", "onDraw", localException);
        return;
      }
      localPaint.setShadowLayer(1.0F, 0.0F, 1.0F, -1);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    try
    {
      measureView(paramInt1, paramInt2);
      if (this.mDisplayHeight == 0.0F)
        this.mDisplayHeight = 0.0F;
      setMeasuredDimension(this.mDisplayWidth, (int)this.mDisplayHeight);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        KXLog.e("KXIntroView", "onMeasure", localException);
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    super.onTouchEvent(paramMotionEvent);
    if ((!isEnabled()) || (this.Listener == null));
    while (true)
    {
      return false;
      if (paramMotionEvent.getAction() == 1)
      {
        float f3 = paramMotionEvent.getX();
        float f4 = paramMotionEvent.getY();
        if ((!cancelCurInfo(f3, f4)) && (clickLink(this.mListInfo, f3, f4, 1)))
          return true;
      }
      if (paramMotionEvent.getAction() != 0)
        break;
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      if (clickLink(this.mListInfo, f1, f2, 0))
        return true;
    }
    if (paramMotionEvent.getAction() == 3)
    {
      cancelCurInfo();
      return false;
    }
    cancelCurInfo(paramMotionEvent.getX(), paramMotionEvent.getY());
    return false;
  }

  public void setIntroViewMaxLines(int paramInt)
  {
    this.mMaxLines = paramInt;
  }

  public void setIsLbs(boolean paramBoolean)
  {
    this.mIsLbs = paramBoolean;
  }

  public void setLbsEllipse(boolean paramBoolean)
  {
    this.mLbsEllipse = paramBoolean;
  }

  public void setMaxWidth(int paramInt)
  {
    super.setMaxWidth(paramInt);
    this.mMaxWidPix = paramInt;
  }

  public void setOnClickLinkListener(OnClickLinkListener paramOnClickLinkListener)
  {
    this.Listener = paramOnClickLinkListener;
  }

  public void setShowShadow(boolean paramBoolean)
  {
    this.mShowShadow = paramBoolean;
  }

  public void setTitleList(ArrayList<KXLinkInfo> paramArrayList)
  {
    setLbsEllipse(false);
    this.mListInfo = paramArrayList;
    this.mDisplayHeight = 0.0F;
    requestLayout();
  }

  public void setViewMore(boolean paramBoolean)
  {
    this.mViewMore = paramBoolean;
  }

  public static abstract interface OnClickLinkListener
  {
    public abstract void onClick(KXLinkInfo paramKXLinkInfo);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXIntroView
 * JD-Core Version:    0.6.0
 */