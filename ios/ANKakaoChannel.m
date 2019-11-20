//
//  ANKakaoChannel.m
//  RNCKakaoSDK
//
//  Created by Minhyuk Kim on 2019/11/18.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "ANKakaoChannel.h"
#import <KakaoPlusFriend/KakaoPlusFriend.h>

@implementation ANKakaoChannel

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE(ANKakaoChannel)

RCT_EXPORT_METHOD(addFriend: (NSString *) friendId
                  resolver: (RCTPromiseResolveBlock)resolve
                  rejecter: (RCTPromiseRejectBlock)reject)
{
    
    KPFPlusFriend* plusFriend = [[KPFPlusFriend alloc] initWithId:friendId];
    [plusFriend addFriend];
    resolve(@"SUCCESS");
};


RCT_EXPORT_METHOD(chat: (NSString *) friendId
                  resolver: (RCTPromiseResolveBlock)resolve
                  rejecter: (RCTPromiseRejectBlock)reject)
{
    KPFPlusFriend* plusFriend = [[KPFPlusFriend alloc] initWithId:friendId];
    [plusFriend chat];
    resolve(@"SUCCESS");
};

@end
