package testing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import recorder.SpecializedScreenRecorder;

public class BaseTest {

    protected static WebDriver driver;
    protected static ScreenRecorder screenRecorder;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeSuite
    public void setUpSuite() throws Exception {
        // Initialize screen recorder
        File file = new File("recordings/");
        file.mkdirs(); 
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        screenRecorder = new SpecializedScreenRecorder(file, "TestVideo-" + timeStamp);
        screenRecorder.start();
        logger.info("Video recording started");



    }

    @AfterSuite
    public void tearDownSuite() throws Exception {
        // Stop screen recorder if initialized
        if (screenRecorder != null) {
            screenRecorder.stop();
             logger.info("Video recording stopped");
        }   
    }
}
