package com.shop.shop.entity;

import com.shop.shop.constant.Role;
import com.shop.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity{

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    //회원은 이메일을 통해 유일하게 구분하기 때문에 동일한 값이 데이터에
    //들어올수 없도록 unique속성을 지정한다.
    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    //Member엔티티를 생성하는 메소드, 엔티티클래스에 회원생성 메소드를
    //만들어 관리한다면 관리의 이점이있다.

    public static Member createMember(MemberFormDto memberFormDto,PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        //스프링 시큐리티 설정 클래스에 등록한 BCryptPasswordEncoder를 파라미터로 넘겨서 비밀번호 암호화
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);
        return member;
    }
}
