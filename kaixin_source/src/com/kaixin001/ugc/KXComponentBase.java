package com.kaixin001.ugc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public abstract class KXComponentBase
{
  protected static final String TAG = "Component";
  protected Context mContext;
  protected View mParent;

  public KXComponentBase(Context paramContext, int paramInt)
  {
    this(paramContext, ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(paramInt, null));
  }

  public KXComponentBase(Context paramContext, View paramView)
  {
    this.mContext = paramContext;
    this.mParent = paramView;
    init(paramContext, paramView);
  }

  public View getView()
  {
    return this.mParent;
  }

  public abstract void init(Context paramContext, View paramView);

  public boolean isShown()
  {
    return this.mParent.getVisibility() == 0;
  }

  public void show(boolean paramBoolean)
  {
    View localView;
    if (this.mParent != null)
    {
      localView = this.mParent;
      if (!paramBoolean)
        break label24;
    }
    label24: for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.ugc.KXComponentBase
 * JD-Core Version:    0.6.0
 */