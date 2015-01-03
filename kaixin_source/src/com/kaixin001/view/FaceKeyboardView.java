package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.kaixin001.activity.KXActivity;
import com.kaixin001.model.FaceModel;
import java.util.ArrayList;
import java.util.Collection;

public class FaceKeyboardView extends FrameLayout
  implements ViewPager.OnPageChangeListener, View.OnClickListener
{
  public static final int FACE_DELETE = -1;
  public static final int FACE_NONE = -2;
  public static final int PAGE_FACE_NUM = 31;
  private Context mContext;
  private ViewPager mCurViewPager;
  private Button mEmojiBtn;
  private ViewPager mEmojiFaceViewPager;
  private FaceModel mFaceModel;
  private int mFaceType = -1;
  private IndexIndicateView mIndexIndicateView;
  private ViewPager mKXFaceViewPager;
  private Button mKaixinBtn;
  private OnFaceSelectedListener mOnFaceSelectedListener;

  public FaceKeyboardView(Context paramContext)
  {
    super(paramContext);
  }

  public FaceKeyboardView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public FaceKeyboardView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private ArrayList<Bitmap> getFaceIcons(int paramInt)
  {
    return this.mFaceModel.getFaceIcons(paramInt);
  }

  private Bitmap getFaceImg(int paramInt)
  {
    if (this.mFaceType == 0)
      return (Bitmap)getFaceIcons(this.mFaceType).get(paramInt);
    return (Bitmap)getFaceIcons(this.mFaceType).get(paramInt);
  }

  private String getFaceMsg(int paramInt)
  {
    if (this.mFaceType == 0)
      return (String)getFaceStrings(this.mFaceType).get(paramInt);
    return (String)getFaceStrings(this.mFaceType).get(paramInt);
  }

  private ArrayList<String> getFaceStrings(int paramInt)
  {
    return this.mFaceModel.getFaceCodes(paramInt);
  }

  private void initFacePagers(int paramInt)
  {
    ArrayList localArrayList1 = new ArrayList();
    if (paramInt == 1);
    int i;
    Bitmap localBitmap;
    ArrayList localArrayList2;
    int j;
    for (ViewPager localViewPager = this.mEmojiFaceViewPager; ; localViewPager = this.mKXFaceViewPager)
    {
      if (localArrayList1.size() == 0)
      {
        i = 0;
        localBitmap = BitmapFactory.decodeResource(getResources(), 2130838163);
        localArrayList2 = getFaceIcons(paramInt);
        j = localArrayList2.size();
        if (i < j)
          break;
      }
      localViewPager.setAdapter(new FaceKeyboardAdapter(localArrayList1));
      localViewPager.setOnPageChangeListener(this);
      return;
    }
    if (i + 31 < j);
    ArrayList localArrayList3;
    int m;
    for (int k = 31; ; k = j - i)
    {
      localArrayList3 = new ArrayList();
      m = 0;
      if (m < 31)
        break label185;
      localArrayList3.add(localBitmap);
      FacePager localFacePager = new FacePager(this.mContext, localArrayList3);
      localFacePager.setIndex(localArrayList1.size());
      localFacePager.setOnFaceSelectedListener(this.mOnFaceSelectedListener);
      localArrayList1.add(localFacePager);
      i += 31;
      break;
    }
    label185: if ((k < 31) && (m >= k))
      localArrayList3.add(null);
    while (true)
    {
      m++;
      break;
      localArrayList3.add((Bitmap)localArrayList2.get(i + m));
    }
  }

  private void showFaceType(int paramInt)
  {
    if (this.mFaceType == paramInt)
      return;
    this.mFaceType = paramInt;
    if (paramInt == 1)
    {
      this.mCurViewPager = this.mEmojiFaceViewPager;
      this.mKXFaceViewPager.setVisibility(4);
      this.mEmojiFaceViewPager.setVisibility(0);
      this.mKaixinBtn.setBackgroundResource(2130839432);
      this.mEmojiBtn.setBackgroundResource(2130839433);
    }
    while (true)
    {
      this.mCurViewPager.setCurrentItem(0);
      DisplayMetrics localDisplayMetrics = this.mContext.getResources().getDisplayMetrics();
      this.mIndexIndicateView.updateState(this.mCurViewPager.getAdapter().getCount(), 0, localDisplayMetrics.widthPixels);
      return;
      this.mCurViewPager = this.mKXFaceViewPager;
      this.mKXFaceViewPager.setVisibility(0);
      this.mEmojiFaceViewPager.setVisibility(4);
      this.mKaixinBtn.setBackgroundResource(2130839433);
      this.mEmojiBtn.setBackgroundResource(2130839432);
    }
  }

  public void init(Context paramContext)
  {
    this.mContext = paramContext;
    View localView = inflate(paramContext, 2130903101, this);
    this.mKXFaceViewPager = ((ViewPager)localView.findViewById(2131362208));
    this.mEmojiFaceViewPager = ((ViewPager)localView.findViewById(2131362209));
    this.mKaixinBtn = ((Button)localView.findViewById(2131362211));
    this.mKaixinBtn.setOnClickListener(this);
    this.mEmojiBtn = ((Button)localView.findViewById(2131362212));
    this.mEmojiBtn.setOnClickListener(this);
    this.mIndexIndicateView = ((IndexIndicateView)localView.findViewById(2131362210));
    this.mFaceModel = FaceModel.getInstance();
    initFacePagers(0);
    initFacePagers(1);
    showFaceType(0);
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362211:
      showFaceType(0);
      return;
    case 2131362212:
    }
    showFaceType(1);
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0)
    {
      KXSliderLayout2.mCansHorizontalScrolling = false;
      if ((this.mContext instanceof KXActivity))
        ((KXActivity)this.mContext).disallowDetectSlide(true);
    }
    return super.onInterceptTouchEvent(paramMotionEvent);
  }

  public void onPageScrollStateChanged(int paramInt)
  {
  }

  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
  {
  }

  public void onPageSelected(int paramInt)
  {
    this.mIndexIndicateView.updateState(this.mCurViewPager.getAdapter().getCount(), paramInt, getWidth());
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = super.onTouchEvent(paramMotionEvent);
    if (bool)
    {
      KXSliderLayout2.mCansHorizontalScrolling = false;
      if ((this.mContext instanceof KXActivity))
        ((KXActivity)this.mContext).disallowDetectSlide(true);
    }
    return bool;
  }

  public void setOnFaceSelectedListener(OnFaceSelectedListener paramOnFaceSelectedListener)
  {
    this.mOnFaceSelectedListener = paramOnFaceSelectedListener;
  }

  class FaceKeyboardAdapter extends PagerAdapter
  {
    private ArrayList<View> mViews = new ArrayList();

    public FaceKeyboardAdapter()
    {
      Collection localCollection;
      if (localCollection != null)
        this.mViews.addAll(localCollection);
    }

    public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
    {
      try
      {
        ((ViewPager)paramViewGroup).removeView((View)this.mViews.get(paramInt));
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }

    public int getCount()
    {
      return this.mViews.size();
    }

    public Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
    {
      try
      {
        ((ViewPager)paramViewGroup).addView((View)this.mViews.get(paramInt), 0);
        return this.mViews.get(paramInt);
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }

    public boolean isViewFromObject(View paramView, Object paramObject)
    {
      return paramView == paramObject;
    }
  }

  class FacePager extends FrameLayout
    implements AdapterView.OnItemClickListener
  {
    private FaceKeyboardView.FacePagerAdapter mFaceAdapter;
    private ArrayList<Bitmap> mFaceDataList;
    private GridView mGridView;
    private int mIndex = 0;
    private FaceKeyboardView.OnFaceSelectedListener mListener;

    public FacePager(ArrayList<Bitmap> arg2)
    {
      super();
      Object localObject;
      this.mFaceDataList = localObject;
      init(localContext);
    }

    private void init(Context paramContext)
    {
      this.mGridView = ((GridView)inflate(paramContext, 2130903102, this).findViewById(2131362213));
      this.mFaceAdapter = new FaceKeyboardView.FacePagerAdapter(FaceKeyboardView.this, FaceKeyboardView.this.mContext, this.mFaceDataList);
      this.mGridView.setOnItemClickListener(this);
      this.mGridView.setAdapter(this.mFaceAdapter);
    }

    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      if (this.mListener != null)
      {
        if (paramInt == 31)
          this.mListener.FaceSelected(-1, null, null);
      }
      else
        return;
      if ((Bitmap)this.mFaceDataList.get(paramInt) == null)
      {
        this.mListener.FaceSelected(-2, null, null);
        return;
      }
      int i = paramInt + 31 * this.mIndex;
      this.mListener.FaceSelected(i, FaceKeyboardView.this.getFaceMsg(i), FaceKeyboardView.this.getFaceImg(i));
    }

    public void setIndex(int paramInt)
    {
      this.mIndex = paramInt;
    }

    public void setOnFaceSelectedListener(FaceKeyboardView.OnFaceSelectedListener paramOnFaceSelectedListener)
    {
      this.mListener = paramOnFaceSelectedListener;
    }
  }

  class FacePagerAdapter extends BaseAdapter
  {
    private ArrayList<Bitmap> mFaceDataList;

    public FacePagerAdapter(ArrayList<Bitmap> arg2)
    {
      Object localObject;
      this.mFaceDataList = localObject;
    }

    public int getCount()
    {
      if (this.mFaceDataList == null)
        return 0;
      return this.mFaceDataList.size();
    }

    public Object getItem(int paramInt)
    {
      return Integer.valueOf(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      ImageView localImageView;
      if (paramView == null)
      {
        localImageView = new ImageView(FaceKeyboardView.this.mContext);
        DisplayMetrics localDisplayMetrics = FaceKeyboardView.this.mContext.getResources().getDisplayMetrics();
        localImageView.setLayoutParams(new AbsListView.LayoutParams((int)(40.0F * localDisplayMetrics.density), (int)(178.0F * localDisplayMetrics.density / 4.0F)));
        localImageView.setScaleType(ImageView.ScaleType.CENTER);
      }
      while (true)
      {
        localImageView.setImageBitmap(null);
        localImageView.setImageBitmap((Bitmap)this.mFaceDataList.get(paramInt));
        return localImageView;
        localImageView = (ImageView)paramView;
      }
    }
  }

  public static abstract interface OnFaceSelectedListener
  {
    public abstract void FaceSelected(int paramInt, String paramString, Bitmap paramBitmap);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.FaceKeyboardView
 * JD-Core Version:    0.6.0
 */