package com.hello.proxy.pureproxy.concreteproxy;

import com.hello.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import com.hello.proxy.pureproxy.concreteproxy.code.ConcreteLogic;
import com.hello.proxy.pureproxy.concreteproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

    @Test
    void noProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);
        client.execute();
    }

    @Test
    void addProxy() {
        ConcreteLogic concreteLogic = new TimeProxy(new ConcreteLogic());
        ConcreteClient client = new ConcreteClient(concreteLogic);
        client.execute();
    }
}
