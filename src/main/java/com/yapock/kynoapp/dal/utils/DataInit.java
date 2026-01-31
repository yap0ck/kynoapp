package com.yapock.kynoapp.dal.utils;

import com.yapock.kynoapp.dal.models.Federation;
import com.yapock.kynoapp.dal.repositories.FederationRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements InitializingBean {
    private final FederationRepository federationRepository;

    @Value("${api.data-init}")
    private boolean insertion;

    public DataInit(FederationRepository federationRepository) {
        this.federationRepository = federationRepository;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if(insertion){
            Federation federation = Federation.builder()
                    .name("Union Royale Cynologique Saint-Hubert")
                    .country("Belgique")
                    .url("https://www.kkush.be/")
                    .build();
            federationRepository.save(federation);
        }
    }
}
