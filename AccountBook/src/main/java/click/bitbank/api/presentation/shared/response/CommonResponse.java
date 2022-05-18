package click.bitbank.api.presentation.shared.response;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {

    private int rt;
    private String rtMsg;
}
