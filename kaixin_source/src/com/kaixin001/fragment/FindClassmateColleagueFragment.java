package com.kaixin001.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.kaixin001.view.KXPopupListWindow.DoSelect;
import com.kaixin001.view.KXTopTab;
import com.kaixin001.view.KXTopTabHost;
import com.kaixin001.view.KXTopTabHost.OnTabChangeListener;
import java.util.ArrayList;

public class FindClassmateColleagueFragment extends BaseFragment
  implements View.OnClickListener, KXTopTabHost.OnTabChangeListener, AdapterView.OnItemClickListener, KXPopupListWindow.DoSelect
{
  public static final int MODE_CLASSMATE = 1;
  public static final int MODE_COLLEAGUE = 2;
  private ImageView mBtnLeft;
  private ImageView mBtnRight;
  private Button mBtnSearch;
  private int mCurTabIndex = -1;
  private String mDefaultCompany;
  private String mDefaultJuniorSchool;
  private String mDefaultSeniorSchool;
  private String mDefaultUniversitySchool;
  private GetMatchUniversityTask mGetMatchUniversityTask;
  private LoadUniversityDataTask mLoadUniversityDataTask;
  private ArrayList<String> mMatchIdList = new ArrayList();
  private int mMatchIndex = -1;
  private ArrayList<String> mMathNameList = new ArrayList();
  private int mMode = 1;
  private EditText mName;
  private View mNameDelete;
  private View mNameLayout;
  private NameListAdapter mNameListAdapter;
  private ListView mNameListView;
  private TextView mNameTag;
  private KXTopTabHost mTabHost;
  private TextView mTextRight;
  private String mUniversityId = "";
  private ArrayList<String> mUniversityIdList = new ArrayList();
  private ArrayList<String> mUniversityNameList = new ArrayList();
  private EditText mYear;
  private View mYearDelete;
  private View mYearLayout;
  private TextView mYearTag;

  public static boolean containStr(String paramString1, String paramString2)
  {
    int i;
    if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)) || (paramString1.length() < paramString2.length()))
      i = 0;
    while (true)
    {
      return i;
      int j = paramString2.length();
      i = 1;
      for (int k = 0; k < j; k++)
        if (paramString1.indexOf(paramString2.substring(k, k + 1)) < 0)
          return false;
    }
  }

  private void initData()
  {
  }

  private void initTabHost(View paramView)
  {
    this.mTabHost = ((KXTopTabHost)paramView.findViewById(2131362293));
    this.mTabHost.setOnTabChangeListener(this);
    KXTopTab localKXTopTab1 = new KXTopTab(getActivity());
    localKXTopTab1.setText(2131428620);
    this.mTabHost.addTab(localKXTopTab1);
    KXTopTab localKXTopTab2 = new KXTopTab(getActivity());
    localKXTopTab2.setText(2131428621);
    this.mTabHost.addTab(localKXTopTab2);
    KXTopTab localKXTopTab3 = new KXTopTab(getActivity());
    localKXTopTab3.setText(2131428622);
    this.mTabHost.addTab(localKXTopTab3);
    if (this.mCurTabIndex < 0)
      this.mCurTabIndex = 2;
    this.mTabHost.setCurrentTab(this.mCurTabIndex);
  }

  private void initTitle(View paramView)
  {
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    this.mBtnRight = ((ImageView)findViewById(2131362928));
    this.mBtnRight.setVisibility(8);
    this.mTextRight = ((TextView)paramView.findViewById(2131362930));
    this.mTextRight.setVisibility(8);
    this.mBtnLeft = ((ImageView)paramView.findViewById(2131362914));
    this.mBtnLeft.setOnClickListener(this);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setVisibility(0);
    if (this.mMode == 1);
    for (int i = 2131428607; ; i = 2131428608)
    {
      localTextView.setText(i);
      return;
    }
  }

  private void initView()
  {
    initTabHost(getView());
    this.mNameLayout = getView().findViewById(2131362294);
    this.mName = ((EditText)getView().findViewById(2131362296));
    this.mNameDelete = getView().findViewById(2131362297);
    this.mNameDelete.setOnClickListener(this);
    this.mYearLayout = getView().findViewById(2131362298);
    this.mYear = ((EditText)getView().findViewById(2131362300));
    this.mYearDelete = getView().findViewById(2131362301);
    this.mYearDelete.setOnClickListener(this);
    this.mNameTag = ((TextView)findViewById(2131362295));
    this.mYearTag = ((TextView)findViewById(2131362299));
    this.mBtnSearch = ((Button)getView().findViewById(2131362302));
    this.mBtnSearch.setOnClickListener(this);
    if (this.mMode == 2)
    {
      this.mTabHost.setVisibility(8);
      this.mYearLayout.setVisibility(8);
      ((TextView)getView().findViewById(2131362295)).setText(2131428625);
    }
    this.mName.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
        if (FindClassmateColleagueFragment.this.isNeedReturn())
          return;
        String str = FindClassmateColleagueFragment.this.mName.getText().toString();
        if (TextUtils.isEmpty(str))
        {
          FindClassmateColleagueFragment.this.mNameDelete.setVisibility(8);
          FindClassmateColleagueFragment.this.mBtnSearch.setEnabled(false);
          FindClassmateColleagueFragment.this.mBtnSearch.setTextColor(2147483647);
        }
        while (true)
        {
          if ((str == null) || (str.length() <= 2))
            break label206;
          if (((FindClassmateColleagueFragment.this.mMatchIndex >= 0) && (FindClassmateColleagueFragment.this.mMatchIndex < FindClassmateColleagueFragment.this.mMathNameList.size()) && (str.equals(FindClassmateColleagueFragment.this.mMathNameList.get(FindClassmateColleagueFragment.this.mMatchIndex)))) || (FindClassmateColleagueFragment.this.mMode != 1) || (FindClassmateColleagueFragment.this.mCurTabIndex != 2))
            break;
          FindClassmateColleagueFragment.this.startCheckMatch();
          return;
          FindClassmateColleagueFragment.this.mNameDelete.setVisibility(0);
          FindClassmateColleagueFragment.this.mBtnSearch.setEnabled(true);
          FindClassmateColleagueFragment.this.mBtnSearch.setTextColor(-1);
        }
        label206: FindClassmateColleagueFragment.this.cancelTask(FindClassmateColleagueFragment.this.mGetMatchUniversityTask);
        FindClassmateColleagueFragment.this.mMatchIndex = -1;
        FindClassmateColleagueFragment.this.showNameList(false);
      }
    });
    this.mName.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        FindClassmateColleagueFragment.this.showNameList(false);
      }
    });
    this.mName.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramView, boolean paramBoolean)
      {
        if (paramBoolean)
        {
          FindClassmateColleagueFragment.this.mNameLayout.setBackgroundResource(2130838215);
          FindClassmateColleagueFragment.this.mNameLayout.setPadding(FindClassmateColleagueFragment.this.dipToPx(8.0F), 0, FindClassmateColleagueFragment.this.dipToPx(9.7F), 0);
          FindClassmateColleagueFragment.this.mNameTag.setTextColor(FindClassmateColleagueFragment.this.getActivity().getResources().getColor(2130839428));
          FindClassmateColleagueFragment.this.mYearDelete.setVisibility(8);
          if (!TextUtils.isEmpty(FindClassmateColleagueFragment.this.mName.getText().toString()))
          {
            FindClassmateColleagueFragment.this.mNameDelete.setVisibility(0);
            return;
          }
          FindClassmateColleagueFragment.this.mNameDelete.setVisibility(8);
          return;
        }
        FindClassmateColleagueFragment.this.mNameLayout.setBackgroundResource(2130838216);
        FindClassmateColleagueFragment.this.mNameLayout.setPadding(FindClassmateColleagueFragment.this.dipToPx(8.0F), 0, FindClassmateColleagueFragment.this.dipToPx(9.7F), 0);
        FindClassmateColleagueFragment.this.mNameTag.setTextColor(FindClassmateColleagueFragment.this.getActivity().getResources().getColor(2130839405));
      }
    });
    this.mName.requestFocus();
    this.mYear.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
      }

      public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
      {
        if (FindClassmateColleagueFragment.this.isNeedReturn())
          return;
        if (TextUtils.isEmpty(paramCharSequence))
        {
          FindClassmateColleagueFragment.this.mYearDelete.setVisibility(8);
          return;
        }
        FindClassmateColleagueFragment.this.mYearDelete.setVisibility(0);
      }
    });
    this.mYear.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramView, boolean paramBoolean)
      {
        if (paramBoolean)
        {
          FindClassmateColleagueFragment.this.mYearLayout.setBackgroundResource(2130838215);
          FindClassmateColleagueFragment.this.mYearLayout.setPadding(FindClassmateColleagueFragment.this.dipToPx(8.0F), 0, FindClassmateColleagueFragment.this.dipToPx(9.7F), 0);
          FindClassmateColleagueFragment.this.mYearTag.setTextColor(FindClassmateColleagueFragment.this.getActivity().getResources().getColor(2130839428));
          FindClassmateColleagueFragment.this.mNameDelete.setVisibility(8);
          if (!TextUtils.isEmpty(FindClassmateColleagueFragment.this.mYear.getText().toString()))
          {
            FindClassmateColleagueFragment.this.mYearDelete.setVisibility(0);
            return;
          }
          FindClassmateColleagueFragment.this.mYearDelete.setVisibility(8);
          return;
        }
        FindClassmateColleagueFragment.this.mYearLayout.setBackgroundResource(2130838216);
        FindClassmateColleagueFragment.this.mYearLayout.setPadding(FindClassmateColleagueFragment.this.dipToPx(8.0F), 0, FindClassmateColleagueFragment.this.dipToPx(9.7F), 0);
        FindClassmateColleagueFragment.this.mYearTag.setTextColor(FindClassmateColleagueFragment.this.getActivity().getResources().getColor(2130839405));
      }
    });
    this.mNameListView = ((ListView)getView().findViewById(2131362304));
    this.mNameListAdapter = new NameListAdapter();
    this.mNameListView.setAdapter(this.mNameListAdapter);
    this.mNameListView.setOnItemClickListener(this);
    updateView();
  }

  private void showNameList(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mNameListView.setVisibility(8);
      this.mHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          if (FindClassmateColleagueFragment.this.isNeedReturn())
            return;
          if ((FindClassmateColleagueFragment.this.mMathNameList == null) || (FindClassmateColleagueFragment.this.mMathNameList.size() == 0))
          {
            FindClassmateColleagueFragment.this.mNameListView.setVisibility(8);
            return;
          }
          FindClassmateColleagueFragment.this.mNameListView.setVisibility(0);
          FindClassmateColleagueFragment.this.mNameListAdapter.notifyDataSetChanged();
        }
      }
      , 50L);
      return;
    }
    this.mNameListView.setVisibility(8);
  }

  private void startCheckMatch()
  {
    cancelTask(this.mGetMatchUniversityTask);
    this.mGetMatchUniversityTask = new GetMatchUniversityTask();
    GetMatchUniversityTask localGetMatchUniversityTask = this.mGetMatchUniversityTask;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.mName.getText().toString();
    localGetMatchUniversityTask.execute(arrayOfString);
  }

  private void startSearch()
  {
    String str1 = this.mName.getText().toString();
    String str2 = "";
    if ((this.mMode == 1) && (this.mCurTabIndex != 2))
      str2 = this.mYear.getText().toString();
    Intent localIntent = new Intent(getActivity(), FindFriendListFragment.class);
    localIntent.putExtra("mode", FindFriendListFragment.MODE_SEARCH_CLASSMATE_COLLEAGUE);
    localIntent.putExtra("lable", str1);
    localIntent.putExtra("name", str1);
    localIntent.putExtra("year", str2);
    if (this.mMode == 2)
      localIntent.putExtra("company", str1);
    this.mUniversityId = getUniversityId(str1);
    localIntent.putExtra("universityId", this.mUniversityId);
    startFragment(localIntent, true, 1);
  }

  private void updateView()
  {
    if (TextUtils.isEmpty(this.mName.getText().toString()))
    {
      this.mNameDelete.setVisibility(8);
      this.mBtnSearch.setEnabled(false);
      this.mBtnSearch.setTextColor(2147483647);
    }
    if (TextUtils.isEmpty(this.mYear.getText().toString()))
      this.mYearDelete.setVisibility(8);
    if (this.mCurTabIndex == 2)
    {
      this.mYearLayout.setVisibility(8);
      return;
    }
    this.mYearLayout.setVisibility(0);
  }

  public void beforeTabChanged(int paramInt)
  {
  }

  public void doSelect(int paramInt)
  {
  }

  public String getUniversityId(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return "";
    int i = this.mUniversityNameList.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return "";
      if (paramString.equals((String)this.mUniversityNameList.get(j)))
        return (String)this.mUniversityIdList.get(j);
    }
  }

  public void onClick(View paramView)
  {
    if (paramView == this.mBtnLeft)
      finish();
    do
    {
      return;
      if (paramView == this.mNameDelete)
      {
        this.mName.setText("");
        return;
      }
      if (paramView != this.mYearDelete)
        continue;
      this.mYear.setText("");
      return;
    }
    while (paramView != this.mBtnSearch);
    startSearch();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
      this.mMode = localBundle.getInt("mode");
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903112, paramViewGroup, false);
  }

  public void onDestroy()
  {
    this.mUniversityNameList.clear();
    this.mUniversityIdList.clear();
    super.onDestroy();
  }

  public void onDestroyView()
  {
    cancelTask(this.mLoadUniversityDataTask);
    cancelTask(this.mGetMatchUniversityTask);
    super.onDestroyView();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    this.mMatchIndex = paramInt;
    this.mNameListAdapter.notifyDataSetChanged();
    this.mName.setText((CharSequence)this.mMathNameList.get(this.mMatchIndex));
    try
    {
      Editable localEditable = this.mName.getText();
      Selection.setSelection(localEditable, localEditable.length());
      showNameList(false);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void onTabChanged(int paramInt)
  {
    this.mCurTabIndex = paramInt;
    if (this.mCurTabIndex != 2)
      showNameList(false);
    updateView();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    initTitle(getView());
    initView();
    if (!dataInited())
    {
      initData();
      this.mLoadUniversityDataTask = new LoadUniversityDataTask();
      this.mLoadUniversityDataTask.execute(new Void[0]);
    }
    this.mHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        if (FindClassmateColleagueFragment.this.isNeedReturn())
          return;
        FindClassmateColleagueFragment.this.showKeyboard();
      }
    }
    , 100L);
  }

  public class GetMatchUniversityTask extends KXFragment.KXAsyncTask<String, Void, Integer>
  {
    private ArrayList<String> ids = new ArrayList();
    private ArrayList<String> names = new ArrayList();

    public GetMatchUniversityTask()
    {
      super();
    }

    protected Integer doInBackgroundA(String[] paramArrayOfString)
    {
      String str1 = paramArrayOfString[0];
      int i = FindClassmateColleagueFragment.this.mUniversityNameList.size();
      for (int j = 0; ; j++)
      {
        if (j >= i)
          return Integer.valueOf(1);
        String str2 = (String)FindClassmateColleagueFragment.this.mUniversityNameList.get(j);
        if (!FindClassmateColleagueFragment.containStr(str2, str1))
          continue;
        this.names.add(str2);
        this.ids.add((String)FindClassmateColleagueFragment.this.mUniversityIdList.get(j));
      }
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
      if ((FindClassmateColleagueFragment.this.getActivity() == null) || (FindClassmateColleagueFragment.this.getView() == null))
        return;
      FindClassmateColleagueFragment.this.mMathNameList.clear();
      FindClassmateColleagueFragment.this.mMatchIdList.clear();
      FindClassmateColleagueFragment.this.mMathNameList.addAll(this.names);
      FindClassmateColleagueFragment.this.mMatchIdList.addAll(this.ids);
      FindClassmateColleagueFragment.this.mMatchIndex = -1;
      FindClassmateColleagueFragment.this.showNameList(true);
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  public class LoadUniversityDataTask extends KXFragment.KXAsyncTask<Void, Void, Integer>
  {
    public LoadUniversityDataTask()
    {
      super();
    }

    // ERROR //
    protected Integer doInBackgroundA(Void[] paramArrayOfVoid)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_2
      //   2: aload_0
      //   3: getfield 11	com/kaixin001/fragment/FindClassmateColleagueFragment$LoadUniversityDataTask:this$0	Lcom/kaixin001/fragment/FindClassmateColleagueFragment;
      //   6: invokestatic 26	com/kaixin001/fragment/FindClassmateColleagueFragment:access$0	(Lcom/kaixin001/fragment/FindClassmateColleagueFragment;)Ljava/util/ArrayList;
      //   9: invokevirtual 32	java/util/ArrayList:clear	()V
      //   12: aload_0
      //   13: getfield 11	com/kaixin001/fragment/FindClassmateColleagueFragment$LoadUniversityDataTask:this$0	Lcom/kaixin001/fragment/FindClassmateColleagueFragment;
      //   16: invokestatic 35	com/kaixin001/fragment/FindClassmateColleagueFragment:access$1	(Lcom/kaixin001/fragment/FindClassmateColleagueFragment;)Ljava/util/ArrayList;
      //   19: invokevirtual 32	java/util/ArrayList:clear	()V
      //   22: new 37	java/io/DataInputStream
      //   25: dup
      //   26: aload_0
      //   27: getfield 11	com/kaixin001/fragment/FindClassmateColleagueFragment$LoadUniversityDataTask:this$0	Lcom/kaixin001/fragment/FindClassmateColleagueFragment;
      //   30: invokevirtual 41	com/kaixin001/fragment/FindClassmateColleagueFragment:getResources	()Landroid/content/res/Resources;
      //   33: invokevirtual 47	android/content/res/Resources:getAssets	()Landroid/content/res/AssetManager;
      //   36: ldc 49
      //   38: invokevirtual 55	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
      //   41: invokespecial 58	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
      //   44: astore_3
      //   45: aload_3
      //   46: invokevirtual 62	java/io/DataInputStream:readInt	()I
      //   49: istore 8
      //   51: iconst_0
      //   52: istore 9
      //   54: iload 9
      //   56: iload 8
      //   58: if_icmplt +13 -> 71
      //   61: aload_3
      //   62: ifnull +120 -> 182
      //   65: aload_3
      //   66: invokevirtual 65	java/io/DataInputStream:close	()V
      //   69: aconst_null
      //   70: areturn
      //   71: aload_0
      //   72: getfield 11	com/kaixin001/fragment/FindClassmateColleagueFragment$LoadUniversityDataTask:this$0	Lcom/kaixin001/fragment/FindClassmateColleagueFragment;
      //   75: invokestatic 26	com/kaixin001/fragment/FindClassmateColleagueFragment:access$0	(Lcom/kaixin001/fragment/FindClassmateColleagueFragment;)Ljava/util/ArrayList;
      //   78: aload_3
      //   79: invokevirtual 69	java/io/DataInputStream:readUTF	()Ljava/lang/String;
      //   82: invokevirtual 73	java/util/ArrayList:add	(Ljava/lang/Object;)Z
      //   85: pop
      //   86: aload_0
      //   87: getfield 11	com/kaixin001/fragment/FindClassmateColleagueFragment$LoadUniversityDataTask:this$0	Lcom/kaixin001/fragment/FindClassmateColleagueFragment;
      //   90: invokestatic 35	com/kaixin001/fragment/FindClassmateColleagueFragment:access$1	(Lcom/kaixin001/fragment/FindClassmateColleagueFragment;)Ljava/util/ArrayList;
      //   93: aload_3
      //   94: invokevirtual 69	java/io/DataInputStream:readUTF	()Ljava/lang/String;
      //   97: invokevirtual 73	java/util/ArrayList:add	(Ljava/lang/Object;)Z
      //   100: pop
      //   101: iinc 9 1
      //   104: goto -50 -> 54
      //   107: astore 4
      //   109: aload 4
      //   111: invokevirtual 76	java/lang/Exception:printStackTrace	()V
      //   114: aload_2
      //   115: ifnull -46 -> 69
      //   118: aload_2
      //   119: invokevirtual 65	java/io/DataInputStream:close	()V
      //   122: goto -53 -> 69
      //   125: astore 7
      //   127: aload 7
      //   129: invokevirtual 77	java/io/IOException:printStackTrace	()V
      //   132: goto -10 -> 122
      //   135: astore 5
      //   137: aload_2
      //   138: ifnull +7 -> 145
      //   141: aload_2
      //   142: invokevirtual 65	java/io/DataInputStream:close	()V
      //   145: aload 5
      //   147: athrow
      //   148: astore 6
      //   150: aload 6
      //   152: invokevirtual 77	java/io/IOException:printStackTrace	()V
      //   155: goto -10 -> 145
      //   158: astore 12
      //   160: aload 12
      //   162: invokevirtual 77	java/io/IOException:printStackTrace	()V
      //   165: goto -96 -> 69
      //   168: astore 5
      //   170: aload_3
      //   171: astore_2
      //   172: goto -35 -> 137
      //   175: astore 4
      //   177: aload_3
      //   178: astore_2
      //   179: goto -70 -> 109
      //   182: goto -113 -> 69
      //
      // Exception table:
      //   from	to	target	type
      //   22	45	107	java/lang/Exception
      //   118	122	125	java/io/IOException
      //   22	45	135	finally
      //   109	114	135	finally
      //   141	145	148	java/io/IOException
      //   65	69	158	java/io/IOException
      //   45	51	168	finally
      //   71	101	168	finally
      //   45	51	175	java/lang/Exception
      //   71	101	175	java/lang/Exception
    }

    protected void onPostExecuteA(Integer paramInteger)
    {
    }

    protected void onPreExecuteA()
    {
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  public class NameListAdapter extends BaseAdapter
  {
    public NameListAdapter()
    {
    }

    public int getCount()
    {
      return FindClassmateColleagueFragment.this.mMathNameList.size();
    }

    public Object getItem(int paramInt)
    {
      return FindClassmateColleagueFragment.this.mMathNameList.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
        paramView = ((LayoutInflater)FindClassmateColleagueFragment.this.getActivity().getSystemService("layout_inflater")).inflate(2130903297, null);
      String str = (String)FindClassmateColleagueFragment.this.mMathNameList.get(paramInt);
      TextView localTextView = (TextView)paramView.findViewById(2131362400);
      if (!TextUtils.isEmpty(str))
      {
        localTextView.setText(str);
        localTextView.setVisibility(0);
        return paramView;
      }
      localTextView.setVisibility(8);
      return paramView;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.FindClassmateColleagueFragment
 * JD-Core Version:    0.6.0
 */