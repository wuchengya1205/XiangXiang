package com.location.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionResult;

import java.util.List;

public class ShowLocationActivity extends AppCompatActivity {

    private MapView mMapView;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private MyLocationStyle myLocationStyle;
    private AMap mAMap;
    private UiSettings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location);
        getPersission(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        mSettings = mAMap.getUiSettings();
    }

    /**
     * 判断是否有相关权限
     * @param savedInstanceState
     */
    private void getPersission(Bundle savedInstanceState) {
        Boolean location = EasyPermission.build().hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        Boolean read     = EasyPermission.build().hasPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        Boolean write    = EasyPermission.build().hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (location && read && write) {
            initView(savedInstanceState);
            initData();
        } else {
            arvixe(savedInstanceState);
        }

    }

    /**
     * 动态申请权限
     * @param savedInstanceState
     */
    private void arvixe(final Bundle savedInstanceState) {
        EasyPermission.build()
                .mRequestCode(10010)
                .mContext(this)
                .mPerms(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .mResult(new EasyPermissionResult() {
                    @Override
                    public void onPermissionsAccess(int requestCode) {
                        super.onPermissionsAccess(requestCode);
                        initView(savedInstanceState);
                        initData();
                    }

                    @Override
                    public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
                        super.onPermissionsDismiss(requestCode, permissions);
                        finish();
                    }
                }).requestPermission();
    }

    private void initData() {
        myLocationStyle = new MyLocationStyle();
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        // myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);
        // 自定义定位蓝点
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.yuan));
//        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(50, 0, 0, 180));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(0.0f);// 设置圆形的边框粗细
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mAMap.setMyLocationEnabled(true);
        //设置定位蓝点的Style
        mAMap.setMyLocationStyle(myLocationStyle);
        // 可触发定位并显示当前位置
        mAMap.setMyLocationEnabled(true);
        //开启以中心点进行手势操作
        mSettings.setGestureScaleByMapCenter(true);
        // 设置是否显示放大缩放按钮
        mSettings.setZoomControlsEnabled(false);
        // 指南针用于向 App 端用户展示地图方向，默认不显示
        mSettings.setCompassEnabled(false);
        // 定位按钮
        mSettings.setMyLocationButtonEnabled(false);
        // 比例尺
        mSettings.setScaleControlsEnabled(false);
        // 地图logo位置
        // AMapOptions.LOGO_POSITION_BOTTOM_LEFT  左边
        // AMapOptions.LOGO_MARGIN_BOTTOM 底部
        // AMapOptions.LOGO_MARGIN_RIGHT  右边
        // AMapOptions.LOGO_POSITION_BOTTOM_CENTER 底部居中
        // AMapOptions.LOGO_POSITION_BOTTOM_LEFT 左下角
        // AMapOptions.LOGO_POSITION_BOTTOM_RIGHT 右下角
        mSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
        // 开始定位
        startLocation();
    }

    /**
     * 开始定位 以及设置定位的参数
     */
    private void startLocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        // 启动定位
        mlocationClient.startLocation();
        //设置定位监听
//        mlocationClient.setLocationListener(this);
    }

    /**
     * 地图生命周期于activity绑定
     */
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mlocationClient.stopLocation();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
}
