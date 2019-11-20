
# Kakao Link Guide

카카오링크 기능 중 Custom을 제외한 나머지를 사용할 수 있습니다.

사용방법은 기존의 카카오링크와 유사하게 작성되었습니다.

## Guide Index
- [`피드 템플릿 보내기`](Link.md#피드-템플릿-보내기)
- [`리스트 템플릿 보내기`](Link.md#리스트-템플릿-보내기)
- [`위치 템플릿 보내기`](Link.md#위치-템플릿-보내기)
- [`커머스 템플릿 보내기`](Link.md#커머스-템플릿-보내기)
- [`텍스트 템플릿 보내기`](Link.md#텍스트-템플릿-보내기)
- [`스크랩 템플릿 보내기`](Link.md#스크랩-템플릿-보내기)


## 사용방법

### `기본 템플릿 보내기`

카카오링크 SDK에서는 가장 많이 쓰이는 메시지 템플릿 형태를 기본 템플릿으로 정의하고 소스코드 상에서 간편하게 생성할 수 있는 인터페이스를 제공합니다. 카카오링크 SDK에서는 기본 템플릿의 계층 구조를 효과적으로 표현하기 위하여 기본 자료형 이외에 다양한 오브젝트 클래스를 정의하고 있습니다.


#### `Content 오브젝트`
콘텐츠의 내용을 담고 있는 오브젝트입니다. 기본 템플릿에서는 모든 메시지에 하나 이상의 콘텐츠를 가지고 있습니다.

```
기본 템플릿에서 사용되는 콘텐츠 오브젝트는 한 개의 이미지와 제목, 설명, 링크 정보를 가질 수 있습니다.
```

|이름|설명|타입|필수|
|-|-|-|-|
|title|콘텐츠의 타이틀|string|O|
|link|콘텐츠 클릭 시 이동할 링크 정보|[`LinkObject`](Link.md#Link-오브젝트)|O|
|imageURL|콘텐츠의 이미지 URL|string|O|
|desc|콘텐츠의 상세 설명|string|X|
|imageWidth|콘텐츠의 이미지 너비 (단위: 픽셀)|number|X|
|imageHeight|콘텐츠의 이미지 높이 (단위: 픽셀)|number|X|


#### `Link 오브젝트`
메시지에서 콘텐츠 영역이나 버튼 클릭 시에 이동되는 링크 정보 오브젝트입니다.

- 오브젝트 내 프로퍼티 중 하나 이상은 반드시 존재해야 합니다. 아무 값도 입력하지 않으면 버튼이 보이지 않거나 클릭 시 이동하지 않을 수 있습니다.
- 링크에 사용되는 도메인은 반드시 내 애플리케이션 설정에 등록되어야 합니다. 도메인은 개발자 웹사이트의 [내 애플리케이션] - 앱 선택 - [설정] - [일반] 메뉴에서 등록할 수 있습니다.
- 링크 실행 우선순위는 (android/ios)ExecutionParams > mobileWebURL > webURL 입니다.

|이름|설명|타입|필수|
|-|-|-|-|
|webURL|PC버전 카카오톡에서 사용하는 웹 링크 URL|string|X|
|mobileWebURL|모바일 카카오톡에서 사용하는 웹 링크 URL|string|X|
|androidExecutionParams|안드로이드 카카오톡에서 사용하는 앱 링크 URL에 사용될 파라미터.|string|X|
|iosExecutionParams|iOS 카카오톡에서 사용하는 앱 링크 URL에 사용될 파라미터.|string|X|


#### `Social 오브젝트`
좋아요 수, 댓글 수 등의 소셜 정보를 표현하기 위해 사용되는 오브젝트입니다.
```
5개의 속성 중 최대 3개만 표시해 줍니다. 우선순위는 Like > Comment > Shared > View > Subscriber 입니다.
```

|이름|설명|타입|필수|
|-|-|-|-|
|likeCount|콘텐츠의 좋아요 수|number|X|
|commentCount|콘텐츠의 댓글 수|number|X|
|sharedCount|콘텐츠의 공유 수|number|X|
|viewCount|콘텐츠의 조회 수|number|X|
|subscriberCount|콘텐츠의 구독 수|number|X|


#### `CommerceDetail 오브젝트`
가격 정보를 표현하기 위해 사용되는 오브젝트입니다.

|이름|설명|타입|필수|
|-|-|-|-|
|regularPrice|정상가격|number|O|
|discountPrice|할인된 가격|number|X|
|discountRate|할인율|number|X|
|fixedDiscountPrice|정액 할인 가격|number|X|

#### `Button 오브젝트`
메시지 하단에 추가되는 버튼 오브젝트입니다.

|이름|설명|타입|필수|
|-|-|-|-|
|title|버튼의 타이틀|string|O|
|webURL|PC버전 카카오톡에서 사용하는 웹 링크 URL|string|X|
|mobileWebURL|모바일 카카오톡에서 사용하는 웹 링크 URL|string|X|
|androidExecutionParams|안드로이드 카카오톡에서 사용하는 앱 링크 URL에 사용될 파라미터.|string|X|
|iosExecutionParams|iOS 카카오톡에서 사용하는 앱 링크 URL에 사용될 파라미터.|string|X|

### `피드 템플릿 보내기`

```
1. 이미지 영역: 최대 1장, 최소 200px * 200px이상, 2MB이하
2. 제목/설명 영역: 최대 4줄 표시 (제목, 설명 각각 2줄 표시)
3. 소셜 영역: 최대 3개 표시 (순서: 좋아요 > 댓글 > 공유 > 조회 > 구독)
4. 버튼 영역: 최대 2개 표시, 버튼명 8자 이하 권장
```

<img alt="screenshot of FeedTemplate" src="https://developers.kakao.com/assets/images/dashboard/default_feed_spec.png" />

기본 템플릿으로 제공되는 피드 템플릿은 하나의 콘텐츠와 하나의 기본 버튼을 가집니다. 소셜 정보를 추가할 수 있으며 임의의 버튼을 설정할 수도 있습니다.

카카오링크 SDK에서는 다음과 같은 형태의 피드 템플릿 클래스를 사용합니다.


|이름|설명|타입|필수|
|---|-------|---|-------|
|content|메시지의 메인 콘텐츠 정보|[`ContentObject`](Link.md#Content-오브젝트)|O|
|social |콘텐츠에 대한 소셜 정보|[`SocialObject`](Link.md#Social-오브젝트)|X|
|buttonTitle |기본 버튼 타이틀("자세히 보기")을 변경하고 싶을 때 설정|string|X|
|buttons |버튼 목록. 버튼 타이틀과 링크를 변경하고 싶을때, 버튼 두개를 사용하고 싶을때 사용 |[`ButtonObject[]`](Link.md#Button-오브젝트)|X|

buttonTitle, buttons 모두 있을 경우 buttons가 사용됩니다. 둘 다 주어지지 않았을 때에는 기본 타이틀과 content에 있는 link정보로 버튼 하나가 구성됩니다.


```js
import KakaoSDK from 'actbase-native-kakaosdk';

...

KakaoSDK.link.sendFeed({
  content: {
    title: '디저트 사진',
    desc: '아메리카노, 빵, 케익',
    imageURL: 'http://mud-kage.kakao.co.kr/dn/NTmhS/btqfEUdFAUf/FjKzkZsnoeE4o19klTOVI1/openlink_640x640s.jpg',
    link: {
      webURL: 'https://developers.kakao.com',
      mobileWebURL: 'https://developers.kakao.com',
    },
  },
  social: {
    likeCount: 10,
    commentCount: 20,
    sharedCount: 30,
    viewCount: 40,
  },
  buttons: [{
    title: '앱에서 보기',
    webURL: 'https://developers.kakao.com',
    mobileWebURL: 'https://developers.kakao.com',
    androidExecutionParams: 'key1=value1',
    iosExecutionParams: 'key1=value1',
  }],
})
.then(r => console.log('success'))
.catch(e => console.log(e));
```


### `리스트 템플릿 보내기`

```
1. 헤더 영역
2. 아이템 리스트 영역: 최대 3개 표시
3. 제목/설명 영역: 최대 3줄 표시 (제목 2줄, 설명 1줄 표시)
4. 이미지 영역: 최소 200px * 200px이상, 2MB이하
5. 버튼 영역: 최대 2개 표시, 버튼명 8자 이하 권장
```

<img alt="screenshot of ListTemplate" src="https://developers.kakao.com/assets/images/dashboard/default_list_spec.png" />

리스트 템플릿은 메시지 상단에 노출되는 헤더 타이틀과, 콘텐츠 목록, 버튼 등으로 구성됩니다. 헤더와 콘텐츠 각각의 링크를 가질 수 있습니다. 피드 템플릿과 마찬가지로 하나의 기본 버튼을 가지며 임의의 버튼을 설정할 수 있습니다.


|이름|설명|타입|필수|
|---|-------|---|-------|
|headerTitle|리스트 상단에 노출되는 헤더 타이틀|string|O|
|headerLink|헤더 타이틀 내용에 해당하는 링크 정보|[`LinkObject`](Link.md#Link-오브젝트)|O|
|contents|리스트에 노출되는 콘텐츠 목록. 최소 2개, 최대 3개|[`ContentObject[]`](Link.md#Content-오브젝트)|O|
|buttonTitle|기본 버튼 타이틀("자세히 보기")을 변경하고 싶을 때 설정|string|X|
|buttons|버튼 목록. 버튼 타이틀과 링크를 변경하고 싶을때, 버튼 두개를 사용하고 싶을때 사용.(최대 2개)|[`ButtonObject`](Link.md#Button-오브젝트)|X|

buttonTitle, buttons 모두 있을 경우 buttons가 사용됩니다. 둘 다 주어지지 않았을 때에는 기본 타이틀과 content에 있는 link정보로 버튼 하나가 구성됩니다.


```js
import KakaoSDK from 'actbase-native-kakaosdk';

...

KakaoSDK.link.sendList({
  headerTitle: 'WEEKELY MAGAZINE',
  headerLink: {
    webURL: 'https://developers.kakao.com',
    mobileWebURL: 'https://developers.kakao.com',
  },
  buttons: [{
    title: '웹에서 보기',
    webURL: 'https://developers.kakao.com',
    mobileWebURL: 'https://developers.kakao.com',
  }, {
    title: '앱에서 보기',
    webURL: 'https://developers.kakao.com',
    mobileWebURL: 'https://developers.kakao.com',
    androidExecutionParams: 'key1=value1',
    iosExecutionParams: 'key1=value1',
  }],
  contents: [{
    title: '자전거 라이더를 위한 공간',
    desc: '매거진',
    imageURL: 'http://mud-kage.kakao.co.kr/dn/QNvGY/btqfD0SKT9m/k4KUlb1m0dKPHxGV8WbIK1/openlink_640x640s.jpg',
    link: {
      webURL: 'https://developers.kakao.com',
      mobileWebURL: 'https://developers.kakao.com',
    },
  }, {
    title: '비쥬얼이 끝내주는 오레오 카푸치노',
    desc: '매거진',
    imageURL: 'http://mud-kage.kakao.co.kr/dn/boVWEm/btqfFGlOpJB/mKsq9z6U2Xpms3NztZgiD1/openlink_640x640s.jpg',
    link: {
      webURL: 'https://developers.kakao.com',
      mobileWebURL: 'https://developers.kakao.com',
    },
  }, {
    title: '감성이 가득한 분위기',
    desc: '매거진',
    imageURL: 'http://mud-kage.kakao.co.kr/dn/NTmhS/btqfEUdFAUf/FjKzkZsnoeE4o19klTOVI1/openlink_640x640s.jpg',
    link: {
      webURL: 'https://developers.kakao.com',
      mobileWebURL: 'https://developers.kakao.com',
    },
  }],
});
```


### `위치 템플릿 보내기`

```
1. 이미지 영역: 최대 1장, 최소 200px * 200px이상, 2MB이하
2. 제목/설명 영역: 최대 4줄 표시 (제목, 설명 각각 2줄 표시)
3. 소셜 영역: 최대 3개 표시 (순서: 좋아요 > 댓글 > 공유 > 조회 > 구독)
4. 버튼 영역: 최대 2개 표시, 버튼명 8자 이하 권장
```

<img alt="screenshot of LocationTemplate" src="https://developers.kakao.com/assets/images/dashboard/default_location_spec.png" />

위치 템플릿은 지도 표시에 사용되는 주소 정보와 해당 위치를 설명할 수 있는 콘텐츠 오브젝트로 구성됩니다. 왼쪽 하단에 기본 버튼, 오른쪽 하단에 지도를 보여주기 위한 "위치 보기" 버튼이 추가됩니다. "위치 보기" 버튼을 클릭하면 카카오톡 채팅방 내에서 바로 지도 화면으로 전환하여 해당 주소의 위치를 확인할 수 있습니다


|이름|설명|타입|필수|
|-|-|-|-|
|content|위치에 대해 설명하는 콘텐츠 정보|[`ContentObject`](Link.md#Content-오브젝트)|O|
|address|공유할 위치의 주소 예) 경기 성남시 분당구 판교역로 235|string|O|
|addressTitle|카카오톡 내의 지도 뷰에서 사용되는 타이틀 예) 카카오판교오피스|string|X|
|social|메인 콘텐츠의 부가 소셜 정보|[`SocialObject`](Link.md#Social-오브젝트)|X|
|buttonTitle|기본 버튼 타이틀("자세히 보기")을 변경하고 싶을 때 설정|string|X|
|buttons|버튼 목록. 기본 버튼의 타이틀 외에 링크도 변경하고 싶을 때 설정. (최대 1개, 오른쪽 "위치 보기" 버튼은 고정)|[`ButtonObject`](Link.md#Button-오브젝트)|X|


```js
import KakaoSDK from 'actbase-native-kakaosdk';

...

KakaoSDK.link.sendLocation({
  address: '성남시 분당구 판교역로 235',
  addressTitle: '카카오 판교오피스',
  content: {
    title: '카카오 판교오피스',
    desc: '카카오 판교오피스 위치입니다.',
    imageURL: 'http://www.kakaocorp.com/images/logo/og_daumkakao_151001.png',
    link: {
      webURL: 'https://developers.kakao.com',
      mobileWebURL: 'https://developers.kakao.com',
    },
  },
})
.then(r => console.log('success'))
.catch(e => console.log(e));
```


### `커머스 템플릿 보내기`

```
1. 이미지 영역: 최대 1장, 최소 200px * 200px이상, 2MB이하
2. 할인된 가격 영역
3. 정상가격 영역
4. 할인율 영역
5. 제품명 영역: 최대 2줄 표시
6. 버튼 영역: 최대 2개 표시, 버튼명 8자 이하 권장
```

<img alt="screenshot of CommerceTemplate" src="https://developers.kakao.com/assets/images/dashboard/default_commerce_spec.png" />

기본 템플릿으로 제공되는 커머스 템플릿은 하나의 콘텐츠, 커머스 상세와 하나의 기본 버튼을 가집니다. 추가로 임의의 버튼을 설정할 수도 있습니다.

|이름|설명|타입|필수|
|-|-|-|-|
|content|메시지의 메인 콘텐츠 정보|[`ContentObject`](Link.md#Content-오브젝트)|O|
|commerce|상품에 대한 가격 정보|[`CommerceDetailObject`](Link.md#CommerceDetail-오브젝트)|O|
|buttonTitle|기본 버튼 타이틀("자세히 보기")을 변경하고 싶을 때 설정|string|X|
|buttons|버튼 목록. 버튼 타이틀과 링크를 변경하고 싶을때, 버튼 두개를 사용하고 싶을때 사용.(최대 2개)|[`ButtonObject[]`](Link.md#Button-오브젝트)|X|


```js
import KakaoSDK from 'actbase-native-kakaosdk';

...

KakaoSDK.link.sendCommerce({
  contents: {
    title: 'Ivory long dress (4 Color)',
    imageURL: 'http://mud-kage.kakao.co.kr/dn/RY8ZN/btqgOGzITp3/uCM1x2xu7GNfr7NS9QvEs0/kakaolink40_original.png',
    link: {
      webURL: 'https://developers.kakao.com',
      mobileWebURL: 'https://developers.kakao.com',
    },
  },
  commerce: {
    regularPrice: 208800,
    discountPrice: 146160,
    discountRate: 30,
  },
  buttons: [{
    title: '구매하기',
    webURL: 'https://style.kakao.com/main/women/contentId=100/buy',
    mobileWebURL: 'https://m.style.kakao.com/main/women/contentId=100/buy',
  }, {
    title: '앱에서 보기',
    webURL: 'https://style.kakao.com/main/women/contentId=100/share',
    mobileWebURL: 'https://m.style.kakao.com/main/women/contentId=100/share',
    androidExecutionParams: 'contentId=100&share=true',
    iosExecutionParams: 'contentId=100&share=true',
  }],
});
```


### `텍스트 템플릿 보내기`

|이름|설명|타입|필수|
|-|-|-|-|
|text|최대 200자의 텍스트 정보|string|O|
|link|해당 컨텐츠 클릭 시 이동 할 링크 정보|[`LinkObject`](Link.md#Link-오브젝트)|O|
|buttonTitle|기본 버튼 타이틀("자세히 보기")을 변경하고 싶을 때 설정|string|X|
|buttons|메시지 하단에 추가되는 버튼 목록. 버튼 타이틀과 링크를 변경하고 싶을때, 버튼 두개를 사용하고 싶을때 사용. 최대 2개|[`ButtonObject[]`](Link.md#Button-오브젝트)|X|

```js
import KakaoSDK from 'actbase-native-kakaosdk';

...

KakaoSDK.link.sendText({
  text: 'Text',
  link: {
    webURL: 'https://developers.kakao.com',
    mobileWebURL: 'https://developers.kakao.com',
  },
  buttonTitle: 'This is button',
})
.then(r => console.log('success'))
.catch(e => console.log(e));
```




### `스크랩 템플릿 보내기`

```
1. 이미지 영역: 최대 1장, 최소 200px * 200px이상, 2MB이하
2. 제목/설명 영역: 최대 4줄 표시 (제목, 설명 각각 2줄 표시)
3. 버튼 영역: 최대 1개 표시, 버튼명 8자 이하 권장
4. A. og:image / B. video:duration, music:duration / C. og:title, og:description / D. og:site_name
```

<img alt="screenshot of ScrapTeamplte" src="https://developers.kakao.com/assets/images/dashboard/default_scrap_spec.png" />

웹 사이트 콘텐츠를 해당 웹 페이지의 Open Graph 정보를 바탕으로 이미지, 제목, 설명, 링크를 구성하여 별도의 템플릿 생성 작업 없이 URL만으로 간편하게 전달합니다.
```
스크랩하려는 웹 사이트의 도메인은 반드시 내 애플리케이션 설정에 등록되어야 합니다.
도메인은 개발자 웹사이트의 [내 애플리케이션] - 앱 선택 - [설정] - [일반] 메뉴에서 등록할 수 있습니다.
```

```js
import KakaoSDK from 'actbase-native-kakaosdk';

...

KakaoSDK.link.sendURL('https://developers.kakao.com')
.then(r => console.log('success'))
.catch(e => console.log(e));
```


