package net.axda.se.exception;

/**
 * 在js中调用LSE存在但此项目中未实现的API时，可以抛出此异常。
 */
public class UnsupportedMemberException extends ScriptExecuteException {

    private String member;

    public UnsupportedMemberException(String member) {
        this.member = member;
    }

    @Override
    public String getMessage() {
        return "The Member '" + member + "' is unsupported.";
    }
}
