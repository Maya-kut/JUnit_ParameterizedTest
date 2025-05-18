import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static jdk.internal.misc.ThreadFlock.open;


public class ParameterizedTestsOfTbank {

    @BeforeEach
    void openPage() {
        open("https://www.tbank.ru/");
//        Configuration.browserSize = "1030x778";
        Configuration.browser = "chrome";
        Configuration.pageLoadStrategy = "eager";
        }
    }

    @MethodSource

    @ParameterizedTest
    @Tag("SMOKE")
    void tbankMainShouldHaveButtons(List <String> menuButtons) {
        $("*[data-test='menu']").shouldHave();
    }

}
