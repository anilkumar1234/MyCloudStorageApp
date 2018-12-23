'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import styled from '@emotion/styled';

import TreeNode from './node';
import defaultDecorators from './decorators';
import defaultTheme from '../themes/default';
import defaultAnimations from '../themes/animations';

import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as actionCreators from '../actions';

const Ul = styled('ul', {
    shouldForwardProp: prop => ['className', 'children'].indexOf(prop) !== -1
})((({style}) => style));

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


class TreeBeard extends React.Component {
  constructor(props){
    super(props);
    console.log("Beard");
    console.log(props);
  }

    render() {
      //console.log(this.props);
        const {animations, decorators, data: propsData, onToggle, style,downloadFile,listFiles} = this.props;
        let data = propsData;
        // Support Multiple Root Nodes. Its not formally a tree, but its a use-case.
        if (!Array.isArray(data)) {
            data = [data];
        }
        return (
            <Ul style={style.tree.base}
                ref={ref => this.treeBaseRef = ref}>
                {data.map(function(node, index){
                    return (<TreeNode animations={animations}
                              decorators={decorators}
                              key={node.id || index}
                              style={style.tree.node}
                              node={node}
                              onToggle={onToggle} downloadFile={this.props.downloadFile} listFile={this.props.listFile}
                              />);
                    }.bind(this))
                }
            </Ul>
        );
    }
}

TreeBeard.propTypes = {
    style: PropTypes.object,
    data: PropTypes.oneOfType([
        PropTypes.object,
        PropTypes.array
    ]).isRequired,
    animations: PropTypes.oneOfType([
        PropTypes.object,
        PropTypes.bool
    ]),
    onToggle: PropTypes.func,
    decorators: PropTypes.object,
    downloadFile:PropTypes.func,
    listFiles:PropTypes.func
};

TreeBeard.defaultProps = {
    style: defaultTheme,
    animations: defaultAnimations,
    decorators: defaultDecorators
};

export default connect(mapStateToProps,mapDispatchToProps)(TreeBeard);
