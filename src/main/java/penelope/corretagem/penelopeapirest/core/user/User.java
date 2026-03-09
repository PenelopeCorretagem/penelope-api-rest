package penelope.corretagem.penelopeapirest.core.user;

import penelope.corretagem.penelopeapirest.data.domain.enums.AccessLevel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class User {
    private final Long id;
    private final String name;
    private final String email;
    private final String password;
    private final String cpf;
    private final LocalDate dateBirth;
    private final BigDecimal monthlyIncome;
    private final String phone;
    private final String creci;
    private final AccessLevel accessLevel;
    private final LocalDate dateCreation;
    private final boolean active;
    private String passwordResetToken;
    private Date passwordResetTokenExpiry;

    private User(
            Long id,
            String name,
            String email,
            String password,
            String cpf,
            LocalDate dateBirth,
            BigDecimal monthlyIncome,
            String phone,
            String creci,
            AccessLevel accessLevel,
            LocalDate dateCreation,
            boolean active,
            String passwordResetToken,
            Date passwordResetTokenExpiry
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.dateBirth = dateBirth;
        this.monthlyIncome = monthlyIncome;
        this.phone = phone;
        this.creci = creci;
        this.accessLevel = accessLevel;
        this.dateCreation = dateCreation;
        this.active = active;
        this.passwordResetToken = passwordResetToken;
        this.passwordResetTokenExpiry = passwordResetTokenExpiry;
    }

    public static User createNew(
            String name,
            String email,
            String password,
            String cpf,
            LocalDate dateBirth,
            BigDecimal monthlyIncome,
            String phone,
            String creci,
            AccessLevel accessLevel
    ) {
        return new User(
                null,
                name,
                email,
                password,
                cpf,
                dateBirth,
                monthlyIncome,
                phone,
                creci,
                accessLevel,
                LocalDate.now(),
                true,
                null,
                null);
    }

    public static User restore(
            Long id,
            String name,
            String email,
            String password,
            String cpf,
            LocalDate dateBirth,
            BigDecimal monthlyIncome,
            String phone,
            String creci,
            AccessLevel accessLevel,
            LocalDate dateCreation,
            boolean active,
            String passwordResetToken,
            Date passwordResetTokenExpiry
    ) {
        return new User(
                id,
                name,
                email,
                password,
                cpf,
                dateBirth,
                monthlyIncome,
                phone,
                creci,
                accessLevel,
                dateCreation,
                active,
                passwordResetToken,
                passwordResetTokenExpiry
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public String getPhone() {
        return phone;
    }

    public String getCreci() {
        return creci;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public boolean isActive() {
        return active;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public Date getPasswordResetTokenExpiry() {
        return passwordResetTokenExpiry;
    }
}