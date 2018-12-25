import React from 'react';
import FileUploadForm from './form';

const FileInfo = (props) => {
  const {selectedFile,downloadFile,uploadFile}=props;
  return (
    <div>
      <table>
      <tbody>
      <tr>
        <th>Property</th>
        <th>Value</th>
      </tr>
      <tr>
        <td>Name</td>
        <td>{selectedFile !=null && selectedFile.name}</td>
      </tr>
      <tr>
        <td>Path</td>
        <td>{selectedFile !=null && selectedFile.path}</td>
      </tr>
      <tr>
        <td>Size</td>
        <td>{selectedFile !=null && selectedFile.size}</td>
      </tr>

      <tr>
        <td>Created</td>
        <td>{selectedFile !=null && selectedFile.createdDate}</td>
      </tr>
      <tr>
        <td>Modified</td>
        <td>{selectedFile !=null && selectedFile.modifiedDate}</td>
      </tr>
      <tr>
        <td>Tags</td>
        <td>{selectedFile !=null && selectedFile.tags}</td>
      </tr>
      <tr>
        {selectedFile !=null && selectedFile.type==='file' && <td colSpan='2'><button type="button" onClick={downloadFile} data-selectedfile={selectedFile.path}>Download</button></td>}
        {selectedFile !=null && selectedFile.type==='folder' && <td colSpan='2'>
         <FileUploadForm uploadFile={uploadFile} path={selectedFile.path}/>
        </td>}
      </tr>
      </tbody>
      </table>
    </div>
  );
};

export default FileInfo;
