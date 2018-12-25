import React, { Component } from 'react';
import styled from 'styled-components';
import Tree from './Tree';
import FileInfo from './FileInfo';

const StyledFileExplorer = styled.div`
  width: 50%;
  max-width: 100%;
  margin: 0 auto;
  display: flex;
`;

const TreeWrapper = styled.div`
  width: 500px;
`;

export default class FileExplorer extends Component {

  constructor(props){
    super(props);
    this.state={selectedFile: null,nodes:{
      '/': {
        path: '/',
        name:'/',
        type: 'folder',
        isRoot: true,
        children: [],
      },
    }};
    this.fetchContents=this.fetchContents.bind(this);
  }

  onSelect = (file) => this.setState({ selectedFile: file });

  fetchContents = (path) => {
    console.log("Fetching contents.."+path);
    fetch(`/rest/list?path=`+path)
      .then(res => res.json())
      .then(json => {
        let children=[];
        let tmpNodes={};
        for(var i=0;i<json.length;i++){
          if(json[i].directory){
            json[i].type='folder';
          }else{
            json[i].type='file';
          }
          json[i].isOpen=false;
          json[i].children=[];
          children=children.concat([json[i].path]);
          tmpNodes[json[i].path]=json[i];
        }
        let nodes = {...this.state.nodes,...tmpNodes};
        nodes[path].children=children;
        this.setState({ nodes });
      })
      .catch(error => {
        console.log(error);
      });
  };


  downloadFile = (event) => {
    let path=event.currentTarget.dataset.selectedfile;
    let url='/rest/download?fileName='+path;
    fetch(url)
      .then(response => {
        const filename =  response.headers.get('Content-Disposition').split('filename=')[1];
        response.blob().then(blob => {
          let url = window.URL.createObjectURL(blob);
          let a = document.createElement('a');
          a.href = url;
          a.download = filename;
          a.click();
      });
     });
  }

  uploadFile = (file,path) => {
    console.log("Uploading file..");
    var formData  = new FormData();
    formData.append('file', file);
    let fileName=file.name;
    let url="/rest/upload?path="+path+"/"+encodeURIComponent(fileName);
    fetch(url, {
      method: 'POST',
      body: formData
    })
    .then((response) => response.json())
    .then( (response) => {
       console.log(response);
       let tmpPath=path.substring(0,path.lastIndexOf("/"));
       this.fetchContents(tmpPath);
       alert("File uploaded successfully..");
    })
    .catch((error) => {
        console.log(error);
    });
  };

  render() {
    const { selectedFile } = this.state;

    return (
      <StyledFileExplorer>
        <TreeWrapper>
          <Tree onSelect={this.onSelect} nodes={this.state.nodes} fetchContents={this.fetchContents}/>
        </TreeWrapper>
        <div>
          <FileInfo selectedFile={selectedFile} downloadFile={this.downloadFile} uploadFile={this.uploadFile}/>
        </div>
      </StyledFileExplorer>
    )
  }
}
