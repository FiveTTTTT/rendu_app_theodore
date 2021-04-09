import React, {
  Component
} from 'react';
import {
  View,
  Text,
  AppRegistry
} from 'react-native';
class ReactCalculator extends Component {
  render() {
    return (
      <View style={{ flex: 2 }}>
        <View style={{ flex: 5, backgroundColor: '#193441' }}></View>
        <View style={{ flex: 5, backgroundColor: '#3E606F' }}></View>
      </View>
    );
  }
}

export default ReactCalculator