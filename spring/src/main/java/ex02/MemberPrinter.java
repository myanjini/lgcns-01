package ex02;

import java.time.format.DateTimeFormatter;

import org.springframework.lang.Nullable;

public class MemberPrinter {
    private DateTimeFormatter dateTimeFormatter;
    
    @Nullable
    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }
    
    public void print(Member member) {
        if (dateTimeFormatter == null) {
            System.out.printf("회원정보: ID=%s, 이메일=%s, 이름=%s, 등록일=%tF\n", 
                member.getId(), member.getEmail(), member.getName(), 
                member.getRegisterDateTime());
        } else {
            System.out.printf("회원정보: ID=%s, 이메일=%s, 이름=%s, 등록일=%tF\n", 
                member.getId(), member.getEmail(), member.getName(), 
                dateTimeFormatter.format(member.getRegisterDateTime()));
        }
    }
}
