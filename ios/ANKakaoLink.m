//
//  ANKakaoLink.m
//  RNCKakaoSDK
//
//  Created by Suhan Moon on 2019/11/03.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "ANKakaoLink.h"
#import <KakaoLink/KakaoLink.h>
#import <KakaoMessageTemplate/KakaoMessageTemplate.h>

@implementation ANKakaoLink

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE(ANKakaoLink)

- (void) sendWithTemplate: (KMTTemplate *)template resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject {
    [[KLKTalkLinkCenter sharedCenter] sendDefaultWithTemplate:template success:^(NSDictionary<NSString *,NSString *> * _Nullable warningMsg, NSDictionary<NSString *,NSString *> * _Nullable argumentMsg) {
        resolve(argumentMsg);
    } failure:^(NSError * _Nonnull error) {
        NSDictionary *dict = error.userInfo;
        NSString *reason = [dict objectForKey:@"NSLocalizedFailureReason"];
        reject(@"ANKakaoLink", reason, error);
    }];
}

- (KMTContentObject *) createContent: (NSDictionary *) dict {
    if (dict == nil) return nil;
    return [KMTContentObject contentObjectWithBuilderBlock:^(KMTContentBuilder * _Nonnull contentBuilder) {
        contentBuilder.title = [dict objectForKey:@"title"];
        contentBuilder.desc = [dict objectForKey:@"desc"];
        contentBuilder.link = [self createLink: [dict objectForKey:@"link"]];
        if ([dict objectForKey:@"imageURL"] != nil) {
            contentBuilder.imageURL = [NSURL URLWithString:[dict objectForKey:@"imageURL"]];
        }
    }];
}

- (KMTLinkObject *) createLink: (NSDictionary *) link {
    if (link == nil) return nil;
    return [KMTLinkObject linkObjectWithBuilderBlock:^(KMTLinkBuilder * _Nonnull linkBuilder) {
        linkBuilder.webURL = [link objectForKey:@"webURL"] == nil ? nil: [NSURL URLWithString:[link objectForKey:@"webURL"]];
        linkBuilder.mobileWebURL = [link objectForKey:@"mobileWebURL"] == nil ? nil: [NSURL URLWithString:[link objectForKey:@"mobileWebURL"]];
        linkBuilder.iosExecutionParams = [link objectForKey:@"iosExecutionParams"];
        linkBuilder.androidExecutionParams = [link objectForKey:@"androidExecutionParams"];
    }];
}

- (KMTButtonObject *) createButton: (NSDictionary *) button {
    if (button == nil) return nil;
    return [KMTButtonObject buttonObjectWithBuilderBlock:^(KMTButtonBuilder * _Nonnull buttonBuilder) {
        buttonBuilder.title = [button objectForKey:@"title"];
        buttonBuilder.link = [self createLink: button];
    }];
}

- (KMTSocialObject *) createSocial: (NSDictionary *) dict {
    if (dict == nil) return nil;
    return [KMTSocialObject socialObjectWithBuilderBlock:^(KMTSocialBuilder * _Nonnull socialBuilder) {
        socialBuilder.likeCount = [dict objectForKey:@"likeCount"];
        socialBuilder.commnentCount = [dict objectForKey:@"commentCount"];
        socialBuilder.sharedCount = [dict objectForKey:@"sharedCount"];
        socialBuilder.viewCount = [dict objectForKey:@"viewCount"];
        socialBuilder.subscriberCount = [dict objectForKey:@"subscriberCount"];
    }];
}

- (KMTCommerceObject *) createCommerce: (NSDictionary *)dict {
    if (dict == nil) return nil;
    return [KMTCommerceObject commerceObjectWithBuilderBlock:^(KMTCommerceBuilder * _Nonnull commerceBuilder) {
        commerceBuilder.regularPrice = [dict objectForKey:@"regularPrice"];
        commerceBuilder.discountPrice = [dict objectForKey:@"discountPrice"];
        commerceBuilder.discountRate = [dict objectForKey:@"discountRate"];
    }];
}

RCT_EXPORT_METHOD(sendFeed,
                 sendFeed: (NSDictionary *)dict resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    
    KMTTemplate *template = [KMTFeedTemplate feedTemplateWithBuilderBlock:^(KMTFeedTemplateBuilder * _Nonnull feedTemplateBuilder) {
        feedTemplateBuilder.content = [self createContent:[dict objectForKey:@"content"]];
        feedTemplateBuilder.social = [self createSocial:[dict objectForKey:@"social"]];
        
        if ([dict objectForKey:@"buttons"] != nil) {
            NSArray * buttons = [dict objectForKey:@"buttons"];
            for (NSDictionary * btn in buttons) [feedTemplateBuilder addButton:[self createButton:btn]];
        }
    }];
    
    [self sendWithTemplate:template resolver:resolve rejecter:reject];
}

RCT_EXPORT_METHOD(sendList,
                 sendList: (NSDictionary *)dict resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {

    KMTTemplate *template = [KMTListTemplate listTemplateWithBuilderBlock:^(KMTListTemplateBuilder * _Nonnull listTemplateBuilder) {

        listTemplateBuilder.headerTitle = [dict objectForKey:@"headerTitle"];
        listTemplateBuilder.headerLink = [self createLink: [dict objectForKey:@"headerLink"]];
        
        if ([dict objectForKey:@"contents"] != nil) {
            NSArray * contents = [dict objectForKey:@"contents"];
            for (NSDictionary * content in contents) [listTemplateBuilder addContent:[self createContent:content]];
        }
        
        if ([dict objectForKey:@"buttons"] != nil) {
            NSArray * buttons = [dict objectForKey:@"buttons"];
            for (NSDictionary * btn in buttons) [listTemplateBuilder addButton:[self createButton:btn]];
        }
    }];
    
    [self sendWithTemplate:template resolver:resolve rejecter:reject];
}

RCT_EXPORT_METHOD(sendLocation,
                 sendLocation: (NSDictionary *)dict resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {

    KMTTemplate *template = [KMTLocationTemplate locationTemplateWithBuilderBlock:^(KMTLocationTemplateBuilder * _Nonnull locationTemplateBuilder) {
        locationTemplateBuilder.content = [self createContent:[dict objectForKey:@"content"]];
        locationTemplateBuilder.address = [dict objectForKey:@"address"];
        locationTemplateBuilder.addressTitle = [dict objectForKey:@"addressTitle"];
        
        if ([dict objectForKey:@"buttons"] != nil) {
            NSArray * buttons = [dict objectForKey:@"buttons"];
            for (NSDictionary * btn in buttons) [locationTemplateBuilder addButton:[self createButton:btn]];
        }
    }];
    [self sendWithTemplate:template resolver:resolve rejecter:reject];
}

RCT_EXPORT_METHOD(sendCommerce,
                 sendCommerce: (NSDictionary *)dict resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {

    KMTTemplate *template = [KMTCommerceTemplate commerceTemplateWithBuilderBlock:^(KMTCommerceTemplateBuilder * _Nonnull commerceTemplateBuilder) {
        commerceTemplateBuilder.content = [self createContent:[dict objectForKey:@"content"]];
        commerceTemplateBuilder.commerce = [self createCommerce:[dict objectForKey:@"commerce"]];
        if ([dict objectForKey:@"buttons"] != nil) {
            NSArray * buttons = [dict objectForKey:@"buttons"];
            for (NSDictionary * btn in buttons) [commerceTemplateBuilder addButton:[self createButton:btn]];
        }
    }];
    [self sendWithTemplate:template resolver:resolve rejecter:reject];
}

RCT_EXPORT_METHOD(sendText,
                 sendText: (NSDictionary *)dict resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {

    KMTTemplate *template = [KMTTextTemplate textTemplateWithBuilderBlock:^(KMTTextTemplateBuilder * _Nonnull textTemplateBuilder) {
        textTemplateBuilder.text = [dict objectForKey:@"text"];
        textTemplateBuilder.link = [self createLink: [dict objectForKey:@"link"]];
        textTemplateBuilder.buttonTitle = [dict objectForKey:@"buttonTitle"];
        
        if ([dict objectForKey:@"buttons"] != nil) {
            NSArray * buttons = [dict objectForKey:@"buttons"];
            for (NSDictionary * btn in buttons) [textTemplateBuilder addButton:[self createButton:btn]];
        }
    }];
    [self sendWithTemplate:template resolver:resolve rejecter:reject];
}

RCT_EXPORT_METHOD(sendURL,
                 sendURL: (NSString *)url resolver: (RCTPromiseResolveBlock)resolve rejecter: (RCTPromiseRejectBlock)reject) {
    
    [[KLKTalkLinkCenter sharedCenter] sendScrapWithURL: [NSURL URLWithString: url] success:^(NSDictionary<NSString *,NSString *> * _Nullable warningMsg, NSDictionary<NSString *,NSString *> * _Nullable argumentMsg) {
        resolve(argumentMsg);
    } failure:^(NSError * _Nonnull error) {
        NSDictionary *dict = error.userInfo;
        NSString *reason = [dict objectForKey:@"NSLocalizedFailureReason"];
        reject(@"ANKakaoLink", reason, error);
    }];
}

@end
