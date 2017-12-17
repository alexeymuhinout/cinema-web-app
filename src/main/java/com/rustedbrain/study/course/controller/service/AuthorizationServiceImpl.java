package com.rustedbrain.study.course.controller.service;

import com.rustedbrain.study.course.controller.repository.AdministratorRepository;
import com.rustedbrain.study.course.controller.repository.MemberRepository;
import com.rustedbrain.study.course.model.authorization.Administrator;
import com.rustedbrain.study.course.model.authorization.Member;
import com.rustedbrain.study.course.model.authorization.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

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
        return isRegisteredMember(name, password) || isRegisteredAdministrator(name, password);
    }

    public boolean isRegisteredMember(String name, String password) throws IllegalArgumentException {
        Member member = memberRepository.getMemberByMail(name);
        if (member != null) {
            return isPasswordMatches(member, password);
        }

        member = memberRepository.getMemberByLogin(name);
        return member != null && isPasswordMatches(member, password);
    }

    @Override
    public User getUser(String loginOrMail) {
        Administrator administrator = administratorRepository.getAdministratorByMail(loginOrMail);
        administrator = administratorRepository.getAdministratorByLogin(loginOrMail);
        Member member = memberRepository.getMemberByMail(loginOrMail);
        member = memberRepository.getMemberByLogin(loginOrMail);
        return administrator != null ? administrator : member;
    }

    @Override
    public void deleteUser(User user) {
        if (user instanceof Administrator) {
            administratorRepository.delete((Administrator) user);
        } else if (user instanceof Member) {
            memberRepository.delete((Member) user);
        }
    }

    public boolean isRegisteredAdministrator(String loginOrMail, String password) throws IllegalArgumentException {
        Administrator administrator = administratorRepository.getAdministratorByMail(loginOrMail);
        if (administrator != null) {
            return isPasswordMatches(administrator, password);
        }

        administrator = administratorRepository.getAdministratorByLogin(loginOrMail);
        return administrator != null && isPasswordMatches(administrator, password);
    }

    private boolean isPasswordMatches(User user, String password) {
        return user.getPassword().equals(password);
    }

    @Override
    public boolean isUniqueUser(String mail, String login) {
        return (memberRepository.getMemberByMail(mail) == null) && (memberRepository.getMemberByLogin(login) == null);
    }
}
