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
public class MemberLogoutRequest implements RequestVerify {

    private Integer memberId; // 회원 아이디

    @Override
    public void verify() {

        if (memberId == null) throw new BadRequestException(ExceptionMessage.IsRequiredMemberId.getMessage());
    }
}
