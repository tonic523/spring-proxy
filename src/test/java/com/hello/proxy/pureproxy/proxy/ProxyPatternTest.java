package com.hello.proxy.pureproxy.proxy;

import com.hello.proxy.pureproxy.proxy.code.CacheProxy;
import com.hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import com.hello.proxy.pureproxy.proxy.code.RealSubject;
import com.hello.proxy.pureproxy.proxy.code.Subject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    /*
     * 3번 호출하면 3초가 걸린다.
     * 만약 한번 조회하면 변하지 않는 데이터라면 어딘가 보관해두고 사용하는 것이 성능에 좋다.
     * 이를 캐시라고 부른다.
     */
    @Test
    void noProxyTest() {
        Subject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);

        client.execute();
        client.execute();
        client.execute();
    }

    /*
     * 캐시 프록시를 적용
     */
    @Test
    void cacheProxyTest() {
        Subject cacheProxy = new CacheProxy(new RealSubject());
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);

        client.execute();
        client.execute();
        client.execute();
    }
}
