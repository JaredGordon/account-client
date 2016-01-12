package cf.pivotal.accountClient;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@WebIntegrationTest(value = "server.port=9873")
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
public class AccountControllerTest {

	@Autowired
	AccountController accountController;

	@Autowired
	AccountProfileController accountProfileController;

	@Test
	public void testFind() {
		Account obj = accountController.findAccount(new Long(2));
		assertNotNull("Should find a result.", obj);

		assertNotNull(obj.getAccountid());
		assertNotNull(obj.getBalance());
		assertNotNull(obj.getCreationdate());
		assertNotNull(obj.getLogincount());
		assertNotNull(obj.getLogoutcount());
		assertNotNull(obj.getOpenbalance());
		assertNotNull(obj.getVersion());
	}

	@Test
	public void testFindByProfile() {
		Accountprofile ap = accountProfileController
				.findAccountProfile(new Long(2));
		assertNotNull(ap);

		Account obj = accountController.findByProfile(ap);
		assertNotNull("Should find a result.", obj);
	}

	@Test
	public void testSaveAndDelete() {
		Accountprofile ap = accountProfileController
				.findAccountProfile(new Long(2));
		assertNotNull(ap);

		Account a = new Account();
		a.setAccountprofile(ap);
		a = accountController.saveAccount(a);
		assertNotNull(a);
		Long id = a.getAccountid();
		assertNotNull(id);
		
		accountController.deleteAccount(a);
	}
}
