package io.actbase.kakaosdk.navi;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.kakao.util.exception.KakaoException;
import com.kakao.plusfriend.PlusFriendService;

public class ARNKakaoNavi extends ReactContextBaseJavaModule {

    private ReactApplicationContext reactContext;

    public ANKakaoNavi(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "ARNKakaoNavi";
    }

}
