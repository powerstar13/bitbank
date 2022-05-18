package click.bitbank.api.domain.accountBook;

import click.bitbank.api.application.response.AccountBookStatisticResponse;
import click.bitbank.api.application.response.DTO.DailyTotalDTO;
import click.bitbank.api.application.response.DTO.DonutGraphDTO;
import click.bitbank.api.application.response.DTO.WeeklyTotalDTO;
import click.bitbank.api.domain.accountBook.model.AccountBookType;
import click.bitbank.api.domain.accountBook.repository.ExpenditureRepository;
import click.bitbank.api.domain.accountBook.repository.IncomeRepository;
import click.bitbank.api.infrastructure.exception.status.BadRequestException;
import click.bitbank.api.presentation.shared.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountBookFindSpecification {
    
    private final MemberSpecification memberSpecification;
    private final ExpenditureRepository expenditureRepository;
    private final IncomeRepository incomeRepository;
    
    /**
     * 웗 별 수입/지출 통계 검증 및 조회 처리
     * @param memberId : 회원 고유번호
     * @param month : 조회할 월
     * @param accountBookType : 조회할 가계부 유형(수입(I), 지출(P))
     * @return Mono<AccountBookStatisticResponse> : 월 별 수입/지출 통계
     */
    public Mono<AccountBookStatisticResponse> statisticVerify(int memberId, int month, AccountBookType accountBookType) {
        
        Mono<CommonResponse> memberVerifyMono = memberSpecification.memberExistVerify(memberId);
        Mono<Long> monthlyTotalMono = accountBookType.equals(AccountBookType.P) ? expenditureRepository.monthlyTotal(memberId, month) : incomeRepository.monthlyTotal(memberId, month);
        Flux<WeeklyTotalDTO> weeklyTotalFlux = accountBookType.equals(AccountBookType.P) ? expenditureRepository.weeklyTotal(memberId, month) : incomeRepository.weeklyTotal(memberId, month);
        Flux<DonutGraphDTO> categoryTotalFlux = accountBookType.equals(AccountBookType.P) ? expenditureRepository.categoryTotal(memberId, month) : incomeRepository.categoryTotal(memberId, month);
        Flux<DailyTotalDTO> dailyTotalFlux = accountBookType.equals(AccountBookType.P) ? expenditureRepository.dailyTotal(memberId, month) : incomeRepository.dailyTotal(memberId, month);
    
        Mono<List<DonutGraphDTO>> getDonutGraphDTOList = categoryTotalFlux.collectList()
            .map(donutGraphDTOList ->
                donutGraphDTOList.stream()
                    .peek(donutGraphDTO -> donutGraphDTO.setName(accountBookType, donutGraphDTO.getName()))
                    .collect(Collectors.toList())
            );
    
        Mono<List<WeeklyTotalDTO>> getWeeklyTotalDTOList = weeklyTotalFlux.collectList()
            .map(weeklyTotalDTOList -> {
                for (int i = 0; i < weeklyTotalDTOList.size(); i++) {
                    weeklyTotalDTOList.get(i).setWeek(String.format("%s월 %s주", month, i + 1));
                }
                return weeklyTotalDTOList;
            });
    
        Mono<List<Long>> getDailyTotalList = dailyTotalFlux.collectList()
            .map(dailyTotalDTOList ->
                dailyTotalDTOList.stream()
                    .map(DailyTotalDTO::getTotal)
                    .collect(Collectors.toList())
            );
    
        Mono<List<String>> getDayList = dailyTotalFlux.collectList()
            .map(dailyTotalDTOList ->
                dailyTotalDTOList.stream()
                    .map(DailyTotalDTO::getDay)
                    .collect(Collectors.toList())
            );
    
        return memberVerifyMono
            .flatMap(successResponse -> {
                if (successResponse.getRt() != 200) return Mono.error(new BadRequestException(successResponse.getRtMsg()));
            
                return Mono.zip(monthlyTotalMono, getWeeklyTotalDTOList, getDonutGraphDTOList, getDailyTotalList, getDayList)
                    .map(objects ->
                        AccountBookStatisticResponse.builder()
                            .monthlyTotal(objects.getT1())
                            .weeklyTotalDTOList(objects.getT2())
                            .donutGraphDTOList(objects.getT3())
                            .lineGraphDailyTotalList(objects.getT4())
                            .lineGraphDayList(objects.getT5())
                            .build()
                    ).switchIfEmpty(Mono.just(new AccountBookStatisticResponse())).retry();
            });
    }
    
    /**
     * 해당 월의 주차 별 워딩 가져오기
     * --> README : 주차 별 값이 애매한 이슈가 있어 우선 사용 안함
     *
     * @param week : 1년 중 몇 번째 주
     * @return String : 0월 0주
     */
    private String getLastWeekOfYearOnSunday(int week) {
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.setMinimalDaysInFirstWeek(4);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        
        return (calendar.get(Calendar.MONTH) + 1) + "월 " + (calendar.get(Calendar.WEEK_OF_MONTH)) + "주";
    }
}
