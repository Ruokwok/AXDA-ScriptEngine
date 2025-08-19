package net.axda.se;

public enum Depends {

    ECONOMY_API("EconomyAPI", "me.onebone.economyapi.EconomyAPI", "economy"),
    ;

    private String name;
    private String clazz;
    private String index;

    Depends(String name, String clazz, String index) {
        this.name = name;
        this.clazz = clazz;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public String getClazz() {
        return clazz;
    }

    public String getIndex() {
        return index;
    }

}
