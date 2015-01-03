package com.weibo.sdk.android.net;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import org.apache.http.HttpHost;

public class NetStateManager
{
  public static NetState CUR_NETSTATE = NetState.Mobile;
  private static Context mContext;

  public static HttpHost getAPN()
  {
    Uri localUri = Uri.parse("content://telephony/carriers/preferapn");
    Context localContext = mContext;
    Cursor localCursor = null;
    if (localContext != null)
      localCursor = mContext.getContentResolver().query(localUri, null, null, null, null);
    HttpHost localHttpHost = null;
    if (localCursor != null)
    {
      boolean bool = localCursor.moveToFirst();
      localHttpHost = null;
      if (bool)
      {
        String str = localCursor.getString(localCursor.getColumnIndex("proxy"));
        localHttpHost = null;
        if (str != null)
        {
          int i = str.trim().length();
          localHttpHost = null;
          if (i > 0)
            localHttpHost = new HttpHost(str, 80);
        }
        localCursor.close();
      }
    }
    return localHttpHost;
  }

  public static enum NetState
  {
    static
    {
      NOWAY = new NetState("NOWAY", 2);
      NetState[] arrayOfNetState = new NetState[3];
      arrayOfNetState[0] = Mobile;
      arrayOfNetState[1] = WIFI;
      arrayOfNetState[2] = NOWAY;
      ENUM$VALUES = arrayOfNetState;
    }
  }

  public class NetStateReceive extends BroadcastReceiver
  {
    public NetStateReceive()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      NetStateManager.mContext = paramContext;
      if ("android.net.conn.CONNECTIVITY_CHANGE".equals(paramIntent.getAction()))
      {
        WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
        WifiInfo localWifiInfo = localWifiManager.getConnectionInfo();
        if ((!localWifiManager.isWifiEnabled()) || (-1 == localWifiInfo.getNetworkId()))
          NetStateManager.CUR_NETSTATE = NetStateManager.NetState.Mobile;
      }
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.weibo.sdk.android.net.NetStateManager
 * JD-Core Version:    0.6.0
 */