//
//  ANKakaoNavi.m
//  RNCKakaoSDK
//
//  Created by Minhyuk Kim on 2019/11/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "ARNKakaoNavi.h"
#import <KakaoNavi/KakaoNavi.h>

@implementation ARNKakaoNavi

- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE(ARNKakaoNavi)

RCT_EXPORT_METHOD(share: (NSDictionary *)location
                  options: (NSDictionary *)options
                  viaList: (NSArray *)viaList
                  shareWithResolver: (RCTPromiseResolveBlock) resolve
                  rejector: (RCTPromiseRejectBlock) reject)
{
  KNVLocation *destination = [KNVLocation locationWithName: location[@"name"]
                                                         x: location[@"x"]
                                                         y: location[@"y"]
                              ];
  
  KNVParams *params = [KNVParams paramWithDestination:destination options:(KNVOptions *) options viaList:viaList];
  
  
  [[KNVNaviLauncher sharedLauncher] setEnableWebNavi:YES];
  [[KNVNaviLauncher sharedLauncher] shareDestinationWithParams: params completion:^(NSError * _Nullable error) {
    if (error) {
      reject(@"ANKakaoNavi", error.userInfo.description, nil);
    } else {
      resolve(@"SUCCESS");
    }
  }];
  
}


RCT_EXPORT_METHOD(navigate: (NSDictionary *)location
                  options: (NSDictionary *)options
                  viaList:(NSArray *)viaList
                  shareWithResolver: (RCTPromiseResolveBlock) resolve
                  rejector: (RCTPromiseRejectBlock) reject)
{
  KNVLocation *destination = [KNVLocation locationWithName: location[@"name"]
                                                         x: location[@"x"]
                                                         y: location[@"y"]
                              ];
  
  KNVParams *params = [KNVParams paramWithDestination:destination options:[KNVOptions options] viaList:viaList];
  
  
  [[KNVNaviLauncher sharedLauncher] setEnableWebNavi:YES];
  [[KNVNaviLauncher sharedLauncher] navigateWithParams: params completion:^(NSError * _Nullable error) {
    if (error) {
      reject(@"ANKakaoNavi", error.userInfo.description, nil);
    } else {
      resolve(@"SUCCESS");
    }
  }];
  
}


@end
