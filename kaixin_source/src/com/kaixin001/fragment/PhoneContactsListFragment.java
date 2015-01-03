package com.kaixin001.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;
import com.kaixin001.model.PhoneContactInfo;
import com.kaixin001.model.Setting;
import com.kaixin001.util.CloseUtil;
import com.kaixin001.util.CrashRecoverUtil;
import com.kaixin001.util.DialogUtil;
import com.kaixin001.util.ImageCache;
import com.kaixin001.util.IntentUtil;
import com.kaixin001.util.KXLog;
import com.kaixin001.util.PinYinUtils;
import com.kaixin001.view.AlphabetIndexerBar;
import com.kaixin001.view.AlphabetIndexerBar.OnIndexerChangedListener;
import java.io.ByteArrayOutputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class PhoneContactsListFragment extends BaseFragment
  implements View.OnClickListener
{
  private static final String TAG = "PhoneContactsListActivity";
  private PhoneContactAdapter mAdapter = null;
  private int mCheckedPosition = -1;
  private String mFlogo;
  private GetPhoneContactsTask mGetPhoneContactsTask = null;
  private ImageView mLeftButton = null;
  private FrameLayout mPhoneContactLayoutList = null;
  private final ArrayList<PhoneContactInfo> mPhoneContactList = new ArrayList();
  private ListView mPhoneContactsListview = null;
  private ImageView mRightButton = null;
  private TextView mTvListEmpty = null;
  private AlphabetIndexerBar mViewAlphabetIndexer = null;

  private byte[] Bitmap2Bytes(Bitmap paramBitmap)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
    return localByteArrayOutputStream.toByteArray();
  }

  private boolean checkIfPhotoIsNull()
  {
    try
    {
      ContentResolver localContentResolver = getActivity().getContentResolver();
      Long localLong = Long.valueOf(((PhoneContactInfo)this.mPhoneContactList.get(this.mCheckedPosition)).getId());
      String str = "raw_contact_id = " + localLong + " AND " + "mimetype" + "='" + "vnd.android.cursor.item/photo" + "'";
      Cursor localCursor = localContentResolver.query(ContactsContract.Data.CONTENT_URI, null, str, null, null);
      if (localCursor.getCount() > 0)
      {
        localCursor.close();
        return false;
      }
      if (localCursor != null)
      {
        localCursor.close();
        return true;
      }
    }
    catch (Exception localException)
    {
    }
    return true;
  }

  private boolean getLocalContact(int paramInt)
  {
    Cursor localCursor1 = null;
    try
    {
      ContentResolver localContentResolver = getActivity().getContentResolver();
      boolean bool1 = Setting.getInstance().getCType().startsWith("21703");
      localCursor1 = null;
      if ((bool1) || (Setting.getInstance().getCType().startsWith("06203")))
      {
        localCursor1 = localContentResolver.query(ContactsContract.RawContacts.CONTENT_URI, null, null, null, null);
        if ((localCursor1 != null) && (localCursor1.getCount() > 0))
        {
          localCursor1.moveToFirst();
          label78: if (1 != localCursor1.getInt(localCursor1.getColumnIndex("deleted")))
            break label176;
        }
      }
      while (true)
      {
        boolean bool2 = localCursor1.moveToNext();
        if (bool2)
          break label78;
        label176: String str1;
        do
        {
          Cursor localCursor2;
          do
          {
            return true;
            boolean bool3 = Build.BRAND.contains("google");
            localCursor1 = null;
            if (bool3)
            {
              localCursor1 = localContentResolver.query(ContactsContract.RawContacts.CONTENT_URI, null, "account_type <> 'com.anddroid.contacts.sim' AND display_name <> ''", null, null);
              break;
            }
            localCursor1 = localContentResolver.query(ContactsContract.RawContacts.CONTENT_URI, null, "account_type <> 'com.anddroid.contacts.sim'  AND account_type <> 'com.google' AND display_name <> ''", null, null);
            break;
            localPhoneContactInfo = new PhoneContactInfo();
            localPhoneContactInfo.setContactName(localCursor1.getString(localCursor1.getColumnIndex("display_name")));
            localPhoneContactInfo.setId(localCursor1.getInt(localCursor1.getColumnIndex("_id")));
            localPhoneContactInfo.setIsChecked(Boolean.valueOf(false));
            if (paramInt >= 0)
              break label426;
            localCursor2 = localContentResolver.query(ContactsContract.RawContacts.CONTENT_URI, new String[] { "sort_key" }, "display_name='" + localPhoneContactInfo.getContactName() + "'", null, null);
          }
          while (localCursor2 == null);
          localCursor2.moveToFirst();
          str1 = localCursor2.getString(localCursor2.getColumnIndex("sort_key"));
        }
        while (str1 == null);
        arrayOfString = str1.split(" ");
        localStringBuffer = new StringBuffer();
        i = 0;
        if (i < arrayOfString.length)
          break label409;
        localObject2 = localStringBuffer.toString();
        localPhoneContactInfo.setContactNamePinyin((String)localObject2);
        this.mPhoneContactList.add(localPhoneContactInfo);
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        PhoneContactInfo localPhoneContactInfo;
        String[] arrayOfString;
        StringBuffer localStringBuffer;
        int i;
        KXLog.e("PhoneContactsListActivity", localException.getMessage());
        return false;
        label409: localStringBuffer.append(arrayOfString[i]);
        i += 2;
        continue;
        label426: String str2 = PinYinUtils.getInstance().getPinYinString(localPhoneContactInfo.getContactName());
        Object localObject2 = str2;
      }
    }
    finally
    {
      CloseUtil.close(localCursor1);
      if (localCursor1 != null)
        localCursor1.close();
    }
    throw localObject1;
  }

  private void preSetContactPhoto()
  {
    if (!TextUtils.isEmpty(this.mFlogo))
    {
      Bitmap localBitmap = ImageCache.getInstance().createSafeImage(this.mFlogo);
      if (localBitmap != null)
        if (setContactPhoto(getActivity().getContentResolver(), Bitmap2Bytes(localBitmap), Long.valueOf(((PhoneContactInfo)this.mPhoneContactList.get(this.mCheckedPosition)).getId()).longValue()))
          Toast.makeText(getActivity(), 2131427880, 0).show();
    }
    while (true)
    {
      finish();
      return;
      Toast.makeText(getActivity(), 2131427881, 0).show();
      continue;
      Toast.makeText(getActivity(), 2131427881, 0).show();
      continue;
      Toast.makeText(getActivity(), 2131427881, 0).show();
    }
  }

  private void setTitleBar(View paramView)
  {
    this.mLeftButton = ((ImageView)paramView.findViewById(2131362914));
    this.mLeftButton.setVisibility(0);
    this.mLeftButton.setOnClickListener(this);
    this.mRightButton = ((ImageView)paramView.findViewById(2131362928));
    this.mRightButton.setImageResource(2130839022);
    this.mRightButton.setVisibility(8);
    this.mRightButton.setOnClickListener(this);
    ((ImageView)paramView.findViewById(2131362919)).setVisibility(8);
    TextView localTextView = (TextView)paramView.findViewById(2131362920);
    localTextView.setText(getResources().getString(2131427878));
    localTextView.setVisibility(0);
    getResources().getString(2131427597);
  }

  private void showMainView(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mPhoneContactsListview.setVisibility(0);
      this.mTvListEmpty.setVisibility(8);
      return;
    }
    this.mPhoneContactsListview.setVisibility(8);
    this.mTvListEmpty.setVisibility(0);
  }

  private void showProgressBar(boolean paramBoolean)
  {
    View localView = getView().findViewById(2131362183);
    if (paramBoolean)
    {
      localView.setVisibility(0);
      return;
    }
    localView.setVisibility(8);
  }

  public void onClick(View paramView)
  {
    if (paramView.equals(this.mLeftButton))
    {
      this.mGetPhoneContactsTask.cancel(true);
      finish();
    }
    do
      return;
    while (!paramView.equals(this.mRightButton));
    if (checkIfPhotoIsNull())
    {
      preSetContactPhoto();
      return;
    }
    DialogUtil.showMsgDlgLite(getActivity(), 2131427879, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        PhoneContactsListFragment.this.preSetContactPhoto();
        paramDialogInterface.dismiss();
      }
    });
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (CrashRecoverUtil.isCrashBefore())
    {
      CrashRecoverUtil.crashRecover(getActivity().getApplicationContext());
      IntentUtil.returnToDesktop(getActivity());
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903270, paramViewGroup, false);
  }

  public void onDestroy()
  {
    super.onDestroy();
    cancelTask(this.mGetPhoneContactsTask);
    this.mPhoneContactList.clear();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle != null)
      this.mFlogo = localBundle.getString("flogo");
    this.mViewAlphabetIndexer = ((AlphabetIndexerBar)paramView.findViewById(2131362143));
    this.mViewAlphabetIndexer.showSearchIcon(false);
    this.mViewAlphabetIndexer.setVisibility(8);
    this.mViewAlphabetIndexer.setOnIndexerChangedListener(new AlphabetIndexerBar.OnIndexerChangedListener()
    {
      public void onIndexerChanged(String paramString)
      {
        if (paramString == null);
        Object[] arrayOfObject;
        do
        {
          return;
          arrayOfObject = PhoneContactsListFragment.this.mAdapter.getSections();
        }
        while ((arrayOfObject == null) || (arrayOfObject.length == 0));
        for (int i = -1 + arrayOfObject.length; ; i--)
        {
          int j = 0;
          if (i < 0);
          while (true)
          {
            PhoneContactsListFragment.this.mPhoneContactsListview.setSelection(j);
            return;
            if (paramString.compareTo(arrayOfObject[i].toString()) < 0)
              break;
            j = PhoneContactsListFragment.this.mAdapter.getPositionForSection(i);
          }
        }
      }
    });
    setTitleBar(paramView);
    this.mPhoneContactLayoutList = ((FrameLayout)paramView.findViewById(2131363332));
    this.mPhoneContactLayoutList.setVisibility(8);
    this.mPhoneContactsListview = ((ListView)paramView.findViewById(2131363333));
    this.mTvListEmpty = ((TextView)paramView.findViewById(2131363331));
    this.mGetPhoneContactsTask = new GetPhoneContactsTask();
    this.mGetPhoneContactsTask.execute(new Void[] { null });
    this.mPhoneContactsListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
      {
        RadioButton localRadioButton = (RadioButton)((LinearLayout)paramView).findViewById(2131363329);
        if (PhoneContactsListFragment.this.mCheckedPosition != paramInt)
        {
          localRadioButton.setChecked(true);
          PhoneContactsListFragment.PhoneContactAdapter.access$0(PhoneContactsListFragment.this.mAdapter, PhoneContactsListFragment.this.mCheckedPosition, paramInt);
          if (PhoneContactsListFragment.this.mCheckedPosition != -1)
          {
            int i = paramAdapterView.getLastVisiblePosition();
            int j = 1 + (i - paramAdapterView.getChildCount());
            if ((PhoneContactsListFragment.this.mCheckedPosition >= j) && (PhoneContactsListFragment.this.mCheckedPosition <= i))
              ((PhoneContactsListFragment.PhoneContactsViewHolder)((LinearLayout)paramAdapterView.getChildAt(PhoneContactsListFragment.this.mCheckedPosition - j)).getTag()).radioBtn.setChecked(false);
          }
          PhoneContactsListFragment.this.mCheckedPosition = paramInt;
        }
        PhoneContactsListFragment.this.mRightButton.setVisibility(0);
      }
    });
  }

  public boolean setContactPhoto(ContentResolver paramContentResolver, byte[] paramArrayOfByte, long paramLong)
  {
    try
    {
      ContentValues localContentValues = new ContentValues();
      int i = -1;
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("raw_contact_id").append("=").append(paramLong).append(" AND ").append("mimetype").append("='").append("vnd.android.cursor.item/photo").append("'");
      Cursor localCursor = paramContentResolver.query(ContactsContract.Data.CONTENT_URI, null, localStringBuffer.toString(), null, null);
      int j = localCursor.getColumnIndexOrThrow("_id");
      if (localCursor.moveToFirst())
        i = localCursor.getInt(j);
      localContentValues.put("raw_contact_id", Long.valueOf(paramLong));
      localContentValues.put("is_super_primary", Integer.valueOf(1));
      localContentValues.put("data15", paramArrayOfByte);
      localContentValues.put("mimetype", "vnd.android.cursor.item/photo");
      if (i >= 0)
        paramContentResolver.update(ContactsContract.Data.CONTENT_URI, localContentValues, "_id = " + i, null);
      while (localCursor != null)
      {
        localCursor.close();
        break;
        paramContentResolver.insert(ContactsContract.Data.CONTENT_URI, localContentValues);
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      KXLog.e("PhoneContactsListActivity", localIllegalArgumentException.getMessage());
      return false;
    }
    return true;
  }

  public class GetPhoneContactsTask extends KXFragment.KXAsyncTask<Void, Void, Boolean>
  {
    public GetPhoneContactsTask()
    {
      super();
    }

    protected Boolean doInBackgroundA(Void[] paramArrayOfVoid)
    {
      int i = Integer.parseInt(Build.VERSION.SDK);
      boolean bool = PhoneContactsListFragment.this.getLocalContact(i);
      if (isCancelled())
        return null;
      return Boolean.valueOf(bool);
    }

    protected void onPostExecuteA(Boolean paramBoolean)
    {
      if (paramBoolean.booleanValue())
      {
        PhoneContactsListFragment.this.showProgressBar(false);
        PhoneContactsListFragment.this.mPhoneContactLayoutList.setVisibility(0);
        PhoneContactsListFragment.SortComparator localSortComparator = new PhoneContactsListFragment.SortComparator();
        Collections.sort(PhoneContactsListFragment.this.mPhoneContactList, localSortComparator);
        if (PhoneContactsListFragment.this.mPhoneContactList.size() > 0)
          PhoneContactsListFragment.this.mViewAlphabetIndexer.setVisibility(0);
        while (true)
        {
          PhoneContactsListFragment.this.mAdapter = new PhoneContactsListFragment.PhoneContactAdapter(PhoneContactsListFragment.this, PhoneContactsListFragment.this.getActivity(), PhoneContactsListFragment.this.mPhoneContactList);
          PhoneContactsListFragment.this.mPhoneContactsListview.setAdapter(PhoneContactsListFragment.this.mAdapter);
          PhoneContactsListFragment.this.mAdapter.notifyDataSetChanged();
          return;
          PhoneContactsListFragment.this.showMainView(false);
        }
      }
      PhoneContactsListFragment.this.showProgressBar(false);
      Toast.makeText(PhoneContactsListFragment.this.getActivity(), 2131427885, 1).show();
    }

    protected void onPreExecuteA()
    {
      PhoneContactsListFragment.this.showProgressBar(true);
    }

    protected void onProgressUpdateA(Void[] paramArrayOfVoid)
    {
    }
  }

  public class PhoneContactAdapter extends BaseAdapter
    implements SectionIndexer
  {
    private ArrayList<PhoneContactInfo> itemList;
    private LayoutInflater mInflater;
    private ArrayList<Integer> mListSecPos = new ArrayList();
    private ArrayList<String> mListSections = new ArrayList();

    public PhoneContactAdapter(ArrayList<PhoneContactInfo> arg2)
    {
      Object localObject1;
      this.mInflater = ((LayoutInflater)localObject1.getSystemService("layout_inflater"));
      Object localObject2;
      this.itemList = localObject2;
    }

    private void updateData(int paramInt1, int paramInt2)
    {
      if (paramInt1 != -1)
        ((PhoneContactInfo)this.itemList.get(paramInt1)).setIsChecked(Boolean.valueOf(false));
      ((PhoneContactInfo)this.itemList.get(paramInt2)).setIsChecked(Boolean.valueOf(true));
    }

    public int getCount()
    {
      return this.itemList.size();
    }

    public Object getItem(int paramInt)
    {
      return Integer.valueOf(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public ArrayList<PhoneContactInfo> getItemList()
    {
      return this.itemList;
    }

    public int getPositionForSection(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= this.mListSecPos.size()))
        return 0;
      return ((Integer)this.mListSecPos.get(paramInt)).intValue();
    }

    public int getSectionForPosition(int paramInt)
    {
      int i = 0;
      if (paramInt < 0)
        i = 0;
      while (true)
      {
        return i;
        if (paramInt >= PhoneContactsListFragment.this.mPhoneContactList.size())
          return -1 + this.mListSecPos.size();
        for (int j = 0; (j < this.mListSecPos.size()) && (paramInt >= ((Integer)this.mListSecPos.get(j)).intValue()); j++)
          i = j;
      }
    }

    public Object[] getSections()
    {
      return this.mListSections.toArray();
    }

    // ERROR //
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 45	com/kaixin001/fragment/PhoneContactsListFragment$PhoneContactAdapter:itemList	Ljava/util/ArrayList;
      //   4: iload_1
      //   5: invokevirtual 55	java/util/ArrayList:get	(I)Ljava/lang/Object;
      //   8: checkcast 57	com/kaixin001/model/PhoneContactInfo
      //   11: astore 4
      //   13: aload_2
      //   14: ifnonnull +106 -> 120
      //   17: aload_0
      //   18: getfield 43	com/kaixin001/fragment/PhoneContactsListFragment$PhoneContactAdapter:mInflater	Landroid/view/LayoutInflater;
      //   21: ldc 104
      //   23: aconst_null
      //   24: invokevirtual 108	android/view/LayoutInflater:inflate	(ILandroid/view/ViewGroup;)Landroid/view/View;
      //   27: astore_2
      //   28: new 110	com/kaixin001/fragment/PhoneContactsListFragment$PhoneContactsViewHolder
      //   31: dup
      //   32: aload_2
      //   33: invokespecial 113	com/kaixin001/fragment/PhoneContactsListFragment$PhoneContactsViewHolder:<init>	(Landroid/view/View;)V
      //   36: astore 9
      //   38: aload_2
      //   39: aload 9
      //   41: invokevirtual 119	android/view/View:setTag	(Ljava/lang/Object;)V
      //   44: aload 9
      //   46: astore 6
      //   48: aload 6
      //   50: aload_2
      //   51: aload 4
      //   53: invokestatic 122	com/kaixin001/fragment/PhoneContactsListFragment$PhoneContactsViewHolder:access$0	(Lcom/kaixin001/fragment/PhoneContactsListFragment$PhoneContactsViewHolder;Landroid/view/View;Lcom/kaixin001/model/PhoneContactInfo;)V
      //   56: aload_0
      //   57: iload_1
      //   58: invokevirtual 124	com/kaixin001/fragment/PhoneContactsListFragment$PhoneContactAdapter:getSectionForPosition	(I)I
      //   61: istore 7
      //   63: iload_1
      //   64: aload_0
      //   65: iload 7
      //   67: invokevirtual 126	com/kaixin001/fragment/PhoneContactsListFragment$PhoneContactAdapter:getPositionForSection	(I)I
      //   70: if_icmpne +89 -> 159
      //   73: aload_0
      //   74: getfield 29	com/kaixin001/fragment/PhoneContactsListFragment$PhoneContactAdapter:mListSections	Ljava/util/ArrayList;
      //   77: iload 7
      //   79: invokevirtual 55	java/util/ArrayList:get	(I)Ljava/lang/Object;
      //   82: checkcast 128	java/lang/String
      //   85: ldc 130
      //   87: invokevirtual 134	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   90: ifne +69 -> 159
      //   93: iconst_1
      //   94: istore 8
      //   96: iload 8
      //   98: ifeq +34 -> 132
      //   101: aload 6
      //   103: aload_0
      //   104: getfield 29	com/kaixin001/fragment/PhoneContactsListFragment$PhoneContactAdapter:mListSections	Ljava/util/ArrayList;
      //   107: iload 7
      //   109: invokevirtual 55	java/util/ArrayList:get	(I)Ljava/lang/Object;
      //   112: checkcast 128	java/lang/String
      //   115: invokevirtual 138	com/kaixin001/fragment/PhoneContactsListFragment$PhoneContactsViewHolder:setSection	(Ljava/lang/String;)V
      //   118: aload_2
      //   119: areturn
      //   120: aload_2
      //   121: invokevirtual 142	android/view/View:getTag	()Ljava/lang/Object;
      //   124: checkcast 110	com/kaixin001/fragment/PhoneContactsListFragment$PhoneContactsViewHolder
      //   127: astore 6
      //   129: goto -81 -> 48
      //   132: aload 6
      //   134: aconst_null
      //   135: invokevirtual 138	com/kaixin001/fragment/PhoneContactsListFragment$PhoneContactsViewHolder:setSection	(Ljava/lang/String;)V
      //   138: aload_2
      //   139: areturn
      //   140: astore 5
      //   142: ldc 144
      //   144: aload 5
      //   146: invokevirtual 148	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   149: invokestatic 154	com/kaixin001/util/KXLog:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   152: aload_2
      //   153: areturn
      //   154: astore 5
      //   156: goto -14 -> 142
      //   159: iconst_0
      //   160: istore 8
      //   162: goto -66 -> 96
      //
      // Exception table:
      //   from	to	target	type
      //   17	38	140	java/lang/Exception
      //   48	93	140	java/lang/Exception
      //   101	118	140	java/lang/Exception
      //   120	129	140	java/lang/Exception
      //   132	138	140	java/lang/Exception
      //   38	44	154	java/lang/Exception
    }

    public void notifyDataSetChanged()
    {
      this.mListSections.clear();
      this.mListSecPos.clear();
      Object localObject1 = "-1";
      int i = 0;
      if (i >= PhoneContactsListFragment.this.mPhoneContactList.size())
      {
        super.notifyDataSetChanged();
        return;
      }
      String str1 = ((PhoneContactInfo)PhoneContactsListFragment.this.mPhoneContactList.get(i)).getContactNamePinyin();
      String str2;
      if ((str1 == null) || (str1.length() == 0))
      {
        str2 = "";
        label71: if (!TextUtils.isEmpty(str2))
          break label127;
      }
      label127: for (Object localObject2 = localObject1; ; localObject2 = str2.substring(0, 1).toUpperCase())
      {
        if (!((String)localObject2).equals(localObject1))
        {
          localObject1 = localObject2;
          this.mListSections.add(localObject1);
          this.mListSecPos.add(Integer.valueOf(i));
        }
        i++;
        break;
        str2 = str1;
        break label71;
      }
    }

    public void setItemList(ArrayList<PhoneContactInfo> paramArrayList)
    {
      this.itemList = paramArrayList;
    }
  }

  private static class PhoneContactsViewHolder
  {
    TextView phoneContactname;
    RadioButton radioBtn;
    TextView tvSection;
    TextView txtSection;

    public PhoneContactsViewHolder(View paramView)
    {
      this.phoneContactname = ((TextView)paramView.findViewById(2131363330));
      this.radioBtn = ((RadioButton)paramView.findViewById(2131363329));
      this.txtSection = ((TextView)paramView.findViewById(2131362144));
      this.txtSection.setClickable(false);
      this.tvSection = ((TextView)paramView.findViewById(2131362144));
    }

    private void setPhoneContactsItem(View paramView, PhoneContactInfo paramPhoneContactInfo)
    {
      this.phoneContactname.setText(paramPhoneContactInfo.getContactName());
      this.radioBtn.setChecked(paramPhoneContactInfo.getIsChecked().booleanValue());
    }

    public void setSection(String paramString)
    {
      if (TextUtils.isEmpty(paramString))
      {
        this.tvSection.setVisibility(8);
        return;
      }
      this.tvSection.setVisibility(0);
      this.tvSection.setText(paramString);
    }
  }

  public static class SortComparator
    implements Comparator<Object>
  {
    public int compare(Object paramObject1, Object paramObject2)
    {
      PhoneContactInfo localPhoneContactInfo1 = (PhoneContactInfo)paramObject1;
      PhoneContactInfo localPhoneContactInfo2 = (PhoneContactInfo)paramObject2;
      return Collator.getInstance(Locale.CHINA).compare(localPhoneContactInfo1.getContactNamePinyin(), localPhoneContactInfo2.getContactNamePinyin());
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.PhoneContactsListFragment
 * JD-Core Version:    0.6.0
 */