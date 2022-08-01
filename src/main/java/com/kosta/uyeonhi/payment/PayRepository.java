package com.kosta.uyeonhi.payment;

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.signUp.UserVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface PayRepository extends CrudRepository<PayVO, Long>, QuerydslPredicateExecutor<PayVO> {
	public List<PayVO> findByUser(UserVO user);

	public default Predicate makePredicate(String type, String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		QPayVO pay = QPayVO.payVO;

		if (type == null)
			return builder;
		builder.and(pay.p_id.gt(0));
		
		return builder;
	}
}