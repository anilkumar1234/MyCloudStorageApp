'use strict';

import React from 'react';
import PropTypes from 'prop-types';
import {VelocityTransitionGroup} from 'velocity-react';
import styled from '@emotion/styled';

import NodeHeader from './header';

import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as actionCreators from '../actions';

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


const Li = styled('li', {
    shouldForwardProp: prop => ['className', 'children', 'ref'].indexOf(prop) !== -1
})(({style}) => style);
const Ul = styled('ul', {
    shouldForwardProp: prop => ['className', 'children', 'ref'].indexOf(prop) !== -1
})(({style}) => style);

class TreeNode extends React.Component {
    constructor(props) {
        super(props);
        console.log("Node");
        console.log(props);
        this.onClick = this.onClick.bind(this);
        //console.log("Node");
    }

    onClick() {
      //console.log("Node");
      //console.log(this.props);

        const {node, onToggle} = this.props;
        const {toggled} = node;

        if (onToggle) {
            onToggle(node, !toggled);
        }
        //console.log(node);

        /*if(node.directory){
          if(node.toggled){
            listFiles(node.path,function(data){
              console.log("Data received:"+node.path);
              console.log(data);
            });
          }
        }else{
            downloadFile(node.path);
        }*/
    }

    animations() {
        const {animations, node} = this.props;

        if (animations === false) {
            return false;
        }

        const anim = Object.assign({}, animations, node.animations);
        return {
            toggle: anim.toggle(this.props),
            drawer: anim.drawer(this.props)
        };
    }

    decorators() {
        // Merge Any Node Based Decorators Into The Pack
        const {decorators, node} = this.props;
        let nodeDecorators = node.decorators || {};

        return Object.assign({}, decorators, nodeDecorators);
    }

    render() {
        const {style} = this.props;
        const decorators = this.decorators();
        const animations = this.animations();

        return (
            <Li innerRef={ref => this.topLevelRef = ref}
                style={style.base}>
                {this.renderHeader(decorators, animations)}

                {this.renderDrawer(decorators, animations)}
            </Li>
        );
    }

    renderDrawer(decorators, animations) {
        const {node: {toggled}} = this.props;

        if (!animations && !toggled) {
            return null;
        } else if (!animations && toggled) {
            return this.renderChildren(decorators, animations);
        }

        const {animation, duration, ...restAnimationInfo} = animations.drawer;
        return (
            <VelocityTransitionGroup {...restAnimationInfo}
                                     ref={ref => this.velocityRef = ref}>
                {toggled ? this.renderChildren(decorators, animations) : null}
            </VelocityTransitionGroup>
        );
    }

    renderHeader(decorators, animations) {
        const {node, style} = this.props;

        return (
            <NodeHeader animations={animations}
                        decorators={decorators}
                        node={Object.assign({}, node)}
                        onClick={this.onClick}
                        style={style}/>
        );
    }

    renderChildren(decorators) {
        const {animations, decorators: propDecorators, node, style} = this.props;

        if (node.loading) {
            return this.renderLoading(decorators);
        }

        let children = node.children;
        if (!Array.isArray(children)) {
            children = children ? [children] : [];
        }

        return (
            <Ul style={style.subtree}
                ref={ref => this.subtreeRef = ref}>
                {children.map((child, index) => <TreeNode {...this._eventBubbles()}
                                                          animations={animations}
                                                          decorators={propDecorators}
                                                          key={child.id || index}
                                                          node={child}
                                                          style={style}/>
                )}
            </Ul>
        );
    }

    renderLoading(decorators) {
        const {style} = this.props;

        return (
            <Ul style={style.subtree}>
                <li>
                    <decorators.Loading style={style.loading}/>
                </li>
            </Ul>
        );
    }

    _eventBubbles() {
        const {onToggle} = this.props;

        return {
            onToggle
        };
    }
}

TreeNode.propTypes = {
    style: PropTypes.object.isRequired,
    node: PropTypes.object.isRequired,
    decorators: PropTypes.object.isRequired,
    animations: PropTypes.oneOfType([
        PropTypes.object,
        PropTypes.bool
    ]).isRequired,
    onToggle: PropTypes.func,
    downloadFile:PropTypes.func,
    listFiles:PropTypes.func
};

export default connect(mapStateToProps,mapDispatchToProps)(TreeNode);
