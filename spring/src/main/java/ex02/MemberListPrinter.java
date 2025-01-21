package ex02;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("listPrinter")
public class MemberListPrinter {
    @Autowired
    private MemberDAO memberDAO;
    
    @Autowired
    // @Qualifier("summaryPrinter")
    private MemberSummaryPrinter printer;
    
//    public MemberListPrinter(MemberDAO memberDAO, MemberPrinter printer) {
//        this.memberDAO = memberDAO;
//        this.printer = printer;
//    }
    
    public void printAll() {
        Collection<Member> members = memberDAO.selectAll();
        members.forEach(member -> printer.print(member));
    }
}
