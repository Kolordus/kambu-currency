package com.kolak.kambucurrency.service;

import com.kolak.kambucurrency.model.PersistedRequest;
import com.kolak.kambucurrency.repository.PersistedRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class PersistRequestService {

    private final PersistedRequestRepository persistedRequestRepository;

    public PersistRequestService(PersistedRequestRepository persistedRequestRepository) {
        this.persistedRequestRepository = persistedRequestRepository;
    }

    public List<PersistedRequest> getAllPersistedRequests() {
        saveRequest();
        return persistedRequestRepository.findAll();
    }

    public PersistedRequest saveRequest() {
        PersistedRequest persistedRequest = new PersistedRequest();
        persistedRequest.setRequestUrl(getRequest());
        persistedRequest.setTimeCreated(LocalDateTime.now());
        return persistedRequestRepository.save(persistedRequest);
    }

    public PersistedRequest saveRequest(String base, Map<String, Double> desiredCurrencies, List<String> invokedExternalApiUrls) {
        PersistedRequest persistedRequest = new PersistedRequest();
        persistedRequest.setRequestUrl(getRequest());
        persistedRequest.setBaseCurrency(base);
        persistedRequest.setTimeCreated(LocalDateTime.now());
        persistedRequest.setDesiredCurrenciesResponse(desiredCurrencies);
        persistedRequest.setInvokedExternalApiUrls(invokedExternalApiUrls);
        return persistedRequestRepository.save(persistedRequest);
    }

    public PersistedRequest saveRequest(Double amount, String base, Map<String, Double> desiredCurrencies, List<String> invokedExternalApiUrls) {
        return persistedRequestRepository.save(new PersistedRequest(getRequest(), LocalDateTime.now(), amount, base, desiredCurrencies, invokedExternalApiUrls));
    }

    public PersistedRequest saveRequestWithError(String exceptionName) {
        PersistedRequest persistedRequest = new PersistedRequest();
        persistedRequest.setRequestUrl(getRequest());
        persistedRequest.setTimeCreated(LocalDateTime.now());
        persistedRequest.setExceptionName(exceptionName);

        return persistedRequestRepository.save(persistedRequest);
    }

    private String getRequest() {
        HttpServletRequest req =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                        .getRequest();


        if (req.getQueryString() == null) {
            return req.getRequestURL().toString();
        }

        return req.getRequestURL() + "?" + req.getQueryString();
    }

}
