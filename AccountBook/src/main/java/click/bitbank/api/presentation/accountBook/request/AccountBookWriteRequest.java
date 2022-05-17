package click.bitbank.api.presentation.accountBook.request;

import click.bitbank.api.domain.accountBook.model.AccountBookType;
import click.bitbank.api.domain.accountBook.model.expenditure.ExpenditureType;
import click.bitbank.api.domain.accountBook.model.income.IncomeType;
import click.bitbank.api.domain.accountBook.model.transfer.TransferType;
import click.bitbank.api.infrastructure.exception.status.BadRequestException;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.presentation.shared.request.RequestVerify;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountBookWriteRequest implements RequestVerify {
    private int memberId; // 회원 고유번호

    private int accountBookId;

    private AccountBookType accountBookType; // 가계부 내역 유형

    private IncomeType incomeType; // 수입 유형

    private ExpenditureType expenditureType; // 지출 유형

    private TransferType transferType; // 이체 유형

    private String accountName; // 거래처명

    private BigInteger price; // 금액

    private String createdDate; // 작성한 날짜

    @Override
    public void verify() {
        verifyAccountBookType();
        if (StringUtils.isBlank(accountName)) throw new BadRequestException(ExceptionMessage.IsRequiredAccountName.getMessage());
        if (price.compareTo(new BigInteger("0")) == -1) throw new BadRequestException(ExceptionMessage.IsRequiredPositiveNumber.getMessage());
        if (StringUtils.isBlank(createdDate)) throw new BadRequestException(ExceptionMessage.IsRequiredCreatedDate.getMessage());
    }

    /**
     * AccountBookType 유효성검사
     * I,E,T,null인 경우 처리
     */
    private void verifyAccountBookType() {

        if (accountBookType == null) throw new BadRequestException(ExceptionMessage.IsRequiredAccountType.getMessage());

        if (accountBookType == AccountBookType.I && incomeType == null) {
            throw new BadRequestException(ExceptionMessage.IsRequiredIncomeType.getMessage());
        } else if (accountBookType == AccountBookType.E && expenditureType == null) {
            throw new BadRequestException(ExceptionMessage.IsRequiredExpenditureType.getMessage());
        } else if (accountBookType == AccountBookType.T && transferType == null) {
            throw new BadRequestException(ExceptionMessage.IsRequiredTransferType.getMessage());
        }
    }
}
