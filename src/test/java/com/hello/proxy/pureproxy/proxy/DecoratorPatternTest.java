package com.hello.proxy.pureproxy.proxy;

import com.hello.proxy.pureproxy.proxy.code.Component;
import com.hello.proxy.pureproxy.proxy.code.DecoratorPatternClient;
import com.hello.proxy.pureproxy.proxy.code.MessageDecorator;
import com.hello.proxy.pureproxy.proxy.code.RealComponent;
import com.hello.proxy.pureproxy.proxy.code.TimeDecorator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator() {
        Component component = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(component);
        client.execute();
    }

    @Test
    void messageDecorator() {
        MessageDecorator component = new MessageDecorator(new RealComponent());
        DecoratorPatternClient client = new DecoratorPatternClient(component);
        client.execute();
    }

    @Test
    void timeDecorator() {
        TimeDecorator component = new TimeDecorator(new MessageDecorator(new RealComponent()));
        DecoratorPatternClient client = new DecoratorPatternClient(component);
        client.execute();
    }
}
