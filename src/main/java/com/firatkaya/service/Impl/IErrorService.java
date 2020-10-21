package com.firatkaya.service.Impl;

import com.firatkaya.entity.Error;
import com.firatkaya.repository.ErrorRepository;
import com.firatkaya.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IErrorService implements ErrorService {

    private final ErrorRepository errorRepository;

    @Override
    public Error saveError(Error error) {
        return errorRepository.save(error);
    }

    @Override
    public Error updateError(Error error) {
        return errorRepository.save(error);
    }

    @Override
    public void deleteError(Long id) {
        errorRepository.deleteById(id);
    }

    @Override
    public void deleteErrors() {
        errorRepository.deleteAll();
    }

    @Override
    public Error getError(Long id) {
        return errorRepository.findByErrorId(id);
    }

    @Override
    public List<Error> getErrors() {
        return errorRepository.findAll();
    }
}
