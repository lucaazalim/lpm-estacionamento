package br.pucminas.titas.enums;

import br.pucminas.titas.entidades.Plano;

/**
 * Representa o tipo de plano de um cliente.
 */
public enum TipoPlano {

    HORISTA(new Plano.Horista("Horista")),
    TURNISTA_MANHA(new Plano.Turnista(Turno.MANHA)),
    TURNISTA_TARDE(new Plano.Turnista(Turno.TARDE)),
    TURNISTA_NOITE(new Plano.Turnista(Turno.NOITE)),
    MENSALISTA(new Plano.Mensalista());

    private Plano plano;

    TipoPlano(Plano plano) {
        this.plano = plano;
    }

    public Plano getPlano() {
        return this.plano;
    }

    @Override
    public String toString() {
        return this.getPlano().toString();
    }
}
