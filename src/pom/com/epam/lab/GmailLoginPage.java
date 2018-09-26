package com.epam.lab;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GmailLoginPage {
	private WebDriver driver;
	private final String VALUE_ATTACHED_MESSAGE = "%s value attached";
	private static final Logger logger = LogManager.getLogger(GmailHomePage.class);
	@FindBy(xpath = "//input[@type = 'email']")
	private WebElement emailInput;
	@FindBy(xpath = "//div[@id = 'identifierNext']//span")
	private WebElement emailSubmit;
	@FindBy(name = "password")
	private WebElement passwordInput;
	@FindBy(css = "#passwordNext")
	private WebElement passwordSubmit;

	public GmailLoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public void typeEmailAndSubmit(String login) {
		emailInput.sendKeys(login);
		logger.info(String.format(VALUE_ATTACHED_MESSAGE, "Email"));
		emailSubmit.click();
	}

	public GmailHomePage typePasswordAndSubmit(String password) {
		passwordInput.sendKeys(password);
		logger.info(String.format(VALUE_ATTACHED_MESSAGE, "Password"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", passwordSubmit);
		return new GmailHomePage(driver);
	}

}
