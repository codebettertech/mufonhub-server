package io.codebetter.mufonhub.service.api;

import io.smallrye.mutiny.Uni;

import java.util.List;

public interface AbstractService<K> {

    Uni<List<K>> findAll();
    Uni<K> create(K dto);
    Uni<K> update(String id,Object dto);
    Uni<Void> delete(String id);
    Uni<K> getById(String id);
    Uni<Long> count();
}
