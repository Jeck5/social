package ru.otus.highload.social.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.highload.social.model.Dialog;
import ru.otus.highload.social.repository.DialogRepository;

@Service
@Transactional
@AllArgsConstructor
public class DialogServiceImpl implements DialogService {

    private final DialogRepository dialogRepository;

    @Override
    public Dialog createDialog(String name, Long userId) {
        return dialogRepository.createDialog(name, userId);
    }
}
