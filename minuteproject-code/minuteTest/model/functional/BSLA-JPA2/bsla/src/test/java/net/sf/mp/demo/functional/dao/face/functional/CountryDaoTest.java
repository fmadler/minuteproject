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

import net.sf.mp.demo.functional.domain.functional.Country;
import net.sf.mp.demo.functional.dao.face.functional.CountryDao;

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
import net.sf.mp.demo.functional.dao.face.functional.CountryDao;
import net.sf.mp.demo.functional.domain.functional.Country;
import net.sf.mp.demo.functional.dao.face.functional.CityDao;
import net.sf.mp.demo.functional.domain.functional.City;

public class CountryDaoTest extends AdapterFunctionalTestDao {

    // Test are commented because the sample set use will setting up datase with data
    // might not correspond to the anything valuable
    // resulting in a failure of your test

    // work with minuteproject updatable code feature to exclude this artifact from consecutive
    // generation erasure and adapt you test to your data scenario.

	@Autowired @Qualifier("countryDao")
	protected CountryDao countryDao;
	@Autowired @Qualifier("cityDao")
	protected CityDao cityDao;
	
	/*
	* Do the insert first.
	* To do the insert do on all mandatory fields;
	* If a mandatory field is a fk => insert the mandatory entity and give the entity value to populate the fk.
	* Then perform load update, delete, loadfirstlevel
	*/
    	
	@Test
	public void testInsertCountry () {
	    Country country = insertCountry ();
	    assertNotNull(country);
	}
	
	@Test	
    public void testLoadCountry () {
    	Country country = insertCountry();
    	Country country2 = loadCountry(country);
       // assertion
     	assertTrue(country.getId().equals(country2.getId()));
     	assertTrue(country.getName().equals(country2.getName()));
	}	
		
	@Test		
	public void testDeleteCountry () {
    	Country country = insertCountry();
    	countryDao.deleteCountry(country);
    	Country country2 = loadCountry(country);
        assertNull (country2);
    }
	
	@Test	
	public void testUpdateCountry () {
    	Country country = insertCountry();
    	Country country2 = loadCountry(country);  	
        country.setName (getString2(45));
	    countryDao.updateCountry(country);
    	Country country3 = loadCountry(country);
        // assertion
     	assertTrue(country.getId().equals(country3.getId()));
     	assertTrue(country.getName().equals(country3.getName()));
	}
/* updateNotNull is not on both interface	
	public void testUpdateNotNullCountry () {
    	Country country = insertCountry();
    	//Country country2 = loadCountry(country);  	
		Country country2 = new Country();
        country2.setId(country.getId());
        country2.setName (getString2(45));
	    countryDao.updateNotNullOnlyCountry(country2);
    	Country country3 = loadCountry(country);
        // assertion
     	assertTrue(country3.getId().equals(country.getId()));
     	assertTrue(country3.getName().equals(country2.getName()));
     	assertTrue(country3.getCapital().equals(country.getCapital()));
	}	
*/   
 
	@Test	
    public void testPagination () {
        //assuming that there is something in the DB
        insertCountry ();

        Integer start = 0;
        Integer max = 10;
        Country sort = new Country();
        EntitySort<Country> entitySort = new EntitySort<Country>(sort, QuerySortOrder.ASC);
        Country criteria = new Country();
        EntityCriteria<Country> entityCriteria = new EntityCriteria<Country>(criteria, EntityMatchType.ALL, OperandType.CONTAINS, true);

        Country what = populateFirstNonPkFieldCountry ();

        //Country.fullMask();

        QueryData<Country> queryData = new QueryData<Country>(start, max, entitySort,entityCriteria, what);
        countryDao.find(queryData);
        assertTrue (queryData.getTotalResultCount()>0);

    }
 
    public Country insertCountry () {
        Country country = populateCountry ();   	
    	countryDao.insertCountry(country);
    	return country;
	}

    public Country loadCountry (Country country) {
    	return countryDao.loadCountry(country.getId());
	}	

    public Country populateCountry () {
        Country country = new Country();
        country.setName (getString1(45));
        return country;
    }

    // dependency Country injection
    public Country injectCountry () {
	    // if Country has already been injected, 
		// use the same one to avoid recursivity injections
		Country country;
		if (hasAlreadyBeenInjected (Country.class)) {
		   country = (Country)getInjected (Country.class);
		} else {
		   country = populateCountry ();
           countryDao.insertCountry (country);
		   setInjected (Country.class, country);
		}
        return country;
    }

    // dependency City injection
    public City injectCity () {
	    // if City has already been injected, 
		// use the same one to avoid recursivity injections
		City city;
		if (hasAlreadyBeenInjected (City.class)) {
		   city = (City)getInjected (City.class);
		} else {
		   city = populateCity ();
           cityDao.insertCity (city);
		   setInjected (City.class, city);
		}
        return city;
    }

    public City populateCity () {
        City city = new City();
        city.setName (getString1(45));
          Country country1 = injectCountry();
        //Integer Integer    Integer    	
        city.setCountryId(country1);
        return city;
    }   
     
    public Country populateFirstNonPkFieldCountry () {
       // works if the table does not contain only pk
       Country country = new Country();
       return country;
    }
        
}