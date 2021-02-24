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

import net.sf.mp.demo.functional.domain.functional.Department;
import net.sf.mp.demo.functional.dao.face.functional.DepartmentDao;

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

public class DepartmentDaoTest extends AdapterFunctionalTestDao {

    // Test are commented because the sample set use will setting up datase with data
    // might not correspond to the anything valuable
    // resulting in a failure of your test

    // work with minuteproject updatable code feature to exclude this artifact from consecutive
    // generation erasure and adapt you test to your data scenario.

	@Autowired @Qualifier("departmentDao")
	protected DepartmentDao departmentDao;
	@Autowired @Qualifier("companyDao")
	protected CompanyDao companyDao;
	
	/*
	* Do the insert first.
	* To do the insert do on all mandatory fields;
	* If a mandatory field is a fk => insert the mandatory entity and give the entity value to populate the fk.
	* Then perform load update, delete, loadfirstlevel
	*/
    	
	@Test
	public void testInsertDepartment () {
	    Department department = insertDepartment ();
	    assertNotNull(department);
	}
	
	@Test	
    public void testLoadDepartment () {
    	Department department = insertDepartment();
    	Department department2 = loadDepartment(department);
       // assertion
     	assertTrue(department.getId().equals(department2.getId()));
     	assertTrue(department.getName().equals(department2.getName()));
	}	
		
	@Test		
	public void testDeleteDepartment () {
    	Department department = insertDepartment();
    	departmentDao.deleteDepartment(department);
    	Department department2 = loadDepartment(department);
        assertNull (department2);
    }
	
	@Test	
	public void testUpdateDepartment () {
    	Department department = insertDepartment();
    	Department department2 = loadDepartment(department);  	
        department.setName (getString2(45));
	    departmentDao.updateDepartment(department);
    	Department department3 = loadDepartment(department);
        // assertion
     	assertTrue(department.getId().equals(department3.getId()));
     	assertTrue(department.getName().equals(department3.getName()));
	}
/* updateNotNull is not on both interface	
	public void testUpdateNotNullDepartment () {
    	Department department = insertDepartment();
    	//Department department2 = loadDepartment(department);  	
		Department department2 = new Department();
        department2.setId(department.getId());
	    departmentDao.updateNotNullOnlyDepartment(department2);
    	Department department3 = loadDepartment(department);
        // assertion
     	assertTrue(department3.getId().equals(department.getId()));
     	assertTrue(department3.getParentDepartmentId().equals(department.getParentDepartmentId()));
     	assertTrue(department3.getName().equals(department.getName()));
     	assertTrue(department3.getCompanyId().equals(department.getCompanyId()));
	}	
*/   
 
	@Test	
    public void testPagination () {
        //assuming that there is something in the DB
        insertDepartment ();

        Integer start = 0;
        Integer max = 10;
        Department sort = new Department();
        EntitySort<Department> entitySort = new EntitySort<Department>(sort, QuerySortOrder.ASC);
        Department criteria = new Department();
        EntityCriteria<Department> entityCriteria = new EntityCriteria<Department>(criteria, EntityMatchType.ALL, OperandType.CONTAINS, true);

        Department what = populateFirstNonPkFieldDepartment ();

        //Department.fullMask();

        QueryData<Department> queryData = new QueryData<Department>(start, max, entitySort,entityCriteria, what);
        departmentDao.find(queryData);
        assertTrue (queryData.getTotalResultCount()>0);

    }
 
    public Department insertDepartment () {
        Department department = populateDepartment ();   	
    	departmentDao.insertDepartment(department);
    	return department;
	}

    public Department loadDepartment (Department department) {
    	return departmentDao.loadDepartment(department.getId());
	}	

    public Department populateDepartment () {
        Department department = new Department();
        department.setName (getString1(45));
         return department;
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
     
    public Department populateFirstNonPkFieldDepartment () {
       // works if the table does not contain only pk
       Department department = new Department();
       return department;
    }
        
}