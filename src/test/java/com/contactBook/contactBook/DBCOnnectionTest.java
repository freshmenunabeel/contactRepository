package com.contactBook.contactBook;

import com.contactBook.contactBook.config.ContactBookApplication;
import com.contactBook.contactBook.model.UserInfo;
import com.contactBook.contactBook.repository.UserInfoRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		ContactBookApplication.class }, initializers = ConfigFileApplicationContextInitializer.class)
@Profile("default")
public class DBCOnnectionTest {

	//@Value("${timeZone}")
	private String timeZone = "Asia/Calcutta";
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Test
	public void testDBConnection() {
		Assert.assertNotNull(userInfoRepository);
		Assert.assertNotNull(timeZone);
		UserInfo userInfo = new UserInfo();
		userInfo.setName("NABEEL");
		userInfo.setCreatedAt(new Timestamp(DateTime.now(DateTimeZone.forID(timeZone)).getMillis()));
		userInfo.setUpdatedAt(new Timestamp(DateTime.now(DateTimeZone.forID(timeZone)).getMillis()));
		userInfo = userInfoRepository.save(userInfo);
		Assert.assertNotNull(userInfo);
	}
}
