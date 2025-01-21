package ex02;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class MemberRegisterService {
    private MemberDAO memberDAO;
    
    public MemberRegisterService(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }
    
    public long regist(RegisterRequest req) {
        Member member = memberDAO.selectByEmail(req.getEmail());
        if (member != null) {
            throw new DuplicateMemberException("이메일 중복 " + req.getEmail());
        }
        
        Member newMember = new Member(req.getEmail(), req.getPassword(), req.getName(), LocalDateTime.now());
        memberDAO.insert(newMember);
        return newMember.getId();
    }
}
