package xyz.fnplus.omega.helper;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import java.util.List;
import xyz.fnplus.omega.AppTrackerService;
import xyz.fnplus.omega.util.UtilLogger;

public class ServiceHelper {

  private static UtilLogger log = new UtilLogger(ServiceHelper.class);

  public static synchronized void stopBackgroundServiceIfRunning(Context context) {
    boolean alreadyRunning = ServiceHelper.checkIfAppTrackerServiceIsRunning(context);

    log.d("Is AppTrackerService is running: %s", alreadyRunning);

    if (alreadyRunning) {
      Intent intent = new Intent(context, AppTrackerService.class);
      context.stopService(intent);
    }
  }

  public static synchronized void startBackgroundServiceIfNotAlreadyRunning(Context context) {

    boolean alreadyRunning = ServiceHelper.checkIfAppTrackerServiceIsRunning(context);

    log.d("Is AppTrackerService already running: %s", alreadyRunning);

    if (!alreadyRunning) {

      Intent intent = new Intent(context, AppTrackerService.class);
      context.startService(intent);
    }
  }

  public static boolean checkIfAppTrackerServiceIsRunning(Context context) {

    return checkIfServiceIsRunning(context, "com.nolanlawson.apptracker.AppTrackerService");
  }

  public static boolean checkIfUpdateAppStatsServiceIsRunning(Context context) {
    return checkIfServiceIsRunning(context, "com.nolanlawson.apptracker.UpdateAppStatsService");
  }

  private static boolean checkIfServiceIsRunning(Context context, String serviceName) {

    ComponentName componentName = new ComponentName("com.nolanlawson.apptracker", serviceName);

    ActivityManager activityManager =
        (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

    List<ActivityManager.RunningServiceInfo> procList =
        activityManager.getRunningServices(Integer.MAX_VALUE);

    if (procList != null) {

      for (ActivityManager.RunningServiceInfo appProcInfo : procList) {
        if (appProcInfo != null && componentName.equals(appProcInfo.service)) {
          log.d("%s is already running", serviceName);
          return true;
        }
      }
    }
    log.d("%s is not running", serviceName);
    return false;
  }
}
