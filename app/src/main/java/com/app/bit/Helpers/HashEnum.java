package com.app.bit.Helpers;

public enum HashEnum {
    MD5(1),
    SHA1(2),
    SHA256(3);

    private int opcao;

    HashEnum(int valor)
    {
        opcao = valor;
    }

    public int getOpcao()
    {
        return opcao;
    }
}
