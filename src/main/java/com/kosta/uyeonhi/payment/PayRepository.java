package com.kosta.uyeonhi.payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.kosta.uyeonhi.signUp.UserVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface PayRepository extends CrudRepository<PayVO, Long>, QuerydslPredicateExecutor<PayVO> {
	public Page<PayVO> findByUser(UserVO user, Pageable paging);

	public default Predicate makePredicate(String type, String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		QPayVO pay = QPayVO.payVO;
		builder.and(pay.p_id.gt(0));

		if (type == null)
			return builder;

		return builder;
	}
}