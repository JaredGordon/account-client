package cf.pivotal.accountClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;

@Configuration
public class TestConfiguration {

    @Bean
    public AccountRepository accountRepository() {
        return Feign
                .builder()
                .encoder(new AccountEncoder())
                .decoder(new AccountDecoder())
                .target(AccountRepository.class,
//                        "http://account-service.cfapps.io");
                        "http://localhost:8080");
    }

    @Bean
    public AccountProfileRepository accountProfileRepository() {
        return Feign
                .builder()
                .encoder(new AccountEncoder())
                .decoder(new AccountDecoder())
                .target(AccountProfileRepository.class,
//                        "http://account-service.cfapps.io");
                        "http://localhost:8080");
    }
}