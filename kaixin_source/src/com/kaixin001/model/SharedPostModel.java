package com.kaixin001.model;

import com.kaixin001.item.RepItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SharedPostModel extends KXModel
{
  public static final String TYPE_HOT = "hot";
  public static final String TYPE_NORMAL = "normal";
  protected static final Map<String, SharedPostModel> holder;
  protected static final Set<String> set = new HashSet();
  protected ArrayList<RepItem> mRepostList = null;
  private int mTotal = 0;
  private List<RepItem> moreItems;

  static
  {
    set.add("hot");
    set.add("normal");
    holder = new HashMap();
  }

  public static void clear(String paramString)
  {
    if (!set.contains(paramString))
      throw new RuntimeException("illegal type");
    synchronized (holder)
    {
      SharedPostModel localSharedPostModel = (SharedPostModel)holder.remove(paramString);
      if (localSharedPostModel != null)
        localSharedPostModel.clear();
      return;
    }
  }

  public static SharedPostModel getInstance(String paramString)
  {
    if (!set.contains(paramString))
      throw new RuntimeException("illegal type");
    synchronized (holder)
    {
      SharedPostModel localSharedPostModel = (SharedPostModel)holder.get(paramString);
      if (localSharedPostModel == null)
      {
        localSharedPostModel = new SharedPostModel();
        holder.put(paramString, localSharedPostModel);
      }
      return localSharedPostModel;
    }
  }

  public void appendPosts(ArrayList<RepItem> paramArrayList, int paramInt)
  {
    if (this.mRepostList == null)
      this.mRepostList = new ArrayList();
    if ((paramArrayList == null) || (paramArrayList.size() == 0));
    do
    {
      return;
      this.mRepostList.addAll(paramArrayList);
    }
    while (paramArrayList.size() != 0);
    this.mTotal = this.mRepostList.size();
  }

  public void clear()
  {
    if (this.mRepostList != null)
      this.mRepostList.clear();
    this.mRepostList = null;
    this.mTotal = 0;
  }

  public List<RepItem> getMoreItems()
  {
    return this.moreItems;
  }

  public ArrayList<RepItem> getRepostList()
  {
    return this.mRepostList;
  }

  public int getTotal()
  {
    return this.mTotal;
  }

  public void setMoreItems(List<RepItem> paramList)
  {
    this.moreItems = paramList;
  }

  public void setPosts(ArrayList<RepItem> paramArrayList, int paramInt)
  {
    if (this.mRepostList == null)
      this.mRepostList = new ArrayList();
    while (true)
    {
      if (paramArrayList != null)
      {
        this.mRepostList.addAll(paramArrayList);
        if (paramArrayList.size() == 0)
          this.mTotal = this.mRepostList.size();
      }
      return;
      this.mRepostList.clear();
    }
  }

  public void setTotal(int paramInt)
  {
    this.mTotal = paramInt;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.SharedPostModel
 * JD-Core Version:    0.6.0
 */