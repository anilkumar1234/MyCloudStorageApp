import {GET_CONTENTS,DOWNLOAD} from './types';

const initialState={};

function listFiles(path,callback){
  fetch('http://localhost:1234/rest/list?path='+path)
    .then(response => response.json())
    .then(data => {
      callback(data);
    });
}

function downloadFile(url){
  window.open(url);
}

export default function(state=initialState,action){
  switch (action.type) {
    case GET_CONTENTS:
      var data=state.data;
      return{
        ...state
      };
    case DOWNLOAD:
      return {
        ...state,
      }
    default:
      return state;
  }
};
