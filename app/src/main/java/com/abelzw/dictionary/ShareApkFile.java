package com.abelzw.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import java.io.File;
public class ShareApkFile extends AppCompatActivity {

    public void shareApplication(Activity activity) {
        try {

            PackageManager pm = activity.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(activity.getPackageName(), 0);
            File srcFile = new File(ai.publicSourceDir);

            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setType("*/*");     this will load all the installed application to share the app
            intent.setType("application/vnd.android.package-archive");        //this will load only the file sharable app like exender , wechat...
            Uri uri = FileProvider.getUriForFile(ShareApkFile.this, activity.getPackageName(), srcFile);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            activity.grantUriPermission(activity.getPackageManager().toString(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
