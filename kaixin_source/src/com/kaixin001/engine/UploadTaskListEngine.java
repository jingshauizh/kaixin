package com.kaixin001.engine;

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.kaixin001.activity.KXApplication;
import com.kaixin001.db.UpLoadTaskListDBAdapter;
import com.kaixin001.db.UpLoadTaskListDBAdapter.TaskParameters;
import com.kaixin001.item.CircleRecordUploadTask;
import com.kaixin001.item.DiaryUploadTask;
import com.kaixin001.item.KXUploadTask;
import com.kaixin001.item.MessageUploadTask;
import com.kaixin001.item.PhotoUploadTask;
import com.kaixin001.item.RecordUploadTask;
import com.kaixin001.model.User;
import com.kaixin001.util.KXLog;
import java.util.ArrayList;
import java.util.Iterator;

public class UploadTaskListEngine
  implements Runnable
{
  public static final int MSG_UPLOAD_CIRCLE_RECORD_ERROR = 10007;
  public static final int MSG_UPLOAD_CIRCLE_RECORD_SUCCESS = 10006;
  public static final int MSG_UPLOAD_MESSAGE_TASK_ERROR = 10005;
  public static final int MSG_UPLOAD_TASK_ERROR = 10003;
  public static final int MSG_UPLOAD_TASK_LIST_CHANGED = 10001;
  public static final int MSG_UPLOAD_TASK_PROGRESS = 10002;
  public static final int MSG_UPLOAD_TASK_SUCCESS = 10004;
  private static final int RETRY_TIMES = 1;
  private static final String TAG = "UploadTaskListModel";
  private static UploadTaskListEngine sInstance;
  boolean isClearred = false;
  private Context mContext = null;
  private KXUploadTask mCurrentTask = null;
  private UpLoadTaskListDBAdapter mDBAdapter = null;
  private ArrayList<KXUploadTask> mFinishedTaskList = null;
  private boolean mIsRunning = false;
  private boolean mIsStop = false;
  private ArrayList<Handler> mListHandlers = new ArrayList();
  private Object mObjLock = new Object();
  private Thread mThread = null;
  private ArrayList<KXUploadTask> mUploadingTaskList = null;
  private ArrayList<KXUploadTask> mWaitTaskList = null;
  private boolean mbIsInitialized = false;
  private long mnDiaryBlankTime = 65L;
  private long mnRecordBlankTime = 15L;
  private long[] mnTimeArray = new long[3];

  static
  {
    if (!UploadTaskListEngine.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      sInstance = null;
      return;
    }
  }

  private UploadTaskListEngine()
  {
    initThread();
    startThread();
    this.isClearred = false;
  }

  private boolean addUploadTask(KXUploadTask paramKXUploadTask, boolean paramBoolean)
  {
    if (paramKXUploadTask == null)
      return false;
    if (!isChatTask(paramKXUploadTask))
    {
      insertTaskInDB(paramKXUploadTask);
      debug("insert task to db, task id = " + paramKXUploadTask.getTaskId() + ", content = " + paramKXUploadTask.getTitle());
      if (this.mWaitTaskList == null)
        this.mWaitTaskList = new ArrayList();
    }
    int j;
    label113: int k;
    label178: label219: label220: int m;
    label279: label293: label307: label308: label360: int n;
    label414: label459: int i1;
    while (true)
    {
      int i;
      while (!isChatTask((KXUploadTask)this.mWaitTaskList.get(k - 1)))
        synchronized (this.mWaitTaskList)
        {
          i = this.mWaitTaskList.size();
          if (isChatTask(paramKXUploadTask))
          {
            j = i;
            break label655;
            do
            {
              this.mWaitTaskList.add(j, paramKXUploadTask);
              debug("wait list size = " + this.mWaitTaskList.size());
              if (paramBoolean)
                notifyUploadTask();
              broadcastMessage(10001, 0, null);
              return true;
              if (isChatTask(this.mCurrentTask))
                break;
              stopCurrentTask();
              break;
            }
            while (isChatTask((KXUploadTask)this.mWaitTaskList.get(j - 1)));
            j--;
            break label655;
          }
          if (paramKXUploadTask.getTaskType() != 3)
            break label293;
          k = i;
          break label663;
          if ((k < 0) || (k > i))
            break label279;
          this.mWaitTaskList.add(k, paramKXUploadTask);
        }
      k--;
      break label663;
      if ($assertionsDisabled)
        continue;
      throw new AssertionError();
      if (paramKXUploadTask.getTaskType() == 2)
      {
        m = i;
        break label671;
        while (((KXUploadTask)this.mWaitTaskList.get(m - 1)).getTaskType() != 3)
        {
          if ((m < 0) || (m > i))
            break label360;
          this.mWaitTaskList.add(m, paramKXUploadTask);
          break;
        }
        m--;
        break label671;
        if ($assertionsDisabled)
          continue;
        throw new AssertionError();
      }
      if (paramKXUploadTask.getTaskType() == 1)
      {
        n = i;
        break label679;
        do
        {
          if ((n < 0) || (n > i))
            break label459;
          this.mWaitTaskList.add(n, paramKXUploadTask);
          break;
          if (((KXUploadTask)this.mWaitTaskList.get(n - 1)).getTaskType() == 3)
            break label687;
        }
        while (((KXUploadTask)this.mWaitTaskList.get(n - 1)).getTaskType() != 2);
        break label687;
        if ($assertionsDisabled)
          continue;
        throw new AssertionError();
      }
      if (paramKXUploadTask.getTaskType() != 0)
        break label592;
      i1 = i;
      break label693;
      label487: if ((i1 < 0) || (i1 > i))
        break label578;
      this.mWaitTaskList.add(i1, paramKXUploadTask);
    }
    label655: label663: label671: label679: label687: label691: label693: label701: label705: 
    while (true)
    {
      if ((((KXUploadTask)this.mWaitTaskList.get(i1 - 1)).getTaskType() != 3) && (((KXUploadTask)this.mWaitTaskList.get(i1 - 1)).getTaskType() != 2))
      {
        if (((KXUploadTask)this.mWaitTaskList.get(i1 - 1)).getTaskType() != 1)
          break label487;
        break label701;
        label578: if ($assertionsDisabled)
          break label113;
        throw new AssertionError();
        label592: if ((paramKXUploadTask.getTaskType() == 4) || (paramKXUploadTask.getTaskType() == 5))
        {
          this.mWaitTaskList.add(paramKXUploadTask);
          break label113;
        }
        if (paramKXUploadTask.getTaskType() == 10)
        {
          this.mWaitTaskList.add(paramKXUploadTask);
          break label113;
        }
        if ($assertionsDisabled)
          break label113;
        throw new AssertionError();
        if (j > 0)
          break label178;
        break;
        if (k > 0)
          break label219;
        break label220;
        if (m > 0)
          break label307;
        break label308;
        while (true)
        {
          if (n > 0)
            break label691;
          break;
          n--;
        }
        break label414;
      }
      while (true)
      {
        if (i1 > 0)
          break label705;
        break;
        i1--;
      }
    }
  }

  private KXUploadTask createTaskFromParameters(UpLoadTaskListDBAdapter.TaskParameters paramTaskParameters)
  {
    Object localObject;
    switch (paramTaskParameters.mData16)
    {
    case 6:
    case 7:
    case 8:
    case 9:
    default:
      boolean bool = $assertionsDisabled;
      localObject = null;
      if (bool)
        break;
      throw new AssertionError();
    case 3:
      localObject = new DiaryUploadTask(getContext());
    case 2:
    case 1:
    case 0:
    case 4:
    case 5:
    case 10:
    }
    while ((!$assertionsDisabled) && (localObject == null))
    {
      throw new AssertionError();
      localObject = new PhotoUploadTask(getContext());
      continue;
      localObject = new RecordUploadTask(getContext());
      ((KXUploadTask)localObject).setTaskType(1);
      continue;
      localObject = new RecordUploadTask(getContext());
      ((KXUploadTask)localObject).setTaskType(0);
      continue;
      localObject = new MessageUploadTask(getContext(), 3);
      ((KXUploadTask)localObject).setTaskType(4);
      continue;
      localObject = new MessageUploadTask(getContext(), 5);
      ((KXUploadTask)localObject).setTaskType(5);
      continue;
      localObject = new CircleRecordUploadTask(getContext());
      ((KXUploadTask)localObject).setTaskType(10);
    }
    ((KXUploadTask)localObject).initUploadTask(paramTaskParameters);
    return (KXUploadTask)localObject;
  }

  private void debug(String paramString)
  {
    KXLog.d("UploadTaskListModel", paramString);
  }

  private long getCurrentSec()
  {
    return System.currentTimeMillis();
  }

  private UpLoadTaskListDBAdapter getDBAdapter()
  {
    monitorenter;
    try
    {
      if (this.mDBAdapter == null)
        this.mDBAdapter = new UpLoadTaskListDBAdapter(getContext());
      UpLoadTaskListDBAdapter localUpLoadTaskListDBAdapter = this.mDBAdapter;
      return localUpLoadTaskListDBAdapter;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public static UploadTaskListEngine getInstance()
  {
    monitorenter;
    try
    {
      if ((sInstance == null) || (sInstance.isClearred))
        sInstance = new UploadTaskListEngine();
      UploadTaskListEngine localUploadTaskListEngine = sInstance;
      return localUploadTaskListEngine;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  private KXUploadTask getOneUploadTask()
  {
    while (true)
    {
      int j;
      synchronized (this.mWaitTaskList)
      {
        int i = this.mWaitTaskList.size();
        j = 0;
        if (j >= i)
          return null;
        KXUploadTask localKXUploadTask = (KXUploadTask)this.mWaitTaskList.get(j);
        if (localKXUploadTask.getTaskStatus() != 3)
          return localKXUploadTask;
      }
      j++;
    }
  }

  private long getSleepTime(int paramInt)
  {
    long l1 = 0L;
    if (paramInt == 0)
    {
      l1 = 1000L * this.mnRecordBlankTime - (getCurrentSec() - this.mnTimeArray[1]);
      if (l1 < 0L)
      {
        l1 = 0L;
        this.mnTimeArray[1] = getCurrentSec();
      }
    }
    do
    {
      return l1;
      if (l1 <= 1000L * this.mnRecordBlankTime)
        break;
      l1 = 1000L * this.mnRecordBlankTime;
      break;
      if (paramInt != 1)
        continue;
      long l3 = 1000L * this.mnRecordBlankTime - (getCurrentSec() - this.mnTimeArray[2]);
      if (l3 < 0L)
        l3 = 0L;
      while (true)
      {
        this.mnTimeArray[2] = getCurrentSec();
        return l3;
        if (l3 <= 1000L * this.mnRecordBlankTime)
          continue;
        l3 = 1000L * this.mnRecordBlankTime;
      }
    }
    while (paramInt != 3);
    long l2 = 1000L * this.mnDiaryBlankTime - (getCurrentSec() - this.mnTimeArray[0]);
    if (l2 < 0L)
      l2 = 0L;
    while (true)
    {
      this.mnTimeArray[0] = getCurrentSec();
      return l2;
      if (l2 <= 1000L * this.mnDiaryBlankTime)
        continue;
      l2 = 1000L * this.mnDiaryBlankTime;
    }
  }

  private void initThread()
  {
    clear();
    this.mWaitTaskList = new ArrayList();
    this.mFinishedTaskList = new ArrayList();
    this.mUploadingTaskList = new ArrayList();
    if (this.mContext != null)
      loadFromDb();
    do
      return;
    while ($assertionsDisabled);
    throw new AssertionError();
  }

  private void insertTaskInDB(KXUploadTask paramKXUploadTask)
  {
    if ((paramKXUploadTask == null) || (paramKXUploadTask.getTaskStatus() != 0))
      return;
    ContentValues localContentValues = paramKXUploadTask.toContentValues();
    paramKXUploadTask.setTaskId((int)getInstance().getDBAdapter().insertTask(localContentValues));
  }

  private void notifyUploadTask()
  {
    try
    {
      synchronized (this.mObjLock)
      {
        this.mObjLock.notify();
        return;
      }
    }
    catch (Exception localException)
    {
      KXLog.e("UploadTaskListModel", "addUploadTask", localException);
    }
  }

  private void resetUploadingTime(int paramInt)
  {
    if (paramInt == 0)
      this.mnTimeArray[1] = getCurrentSec();
    do
    {
      return;
      if (paramInt != 1)
        continue;
      this.mnTimeArray[2] = getCurrentSec();
      return;
    }
    while (paramInt != 3);
    this.mnTimeArray[0] = getCurrentSec();
  }

  private void startThread()
  {
    if (this.mThread == null)
      this.mThread = new Thread(this);
    do
      try
      {
        KXLog.d("UploadTaskListEngine", "start thread");
        this.mThread.start();
        return;
      }
      catch (Exception localException)
      {
        KXLog.e("UploadTaskListEngine", localException.toString());
        return;
      }
    while ($assertionsDisabled);
    throw new AssertionError();
  }

  private void stopCurrentTask()
  {
    if (this.mCurrentTask != null)
    {
      this.mCurrentTask.doCancel();
      this.mCurrentTask.setNType(-1);
    }
  }

  private void stopThread()
  {
    try
    {
      if (this.mThread != null)
        this.mThread.interrupt();
      return;
    }
    catch (Exception localException)
    {
      KXLog.e("upload thread reset thread", localException.toString());
      return;
    }
    finally
    {
      this.mThread = null;
    }
    throw localObject;
  }

  // ERROR //
  private void waitForUploadTask()
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_1
    //   2: aload_0
    //   3: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   6: ifnull +44 -> 50
    //   9: aload_0
    //   10: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   13: invokevirtual 169	java/util/ArrayList:size	()I
    //   16: ifle +34 -> 50
    //   19: aload_0
    //   20: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   23: astore 5
    //   25: aload 5
    //   27: monitorenter
    //   28: aload_0
    //   29: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   32: invokevirtual 331	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   35: astore 7
    //   37: aload 7
    //   39: invokeinterface 336 1 0
    //   44: ifne +27 -> 71
    //   47: aload 5
    //   49: monitorexit
    //   50: iload_1
    //   51: ifeq +19 -> 70
    //   54: aload_0
    //   55: getfield 85	com/kaixin001/engine/UploadTaskListEngine:mObjLock	Ljava/lang/Object;
    //   58: astore_3
    //   59: aload_3
    //   60: monitorenter
    //   61: aload_0
    //   62: getfield 85	com/kaixin001/engine/UploadTaskListEngine:mObjLock	Ljava/lang/Object;
    //   65: invokevirtual 339	java/lang/Object:wait	()V
    //   68: aload_3
    //   69: monitorexit
    //   70: return
    //   71: aload 7
    //   73: invokeinterface 343 1 0
    //   78: checkcast 143	com/kaixin001/item/KXUploadTask
    //   81: astore 8
    //   83: aload 8
    //   85: ifnull -48 -> 37
    //   88: aload 8
    //   90: invokevirtual 261	com/kaixin001/item/KXUploadTask:getTaskStatus	()I
    //   93: iconst_3
    //   94: if_icmpeq -57 -> 37
    //   97: iconst_0
    //   98: istore_1
    //   99: goto -62 -> 37
    //   102: astore 6
    //   104: aload 5
    //   106: monitorexit
    //   107: aload 6
    //   109: athrow
    //   110: astore_2
    //   111: ldc 30
    //   113: ldc_w 344
    //   116: aload_2
    //   117: invokestatic 298	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   120: return
    //   121: astore 4
    //   123: aload_3
    //   124: monitorexit
    //   125: aload 4
    //   127: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   28	37	102	finally
    //   37	50	102	finally
    //   71	83	102	finally
    //   88	97	102	finally
    //   104	107	102	finally
    //   2	28	110	java/lang/Exception
    //   54	61	110	java/lang/Exception
    //   107	110	110	java/lang/Exception
    //   125	128	110	java/lang/Exception
    //   61	70	121	finally
    //   123	125	121	finally
  }

  public boolean addUploadTask(KXUploadTask paramKXUploadTask)
  {
    return addUploadTask(paramKXUploadTask, true);
  }

  public void broadcastMessage(int paramInt1, int paramInt2, Object paramObject)
  {
    ArrayList localArrayList = this.mListHandlers;
    monitorenter;
    int i = 0;
    while (true)
    {
      Handler localHandler;
      try
      {
        Iterator localIterator = this.mListHandlers.iterator();
        if (!localIterator.hasNext())
          return;
        localHandler = (Handler)localIterator.next();
        i++;
        if ((i > 1) && ((paramInt1 == 10003) || (paramInt1 == 10004)))
        {
          if (localHandler == null)
            continue;
          Message localMessage2 = Message.obtain();
          localMessage2.what = paramInt1;
          localMessage2.arg1 = paramInt2;
          localMessage2.obj = paramObject;
          localHandler.sendMessage(localMessage2);
          continue;
        }
      }
      finally
      {
        monitorexit;
      }
      if (localHandler == null)
        continue;
      Message localMessage1 = Message.obtain();
      localMessage1.what = paramInt1;
      localMessage1.arg1 = paramInt2;
      localMessage1.obj = paramObject;
      localHandler.sendMessage(localMessage1);
    }
  }

  public boolean cancelCurrentUploadingTask(KXUploadTask paramKXUploadTask)
  {
    paramKXUploadTask.setNType(-1);
    paramKXUploadTask.doCancel();
    assert (this.mUploadingTaskList.size() <= 1);
    if ((this.mUploadingTaskList.size() > 0) && (((KXUploadTask)this.mUploadingTaskList.get(0)).getTaskId() == paramKXUploadTask.getTaskId()))
    {
      deleteTaskInDB((KXUploadTask)this.mUploadingTaskList.get(0));
      paramKXUploadTask.deleteDraft();
    }
    while (true)
    {
      synchronized (this.mUploadingTaskList)
      {
        if (this.mUploadingTaskList.size() <= 0)
          continue;
        this.mUploadingTaskList.remove(0);
        i = 1;
        broadcastMessage(10001, 0, null);
        return i;
      }
      int i = 0;
    }
  }

  public void clear()
  {
    this.isClearred = true;
    if (this.mWaitTaskList != null);
    synchronized (this.mWaitTaskList)
    {
      this.mWaitTaskList.clear();
      this.mWaitTaskList = null;
      if (this.mFinishedTaskList != null)
      {
        this.mFinishedTaskList.clear();
        this.mFinishedTaskList = null;
      }
      if (this.mUploadingTaskList != null)
      {
        this.mUploadingTaskList.clear();
        this.mUploadingTaskList = null;
      }
      return;
    }
  }

  public boolean deleteTask(KXUploadTask paramKXUploadTask)
  {
    boolean bool2;
    Integer localInteger;
    synchronized (this.mWaitTaskList)
    {
      boolean bool1 = this.mWaitTaskList.contains(paramKXUploadTask);
      bool2 = false;
      localInteger = null;
      if (bool1)
      {
        bool2 = this.mWaitTaskList.remove(paramKXUploadTask);
        localInteger = Integer.valueOf(1);
      }
      synchronized (this.mUploadingTaskList)
      {
        if (this.mUploadingTaskList.contains(paramKXUploadTask))
          bool2 = this.mUploadingTaskList.remove(paramKXUploadTask);
      }
    }
    synchronized (this.mFinishedTaskList)
    {
      if (this.mFinishedTaskList.contains(paramKXUploadTask))
        bool2 = this.mFinishedTaskList.remove(paramKXUploadTask);
      if (bool2)
      {
        paramKXUploadTask.deleteDraft();
        if (this.mDBAdapter != null)
          this.mDBAdapter.deleteATask(paramKXUploadTask.getTaskId());
        broadcastMessage(10001, 1, localInteger);
      }
      return bool2;
      localObject1 = finally;
      monitorexit;
      throw localObject1;
      localObject2 = finally;
      monitorexit;
      throw localObject2;
    }
  }

  public void deleteTaskInDB(KXUploadTask paramKXUploadTask)
  {
    if (paramKXUploadTask == null)
      return;
    getDBAdapter().deleteATask(paramKXUploadTask.getTaskId());
  }

  public void deleteTasksInDB(String paramString)
  {
    getDBAdapter().deleteTasks(paramString);
  }

  public KXUploadTask findTaskById(int paramInt)
  {
    monitorenter;
    while (true)
    {
      KXUploadTask localKXUploadTask;
      int i;
      int n;
      int k;
      try
      {
        ArrayList localArrayList1 = this.mWaitTaskList;
        localKXUploadTask = null;
        if (localArrayList1 == null)
          continue;
        ArrayList localArrayList2 = this.mWaitTaskList;
        monitorenter;
        i = 0;
        try
        {
          int j = this.mWaitTaskList.size();
          localKXUploadTask = null;
          if (i < j)
            continue;
          monitorexit;
          if ((localKXUploadTask != null) || (this.mUploadingTaskList == null))
            continue;
          n = 0;
          if (n >= this.mUploadingTaskList.size())
          {
            if ((localKXUploadTask != null) || (this.mFinishedTaskList == null))
              continue;
            k = 0;
            int m = this.mFinishedTaskList.size();
            if (k < m)
              break label196;
            return localKXUploadTask;
            if (paramInt != ((KXUploadTask)this.mWaitTaskList.get(i)).getTaskId())
              break label238;
            localKXUploadTask = (KXUploadTask)this.mWaitTaskList.get(i);
            continue;
          }
        }
        finally
        {
          monitorexit;
        }
      }
      finally
      {
        monitorexit;
      }
      if (paramInt == ((KXUploadTask)this.mUploadingTaskList.get(n)).getTaskId())
      {
        localKXUploadTask = (KXUploadTask)this.mUploadingTaskList.get(n);
        continue;
        label196: if (paramInt == ((KXUploadTask)this.mFinishedTaskList.get(k)).getTaskId())
        {
          localKXUploadTask = (KXUploadTask)this.mFinishedTaskList.get(k);
          continue;
        }
        k++;
        continue;
        label238: i++;
        continue;
      }
      n++;
    }
  }

  public Context getContext()
  {
    if (this.mContext == null)
      this.mContext = KXApplication.getInstance();
    return this.mContext;
  }

  public final ArrayList<KXUploadTask> getFinishedTaskList()
  {
    return this.mFinishedTaskList;
  }

  // ERROR //
  public final int getNeedUploadingTaskCount()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   4: ifnonnull +14 -> 18
    //   7: aload_0
    //   8: new 91	java/util/ArrayList
    //   11: dup
    //   12: invokespecial 92	java/util/ArrayList:<init>	()V
    //   15: putfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   18: iconst_0
    //   19: istore_1
    //   20: aload_0
    //   21: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   24: astore_2
    //   25: aload_2
    //   26: monitorenter
    //   27: iconst_0
    //   28: istore_3
    //   29: iload_3
    //   30: aload_0
    //   31: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   34: invokevirtual 169	java/util/ArrayList:size	()I
    //   37: if_icmplt +34 -> 71
    //   40: aload_2
    //   41: monitorexit
    //   42: aload_0
    //   43: getfield 81	com/kaixin001/engine/UploadTaskListEngine:mUploadingTaskList	Ljava/util/ArrayList;
    //   46: astore 5
    //   48: aload 5
    //   50: monitorenter
    //   51: iconst_0
    //   52: istore 6
    //   54: iload 6
    //   56: aload_0
    //   57: getfield 81	com/kaixin001/engine/UploadTaskListEngine:mUploadingTaskList	Ljava/util/ArrayList;
    //   60: invokevirtual 169	java/util/ArrayList:size	()I
    //   63: if_icmplt +93 -> 156
    //   66: aload 5
    //   68: monitorexit
    //   69: iload_1
    //   70: ireturn
    //   71: aload_0
    //   72: aload_0
    //   73: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   76: iload_3
    //   77: invokevirtual 189	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   80: checkcast 143	com/kaixin001/item/KXUploadTask
    //   83: invokevirtual 130	com/kaixin001/engine/UploadTaskListEngine:isChatTask	(Lcom/kaixin001/item/KXUploadTask;)Z
    //   86: ifne +160 -> 246
    //   89: aload_0
    //   90: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   93: iload_3
    //   94: invokevirtual 189	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   97: checkcast 143	com/kaixin001/item/KXUploadTask
    //   100: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   103: iconst_5
    //   104: if_icmpeq +142 -> 246
    //   107: aload_0
    //   108: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   111: iload_3
    //   112: invokevirtual 189	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   115: checkcast 143	com/kaixin001/item/KXUploadTask
    //   118: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   121: iconst_4
    //   122: if_icmpeq +124 -> 246
    //   125: aload_0
    //   126: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   129: iload_3
    //   130: invokevirtual 189	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   133: checkcast 143	com/kaixin001/item/KXUploadTask
    //   136: invokevirtual 261	com/kaixin001/item/KXUploadTask:getTaskStatus	()I
    //   139: iconst_3
    //   140: if_icmpeq +106 -> 246
    //   143: iinc 1 1
    //   146: goto +100 -> 246
    //   149: astore 4
    //   151: aload_2
    //   152: monitorexit
    //   153: aload 4
    //   155: athrow
    //   156: aload_0
    //   157: aload_0
    //   158: getfield 81	com/kaixin001/engine/UploadTaskListEngine:mUploadingTaskList	Ljava/util/ArrayList;
    //   161: iload 6
    //   163: invokevirtual 189	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   166: checkcast 143	com/kaixin001/item/KXUploadTask
    //   169: invokevirtual 130	com/kaixin001/engine/UploadTaskListEngine:isChatTask	(Lcom/kaixin001/item/KXUploadTask;)Z
    //   172: ifne +80 -> 252
    //   175: aload_0
    //   176: getfield 81	com/kaixin001/engine/UploadTaskListEngine:mUploadingTaskList	Ljava/util/ArrayList;
    //   179: iload 6
    //   181: invokevirtual 189	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   184: checkcast 143	com/kaixin001/item/KXUploadTask
    //   187: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   190: iconst_5
    //   191: if_icmpeq +61 -> 252
    //   194: aload_0
    //   195: getfield 81	com/kaixin001/engine/UploadTaskListEngine:mUploadingTaskList	Ljava/util/ArrayList;
    //   198: iload 6
    //   200: invokevirtual 189	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   203: checkcast 143	com/kaixin001/item/KXUploadTask
    //   206: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   209: iconst_4
    //   210: if_icmpeq +42 -> 252
    //   213: aload_0
    //   214: getfield 81	com/kaixin001/engine/UploadTaskListEngine:mUploadingTaskList	Ljava/util/ArrayList;
    //   217: iload 6
    //   219: invokevirtual 189	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   222: checkcast 143	com/kaixin001/item/KXUploadTask
    //   225: invokevirtual 261	com/kaixin001/item/KXUploadTask:getTaskStatus	()I
    //   228: iconst_3
    //   229: if_icmpeq +23 -> 252
    //   232: iinc 1 1
    //   235: goto +17 -> 252
    //   238: astore 7
    //   240: aload 5
    //   242: monitorexit
    //   243: aload 7
    //   245: athrow
    //   246: iinc 3 1
    //   249: goto -220 -> 29
    //   252: iinc 6 1
    //   255: goto -201 -> 54
    //
    // Exception table:
    //   from	to	target	type
    //   29	42	149	finally
    //   71	143	149	finally
    //   151	153	149	finally
    //   54	69	238	finally
    //   156	232	238	finally
    //   240	243	238	finally
  }

  protected String getTaskTypeString(int paramInt)
  {
    if (this.mContext == null)
      return null;
    String str;
    switch (paramInt)
    {
    default:
      str = "";
    case 0:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return str;
      str = this.mContext.getString(2131427930);
      continue;
      str = this.mContext.getString(2131427931);
      continue;
      str = this.mContext.getString(2131427932);
    }
  }

  public final int getUploadingTask()
  {
    if (this.mUploadingTaskList == null)
      this.mUploadingTaskList = new ArrayList();
    int i = 0;
    for (int j = 0; ; j++)
    {
      if (j >= this.mUploadingTaskList.size())
        return i;
      if ((isChatTask((KXUploadTask)this.mUploadingTaskList.get(j))) || (((KXUploadTask)this.mUploadingTaskList.get(j)).getTaskType() == 5) || (((KXUploadTask)this.mUploadingTaskList.get(j)).getTaskType() == 4))
        continue;
      i++;
    }
  }

  public final ArrayList<KXUploadTask> getUploadingTaskList()
  {
    return this.mUploadingTaskList;
  }

  public final ArrayList<KXUploadTask> getWaitTaskList()
  {
    return this.mWaitTaskList;
  }

  public final int getWaitingArrayTaskCount()
  {
    if (this.mWaitTaskList == null)
      this.mWaitTaskList = new ArrayList();
    int i = 0;
    ArrayList localArrayList = this.mWaitTaskList;
    monitorenter;
    for (int j = 0; ; j++)
      try
      {
        if (j >= this.mWaitTaskList.size())
          return i;
        if ((isChatTask((KXUploadTask)this.mWaitTaskList.get(j))) || (((KXUploadTask)this.mWaitTaskList.get(j)).getTaskType() == 5) || (((KXUploadTask)this.mWaitTaskList.get(j)).getTaskType() == 4))
          continue;
        i++;
      }
      finally
      {
        monitorexit;
      }
  }

  public final int getWaitingTaskCount()
  {
    if (this.mWaitTaskList == null)
      this.mWaitTaskList = new ArrayList();
    if (this.mUploadingTaskList == null)
      this.mUploadingTaskList = new ArrayList();
    int i = 0;
    ArrayList localArrayList = this.mWaitTaskList;
    monitorenter;
    for (int j = 0; ; j++)
      while (true)
      {
        int k;
        try
        {
          if (j < this.mWaitTaskList.size())
            continue;
          monitorexit;
          k = 0;
          if (k >= this.mUploadingTaskList.size())
          {
            return i;
            if ((isChatTask((KXUploadTask)this.mWaitTaskList.get(j))) || (((KXUploadTask)this.mWaitTaskList.get(j)).getTaskType() == 5) || (((KXUploadTask)this.mWaitTaskList.get(j)).getTaskType() == 4))
              break;
            i++;
          }
        }
        finally
        {
          monitorexit;
        }
        if ((!isChatTask((KXUploadTask)this.mUploadingTaskList.get(k))) && (((KXUploadTask)this.mUploadingTaskList.get(k)).getTaskType() != 5) && (((KXUploadTask)this.mUploadingTaskList.get(k)).getTaskType() != 4))
          i++;
        k++;
      }
  }

  public boolean isChatTask(KXUploadTask paramKXUploadTask)
  {
    if (paramKXUploadTask == null);
    do
      return false;
    while ((paramKXUploadTask.getTaskType() != 6) && (paramKXUploadTask.getTaskType() != 7) && (paramKXUploadTask.getTaskType() != 8) && (paramKXUploadTask.getTaskType() != 9) && (paramKXUploadTask.getTaskType() != 11) && (paramKXUploadTask.getTaskType() != 12));
    return true;
  }

  public void loadFromDb()
  {
    if ((User.getInstance().getUID() == null) || (User.getInstance().getUID().length() == 0))
      return;
    ArrayList localArrayList1 = getDBAdapter().getTasks(-1, -1, -1, User.getInstance().getUID());
    ArrayList localArrayList2 = this.mWaitTaskList;
    monitorenter;
    for (int i = 0; ; i++)
    {
      UpLoadTaskListDBAdapter.TaskParameters localTaskParameters;
      try
      {
        if (i >= localArrayList1.size())
        {
          monitorexit;
          notifyUploadTask();
          return;
        }
        localTaskParameters = (UpLoadTaskListDBAdapter.TaskParameters)localArrayList1.get(i);
        if (localTaskParameters.status == 0)
        {
          this.mWaitTaskList.add(createTaskFromParameters(localTaskParameters));
          continue;
        }
        if (localTaskParameters.status == 2)
          this.mFinishedTaskList.add(createTaskFromParameters(localTaskParameters));
      }
      finally
      {
        monitorexit;
      }
      if (localTaskParameters.status != 3)
        continue;
      KXUploadTask localKXUploadTask = createTaskFromParameters(localTaskParameters);
      localKXUploadTask.setTaskStatus(0);
      this.mWaitTaskList.add(localKXUploadTask);
    }
  }

  public void reInitThread()
  {
    initThread();
  }

  public void register(Handler paramHandler)
  {
    this.mListHandlers.remove(paramHandler);
    this.mListHandlers.add(paramHandler);
  }

  public void restartTask(KXUploadTask paramKXUploadTask)
  {
    if (paramKXUploadTask == null)
      return;
    int i = paramKXUploadTask.getTaskId();
    ArrayList localArrayList = this.mWaitTaskList;
    monitorenter;
    for (int j = 0; ; j++)
      while (true)
      {
        try
        {
          if (j >= this.mWaitTaskList.size())
            return;
        }
        finally
        {
          monitorexit;
        }
        if (((KXUploadTask)this.mWaitTaskList.get(j)).getTaskId() != i)
          break;
        ((KXUploadTask)this.mWaitTaskList.get(j)).setTaskStatus(0);
        paramKXUploadTask.setProgress(0);
        this.mWaitTaskList.remove(paramKXUploadTask);
        this.mWaitTaskList.add(0, paramKXUploadTask);
        notifyUploadTask();
        broadcastMessage(10001, 0, null);
      }
  }

  // ERROR //
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: iconst_1
    //   2: putfield 89	com/kaixin001/engine/UploadTaskListEngine:mIsRunning	Z
    //   5: iconst_0
    //   6: istore_1
    //   7: aload_0
    //   8: getfield 87	com/kaixin001/engine/UploadTaskListEngine:mIsStop	Z
    //   11: ifeq +9 -> 20
    //   14: aload_0
    //   15: iconst_0
    //   16: putfield 89	com/kaixin001/engine/UploadTaskListEngine:mIsRunning	Z
    //   19: return
    //   20: aload_0
    //   21: invokespecial 456	com/kaixin001/engine/UploadTaskListEngine:getOneUploadTask	()Lcom/kaixin001/item/KXUploadTask;
    //   24: astore_3
    //   25: aload_3
    //   26: ifnull +349 -> 375
    //   29: aload_0
    //   30: aload_3
    //   31: putfield 108	com/kaixin001/engine/UploadTaskListEngine:mCurrentTask	Lcom/kaixin001/item/KXUploadTask;
    //   34: aload_3
    //   35: iconst_1
    //   36: invokevirtual 446	com/kaixin001/item/KXUploadTask:setTaskStatus	(I)V
    //   39: aload_0
    //   40: aload_3
    //   41: invokevirtual 130	com/kaixin001/engine/UploadTaskListEngine:isChatTask	(Lcom/kaixin001/item/KXUploadTask;)Z
    //   44: ifne +16 -> 60
    //   47: aload_3
    //   48: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   51: iconst_5
    //   52: if_icmpeq +8 -> 60
    //   55: aload_3
    //   56: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   59: pop
    //   60: aload_0
    //   61: getfield 81	com/kaixin001/engine/UploadTaskListEngine:mUploadingTaskList	Ljava/util/ArrayList;
    //   64: astore 4
    //   66: aload 4
    //   68: monitorenter
    //   69: aload_0
    //   70: getfield 81	com/kaixin001/engine/UploadTaskListEngine:mUploadingTaskList	Ljava/util/ArrayList;
    //   73: aload_3
    //   74: invokevirtual 198	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   77: pop
    //   78: aload 4
    //   80: monitorexit
    //   81: aload_0
    //   82: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   85: astore 7
    //   87: aload 7
    //   89: monitorenter
    //   90: aload_0
    //   91: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   94: aload_3
    //   95: invokevirtual 384	java/util/ArrayList:remove	(Ljava/lang/Object;)Z
    //   98: pop
    //   99: aload 7
    //   101: monitorexit
    //   102: aload_0
    //   103: new 136	java/lang/StringBuilder
    //   106: dup
    //   107: ldc_w 458
    //   110: invokespecial 141	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   113: aload_0
    //   114: getfield 81	com/kaixin001/engine/UploadTaskListEngine:mUploadingTaskList	Ljava/util/ArrayList;
    //   117: invokevirtual 169	java/util/ArrayList:size	()I
    //   120: invokevirtual 151	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   123: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   126: invokespecial 166	com/kaixin001/engine/UploadTaskListEngine:debug	(Ljava/lang/String;)V
    //   129: aload_0
    //   130: new 136	java/lang/StringBuilder
    //   133: dup
    //   134: ldc_w 460
    //   137: invokespecial 141	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   140: aload_0
    //   141: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   144: invokevirtual 169	java/util/ArrayList:size	()I
    //   147: invokevirtual 151	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   150: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   153: invokespecial 166	com/kaixin001/engine/UploadTaskListEngine:debug	(Ljava/lang/String;)V
    //   156: aload_0
    //   157: sipush 10001
    //   160: iconst_0
    //   161: aconst_null
    //   162: invokevirtual 182	com/kaixin001/engine/UploadTaskListEngine:broadcastMessage	(IILjava/lang/Object;)V
    //   165: iload_1
    //   166: ifeq +14 -> 180
    //   169: aload_0
    //   170: aload_3
    //   171: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   174: invokespecial 462	com/kaixin001/engine/UploadTaskListEngine:getSleepTime	(I)J
    //   177: invokestatic 466	java/lang/Thread:sleep	(J)V
    //   180: iconst_0
    //   181: istore_1
    //   182: aload_3
    //   183: invokevirtual 469	com/kaixin001/item/KXUploadTask:doUpload	()Z
    //   186: istore 23
    //   188: iload 23
    //   190: istore_1
    //   191: aload_3
    //   192: iconst_1
    //   193: aload_3
    //   194: invokevirtual 472	com/kaixin001/item/KXUploadTask:getTaskOperTimes	()I
    //   197: iadd
    //   198: invokevirtual 475	com/kaixin001/item/KXUploadTask:setTaskOperTimes	(I)V
    //   201: iload_1
    //   202: ifeq +271 -> 473
    //   205: aload_0
    //   206: ldc_w 477
    //   209: invokespecial 166	com/kaixin001/engine/UploadTaskListEngine:debug	(Ljava/lang/String;)V
    //   212: aload_3
    //   213: iconst_2
    //   214: invokevirtual 446	com/kaixin001/item/KXUploadTask:setTaskStatus	(I)V
    //   217: aload_3
    //   218: invokevirtual 480	com/kaixin001/item/KXUploadTask:updateFinishTime	()V
    //   221: aload_0
    //   222: getfield 83	com/kaixin001/engine/UploadTaskListEngine:mFinishedTaskList	Ljava/util/ArrayList;
    //   225: ifnonnull +14 -> 239
    //   228: aload_0
    //   229: new 91	java/util/ArrayList
    //   232: dup
    //   233: invokespecial 92	java/util/ArrayList:<init>	()V
    //   236: putfield 83	com/kaixin001/engine/UploadTaskListEngine:mFinishedTaskList	Ljava/util/ArrayList;
    //   239: aload_3
    //   240: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   243: bipush 8
    //   245: if_icmpeq +11 -> 256
    //   248: aload_0
    //   249: aload_3
    //   250: invokevirtual 130	com/kaixin001/engine/UploadTaskListEngine:isChatTask	(Lcom/kaixin001/item/KXUploadTask;)Z
    //   253: ifne +12 -> 265
    //   256: aload_0
    //   257: getfield 83	com/kaixin001/engine/UploadTaskListEngine:mFinishedTaskList	Ljava/util/ArrayList;
    //   260: iconst_0
    //   261: aload_3
    //   262: invokevirtual 173	java/util/ArrayList:add	(ILjava/lang/Object;)V
    //   265: aload_3
    //   266: invokevirtual 480	com/kaixin001/item/KXUploadTask:updateFinishTime	()V
    //   269: aload_3
    //   270: iconst_2
    //   271: invokevirtual 446	com/kaixin001/item/KXUploadTask:setTaskStatus	(I)V
    //   274: aload_0
    //   275: getfield 81	com/kaixin001/engine/UploadTaskListEngine:mUploadingTaskList	Ljava/util/ArrayList;
    //   278: astore 11
    //   280: aload 11
    //   282: monitorenter
    //   283: aload_0
    //   284: getfield 81	com/kaixin001/engine/UploadTaskListEngine:mUploadingTaskList	Ljava/util/ArrayList;
    //   287: aload_3
    //   288: invokevirtual 384	java/util/ArrayList:remove	(Ljava/lang/Object;)Z
    //   291: pop
    //   292: aload 11
    //   294: monitorexit
    //   295: iload_1
    //   296: ifeq +11 -> 307
    //   299: aload_0
    //   300: aload_3
    //   301: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   304: invokespecial 482	com/kaixin001/engine/UploadTaskListEngine:resetUploadingTime	(I)V
    //   307: aload_3
    //   308: invokevirtual 485	com/kaixin001/item/KXUploadTask:getNType	()I
    //   311: iconst_m1
    //   312: if_icmpeq +25 -> 337
    //   315: aload_0
    //   316: aload_3
    //   317: invokevirtual 130	com/kaixin001/engine/UploadTaskListEngine:isChatTask	(Lcom/kaixin001/item/KXUploadTask;)Z
    //   320: ifne +17 -> 337
    //   323: aload_0
    //   324: aload_3
    //   325: invokevirtual 488	com/kaixin001/engine/UploadTaskListEngine:updateTaskInDB	(Lcom/kaixin001/item/KXUploadTask;)V
    //   328: aload_0
    //   329: aconst_null
    //   330: putfield 108	com/kaixin001/engine/UploadTaskListEngine:mCurrentTask	Lcom/kaixin001/item/KXUploadTask;
    //   333: lconst_0
    //   334: invokestatic 466	java/lang/Thread:sleep	(J)V
    //   337: iload_1
    //   338: ifeq +249 -> 587
    //   341: aload_3
    //   342: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   345: iconst_5
    //   346: if_icmpeq +241 -> 587
    //   349: aload_3
    //   350: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   353: iconst_4
    //   354: if_icmpeq +233 -> 587
    //   357: aload_0
    //   358: sipush 10004
    //   361: iconst_0
    //   362: aload_3
    //   363: invokevirtual 182	com/kaixin001/engine/UploadTaskListEngine:broadcastMessage	(IILjava/lang/Object;)V
    //   366: aload_0
    //   367: sipush 10001
    //   370: iconst_0
    //   371: aload_3
    //   372: invokevirtual 182	com/kaixin001/engine/UploadTaskListEngine:broadcastMessage	(IILjava/lang/Object;)V
    //   375: aload_0
    //   376: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   379: ifnull +11 -> 390
    //   382: aload_0
    //   383: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   386: invokevirtual 169	java/util/ArrayList:size	()I
    //   389: pop
    //   390: aload_3
    //   391: ifnull +29 -> 420
    //   394: aload_3
    //   395: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   398: bipush 8
    //   400: if_icmpeq +20 -> 420
    //   403: aload_0
    //   404: aload_3
    //   405: invokevirtual 130	com/kaixin001/engine/UploadTaskListEngine:isChatTask	(Lcom/kaixin001/item/KXUploadTask;)Z
    //   408: ifeq +12 -> 420
    //   411: aload_0
    //   412: getfield 83	com/kaixin001/engine/UploadTaskListEngine:mFinishedTaskList	Ljava/util/ArrayList;
    //   415: aload_3
    //   416: invokevirtual 384	java/util/ArrayList:remove	(Ljava/lang/Object;)Z
    //   419: pop
    //   420: aload_0
    //   421: invokespecial 490	com/kaixin001/engine/UploadTaskListEngine:waitForUploadTask	()V
    //   424: goto -417 -> 7
    //   427: astore_2
    //   428: ldc 30
    //   430: ldc_w 491
    //   433: aload_2
    //   434: invokestatic 298	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   437: goto -430 -> 7
    //   440: astore 5
    //   442: aload 4
    //   444: monitorexit
    //   445: aload 5
    //   447: athrow
    //   448: astore 8
    //   450: aload 7
    //   452: monitorexit
    //   453: aload 8
    //   455: athrow
    //   456: astore 10
    //   458: ldc 30
    //   460: ldc_w 493
    //   463: aload 10
    //   465: invokestatic 298	com/kaixin001/util/KXLog:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   468: iconst_0
    //   469: istore_1
    //   470: goto -279 -> 191
    //   473: aload_3
    //   474: invokevirtual 485	com/kaixin001/item/KXUploadTask:getNType	()I
    //   477: iconst_m1
    //   478: if_icmpeq -204 -> 274
    //   481: aload_3
    //   482: invokevirtual 472	com/kaixin001/item/KXUploadTask:getTaskOperTimes	()I
    //   485: iconst_1
    //   486: if_icmpne +48 -> 534
    //   489: aload_3
    //   490: iconst_3
    //   491: invokevirtual 446	com/kaixin001/item/KXUploadTask:setTaskStatus	(I)V
    //   494: aload_0
    //   495: aload_3
    //   496: invokevirtual 130	com/kaixin001/engine/UploadTaskListEngine:isChatTask	(Lcom/kaixin001/item/KXUploadTask;)Z
    //   499: ifne -225 -> 274
    //   502: aload_0
    //   503: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   506: astore 20
    //   508: aload 20
    //   510: monitorenter
    //   511: aload_0
    //   512: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   515: aload_3
    //   516: invokevirtual 198	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   519: pop
    //   520: aload 20
    //   522: monitorexit
    //   523: goto -249 -> 274
    //   526: astore 21
    //   528: aload 20
    //   530: monitorexit
    //   531: aload 21
    //   533: athrow
    //   534: aload_3
    //   535: iconst_3
    //   536: invokevirtual 446	com/kaixin001/item/KXUploadTask:setTaskStatus	(I)V
    //   539: aload_0
    //   540: aload_3
    //   541: invokevirtual 130	com/kaixin001/engine/UploadTaskListEngine:isChatTask	(Lcom/kaixin001/item/KXUploadTask;)Z
    //   544: ifne -270 -> 274
    //   547: aload_0
    //   548: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   551: astore 17
    //   553: aload 17
    //   555: monitorenter
    //   556: aload_0
    //   557: getfield 79	com/kaixin001/engine/UploadTaskListEngine:mWaitTaskList	Ljava/util/ArrayList;
    //   560: aload_3
    //   561: invokevirtual 198	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   564: pop
    //   565: aload 17
    //   567: monitorexit
    //   568: goto -294 -> 274
    //   571: astore 18
    //   573: aload 17
    //   575: monitorexit
    //   576: aload 18
    //   578: athrow
    //   579: astore 12
    //   581: aload 11
    //   583: monitorexit
    //   584: aload 12
    //   586: athrow
    //   587: aload_3
    //   588: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   591: iconst_5
    //   592: if_icmpeq +32 -> 624
    //   595: aload_3
    //   596: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   599: iconst_4
    //   600: if_icmpeq +24 -> 624
    //   603: aload_3
    //   604: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   607: bipush 10
    //   609: if_icmpeq +15 -> 624
    //   612: aload_0
    //   613: sipush 10003
    //   616: iconst_0
    //   617: aload_3
    //   618: invokevirtual 182	com/kaixin001/engine/UploadTaskListEngine:broadcastMessage	(IILjava/lang/Object;)V
    //   621: goto -255 -> 366
    //   624: iload_1
    //   625: ifne -259 -> 366
    //   628: aload_3
    //   629: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   632: iconst_5
    //   633: if_icmpeq +11 -> 644
    //   636: aload_3
    //   637: invokevirtual 192	com/kaixin001/item/KXUploadTask:getTaskType	()I
    //   640: iconst_4
    //   641: if_icmpne -275 -> 366
    //   644: aload_3
    //   645: instanceof 226
    //   648: ifeq +46 -> 694
    //   651: aload_3
    //   652: checkcast 226	com/kaixin001/item/MessageUploadTask
    //   655: astore 16
    //   657: aload 16
    //   659: invokevirtual 496	com/kaixin001/item/MessageUploadTask:getMode	()I
    //   662: iconst_3
    //   663: if_icmpne +19 -> 682
    //   666: aload_0
    //   667: sipush 10005
    //   670: aload 16
    //   672: invokevirtual 499	com/kaixin001/item/MessageUploadTask:getLastError	()I
    //   675: aload_3
    //   676: invokevirtual 182	com/kaixin001/engine/UploadTaskListEngine:broadcastMessage	(IILjava/lang/Object;)V
    //   679: goto -313 -> 366
    //   682: aload_0
    //   683: sipush 10005
    //   686: iconst_0
    //   687: aload_3
    //   688: invokevirtual 182	com/kaixin001/engine/UploadTaskListEngine:broadcastMessage	(IILjava/lang/Object;)V
    //   691: goto -325 -> 366
    //   694: aload_0
    //   695: sipush 10005
    //   698: iconst_0
    //   699: aload_3
    //   700: invokevirtual 182	com/kaixin001/engine/UploadTaskListEngine:broadcastMessage	(IILjava/lang/Object;)V
    //   703: goto -337 -> 366
    //
    // Exception table:
    //   from	to	target	type
    //   20	25	427	java/lang/Exception
    //   29	60	427	java/lang/Exception
    //   60	69	427	java/lang/Exception
    //   81	90	427	java/lang/Exception
    //   102	165	427	java/lang/Exception
    //   169	180	427	java/lang/Exception
    //   191	201	427	java/lang/Exception
    //   205	239	427	java/lang/Exception
    //   239	256	427	java/lang/Exception
    //   256	265	427	java/lang/Exception
    //   265	274	427	java/lang/Exception
    //   274	283	427	java/lang/Exception
    //   299	307	427	java/lang/Exception
    //   307	337	427	java/lang/Exception
    //   341	366	427	java/lang/Exception
    //   366	375	427	java/lang/Exception
    //   375	390	427	java/lang/Exception
    //   394	420	427	java/lang/Exception
    //   420	424	427	java/lang/Exception
    //   445	448	427	java/lang/Exception
    //   453	456	427	java/lang/Exception
    //   458	468	427	java/lang/Exception
    //   473	511	427	java/lang/Exception
    //   531	534	427	java/lang/Exception
    //   534	556	427	java/lang/Exception
    //   576	579	427	java/lang/Exception
    //   584	587	427	java/lang/Exception
    //   587	621	427	java/lang/Exception
    //   628	644	427	java/lang/Exception
    //   644	679	427	java/lang/Exception
    //   682	691	427	java/lang/Exception
    //   694	703	427	java/lang/Exception
    //   69	81	440	finally
    //   442	445	440	finally
    //   90	102	448	finally
    //   450	453	448	finally
    //   182	188	456	java/lang/Exception
    //   511	523	526	finally
    //   528	531	526	finally
    //   556	568	571	finally
    //   573	576	571	finally
    //   283	295	579	finally
    //   581	584	579	finally
  }

  public void setContext(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public void unRegister(Handler paramHandler)
  {
    this.mListHandlers.remove(paramHandler);
  }

  public void updateTaskInDB(KXUploadTask paramKXUploadTask)
  {
    if (paramKXUploadTask == null)
      return;
    ContentValues localContentValues = paramKXUploadTask.toContentValues();
    getDBAdapter().updateTask(paramKXUploadTask.getTaskId(), localContentValues);
  }
}

/* Location:           C:\9exce\android\pj\kaixin_android_3.9.9_034_kaixin001\classes_dex2jar.jar
 * Qualified Name:     com.kaixin001.engine.UploadTaskListEngine
 * JD-Core Version:    0.6.0
 */