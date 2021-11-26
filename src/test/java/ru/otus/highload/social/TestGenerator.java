package ru.otus.highload.social;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.otus.highload.social.model.Gender;
import ru.otus.highload.social.model.Role;
import ru.otus.highload.social.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class TestGenerator {

    @Test
    @SneakyThrows
    void testNames() {
        List<List<String>> names = filterNames(getnames("src/test/resources/testdata/russian_names.csv"), 10000, 6000000);
        List<List<String>> surnames = filterNames(getnames("src/test/resources/testdata/russian_surnames.csv"), 10000, 800000);
        List<String> strings = new ArrayList<>();
        PrintWriter writer = new PrintWriter("src/test/resources/testdata/names_result.csv");
        StringBuilder stringBuilder = new StringBuilder();

        log.info("Start - " + Instant.now());
        for (int i = 0; i < 1000000; i++) {
            //strings.add(getRandomName(names, countBound(names)) +";"+ getRandomName(surnames, countBound(surnames)));
            stringBuilder.append(getRandomName(names, countBound(names)) + ";" + getRandomName(surnames, countBound(surnames)) + "\n");
            if (i % 1000 == 0) {
                log.info("---> " + i);
            }
        }
        log.info("End - " + Instant.now());
        writer.write(stringBuilder.toString());
        writer.flush();
        strings.size();
    }

    @Test
    @SneakyThrows
    void generateSql() {
        String start = "insert into users(login, password,role, first_name, last_name, city) values ";
        String pattern = " ('loginValue', '$2a$12$cTHLwZ2plcg6gh1pLTJna.KK1JzFg2cHMh6TBOLufcmZ7CbvlwRyO', 'USER', 'fname','lname', 'city' ),\n";
        PrintWriter writer = new PrintWriter("src/test/resources/testdata/insert_users.sql");
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/testdata/names_result.csv"))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null && i < 1000000) {
                String[] values = line.split(";");
                String login = "login" + i;
                String fName = values[0];
                String lName = values[1];
                String sql = pattern.replaceFirst("loginValue", login)
                        .replaceFirst("fname", values[0])
                        .replaceFirst("lname", values[1]);
                if (i % 2000 == 0) {
                    sql = start + sql;
                }
                if (i % 2000 == 1999) {
                    sql = sql.replaceFirst(",\n", ";\n");
                }
                stringBuilder.append(sql);
                if (i % 2000 == 0) {
                    log.info("---> " + i);
                }
                i++;
            }
            writer.write(stringBuilder.toString());
            writer.flush();
        }
    }

    private int countBound(List<List<String>> names) {
        return names.stream()
                .map(row -> intFromString(row.get(3)))
                .reduce(Integer::sum)
                .get();

    }

    int intFromString(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }

    @SneakyThrows
    List<List<String>> getnames(String file) {
        int count = 0;
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
                count += intFromString(values[3]);
            }
        }
        log.info("count - " + count);
        return records;
    }

    String getRandomName(List<List<String>> names, int bound) {
        int index = new Random().nextInt(bound) + 1;
        int i = 1;
        int previ = i;
        int j = 1;
        String cur = "";
        while (i < index && j < names.size()) {
            int count = intFromString(names.get(j).get(3));
            cur = names.get(j).get(1);
            i += count;
            if (i >= index && previ < index) {
                return cur;
            }
            j++;
            previ = i;
        }
        return names.get(new Random().nextInt(names.size() - 3) + 1).get(1);
    }

    private List<List<String>> filterNames(List<List<String>> initialNames, int limit1, int limit2) {
        return initialNames.stream()
                .filter(names -> intFromString(names.get(3)) > limit1 && intFromString(names.get(3)) < limit2)
                .collect(Collectors.toList());
    }

}
