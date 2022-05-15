package click.bitbank.api.domain.accountBook.repository;

import click.bitbank.api.domain.accountBook.IncomeType;
import click.bitbank.api.domain.accountBook.model.Income;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface IncomeRepository extends ReactiveCrudRepository<Income, Integer> {


    Flux<Income> findByMemberId(int memberId);

    Flux<Income> findByMemberIdAndIncomeInfoContaining(int memberId, String searchKeyword);

    @Query("SELECT * FROM income WHERE memberId = :memberId AND DATE(incomeDate) BETWEEN :startDate AND :endDate")
    Flux<Income> findByMemberIdAndIncomeDateBetween(int memberId, String startDate, String endDate);

    @Query("SELECT * FROM income WHERE memberId = :memberId AND incomeInfo LIKE :searchKeyword AND DATE(incomeDate) BETWEEN :startDate AND :endDate")
    Flux<Income> findByMemberIdAndIncomeDateBetweenAndIncomeInfoContaining(int memberId, String startDate, String endDate, String searchKeyword);

    Flux<Income> findByMemberIdAndIncomeTypeIn(int memberId, List<IncomeType> incomeType);

    Flux<Income> findByMemberIdAndIncomeTypeInAndIncomeInfoContaining(int memberId, List<IncomeType> incomeType, String searchKeyword);

    @Query("SELECT * FROM income WHERE memberId = :memberId AND (DATE(incomeDate) BETWEEN :startDate AND :endDate) AND (incomeType IN (:incomeType))")
    Flux<Income> findByMemberIdAndIncomeDateBetweenAndIncomeTypeIn(int memberId, List<IncomeType> incomeType, String startDate, String endDate);

    @Query("SELECT * FROM income WHERE memberId = :memberId AND incomeType IN (:incomeType) AND incomeInfo LIKE :searchKeyword AND (DATE(incomeDate) BETWEEN :startDate AND :endDate)")
    Flux<Income> findByMemberIdAndIncomeDateBetweenAndIncomeInfoContainingAndIncomeTypeIn(int memberId, List<IncomeType> incomeType, String startDate, String endDate, String searchKeyword);




//    Flux<Income> findByMemberIdAndIncomeType(int memberId, IncomeType incomeType);
//
//    Flux<Income> findByMemberIdAndIncomeTypeAndIncomeInfoContaining(int memberId, IncomeType incomeType, String searchKeyword);
//
//    @Query("SELECT * FROM income WHERE memberId = :memberId AND DATE(incomeDate) BETWEEN :startDate AND :endDate")
//    Flux<Income> findByMemberIdAndIncomeTypeAndIncomeDateBetween(int memberId, IncomeType incomeType, String startDate, String endDate);
//
//    @Query("SELECT * FROM income WHERE memberId = :memberId AND incomeInfo LIKE :searchKeyword AND DATE(incomeDate) BETWEEN :startDate AND :endDate")
//    Flux<Income> findByMemberIdAndIncomeTypeAndIncomeDateBetweenAndIncomeInfoContaining(int memberId, IncomeType incomeType, String startDate, String endDate, String searchKeyword);



    // 최근 일주일
//    @Query("SELECT * FROM income WHERE incomeDate BETWEEN DATE_ADD(NOW(), INTERVAL -1 WEEK ) AND NOW()")

//    // 이번 주 수입 조회
//    @Query("SELECT * FROM income WHERE memberId = :memberId AND incomeDate BETWEEN (SELECT ADDDATE(CURDATE(), - WEEKDAY(CURDATE()) + 0 )) AND (SELECT ADDDATE(CURDATE(), - WEEKDAY(CURDATE()) + 6 ))")
//    Flux<Income> findByMemberIdInWeek(int memberId);
//
//    @Query("SELECT * FROM income WHERE memberId = :memberId AND MONTH(incomeDate) = MONTH(CURRENT_DATE()) AND YEAR(incomeDate) = YEAR(CURRENT_DATE())")
//    Flux<Income> findByMemberIdInMonth(int memberId);
//    @Query("SELECT * FROM income WHERE memberId = :memberId AND DATE(incomeDate) >= DATE_FORMAT(NOW(), '%Y-01-01');")
//    Flux<Income> findByMemberIdInYear(int memberId);
//
//    @Query("SELECT * FROM income WHERE memberId = :memberId AND incomeDate BETWEEN DATE_ADD(NOW(), INTERVAL -3 MONTH ) AND NOW()")
//    Flux<Income> findByMemberIdInM3(int memberId);




}
