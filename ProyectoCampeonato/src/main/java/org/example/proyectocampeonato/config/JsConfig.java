package org.example.proyectocampeonato.config;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Configuramos los escapes para evitar que escape los caracteres <, > y &
        mapper.getFactory().setCharacterEscapes(new CharacterEscapes() {
            @Override
            public int[] getEscapeCodesForAscii() {
                int[] esc = CharacterEscapes.standardAsciiEscapesForJSON();
                esc['<'] = CharacterEscapes.ESCAPE_NONE;
                esc['>'] = CharacterEscapes.ESCAPE_NONE;
                esc['&'] = CharacterEscapes.ESCAPE_NONE;
                return esc;
            }

            @Override
            public SerializableString getEscapeSequence(int ch) {
                return null;
            }
        });

        return mapper;
    }
}