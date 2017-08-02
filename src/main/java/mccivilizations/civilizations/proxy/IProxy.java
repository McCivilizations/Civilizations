package mccivilizations.civilizations.proxy;

public interface IProxy {
    default void preInit() {}
    default void init() {}
    default void postInit() {}
}
