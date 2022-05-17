package click.bitbank.api.application.accountBook;

import click.bitbank.api.application.response.AccountBookSearchResponse;
import click.bitbank.api.application.response.AccountBookStatisticResponse;
import click.bitbank.api.application.response.AccountBookWriteResponse;
import click.bitbank.api.application.response.DTO.DailyTotalDTO;
import click.bitbank.api.application.response.DTO.DonutGraphDTO;
import click.bitbank.api.application.response.DTO.WeeklyTotalDTO;
import click.bitbank.api.domain.accountBook.AccountBookFindSpecification;
import click.bitbank.api.domain.accountBook.AccountBookType;
import click.bitbank.api.domain.accountBook.MemberSpecification;
import click.bitbank.api.domain.accountBook.repository.ExpenditureRepository;
import click.bitbank.api.domain.accountBook.repository.IncomeRepository;
import click.bitbank.api.application.response.AccountBookWriteResponse;
import click.bitbank.api.domain.accountBook.MemberSpecification;
import click.bitbank.api.domain.accountBook.specification.AccountBookWriteSpecification;
import click.bitbank.api.domain.service.AccountBookSearchService;
import click.bitbank.api.infrastructure.exception.status.BadRequestException;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.presentation.accountBook.request.AccountBookSearchRequest;
import click.bitbank.api.presentation.accountBook.request.AccountBookWriteRequest;
import click.bitbank.api.presentation.shared.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountBookApplicationServiceImpl implements AccountBookApplicationService {
    
    private final MemberSpecification memberSpecification;
    private final AccountBookSearchService accountBookSearchService;
    private final AccountBookWriteSpecification accountBookWriteSpecification;
    private final AccountBookFindSpecification accountBookFindSpecification;

    /**
     * 가계부 작성
     *
     * @param serverRequest
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Object> accountBookWrite(ServerRequest serverRequest) {
//        String jwt = serverRequest.headers().firstHeader("Authorization");

        return serverRequest.bodyToMono(AccountBookWriteRequest.class).flatMap(
            request -> {
                log.info(String.valueOf(request));

                request.verify(); // 유효성 검사
                return accountBookWriteSpecification.accountBookExistCheckAndWrite(request);
            }
        );
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
                request.verify();
                log.info(String.valueOf(request));
                return memberSpecification.memberExistenceCheck(request.getMemberId())
                    .flatMap(m -> {
                        return accountBookSearchService.makeAccountBookSearchByDail(request).log();
                    }).log();
            }).switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())));
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
        // 지출(expenditure), 수입(income) 경로에 따라 조회할 가계부 유형 분기 처리
        AccountBookType accountBookType = serverRequest.path().contains("expenditure") ? AccountBookType.P : AccountBookType.I;
        
        int memberId = this.getMemberIdByRequest(serverRequest); // 회원 고유번호 추출
        int month = this.getMonthByRequest(serverRequest); // 조회할 월 추출
    
        return accountBookFindSpecification.statisticVerify(memberId, month, accountBookType);
    }
    
}
