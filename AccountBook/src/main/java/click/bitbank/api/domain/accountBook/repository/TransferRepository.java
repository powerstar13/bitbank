package click.bitbank.api.domain.accountBook.repository;

import click.bitbank.api.domain.accountBook.model.transfer.Transfer;
import click.bitbank.api.domain.accountBook.model.transfer.TransferType;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface TransferRepository extends ReactiveCrudRepository<Transfer, Integer> {


    Flux<Transfer> findByMemberId(int memberId);    // 사용자의 모든 데이터 조회

    Flux<Transfer> findByMemberIdAndTransferInfoContaining(int memberId, String searchKeyword); // 검색어를 포함하는 사용자의 데이터 조회

    @Query("SELECT * FROM transfer WHERE memberId = :memberId AND DATE(transferDate) BETWEEN :startDate AND :endDate")
    Flux<Transfer> findByMemberIdAndTransferDateBetween(int memberId, String startDate, String endDate);    // 검색 기간에 해당하는 사용자의 데이터 조회

    @Query("SELECT * FROM transfer WHERE memberId = :memberId AND transferInfo LIKE :searchKeyword AND DATE(transferDate) BETWEEN :startDate AND :endDate")
    Flux<Transfer> findByMemberIdAndTransferDateBetweenAndTransferInfoContaining(int memberId, String startDate, String endDate, String searchKeyword); // 검색어를 포함하고 검색 기간에 해당하는 사용자의 데이터 조회

    Flux<Transfer> findByMemberIdAndTransferTypeIn(int memberId, List<TransferType> transferType);  // 이체 유형과 일치하는 사용자의 데이터 조회

    Flux<Transfer> findByMemberIdAndTransferTypeInAndTransferInfoContaining(int memberId, List<TransferType> transferType, String searchKeyword);   // 이체 유형과 일치하고 검색어를 포함하는 사용자의 데이터 조회

    @Query("SELECT * FROM transfer WHERE memberId = :memberId AND (DATE(transferDate) BETWEEN :startDate AND :endDate) AND (transferType IN (:transferType))")
    Flux<Transfer> findByMemberIdAndTransferDateBetweenAndTransferTypeIn(int memberId, List<TransferType> transferType, String startDate, String endDate);  // 검색 기간, 이체 유형에 해당하는 사용자의 데이터 조회

    @Query("SELECT * FROM transfer WHERE memberId = :memberId AND transferType IN (:transferType) AND transferInfo LIKE :searchKeyword AND (DATE(transferDate) BETWEEN :startDate AND :endDate)")
    Flux<Transfer> findByMemberIdAndTransferDateBetweenAndTransferInfoContainingAndTransferTypeIn(int memberId, List<TransferType> transferType, String startDate, String endDate, String searchKeyword);   // 이체 유형과 검색 기간에 해당하고 검색어를 포함하는 사용자의 데이터 조회


}
