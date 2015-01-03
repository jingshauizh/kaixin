package com.kaixin001.model;

import com.kaixin001.item.FilmCommentItem;
import com.kaixin001.item.FilmInfo;
import java.util.ArrayList;

public class FilmDetailModel extends KXModel
{
  private int mAllCommentNum;
  private ArrayList<FilmCommentItem> mAllComments = new ArrayList();
  private boolean mAllHasMore;
  private int mFriendCommentNum;
  private ArrayList<FilmCommentItem> mFriendComments = new ArrayList();
  private boolean mFriendHasMore;
  private FilmInfo mInfo;

  public boolean allHasMore()
  {
    return this.mAllHasMore;
  }

  public void clear()
  {
    this.mAllComments.clear();
    this.mFriendComments.clear();
  }

  public boolean friendHasMore()
  {
    return this.mFriendHasMore;
  }

  public int getAllCommentNum()
  {
    return this.mAllCommentNum;
  }

  public ArrayList<FilmCommentItem> getAllComments()
  {
    return this.mAllComments;
  }

  public int getFriendCommentNum()
  {
    return this.mFriendCommentNum;
  }

  public ArrayList<FilmCommentItem> getFriendComments()
  {
    return this.mFriendComments;
  }

  public FilmInfo getInfo()
  {
    return this.mInfo;
  }

  public void setAllCommentNum(int paramInt)
  {
    this.mAllCommentNum = paramInt;
  }

  public void setAllHasMore(boolean paramBoolean)
  {
    this.mAllHasMore = paramBoolean;
  }

  public void setFriendCommentNum(int paramInt)
  {
    this.mFriendCommentNum = paramInt;
  }

  public void setFriendHasMore(boolean paramBoolean)
  {
    this.mFriendHasMore = paramBoolean;
  }

  public void setInfo(FilmInfo paramFilmInfo)
  {
    this.mInfo = paramFilmInfo;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.FilmDetailModel
 * JD-Core Version:    0.6.0
 */