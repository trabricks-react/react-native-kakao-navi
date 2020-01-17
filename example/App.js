/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from "react";
import {
  Platform,
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  ActivityIndicator
} from "react-native";

import KakaoSDK from "react-native-ccs-kakaosdk";

export default function App() {
  const share = () => {
    const destination = {
      name: "카카오판교오피스",
      x: 321286,
      y: 533707
    };
    KakaoSDK.navi.share(destination);
  };

  const navigate = () => {
    const destination = {
      name: "카카오판교오피스",
      x: 321286,
      y: 533707
    };
    KakaoSDK.navi.navigate(destination);
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity onPress={() => share()} style={styles.button}>
        <Text>share</Text>
      </TouchableOpacity>

      <TouchableOpacity onPress={() => navigate()} style={styles.button}>
        <Text>navigate</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#F5FCFF"
  },

  button: {
    borderColor: "#000",
    borderWidth: 1,
    padding: 10,
    width: 200,
    margin: 10
  }
});
