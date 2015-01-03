package com.kaixin001.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.MessageHandlerHolder;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.SendGiftEngine;
import com.kaixin001.item.FriendInfo;
import com.kaixin001.item.GiftItem;
import com.kaixin001.model.GiftListModel;
import com.kaixin001.model.User;
import com.kaixin001.task.GetGiftListTask;
import com.kaixin001.util.AnimationUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageDownloader.RoundCornerType;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.UserHabitUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class SendGiftFragment extends BaseFragment
{
  private static final int DIALOG_ID_GET_GIFTS = 2;
  private static final int DIALOG_ID_SENDING = 1;
  public static final int GIFT_SELECTED = 1;
  boolean bIsCanceled = false;
  private ArrayList<FriendInfo> checkedFriendsList = new ArrayList();
  private String defaultps = "";
  private GetGiftListTask getGiftListTask;
  private ImageView giftEditLine2;
  private String giftId = "0";
  private GiftItem[] giftList;
  private int giftPosition = -1;
  private String giftType = "0";
  private ImageView imageView;
  int logoSize;
  int logoSpace;
  private String mFname = "";
  private String mFuid = "";
  private final ArrayList<FriendInfo> mReceiverList = new ArrayList();
  private String myPosition = "0";
  private EditText replyComment;
  private SendGiftEngine sendGiftEngine = SendGiftEngine.getInstance();
  SendGiftTask sendGiftTask;
  private CheckBox sendMms;
  private CheckBox sendStyle;
  private String smid = "0";

  private void cancelSend()
  {
    if (this.sendGiftTask != null)
    {
      this.bIsCanceled = true;
      this.sendGiftEngine.cancel();
      this.sendGiftTask.cancel(true);
      this.sendGiftTask = null;
    }
  }

  private void constructViews()
  {
    LinearLayout localLinearLayout1 = (LinearLayout)findViewById(2131363608);
    localLinearLayout1.removeAllViews();
    LinearLayout localLinearLayout2 = new LinearLayout(getActivity());
    TableLayout.LayoutParams localLayoutParams = new TableLayout.LayoutParams();
    localLayoutParams.width = -1;
    localLayoutParams.height = -2;
    localLinearLayout2.setLayoutParams(localLayoutParams);
    int i = 0;
    if (i > this.mReceiverList.size())
    {
      localLinearLayout1.addView(localLinearLayout2);
      localLinearLayout1.setClickable(true);
      localLinearLayout1.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          ArrayList localArrayList = new ArrayList();
          if (SendGiftFragment.this.mReceiverList != null);
          for (int i = 0; ; i++)
          {
            if (i >= SendGiftFragment.this.mReceiverList.size())
            {
              Intent localIntent = new Intent(SendGiftFragment.this.getActivity(), FriendsFragment.class);
              Bundle localBundle = new Bundle();
              localBundle.putInt("MODE", 1);
              localBundle.putInt("maxnum", 10);
              if (localArrayList != null)
                localBundle.putSerializable("checkedFriendsList", SendGiftFragment.this.checkedFriendsList);
              localIntent.putExtras(localBundle);
              AnimationUtil.startFragmentForResult(SendGiftFragment.this, localIntent, 4, 1);
              return;
            }
            localArrayList.add(((FriendInfo)SendGiftFragment.this.mReceiverList.get(i)).getFuid());
          }
        }
      });
      return;
    }
    View localView = getActivity().getLayoutInflater().inflate(2130903330, null, false);
    ImageView localImageView1 = (ImageView)localView.findViewById(2131363605);
    ImageView localImageView2 = (ImageView)localView.findViewById(2131363606);
    if (i == this.mReceiverList.size())
    {
      localImageView1.setImageResource(2130838076);
      localImageView2.setVisibility(8);
    }
    while (true)
    {
      localLinearLayout2.addView(localView);
      i++;
      break;
      displayRoundPicture(localImageView1, ((FriendInfo)this.mReceiverList.get(i)).getFlogo(), ImageDownloader.RoundCornerType.hdpi_big, 2130838079);
      localImageView2.setVisibility(0);
    }
  }

  public void finish()
  {
    super.finish();
    AnimationUtil.finishActivity(getActivity(), 2);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    Iterator localIterator;
    if (paramInt2 == -1)
    {
      if (paramInt1 != 4)
        break label280;
      if (paramIntent != null)
      {
        Bundle localBundle2 = paramIntent.getBundleExtra("bundle");
        if (localBundle2 != null)
          this.checkedFriendsList = ((ArrayList)localBundle2.getSerializable("checkedFriendsList"));
      }
      this.mReceiverList.clear();
      if (this.checkedFriendsList != null)
      {
        localIterator = this.checkedFriendsList.iterator();
        if (localIterator.hasNext())
          break label108;
      }
      constructViews();
      ((InputMethodManager)getActivity().getSystemService("input_method")).showSoftInput(this.replyComment, 1);
    }
    while (true)
    {
      return;
      label108: FriendInfo localFriendInfo1 = (FriendInfo)localIterator.next();
      String str3 = localFriendInfo1.getName();
      String str4 = localFriendInfo1.getFlogo();
      String str5 = localFriendInfo1.getFuid();
      label272: label274: for (int m = 0; ; m++)
      {
        int n = this.mReceiverList.size();
        int i1 = m;
        int i2 = 0;
        if (i1 >= n);
        while (true)
        {
          if (i2 != 0)
            break label272;
          if (str3.length() > 3)
          {
            StringBuilder localStringBuilder = new StringBuilder(String.valueOf(str3.substring(0, 3)));
            str3 = "...";
          }
          ArrayList localArrayList = this.mReceiverList;
          FriendInfo localFriendInfo2 = new FriendInfo(str3, str5, str4);
          localArrayList.add(localFriendInfo2);
          break;
          if (!((FriendInfo)this.mReceiverList.get(m)).getFuid().equals(str5))
            break label274;
          i2 = 1;
        }
        break;
      }
      label280: if ((paramInt1 != 1) || (paramIntent == null))
        continue;
      Bundle localBundle1 = paramIntent.getExtras();
      if (localBundle1 == null)
        continue;
      String str1 = localBundle1.getString("giftId");
      String str2 = localBundle1.getString("defaultps");
      if ((str2 != null) && (!TextUtils.isEmpty(str2)))
      {
        this.replyComment.setText(str2);
        int k = Math.min(str2.length(), this.replyComment.getText().length());
        this.replyComment.setSelection(k);
      }
      if ((str1 == null) || (TextUtils.isEmpty(str1)))
        continue;
      GiftItem[] arrayOfGiftItem = GiftListModel.getInstance().gifts;
      if (arrayOfGiftItem == null)
        continue;
      int i = arrayOfGiftItem.length;
      for (int j = 0; j < i; j++)
      {
        GiftItem localGiftItem = arrayOfGiftItem[j];
        if (!str1.equals(localGiftItem.gid))
          continue;
        this.giftId = str1;
        displayPicture(this.imageView, localGiftItem.pic, 2130838086);
        return;
      }
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    if (paramInt == 1)
      return ProgressDialog.show(getActivity(), "", getResources().getText(2131427870), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          SendGiftFragment.this.cancelSend();
        }
      });
    if (paramInt == 2)
      return ProgressDialog.show(getActivity(), "", getResources().getText(2131427869), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          SendGiftFragment.this.dismissDialog(2);
          SendGiftFragment.this.getGiftListTask.cancel(true);
          SendGiftFragment.this.finish();
        }
      });
    return super.onCreateDialog(paramInt);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903331, paramViewGroup, false);
  }

  public void onDestroy()
  {
    MessageHandlerHolder.getInstance().removeHandler(this.mHandler);
    if ((this.sendGiftTask != null) && (this.sendGiftTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.sendGiftTask.cancel(true);
      SendGiftEngine.getInstance().cancel();
    }
    super.onDestroy();
  }

  public void onResume()
  {
    constructViews();
    if (this.replyComment.getLineCount() > 1)
      this.giftEditLine2.setVisibility(0);
    while (true)
    {
      super.onResume();
      return;
      this.giftEditLine2.setVisibility(4);
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
    getActivity().getWindow().setSoftInputMode(3);
    Bundle localBundle = getArguments();
    Iterator localIterator;
    if (localBundle != null)
    {
      String str1 = localBundle.getString("fname");
      if (str1 != null)
        this.mFname = str1;
      String str2 = localBundle.getString("fuid");
      if (str2 != null)
        this.mFuid = str2;
      String str3 = localBundle.getString("giftType");
      if (str3 != null)
        this.giftType = str3;
      String str4 = localBundle.getString("position");
      if (str4 != null)
        this.myPosition = str4;
      String str5 = localBundle.getString("smid");
      if (str5 != null)
        this.smid = str5;
      String str6 = localBundle.getString("giftId");
      if (str6 != null)
        this.giftId = str6;
      String str7 = localBundle.getString("defaultps");
      if (str7 != null)
        this.defaultps = str7;
      this.checkedFriendsList = ((ArrayList)localBundle.getSerializable("checkedFriendsList"));
      if (this.checkedFriendsList != null)
      {
        this.mReceiverList.clear();
        localIterator = this.checkedFriendsList.iterator();
        if (localIterator.hasNext())
          break label475;
      }
    }
    setTitleBar();
    this.sendStyle = ((CheckBox)findViewById(2131363616));
    this.sendMms = ((CheckBox)findViewById(2131363617));
    this.imageView = ((ImageView)findViewById(2131363615));
    this.giftEditLine2 = ((ImageView)findViewById(2131363614));
    this.giftList = GiftListModel.getInstance().gifts;
    this.imageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent(SendGiftFragment.this.getActivity(), SelectGiftFragment.class);
        Bundle localBundle = new Bundle();
        localBundle.putString("giftId", SendGiftFragment.this.giftId);
        localIntent.putExtras(localBundle);
        AnimationUtil.startFragmentForResult(SendGiftFragment.this, localIntent, 1, 1);
      }
    });
    this.replyComment = ((EditText)findViewById(2131363612));
    this.replyComment.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramEditable)
      {
        if (SendGiftFragment.this.replyComment.getLineCount() > 1)
        {
          SendGiftFragment.this.giftEditLine2.setVisibility(0);
          return;
        }
        SendGiftFragment.this.giftEditLine2.setVisibility(4);
      }

      public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
        if (SendGiftFragment.this.isNeedReturn())
          return;
        if (SendGiftFragment.this.replyComment.getLineCount() > 1)
        {
          SendGiftFragment.this.giftEditLine2.setVisibility(0);
          return;
        }
        SendGiftFragment.this.giftEditLine2.setVisibility(4);
      }
    });
    GiftItem[] arrayOfGiftItem;
    int i;
    if ((!this.giftId.equals("0")) && (this.giftList != null))
    {
      arrayOfGiftItem = this.giftList;
      i = arrayOfGiftItem.length;
    }
    label672: for (int j = 0; ; j++)
    {
      if (j >= i);
      while (true)
      {
        if (!this.defaultps.equals(""))
        {
          this.replyComment.setText(this.defaultps);
          int k = Math.min(this.defaultps.length(), this.replyComment.getText().length());
          this.replyComment.setSelection(k);
        }
        this.logoSize = getResources().getDimensionPixelSize(2131296260);
        this.logoSpace = getResources().getDimensionPixelSize(2131296261);
        MessageHandlerHolder.getInstance().addHandler(this.mHandler);
        return;
        label475: FriendInfo localFriendInfo = (FriendInfo)localIterator.next();
        String str8 = localFriendInfo.getName();
        String str9 = localFriendInfo.getFlogo();
        String str10 = localFriendInfo.getFuid();
        label623: label625: for (int m = 0; ; m++)
        {
          int n = this.mReceiverList.size();
          int i1 = 0;
          if (m >= n);
          while (true)
          {
            if (i1 != 0)
              break label623;
            if (str8.length() > 3)
              str8 = str8.substring(0, 3) + "...";
            this.mReceiverList.add(new FriendInfo(str8, str10, str9));
            break;
            if (!((FriendInfo)this.mReceiverList.get(m)).getFuid().equals(str10))
              break label625;
            i1 = 1;
          }
          break;
        }
        GiftItem localGiftItem = arrayOfGiftItem[j];
        if (!this.giftId.equals(localGiftItem.gid))
          break label672;
        displayPicture(this.imageView, localGiftItem.pic, 2130838086);
      }
    }
  }

  protected void setTitleBar()
  {
    ImageView localImageView1 = (ImageView)findViewById(2131362914);
    localImageView1.setVisibility(0);
    localImageView1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        SendGiftFragment.this.finish();
      }
    });
    ImageView localImageView2 = (ImageView)findViewById(2131362928);
    localImageView2.setVisibility(0);
    localImageView2.setImageResource(2130839272);
    localImageView2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        SendGiftFragment.this.hideKeyboard();
        if (SendGiftFragment.this.giftId.equals("0"))
        {
          DialogUtil.showKXAlertDialog(SendGiftFragment.this.getActivity(), SendGiftFragment.this.getResources().getString(2131427868).toString(), null);
          return;
        }
        if (SendGiftFragment.this.mReceiverList.size() == 0)
        {
          DialogUtil.showKXAlertDialog(SendGiftFragment.this.getActivity(), SendGiftFragment.this.getResources().getString(2131428450).toString(), null);
          return;
        }
        SendGiftFragment.this.sendGiftTask = new SendGiftFragment.SendGiftTask(SendGiftFragment.this, null);
        SendGiftFragment.this.sendGiftTask.execute(new Void[0]);
        SendGiftFragment.this.showDialog(1);
        boolean bool = SendGiftFragment.this.sendStyle.isChecked();
        int i = SendGiftFragment.this.mReceiverList.size();
        if (bool)
        {
          UserHabitUtils.getInstance(SendGiftFragment.this.getActivity().getApplicationContext()).addUserHabit("Gift_Silent_Send_Contact_Count:" + i);
          return;
        }
        UserHabitUtils.getInstance(SendGiftFragment.this.getActivity().getApplicationContext()).addUserHabit("Gift_Send_Contact_Count:" + i);
      }
    });
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    if (this.mFname.length() > 3)
      new StringBuilder(String.valueOf(this.mFname.substring(0, 3))).append("...").toString();
    while (true)
    {
      localTextView.setText(getResources().getString(2131428442));
      localTextView.setVisibility(0);
      return;
    }
  }

  private class SendGiftTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    private SendGiftTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      while (true)
      {
        int k;
        try
        {
          String str1 = SendGiftFragment.this.replyComment.getText().toString();
          boolean bool1 = SendGiftFragment.this.sendStyle.isChecked();
          boolean bool2 = SendGiftFragment.this.sendMms.isChecked();
          int i = Integer.parseInt(SendGiftFragment.this.giftId);
          StringBuffer localStringBuffer = new StringBuffer();
          int j = SendGiftFragment.this.mReceiverList.size();
          k = 0;
          if (k < j)
            continue;
          String str2 = localStringBuffer.toString();
          KXLog.d("Values", "mFuid=" + SendGiftFragment.this.mFuid);
          KXLog.d("Values", "msg=" + str1);
          KXLog.d("Values", "isQuiet=" + bool1);
          KXLog.d("Values", "giftId=" + SendGiftFragment.this.giftId);
          return Integer.valueOf(SendGiftFragment.this.sendGiftEngine.sendGift(SendGiftFragment.this.getActivity().getApplicationContext(), str2, SendGiftFragment.this.smid, str1, bool1, bool2, i));
          if (k != 0)
            continue;
          localStringBuffer.append(((FriendInfo)SendGiftFragment.this.mReceiverList.get(k)).getFuid());
          break label304;
          localStringBuffer.append("," + ((FriendInfo)SendGiftFragment.this.mReceiverList.get(k)).getFuid());
        }
        catch (SecurityErrorException localSecurityErrorException)
        {
          return null;
        }
        label304: k++;
      }
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      Context localContext = SendGiftFragment.this.getActivity().getApplicationContext();
      if (paramInteger == null)
        SendGiftFragment.this.finish();
      while (true)
      {
        return;
        try
        {
          SendGiftFragment.this.dismissDialog(1);
          if (SendGiftFragment.this.bIsCanceled)
            continue;
          if (paramInteger.intValue() != 1)
            break;
          Intent localIntent = new Intent();
          localIntent.putExtra("giftType", SendGiftFragment.this.giftType);
          localIntent.putExtra("position", SendGiftFragment.this.myPosition);
          SendGiftFragment.this.setResult(-1, localIntent);
          SendGiftFragment.this.finish();
          Toast.makeText(localContext, localContext.getResources().getString(2131427871).toString(), 0).show();
          return;
        }
        catch (Exception localException)
        {
          KXLog.e("SendGiftActivity", "onPostExecute", localException);
          return;
        }
      }
      if (paramInteger.intValue() == -6)
      {
        DialogUtil.showKXAlertDialog(SendGiftFragment.this.getActivity(), localContext.getResources().getString(2131427872).toString(), null);
        return;
      }
      DialogUtil.showKXAlertDialog(SendGiftFragment.this.getActivity(), localContext.getResources().getString(2131427873).toString(), null);
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
 * Qualified Name:     com.kaixin001.fragment.SendGiftFragment
 * JD-Core Version:    0.6.0
 */