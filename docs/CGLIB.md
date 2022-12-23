# CGLIB

- CGLIB: Code Generator Library
- 바이트코드를 조작해서 동적으로 클래스를 생성하는 기술을 제공하는 라이브러리이다.
- 인터페이스가 없어도 구체 클래스만으로 동적 프록시를 만들어낼 수 있다.
- 외부 라이브러리인데, 스프링 프레임워크가 스프링 내부 소스 코드에 포함시켰다.
  - 스프링을 사용한다면 별도의 외부 라이브러리를 추가하지 않아도 사용할 수 있다.
- 직접 CGLIB를 사용하지 않고 스프링에서 제공하는 `ProxyFactory`를 통해 사용할 수 있다.

## CGLIB 제약
- 부모 클래스의 생성자를 체크해야 한다. (부모 클래스의 기본 생성자가 필요)
- 클래스에 final 키워드가 붙으면 CGLIB에서 예외가 발생한다.
  - `java.lang.IllegalArgumentException: Cannot subclass final class com.hello.proxy.common.service.ConcreteService`
- 메서드에 final 키워드가 붙으면 해당 메서드를 오버라이딩 할 수 없다. (프록시 로직이 동작하지 않는다.)
