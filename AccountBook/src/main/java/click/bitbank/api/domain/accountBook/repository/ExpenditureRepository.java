package click.bitbank.api.domain.accountBook.repository;

import click.bitbank.api.application.response.DTO.DailyTotalDTO;
import click.bitbank.api.application.response.DTO.DonutGraphDTO;
import click.bitbank.api.application.response.DTO.WeeklyTotalDTO;

import click.bitbank.api.domain.accountBook.model.expenditure.Expenditure;
import click.bitbank.api.domain.accountBook.model.expenditure.ExpenditureType;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    // 특정 월
    @Query(
        "SELECT SUM(expenditureMoney) " +
        "FROM expenditure " +
        "WHERE " +
        "    memberId = :memberId " +
        "    AND DATE_FORMAT(expenditureDate, '%Y-%c') = CONCAT(DATE_FORMAT(NOW(), '%Y-'), :month)"
    )
    Mono<Long> monthlyTotal(@Param("memberId") int memberId, @Param("month") int month);
    
    // 주차 별
    @Query(
        "SELECT " +
        "    WEEK(expenditureDate, 2) AS `week`," +
        "    SUM(expenditureMoney) AS `total` " +
        "FROM expenditure " +
        "WHERE " +
        "    memberId = :memberId " +
        "    AND DATE_FORMAT(expenditureDate, '%Y-%c') = CONCAT(DATE_FORMAT(NOW(), '%Y-'), :month)" +
        "GROUP BY `week`" +
        "ORDER BY `week`"
    )
    Flux<WeeklyTotalDTO> weeklyTotal(@Param("memberId") int memberId, @Param("month") int month);
    
    // 카테고리 별
    @Query(
        "SELECT " +
        "    ROW_NUMBER() OVER(ORDER BY SUM(expenditureMoney) DESC) AS `id`," +
        "    expenditureType AS `name`," +
        "    SUM(expenditureMoney) AS `quantity`, " +
        "    ROUND((SUM(expenditureMoney) * 100) / SUM(SUM(expenditureMoney)) OVER(), 0)  AS `percentage` " +
        "FROM expenditure " +
        "WHERE " +
        "    memberId = :memberId " +
        "    AND DATE_FORMAT(expenditureDate, '%Y-%c') = CONCAT(DATE_FORMAT(NOW(), '%Y-'), :month)" +
        "GROUP BY `name`" +
        "ORDER BY `quantity` DESC"
    )
    Flux<DonutGraphDTO> categoryTotal(@Param("memberId") int memberId, @Param("month") int month);
    
    // 일자 별
    @Query(
        "SELECT " +
        "    DATE_FORMAT(expenditureDate, '%m.%d') AS `day`," +
        "    SUM(expenditureMoney) AS `total` " +
        "FROM expenditure " +
        "WHERE " +
        "    memberId = :memberId " +
        "    AND DATE_FORMAT(expenditureDate, '%Y-%c') = CONCAT(DATE_FORMAT(NOW(), '%Y-'), :month)" +
        "GROUP BY `day`" +
        "ORDER BY `day`"
    )
    Flux<DailyTotalDTO> dailyTotal(@Param("memberId") int memberId, @Param("month") int month);
}
