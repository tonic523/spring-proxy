# @Aspect AOP

- 스프링 애플리케이션에 프록시를 적용하려면 포인트컷, 어드바이스로 구성되어 있는 어드바이저를 만들어서 스프링 빈으로 등록하면 된다.
- 스프링은 `@Aspect` 애노테이션으로 매우 편리하게 어드바이저 생성 기능을 지원한다.

## `@Aspect`가 붙은 어드바이저가 생성되는 원리
- 예제: src/main/java/com/hello/proxy/config/v6_aop/aspect/LogTraceAspect.java

- 스프링 애플리케이션 로딩 시점에 자동 프록시 생성기를 호출한다.
- `AnnotationAwareAspectJAutoProxyCreator`가 `@Aspect`를 찾아서 Advisor로 만들어준다.
  - 생성 과정은 `@Aspect 어드바이저 빌더` 내부에 생성한 어드바이저를 저장한다.
  - `@Aspect 어드바이저 빌더` = `BeanFactoryAspectJAdvisorsBuilder` 클래스

## 자동 프록시 생성기의 작동 과정
1. 생성: 스프링 빈 대상이 되는 객체를 생성
2. 생성된 객체를 빈 저장소에 등록하기 직전에 빈 후처리기에 전달
3. 스프링 컨테이너에서 `Advisor` 빈을 모두 조회
4. `@Aspect` 어드바이저 빌더 내부에 저장된 `Advisor`를 모두 조회
5. 모든 `Advisor`에 포함되어 있는 포인트컷을 사용해서 해당 객체가 프록시를 적용할 대상인지 아닌지 판단
   1. 이 때 객체의 클래스 정보 뿐만 아니라, 모든 메서드를 하나하나 모두 매칭해본다.
6. 프록시 적용 대상이면 프록시를 생성하고 반환한다. 적용 대상이 아니라면 원본 객체를 스프링 빈으로 등록한다.
7. 반환된 객체를 스프링 빈으로 등록한다.
