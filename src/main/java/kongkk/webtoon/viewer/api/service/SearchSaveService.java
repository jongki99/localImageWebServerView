package kongkk.webtoon.viewer.api.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import kongkk.webtoon.viewer.domain.entity.BookEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SearchSaveService {
	public void testEntityBuilder() {
		log.debug("testEntityBuilder start !!!");
		BookEntity.BookEntityBuilder<?, ?> builder = BookEntity.builder();
		
		BookEntity book = builder
				.name(null)
				.createAt(null)
				.updatedAt(null)
				.build()
		;
		
		book.setCreateAt(LocalDateTime.now());
	}
}
