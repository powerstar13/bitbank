package click.bitbank.api.domain.service;

import click.bitbank.api.application.response.DTO.AccountBookByCategoryDTO;
import click.bitbank.api.application.response.DTO.AccountBookInfoDTO;
import click.bitbank.api.application.response.DTO.AccountBookSearchByDailyDTO;
import click.bitbank.api.domain.accountBook.SearchDateType;
import click.bitbank.api.domain.accountBook.model.Expenditure;
import click.bitbank.api.domain.accountBook.model.Income;
import click.bitbank.api.domain.accountBook.model.Transfer;
import click.bitbank.api.domain.accountBook.repository.ExpenditureRepository;
import click.bitbank.api.domain.accountBook.repository.IncomeRepository;
import click.bitbank.api.domain.accountBook.repository.TransferRepository;
import click.bitbank.api.presentation.accountBook.request.AccountBookSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountBookSearchService {

    private final IncomeRepository incomeRepository;
    private final ExpenditureRepository expenditureRepository;
    private final TransferRepository transferRepository;

    private final ModelMapper modelMapper;

    public Mono<List<AccountBookSearchByDailyDTO>> makeAccountBookSearchByDail(AccountBookSearchRequest request) {

        return Flux.merge(getIncomeSearch(request), getExpenditureSearch(request), getTransferSearch(request))
                .flatMapIterable(Function.identity())
                .collectList()
                .flatMap(accountBookByCategoryDTOList -> Mono.just(makeAccountBookSearchResponse(accountBookByCategoryDTOList)));
    }


    public List<AccountBookSearchByDailyDTO> makeAccountBookSearchResponse(List<AccountBookByCategoryDTO> accountBookByCategoryDTOList) {
        Map<String, AccountBookSearchByDailyDTO> accountBookMap = new HashMap<>();

        for (AccountBookByCategoryDTO accountBook : accountBookByCategoryDTOList) {

            AccountBookSearchByDailyDTO accountBookSearchByDailyDTO = new AccountBookSearchByDailyDTO(accountBook.getDate());

            if (!accountBookMap.containsKey(accountBookSearchByDailyDTO.getDate())) {   // 해당 날짜의 key, value 생성
                accountBookMap.put(accountBookSearchByDailyDTO.getDate(), accountBookSearchByDailyDTO);
            }
            accountBookSearchByDailyDTO = accountBookMap.get(accountBookSearchByDailyDTO.getDate());    // 해당 날짜에 존재하는 value 가져오기
            accountBookSearchByDailyDTO.setAccountBookTotalByDaily(accountBook.getMoney()); // 해당 날짜 총 금액 구하기

            AccountBookInfoDTO accountBookInfoDTO = new AccountBookInfoDTO(accountBook.getAccountBookType(), accountBook.getInfo(), accountBook.getMoney());
            accountBookSearchByDailyDTO.setAccountBookInfoDTOList(accountBookInfoDTO);  // 가계부 상제 정보 세팅

            accountBookMap.put(accountBookSearchByDailyDTO.getDate(), accountBookSearchByDailyDTO); // 변경된 데이터로 value 수정
//            AccountBookInfoDTO accountBookInfoDTO = new AccountBookInfoDTO(accountBook.getAccountBookType(), accountBook.getInfo(), accountBook.getMoney());
        }

        Object[] sortedAccountBook = accountBookMap.keySet().toArray();
        Arrays.sort(sortedAccountBook);
        List<AccountBookSearchByDailyDTO> accountBookSearchByDailyDTOList = new ArrayList<>();

        for (String key : accountBookMap.keySet()) {
            accountBookSearchByDailyDTOList.add(accountBookMap.get(key));
        }

        return accountBookSearchByDailyDTOList;
    }

    /**
     * 가계부 목록 검색 (수입)
     *
     * @param request : 전달된 Request
     * @return
     */
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<List<AccountBookByCategoryDTO>> getIncomeSearch(AccountBookSearchRequest request) {
        Mono<List<Income>> income = null;

        // 수입 유형이 지정되지 않은 경우
        if (request.getIncomeType() == null || request.getIncomeType().isEmpty()) {

            // 검색어가 없는 경우
            if (request.getSearchKeyword() == null) {

                // 전체 기간 조회
                if (request.getSearchDateType() == SearchDateType.A || request.getSearchDateType() == null) {
                    income = incomeRepository.findByMemberId(request.getMemberId()).collectList().log();
                } else {
                    setBoundsDate(request); // 검색 기간 세팅
                    income = incomeRepository.findByMemberIdAndIncomeDateBetween(request.getMemberId(), request.getSearchStartDate(), request.getSearchEndDate())
                            .collectList().log();
                }

            } else {

                // 전체 기간을 조회하거나, 기간이 설정되어 있지 않은 경우
                if (request.getSearchDateType() == SearchDateType.A || request.getSearchDateType() == null) {
                    income = incomeRepository.findByMemberIdAndIncomeInfoContaining(request.getMemberId(), request.getSearchKeyword()).collectList().log();
                } else {
                    setBoundsDate(request); // 검색 기간 세팅

                    income = incomeRepository.findByMemberIdAndIncomeDateBetweenAndIncomeInfoContaining(request.getMemberId(), request.getSearchStartDate(), request.getSearchEndDate(), request.getSearchKeywordAddPercent())
                            .collectList().log();
                }

            }
        } else {

            // 검색어가 없는 경우
            if (request.getSearchKeyword() == null) {

                // 전체 기간 조회
                if (request.getSearchDateType() == SearchDateType.A || request.getSearchDateType() == null) {
                    income = incomeRepository.findByMemberIdAndIncomeTypeIn(request.getMemberId(), request.getIncomeType()).collectList().log();
                } else {
                    setBoundsDate(request); // 검색 기간 세팅
                    income = incomeRepository.findByMemberIdAndIncomeDateBetweenAndIncomeTypeIn(request.getMemberId(), request.getIncomeType(), request.getSearchStartDate(), request.getSearchEndDate())
                            .collectList().log();
                }

            } else {

                // 전체 기간을 조회하거나, 기간이 설정되어 있지 않은 경우
                if (request.getSearchDateType() == SearchDateType.A || request.getSearchDateType() == null) {
                    income = incomeRepository.findByMemberIdAndIncomeTypeInAndIncomeInfoContaining(request.getMemberId(), request.getIncomeType(), request.getSearchKeyword()).collectList().log();
                } else {
                    setBoundsDate(request); // 검색 기간 세팅

                    income = incomeRepository.findByMemberIdAndIncomeDateBetweenAndIncomeInfoContainingAndIncomeTypeIn(request.getMemberId(), request.getIncomeType(), request.getSearchStartDate(), request.getSearchEndDate(), request.getSearchKeywordAddPercent())
                            .collectList().log();
                }

            }
        }
        return income.map(accountBookList -> {
            List<AccountBookByCategoryDTO> accountBookByCategoryDTOList = accountBookList.stream()
                    .map(accountBook -> new AccountBookByCategoryDTO(accountBook.getIncomeDate(), accountBook.getIncomeInfo(), accountBook.getIncomeMoney(), accountBook.getAccountBookType()))
                    .collect(Collectors.toList());
            return accountBookByCategoryDTOList;
        });
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<List<AccountBookByCategoryDTO>> getExpenditureSearch(AccountBookSearchRequest request) {
        Mono<List<Expenditure>> expenditure = null;

        // 지출 유형이 지정되지 않은 경우
        if (request.getExpenditureType() == null || request.getExpenditureType().isEmpty()) {

            // 검색어가 없는 경우
            if (request.getSearchKeyword() == null) {

                // 전체 기간 조회
                if (request.getSearchDateType() == SearchDateType.A || request.getSearchDateType() == null) {
                    expenditure = expenditureRepository.findByMemberId(request.getMemberId()).collectList().log();
                } else {
                    setBoundsDate(request); // 검색 기간 세팅
                    expenditure = expenditureRepository.findByMemberIdAndExpenditureDateBetween(request.getMemberId(), request.getSearchStartDate(), request.getSearchEndDate())
                            .collectList().log();
                }

            } else {

                // 전체 기간을 조회하거나, 기간이 설정되어 있지 않은 경우
                if (request.getSearchDateType() == SearchDateType.A || request.getSearchDateType() == null) {
                    expenditure = expenditureRepository.findByMemberIdAndExpenditureInfoContaining(request.getMemberId(), request.getSearchKeyword()).collectList().log();
                } else {
                    setBoundsDate(request); // 검색 기간 세팅

                    expenditure = expenditureRepository.findByMemberIdAndExpenditureDateBetweenAndExpenditureInfoContaining(request.getMemberId(), request.getSearchStartDate(), request.getSearchEndDate(), request.getSearchKeywordAddPercent())
                            .collectList().log();
                }

            }
        } else {

            // 검색어가 없는 경우
            if (request.getSearchKeyword() == null) {

                // 전체 기간 조회
                if (request.getSearchDateType() == SearchDateType.A || request.getSearchDateType() == null) {
                    expenditure = expenditureRepository.findByMemberIdAndExpenditureTypeIn(request.getMemberId(), request.getExpenditureType()).collectList().log();
                } else {
                    setBoundsDate(request); // 검색 기간 세팅
                    expenditure = expenditureRepository.findByMemberIdAndExpenditureDateBetweenAndExpenditureTypeIn(request.getMemberId(), request.getExpenditureType(), request.getSearchStartDate(), request.getSearchEndDate())
                            .collectList().log();
                }

            } else {

                // 전체 기간을 조회하거나, 기간이 설정되어 있지 않은 경우
                if (request.getSearchDateType() == SearchDateType.A || request.getSearchDateType() == null) {
                    expenditure = expenditureRepository.findByMemberIdAndExpenditureTypeInAndExpenditureInfoContaining(request.getMemberId(), request.getExpenditureType(), request.getSearchKeyword()).collectList().log();
                } else {
                    setBoundsDate(request); // 검색 기간 세팅

                    expenditure = expenditureRepository.findByMemberIdAndExpenditureDateBetweenAndExpenditureInfoContainingAndExpenditureTypeIn(request.getMemberId(), request.getExpenditureType(), request.getSearchStartDate(), request.getSearchEndDate(), request.getSearchKeywordAddPercent())
                            .collectList().log();
                }

            }
        }
        return expenditure.map(accountBookList -> {
            List<AccountBookByCategoryDTO> accountBookByCategoryDTOList = accountBookList.stream()
                    .map(accountBook -> new AccountBookByCategoryDTO(accountBook.getExpenditureDate(), accountBook.getExpenditureInfo(), accountBook.getNegateExpenditureMoney(), accountBook.getAccountBookType()))
                    .collect(Collectors.toList());
            return accountBookByCategoryDTOList;
        });
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<List<AccountBookByCategoryDTO>> getTransferSearch(AccountBookSearchRequest request) {
        Mono<List<Transfer>> transfer = null;

        // 지출 유형이 지정되지 않은 경우
        if (request.getTransferType() == null || request.getTransferType().isEmpty()) {

            // 검색어가 없는 경우
            if (request.getSearchKeyword() == null) {

                // 전체 기간 조회
                if (request.getSearchDateType() == SearchDateType.A || request.getSearchDateType() == null) {
                    transfer = transferRepository.findByMemberId(request.getMemberId()).collectList().log();
                } else {
                    setBoundsDate(request); // 검색 기간 세팅
                    transfer = transferRepository.findByMemberIdAndTransferDateBetween(request.getMemberId(), request.getSearchStartDate(), request.getSearchEndDate())
                            .collectList().log();
                }

            } else {

                // 전체 기간을 조회하거나, 기간이 설정되어 있지 않은 경우
                if (request.getSearchDateType() == SearchDateType.A || request.getSearchDateType() == null) {
                    transfer = transferRepository.findByMemberIdAndTransferInfoContaining(request.getMemberId(), request.getSearchKeyword()).collectList().log();
                } else {
                    setBoundsDate(request); // 검색 기간 세팅

                    transfer = transferRepository.findByMemberIdAndTransferDateBetweenAndTransferInfoContaining(request.getMemberId(), request.getSearchStartDate(), request.getSearchEndDate(), request.getSearchKeywordAddPercent())
                            .collectList().log();
                }

            }
        } else {

            // 검색어가 없는 경우
            if (request.getSearchKeyword() == null) {

                // 전체 기간 조회
                if (request.getSearchDateType() == SearchDateType.A || request.getSearchDateType() == null) {
                    transfer = transferRepository.findByMemberIdAndTransferTypeIn(request.getMemberId(), request.getTransferType()).collectList().log();
                } else {
                    setBoundsDate(request); // 검색 기간 세팅
                    transfer = transferRepository.findByMemberIdAndTransferDateBetweenAndTransferTypeIn(request.getMemberId(), request.getTransferType(), request.getSearchStartDate(), request.getSearchEndDate())
                            .collectList().log();
                }

            } else {

                // 전체 기간을 조회하거나, 기간이 설정되어 있지 않은 경우
                if (request.getSearchDateType() == SearchDateType.A || request.getSearchDateType() == null) {
                    transfer = transferRepository.findByMemberIdAndTransferTypeInAndTransferInfoContaining(request.getMemberId(), request.getTransferType(), request.getSearchKeyword()).collectList().log();
                } else {
                    setBoundsDate(request); // 검색 기간 세팅

                    transfer = transferRepository.findByMemberIdAndTransferDateBetweenAndTransferInfoContainingAndTransferTypeIn(request.getMemberId(), request.getTransferType(), request.getSearchStartDate(), request.getSearchEndDate(), request.getSearchKeywordAddPercent())
                            .collectList().log();
                }

            }
        }
        return transfer.map(accountBookList -> {
            List<AccountBookByCategoryDTO> accountBookByCategoryDTOList = accountBookList.stream()
                    .map(accountBook -> new AccountBookByCategoryDTO(accountBook.getTransferDate(), accountBook.getTransferInfo(), accountBook.getNegateExpenditureMoney(), accountBook.getAccountBookType()))
                    .collect(Collectors.toList());
            return accountBookByCategoryDTOList;
        });
    }

    public void setBoundsDate(AccountBookSearchRequest request) {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        switch (request.getSearchDateType()) {
            case W: // 이번 주
                request.setSearchStartDate(today.with(DayOfWeek.MONDAY).format(dateFormat));
                log.info(request.getSearchStartDate());
                request.setSearchEndDate(today.with(DayOfWeek.SATURDAY).format(dateFormat));
                break;
            case M: // 이번 달
                request.setSearchStartDate(today.withDayOfMonth(1).format(dateFormat));
                request.setSearchEndDate(today.with(TemporalAdjusters.lastDayOfMonth()).format(dateFormat));
                break;
            case Y: // 이번 년도
                request.setSearchStartDate(today.with(TemporalAdjusters.firstDayOfYear()).format(dateFormat));
                request.setSearchEndDate(today.with(TemporalAdjusters.lastDayOfYear()).format(dateFormat));
                break;
            case M3:    // 최근 3개월
                request.setSearchStartDate(today.minusMonths(3).format(dateFormat));
                request.setSearchEndDate(today.format(dateFormat));
                break;
            case M6:    // 최근 6개월
                request.setSearchStartDate(today.minusMonths(6).format(dateFormat));
                request.setSearchEndDate(today.format(dateFormat));
                break;
            case S:  // 기간 설정
                break;
        }
        log.info(String.valueOf(request));
    }

}
