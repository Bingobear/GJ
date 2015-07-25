package model;

/**Link Class - different link complexities
 * @author Simon Bruns
 *
 */
public class Link {
//private int sourceP;
//private int targetP;
private double value;
private String source;
private String target;
public Link(int counter, int counterG, double value) {
//	this.sourceP = counter;
//	this.targetP = counterG;
	//TODO DEBUG value
	this.value = 1;
}
public Link(int counter, int counterG, double value,String source,String target) {
//	this.source = counter;
//	this.target = counterG;
	this.source = source;
	this.target = target;
	//TODO DEBUG value
	this.value = 1;
}

public double getValue() {
	return value;
}
public void setValue(double value) {
	this.value = value;
}
public String getSourceP() {
	return source;
}
public void setSourceP(String sourceP) {
	this.source = sourceP;
}
public String getTragetP() {
	return target;
}
public void setTragetP(String tragetP) {
	this.target = tragetP;
}
}
