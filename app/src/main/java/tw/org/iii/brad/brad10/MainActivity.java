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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private File sdroot, approot;//參考手機照片,此處sdroot是外存,approot是存在app之下的
    @Override                   //以lg手機來說 sdroot在storage/emulated/legacy底下
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
        sdroot = Environment.getExternalStorageDirectory();
        Log.v("brad",sdroot.getAbsolutePath());
        approot = new File(sdroot,"Android/data/" + getPackageName());
        if(!approot.exists()){
            approot.mkdirs();
        }
    }

    public void test1(View view) {
        try {
            FileOutputStream fout =
                    new FileOutputStream(sdroot.getAbsolutePath()+"/001.txt");
            fout.write("CCCCCCC".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.v("brad",e.toString());
        }
    }

    public void test2(View view) {
        try {
            FileOutputStream fout =
                    new FileOutputStream(approot.getAbsolutePath()+"/001.txt");
            fout.write("double d".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.v("brad",e.toString());
        }
    }

    public void test3(View view) {
        try {
            FileInputStream fin =
                    new FileInputStream(sdroot.getAbsolutePath()+"/001.txt");
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(fin));
            String line = reader.readLine();
            fin.close();
            Log.v("brad",line);

        }catch (Exception e){
            Log.v("brad",e.toString());
        }

    }

    public void test4(View view) {
        try {
            FileInputStream fin =
                    new FileInputStream(approot.getAbsolutePath()+"/001.txt");
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(fin));
            String line = reader.readLine();
            fin.close();
            Log.v("brad",line);

        }catch (Exception e){
            Log.v("brad",e.toString());
        }
    }
}
