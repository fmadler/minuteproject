package net.sf.minuteProject.model.data.criteria;

import junit.framework.Assert;
import net.sf.minuteProject.architecture.bsla.domain.AbstractDomainObject;
import net.sf.minuteProject.model.data.criteria.constant.EntityMatchType;
import net.sf.minuteProject.model.data.criteria.constant.OperandType;
import net.sf.minuteProject.model.data.criteria.constant.QuerySortOrder;
import net.sf.minuteProject.model.data.criteria.order.OrderCriteria;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryDataTest {

	QueryData<DummyDo> queryData;
	QueryData<DummyDo> previousQueryData;
	private int start = 0;
	private int max = 10;
	DummyDo a = new DummyDo();
	DummyDo b = new DummyDo();
	DummyDo a1 = new DummyDo();
	DummyDo b1 = new DummyDo(),b2 = new DummyDo();
	EntitySort<DummyDo> entitySort;
	EntityCriteria<DummyDo> entityCriteriaEmpty;
	EntityCriteria<DummyDo> entityCriteriaA1, entityCriteriaB1, entityCriteriaB2;
	
	@Before
	public void init() {
		entitySort = new EntitySort<DummyDo>(a, QuerySortOrder.ASC);
		entityCriteriaEmpty = new EntityCriteria<DummyDo>(a, EntityMatchType.ALL, OperandType.EQUALS, true);
		a1.something="test";
		entityCriteriaA1 = new EntityCriteria<DummyDo>(a1, EntityMatchType.ALL, OperandType.EQUALS, true);
		b1.something="test";
		entityCriteriaB1 = new EntityCriteria<DummyDo>(b1, EntityMatchType.ALL, OperandType.EQUALS, true);
		b2.something="test2";
		entityCriteriaB2 = new EntityCriteria<DummyDo>(b2, EntityMatchType.ALL, OperandType.EQUALS, true);
	}
	
	@Test
	public void testNeedsNewCount() {
		queryData = new QueryData<DummyDo>(start, max, entitySort, entityCriteriaEmpty, a);
		previousQueryData = new QueryData<DummyDo>(20, 30, entitySort, entityCriteriaEmpty, b);
		assertFalse (queryData.needsNewCount(previousQueryData));
	}
		
	@Test
	public void testNeedsNewCountNullWhat() {
		queryData = new QueryData<DummyDo>(start, max, entitySort, entityCriteriaEmpty);
		previousQueryData = new QueryData<DummyDo>(20, 30, entitySort, entityCriteriaEmpty);
		assertFalse (queryData.needsNewCount(previousQueryData));
	}
	
	@Test
	public void testNeedsNewCountWithCriteriaMismatchWithDummy() {
		queryData = new QueryData<DummyDo>(start, max, entitySort, entityCriteriaEmpty);
		previousQueryData = new QueryData<DummyDo>(20, 30, entitySort, entityCriteriaB1);
		assertTrue (queryData.needsNewCount(previousQueryData));
	}
	
	@Test
	public void testNeedsNewCountWithCriteriaMatch() {
		queryData = new QueryData<DummyDo>(start, max, entitySort, entityCriteriaA1);
		previousQueryData = new QueryData<DummyDo>(20, 30, entitySort, entityCriteriaB1);
		assertFalse (queryData.needsNewCount(previousQueryData));
	}
	
	@Test
	public void testNeedsNewCountWithCriteriaMismatch() {
		queryData = new QueryData<DummyDo>(start, max, entitySort, entityCriteriaB1);
		previousQueryData = new QueryData<DummyDo>(20, 30, entitySort, entityCriteriaB2);
		assertTrue (queryData.needsNewCount(previousQueryData));
	}
	
	private class DummyDo extends AbstractDomainObject{
		private String something;

		public String getSomething() {
			return something;
		}

		public void setSomething(String something) {
			this.something = something;
		}

		@Override
		public boolean equalsMask(Object object) {
			if (object == null) return false;	
			if (object == this) return true;
			if (!(object instanceof DummyDo)) return false;
			DummyDo dummyDo = (DummyDo) object;
			if (dummyDo.something!=null && something!=null && !dummyDo.something.equals(something)) return false;
			if ((dummyDo.something!=null && something==null) || (dummyDo.something==null && something!=null)) return false;
			return true;
		}

		@Override
		public AbstractDomainObject clone() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AbstractDomainObject mask(String field) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
