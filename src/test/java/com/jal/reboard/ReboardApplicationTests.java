package com.jal.reboard;

import com.jal.reboard.domain.dto.BoardDTO;
import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.domain.type.CType;
import com.jal.reboard.repository.BoardRepository;
import com.jal.reboard.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class ReboardApplicationTests {

	@Autowired
	BoardRepository boardRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void 글작성() {

		IntStream.rangeClosed(1,10).forEach(i -> {

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
				.bno(5L)
				.title("@Test에서")
				.content("테스트")
				.build();

		boardService.글수정(dto);
	}

}
