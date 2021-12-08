package ru.otus.highload.social.service;

import ru.otus.highload.social.model.Dialog;

public interface DialogService {

    Dialog createDialog(String name, Long userId);

}
