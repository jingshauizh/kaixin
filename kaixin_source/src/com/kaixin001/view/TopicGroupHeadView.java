package com.kaixin001.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kaixin001.model.TopicModel;

public class TopicGroupHeadView extends LinearLayout
{
  public static int CHANGE_IS_MOR = -666;
  private View headView;
  private boolean isMore = false;
  private Handler mHandler;
  private TextView topicDetail;
  private TextView topicDetailAll;
  private TopicModel topicModel = TopicModel.getInstance();
  private ImageView topicMore;
  private TextView topicTitle;

  public TopicGroupHeadView(Context paramContext)
  {
    super(paramContext);
    LayoutInflater.from(paramContext).inflate(2130903346, this, true);
    this.topicTitle = ((TextView)findViewById(2131363723));
    this.topicDetail = ((TextView)findViewById(2131363725));
    this.topicDetailAll = ((TextView)findViewById(2131363726));
    this.topicMore = ((ImageView)findViewById(2131363727));
    this.mHandler = new Handler()
    {
      public void handleMessage(Message paramMessage)
      {
        if (paramMessage.what == TopicGroupHeadView.CHANGE_IS_MOR)
        {
          if (TopicGroupHeadView.this.isMore)
            break label71;
          if (TopicGroupHeadView.this.topicDetail.getLineCount() >= 3)
          {
            TopicGroupHeadView.this.topicMore.setVisibility(0);
            TopicGroupHeadView.this.topicMore.setBackgroundResource(2130839073);
          }
        }
        else
        {
          return;
        }
        TopicGroupHeadView.this.topicMore.setVisibility(8);
        return;
        label71: if (TopicGroupHeadView.this.topicDetailAll.getLineCount() > 3)
        {
          TopicGroupHeadView.this.topicMore.setVisibility(0);
          TopicGroupHeadView.this.topicMore.setBackgroundResource(2130839072);
          return;
        }
        TopicGroupHeadView.this.topicMore.setVisibility(8);
      }
    };
    setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        TopicGroupHeadView localTopicGroupHeadView;
        boolean bool2;
        if (TopicGroupHeadView.this.isMore)
        {
          TopicGroupHeadView.this.topicDetailAll.setVisibility(8);
          TopicGroupHeadView.this.topicDetail.setVisibility(0);
          Message localMessage2 = new Message();
          localMessage2.what = TopicGroupHeadView.CHANGE_IS_MOR;
          TopicGroupHeadView.this.mHandler.sendMessage(localMessage2);
          localTopicGroupHeadView = TopicGroupHeadView.this;
          boolean bool1 = TopicGroupHeadView.this.isMore;
          bool2 = false;
          if (!bool1)
            break label147;
        }
        while (true)
        {
          localTopicGroupHeadView.isMore = bool2;
          return;
          TopicGroupHeadView.this.topicDetailAll.setVisibility(0);
          TopicGroupHeadView.this.topicDetail.setVisibility(8);
          Message localMessage1 = new Message();
          localMessage1.what = TopicGroupHeadView.CHANGE_IS_MOR;
          TopicGroupHeadView.this.mHandler.sendMessage(localMessage1);
          break;
          label147: bool2 = true;
        }
      }
    });
    fillHeadView();
    Message localMessage = new Message();
    localMessage.what = CHANGE_IS_MOR;
    this.mHandler.sendMessage(localMessage);
  }

  public void fillHeadView()
  {
    if (!TextUtils.isEmpty(this.topicModel.title))
      this.topicTitle.setText(this.topicModel.title);
    while (true)
    {
      this.topicDetail.setText(this.topicModel.detail);
      this.topicDetailAll.setText(this.topicModel.detail);
      if (this.isMore)
        break;
      this.topicDetailAll.setVisibility(8);
      this.topicDetail.setVisibility(0);
      return;
      this.topicTitle.setVisibility(8);
    }
    this.topicDetailAll.setVisibility(0);
    this.topicDetail.setVisibility(8);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.TopicGroupHeadView
 * JD-Core Version:    0.6.0
 */