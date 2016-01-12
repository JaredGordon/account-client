package cf.pivotal.accountClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;

@WebIntegrationTest(value = "server.port=9873")
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class AccountProfileControllerTest {

    @Autowired
    AccountController accountController;

    @Autowired
    AccountProfileController accountProfileController;

    @Test
    public void testFind() {
        Accountprofile obj = accountProfileController
                .findAccountProfile(new Long(2));
        assertNotNull("Should find a result.", obj);

        assertNotNull(obj.getAccounts());
        assertTrue(obj.getAccounts().size() > 0);
        assertNotNull(obj.getAddress());
        assertNotNull(obj.getAuthtoken());
        assertNotNull(obj.getCreditcard());
        assertNotNull(obj.getEmail());
        assertNotNull(obj.getFullname());
        assertNotNull(obj.getPasswd());
        assertEquals(new Long(2), obj.getProfileid());
        assertNotNull(obj.getUserid());

        obj = accountProfileController.findAccountProfile(new Long(12345));
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
    public void testSaveProfileAndDelete() {
        Accountprofile ap = new Accountprofile();
        ap.setUserid("deleteMe:" + System.currentTimeMillis());
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
        a.setBalance(new BigDecimal(123));
        accountController.saveAccount(a);

        //		accountProfileController.deletelAccountProfile(ap);
//
//		ap = accountProfileController.findAccountProfile(id);
//		assertNull(ap);
    }

    @Test
    public void testSaveAccountAndDelete() {
        Accountprofile ap = new Accountprofile();
        ap.setUserid("deleteMe:" + System.currentTimeMillis());
        ap.setAddress("address");
        ap.setAuthtoken("authtoken");
        ap.setCreditcard("creditcard");
        ap.setEmail("email");
        ap.setFullname("fullname");
        ap.setPasswd("passwd");

        ap = accountProfileController.saveAccountProfile(ap);
        assertNotNull(ap);
//
//        Long id = ap.getProfileid();
//        ap = accountProfileController.findAccountProfile(id);
//        assertNotNull(ap);

        Account a = new Account();
        a.setAccountprofile(ap);
        ap.setAccounts(new ArrayList<Account>());
        ap.getAccounts().add(a);
        a.setBalance(new BigDecimal(123));
        accountController.saveAccount(a);

        //		accountProfileController.deletelAccountProfile(ap);
//
//		ap = accountProfileController.findAccountProfile(id);
//		assertNull(ap);
    }

}
