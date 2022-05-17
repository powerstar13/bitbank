package click.bitbank.api.domain.model.member;

import click.bitbank.api.infrastructure.util.MemberSha256;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * 리프레시토큰 발급
     * @param refreshToken : 리프레시 토큰
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * 로그아웃
     */
    public void logout() {
        this.refreshToken = null;
    }

    /**
     * 회원 탈퇴
     */
    public void delete() {
        this.delDate = LocalDateTime.now();
    }

    /**
     * 회원 정보 수정
     * @param memberName : 이름
     * @param memberPassword : 비밀번호
     */
    public void modify(String memberName, String memberPassword) {
        if (StringUtils.isNotBlank(memberName)) this.memberName = memberName;
        if (StringUtils.isNotBlank(memberPassword)) this.memberPassword = MemberSha256.encrypt(memberPassword);
    }
}
