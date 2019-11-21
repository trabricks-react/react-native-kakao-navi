declare module "actbase-native-kakaosdk" {
  export enum CoordType {
    KNVCoordTypeKATEC = 1,
    KNVCoordTypeWGS84 = 2
  }
  export enum RpOption {
    KNVRpOptionFast = 1,
    KNVRpOptionFree = 2,
    KNVRpOptionShortest = 3,
    KNVRpOptionNoAuto = 4,
    KNVRpOptionWide = 5,
    KNVRpOptionHighway = 6,
    KNVRpOptionNormal = 8,
    KNVRpOptionRecommended = 100
  }

  export enum VehicleType {
    KNVVehicleTypeFirst = 1,
    KNVVehicleTypeSecond = 2,
    KNVVehicleTypeThird = 3,
    KNVVehicleTypeFourth = 4,
    KNVVehicleTypeFifth = 5,
    KNVVehicleTypeSixth = 6,
    KNVVehicleTypeTwoWheel = 7
  }

  export interface ARNKakaoNaviLocation {
    name: string;
    x: number;
    y: number;
  }

  export interface ARNKakaoNaviOptions {
    coordType?: CoordType;
    returnUri?: string;
    routeInfo?: boolean;
    rpOption?: RpOption;
    startAngle?: number;
    startX?: number;
    startY?: number;
    userId?: string;
    vehicleType?: VehicleType;
  }

  export type ARNKakaoNaviViaList = ARNKakaoNaviLocation[];

  export interface ARNKakaoNavi {
    share: (
      location: ARNKakaoNaviLocation,
      options?: ARNKakaoNaviOptions,
      viaList?: ARNKakaoNaviViaList
    ) => Promise<"SUCCESS">;
    navigate: (
      location: ARNKakaoNaviLocation,
      options?: ARNKakaoNaviOptions,
      viaList?: ARNKakaoNaviViaList
    ) => Promise<"SUCCESS">;
  }

  export default ARNKakaoNavi;
}
