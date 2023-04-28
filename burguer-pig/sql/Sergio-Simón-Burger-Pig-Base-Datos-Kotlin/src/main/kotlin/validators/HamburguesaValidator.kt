package validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import errors.HamburguesaErrors
import models.Hamburguesa

//fun Hamburguesa.validate(): Result<Hamburguesa, HamburguesaErrors> {
//    return when {
//        precio <= 0 -> Err(HamburguesaErrors.PrecioNoValido("Precio no valido"))
//        cantidad <= 0 -> Err(HamburguesaErrors.CantidadNoValida("Precio no valido"))
//        else -> Ok(this)
//    }
//}