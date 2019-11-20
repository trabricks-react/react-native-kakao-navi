require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name    = "ARNKakaoNavi"
  s.version = package['version']
  s.summary = "Kakao Login For React Native."
  
  s.authors   = { "Suhan Moon" => "leader@trabricks.io" }
  s.homepage  = "https://github.com/trabricks/react-native-login#readme"
  s.license   = "MIT"

  s.platform      = :ios, "9.0"
  s.framework     = 'UIKit'
  s.requires_arc  = true

  s.source        = { :git => "https://github.com/trabricks/react-native-kakao-login.git" }
  s.source_files  = "ios/*.{h,m}"

  s.dependency "React"
  s.dependency "ARNKakaoSDK"

  s.vendored_frameworks = 'KakaoNavi.framework'

end

  
