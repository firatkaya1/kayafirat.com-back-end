package com.kayafirat.service.Impl;

import com.kayafirat.entity.Error;
import com.kayafirat.repository.ErrorRepository;
import com.kayafirat.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void updateReadStatus(Long id){
        errorRepository.updateReadStatus(id);
    }
}
