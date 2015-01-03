package com.kaixin001.util;

import java.util.ArrayList;
import java.util.Collection;

public class UserHabitList<E> extends ArrayList<E>
{
  private onAddListerner listerner = null;

  private void onAdd()
  {
    if (this.listerner != null)
      this.listerner.onAdd(this);
  }

  public void add(int paramInt, E paramE)
  {
    super.add(paramInt, paramE);
    onAdd();
  }

  public boolean add(E paramE)
  {
    boolean bool = super.add(paramE);
    onAdd();
    return bool;
  }

  public boolean addAll(int paramInt, Collection<? extends E> paramCollection)
  {
    boolean bool = super.addAll(paramInt, paramCollection);
    onAdd();
    return bool;
  }

  public boolean addAll(Collection<? extends E> paramCollection)
  {
    boolean bool = super.addAll(paramCollection);
    onAdd();
    return bool;
  }

  public void setOnAddListerner(onAddListerner paramonAddListerner)
  {
    this.listerner = paramonAddListerner;
  }

  public static abstract interface onAddListerner
  {
    public abstract void onAdd(UserHabitList paramUserHabitList);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.util.UserHabitList
 * JD-Core Version:    0.6.0
 */