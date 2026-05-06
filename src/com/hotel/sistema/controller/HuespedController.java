package com.hotel.sistema.controller;

import com.hotel.sistema.dao.HuespedDAO;
import com.hotel.sistema.model.Huesped;

import java.util.List;

public class HuespedController {

	private HuespedDAO dao = new HuespedDAO();

    public boolean guardar(Huesped h) {

        if (h.getNumeroDocumento().isEmpty() ||
            h.getNombres().isEmpty()) {

            return false;
        }

        return dao.registrar(h);
    }

    public List<Huesped> listar() {
        return dao.listar();
    }

    public boolean eliminar(int id) {
        return dao.eliminar(id);
    }
}
