# KakaoSDK for React Native

![platforms](https://img.shields.io/badge/platforms-Android%20%7C%20iOS-brightgreen.svg?style=flat-square&colorB=191A17)
[![npm](https://img.shields.io/npm/v/actbase-native-kakaosdk.svg?style=flat-square)](https://www.npmjs.com/package/actbase-native-kakaosdk)
[![npm](https://img.shields.io/npm/dm/actbase-native-kakaosdk.svg?style=flat-square&colorB=007ec6)](https://www.npmjs.com/package/actbase-native-kakaosdk)
[![github issues](https://img.shields.io/github/issues/actbase/actbase-native-kakaosdk.svg?style=flat-square)](https://github.com/actbase/actbase-native-kakaosdk/issues)
[![github closed issues](https://img.shields.io/github/issues-closed/actbase/actbase-native-kakaosdk.svg?style=flat-square&colorB=44cc11)](https://github.com/actbase/actbase-native-kakaosdk/issues?q=is%3Aissue+is%3Aclosed)
[![Issue Stats](https://img.shields.io/issuestats/i/github/actbase/actbase-native-kakaosdk.svg?style=flat-square&colorB=44cc11)](http://github.com/actbase/actbase-native-kakaosdk/issues)

## 기능

- [`카카오 사용자관리`]()
- [`카카오링크`](https://github.com/actbase/actbase-native-kakaosdk/blob/master/README.md#카카오링크)
- 카카오톡
- 카카오스토리
- [`카카오톡 채널`]()

## Getting started

### Mostly automatic installation (RN >= 0.60)

```
$ npm install @actbase/react-native-kakaosdk --save
$ cd ios && pod install && cd ..
```

### Mostly automatic installation (RN <= 0.59)

```
$ npm install @actbase/react-native-kakaosdk --save
$ react-native link @actbase/react-native-kakaosdk
$ cd ios && pod install && cd ..
```

## 기본설정하기

### iOS 설정

#### 프로젝트에 내 앱 설정

카카오계정을 통해 인증을 받고 자신의 앱 실행을 위해서 아래 사진과 같이 URL Types 항목을 추가해야 합니다. URL Schemes에는 KAKAO_APP_KEY 앞에 "kakao" 문자열을 붙여서 등록합니다.
<img src="https://developers.kakao.com/assets/images/ios/url_types.png" alt="url_types">

카카오 개발자 웹사이트에 등록된 해당 앱의 네이티브 앱 키를 프로젝트 plist 파일에 등록을 합니다. "KAKAO_APP_KEY"라는 이름으로 Key를 추가하고, Type은 String, Value는 해당 앱의 네이티브 앱 키 값으로 등록합니다.
<img src="https://developers.kakao.com/assets/images/ios/setting_plist.png" alt="App Key & App Redirect URI 등록">

#### 프로젝트에 내 앱 설정(iOS9 지원)

iOS9에서는 다양한 기능이 추가되면서 동작 및 설정의 변화가 생겼습니다. iOS9에서 올바로 Kakao SDK를 사용하기 위해서는 다음과 같은 두가지 설정을 앱의 plist(또는 프로젝트의 Info 설정)에 반드시 해 주어야 합니다.

Kakao SDK가 사용하는 카카오 앱들의 커스텀 스킴 스위칭을 허용하는 화이트리스트 추가
두번째, LSApplicationQueriesSchemes의 키로 Array 값 kakao{your_app_key} 및 카카오 앱들의 커스텀 스킴들을 등록합니다. 다음은 예시 입니다.

```xml
<key>LSApplicationQueriesSchemes</key>
<array>
    <!-- 공통 -->
    <string>{YOUR_KAKAO_APP_KEY}</string>

    <!-- 간편로그인 -->
    <string>kakaokompassauth</string>
    <string>storykompassauth</string>

    <!-- 카카오톡링크 -->
    <string>kakaolink</string>
    <string>kakaotalk-5.9.7</string>

    <!-- 카카오스토리링크 -->
    <string>storylink</string>
</array>
```

보다 자세한 설명은 Privacy and Your App을 참고합니다.

### Android 설정

#### Gradle 환경에서 사용하기

Gradle을 이용하면 소스를 받을 필요 없고, 로컬에 인스톨해줘야 하는 모듈도 리모트에서 받아올 수 있으며, 필요한 기능 라이브러리만 사용하여 앱파일 크기를 줄일 수 있습니다.

1. Gradle repository

```gradle
subprojects {
    repositories {
        mavenCentral()
        maven { url 'http://devrepo.kakao.com:8088/nexus/content/groups/public/' }
    }
}
```

2. 사용할 앱의 build.gradle 파일에 아래와 같이 필요한 모듈을 선택적으로 설정합니다.

project.KAKAO_SDK_VERSION 에는 sdk 버전을 명시합니다. gradle 지원은 1.0.36부터 지원되지만 최신 패키지 구조가 변경된 1.1.0버전 이상의 gradle project를 사용하시길 권장합니다.

[app/build.grdle]

```gradle
dependencies {
    implementation group: 'com.kakao.sdk', name: 'usermgmt', version: project.KAKAO_SDK_VERSION
    implementation group: 'com.kakao.sdk', name: 'kakaolink', version: project.KAKAO_SDK_VERSION
    implementation group: 'com.kakao.sdk', name: 'kakaotalk', version: project.KAKAO_SDK_VERSION
    implementation group: 'com.kakao.sdk', name: 'kakaostory', version: project.KAKAO_SDK_VERSION
    implementation group: 'com.kakao.sdk', name: 'plusfriend', version: project.KAKAO_SDK_VERSION
}
```

[gradle.properties]

```properties
KAKAO_SDK_VERSION=1.25.0
```

3. 프로가드 적용시에는 아래 옵션을 설정에 추가합니다.

```gradle
-keep class com.kakao.** { *; }
-keepattributes Signature
-keepclassmembers class * {
  public static <fields>;
  public *;
}
-dontwarn android.support.v4.**,org.slf4j.**,com.google.android.gms.**
```

Kakao SDK에포함된 kakao-open-android-sdk-sample에서 제공되는 SampleLoginActivity, SampleSignupActivity을 통해 로그인 기반 앱을 가볍게 만들어 볼 수 있습니다. 아래의 설정 예제를 참고하여, 생성하고자 하는 앱에 설정을 적용합니다.

4. 다음과 같이 앱 설정을 합니다. [strings.xml]

앱생성시 발급된 네이티브 앱키를 kakao_app_key이란 이름으로 정의하고, AndroidManifest.xml에서 앱키를 등록합니다.

```xml
<resources>
    <string name="kakao_app_key">{YOUR_KAKAO_APP_KEY}</string>
</resources>
```

앱키 값은 개발자 웹사이트에서 제공하는 대쉬보드의 설정 > 일반 > 앱 키 > 네이티브 앱 키 메뉴를 통해 확인 가능합니다.

[AndroidManifest.xml]

1. 서버와의 통신을 위해 network 권한을 설정합니다.

2. com.kakao.sdk.AppKey 이름으로 앱키를 등록합니다.

<!-- 1 -->

```xml
<uses-permission android:name="android.permission.INTERNET" />

<application>
    ...
    <meta-data
        android:name="com.kakao.sdk.AppKey"
        android:value="@string/kakao_app_key" />
    ...
</application>
```

## 사용하기

### `카카오링크`

카카오톡링크는 미리 정의된 메시지 템플릿을 이용하여 메시지를 전송합니다. 카카오톡링크로 보낼 수 있는 메시지 템플릿 유형은 다음과 같습니다.

자세히 보고 싶은 메시지를 클릭하세요.

[<img width="150" src="https://developers.kakao.com/assets/images/dashboard/default_feed.png">](https://github.com/trabricks/react-native-kakaosdk/blob/master/docs/Link.md#피드-템플릿-보내기)
[<img width="150" src="https://developers.kakao.com/assets/images/dashboard/default_list.png">](https://github.com/trabricks/react-native-kakaosdk/blob/master/docs/Link.md#리스트-템플릿-보내기)
[<img width="150" src="https://developers.kakao.com/assets/images/dashboard/default_commerce.png">](https://github.com/trabricks/react-native-kakaosdk/blob/master/docs/Link.md#커머스-템플릿-보내기)
[<img width="150" src="https://developers.kakao.com/assets/images/dashboard/default_location.png">](https://github.com/trabricks/react-native-kakaosdk/blob/master/docs/Link.md#위치-템플릿-보내기)
[<img width="150" src="https://developers.kakao.com/assets/images/dashboard/default_scrap.png">](https://github.com/trabricks/react-native-kakaosdk/blob/master/docs/Link.md#스크랩-템플릿-보내기)
