package com.kaixin001.view;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.kaixin001.fragment.NewsFragment;
import com.kaixin001.model.User;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContentListWindow extends PopupWindow
  implements View.OnClickListener
{
  public static final int TYPE_ALL = 100;
  public static final int TYPE_CANCEL_ACTION = -1;
  public static final int TYPE_CONSTRUCTOR = 101;
  public static final int TYPE_EDIT_CARD = 201;
  public static final int TYPE_FRIENDS_LIST = 500;
  public static final int TYPE_FRIENDS_PUBLIC = 502;
  public static final int TYPE_HIDE_EMAIL = 202;
  public static final int TYPE_HIDE_MOBILE = 203;
  public static final int TYPE_HIDE_PHONE = 204;
  public static final int TYPE_OTHER = 301;
  public static final int TYPE_TOPIC_GROUP = 102;
  public static final int YPTE_FRIENDS_MY = 501;
  private static int[] mContentRes;
  private static int[] mContentResForLook;
  private static int[] mContentResFriends;
  public static String[] mEditContent = null;
  public static int[] mEditTypeList = { 201, 202, 203, 204 };
  private static int[] typeList;
  private static int[] typeListForLook;
  private int[] imageRes = null;
  private CategoryListAdapter mAdapter;
  private DoSelect mClickListener = null;
  private Context mContext;
  private LinearLayout mHeader;
  private ArrayList<String> mListData;
  private ListView mListView;
  private int mSelectType;
  private View mViewPopupWindow;
  private View.OnKeyListener tabMenuOnKeyListener = new View.OnKeyListener()
  {
    public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
    {
      if ((paramInt == 4) && (ContentListWindow.this.isShowing()))
      {
        ContentListWindow.this.dismiss();
        if (ContentListWindow.this.mClickListener != null)
          ContentListWindow.this.mClickListener.doSelect(-1);
        return true;
      }
      return false;
    }
  };

  static
  {
    mContentRes = new int[] { 2131428481, 2131428482 };
    mContentResForLook = new int[] { 2131427766, 2131427765, 2131427763 };
    mContentResFriends = new int[] { 2131427359, 2131427358 };
    int[] arrayOfInt1 = new int[2];
    arrayOfInt1[0] = 100;
    arrayOfInt1[1] = Integer.parseInt("11");
    typeList = arrayOfInt1;
    int[] arrayOfInt2 = new int[3];
    arrayOfInt2[0] = Integer.parseInt("1088");
    arrayOfInt2[1] = Integer.parseInt("1018");
    arrayOfInt2[2] = Integer.parseInt("1");
    typeListForLook = arrayOfInt2;
  }

  public ContentListWindow(View paramView, int paramInt1, int paramInt2, boolean paramBoolean, Context paramContext, int paramInt3)
  {
    super(paramView, paramInt1, paramInt2, paramBoolean);
    this.mViewPopupWindow = paramView;
    this.mContext = paramContext;
    this.mSelectType = paramInt3;
    initView();
    this.imageRes = null;
  }

  public ContentListWindow(View paramView, int paramInt1, int paramInt2, boolean paramBoolean, Context paramContext, int paramInt3, int[] paramArrayOfInt)
  {
    super(paramView, paramInt1, paramInt2, paramBoolean);
    this.mViewPopupWindow = paramView;
    this.mContext = paramContext;
    this.mSelectType = paramInt3;
    this.imageRes = paramArrayOfInt;
    initView();
  }

  private static int getIndexByType(int paramInt)
  {
    if (paramInt == 100)
    {
      i = 0;
      return i;
    }
    for (int i = 0; ; i++)
    {
      if (i >= typeList.length)
        return -1;
      if (typeList[i] == paramInt)
        break;
    }
  }

  public static String getStringByType(Context paramContext, int paramInt)
  {
    if (paramInt == 100)
      return paramContext.getString(2131428481);
    int i;
    if (!User.getInstance().GetLookAround())
      i = typeList.length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        return null;
        i = typeListForLook.length;
        break;
      }
      if (typeList[j] != paramInt)
        continue;
      if (!User.getInstance().GetLookAround())
        return paramContext.getString(mContentRes[j]);
      return paramContext.getString(mContentResForLook[j]);
    }
  }

  private void initView()
  {
    String[] arrayOfString;
    if ((this.mSelectType == 201) || (this.mSelectType == 301))
      arrayOfString = mEditContent;
    while (true)
    {
      this.mListData = new ArrayList(Arrays.asList(arrayOfString));
      this.mAdapter = new CategoryListAdapter(this.mContext, 2130903240, this.mListData, this.imageRes);
      this.mListView = ((ListView)this.mViewPopupWindow.findViewById(2131363110));
      this.mListView.setAdapter(this.mAdapter);
      this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
      {
        public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
        {
          if (ContentListWindow.this.mClickListener != null)
          {
            if (ContentListWindow.this.mSelectType != 201)
              break label48;
            ContentListWindow.this.mClickListener.doSelect(ContentListWindow.mEditTypeList[paramInt]);
          }
          while (true)
          {
            ContentListWindow.this.dismiss();
            return;
            label48: if (ContentListWindow.this.mSelectType == 301)
            {
              ContentListWindow.this.mClickListener.doSelect(paramInt);
              continue;
            }
            ContentListWindow.this.mClickListener.doSelect(ContentListWindow.typeList[paramInt]);
          }
        }
      });
      this.mListView.setFocusableInTouchMode(true);
      this.mListView.setOnKeyListener(this.tabMenuOnKeyListener);
      return;
      if ((this.mSelectType != 501) && (this.mSelectType != 502))
        break;
      arrayOfString = new String[mContentResFriends.length];
      for (int i = 0; i < arrayOfString.length; i++)
        arrayOfString[i] = this.mContext.getString(mContentResFriends[i]);
    }
    label201: int j;
    if (!User.getInstance().GetLookAround())
    {
      arrayOfString = new String[mContentRes.length];
      j = 0;
      label203: if (j < arrayOfString.length)
      {
        if (!User.getInstance().GetLookAround())
          break label250;
        arrayOfString[j] = this.mContext.getString(mContentResForLook[j]);
      }
    }
    while (true)
    {
      j++;
      break label203;
      break;
      arrayOfString = new String[mContentResForLook.length];
      break label201;
      label250: arrayOfString[j] = this.mContext.getString(mContentRes[j]);
    }
  }

  public void onClick(View paramView)
  {
    if ((paramView.equals(this.mHeader)) && (this.mClickListener != null))
    {
      this.mClickListener.doSelect(100);
      dismiss();
    }
  }

  public void setDoSelectListener(DoSelect paramDoSelect)
  {
    this.mClickListener = paramDoSelect;
  }

  public void setFocus(int paramInt)
  {
    int i = getIndexByType(paramInt);
    if (i != -1 + this.mSelectType)
      this.mSelectType = i;
  }

  private class CategoryListAdapter extends ArrayAdapter<String>
  {
    private int[] imageRes = null;
    private ArrayList<String> items;
    private boolean showNew = false;

    public CategoryListAdapter(int paramArrayList, ArrayList<String> paramArrayOfInt, int[] arg4)
    {
      super(paramArrayOfInt, localList);
      this.items = localList;
      this.showNew = NewsFragment.showCloudAlbumItemNew(ContentListWindow.this.mContext);
      Object localObject;
      this.imageRes = localObject;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      String str = (String)this.items.get(paramInt);
      ImageView localImageView2;
      while (true)
      {
        ImageView localImageView1;
        try
        {
          paramView = ((LayoutInflater)ContentListWindow.this.mContext.getSystemService("layout_inflater")).inflate(2130903240, null);
          TextView localTextView = (TextView)paramView.findViewById(2131363112);
          localTextView.setText(str);
          if ((TextUtils.isEmpty(str)) || (!str.equals(ContentListWindow.this.mContext.getResources().getString(2131428482))) || (!this.showNew))
            continue;
          localTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, ContentListWindow.this.mContext.getResources().getDrawable(2130838227), null);
          NewsFragment.setShowCloudAlbumItemNew(ContentListWindow.this.mContext, false);
          localImageView1 = (ImageView)paramView.findViewById(2131363113);
          if (ContentListWindow.this.mSelectType == 201)
          {
            localImageView1.setVisibility(8);
            localTextView.setTextColor(-7829368);
            localImageView2 = (ImageView)paramView.findViewById(2131363111);
            if ((this.imageRes != null) && (this.imageRes.length >= paramInt + 1))
              break;
            localImageView2.setVisibility(8);
            return paramView;
            localTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            continue;
          }
        }
        catch (Exception localException)
        {
          KXLog.e("RepostListAdapter", "getView", localException);
          return paramView;
        }
        if (paramInt == ContentListWindow.access$3(ContentListWindow.this.mSelectType))
        {
          localImageView1.setVisibility(0);
          continue;
        }
        localImageView1.setVisibility(8);
      }
      localImageView2.setImageResource(this.imageRes[paramInt]);
      localImageView2.setVisibility(0);
      return paramView;
    }
  }

  public static abstract interface DoSelect
  {
    public abstract void doSelect(int paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.ContentListWindow
 * JD-Core Version:    0.6.0
 */