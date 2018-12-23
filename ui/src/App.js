import React, { Component } from 'react';
import TreeView from './TreeView';
import UploadButton from './components/upload';
import {Treebeard} from './TreeComponents';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as actionCreators from './actions';


const mapStateToProps = function(state){
  return {
    data: state.data,
  }
}

const mapDispatchToProps = function (dispatch) {
  return bindActionCreators({
    downloadFile: actionCreators.downloadFile,
    listFiles:actionCreators.listFiles
  }, dispatch)
}


class App extends Component {

  constructor(props){
    super(props);
    this.state={data:{name:"root",toggled:true,children:[],loading:true}};
    this.onToggle = this.onToggle.bind(this);
    this.buildTree=this.buildTree.bind(this);
    this.listFiles=this.listFiles.bind(this);
    this.downloadFile=this.downloadFile.bind(this);
    this.setTreeData=this.setTreeData.bind(this);
  }

  buildTree(data){
    var tree={name:"root",toggled:true,children:[]};
    for(var i=0;i<data.length;i++){
      if(data[i].directory){
        data[i].toggled=true;
      }
      tree.children.push(data[i]);
    }
    console.log(tree);
    return tree;
  }

  onToggle(node, toggled){
      if(this.state.cursor){
        this.setState({cursor:{active:false}});
      }
      node.active = true;
      if(node.children){ node.toggled = toggled; }
      this.setState({ cursor: node });
  }

  listFiles(path,callback){
    fetch('http://localhost:1234/rest/list?path='+path)
      .then(response => response.json())
      .then(data => {
        callback(data);
      });
  }

  downloadFile(path){
    window.open(path);
  }

  setTreeData(data){
    var fileTree=this.buildTree(data);
    this.setState({data:fileTree});
  }

  componentDidMount(){
    this.listFiles("/Expedia",this.setTreeData);
  }

  render() {
    return (
        <div className="App">
        <Treebeard
            data={this.state.data}
            onToggle={this.onToggle} listFiles={this.props.listFiles} downloadFile={this.props.downloadFile}
        />
          <UploadButton />
        </div>
    );
  }
}

export default connect(mapStateToProps,mapDispatchToProps)(App);
