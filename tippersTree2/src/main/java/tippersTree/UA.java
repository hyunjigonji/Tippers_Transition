package tippersTree;

public class UA {
	public String Entity;
	public String Property;
	public String Condition;
	
	public UA() { }
	
	public UA(String newEntity, String newProperty) {
		Entity = newEntity;
		Property = newProperty;
	}
	
	public UA(String newEntity, String newProperty, String newCondition) {
		Entity = newEntity;
		Property = newProperty;
		Condition = newCondition;
	}
}
