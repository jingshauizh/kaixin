package com.kaixin001.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.OnViewMoreClickListener;
import com.kaixin001.businesslogic.AddFriend;
import com.kaixin001.businesslogic.IUseAddFriendBussinessLogic;
import com.kaixin001.businesslogic.ShowCirclePhoto;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.model.CircleNewsModel.CircleNewsItem;
import com.kaixin001.model.CircleNewsModel.CircleNewsTalkContent;
import com.kaixin001.model.CircleNewsModel.KXPicture;
import com.kaixin001.model.KaixinUser;
import com.kaixin001.model.User;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.KXTextUtil;
import com.kaixin001.view.KXIntroView;
import com.kaixin001.view.KXIntroView.OnClickLinkListener;
import java.util.ArrayList;
import java.util.List;

public class CircleNewsAdapter extends ArrayAdapter<CircleNewsModel.CircleNewsItem>
{
  private static final String TAG = "CircleNewsAdapter";
  private String gid;
  private String gname;
  private ShowCirclePhoto mCirclePhotoShowUtil;
  private Activity mContext;
  private View mFooter;
  private ProgressBar mFooterProBar;
  private TextView mFooterTV;
  private BaseFragment mFragment;
  ArrayList<CircleNewsModel.CircleNewsItem> newsList;
  LayoutInflater vi;

  public CircleNewsAdapter(BaseFragment paramBaseFragment, int paramInt, String paramString1, ArrayList<CircleNewsModel.CircleNewsItem> paramArrayList, String paramString2)
  {
    super(paramBaseFragment.getActivity(), paramInt, paramArrayList);
    this.newsList = paramArrayList;
    this.mFragment = paramBaseFragment;
    this.mContext = paramBaseFragment.getActivity();
    this.gid = paramString1;
    this.gname = paramString2;
    this.mFooter = paramBaseFragment.getActivity().getLayoutInflater().inflate(2130903259, null);
    this.mFooter.setOnClickListener(new View.OnClickListener(paramBaseFragment)
    {
      public void onClick(View paramView)
      {
        ((OnViewMoreClickListener)this.val$fragment).onViewMoreClick();
      }
    });
    this.mFooterTV = ((TextView)this.mFooter.findViewById(2131362073));
    this.mFooterTV.setText(2131427748);
    this.mFooterProBar = ((ProgressBar)this.mFooter.findViewById(2131362072));
    this.vi = ((LayoutInflater)this.mContext.getSystemService("layout_inflater"));
  }

  private ShowCirclePhoto getPhotoShowUtil()
  {
    if (this.mCirclePhotoShowUtil == null)
      this.mCirclePhotoShowUtil = new ShowCirclePhoto(this.mContext, this.mFragment);
    return this.mCirclePhotoShowUtil;
  }

  public AddFriend getAddFriendUtil()
  {
    return ((IUseAddFriendBussinessLogic)this.mFragment).getAddFriendUtil();
  }

  public int getCount()
  {
    if (this.newsList == null)
      return 0;
    return this.newsList.size();
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramInt >= this.newsList.size())
      return paramView;
    try
    {
      CircleNewsModel.CircleNewsItem localCircleNewsItem = (CircleNewsModel.CircleNewsItem)this.newsList.get(paramInt);
      if (localCircleNewsItem.suser == null)
        return this.mFooter;
      CircleNewsItemViewTag localCircleNewsItemViewTag;
      if ((paramView == null) || (paramView == this.mFooter))
      {
        paramView = this.vi.inflate(2130903080, null);
        localCircleNewsItemViewTag = new CircleNewsItemViewTag(paramView, null);
        paramView.setTag(localCircleNewsItemViewTag);
      }
      while (true)
      {
        localCircleNewsItemViewTag.updateCircleNews(localCircleNewsItem);
        break;
        localCircleNewsItemViewTag = (CircleNewsItemViewTag)paramView.getTag();
      }
    }
    catch (Exception localException)
    {
      KXLog.e("CircleNewsAdapter", "getView", localException);
    }
    return paramView;
  }

  public boolean isFooterShowLoading()
  {
    if (this.mFooterProBar == null);
    do
      return false;
    while (this.mFooterProBar.getVisibility() != 0);
    return true;
  }

  public void showLoadingFooter(boolean paramBoolean)
  {
    ProgressBar localProgressBar;
    if (this.mFooterProBar != null)
    {
      localProgressBar = this.mFooterProBar;
      if (!paramBoolean)
        break label71;
    }
    label71: for (int j = 0; ; j = 4)
    {
      localProgressBar.setVisibility(j);
      if (this.mFooterTV != null)
      {
        int i = this.mContext.getResources().getColor(2130839419);
        if (paramBoolean)
          i = this.mContext.getResources().getColor(2130839395);
        this.mFooterTV.setTextColor(i);
      }
      return;
    }
  }

  private class CircleNewsItemViewTag
    implements View.OnClickListener
  {
    private TextView btnDoMore;
    private String fname;
    private String fuid;
    private ImageView ivClientIcon = null;
    private ImageView ivImage1;
    public ImageView ivLogo;
    private LinearLayout llytImages;
    private LinearLayout lytLocation;
    private LinearLayout lytReply;
    private TextView tvClentName = null;
    private TextView tvCtime = null;
    private TextView tvLocation = null;
    private TextView tvReply = null;
    public KXIntroView tvTitle;

    private CircleNewsItemViewTag(View arg2)
    {
      Object localObject;
      this.ivLogo = ((ImageView)localObject.findViewById(2131362058));
      this.tvTitle = ((KXIntroView)localObject.findViewById(2131361808));
      this.llytImages = ((LinearLayout)localObject.findViewById(2131362077));
      this.ivImage1 = ((ImageView)localObject.findViewById(2131362078));
      this.lytLocation = ((LinearLayout)localObject.findViewById(2131362082));
      this.tvLocation = ((TextView)localObject.findViewById(2131362084));
      this.tvCtime = ((TextView)localObject.findViewById(2131362085));
      this.ivClientIcon = ((ImageView)localObject.findViewById(2131362086));
      this.tvClentName = ((TextView)localObject.findViewById(2131362045));
      this.lytReply = ((LinearLayout)localObject.findViewById(2131362087));
      this.tvReply = ((TextView)localObject.findViewById(2131362089));
      this.btnDoMore = ((TextView)localObject.findViewById(2131362075));
    }

    private String getCircleTalkTitle(CircleNewsModel.CircleNewsItem paramCircleNewsItem)
    {
      StringBuffer localStringBuffer = new StringBuffer(80);
      if (paramCircleNewsItem.talkType == 2)
      {
        CircleNewsModel.CircleNewsTalkContent localCircleNewsTalkContent2 = (CircleNewsModel.CircleNewsTalkContent)paramCircleNewsItem.content.get(0);
        localStringBuffer.append(KXTextUtil.getUserText(this.fname, this.fuid, false)).append(" ").append(localCircleNewsTalkContent2.txt);
        return localStringBuffer.toString();
      }
      if (paramCircleNewsItem.talkType == 1)
      {
        localStringBuffer.append(KXTextUtil.getUserText(this.fname, this.fuid, false)).append(" ");
        if (paramCircleNewsItem.users == null);
        for (int k = 0; k == 0; k = paramCircleNewsItem.users.size())
          return localStringBuffer.toString();
        localStringBuffer.append("邀请 ");
        for (int m = 0; ; m++)
        {
          if (m >= k);
          while (true)
          {
            localStringBuffer.append("加入圈子 ");
            break;
            KaixinUser localKaixinUser = (KaixinUser)paramCircleNewsItem.users.get(m);
            localStringBuffer.append(KXTextUtil.getUserText(localKaixinUser.realname, localKaixinUser.uid, false));
            if (m != 4)
              break label210;
            localStringBuffer.append("等");
          }
          label210: if (m >= k - 1)
            continue;
          localStringBuffer.append("、");
        }
      }
      localStringBuffer.append(KXTextUtil.getUserText(this.fname, this.fuid, false)).append(": ");
      if (paramCircleNewsItem.content == null);
      for (int i = 0; i == 0; i = paramCircleNewsItem.content.size())
        return localStringBuffer.toString();
      int j = 0;
      label291: CircleNewsModel.CircleNewsTalkContent localCircleNewsTalkContent1;
      if (j < i)
      {
        localCircleNewsTalkContent1 = (CircleNewsModel.CircleNewsTalkContent)paramCircleNewsItem.content.get(j);
        if (localCircleNewsTalkContent1.type != 0)
          break label338;
        localStringBuffer.append(localCircleNewsTalkContent1.txt);
      }
      while (true)
      {
        j++;
        break label291;
        break;
        label338: if (localCircleNewsTalkContent1.type == 1)
        {
          localStringBuffer.append(KXTextUtil.getAtUserText()).append(KXTextUtil.getUserText(localCircleNewsTalkContent1.uname, localCircleNewsTalkContent1.uid, false));
          continue;
        }
        if (localCircleNewsTalkContent1.type == 2)
        {
          localStringBuffer.append(KXTextUtil.getURLLinkText("附链接", localCircleNewsTalkContent1.txt));
          continue;
        }
        if (localCircleNewsTalkContent1.type == 4)
          continue;
      }
    }

    public void onClick(View paramView)
    {
      if (TextUtils.isEmpty(User.getInstance().getOauthToken()))
      {
        Toast.makeText(CircleNewsAdapter.this.mContext, 2131427972, 0).show();
        return;
      }
      if (paramView.getId() == 2131361834)
      {
        IntentUtil.showHomeFragment(CircleNewsAdapter.this.mFragment, this.fuid, this.fname);
        return;
      }
      IntentUtil.showHomeFragment(CircleNewsAdapter.this.mFragment, this.fuid, this.fname);
    }

    public void updateCircleNews(CircleNewsModel.CircleNewsItem paramCircleNewsItem)
      throws Exception
    {
      1 local1 = new KXIntroView.OnClickLinkListener()
      {
        public void onClick(KXLinkInfo paramKXLinkInfo)
        {
          if ((paramKXLinkInfo.isStar()) || (paramKXLinkInfo.isUserName()))
          {
            String str1 = paramKXLinkInfo.getId();
            String str2 = paramKXLinkInfo.getContent();
            IntentUtil.showHomeFragment(CircleNewsAdapter.this.mFragment, str1, str2);
          }
          do
          {
            return;
            if (!paramKXLinkInfo.isUrlLink())
              continue;
            IntentUtil.showWebPage(CircleNewsAdapter.this.mContext, CircleNewsAdapter.this.mFragment, paramKXLinkInfo.getId(), null);
            return;
          }
          while (!paramKXLinkInfo.isTopic());
          IntentUtil.showTopicGroupActivity(CircleNewsAdapter.this.mFragment, paramKXLinkInfo.getId());
        }
      };
      this.btnDoMore.setOnClickListener(new View.OnClickListener(paramCircleNewsItem)
      {
        public void onClick(View paramView)
        {
          IntentUtil.replyCircleTalk(CircleNewsAdapter.this.mContext, CircleNewsAdapter.this.mFragment, CircleNewsAdapter.this.gid, this.val$news.stid);
        }
      });
      CircleNewsAdapter.this.mFragment.displayPicture(this.ivLogo, paramCircleNewsItem.suser.logo, 2130838676);
      this.fuid = paramCircleNewsItem.suser.uid;
      this.fname = paramCircleNewsItem.suser.realname;
      String str1 = getCircleTalkTitle(paramCircleNewsItem);
      this.tvTitle.setOnClickLinkListener(local1);
      this.tvTitle.setTitleList(NewsInfo.makeIntroList(str1));
      if ((paramCircleNewsItem.pic != null) && (!TextUtils.isEmpty(paramCircleNewsItem.pic.smallPic)))
      {
        this.llytImages.setVisibility(0);
        this.ivImage1.setVisibility(0);
        this.ivImage1.setOnClickListener(new View.OnClickListener(paramCircleNewsItem)
        {
          public void onClick(View paramView)
          {
            CircleNewsAdapter.this.getPhotoShowUtil().showPhoto(this.val$news.tid, this.val$news.gid, this.val$news.pic.pid, CircleNewsAdapter.this.gname);
          }
        });
        CircleNewsAdapter.this.mFragment.displayPicture(this.ivImage1, paramCircleNewsItem.pic.smallPic, 2130838784);
        if (!TextUtils.isEmpty(paramCircleNewsItem.location))
          break label265;
        this.lytLocation.setVisibility(8);
        label190: this.tvCtime.setText(KXTextUtil.formatTimestamp(1000L * paramCircleNewsItem.ctime));
        if (!TextUtils.isEmpty(paramCircleNewsItem.source))
          break label295;
        this.ivClientIcon.setVisibility(8);
        this.tvClentName.setVisibility(8);
      }
      while (true)
      {
        if (paramCircleNewsItem.rnum != 0)
          break label325;
        this.lytReply.setVisibility(8);
        return;
        this.llytImages.setVisibility(8);
        break;
        label265: this.lytLocation.setVisibility(0);
        this.tvLocation.setVisibility(0);
        this.tvLocation.setText(paramCircleNewsItem.location);
        break label190;
        label295: this.ivClientIcon.setVisibility(0);
        this.tvClentName.setVisibility(0);
        this.tvClentName.setText(paramCircleNewsItem.source);
      }
      label325: this.lytReply.setVisibility(0);
      TextView localTextView = this.tvReply;
      String str2 = CircleNewsAdapter.this.mContext.getString(2131428084);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramCircleNewsItem.rnum);
      localTextView.setText(String.format(str2, arrayOfObject));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.adapter.CircleNewsAdapter
 * JD-Core Version:    0.6.0
 */