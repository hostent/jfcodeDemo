package com.jfcore.tools;

import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Copy {
	
	public static void main(String[] args) {
		
		
	}

	public static <T, V> List<V> copyList(List<T> list, Class<T> tcls, Class<V> vcls) {

		List<V> resList = new ArrayList<V>();
		if (Objects.nonNull(list) && list.size() > 0) {
			BeanCopier copier = BeanCopier.create(tcls, vcls, false);

			for (T t : list) {
				V v = null;
				try {
					v = vcls.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				copier.copy(t, v, null);

				resList.add(v);
			}
		}

		return resList;
	}

	public static <T, V> V copy(T t, Class<T> tcls, Class<V> vcls) {

		BeanCopier copier = BeanCopier.create(tcls, vcls, false);

		V v = null;
		try {
			v = vcls.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		copier.copy(t, v, null);

		return v;

	}
	
	

}
