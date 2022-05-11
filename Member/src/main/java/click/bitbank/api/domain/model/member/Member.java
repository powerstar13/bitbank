package click.bitbank.api.domain.model.member;

import lombok.*;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "member")
public class Member {

    @Id // ID로 엔티티를 식별한다.
    @Column(value = "memberId")
    private int memberId; // 회원 고유번호

    @Column(value = "memberLoginId")
    private String memberLoginId; // 회원 아이디

    @Column(value = "memberType")
    private MemberType memberType; // 회원 유형

    @Column(value = "memberName")
    private String memberName; // 회원 이름

    @Column(value = "memberPassword")
    private String memberPassword; // 회원 비밀번호

    @Column(value = "socialToken")
    private String socialToken; // 소셜 로그인 토큰

    @Column(value = "refreshToken")
    private String refreshToken; // JWT 리프레시 토큰

    @Column(value = "regDate")
    @CreatedDate
    private LocalDateTime regDate; // 생성일

    @Column(value = "modDate")
    @LastModifiedDate
    private LocalDateTime modDate; // 수정일

    @Column(value = "delDate")
    private LocalDateTime delDate; // 삭제일

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
