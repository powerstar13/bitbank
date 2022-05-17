package click.bitbank.api.presentation.member.request;

import click.bitbank.api.infrastructure.exception.status.BadRequestException;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.presentation.shared.request.RequestVerify;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberModificationRequest implements RequestVerify {

    private Integer memberId; // 회원 고유번호
    private String memberName; // 회원 이름
    private String memberPassword; // 회원 비밀번호

    @Override
    public void verify() {
        if (memberId == null) throw new BadRequestException(ExceptionMessage.IsRequiredMemberId.getMessage());
        if (StringUtils.isBlank(memberName)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberName.getMessage());
    }
}
