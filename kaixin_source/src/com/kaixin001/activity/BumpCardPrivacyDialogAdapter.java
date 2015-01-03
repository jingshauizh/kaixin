package com.kaixin001.activity;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kaixin001.item.UserCard;
import java.util.ArrayList;

public class BumpCardPrivacyDialogAdapter extends BaseAdapter
{
  private ArrayList<ListData> items;
  private Context mContext;

  public BumpCardPrivacyDialogAdapter(Context paramContext, UserCard paramUserCard)
  {
    this.mContext = paramContext;
    this.items = buildItemDataFromCard(paramUserCard);
  }

  private ArrayList<ListData> buildItemDataFromCard(UserCard paramUserCard)
  {
    ArrayList localArrayList = new ArrayList();
    ListData localListData1 = new ListData(null);
    localListData1.showType = -1;
    localArrayList.add(localListData1);
    if (!TextUtils.isEmpty(paramUserCard.mobile))
    {
      ListData localListData2 = new ListData(null);
      localListData2.showType = 2;
      localListData2.isCurrentHide = paramUserCard.isMobileHide();
      localArrayList.add(localListData2);
    }
    if (!TextUtils.isEmpty(paramUserCard.phone))
    {
      ListData localListData3 = new ListData(null);
      localListData3.showType = 1;
      localListData3.isCurrentHide = paramUserCard.isPhoneHide();
      localArrayList.add(localListData3);
    }
    if (!TextUtils.isEmpty(paramUserCard.email))
    {
      ListData localListData4 = new ListData(null);
      localListData4.showType = 3;
      localListData4.isCurrentHide = paramUserCard.isEmailHide();
      localArrayList.add(localListData4);
    }
    return localArrayList;
  }

  public int getCount()
  {
    if (this.items == null)
      return 0;
    return this.items.size();
  }

  public ListData getItem(int paramInt)
  {
    return (ListData)this.items.get(paramInt);
  }

  public void getItemAction(String[] paramArrayOfString, int paramInt)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length < 2) || (paramInt <= 0));
    ListData localListData;
    do
    {
      return;
      localListData = (ListData)this.items.get(paramInt);
    }
    while (localListData == null);
    switch (localListData.showType)
    {
    default:
    case 3:
    case 2:
    case 1:
    }
    while (localListData.isCurrentHide)
    {
      paramArrayOfString[1] = "unhide";
      return;
      paramArrayOfString[0] = "email";
      continue;
      paramArrayOfString[0] = "mobile";
      continue;
      paramArrayOfString[0] = "phone";
    }
    paramArrayOfString[1] = "hide";
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ListData localListData = (ListData)this.items.get(paramInt);
    View localView = LayoutInflater.from(this.mContext).inflate(2130903190, null);
    TextView localTextView = (TextView)localView.findViewById(2131362939);
    Resources localResources = this.mContext.getResources();
    if (localListData.isCurrentHide);
    for (String str1 = localResources.getString(2131428095); ; str1 = localResources.getString(2131428094))
      switch (localListData.showType)
      {
      default:
        localTextView.setText(2131428099);
        return localView;
      case 3:
      case 2:
      case 1:
      }
    String str4 = localResources.getString(2131428124);
    localTextView.setText(str1 + str4);
    return localView;
    String str3 = localResources.getString(2131428098);
    localTextView.setText(str1 + str3);
    return localView;
    String str2 = localResources.getString(2131428097);
    localTextView.setText(str1 + str2);
    return localView;
  }

  public void updateCardPrivacy(UserCard paramUserCard)
  {
    this.items = buildItemDataFromCard(paramUserCard);
    notifyDataSetChanged();
  }

  private class ListData
  {
    public boolean isCurrentHide;
    public int showType;

    private ListData()
    {
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.activity.BumpCardPrivacyDialogAdapter
 * JD-Core Version:    0.6.0
 */