package tools;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import io.qameta.allure.Allure;
import lombok.NonNull;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AttachmentTools {

    public static byte[] namedScreenShot(@NonNull final WebDriver driver) throws IOException {
        final byte[] bytes = getScreenShotBytes(driver);
        //attachment for an allure report
        attachImage("Test screen shot", bytes);
        return bytes;
    }

    private static byte[] getScreenShotBytes(final WebDriver driver) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(Shutterbug.shootPage(
                driver,
                ScrollStrategy.WHOLE_PAGE, 50, true).getImage(),
                "png",
                byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static void attachData(@NonNull final String attachName, @NonNull final String data) {
        Allure.addAttachment(attachName, data);
    }

    public static void attachImage(@NonNull final String name, byte[] bytes) {
        Allure.getLifecycle().addAttachment(
                name,
                "image/png",
                ".png",
                bytes);
    }
}
