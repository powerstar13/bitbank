package click.bitbank.api.application.response;

import click.bitbank.api.presentation.shared.response.CreatedSuccessResponse;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountBookWriteResponse extends CreatedSuccessResponse {
    private int accountBookId;
}
