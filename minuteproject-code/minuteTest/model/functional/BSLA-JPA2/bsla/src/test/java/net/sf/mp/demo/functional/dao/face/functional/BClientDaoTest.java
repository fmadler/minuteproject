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

import net.sf.mp.demo.functional.domain.functional.BClient;
import net.sf.mp.demo.functional.dao.face.functional.BClientDao;

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
import net.sf.mp.demo.functional.dao.face.functional.AddressDao;
import net.sf.mp.demo.functional.domain.functional.Address;
import net.sf.mp.demo.functional.dao.face.functional.CountryDao;
import net.sf.mp.demo.functional.domain.functional.Country;
import net.sf.mp.demo.functional.dao.face.functional.CityDao;
import net.sf.mp.demo.functional.domain.functional.City;

public class BClientDaoTest extends AdapterFunctionalTestDao {

    // Test are commented because the sample set use will setting up datase with data
    // might not correspond to the anything valuable
    // resulting in a failure of your test

    // work with minuteproject updatable code feature to exclude this artifact from consecutive
    // generation erasure and adapt you test to your data scenario.

	@Autowired @Qualifier("BClientDao")
	protected BClientDao BClientDao;
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
	public void testInsertBClient () {
	    BClient bclient = insertBClient ();
	    assertNotNull(bclient);
	}
	
	@Test	
    public void testLoadBClient () {
    	BClient bclient = insertBClient();
    	BClient bclient2 = loadBClient(bclient);
       // assertion
     	assertTrue(bclient.getId().equals(bclient2.getId()));
     	assertTrue(bclient.getName().equals(bclient2.getName()));
     	assertTrue(bclient.getDescription().equals(bclient2.getDescription()));
     	assertTrue(bclient.getAge().equals(bclient2.getAge()));
     	assertTrue(bclient.getAddressAddressId().equals(bclient2.getAddressAddressId()));
     	assertTrue(bclient.getTitle().equals(bclient2.getTitle()));
	}	
		
	@Test		
	public void testDeleteBClient () {
    	BClient bclient = insertBClient();
    	BClientDao.deleteBClient(bclient);
    	BClient bclient2 = loadBClient(bclient);
        assertNull (bclient2);
    }
	
	@Test	
	public void testUpdateBClient () {
    	BClient bclient = insertBClient();
    	BClient bclient2 = loadBClient(bclient);  	
        bclient.setName (getString2(45));
        bclient.setDescription (getString2(45));
        bclient.setAge (getInteger2());
        bclient.setTitle (getString2(45));
	    BClientDao.updateBClient(bclient);
    	BClient bclient3 = loadBClient(bclient);
        // assertion
     	assertTrue(bclient.getId().equals(bclient3.getId()));
     	assertTrue(bclient.getName().equals(bclient3.getName()));
     	assertTrue(bclient.getDescription().equals(bclient3.getDescription()));
     	assertTrue(bclient.getAge().equals(bclient3.getAge()));
     	assertTrue(bclient.getAddressAddressId().equals(bclient3.getAddressAddressId()));
     	assertTrue(bclient.getTitle().equals(bclient3.getTitle()));
	}
/* updateNotNull is not on both interface	
	public void testUpdateNotNullBClient () {
    	BClient bclient = insertBClient();
    	//BClient bclient2 = loadBClient(bclient);  	
		BClient bclient2 = new BClient();
        bclient2.setId(bclient.getId());
        bclient2.setName (getString2(45));
        bclient2.setAge (getInteger2());
        bclient2.setTitle (getString2(45));
	    BClientDao.updateNotNullOnlyBClient(bclient2);
    	BClient bclient3 = loadBClient(bclient);
        // assertion
     	assertTrue(bclient3.getId().equals(bclient.getId()));
     	assertTrue(bclient3.getName().equals(bclient2.getName()));
     	assertTrue(bclient3.getDescription().equals(bclient.getDescription()));
     	assertTrue(bclient3.getAge().equals(bclient2.getAge()));
     	assertTrue(bclient3.getAddressAddressId().equals(bclient.getAddressAddressId()));
     	assertTrue(bclient3.getTitle().equals(bclient2.getTitle()));
	}	
*/   
 
	@Test	
    public void testPagination () {
        //assuming that there is something in the DB
        insertBClient ();

        Integer start = 0;
        Integer max = 10;
        BClient sort = new BClient();
        EntitySort<BClient> entitySort = new EntitySort<BClient>(sort, QuerySortOrder.ASC);
        BClient criteria = new BClient();
        EntityCriteria<BClient> entityCriteria = new EntityCriteria<BClient>(criteria, EntityMatchType.ALL, OperandType.CONTAINS, true);

        BClient what = populateFirstNonPkFieldBClient ();

        //BClient.fullMask();

        QueryData<BClient> queryData = new QueryData<BClient>(start, max, entitySort,entityCriteria, what);
        BClientDao.find(queryData);
        assertTrue (queryData.getTotalResultCount()>0);

    }
 
    public BClient insertBClient () {
        BClient bclient = populateBClient ();   	
    	BClientDao.insertBClient(bclient);
    	return bclient;
	}

    public BClient loadBClient (BClient bclient) {
    	return BClientDao.loadBClient(bclient.getId());
	}	

    public BClient populateBClient () {
        BClient bclient = new BClient();
        bclient.setName (getString1(45));
        bclient.setDescription (getString1(45));
        bclient.setAge (getInteger1());
        bclient.setTitle (getString1(45));
          Address addressAddressId1 = injectAddress();	
        //Integer Integer
        bclient.setAddressAddressId(addressAddressId1);

        return bclient;
    }

    // dependency Address injection
    public Address injectAddress () {
	    // if Address has already been injected, 
		// use the same one to avoid recursivity injections
		Address address;
		if (hasAlreadyBeenInjected (Address.class)) {
		   address = (Address)getInjected (Address.class);
		} else {
		   address = populateAddress ();
           addressDao.insertAddress (address);
		   setInjected (Address.class, address);
		}
        return address;
    }

    public Address populateAddress () {
        Address address = new Address();
        address.setStreet1 (getString1(45));
        address.setNumber (getInteger1());
          City city1 = injectCity();
        //Integer Integer    Integer    	
        address.setCityId(city1);
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
     
    public BClient populateFirstNonPkFieldBClient () {
       // works if the table does not contain only pk
       BClient bclient = new BClient();
       return bclient;
    }
        
}