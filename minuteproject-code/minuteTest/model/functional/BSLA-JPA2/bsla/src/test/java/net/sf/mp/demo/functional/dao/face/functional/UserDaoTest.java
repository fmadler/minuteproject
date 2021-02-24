/**
	* Copyright (c) minuteproject, minuteproject@gmail.com
	* All rights reserved.
	* 
	* Licensed under the Apache License, Version 2.0 (the "License")
	* you may not use this file except in compliance with the License.
	* You may obtain a copy of the License at
	* 
	* http://www.apache.org/licenses/LICENSE-2.0
	* 
	* Unless required by applicable law or agreed to in writing, software
	* distributed under the License is distributed on an "AS IS" BASIS,
	* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	* See the License for the specific language governing permissions and
	* limitations under the License.
	* 
	* More information on minuteproject:
	* twitter @minuteproject
	* wiki http://minuteproject.wikispaces.com 
	* blog http://minuteproject.blogspot.net
	* 
*/
/**
	* template reference : 
	* - Minuteproject version : 0.8.5
	* - name      : TestDaoInterface
	* - file name : BslaDaoInterfaceTest.vm
	* - time      : 2014/01/30 AD at 11:53:12 CET
*/
package net.sf.mp.demo.functional.dao.face.functional;

import net.sf.mp.demo.functional.domain.functional.User;
import net.sf.mp.demo.functional.dao.face.functional.UserDao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import net.sf.minuteProject.model.data.criteria.EntityCriteria;
import net.sf.minuteProject.model.data.criteria.EntitySort;
import net.sf.minuteProject.model.data.criteria.QueryData;
import net.sf.minuteProject.model.data.criteria.constant.EntityMatchType;
import net.sf.minuteProject.model.data.criteria.constant.OperandType;
import net.sf.minuteProject.model.data.criteria.constant.QuerySortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import net.sf.mp.demo.functional.dao.face.AdapterFunctionalTestDao;
import net.sf.mp.demo.functional.dao.face.functional.CompanyDao;
import net.sf.mp.demo.functional.domain.functional.Company;
import net.sf.mp.demo.functional.dao.face.functional.DepartmentDao;
import net.sf.mp.demo.functional.domain.functional.Department;

public class UserDaoTest extends AdapterFunctionalTestDao {

    // Test are commented because the sample set use will setting up datase with data
    // might not correspond to the anything valuable
    // resulting in a failure of your test

    // work with minuteproject updatable code feature to exclude this artifact from consecutive
    // generation erasure and adapt you test to your data scenario.

	@Autowired @Qualifier("userDao")
	protected UserDao userDao;
	@Autowired @Qualifier("companyDao")
	protected CompanyDao companyDao;
	@Autowired @Qualifier("departmentDao")
	protected DepartmentDao departmentDao;
	
	/*
	* Do the insert first.
	* To do the insert do on all mandatory fields;
	* If a mandatory field is a fk => insert the mandatory entity and give the entity value to populate the fk.
	* Then perform load update, delete, loadfirstlevel
	*/
    	
	@Test
	public void testInsertUser () {
	    User user = insertUser ();
	    assertNotNull(user);
	}
	
	@Test	
    public void testLoadUser () {
    	User user = insertUser();
    	User user2 = loadUser(user);
       // assertion
     	assertTrue(user.getId().equals(user2.getId()));
     	assertTrue(user.getName().equals(user2.getName()));
     	assertTrue(user.getEmail().equals(user2.getEmail()));
     	assertTrue(user.getDepartmentId().equals(user2.getDepartmentId()));
	}	
		
	@Test		
	public void testDeleteUser () {
    	User user = insertUser();
    	userDao.deleteUser(user);
    	User user2 = loadUser(user);
        assertNull (user2);
    }
	
	@Test	
	public void testUpdateUser () {
    	User user = insertUser();
    	User user2 = loadUser(user);  	
        user.setName (getString2(45));
        user.setEmail (getString2(45));
	    userDao.updateUser(user);
    	User user3 = loadUser(user);
        // assertion
     	assertTrue(user.getId().equals(user3.getId()));
     	assertTrue(user.getName().equals(user3.getName()));
     	assertTrue(user.getEmail().equals(user3.getEmail()));
     	assertTrue(user.getDepartmentId().equals(user3.getDepartmentId()));
	}
/* updateNotNull is not on both interface	
	public void testUpdateNotNullUser () {
    	User user = insertUser();
    	//User user2 = loadUser(user);  	
		User user2 = new User();
        user2.setId(user.getId());
        user2.setName (getString2(45));
	    userDao.updateNotNullOnlyUser(user2);
    	User user3 = loadUser(user);
        // assertion
     	assertTrue(user3.getId().equals(user.getId()));
     	assertTrue(user3.getName().equals(user2.getName()));
     	assertTrue(user3.getEmail().equals(user.getEmail()));
     	assertTrue(user3.getDepartmentId().equals(user.getDepartmentId()));
	}	
*/   
 
	@Test	
    public void testPagination () {
        //assuming that there is something in the DB
        insertUser ();

        Integer start = 0;
        Integer max = 10;
        User sort = new User();
        EntitySort<User> entitySort = new EntitySort<User>(sort, QuerySortOrder.ASC);
        User criteria = new User();
        EntityCriteria<User> entityCriteria = new EntityCriteria<User>(criteria, EntityMatchType.ALL, OperandType.CONTAINS, true);

        User what = populateFirstNonPkFieldUser ();

        //User.fullMask();

        QueryData<User> queryData = new QueryData<User>(start, max, entitySort,entityCriteria, what);
        userDao.find(queryData);
        assertTrue (queryData.getTotalResultCount()>0);

    }
 
    public User insertUser () {
        User user = populateUser ();   	
    	userDao.insertUser(user);
    	return user;
	}

    public User loadUser (User user) {
    	return userDao.loadUser(user.getId());
	}	

    public User populateUser () {
        User user = new User();
        user.setName (getString1(45));
        user.setEmail (getString1(45));
          Department departmentId1 = injectDepartment();	
        //Integer Integer
        user.setDepartmentId(departmentId1);

        return user;
    }

    // dependency Company injection
    public Company injectCompany () {
	    // if Company has already been injected, 
		// use the same one to avoid recursivity injections
		Company company;
		if (hasAlreadyBeenInjected (Company.class)) {
		   company = (Company)getInjected (Company.class);
		} else {
		   company = populateCompany ();
           companyDao.insertCompany (company);
		   setInjected (Company.class, company);
		}
        return company;
    }

    public Company populateCompany () {
        Company company = new Company();
        company.setName (getString1(45));
        return company;
    }   
    // dependency Department injection
    public Department injectDepartment () {
	    // if Department has already been injected, 
		// use the same one to avoid recursivity injections
		Department department;
		if (hasAlreadyBeenInjected (Department.class)) {
		   department = (Department)getInjected (Department.class);
		} else {
		   department = populateDepartment ();
           departmentDao.insertDepartment (department);
		   setInjected (Department.class, department);
		}
        return department;
    }

    public Department populateDepartment () {
        Department department = new Department();
        department.setName (getString1(45));
         return department;
    }   
     
    public User populateFirstNonPkFieldUser () {
       // works if the table does not contain only pk
       User user = new User();
       return user;
    }
        
}