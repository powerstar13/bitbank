package click.bitbank.api.domain.accountBook.DAO;

import click.bitbank.api.domain.accountBook.model.AccountBook;
import click.bitbank.api.domain.accountBook.model.AccountBookType;
import click.bitbank.api.domain.accountBook.model.Classification;
import click.bitbank.api.domain.accountBook.model.expenditure.Expenditure;
import click.bitbank.api.domain.accountBook.model.expenditure.ExpenditureType;
import click.bitbank.api.domain.accountBook.model.income.Income;
import click.bitbank.api.domain.accountBook.model.income.IncomeType;
import click.bitbank.api.domain.accountBook.model.transfer.Transfer;
import click.bitbank.api.domain.accountBook.model.transfer.TransferType;
import click.bitbank.api.presentation.accountBook.request.AccountBookWriteRequest;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface AccountBookFactory {
    AccountBook builder(AccountBookType accountBookType, LocalDateTime accountBookDate, String accountBookInfo, BigInteger accountBookMoney);
    Income incomeBuilder(String incomeInfo, LocalDateTime incomeDate, IncomeType incomeType, BigInteger incomeMoney);
    Expenditure expenditureBuilder(String expenditureInfo, LocalDateTime expenditureDate, ExpenditureType expenditureType, BigInteger expenditureMoney);
    Transfer transferBuilder(String transferInfo, LocalDateTime transferDate, TransferType transferType, BigInteger transferMoney);
}
