package com.kaixin001.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FragmentUtil
{
  private static FragmentUtil util = new FragmentUtil();
  private List<BaseFragment> list = new ArrayList();

  public static FragmentUtil getInstance()
  {
    return util;
  }

  public void add(BaseFragment paramBaseFragment)
  {
    this.list.add(paramBaseFragment);
  }

  public void clear()
  {
    this.list.removeAll(this.list);
  }

  public void finish()
  {
    Iterator localIterator = this.list.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      ((BaseFragment)localIterator.next()).finish();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.FragmentUtil
 * JD-Core Version:    0.6.0
 */