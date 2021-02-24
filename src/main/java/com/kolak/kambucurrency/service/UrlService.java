package com.kolak.kambucurrency.service;

import com.kolak.kambucurrency.model.PersistedRequest;
import com.kolak.kambucurrency.repository.PersistedRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

@Service
public class UrlService {

    private final static int VALUE = 0;
    private final PersistedRequestRepository persistedRequestRepository;

    public UrlService(PersistedRequestRepository persistedRequestRepository) {
        this.persistedRequestRepository = persistedRequestRepository;
    }

    public void saveRequest() {

        HttpServletRequest req =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                        .getRequest();

        StringBuffer requestURL = req.getRequestURL();
        Map<String, String[]> parameterMap = req.getParameterMap();

        getParams(parameterMap, requestURL);

        persistedRequestRepository.save(new PersistedRequest(requestURL.toString(), LocalDateTime.now()));
    }

    public void saveRequest(String url) {
        persistedRequestRepository.save(new PersistedRequest(url, LocalDateTime.now()));
    }


    public void getParams(Map<String, String[]> parameterMap, StringBuffer requestURL) {
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
