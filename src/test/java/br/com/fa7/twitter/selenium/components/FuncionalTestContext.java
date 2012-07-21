package br.com.fa7.twitter.selenium.components;

import org.openqa.selenium.WebDriver;

import br.com.fa7.twitter.Jetty;

public class FuncionalTestContext {

	private static DriverRegister driverRegister = new DriverRegister();
	private static boolean isServerStarted = false;
	private static boolean isDriverStarted = false;
	private static int inUseCount = 0;

	private static void startServer() {
		if (!isServerStarted) {
			Jetty.start();
			isServerStarted = true;
		}
	}

	private static void shutDownServer() {
		if (isServerStarted) {
			Jetty.stop();
			isServerStarted = false;
		}
	}
	
	private static void startDriver() {
		driverRegister.registerNew();
		isDriverStarted = true;
	}
	
	private static void shutDownDriver() {
		driverRegister.getDriver().close();
		isDriverStarted = false;
	}

	public static WebDriver getDriver() {
		if (!isDriverStarted) return null; 
		return driverRegister.getDriver();
	}
	
	public static void setDriverAsDefault(){
		driverRegister.setDriverAsDefault();
	}
	
	public static void inititialize() {
		if (inUseCount == 0){
			startServer();
			startDriver();
		}
		inUseCount++;
	}
	
	public static void done() {
		if (--inUseCount == 0) {
			shutDownDriver();
			shutDownServer();
		}
	}
}
