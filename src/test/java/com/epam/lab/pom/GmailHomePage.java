package com.epam.lab.pom;

import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.lab.pageElements.Button;
import com.epam.lab.pageElements.CustomFieldDecorator;


public class GmailHomePage {
	private WebDriver driver;
	private GmailMessageBlockWidget gmailMessageBlockWidget;
	private final String MESSAGE_BLOCK_XPATH = "//div[contains(@class, 'nH') and contains(@class ,'Hd')]";
	private final String VALUE_ATTACHED_TO_THE_FIELD = "Value '%s' attached to the field %s";
	private static final Logger logger = LogManager.getLogger(GmailHomePage.class);
	@FindBy(xpath = "//div[contains(@class, 'T-I') and contains(@class ,'J-J5-Ji') and contains(@class ,'T-I-KE') and contains(@class ,'L3')]")
	private Button composeButton;
	@FindBy(xpath = "//div[contains(@class, 'nH') and contains(@class ,'Hd')]")
	private WebElement composeTable;
	@FindBy(css = "*[href='https://mail.google.com/mail/u/0/#drafts'")
	private Button draftButton;
	@FindBy(xpath = "//*[@role='main']//tr[contains(@class, 'zA') and contains(@class ,'yO')][1]")
	private WebElement lastMessage;
	@FindBy(xpath = MESSAGE_BLOCK_XPATH)
	private WebElement messageBlock;
	@FindBy(id = "link_vsm")
	private WebElement viewSentMessage;

	public GmailHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new CustomFieldDecorator(driver), this);
		gmailMessageBlockWidget = new GmailMessageBlockWidget(driver);
	}

	public void composeClick(int timeOut) {
		composeButton.click(driver, timeOut);
		logger.info("Compose button clicked");
	}

	public void draftClick(int timeOut) {
		draftButton.click(driver, timeOut);
		logger.info("Draft tab opened");
	}

	public void lastMessageClick(String draftLettersURL, int timeOut) {
		isURLCorrect(draftLettersURL, timeOut);
		lastMessage.click();
		logger.info("Last message selected");
	}

	public void typeReceiver(String receiver) {
		gmailMessageBlockWidget.typeReceiver(receiver);
		logger.info(String.format(VALUE_ATTACHED_TO_THE_FIELD, receiver, "receiver"));
	}

	public void typeCopyReceiver(String receiver, int timeOut) {
		gmailMessageBlockWidget.typeCopyReceiver(receiver, timeOut);
		logger.info(String.format(VALUE_ATTACHED_TO_THE_FIELD, receiver, "cc"));
	}

	public void typeHiddenCopyReceiver(String receiver, int timeOut) {
		gmailMessageBlockWidget.typeHiddenCopyReceiver(receiver, timeOut);
		logger.info(String.format(VALUE_ATTACHED_TO_THE_FIELD, receiver, "bcc"));
	}

	public void typeSubject(String subject) {
		gmailMessageBlockWidget.typeSubject(subject);
		logger.info(String.format(VALUE_ATTACHED_TO_THE_FIELD, subject, "subject"));
	}

	public void typeMessage(String message) {
		gmailMessageBlockWidget.typeMessage(message);
		logger.info(String.format(VALUE_ATTACHED_TO_THE_FIELD, message, "message"));
	}

	public boolean checkComposeFields(String receiver, String cc, String bcc, String subject, String message) {
		return gmailMessageBlockWidget.checkComposeFields(receiver, cc, bcc, subject, message);
	}

	public void saveAndClose(int timeOut) {
		gmailMessageBlockWidget.saveAndClose(timeOut);
	}

	public void clickSendButton() {
		gmailMessageBlockWidget.clickSendButton();
		logger.info("Sending message");
	}

	public boolean isMessageBlockPresent(int timeOut, boolean isOpened) {
		boolean isBlockClosed = isOpened;
		final String MESSAGE_BLOCK = "Message block %s";
		try {
			if (isOpened) {
				(new WebDriverWait(driver, timeOut)).until(new Function<WebDriver, Boolean>() {
					public Boolean apply(WebDriver driver) {
						boolean isPresent = true;
						try {
							driver.findElement(By.xpath(MESSAGE_BLOCK_XPATH));
						} catch (Exception ex) {
							isPresent = false;
						}
						return isPresent;
					}
				});
			} else {
				(new WebDriverWait(driver, timeOut)).until(ExpectedConditions.visibilityOf(messageBlock));
			}
		} catch (Exception ex) {
			isBlockClosed = !isBlockClosed;
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
