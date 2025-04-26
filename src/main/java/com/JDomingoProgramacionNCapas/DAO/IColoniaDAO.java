
package com.JDomingoProgramacionNCapas.DAO;

import com.JDomingoProgramacionNCapas.ML.Result;


public interface IColoniaDAO {
    Result ColoniaByIdMunicipio(int IdMunicipio);
    
    Result ColoniaByCodigoPostal(String CodigoPostal);
}
