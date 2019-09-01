package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewLetterPage extends AbstractPage {

    @FindBy(xpath = "//input[@tabindex='100']")
    WebElement fillAdressField;

    @FindBy(xpath = "//input[@tabindex='400']")
    WebElement fillSubjectField;

    @FindBy(xpath = "//div[@role = 'textbox']")
    WebElement fillBodyInNewLetterField;

    @FindBy(xpath = "//span[text()='Сохранить']")
    WebElement saveLetterButton;

    @FindBy(xpath = "//button[@title='Закрыть']")
    WebElement quitButton;

    public NewLetterPage(WebDriver driver) {
        super(driver);
    }

    public NewLetterPage fillAdressField(String query) {
        fillAdressField.sendKeys(query);
        return this;
    }

    public NewLetterPage fillSubjectField(String query) {
        fillSubjectField.sendKeys(query);
        return this;
    }

    public NewLetterPage fillBodyInNewLetterField(String query) {
        fillBodyInNewLetterField.sendKeys(query);
        return this;
    }

    public NewLetterPage saveLetterInDrafts() {
        saveLetterButton.click();
        return new NewLetterPage(driver);
    }

    public NewLetterPage quitFromNewLetterPage() {
        quitButton.click();
        return new NewLetterPage(driver);
    }
}
