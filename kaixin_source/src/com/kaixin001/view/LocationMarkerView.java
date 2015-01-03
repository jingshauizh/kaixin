package com.kaixin001.view;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.kaixin001.lbs.RefreshLandmarkProxy;
import com.kaixin001.lbs.RefreshLandmarkProxy.IRefreshLandmarkListCallback;

public class LocationMarkerView extends FrameLayout
  implements RefreshLandmarkProxy.IRefreshLandmarkListCallback, View.OnClickListener
{
  private Button backgroundBtn;
  private LinearLayout container;
  private View deleteBtn;
  private RefreshLandmarkProxy landMarker;
  private String location;
  private ImageView locationImg;
  private ProgressBar refreshProgress;
  private LocationState state = LocationState.NONE;
  private TextView waitingText;

  public LocationMarkerView(Context paramContext)
  {
    super(paramContext);
    initView((Activity)paramContext);
  }

  public LocationMarkerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initView((Activity)paramContext);
  }

  public LocationMarkerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initView((Activity)paramContext);
  }

  private void initView(Activity paramActivity)
  {
    View localView = ((LayoutInflater)paramActivity.getSystemService("layout_inflater")).inflate(2130903402, this);
    this.container = ((LinearLayout)localView.findViewById(2131363942));
    this.locationImg = ((ImageView)localView.findViewById(2131363943));
    this.deleteBtn = localView.findViewById(2131363945);
    this.backgroundBtn = ((Button)localView.findViewById(2131363944));
    this.backgroundBtn.setOnClickListener(this);
    this.refreshProgress = ((ProgressBar)localView.findViewById(2131363946));
    this.waitingText = ((TextView)localView.findViewById(2131363947));
    this.landMarker = new RefreshLandmarkProxy(paramActivity, this);
    this.deleteBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        LocationMarkerView.this.toggleState();
      }
    });
  }

  private void updateUI(LocationState paramLocationState, String paramString)
  {
    this.container.setVisibility(0);
    this.state = paramLocationState;
    switch ($SWITCH_TABLE$com$kaixin001$view$LocationMarkerView$LocationState()[paramLocationState.ordinal()])
    {
    default:
      return;
    case 1:
      this.backgroundBtn.setVisibility(8);
      this.refreshProgress.setVisibility(0);
      this.locationImg.setVisibility(8);
      this.deleteBtn.setVisibility(8);
      this.waitingText.setVisibility(0);
      this.waitingText.setText(2131427720);
      this.location = null;
      return;
    case 4:
      this.locationImg.setVisibility(0);
      this.deleteBtn.setVisibility(0);
      this.refreshProgress.setVisibility(8);
      this.waitingText.setVisibility(0);
      this.waitingText.setText(2131427721);
      this.backgroundBtn.setVisibility(8);
      this.location = null;
      return;
    case 2:
      this.locationImg.setVisibility(0);
      this.deleteBtn.setVisibility(0);
      this.refreshProgress.setVisibility(8);
      this.waitingText.setVisibility(8);
      this.backgroundBtn.setVisibility(0);
      this.backgroundBtn.setText(paramString);
      this.location = paramString;
      return;
    case 3:
    }
    this.container.setVisibility(8);
  }

  public String getLandmark()
  {
    if ((this.state == LocationState.SHOW) && (this.landMarker.isLocationValid()))
      return this.location;
    return null;
  }

  public Location getLocation()
  {
    if ((this.state == LocationState.SHOW) && (this.landMarker.isLocationValid()))
      return this.landMarker.getLocation();
    return null;
  }

  public void onCancleRefreshLandmarks()
  {
    updateUI(LocationState.HIDE, null);
  }

  public void onClick(View paramView)
  {
    this.landMarker.showLandmarks(getContext());
  }

  public void onGetLandmarksFailed()
  {
    updateUI(LocationState.FAILED, null);
  }

  public void onRefreshLandmarks()
  {
    updateUI(LocationState.SEARCHING, null);
  }

  public void onSelectLandmark(String paramString)
  {
    updateUI(LocationState.SHOW, paramString);
  }

  public void toggleState()
  {
    if (this.state == LocationState.NONE)
    {
      this.landMarker.refreshLandmarks();
      return;
    }
    if (this.state != LocationState.HIDE)
    {
      updateUI(LocationState.HIDE, null);
      return;
    }
    if (this.landMarker.isLocationValid())
    {
      this.landMarker.showLandmarks(getContext());
      return;
    }
    this.landMarker.refreshLandmarks();
  }

  private static enum LocationState
  {
    static
    {
      HIDE = new LocationState("HIDE", 2);
      FAILED = new LocationState("FAILED", 3);
      NONE = new LocationState("NONE", 4);
      LocationState[] arrayOfLocationState = new LocationState[5];
      arrayOfLocationState[0] = SEARCHING;
      arrayOfLocationState[1] = SHOW;
      arrayOfLocationState[2] = HIDE;
      arrayOfLocationState[3] = FAILED;
      arrayOfLocationState[4] = NONE;
      ENUM$VALUES = arrayOfLocationState;
    }
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.view.LocationMarkerView
 * JD-Core Version:    0.6.0
 */