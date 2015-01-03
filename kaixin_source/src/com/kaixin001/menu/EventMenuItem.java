package com.kaixin001.menu;

import android.support.v4.app.FragmentActivity;
import com.kaixin001.fragment.BaseFragment;
import com.kaixin001.model.EventModel.EventData;
import com.kaixin001.util.IntentUtil;

public class EventMenuItem extends MenuItem
{
  public static final int STATE_LOADING = 2;
  public static final int STATE_NORMAL = 1;
  public static final int STATE_NO_EVENT = 3;
  public static EventModel.EventData currentEventData = null;
  public final EventModel.EventData mData;
  public int mState;

  public EventMenuItem(int paramInt, EventModel.EventData paramEventData)
  {
    super(MenuItemId.ID_EVENT);
    this.mState = paramInt;
    this.mData = paramEventData;
    this.onMenuItemClickListener = new MenuItem.OnClickListener()
    {
      public void onClick(BaseFragment paramBaseFragment)
      {
        if (!EventMenuItem.isAvailableEvent(EventMenuItem.this));
        EventModel.EventData localEventData;
        int i;
        do
        {
          do
          {
            return;
            localEventData = EventMenuItem.this.mData;
          }
          while (localEventData == null);
          i = localEventData.getEventType();
          if (i != 0)
            continue;
          IntentUtil.showWebPage(paramBaseFragment.getActivity(), paramBaseFragment, localEventData.getUrl(), paramBaseFragment.getActivity().getString(2131427974), null, -1);
          return;
        }
        while ((1 != i) && (2 != i));
        EventMenuItem.startRightFragment(paramBaseFragment, IntentUtil.setWebPageIntent(paramBaseFragment.getActivity(), localEventData.getUrl(), paramBaseFragment.getActivity().getString(2131427974), localEventData, -1));
      }
    };
  }

  public static boolean isAvailableEvent(EventMenuItem paramEventMenuItem)
  {
    if (paramEventMenuItem == null);
    do
      return false;
    while ((paramEventMenuItem.mState == 2) || (paramEventMenuItem.mState == 3));
    return true;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.menu.EventMenuItem
 * JD-Core Version:    0.6.0
 */