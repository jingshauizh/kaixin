package com.kaixin001.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.activity.KXEnvironment;
import com.kaixin001.item.HoroscopeDataProvider;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import java.util.ArrayList;
import java.util.List;

public class HoroscopeChooseFragment extends BaseFragment
  implements View.OnClickListener, AdapterView.OnItemClickListener
{
  public static final String STAR_NAME = "star.name";
  private String from;
  ImageView leftBtn;
  private HoroscopeChooseAdapter mAdapter;
  private GridView mGrid;
  private String mStarName = "";
  private ImageView rightBtn;
  private ViewGroup rootView;
  private ArrayList<String> titleNames;
  private TextView tvTitle;

  public static boolean getHoroscopeSelected(Context paramContext)
  {
    String str = User.getInstance().getUID();
    return paramContext.getSharedPreferences("select_horoscope" + str, 0).getBoolean("selected", false);
  }

  private String getValue(String paramString)
  {
    if (paramString == null)
      return "";
    try
    {
      String[] arrayOfString = getResources().getStringArray(2131492905);
      for (int i = 0; ; i++)
      {
        int j = arrayOfString.length;
        if (i >= j)
          return "";
        if (!arrayOfString[i].equals(paramString))
          continue;
        String str = i + 1;
        return str;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  private void initTitleBar()
  {
    this.leftBtn = ((ImageView)findViewById(2131362914));
    this.leftBtn.setOnClickListener(this);
    this.rootView = ((ViewGroup)findViewById(2131362700));
    this.tvTitle = ((TextView)this.rootView.findViewById(2131362920));
    this.tvTitle.setVisibility(0);
    this.rightBtn = ((ImageView)this.rootView.findViewById(2131362928));
    this.rightBtn.setImageResource(2130838304);
    this.rightBtn.setScaleType(ImageView.ScaleType.CENTER_CROP);
    this.rightBtn.setOnClickListener(this);
    if ((this.from != null) && (this.from.equals("HoroscopeFragment")))
    {
      this.tvTitle.setText("查看星座");
      this.rightBtn.setVisibility(8);
      return;
    }
    this.tvTitle.setText("设置我的星座");
    this.rightBtn.setVisibility(0);
  }

  private void initUI()
  {
    initTitleBar();
    this.mGrid = ((GridView)findViewById(2131362701));
    this.mGrid.setSelector(new BitmapDrawable());
    this.mAdapter = new HoroscopeChooseAdapter(getActivity(), this.titleNames);
    this.mGrid.setOnItemClickListener(this);
    this.mGrid.setAdapter(this.mAdapter);
  }

  private boolean isSelected(String paramString)
  {
    return paramString.equals(this.mStarName);
  }

  public static void setHoroscopeClicked(Context paramContext, boolean paramBoolean)
  {
    String str = User.getInstance().getUID();
    paramContext.getSharedPreferences("select_horoscope" + str, 0).edit().putBoolean("selected", paramBoolean).commit();
  }

  public void onClick(View paramView)
  {
    if (paramView == this.leftBtn)
      if ((this.from != null) && (this.from.equals("KaixinMenuListFragment")))
        if (super.isMenuListVisibleInMain())
          super.showSubActivityInMain();
    do
    {
      return;
      super.showMenuListInMain();
      return;
      finish();
      return;
    }
    while (paramView != this.rightBtn);
    if (TextUtils.isEmpty(this.mStarName))
    {
      Toast.makeText(getActivity(), "请选择你的星座", 0).show();
      return;
    }
    if ((this.from != null) && (this.from.equals("KaixinMenuListFragment")))
    {
      SavaSetTask localSavaSetTask = new SavaSetTask();
      String[] arrayOfString = new String[1];
      arrayOfString[0] = this.mStarName;
      localSavaSetTask.execute(arrayOfString);
      return;
    }
    finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    HoroscopeFragment.saveItemClick(getActivity(), true);
    this.titleNames = new ArrayList();
    Resources localResources = getActivity().getResources();
    this.titleNames.add(localResources.getString(2131428645));
    this.titleNames.add(localResources.getString(2131428646));
    this.titleNames.add(localResources.getString(2131428647));
    this.titleNames.add(localResources.getString(2131428648));
    this.titleNames.add(localResources.getString(2131428649));
    this.titleNames.add(localResources.getString(2131428650));
    this.titleNames.add(localResources.getString(2131428651));
    this.titleNames.add(localResources.getString(2131428652));
    this.titleNames.add(localResources.getString(2131428653));
    this.titleNames.add(localResources.getString(2131428654));
    this.titleNames.add(localResources.getString(2131428655));
    this.titleNames.add(localResources.getString(2131428656));
    this.mStarName = KXEnvironment.loadStrParams(getActivity(), "star.name", true, "");
    Bundle localBundle = getArguments();
    if (localBundle != null)
      this.from = localBundle.getString("from");
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903161, paramViewGroup, false);
  }

  public void onDestroy()
  {
    super.onDestroy();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    this.mStarName = ((String)this.mAdapter.getItem(paramInt));
    if (this.mAdapter != null)
      this.mAdapter.notifyDataSetChanged();
    if ((this.from != null) && (this.from.equals("HoroscopeFragment")))
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("star.name", this.mStarName);
      setResult(0, localIntent);
      finish();
    }
    do
      return;
    while ((this.from == null) || (!this.from.equals("KaixinMenuListFragment")) || (getActivity() == null));
    KXEnvironment.saveStrParams(getActivity(), "star.name", true, this.mStarName);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initUI();
  }

  public class HoroscopeChooseAdapter extends ArrayAdapter<String>
  {
    public HoroscopeChooseAdapter(int paramList, List<String> arg3)
    {
      super(i, localList);
    }

    public HoroscopeChooseAdapter(List<String> arg2)
    {
      this(localContext, 0, localList);
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
        paramView = View.inflate(getContext(), 2130903162, null);
      String str = (String)getItem(paramInt);
      ImageView localImageView = (ImageView)paramView.findViewById(2131362702);
      TextView localTextView1 = (TextView)paramView.findViewById(2131362703);
      TextView localTextView2 = (TextView)paramView.findViewById(2131362704);
      if (this.this$0.isSelected(str))
        localImageView.setBackgroundResource(2130839380);
      while (true)
      {
        localTextView1.setText(str);
        localTextView2.setText(HoroscopeDataProvider.getHoroscopeTime(str));
        localImageView.setImageResource(HoroscopeDataProvider.getHoroscopeIconID(str).intValue());
        return paramView;
        localImageView.setBackgroundResource(2130839379);
      }
    }
  }

  class SavaSetTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    SavaSetTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      String str = Protocol.getInstance().getSetStarUrl(paramArrayOfString[0]);
      HttpProxy localHttpProxy = new HttpProxy(HoroscopeChooseFragment.this.getActivity());
      try
      {
        localHttpProxy.httpGet(str, null, null);
        return Integer.valueOf(1);
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      HoroscopeChooseFragment.setHoroscopeClicked(HoroscopeChooseFragment.this.getActivity(), true);
      KXEnvironment.saveStrParams(HoroscopeChooseFragment.this.getActivity(), "star.name", true, HoroscopeChooseFragment.this.mStarName);
      PreferenceManager.getDefaultSharedPreferences(HoroscopeChooseFragment.this.getActivity()).edit().putString("horoscope_setting_choose_list", HoroscopeChooseFragment.this.getValue(HoroscopeChooseFragment.this.mStarName)).commit();
      Intent localIntent = new Intent(HoroscopeChooseFragment.this.getActivity(), HoroscopeFragment.class);
      localIntent.putExtra("star.name", HoroscopeChooseFragment.this.mStarName);
      HoroscopeChooseFragment.this.startFragment(localIntent, false, 1);
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
 * Qualified Name:     com.kaixin001.fragment.HoroscopeChooseFragment
 * JD-Core Version:    0.6.0
 */