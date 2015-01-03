package com.kaixin001.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaixin001.model.VoteModel;

public class VoteSummaryFragment extends BaseFragment
{
  private VoteModel mModel = VoteModel.getInstance();
  private String mVoteSummary = "";

  private void updateData()
  {
    this.mVoteSummary = this.mModel.getSummary();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903417, paramViewGroup, false);
  }

  public void onDestroy()
  {
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    setTitleBar();
    updateData();
    ((TextView)findViewById(2131364031)).setText(this.mVoteSummary);
  }

  protected void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        VoteSummaryFragment.this.finish();
      }
    });
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setText(getResources().getString(2131427632));
    localTextView.setVisibility(0);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.VoteSummaryFragment
 * JD-Core Version:    0.6.0
 */