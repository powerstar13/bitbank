package click.bitbank.api.domain.accountBook.repository;

import click.bitbank.api.domain.accountBook.ExpenditureType;
import click.bitbank.api.domain.accountBook.model.Expenditure;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ExpenditureRepository extends ReactiveCrudRepository<Expenditure, Integer> {

    Flux<Expenditure> findByMemberId(int memberId);

    Flux<Expenditure> findByMemberIdAndExpenditureInfoContaining(int memberId, String searchKeyword);

    @Query("SELECT * FROM expenditure WHERE memberId = :memberId AND DATE(incomeDate) BETWEEN :startDate AND :endDate")
    Flux<Expenditure> findByMemberIdAndExpenditureDateBetween(int memberId, String startDate, String endDate);

    @Query("SELECT * FROM expenditure WHERE memberId = :memberId AND incomeInfo LIKE :searchKeyword AND DATE(incomeDate) BETWEEN :startDate AND :endDate")
    Flux<Expenditure> findByMemberIdAndExpenditureDateBetweenAndExpenditureInfoContaining(int memberId, String startDate, String endDate, String searchKeyword);

    Flux<Expenditure> findByMemberIdAndExpenditureTypeIn(int memberId, List<ExpenditureType> incomeType);

    Flux<Expenditure> findByMemberIdAndExpenditureTypeInAndExpenditureInfoContaining(int memberId, List<ExpenditureType> incomeType, String searchKeyword);

    @Query("SELECT * FROM expenditure WHERE memberId = :memberId AND (DATE(incomeDate) BETWEEN :startDate AND :endDate) AND (incomeType IN (:incomeType))")
    Flux<Expenditure> findByMemberIdAndExpenditureDateBetweenAndExpenditureTypeIn(int memberId, List<ExpenditureType> incomeType, String startDate, String endDate);

    @Query("SELECT * FROM expenditure WHERE memberId = :memberId AND incomeType IN (:incomeType) AND incomeInfo LIKE :searchKeyword AND (DATE(incomeDate) BETWEEN :startDate AND :endDate)")
    Flux<Expenditure> findByMemberIdAndExpenditureDateBetweenAndExpenditureInfoContainingAndExpenditureTypeIn(int memberId, List<ExpenditureType> incomeType, String startDate, String endDate, String searchKeyword);

}
