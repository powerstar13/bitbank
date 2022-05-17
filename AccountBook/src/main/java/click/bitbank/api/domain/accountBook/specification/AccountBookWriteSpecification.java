package click.bitbank.api.domain.accountBook.specification;

import click.bitbank.api.application.response.AccountBookWriteResponse;
import click.bitbank.api.domain.accountBook.DAO.AccountBookFactory;
import click.bitbank.api.domain.accountBook.model.AccountBook;
import click.bitbank.api.domain.accountBook.model.AccountBookType;
import click.bitbank.api.domain.accountBook.repository.AccountBookRepository;
import click.bitbank.api.domain.accountBook.repository.ExpenditureRepository;
import click.bitbank.api.domain.accountBook.repository.IncomeRepository;
import click.bitbank.api.domain.accountBook.repository.TransferRepository;
import click.bitbank.api.presentation.accountBook.request.AccountBookWriteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AccountBookWriteSpecification {

    private final AccountBookRepository accountBookRepository;
    private final IncomeRepository incomeRepository;
    private final ExpenditureRepository expenditureRepository;
    private final TransferRepository transferRepository;
    private final AccountBookFactory accountBookFactory;

    public Mono<AccountBookWriteResponse> accountBookExistCheckAndWrite(AccountBookWriteRequest request) {
        return accountBookRepository.findByAccountBookId(request.getAccountBookId())
            .hasElement()
            .flatMap(accountBook -> {
                return this.write(request)
                    .flatMap(savedAccountBook -> Mono.just(
                        AccountBookWriteResponse.builder()
                            .accountBookId(savedAccountBook.getAccountBookId())
                            .build()
                    ));
//                AccountBookType accountBookType = request.getAccountBookType();
//                String accountName = request.getAccountName();
//                Timestamp createdTime = Timestamp.valueOf(request.getCreatedDate());
//                BigInteger price = request.getPrice();
//                if (accountBookType == AccountBookType.I) {
//                    // 수입

//
//                } else if (accountBookType == AccountBookType.E) {
//                    // 지출
//
//                } else if (accountBookType == AccountBookType.T) {
//                    // 이체
//                    return this.transferRepository.save(
//                            this.accountBookFactory.transferBuilder(accountName, createdTime, request.getTransferType(), price)
//                    );
//                }
            });
        }

    private Mono<AccountBook> write(AccountBookWriteRequest request) {
        AccountBookType accountBookType = request.getAccountBookType();
        if (accountBookType == AccountBookType.I) {
            return accountBookRepository.save(
                accountBookFactory.builder(
                    request.getAccountBookType(),
                    request.getCreatedDate(),
                    request.getAccountName(),
                    request.getPrice()
                )
            );
        } else if (accountBookType == AccountBookType.E) {
            return accountBookRepository.save(
                accountBookFactory.builder(
                    request.getAccountBookType(),
                    request.getCreatedDate(),
                    request.getAccountName(),
                    request.getPrice()
                )
            );
        } else if (accountBookType == AccountBookType.T) {
            return accountBookRepository.save(
                accountBookFactory.builder(
                    request.getAccountBookType(),
                    request.getCreatedDate(),
                    request.getAccountName(),
                    request.getPrice()
                )
            );
        }
        return null;
    }

//    private Mono<Income> writeIncome(AccountBookWriteRequest request) {
//        return this.incomeRepository.save(
//            this.accountBookFactory.incomeBuilder(
//                request.getAccountName(),
//                Timestamp.valueOf(request.getCreatedDate()),
//                request.getIncomeType(),
//                request.getPrice()
//            )
//        ).switchIfEmpty(Mono.error(new RegistrationFailException(ExceptionMessage.WriteFailAccountBook.getMessage())));
//    }
//
//    private Mono<Expenditure> writeExpenditure(AccountBookWriteRequest request) {
//        return this.expenditureRepository.save(
//            this.accountBookFactory.expenditureBuilder(
//                request.getAccountName(),
//                Timestamp.valueOf(request.getCreatedDate()),
//                request.getExpenditureType(),
//                request.getPrice()
//            )
//        ).switchIfEmpty(Mono.error(new RegistrationFailException(ExceptionMessage.WriteFailAccountBook.getMessage())));
//    }
//
//    private Mono<Transfer> writeTransfer(AccountBookWriteRequest request) {
//        return this.transferRepository.save(
//            this.accountBookFactory.transferBuilder(
//                accountName,
//                createdTime,
//                request.getTransferType(),
//                price)
//        );
//    }
}
