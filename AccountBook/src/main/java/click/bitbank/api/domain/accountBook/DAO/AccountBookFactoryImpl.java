package click.bitbank.api.domain.accountBook.DAO;

import click.bitbank.api.domain.accountBook.model.AccountBook;
import click.bitbank.api.domain.accountBook.model.AccountBookType;
import click.bitbank.api.domain.accountBook.model.expenditure.Expenditure;
import click.bitbank.api.domain.accountBook.model.expenditure.ExpenditureType;
import click.bitbank.api.domain.accountBook.model.income.Income;
import click.bitbank.api.domain.accountBook.model.income.IncomeType;
import click.bitbank.api.domain.accountBook.model.transfer.Transfer;
import click.bitbank.api.domain.accountBook.model.transfer.TransferType;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.Timestamp;

@Component
public class AccountBookFactoryImpl implements AccountBookFactory {

    @Override
    public AccountBook builder(AccountBookType accountBookType, Timestamp accountBookDate, String accountBookInfo, BigInteger accountBookMoney) {
//        if (accountBookType == AccountBookType.I) {
//
//        }
        return AccountBook.builder()
                .accountBookDate(accountBookDate)
                .accountBookInfo(accountBookInfo)
                .accountBookMoney(accountBookMoney)
                .build();
    }

    @Override
    public Income incomeBuilder(String incomeInfo, Timestamp incomeDate, IncomeType incomeType, BigInteger incomeMoney) {
        return Income.builder()
                .incomeInfo(incomeInfo)
                .incomeDate(incomeDate)
                .incomeType(incomeType)
                .incomeMoney(incomeMoney)
                .build();
    }

    @Override
    public Expenditure expenditureBuilder(String expenditureInfo, Timestamp expenditureDate, ExpenditureType expenditureType, BigInteger expenditureMoney) {
        return Expenditure.builder()
                .expenditureInfo(expenditureInfo)
                .expenditureDate(expenditureDate)
                .expenditureType(expenditureType)
                .expenditureMoney(expenditureMoney)
                .build();
    }

    @Override
    public Transfer transferBuilder(String transferInfo, Timestamp transferDate,TransferType transferType, BigInteger transferMoney) {
        return Transfer.builder()
                .transferInfo(transferInfo)
                .transferDate(transferDate)
                .transferType(transferType)
                .transferMoney(transferMoney)
                .build();
    }
}
