package controllers.modulo

import controllers.CrudController
import errors.ModuloError
import models.Modulo
import java.util.UUID

interface IModuloController: CrudController<Modulo, UUID, ModuloError>