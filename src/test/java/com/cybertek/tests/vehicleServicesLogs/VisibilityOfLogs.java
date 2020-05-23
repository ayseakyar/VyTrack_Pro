package com.cybertek.tests.vehicleServicesLogs;

import com.cybertek.pages.DashboardPage;
import com.cybertek.pages.LoginPage;
import com.cybertek.pages.LogoutPage;
import com.cybertek.pages.VehicleServicesLogsPage;
import com.cybertek.tests.TestBase;
import com.cybertek.utilities.BrowserUtils;
import com.cybertek.utilities.ConfigurationReader;
import com.cybertek.utilities.Driver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class VisibilityOfLogs extends TestBase {

    @Test
    public void byAuthorized () {

        extentLogger = report.createTest("Visibility of logs by authorized");

        //Log in with user valid crudential
        LoginPage login = new LoginPage();
        String username = ConfigurationReader.get("driver_username");
        String password = ConfigurationReader.get("driver_password");
        extentLogger.info("username: " + username);
        extentLogger.info("password: " + password);
        extentLogger.info("Login as a Track Driver (authorized)");
        login.login(username,password);

        //Click FLEET module + click Vehicle serviece log
        extentLogger.info("Click Vehicle Servieces Logs");
        new DashboardPage().navigateToModule("Fleet","Vehicle Services Logs");

        VehicleServicesLogsPage vehicleSLP= new VehicleServicesLogsPage();
        String ServicesLogsPageText = "VehicleServicesLogs";
        vehicleSLP.waitUntilLoaderScreenDisappear();

        //Verify VehicleServicesLogs page open
        extentLogger.info("Verify The page is VehicleServicesLogs");
        String actualResult=vehicleSLP.modulePage.getText();
        Assert.assertEquals(actualResult, ServicesLogsPageText, "Verify open VehicleServicesLogs page");

        extentLogger.pass("PASS : Visibility of logs by user");


        // signout
        BrowserUtils.waitFor(5);
        LogoutPage logout = new LogoutPage();
        logout.logOut();




    }


    @DataProvider(name = "credential" )
    public Object[][]createData() {
        return new Object[][]{
                { ConfigurationReader.get("salesmanager_username"),ConfigurationReader.get("salesmanager_password")},
                { ConfigurationReader.get("storemanager_username"),ConfigurationReader.get("storemanager_password")},

        };
    }
    @Test(dataProvider = "credential")
    public void byNonAuthorized (String username,String password) {
        extentLogger = report.createTest("Visibility of logs by non-authorized");

        //Log in with user valid crudential
        LoginPage login = new LoginPage();
        extentLogger.info("username: " + username);
        extentLogger.info("password: " + password);
        extentLogger.info("Login as a "+username+" (non-authorized)");
        login.login(username,password);

        //Click FLEET module + click Vehicle serviece log
        extentLogger.info("Click Vehicle Servieces Logs");
        new DashboardPage().navigateToModule("Fleet","Vehicle Services Logs");
        VehicleServicesLogsPage vehicleSLP= new VehicleServicesLogsPage();
        vehicleSLP.waitUntilLoaderScreenDisappear();
        extentLogger.info("Verify The page is Dashboard page");
        Assert.assertTrue(vehicleSLP.modulePage.getText().equals("Dashboard"),"Verify Dashboard page");


        //Verify error message "You do not have permission to perform this action."
        extentLogger.info("Verify error message");
        String expectedText = "You do not have permission to perform this action.";
        String actualResult=vehicleSLP.errorMessage.getText();
        Assert.assertEquals(actualResult,expectedText, "Verify error message");

        extentLogger.pass("PASS : Visibility of logs by user");

        // signout
        LogoutPage logout = new LogoutPage();
        logout.logOut();


    }


}
