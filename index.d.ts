import { NativeSyntheticEvent } from "react-native";
declare module "actbase-native-kakaosdk" {
  export interface LinkObject {
    webURL?: string;
    mobileWebURL?: string;
    androidExecutionParams?: string;
    iosExecutionParams?: string;
  }

  export interface SocialObject {
    likeCount?: number;
    commentCount?: number;
    sharedCount?: number;
    viewCount?: number;
    subscriberCount?: number;
  }

  export interface CommerceDetailObject {
    regularPrice: number;
    discountPrice?: number;
    discountRate?: number;
    fixedDiscountPrice?: number;
  }

  export interface ButtonObject {
    title: string;
    webURL?: string;
    mobileWebURL?: string;
    androidExecutionParams?: string;
    iosExecutionParams?: string;
  }

  export interface ContentObject {
    title: string;
    link: LinkObject;
    imageURL: string;
    desc?: string;
    imageWidth?: number;
    imageHeight?: number;
  }

  export interface SendFeedParams {
    content: ContentObject;
    social?: SocialObject;
    buttons?: ButtonObject[];
  }

  export interface SendListParams {
    headerTitle: string;
    headerLink: LinkObject;
    contents?: ContentObject[];
    buttons?: ButtonObject[];
  }

  export interface SendLocationParams {
    content: ContentObject;
    address: string;
    addressTitle?: string;
    buttons?: ButtonObject[];
  }

  export interface SendCommerceParams {
    content: ContentObject;
    commerce: CommerceDetailObject;
    buttons?: ButtonObject[];
  }

  export interface SendTextParams {
    text: string;
    link: LinkObject;
    buttonTitle?: string;
    buttons?: ButtonObject[];
  }

  export interface AccessTokenType {
    accessToken: string;
    remainingExpireTime: number;
    scopes: string[];
  }

  export interface ANKakaoLinkResponseType {
    key: string;
    value: string;
  }

  export interface ANKakaoLink {
    sendFeed: (data: SendFeedParams) => Promise<ANKakaoLinkResponseType>;

    sendList: (data: SendListParams) => Promise<ANKakaoLinkResponseType>;

    sendLocation: (
      data: SendLocationParams
    ) => Promise<ANKakaoLinkResponseType>;

    sendCommerce: (
      data: SendCommerceParams
    ) => Promise<ANKakaoLinkResponseType>;

    sendText: (data: SendTextParams) => Promise<ANKakaoLinkResponseType>;

    sendURL: (url: string) => Promise<ANKakaoLinkResponseType>;
  }

  export interface ANKakaoLogin {
    getAccessToken: () => Promise<null | AccessTokenType>;
    login: () => Promise<null | AccessTokenType>;
    logout: () => Promise<"SUCCESS">;
  }

  export interface ANKakaoChannel {
    addFriend: (id: string) => Promise<"SUCCESS">;
    chat: (id: string) => Promise<"SUCCESS">;
  }

  export interface KakaoSDK {
    link: ANKakaoLink;
    login: ANKakaoLogin;
    channel: ANKakaoChannel;
  }

  export default KakaoSDK;
}
