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

public interface AccountBookFactory {
    AccountBook builder(AccountBookType accountBookType, Timestamp accountBookDate, String accountBookInfo, BigInteger accountBookMoney);
    Income incomeBuilder(String incomeInfo, Timestamp incomeDate, IncomeType incomeType, BigInteger incomeMoney);
    Expenditure expenditureBuilder(String expenditureInfo, Timestamp expenditureDate, ExpenditureType expenditureType, BigInteger expenditureMoney);
    Transfer transferBuilder(String transferInfo, Timestamp transferDate, TransferType transferType, BigInteger transferMoney);
}
