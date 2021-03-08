package com.kolak.kambucurrency.unit;

import com.kolak.kambucurrency.model.PersistedRequest;
import com.kolak.kambucurrency.repository.PersistedRequestRepository;
import com.kolak.kambucurrency.service.PersistRequestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PersistRequestServiceTest {

    @Mock PersistedRequestRepository persistedRequestRepository;

    @InjectMocks
    PersistRequestService persistRequestService;

    @Test
    public void shouldSave() {
        // when
        persistRequestService.saveRequest();

        // then
        Mockito.verify(persistedRequestRepository, Mockito.times(1))
                .save(any(PersistedRequest.class));
    }
}
