package com.amap.mapapi.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.amap.mapapi.core.PoiItem;
import com.amap.mapapi.core.e;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PoiOverlay extends ItemizedOverlay<PoiItem>
{
  private t a = null;
  private ArrayList<PoiItem> b = new ArrayList();
  private boolean c = true;
  private MapView d;
  private boolean e = false;

  public PoiOverlay(Drawable paramDrawable, List<PoiItem> paramList)
  {
    this(paramDrawable, paramList, "");
  }

  public PoiOverlay(Drawable paramDrawable, List<PoiItem> paramList, String paramString)
  {
    super(paramDrawable);
    String str = paramString.trim();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      PoiItem localPoiItem = (PoiItem)localIterator.next();
      if (str.equals(""))
      {
        this.b.add(localPoiItem);
        continue;
      }
      if (localPoiItem.getTypeCode() == null)
        continue;
      if ((str.length() <= 2) && (localPoiItem.getTypeCode().startsWith(str)))
      {
        this.b.add(localPoiItem);
        continue;
      }
      if (!str.equals(localPoiItem.getTypeCode()))
        continue;
      this.b.add(localPoiItem);
    }
    populate();
  }

  static Bitmap a(Drawable paramDrawable)
  {
    int i = paramDrawable.getIntrinsicWidth();
    int j = paramDrawable.getIntrinsicHeight();
    if (paramDrawable.getOpacity() != -1);
    for (Bitmap.Config localConfig = Bitmap.Config.ARGB_4444; ; localConfig = Bitmap.Config.RGB_565)
    {
      Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
      Canvas localCanvas = new Canvas(localBitmap);
      paramDrawable.setBounds(0, 0, i, j);
      paramDrawable.draw(localCanvas);
      return localBitmap;
    }
  }

  static Drawable a(Drawable paramDrawable, int paramInt1, int paramInt2)
  {
    int i = paramDrawable.getIntrinsicWidth();
    int j = paramDrawable.getIntrinsicHeight();
    Bitmap localBitmap1 = a(paramDrawable);
    Matrix localMatrix = new Matrix();
    localMatrix.postScale(paramInt1 / i, paramInt2 / j);
    Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1, 0, 0, i, j, localMatrix, true);
    if ((localBitmap1 != null) && (!localBitmap1.isRecycled()))
      localBitmap1.recycle();
    return new BitmapDrawable(localBitmap2);
  }

  private Spanned a(PoiItem paramPoiItem)
  {
    Object localObject = "";
    try
    {
      localObject = paramPoiItem.getTypeDes().split(" - ")[1];
      String str = e.a("类别: " + (String)localObject, "#000000");
      localObject = str;
      label44: return e.b((String)localObject);
    }
    catch (Exception localException)
    {
      break label44;
    }
  }

  private void a(LinearLayout paramLinearLayout, PoiItem paramPoiItem, Context paramContext)
  {
    String str1 = paramPoiItem.getTel();
    if (e.a(str1))
      return;
    TextView localTextView = new TextView(paramContext);
    String str2 = e.a("Tel:  " + str1, "#000000");
    localTextView.setBackgroundColor(-1);
    localTextView.setText(e.b(str2));
    localTextView.setLinksClickable(true);
    Linkify.addLinks(localTextView, 4);
    paramLinearLayout.addView(localTextView, new LinearLayout.LayoutParams(-1, -2));
  }

  private Spanned b(PoiItem paramPoiItem)
  {
    String str = paramPoiItem.getSnippet();
    if (e.a(str))
      return null;
    return e.b(e.a("地址: " + str, "#000000"));
  }

  public void addToMap(MapView paramMapView)
  {
    this.d = paramMapView;
    paramMapView.getOverlays().add(this);
    this.e = true;
  }

  public void closePopupWindow()
  {
    if (this.a != null)
      this.a.c();
    this.a = null;
  }

  protected PoiItem createItem(int paramInt)
  {
    return (PoiItem)this.b.get(paramInt);
  }

  public void enablePopup(boolean paramBoolean)
  {
    this.c = paramBoolean;
    if (!this.c)
      closePopupWindow();
  }

  protected MapView.LayoutParams getLayoutParam()
  {
    return null;
  }

  protected MapView.LayoutParams getLayoutParam(int paramInt)
  {
    return null;
  }

  protected Drawable getPopupBackground()
  {
    return null;
  }

  protected Drawable getPopupMarker(PoiItem paramPoiItem)
  {
    Drawable localDrawable = paramPoiItem.getMarker(0);
    if (localDrawable == null)
      localDrawable = getDefaultMarker();
    return a(localDrawable, 24, 18);
  }

  protected View getPopupView(PoiItem paramPoiItem)
  {
    Context localContext = this.d.getContext();
    LinearLayout localLinearLayout1 = new LinearLayout(localContext);
    localLinearLayout1.setOrientation(1);
    localLinearLayout1.setPadding(5, 10, 5, 20);
    LinearLayout localLinearLayout2 = new LinearLayout(localContext);
    localLinearLayout2.setOrientation(0);
    localLinearLayout2.setGravity(51);
    ImageView localImageView = new ImageView(localContext);
    localImageView.setBackgroundColor(-1);
    localImageView.setImageDrawable(getPopupMarker(paramPoiItem));
    TextView localTextView1 = new TextView(localContext);
    localTextView1.setBackgroundColor(-1);
    localTextView1.setText(e.b(e.a(paramPoiItem.getTitle(), "#000000")));
    localLinearLayout2.addView(localImageView, new LinearLayout.LayoutParams(-2, -2));
    localLinearLayout2.addView(localTextView1, new LinearLayout.LayoutParams(-2, -2));
    localLinearLayout1.addView(localLinearLayout2);
    if (b(paramPoiItem) != null)
    {
      TextView localTextView2 = new TextView(localContext);
      localTextView2.setBackgroundColor(-1);
      localTextView2.setText(b(paramPoiItem));
      localLinearLayout1.addView(localTextView2, new LinearLayout.LayoutParams(-1, -2));
    }
    TextView localTextView3 = new TextView(localContext);
    localTextView3.setBackgroundColor(-1);
    localTextView3.setText(a(paramPoiItem));
    localLinearLayout1.addView(localTextView3, new LinearLayout.LayoutParams(-1, -2));
    a(localLinearLayout1, paramPoiItem, localContext);
    TextView localTextView4 = new TextView(localContext);
    localTextView4.setText("");
    localTextView4.setHeight(5);
    localTextView4.setWidth(1);
    localLinearLayout1.addView(localTextView4);
    return localLinearLayout1;
  }

  protected boolean onTap(int paramInt)
  {
    super.onTap(paramInt);
    return showPopupWindow(paramInt);
  }

  public boolean removeFromMap()
  {
    if (this.d == null)
      throw new UnsupportedOperationException("poioverlay must be added to map frist!");
    if (!this.e)
      return false;
    boolean bool = this.d.getOverlays().remove(this);
    if (bool)
    {
      if (this.a != null)
      {
        this.a.a();
        closePopupWindow();
      }
      this.e = false;
    }
    return bool;
  }

  public boolean showPopupWindow(int paramInt)
  {
    if (!this.c)
      return false;
    if (this.d == null)
      throw new UnsupportedOperationException("poioverlay must be added to map first!");
    PoiItem localPoiItem = (PoiItem)this.b.get(paramInt);
    View localView = getPopupView((PoiItem)this.b.get(paramInt));
    this.a = new t(this.d, localView, localPoiItem.getPoint(), getPopupBackground(), getLayoutParam(paramInt));
    this.d.a().b.a(localPoiItem.getPoint());
    this.a.b();
    return true;
  }

  public int size()
  {
    return this.b.size();
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.amap.mapapi.map.PoiOverlay
 * JD-Core Version:    0.6.0
 */