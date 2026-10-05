package com.library.service;

import com.library.entity.Member;
import com.library.exception.BadRequestException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
    }

    public Member createMember(Member member) {
        if (memberRepository.existsByMembershipNumber(member.getMembershipNumber())) {
            throw new BadRequestException("Membership number already exists");
        }
        if (member.getEmail() != null && memberRepository.existsByEmail(member.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member memberDetails) {
        Member member = getMemberById(id);
        
        if (!member.getMembershipNumber().equals(memberDetails.getMembershipNumber()) && 
            memberRepository.existsByMembershipNumber(memberDetails.getMembershipNumber())) {
            throw new BadRequestException("Membership number already exists");
        }
        
        if (memberDetails.getEmail() != null && !memberDetails.getEmail().equals(member.getEmail()) && 
            memberRepository.existsByEmail(memberDetails.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        member.setMembershipNumber(memberDetails.getMembershipNumber());
        member.setName(memberDetails.getName());
        member.setEmail(memberDetails.getEmail());
        member.setPhone(memberDetails.getPhone());
        member.setAddress(memberDetails.getAddress());
        member.setDateOfBirth(memberDetails.getDateOfBirth());
        member.setStatus(memberDetails.getStatus());

        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        Member member = getMemberById(id);
        memberRepository.delete(member);
    }

    public List<Member> searchMembers(String query) {
        return memberRepository.searchMembers(query);
    }
}
