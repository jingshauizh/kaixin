package com.kaixin001.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.kaixin001.adapter.NewsItemAdapter;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.fragment.NewsFragment;
import com.kaixin001.item.NewsInfo;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import org.json.JSONObject;

public class GDTAdView extends LinearLayout
{
  private TextView appNameTextView;
  private ImageView contentImageView;
  private TextView contentTextView;
  private Button downloadButton;
  private BaseFragment fragment;
  private ImageView iconImageView;
  private NewsInfo info;
  private RatingBar scoreBar;
  private TextView sizeTextView;

  public GDTAdView(Context paramContext, BaseFragment paramBaseFragment, NewsInfo paramNewsInfo, NewsItemAdapter paramNewsItemAdapter)
  {
    super(paramContext);
    this.info = paramNewsInfo;
    this.fragment = paramBaseFragment;
    View localView = LayoutInflater.from(paramContext).inflate(2130903137, null);
    addView(localView);
    this.iconImageView = ((ImageView)localView.findViewById(2131362441));
    this.appNameTextView = ((TextView)localView.findViewById(2131362442));
    this.contentTextView = ((TextView)localView.findViewById(2131362443));
    this.contentImageView = ((ImageView)localView.findViewById(2131362444));
    this.scoreBar = ((RatingBar)localView.findViewById(2131362445));
    this.sizeTextView = ((TextView)localView.findViewById(2131362446));
    this.downloadButton = ((Button)localView.findViewById(2131362447));
    if (paramNewsInfo != null)
      init();
  }

  private void init()
  {
    if (this.info.btn_render != 2)
    {
      this.scoreBar.setVisibility(8);
      this.sizeTextView.setVisibility(8);
      this.downloadButton.setVisibility(8);
    }
    while (true)
    {
      String str1 = this.info.ext.optString("iconurl");
      if (!TextUtils.isEmpty(str1))
        this.fragment.displayRoundPicture(this.iconImageView, str1, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
      String str2 = this.info.ext.optString("appname");
      if (!TextUtils.isEmpty(str2))
        this.appNameTextView.setText(str2);
      String str3 = this.info.txt;
      if (!TextUtils.isEmpty(str3))
        this.contentTextView.setText(str3);
      String str4 = this.info.img;
      if (!TextUtils.isEmpty(str4))
        this.fragment.displayPicture(this.contentImageView, str4, 2130838784);
      int i = this.info.ext.optInt("appscore");
      this.scoreBar.setRating(i / 2.0F);
      int j = this.info.ext.optInt("num_app_users");
      this.sizeTextView.setText(j + "人在玩");
      this.downloadButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if ((GDTAdView.this.fragment instanceof NewsFragment))
            ((NewsFragment)GDTAdView.this.fragment).downloadGDT(GDTAdView.this.info);
        }
      });
      setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (GDTAdView.this.info.btn_render == 2);
          do
            return;
          while (!(GDTAdView.this.fragment instanceof NewsFragment));
          ((NewsFragment)GDTAdView.this.fragment).downloadGDT(GDTAdView.this.info);
        }
      });
      return;
      this.scoreBar.setVisibility(0);
      this.sizeTextView.setVisibility(0);
      this.downloadButton.setVisibility(0);
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    NewsFragment localNewsFragment = (NewsFragment)this.fragment;
    switch (paramMotionEvent.getAction())
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      return super.onTouchEvent(paramMotionEvent);
      localNewsFragment.down_x = (int)paramMotionEvent.getRawX();
      localNewsFragment.down_y = (int)paramMotionEvent.getRawY();
      continue;
      localNewsFragment.up_x = (int)paramMotionEvent.getRawX();
      localNewsFragment.up_y = (int)paramMotionEvent.getRawY();
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.GDTAdView
 * JD-Core Version:    0.6.0
 */