package com.kaixin001.util;

import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.widget.EditText;
import com.kaixin001.model.FriendsModel;
import com.kaixin001.model.FriendsModel.Friend;
import com.kaixin001.model.Setting;
import java.util.ArrayList;
import java.util.HashMap;

public class FriendsUtil
{
  private static final String TAG = "FriendsUtil";
  private static final String TAG_AT = "@";
  private static final String TAG_NAME_LEFT = "(#";
  private static final String TAG_NAME_RIGHT = "#)";

  private static void addFriendList(FriendsModel.Friend paramFriend, ArrayList<FriendsModel.Friend> paramArrayList)
  {
    String str = paramFriend.getFuid();
    int i = paramArrayList.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        paramArrayList.add(paramFriend);
      do
        return;
      while (((FriendsModel.Friend)paramArrayList.get(j)).getFuid().compareTo(str) == 0);
    }
  }

  public static Bitmap addNameBmp2Map(String paramString1, HashMap<String, Bitmap> paramHashMap, String paramString2, TextPaint paramTextPaint, int paramInt)
  {
    Bitmap localBitmap = (Bitmap)paramHashMap.get(paramString1);
    if (localBitmap == null)
      localBitmap = ImageCache.createStringBitmap("@" + paramString2, paramTextPaint, paramInt);
    if (localBitmap != null)
      paramHashMap.put(paramString1, localBitmap);
    return localBitmap;
  }

  public static void getAllFriends(ArrayList<FriendsModel.Friend> paramArrayList)
  {
    try
    {
      paramArrayList.clear();
      ArrayList localArrayList = FriendsModel.getInstance().getFriends();
      if (localArrayList != null)
        paramArrayList.addAll(localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static String getNameBmpFormat(String paramString1, String paramString2)
  {
    return "@" + paramString1 + "(#" + paramString2 + "#)";
  }

  public static int insertFriendIntoContent(int paramInt1, int paramInt2, String paramString1, String paramString2, EditText paramEditText, HashMap<String, Bitmap> paramHashMap, int paramInt3, int paramInt4)
  {
    String str = getNameBmpFormat(paramString1, paramString2);
    Bitmap localBitmap = addNameBmp2Map(str, paramHashMap, paramString2, paramEditText.getPaint(), 0);
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)));
    while (true)
    {
      return 0;
      Editable localEditable = paramEditText.getText().replace(paramInt1, paramInt2, str + " ");
      if (paramInt1 + str.length() > paramInt3)
        break;
      if ((1 != paramInt4) && (!Setting.getInstance().isShowFaceIconInLandScape()))
        continue;
      localEditable.setSpan(new ImageSpan(localBitmap), paramInt1, paramInt1 + str.length(), 33);
      return 1;
    }
    return -1;
  }

  public static int insertFriendIntoContent(int paramInt1, int paramInt2, String paramString1, String paramString2, EditText paramEditText, HashMap<String, Bitmap> paramHashMap, int paramInt3, int paramInt4, int paramInt5)
  {
    String str = getNameBmpFormat(paramString1, paramString2);
    Bitmap localBitmap = addNameBmp2Map(str, paramHashMap, paramString2, paramEditText.getPaint(), paramInt5);
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)));
    while (true)
    {
      return 0;
      Editable localEditable = paramEditText.getText().replace(paramInt1, paramInt2, str + " ");
      if (paramInt1 + str.length() > paramInt3)
        break;
      if ((1 != paramInt4) && (!Setting.getInstance().isShowFaceIconInLandScape()))
        continue;
      localEditable.setSpan(new ImageSpan(null, localBitmap, 1), paramInt1, paramInt1 + str.length(), 33);
      return 1;
    }
    return -1;
  }

  public static void searchListData(ArrayList<FriendsModel.Friend> paramArrayList1, String paramString, ArrayList<FriendsModel.Friend> paramArrayList2)
  {
    if ((paramArrayList1 == null) || (TextUtils.isEmpty(paramString)));
    int i;
    while (true)
    {
      return;
      i = paramArrayList1.size();
      if (!StringUtil.isContainChinese(paramString))
        break;
      for (int k = 0; k < i; k++)
      {
        FriendsModel.Friend localFriend2 = (FriendsModel.Friend)paramArrayList1.get(k);
        if (!localFriend2.getFname().contains(paramString))
          continue;
        addFriendList(localFriend2, paramArrayList2);
      }
    }
    int j = 0;
    label71: FriendsModel.Friend localFriend1;
    if (j < i)
    {
      localFriend1 = (FriendsModel.Friend)paramArrayList1.get(j);
      if (!localFriend1.getFname().contains(paramString))
        break label112;
      addFriendList(localFriend1, paramArrayList2);
    }
    while (true)
    {
      j++;
      break label71;
      break;
      label112: if (searchPy(localFriend1.getPy(), paramString))
      {
        addFriendList(localFriend1, paramArrayList2);
        continue;
      }
      if (!searchPy(localFriend1.getFpy(), paramString))
        continue;
      addFriendList(localFriend1, paramArrayList2);
    }
  }

  private static boolean searchPy(String[] paramArrayOfString, String paramString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length <= 0));
    while (true)
    {
      return false;
      int i = paramArrayOfString.length;
      for (int j = 0; j < i; j++)
        if (paramArrayOfString[j].startsWith(paramString))
          return true;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.FriendsUtil
 * JD-Core Version:    0.6.0
 */