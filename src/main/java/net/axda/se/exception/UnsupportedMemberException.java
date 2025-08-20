package net.axda.se.exception;

/**
 * 在js中调用LSE存在但此项目中未实现的API时，可以抛出此异常。
 */
public class UnsupportedMemberException extends RuntimeException {

    public UnsupportedMemberException(String member) {
        super("The Member '" + member + "' is unsupported.");
    }
}
