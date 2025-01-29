import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class EbayAddToCartTest {
    public static void main(String[] args) {
        // Set up WebDriver
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // Update with actual path
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            // Step 1: Open browser and navigate to eBay
            driver.get("https://www.ebay.com");

            // Step 2: Search for 'book'
            WebElement searchBox = driver.findElement(By.id("gh-ac"));
            searchBox.sendKeys("book");
            searchBox.submit();

            // Step 3: Wait for search results
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Fetch all listed items that have a valid link
            List<WebElement> books = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//li[contains(@class,'s-item')]//a[@class='s-item__link']")
            ));

            if (books.isEmpty()) {
                System.out.println("Test Failed: No books found.");
                driver.quit();
                return;
            }

            // Click on the first valid book link
            books.get(0).click();

            // Step 4: Click on 'Add to Cart'
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("atcBtn_btn_1")));
            addToCartButton.click();

            // Step 5: Verify cart is updated
            WebElement cartIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gh-cart-n")));
            String cartItemCount = cartIcon.getText();

            if (!cartItemCount.isEmpty() && Integer.parseInt(cartItemCount) > 0) {
                System.out.println("Test Passed: Item successfully added to cart.");
            } else {
                System.out.println("Test Failed: Cart count did not update.");
            }
        } catch (Exception e) {
            System.out.println("Test Failed: " + e.getMessage());
        } finally {
            // Step 6: Close the browser
            driver.quit();
        }
    }
}
