package cf.pivotal.accountClient;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"cf.pivotal.accountClient"})
public class TestConfiguration {

    static final Long TEST_ID = 1L;

    @Bean
    public AccountRepository accountRepository() {
        return Feign
                .builder()
                .encoder(new AccountEncoder())
                .decoder(new AccountDecoder())
                .target(AccountRepository.class,
                        "http://account-service.cfapps.io");
//                        "http://localhost:8080");
    }

    @Bean
    public AccountProfileRepository accountProfileRepository() {
        return Feign
                .builder()
                .encoder(new AccountEncoder())
                .decoder(new AccountDecoder())
                .target(AccountProfileRepository.class,
                       "http://account-service.cfapps.io");
//                        "http://localhost:8080");
    }
}