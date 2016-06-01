package cf.pivotal.accountClient;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(value = SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfiguration.class)
public class AccountProfileControllerTest {

    @Autowired
    private AccountController accountController;

    @Autowired
    private AccountProfileController accountProfileController;

    @Test
    public void testFind() {
        Accountprofile obj = accountProfileController
                .findAccountProfile(TestConfiguration.TEST_ID);
        assertNotNull("Should find a result.", obj);

        assertNotNull(obj.getAccounts());
        assertTrue(obj.getAccounts().size() > 0);
        assertNotNull(obj.getAddress());
        assertNotNull(obj.getAuthtoken());
        assertNotNull(obj.getCreditcard());
        assertNotNull(obj.getEmail());
        assertNotNull(obj.getFullname());
        assertNotNull(obj.getPasswd());
        assertEquals(TestConfiguration.TEST_ID, obj.getProfileid());
        assertNotNull(obj.getUserid());

        obj = accountProfileController.findAccountProfile(12345L);
        assertNull(obj);
    }

    @Test
    public void testFindByAuth() {
        Accountprofile obj = accountProfileController.findByAuthtoken("token");
        assertNotNull(obj);
        assertEquals("token", obj.getAuthtoken());
        assertNotNull(obj.getAccounts());
        assertTrue(obj.getAccounts().size() > 0);
        obj = accountProfileController.findByAuthtoken("foo");
        assertNull(obj);
    }

    @Test
    public void testFindByUserId() {
        Accountprofile obj = accountProfileController.findByUserid("test");
        assertNotNull(obj);
        assertEquals("test", obj.getUserid());
        obj = accountProfileController.findByUserid("foo");
        assertNull(obj);
    }

    @Test
    public void testFindByUserIdAndPassword() {
        Accountprofile obj = accountProfileController.findByUseridAndPasswd(
                "test", "test");
        assertNotNull(obj);
        assertEquals("test", obj.getUserid());
        assertEquals("test", obj.getPasswd());
        obj = accountProfileController.findByUseridAndPasswd("foo", "test");
        assertNull(obj);
        obj = accountProfileController.findByUseridAndPasswd("test", "foo");
        assertNull(obj);
    }

    @Test
    @Ignore
    public void testSaveAccountAndDelete() {
        Accountprofile ap = new Accountprofile();
        ap.setUserid("deleteMe2:" + System.currentTimeMillis());
        ap.setAddress("address");
        ap.setAuthtoken("authtoken");
        ap.setCreditcard("creditcard");
        ap.setEmail("email");
        ap.setFullname("fullname");
        ap.setPasswd("passwd");

        ap = accountProfileController.saveAccountProfile(ap);
        assertNotNull(ap);

        Long id = ap.getProfileid();
        ap = accountProfileController.findAccountProfile(id);
        assertNotNull(ap);

        Account a = new Account();
        a.setAccountprofile(ap);
        ap.setAccounts(new ArrayList<Account>());
        ap.getAccounts().add(a);
        a.setBalance(new BigDecimal(123));
        accountController.saveAccount(a);

//        accountProfileController.deletelAccountProfile(ap);
//
//        ap = accountProfileController.findAccountProfile(id);
//        assertNull(ap);
    }

}
