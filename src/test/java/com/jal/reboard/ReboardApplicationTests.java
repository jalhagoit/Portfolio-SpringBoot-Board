package com.jal.reboard;

import com.jal.reboard.domain.dto.BoardDTO;
import com.jal.reboard.domain.entity.Board;
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
					.title("제목.."+i)
					.content("내용..." + i)
					.build();

			boardRepository.save(board);

		});
	}

	@Autowired
	private BoardService boardService;

	@Test
	public void 글수정() {

		BoardDTO dto = BoardDTO.builder()
				.bno(5L)
				.title("제목 변경2")
				.content("내용 변경2")
				.build();

		boardService.modify(dto);
	}

}
