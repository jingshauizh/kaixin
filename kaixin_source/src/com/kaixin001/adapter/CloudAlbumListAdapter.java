package com.kaixin001.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.kaixin001.engine.AsyncLocalImageLoader;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.NewsDetailPictureFragment;
import com.kaixin001.item.CloudAlbumPicItem;
import com.kaixin001.model.CloudAlbumModel;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.CloudAlbumManager;
import com.kaixin001.view.KXCloudAlbumPicView;
import java.util.ArrayList;
import java.util.HashMap;

public class CloudAlbumListAdapter extends BaseAdapter
{
  private static final int LINE_IMAGE_NUM = 3;
  private AsyncLocalImageLoader mAsyncLocalImageLoader;
  private HashMap<String, KXCloudAlbumPicView> mBindViewList = new HashMap();
  private HashMap<KXCloudAlbumPicView, String> mBindViewList2 = new HashMap();
  private View mFooterView;
  private BaseFragment mFragment;
  private ArrayList<ArrayList<CloudAlbumPicItem>> mItems = new ArrayList();

  public CloudAlbumListAdapter(BaseFragment paramBaseFragment, View paramView, ArrayList<CloudAlbumPicItem> paramArrayList)
  {
    this.mFragment = paramBaseFragment;
    this.mFooterView = paramView;
    this.mAsyncLocalImageLoader = new AsyncLocalImageLoader(paramBaseFragment.getActivity());
  }

  private void actionViewBigPic(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    Intent localIntent = new Intent(this.mFragment.getActivity(), NewsDetailPictureFragment.class);
    localIntent.putExtra("small", paramString1);
    localIntent.putExtra("large", paramString2);
    localIntent.putExtra("from", "cloudalbum");
    localIntent.putExtra("cpid", paramString3);
    localIntent.putExtra("md5", paramString4);
    localIntent.putExtra("status", paramInt);
    localIntent.putExtra("action", "com.kaixin001.VIEW_SINGLE_PHOTO");
    AnimationUtil.startFragment(this.mFragment, localIntent, 1);
  }

  public int getCount()
  {
    return this.mItems.size();
  }

  public Object getItem(int paramInt)
  {
    if (paramInt >= this.mItems.size())
      return null;
    return this.mItems.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ArrayList localArrayList = (ArrayList)getItem(paramInt);
    if (localArrayList.size() == 0)
      return this.mFooterView;
    ViewHolder localViewHolder;
    KXCloudAlbumPicView[] arrayOfKXCloudAlbumPicView1;
    int j;
    label151: KXCloudAlbumPicView[] arrayOfKXCloudAlbumPicView2;
    int m;
    label166: int n;
    if (paramView != null)
    {
      View localView = this.mFooterView;
      if (paramView != localView);
    }
    else
    {
      paramView = this.mFragment.getActivity().getLayoutInflater().inflate(2130903084, null);
      localViewHolder = new ViewHolder();
      localViewHolder.mItemLayout = new KXCloudAlbumPicView[3];
      localViewHolder.mItemLayout[0] = ((KXCloudAlbumPicView)paramView.findViewById(2131362104));
      localViewHolder.mItemLayout[1] = ((KXCloudAlbumPicView)paramView.findViewById(2131362105));
      localViewHolder.mItemLayout[2] = ((KXCloudAlbumPicView)paramView.findViewById(2131362106));
      paramView.setTag(localViewHolder);
      arrayOfKXCloudAlbumPicView1 = localViewHolder.mItemLayout;
      int i = arrayOfKXCloudAlbumPicView1.length;
      j = 0;
      if (j >= i)
      {
        arrayOfKXCloudAlbumPicView2 = localViewHolder.mItemLayout;
        int k = arrayOfKXCloudAlbumPicView2.length;
        m = 0;
        if (m < k)
          break label233;
        n = 0;
      }
    }
    while (true)
    {
      if (n >= localArrayList.size())
      {
        return paramView;
        KXCloudAlbumPicView localKXCloudAlbumPicView = arrayOfKXCloudAlbumPicView1[j];
        CloudAlbumManager.getInstance().addHandler(localKXCloudAlbumPicView.getHandler());
        localKXCloudAlbumPicView.setAsyncLocalImageLoader(this.mAsyncLocalImageLoader);
        j++;
        break;
        localViewHolder = (ViewHolder)paramView.getTag();
        break label151;
        label233: arrayOfKXCloudAlbumPicView2[m].setVisibility(4);
        m++;
        break label166;
      }
      CloudAlbumPicItem localCloudAlbumPicItem = (CloudAlbumPicItem)localArrayList.get(n);
      localViewHolder.mItemLayout[n].setVisibility(0);
      localViewHolder.mItemLayout[n].update(this.mFragment, this.mAsyncLocalImageLoader, localCloudAlbumPicItem);
      localViewHolder.mItemLayout[n].setOnClickListener(new View.OnClickListener(localCloudAlbumPicItem)
      {
        public void onClick(View paramView)
        {
          int i = this.val$pic.mState;
          if (this.val$pic.mIsLocalAlbumPic)
            i = CloudAlbumModel.getInstance().getStatus(this.val$pic.mMD5);
          CloudAlbumListAdapter localCloudAlbumListAdapter = CloudAlbumListAdapter.this;
          String str1 = this.val$pic.mThumbUrl;
          if (TextUtils.isEmpty(this.val$pic.mLargeUrl));
          for (String str2 = this.val$pic.mUrl; ; str2 = this.val$pic.mLargeUrl)
          {
            localCloudAlbumListAdapter.actionViewBigPic(str1, str2, this.val$pic.mCpid, this.val$pic.mMD5, i);
            return;
          }
        }
      });
      synchronized (this.mBindViewList)
      {
        String str = (String)this.mBindViewList2.get(localViewHolder.mItemLayout[n]);
        if (str != null)
        {
          this.mBindViewList.remove(str);
          this.mBindViewList2.remove(localViewHolder.mItemLayout[n]);
        }
        this.mBindViewList.put(localCloudAlbumPicItem.mMD5, localViewHolder.mItemLayout[n]);
        this.mBindViewList2.put(localViewHolder.mItemLayout[n], localCloudAlbumPicItem.mMD5);
        n++;
      }
    }
  }

  public void setListData(ArrayList<CloudAlbumPicItem> paramArrayList, boolean paramBoolean)
  {
    this.mItems.clear();
    Object localObject = null;
    int i = 0;
    if (i >= paramArrayList.size())
    {
      if ((localObject != null) && (((ArrayList)localObject).size() > 0))
        this.mItems.add(localObject);
      if (paramBoolean)
      {
        ArrayList localArrayList = new ArrayList();
        this.mItems.add(localArrayList);
      }
      return;
    }
    if (localObject == null)
      localObject = new ArrayList();
    CloudAlbumPicItem localCloudAlbumPicItem = (CloudAlbumPicItem)paramArrayList.get(i);
    if (CloudAlbumModel.getInstance().getStatus(localCloudAlbumPicItem.mMD5) == 4);
    while (true)
    {
      i++;
      break;
      ((ArrayList)localObject).add(localCloudAlbumPicItem);
      if (((ArrayList)localObject).size() != 3)
        continue;
      this.mItems.add(localObject);
      localObject = null;
    }
  }

  class ViewHolder
  {
    public KXCloudAlbumPicView[] mItemLayout;

    ViewHolder()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.CloudAlbumListAdapter
 * JD-Core Version:    0.6.0
 */