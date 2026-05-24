package penelope.corretagem.penelopeapirest.core.user;

import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;
import penelope.corretagem.penelopeapirest.core.exception.PasswordResetTokenExpiredException;
import penelope.corretagem.penelopeapirest.core.user.valueObject.AccessLevel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class User {
    private final Long id;
    private final LocalDate dateCreation;
    private AccessLevel accessLevel;
    private String creci;

    private String name;
    private String email;
    private String password;
    private String cpf;
    private LocalDate dateBirth;
    private BigDecimal monthlyIncome;
    private String phone;
    private boolean active;

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

    public void updateInformation(
            String name, String email, String cpf,
            LocalDate dateBirth, BigDecimal monthlyIncome, String phone) {

        if (name != null) this.name = name;
        if (email != null) this.email = email;
        if (cpf != null) this.cpf = cpf;
        if (dateBirth != null) this.dateBirth = dateBirth;
        if (monthlyIncome != null) this.monthlyIncome = monthlyIncome;
        if (phone != null) this.phone = phone;
    }

    public void updateAccessProfile(AccessLevel accessLevel, String creci) {
        AccessLevel targetAccessLevel = accessLevel != null ? accessLevel : this.accessLevel;
        String targetCreci = creci != null ? creci : this.creci;

        if (targetAccessLevel == AccessLevel.CLIENTE && targetCreci != null && !targetCreci.isBlank()) {
            throw new DomainValidationException("Clientes não devem possuir Creci");
        }

        if (targetAccessLevel == AccessLevel.CORRETOR && (targetCreci == null || targetCreci.isBlank())) {
            throw new DomainValidationException("Corretores devem possuir Creci");
        }

        this.accessLevel = targetAccessLevel;
        this.creci = (targetCreci != null && targetCreci.isBlank()) ? null : targetCreci;
    }

    public void generatePasswordResetToken(String token, Date expiryDate) {
        this.passwordResetToken = token;
        this.passwordResetTokenExpiry = expiryDate;
    }

    public void applyNewPassword(String newEncryptedPassword) {

        if (this.passwordResetTokenExpiry == null || this.passwordResetTokenExpiry.before(new Date())) {
            throw new PasswordResetTokenExpiredException();
        }

        this.password = newEncryptedPassword;
        this.passwordResetToken = null;       // Limpa o token para não ser usado de novo
        this.passwordResetTokenExpiry = null; // Limpa a data de expiração
    }

    public void changePassword(String newEncryptedPassword) {
        if (newEncryptedPassword != null) {
            this.password = newEncryptedPassword;
        }
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
