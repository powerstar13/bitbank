package click.bitbank.api.presentation.member.request;

import click.bitbank.api.infrastructure.exception.status.BadRequestException;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.presentation.shared.request.RequestVerify;
import com.github.javafaker.Faker;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginRequest implements RequestVerify {

    private String socialToken; // 소셜 토큰

    private String memberName; // 회원 이름

    @Override
    public void verify() {

        if (StringUtils.isBlank(socialToken)) throw new BadRequestException(ExceptionMessage.IsRequiredSocialToken.getMessage());
        if (StringUtils.isBlank(memberName)) { // 전달된 이름이 없을 경우
            Faker faker = new Faker();
            this.memberName = faker.name().fullName(); // 랜덤 이름 생성
        };
    }
}
