package com.kaixin001.model;

import com.kaixin001.item.ExtremityItemLv1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExtremityCommentModel extends KXModel
{
  private List<ExtremityItemLv1> comments = Collections.synchronizedList(new ArrayList());
  private List<ExtremityItemLv1> reposts = Collections.synchronizedList(new ArrayList());

  public void addComment(int paramInt, ExtremityItemLv1 paramExtremityItemLv1)
  {
    if (paramExtremityItemLv1 == null)
      return;
    this.comments.add(paramInt, paramExtremityItemLv1);
  }

  public void addComment(ExtremityItemLv1 paramExtremityItemLv1)
  {
    if (paramExtremityItemLv1 == null)
      return;
    this.comments.add(paramExtremityItemLv1);
  }

  public void addRepost(ExtremityItemLv1 paramExtremityItemLv1)
  {
    if (paramExtremityItemLv1 == null)
      return;
    this.reposts.add(0, paramExtremityItemLv1);
  }

  public void clear()
  {
    this.comments.clear();
    this.reposts.clear();
  }

  public void clearComments()
  {
    this.comments.clear();
  }

  public void clearReposts()
  {
    this.reposts.clear();
  }

  public List<ExtremityItemLv1> getAllCommentsAct()
  {
    return this.comments;
  }

  public List<ExtremityItemLv1> getReposts()
  {
    return this.reposts;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.model.ExtremityCommentModel
 * JD-Core Version:    0.6.0
 */