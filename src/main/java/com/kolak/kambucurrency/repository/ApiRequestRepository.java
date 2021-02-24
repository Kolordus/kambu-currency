package com.kolak.kambucurrency.repository;

import com.kolak.kambucurrency.model.apirequest.ApiRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRequestRepository extends JpaRepository<ApiRequestModel, Long> {
}
