package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KXPopupListWindow extends PopupWindow
{
  public static final int IMG_TYPE_DEFAULT = 9001;
  private CategoryListAdapter mAdapter;
  private DoSelect mClickListener = null;
  private Context mContext;
  private ArrayList<Integer> mIconRes;
  private ArrayList<Integer> mImgRes;
  private int mImgType = 0;
  private int mItemLayoutId = 0;
  private ListView mListView;
  private ArrayList<String> mStrs = new ArrayList();
  private View mViewPopupWindow;
  private View.OnKeyListener tabMenuOnKeyListener = new View.OnKeyListener()
  {
    public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
    {
      if ((paramInt == 4) && (KXPopupListWindow.this.isShowing()))
      {
        KXPopupListWindow.this.dismiss();
        if (KXPopupListWindow.this.mClickListener != null)
          KXPopupListWindow.this.mClickListener.doSelect(KXPopupListWindow.this.mListView.getSelectedItemPosition());
        return true;
      }
      return false;
    }
  };

  public KXPopupListWindow(View paramView, int paramInt1, int paramInt2, boolean paramBoolean, Context paramContext, ArrayList<String> paramArrayList)
  {
    super(paramView, paramInt1, paramInt2, paramBoolean);
    this.mViewPopupWindow = paramView;
    this.mContext = paramContext;
    this.mIconRes = null;
    this.mStrs = paramArrayList;
    this.mImgRes = null;
    initView();
  }

  public KXPopupListWindow(View paramView, int paramInt1, int paramInt2, boolean paramBoolean, Context paramContext, ArrayList<Integer> paramArrayList1, ArrayList<Integer> paramArrayList2, ArrayList<Integer> paramArrayList3)
  {
    super(paramView, paramInt1, paramInt2, paramBoolean);
    this.mViewPopupWindow = paramView;
    this.mContext = paramContext;
    this.mIconRes = paramArrayList1;
    this.mImgRes = paramArrayList3;
    Iterator localIterator = paramArrayList2.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        initView();
        return;
      }
      Integer localInteger = (Integer)localIterator.next();
      localInteger.intValue();
      this.mContext.getResources().getString(localInteger.intValue());
      this.mStrs.add(this.mContext.getResources().getString(localInteger.intValue()));
    }
  }

  private void initView()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.mStrs != null)
      localArrayList.addAll(this.mStrs);
    this.mAdapter = new CategoryListAdapter(this.mContext, 2130903240, this.mIconRes, localArrayList, this.mImgRes);
    this.mListView = ((ListView)this.mViewPopupWindow.findViewById(2131363110));
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
      {
        if (KXPopupListWindow.this.mClickListener != null)
          KXPopupListWindow.this.mClickListener.doSelect(paramInt);
        KXPopupListWindow.this.dismiss();
      }
    });
    this.mListView.setFocusableInTouchMode(true);
    this.mListView.setOnKeyListener(this.tabMenuOnKeyListener);
  }

  public void setDoSelectListener(DoSelect paramDoSelect)
  {
    this.mClickListener = paramDoSelect;
  }

  public void setImgType(int paramInt)
  {
    this.mImgType = paramInt;
  }

  public void setItemLayoutId(int paramInt)
  {
    this.mItemLayoutId = paramInt;
  }

  private class CategoryListAdapter extends ArrayAdapter<String>
  {
    private ArrayList<Integer> icons;
    private int[] imageRes = null;
    private ArrayList<Integer> imgs;
    private ArrayList<String> items;
    private boolean showNew = false;

    public CategoryListAdapter(int paramArrayList1, ArrayList<Integer> paramArrayList, ArrayList<String> paramArrayList2, ArrayList<Integer> arg5)
    {
      super(paramArrayList, localList);
      this.items = localList;
      this.icons = paramArrayList2;
      Object localObject;
      this.imgs = localObject;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      ArrayList localArrayList1 = this.items;
      String str = null;
      if (localArrayList1 != null)
      {
        int i1 = this.items.size();
        str = null;
        if (paramInt < i1)
          str = (String)this.items.get(paramInt);
      }
      ArrayList localArrayList2 = this.icons;
      int i = 0;
      if (localArrayList2 != null)
      {
        int n = this.icons.size();
        i = 0;
        if (paramInt < n)
          i = ((Integer)this.icons.get(paramInt)).intValue();
      }
      ArrayList localArrayList3 = this.imgs;
      int j = 0;
      if (localArrayList3 != null)
      {
        int m = this.imgs.size();
        j = 0;
        if (paramInt < m)
          j = ((Integer)this.imgs.get(paramInt)).intValue();
      }
      ImageView localImageView2;
      while (true)
      {
        ImageView localImageView1;
        try
        {
          LayoutInflater localLayoutInflater = (LayoutInflater)KXPopupListWindow.this.mContext.getSystemService("layout_inflater");
          int k = KXPopupListWindow.this.mItemLayoutId;
          if (k > 0)
            continue;
          k = 2130903296;
          paramView = localLayoutInflater.inflate(k, null);
          localImageView1 = (ImageView)paramView.findViewById(2131362399);
          TextView localTextView = (TextView)paramView.findViewById(2131362400);
          localImageView2 = (ImageView)paramView.findViewById(2131362401);
          if (TextUtils.isEmpty(str))
            continue;
          localTextView.setText(str);
          localTextView.setVisibility(0);
          if (i > 0)
          {
            localImageView1.setVisibility(0);
            localImageView1.setImageResource(i);
            if (j <= 0)
              break;
            localImageView2.setVisibility(0);
            localImageView2.setImageResource(j);
            return paramView;
            localTextView.setVisibility(8);
            continue;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("RepostListAdapter", "getView", localException);
          return paramView;
        }
        localImageView1.setVisibility(8);
      }
      if (KXPopupListWindow.this.mImgType != 0)
      {
        localImageView2.setVisibility(0);
        return paramView;
      }
      localImageView2.setVisibility(8);
      return paramView;
    }
  }

  public static abstract interface DoSelect
  {
    public abstract void doSelect(int paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.KXPopupListWindow
 * JD-Core Version:    0.6.0
 */