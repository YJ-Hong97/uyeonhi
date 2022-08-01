package com.kosta.uyeonhi.reply;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.kosta.uyeonhi.signUp.UserVO;
import com.kosta.uyeonhi.sns.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reply{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reply_id;
	
	@Column(nullable = false, length = 30)
	private String reply_content;

	@ManyToOne(fetch = FetchType.LAZY)
	private UserVO user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Board board;
	
	private int reply_like;
	
	private boolean isRemoved= false;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //49~54 자기참조
    @JoinColumn(name = "parent_id", nullable = true)
    private Reply parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Reply> children = new ArrayList<>();
}
