package com.epam.lab;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GmailMessageBlock {
	@FindBy(css = "*[name='to']")
	private WebElement receiverInputField;
	@FindBy(xpath = "//span[contains(@class, 'aB') and contains(@class ,'gQ') and contains(@class ,'pE')]")
	private WebElement copyReceiverButton;
	@FindBy(css = "*[name='cc']")
	private WebElement copyReceiverInput;
	@FindBy(xpath = "//span[contains(@class, 'aB') and contains(@class ,'gQ') and contains(@class ,'pB')]")
	private WebElement hiddenCopyReceiverButton;
	@FindBy(css = "*[name='bcc']")
	private WebElement hiddenCopyReceiverInput;
	@FindBy(css = "*[name='subjectbox']")
	private WebElement subjectInputField;
	@FindBy(xpath = "//*[@role='textbox']")
	private WebElement messageInput;
	@FindBy(xpath = "//img[@class='Ha']")
	private WebElement saveAndCloseButton;
	@FindBy(xpath = "//input[@name = 'to']")
	private WebElement receiverInput;
	@FindBy(xpath = "//input[@name = 'cc']")
	private WebElement ccInput;
	@FindBy(xpath = "//input[@name = 'bcc']")
	private WebElement bccInput;
	@FindBy(xpath = "//input[@name = 'subject']")
	private WebElement subjectInput;

	public GmailMessageBlock(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void typeReceiver(String receiver) {
		receiverInputField.sendKeys(receiver);
	}

	public void typeCopyReceiver(String receiver) {
		copyReceiverButton.click();
		copyReceiverInput.sendKeys(receiver);
	}

	public void typeHiddenCopyReceiver(String receiver) {
		hiddenCopyReceiverButton.click();
		hiddenCopyReceiverInput.sendKeys(receiver);
	}

	public void typeSubject(String subject) {
		subjectInputField.sendKeys(subject);
	}

	public void typeMessage(String message) {
		messageInput.sendKeys(message);
	}

	public boolean checkComposeFields(String receiver, String cc, String bcc, String subject, String message) {
		String valueAttribute = "value";
		return receiverInput.getAttribute(valueAttribute).equals(receiver)
				|| ccInput.getAttribute(valueAttribute).equals(cc)
				|| bccInput.getAttribute(valueAttribute).equals("bcc")
				|| subjectInput.getAttribute(valueAttribute).equals(subject) || messageInput.getText().equals(message)
						? true : false;
	}

	public void saveAndClose() {
		saveAndCloseButton.click();
	}

	public void clickSendButton() {
		String keysPressed = Keys.chord(Keys.CONTROL, Keys.RETURN);
		messageInput.sendKeys(keysPressed);
	}
}
