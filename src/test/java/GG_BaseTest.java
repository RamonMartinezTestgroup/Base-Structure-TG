package test.java;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Collections;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
import main.java.utils.CC_Parametros;
import main.java.utils.GG_Utils;
import test.java.carritocompras.CC_Test;

public class GG_BaseTest {

	public static WebDriver driver;
	public ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest logger;

	@BeforeTest
	public void beforeTestMethod() {
		// Se inicializa el reporte

		htmlReporter = new ExtentHtmlReporter(CC_Parametros.gloDir + File.separator + "reporte" + File.separator
				+ "ReporteAutomatizacion_" + GG_Utils.getDateTime() + ".html");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle("Reporte Automatizaci�n");
		htmlReporter.config().setReportName("Resultado Pruebas Automatizadas");
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Tester Automatizador", CC_Parametros.nombreAutomatizador);
		extent.setSystemInfo("Projecto", CC_Parametros.nombreProyecto);
	}

	@BeforeMethod
	@Parameters(value = { "browserName" })
	public void beforeMethodMethod(String browserName, Method testMethod) {
		// Chromedriver
		logger = extent.createTest(testMethod.getName());
		setUpDriver(browserName);
		driver.manage().window().maximize();
		driver.get(CC_Parametros.url);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
	}

//	@AfterMethod
//	public void afterMethodScreenShot(ITestResult result) throws IOException {
//		String methodName = result.getMethod().getMethodName();
//		String screnshotPath = GG_Utils.takeMethodScreenShot(methodName, result);
//
//		logger.assignAuthor(CC_Parametros.nombreAutomatizador);
//		String logText = "Test Case: " + methodName + " Pasa-OK";
//		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
//		logger.log(Status.PASS, m);
//		logger.addScreenCaptureFromPath(screnshotPath, methodName);
//	}

	@AfterMethod
	public void afterMethodMethod(ITestResult result) throws IOException {
		// Etapas de los resultados.
		String methodName = result.getMethod().getMethodName();
		String screnshotPath = GG_Utils.takeScreenshot(methodName, result);

		if (result.getStatus() == ITestResult.SUCCESS) {
			logger.assignAuthor(CC_Parametros.nombreAutomatizador);
			String logText = "Test Case: " + methodName + " Pasa-OK";
			Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
			logger.log(Status.PASS, m);
			logger.addScreenCaptureFromPath(screnshotPath, methodName);
		} else if (result.getStatus() == ITestResult.FAILURE) {
			logger.assignAuthor(CC_Parametros.nombreAutomatizador);
			String logText = "Test Case: " + methodName + " Falla";
			Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
			logger.log(Status.FAIL, m);
			logger.addScreenCaptureFromPath(screnshotPath, methodName);
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.assignAuthor(CC_Parametros.nombreAutomatizador);
			String logText = "Test Case: " + methodName + " Saltado";
			Markup m = MarkupHelper.createLabel(logText, ExtentColor.AMBER);
			logger.log(Status.SKIP, m);
			logger.addScreenCaptureFromPath(screnshotPath, methodName);
		}

		driver.quit();
	}

	@AfterTest
	public void afterTestMethod() {
		extent.flush();
	}

	public void setUpDriver(String browserName) {
		// Para Chrome
		if (browserName.equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();

			// Skip captcha
			ChromeOptions options = new ChromeOptions();

			options.addArguments("--headless", "--remote-allow-origins=*", "start-maximized");
			/*
			 * "--window-size=1920,1200","--disable-gpu", "--ignore-certificate-errors",
			 * "--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage"
			 */

			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			options.setExperimentalOption("useAutomationExtension", false);
			options.addArguments("--incognito", "--disable-blink-features=AutomationControlled");

			options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			if (CC_Test.gloVerFlujo.equals("S")) { // Ver el Flujo en el Browser
				driver = new ChromeDriver();
			} else {
				driver = new ChromeDriver(options); // el argumento options es para que se ejecute en background
			}

			driver.manage().window().setSize(new Dimension(1920, 1080));
			// Skip captcha

		} else if (browserName.equalsIgnoreCase("edge")) {
			// Para Edge
			WebDriverManager.edgedriver().setup();

			// Skip captcha
			EdgeOptions options = new EdgeOptions();

			options.addArguments("--headless", "--remote-allow-origins=*", "start-maximized");

			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			options.setExperimentalOption("useAutomationExtension", false);
			options.addArguments("--incognito", "--disable-blink-features=AutomationControlled");

			options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			if (CC_Test.gloVerFlujo.equals("S")) { // Ver el Flujo en el Browser
				driver = new EdgeDriver();
			} else {
				driver = new EdgeDriver(options); // el argumento options es para que se ejecute en background
			}

			driver.manage().window().setSize(new Dimension(1920, 1080));
			// Skip captcha

		} else if (browserName.equalsIgnoreCase("FireFox")) {
			// Para FireFox

			System.out.println("Estamos trabajando activar la ejecución de las pruebas de automatización "
					+ "en FireFox. Atento a las próximas actualizaciones.");

		} else {
			System.out.println("You must have declare a web browser in testng.xml source window. Go to 'testng.xml' "
					+ "sheet and look at the bottom left corner of the editor sheet. Select 'source', "
					+ "and look for the test section. Within this section, in 'parameter' you must enter two values: "
					+ "name=\"browserName\" value=\"The browser you want to use.\"");

		}
	}
}
