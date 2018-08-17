package com.stevencisneros.inputandanalysisapi;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.stevencisneros.inputandanalysisapi.FileParser.FileAnalysis;



public class FileParserJunit {

	FileAnalysis analysis;
	String filePath = "/Users/steve-0/Desktop/Code42/test/example.txt"; // Use your own path here.
	
	@Before
	public void setUp()
	{
	 analysis = FileParser.parse(filePath);	
	}
	
	@Test
	public void parseTest()
	{
		assertNotNull(analysis);
	}
	
	@Test
	public void getCountOfNumbersTest()
	{
		assertEquals(analysis.getCountOfNumbers(),2);
	}
	
	@Test
	public void containsTest()
	{
		assertTrue(analysis.contains("foo"));
	}

}
