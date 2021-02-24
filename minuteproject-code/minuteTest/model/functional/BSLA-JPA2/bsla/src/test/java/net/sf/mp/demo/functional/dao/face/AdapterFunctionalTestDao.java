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
	* - name      : BslaDaoTestAdapter
	* - file name : BslaDaoTestAdapter.vm
	* - time      : 2014/01/30 AD at 11:53:12 CET
*/
package net.sf.mp.demo.functional.dao.face;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
     "classpath:net/sf/mp/demo/functional/factory/spring/spring-config-Functional-BE-main.xml"
})
@TransactionConfiguration(transactionManager = "functionalTransactionManager") 
@Transactional
public class AdapterFunctionalTestDao extends AbstractTransactionalJUnit4SpringContextTests { 

	@Override
	@Autowired
	public void setDataSource(@Qualifier(value = "functionalDataSource") DataSource dataSource) {
	   this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}
    
    private Map<Class, Object> map;
    @Before
    public void setUp () {
        map = new HashMap();
    }
	
    protected boolean hasAlreadyBeenInjected (Class clazz) {
        return map.containsKey(clazz);
    }

    protected Object getInjected (Class clazz) {
        return map.get(clazz);
    }

    protected void setInjected (Class clazz, Object object) {
        map.put(clazz, object);
    }
	
    protected String getString1 (int length) {
        return StringUtils.substring ("This is a test string",0,length);
    }

    protected Integer getInteger1 () {
        return new Integer (100);
    }

    protected Long getLong (long l) {
        return new Long (l);
     }
         
    protected Long getLong1 () {
        return getLong (100);
    }

    protected Boolean getBoolean1 () {
        return new Boolean (false);
    }

    protected Long getDecimal1() {
        return getLong1();
    }

    protected BigDecimal getBigDecimal1() {
        return BigDecimal.valueOf(getLong1());
    }
    
    protected String getString2 (int length) {
        return StringUtils.substring ("that is a second test string",0,length);
    }

    protected Integer getInteger2 () {
        return new Integer (200);
    }
    
    protected Long getLong2 () {
       return getLong (200);
    }

    protected Boolean getBoolean2 () {
       return new Boolean (true);
    }

    protected Long getDecimal2() {
        return getLong2();
    }

    protected BigDecimal getBigDecimal2() {
        return BigDecimal.valueOf(getLong2());
    }
    
    protected Date getDate () {
       return new Date();
    }

    protected Timestamp getTimestamp () {
       return new Timestamp(getDate().getTime());
    }
        
    protected String getBlobString(int size) {
       return new String (new String[size].toString());
    }
    
    protected String getClobString(int size) {
       return new String (new String[size].toString());    
    }
    
    protected Long convertStringToLong (String s) {
       return Long.valueOf(s);
    }
    
    protected Long convertIntegerToLong (Integer i) {
       return convertStringToLong(i+"");
    }

    protected BigDecimal convertIntegerToBigDecimal (Integer i) {
       return BigDecimal.valueOf(convertIntegerToLong(i));
    }
   
    protected String convertBigDecimalToString (BigDecimal i) {
       return i+"";
    }

    protected Long convertBigDecimalToLong (BigDecimal i) {
       return Long.valueOf(convertBigDecimalToString(i));
    }
         
    protected Integer convertLongToInteger (Long l) {
    	return Integer.valueOf(l+"");
    }
                      
    protected Clob getClob(int size) {
    	Clob c = new Clob() {
			
			public void truncate(long len) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			public int setString(long pos, String str, int offset, int len)
					throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int setString(long pos, String str) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Writer setCharacterStream(long pos) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public OutputStream setAsciiStream(long pos) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public long position(Clob searchstr, long start) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public long position(String searchstr, long start) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public long length() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public String getSubString(long pos, int length) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getCharacterStream(long pos, long length) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Reader getCharacterStream() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public InputStream getAsciiStream() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public void free() throws SQLException {
				// TODO Auto-generated method stub
				
			}
		};
        return c;    
     }
     
    protected Blob getBlob(int size) {
    	Blob b = new Blob() {
			
			byte[] bytes = null;

			public java.io.InputStream getBinaryStream()
			   throws java.sql.SQLException
			{
			   return new java.io.ByteArrayInputStream(bytes);
			}

			public long length() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			public byte[] getBytes(long pos, int length) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			public long position(byte[] pattern, long start)
					throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			public long position(Blob pattern, long start) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			public int setBytes(long pos, byte[] bytes) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			public int setBytes(long pos, byte[] bytes, int offset, int len)
					throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			public OutputStream setBinaryStream(long pos) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}

			public void truncate(long len) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			public void free() throws SQLException {
				// TODO Auto-generated method stub
				
			}

			public InputStream getBinaryStream(long pos, long length)
					throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
    			

		};
        return b;    
     }    

     
}