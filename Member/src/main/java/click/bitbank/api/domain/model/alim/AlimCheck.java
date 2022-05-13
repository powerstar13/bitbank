package click.bitbank.api.domain.model.alim;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AlimCheck {

    Y("Y", "읽음"),
    N("N", "읽지 않음");

    private String name;
    private String description;
}
