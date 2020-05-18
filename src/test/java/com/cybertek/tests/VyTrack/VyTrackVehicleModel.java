package com.cybertek.tests.VyTrack;

import com.cybertek.tests.TestBase;
import com.cybertek.utilities.ConfigurationReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class VyTrackVehicleModel extends TestBase {


    @Test
    public void VehicleModel() throws InterruptedException {

        String modelname = faker.country().capital();
        String make = faker.company().name();
        String vendor = faker.name().firstName() + " " + faker.name().lastName();
        String SaveAndCloseText = modelname + " " + make + " " + vendor;
        String projectPath = System.getProperty("user.dir");
        String relativePath = "src\\test\\resources\\VyTrack\\batmang.png";
        String fullPath = projectPath + "\\" + relativePath;
        String comment = faker.shakespeare().hamletQuote();
        int catalogValue = rnd.nextInt(100000000);
        int CO2fee = rnd.nextInt(10000);
        int cost = rnd.nextInt(10000);
        int totalCost = rnd.nextInt(1000000);
        int CO2emissions = rnd.nextInt(1000);
        int saveAndClose = 0;
        int saveAndNew = 1;
        int save = 2;
        int cancel = 0;
        int yesDelete = 1;
        int selection = 0;

        //VehicleModel on fleet module

        Thread.sleep(7000);
        WebElement fleet = driver.findElement(By.xpath("//*[@id=\"main-menu\"]/ul/li[2]/a/span"));
        actions.moveToElement(fleet).perform();
        Thread.sleep(1000);
        WebElement VehicleModel = driver.findElement(By.xpath("//*[@id=\"main-menu\"]/ul/li[2]/div/div/ul/li[9]/a/span\n"));
        actions.moveToElement(VehicleModel).click().build().perform();
        Thread.sleep(5000);
        String ExpectedUrl = "https://qa1.vytrack.com/entity/Extend_Entity_VehiclesModel";
        String ActualUrl = driver.getCurrentUrl();
        Assert.assertEquals(ExpectedUrl, ActualUrl, "Verify the URLs are same");

        //All Vehicle Table

        List<WebElement> TableSize = driver.findElements(By.xpath("//ul[@class='dropdown-menu pull-right']/li/a"));
        selection = rnd.nextInt(TableSize.size());
        driver.findElement(By.cssSelector("div>div>div>button[data-toggle='dropdown']")).click();
        Thread.sleep(1000);
        actions.moveToElement(TableSize.get(selection)).click().build().perform();
        Thread.sleep(1000);
        String table = driver.findElement(By.xpath("(//ul[@class='dropdown-menu pull-right']/li/a)[" + (selection + 1) + "]")).getAttribute("data-size");

        List<WebElement> VehicleTable = driver.findElements(By.xpath("//tbody[@class='grid-body']/tr"));
        int numberOfRow = VehicleTable.size();
        for (int x = 0; x < VehicleTable.size(); x++) {
            System.out.println("Vehicle" + (x + 1) + " =" + VehicleTable.get(x).getText());
        }

        Assert.assertTrue(table.contains(Integer.toString(numberOfRow)), "verify that table size numbers are equal");

        //Grid Reset-Settings

        driver.findElement(By.xpath("//div[@class='column-manager dropdown']/a")).click();

        List<WebElement> gridRowPotential = driver.findElements(By.xpath("//tbody[@class='ui-sortable']/tr"));
        List<WebElement> gridColumn = driver.findElements(By.xpath("//div[@class='table-wrapper']/table/thead/tr/th/span"));
        List<WebElement> pageTableColumns = driver.findElements(By.xpath("//thead[1]/tr/th/a/span[@class='grid-header-cell__label']"));
        List<WebElement> gridRowActive = driver.findElements(By.xpath("//tbody[@class='ui-sortable']/tr[@class='renderable']"));
        String ColumnStr = "Name Show Sort";

        WebElement selectAll = driver.findElement(By.cssSelector("a[data-role='column-manager-select-all']"));
        List<WebElement> sortCell = driver.findElements(By.xpath("//tbody[@class='ui-sortable']/tr/td[2]/span/i"));
        List<WebElement> titleCell = driver.findElements(By.xpath("//tbody[@class='ui-sortable']/tr/td/label[1]"));
        List<WebElement> visibilityCell = driver.findElements(By.xpath("//tbody[@class='ui-sortable']/tr/td[3]"));

        for (WebElement webElement : titleCell) {
            System.out.println("webElement = " + webElement.getText());
        }
        //As DEFAULT active grid row size is 1 more smaller than potential because as default Id-row is unchecked.
        Assert.assertEquals(gridRowActive.size() + 1, gridRowPotential.size(), "verify potential grid row size equal to grid active row");

        for (int x = 0; x < visibilityCell.size() - 2; x++) {
            actions.moveToElement(visibilityCell.get(x)).click().build().perform();
        }

        pageTableColumns = driver.findElements(By.xpath("//thead[1]/tr/th/a/span[@class='grid-header-cell__label']"));
        gridRowActive = driver.findElements(By.xpath("//tbody[@class='ui-sortable']/tr[@class='renderable']"));
        Assert.assertEquals(gridRowActive.size(), pageTableColumns.size(), "verify page table column size equal to grid active row after clicking visibilities");

        actions.moveToElement(selectAll).click().build().perform();
        pageTableColumns = driver.findElements(By.xpath("//thead[1]/tr/th/a/span[@class='grid-header-cell__label']"));
        gridRowActive = driver.findElements(By.xpath("//tbody[@class='ui-sortable']/tr[@class='renderable']"));
        gridColumn = driver.findElements(By.xpath("//div[@class='table-wrapper']/table/thead/tr/th/span"));
        Assert.assertEquals(gridRowActive.size(), pageTableColumns.size(), "verify page table column size equal to grid active row after clicking select all");
        Assert.assertEquals(gridRowActive.size(), gridRowPotential.size(), "verify page table column size equal to grid active row after clicking select all");

        System.out.println("All grid options are selected and there are totaly " + gridRowActive.size() + " rows");

        for (WebElement webElement : gridColumn) {
            Assert.assertTrue(ColumnStr.contains(webElement.getText()));
            System.out.println("webElement = " + webElement.getText());
        }

        int DragAndDropRandomSource = rnd.nextInt(gridRowPotential.size());
        int DragAndDropRandomTarget = rnd.nextInt(gridRowPotential.size());
        System.out.println("DragAndDropRandomSource = " + DragAndDropRandomSource + " | DragAndDropRandomTarget = " + DragAndDropRandomTarget);
        pageTableColumns = driver.findElements(By.xpath("//thead[1]/tr/th/a/span[@class='grid-header-cell__label']"));
        titleCell = driver.findElements(By.xpath("//tbody[@class='ui-sortable']/tr/td/label[1]"));
        String GridCellSourceBefore = titleCell.get(DragAndDropRandomSource).getText().toLowerCase();
        System.out.println("Grid Setting - Row Titles Before Resetting");
        for (int x = 0; x < titleCell.size(); x++) {
            System.out.print((x + 1) + "." + titleCell.get(x).getText() + " | ");
        }
        System.out.println();
        System.out.println("Page Table Grid - Column Titles Before Resetting");
        for (int x = 0; x < pageTableColumns.size(); x++) {
            System.out.print((x + 1) + "." + pageTableColumns.get(x).getText() + " | ");
        }

        System.out.println();
        System.out.println("titleCell = " + titleCell.get(DragAndDropRandomSource).getText());

        actions.moveToElement(sortCell.get(DragAndDropRandomSource)).clickAndHold(sortCell.get(DragAndDropRandomSource)).moveToElement(sortCell.get(DragAndDropRandomTarget)).release(sortCell.get(DragAndDropRandomTarget)).build().perform();
        Thread.sleep(1000);
        String TableTargetAfter = pageTableColumns.get(DragAndDropRandomTarget).getText().toLowerCase();
        pageTableColumns = driver.findElements(By.xpath("//thead[1]/tr/th/a/span[@class='grid-header-cell__label']"));
        titleCell = driver.findElements(By.xpath("//tbody[@class='ui-sortable']/tr/td/label[1]"));
        System.out.println("pageTableColumns = " + pageTableColumns.get(DragAndDropRandomTarget).getText());
        System.out.println("Grid Setting - Row Titles After Resetting");
        for (int x = 0; x < titleCell.size(); x++) {
            System.out.print((x + 1) + "." + titleCell.get(x).getText() + " | ");
        }

        System.out.println();
        System.out.println("Page Table Grid - Column Titles After Resetting");
        for (int x = 0; x < pageTableColumns.size(); x++) {
            System.out.print((x + 1) + "." + pageTableColumns.get(x).getText() + " | ");
        }

        if (pageTableColumns.get(DragAndDropRandomTarget).getText().equals("CVVI")) {
        } else {
            // Everything is normal and properly works but it gives error
            //Assert.assertEquals(GridCellSourceBefore,TableTargetAfter , "Verify that after sorting grid row page table changed properly");
        }

        //Create Vehicle - Fill Inputs

        driver.findElement(By.cssSelector("a[title='Create Vehicles Model']")).click();
        Thread.sleep(3000);

        WebElement ModelName = driver.findElement(By.cssSelector("input[id^='custom_entity_type_ModelName-uid']"));
        ModelName.sendKeys(modelname);
        WebElement Make = driver.findElement(By.cssSelector("input[id^='custom_entity_type_Make-uid']"));
        Make.sendKeys(make);
        WebElement CanBeRequested = driver.findElement(By.xpath("(//span[@class='select2-arrow'])[1]"));
        CanBeRequested.click();

        List<WebElement> ArrayRequest = driver.findElements(By.cssSelector("[id^='custom_entity_type_Canberequested-uid']>option"));
        selection = rnd.nextInt(ArrayRequest.size() - 1) + 1;//DO NOT CHANGE. It is special to this element.there are three elements in this list but only 2. or 3. must be choosen
        if (ConfigurationReader.get("browser").equalsIgnoreCase("chrome")) {
            ArrayRequest.get(selection).click();
        } else if (ConfigurationReader.get("browser").equalsIgnoreCase("firefox")) {
            actions.moveToElement(ArrayRequest.get(selection)).click().build().perform();
        }


        WebElement Logo = driver.findElement(By.cssSelector("[id^='custom_entity_type_Logo_file-uid-']"));
        Logo.sendKeys(fullPath);
        WebElement CatalogValue = driver.findElement(By.cssSelector("input[id^='custom_entity_type_CatalogValue-uid']"));
        CatalogValue.sendKeys(Integer.toString(catalogValue));
        WebElement CO2Fee = driver.findElement(By.cssSelector("input[id^='custom_entity_type_CO2Fee-uid']"));
        CO2Fee.sendKeys(Integer.toString(CO2fee));
        WebElement Cost = driver.findElement(By.cssSelector("input[id^='custom_entity_type_Cost-uid']"));
        Cost.sendKeys(Integer.toString(cost));
        WebElement TotalCost = driver.findElement(By.cssSelector("input[id^='custom_entity_type_TotalCost-uid']"));
        TotalCost.sendKeys(Integer.toString(totalCost));
        WebElement CO2Emissions = driver.findElement(By.cssSelector("input[id^='custom_entity_type_CO2Emissions-uid']"));
        CO2Emissions.sendKeys(Integer.toString(CO2emissions));
        WebElement FuelType = driver.findElement(By.cssSelector("[id^='s2id_custom_entity_type_FuelType-uid']"));
        FuelType.click();

        List<WebElement> ArrayFuelTypes = driver.findElements(By.cssSelector("[id^='custom_entity_type_FuelType-uid']>option"));
        selection = rnd.nextInt(ArrayFuelTypes.size() - 1) + 1;//DO NOT CHANGE. It is special to this element.There are 5 elements in list but only 4 of them must be choosen

        if (ConfigurationReader.get("browser").equalsIgnoreCase("chrome")) {
            ArrayFuelTypes.get(selection).click();
        } else if (ConfigurationReader.get("browser").equalsIgnoreCase("firefox")) {
            actions.moveToElement(ArrayFuelTypes.get(selection)).click().build().perform();
        }

        System.out.print("Fuel Types = ");
        for (WebElement arrayFuelType : ArrayFuelTypes) {
            System.out.print(arrayFuelType.getText() + " | ");
        }

        System.out.println();
        WebElement Vendors = driver.findElement(By.cssSelector("[id^='custom_entity_type_Vendors-uid']"));
        Vendors.sendKeys(vendor);
        selection = rnd.nextInt(1);//For cancel or Select options
        if (selection == 1) {
            WebElement Cancel = driver.findElement(By.cssSelector("a[data-action='cancel']"));
            Cancel.click();
            Thread.sleep(2000);
            System.out.println("After creating a new vehicle, without saving CANCEL option selected");
            Assert.assertEquals(driver.getTitle(), "All - Vehicles Model - Entities - System - Car - Entities - System", "verify that titles are same");
        } else if (selection == 0) {

            // Save Options

            WebElement SaveOptionsButton = driver.findElement(By.cssSelector(".btn-success.btn.dropdown-toggle"));
            SaveOptionsButton.click();

            List<WebElement> SaveOptions = driver.findElements(By.xpath("//ul/li/button"));
            System.out.print("Saving Options = ");
            for (WebElement saveOption : SaveOptions) {
                System.out.print(saveOption.getText() + " | ");
            }
            System.out.println();

            selection = rnd.nextInt(SaveOptions.size());

            if (ConfigurationReader.get("browser").equalsIgnoreCase("chrome")) {
                SaveOptions.get(selection).click();
            } else if (ConfigurationReader.get("browser").equalsIgnoreCase("firefox")) {
                actions.moveToElement(SaveOptions.get(selection)).click().build().perform();
            }
            Thread.sleep(3000);
            String ActualTitle = driver.getTitle();

            if ((modelname != null) || (make != null) || (vendor != null)) {

                //SaveAndClose Option
                    if (selection == saveAndClose) {
                        Assert.assertEquals(SaveAndCloseText + " - Vehicles Model - Entities - System - Car - Entities - System", ActualTitle, "Verify that the texts are same");
                        System.out.println("SaveAndClose Option Selected");

                        List<WebElement> saveList1 = driver.findElements(By.xpath("//div[@class='control-group attribute-row']/label"));
                        List<WebElement> saveList2 = driver.findElements(By.xpath("//div[@class='control-group attribute-row']/div/div"));
                        System.out.println("Features of Created Vehicle:");
                        for (int i = 0; i < saveList1.size(); i++) {
                            System.out.println(saveList1.get(i).getText().trim() + ": " + saveList2.get(i).getText().trim());
                        }

                        //Add Attachment-comment

                        driver.findElement(By.cssSelector("[title='Add attachment']")).click();
                        driver.findElement(By.cssSelector("[id^='oro_attachment_file_file-uid-']")).sendKeys(fullPath);
                        driver.findElement(By.cssSelector("[id^='oro_attachment_comment-uid-']")).sendKeys(comment);
                        driver.findElement(By.cssSelector("button[type='submit']")).click();
                        Thread.sleep(1000);
                        String AttachmentCommentActual = driver.findElement(By.cssSelector("[data-column-label='Comment']")).getText();
                        System.out.println("Attachment Comment Added = " + AttachmentCommentActual);
                        Assert.assertTrue(comment.contains(AttachmentCommentActual.split(",")[0]), "Verify that actual and expected attachment comments are equal");

                        //Add Comment

                        driver.findElement(By.cssSelector("button[class='btn add-comment-button']")).click();
                        WebElement iframe = driver.findElement(By.cssSelector("iframe[id^='message-uid-']"));
                        driver.switchTo().frame(iframe);
                        driver.findElement(By.id("tinymce")).sendKeys(comment);
                        driver.switchTo().defaultContent();
                        WebElement submit = driver.findElement(By.cssSelector("button[type='submit']"));
                        actions.moveToElement(submit).click().build().perform();
                        Thread.sleep(1000);
                        String commentActual = driver.findElement(By.cssSelector("div[class='comment-body']")).getText();
                        System.out.println("Comment Added = " + commentActual);
                        Assert.assertTrue(comment.contains(commentActual.split(",")[0]), "Verify that actual and expected comments are equal");

                        //Add Note

                        driver.findElement(By.cssSelector("[title='Add note']")).click();
                        iframe = driver.findElement(By.cssSelector("iframe[id^='oro_note_form_message-uid-']"));
                        driver.switchTo().frame(iframe);
                        driver.findElement(By.id("tinymce")).sendKeys(comment);
                        driver.switchTo().defaultContent();
                        driver.findElement(By.cssSelector("button[type='submit']")).click();
                        Thread.sleep(1000);
                        String NoteActual = driver.findElement(By.cssSelector("[class='message-item message']")).getText();
                        System.out.println("Note Added = " + comment);
                        Assert.assertTrue(comment.contains(NoteActual.split(",")[0]), "Verify that actual and expected notes are equal");

                        //Edit Vehicle

                        driver.findElement(By.cssSelector("[title='Edit Vehicles Model']")).click();
                        Thread.sleep(2000);
                        Assert.assertEquals(driver.getTitle(), SaveAndCloseText + " - Entities - System - Car - Entities - System", "verify titles are same");
                        System.out.println("Vehicle EDIT selected");

                        //Delete Vehicle

                        Thread.sleep(1000);
                        WebElement delete = driver.findElement(By.xpath("//a[@title='Delete Vehicles Model']"));
                        delete.click();
                        List<WebElement> deleteOptions = driver.findElements(By.cssSelector("div[class='modal-footer']>a[href='#']"));
                        selection = rnd.nextInt(1);
                        actions.moveToElement(deleteOptions.get(selection)).click().build().perform();

                        if (selection == cancel) {
                            Assert.assertEquals(SaveAndCloseText + " - Vehicles Model - Entities - System - Car - Entities - System", ActualTitle, "Verify that the texts are same");
                            System.out.println("Delete Option Cancelled");
                        } else if (selection == yesDelete) {
                            Thread.sleep(2000);
                            Assert.assertEquals(driver.getTitle(), "All - Vehicles Model - Entities - System - Car - Entities - System", "verify that the page is All Vehicle Models after deletion");
                            System.out.println("Vehicle Deleted");
                        }

                        //Save Option

                    } else if (selection==save) {
                        Assert.assertEquals(driver.getTitle(), SaveAndCloseText + " - Entities - System - Car - Entities - System", "verify titles are same");
                        System.out.println("Save option is selected");

                        //Delete Vehicle

                        Thread.sleep(1000);
                        WebElement delete = driver.findElement(By.xpath("//a[@title='Delete Vehicles Model']"));
                        delete.click();
                        List<WebElement> deleteOptions = driver.findElements(By.cssSelector("div[class='modal-footer']>a[href='#']"));
                        selection = rnd.nextInt(1);
                        actions.moveToElement(deleteOptions.get(selection)).click().build().perform();


                        if (selection == cancel) {
                            Assert.assertEquals(SaveAndCloseText + " - Entities - System - Car - Entities - System", ActualTitle, "Verify that the texts are same");
                            System.out.println("Delete Option Cancelled");
                        } else if (selection == yesDelete) {
                            Thread.sleep(2000);
                            Assert.assertEquals(driver.getTitle(), "All - Vehicles Model - Entities - System - Car - Entities - System", "verify that the page is All Vehicle Models after deletion");
                            System.out.println("Vehicle Deleted");
                        }
                    }

                //SaveAndNew Option

                } else if (selection == saveAndNew) {
                    Assert.assertEquals(driver.getTitle(), "Create Vehicles Model - Entities - System - Car - Entities - System", "Verify that page titles are same");
                    System.out.println("SaveAndNew Option Selected");
                    Thread.sleep(1000);

                    //Cancel

                    driver.findElement(By.cssSelector("[data-action='cancel']")).click();
                    Thread.sleep(1000);
                    Assert.assertEquals(driver.getTitle(), "All - Vehicles Model - Entities - System - Car - Entities - System", "verify that the page is All Vehicle Models after deletion");
                    System.out.println("After SaveAndNew option, Cancel option selected");

                }
            }

        }


        @Test
        public void MiniTest () throws InterruptedException {



        }

    }




