package com.kosta.uyeonhi.applicant;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.uyeonhi.sns.Board;

public interface TagRepository extends JpaRepository<Board, BigDecimal> {

    public List<Board> findByTagContaining(String searchValue, Sort sort);
}
