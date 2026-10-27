package p466.studygroupfinder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.dialect.JdbcDialect;
import org.springframework.data.jdbc.core.dialect.JdbcPostgresDialect;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

@Configuration
@Profile("sqlite")
public class SqliteJdbcConfig extends AbstractJdbcConfiguration {

    @Override
    @Bean
    public JdbcDialect jdbcDialect(NamedParameterJdbcOperations operations) {
        return JdbcPostgresDialect.INSTANCE;
    }

    @Override
    protected List<?> userConverters() {
        return List.of(new LocalDateTimeToText(), new TextToLocalDateTime());
    }

    @WritingConverter
    static class LocalDateTimeToText implements Converter<LocalDateTime, String> {
        @Override
        public String convert(LocalDateTime source) {
            return source.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }

    @ReadingConverter
    static class TextToLocalDateTime implements Converter<String, LocalDateTime> {
        @Override
        public LocalDateTime convert(String source) {
            return LocalDateTime.parse(source.trim().replace(' ', 'T'));
        }
    }
}
