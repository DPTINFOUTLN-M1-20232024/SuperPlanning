package fr.wedidit.superplanning.superplanning.identifiables.completes.others;

import lombok.extern.slf4j.Slf4j;
import com.google.common.hash.Hashing;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.nio.charset.StandardCharsets;

@Getter
@ToString
@EqualsAndHashCode
@Slf4j
public class StudentConnection {

    private final String mail;
    private final String hashPassword;

    private StudentConnection(String mail, String password) {
        this.mail = mail;
        this.hashPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    public static StudentConnection of(String mail, String hashPassword) {
        return new StudentConnection(mail, hashPassword);
    }

}
