package net.symbiosis.persistence.entity.complex_type;

import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.enumeration.sym_country;
import net.symbiosis.persistence.entity.enumeration.sym_language;
import net.symbiosis.persistence.entity.enumeration.sym_response_code;
import net.symbiosis.persistence.entity.super_class.sym_entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "sym_user_id"))
public class sym_user extends sym_entity<sym_user> {
    @Column(length = 20)
    private String first_name;
    @Column(length = 20)
    private String last_name;
    @Basic
    private Date date_of_birth;
    @Column(unique = true, length = 20)
    private String username;
    @Basic
    private String password;
    @Basic
    private String pin;
    @Column(nullable = false, precision = 1)
    private Integer password_tries;
    @Column(nullable = false, precision = 1)
    private Integer pin_tries;
    @Basic(optional = false)
    @Column(length = 128)
    private String salt;
    @Column(unique = true)
    private String email;
    @Column(unique = true, length = 12)
    private String msisdn;
    @Column(length = 12)
    private String msisdn2;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_status_id")
    private sym_response_code user_status;
    @OneToOne
    @JoinColumn(name = "wallet_id")
    private sym_wallet wallet;
    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id")
    private sym_country country;
    @ManyToOne(optional = false)
    @JoinColumn(name = "language_id")
    private sym_language language;

    public sym_user() {
    }

    public sym_user(String first_name, String last_name, Date date_of_birth, String username, String password,
                    String pin, Integer password_tries, Integer pin_tries, String salt, String email,
                    String msisdn, String msisdn2, sym_response_code user_status,
                    sym_wallet wallet, sym_country country, sym_language language) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.username = username;
        this.password = password;
        this.pin = pin;
        this.password_tries = password_tries;
        this.pin_tries = pin_tries;
        this.salt = salt;
        this.email = email;
        this.msisdn = msisdn;
        this.msisdn2 = msisdn2;
        this.user_status = user_status;
        this.wallet = wallet;
        this.country = country;
        this.language = language;
    }

    public sym_user(String first_name, String last_name, Date date_of_birth, String username, String password,
                    String pin, Integer password_tries, Integer pin_tries, String salt, String email,
                    String msisdn, String msisdn2, sym_response_code user_status,
                    sym_country country, sym_language language) {
        this(first_name, last_name, date_of_birth, username, password, pin, password_tries,
                pin_tries, salt, email, msisdn, msisdn2, user_status, null, country, language);
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMsisdn2() {
        return msisdn2;
    }

    public void setMsisdn2(String msisdn2) {
        this.msisdn2 = msisdn2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Integer getPassword_tries() {
        return password_tries;
    }

    public void setPassword_tries(Integer password_tries) {
        this.password_tries = password_tries;
    }

    public Integer getPin_tries() {
        return pin_tries;
    }

    public void setPin_tries(Integer pin_tries) {
        this.pin_tries = pin_tries;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public sym_response_code getUser_status() {
        return user_status;
    }

    public void setUser_status(sym_response_code user_status) {
        this.user_status = user_status;
    }

    public sym_wallet getWallet() {
        return wallet;
    }

    public sym_user setWallet(sym_wallet wallet) {
        this.wallet = wallet;
        return this;
    }

    public sym_country getCountry() {
        return country;
    }

    public void setCountry(sym_country country) {
        this.country = country;
    }

    public sym_language getLanguage() {
        return language;
    }

    public void setLanguage(sym_language language) {
        this.language = language;
    }

//	@OrderColumn(name = "auth_user_id")
//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = sym_auth_user.class)
//	@JoinColumn(name = "sym_user_id")
//	public Set<sym_auth_user> getAuth_users() {
//		return auth_users;
//	}
//
//	public void setAuth_users(Set<sym_auth_user> auth_users) { this.auth_users = auth_users; }
}
