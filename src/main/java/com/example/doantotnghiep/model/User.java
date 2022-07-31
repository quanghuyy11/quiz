package com.example.doantotnghiep.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;

@Component
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Vui lòng nhập tên đăng nhập")
    private String username;

    @Length(min = 6,message = "Không được ít hơn 6 kí tự.")
    @NotBlank(message = "Mật khẩu không được để trống.")
    private String passWord;

    @Length(min = 6)
    @NotBlank(message = "Mật khẩu không được để trống.")
    private String rePassWord;

    @NotNull
    private String fullName;

    @Pattern(regexp = "^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$", message = "Sai định dạng.")
    @NotBlank(message = " Email không được để trống.")
    private String email;

    @NotNull
    private String address;

    @Pattern(regexp = "^((\\(84\\)\\+)|(0))((91)|(90))[\\d]{7}$", message = "Sai định dạng.")
    private String phoneNumber;

    @Column(name = "img", columnDefinition = "text")
    private String img;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;


    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Result> results;

    public User() {
    }

    public User(Integer id,
                @NotBlank(message = "Tài khoản không được để trống.") String username,
                @Length(min = 6,message = "Không được ít hơn 6 kí tự.") @NotBlank(message = "Mật khẩu không được để trống.") String passWord,
                @Length(min = 6) @NotBlank(message = "Mật khẩu không được để trống.") String rePassWord,
                @NotBlank(message = "Tên không được để trống.") String fullName,
                @Pattern(regexp = "^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$", message = "Sai định dạng.") @NotBlank(message = " Email không được để trống.") String email,
                @NotBlank(message = "Địa chỉ  không được để trống") String address,
                @Pattern(regexp = "^((\\(84\\)\\+)|(0))((91)|(90))[\\d]{7}$", message = "Sai định dạng.") String phoneNumber,
                String img, Set<Role> roles, List<Exam> exams, List<Result> results) {
        this.id = id;
        this.username = username;
        this.passWord = passWord;
        this.rePassWord = rePassWord;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.img = img;
        this.roles = roles;
        this.results = results;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRePassWord() {
        return rePassWord;
    }

    public void setRePassWord(String rePassWord) {
        this.rePassWord = rePassWord;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
