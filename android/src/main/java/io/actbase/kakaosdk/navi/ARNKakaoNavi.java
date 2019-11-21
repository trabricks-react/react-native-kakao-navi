package io.actbase.kakaosdk.navi;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.kakao.kakaonavi.KakaoNaviParams;
import com.kakao.kakaonavi.KakaoNaviService;
import com.kakao.kakaonavi.Location;
import com.kakao.kakaonavi.NaviOptions;
import com.kakao.kakaonavi.options.CoordType;
import com.kakao.kakaonavi.options.RpOption;
import com.kakao.kakaonavi.options.VehicleType;
import com.kakao.util.exception.KakaoException;

import java.util.ArrayList;
import java.util.List;

public class ARNKakaoNavi extends ReactContextBaseJavaModule {

    private ReactApplicationContext reactContext;

    public ARNKakaoNavi(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "ARNKakaoNavi";
    }

    public VehicleType getVehicleType(final int vehicleType) {
        switch (vehicleType) {
            case 2:
                return VehicleType.SECOND;
            case 3:
                return VehicleType.THIRD;
            case 4:
                return VehicleType.FOURTH;
            case 5:
                return VehicleType.FIFTH;
            case 6:
                return VehicleType.SIXTH;
            case 7:
                return VehicleType.TWO_WHEEL;
            default:
                return VehicleType.FIRST;
        }
    }

    public RpOption getRpOption(final int rpOption) {
        switch (rpOption) {
            case 2:
                return RpOption.FREE;
            case 3:
                return RpOption.SHORTEST;
            case 4:
                return RpOption.NO_AUTO;
            case 5:
                return RpOption.WIDE;
            case 6:
                return RpOption.HIGHWAY;
            case 8:
                return RpOption.NORMAL;
            case 100:
                return RpOption.RECOMMENDED;
            default:
                return RpOption.FAST;
        }
    }

    @ReactMethod
    public void share(final Location location, final ReadableMap options, final ReadableArray viaList, final Promise promise) {

        Location destination = Location.newBuilder(location.getName(), location.getX(), location.getY()).build();

        NaviOptions.Builder option = new NaviOptions.Builder();

        if (options.getString("coordType").equals("WGS84")) {
            option.setCoordType(CoordType.WGS84);
        }

        option.setVehicleType(getVehicleType(options.getInt("vehicleType")));
        option.setRpOption(getRpOption(options.getInt("rpOption")));
        option.setRouteInfo(options.getBoolean("routeInfo"));
        option.setStartX(options.getDouble("startX"));
        option.setStartY(options.getDouble("startY"));
        option.setStartAngle(options.getInt("startAngle"));
        option.setUserId(options.getString("userId"));
        option.setReturnUri(options.getString("returnUri"));
        List<Location> list = new ArrayList<Location>();
        for (int i = 0; i < viaList.size(); i++) {
            ReadableMap map = viaList.getMap(i);
            list.add(Location.newBuilder(map.getString("name"), map.getDouble("x"), map.getDouble("y")).build());
        }
        KakaoNaviParams.Builder builder = KakaoNaviParams.newBuilder(destination)
                .setViaList(list)
                .setNaviOptions(option.build());
        KakaoNaviParams params = builder.build();
        try {
            KakaoNaviService.getInstance().shareDestination(this.getCurrentActivity(), params);
            promise.resolve("SUCCESS");
        } catch (KakaoException e) {
            promise.reject("ARNKakaoNavi", e.toString());
        }
    }

    @ReactMethod
    public void navigate(final Location location, final ReadableMap options, final ReadableArray viaList, final Promise promise) {

        Location destination = Location.newBuilder(location.getName(), location.getX(), location.getY()).build();

        NaviOptions.Builder option = new NaviOptions.Builder();

        if (options.getString("coordType").equals("WGS84")) {
            option.setCoordType(CoordType.WGS84);
        }
        option.setVehicleType(getVehicleType(options.getInt("vehicleType")));
        option.setRpOption(getRpOption(options.getInt("rpOption")));
        option.setRouteInfo(options.getBoolean("routeInfo"));
        option.setStartX(options.getDouble("startX"));
        option.setStartY(options.getDouble("startY"));
        option.setStartAngle(options.getInt("startAngle"));
        option.setUserId(options.getString("userId"));
        option.setReturnUri(options.getString("returnUri"));
        List<Location> list = new ArrayList<Location>();
        for (int i = 0; i < viaList.size(); i++) {
            ReadableMap map = viaList.getMap(i);
            list.add(Location.newBuilder(map.getString("name"), map.getDouble("x"), map.getDouble("y")).build());
        }
        NaviOptions naviOptions = option.build();
        KakaoNaviParams.Builder builder = KakaoNaviParams.newBuilder(destination)
                .setViaList(list)
                .setNaviOptions(naviOptions);
        KakaoNaviParams params = builder.build();
        try {
            KakaoNaviService.getInstance().navigate(this.getCurrentActivity(), params);
            promise.resolve("SUCCESS");
        } catch (KakaoException e) {
            promise.reject("ARNKakaoNavi", e.toString());
        }
    }
}
