import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class ParameterizedTestsOfChitayGorod {

    @BeforeEach
    void openPage() {
        open("https://www.chitai-gorod.ru/");
        Configuration.pageLoadStrategy = "eager";
    }

    @DisplayName("Проверить, что при поиске по существующему значению, результат поиска не будет равен 0")
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

    @DisplayName("Проверить, что при поиске по автору, будут найдены книги этого автора")
    @CsvSource(value = {
            "Александр Зернов, Растения Российского Западного Кавказа: Полевой атлас",
            "Томас Шустер, Определитель болезней и вредителей растений",
            "Мирослав Адамчик, Справочник-определитель:Садово-парковые растения России (миньон)"
    })
    @CsvFileSource(resources = "/testData/searchResultsShouldContainValue.csv")
    @ParameterizedTest(name = "Для автора {0} должна быть книга {1}")
    @Tag("REGRESS")
    void authorsSearchResultsShouldContainBook(String author, String book) {
        $("input[class='search-form__input search-form__input--search']").setValue(author).pressEnter();
        $$("[class='app-catalog__content']").findBy(text(book)).shouldHave(text(book));
    }

    @Disabled("Проверять в случае непрохождения позитивного теста на поиск")
    @DisplayName("Проверить, что при поиске по несуществующему значению, будет показано сообщение 'Похоже, у нас такого нет'")
    @ValueSource(strings = {
            "e-r-r-o-r", "о-ш-и-б-к-а-п-о-и-с-к-а"
    })
    @ParameterizedTest(name = "Для поискового запроса {0} должно отображаться сообщение об ошибке поиска")
    @Tag("SMOKE")
    @Tag("REGRESS")
    void searchResultsIsEmpty(String bookName) {
        $("input[class='search-form__input search-form__input--search']").setValue(bookName).pressEnter();
        $$("section[class='catalog-stub catalog-stub--row search-page__nf-stub']").shouldHave(texts("Похоже, у нас такого нет", "Но на всякий случай советуем проверить опечатки в запросе."));
        $("div[class='chg-app-button__content']").shouldBe(visible);
    }
}
