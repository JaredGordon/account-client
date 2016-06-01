package cf.pivotal.accountClient;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(value=SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfiguration.class)
public class AccountControllerTest {

    @Autowired
    private AccountController accountController;

    @Autowired
    private AccountProfileController accountProfileController;

    @Test
    public void testFind() {
        Account obj = accountController.findAccount(TestConfiguration.TEST_ID);
        assertNotNull("Should find a result.", obj);

        assertNotNull(obj.getAccountid());
        assertNotNull(obj.getBalance());
        assertNotNull(obj.getCreationdate());
        assertNotNull(obj.getLogincount());
        assertNotNull(obj.getLogoutcount());
        assertNotNull(obj.getOpenbalance());
        assertNotNull(obj.getVersion());
        assertNotNull(obj.getAccountprofile());
    }

    @Test
    public void testFindByProfile() {
        Accountprofile ap = accountProfileController
                .findAccountProfile(TestConfiguration.TEST_ID);
        assertNotNull(ap);

        Account obj = accountController.findByProfile(ap);
        assertNotNull("Should find a result.", obj);
    }

    @Test
    @Ignore
    public void testSaveAndDelete() {
        Accountprofile ap = accountProfileController
                .findAccountProfile(TestConfiguration.TEST_ID);
        assertNotNull(ap);

        long l = ap.getAccounts().size();

        Account a = new Account();
        Date now = new Date();
        a.setCreationdate(now);
        ap.addAccount(a);

        ap = accountProfileController.saveAccountProfile(ap);
        assertNotNull(ap);
        ap = accountProfileController.findAccountProfile(ap.getProfileid());

        assertTrue(ap.getAccounts().size() > l);

//        accountController.deleteAccount(a2);
    }
}
