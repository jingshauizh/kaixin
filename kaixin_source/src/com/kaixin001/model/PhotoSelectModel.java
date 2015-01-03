package com.kaixin001.model;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import com.kaixin001.item.LocalPhotoItem;
import com.kaixin001.util.CloseUtil;
import com.kaixin001.util.FileUtil;
import java.util.ArrayList;
import java.util.Iterator;

public class PhotoSelectModel extends KXModel
{
  private static PhotoSelectModel instance = new PhotoSelectModel();
  private ArrayList<PhotoSelectChangedListener> mPhotoSelectChangedListener = new ArrayList();
  private ArrayList<LocalPhotoItem> mSelectPhotoList = new ArrayList();

  public static PhotoSelectModel getInstance()
  {
    return instance;
  }

  private void notifyDataChanged()
  {
    Iterator localIterator;
    if (this.mSelectPhotoList != null)
      localIterator = this.mPhotoSelectChangedListener.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      PhotoSelectChangedListener localPhotoSelectChangedListener = (PhotoSelectChangedListener)localIterator.next();
      if (localPhotoSelectChangedListener == null)
        continue;
      localPhotoSelectChangedListener.onPhotoSelectChanged(this.mSelectPhotoList.size());
    }
  }

  public void addPhoto(Activity paramActivity, String paramString)
  {
    Cursor localCursor = null;
    if (TextUtils.isEmpty(paramString))
      return;
    while (true)
    {
      int j;
      try
      {
        FileUtil.loadLocalPictures(paramActivity, null, 10, false);
        localCursor = paramActivity.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        boolean bool1 = localCursor.moveToLast();
        boolean bool2 = bool1;
        i = 0;
        if (!bool2)
          return;
        if (!paramString.equals(localCursor.getString(localCursor.getColumnIndexOrThrow("_data"))))
          continue;
        addPhoto(localCursor.getString(localCursor.getColumnIndexOrThrow("_id")), paramString);
        return;
        bool2 = localCursor.moveToPrevious();
        j = i + 1;
        if (i > 20)
        {
          addPhoto(System.currentTimeMillis(), paramString);
          return;
        }
      }
      catch (Exception localException)
      {
        return;
      }
      finally
      {
        CloseUtil.close(localCursor);
      }
      int i = j;
    }
  }

  public void addPhoto(String paramString1, String paramString2)
  {
    LocalPhotoItem localLocalPhotoItem = new LocalPhotoItem();
    localLocalPhotoItem.mId = paramString1;
    localLocalPhotoItem.mPath = paramString2;
    this.mSelectPhotoList.add(localLocalPhotoItem);
    notifyDataChanged();
  }

  public void addPhotoSelectChangedListener(PhotoSelectChangedListener paramPhotoSelectChangedListener)
  {
    this.mPhotoSelectChangedListener.add(paramPhotoSelectChangedListener);
  }

  public void clear()
  {
    this.mSelectPhotoList.clear();
  }

  public void clearAllPhotos()
  {
    this.mSelectPhotoList.clear();
    notifyDataChanged();
  }

  public ArrayList<LocalPhotoItem> getSelectPhotoList()
  {
    return this.mSelectPhotoList;
  }

  public boolean isSelect(String paramString)
  {
    Iterator localIterator = this.mSelectPhotoList.iterator();
    do
      if (!localIterator.hasNext())
        return false;
    while (!((LocalPhotoItem)localIterator.next()).mId.equals(paramString));
    return true;
  }

  public void removePhotoById(String paramString)
  {
    Iterator localIterator = this.mSelectPhotoList.iterator();
    LocalPhotoItem localLocalPhotoItem;
    do
    {
      if (!localIterator.hasNext())
        return;
      localLocalPhotoItem = (LocalPhotoItem)localIterator.next();
    }
    while (!localLocalPhotoItem.mId.equals(paramString));
    this.mSelectPhotoList.remove(localLocalPhotoItem);
    notifyDataChanged();
  }

  public void removePhotoByPath(String paramString)
  {
    Iterator localIterator = this.mSelectPhotoList.iterator();
    LocalPhotoItem localLocalPhotoItem;
    do
    {
      if (!localIterator.hasNext())
        return;
      localLocalPhotoItem = (LocalPhotoItem)localIterator.next();
    }
    while (!localLocalPhotoItem.mPath.equals(paramString));
    this.mSelectPhotoList.remove(localLocalPhotoItem);
    notifyDataChanged();
  }

  public void removePhotoSelectChangedListener(PhotoSelectChangedListener paramPhotoSelectChangedListener)
  {
    this.mPhotoSelectChangedListener.remove(paramPhotoSelectChangedListener);
  }

  public void repacePhotoPath(String paramString1, String paramString2)
  {
    Iterator localIterator = this.mSelectPhotoList.iterator();
    LocalPhotoItem localLocalPhotoItem;
    do
    {
      if (!localIterator.hasNext())
        return;
      localLocalPhotoItem = (LocalPhotoItem)localIterator.next();
    }
    while (!localLocalPhotoItem.mPath.equals(paramString1));
    localLocalPhotoItem.mPath = paramString2;
  }

  public static abstract interface PhotoSelectChangedListener
  {
    public abstract void onPhotoSelectChanged(int paramInt);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.PhotoSelectModel
 * JD-Core Version:    0.6.0
 */