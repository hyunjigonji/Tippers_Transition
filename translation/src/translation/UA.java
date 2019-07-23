package translation;

public class UA {
	public int Entity;
	public int Property;
	public int Condition;
	
	public UA() { }
	
	public UA(int newEntity, int newProperty) {
		Entity = newEntity;
		Property = newProperty;
	}
	
	public UA(int newEntity, int newProperty, int newCondition) {
		Entity = newEntity;
		Property = newProperty;
		Condition = newCondition;
	}
}
