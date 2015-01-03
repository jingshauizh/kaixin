package com.amap.mapapi.route;

import com.amap.mapapi.core.AMapException;
import com.amap.mapapi.core.j;
import java.io.InputStream;
import java.net.Proxy;
import java.util.ArrayList;

abstract class d extends e
{
  public d(f paramf, Proxy paramProxy, String paramString1, String paramString2)
  {
    super(paramf, paramProxy, paramString1, paramString2);
  }

  protected ArrayList<Route> a(InputStream paramInputStream)
    throws AMapException
  {
    return null;
  }

  protected void a(Route paramRoute)
  {
    for (int i = -1 + paramRoute.getStepCount(); i > 0; i--)
    {
      DriveWalkSegment localDriveWalkSegment2 = (DriveWalkSegment)paramRoute.getStep(i);
      DriveWalkSegment localDriveWalkSegment3 = (DriveWalkSegment)paramRoute.getStep(i - 1);
      localDriveWalkSegment2.setActionCode(localDriveWalkSegment3.getActionCode());
      localDriveWalkSegment2.setActionDescription(localDriveWalkSegment3.getActionDescription());
    }
    DriveWalkSegment localDriveWalkSegment1 = (DriveWalkSegment)paramRoute.getStep(0);
    localDriveWalkSegment1.setActionCode(-1);
    localDriveWalkSegment1.setActionDescription("");
  }

  protected String e()
  {
    return j.a().d() + "/route/simple";
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.route.d
 * JD-Core Version:    0.6.0
 */