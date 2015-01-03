package com.kaixin001.util;

import android.text.TextUtils;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.view.NewsItemAdvertView;
import com.kaixin001.view.NewsItemFriendView;
import com.kaixin001.view.NewsItemPhotoView;
import com.kaixin001.view.NewsItemPlazaView;
import com.kaixin001.view.NewsItemRecordView;
import com.kaixin001.view.NewsItemRepostView;
import com.kaixin001.view.NewsItemSearchView;
import com.kaixin001.view.NewsItemView;

public class NewsItemViewFactory
{
  public static final int DIARY_TYPE = 4;
  public static final int FRINED_AND_GIFT_TYPE = 3;
  public static final int LOCATION_TYPE = 2;
  public static final int NEW_TYPE_NUMBER = 9;
  public static final int PHOTE_STYLE_BOX_DIARY_LOGO_TYPE = 5;
  public static final int PLAZA_REPOST = 6;
  public static final int RECORD_AND_REPOST_3ITEMS_OTHER = 1;
  public static final int REPASTE_AND_VOTE_TYPE = 0;
  public static final int TYPE_ADVERTISE = 8;
  public static final int TYPE_SEARCH_FRIEND = 7;

  public static int getNewsType(NewsInfo paramNewsInfo)
  {
    if ((paramNewsInfo == null) || (paramNewsInfo.mNtype == null));
    do
    {
      do
      {
        return 1;
        if ((paramNewsInfo.mNtype.equals("1088")) && (!TextUtils.isEmpty(paramNewsInfo.mNtypename)) && ("plaza_reposte".equals(paramNewsInfo.mNtypename)))
          return 6;
        if (paramNewsInfo.mNtype.equals("5001"))
          return 7;
        if ((paramNewsInfo.mNtype.equals("1088")) || (paramNewsInfo.mNtype.equals("1016")) || (paramNewsInfo.mNtype.equals("1020")))
          return 0;
      }
      while ((paramNewsInfo.mNtype.equals("1018")) || (paramNewsInfo.mNtype.equals("1242")) || (paramNewsInfo.mNtype.equals("1192")) || (paramNewsInfo.mNtype.equals("2")));
      if ((paramNewsInfo.mNtype.equals("0")) || (paramNewsInfo.mNtype.equals("1002")))
        return 3;
      if ((paramNewsInfo.mNtype.equals("1")) || (paramNewsInfo.mNtype.equals("1210")) || (paramNewsInfo.mNtype.equals("4")))
        return 5;
    }
    while (!paramNewsInfo.mNtype.equals("1120"));
    return 8;
  }

  public static NewsItemView makeNewsItemViewFactory(BaseFragment paramBaseFragment, NewsInfo paramNewsInfo, NewsItemAdapter paramNewsItemAdapter)
  {
    if ((paramNewsInfo == null) || (paramBaseFragment == null) || (paramNewsItemAdapter == null))
      return null;
    if ((TextUtils.isEmpty(paramNewsInfo.mNtype)) || (paramNewsInfo.mNtype.equals("5001")))
      return new NewsItemSearchView(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
    if ((paramNewsInfo.mNtype.equals("1088")) && (!TextUtils.isEmpty(paramNewsInfo.mNtypename)) && (paramNewsInfo.mNtypename.equals("plaza_reposte")))
      return new NewsItemPlazaView(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
    if ((paramNewsInfo.mNtype.equals("1088")) || (paramNewsInfo.mNtype.equals("1016")) || (paramNewsInfo.mNtype.equals("1020")))
      return new NewsItemRepostView(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
    if ((paramNewsInfo.mNtype.equals("1018")) || (paramNewsInfo.mNtype.equals("1242")) || (paramNewsInfo.mNtype.equals("1192")) || (paramNewsInfo.mNtype.equals("2")))
      return new NewsItemRecordView(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
    if ((paramNewsInfo.mNtype.equals("0")) || (paramNewsInfo.mNtype.equals("1002")))
      return new NewsItemFriendView(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
    if ((paramNewsInfo.mNtype.equals("1")) || (paramNewsInfo.mNtype.equals("1210")) || (paramNewsInfo.mNtype.equals("4")))
      return new NewsItemPhotoView(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
    if (paramNewsInfo.mNtype.equals("1120"))
      return new NewsItemAdvertView(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
    return new NewsItemRecordView(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.NewsItemViewFactory
 * JD-Core Version:    0.6.0
 */