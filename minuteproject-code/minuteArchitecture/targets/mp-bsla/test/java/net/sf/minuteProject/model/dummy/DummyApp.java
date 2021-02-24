package net.sf.minuteProject.model.dummy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.minuteProject.model.data.criteria.order.OrderCriteria;
import net.sf.minuteProject.model.data.criteria.value.IntegerValue;

public class DummyApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DummyDo dummyDo = new DummyDo();

		dummyDo.getBirthdate().between(new Date(), new Date());
		dummyDo.getBirthdate().between(new Date(), new Date());
		dummyDo.getBirthdate().lt(new Date());
//		Or(dummyDo.getBirthdate().lt(new Date()));
		dummyDo.or(dummyDo.getBirthdate().le(new Date()));
		dummyDo.or(dummyDo.getBirthdate().le(new Date()).
				and(dummyDo.getBirthdate().gt(new Date())).
				and(dummyDo.getBirthdate().ge(new Date())));
		String s = dummyDo.popWhereCriteria();
		

		System.out.println(">>>\n"+s);
		System.out.println(">>> TODO algo to correctly depile the stack: reverse polish notation!!!");
		
		DummyWhatDo dummyWhatDo = new DummyWhatDo();
		dummyWhatDo.setSalary().mask().order(OrderCriteria.ASC);
		dummyWhatDo.setSalary().avg();		
		dummyWhatDo.count();
		
		System.out.println(">>>\n"+dummyWhatDo.popWhatCriteria());
		
		List<DummyWhatDo> list = getDummyWhatDo (dummyWhatDo, dummyDo);
		
		for (DummyWhatDo dummyWhatDo2 : list) {
			// we know the output type.
			System.out.println(dummyWhatDo2.getSalary().value());
//			System.out.println(dummyWhatDo2.getSalary().avg().value());
		}
	}

	private static List<DummyWhatDo> getDummyWhatDo(DummyWhatDo dummyWhatDo,
			DummyDo dummyDo) {
		// TODO Auto-generated method stub
		List<DummyWhatDo> list = new ArrayList<DummyWhatDo>();
		list.add(new DummyWhatDo(new IntegerValue(2)));
		list.add(new DummyWhatDo(new IntegerValue(3)));
		
		IntegerValue sal = new IntegerValue(Integer.valueOf("12335"));
//		sal.avg().setValue(Integer.valueOf("135"));
		DummyWhatDo do1 = new DummyWhatDo(sal);
		list.add(do1);
		return list;
	}

}
