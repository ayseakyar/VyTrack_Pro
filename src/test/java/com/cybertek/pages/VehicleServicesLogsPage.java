package com.cybertek.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VehicleServicesLogsPage extends BasePage{

    @FindBy(xpath = "//ul[@class='nav-multilevel main-menu']/li[1]")
    public WebElement fleetModule;

    @FindBy(xpath ="//span[contains(text(),'Vehicle Services Logs')]")
    public WebElement servicesLogsModule;

    @FindBy(xpath = "(//h1)[2]")
    public WebElement servicesLogsPage;
}
