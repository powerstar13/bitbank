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

    @Query("SELECT * FROM expenditure WHERE memberId = :memberId AND DATE(expenditureDate) BETWEEN :startDate AND :endDate")
    Flux<Expenditure> findByMemberIdAndExpenditureDateBetween(int memberId, String startDate, String endDate);

    @Query("SELECT * FROM expenditure WHERE memberId = :memberId AND expenditureInfo LIKE :searchKeyword AND DATE(expenditureDate) BETWEEN :startDate AND :endDate")
    Flux<Expenditure> findByMemberIdAndExpenditureDateBetweenAndExpenditureInfoContaining(int memberId, String startDate, String endDate, String searchKeyword);

    Flux<Expenditure> findByMemberIdAndExpenditureTypeIn(int memberId, List<ExpenditureType> expenditureType);

    Flux<Expenditure> findByMemberIdAndExpenditureTypeInAndExpenditureInfoContaining(int memberId, List<ExpenditureType> expenditureType, String searchKeyword);

    @Query("SELECT * FROM expenditure WHERE memberId = :memberId AND (DATE(expenditureDate) BETWEEN :startDate AND :endDate) AND (expenditureType IN (:expenditureType))")
    Flux<Expenditure> findByMemberIdAndExpenditureDateBetweenAndExpenditureTypeIn(int memberId, List<ExpenditureType> expenditureType, String startDate, String endDate);

    @Query("SELECT * FROM expenditure WHERE memberId = :memberId AND expenditureType IN (:expenditureType) AND expenditureInfo LIKE :searchKeyword AND (DATE(expenditureDate) BETWEEN :startDate AND :endDate)")
    Flux<Expenditure> findByMemberIdAndExpenditureDateBetweenAndExpenditureInfoContainingAndExpenditureTypeIn(int memberId, List<ExpenditureType> expenditureType, String startDate, String endDate, String searchKeyword);

}
