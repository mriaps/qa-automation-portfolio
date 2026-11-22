package com.mriaps.listeners;

import com.mriaps.tests.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotListener implements ITestListener {

    private static final Path SCREENSHOT_DIR = Paths.get("target", "screenshots");
    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");

    @Override
    public void onTestFailure(ITestResult result) {
        if (!(result.getInstance() instanceof BaseTest test)) {
            return;
        }
        WebDriver driver = test.getDriver();
        if (!(driver instanceof TakesScreenshot screenshotDriver)) {
            return;
        }

        try {
            byte[] png = screenshotDriver.getScreenshotAs(OutputType.BYTES);
            Files.createDirectories(SCREENSHOT_DIR);

            String fileName = result.getTestClass().getRealClass().getSimpleName()
                    + "_" + result.getMethod().getMethodName()
                    + "_" + LocalDateTime.now().format(TIME_FORMAT) + ".png";
            Path file = SCREENSHOT_DIR.resolve(fileName);
            Files.write(file, png);
            Allure.addAttachment("Screenshot on failure", "image/png",
                    new ByteArrayInputStream(png), "png");

            System.out.println("Screenshot saved: " + file.toAbsolutePath());
        } catch (IOException | WebDriverException e) {
            System.err.println("Could not capture screenshot: " + e.getMessage());
        }
    }
}