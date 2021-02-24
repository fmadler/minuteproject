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

import net.sf.mp.demo.functional.domain.functional.City;
import net.sf.mp.demo.functional.dao.face.functional.CityDao;

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
import net.sf.mp.demo.functional.dao.face.functional.CityDao;
import net.sf.mp.demo.functional.domain.functional.City;
import net.sf.mp.demo.functional.dao.face.functional.CountryDao;
import net.sf.mp.demo.functional.domain.functional.Country;

public class CityDaoTest extends AdapterFunctionalTestDao {

    // Test are commented because the sample set use will setting up datase with data
    // might not correspond to the anything valuable
    // resulting in a failure of your test

    // work with minuteproject updatable code feature to exclude this artifact from consecutive
    // generation erasure and adapt you test to your data scenario.

	@Autowired @Qualifier("cityDao")
	protected CityDao cityDao;
	@Autowired @Qualifier("countryDao")
	protected CountryDao countryDao;
	
	/*
	* Do the insert first.
	* To do the insert do on all mandatory fields;
	* If a mandatory field is a fk => insert the mandatory entity and give the entity value to populate the fk.
	* Then perform load update, delete, loadfirstlevel
	*/
    	
	@Test
	public void testInsertCity () {
	    City city = insertCity ();
	    assertNotNull(city);
	}
	
	@Test	
    public void testLoadCity () {
    	City city = insertCity();
    	City city2 = loadCity(city);
       // assertion
     	assertTrue(city.getId().equals(city2.getId()));
     	assertTrue(city.getName().equals(city2.getName()));
     	assertTrue(city.getCountryId().equals(city2.getCountryId()));
	}	
		
	@Test		
	public void testDeleteCity () {
    	City city = insertCity();
    	cityDao.deleteCity(city);
    	City city2 = loadCity(city);
        assertNull (city2);
    }
	
	@Test	
	public void testUpdateCity () {
    	City city = insertCity();
    	City city2 = loadCity(city);  	
        city.setName (getString2(45));
	    cityDao.updateCity(city);
    	City city3 = loadCity(city);
        // assertion
     	assertTrue(city.getId().equals(city3.getId()));
     	assertTrue(city.getName().equals(city3.getName()));
     	assertTrue(city.getCountryId().equals(city3.getCountryId()));
	}
/* updateNotNull is not on both interface	
	public void testUpdateNotNullCity () {
    	City city = insertCity();
    	//City city2 = loadCity(city);  	
		City city2 = new City();
        city2.setId(city.getId());
        city2.setName (getString2(45));
	    cityDao.updateNotNullOnlyCity(city2);
    	City city3 = loadCity(city);
        // assertion
     	assertTrue(city3.getId().equals(city.getId()));
     	assertTrue(city3.getName().equals(city2.getName()));
     	assertTrue(city3.getCountryId().equals(city.getCountryId()));
	}	
*/   
 
	@Test	
    public void testPagination () {
        //assuming that there is something in the DB
        insertCity ();

        Integer start = 0;
        Integer max = 10;
        City sort = new City();
        EntitySort<City> entitySort = new EntitySort<City>(sort, QuerySortOrder.ASC);
        City criteria = new City();
        EntityCriteria<City> entityCriteria = new EntityCriteria<City>(criteria, EntityMatchType.ALL, OperandType.CONTAINS, true);

        City what = populateFirstNonPkFieldCity ();

        //City.fullMask();

        QueryData<City> queryData = new QueryData<City>(start, max, entitySort,entityCriteria, what);
        cityDao.find(queryData);
        assertTrue (queryData.getTotalResultCount()>0);

    }
 
    public City insertCity () {
        City city = populateCity ();   	
    	cityDao.insertCity(city);
    	return city;
	}

    public City loadCity (City city) {
    	return cityDao.loadCity(city.getId());
	}	

    public City populateCity () {
        City city = new City();
        city.setName (getString1(45));
          Country countryId1 = injectCountry();	
        //Integer Integer
        city.setCountryId(countryId1);

        return city;
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

    public Country populateCountry () {
        Country country = new Country();
        country.setName (getString1(45));
        return country;
    }   
     
    public City populateFirstNonPkFieldCity () {
       // works if the table does not contain only pk
       City city = new City();
       return city;
    }
        
}