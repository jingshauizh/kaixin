package com.kaixin001.model;

import com.kaixin001.item.FilmInfo;
import java.util.ArrayList;

public class FilmListModel extends KXModel
{
  private static FilmListModel instance = new FilmListModel();
  private ArrayList<FilmInfo> mDataList = new ArrayList();
  private boolean mHasMore;

  public static FilmListModel getInstance()
  {
    return instance;
  }

  public void clear()
  {
    this.mDataList.clear();
  }

  public ArrayList<FilmInfo> getFilmList()
  {
    return this.mDataList;
  }

  public boolean hasMore()
  {
    return this.mHasMore;
  }

  public void setHasMore(boolean paramBoolean)
  {
    this.mHasMore = paramBoolean;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.FilmListModel
 * JD-Core Version:    0.6.0
 */