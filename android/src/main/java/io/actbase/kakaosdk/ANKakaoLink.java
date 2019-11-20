package io.actbase.kakaosdk;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.CommerceDetailObject;
import com.kakao.message.template.CommerceTemplate;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.ListTemplate;
import com.kakao.message.template.LocationTemplate;
import com.kakao.message.template.SocialObject;
import com.kakao.message.template.TemplateParams;
import com.kakao.message.template.TextTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

import java.util.HashMap;
import java.util.Map;

public class ANKakaoLink extends ReactContextBaseJavaModule {

    private ReactApplicationContext reactContext;

    public ANKakaoLink(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "ANKakaoLink";
    }

    private String gs(ReadableMap map, String key) {
        return map.hasKey(key) ? map.getString(key) : null;
    }

    private Integer gi(ReadableMap map, String key) {
        return map.hasKey(key) ? map.getInt(key) : null;
    }

    private void sendWithTemplate(TemplateParams template, final Promise promise) {
        Map<String, String> serverCallbackArgs = new HashMap<String, String>();
        serverCallbackArgs.put("user_id", "${current_user_id}");
        serverCallbackArgs.put("product_id", "${shared_product_id}");

        KakaoLinkService.getInstance().sendDefault(this.reactContext, template, serverCallbackArgs,
                new ResponseCallback<KakaoLinkResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Logger.e(errorResult.toString());
                        promise.reject(errorResult.getException());
                    }

                    @Override
                    public void onSuccess(KakaoLinkResponse result) {
                        promise.resolve(null);
                    }
                }
        );


    }

    private ContentObject createContent(ReadableMap dict) {
        if (dict == null) return null;
        return ContentObject.newBuilder(gs(dict, "title"),
                gs(dict, "imageURL"),
                this.createLink(dict.getMap("link")))
                .setDescrption(gs(dict, "desc"))
                .build();
    }

    private LinkObject createLink(ReadableMap dict) {
        if (dict == null) return null;

        LinkObject.Builder builder = LinkObject.newBuilder();
        builder.setWebUrl(gs(dict, "webURL"));
        builder.setMobileWebUrl(gs(dict, "mobileWebURL"));
        builder.setIosExecutionParams(gs(dict, "iosExecutionParams"));
        builder.setAndroidExecutionParams(gs(dict, "androidExecutionParams"));
        return builder.build();
    }

    private ButtonObject createButton(ReadableMap dict) {
        if (dict == null) return null;
        return new ButtonObject(dict.getString("title"), this.createLink(dict));
    }


    private SocialObject createSocial(ReadableMap dict) {
        if (dict == null) return null;
        return SocialObject.newBuilder()
                .setLikeCount(gi(dict, "likeCount"))
                .setCommentCount(gi(dict, "commentCount"))
                .setSharedCount(gi(dict, "sharedCount"))
                .setViewCount(gi(dict, "viewCount"))
                .setSubscriberCount(gi(dict, "subscriberCount"))
                .build();
    }


    private CommerceDetailObject createCommerce(ReadableMap dict) {
        if (dict == null) return null;

        return CommerceDetailObject.newBuilder(gi(dict, "regularPrice"))
                .setDiscountPrice(gi(dict, "discountPrice"))
                .setDiscountRate(gi(dict, "discountRate"))
                .build();
    }


    @ReactMethod
    public void sendFeed(ReadableMap dict, Promise promise) {
        FeedTemplate.Builder builder = FeedTemplate.newBuilder(this.createContent(dict.getMap("content")));
        if (dict.hasKey("social")) {
            builder.setSocial(this.createSocial(dict.getMap("social")));
        }

        if (dict.hasKey("buttons")) {
            ReadableArray array = dict.getArray("buttons");
            for (int i = 0; i < array.size(); i++) {
                builder.addButton(this.createButton(array.getMap(i)));
            }
        }
        this.sendWithTemplate(builder.build(), promise);
    }

    @ReactMethod
    public void sendList(ReadableMap dict, Promise promise) {
        ListTemplate.Builder builder = ListTemplate.newBuilder(gs(dict, "headerTitle"),
                this.createLink(dict.getMap("headerLink")));


        if (dict.hasKey("contents")) {
            ReadableArray array = dict.getArray("contents");
            for (int i = 0; i < array.size(); i++) {
                builder.addContent(this.createContent(array.getMap(i)));
            }
        }

        if (dict.hasKey("buttons")) {
            ReadableArray array = dict.getArray("buttons");
            for (int i = 0; i < array.size(); i++) {
                builder.addButton(this.createButton(array.getMap(i)));
            }
        }
        this.sendWithTemplate(builder.build(), promise);
    }

    @ReactMethod
    public void sendLocation(ReadableMap dict, Promise promise) {
        LocationTemplate.Builder builder = LocationTemplate.newBuilder(gs(dict, "address"), this.createContent(dict.getMap("content")));
        builder.setAddressTitle(gs(dict, "addressTitle"));

        if (dict.hasKey("buttons")) {
            ReadableArray array = dict.getArray("buttons");
            for (int i = 0; i < array.size(); i++) {
                builder.addButton(this.createButton(array.getMap(i)));
            }
        }
        this.sendWithTemplate(builder.build(), promise);
    }

    @ReactMethod
    public void sendCommerce(ReadableMap dict, Promise promise) {
        CommerceTemplate.Builder builder = CommerceTemplate.newBuilder(this.createContent(dict.getMap("content")), this.createCommerce(dict.getMap("commerce")));
        if (dict.hasKey("buttons")) {
            ReadableArray array = dict.getArray("buttons");
            for (int i = 0; i < array.size(); i++) {
                builder.addButton(this.createButton(array.getMap(i)));
            }
        }
        this.sendWithTemplate(builder.build(), promise);
    }

    @ReactMethod
    public void sendText(ReadableMap dict, Promise promise) {
        TextTemplate.Builder builder = TextTemplate.newBuilder(gs(dict, "text"), this.createLink(dict.getMap("link")));
        builder.setButtonTitle(gs(dict, "buttonTitle"));
        if (dict.hasKey("buttons")) {
            ReadableArray array = dict.getArray("buttons");
            for (int i = 0; i < array.size(); i++) {
                builder.addButton(this.createButton(array.getMap(i)));
            }
        }
        this.sendWithTemplate(builder.build(), promise);
    }

    @ReactMethod
    public void sendURL(String url, final Promise promise) {
        // 기본적인 스크랩 템플릿을 사용하여 보내는 코드
        Map<String, String> serverCallbackArgs = new HashMap<String, String>();
        serverCallbackArgs.put("user_id", "${current_user_id}");
        serverCallbackArgs.put("product_id", "${shared_product_id}");

        KakaoLinkService.getInstance().sendScrap(this.reactContext, url, serverCallbackArgs,
                new ResponseCallback<KakaoLinkResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Logger.e(errorResult.toString());
                        promise.reject(errorResult.getException());
                    }

                    @Override
                    public void onSuccess(KakaoLinkResponse result) {
                        promise.resolve(null);
                    }
                }
        );


    }

}