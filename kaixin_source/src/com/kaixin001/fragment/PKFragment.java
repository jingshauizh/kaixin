package com.kaixin001.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaixin001.activity.PKVotedActivity;
import com.kaixin001.engine.KXPKEngine;
import com.kaixin001.engine.UploadTaskListEngine;
import com.kaixin001.item.PKInfoItem;
import com.kaixin001.item.PKRecordListItem;
import com.kaixin001.item.RecordUploadTask;
import com.kaixin001.model.PKModel;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.view.KXFrameImageView;
import com.kaixin001.view.KXListView;
import java.util.ArrayList;
import java.util.List;

public class PKFragment extends BaseFragment
  implements View.OnClickListener
{
  public static final int PK_TYPE_BLUE = 1;
  public static final int PK_TYPE_RED;
  private PKAdapter adapter;
  private TextView blueContentText;
  private TextView blueNumberText;
  private KXListView commentListView;
  private PKTask pkTask;
  private String pkid;
  private List<PKRecordListItem> recordsList;
  private TextView redContentText;
  private TextView redNumberText;
  private Button supportBlueButton;
  private Button supportRedButton;
  private TextView titleTextView;
  private ImageView voteAtImage;
  private EditText voteContentEdit;
  private Button voteSendBtn;
  private TextView voteTipText;
  private View voteView;

  private void initData()
  {
    this.pkid = PKModel.getInstance().getPkid();
    this.recordsList = new ArrayList();
    this.adapter = new PKAdapter();
    this.commentListView.setAdapter(this.adapter);
    if (this.pkTask == null)
      this.pkTask = new PKTask();
    this.pkTask.execute(new Void[0]);
  }

  private void initView()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    localImageView.setOnClickListener(this);
    TextView localTextView = (TextView)findViewById(2131362920);
    localTextView.setVisibility(0);
    localTextView.setText(2131428689);
    this.commentListView = ((KXListView)findViewById(2131363422));
    View localView = LayoutInflater.from(getActivity()).inflate(2130903288, null);
    this.titleTextView = ((TextView)localView.findViewById(2131363424));
    this.supportRedButton = ((Button)localView.findViewById(2131363430));
    this.supportBlueButton = ((Button)localView.findViewById(2131363435));
    this.redContentText = ((TextView)localView.findViewById(2131363428));
    this.blueContentText = ((TextView)localView.findViewById(2131363433));
    this.redNumberText = ((TextView)localView.findViewById(2131363431));
    this.blueNumberText = ((TextView)localView.findViewById(2131363436));
    this.commentListView.addHeaderView(localView);
    this.supportRedButton.setOnClickListener(this);
    this.supportBlueButton.setOnClickListener(this);
  }

  private void initVoteView()
  {
    this.voteView = findViewById(2131363423);
    View localView = findViewById(2131361820);
    this.voteTipText = ((TextView)findViewById(2131361822));
    this.voteContentEdit = ((EditText)findViewById(2131361823));
    this.voteAtImage = ((ImageView)findViewById(2131361824));
    this.voteSendBtn = ((Button)findViewById(2131361825));
    this.voteAtImage.setOnClickListener(this);
    this.voteSendBtn.setOnClickListener(this);
    localView.setOnClickListener(this);
    this.voteView.setOnClickListener(this);
    this.voteView.setVisibility(8);
  }

  private void sendVoteContent()
  {
    String str = String.valueOf(this.voteContentEdit.getText()).trim();
    RecordUploadTask localRecordUploadTask = new RecordUploadTask(getActivity().getApplicationContext());
    localRecordUploadTask.initRecordUploadTask("0", str, null, "2", "", "", "", null, 1);
    UploadTaskListEngine.getInstance().addUploadTask(localRecordUploadTask);
    this.voteView.setVisibility(8);
    hideKeyboard();
  }

  private void voteWithType(int paramInt)
  {
    new VoteTask(paramInt).execute(new Void[0]);
  }

  private void writeAtSomeone()
  {
    Intent localIntent = new Intent(getActivity(), FriendsFragment.class);
    Bundle localBundle = new Bundle();
    localBundle.putInt("MODE", 2);
    localIntent.putExtras(localBundle);
    AnimationUtil.startFragmentForResult(this, localIntent, 4, 1);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt2 == -1) && (paramInt1 == 4))
    {
      paramIntent.getStringExtra("fuid");
      String str = paramIntent.getStringExtra("fname");
      this.voteContentEdit.append("@" + str);
    }
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131362914:
      finish();
      return;
    case 2131363430:
      voteWithType(0);
      return;
    case 2131363435:
      voteWithType(1);
      return;
    case 2131363423:
      this.voteView.setVisibility(8);
      return;
    case 2131361824:
      writeAtSomeone();
      return;
    case 2131361825:
      sendVoteContent();
      return;
    case 2131361820:
    }
    this.voteView.setVisibility(0);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903287, paramViewGroup, false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initView();
    initData();
    initVoteView();
  }

  public void setHeader(PKInfoItem paramPKInfoItem)
  {
    this.titleTextView.setText(paramPKInfoItem.getTitle());
    this.redContentText.setText(paramPKInfoItem.getRedtitle());
    this.blueContentText.setText(paramPKInfoItem.getBluetitle());
    this.redNumberText.setText(paramPKInfoItem.getRedtotal());
    this.blueNumberText.setText(paramPKInfoItem.getBluetotal());
  }

  public void setVoteView(int paramInt, String paramString)
  {
    this.voteView.setVisibility(0);
    if (paramInt == 0)
      this.voteTipText.setText(2131428693);
    while (true)
    {
      this.voteContentEdit.setText(paramString);
      return;
      if (paramInt != 1)
        continue;
      this.voteTipText.setText(2131428694);
    }
  }

  class PKAdapter extends BaseAdapter
  {
    PKAdapter()
    {
    }

    public int getCount()
    {
      return PKFragment.this.recordsList.size();
    }

    public Object getItem(int paramInt)
    {
      return PKFragment.this.recordsList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      PKRecordListItem localPKRecordListItem = (PKRecordListItem)PKFragment.this.recordsList.get(paramInt);
      PKFragment.ViewHolder localViewHolder;
      if (paramView == null)
      {
        paramView = LayoutInflater.from(PKFragment.this.getActivity()).inflate(2130903289, null);
        localViewHolder = new PKFragment.ViewHolder(PKFragment.this);
        localViewHolder.redImage = ((KXFrameImageView)paramView.findViewById(2131363437));
        localViewHolder.blueImage = ((KXFrameImageView)paramView.findViewById(2131363439));
        localViewHolder.redText = ((TextView)paramView.findViewById(2131363438));
        localViewHolder.blueText = ((TextView)paramView.findViewById(2131363440));
        paramView.setTag(localViewHolder);
        if (localPKRecordListItem.getPktype() != 0)
          break label219;
        localViewHolder.redImage.setVisibility(0);
        localViewHolder.redText.setVisibility(0);
        localViewHolder.blueImage.setVisibility(8);
        localViewHolder.blueText.setVisibility(8);
        localViewHolder.redImage.setFrameNinePatchResId(2130838099);
        PKFragment.this.displayRoundPicture(localViewHolder.redImage, localPKRecordListItem.getImgURL(), ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
        localViewHolder.redText.setText(localPKRecordListItem.getContent());
      }
      label219: 
      do
      {
        return paramView;
        localViewHolder = (PKFragment.ViewHolder)paramView.getTag();
        break;
      }
      while (localPKRecordListItem.getPktype() != 1);
      localViewHolder.redImage.setVisibility(8);
      localViewHolder.redText.setVisibility(8);
      localViewHolder.blueImage.setVisibility(0);
      localViewHolder.blueText.setVisibility(0);
      localViewHolder.blueImage.setFrameNinePatchResId(2130838099);
      PKFragment.this.displayRoundPicture(localViewHolder.blueImage, localPKRecordListItem.getImgURL(), ImageDownloader.RoundCornerType.hdpi_big, 2130838334);
      localViewHolder.blueText.setText(localPKRecordListItem.getContent());
      return paramView;
    }
  }

  class PKTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    PKTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      KXPKEngine.getInstance().getTopicInfo(PKFragment.this.getActivity(), PKFragment.this.pkid);
      KXPKEngine.getInstance().getRecordsList(PKFragment.this.getActivity(), 0, PKFragment.this.pkid, 0, 60);
      KXPKEngine.getInstance().getRecordsList(PKFragment.this.getActivity(), 1, PKFragment.this.pkid, 0, 60);
      return Integer.valueOf(1);
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      PKInfoItem localPKInfoItem = PKModel.getInstance().getPkInfoItem();
      PKFragment.this.setHeader(localPKInfoItem);
      List localList1 = PKModel.getInstance().getRedList();
      List localList2 = PKModel.getInstance().getBlueList();
      int i = 0;
      int j = 0;
      int k = 0;
      if (k >= localList1.size() + localList2.size())
      {
        PKFragment.this.adapter.notifyDataSetChanged();
        return;
      }
      int m = (int)(2.0D * Math.random());
      if ((m == 0) && (i < localList1.size()))
      {
        PKRecordListItem localPKRecordListItem4 = (PKRecordListItem)localList1.get(i);
        i++;
        PKFragment.this.recordsList.add(localPKRecordListItem4);
      }
      while (true)
      {
        k++;
        break;
        if ((m == 0) && (i == localList1.size()))
        {
          PKRecordListItem localPKRecordListItem3 = (PKRecordListItem)localList2.get(j);
          j++;
          PKFragment.this.recordsList.add(localPKRecordListItem3);
          continue;
        }
        if ((m == 1) && (j < localList2.size()))
        {
          PKRecordListItem localPKRecordListItem2 = (PKRecordListItem)localList2.get(j);
          j++;
          PKFragment.this.recordsList.add(localPKRecordListItem2);
          continue;
        }
        if ((m != 1) || (j != localList2.size()))
          continue;
        PKRecordListItem localPKRecordListItem1 = (PKRecordListItem)localList1.get(i);
        i++;
        PKFragment.this.recordsList.add(localPKRecordListItem1);
      }
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  class ViewHolder
  {
    KXFrameImageView blueImage;
    TextView blueText;
    KXFrameImageView redImage;
    TextView redText;

    ViewHolder()
    {
    }
  }

  class VoteTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    private int pktype;

    public VoteTask(int arg2)
    {
      super();
      int i;
      this.pktype = i;
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      return Integer.valueOf(KXPKEngine.getInstance().pkVote(PKFragment.this.getActivity(), PKFragment.this.pkid, this.pktype));
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if (paramInteger.intValue() == 0)
      {
        String str = PKModel.getInstance().getWord();
        if (TextUtils.isEmpty(str))
          str = "你已发表过观点";
        Intent localIntent = new Intent(PKFragment.this.getActivity(), PKVotedActivity.class);
        localIntent.putExtra("word", str);
        PKFragment.this.startActivity(localIntent);
      }
      do
        return;
      while (paramInteger.intValue() != 1);
      PKFragment.this.setVoteView(this.pktype, PKModel.getInstance().getWord());
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.PKFragment
 * JD-Core Version:    0.6.0
 */