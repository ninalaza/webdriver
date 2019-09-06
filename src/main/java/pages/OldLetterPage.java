package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class OldLetterPage extends AbstractPage {

    public OldLetterPage
            (WebDriver driver) {
        super(driver);
    }

    @FindBy (xpath = "//div[@title='lazarnina@bk.ru']/child::span")
    WebElement mailAdressInOldLetter;

    @FindBy (name = "Subject")
    WebElement mailSubject;

    @FindBy (xpath = "//div[contains(@class, 'editable-container')]")
    WebElement mailEditor;

    @FindBy (xpath = "//span[text()='Отправить']")
    WebElement sendButton;

    @FindBy(xpath = "//span[@title='Закрыть']")
    WebElement closeButtonX;

    public String getMailAdressInOldLetter(){
        waitForElementVisible(mailAdressInOldLetter);
        return mailAdressInOldLetter.getText();
    }

    public String getMailSubject(){
        return mailSubject.getAttribute("value");
    }

    public boolean isTextBodyExistsInMessage(String mailBody) {
        waitForElementVisible(mailEditor);
        String xpath = String.format(".//div[contains(text(), '%s')]", mailBody);
        List texts = mailEditor.findElements(By.xpath(xpath));

        return texts.size() > 0;
    }

    public  void sendMail(){
        waitUntilElementClickable(sendButton);
        sendButton.click();
    }

    public void closeAlertSendSuccessfull(){
        waitForElementVisible(closeButtonX);
        closeButtonX.click();
    }
}
