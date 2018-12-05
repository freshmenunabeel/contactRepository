package com.contactBook.contactBook.repository;

import com.contactBook.contactBook.model.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
	UserInfo findByUniqueUserId(String name);

	@Query(value = "select name,email from user_info_contact left outer join UserContact as user_contact on (user_info_contact.id=user_contact.userId) where email = ?1 or name = ?1 limit ?2,?3", nativeQuery = true)
	Object[][] search(String name, Integer fromLimit, Integer size);

}
