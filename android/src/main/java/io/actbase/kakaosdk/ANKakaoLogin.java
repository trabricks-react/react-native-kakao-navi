
package io.actbase.kakaosdk;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.auth.authorization.accesstoken.AccessToken;
import com.kakao.util.exception.KakaoException;

import io.actbase.kakaosdk.impl.KakaoSDKAdapter;
import io.actbase.kakaosdk.impl.LoginButton;

public class ANKakaoLogin extends ReactContextBaseJavaModule implements ActivityEventListener {

    private ReactApplicationContext reactContext;
    private KakaoSDKAdapter kakaoSDKAdapter;
    private LoginButton loginButton;

    public ANKakaoLogin(ReactApplicationContext reactContext) {
        super(reactContext);

        this.reactContext = reactContext;
        this.reactContext.addActivityEventListener(this);

        initKakaoSDK();
        this.loginButton = new LoginButton(this.reactContext);
    }

    @Override
    public String getName() {
        return "RNCKakaoSDK";
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        Log.d(getName(), "onActivityResult");
        try {
            if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
                return;
            }
        }
        catch(Exception ex) {
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    public void initKakaoSDK() {
        if (kakaoSDKAdapter == null) {
            kakaoSDKAdapter = new KakaoSDKAdapter(reactContext);
            try {
                KakaoSDK.init(kakaoSDKAdapter);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @ReactMethod
    public void login(Promise promise) {

        try {
            this.initKakaoSDK();

            if (!Session.getCurrentSession().isClosed()) {
                Session.getCurrentSession().close();
            }

            Session.getCurrentSession().clearCallbacks();
            loginButton.open(AuthType.KAKAO_LOGIN_ALL, new SessionCallback(promise));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            promise.reject(ex);
        }
    }


    @ReactMethod
    public void logout(Promise promise) {

        try {
            this.initKakaoSDK();

            if (!Session.getCurrentSession().isClosed()) {
                Session.getCurrentSession().close();
            }

            promise.resolve("SUCCESS");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            promise.reject(ex);
        }
    }

    @ReactMethod
    public void getAccessToken(Promise promise) {

        try {
            this.initKakaoSDK();
            AccessToken token = Session.getCurrentSession().getTokenInfo();

            if (token.getAccessToken() == null) throw new Exception("Required login..");

            WritableMap map = Arguments.createMap();
            map.putString("accessToken", token.getAccessToken());
            map.putDouble("remainingExpireTime", token.getRemainingExpireTime() / 1000.0f);

            promise.resolve(map);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            promise.reject(ex);
        }

    }

    private class SessionCallback implements ISessionCallback {
        private Promise promise;

        public SessionCallback(Promise promise) {
            this.promise = promise;
        }

        @Override
        public void onSessionOpened() {
            Log.d(getName(), "SessionCallback.onSessionOpened");
            if (this.promise != null) {
                getAccessToken(this.promise);
                this.promise = null;
            }
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.d(getName(), "SessionCallback.onSessionOpenFailed");
            exception.printStackTrace();
            if (this.promise != null) {
                this.promise.reject(exception);
                this.promise = null;
            }
        }

    }

}
