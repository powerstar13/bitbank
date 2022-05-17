package click.bitbank.api.application.response.DTO;

import lombok.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountBookSearchByDailyDTO {

    String date;    // 날짜

    String day;     // 요일

    BigInteger accountBookTotalByDaily;   // 해당 날짜 가계부 총 금액

    List<AccountBookInfoDTO> accountBookInfoDTOList;    // 해당 날짜 가계부 내역 리스트

    public AccountBookSearchByDailyDTO(LocalDateTime date) {
        setAccountBookDate(date);   // 날짜, 요일 세팅
        this.accountBookTotalByDaily = BigInteger.valueOf(0);
        accountBookInfoDTOList = new ArrayList<>();
    }


    /**
     * `[yyyy년] MM월 dd일 O요일`로 세팅
     * @param date : LocalDateTime 타입의 가계부 날짜
     */
    public void setAccountBookDate(LocalDateTime date) {
        LocalDateTime firstDayThisYear = LocalDateTime.now().with(TemporalAdjusters.firstDayOfYear());

//        this.date = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        if (date.isBefore(firstDayThisYear)) {  // 올해의 가계부 내역이 아니라면, 년도 추가
            this.date = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        } else {
            this.date = date.format(DateTimeFormatter.ofPattern("MM월 dd일"));
        }
        this.day = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);   // 요일 지정
    }

    public void setAccountBookTotalByDaily(BigInteger moneyByDaily) {
        this.accountBookTotalByDaily = this.accountBookTotalByDaily.add(moneyByDaily);
    }

    public void setAccountBookInfoDTOList(AccountBookInfoDTO accountBookInfoDTO) {
        this.accountBookInfoDTOList.add(accountBookInfoDTO);
    }
}
