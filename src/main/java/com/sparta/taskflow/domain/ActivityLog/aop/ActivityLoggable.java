package com.sparta.taskflow.domain.ActivityLog.aop;

import com.sparta.taskflow.domain.ActivityLog.Enum.ActivityType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 어노테이션이 붙은 메서드를 AOP가 감지하고
 * AOP가 이 어노테이션을 통해 타입값을 읽고
 * 로그에 이 타입을 저장함
 */
/
@Target(ElementType.METHOD) // 메서드에만 적용
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 유지가 되야 AOP가 감지
public @interface ActivityLoggable {
    ActivityType value(); // 어떤 활동 타입인지 반드시 지정해줘야한다
}

