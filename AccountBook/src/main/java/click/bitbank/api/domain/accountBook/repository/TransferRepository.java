package click.bitbank.api.domain.accountBook.repository;

import click.bitbank.api.domain.accountBook.TransferType;
import click.bitbank.api.domain.accountBook.model.Transfer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface TransferRepository extends ReactiveCrudRepository<Transfer, Integer> {


    Flux<Transfer> findByMemberId(int memberId);

    Flux<Transfer> findByMemberIdAndTransferInfoContaining(int memberId, String searchKeyword);

    @Query("SELECT * FROM transfer WHERE memberId = :memberId AND DATE(transferDate) BETWEEN :startDate AND :endDate")
    Flux<Transfer> findByMemberIdAndTransferDateBetween(int memberId, String startDate, String endDate);

    @Query("SELECT * FROM transfer WHERE memberId = :memberId AND transferInfo LIKE :searchKeyword AND DATE(transferDate) BETWEEN :startDate AND :endDate")
    Flux<Transfer> findByMemberIdAndTransferDateBetweenAndTransferInfoContaining(int memberId, String startDate, String endDate, String searchKeyword);

    Flux<Transfer> findByMemberIdAndTransferTypeIn(int memberId, List<TransferType> transferType);

    Flux<Transfer> findByMemberIdAndTransferTypeInAndTransferInfoContaining(int memberId, List<TransferType> transferType, String searchKeyword);

    @Query("SELECT * FROM transfer WHERE memberId = :memberId AND (DATE(transferDate) BETWEEN :startDate AND :endDate) AND (transferType IN (:transferType))")
    Flux<Transfer> findByMemberIdAndTransferDateBetweenAndTransferTypeIn(int memberId, List<TransferType> transferType, String startDate, String endDate);

    @Query("SELECT * FROM transfer WHERE memberId = :memberId AND transferType IN (:transferType) AND transferInfo LIKE :searchKeyword AND (DATE(transferDate) BETWEEN :startDate AND :endDate)")
    Flux<Transfer> findByMemberIdAndTransferDateBetweenAndTransferInfoContainingAndTransferTypeIn(int memberId, List<TransferType> transferType, String startDate, String endDate, String searchKeyword);


}
