package com.kolak.kambucurrency.service;

import com.kolak.kambucurrency.model.PersistedRequest;
import com.kolak.kambucurrency.repository.PersistedRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class PersistRequestService {

    private final static int VALUE = 0;
    private final PersistedRequestRepository persistedRequestRepository;

    public PersistRequestService(PersistedRequestRepository persistedRequestRepository) {
        this.persistedRequestRepository = persistedRequestRepository;
    }

    public List<PersistedRequest> getAllPersistedRequests() {
        this.saveRequest();
        return persistedRequestRepository.findAll();
    }

    public void saveRequest() {
        PersistedRequest persistedRequest = new PersistedRequest();
        persistedRequest.setRequestUrl(getRequest());
        persistedRequest.setTimeCreated(LocalDateTime.now());
        persistedRequestRepository.save(persistedRequest);
    }

    public void saveRequest(String base, Map<String, Double> desiredCurrencies, List<String> invokedExternalApiUrls) {
        PersistedRequest persistedRequest = new PersistedRequest();
        persistedRequest.setRequestUrl(getRequest());
        persistedRequest.setBaseCurrency(base);
        persistedRequest.setTimeCreated(LocalDateTime.now());
        persistedRequest.setDesiredCurrencies(desiredCurrencies);
        persistedRequest.setInvokedExternalApiUrls(invokedExternalApiUrls);
        persistedRequestRepository.save(persistedRequest);
    }

    public void saveRequest(Double amount, String base, Map<String, Double> desiredCurrencies, List<String> invokedExternalApiUrls) {
        persistedRequestRepository.save(new PersistedRequest(getRequest(), LocalDateTime.now(), amount, base, desiredCurrencies, invokedExternalApiUrls));
    }

    private String getRequest() {
        HttpServletRequest req =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                        .getRequest();

        StringBuffer requestURL = req.getRequestURL();
        Map<String, String[]> parameterMap = req.getParameterMap();

        getParams(parameterMap, requestURL);

        return requestURL.toString();
    }

    private void getParams(Map<String, String[]> parameterMap, StringBuffer requestURL) {
        if (!parameterMap.isEmpty()) {
            requestURL.append("?");
            Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();

            while (iterator.hasNext()){
                Map.Entry<String, String[]> next = iterator.next();

                requestURL.append(next.getKey());
                requestURL.append("=");
                requestURL.append(next.getValue()[VALUE]);

                if (iterator.hasNext())
                    requestURL.append("&");
            }
        }
    }

}
