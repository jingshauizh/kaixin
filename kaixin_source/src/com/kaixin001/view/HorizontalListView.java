package com.kaixin001.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.Scroller;
import java.util.LinkedList;
import java.util.Queue;

public class HorizontalListView extends AdapterView<ListAdapter>
{
  protected ListAdapter mAdapter;
  public boolean mAlwaysOverrideTouch = true;
  protected int mCurrentX;
  private boolean mDataChanged = false;
  private DataSetObserver mDataObserver = new DataSetObserver()
  {
    public void onChanged()
    {
      synchronized (HorizontalListView.this)
      {
        HorizontalListView.this.mDataChanged = true;
        HorizontalListView.this.invalidate();
        HorizontalListView.this.requestLayout();
        return;
      }
    }

    public void onInvalidated()
    {
      HorizontalListView.this.reset();
      HorizontalListView.this.invalidate();
      HorizontalListView.this.requestLayout();
    }
  };
  private int mDisplayOffset = 0;
  private GestureDetector mGesture;
  private int mLeftViewIndex = -1;
  private int mMaxX = 2147483647;
  protected int mNextX;
  private GestureDetector.OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener()
  {
    private boolean isEventWithinView(MotionEvent paramMotionEvent, View paramView)
    {
      Rect localRect = new Rect();
      int[] arrayOfInt = new int[2];
      paramView.getLocationOnScreen(arrayOfInt);
      int i = arrayOfInt[0];
      int j = i + paramView.getWidth();
      int k = arrayOfInt[1];
      localRect.set(i, k, j, k + paramView.getHeight());
      return localRect.contains((int)paramMotionEvent.getRawX(), (int)paramMotionEvent.getRawY());
    }

    public boolean onDown(MotionEvent paramMotionEvent)
    {
      return HorizontalListView.this.onDown(paramMotionEvent);
    }

    public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
    {
      return HorizontalListView.this.onFling(paramMotionEvent1, paramMotionEvent2, paramFloat1, paramFloat2);
    }

    public void onLongPress(MotionEvent paramMotionEvent)
    {
      int i = HorizontalListView.this.getChildCount();
      for (int j = 0; ; j++)
      {
        if (j >= i);
        while (true)
        {
          return;
          View localView = HorizontalListView.this.getChildAt(j);
          if (!isEventWithinView(paramMotionEvent, localView))
            break;
          if (HorizontalListView.this.mOnItemLongClicked == null)
            continue;
          HorizontalListView.this.mOnItemLongClicked.onItemLongClick(HorizontalListView.this, localView, j + (1 + HorizontalListView.this.mLeftViewIndex), HorizontalListView.this.mAdapter.getItemId(j + (1 + HorizontalListView.this.mLeftViewIndex)));
          return;
        }
      }
    }

    public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
    {
      synchronized (HorizontalListView.this)
      {
        HorizontalListView localHorizontalListView2 = HorizontalListView.this;
        localHorizontalListView2.mNextX += (int)paramFloat1;
        HorizontalListView.this.requestLayout();
        return true;
      }
    }

    public boolean onSingleTapConfirmed(MotionEvent paramMotionEvent)
    {
      for (int i = 0; ; i++)
      {
        if (i >= HorizontalListView.this.getChildCount());
        while (true)
        {
          return true;
          View localView = HorizontalListView.this.getChildAt(i);
          if (!isEventWithinView(paramMotionEvent, localView))
            break;
          if (HorizontalListView.this.mOnItemClicked != null)
            HorizontalListView.this.mOnItemClicked.onItemClick(HorizontalListView.this, localView, i + (1 + HorizontalListView.this.mLeftViewIndex), HorizontalListView.this.mAdapter.getItemId(i + (1 + HorizontalListView.this.mLeftViewIndex)));
          if (HorizontalListView.this.mOnItemSelected == null)
            continue;
          HorizontalListView.this.mOnItemSelected.onItemSelected(HorizontalListView.this, localView, i + (1 + HorizontalListView.this.mLeftViewIndex), HorizontalListView.this.mAdapter.getItemId(i + (1 + HorizontalListView.this.mLeftViewIndex)));
        }
      }
    }
  };
  private AdapterView.OnItemClickListener mOnItemClicked;
  private AdapterView.OnItemLongClickListener mOnItemLongClicked;
  private AdapterView.OnItemSelectedListener mOnItemSelected;
  private Queue<View> mRemovedViewQueue = new LinkedList();
  private int mRightViewIndex = 0;
  protected Scroller mScroller;

  public HorizontalListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initView();
  }

  private void addAndMeasureChild(View paramView, int paramInt)
  {
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    if (localLayoutParams == null)
      localLayoutParams = new ViewGroup.LayoutParams(-1, -1);
    addViewInLayout(paramView, paramInt, localLayoutParams, true);
    paramView.measure(View.MeasureSpec.makeMeasureSpec(getWidth(), -2147483648), View.MeasureSpec.makeMeasureSpec(getHeight(), -2147483648));
  }

  private void fillList(int paramInt)
  {
    View localView1 = getChildAt(-1 + getChildCount());
    int i = 0;
    if (localView1 != null)
      i = localView1.getRight();
    fillListRight(i, paramInt);
    View localView2 = getChildAt(0);
    int j = 0;
    if (localView2 != null)
      j = localView2.getLeft();
    fillListLeft(j, paramInt);
  }

  private void fillListLeft(int paramInt1, int paramInt2)
  {
    while (true)
    {
      if ((paramInt1 + paramInt2 <= 0) || (this.mLeftViewIndex < 0))
        return;
      View localView = this.mAdapter.getView(this.mLeftViewIndex, (View)this.mRemovedViewQueue.poll(), this);
      addAndMeasureChild(localView, 0);
      paramInt1 -= localView.getMeasuredWidth();
      this.mLeftViewIndex = (-1 + this.mLeftViewIndex);
      this.mDisplayOffset -= localView.getMeasuredWidth();
    }
  }

  private void fillListRight(int paramInt1, int paramInt2)
  {
    while (true)
    {
      if (this.mRightViewIndex >= this.mAdapter.getCount())
        return;
      View localView = this.mAdapter.getView(this.mRightViewIndex, (View)this.mRemovedViewQueue.poll(), this);
      addAndMeasureChild(localView, -1);
      paramInt1 += localView.getMeasuredWidth();
      if (this.mRightViewIndex == -1 + this.mAdapter.getCount())
        this.mMaxX = (paramInt1 + this.mCurrentX - getWidth());
      if (this.mMaxX < 0)
        this.mMaxX = 0;
      this.mRightViewIndex = (1 + this.mRightViewIndex);
    }
  }

  private void initView()
  {
    monitorenter;
    try
    {
      this.mLeftViewIndex = -1;
      this.mRightViewIndex = 0;
      this.mDisplayOffset = 0;
      this.mCurrentX = 0;
      this.mNextX = 0;
      this.mMaxX = 2147483647;
      this.mScroller = new Scroller(getContext());
      this.mGesture = new GestureDetector(getContext(), this.mOnGesture);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  private void positionItems(int paramInt)
  {
    int i;
    if (getChildCount() > 0)
    {
      this.mDisplayOffset = (paramInt + this.mDisplayOffset);
      i = this.mDisplayOffset;
    }
    for (int j = 0; ; j++)
    {
      if (j >= getChildCount())
        return;
      View localView = getChildAt(j);
      int k = localView.getMeasuredWidth();
      localView.layout(i, 0, i + k, localView.getMeasuredHeight());
      i += k + localView.getPaddingRight();
    }
  }

  private void removeNonVisibleItems(int paramInt)
  {
    View localView1 = getChildAt(0);
    if ((localView1 == null) || (paramInt + localView1.getRight() > 0));
    for (View localView2 = getChildAt(-1 + getChildCount()); ; localView2 = getChildAt(-1 + getChildCount()))
    {
      if ((localView2 == null) || (paramInt + localView2.getLeft() < getWidth()))
      {
        return;
        this.mDisplayOffset += localView1.getMeasuredWidth();
        this.mRemovedViewQueue.offer(localView1);
        removeViewInLayout(localView1);
        this.mLeftViewIndex = (1 + this.mLeftViewIndex);
        localView1 = getChildAt(0);
        break;
      }
      this.mRemovedViewQueue.offer(localView2);
      removeViewInLayout(localView2);
      this.mRightViewIndex = (-1 + this.mRightViewIndex);
    }
  }

  private void reset()
  {
    monitorenter;
    try
    {
      initView();
      removeAllViewsInLayout();
      requestLayout();
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    return super.dispatchTouchEvent(paramMotionEvent) | this.mGesture.onTouchEvent(paramMotionEvent);
  }

  public ListAdapter getAdapter()
  {
    return this.mAdapter;
  }

  public View getSelectedView()
  {
    return null;
  }

  protected boolean onDown(MotionEvent paramMotionEvent)
  {
    this.mScroller.forceFinished(true);
    return true;
  }

  protected boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    monitorenter;
    try
    {
      this.mScroller.fling(this.mNextX, 0, (int)(-paramFloat1), 0, 0, this.mMaxX, 0, 0);
      monitorexit;
      requestLayout();
      return true;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    monitorenter;
    try
    {
      super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
      ListAdapter localListAdapter = this.mAdapter;
      if (localListAdapter == null);
      while (true)
      {
        return;
        if (this.mDataChanged)
        {
          int j = this.mCurrentX;
          initView();
          removeAllViewsInLayout();
          this.mNextX = j;
          this.mDataChanged = false;
        }
        if (this.mScroller.computeScrollOffset())
          this.mNextX = this.mScroller.getCurrX();
        if (this.mNextX <= 0)
        {
          this.mNextX = 0;
          this.mScroller.forceFinished(true);
        }
        if (this.mNextX >= this.mMaxX)
        {
          this.mNextX = this.mMaxX;
          this.mScroller.forceFinished(true);
        }
        int i = this.mCurrentX - this.mNextX;
        removeNonVisibleItems(i);
        fillList(i);
        positionItems(i);
        this.mCurrentX = this.mNextX;
        if (this.mScroller.isFinished())
          continue;
        post(new Runnable()
        {
          public void run()
          {
            HorizontalListView.this.requestLayout();
          }
        });
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public void scrollTo(int paramInt)
  {
    monitorenter;
    try
    {
      this.mScroller.startScroll(this.mNextX, 0, paramInt - this.mNextX, 0);
      requestLayout();
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void setAdapter(ListAdapter paramListAdapter)
  {
    if (this.mAdapter != null)
      this.mAdapter.unregisterDataSetObserver(this.mDataObserver);
    this.mAdapter = paramListAdapter;
    this.mAdapter.registerDataSetObserver(this.mDataObserver);
    reset();
  }

  public void setOnItemClickListener(AdapterView.OnItemClickListener paramOnItemClickListener)
  {
    this.mOnItemClicked = paramOnItemClickListener;
  }

  public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener paramOnItemLongClickListener)
  {
    this.mOnItemLongClicked = paramOnItemLongClickListener;
  }

  public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener paramOnItemSelectedListener)
  {
    this.mOnItemSelected = paramOnItemSelectedListener;
  }

  public void setSelection(int paramInt)
  {
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.HorizontalListView
 * JD-Core Version:    0.6.0
 */