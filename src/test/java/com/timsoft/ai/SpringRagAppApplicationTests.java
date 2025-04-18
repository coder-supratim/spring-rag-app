package com.timsoft.ai;

import com.timsoft.ai.service.ChatBotService;
import com.timsoft.ai.service.DataLoaderService;
import com.timsoft.ai.service.DataRetrievalService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@Profile("airag")
@SpringBootTest(classes = SpringRagAppApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpringAiRagApplicationLiveTest {

	private final Logger logger = LoggerFactory.getLogger(SpringRagAppApplication.class);
	@Autowired
	private ChatBotService chatBotService;
	@Autowired
	private DataLoaderService dataLoaderService;
	@Autowired
	private DataRetrievalService dataRetrievalService;

	@BeforeAll
	void setup() {
		dataLoaderService.deleteAll("faq:*");
		dataLoaderService.load();
	}

	@Test
	void whenQuery_thenRetrieveSimilarDataFromRedisVectorDB() {
		String query = "How are employees supposed to dress";
		List<Document> documents = dataRetrievalService.searchData(query);
		logger.info("The number of documents fetched: {}", documents.size());
		logger.info("Search data: ");
		documents.forEach(e -> logger.info(e.getFormattedContent()));
		assertTrue(documents.size() > 1);
	}

	@Test
	void whenQueryAskedWithinContext_thenAnswerFromTheContext() {
		String response = chatBotService.chat("How are employees supposed to dress?");
		assertNotNull(response);
		logger.info("Response from LLM: {}", response);
	}

	@Test
	void whenQueryAskedOutOfContext_thenDontAnswer() {
		String response = chatBotService.chat("What should employees eat?");
		assertEquals("I'm sorry I don't have the information you are looking for.", response);
		logger.info("Response from the LLM: {}", response);
	}
}