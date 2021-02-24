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

import net.sf.mp.demo.functional.domain.functional.Address;
import net.sf.mp.demo.functional.dao.face.functional.AddressDao;

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

public class AddressDaoTest extends AdapterFunctionalTestDao {

    // Test are commented because the sample set use will setting up datase with data
    // might not correspond to the anything valuable
    // resulting in a failure of your test

    // work with minuteproject updatable code feature to exclude this artifact from consecutive
    // generation erasure and adapt you test to your data scenario.

	@Autowired @Qualifier("addressDao")
	protected AddressDao addressDao;
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
	public void testInsertAddress () {
	    Address address = insertAddress ();
	    assertNotNull(address);
	}
	
	@Test	
    public void testLoadAddress () {
    	Address address = insertAddress();
    	Address address2 = loadAddress(address);
       // assertion
     	assertTrue(address.getAddressId().equals(address2.getAddressId()));
     	assertTrue(address.getStreet1().equals(address2.getStreet1()));
     	assertTrue(address.getNumber().equals(address2.getNumber()));
     	assertTrue(address.getCityId().equals(address2.getCityId()));
	}	
		
	@Test		
	public void testDeleteAddress () {
    	Address address = insertAddress();
    	addressDao.deleteAddress(address);
    	Address address2 = loadAddress(address);
        assertNull (address2);
    }
	
	@Test	
	public void testUpdateAddress () {
    	Address address = insertAddress();
    	Address address2 = loadAddress(address);  	
        address.setStreet1 (getString2(45));
        address.setNumber (getInteger2());
	    addressDao.updateAddress(address);
    	Address address3 = loadAddress(address);
        // assertion
     	assertTrue(address.getAddressId().equals(address3.getAddressId()));
     	assertTrue(address.getStreet1().equals(address3.getStreet1()));
     	assertTrue(address.getNumber().equals(address3.getNumber()));
     	assertTrue(address.getCityId().equals(address3.getCityId()));
	}
/* updateNotNull is not on both interface	
	public void testUpdateNotNullAddress () {
    	Address address = insertAddress();
    	//Address address2 = loadAddress(address);  	
		Address address2 = new Address();
        address2.setAddressId(address.getAddressId());
        address2.setStreet1 (getString2(45));
	    addressDao.updateNotNullOnlyAddress(address2);
    	Address address3 = loadAddress(address);
        // assertion
     	assertTrue(address3.getAddressId().equals(address.getAddressId()));
     	assertTrue(address3.getStreet1().equals(address2.getStreet1()));
     	assertTrue(address3.getNumber().equals(address.getNumber()));
     	assertTrue(address3.getCityId().equals(address.getCityId()));
	}	
*/   
 
	@Test	
    public void testPagination () {
        //assuming that there is something in the DB
        insertAddress ();

        Integer start = 0;
        Integer max = 10;
        Address sort = new Address();
        EntitySort<Address> entitySort = new EntitySort<Address>(sort, QuerySortOrder.ASC);
        Address criteria = new Address();
        EntityCriteria<Address> entityCriteria = new EntityCriteria<Address>(criteria, EntityMatchType.ALL, OperandType.CONTAINS, true);

        Address what = populateFirstNonPkFieldAddress ();

        //Address.fullMask();

        QueryData<Address> queryData = new QueryData<Address>(start, max, entitySort,entityCriteria, what);
        addressDao.find(queryData);
        assertTrue (queryData.getTotalResultCount()>0);

    }
 
    public Address insertAddress () {
        Address address = populateAddress ();   	
    	addressDao.insertAddress(address);
    	return address;
	}

    public Address loadAddress (Address address) {
    	return addressDao.loadAddress(address.getAddressId());
	}	

    public Address populateAddress () {
        Address address = new Address();
        address.setStreet1 (getString1(45));
        address.setNumber (getInteger1());
          City cityId1 = injectCity();	
        //Integer Integer
        address.setCityId(cityId1);

        return address;
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
     
    public Address populateFirstNonPkFieldAddress () {
       // works if the table does not contain only pk
       Address address = new Address();
       return address;
    }
        
}