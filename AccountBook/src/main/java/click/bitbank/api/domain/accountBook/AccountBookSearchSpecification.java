package click.bitbank.api.domain.accountBook;

import click.bitbank.api.domain.accountBook.repository.ExpenditureRepository;
import click.bitbank.api.domain.accountBook.repository.IncomeRepository;
import click.bitbank.api.domain.accountBook.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountBookSearchSpecification {

    private final ExpenditureRepository expenditureRepository;
    private final IncomeRepository incomeRepository;
    private final TransferRepository transferRepository;

}
