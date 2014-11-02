package jabs.nationalflags;

public class Country {
	String name;
	int[] flagColors;
	
	public Country(String name, int flagColor1, int flagColor2, int flagColor3) {
		this.name = name;
		this.flagColors = new int[] { flagColor1, flagColor2, flagColor3};
	}
	public String getName() {
		return this.name;
	}
	public int[] getFlagColors() {
		return this.flagColors;
	}
}
