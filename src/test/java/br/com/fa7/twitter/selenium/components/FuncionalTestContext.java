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
	
	public static void inititialize() {
		System.out.println("Invocação do contexto de teste. Em uso:" + inUseCount);
		if (inUseCount == 0){
			System.out.println("iniciando contexto de teste");
			inUseCount++;
			startServer();
			startDriver();
		}
	}
	
	public static void done() {
		System.out.println("Conclusão do contexto de teste. Em uso:" + inUseCount );
		if (--inUseCount == 0) {
			System.out.println("encerrando contexto de teste");
			shutDownDriver();
			shutDownServer();
		}
	}
	
	public static void setDriverAsDefault(){
		driverRegister.setDriverAsDefault();
	}

}
