package com.autonavi.aps.api;

public abstract interface IAPS
{
  public abstract Location getCurrentLocation(android.location.Location paramLocation);

  public abstract String getVersion();

  public abstract void onDestroy();

  public abstract void setKey(String paramString);

  public abstract void setLicence(String paramString);

  public abstract void setOpenGps(boolean paramBoolean);

  public abstract void setOpenSystemNetworkLocation(boolean paramBoolean);

  public abstract void setPackageName(String paramString);

  public abstract void setProductName(String paramString);
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.autonavi.aps.api.IAPS
 * JD-Core Version:    0.6.0
 */