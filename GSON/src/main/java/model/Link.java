package model;

public class Link {
private int source;
private int target;
private double value;
public Link(int counter, int counterG, double value) {
	this.source = counter;
	this.target = counterG;
	//TODO DEBUG value
	this.value = 1;
}
public int getSource() {
	return source;
}
public void setSource(int source) {
	this.source = source;
}
public int getDestination() {
	return target;
}
public void setDestination(int destination) {
	this.target = destination;
}
public double getValue() {
	return value;
}
public void setValue(double value) {
	this.value = value;
}
}
