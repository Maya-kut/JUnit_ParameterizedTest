import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.*;


public class ParameterizedTestsOfChitayGorod {

    @BeforeEach
    void openPage() {
        open("https://www.chitai-gorod.ru/");
        Configuration.pageLoadStrategy = "eager";
    }

    @ValueSource(strings = {
            "Молчание ягнят", "я окей ты окей", "java"
    })
    @ParameterizedTest(name = "Для поискового запроса {0} должен отдаваться не пустой список книг")
    @Tag("SMOKE")
    @Tag("REGRESS")
    void searchResultsNotEmpty(String bookName) {
        $("input[class='search-form__input search-form__input--search']").setValue(bookName).pressEnter();
        $$("[class='app-catalog__content']").shouldBe(sizeGreaterThan(0));
    }

}
