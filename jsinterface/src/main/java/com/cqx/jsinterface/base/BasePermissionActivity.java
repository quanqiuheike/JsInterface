package com.cqx.jsinterface.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by chengqiuxia on 2018/4/2.
 */

public class BasePermissionActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private final String TAG = "BasePermissionActivity";

    private String[] deniedPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 权限校验
     *
     * @param permission
     * @return
     */
    public boolean permissionCheck(String[] permission) {
        boolean flag = true;
        List<String> deniedPermissionList = new ArrayList<>();
        try {
            //判断APP是否已经拥有该权限
            for (int i = 0; i < permission.length; i++) {
                if (!EasyPermissions.hasPermissions(this, permission[i])) {
                    deniedPermissionList.add(permission[i]);
                }
            }
            if (deniedPermissionList.size() > 0) {
                flag = false;

                deniedPermissions = new String[deniedPermissionList.size()];
                deniedPermissionList.toArray(deniedPermissions);

                EasyPermissions.requestPermissions(BasePermissionActivity.this, 9001, deniedPermissions);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 9002 && deniedPermissions != null) {
                //判断APP是否拥有该权限
                if (!EasyPermissions.hasPermissions(this, deniedPermissions)) {
                    Toast.makeText(BasePermissionActivity.this, "开启所需要的权限才能正常使用客户端", Toast.LENGTH_LONG).show();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        Log.i(TAG, "onPermissionsGranted: ");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.i(TAG, "onPermissionsDenied: ");
        try {
            if(perms!=null&&perms.size()>0){
                //权限禁止。如果没有勾选不再提醒，那么直接提醒。如果曾经勾选了不再提醒，那么需要手动去手机设置中设置一下。

                if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setMessage("检测到某些权限被禁止并设置了不再提醒，需要您到 手机－设置－应用管理－权限管理 中手动开启。");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(BasePermissionActivity.this,"开启所需要的权限才能正常使用客户端",Toast.LENGTH_LONG).show();

                        }
                    });
                    builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri=Uri.fromParts("package",BasePermissionActivity.this.getPackageName(),null);
                            intent.setData(uri);
                            BasePermissionActivity.this.startActivity(intent);

                        }
                    });
                    builder.show();
                }else{
                    Toast.makeText(BasePermissionActivity.this,"开启所需要的权限才能正常使用客户端",Toast.LENGTH_LONG).show();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
