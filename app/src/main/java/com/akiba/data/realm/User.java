package com.akiba.data.realm;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Leyo on 18/02/2017.
 */

public class User extends RealmObject {

    @PrimaryKey
    private String id;
    @Required
    private String nama;
    @Ignore
    private String alamat;

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId() {
        return id;
    }
    // getters and setters
    //getters and setters
}