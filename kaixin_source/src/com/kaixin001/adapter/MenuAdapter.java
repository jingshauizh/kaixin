package com.kaixin001.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.activity.MainActivity;
import com.kaixin001.activity.OnViewMoreClickListener;
import com.kaixin001.engine.NavigatorApplistEngine.KXAppItem;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.HoroscopeChooseFragment;
import com.kaixin001.fragment.HoroscopeFragment;
import com.kaixin001.fragment.KaixinMenuListFragment;
import com.kaixin001.menu.EventMenuItem;
import com.kaixin001.menu.KXAppMenuItem;
import com.kaixin001.menu.MenuCategory;
import com.kaixin001.menu.MenuItem;
import com.kaixin001.menu.MenuItem.OnClickListener;
import com.kaixin001.menu.MenuItemId;
import com.kaixin001.menu.TippedMenuItem;
import com.kaixin001.model.EventModel.EventData;
import com.kaixin001.model.Setting;
import com.kaixin001.model.User;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UserHabitUtils;
import java.util.ArrayList;

public class MenuAdapter
{
  static class CategoryViewTag
  {
    TextView tvText;

    public CategoryViewTag(View paramView)
    {
      this.tvText = ((TextView)paramView.findViewById(2131363005));
    }
  }

  public static class MenuItemViewTag
  {
    private BaseFragment context;
    ImageView ivIcon;
    ImageView ivIcon2;
    View mainView;
    TextView tvName;
    TextView tvTip;
    ImageView vDivider;

    public MenuItemViewTag(View paramView, BaseFragment paramBaseFragment)
    {
      this.context = paramBaseFragment;
      this.mainView = paramView.findViewById(2131363006);
      this.ivIcon = ((ImageView)paramView.findViewById(2131362452));
      this.tvName = ((TextView)paramView.findViewById(2131362454));
      this.ivIcon2 = ((ImageView)paramView.findViewById(2131363007));
      this.tvTip = ((TextView)paramView.findViewById(2131363008));
      this.vDivider = ((ImageView)paramView.findViewById(2131363009));
    }

    private void updateEvent(MenuItem paramMenuItem)
    {
      EventMenuItem localEventMenuItem = (EventMenuItem)paramMenuItem;
      EventModel.EventData localEventData = localEventMenuItem.mData;
      if (localEventMenuItem.mState == 3)
        this.tvName.setText(2131428365);
      do
      {
        return;
        if (localEventMenuItem.mState != 2)
          continue;
        this.tvName.setText(2131428366);
        return;
      }
      while (localEventData == null);
      this.tvName.setText(localEventData.getActionTitle());
    }

    public void showMenuItem(MenuItem paramMenuItem, boolean paramBoolean1, boolean paramBoolean2)
    {
      label13: label59: label87: label121: TippedMenuItem localTippedMenuItem;
      if (paramBoolean1)
      {
        this.mainView.setBackgroundResource(2130838978);
        this.ivIcon.setVisibility(4);
        if ((paramMenuItem.menuItemId.defIconId != 0) || (!TextUtils.isEmpty(paramMenuItem.iconURL)))
        {
          if (!paramBoolean1)
            break label434;
          this.ivIcon.setImageResource(paramMenuItem.menuItemId.pressIconId);
          this.ivIcon.setVisibility(0);
        }
        this.tvName.setVisibility(0);
        if (!paramBoolean1)
          break label451;
        this.tvName.setTextColor(-1);
        if (!TextUtils.isEmpty(paramMenuItem.text))
          break label464;
        if (paramMenuItem.menuItemId.textId != 0)
          this.tvName.setText(paramMenuItem.menuItemId.textId);
        this.ivIcon2.setVisibility(8);
        this.ivIcon2.setOnClickListener(null);
        this.tvTip.setVisibility(8);
        if ((paramMenuItem instanceof TippedMenuItem))
        {
          localTippedMenuItem = (TippedMenuItem)paramMenuItem;
          if (localTippedMenuItem.defTipIconId != 0)
          {
            this.ivIcon2.setVisibility(0);
            this.ivIcon2.setImageResource(localTippedMenuItem.defTipIconId);
          }
          if (!TextUtils.isEmpty(localTippedMenuItem.textTip))
            break label478;
          this.tvTip.setVisibility(4);
        }
        if ((!User.getInstance().GetLookAround()) && (paramMenuItem.menuItemId == MenuItemId.ID_PHOTO) && (KaixinMenuListFragment.mShowPictureAction))
        {
          this.ivIcon2.setVisibility(0);
          this.ivIcon2.setImageResource(2130838096);
        }
        if ((!User.getInstance().GetLookAround()) && (paramMenuItem.menuItemId == MenuItemId.ID_HOROSCOPE) && (!HoroscopeFragment.getItemClick(this.context.getActivity())))
        {
          this.tvTip.setVisibility(0);
          this.tvTip.setText("");
          this.tvTip.setBackgroundResource(2130838227);
        }
        if (User.getInstance().GetLookAround())
          switch ($SWITCH_TABLE$com$kaixin001$menu$MenuItemId()[paramMenuItem.menuItemId.ordinal()])
          {
          default:
            this.tvName.setTextColor(2013265919);
          case 1:
          case 4:
          case 15:
          case 23:
          }
      }
      while (true)
        switch ($SWITCH_TABLE$com$kaixin001$menu$MenuItemId()[paramMenuItem.menuItemId.ordinal()])
        {
        default:
          return;
          this.mainView.setBackgroundResource(2130839426);
          break label13;
          label434: this.ivIcon.setImageResource(paramMenuItem.menuItemId.defIconId);
          break label59;
          label451: this.tvName.setTextColor(-4934476);
          break label87;
          label464: this.tvName.setText(paramMenuItem.text);
          break label121;
          label478: this.tvTip.setVisibility(0);
          this.tvTip.setBackgroundResource(localTippedMenuItem.textTipBGId);
          if ((localTippedMenuItem.textTipBGId != 0) || (paramBoolean1))
          {
            this.tvTip.setTextColor(-657931);
            label520: this.tvTip.setText(localTippedMenuItem.textTip);
            if (localTippedMenuItem.textTip.length() < 2)
              break label595;
            this.tvTip.setMinWidth((int)(26.0F * KXEnvironment.DENSITY));
          }
          while (paramMenuItem.menuItemId == MenuItemId.ID_NEWS)
          {
            this.tvTip.setText("");
            break;
            this.tvTip.setTextColor(-4934476);
            break label520;
            label595: this.tvTip.setMinWidth((int)(20.0F * KXEnvironment.DENSITY));
          }
          this.tvName.setTextColor(-1);
        case 23:
        case 7:
        }
      if ((paramBoolean2) && (Setting.getInstance().calledByUnicom))
      {
        this.ivIcon2.setVisibility(0);
        this.ivIcon2.setImageResource(2130837548);
        this.ivIcon2.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            Intent localIntent = MenuAdapter.MenuItemViewTag.this.context.getActivity().getPackageManager().getLaunchIntentForPackage("com.unicom.united");
            if (localIntent != null)
              MenuAdapter.MenuItemViewTag.this.context.startActivity(localIntent);
          }
        });
        return;
      }
      this.ivIcon2.setVisibility(8);
      this.ivIcon2.setImageDrawable(null);
      this.ivIcon2.setOnClickListener(null);
      return;
      this.ivIcon2.setOnClickListener(((KaixinMenuListFragment)this.context).onClickUploadPhoto);
    }
  }

  public static class MenuListAdapter extends BaseAdapter
    implements AdapterView.OnItemClickListener
  {
    private LayoutInflater inflater;
    public ArrayList<MenuItem> list;
    private View lytFooter;
    private int mDownIndex;
    private View mFooter;
    private ProgressBar mFooterProBar;
    private TextView mFooterTV;
    private BaseFragment mFragment;
    private ImageView mSelectImageView;
    private int mSelectIndex;
    private TextView mSelectTextView;
    private RelativeLayout mSetting;
    private MenuCategory menuCategory;
    public MenuItem selectedItem = null;
    LayoutInflater vi;

    public MenuListAdapter(BaseFragment paramBaseFragment, MenuCategory paramMenuCategory, ImageView paramImageView, TextView paramTextView, RelativeLayout paramRelativeLayout)
    {
      this.menuCategory = paramMenuCategory;
      this.list = paramMenuCategory.menuItemList;
      this.mFragment = paramBaseFragment;
      this.mSelectImageView = paramImageView;
      this.mSelectTextView = paramTextView;
      this.mSetting = paramRelativeLayout;
      this.vi = ((LayoutInflater)paramBaseFragment.getActivity().getSystemService("layout_inflater"));
      this.mFooter = paramBaseFragment.getActivity().getLayoutInflater().inflate(2130903215, null);
      this.mFooter.setOnClickListener(new View.OnClickListener(paramBaseFragment)
      {
        public void onClick(View paramView)
        {
          ((OnViewMoreClickListener)this.val$fragment.getActivity()).onViewMoreClick();
        }
      });
      this.mFooter.setBackgroundResource(2130838611);
      this.lytFooter = this.mFooter.findViewById(2131362071);
      this.lytFooter.setDuplicateParentStateEnabled(true);
      this.mFooterTV = ((TextView)this.mFooter.findViewById(2131363012));
      this.mFooterProBar = ((ProgressBar)this.mFooter.findViewById(2131363011));
    }

    public MenuListAdapter(BaseFragment paramBaseFragment, ArrayList<MenuItem> paramArrayList)
    {
      this.list = paramArrayList;
      this.mFragment = paramBaseFragment;
      this.vi = ((LayoutInflater)paramBaseFragment.getActivity().getSystemService("layout_inflater"));
      this.mFooter = paramBaseFragment.getActivity().getLayoutInflater().inflate(2130903215, null);
      this.mFooter.setOnClickListener(new View.OnClickListener(paramBaseFragment)
      {
        public void onClick(View paramView)
        {
          ((OnViewMoreClickListener)this.val$fragment.getActivity()).onViewMoreClick();
        }
      });
      this.mFooter.setBackgroundResource(2130838611);
      this.lytFooter = this.mFooter.findViewById(2131362071);
      this.lytFooter.setDuplicateParentStateEnabled(true);
      this.mFooterTV = ((TextView)this.mFooter.findViewById(2131363012));
      this.mFooterProBar = ((ProgressBar)this.mFooter.findViewById(2131363011));
    }

    private static void recordGameUserHabit(Context paramContext, KXAppMenuItem paramKXAppMenuItem)
    {
      String str = paramKXAppMenuItem.kxAppItem.getAppId();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("MenuGame_").append(str).append("");
      UserHabitUtils.getInstance(paramContext).addUserHabit(localStringBuilder.toString());
    }

    int findMotionRow(ListView paramListView, int paramInt)
    {
      int i = paramListView.getChildCount();
      if (i > 0)
        for (int j = 0; ; j++)
        {
          if (j >= i)
            return -1 + (i + paramListView.getFirstVisiblePosition());
          if (paramInt <= paramListView.getChildAt(j).getBottom())
            return j + paramListView.getFirstVisiblePosition();
        }
      return -1;
    }

    public int getCount()
    {
      ArrayList localArrayList = this.list;
      int i = 0;
      if (localArrayList != null)
        i = this.list.size();
      return i;
    }

    public Object getItem(int paramInt)
    {
      return this.list.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      MenuItem localMenuItem = (MenuItem)getItem(paramInt);
      if (localMenuItem.menuItemId == MenuItemId.ID_VIEWMORE_SUGGEST_APPLICATION)
        return this.mFooter;
      MenuAdapter.MenuItemViewTag localMenuItemViewTag;
      label80: boolean bool;
      if ((paramView == null) || (paramView == this.mFooter))
        if ((this.mFragment instanceof KaixinMenuListFragment))
        {
          paramView = this.vi.inflate(2130903212, null);
          localMenuItemViewTag = new MenuAdapter.MenuItemViewTag(paramView, this.mFragment);
          paramView.setTag(localMenuItemViewTag);
          if (this.selectedItem != localMenuItem)
            break label151;
          bool = true;
          label92: localMenuItemViewTag.showMenuItem(localMenuItem, bool, false);
          if (paramInt != -1 + getCount())
            break label157;
          localMenuItemViewTag.vDivider.setVisibility(8);
        }
      while (true)
      {
        return paramView;
        paramView = this.vi.inflate(2130903213, null);
        break;
        localMenuItemViewTag = (MenuAdapter.MenuItemViewTag)paramView.getTag();
        break label80;
        label151: bool = false;
        break label92;
        label157: localMenuItemViewTag.vDivider.setVisibility(0);
      }
    }

    public boolean isFooterShowLoading()
    {
      if (this.mFooterProBar == null);
      do
        return false;
      while (this.mFooterProBar.getVisibility() != 0);
      return true;
    }

    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      this.mSelectImageView.setImageResource(2130838972);
      this.mSelectTextView.setTextColor(-4934476);
      this.mSetting.setBackgroundResource(2130839426);
      MenuItem localMenuItem = (MenuItem)getItem(paramInt);
      if (localMenuItem.menuItemId == MenuItemId.ID_PHOTO)
        KaixinMenuListFragment.mShowPictureAction = false;
      KXLog.d("FRAGMENT", "MenuAdapter onItemClick " + localMenuItem.menuItemId.id + " ----------------------------");
      if (User.getInstance().GetLookAround())
        switch ($SWITCH_TABLE$com$kaixin001$menu$MenuItemId()[localMenuItem.menuItemId.ordinal()])
        {
        default:
          this.mFragment.showLoginPage(0);
        case 1:
        case 4:
        case 15:
        case 23:
        }
      do
      {
        return;
        switch ($SWITCH_TABLE$com$kaixin001$menu$MenuItemId()[localMenuItem.menuItemId.ordinal()])
        {
        default:
        case 24:
        }
      }
      while (localMenuItem.onMenuItemClickListener == null);
      if ((localMenuItem.menuItemId == MenuItemId.ID_HOROSCOPE) && (!HoroscopeChooseFragment.getHoroscopeSelected(this.mFragment.getActivity())))
      {
        this.selectedItem = localMenuItem;
        notifyDataSetChanged();
        ((MainActivity)this.mFragment.getActivity()).clearBackStack();
        Intent localIntent = new Intent(this.mFragment.getActivity(), HoroscopeChooseFragment.class);
        localIntent.putExtra("from", "KaixinMenuListFragment");
        if ((this.mFragment instanceof KaixinMenuListFragment))
        {
          MainActivity localMainActivity = (MainActivity)((KaixinMenuListFragment)this.mFragment).getActivity();
          localMainActivity.startSubFragment(localIntent);
          localMainActivity.showSubFragment();
        }
        while (true)
        {
          UserHabitUtils.getInstance(this.mFragment.getActivity()).addUserHabit("click_list_horoscope");
          return;
          KeyEvent localKeyEvent = new KeyEvent(0, 82);
          this.mFragment.getActivity().dispatchKeyEvent(localKeyEvent);
          return;
          this.mFragment.startActivity(localIntent);
        }
      }
      this.selectedItem = localMenuItem;
      notifyDataSetChanged();
      localMenuItem.onMenuItemClickListener.onClick(this.mFragment);
    }

    public boolean onTapDown(MotionEvent paramMotionEvent, ListView paramListView, ImageView paramImageView, TextView paramTextView, RelativeLayout paramRelativeLayout)
    {
      int i = paramListView.getFirstVisiblePosition();
      int[] arrayOfInt = new int[2];
      paramListView.getLocationOnScreen(arrayOfInt);
      if ((arrayOfInt[1] + paramListView.getHeight() < paramMotionEvent.getY()) || (paramMotionEvent.getY() < arrayOfInt[1]))
      {
        if (paramListView.getChildCount() < 0)
        {
          ((MenuAdapter.MenuItemViewTag)paramListView.getChildAt(0).getTag()).showMenuItem((MenuItem)getItem(i + 0), false, false);
          return false;
        }
      }
      else
      {
        paramImageView.setImageResource(2130838972);
        paramTextView.setTextColor(-4934476);
        paramRelativeLayout.setBackgroundResource(2130839426);
      }
      int j = (int)(11.0F * this.mFragment.getResources().getDisplayMetrics().density);
      int k = paramListView.pointToPosition((int)paramMotionEvent.getX() - arrayOfInt[0], j + ((int)paramMotionEvent.getY() - arrayOfInt[1]));
      if (paramMotionEvent.getAction() == 1)
        if (this.mDownIndex >= 0)
          onItemClick(null, null, this.mDownIndex, 0L);
      int m;
      do
      {
        do
          return true;
        while ((paramMotionEvent.getAction() != 0) && (paramMotionEvent.getAction() != 2));
        m = this.mDownIndex;
        this.mDownIndex = k;
      }
      while (m == this.mDownIndex);
      int n = paramListView.getChildCount();
      int i1 = 0;
      label229: MenuAdapter.MenuItemViewTag localMenuItemViewTag;
      MenuItem localMenuItem;
      if (i1 < n)
      {
        localMenuItemViewTag = (MenuAdapter.MenuItemViewTag)paramListView.getChildAt(i1).getTag();
        localMenuItem = (MenuItem)getItem(i + i1);
        if (i1 != this.mDownIndex - i)
          break label295;
      }
      label295: for (boolean bool = true; ; bool = false)
      {
        localMenuItemViewTag.showMenuItem(localMenuItem, bool, false);
        i1++;
        break label229;
        break;
      }
    }

    public void setAllItemUnSelected(ListView paramListView)
    {
      int i = paramListView.getFirstVisiblePosition();
      int j = paramListView.getChildCount();
      for (int k = 0; ; k++)
      {
        if (k >= j)
          return;
        ((MenuAdapter.MenuItemViewTag)paramListView.getChildAt(k).getTag()).showMenuItem((MenuItem)getItem(i + k), false, false);
      }
    }

    public void showLoadingFooter(boolean paramBoolean)
    {
      ProgressBar localProgressBar;
      if (this.mFooterProBar != null)
      {
        localProgressBar = this.mFooterProBar;
        if (!paramBoolean)
          break label73;
      }
      label73: for (int j = 0; ; j = 4)
      {
        localProgressBar.setVisibility(j);
        if (this.mFooterTV != null)
        {
          int i = this.mFragment.getResources().getColor(2130839385);
          if (paramBoolean)
            i = this.mFragment.getResources().getColor(2130839395);
          this.mFooterTV.setTextColor(i);
        }
        return;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.MenuAdapter
 * JD-Core Version:    0.6.0
 */