package com.kaixin001.model;

import com.kaixin001.item.ActivityAwardItem;
import java.util.ArrayList;
import java.util.Iterator;

public class ActivityAwardModel extends KXModel
{
  public KaixinModelTemplate<ActivityAwardItem> awards = new KaixinModelTemplate();

  public static void deleteAwardById(ArrayList<ActivityAwardItem> paramArrayList, String paramString)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayList.size())
        return;
      if (!paramString.equals(((ActivityAwardItem)paramArrayList.get(i)).id))
        continue;
      paramArrayList.remove(i);
      return;
    }
  }

  public void clear()
  {
    this.awards.clearItemList();
  }

  public void useAwardById(String paramString)
  {
    Iterator localIterator = this.awards.getItemList().iterator();
    ActivityAwardItem localActivityAwardItem;
    do
    {
      if (!localIterator.hasNext())
        return;
      localActivityAwardItem = (ActivityAwardItem)localIterator.next();
    }
    while (!paramString.equals(localActivityAwardItem.id));
    localActivityAwardItem.used = 1;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.ActivityAwardModel
 * JD-Core Version:    0.6.0
 */