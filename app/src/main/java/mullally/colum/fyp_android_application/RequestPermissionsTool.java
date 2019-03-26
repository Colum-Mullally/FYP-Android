package mullally.colum.fyp_android_application;

import android.app.Activity;
import android.content.Context;

public interface RequestPermissionsTool {
    void requestPermissions(Activity context, String[] permissions);

    boolean isPermissionsGranted(Context context, String[] permissions);

    void onPermissionDenied();
}
