package com.epam.lab;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.lab.parsers.PropertyParser;
import com.epam.lab.parsers.XMLParser;

public class GmailSendTest {
	private final String PROPERTIES_PATH = "src/test/resources/config.properties";
	private WebDriver chromeDriver;
	private XMLParser xmlParser;
	private PropertyParser propertyParser;
	private String receiver;
	private String copyReceiver;
	private String hiddenCopyReceiver;
	private String subject;
	private String messageText;

	@BeforeClass
	public void driverSetup() {
		propertyParser = new PropertyParser(PROPERTIES_PATH);
		xmlParser = new XMLParser(propertyParser.getProperty("xmlPath"));
		System.setProperty("webdriver.chrome.driver", propertyParser.getProperty("chromeDriverPath"));
		chromeDriver = new ChromeDriver();
		chromeDriver.manage().timeouts().implicitlyWait(Integer.parseInt(propertyParser.getProperty("implicitlyWait")),
				TimeUnit.SECONDS);
		chromeDriver.get(xmlParser.getProperty("homePageURL"));
		receiver = xmlParser.getProperty("to");
		copyReceiver = xmlParser.getProperty("cc");
		hiddenCopyReceiver = xmlParser.getProperty("bcc");
		subject = xmlParser.getProperty("subject");
		messageText = xmlParser.getProperty("text");
	}

	@Test(priority = 1)
	public void loginTest() {
		GmailLoginPage gmailLoginPage = new GmailLoginPage(chromeDriver);
		gmailLoginPage.typeEmailAndSubmit(xmlParser.getProperty("email"));
		GmailHomePage gmailHomePage = gmailLoginPage.typePasswordAndSubmit(xmlParser.getProperty("password"));
		Assert.assertTrue(gmailHomePage.isURLCorrect(xmlParser.getProperty("inputMessagesPageURL"),
				Integer.parseInt(propertyParser.getProperty("pageUpdateTimeOut"))));
	}

	@Test(priority = 2)
	public void composeClickTest() {
		GmailHomePage gmailHomePage = new GmailHomePage(chromeDriver);
		gmailHomePage.composeClick();
		Assert.assertTrue(gmailHomePage
				.isMessageBlockPresent(Integer.parseInt(propertyParser.getProperty("pageElementChangeTimeOut"))));
	}

	@Test(priority = 3)
	public void fillComposeFieldsTest() {
		GmailHomePage gmailHomePage = new GmailHomePage(chromeDriver);
		gmailHomePage.typeReceiver(receiver);
		gmailHomePage.typeCopyReceiver(copyReceiver);
		gmailHomePage.typeHiddenCopyReceiver(hiddenCopyReceiver);
		gmailHomePage.typeSubject(subject);
		gmailHomePage.typeMessage(messageText);
		Assert.assertTrue(
				gmailHomePage.checkComposeFields(receiver, copyReceiver, hiddenCopyReceiver, subject, messageText));
	}

	@Test(priority = 4)
	public void closeComposeTable() {
		GmailHomePage gmailHomePage = new GmailHomePage(chromeDriver);
		gmailHomePage.saveAndClose();
		Assert.assertTrue(!gmailHomePage
				.isMessageBlockPresent(Integer.parseInt(propertyParser.getProperty("pageElementChangeTimeOut"))));
	}

	@Test(priority = 5)
	public void draftOpenTest() {
		GmailHomePage gmailHomePage = new GmailHomePage(chromeDriver);
		gmailHomePage.draftClick();
		gmailHomePage.lastMessageClick(xmlParser.getProperty("draftLettersURL"),
				Integer.parseInt(propertyParser.getProperty("pageUpdateTimeOut")));
		Assert.assertTrue(gmailHomePage
				.isMessageBlockPresent(Integer.parseInt(propertyParser.getProperty("pageElementChangeTimeOut"))));
	}

	@Test(priority = 6)
	public void draftFieldsTest() {
		GmailHomePage gmailHomePage = new GmailHomePage(chromeDriver);
		Assert.assertTrue(
				gmailHomePage.checkComposeFields(receiver, copyReceiver, hiddenCopyReceiver, subject, messageText));
	}

	@Test(priority = 7)
	public void sendButtonClickTest() {
		GmailHomePage gmailHomePage = new GmailHomePage(chromeDriver);
		gmailHomePage.clickSendButton();
		Assert.assertTrue(
				gmailHomePage.isMessageSent(Integer.parseInt(propertyParser.getProperty("pageUpdateTimeOut"))));
	}

	@AfterClass
	public void driverQuit() {
		chromeDriver.quit();
	}
}
