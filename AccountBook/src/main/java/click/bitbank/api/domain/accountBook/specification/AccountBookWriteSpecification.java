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
     * 가계부 작성
     * @param request AccountBookWriteRequest
     * @return Mono<AccountBookWriteResponse>
     */
    @Transactional(rollbackFor = Exception.class)
    public Mono<AccountBookWriteResponse> accountBookExistCheckAndWrite(AccountBookWriteRequest request) {
        AccountBookType accountBookType = request.getAccountBookType();
        if (accountBookType == AccountBookType.I) {
            return this.writeIncome(request)
                .flatMap(income -> Mono.just(
                    AccountBookWriteResponse.builder()
                        .accountBookId(income.getIncomeId())
                        .build()
                ));
        } else if (accountBookType == AccountBookType.P) {
            return this.writeExpenditure(request)
                .flatMap(expenditure -> Mono.just(
                    AccountBookWriteResponse.builder()
                        .accountBookId(expenditure.getExpenditureId())
                        .build()
                ));
        } else if (accountBookType == AccountBookType.T) {
            return this.writeTransfer(request)
                .flatMap(transfer -> Mono.just(
                    AccountBookWriteResponse.builder()
                        .accountBookId(transfer.getTransferId())
                        .build()
                ));
        }
        return Mono.error(new RegistrationFailException(ExceptionMessage.WriteFailAccountBook.getMessage()));
    }

    /**
     * 수입 작성
     * @param request: AccountBookWriteRequest
     * @return Mono<Income>
     */
    private Mono<Income> writeIncome(AccountBookWriteRequest request) {
        return incomeRepository.save(
            accountBookFactory.incomeBuilder(
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
     * @return Mono<Expenditure>
     */
    private Mono<Expenditure> writeExpenditure(AccountBookWriteRequest request) {
        return expenditureRepository.save(
            accountBookFactory.expenditureBuilder(
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
     * @return Mono<Transfer>
     */
    private Mono<Transfer> writeTransfer(AccountBookWriteRequest request) {
        return transferRepository.save(
            accountBookFactory.transferBuilder(
                request.getAccountName(),
                LocalDateTime.parse(request.getCreatedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getTransferType(),
                request.getPrice(),
                request.getMemberId()
            )
        ).switchIfEmpty(Mono.error(new RegistrationFailException(ExceptionMessage.WriteFailAccountBook.getMessage())));
    }
}
