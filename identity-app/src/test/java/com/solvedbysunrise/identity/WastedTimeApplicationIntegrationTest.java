package com.solvedbysunrise.identity;

import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dto.Hello;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import static com.solvedbysunrise.identity.util.UriUtil.getFullyQualifiedUriPattern;
import static org.hamcrest.CoreMatchers.is;


@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@WebIntegrationTest
public class WastedTimeApplicationIntegrationTest {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private String wastedTimeBaseUrl;

    private static final Hello EXPECTED_WORLD = new Hello();
    {
        EXPECTED_WORLD.setWho("harry");
    }

	@Test
	public void application_will_start() {
		UriTemplate template = new UriTemplate(getFullyQualifiedUriPattern(wastedTimeBaseUrl, "/hello"));
        Hello world =  restTemplate.getForEntity(template.expand(), Hello.class).getBody();
		Assert.assertThat(world, is(EXPECTED_WORLD));
	}

}
