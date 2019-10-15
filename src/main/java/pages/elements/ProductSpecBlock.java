package pages.elements;

import lombok.Getter;
import lombok.NonNull;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.List;
import java.util.Objects;

@Getter
public class ProductSpecBlock extends HtmlElement {

    @Name("Spec list")
    @FindBy(css = ".n-product-spec")
    private List<SpecsItem> specList;


    public String getSpecByName(@NonNull final String specName) {
        SpecsItem specsItem = specList
                .stream()
                .filter(spec -> spec.getSpecName().exists() && spec.getSpecName().getText().trim().equalsIgnoreCase(specName))
                .findFirst().orElse(null);
        return Objects.isNull(specsItem) ? "not found spec" : specsItem.getSpecValue().exists() ? specsItem.getSpecValue().getText().trim() : "";
    }
}
