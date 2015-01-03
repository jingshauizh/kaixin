package com.kaixin001.view;

import android.view.LayoutInflater;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.NewsInfo;

public class NewsItemAdvertView extends NewsItemView
{
  private NewsInfo info;

  public NewsItemAdvertView(BaseFragment paramBaseFragment, NewsInfo paramNewsInfo, NewsItemAdapter paramNewsItemAdapter)
  {
    super(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
    this.info = paramNewsInfo;
    this.rootView = this.inflater.inflate(2130903247, this);
  }

  public boolean show(NewsInfo paramNewsInfo)
  {
    super.show(paramNewsInfo);
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.NewsItemAdvertView
 * JD-Core Version:    0.6.0
 */