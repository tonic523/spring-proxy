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

### 어드바이스 (Advice)
- 프록시가 호출하는 부가 기능
- 프록시 로직

### 어드바이저 (Advisor)
- 단순하게 하나의 포인트컷 + 하나의 어드바이스를 가지고 있는 것


