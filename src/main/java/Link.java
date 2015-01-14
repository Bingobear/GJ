
public class Link {
private int source;
private int destination;
private double value;
public Link(int counter, int counterG, double value) {
	this.source = counter;
	this.destination = counterG;
}
public int getSource() {
	return source;
}
public void setSource(int source) {
	this.source = source;
}
public int getDestination() {
	return destination;
}
public void setDestination(int destination) {
	this.destination = destination;
}
public double getValue() {
	return value;
}
public void setValue(double value) {
	this.value = value;
}
}
