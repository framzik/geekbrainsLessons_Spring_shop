package ru.geekmarket.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.khrebtov.shopuitests.DriverInitializer;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ru.geekmarket.steps.LoginSteps.webDriver;

public class CategorySteps {

    @When("I navigate to category.html page")
    public void iNavigateToCategoryHtmlPage() {
        webDriver.get(DriverInitializer.getProperty("category.url"));
    }

    @Then("I see button {string}")
    public void iSeeButton(String btn_text) {
        WebElement webElement = webDriver.findElement(By.xpath("/html/body/div[2]/div/div[1]/a"));
        assertThat(webElement.getText()).isEqualTo(btn_text);
    }

    @And("I click on Add category button")
    public void iClickOnAddCategoryButton() {
        WebElement webElement = webDriver.findElement(By.xpath("/html/body/div[2]/div/div[1]/a"));
        webElement.click();
    }

    @And("I provide Name as {string} and Description as {string}")
    public void iProvideNameAsAndDescriptionAs(String name, String desc) {
        WebElement webElement = webDriver.findElement(By.id("name"));
        webElement.sendKeys(name);
        webElement = webDriver.findElement(By.id("cost"));
        webElement.sendKeys(desc);
    }

    @And("I click on Submit button")
    public void iClickOnSubmitButton() {
        WebElement webElement = webDriver.findElement(By.xpath("/html/body/div[2]/div/div/form/button"));
        webElement.click();
    }

    @Then("in table i see category {string} {string}")
    public void inTableISeeCategory(String name, String desc) {
        boolean isContains = checkCategory(name, desc);

        assertTrue(isContains);
    }

    @And("i delete category {string} {string}")
    public void iDeleteCategory(String name, String desc) {
        WebElement webElement = webDriver.findElement(By.xpath("/html/body/div[2]/div/div[3]/table/tbody/tr[5]/td[3" +
                                                                     "]/form/a[2]"));
        webElement.click();
    }

    @Then("in table i do not see category {string} {string}")
    public void inTableIDoNotSeeCategory(String name, String desc) {
        boolean isContains = checkCategory(name, desc);

        assertFalse(isContains);
    }

    private boolean checkCategory(String name, String desc) {
        List<WebElement> elements = webDriver.findElements(By.xpath("/html/body/div[2]/div/div[3]/table"));
        List<String> collect = elements.stream()
                                       .map(WebElement::getText)
                                       .collect(Collectors.toList());
        boolean isContains = false;
        for (String str: collect) {
            if (str.contains(name + " " + desc)) {
                isContains = true;
                break;
            }
        }
        return isContains;
    }
}

