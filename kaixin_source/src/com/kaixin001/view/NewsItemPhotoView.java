package com.kaixin001.view;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.PhotoItem;
import java.util.ArrayList;

public class NewsItemPhotoView extends NewsItemBaseView
{
  private NewsInfo info;
  public KXFrameImageView mImgBoxdiary02 = null;
  public KXFrameImageView mImgLogo01 = null;
  public KXFrameImageView mImgPhoto1 = null;
  public KXFrameImageView mImgPhoto2 = null;
  public KXFrameImageView mImgPhoto3 = null;
  public KXFrameImageView mImgUpPhoto03 = null;
  public KXIntroView mNewsItemContent = null;
  public RelativeLayout mNewsItemContentLayout = null;
  public LinearLayout newsItemImglayout = null;
  public LinearLayout newsItemImglayout2 = null;
  public TextView tvNewsItemFrom;

  public NewsItemPhotoView(BaseFragment paramBaseFragment, NewsInfo paramNewsInfo, NewsItemAdapter paramNewsItemAdapter)
  {
    super(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
    this.info = paramNewsInfo;
    this.rootView = this.inflater.inflate(2130903251, this);
    this.mNewsItemContent = ((KXIntroView)this.rootView.findViewById(2131362595));
    this.mNewsItemContentLayout = ((RelativeLayout)this.rootView.findViewById(2131362589));
    this.newsItemImglayout = ((LinearLayout)this.rootView.findViewById(2131362630));
    this.newsItemImglayout2 = ((LinearLayout)this.rootView.findViewById(2131362637));
    this.mImgLogo01 = ((KXFrameImageView)this.rootView.findViewById(2131363193));
    this.mImgBoxdiary02 = ((KXFrameImageView)this.rootView.findViewById(2131363194));
    this.mImgUpPhoto03 = ((KXFrameImageView)this.rootView.findViewById(2131362636));
    this.mImgPhoto1 = ((KXFrameImageView)this.rootView.findViewById(2131363197));
    this.mImgPhoto2 = ((KXFrameImageView)this.rootView.findViewById(2131363198));
    this.mImgPhoto3 = ((KXFrameImageView)this.rootView.findViewById(2131363199));
  }

  public boolean show(NewsInfo paramNewsInfo)
  {
    super.show(paramNewsInfo);
    this.mNewsItemContentLayout.setBackgroundResource(2130838682);
    if ("4".equals(paramNewsInfo.mNtype))
    {
      super.showTopLayout(paramNewsInfo, " 更新了头像");
      this.mNewsItemContentLayout.setBackgroundResource(2130838285);
    }
    while (true)
    {
      showTitle(paramNewsInfo);
      showPhotos(paramNewsInfo);
      showBottomLayout(paramNewsInfo);
      return true;
      if (!"1210".equals(paramNewsInfo.mNtype))
        continue;
      super.showTopLayout(paramNewsInfo, " 发表九宫格日记");
    }
  }

  protected void showBottomLayout(NewsInfo paramNewsInfo)
  {
    super.showBottomLayout(paramNewsInfo);
    this.tvNewsItemFrom = ((TextView)findViewById(2131363204));
    this.tvNewsItemFrom.setVisibility(8);
  }

  public void showPhotos(NewsInfo paramNewsInfo)
  {
    if ("1242".equals(paramNewsInfo.mNtype));
    do
    {
      return;
      if (("4".equals(paramNewsInfo.mNtype)) && (paramNewsInfo.mNtypename.equals("updatelogo")))
      {
        this.newsItemImglayout.setVisibility(0);
        this.newsItemImglayout2.setVisibility(8);
        this.mImgBoxdiary02.setVisibility(8);
        this.mImgUpPhoto03.setVisibility(8);
        this.mImgLogo01.setTag(null);
        this.mImgLogo01.setOnClickListener(null);
        this.mImgLogo01.setVisibility(0);
        this.mImgLogo01.setImageResource(0);
        this.mFragment.displayPicture(this.mImgLogo01, paramNewsInfo.mThumbnail, 2130838784);
        return;
      }
      if (!"1210".equals(paramNewsInfo.mNtype))
        continue;
      this.newsItemImglayout.setVisibility(0);
      this.newsItemImglayout2.setVisibility(8);
      this.mImgLogo01.setVisibility(8);
      this.mImgUpPhoto03.setVisibility(8);
      this.mImgBoxdiary02.setTag(null);
      this.mImgBoxdiary02.setOnClickListener(null);
      this.mImgBoxdiary02.setVisibility(0);
      this.mImgBoxdiary02.setImageResource(0);
      this.mImgBoxdiary02.setType(5);
      this.mImgBoxdiary02.resetState(isSaveFlowState());
      this.mImgBoxdiary02.setInfoDrawable(2130838255);
      this.mFragment.displayPicture(this.mImgBoxdiary02, paramNewsInfo.mThumbnail, 2130838784);
      String str3 = paramNewsInfo.mThumbnail;
      if (isSaveFlowState(this.mImgBoxdiary02))
      {
        this.mImgBoxdiary02.setOnClickListener(new View.OnClickListener(str3)
        {
          public void onClick(View paramView)
          {
            if (NewsItemPhotoView.this.mImgBoxdiary02.saveFlowState())
            {
              NewsItemPhotoView.this.mImgBoxdiary02.startDownloading();
              NewsItemPhotoView.this.mFragment.displayPicture(NewsItemPhotoView.this.mImgBoxdiary02, this.val$picUrl, 0);
              NewsItemPhotoView.this.mImgBoxdiary02.setOnClickListener(null);
            }
          }
        });
        return;
      }
      this.mImgBoxdiary02.setOnClickListener(null);
      return;
    }
    while (!"1".equals(paramNewsInfo.mNtype));
    if ((paramNewsInfo.mPhotoList == null) || (paramNewsInfo.mPhotoList.size() == 1))
    {
      this.newsItemImglayout.setVisibility(0);
      this.newsItemImglayout2.setVisibility(8);
      this.mImgUpPhoto03.setVisibility(0);
      this.mImgLogo01.setVisibility(8);
      this.mImgBoxdiary02.setVisibility(8);
      PhotoItem localPhotoItem1 = (PhotoItem)paramNewsInfo.mPhotoList.get(0);
      KXFrameImageView localKXFrameImageView1 = this.mImgUpPhoto03;
      String[] arrayOfString1 = new String[6];
      arrayOfString1[0] = localPhotoItem1.index;
      arrayOfString1[1] = localPhotoItem1.fuid;
      arrayOfString1[2] = localPhotoItem1.albumTitle;
      arrayOfString1[3] = localPhotoItem1.albumId;
      arrayOfString1[4] = localPhotoItem1.privacy;
      arrayOfString1[5] = localPhotoItem1.visible;
      localKXFrameImageView1.setTag(arrayOfString1);
      if ("2".equals(localPhotoItem1.privacy))
      {
        this.mImgUpPhoto03.setOnClickListener(this.adapter.mImgOnClickListener);
        this.mImgUpPhoto03.setImageResource(2130838692);
        return;
      }
      this.mImgUpPhoto03.setOnClickListener(this.adapter.mImgOnClickListener);
      this.mImgUpPhoto03.setImageResource(0);
      this.mImgUpPhoto03.resetState(isSaveFlowState());
      this.mFragment.displayPicture(this.mImgUpPhoto03, localPhotoItem1.thumbnail, 2130838784);
      String str1 = localPhotoItem1.thumbnail;
      if (isSaveFlowState(this.mImgUpPhoto03))
      {
        this.mImgUpPhoto03.setOnClickListener(new View.OnClickListener(str1)
        {
          public void onClick(View paramView)
          {
            if (NewsItemPhotoView.this.mImgUpPhoto03.saveFlowState())
            {
              NewsItemPhotoView.this.mImgUpPhoto03.startDownloading();
              NewsItemPhotoView.this.mFragment.displayPicture(NewsItemPhotoView.this.mImgUpPhoto03, this.val$picUrl, 0);
              NewsItemPhotoView.this.mImgUpPhoto03.setOnClickListener(NewsItemPhotoView.this.adapter.mImgOnClickListener);
            }
          }
        });
        return;
      }
      this.mImgUpPhoto03.setOnClickListener(this.adapter.mImgOnClickListener);
      return;
    }
    if (paramNewsInfo.mPhotoList.size() > 1)
    {
      this.newsItemImglayout.setVisibility(8);
      this.newsItemImglayout2.setVisibility(0);
    }
    ArrayList localArrayList = paramNewsInfo.mPhotoList;
    if (localArrayList == null);
    KXFrameImageView localKXFrameImageView2;
    PhotoItem localPhotoItem2;
    int j;
    for (int i = 0; ; i = localArrayList.size())
    {
      if (i > 3)
        i = 3;
      localKXFrameImageView2 = null;
      localPhotoItem2 = null;
      j = 0;
      if (j < i)
        break;
      switch (j)
      {
      default:
        return;
      case 1:
        this.mImgPhoto2.setVisibility(8);
        this.mImgPhoto3.setVisibility(8);
        return;
      case 2:
      }
    }
    switch (j)
    {
    default:
      label728: String[] arrayOfString2 = new String[6];
      arrayOfString2[0] = localPhotoItem2.index;
      arrayOfString2[1] = localPhotoItem2.fuid;
      arrayOfString2[2] = localPhotoItem2.albumTitle;
      arrayOfString2[3] = localPhotoItem2.albumId;
      arrayOfString2[4] = localPhotoItem2.privacy;
      arrayOfString2[5] = localPhotoItem2.visible;
      localKXFrameImageView2.setTag(arrayOfString2);
      localKXFrameImageView2.setVisibility(0);
      if (!"2".equals(localPhotoItem2.privacy))
        break;
      localKXFrameImageView2.setOnClickListener(this.adapter.mImgOnClickListener);
      localKXFrameImageView2.setImageResource(2130838692);
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      j++;
      break;
      localKXFrameImageView2 = this.mImgPhoto1;
      localPhotoItem2 = (PhotoItem)localArrayList.get(0);
      break label728;
      localKXFrameImageView2 = this.mImgPhoto2;
      localPhotoItem2 = (PhotoItem)localArrayList.get(1);
      break label728;
      localKXFrameImageView2 = this.mImgPhoto3;
      localPhotoItem2 = (PhotoItem)localArrayList.get(2);
      break label728;
      localKXFrameImageView2.setOnClickListener(this.adapter.mImgOnClickListener);
      localKXFrameImageView2.resetState(isSaveFlowState());
      this.mFragment.displayPicture(localKXFrameImageView2, localPhotoItem2.thumbnail, 2130838784);
      String str2 = localPhotoItem2.thumbnail;
      if (isSaveFlowState(localKXFrameImageView2))
      {
        localKXFrameImageView2.setOnClickListener(new View.OnClickListener(str2)
        {
          public void onClick(View paramView)
          {
            if ((paramView != null) && ((paramView instanceof KXSaveFlowImageView)))
            {
              KXSaveFlowImageView localKXSaveFlowImageView = (KXSaveFlowImageView)paramView;
              if (localKXSaveFlowImageView.saveFlowState())
              {
                localKXSaveFlowImageView.startDownloading();
                NewsItemPhotoView.this.mFragment.displayPicture(localKXSaveFlowImageView, this.val$picUrl, 0);
                localKXSaveFlowImageView.setOnClickListener(NewsItemPhotoView.this.adapter.mImgOnClickListener);
              }
            }
          }
        });
        continue;
      }
      localKXFrameImageView2.setOnClickListener(this.adapter.mImgOnClickListener);
    }
    this.mImgPhoto3.setVisibility(8);
  }

  public void showTitle(NewsInfo paramNewsInfo)
  {
    KXIntroView.OnClickLinkListener localOnClickLinkListener = this.adapter.makeTitleClickListener(paramNewsInfo);
    if (TextUtils.isEmpty(paramNewsInfo.mTitle))
    {
      ArrayList localArrayList2 = this.adapter.getIntro2KXLinkList(paramNewsInfo);
      this.mNewsItemContent.setTitleList(localArrayList2);
      this.mNewsItemContent.setOnClickLinkListener(localOnClickLinkListener);
      this.mNewsItemContent.setVisibility(0);
      return;
    }
    if ("1210".equals(paramNewsInfo.mNtype))
    {
      this.mNewsItemContent.setTitleList(null);
      this.mNewsItemContent.setVisibility(8);
      return;
    }
    if ("4".equals(paramNewsInfo.mNtype))
    {
      this.mNewsItemContent.setTitleList(null);
      this.mNewsItemContent.setVisibility(8);
      return;
    }
    if (TextUtils.isEmpty(paramNewsInfo.mIntro))
    {
      this.mNewsItemContent.setTitleList(null);
      this.mNewsItemContent.setVisibility(8);
      return;
    }
    ArrayList localArrayList1 = this.adapter.getIntro2KXLinkListAddLocation(paramNewsInfo);
    this.mNewsItemContent.setTitleList(localArrayList1);
    this.mNewsItemContent.setOnClickLinkListener(localOnClickLinkListener);
    this.mNewsItemContent.setVisibility(0);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.NewsItemPhotoView
 * JD-Core Version:    0.6.0
 */