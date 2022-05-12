package click.bitbank.api.application.member;

import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import click.bitbank.api.domain.member.MemberType;
import click.bitbank.api.application.response.MemberInfoResponse;
import click.bitbank.api.application.response.MemberRegistrationResponse;

public interface MemberApplicationService {

    Mono<MemberRegistrationResponse> memberRegistration(ServerRequest request, MemberType memberType); // 회원 계정 생성

    Mono<MemberInfoResponse> findMemberInfo(ServerRequest request); // 회원 정보 조회
}