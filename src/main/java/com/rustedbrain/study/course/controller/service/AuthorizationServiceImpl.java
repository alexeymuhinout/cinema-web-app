package com.rustedbrain.study.course.controller.service;

import com.rustedbrain.study.course.controller.repository.MemberRepository;
import com.rustedbrain.study.course.model.authorization.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private MemberRepository memberRepository;


    @Override
    public void registerMember(Member member) throws IllegalArgumentException {
        memberRepository.save(member);
    }

    @Override
    public void deleteMember(Member member) throws IllegalArgumentException {
        memberRepository.deleteById(member.getId());
    }

    @Override
    public boolean isRegisteredUser(String name, String password) throws IllegalArgumentException {
        Member member = memberRepository.getMemberByMail(name);
        if (member != null) {
            return isPasswordMatches(member, password);
        }

        member = memberRepository.getMemberByLogin(name);
        return member != null && isPasswordMatches(member, password);
    }

    private boolean isPasswordMatches(Member member, String password) {
        return member.getPassword().equals(password);
    }

    @Override
    public boolean isUniqueUser(String mail, String login) {
        return (memberRepository.getMemberByMail(mail) == null) && (memberRepository.getMemberByLogin(login) == null);
    }
}
