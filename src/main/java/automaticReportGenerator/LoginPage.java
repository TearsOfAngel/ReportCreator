package automaticReportGenerator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public WebDriver driver;

    @FindBy(xpath = "//*[contains(@id, 'us_oper_login_id')]")
    private WebElement loginField;

    @FindBy(xpath = "//*[@id=\"login_page_form\"]/form/input[3]")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"login_page_form\"]/form/center/input")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void inputLogin(String login) {
        loginField.sendKeys(login);
    }

    public void inputPassword(String passwd) {
        passwordField.sendKeys(passwd);
    }

    public void clickLoginButton() {
        loginButton.click();
    }
}
