package com.jal.reboard;

import com.jal.reboard.domain.dto.BoardDTO;
import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.domain.entity.Member;
import com.jal.reboard.domain.type.CType;
import com.jal.reboard.domain.type.RoleType;
import com.jal.reboard.repository.BoardRepository;
import com.jal.reboard.repository.MemberRepository;
import com.jal.reboard.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@SpringBootTest
class ReboardApplicationTests {

	@Autowired
	BoardRepository boardRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void 글작성() {

		IntStream.rangeClosed(1,50).forEach(i -> {

			Board board = Board.builder()
					.title("제목c.."+i)
					.content("내용c..." + i)
					.build();

			board.setCtype(CType.ONBOARD);
			boardRepository.save(board);

		});
	}

	@Autowired
	private BoardService boardService;

	@Test
	public void 글수정() {

		BoardDTO dto = BoardDTO.builder()
				.bno(2L)
				.title("@Test에서")
				.content("테스트")
				.build();

		boardService.글수정(dto);
	}

	@Test
	public void 글삭제() {

		LongStream.rangeClosed(20L,113L).forEach(i -> {

			boardService.글삭제(i);

		});
	}

	@Autowired
	MemberRepository memberRepository;

	@Test
	public void 유저생성() {

		IntStream.rangeClosed(1,10).forEach(i -> {

			Member member = Member.builder()
					.username("user"+i)
					.password("1234")
					.roleType(RoleType.USER)
					.build();

			memberRepository.save(member);

		});
	}

//	@Test
//	public void 작성자추가() {
//
//		LongStream.rangeClosed(1L, 197L).forEach(i -> {
//
//			if (boardRepository.existsById(i)) {
//
//				long mno = (long)(Math.random() * 10) +1;
//
//				BoardDTO dto = BoardDTO.builder()
//						.bno(i)
//						.writer(mno)
//						.build();
//
//				boardService.작성자추가(dto);
//
//			}
//		});
//	}

}
