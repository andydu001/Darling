package com.example.darling;
import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.PowerManager;

import java.io.IOException;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {
    private TextView printInfo;
    private ImageButton imageButton;
    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        printInfo = findViewById(R.id.textView);
        imageButton = findViewById(R.id.imageButton);

        // Bundle bundle = out.readBundle(getClass().getClassLoader());

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);

        //  printInfo.setText(String.valueOf(powerManager.getCurrentThermalStatus()));


        WallpaperManager wallpaperManager = (WallpaperManager) getSystemService(WALLPAPER_SERVICE);
        // String na =  wallpaperManager.getWallpaperInfo().getServiceInfo().name;
        // int d = wallpaperManager.getBuiltInDrawable().getAlpha();
        boolean vis = wallpaperManager.getBuiltInDrawable().isVisible();

        //check to see if the wallpaper is suppoeted
        if(wallpaperManager.isWallpaperSupported() && wallpaperManager.isSetWallpaperAllowed()) {

           /*try {
               wallpaperManager.clear();
           } catch (IOException e) {
               throw new RuntimeException(e);
           }*/
            try {
                wallpaperManager.setResource(R.drawable.composing);
               // wallpaperManager.suggestDesiredDimensions(100,100);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        // imageButton.setImageDrawable(wallpaperManager.getDrawable());
        // printInfo.setText( String.valueOf(vis));

        UiModeManager uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);

        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
        uiModeManager.enableCarMode(UiModeManager.ENABLE_CAR_MODE_GO_CAR_HOME);


        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        String disName = windowManager.getDefaultDisplay().getName();

        printInfo.append(disName+" ");
        printInfo.append(String.valueOf(windowManager.getDefaultDisplay().getMode().getPhysicalWidth()) +" ");
        printInfo.append(String.valueOf(windowManager.getDefaultDisplay().getMode().getRefreshRate()) +" ");

        StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        String storage = storageManager.getPrimaryStorageVolume().getMediaStoreVolumeName();
        printInfo.append(storage+" ");

        storageManager.getStorageVolumes().iterator().forEachRemaining(new Consumer<StorageVolume>() {
            @Override
            public void accept(StorageVolume storageVolume) {
                printInfo.append(storageVolume.getDirectory().getAbsolutePath()+" ");
                printInfo.append(storageVolume.getState()+" ");

                printInfo.append(String.valueOf(storageVolume.getDirectory().getTotalSpace())+" ");
            }
        });

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.getSensorList(SensorManager.SENSOR_DELAY_FASTEST).iterator().forEachRemaining(new Consumer<Sensor>() {
            @Override
            public void accept(Sensor sensor) {
                printInfo.append(sensor.getVendor()+" ");
            }
        });



    }
}