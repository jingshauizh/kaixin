package com.kaixin001.view;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.FindFriendFragment;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.util.UserHabitUtils;

public class NewsItemSearchView extends NewsItemView
{
  private BaseFragment mFragment;
  private NewsInfo mInfo;

  public NewsItemSearchView(BaseFragment paramBaseFragment, NewsInfo paramNewsInfo, NewsItemAdapter paramNewsItemAdapter)
  {
    super(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
    this.mFragment = paramBaseFragment;
    this.mInfo = paramNewsInfo;
  }

  public boolean show(NewsInfo paramNewsInfo)
  {
    showSearchView(paramNewsInfo);
    return true;
  }

  public void showSearchView(NewsInfo paramNewsInfo)
  {
    this.rootView = this.mFragment.getActivity().getLayoutInflater().inflate(2130903256, this);
    ((Button)this.rootView.findViewById(2131363275)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(NewsItemSearchView.this.mFragment.getActivity(), FindFriendFragment.class);
        NewsItemSearchView.this.mFragment.startFragment(localIntent, true, 1);
        UserHabitUtils.getInstance(NewsItemSearchView.this.mFragment.getActivity()).addUserHabit("news_fragment_add_friend");
      }
    });
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.NewsItemSearchView
 * JD-Core Version:    0.6.0
 */