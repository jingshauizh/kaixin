package com.kaixin001.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.app.LoaderManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
//import com.kaixin001.util.ActivityUtil;
//import com.kaixin001.util.ImageDownLoaderManager;
//import com.kaixin001.util.ImageDownloader.RoundCornerType;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class KXFragment extends Fragment
{
  public Dialog dialog;
 // private ImageDownLoaderManager imageDownLoaderManager = ImageDownLoaderManager.getInstance();
  public SparseArray<Dialog> mManagedDialogs;

  private Dialog createDialog()
  {
    this.dialog = onCreateDialog();
    if (this.dialog == null)
      this.dialog = new Dialog(getActivity());
    return this.dialog;
  }

  private Dialog createDialog(Integer paramInteger)
  {
    Dialog localDialog = onCreateDialog(paramInteger.intValue());
    if (localDialog == null)
      throw new IllegalArgumentException("Activity#onCreateDialog did not create a dialog for id " + paramInteger);
    return localDialog;
  }

  private void dismissAllDialogs()
  {
    int i;
    if (this.mManagedDialogs != null)
      i = this.mManagedDialogs.size();
    return;
  }

  protected boolean cancelTask(AsyncTask paramAsyncTask)
  {
    if ((paramAsyncTask != null) && (paramAsyncTask.getStatus() != AsyncTask.Status.FINISHED) && (!paramAsyncTask.isCancelled()))
    {
      paramAsyncTask.cancel(true);
      return true;
    }
    return false;
  }

  public int dipToPx(float paramFloat)
  {
    return (int)(0.5F + paramFloat * getResources().getDisplayMetrics().density);
  }

  public void dismissDialog()
  {
    if ((this.dialog != null) && (this.dialog.isShowing()))
      this.dialog.dismiss();
    this.dialog = null;
  }

  public final void dismissDialog(int paramInt)
  {
    if (this.mManagedDialogs == null){
    	 return;
    }
    Dialog localDialog;
   
     
      localDialog = (Dialog)this.mManagedDialogs.get(paramInt);
    
    localDialog.dismiss();
  }

  public void displayOtherShapPicture(ImageView paramImageView, String paramString1, String paramString2, int paramInt)
  {
    //this.imageDownLoaderManager.displayOtherShapPicture(this, paramImageView, paramString1, paramString2, paramInt, null);
  }

  public void displayPicture(ImageView paramImageView, String paramString, int paramInt)
  {
    //this.imageDownLoaderManager.displayPicture(this, paramImageView, paramString, paramInt, null);
  }

  public void displayPicture(Object paramObject, ImageView paramImageView, String paramString1, String paramString2, int paramInt)
  {
    //this.imageDownLoaderManager.displayPicture(this, paramImageView, paramString1, paramString2, paramInt, null);
  }

  public void displayPictureCancel(ImageView paramImageView)
  {
    //this.imageDownLoaderManager.removeImageViewItem(paramImageView);
  }

  public void displayPictureExt(ImageView paramImageView, String paramString, int paramInt)
  {
    //this.imageDownLoaderManager.displayPictureExt(this, paramImageView, paramString, paramInt, null);
  }

  public void displayRoundPicture(ImageView paramImageView, String paramString,  int paramInt)
  {
    //this.imageDownLoaderManager.displayOtherShapPicture(this, paramImageView, paramString, "round", paramInt, null);
  }

  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    super.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
  }

  public View findViewById(int paramInt)
  {
    if (getView() == null)
      return null;
    return getView().findViewById(paramInt);
  }

  public boolean getIsCanLoad()
  {
    //return this.imageDownLoaderManager.getIsCanLoad();
	  return false;
  }

  public LoaderManager getLoaderManager()
  {
    return super.getLoaderManager();
  }

  public void hideKeyboard()
  {
    //if (getActivity() != null)
     // ActivityUtil.hideInputMethod(getActivity());
  }

  public boolean isNeedReturn()
  {
    return (getActivity() == null) || (getView() == null);
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  public boolean onContextItemSelected(MenuItem paramMenuItem)
  {
    return super.onContextItemSelected(paramMenuItem);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public Animation onCreateAnimation(int paramInt1, boolean paramBoolean, int paramInt2)
  {
    return super.onCreateAnimation(paramInt1, paramBoolean, paramInt2);
  }

  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
  {
    super.onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
  }

  protected Dialog onCreateDialog()
  {
    return null;
  }

  protected Dialog onCreateDialog(int paramInt)
  {
    return null;
  }

  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
  }

  public void onDestroy()
  {
    super.onDestroy();
  }

  public void onDestroyOptionsMenu()
  {
    super.onDestroyOptionsMenu();
  }

  public void onDestroyView()
  {
    super.onDestroyView();
  }

  public void onDetach()
  {
    super.onDetach();
  }

  public void onHiddenChanged(boolean paramBoolean)
  {
    super.onHiddenChanged(paramBoolean);
  }

  public void onInflate(Activity paramActivity, AttributeSet paramAttributeSet, Bundle paramBundle)
  {
    super.onInflate(paramActivity, paramAttributeSet, paramBundle);
  }

  public void onLowMemory()
  {
    super.onLowMemory();
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    return super.onOptionsItemSelected(paramMenuItem);
  }

  public void onOptionsMenuClosed(Menu paramMenu)
  {
    super.onOptionsMenuClosed(paramMenu);
  }

  public void onPause()
  {
    //this.imageDownLoaderManager.cancel(this);
    super.onPause();
  }

  public void onPrepareOptionsMenu(Menu paramMenu)
  {
    super.onPrepareOptionsMenu(paramMenu);
  }

  public void onResume()
  {
    super.onResume();
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
  }

  public void onStart()
  {
    super.onStart();
  }

  public void onStop()
  {
    super.onStop();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
  }

  public void onViewStateRestored(Bundle paramBundle)
  {
    super.onViewStateRestored(paramBundle);
  }

  public int pxTodip(float paramFloat)
  {
    return (int)(0.5F + paramFloat / getResources().getDisplayMetrics().density);
  }

  public void registerForContextMenu(View paramView)
  {
    super.registerForContextMenu(paramView);
  }

  public final void removeDialog(int paramInt)
  {
    if (this.mManagedDialogs == null){
    	 return;
    }
    Dialog localDialog;
    
     
      localDialog = (Dialog)this.mManagedDialogs.get(paramInt);
    
    localDialog.dismiss();
    this.mManagedDialogs.remove(paramInt);
  }

  public void setArguments(Bundle paramBundle)
  {
    super.setArguments(paramBundle);
  }

  public void setCanLoad()
  {
    //this.imageDownLoaderManager.setCanLoad();
  }

  public void setHasOptionsMenu(boolean paramBoolean)
  {
    super.setHasOptionsMenu(paramBoolean);
  }

  public void setInitialSavedState(Fragment.SavedState paramSavedState)
  {
    super.setInitialSavedState(paramSavedState);
  }

  public void setMenuVisibility(boolean paramBoolean)
  {
    super.setMenuVisibility(paramBoolean);
  }

  public void setNotCanLoad()
  {
    //this.imageDownLoaderManager.setNotCanLoad();
  }

  public void setRetainInstance(boolean paramBoolean)
  {
    super.setRetainInstance(paramBoolean);
  }

  public void setTargetFragment(Fragment paramFragment, int paramInt)
  {
    super.setTargetFragment(paramFragment, paramInt);
  }

  public void setUserVisibleHint(boolean paramBoolean)
  {
    super.setUserVisibleHint(paramBoolean);
  }

  public void showDialog()
  {
    this.dialog = createDialog();
    if ((this.dialog != null) && (!this.dialog.isShowing()))
      this.dialog.show();
  }

  public final void showDialog(int paramInt)
  {
    if (this.mManagedDialogs == null)
      this.mManagedDialogs = new SparseArray();
    Dialog localDialog = (Dialog)this.mManagedDialogs.get(paramInt);
    if (localDialog == null)
    {
      localDialog = createDialog(Integer.valueOf(paramInt));
      this.mManagedDialogs.put(paramInt, localDialog);
    }
    localDialog.show();
  }

  public void showKeyboard()
  {
    //if (getActivity() != null)
      //ActivityUtil.showInputMethod(getActivity());
  }

  public void unregisterForContextMenu(View paramView)
  {
    super.unregisterForContextMenu(paramView);
  }

  public abstract class KXAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result>
  {
    public KXAsyncTask()
    {
    }

    protected Result doInBackground(Params[] paramArrayOfParams)
    {
      if ((KXFragment.this.getActivity() == null) || (KXFragment.this.getView() == null))
        return null;
      try
      {
    	  Result localObject = doInBackgroundA(paramArrayOfParams);
        return localObject;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      
    }

    protected abstract Result doInBackgroundA(Params[] paramArrayOfParams);

    protected void onCancelled()
    {
      if ((KXFragment.this.getActivity() == null) || (KXFragment.this.getView() == null))
        return;
      try
      {
        onCancelledA();
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }

    protected void onCancelledA()
    {
    }

    protected void onPostExecute(Result paramResult)
    {
      if ((KXFragment.this.getActivity() == null) || (KXFragment.this.getView() == null) || (paramResult == null))
        return;
      try
      {
        onPostExecuteA(paramResult);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }

    protected abstract void onPostExecuteA(Result paramResult);

    protected void onPreExecute()
    {
      if ((KXFragment.this.getActivity() == null) || (KXFragment.this.getView() == null))
        return;
      try
      {
        onPreExecuteA();
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }

    protected abstract void onPreExecuteA();

    protected void onProgressUpdate(Progress[] paramArrayOfProgress)
    {
      if ((KXFragment.this.getActivity() == null) || (KXFragment.this.getView() == null) || (paramArrayOfProgress == null))
        return;
      try
      {
        onProgressUpdateA(paramArrayOfProgress);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }

    protected abstract void onProgressUpdateA(Progress[] paramArrayOfProgress);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.fragment.KXFragment
 * JD-Core Version:    0.6.0
 */