package model;

import java.util.ArrayList;

public class DDDFormat {

ArrayList graph = new ArrayList();
ArrayList<Node> nodes = new ArrayList<Node>();
ArrayList<Link> links = new ArrayList<Link>();
boolean multigraph = false;
boolean directed = false;



public DDDFormat(ArrayList<Node> nodes2, ArrayList<Link> links2) {
	this.nodes = nodes2;
	this.links = links2;
}
public ArrayList<Link> getLinks() {
return links;
}
public void setLinks(ArrayList<Link> links) {
this.links = links;
}
public ArrayList<Node> getNodes() {
return nodes;
}
public void setNodes(ArrayList<Node> nodes) {
this.nodes = nodes;
}
}
