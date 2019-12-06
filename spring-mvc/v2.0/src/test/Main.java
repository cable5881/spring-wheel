import com.lqb.app.v2.service.impl.HelloService;
import com.lqb.app.v2.service.IHelloService;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/12/5 15:25
 **/
@Slf4j
public class Main {

    public static void main(String[] args) throws Throwable {

        ProxyHandler proxyHandler = new ProxyHandler(new HelloService());
        IHelloService helloService = (IHelloService)Proxy.newProxyInstance(
                HelloService.class.getClassLoader(),
                HelloService.class.getInterfaces(),
                proxyHandler
        );
        log.error("%%%%%%%%%%%%%%%%%% " + helloService.hello());
    }


}
