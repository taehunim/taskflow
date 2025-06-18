package com.sparta.taskflow.domain.ActivityLog.aop;

import com.sparta.taskflow.domain.ActivityLog.Enum.ActivityType;
import com.sparta.taskflow.domain.ActivityLog.repository.ActivityLogRepository;
import com.sparta.taskflow.domain.ActivityLog.service.ActivityLogService;
import jakarta.servlet.http.HttpServlet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * Advice + Pointcut
 * 공통 관심사를 담은 실제로 동작할 로직
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ActivityLogAspect {

    private final ActivityLogRepository activityLogRepository;
    private final HttpServlet request;

    @AfterReturning("annotation(com.sparta.taskflow.domain.activity.aop.ActivityLoggable)")
    public void logActivity(JoinPoint joinPoint) {

        // 1. 실행된 메서드에서 어노테이션 값 추출
        // jointPoint 는 실행된 메서드에 대한 정보 전체를 가지고 있음
        // 겟 시그니쳐를 통해 시그니쳐를 가져오고 메서드 시그니처로 캐스팅라여
        // 런타임 중에 클래스, 메서드, 필드 등에 대한 정보를 일고 조작함 = 리플렉션 기능
        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // 캐스팅
        ActivityLoggable annotation = signature.getMethod().getAnnotation(ActivityLoggable.class); // 원하는 어노테이션 가져오고
        ActivityType activityType = annotation.value(); // 어노테이션 안에 정의된 값을 꺼냄

        // 2. 파라미터에서 타겟 아이디와 유저 아이디 추출
        // 현재 실행 중인 메서드 매개변수를 배열로 추출
        Object[] args = joinPoint.getArgs();

        // 변수 초기화
        Long userId = null;
        Long targetId = null;

            }
        }
    }

    // 3. 요청 정보 추출

    // 4. 로그 엔티티 생성

    // 5. 저장

}

}
