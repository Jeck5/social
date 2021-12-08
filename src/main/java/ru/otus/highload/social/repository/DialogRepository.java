package ru.otus.highload.social.repository;

import ru.otus.highload.social.model.Dialog;

public interface DialogRepository {

    Dialog createDialog(String name, Long userId);

}
