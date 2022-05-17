package click.bitbank.api.domain.accountBook.DAO;

import click.bitbank.api.domain.accountBook.model.Expenditure;
import click.bitbank.api.domain.accountBook.model.Income;
import click.bitbank.api.domain.accountBook.model.Transfer;

public interface AccountBookFactory {

    Expenditure expenditureBuilder();
    Income incomeBuilder();
    Transfer transferBuilder();
}
