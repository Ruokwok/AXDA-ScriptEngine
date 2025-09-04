package net.axda.se.api;

import net.axda.se.exception.ScriptExecuteException;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;
import org.graalvm.polyglot.proxy.ProxyExecutable;
import org.graalvm.polyglot.proxy.ProxyObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 传入JS脚本的API对象，如果有大量需要计算的属性，可以实现此接口。
 * 子类在脚本中使用的方法需添加@HostAccess.Export注解，方法需接收Value[]类型参数。
 * 子类在脚本中使用的属性需添加@ProxyField注解
 * @author Ruok
 */
public interface ProxyAPI extends ProxyObject {

    @Override
    default Object getMember(String key) {
        if (key.equals("..origin..")) return getOrigin();
        try {
            Method method = getClass().getMethod(key, Value[].class);
            if (method.isAnnotationPresent(HostAccess.Export.class)) {
                return (ProxyExecutable) arguments -> {
                    try {
                        return method.invoke(ProxyAPI.this, (Object) arguments);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e.getTargetException());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                };
            }
        } catch (Exception e) {
            try {
                Method method = getClass().getMethod(key);
                if (method.isAnnotationPresent(ProxyField.class)) {
                    return method.invoke(this);
                }
            } catch (Exception ex) {
                return null;
            }
        }
        return null;
    }

    @Override
    default Object getMemberKeys() {
        ArrayList<Object> list = new ArrayList<>();
        Method[] methods = getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(HostAccess.Export.class)
                    || method.isAnnotationPresent(ProxyField.class)) {
                list.add(method.getName());
            }
        }
        return ProxyArray.fromList(list);
    }

    @Override
    default boolean hasMember(String key) {
        Method[] methods = getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    default void putMember(String key, Value value) {
    }

    Object getOrigin();
}
