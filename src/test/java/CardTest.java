import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardTest {
    String generateDate(int daysToAdd, String pattern) {
        return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(pattern));

    }

    @Test
    public void registrationCardTest() {
        open("http://localhost:9999/");
        $("[placeholder='Город']").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
        $("[name='name']").setValue("Иванов Петр");
        $("[name='phone']").setValue("+79033237979");
        $("[data-test-id='agreement']").click();
        $$("[type='button']").find(Condition.exactText("Забронировать")).click();
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }


}