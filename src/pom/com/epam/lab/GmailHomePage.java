package com.epam.lab;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GmailHomePage {
	private WebDriver driver;
	private GmailMessageBlock gmailMessageBlock;
	private final String VALUE_ATTACHED_TO_THE_FIELD = "Value '%s' attached to the field %s";
	private static final Logger logger = LogManager.getLogger(GmailHomePage.class);
	@FindBy(xpath = "//div[contains(@class, 'T-I') and contains(@class ,'J-J5-Ji') and contains(@class ,'T-I-KE') and contains(@class ,'L3')]")
	private WebElement composeButton;
	@FindBy(xpath = "//div[contains(@class, 'nH') and contains(@class ,'Hd')]")
	private WebElement composeTable;
	@FindBy(css = "*[href='https://mail.google.com/mail/u/0/#drafts'")
	private WebElement draftButton;
	@FindBy(xpath = "//*[@role='main']//tr[contains(@class, 'zA') and contains(@class ,'yO')][1]")
	private WebElement lastMessage;
	@FindBy(xpath = "//div[contains(@class, 'nH') and contains(@class ,'Hd')]")
	private WebElement messageBlock;
	@FindBy(id = "link_vsm")
	private WebElement viewSentMessage;

	public GmailHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		gmailMessageBlock = new GmailMessageBlock(driver);
	}

	public void composeClick() {
		composeButton.click();
		logger.info("Compose button clicked");
	}

	public void draftClick() {
		draftButton.click();
		logger.info("Draft tab opened");
	}

	public void lastMessageClick(String draftLettersURL, int timeOut) {
		isURLCorrect(draftLettersURL, timeOut);
		lastMessage.click();
		logger.info("Last message selected");
	}

	public void typeReceiver(String receiver) {
		gmailMessageBlock.typeReceiver(receiver);
		logger.info(String.format(VALUE_ATTACHED_TO_THE_FIELD, receiver, "receiver"));
	}

	public void typeCopyReceiver(String receiver) {
		gmailMessageBlock.typeCopyReceiver(receiver);
		logger.info(String.format(VALUE_ATTACHED_TO_THE_FIELD, receiver, "cc"));
	}

	public void typeHiddenCopyReceiver(String receiver) {
		gmailMessageBlock.typeHiddenCopyReceiver(receiver);
		logger.info(String.format(VALUE_ATTACHED_TO_THE_FIELD, receiver, "bcc"));
	}

	public void typeSubject(String subject) {
		gmailMessageBlock.typeSubject(subject);
		logger.info(String.format(VALUE_ATTACHED_TO_THE_FIELD, subject, "subject"));
	}

	public void typeMessage(String message) {
		gmailMessageBlock.typeMessage(message);
		logger.info(String.format(VALUE_ATTACHED_TO_THE_FIELD, message, "message"));
	}

	public boolean checkComposeFields(String receiver, String cc, String bcc, String subject, String message) {
		return gmailMessageBlock.checkComposeFields(receiver, cc, bcc, subject, message);
	}

	public void saveAndClose() {
		gmailMessageBlock.saveAndClose();
	}

	public void clickSendButton() {
		gmailMessageBlock.clickSendButton();
		logger.info("Sending message");
	}

	public boolean isMessageBlockPresent(int timeOut) {
		boolean isBlockClosed = false;
		final String MESSAGE_BLOCK = "Message block %s";
		try {
			(new WebDriverWait(driver, timeOut)).until(ExpectedConditions.visibilityOf(messageBlock));
		} catch (Exception ex) {
			isBlockClosed = true;
		}
		if (!isBlockClosed) {
			logger.info(String.format(MESSAGE_BLOCK, "opened"));
		} else {
			logger.info(String.format(MESSAGE_BLOCK, "closed"));
		}
		return !isBlockClosed;
	}

	public boolean isURLCorrect(String expectedURL, int timeOut) {
		boolean isComparisionFailed = false;
		try {
			(new WebDriverWait(driver, timeOut)).until((dr) -> dr.getCurrentUrl().equals(expectedURL));
		} catch (Exception ex) {
			logger.error("URLs not matching!");
			isComparisionFailed = true;
		}
		return !isComparisionFailed;
	}

	public boolean isMessageSent(int timeOut) {
		boolean isFailed = false;
		try {
			(new WebDriverWait(driver, timeOut)).until(ExpectedConditions.visibilityOf(viewSentMessage));
		} catch (Exception ex) {
			logger.error("Message sending failed!");
			isFailed = true;
		}
		return !isFailed;
	}
}
