package com.fdays.tsms.airticket.util.test;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestParseTeamBlackUtil extends TestCase {
	public TestParseTeamBlackUtil(String method) {
		super(method);
	}
	public  void testPassenger() {
		assertEquals(true, 1==1);
	}
	
	public static Test suite(){
		TestSuite suite=new TestSuite();
		suite.addTest(new TestParseTeamBlackUtil("testPassenger"));
		
		
		return suite;
		
	}
}
