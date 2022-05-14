package click.bitbank.api.domain.model.alarm;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "alarm")
public class Alarm {

    @Id // ID로 엔티티를 식별한다.
    @Column(value = "alarmId")
    private int alarmId; // 회원 고유번호
    
    @Column(value = "alarmMessage")
    private String alarmMessage; // 알림 메시지
    
    @Column(value = "alarmCheck")
    private boolean alarmCheck; // 알림 읽음 여부

    @Column(value = "regDate")
    @CreatedDate
    private LocalDateTime regDate; // 생성일

    @Column(value = "modDate")
    @LastModifiedDate
    private LocalDateTime modDate; // 수정일
    
    @Column(value = "memberId")
    private int memberId; // 회원 고유번호

    /**
     * 알림 읽음 처리
     */
    public void readAlarmCheck() {
        this.alarmCheck = true;
    }
}
