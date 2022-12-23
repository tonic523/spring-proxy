# Proxy Factory

## 등장 배경
### JDK 동적 프록시, CGLIB를 사용할 때 문제점
> 인터페이스가 있는 경우에는 JDK 동적 프록시를 적용하고, 그렇지 않은 경우에는 CGLIB를 적용하려면 어떻게 해야 할까?
> - 스프링은 동적 프록시를 통합해서 편리하게 만들어주는 `ProxyFactory`를 제공한다.

> 두 기술을 함께 사용할 때 부가 기능을 제공하기 위해 JDK 동적 프록시가 제공하는 `InvocationHandler`와 CGLIB가 제공하는 `MethodInterceptor`를
  각각 중복으로 만들어서 관리해야 할까?
> - 스프링은 이 문제를 해결하기 위해 부가 기능을 적용할 때 `Advice`라는 새로운 개념을 도입했다.
> - 개발자는 `InvocationHandler`이든 `MethodInterceptor`든 신경쓰지 않고 `Advice`만 만들면 된다.

> 특정 조건에 맞을 때 프록시 로직을 적용하는 기능도 공통으로 제공할 수 있지 않을까?
> - 앞서 특정 메서드 이름의 조건에 맞을 때만 프록시 부가 기능이 적용되는 코드를 직접 만들었다.
> - 스프링은 `Pointcut`이라는 개념을 도입해서 이 문제를 일관성 있게 해결했다.

## 프록시 팩토리 (Proxy Factory)

### 프록시 팩토리 예제 코드
- `src/test/java/com/hello/proxy/proxyfactory/ProxyFactoryTest.java`

### 프록시 팩토리의 장점 및 특징
- 프록시 팩토리의 서비스 추상화 덕분에 구체적인 CGLIB, JDK 동적 프록시 기술에 의존하지 않고, 편리하게 생성할 수 있다.
- 프록시 팩토리가 내부에서 JDK 동적 프록시인 경우 InvocationHandler가 Advice를 호출하도록 개발했고,
- CGLIB인 경우 MethodInterceptor가 Advice를 호출하도록 기능을 개발해두었기 때문이다.

> 참고: 스프링 부트는 aop를 적용할 때 기본적으로 proxyTargetClass=true로 설정하여 인터페이스가 있더라도 항상 CGLIB를 사용한다.

## 포인트컷, 어드바이스, 어드바이저

### 포인트컷 (Pointcut)
- 어디에 부가 기능을 적용할지 판단하는 필터링 로직
- 주로 클래스와 메서드 이름으로 필터링
- 어떤 포인트에 기능을 적용할지 잘라서(cut) 구분하는 것

> 스프링이 제공하는 포인트컷
> - `NameMatchMethodPointCut`: 메서드 이름을 기반으로 매칭
> - `JdkRegexpMethodPointcut`: JDK 정규 표현식을 기반으로 포인트컷을 매칭
> - `TruePointcut`: 항상 참을 반환
> - `AnnotationMatchingPointcut`: 애노테이션으로 매칭
> - `AspectJExpressionPointcut`: aspectJ 표현식으로 매칭
> - 실무에서는 사용하기도 편리하고 기능이 많은 aspectJ 표현식을 기반으로 사용하는 방법을 사용하게 된다.

### 어드바이스 (Advice)
- 프록시가 호출하는 부가 기능
- 프록시 로직

### 어드바이저 (Advisor)
- 단순하게 하나의 포인트컷 + 하나의 어드바이스를 가지고 있는 것

## 여러 프록시 적용 예시

### 예제1
- `src/test/java/com/hello/proxy/advisor/MultiAdvisorTest.java`-> multiAdvisorTest1

위 처럼 구현하면 잘못된 방법은 아니지만, 프록시를 2번 생성해야 한다는 문제가 있다.\
만약 적용해야 하는 어드바이저가 10개라면 10개의 프록시를 생성해야 한다.

### 예제2
- `src/test/java/com/hello/proxy/advisor/MultiAdvisorTest.java`-> multiAdvisorTest1

프록시 팩토리에 원하는 만큼 `addAdvisor()`를 통해서 어드바이저를 등록하면 된다.\
등록하는 순서대로 `advisor`가 호출된다.

> AOP 적용 수 만큼 프록시가 생성된다고 착각할 수 있다. 하지만, 스프링은 AOP를 적용할 때 최적화를 진행하여
> 위 처럼 프록시는 하나를 만들고 여러 어드바이저를 적용한다. 정리하면 스프링의 AOP는 target 마다 하나의 프록시만 생성한다.

## 문제점
- 프록시 팩토리와 어드바이저 같은 개념 덕분에 원본 코드를 전혀 손대지 않고 동적 프록시 객체를 빈으로 등록할 수 있었다.
- 하지만, `Config`와 같은 설정 파일이 지나치게 많아지는 단점이 있다.
- 그리고 V3 처럼 컨포넌트 스캔을 사용하는 경우 프록시 적용이 불가능하다.
  - 프록시 객체를 등록하기 전에 실제 객체를 컴포넌트 스캔으로 등록 해버리기 때문이다.
- 위와 같은 문제를 한번에 해결하는 방법이 바로 `빈 후처리기`이다.
