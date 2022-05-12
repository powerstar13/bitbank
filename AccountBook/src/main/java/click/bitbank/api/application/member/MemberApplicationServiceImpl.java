package click.bitbank.api.application.member;

import click.bitbank.api.application.response.MemberInfoResponse;
import click.bitbank.api.application.response.MemberRegistrationResponse;
import click.bitbank.api.domain.member.MemberSaveSpecification;
import click.bitbank.api.domain.member.MemberSearchSpecification;
import click.bitbank.api.domain.member.MemberType;
import click.bitbank.api.infrastructure.exception.status.BadRequestException;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.presentation.member.request.MemberRegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberApplicationServiceImpl implements MemberApplicationService {

    private final MemberSaveSpecification memberSaveSpecification;
    private final MemberSearchSpecification memberSearchSpecification;

    /**
     * 회원 계정 생성
     * @param serverRequest : 전달된 Request
     * @param memberType : 회원 유형
     * @return Mono<MemberRegistrationResponse> : 저장된 회원 정보
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<MemberRegistrationResponse> memberRegistration(ServerRequest serverRequest, MemberType memberType) {

        return serverRequest.bodyToMono(MemberRegistrationRequest.class).flatMap(
            request -> {
                request.verify(); // Request 유효성 검사

                return memberSaveSpecification.memberExistCheckAndRegistration(request, memberType); // 회원 계정 생성
            }
        ).switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<MemberInfoResponse> findMemberInfo(ServerRequest request) {

        String memberIdStr = request.pathVariable("memberId");
        if (StringUtils.isBlank(memberIdStr)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberId.getMessage());

        int memberId = Integer.parseInt(memberIdStr);

        return memberSearchSpecification.getMemberInfo(memberId);
    }
}
