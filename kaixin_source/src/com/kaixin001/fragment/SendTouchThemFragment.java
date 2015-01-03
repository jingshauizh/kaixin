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
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.MessageHandlerHolder;
import com.kaixin001.engine.SecurityErrorException;
import com.kaixin001.engine.SendTouchEngine;
import com.kaixin001.item.TouchItem;
import com.kaixin001.model.TouchListModel;
import com.kaixin001.model.User;
import com.kaixin001.task.GetTouchListTask;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.KXLog;

public class SendTouchThemFragment extends BaseFragment
{
  private static final int DIALOG_ID_GET_GIFTS = 2;
  private static final int DIALOG_ID_SENDING = 1;
  private static Integer[] mActionTexts;
  private static Integer[] mImageIds;
  private static String[] mTouchType = { "1", "6", "11", "3", "8", "7", "5", "9", "13" };
  boolean bIsCanceled = false;
  private Gallery gallery;
  private GetTouchListTask getTouchListTask;
  int giftPosition = -1;
  private String giftType = "0";
  private String mFname = "";
  private String mFuid = "";
  private String myPosition = "0";
  private CheckBox sendStyle;
  private Button sendTouch;
  private SendTouchEngine sendTouchEngine = SendTouchEngine.getInstance();
  SendTouchTask sendTouchTask;
  private String smid = "0";

  static
  {
    Integer[] arrayOfInteger1 = new Integer[9];
    arrayOfInteger1[0] = Integer.valueOf(2130839085);
    arrayOfInteger1[1] = Integer.valueOf(2130839086);
    arrayOfInteger1[2] = Integer.valueOf(2130839087);
    arrayOfInteger1[3] = Integer.valueOf(2130839088);
    arrayOfInteger1[4] = Integer.valueOf(2130839089);
    arrayOfInteger1[5] = Integer.valueOf(2130839090);
    arrayOfInteger1[6] = Integer.valueOf(2130839091);
    arrayOfInteger1[7] = Integer.valueOf(2130839092);
    arrayOfInteger1[8] = Integer.valueOf(2130839093);
    mImageIds = arrayOfInteger1;
    Integer[] arrayOfInteger2 = new Integer[9];
    arrayOfInteger2[0] = Integer.valueOf(2131428397);
    arrayOfInteger2[1] = Integer.valueOf(2131428398);
    arrayOfInteger2[2] = Integer.valueOf(2131428399);
    arrayOfInteger2[3] = Integer.valueOf(2131428400);
    arrayOfInteger2[4] = Integer.valueOf(2131428401);
    arrayOfInteger2[5] = Integer.valueOf(2131428402);
    arrayOfInteger2[6] = Integer.valueOf(2131428403);
    arrayOfInteger2[7] = Integer.valueOf(2131428404);
    arrayOfInteger2[8] = Integer.valueOf(2131428405);
    mActionTexts = arrayOfInteger2;
  }

  private void cancelSend()
  {
    if (this.sendTouchTask != null)
    {
      this.bIsCanceled = true;
      this.sendTouchEngine.cancel();
      this.sendTouchTask.cancel(true);
      this.sendTouchEngine = null;
    }
  }

  private String getEmptyTextView(String paramString1, String paramString2)
  {
    return paramString2.replace("*", paramString1);
  }

  protected boolean handleMessage(Message paramMessage)
  {
    if (getActivity().isFinishing())
      return true;
    if (paramMessage == null)
      return false;
    if (paramMessage.what == 222)
    {
      dismissDialog(2);
      if (paramMessage.arg1 == 1)
      {
        this.gallery.setAdapter(new ImageAdapter(getActivity()));
        this.gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
          public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
          {
            if (SendTouchThemFragment.this.isNeedReturn())
              return;
            int i = SendTouchThemFragment.this.gallery.getChildCount();
            for (int j = 0; ; j++)
            {
              if (j >= i)
              {
                ((LinearLayout)paramView).setBackgroundDrawable(SendTouchThemFragment.this.getResources().getDrawable(2130839001));
                SendTouchThemFragment.this.giftPosition = paramInt;
                return;
              }
              ((LinearLayout)SendTouchThemFragment.this.gallery.getChildAt(j)).setBackgroundDrawable(SendTouchThemFragment.this.getResources().getDrawable(2130838932));
            }
          }

          public void onNothingSelected(AdapterView<?> paramAdapterView)
          {
            SendTouchThemFragment.this.giftPosition = -1;
          }
        });
        this.gallery.setSelection(1);
        return true;
      }
      Toast.makeText(getActivity(), 2131428407, 0).show();
      finish();
      return true;
    }
    return super.handleMessage(paramMessage);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    if (paramInt == 1)
      return ProgressDialog.show(getActivity(), "", getResources().getText(2131428409), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          SendTouchThemFragment.this.cancelSend();
        }
      });
    if (paramInt == 2)
      return ProgressDialog.show(getActivity(), "", getResources().getText(2131428408), true, true, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          SendTouchThemFragment.this.dismissDialog(2);
          SendTouchThemFragment.this.getTouchListTask.cancel(true);
          SendTouchThemFragment.this.finish();
        }
      });
    return super.onCreateDialog(paramInt);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903347, paramViewGroup, false);
  }

  public void onDestroy()
  {
    MessageHandlerHolder.getInstance().removeHandler(this.mHandler);
    if ((this.sendTouchTask != null) && (this.sendTouchTask.getStatus() != AsyncTask.Status.FINISHED))
    {
      this.sendTouchTask.cancel(true);
      SendTouchEngine.getInstance().cancel();
    }
    super.onDestroy();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    User.getInstance().loadDataIfEmpty(getActivity().getApplicationContext());
    Bundle localBundle = getArguments();
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
    }
    setTitleBar();
    this.gallery = ((Gallery)findViewById(2131363729));
    this.sendStyle = ((CheckBox)findViewById(2131363616));
    this.sendTouch = ((Button)findViewById(2131363730));
    this.sendTouch.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (SendTouchThemFragment.this.giftPosition == -1)
        {
          DialogUtil.showKXAlertDialog(SendTouchThemFragment.this.getActivity(), SendTouchThemFragment.this.getResources().getString(2131428396).toString(), null);
          return;
        }
        SendTouchThemFragment.this.sendTouchTask = new SendTouchThemFragment.SendTouchTask(SendTouchThemFragment.this, null);
        SendTouchThemFragment.this.sendTouchTask.execute(new Void[0]);
        SendTouchThemFragment.this.showDialog(1);
      }
    });
    MessageHandlerHolder.getInstance().addHandler(this.mHandler);
    this.getTouchListTask = new GetTouchListTask(getActivity());
    GetTouchListTask localGetTouchListTask = this.getTouchListTask;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mHandler;
    localGetTouchListTask.execute(arrayOfObject);
    showDialog(2);
  }

  protected void setTitleBar()
  {
    ImageView localImageView = (ImageView)findViewById(2131362914);
    localImageView.setVisibility(0);
    localImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        SendTouchThemFragment.this.finish();
      }
    });
    ((ImageView)findViewById(2131362928)).setVisibility(8);
    ((ImageView)findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)findViewById(2131362920);
    if (this.mFname.length() > 3);
    for (String str = this.mFname.substring(0, 3) + "..."; ; str = this.mFname)
    {
      localTextView.setText(getEmptyTextView(str, getResources().getString(2131428396)));
      localTextView.setVisibility(0);
      return;
    }
  }

  public class ImageAdapter extends BaseAdapter
  {
    private Context mContext;
    private LayoutInflater mInflater;

    public ImageAdapter(Context arg2)
    {
      Context localContext;
      this.mInflater = LayoutInflater.from(localContext);
      this.mContext = localContext;
    }

    private void setTouchImage(ImageView paramImageView, TouchItem paramTouchItem)
    {
      for (int i = 0; ; i++)
      {
        if (i >= SendTouchThemFragment.mTouchType.length)
        {
          SendTouchThemFragment.this.displayPicture(paramImageView, paramTouchItem.pic, 2130838063);
          return;
        }
        if (!paramTouchItem.gid.equals(SendTouchThemFragment.mTouchType[i]))
          continue;
        paramImageView.setImageResource(SendTouchThemFragment.mImageIds[i].intValue());
        return;
      }
    }

    public int getCount()
    {
      return SendTouchThemFragment.mImageIds.length;
    }

    public Object getItem(int paramInt)
    {
      return Integer.valueOf(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      ImageView localImageView = new ImageView(this.mContext);
      localImageView.setImageResource(SendTouchThemFragment.mImageIds[paramInt].intValue());
      localImageView.setAdjustViewBounds(true);
      localImageView.setScaleType(ImageView.ScaleType.FIT_XY);
      localImageView.setLayoutParams(new Gallery.LayoutParams(-20 + paramViewGroup.getHeight(), -20 + paramViewGroup.getHeight()));
      localImageView.setBackgroundResource(2130838932);
      SendTouchThemFragment.ViewHolder localViewHolder = null;
      if (paramView == null)
      {
        paramView = this.mInflater.inflate(2130903348, null);
        localViewHolder = new SendTouchThemFragment.ViewHolder(SendTouchThemFragment.this);
        localViewHolder.img = ((ImageView)paramView.findViewById(2131363731));
        localViewHolder.info = ((TextView)paramView.findViewById(2131363732));
        paramView.setTag(localViewHolder);
      }
      localViewHolder.img.setImageResource(SendTouchThemFragment.mImageIds[paramInt].intValue());
      localViewHolder.info.setText(SendTouchThemFragment.mActionTexts[paramInt].intValue());
      return paramView;
    }
  }

  private class SendTouchTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    private SendTouchTask()
    {
      super();
    }

    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      int i;
      int j;
      TouchItem localTouchItem;
      try
      {
        boolean bool = SendTouchThemFragment.this.sendStyle.isChecked();
        i = TouchListModel.getInstance().touchs.length;
        if (i > 0)
        {
          j = 0;
          break label163;
          while (localTouchItem == null)
          {
            return Integer.valueOf(-2);
            label42: if (!SendTouchThemFragment.mTouchType[SendTouchThemFragment.this.giftPosition].equals(TouchListModel.getInstance().touchs[j].gid))
              break label176;
            localTouchItem = TouchListModel.getInstance().touchs[j];
          }
        }
        else
        {
          int m;
          for (int k = Integer.parseInt(localTouchItem.gid); ; k = m)
          {
            return Integer.valueOf(SendTouchThemFragment.this.sendTouchEngine.sendTouch(SendTouchThemFragment.this.getActivity().getApplicationContext(), SendTouchThemFragment.this.mFuid, SendTouchThemFragment.this.smid, null, bool, k));
            m = Integer.parseInt(SendTouchThemFragment.mTouchType[SendTouchThemFragment.this.giftPosition]);
          }
        }
      }
      catch (SecurityErrorException localSecurityErrorException)
      {
        return null;
      }
      while (true)
      {
        label163: if (j < i)
          break label42;
        localTouchItem = null;
        break;
        label176: j++;
      }
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      Context localContext = SendTouchThemFragment.this.getActivity().getApplicationContext();
      try
      {
        SendTouchThemFragment.this.dismissDialog(1);
        if (SendTouchThemFragment.this.bIsCanceled)
          return;
        if (paramInteger.intValue() == 1)
        {
          Intent localIntent = new Intent();
          localIntent.putExtra("giftType", SendTouchThemFragment.this.giftType);
          localIntent.putExtra("position", SendTouchThemFragment.this.myPosition);
          SendTouchThemFragment.this.setResult(-1, localIntent);
          SendTouchThemFragment.this.finish();
          Toast.makeText(localContext, localContext.getResources().getString(2131428410).toString(), 0).show();
          return;
        }
      }
      catch (Exception localException)
      {
        KXLog.e("SendTouchActivity", "onPostExecute", localException);
        return;
      }
      if (paramInteger.intValue() == -6)
      {
        DialogUtil.showKXAlertDialog(SendTouchThemFragment.this.getActivity(), localContext.getResources().getString(2131428411).toString(), null);
        return;
      }
      if (paramInteger.intValue() == -2)
      {
        DialogUtil.showKXAlertDialog(SendTouchThemFragment.this.getActivity(), localContext.getResources().getString(2131428413).toString(), null);
        return;
      }
      DialogUtil.showKXAlertDialog(SendTouchThemFragment.this.getActivity(), localContext.getResources().getString(2131428412).toString(), null);
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  public class ViewHolder
  {
    public ImageView img;
    public TextView info;

    public ViewHolder()
    {
    }

    public ImageView getImg()
    {
      return this.img;
    }

    public TextView getInfo()
    {
      return this.info;
    }

    public void setImg(ImageView paramImageView)
    {
      this.img = paramImageView;
    }

    public void setInfo(TextView paramTextView)
    {
      this.info = paramTextView;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.SendTouchThemFragment
 * JD-Core Version:    0.6.0
 */