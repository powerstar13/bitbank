package click.bitbank.api.domain.model.member;

public interface MemberFactory {

    Member memberBuilder(String memberName, String memberPassword, MemberType memberType); // 회원 정보 세팅
}
