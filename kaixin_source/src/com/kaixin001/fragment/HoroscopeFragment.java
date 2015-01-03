package com.kaixin001.fragment;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.HoroscopeSettingsActivity;
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.engine.HoroscopeEngine;
import com.kaixin001.item.HoroscopeData;
import com.kaixin001.item.HoroscopeDataProvider;
import com.kaixin001.model.HoroscopeModel;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import com.kaixin001.util.ArrayAdapterAddUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.UserHabitUtils;
import com.kaixin001.view.ContentListWindow;
import com.kaixin001.view.ContentListWindow.DoSelect;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.message.BasicNameValuePair;

public class HoroscopeFragment extends BaseFragment
  implements View.OnClickListener, ContentListWindow.DoSelect, PopupWindow.OnDismissListener
{
  private static final int REQUEST_CHOOSE_STAR = 35855;
  private static final String TAG = HoroscopeFragment.class.getSimpleName();
  private TextView badStarText;
  private TextView badredsStarText;
  private AlertDialog.Builder builder;
  private View chooseClick;
  private TextView goodStarText;
  Handler handler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      HoroscopeFragment.this.showPushDialog();
    }
  };
  private LinearLayout healthHoroscopeStar;
  private HoroscopeData horoscopeData;
  private LinearLayout horoscopeHeader;
  private ImageView horoscopeIcon;
  private TextView horoscopeTitleText;
  private boolean isFirst = true;
  private LinearLayout jobHoroscopeStar;
  ImageView leftBtn;
  private View loadingCamp;
  private LinearLayout loveHoroscopeStar;
  private TextView luckyColor;
  private TextView luckyNumber;
  private HoroscopeDataAdapter mAdapter;
  private HoroscopeTask mHoroscopeTask = null;
  private ContentListWindow mPopupWindow;
  private String mTimeType = "1";
  private View mark1;
  private View mark2;
  private View match1;
  private View match2;
  private TextView matchStarText;
  private LinearLayout moneyHoroscopeStar;
  private ListView resultListView;
  private ImageView rightSettingBtn;
  private ImageView rightShareBtn;
  private ViewGroup rootView;
  private String starName = "";
  private TextView starNameText;
  private TextView starTimeText;
  private Timer timer;
  private Button todayBtn;
  private LinearLayout todayHoroscopeStar;
  private Button tomorrowBtn;
  private TextView tvTitle;
  private Button weekBtn;

  private void cancelLoading()
  {
    this.loadingCamp.setVisibility(8);
    this.resultListView.setVisibility(0);
  }

  private void checkStarName()
  {
    String str = KXEnvironment.loadStrParams(getActivity(), "star.name", true, "天秤座");
    if (!TextUtils.isEmpty(str))
      this.starName = str;
  }

  private void createTimerToShowDialog()
  {
    2 local2 = new TimerTask()
    {
      public void run()
      {
        if (HoroscopeFragment.this.isNeedReturn())
          return;
        HoroscopeFragment.this.handler.sendEmptyMessage(0);
      }
    };
    this.timer = new Timer();
    this.timer.schedule(local2, 1000L);
  }

  public static boolean getItemClick(Context paramContext)
  {
    return paramContext.getSharedPreferences("left_item", 0).getBoolean("click", false);
  }

  private void initTitleBar()
  {
    this.leftBtn = ((ImageView)findViewById(2131362914));
    this.leftBtn.setOnClickListener(this);
    this.rootView = ((ViewGroup)findViewById(2131362700));
    this.tvTitle = ((TextView)this.rootView.findViewById(2131362920));
    this.tvTitle.setVisibility(0);
    this.tvTitle.setText("运势");
    this.rightShareBtn = ((ImageView)this.rootView.findViewById(2131362924));
    this.rightShareBtn.setVisibility(0);
    this.rightShareBtn.setImageResource(2130838153);
    this.rightShareBtn.setOnClickListener(this);
    this.rightSettingBtn = ((ImageView)findViewById(2131362928));
    this.rightSettingBtn.setImageResource(2130838137);
    this.rightSettingBtn.setOnClickListener(this);
  }

  private void initUI()
  {
    initTitleBar();
    this.loadingCamp = findViewById(2131362097);
    this.todayBtn = ((Button)findViewById(2131362726));
    this.todayBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        HoroscopeFragment.this.selectStarToday();
        HoroscopeFragment.this.startLoading();
      }
    });
    this.tomorrowBtn = ((Button)findViewById(2131362727));
    this.tomorrowBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        HoroscopeFragment.this.selectStarTomorrow();
        HoroscopeFragment.this.startLoading();
      }
    });
    this.weekBtn = ((Button)findViewById(2131362728));
    this.weekBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        HoroscopeFragment.this.selectStarWeek();
        HoroscopeFragment.this.startLoading();
      }
    });
    this.starNameText = ((TextView)findViewById(2131362724));
    this.chooseClick = findViewById(2131362722);
    this.chooseClick.setOnClickListener(this);
    this.starTimeText = ((TextView)findViewById(2131362725));
    this.horoscopeHeader = ((LinearLayout)View.inflate(getActivity(), 2130903163, null));
    this.todayHoroscopeStar = ((LinearLayout)this.horoscopeHeader.findViewById(2131362706));
    this.loveHoroscopeStar = ((LinearLayout)this.horoscopeHeader.findViewById(2131362708));
    this.healthHoroscopeStar = ((LinearLayout)this.horoscopeHeader.findViewById(2131362709));
    this.moneyHoroscopeStar = ((LinearLayout)this.horoscopeHeader.findViewById(2131362711));
    this.jobHoroscopeStar = ((LinearLayout)this.horoscopeHeader.findViewById(2131362712));
    this.mark1 = this.horoscopeHeader.findViewById(2131362707);
    this.mark2 = this.horoscopeHeader.findViewById(2131362710);
    this.match1 = this.horoscopeHeader.findViewById(2131362713);
    this.match2 = this.horoscopeHeader.findViewById(2131362716);
    this.horoscopeTitleText = ((TextView)this.horoscopeHeader.findViewById(2131362705));
    this.luckyColor = ((TextView)this.horoscopeHeader.findViewById(2131362718));
    this.luckyNumber = ((TextView)this.horoscopeHeader.findViewById(2131362719));
    this.resultListView = ((ListView)findViewById(2131362729));
    this.resultListView.addHeaderView(this.horoscopeHeader, null, false);
    this.resultListView.setSelector(new BitmapDrawable());
    this.goodStarText = ((TextView)this.horoscopeHeader.findViewById(2131362714));
    this.badredsStarText = ((TextView)this.horoscopeHeader.findViewById(2131362715));
    this.matchStarText = ((TextView)this.horoscopeHeader.findViewById(2131362717));
    this.horoscopeIcon = ((ImageView)findViewById(2131362723));
  }

  private void onResponse(boolean paramBoolean)
  {
    cancelLoading();
    if (paramBoolean)
    {
      this.horoscopeData = HoroscopeEngine.getInstance().getModel().getHoroscopeData();
      reSetUIData();
    }
  }

  private void reSetUIData()
  {
    this.todayHoroscopeStar.removeAllViews();
    this.loveHoroscopeStar.removeAllViews();
    this.healthHoroscopeStar.removeAllViews();
    this.jobHoroscopeStar.removeAllViews();
    this.moneyHoroscopeStar.removeAllViews();
    this.horoscopeIcon.setImageResource(HoroscopeDataProvider.getHoroscopeIconID(this.horoscopeData.name).intValue());
    this.starNameText.setText(this.horoscopeData.name);
    this.starTimeText.setText(HoroscopeDataProvider.getHoroscopeTime(this.horoscopeData.name));
    this.luckyColor.setText(this.horoscopeData.color);
    this.luckyNumber.setText(this.horoscopeData.number);
    this.goodStarText.setText(this.horoscopeData.goodstar);
    this.badredsStarText.setText(this.horoscopeData.badstar);
    this.matchStarText.setText(this.horoscopeData.matchstar);
    setStar(this.todayHoroscopeStar, this.horoscopeData.total_idx);
    setStar(this.loveHoroscopeStar, this.horoscopeData.love_idx);
    setStar(this.healthHoroscopeStar, this.horoscopeData.health_idx);
    setStar(this.jobHoroscopeStar, this.horoscopeData.work_idx);
    setStar(this.moneyHoroscopeStar, this.horoscopeData.money_idx);
    if (this.isFirst)
    {
      this.mAdapter = new HoroscopeDataAdapter(getActivity(), this.horoscopeData.result);
      this.resultListView.setAdapter(this.mAdapter);
      this.isFirst = false;
      return;
    }
    this.mAdapter.clear();
    ArrayAdapterAddUtil.addAll(this.mAdapter, this.horoscopeData.result);
    this.mAdapter.notifyDataSetChanged();
  }

  public static void saveItemClick(Context paramContext, boolean paramBoolean)
  {
    paramContext.getSharedPreferences("left_item", 0).edit().putBoolean("click", paramBoolean).commit();
  }

  private void selectStarToday()
  {
    this.horoscopeTitleText.setText("今日运势");
    this.todayBtn.setBackgroundResource(2130837993);
    this.tomorrowBtn.setBackgroundResource(2130837992);
    this.weekBtn.setBackgroundResource(2130837992);
    this.todayBtn.setTextColor(getActivity().getResources().getColor(2131165198));
    this.tomorrowBtn.setTextColor(getActivity().getResources().getColor(2131165199));
    this.weekBtn.setTextColor(getActivity().getResources().getColor(2131165199));
    this.mark1.setVisibility(0);
    this.mark2.setVisibility(0);
    this.match1.setVisibility(8);
    this.match2.setVisibility(8);
    this.mTimeType = "1";
    HoroscopeTask localHoroscopeTask = new HoroscopeTask();
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.mTimeType;
    arrayOfString[1] = this.starName;
    this.mHoroscopeTask = ((HoroscopeTask)localHoroscopeTask.execute(arrayOfString));
  }

  private void selectStarTomorrow()
  {
    this.horoscopeTitleText.setText("明日运势");
    this.todayBtn.setBackgroundResource(2130837992);
    this.tomorrowBtn.setBackgroundResource(2130837993);
    this.weekBtn.setBackgroundResource(2130837992);
    this.todayBtn.setTextColor(getActivity().getResources().getColor(2131165199));
    this.tomorrowBtn.setTextColor(getActivity().getResources().getColor(2131165198));
    this.weekBtn.setTextColor(getActivity().getResources().getColor(2131165199));
    this.mark1.setVisibility(0);
    this.mark2.setVisibility(0);
    this.match1.setVisibility(8);
    this.match2.setVisibility(8);
    this.mTimeType = "3";
    HoroscopeTask localHoroscopeTask = new HoroscopeTask();
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.mTimeType;
    arrayOfString[1] = this.starName;
    this.mHoroscopeTask = ((HoroscopeTask)localHoroscopeTask.execute(arrayOfString));
  }

  private void selectStarWeek()
  {
    this.horoscopeTitleText.setText("本周运势");
    this.todayBtn.setBackgroundResource(2130837992);
    this.tomorrowBtn.setBackgroundResource(2130837992);
    this.weekBtn.setBackgroundResource(2130837993);
    this.todayBtn.setTextColor(getActivity().getResources().getColor(2131165199));
    this.tomorrowBtn.setTextColor(getActivity().getResources().getColor(2131165199));
    this.weekBtn.setTextColor(getActivity().getResources().getColor(2131165198));
    this.mark1.setVisibility(8);
    this.mark2.setVisibility(8);
    this.match1.setVisibility(0);
    this.match2.setVisibility(0);
    this.mTimeType = "4";
    HoroscopeTask localHoroscopeTask = new HoroscopeTask();
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.mTimeType;
    arrayOfString[1] = this.starName;
    this.mHoroscopeTask = ((HoroscopeTask)localHoroscopeTask.execute(arrayOfString));
  }

  private void setStar(LinearLayout paramLinearLayout, String paramString)
  {
    int i = Integer.parseInt(paramString) / 20;
    int j = 0;
    if (j >= i);
    for (int k = 5; ; k--)
    {
      if (k <= i)
      {
        return;
        ImageView localImageView1 = new ImageView(getActivity());
        localImageView1.setImageResource(2130838366);
        localImageView1.setPadding(3, 0, 0, 0);
        paramLinearLayout.addView(localImageView1);
        j++;
        break;
      }
      ImageView localImageView2 = new ImageView(getActivity());
      localImageView2.setImageResource(2130838365);
      localImageView2.setPadding(3, 0, 0, 0);
      paramLinearLayout.addView(localImageView2);
    }
  }

  private void showPopUpWindow(View paramView)
  {
    View localView = getActivity().getLayoutInflater().inflate(2130903239, null, false);
    if ((this.mPopupWindow != null) && (this.mPopupWindow.isShowing()))
      try
      {
        this.mPopupWindow.dismiss();
        this.mPopupWindow = null;
        return;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    float f = getResources().getDisplayMetrics().density;
    int i = getResources().getConfiguration().orientation;
    Resources localResources = getResources();
    int[] arrayOfInt1 = { 2130838346 };
    ContentListWindow.mEditContent = new String[1];
    ContentListWindow.mEditContent[0] = localResources.getString(2131428667);
    if (i == 1);
    for (this.mPopupWindow = new ContentListWindow(localView, (int)(180.0F * f), -2, true, getActivity(), 301, arrayOfInt1); ; this.mPopupWindow = new ContentListWindow(localView, (int)(180.0F * f), -2, true, getActivity(), 301, arrayOfInt1))
    {
      this.mPopupWindow.setDoSelectListener(this);
      this.mPopupWindow.setOnDismissListener(this);
      this.mPopupWindow.setOutsideTouchable(true);
      this.mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
      this.mPopupWindow.setClippingEnabled(false);
      int[] arrayOfInt2 = new int[2];
      paramView.getLocationInWindow(arrayOfInt2);
      int j = 6 * (int)f;
      this.mPopupWindow.showAtLocation(paramView, 53, -10, 9 + (arrayOfInt2[1] + paramView.getHeight() - j));
      return;
    }
  }

  private void showPushDialog()
  {
    DialogUtil.showMsgDlg(getActivity(), "订阅每日运势", "每日运势可以定时推送啦，不可错过的幸运提示，现在就订阅吧！此操作可随时在设置中更改。", "好", "暂不", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        new HoroscopeFragment.SetPushTask(HoroscopeFragment.this)
        {
          protected void onPostExecute(Integer paramInteger)
          {
            try
            {
              super.onPostExecute(paramInteger);
              UserHabitUtils.getInstance(HoroscopeFragment.this.getActivity()).addUserHabit("collection_horoscope_push");
              PreferenceManager.getDefaultSharedPreferences(HoroscopeFragment.this.getActivity()).edit().putBoolean("push_horoscope_preference", true).commit();
              return;
            }
            catch (Exception localException)
            {
            }
          }
        }
        .execute(new String[] { "1" });
      }
    }
    , new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        new HoroscopeFragment.SetPushTask(HoroscopeFragment.this)
        {
          protected void onPostExecute(Integer paramInteger)
          {
            try
            {
              super.onPostExecute(paramInteger);
              UserHabitUtils.getInstance(HoroscopeFragment.this.getActivity()).addUserHabit("un_collection_horoscope_push");
              PreferenceManager.getDefaultSharedPreferences(HoroscopeFragment.this.getActivity()).edit().putBoolean("push_horoscope_preference", false).commit();
              return;
            }
            catch (Exception localException)
            {
            }
          }
        }
        .execute(new String[] { "0" });
      }
    }).show();
  }

  private void startLoading()
  {
    this.loadingCamp.setVisibility(0);
    this.resultListView.setVisibility(8);
  }

  public void doSelect(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return;
    case 0:
    }
    startActivity(new Intent(getActivity(), HoroscopeSettingsActivity.class));
  }

  public void onClick(View paramView)
  {
    if (paramView == this.leftBtn)
      if (super.isMenuListVisibleInMain())
        super.showSubActivityInMain();
    do
    {
      return;
      super.showMenuListInMain();
      return;
      if (paramView == this.rightShareBtn)
      {
        if (this.horoscopeData == null)
        {
          Toast.makeText(getActivity(), "暂无运势信息", 0).show();
          return;
        }
        Intent localIntent2 = new Intent();
        localIntent2.setClass(getActivity(), WriteWeiboFragment.class);
        localIntent2.putExtra("from", "HoroscopeFragment");
        String str = ((BasicNameValuePair)this.horoscopeData.result.get(0)).getValue();
        if (str.length() >= 40)
        {
          StringBuffer localStringBuffer = new StringBuffer(str.substring(0, 39));
          localStringBuffer.append("...");
          str = localStringBuffer.toString();
        }
        int i = Integer.parseInt(this.horoscopeData.total_idx) / 20;
        if (this.mTimeType.equals("1"))
          localIntent2.putExtra("yunShare", "我刚测过了我今日的运势，运势" + i + "颗星，" + str + "http://um0.cn/RCAAys/");
        while (true)
        {
          UserHabitUtils.getInstance(getActivity()).addUserHabit("share_horoscope_button_click");
          startFragment(localIntent2, true, 3);
          return;
          if (this.mTimeType.equals("3"))
          {
            localIntent2.putExtra("yunShare", "我刚测过了我明日的运势，运势" + i + "颗星，" + str + "http://um0.cn/RCAAys/");
            continue;
          }
          if (!this.mTimeType.equals("4"))
            continue;
          localIntent2.putExtra("yunShare", "我刚测过了我本周的运势，运势" + i + "颗星，" + str + "http://um0.cn/RCAAys/");
        }
      }
      if (paramView != this.rightSettingBtn)
        continue;
      showPopUpWindow(this.rightSettingBtn);
      return;
    }
    while (paramView != this.chooseClick);
    Intent localIntent1 = new Intent(getActivity(), HoroscopeChooseFragment.class);
    localIntent1.putExtra("from", "HoroscopeFragment");
    startFragmentForResult(localIntent1, 35855, 3, true);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
      this.starName = localBundle.getString("star.name");
    saveItemClick(getActivity(), true);
    if (KXEnvironment.loadBooleanParams(getActivity(), "isFirstToHoroscope", true, true))
    {
      createTimerToShowDialog();
      KXEnvironment.saveBooleanParams(getActivity(), "isFirstToHoroscope", true, false);
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903165, paramViewGroup, false);
  }

  public void onDestroy()
  {
    cancelTask(this.mHoroscopeTask);
    if (this.timer != null)
      this.timer.cancel();
    super.onDestroy();
  }

  public void onDismiss()
  {
  }

  protected void onFragmentResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onFragmentResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == 35855)
    {
      Bundle localBundle = paramIntent.getExtras();
      if (localBundle != null)
      {
        this.starName = localBundle.getString("star.name");
        startLoading();
        this.mAdapter = null;
        selectStarToday();
      }
    }
  }

  public void onResume()
  {
    super.onResume();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initUI();
    checkStarName();
    startLoading();
    this.isFirst = true;
    this.mAdapter = null;
    selectStarToday();
  }

  public class HoroscopeDataAdapter extends ArrayAdapter<BasicNameValuePair>
  {
    private final Context mContext;

    public HoroscopeDataAdapter(int paramList, List<BasicNameValuePair> arg3)
    {
      super(i, localList);
      this.mContext = paramList;
    }

    public HoroscopeDataAdapter(List<BasicNameValuePair> arg2)
    {
      this(localContext, 0, localList);
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
        paramView = View.inflate(this.mContext, 2130903164, null);
      TextView localTextView1 = (TextView)paramView.findViewById(2131362720);
      TextView localTextView2 = (TextView)paramView.findViewById(2131362721);
      BasicNameValuePair localBasicNameValuePair = (BasicNameValuePair)getItem(paramInt);
      localTextView1.setText(localBasicNameValuePair.getName());
      localTextView2.setText(localBasicNameValuePair.getValue());
      return paramView;
    }
  }

  public class HoroscopeTask extends KXFragment.KXAsyncTask<String, Void, Boolean>
  {
    public HoroscopeTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(String[] paramArrayOfString)
    {
      try
      {
        Thread.sleep(200L);
        String str1 = paramArrayOfString[0];
        String str2 = paramArrayOfString[1];
        return Boolean.valueOf(HoroscopeEngine.getInstance().doGetHoroscopeRequest(HoroscopeFragment.this.getActivity(), str1, str2).booleanValue());
      }
      catch (InterruptedException localInterruptedException)
      {
        while (true)
          localInterruptedException.printStackTrace();
      }
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      HoroscopeFragment.this.onResponse(paramBoolean.booleanValue());
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  class SetPushTask extends AsyncTask<String, Void, Integer>
  {
    SetPushTask()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      if (HoroscopeFragment.this.isNeedReturn())
        return null;
      String str = Protocol.getInstance().setStarsPush(paramArrayOfString[0]);
      HttpProxy localHttpProxy = new HttpProxy(HoroscopeFragment.this.getActivity());
      try
      {
        localHttpProxy.httpGet(str, null, null);
        return Integer.valueOf(1);
      }
      catch (Exception localException)
      {
      }
      return null;
    }

    protected void onPostExecute(Integer paramInteger)
    {
      if ((!HoroscopeFragment.this.isNeedReturn()) && (paramInteger == null));
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.HoroscopeFragment
 * JD-Core Version:    0.6.0
 */