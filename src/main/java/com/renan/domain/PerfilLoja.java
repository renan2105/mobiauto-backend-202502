package com.renan.domain;

import com.renan.domain.enums.Cargo;
import java.util.UUID;

public class PerfilLoja {
    private UUID lojaId;
    private Cargo cargo;

    public PerfilLoja() {}

    public PerfilLoja(UUID lojaId, Cargo cargo) {
        this.lojaId = lojaId;
        this.cargo = cargo;
    }

    public UUID getLojaId() {
        return lojaId;
    }

    public void setLojaId(UUID lojaId) {
        this.lojaId = lojaId;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
