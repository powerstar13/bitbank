package click.bitbank.api.application.response;

import click.bitbank.api.presentation.shared.response.SuccessResponse;
import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AlarmListResponse extends SuccessResponse {
    
    private List<String> alarmMessageList; // 읽지 않은 알림 메시지 목록
}
