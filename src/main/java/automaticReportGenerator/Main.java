package automaticReportGenerator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Main {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        driver.manage().window();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        driver.get("https://us.gblnet.net");
        login(driver);

        driver.get("https://us.gblnet.net/oper/index.php?core_section=task_list&filter_selector0=date_update&date_update0_value2=3&date_update0_date1=01.04.2022&date_update0_date2=01.04.2022&filter_selector1=task_staff_wo_division&employee_find_input=&employee_id1=780");
    }

    public static void login(WebDriver driver) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.inputLogin(ConfProperties.getProperty("login"));
        loginPage.inputPassword(ConfProperties.getProperty("password"));
        loginPage.clickLoginButton();
    }
}
