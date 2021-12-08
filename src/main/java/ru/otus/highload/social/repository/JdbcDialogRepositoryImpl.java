package ru.otus.highload.social.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.otus.highload.social.model.Dialog;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JdbcDialogRepositoryImpl implements DialogRepository{

    @Qualifier("insertDialog")
    private final SimpleJdbcInsert insertDialog;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Dialog createDialog(String name, Long userId) {
        Dialog dialog = Dialog.builder().creatorId(userId).name(name).build();
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(dialog);
        Number newKey = insertDialog.executeAndReturnKey(parameterSource);
        dialog.setId(newKey.longValue());
        jdbcTemplate.update("insert into users_dialogs (user_id,dialog_id) values (?, ?)", userId, dialog.getId());
        return dialog;
    }
}
