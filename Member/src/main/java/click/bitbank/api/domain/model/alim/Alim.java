package click.bitbank.api.domain.model.alim;

import click.bitbank.api.domain.model.member.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "alim")
public class Alim {

    @Id // ID로 엔티티를 식별한다.
    @Column(value = "alimId")
    private int alimId; // 회원 고유번호
    
    @Column(value = "alimMessage")
    private String alimMessage; // 알림 메시지
    
    @Column(value = "alimCheck")
    private AlimCheck alimCheck; // 알림 읽음 여부

    @Column(value = "regDate")
    @CreatedDate
    private LocalDateTime regDate; // 생성일

    @Column(value = "modDate")
    @LastModifiedDate
    private LocalDateTime modDate; // 수정일
    
    @Column(value = "memberId")
    private int memberId; // 회원 고유번호
}
