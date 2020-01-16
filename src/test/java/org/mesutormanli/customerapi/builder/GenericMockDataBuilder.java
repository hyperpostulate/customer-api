package org.mesutormanli.customerapi.builder;

import uk.co.jemos.podam.api.*;

import java.util.List;

public class GenericMockDataBuilder<T> {

    private final Class<T> type;
    private final PodamFactory podamFactory;
    private AbstractClassInfoStrategy classInfoStrategy;

    private GenericMockDataBuilder(Class<T> type) {
        this.type = type;
        podamFactory = new PodamFactoryImpl(new RandomDataProviderStrategyImpl());
        classInfoStrategy = DefaultClassInfoStrategy.getInstance();
    }

    static <T> GenericMockDataBuilder<T> of(Class<T> type) {
        return new GenericMockDataBuilder<>(type);
    }

    GenericMockDataBuilder<T> excludeField(String field) {
        podamFactory.setClassStrategy(classInfoStrategy.addExcludedField(type, field));
        return this;
    }

    T build() {
        return podamFactory.manufacturePojo(type);
    }


    @SuppressWarnings("unchecked")
    public List<T> buildList(int elementSizeOfCollection) {
        podamFactory.getStrategy().setDefaultNumberOfCollectionElements(elementSizeOfCollection);
        return podamFactory.manufacturePojo(List.class, type);
    }


}
