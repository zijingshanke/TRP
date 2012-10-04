package com.fdays.tsms.airticket.util.test;


import java.util.List;

import sun.misc.Timer;


import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.airticket.TempPassenger;
import com.fdays.tsms.airticket.util.ParseBlackUtil;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestParseBlackUtil extends TestCase {
	protected String myString;
	
	public void setUp(){
		myString="123";
	}
	public void testMyString(){
		assertEquals(myString,"123");
	}
	
	public TestParseBlackUtil(String method) {
		super(method);
	}
	public  void testPassenger() {
		String sampleTxt = "E:\\project\\tsms\\doc\\PNRSample\\BlackSample2.txt";
//		sampleTxt = "E:\\project\\tsms\\doc\\PNRSample\\JRE457.txt";		

		TempPNR tempPnr = ParseBlackUtil.getTempPNRByBlack(sampleTxt, ParseBlackUtil.Type_Path);
//		ParseBlackUtil.printTempPNRInfo(tempPnr);
		
		//测试运行时间，可选
		//是否需要引入JunitPerf,测试辅助工具，提供对单个单元测试进行计时，模拟高负载情况等。
//		Timer timer=new Timer();
		
//		timer.start();
		List<TempPassenger> tempPassengerList=tempPnr.getTempPassengerList();
		
//		timer.stop();
//		assertTrue(timer.elapsedTime()<2.0);
		
		
		assertEquals(2, tempPassengerList.size());
		assertEquals("刘廷会", tempPassengerList.get(0).getName());
		assertEquals("杨诗宇", tempPassengerList.get(1).getName());
	}
	
	//指定需要测试的方法，若无，默认测试所有
	public static Test suite(){
		TestSuite suite=new TestSuite();
		suite.addTest(new TestParseBlackUtil("testPassenger"));
		suite.addTest(new TestParseBlackUtil("testMyString"));
		
		//加入其它测试类的方法
		suite.addTestSuite(TestParseTeamBlackUtil.class);
		suite.addTest(TestParseTeamBlackUtil.suite());
		return suite;
		
	}
}
