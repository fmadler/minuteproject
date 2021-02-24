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

import net.sf.mp.demo.functional.domain.functional.Company;
import net.sf.mp.demo.functional.dao.face.functional.CompanyDao;

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

public class CompanyDaoTest extends AdapterFunctionalTestDao {

    // Test are commented because the sample set use will setting up datase with data
    // might not correspond to the anything valuable
    // resulting in a failure of your test

    // work with minuteproject updatable code feature to exclude this artifact from consecutive
    // generation erasure and adapt you test to your data scenario.

	@Autowired @Qualifier("companyDao")
	protected CompanyDao companyDao;
	
	/*
	* Do the insert first.
	* To do the insert do on all mandatory fields;
	* If a mandatory field is a fk => insert the mandatory entity and give the entity value to populate the fk.
	* Then perform load update, delete, loadfirstlevel
	*/
    	
	@Test
	public void testInsertCompany () {
	    Company company = insertCompany ();
	    assertNotNull(company);
	}
	
	@Test	
    public void testLoadCompany () {
    	Company company = insertCompany();
    	Company company2 = loadCompany(company);
       // assertion
     	assertTrue(company.getId().equals(company2.getId()));
     	assertTrue(company.getName().equals(company2.getName()));
	}	
		
	@Test		
	public void testDeleteCompany () {
    	Company company = insertCompany();
    	companyDao.deleteCompany(company);
    	Company company2 = loadCompany(company);
        assertNull (company2);
    }
	
	@Test	
	public void testUpdateCompany () {
    	Company company = insertCompany();
    	Company company2 = loadCompany(company);  	
        company.setName (getString2(45));
	    companyDao.updateCompany(company);
    	Company company3 = loadCompany(company);
        // assertion
     	assertTrue(company.getId().equals(company3.getId()));
     	assertTrue(company.getName().equals(company3.getName()));
	}
/* updateNotNull is not on both interface	
	public void testUpdateNotNullCompany () {
    	Company company = insertCompany();
    	//Company company2 = loadCompany(company);  	
		Company company2 = new Company();
        company2.setId(company.getId());
        company2.setName (getString2(45));
	    companyDao.updateNotNullOnlyCompany(company2);
    	Company company3 = loadCompany(company);
        // assertion
     	assertTrue(company3.getId().equals(company.getId()));
     	assertTrue(company3.getName().equals(company2.getName()));
	}	
*/   
 
	@Test	
    public void testPagination () {
        //assuming that there is something in the DB
        insertCompany ();

        Integer start = 0;
        Integer max = 10;
        Company sort = new Company();
        EntitySort<Company> entitySort = new EntitySort<Company>(sort, QuerySortOrder.ASC);
        Company criteria = new Company();
        EntityCriteria<Company> entityCriteria = new EntityCriteria<Company>(criteria, EntityMatchType.ALL, OperandType.CONTAINS, true);

        Company what = populateFirstNonPkFieldCompany ();

        //Company.fullMask();

        QueryData<Company> queryData = new QueryData<Company>(start, max, entitySort,entityCriteria, what);
        companyDao.find(queryData);
        assertTrue (queryData.getTotalResultCount()>0);

    }
 
    public Company insertCompany () {
        Company company = populateCompany ();   	
    	companyDao.insertCompany(company);
    	return company;
	}

    public Company loadCompany (Company company) {
    	return companyDao.loadCompany(company.getId());
	}	

    public Company populateCompany () {
        Company company = new Company();
        company.setName (getString1(45));
        return company;
    }

     
    public Company populateFirstNonPkFieldCompany () {
       // works if the table does not contain only pk
       Company company = new Company();
       return company;
    }
        
}