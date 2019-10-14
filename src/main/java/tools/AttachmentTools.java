package tools;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import io.qameta.allure.Allure;
import lombok.NonNull;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AttachmentTools {

    public static byte[] namedScreenShot(@NonNull final WebDriver driver) throws IOException {
        final byte[] bytes = getScreenShotBytes(driver);

        //attachment for an allure report
        Allure.getLifecycle().addAttachment(
                "Test screen shot",
                "image/png",
                ".png",
                bytes);
        return bytes;
    }

    private static byte[] getScreenShotBytes(final WebDriver driver) throws IOException {
        //return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(Shutterbug.shootPage(
                driver,
                ScrollStrategy.WHOLE_PAGE, 50, true).getImage(),
                "png",
                byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
