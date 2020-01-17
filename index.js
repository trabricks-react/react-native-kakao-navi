import { NativeModules } from 'react-native';

const { ARNKakaoNavi } = NativeModules;

export const share = (location, options = {}, viaList = []) => {
  if (!location) {
    console.error(
      'Cannot call the ANKakaoNavi.share with ' + location + ' location',
    );
  } else if (viaList.length > 3) {
    console.error('viaList must be <=3');
  } else {
    return ARNKakaoNavi.share(location, options, viaList);
  }
};
export const navigate = (location, options = {}, viaList = []) => {
  if (!location) {
    console.error(
      'Cannot call the ANKakaoNavi.navigate with ' + location + ' location',
    );
  } else if (viaList.length > 3) {
    console.error('viaList must be <=3');
  } else {
    return ARNKakaoNavi.navigate(location, options, viaList);
  }
};

export default {
  share,
  navigate,
};
