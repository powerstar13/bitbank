package click.bitbank.api;

import click.bitbank.api.domain.accountBook.IncomeType;
import click.bitbank.api.domain.accountBook.model.Income;
import click.bitbank.api.domain.accountBook.repository.IncomeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@SpringBootTest
class AccountBookApplicationTests {

	@Test
	void contextLoads() {
	}

}
