package com.kaixin001.item;

import com.kaixin001.model.KaixinModelTemplate;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class PoiPhotoesItem
{
  public int currentSelection = -1;
  public KaixinModelTemplate<KaixinPhotoItem> photoList = new KaixinModelTemplate();
  public PoiItem poi;

  public void clear()
  {
    this.poi = null;
    this.photoList.clearItemList();
    this.currentSelection = -1;
  }

  public KaixinPhotoItem getKaixinPhotoItem(int paramInt)
  {
    try
    {
      this.photoList.itemListLock.lock();
      if (paramInt < this.photoList.getItemList().size())
      {
        KaixinPhotoItem localKaixinPhotoItem = (KaixinPhotoItem)this.photoList.getItemList().get(paramInt);
        return localKaixinPhotoItem;
      }
      return null;
    }
    finally
    {
      this.photoList.itemListLock.unlock();
    }
    throw localObject;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.item.PoiPhotoesItem
 * JD-Core Version:    0.6.0
 */