import React from 'react'

class FileUploadForm extends React.Component {

  constructor(props) {
    super(props);
    this.state ={
      file:null
    }
    this.onFormSubmit = this.onFormSubmit.bind(this)
    this.onChange = this.onChange.bind(this)
  }
  onFormSubmit(e){
    e.preventDefault() // Stop form submit
    this.props.uploadFile(this.state.file,this.props.path);
  }
  onChange(e) {
    this.setState({file:e.target.files[0]})
  }

  render() {
    return (
      <form onSubmit={this.onFormSubmit}>
        <input type="file" onChange={this.onChange} />
        <button type="submit">Upload</button>
      </form>
   )
  }
}
export default FileUploadForm;
