package click.bitbank.api.presentation.accountBook.request;

import click.bitbank.api.domain.accountBook.ExpenditureType;
import click.bitbank.api.domain.accountBook.IncomeType;
import click.bitbank.api.domain.accountBook.SearchDateType;
import click.bitbank.api.domain.accountBook.TransferType;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountBookSearchRequest {

    int memberId;

    String searchKeyword;   // 검색어

    SearchDateType searchDateType;  // 기간

    String searchStartDate; // 검색 시작일

    String searchEndDate;   // 검색 종료일

    ExpenditureType expenditureType;    // 지출 유형

    IncomeType incomeType;  // 수입 유형

    TransferType transferType;  // 이체 유형

}
