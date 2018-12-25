import React, { Component } from 'react';
import values from 'lodash/values';
import PropTypes from 'prop-types';

import TreeNode from './TreeNode';


export default class Tree extends Component {
  constructor(props){
    super(props);
    this.state={nodes:props.nodes};
    //console.log("Tree nodes");
    //console.log(this.state);
  }

  getRootNodes = () => {
    const { nodes } = this.props;
    return values(nodes).filter(node => node.isRoot === true);
  }

  getChildNodes = (node) => {
    const { nodes } = this.props;
    if (!node.children) return [];
    //console.log("Nodes in tree");
    //console.log(nodes);
    return node.children.map(path => nodes[path]);
  }

  onToggle = (node) => {
    const { nodes } = this.props;
    nodes[node.path].isOpen = !node.isOpen;
    this.setState({ nodes });
    if(nodes[node.path].isOpen){
      this.props.fetchContents(node.path);
    }
  }

  onNodeSelect = node => {
    const { onSelect } = this.props;
    onSelect(node);
  }

  render() {
    const rootNodes = this.getRootNodes();
    //console.log("Root Nodes");
    //console.log(rootNodes);
    return (
      <div>
        { rootNodes.map((node,index) => (
          <TreeNode
            key={index}
            node={node}
            getChildNodes={this.getChildNodes}
            onToggle={this.onToggle}
            onNodeSelect={this.onNodeSelect}
          />
        ))}
      </div>
    )
  }
}

Tree.propTypes = {
  onSelect: PropTypes.func.isRequired,
};
