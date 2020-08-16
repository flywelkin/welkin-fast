package io.gitee.welkinfast.security.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/16 12:57
 * @Version 1.0.0
 */
public class DefaultUserDetails {

    private String id;
    private String username;
    private String password;
    private List<String> permissions;

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

    public List<String> getPermissions() {
        if (permissions == null) {
            return new ArrayList();
        }
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
