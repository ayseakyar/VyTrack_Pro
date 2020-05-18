package com.cybertek.tests;


import com.cybertek.pages.LoginPage;
import com.cybertek.utilities.ConfigurationReader;
import com.cybertek.utilities.Driver;
import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestBase {

    protected WebDriver driver;
    protected Actions actions;
    protected WebDriverWait wait;
    protected Random rnd;
    protected Faker faker;

    @BeforeMethod
    public void beforeMethod() throws InterruptedException {
        driver = Driver.get();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        actions = new Actions(driver);
        wait= new WebDriverWait(driver,20);
        rnd = new Random();
        faker =new Faker();
        driver.get(ConfigurationReader.get("qa1_url"));

        LoginPage loginPage=new LoginPage();
        loginPage.login(ConfigurationReader.get("salesmanager_username"),ConfigurationReader.get("salesmanager_password"));
        Assert.assertEquals(driver.getCurrentUrl(), ConfigurationReader.get("qa1_url"));


    }

    @AfterMethod
    public void afterMethod() throws InterruptedException {
        Thread.sleep(2000);
        Driver.closeDriver();
    }


}
