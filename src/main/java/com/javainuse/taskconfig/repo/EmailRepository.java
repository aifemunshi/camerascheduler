package com.javainuse.taskconfig.repo;
 
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.javainuse.taskconfig.model.Email;
 
@EnableScan
@Repository
@Component
public interface EmailRepository extends CrudRepository<Email, String> {
 
  Email findBySerialId(String serialId);
}