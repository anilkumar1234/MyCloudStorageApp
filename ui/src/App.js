import React, { Component } from 'react';
import './App.css';
import FileExplorer from './components/FileExplorer';

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">          
          <h1 className="App-title">Welcome to CloudStorage explorer</h1>
        </header>
        <div className="App-intro">
          <FileExplorer />
        </div>
      </div>
    );
  }
}

export default App;
