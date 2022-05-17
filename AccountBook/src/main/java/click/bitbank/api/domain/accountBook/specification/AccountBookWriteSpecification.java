package click.bitbank.api.domain.accountBook.specification;

import click.bitbank.api.domain.accountBook.DAO.AccountBookFactory;
import click.bitbank.api.domain.accountBook.model.AccountBookType;
import click.bitbank.api.domain.accountBook.model.Classification;
import click.bitbank.api.domain.accountBook.model.expenditure.Expenditure;
import click.bitbank.api.domain.accountBook.model.income.Income;
import click.bitbank.api.domain.accountBook.model.transfer.Transfer;
import click.bitbank.api.domain.accountBook.repository.AccountBookRepository;
import click.bitbank.api.domain.accountBook.repository.ExpenditureRepository;
import click.bitbank.api.domain.accountBook.repository.IncomeRepository;
import click.bitbank.api.domain.accountBook.repository.TransferRepository;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.infrastructure.exception.status.RegistrationFailException;
import click.bitbank.api.presentation.accountBook.request.AccountBookWriteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class AccountBookWriteSpecification {

    private final AccountBookRepository accountBookRepository;
    private final IncomeRepository incomeRepository;
    private final ExpenditureRepository expenditureRepository;
    private final TransferRepository transferRepository;
    private final AccountBookFactory accountBookFactory;

    @Transactional(rollbackFor = Exception.class)
    public Mono<Classification> accountBookExistCheckAndWrite(AccountBookWriteRequest request) {

        return accountBookRepository.findByAccountBookId(request.getAccountBookId())
            .hasElement()
            .flatMap(accountBook -> {
//                return this.write(request)
//                    .flatMap(savedAccountBook -> Mono.just(
//                        AccountBookWriteResponse.builder()
//                            .accountBookId(savedAccountBook.getAccountBookId())
//                            .build()
//                    ));


                AccountBookType accountBookType = request.getAccountBookType();
//                String accountName = request.getAccountName();
//                Timestamp createdTime = Timestamp.valueOf(request.getCreatedDate());
//                BigInteger price = request.getPrice();
                if (accountBookType == AccountBookType.I) {
                    // 수입
                    return this.incomeRepository.save(
                            this.accountBookFactory.incomeBuilder(
                                    request.getAccountName(),
                                    LocalDateTime.parse(request.getCreatedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                                    request.getIncomeType(),
                                    request.getPrice(),
                                    request.getMemberId()
                            )
                    ).switchIfEmpty(Mono.error(new RegistrationFailException(ExceptionMessage.WriteFailAccountBook.getMessage())));


                } else if (accountBookType == AccountBookType.E) {
                    // 지출
                    return this.expenditureRepository.save(
                            this.accountBookFactory.expenditureBuilder(
                                    request.getAccountName(),
                                    LocalDateTime.parse(request.getCreatedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                                    request.getExpenditureType(),
                                    request.getPrice(),
                                    request.getMemberId()
                            )
                    ).switchIfEmpty(Mono.error(new RegistrationFailException(ExceptionMessage.WriteFailAccountBook.getMessage())));

                } else if (accountBookType == AccountBookType.T) {
                    // 이체
                    return this.transferRepository.save(
                            this.accountBookFactory.transferBuilder(
                                    request.getAccountName(),
                                    LocalDateTime.parse(request.getCreatedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                                    request.getTransferType(),
                                    request.getPrice(),
                                    request.getMemberId()
                            )
                    ).switchIfEmpty(Mono.error(new RegistrationFailException(ExceptionMessage.WriteFailAccountBook.getMessage())));
//                    return this.transferRepository.save(
//                            this.accountBookFactory.transferBuilder(request.getAccountName(), request., request.getTransferType(), price)
//                    );
                }
                return Mono.empty();
            });
        }


//    private Mono<AccountBook> write(AccountBookWriteRequest request) {
//        AccountBookType accountBookType = request.getAccountBookType();
//        if (accountBookType == AccountBookType.I) {
//            return accountBookRepository.save(
//                accountBookFactory.builder(
//                    request.getAccountBookType(),
//                    request.getCreatedDate(),
//                    request.getAccountName(),
//                    request.getPrice()
//                )
//            );
//        } else if (accountBookType == AccountBookType.E) {
//            return accountBookRepository.save(
//                accountBookFactory.builder(
//                    request.getAccountBookType(),
//                    request.getCreatedDate(),
//                    request.getAccountName(),
//                    request.getPrice()
//                )
//            );
//        } else if (accountBookType == AccountBookType.T) {
//            return accountBookRepository.save(
//                accountBookFactory.builder(
//                    request.getAccountBookType(),
//                    request.getCreatedDate(),
//                    request.getAccountName(),
//                    request.getPrice()
//                )
//            );
//        }
//        return null;
//    }

//    private Mono<Income> writeIncome(AccountBookWriteRequest request) {
//        return this.incomeRepository.save(
//                this.accountBookFactory.incomeBuilder(
//                        request.getAccountName(),
//                        LocalDateTime.parse(request.getCreatedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
//                        request.getIncomeType(),
//                        request.getPrice()
//                )
//        ).switchIfEmpty(Mono.error(new RegistrationFailException(ExceptionMessage.WriteFailAccountBook.getMessage())));
//    }
//
//    private Mono<Expenditure> writeExpenditure(AccountBookWriteRequest request) {
//        return this.expenditureRepository.save(
//                this.accountBookFactory.expenditureBuilder(
//                        request.getAccountName(),
//                        LocalDateTime.parse(request.getCreatedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
//                        request.getExpenditureType(),
//                        request.getPrice()
//                )
//        ).switchIfEmpty(Mono.error(new RegistrationFailException(ExceptionMessage.WriteFailAccountBook.getMessage())));
//    }
//
//    private Mono<Transfer> writeTransfer(AccountBookWriteRequest request) {
//        return this.transferRepository.save(
//                this.accountBookFactory.transferBuilder(
//                        request.getAccountName(),
//                        LocalDateTime.parse(request.getCreatedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
//                        request.getTransferType(),
//                        request.getPrice()
//                )
//        ).switchIfEmpty(Mono.error(new RegistrationFailException(ExceptionMessage.WriteFailAccountBook.getMessage())));
//    }
}
