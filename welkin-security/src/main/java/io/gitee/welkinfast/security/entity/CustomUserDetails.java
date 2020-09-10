package io.gitee.welkinfast.security.entity;

import io.gitee.welkinfast.security.properties.CustomSecurityProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 自定义用户详细信息
 * @Author yuanjg
 * @CreateTime 2020/08/16 12:57
 * @Version 1.0.0
 */
public class CustomUserDetails {

    private String id;
    private String username;
    private String password;
    private List<String> roles;
    private List<String> permissions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        if (permissions == null) {
            return new ArrayList<String>();
        }
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }


    public boolean isAdmin() {
        if (roles != null) {
            for (String role : roles) {
                if (StringUtils.equalsIgnoreCase(role, CustomSecurityProperties.ADMIN_ROLE)) {
                    return true;
                }
            }
        }
        return false;
    }


}
