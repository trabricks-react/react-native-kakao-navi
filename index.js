import { NativeModules } from "react-native";

const { ARNKakaoNavi } = NativeModules;

export const navi = {
  share: (location, options = {}, viaList = []) => {
    if (!location) {
      console.error(
        "Cannot call the ANKakaoNavi.share with " + location + " location"
      );
    } else if (viaList.length > 3) {
      console.error("viaList must be <=3");
    } else {
      ARNKakaoNavi.share(location, options, viaList);
    }
  },
  navigate: (location, options = {}, viaList = []) => {
    if (!location) {
      console.error(
        "Cannot call the ANKakaoNavi.navigate with " + location + " location"
      );
    } else if (viaList.length > 3) {
      console.error("viaList must be <=3");
    } else {
      ARNKakaoNavi.navigate(location, options, viaList);
    }
  }
};

export default navi;
