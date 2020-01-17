require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name    = "ARNKakaoNavi"
  s.version = package['version']
  s.summary = "Kakao Navigation For React Native."
  
  s.authors   = { "Suhan Moon" => "leader@trabricks.io" }
  s.homepage  = "https://github.com/trabricks/react-native-navi#readme"
  s.license   = package['license']

  s.platform      = :ios, "9.0"
  s.framework     = 'UIKit'
  s.requires_arc  = true

  s.source        = { :git => "https://github.com/trabricks/react-native-kakao-navi.git" }
  s.source_files  = "ios/*.{h,m}"

  s.dependency "React"
  s.dependency "ARNKakaoSDK"

  s.vendored_frameworks = 'KakaoNavi.framework'

end

  
