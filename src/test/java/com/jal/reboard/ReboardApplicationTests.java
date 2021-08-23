package com.jal.reboard;

import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.repository.BoardRepository;
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
					.title("Title..."+i)
					.content("Content....." + i)
					.build();

			boardRepository.save(board);

		});
	}

}
