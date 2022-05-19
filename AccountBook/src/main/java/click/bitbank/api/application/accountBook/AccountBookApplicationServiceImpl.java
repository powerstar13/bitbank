package click.bitbank.api.application.accountBook;

import click.bitbank.api.application.response.AccountBookSearchResponse;
import click.bitbank.api.application.response.AccountBookStatisticResponse;
import click.bitbank.api.application.response.AccountBookWriteResponse;
import click.bitbank.api.domain.accountBook.AccountBookFindSpecification;
import click.bitbank.api.domain.accountBook.MemberSpecification;
import click.bitbank.api.domain.accountBook.model.AccountBookType;
import click.bitbank.api.domain.accountBook.specification.AccountBookWriteSpecification;
import click.bitbank.api.domain.service.AccountBookSearchService;
import click.bitbank.api.infrastructure.exception.status.BadRequestException;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.infrastructure.kafka.KafkaProducerService;
import click.bitbank.api.presentation.accountBook.request.AccountBookSearchRequest;
import click.bitbank.api.presentation.accountBook.request.AccountBookWriteRequest;
import click.bitbank.api.presentation.shared.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountBookApplicationServiceImpl implements AccountBookApplicationService {
    
    private final MemberSpecification memberSpecification;
    private final AccountBookSearchService accountBookSearchService;
    private final AccountBookWriteSpecification accountBookWriteSpecification;
    private final AccountBookFindSpecification accountBookFindSpecification;
    private final KafkaProducerService kafkaProducerService;

    /**
     * 가계부 작성
     * @param serverRequest: ServerRequest
     * @return Mono<AccountBookWriteResponse>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<AccountBookWriteResponse> accountBookWrite(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(AccountBookWriteRequest.class).flatMap(
            request -> {
                kafkaProducerService.sendRequestTopic(serverRequest, request.toString());
                request.verify(); // 유효성 검사

                Mono<CommonResponse> memberVerifyMono = memberSpecification.memberExistVerify(request.getMemberId());

                return memberVerifyMono.flatMap(commonResponse -> { // 회원 인증 처리
                    if (commonResponse.getRt() != 200) return Mono.error(new BadRequestException(commonResponse.getRtMsg()));

                    return accountBookWriteSpecification.accountBookExistCheckAndWrite(request); // 가계부 작성 처리
                });
            }
        ).doOnSuccess(response -> kafkaProducerService.sendResponseTopic(serverRequest, response.toString()));
    }

    /**
     * 가계부 목록 검색
     *
     * @param serverRequest : 전달된 Request
     * @return Mono<AccountBookSearchResponse> : 저장된 가계부 정보
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<AccountBookSearchResponse> accountBookSearch(ServerRequest serverRequest) {
        
        return serverRequest.bodyToMono(AccountBookSearchRequest.class).flatMap(
            request -> {
                kafkaProducerService.sendRequestTopic(serverRequest, request.toString());
                request.verify();

                return memberSpecification.memberExistenceCheck(request.getMemberId())
                    .flatMap(m -> accountBookSearchService.makeAccountBookSearchByDail(request).log()).log();
            }).switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())))
            .doOnSuccess(response -> kafkaProducerService.sendResponseTopic(serverRequest, response.toString()));
    }
    
    /**
     * 회원 고유번호 추출
     *
     * @param serverRequest : Params
     * @return int : 회원 고유번호
     */
    private int getMemberIdByRequest(ServerRequest serverRequest) {
        // 회원 고유번호 추출
        return Integer.parseInt(serverRequest.queryParam("memberId")
            .orElseThrow(() -> new BadRequestException(ExceptionMessage.IsRequiredMemberId.getMessage())));
    }
    
    /**
     * 조회할 월 추출
     *
     * @param serverRequest : Params
     * @return int : 조회할 월
     */
    private int getMonthByRequest(ServerRequest serverRequest) {
        // 회원 고유번호 추출
        return Integer.parseInt(serverRequest.queryParam("month")
            .orElse(String.valueOf(LocalDateTime.now().getMonthValue())));
    }
    
    /**
     * 월 별 수입/지출 통계 조회
     * @param serverRequest : Params
     * @return Mono<AccountBookStatisticResponse> : 월 별 수입/지출 통계
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<AccountBookStatisticResponse> accountBookStatistic(ServerRequest serverRequest) {
        kafkaProducerService.sendRequestTopic(serverRequest, serverRequest.queryParams().toSingleValueMap().toString());
        // 지출(expenditure), 수입(income) 경로에 따라 조회할 가계부 유형 분기 처리
        AccountBookType accountBookType = serverRequest.path().contains("expenditure") ? AccountBookType.P : AccountBookType.I;
        
        int memberId = this.getMemberIdByRequest(serverRequest); // 회원 고유번호 추출
        int month = this.getMonthByRequest(serverRequest); // 조회할 월 추출
    
        return accountBookFindSpecification.statisticVerify(memberId, month, accountBookType)
            .doOnSuccess(response -> kafkaProducerService.sendResponseTopic(serverRequest, response.toString()));
    }
    
}
