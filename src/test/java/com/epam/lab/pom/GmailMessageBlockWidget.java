package com.epam.lab.pom;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.epam.lab.pageElements.Button;
import com.epam.lab.pageElements.CustomFieldDecorator;
import com.epam.lab.pageElements.HiddenInput;
import com.epam.lab.pageElements.TextInput;


public class GmailMessageBlockWidget {
	private WebDriver driver;
	@FindBy(css = "*[name='to']")
	private TextInput receiverInputField;
	@FindBy(xpath = "//span[contains(@class, 'aB') and contains(@class ,'gQ') and contains(@class ,'pE')]")
	private Button copyReceiverButton;
	@FindBy(css = "*[name='cc']")
	private TextInput copyReceiverInputField;
	@FindBy(xpath = "//span[contains(@class, 'aB') and contains(@class ,'gQ') and contains(@class ,'pB')]")
	private Button hiddenCopyReceiverButton;
	@FindBy(css = "*[name='bcc']")
	private TextInput hiddenCopyReceiverInputField;
	@FindBy(css = "*[name='subjectbox']")
	private TextInput subjectInputField;
	@FindBy(xpath = "//*[@role='textbox']")
	private TextInput messageInput;
	@FindBy(xpath = "//img[@class='Ha']")
	private Button saveAndCloseButton;
	@FindBy(xpath = "//input[@name = 'to']")
	private HiddenInput receiverInput;
	@FindBy(xpath = "//input[@name = 'cc']")
	private HiddenInput ccInput;
	@FindBy(xpath = "//input[@name = 'bcc']")
	private HiddenInput bccInput;
	@FindBy(xpath = "//input[@name = 'subject']")
	private HiddenInput subjectInput;

	public GmailMessageBlockWidget(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new CustomFieldDecorator(driver), this);
	}

	public void typeReceiver(String receiver) {
		receiverInputField.type(receiver);
	}

	public void typeCopyReceiver(String receiver, int timeOut) {
		copyReceiverButton.click(driver, timeOut);
		copyReceiverInputField.type(receiver);
	}

	public void typeHiddenCopyReceiver(String receiver, int timeOut) {
		hiddenCopyReceiverButton.click(driver, timeOut);
		hiddenCopyReceiverInputField.type(receiver);
	}

	public void typeSubject(String subject) {
		subjectInputField.type(subject);
	}

	public void typeMessage(String message) {
		messageInput.type(message);
	}

	public boolean checkComposeFields(String receiver, String cc, String bcc, String subject, String message) {
		return receiverInput.getValue().equals(receiver) || ccInput.getValue().equals(cc)
				|| bccInput.getValue().equals("bcc") || subjectInput.getValue().equals(subject)
				|| messageInput.getText().equals(message) ? true : false;
	}

	public void saveAndClose(int timeOut) {
		saveAndCloseButton.click(driver, timeOut);
	}

	public void clickSendButton() {
		String keysPressed = Keys.chord(Keys.CONTROL, Keys.RETURN);
		messageInput.type(keysPressed);
	}
}
