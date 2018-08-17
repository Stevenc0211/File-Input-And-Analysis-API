package com.stevencisneros.inputandanalysisapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;




public class FileParser {


	public static FileAnalysis parse (String FileName)
	{
		try {
			return new FileAnalysis(FileName); //creates an instance of FileAnalysis
		} catch (FileParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	

	
	public static class FileAnalysis
	{
		
		private static DecimalFormat df2 = new DecimalFormat(".##");
		private double sum = 0.0;
		private double average = 0.0;
		private double median = 0.0;
		private double percentage = 0.0;
		private int counterNums = 0;
		private int counterTotLines = 0;
		private String fileName;
		private ArrayList<Double> numbers = new ArrayList<Double>();
		private ArrayList<Pair> stringsList = new ArrayList<Pair>();
		
		private FileAnalysis(String fileName) throws FileParserException
		{
			this.fileName = fileName;
			parse(fileName);
		}
		
		
		public double getTotal() // sum of all the numbers that appeared in the file
		{
			return sum;
		}
		
		public int getCountOfNumbers() // count of how many numbers were read from the source file
		{
			return counterNums;
		}
		
		// return true if the file contains the provided string
		public boolean contains(String string) 
		{
			return search(0, stringsList.size()-1, string); 
		}
		
		// returns  true if the string being tested is a number
		private boolean isNumeric(String string) {
			  return string.matches("-?\\d+(\\.\\d+)?");  // match a positive or negative number, can be decimal
		}
		
		private void printInfo() 
		{ // prints the sum, avg, median of all the numbers found in the file, percentage of values that are numbers
		  
			System.out.println(df2.format(percentage)+" % of values were numbers.");
			System.out.println("Sum is: "+df2.format(sum));
			System.out.println("Average is: "+df2.format(average));
			System.out.println("median is: "+df2.format(median));
			System.out.println();
			// prints distinct Strings
			for(int i = 0; i < stringsList.size(); i++) {	
				System.out.println(stringsList.get(i).toString());;
			}
			System.out.println();
		}
		
		// Binary Search to find a string.
		// O(log n)
		private boolean search(int left, int right, String string)
		{
			string = string.trim(); //makes sure that there isn't a space after the end of the string and gets rid of it
			
			if(right >= left) {
				
				int mid = left + (right - left)/2; 

				if(stringsList.get(mid).getString().equals(string)) { // if this is the string that we're looking for return it
					return true;
				}
				
				if(stringsList.get(mid).getString().toLowerCase().compareTo(string.toLowerCase()) < 0) { // the string we are looking for is at mid is lesser than compared string
					return search(left, mid-1, string);

				}
				
				return search(mid+1, right, string);
				
			}
			return false; // the string is not in our strings list 
		}
		
		// Parse through a file line separating string and numbers.
		private void parse (String fileName) throws FileParserException
		{
			int dotIndex = fileName.indexOf('.');
			String extention  = fileName.substring(dotIndex, fileName.length());
			if(!(extention.equals(".txt")))
			{
				throw new FileParserException("file extention is: "+extention+" , file must be .txt");
			}
			Scanner fileScanner = null;
			try {
				
				fileScanner = new Scanner(new File(fileName));
				while(fileScanner.hasNextLine())
				{
					
					String line = fileScanner.nextLine();
					line=line.trim();
					String [] lineRay = line.split(" "); // splits a line into an array holding either words or a number
					counterTotLines++;
					
					if(isNumeric(lineRay[0])) // checks to see if the first thing in the array is a number 
					{
						counterNums++;
						sum+= Double.parseDouble(lineRay[0]);
						numbers.add(Double.parseDouble(lineRay[0]));
					}
					else
					{
						
						if(stringsList.size()==0) // initializes the strings list since the first thing in an array was a word 
						{
							Pair newString = new Pair(line); // creates a pair which is a string and a count of 1
							stringsList.add(newString);	 
						}
						else {
							
							for(int i = 0; i < stringsList.size(); i++) {
									
								if(stringsList.get(i).getString().equals(line)) // if the string already exists in the list increase the count by 1
								{	
									stringsList.get(i).updateCount();	
									break;
								} 
								else // new String found, create pair and add to list.
								{	
									Pair newPair = new Pair(line);
									stringsList.add(newPair);
									break;	
								}
								
							}
							
						}
						
					}
				}
				
				 average = sum/counterNums;
				Collections.sort(numbers); // sorts the list of numbers in descending order
				if(numbers.size()%2==0)
				{
					int medianIndex = numbers.size()/2;
				    median = (numbers.get(medianIndex-1) + numbers.get(medianIndex))/2;
				}
				else
				{
					int medianIdex = numbers.size()%2;
				    median = numbers.get(medianIdex);
				}
				
				percentage = ((double)counterNums/(double)counterTotLines)*100;
				Collections.sort(stringsList); // sorts the strings list by reverse alphabetical so Z -> A
				 printInfo();
				 
			} catch (FileNotFoundException e) {
				// throw FileParserException for the user to know that something went wrong in this API.
				throw new FileParserException("File: \""+fileName+"\" doesn't exist!");
			} finally{
				
				if(fileScanner != null)
				{
					fileScanner.close();
				}
			}
		}
		
		
	}
	
}
