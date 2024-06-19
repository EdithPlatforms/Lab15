package fun.mitiendita.config;

import org.springframework.beans.factory.annotation.Value;

import com.paypal.base.rest.APIContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfig {
	
	@Value("${client.id}")
	private String clientId;

	@Value("${client.secret}")
	private String clientSecret;

	@Value("${client.mode}")
	private String mode;

	@Bean
	public APIContext apiContext() {
		return new APIContext(clientId, clientSecret, mode);
	}
}
