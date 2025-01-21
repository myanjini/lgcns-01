package ex02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("infoPrinter")
public class MemberInfoPrinter {
    private MemberDAO memberDAO;
    private MemberPrinter printer;
    
    @Autowired
    public void setMemberDAO(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }
    
    @Autowired
    @Qualifier("printer")
    public void setMemberPrinter(MemberPrinter printer) {
        this.printer = printer;
    }
    
    public void printMemberInfo(String email) {
        Member member = memberDAO.selectByEmail(email);
        if (member == null) {
            System.out.println("일치하는 데이터가 없습니다.");
            return;
        }
        printer.print(member);
        System.out.println();
    }
}
