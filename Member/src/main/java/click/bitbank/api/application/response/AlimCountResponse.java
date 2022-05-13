package click.bitbank.api.application.response;

import click.bitbank.api.presentation.shared.response.SuccessResponse;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AlimCountResponse extends SuccessResponse {
    
    private long alimCount; // 읽지 않은 알림 갯수
}
