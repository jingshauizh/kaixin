package com.tencent.tauth;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import java.io.IOException;

public class TencentOpenRes extends View
{
  private TencentOpenRes(Context paramContext)
  {
    super(paramContext);
  }

  public static Drawable getBigLoginBtn(AssetManager paramAssetManager)
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    try
    {
      localStateListDrawable.addState(PRESSED_ENABLED_STATE_SET, Drawable.createFromStream(paramAssetManager.open("login_btn_src_big_pressed.png"), "login_btn_src_big_pressed"));
      localStateListDrawable.addState(EMPTY_STATE_SET, Drawable.createFromStream(paramAssetManager.open("login_btn_src_big_normal.png"), "login_btn_src_big_normal"));
      return localStateListDrawable;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return localStateListDrawable;
  }

  static Drawable getCloseBtnSrc(AssetManager paramAssetManager)
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    try
    {
      localStateListDrawable.addState(PRESSED_ENABLED_STATE_SET, Drawable.createFromStream(paramAssetManager.open("close_btn_src_pressed.png"), "close_btn_src_pressed"));
      localStateListDrawable.addState(EMPTY_STATE_SET, Drawable.createFromStream(paramAssetManager.open("close_btn_src_normal.png"), "close_btn_src_normal"));
      return localStateListDrawable;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return localStateListDrawable;
  }

  static Drawable getCloseBtnSrcBg(AssetManager paramAssetManager)
  {
    try
    {
      Drawable localDrawable = Drawable.createFromStream(paramAssetManager.open("close_btn_bg.png"), "close_btn_bg");
      return localDrawable;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }

  public static Drawable getLoginBtn(AssetManager paramAssetManager)
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    try
    {
      localStateListDrawable.addState(PRESSED_ENABLED_STATE_SET, Drawable.createFromStream(paramAssetManager.open("login_btn_src_pressed.png"), "login_btn_src_pressed"));
      localStateListDrawable.addState(EMPTY_STATE_SET, Drawable.createFromStream(paramAssetManager.open("login_btn_src_normal.png"), "login_btn_src_normal"));
      return localStateListDrawable;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return localStateListDrawable;
  }

  public static Drawable getLogoutBtn(AssetManager paramAssetManager)
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    try
    {
      localStateListDrawable.addState(PRESSED_ENABLED_STATE_SET, Drawable.createFromStream(paramAssetManager.open("logout_btn_src_pressed.png"), "logout_btn_src_pressed"));
      localStateListDrawable.addState(EMPTY_STATE_SET, Drawable.createFromStream(paramAssetManager.open("logout_btn_src_normal.png"), "logout_btn_src_normal"));
      return localStateListDrawable;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return localStateListDrawable;
  }

  public static Drawable getSmallLoginBtn(AssetManager paramAssetManager)
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    try
    {
      localStateListDrawable.addState(PRESSED_ENABLED_STATE_SET, Drawable.createFromStream(paramAssetManager.open("login_btn_src_small_pressed.png"), "login_btn_src_small_pressed"));
      localStateListDrawable.addState(EMPTY_STATE_SET, Drawable.createFromStream(paramAssetManager.open("login_btn_src_small_normal.png"), "login_btn_src_small_normal"));
      return localStateListDrawable;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return localStateListDrawable;
  }

  public static Drawable getSmallLogoutBtn(AssetManager paramAssetManager)
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    try
    {
      localStateListDrawable.addState(PRESSED_ENABLED_STATE_SET, Drawable.createFromStream(paramAssetManager.open("logout_btn_src_small_pressed.png"), "logout_btn_src_small_pressed"));
      localStateListDrawable.addState(EMPTY_STATE_SET, Drawable.createFromStream(paramAssetManager.open("logout_btn_src_small_normal.png"), "logout_btn_src_small_normal"));
      return localStateListDrawable;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return localStateListDrawable;
  }

  static Drawable getTitleBg(AssetManager paramAssetManager)
  {
    try
    {
      Drawable localDrawable = Drawable.createFromStream(paramAssetManager.open("title_bg.png"), "title_bg");
      return localDrawable;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.tencent.tauth.TencentOpenRes
 * JD-Core Version:    0.6.0
 */