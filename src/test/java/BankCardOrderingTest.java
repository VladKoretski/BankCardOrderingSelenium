package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankCardOrderingTest {

    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
       WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldTestBankCardGoodPath() {
        // WebElement form = driver.findElement(By.cssSelector("[data-test-id=callback-form]"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Корецкий Владимир");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79630000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String textActual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", textActual);
    }

    @Test
    public void shouldBeFailedEmptyNameInput () {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79630000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String textActual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", textActual);
    }

    @Test
    public void shouldBeFailedEmptyCheckBox () {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Корецкий Владимир");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79630000000");
        driver.findElement(By.cssSelector("button.button")).click();
        String textActual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).getText().trim();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed());
    }
}