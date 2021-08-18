package jetbrains.youtrack.client.impl;

@FunctionalInterface
public interface APIParseFunction<T, R> {
    R apply(T t) throws Exception;
}
