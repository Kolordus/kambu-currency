package com.kolak.kambucurrency.repository;

import com.kolak.kambucurrency.model.PersistedRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersistedRequestRepository extends JpaRepository<PersistedRequest, Long> {
}
