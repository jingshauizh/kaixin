package com.kaixin001.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.businesslogic.ShowPhoto;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.NewsDetailPictureFragment;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.item.PhotoItem;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.view.media.KXMediaView;
import java.util.ArrayList;
import java.util.Iterator;

public class NewsItemRecordView extends NewsItemBaseView
{
  ShowPhoto mAlbumShowUtil = null;
  private KXIntroView mContent;
  private LinearLayout mImgList;
  private KXIntroView.OnClickLinkListener mIntroViewListener = null;
  private KXMediaView mMediaView = null;
  private KXIntroView mSubContent;
  private LinearLayout mSubImgList;
  private View mSubLayout;
  private KXMediaView mSubMediaView = null;
  private KXIntroView mSubName;
  private KXIntroView mTitle;

  public NewsItemRecordView(BaseFragment paramBaseFragment, NewsInfo paramNewsInfo, NewsItemAdapter paramNewsItemAdapter)
  {
    super(paramBaseFragment, paramNewsInfo, paramNewsItemAdapter);
    this.mIntroViewListener = paramNewsItemAdapter.makeTitleClickListener(paramNewsInfo);
    this.rootView = this.inflater.inflate(2130903253, this);
    this.mTitle = ((KXIntroView)this.rootView.findViewById(2131363225));
    this.mContent = ((KXIntroView)this.rootView.findViewById(2131362595));
    this.mContent.setOnClickLinkListener(this.mIntroViewListener);
    this.mImgList = ((LinearLayout)this.rootView.findViewById(2131363227));
    this.mSubLayout = this.rootView.findViewById(2131363229);
    this.mSubName = ((KXIntroView)this.rootView.findViewById(2131363230));
    this.mSubName.setOnClickLinkListener(this.mIntroViewListener);
    this.mSubContent = ((KXIntroView)this.rootView.findViewById(2131363231));
    this.mSubContent.setOnClickLinkListener(this.mIntroViewListener);
    this.mSubImgList = ((LinearLayout)this.rootView.findViewById(2131363233));
    this.mMediaView = ((KXMediaView)this.rootView.findViewById(2131363180));
    this.mSubMediaView = ((KXMediaView)this.rootView.findViewById(2131363181));
  }

  private void actionViewBigPic(String paramString1, String paramString2)
  {
    Intent localIntent = new Intent(this.mContext, NewsDetailPictureFragment.class);
    localIntent.putExtra("small", paramString1);
    localIntent.putExtra("large", paramString2);
    localIntent.putExtra("action", "com.kaixin001.VIEW_SINGLE_PHOTO");
    AnimationUtil.startFragment(this.mFragment, localIntent, 5);
  }

  private void addImgToList(LinearLayout paramLinearLayout, String paramString, boolean paramBoolean, View.OnClickListener paramOnClickListener, Object paramObject)
  {
    int i = paramLinearLayout.getChildCount();
    KXFrameImageView localKXFrameImageView;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      localKXFrameImageView = (KXFrameImageView)paramLinearLayout.getChildAt(j);
      if ((localKXFrameImageView != null) && (localKXFrameImageView.getVisibility() != 0))
        break;
    }
    if (paramLinearLayout.getVisibility() == 8)
    {
      paramLinearLayout.setVisibility(0);
      if (paramLinearLayout == this.mSubImgList)
        this.rootView.findViewById(2131363234).setVisibility(0);
    }
    else
    {
      localKXFrameImageView.setTag(paramObject);
      localKXFrameImageView.setOnClickListener(paramOnClickListener);
      localKXFrameImageView.setVisibility(0);
      if (!paramBoolean)
        break label182;
      localKXFrameImageView.setTag("BG");
      this.mFragment.displayPicture(localKXFrameImageView, paramString, 2130838784);
      localKXFrameImageView.setImageResource(2130838791);
      localKXFrameImageView.setScaleType(ImageView.ScaleType.CENTER);
    }
    while (true)
    {
      if (!isSaveFlowState(localKXFrameImageView))
        break label211;
      localKXFrameImageView.setOnClickListener(new View.OnClickListener(paramString, paramOnClickListener)
      {
        public void onClick(View paramView)
        {
          if ((paramView != null) && ((paramView instanceof KXSaveFlowImageView)))
          {
            KXSaveFlowImageView localKXSaveFlowImageView = (KXSaveFlowImageView)paramView;
            if (localKXSaveFlowImageView.saveFlowState())
            {
              localKXSaveFlowImageView.startDownloading();
              NewsItemRecordView.this.mFragment.displayPicture(localKXSaveFlowImageView, this.val$imgUrl, 0);
              localKXSaveFlowImageView.setOnClickListener(this.val$listener);
            }
          }
        }
      });
      return;
      this.rootView.findViewById(2131363228).setVisibility(0);
      break;
      label182: localKXFrameImageView.setImageDrawable(null);
      localKXFrameImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      this.mFragment.displayPicture(localKXFrameImageView, paramString, 2130838784);
    }
    label211: localKXFrameImageView.setOnClickListener(paramOnClickListener);
  }

  private void clearListImg(LinearLayout paramLinearLayout)
  {
    int i = paramLinearLayout.getChildCount();
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        paramLinearLayout.setVisibility(8);
        if (paramLinearLayout != this.mSubImgList)
          break;
        this.rootView.findViewById(2131363234).setVisibility(8);
        return;
      }
      View localView = paramLinearLayout.getChildAt(j);
      localView.setVisibility(8);
      if (!(localView instanceof KXSaveFlowImageView))
        continue;
      ((KXSaveFlowImageView)localView).resetState(isSaveFlowState());
    }
    this.rootView.findViewById(2131363228).setVisibility(8);
  }

  private void displayMedia(NewsInfo paramNewsInfo)
  {
    if (paramNewsInfo.mMediaInfo != null)
    {
      this.mMediaView.setVisibility(0);
      this.mMediaView.setMediaData(paramNewsInfo.mMediaInfo);
    }
    while (paramNewsInfo.mSubMediaInfo != null)
    {
      this.mSubMediaView.setVisibility(0);
      this.mSubMediaView.setMediaData(paramNewsInfo.mSubMediaInfo);
      return;
      this.mMediaView.setVisibility(8);
    }
    this.mSubMediaView.setVisibility(8);
  }

  private String getPic(NewsInfo paramNewsInfo, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramNewsInfo.mRecordImages != null) && (paramNewsInfo.mRecordImages.length == 2))
      if (TextUtils.isEmpty(paramNewsInfo.mOrigRecordId))
      {
        if ((!paramBoolean1) && (!paramBoolean2))
          return paramNewsInfo.mRecordImages[1];
        if ((!paramBoolean1) && (paramBoolean2))
          return paramNewsInfo.mRecordImages[0];
      }
      else
      {
        if ((paramBoolean1) && (!paramBoolean2))
          return paramNewsInfo.mRecordImages[1];
        if ((paramBoolean1) && (paramBoolean2))
          return paramNewsInfo.mRecordImages[0];
      }
    return null;
  }

  private void setText(KXIntroView paramKXIntroView, String paramString)
  {
    setText(paramKXIntroView, paramString, true);
  }

  private void setText(KXIntroView paramKXIntroView, String paramString, boolean paramBoolean)
  {
    if (TextUtils.isEmpty(paramString))
    {
      paramKXIntroView.setVisibility(8);
      return;
    }
    ArrayList localArrayList = NewsInfo.makeIntroList(paramString);
    Iterator localIterator;
    if ((!paramBoolean) && (localArrayList != null))
      localIterator = localArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        paramKXIntroView.setTitleList(localArrayList);
        return;
      }
      ((KXLinkInfo)localIterator.next()).setType("-100");
    }
  }

  private void showContent(NewsInfo paramNewsInfo)
  {
    String str1 = "";
    if ("2".equals(paramNewsInfo.mNtype))
    {
      showTopLayout(paramNewsInfo, "发表日记");
      this.mTitle.setVisibility(0);
      setText(this.mTitle, paramNewsInfo.mIntro, false);
      this.mContent.setTextSize(13.0F);
      this.mContent.setTextColor(this.mContext.getResources().getColor(2130839397));
      if (!TextUtils.isEmpty(paramNewsInfo.mContent))
        str1 = paramNewsInfo.mContent.trim();
      setText(this.mContent, str1);
      this.mContent.setLbsEllipse(true);
      this.mContent.setIsLbs("1192".equals(paramNewsInfo.mNtype));
      clearListImg(this.mImgList);
      clearListImg(this.mSubImgList);
      String str2 = getPic(paramNewsInfo, false, false);
      String str3 = getPic(paramNewsInfo, false, false);
      if (!TextUtils.isEmpty(str2))
        addImgToList(this.mImgList, str2, false, new View.OnClickListener(str2, str3)
        {
          public void onClick(View paramView)
          {
            NewsItemRecordView.this.actionViewBigPic(this.val$smallPicUrl, this.val$bigPicUrl);
          }
        }
        , null);
      if (!TextUtils.isEmpty(paramNewsInfo.mOrigRecordIntro))
        break label407;
    }
    label407: for (int i = 0; ; i = 1)
    {
      if ("1242".equals(paramNewsInfo.mNtype))
        i = 1;
      LinearLayout localLinearLayout = this.mImgList;
      if (i != 0)
        localLinearLayout = this.mSubImgList;
      if (("1018".equals(paramNewsInfo.mNtype)) && (paramNewsInfo.mVideoSnapshotLIst != null) && (paramNewsInfo.mVideoSnapshotLIst.size() > 0))
        addImgToList(localLinearLayout, (String)paramNewsInfo.mVideoSnapshotLIst.get(0), true, new View.OnClickListener(paramNewsInfo)
        {
          public void onClick(View paramView)
          {
            if (this.val$info.mOrigRecordIntro != null);
            for (ArrayList localArrayList = NewsInfo.makeIntroList(this.val$info.mOrigRecordIntro); localArrayList == null; localArrayList = NewsInfo.makeIntroList(this.val$info.mIntro))
            {
              label25: return;
              break label50;
            }
            for (int i = 0; ; i++)
            {
              if (i >= localArrayList.size())
              {
                label50: if (i >= localArrayList.size())
                  break label25;
                IntentUtil.showVideoPage(NewsItemRecordView.this.mContext, ((KXLinkInfo)localArrayList.get(i)).getId(), ((KXLinkInfo)localArrayList.get(i)).getType(), ((KXLinkInfo)localArrayList.get(i)).getContent());
                return;
              }
              if (((KXLinkInfo)localArrayList.get(i)).isVideo())
                break;
            }
          }
        }
        , null);
      if (i != 0)
        break label413;
      this.mSubLayout.setVisibility(8);
      displayMedia(paramNewsInfo);
      showRepost3Item(paramNewsInfo);
      return;
      this.mTitle.setVisibility(8);
      this.mContent.setTextSize(15.0F);
      this.mContent.setTextColor(this.mContext.getResources().getColor(2130839398));
      str1 = paramNewsInfo.mIntro;
      if (TextUtils.isEmpty(paramNewsInfo.mLocation))
        break;
      str1 = str1 + paramNewsInfo.mLocation.replace("[|m|]-110[|e|]", "[|m|]-102[|e|]");
      break;
    }
    label413: this.mSubLayout.setVisibility(0);
    if ("1242".equals(paramNewsInfo.mNtype))
    {
      this.mSubName.setVisibility(8);
      setText(this.mSubContent, paramNewsInfo.mSubTitle);
      this.mSubContent.setLbsEllipse(true);
      this.mSubContent.setOnClickLinkListener(new KXIntroView.OnClickLinkListener(paramNewsInfo)
      {
        public void onClick(KXLinkInfo paramKXLinkInfo)
        {
          if ((paramKXLinkInfo.isStar()) || (paramKXLinkInfo.isUserName()))
          {
            String str1 = paramKXLinkInfo.getId();
            String str2 = paramKXLinkInfo.getContent();
            IntentUtil.showHomeFragment(NewsItemRecordView.this.mFragment, str1, str2);
          }
          do
          {
            return;
            if (paramKXLinkInfo.isUrlLink())
            {
              IntentUtil.showWebPage(NewsItemRecordView.this.mContext, NewsItemRecordView.this.mFragment, paramKXLinkInfo.getId(), null);
              return;
            }
            if (paramKXLinkInfo.isLbsPoi())
            {
              IntentUtil.showLbsPositionDetailFragment(NewsItemRecordView.this.mFragment, paramKXLinkInfo.getId(), paramKXLinkInfo.getContent(), "", "");
              return;
            }
            if (paramKXLinkInfo.isVideo())
            {
              IntentUtil.showVideoPage(NewsItemRecordView.this.mContext, paramKXLinkInfo.getId(), paramKXLinkInfo.getType(), paramKXLinkInfo.getContent());
              return;
            }
            if (!paramKXLinkInfo.isRepostAlbumOrVote())
              continue;
            NewsItemRecordView.this.adapter.showRepost3Item(this.val$info);
            return;
          }
          while (!paramKXLinkInfo.isTopic());
          IntentUtil.showTopicGroupActivity(NewsItemRecordView.this.mFragment, paramKXLinkInfo.getId());
        }
      });
    }
    while (true)
    {
      String str6 = getPic(paramNewsInfo, true, false);
      String str7 = getPic(paramNewsInfo, true, false);
      if (TextUtils.isEmpty(str6))
        break;
      addImgToList(this.mSubImgList, str6, false, new View.OnClickListener(str6, str7)
      {
        public void onClick(View paramView)
        {
          NewsItemRecordView.this.actionViewBigPic(this.val$smallPicUrl1, this.val$bigPicUrl1);
        }
      }
      , null);
      break;
      String str4 = paramNewsInfo.mOrigRecordIntro;
      String str5 = paramNewsInfo.mOrigRecordTitle;
      if (!TextUtils.isEmpty(paramNewsInfo.mOrigRecordLocation))
        str4 = str4 + " " + paramNewsInfo.mOrigRecordLocation;
      this.mSubName.setVisibility(0);
      setText(this.mSubName, str5);
      setText(this.mSubContent, str4);
      this.mSubContent.setLbsEllipse(true);
      this.mSubContent.setOnClickLinkListener(this.mIntroViewListener);
    }
  }

  private void showRepost3Item(NewsInfo paramNewsInfo)
  {
    ArrayList localArrayList;
    int i;
    if ("1242".equals(paramNewsInfo.mNtype))
    {
      if ((paramNewsInfo.mPhotoList != null) && (paramNewsInfo.mPhotoList.size() > 0))
      {
        PhotoItem localPhotoItem2 = (PhotoItem)paramNewsInfo.mPhotoList.get(0);
        if ((localPhotoItem2 != null) && (!TextUtils.isEmpty(localPhotoItem2.albumTitle)))
          setText(this.mSubContent, paramNewsInfo.mSubTitle + "\n" + localPhotoItem2.albumTitle);
      }
      localArrayList = paramNewsInfo.mPhotoList;
      if (localArrayList != null)
        break label126;
      i = 0;
      if (i > 3)
        i = 3;
    }
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        return;
        label126: i = localArrayList.size();
        break;
      }
      PhotoItem localPhotoItem1 = (PhotoItem)localArrayList.get(j);
      String str = localPhotoItem1.privacy;
      String[] arrayOfString = new String[6];
      arrayOfString[0] = localPhotoItem1.index;
      arrayOfString[1] = localPhotoItem1.fuid;
      arrayOfString[2] = localPhotoItem1.albumTitle;
      arrayOfString[3] = localPhotoItem1.albumId;
      arrayOfString[4] = str;
      arrayOfString[5] = localPhotoItem1.visible;
      addImgToList(this.mSubImgList, localPhotoItem1.thumbnail, false, this.adapter.mImgOnClickListener, arrayOfString);
    }
  }

  public boolean show(NewsInfo paramNewsInfo)
  {
    this.rootView.setTag(paramNewsInfo);
    super.show(paramNewsInfo);
    showContent(paramNewsInfo);
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.NewsItemRecordView
 * JD-Core Version:    0.6.0
 */