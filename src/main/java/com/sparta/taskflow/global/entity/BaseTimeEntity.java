package com.sparta.taskflow.global.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 직접 테이블이 되지않지만 클래스를 상속한 자식 엔티티는 아래 필드들을 컬럼으로 가지게 됨
@EntityListeners(AuditingEntityListener.class) // JPA 이벤트 리스너 등록
public  abstract class BaseTimeEntity {


    @CreatedDate // 엔티티 최초 저장 시 자동으로 저장 시각 기록
    @Column(updatable = false) // 초기화 이후 수정 불가
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 변경될 때 마다 변경 시각 기록
    private LocalDateTime updatedAt;
}
