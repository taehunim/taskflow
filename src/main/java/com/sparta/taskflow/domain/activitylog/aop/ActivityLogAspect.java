package com.sparta.taskflow.domain.activitylog.aop;

import com.sparta.taskflow.domain.activitylog.Enum.ActivityType;
import com.sparta.taskflow.domain.activitylog.entity.ActivityLog;
import com.sparta.taskflow.domain.activitylog.repository.ActivityLogRepository;
import com.sparta.taskflow.domain.comment.dto.CreateCommentResponseDto;
import com.sparta.taskflow.domain.task.dto.response.TaskResponseDto;
import com.sparta.taskflow.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
    private final HttpServletRequest request;

    @AfterReturning(pointcut = "@annotation(com.sparta.taskflow.domain.activitylog.aop.ActivityLoggable)", returning = "result")
    public void logActivity(JoinPoint joinPoint, Object result) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        ActivityLoggable activityLoggable = signature.getMethod().getAnnotation(ActivityLoggable.class);
        ActivityType activityType = activityLoggable.value();

        Long userID = extractUserId();
        Long targetId = null;

        Object[] args = joinPoint.getArgs();

        if (activityType.getRequiredTargetId()) {
            targetId = extractTargetId(args, activityType); //파라미터에서 뽑아보고(args, activityType);
            if (targetId == null) {
                targetId = extractTargetIdFromResult(result, activityType);
            }
        }

        String url = request.getRequestURI();
        String method = request.getMethod();
        String ipAddress = request.getRemoteAddr();

        ActivityLog activityLog = new ActivityLog(
                userID,
                activityType,
                targetId,
                method,
                url,
                ipAddress,
                content;
        );
        activityLogRepository.save(activityLog);
        log.info("활동 로그 저장됨: {}", activityLog);

    }

    private Long extractUserId() {
        // 스프링 시큐리티 컨텍스트 홀더에서 인증객체를 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 객체가 비어있으면 에러 반환
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("인증된 사용자가 아닙니다.");
        }

        // 인증된 사용자 정보를 가져오자.
        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            User user = (User) principal;
            return Long.valueOf(user.getUsername());
        }
        throw new IllegalStateException("알 수 없는 사용자 입니다.");
    }

    private Long extractTargetId(Object[] args, ActivityType activityType) {
        if (!activityType.getRequiredTargetId()) {
            return null;
        }
        for (Object arg : args) {
            if (arg instanceof Long) {
                Long id = (Long) arg;
                return id;
            }
        }
        return null;
    }

    private Long extractTargetIdFromResult(Object result, ActivityType activityType) {
        if (!(result instanceof ApiResponse<?>)) {
            return null;
        }
        ApiResponse apiResponse = (ApiResponse) result;
        Object data = apiResponse.getData();

        if (data instanceof TaskResponseDto && activityType.name().startsWith("TASK_")) {
            TaskResponseDto dto = (TaskResponseDto) data;
            return dto.getId();
        }

        if (data instanceof CreateCommentResponseDto && activityType.name().startsWith("COMMENT_")) {
            CreateCommentResponseDto dto = (CreateCommentResponseDto) data;
            return dto.getId();
        }

        return null;
    }
}

