declare module "actbase-native-kakaosdk" {
  export enum CoordType {
    KATEC = 1,
    WGS84 = 2
  }
  export enum RpOption {
    Fast = 1,
    Free = 2,
    Shortest = 3,
    NoAuto = 4,
    Wide = 5,
    Highway = 6,
    Normal = 8
  }

  export enum VehicleType {
    First = 1,
    Second = 2,
    Third = 3,
    Fourth = 4,
    Fifth = 5,
    Sixth = 6,
    TwoWheel = 7
  }

  export interface ARNKakaoNaviLocation {
    name: string;
    x: number;
    y: number;
  }

  export interface ARNKakaoNaviOptions {
    coordType?: CoordType;
    vehicleType?: VehicleType;
    rpoption?: RpOption;
    routeInfo?: boolean;
    startX?: number;
    startY?: number;
    startAngle?: number;
    userId?: string;
    returnUri?: string;
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
