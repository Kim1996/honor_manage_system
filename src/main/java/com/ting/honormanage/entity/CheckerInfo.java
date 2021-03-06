package com.ting.honormanage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ting.honormanage.model.CheckerInfoModel;

import javax.persistence.*;

@Entity
public class CheckerInfo {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(length = 11,nullable = false)
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private Authority authority;

    public CheckerInfo() {
    }

    public CheckerInfo(CheckerInfoModel checkerInfoModel)
    {
        this.id = checkerInfoModel.idOfCheckerInfo();
        this.username = checkerInfoModel.usernameOfCheckerInfo();
        this.password = checkerInfoModel.passwordOfCheckerInfo();
        this.name = checkerInfoModel.nameOfCheckerInfo();
        this.phone = checkerInfoModel.phoneOfCheckerInfo();
        this.authority = checkerInfoModel.authorityOfCheckerInfo();
    }

    public void setCheckerInfoFromModel(CheckerInfoModel checkerInfoModel)
    {
        this.id = checkerInfoModel.idOfCheckerInfo();
        this.username = checkerInfoModel.usernameOfCheckerInfo();
        this.password = checkerInfoModel.passwordOfCheckerInfo();
        this.name = checkerInfoModel.nameOfCheckerInfo();
        this.phone = checkerInfoModel.phoneOfCheckerInfo();
        this.authority = checkerInfoModel.authorityOfCheckerInfo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public enum Authority
    {
        //一级权限
        FIRST_LEVEL,
        //二级权限
        SECOND_LEVEL,
        //失效
        INVALID
    }

    public Long idOfCheckerInfoModel() {
        return id;
    }

    public String usernameOfCheckerInfoModel() {
        return username;
    }

    public String passwordOfCheckerInfoModel() {
        return password;
    }

    public String nameOfCheckerInfoModel() {
        return name;
    }

    public String phoneOfCheckerInfoModel() {
        return phone;
    }

    public String authorityOfCheckerInfoModel() {
        String authorityFIRST_LEVEL = "一级权限";
        String authoritySECOND_LEVEL = "二级权限";
        String authorityINVALID = "失效";
        if (this.authority == CheckerInfo.Authority.FIRST_LEVEL) {
            return authorityFIRST_LEVEL;
        } else if (this.authority == CheckerInfo.Authority.SECOND_LEVEL) {
            return authoritySECOND_LEVEL;
        } else if (this.authority == CheckerInfo.Authority.INVALID) {
            return authorityINVALID;
        }else {
            return null;
        }
    }
}
