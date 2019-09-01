import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.NewLetterPage;
import pages.OldLetterPage;
import pages.UserAccountPage;
import testdata.TestData;
import utils.MockDataUtils;

public class TestScenario {
    private WebDriver driver;
    private HomePage homePage;
    private OldLetterPage oldLetterPage;
    private UserAccountPage userAccountPage;
    private NewLetterPage newLetter;
    private String emailSubject;
    private String emailBody;

    @BeforeClass()
    private void initBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        oldLetterPage = new OldLetterPage(driver);
        userAccountPage = new UserAccountPage(driver);
        newLetter = new NewLetterPage(driver);
        emailSubject = MockDataUtils.generateRandomString(10);
        emailBody = MockDataUtils.generateRandomString(10);
    }

    @Test(description = "check if login to mail.ru is successful")
    public void loginToMailBoxTest() {

       homePage.open().fillLoginField(TestData.LOGIN_NAME)
                .fillPasswordField(TestData.PASSWORD).startUserSession();

        String authorizationData = userAccountPage.checkAutorisationData();
        Assert.assertEquals(authorizationData, TestData.EMAIL, "The email does not belong to the account being verified");
    }

    @Test(description = "check if new mail present in draft folder", dependsOnMethods = {"loginToMailBoxTest"})
    public void checkMailSubjectInDraftFolderTest() {
        userAccountPage.writeNewLetter();
        newLetter.fillAdressField(TestData.RECEIVER_EMAIL)
                .fillSubjectField(emailSubject).fillBodyInNewLetterField(emailBody);
        newLetter.saveLetterInDrafts();
        newLetter.quitFromNewLetterPage();
        userAccountPage.tapOnDraftButton();

        userAccountPage.tapOnTheFirstLetterInFolder();

        Assert.assertEquals(oldLetterPage.getMailSubject(), emailSubject);
    }

    @Test(description = "check text in letter", dependsOnMethods = {"checkMailSubjectInDraftFolderTest"})
    public void checkMailTextInDraftsFolderTest() {
        Assert.assertTrue(oldLetterPage.isTextBodyExistsInMessage(emailBody), String.format("Text: %s is not found in message body", emailBody));
    }

    @Test(description = "check letter in Draft folder", dependsOnMethods = {"checkMailTextInDraftsFolderTest"})
    public void checkDraftFolderIsEmpty() {
        oldLetterPage.sendMail();
        oldLetterPage.closeAlertSendSuccessfull();
        userAccountPage.tapOnDraftButton();

        Assert.assertFalse(userAccountPage.isEmailWithSubjectExists(emailSubject), String.format("Email with subject %s exists in folder", emailSubject));
    }

    @Test(description = "check letter in Draft folder", dependsOnMethods = {"checkDraftFolderIsEmpty"})
    public void checkSentFolderIsNotEmpty() {
        userAccountPage.tapOnSentButton();
        userAccountPage.tapOnTheFirstLetterInFolder();

        Assert.assertEquals(userAccountPage.checkSubjectInMailForSentFolder(), emailSubject);
    }

    @Test(description = "log out", dependsOnMethods = {"checkSentFolderIsNotEmpty"})
    public void logout(){
        userAccountPage.logOutFromMailBox();
    }

    @AfterClass(description = "close browser")
    public void kill() {
        driver.close();
    }
}