package click.bitbank.api.domain.accountBook.specification;

import click.bitbank.api.application.response.AccountBookWriteResponse;
import click.bitbank.api.domain.accountBook.DAO.AccountBookFactory;
import click.bitbank.api.domain.accountBook.model.AccountBookType;
import click.bitbank.api.domain.accountBook.model.expenditure.Expenditure;
import click.bitbank.api.domain.accountBook.model.income.Income;
import click.bitbank.api.domain.accountBook.model.transfer.Transfer;
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

    private final IncomeRepository incomeRepository;
    private final ExpenditureRepository expenditureRepository;
    private final TransferRepository transferRepository;
    private final AccountBookFactory accountBookFactory;

    /**
     * 가계부 중복 검사 및 작성
     * @param request AccountBookWriteRequest
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Mono<AccountBookWriteResponse> accountBookExistCheckAndWrite(AccountBookWriteRequest request) {
        AccountBookType accountBookType = request.getAccountBookType();
        if (accountBookType == AccountBookType.I) {
            return incomeRepository.findByIncomeId(request.getAccountBookId())
                .hasElement()
                .flatMap(incomeBook -> {
                    return this.writeIncome(request)
                        .flatMap(income -> Mono.just(
                            AccountBookWriteResponse.builder()
                                .accountBookId(income.getIncomeId())
                                .build()
                    ));
                });
        } else if (accountBookType == AccountBookType.P) {
            return expenditureRepository.findByExpenditureId(request.getAccountBookId())
                .hasElement()
                .flatMap(expenditureBook -> {
                    return this.writeExpenditure(request)
                        .flatMap(expenditure -> Mono.just(
                            AccountBookWriteResponse.builder()
                                .accountBookId(expenditure.getExpenditureId())
                                .build()
                        ));
                });
        } else if (accountBookType == AccountBookType.T) {
            return transferRepository.findByTransferId(request.getAccountBookId())
                .hasElement()
                .flatMap(transferBook -> {
                    return this.writeTransfer(request)
                        .flatMap(transfer -> Mono.just(
                            AccountBookWriteResponse.builder()
                                .accountBookId(transfer.getTransferId())
                                .build()
                        ));
                });
        }
        return Mono.error(new RegistrationFailException(ExceptionMessage.WriteFailAccountBook.getMessage()));
    }

    /**
     * 수입 작성
     * @param request: AccountBookWriteRequest
     * @return
     */
    private Mono<Income> writeIncome(AccountBookWriteRequest request) {
        return this.incomeRepository.save(
            this.accountBookFactory.incomeBuilder(
                request.getAccountName(),
                LocalDateTime.parse(request.getCreatedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getIncomeType(),
                request.getPrice(),
                request.getMemberId()
            )
        ).switchIfEmpty(Mono.error(new RegistrationFailException(ExceptionMessage.WriteFailAccountBook.getMessage())));
    }

    /**
     * 지출 작성
     * @param request: AccountBookWriteRequest
     * @return
     */
    private Mono<Expenditure> writeExpenditure(AccountBookWriteRequest request) {
        return this.expenditureRepository.save(
            this.accountBookFactory.expenditureBuilder(
                request.getAccountName(),
                LocalDateTime.parse(request.getCreatedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getExpenditureType(),
                request.getPrice(),
                request.getMemberId()
            )
        ).switchIfEmpty(Mono.error(new RegistrationFailException(ExceptionMessage.WriteFailAccountBook.getMessage())));
    }

    /**
     * 이체 작성
     * @param request: AccountBookWriteRequest
     * @return
     */
    private Mono<Transfer> writeTransfer(AccountBookWriteRequest request) {
        return this.transferRepository.save(
            this.accountBookFactory.transferBuilder(
                request.getAccountName(),
                LocalDateTime.parse(request.getCreatedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getTransferType(),
                request.getPrice(),
                request.getMemberId()
            )
        ).switchIfEmpty(Mono.error(new RegistrationFailException(ExceptionMessage.WriteFailAccountBook.getMessage())));
    }
}
