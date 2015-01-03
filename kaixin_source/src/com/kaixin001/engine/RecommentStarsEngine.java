package com.kaixin001.engine;

import android.content.Context;
import android.text.TextUtils;
import com.kaixin001.model.RecommentStarsModel;
import com.kaixin001.model.RecommentStarsModel.RecommentStarsTypeItem;
import com.kaixin001.model.StrangerModel.Stranger;
import com.kaixin001.model.User;
import com.kaixin001.network.HttpProxy;
import com.kaixin001.network.Protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class RecommentStarsEngine extends KXEngine
{
  public static final int RESULT_FAILED = 0;
  public static final int RESULT_FAILED_NETWORK = -2;
  public static final int RESULT_FAILED_PARSE = -1;
  public static final int RESULT_OK = 1;

  private boolean parseItems(JSONArray paramJSONArray, ArrayList<RecommentStarsModel.RecommentStarsTypeItem> paramArrayList)
  {
    int j;
    if ((paramJSONArray != null) && (paramJSONArray.length() > 0))
    {
      int i = paramJSONArray.length();
      j = 0;
      if (j >= i)
        return true;
    }
    while (true)
    {
      int m;
      try
      {
        JSONObject localJSONObject1 = paramJSONArray.getJSONObject(j);
        RecommentStarsModel.RecommentStarsTypeItem localRecommentStarsTypeItem = new RecommentStarsModel.RecommentStarsTypeItem();
        localRecommentStarsTypeItem.mType = localJSONObject1.optString("type");
        localRecommentStarsTypeItem.mName = localJSONObject1.optString("name");
        JSONArray localJSONArray = localJSONObject1.optJSONArray("userinfos");
        int k = localJSONArray.length();
        m = 0;
        if (m < k)
          continue;
        paramArrayList.add(localRecommentStarsTypeItem);
        break label275;
        JSONObject localJSONObject2 = localJSONArray.getJSONObject(m);
        StrangerModel.Stranger localStranger = new StrangerModel.Stranger();
        localStranger.fname = localJSONObject2.optString("name");
        localStranger.fuid = localJSONObject2.optString("uid");
        if (localStranger.fuid.equals(User.getInstance().getUID()))
          break label281;
        localStranger.flogo = localJSONObject2.optString("icon50");
        if (!TextUtils.isEmpty(localStranger.flogo))
          continue;
        localStranger.flogo = localJSONObject2.optString("icon");
        localStranger.fans = localJSONObject2.optString("fans");
        if (TextUtils.isEmpty(localStranger.fans))
          continue;
        localStranger.fans = ("粉丝：" + localStranger.fans);
        localRecommentStarsTypeItem.items.add(localStranger);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        break label275;
      }
      return false;
      label275: j++;
      break;
      label281: m++;
    }
  }

  public int getTypes(Context paramContext, RecommentStarsModel paramRecommentStarsModel)
    throws SecurityErrorException
  {
    String str1 = Protocol.getInstance().makeStarsTypeRequest(User.getInstance().getUID());
    HttpProxy localHttpProxy = new HttpProxy(paramContext);
    try
    {
      String str3 = localHttpProxy.httpGet(str1, null, null);
      str2 = str3;
      paramRecommentStarsModel.getTypseList().clear();
      if (str2 == null)
        return 0;
    }
    catch (Exception localException)
    {
      JSONObject localJSONObject;
      do
      {
        String str2;
        while (true)
        {
          localException.printStackTrace();
          str2 = null;
        }
        localJSONObject = super.parseJSON(paramContext, str2);
        if (localJSONObject == null)
          return -1;
      }
      while (this.ret != 1);
      localJSONObject.optString("total", null);
      parseItems(localJSONObject.optJSONArray("data"), paramRecommentStarsModel.getTypseList());
    }
    return 1;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.RecommentStarsEngine
 * JD-Core Version:    0.6.0
 */