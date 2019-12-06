import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/12/5 15:26
 **/
@Slf4j
public class ProxyHandler implements InvocationHandler {

    private Object object;

    public ProxyHandler(Object o) {
        this.object = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        log.error("##################");

        Object result = method.invoke(object, args);

        log.error("$$$$$$$$$$$$$$$$$$");

        return result;
    }

}
