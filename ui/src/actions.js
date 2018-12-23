import {GET_CONTENTS,DOWNLOAD} from './types';

function downloadFile(url){
  return{
    type: DOWNLOAD,
    payload: url
  }
};
function listFiles(path){
  return{
  type:GET_CONTENTS,
  payload:path
}
};
export {downloadFile,listFiles};
