package tw.org.iii.brad.brad10;
//外存空間,資料其他程式也可取用(參考manifest)

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,//檢查是否拿到權限
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted

            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    123);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        } else {
            // Permission has already been granted
            Log.v("brad", "debug1");//取得授權
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.v("brad", "debug2");//獲得授權
                init();
            } else {
                Log.v("brad", "debug3");//不允許
                finish();
            }
        }
    }

    private void init() {//有權限需求時,可將本來要放在onCreate的code放在init,等到同意權限時才執行
        String state = Environment.getExternalStorageState();
        Log.v("brad", state);  // mounted or removed 掛載或移除
        File sdroot = Environment.getExternalStorageDirectory();
        Log.v("brad",sdroot.getAbsolutePath());
    }

    public void test1(View view) {

    }

    public void test2(View view) {

    }
}
