package com.epam.lab.pom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.epam.lab.pageElements.Button;
import com.epam.lab.pageElements.CustomFieldDecorator;
import com.epam.lab.pageElements.TextInput;

public class GmailLoginPage {
	private WebDriver driver;
	private final String VALUE_ATTACHED_MESSAGE = "%s value attached";
	private static final Logger logger = LogManager.getLogger(GmailHomePage.class);
	@FindBy(xpath = "//input[@type = 'email']")
	private TextInput emailInput;
	@FindBy(xpath = "//div[@id = 'identifierNext']//span")
	private Button emailSubmit;
	@FindBy(name = "password")
	private TextInput passwordInput;
	@FindBy(css = "#passwordNext")
	private Button passwordSubmit;

	public GmailLoginPage(WebDriver driver) {
		PageFactory.initElements(new CustomFieldDecorator(driver), this);
		this.driver = driver;
	}

	public void typeEmailAndSubmit(String login, int timeOut) {
		emailInput.type(login);
		logger.info(String.format(VALUE_ATTACHED_MESSAGE, "Email"));
		emailSubmit.click(driver, timeOut);
	}

	public GmailHomePage typePasswordAndSubmit(String password, int timeOut) {
		boolean isPasswordField = true;
		boolean shouldClickWithJS = true;
		passwordInput.type(password, isPasswordField);
		logger.info(String.format(VALUE_ATTACHED_MESSAGE, "Password"));
		passwordSubmit.click(driver, timeOut, shouldClickWithJS);
		return new GmailHomePage(driver);
	}

}
