package com.kaixin001.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.businesslogic.ThirdPartyAppUtil;
import com.kaixin001.model.ThirdGameInfo;
import com.kaixin001.model.ThirdGameInfo.ThirdGameModel;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.view.KXFrameImageView;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameDetailFragment extends BaseFragment
  implements View.OnClickListener
{
  private Button downloadBtn;
  private boolean isOpenedAll = false;
  private ImageView leftButton;
  private RelativeLayout mDownloadIcon;
  private TextView mIntro;
  private KXFrameImageView mLogo;
  private TextView mName;
  private ArrayList<String> mPagerUrlList = new ArrayList();
  private TextView mSize;
  private ThirdGameInfo.ThirdGameModel model;
  private TextView openBtn;
  private ImageView openIcon;
  private View openLayout;
  private KXFrameImageView photo0;
  private KXFrameImageView photo1;
  private KXFrameImageView photo2;
  private KXFrameImageView photo3;
  private TextView tvAuthor;
  private TextView tvDescription;
  private TextView tvDownloadCount;
  private TextView tvLanguage;
  private TextView tvUpdateTime;
  private TextView tvVersion;

  private void initBottomLayout()
  {
    this.tvVersion = ((TextView)findViewById(2131362427));
    this.tvVersion.setText(this.tvVersion.getText() + this.model.versionName);
    this.tvLanguage = ((TextView)findViewById(2131362428));
    this.tvLanguage.setText(this.tvLanguage.getText() + this.model.language);
    this.tvDownloadCount = ((TextView)findViewById(2131362429));
    this.tvDownloadCount.setText(this.tvDownloadCount.getText() + this.model.downloadTimes);
    this.tvUpdateTime = ((TextView)findViewById(2131362430));
    this.tvUpdateTime.setText(this.tvUpdateTime.getText() + this.model.updateTime);
    this.tvAuthor = ((TextView)findViewById(2131362431));
    this.tvAuthor.setText(this.tvAuthor.getText() + this.model.developer);
    this.downloadBtn = ((Button)findViewById(2131362404));
    this.downloadBtn.setOnClickListener(this);
  }

  private void initData()
  {
    Bundle localBundle = getArguments();
    int k;
    if ((localBundle != null) && (localBundle.containsKey("index")))
    {
      k = localBundle.getInt("index", -1);
      if (k == -1);
    }
    while (true)
    {
      String[] arrayOfString;
      int j;
      try
      {
        this.model = ((ThirdGameInfo.ThirdGameModel)ThirdGameInfo.getInstance().getThirdGameList().get(k));
        if (this.model != null)
          continue;
        ThirdGameInfo localThirdGameInfo = ThirdGameInfo.getInstance();
        localThirdGameInfo.getClass();
        this.model = new ThirdGameInfo.ThirdGameModel(localThirdGameInfo);
        arrayOfString = this.model.screenshotsUrl.split(",");
        if (arrayOfString.length <= 0)
          continue;
        int i = arrayOfString.length;
        j = 0;
        if (j >= i)
          return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        Toast.makeText(getActivity(), "游戏数据为空", 1).show();
        return;
      }
      String str = arrayOfString[j];
      this.mPagerUrlList.add(str);
      j++;
    }
  }

  private void initGallery()
  {
    this.photo0 = ((KXFrameImageView)findViewById(2131362416));
    this.photo1 = ((KXFrameImageView)findViewById(2131362417));
    this.photo2 = ((KXFrameImageView)findViewById(2131362418));
    this.photo3 = ((KXFrameImageView)findViewById(2131362419));
    int i;
    if (this.mPagerUrlList.size() > 0)
    {
      i = 0;
      if (i < this.mPagerUrlList.size());
    }
    else
    {
      this.tvDescription = ((TextView)findViewById(2131362421));
      this.tvDescription.setText(this.model.description);
      this.openLayout = findViewById(2131362422);
      this.openIcon = ((ImageView)findViewById(2131362423));
      this.openBtn = ((TextView)findViewById(2131362424));
      this.openLayout.setOnClickListener(this);
      return;
    }
    switch (i)
    {
    default:
    case 0:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      i++;
      break;
      this.photo0.setVisibility(0);
      displayPicture(this.photo0, (String)this.mPagerUrlList.get(i), -1);
      continue;
      this.photo1.setVisibility(0);
      displayPicture(this.photo1, (String)this.mPagerUrlList.get(i), -1);
      continue;
      this.photo2.setVisibility(0);
      displayPicture(this.photo2, (String)this.mPagerUrlList.get(i), -1);
      continue;
      this.photo3.setVisibility(0);
      displayPicture(this.photo3, (String)this.mPagerUrlList.get(i), -1);
    }
  }

  private void initTitleBar()
  {
    this.leftButton = ((ImageView)findViewById(2131362914));
    this.leftButton.setVisibility(0);
    this.leftButton.setOnClickListener(this);
    findViewById(2131362928).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    if (!TextUtils.isEmpty(this.model.name))
      localTextView.setText(this.model.name);
  }

  private void initTopLayout()
  {
    this.mLogo = ((KXFrameImageView)findViewById(2131362407));
    this.mLogo.setFrameNinePatchResId(2130838099);
    displayRoundPicture(this.mLogo, this.model.iconUrl, ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
    this.mDownloadIcon = ((RelativeLayout)findViewById(2131362408));
    this.mDownloadIcon.setOnClickListener(this);
    this.mName = ((TextView)findViewById(2131362410));
    this.mName.setText(this.model.name);
    this.mSize = ((TextView)findViewById(2131362411));
    this.mSize.setText(this.model.apkSize);
    this.mIntro = ((TextView)findViewById(2131362412));
    this.mIntro.setText(this.model.downloadTimes + "次下载");
  }

  public static GameDetailFragment newInstance(int paramInt)
  {
    GameDetailFragment localGameDetailFragment = new GameDetailFragment();
    Bundle localBundle = new Bundle();
    localBundle.putInt("index", paramInt);
    localGameDetailFragment.setArguments(localBundle);
    return localGameDetailFragment;
  }

  public void onClick(View paramView)
  {
    if (paramView == this.leftButton)
      finish();
    do
    {
      return;
      if ((paramView != this.downloadBtn) && (paramView != this.mDownloadIcon))
        continue;
      String str = this.model.downloadUrl;
      if (Patterns.WEB_URL.matcher(str).matches())
      {
        Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        if (ThirdPartyAppUtil.isApkExist(getActivity(), "com.android.browser"))
          localIntent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        startActivity(localIntent);
        return;
      }
      Toast.makeText(getActivity(), "不是有效的地址", 1000).show();
      return;
    }
    while (paramView != this.openLayout);
    if (!this.isOpenedAll)
    {
      this.tvDescription.setMaxLines(2147483647);
      this.tvDescription.postInvalidate();
      this.isOpenedAll = true;
      this.openBtn.setText("收起");
      this.openIcon.setImageResource(2130838026);
      return;
    }
    this.tvDescription.setMaxLines(2);
    this.tvDescription.postInvalidate();
    this.isOpenedAll = false;
    this.openBtn.setText("展开");
    this.openIcon.setImageResource(2130838027);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    initData();
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903134, null, false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initTitleBar();
    initTopLayout();
    initGallery();
    initBottomLayout();
  }

  public void requestFinish()
  {
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.GameDetailFragment
 * JD-Core Version:    0.6.0
 */