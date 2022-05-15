package click.bitbank.api.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDTO {

    private String alarmMessage; // 알림 메시지

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regDate; // 생성일
}
