package click.bitbank.api.application.response;

import click.bitbank.api.presentation.shared.response.SuccessResponse;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AlarmCountResponse extends SuccessResponse {
    
    private long alarmCount; // 읽지 않은 알림 갯수
}
