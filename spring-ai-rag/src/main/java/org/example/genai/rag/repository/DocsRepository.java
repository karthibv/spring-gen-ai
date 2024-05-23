package org.example.genai.rag.repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class DocsRepository {

    private final VectorStore vectorStore;
    private final Logger logger = LoggerFactory.getLogger(DocsRepository.class);

    public DocsRepository(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public List<String> getRelatedDocsFor(String userMessage) {
        return vectorStore.similaritySearch(userMessage).stream()
                .map(it -> it.getContent())
                .collect(Collectors.toList());
    }

    public void add(Resource doc) {
        vectorStore.accept(new TokenTextSplitter().apply(new TextReader(doc).get()));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ingestFilesAfterStartup() {
        try {
            Stream.of(new PathMatchingResourcePatternResolver().getResources("classpath*:docs/**/*"))
                    .forEach(it -> {
                        logger.info("Ingesting file: " + it.getFilename());
                        add(it);
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}