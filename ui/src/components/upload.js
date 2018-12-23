import React, { Component } from 'react';

class UploadButton extends Component{
  render(){
    return (
      <div id="uploadBtn" >
        <input type="file" className="file btn btn-primary btn-file"/>
      </div>
    );
  }
}
export default UploadButton;
