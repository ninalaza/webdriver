package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class UserAccountPage extends AbstractPage {

    @FindBy(xpath = "//span[@data-title-shortcut = 'N']/child::span")
    WebElement newLetterButton;

    @FindBy(xpath = "//a[@href='/drafts/']")
    WebElement draftButton;

    @FindBy(xpath = "//a[@href='/sent/']")
    WebElement sentButton;

    @FindBy(id = "PH_user-email")
    WebElement authorisationButton;

    @FindBy(xpath = "//a[@class='llc js-tooltip-direction_letter-bottom js-letter-list-item llc_normal llc_first']")
    WebElement firstLetterInFolder;

    @FindBy(xpath = "//h2[@class='thread__subject']")
    WebElement subjectInMailForSentFolder;

    @FindBy(id = "PH_logoutLink")
    WebElement logoutLink;

    @FindBy(xpath = "//div[@class='dataset__items']")
    WebElement datasetEmail;

    public UserAccountPage(WebDriver driver) {
        super(driver);
    }

    public NewLetterPage writeNewLetter() {
        waitForElementVisible(newLetterButton);
        newLetterButton.click();
        return new NewLetterPage(driver);
    }

    public String checkAutorisationData() {
        waitUntilElementClickable(authorisationButton);
        return authorisationButton.getText();
    }

    public void tapOnDraftButton() {
       waitUntilElementClickable(draftButton);
        draftButton.click();
    }

    public void tapOnSentButton() {
        waitUntilElementClickable(sentButton);
        sentButton.click();
    }

    public OldLetterPage tapOnTheFirstLetterInFolder() {
        waitUntilElementClickable(firstLetterInFolder);
        firstLetterInFolder.click();
        return new OldLetterPage(driver);
    }

    public String checkSubjectInMailForSentFolder() {
        waitUntilElementClickable(subjectInMailForSentFolder);
        return subjectInMailForSentFolder.getText();
    }

    public boolean isEmailWithSubjectExists(String mailSubject) {
        String xpath = String.format(".//span[@class='ll-sj__normal'][text()='%s']", mailSubject);
        List mails = datasetEmail.findElements(By.xpath(xpath));
        return mails.size() > 0;
    }

    public void logOutFromMailBox(){
        logoutLink.click();
    }
}

