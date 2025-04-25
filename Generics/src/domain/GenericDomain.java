package domain;

import java.util.Objects;

public interface GenericDomain<T> {


    T getId();
    void setId(T id);
}
