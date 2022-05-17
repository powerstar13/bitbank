package click.bitbank.api.domain.accountBook.repository;

import click.bitbank.api.domain.accountBook.model.expenditure.Expenditure;
import click.bitbank.api.domain.accountBook.model.expenditure.ExpenditureType;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ExpenditureRepository extends ReactiveCrudRepository<Expenditure, Integer> {

    Flux<Expenditure> findByMemberId(int memberId); // 사용자의 모든 데이터 조회

    Flux<Expenditure> findByMemberIdAndExpenditureInfoContaining(int memberId, String searchKeyword);   // 검색어를 포함하는 사용자의 데이터 조회

    @Query("SELECT * FROM expenditure WHERE memberId = :memberId AND DATE(expenditureDate) BETWEEN :startDate AND :endDate")
    Flux<Expenditure> findByMemberIdAndExpenditureDateBetween(int memberId, String startDate, String endDate);  // 검색 기간에 해당하는 사용자의 데이터 조회

    @Query("SELECT * FROM expenditure WHERE memberId = :memberId AND expenditureInfo LIKE :searchKeyword AND DATE(expenditureDate) BETWEEN :startDate AND :endDate")
    Flux<Expenditure> findByMemberIdAndExpenditureDateBetweenAndExpenditureInfoContaining(int memberId, String startDate, String endDate, String searchKeyword);    // 검색어를 포함하고 검색 기간에 해당하는 사용자의 데이터 조회

    Flux<Expenditure> findByMemberIdAndExpenditureTypeIn(int memberId, List<ExpenditureType> expenditureType);  // 지출 유형과 일치하는 사용자의 데이터 조회

    Flux<Expenditure> findByMemberIdAndExpenditureTypeInAndExpenditureInfoContaining(int memberId, List<ExpenditureType> expenditureType, String searchKeyword);    // 지출 유형과 일치하고 검색어를 포함하는 사용자의 데이터 조회

    @Query("SELECT * FROM expenditure WHERE memberId = :memberId AND (DATE(expenditureDate) BETWEEN :startDate AND :endDate) AND (expenditureType IN (:expenditureType))")
    Flux<Expenditure> findByMemberIdAndExpenditureDateBetweenAndExpenditureTypeIn(int memberId, List<ExpenditureType> expenditureType, String startDate, String endDate);   // 검색 기간, 지출 유형에 해당하는 사용자의 데이터 조회

    @Query("SELECT * FROM expenditure WHERE memberId = :memberId AND expenditureType IN (:expenditureType) AND expenditureInfo LIKE :searchKeyword AND (DATE(expenditureDate) BETWEEN :startDate AND :endDate)")
    Flux<Expenditure> findByMemberIdAndExpenditureDateBetweenAndExpenditureInfoContainingAndExpenditureTypeIn(int memberId, List<ExpenditureType> expenditureType, String startDate, String endDate, String searchKeyword); // 지출 유형과 검색 기간에 해당하고 검색어를 포함하는 사용자의 데이터 조회

}
