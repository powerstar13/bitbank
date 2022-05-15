package click.bitbank.api.domain.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountBookSearchService {

    private final IncomeRepository incomeRepository;
    private final ExpenditureRepository expenditureRepository;
    private final TransferRepository transferRepository;

    public Mono<List<Income>> makeAccountBookSearchByDail(AccountBookSearchRequest request) {
        Mono<List<Income>> income = getIncomeSearch(request);
        Mono<List<Expenditure>> expenditure = getExpenditureSearch(request);
        Mono<List<Transfer>> transter = getTransferSearch(request);

//        Flux.merge(income.flatMapMany(Flux::fromIterable),income.flatMapMany(Flux::fromIterable)).collectList();
        return getIncomeSearch(request);
    }


    /**
     * 가계부 목록 검색 (수입)
     *
     * @param request : 전달된 Request
     * @return
     */
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<List<Income>> getIncomeSearch(AccountBookSearchRequest request) {
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
        return income;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<List<Expenditure>> getExpenditureSearch(AccountBookSearchRequest request) {
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
        return expenditure;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<List<Transfer>> getTransferSearch(AccountBookSearchRequest request) {
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
        return transfer;
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
