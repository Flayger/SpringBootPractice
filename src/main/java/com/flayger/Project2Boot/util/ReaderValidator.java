package com.flayger.Project2Boot.util;


import com.flayger.Project2Boot.model.Reader;
import com.flayger.Project2Boot.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class ReaderValidator implements Validator {

    private final ReaderRepository readerRepository;

    @Autowired
    public ReaderValidator(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Reader.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Reader reader = (Reader) o;

        Optional<Reader> readerOptional = Optional.ofNullable(readerRepository.findByName(reader.getName()));

        if (readerOptional.isPresent()) {
            if (readerOptional.get().getId() == (reader.getId())) {
                return;
            }
            errors.rejectValue("name", "", "это имя уже занято");
        }
    }
}
