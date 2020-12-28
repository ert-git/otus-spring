package ru.otus.levina.hw03.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.levina.hw03.config.AppProperties;
import ru.otus.levina.hw03.domain.Question;
import ru.otus.levina.hw03.services.core.MessageService;
import ru.otus.levina.hw03.services.formatters.MessageFormatter;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class CsvQuestionsRepository implements QuestionsRepository {
    private static final int ID_COL_IDX = 0;
    private static final int QUESTION_COL_IDX = 1;
    private static final int CORRECT_ANSWER_COL_IDX = 2;
    private static final int ANSWERS_COL_IDX = 3;
    private final String csvResourceName;

    public CsvQuestionsRepository(AppProperties props) {
        String s = props.getCsvResourceName();
        this.csvResourceName = String.format("%s_%s.csv", props.getCsvResourceName(), props.getLocale());
    }

    @Override
    public List<Question> loadQuestions() throws QuestionsRepositoryException {
        log.debug("loadQuestions {}", csvResourceName);
        List<Question> questions = new ArrayList<>();
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(csvResourceName)) {
            CSVFormat format = CSVFormat.DEFAULT;
            CSVParser parser = CSVParser.parse(inputStream, Charset.forName("utf-8"), format);
            for (CSVRecord csvRecord : parser) {
                Question q = new Question(
                        Integer.parseInt(csvRecord.get(ID_COL_IDX)),
                        csvRecord.get(QUESTION_COL_IDX));
                q.setCorrectAnswer(csvRecord.get(CORRECT_ANSWER_COL_IDX));
                int size = csvRecord.size();
                for (int i = ANSWERS_COL_IDX; i < size; i++) {
                    q.getChoices().add(csvRecord.get(i));
                }
                questions.add(q);
            }
        } catch (Exception e) {
            log.error("readQuestions: failed for {}", csvResourceName, e);
            throw new QuestionsRepositoryException(e);
        }
        return questions;
    }
}
