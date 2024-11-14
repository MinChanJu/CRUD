package com.example.CRUD.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* com.example.CRUD..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        // 클래스 이름, 메서드 이름 및 반환형을 가져옵니다.
        String fullClassName = joinPoint.getSignature().getDeclaringTypeName();
        String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = joinPoint.getSignature().getName();
        String returnType = ((MethodSignature) joinPoint.getSignature()).getReturnType().getSimpleName();

        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("time : " + returnType + "\t" + simpleClassName + "." + methodName + "  " + timeMs + "ms");
        }
    }

}
