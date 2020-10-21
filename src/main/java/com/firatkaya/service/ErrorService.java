package com.firatkaya.service;


import com.firatkaya.entity.Error;

import java.util.List;

public interface ErrorService {

    Error saveError(Error error);

    Error updateError(Error error);

    void deleteError(Long id);

    void deleteErrors();

    Error getError(Long id);

    List<Error> getErrors();

}
