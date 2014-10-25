package com.kaixin001.util;

import android.text.TextUtils;
import com.kaixin001.item.KXLinkInfo;
import com.kaixin001.model.FaceModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class ParseNewsInfoUtil
{
  private static final String TAG = "ParseNewsInfoUtil";

  private static void addCommentInfo(ArrayList<KXLinkInfo> paramArrayList, String paramString, TreeMap<Integer, String> paramTreeMap, int paramInt)
  {
    int i = 0;
    int j = paramInt;
    Iterator localIterator = paramTreeMap.keySet().iterator();
   //delete
 
  }

  private static void addNewInfo(ArrayList<KXLinkInfo> paramArrayList, String paramString)
  {
    KXLinkInfo localKXLinkInfo = new KXLinkInfo();
    localKXLinkInfo.setContent(paramString);
    paramArrayList.add(localKXLinkInfo);
  }

  public static String getReplistString(JSONArray paramJSONArray)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      int i = paramJSONArray.length();
      if (i == 0)
        return null;
      for (int j = 0; ; j++)
      {
        if (j >= i)
        {
          String str = localStringBuffer.toString();
          return str;
        }
        localStringBuffer.append(paramJSONArray.getJSONObject(j).getString("title"));
        if (j == i - 1)
          continue;
        localStringBuffer.append('\n');
      }
    }
    catch (Exception localException)
    {
    
        KXLog.e("ParseNewsInfoUtil", "getReplistString", localException);
        String str = localStringBuffer.toString();
    
    }
    finally
    {
      localStringBuffer.toString();
    }
	return null;
    
  }

  // ERROR //
  public static ArrayList<KXLinkInfo> parseCommentAndReplyLinkString(String paramString)
  {
    return null;
  }

  // ERROR //
  public static ArrayList<KXLinkInfo> parseDiaryLinkString(String paramString)
  {
	  return null;
  }

  private static void parseFaceString(ArrayList<KXLinkInfo> paramArrayList, String paramString, TreeMap<Integer, String> paramTreeMap)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    ArrayList localArrayList = FaceModel.getInstance().getAllFaceStringSortByLength();
    int i = localArrayList.size();
    int j = 0;
     String str = null;
    if (j < i)
      str = (String)localArrayList.get(j);
    int m;
    for (int k = paramString.indexOf(str); ; k = paramString.indexOf(str, m))
    {
      if (k < 0)
      {
        j++;
      
        break;
      }
      m = k + str.length();
      paramTreeMap.put(Integer.valueOf(k), str);
    }
  }

  // ERROR //
  public static ArrayList<KXLinkInfo> parseNewsLinkString(String paramString)
  {
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.ParseNewsInfoUtil
 * JD-Core Version:    0.6.0
 */