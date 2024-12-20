package com.example.Bank.domain.account;

import com.example.Bank.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor //스프링이 user 객체생성할 때 빈생성자로 new를 하기 때문
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "account_tb")
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true ,nullable = false,length = 20)
    private Long number; //계좌번호

    @Column(nullable = false,length = 4)
    private Long password; //계좌비번

    @Column(nullable = false)
    private Long balance; //잔 액(기본값 1000원)

    // 항상 orm에서 fk의 주인은 many entity 쪽이다.
    @ManyToOne(fetch = FetchType.LAZY) //지연로딩 == lazy 전략은 eager전략과 다르게 account 조회할때 바로 가지고 오지 않는다. == account.getUser().아무필드호출() == lazy 발동
    private User user; //한명의 유저는 여러개의 계좌를 가질수 있다.

    @CreatedDate //insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate //insert, update
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Account(Long id, Long number, Long password, Long balance, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.number = number;
        this.password = password;
        this.balance = balance;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
