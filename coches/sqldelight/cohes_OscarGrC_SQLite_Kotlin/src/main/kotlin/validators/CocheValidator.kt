package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import errors.CochesErrors
import models.Coche



fun Coche.validate(): com.github.michaelbull.result.Result<Coche,CochesErrors> {
    if(marca.isBlank()){
        return Err(CochesErrors.MarcaVacia("La marca no puede estar vacía"))
    }
    if(modelo.isBlank()){
        return Err(CochesErrors.ModeloVacio("El modelo no puede estar vacío"))
    }
    if(precio<=0){
        return Err(CochesErrors.PrecioNegativo("El precio tiene que ser mayor a 0"))
    }
    return Ok(this)
}



