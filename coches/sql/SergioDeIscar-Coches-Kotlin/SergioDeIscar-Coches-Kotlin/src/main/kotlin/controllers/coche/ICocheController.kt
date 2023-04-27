package controllers.coche

import controllers.CrudController
import errors.CocheError
import models.Coche

interface ICocheController: CrudController<Coche, Long, CocheError>