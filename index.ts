import { NativeModules } from "react-native";
import {
  ARNKakaoNaviLocation,
  ARNKakaoNaviOptions,
  ARNKakaoNaviViaList
} from "@actbase/react-native-kakaosdk";
const { ARNKakaoNavi } = NativeModules;

export const share = (
  location: ARNKakaoNaviLocation,
  options: ARNKakaoNaviOptions = {},
  viaList: ARNKakaoNaviViaList = []
) => {
  if (!location) {
    console.error(
      "Cannot call the ANKakaoNavi.share with " + location + " location"
    );
  } else if (viaList.length > 3) {
    console.error("viaList must be <=3");
  } else {
    return ARNKakaoNavi.share(location, options, viaList);
  }
};
export const navigate = (
  location: ARNKakaoNaviLocation,
  options: ARNKakaoNaviOptions = {},
  viaList: ARNKakaoNaviViaList = []
) => {
  if (!location) {
    console.error(
      "Cannot call the ANKakaoNavi.navigate with " + location + " location"
    );
  } else if (viaList.length > 3) {
    console.error("viaList must be <=3");
  } else {
    return ARNKakaoNavi.navigate(location, options, viaList);
  }
};

export default {
  share,
  navigate
};
