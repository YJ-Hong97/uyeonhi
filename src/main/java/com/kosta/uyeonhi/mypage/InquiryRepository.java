package com.kosta.uyeonhi.mypage;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import org.springframework.data.repository.CrudRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;




public interface InquiryRepository extends CrudRepository<InquiryVO, Long>, QuerydslPredicateExecutor<InquiryVO> {
	
	public default Predicate makePredicate(String type, String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		QInquiryVO inquiry = QInquiryVO.inquiryVO;
		
		 if(type== null) {
				return builder;
			}
		if(type.equals("title")) {
			builder.and(inquiry.title.like("%"+keyword+"%"));
		}else if(type.equals("user")) {
			builder.and(inquiry.user.nickname.like("%"+keyword+"%"));
		}else if(type.equals("content")) {
			builder.and(inquiry.content.like("%"+keyword+"%"));
		}
		
		builder.and(inquiry.inquiryId.gt(0l));
		
		return builder;
	}
}
