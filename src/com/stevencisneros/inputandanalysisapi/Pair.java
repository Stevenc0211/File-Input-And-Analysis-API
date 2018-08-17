package com.stevencisneros.inputandanalysisapi;


public class Pair implements Comparable {

	int count = 1; //count is 1 because when a pair is created there is one instance of it already
	String string = null;
	
	Pair(String string)
	{
		this.string = string;
	}
	
	public void updateCount() //method updates the count by 1 if the string already exists in the list
	{
		count++;
	}
	
	public int getCount () { return count;}
	public String getString() {return string;}
	
	@Override
	public String toString() {return (string+" : "+count);} //ex) foo : 2


	@Override
	public int compareTo(Object o) // takes a pair and compares it to another via lowercase alphabetical value
	{
		Pair pair = (Pair) o; // cast to a pair object.
		return pair.getString().toLowerCase().compareTo(this.string.toLowerCase());
	}
}
